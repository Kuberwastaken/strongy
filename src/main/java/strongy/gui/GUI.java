package strongy.gui;

import strongy.event.DisposeHandler;
import strongy.gui.frames.StrongyFrame;
import strongy.gui.frames.OptionsFrame;
import strongy.gui.splash.Progress;
import strongy.gui.style.SizePreference;
import strongy.gui.style.StyleManager;
import strongy.gui.style.theme.Theme;
import strongy.io.AutoResetTimer;
import strongy.io.ClipboardReader;
import strongy.io.KeyboardListener;
import strongy.io.api.StrongyHttpServer;
import strongy.io.mcinstance.ActiveInstanceProviderFactory;
import strongy.io.mcinstance.IActiveInstanceProvider;
import strongy.io.overlay.StrongyOverlayImageWriter;
import strongy.io.overlay.OBSOverlay;
import strongy.io.preferences.StrongyPreferences;
import strongy.io.savestate.TempFileAccessor;
import strongy.io.updatechecker.GithubUpdateChecker;
import strongy.model.ModelState;
import strongy.model.actions.IActionExecutor;
import strongy.model.datastate.IDataState;
import strongy.model.datastate.calibrator.CalibratorFactory;
import strongy.model.datastate.endereye.CoordinateInputSource;
import strongy.model.datastate.endereye.EnderEyeThrowFactory;
import strongy.model.datastate.endereye.IEnderEyeThrowFactory;
import strongy.model.domainmodel.DomainModelImportExportService;
import strongy.model.domainmodel.IDomainModel;
import strongy.model.environmentstate.IEnvironmentState;
import strongy.model.information.CombinedCertaintyInformationProvider;
import strongy.model.information.InformationMessageList;
import strongy.model.information.McVersionWarningProvider;
import strongy.model.information.MismeasureWarningProvider;
import strongy.model.information.NextThrowDirectionInformationProvider;
import strongy.model.information.PortalLinkingWarningProvider;
import strongy.model.input.ActiveInstanceInputHandler;
import strongy.model.input.ButtonInputHandler;
import strongy.model.input.F3ILocationInputHandler;
import strongy.model.input.HotkeyInputHandler;
import strongy.model.input.IButtonInputHandler;
import strongy.model.input.PlayerPositionInputHandler;
import strongy.util.Profiler;

/**
 * Main class for the user interface.
 */
public class GUI {

	private final StrongyPreferences preferences;

	private ClipboardReader clipboardReader;
	private IActiveInstanceProvider activeInstanceProvider;
	private AutoResetTimer autoResetTimer;

	private StyleManager styleManager;
	private StrongyFrame StrongyFrame;
	private OptionsFrame optionsFrame;

	private IDomainModel domainModel;
	private IActionExecutor actionExecutor;
	private IEnvironmentState environmentState;
	private IDataState dataState;
	private DomainModelImportExportService domainModelImportExportService;

	private CoordinateInputSource coordinateInputSource;
	private IButtonInputHandler buttonInputHandler;

	private InformationMessageList informationMessageList;

	private OBSOverlay obsOverlay;
	private StrongyHttpServer StrongyHttpServer;

	private final DisposeHandler disposeHandler = new DisposeHandler();

	public GUI(StrongyPreferences preferences) {
		this.preferences = preferences;
		initInputMethods();
		initModel();
		initInputHandlers();
		initDataProcessors();
		initUI();
		postInit();
	}

	private void initInputMethods() {
		Progress.setTask("Starting clipboard reader", 0.02f);
		Profiler.start("Init clipboard reader");
		clipboardReader = new ClipboardReader(preferences);
		KeyboardListener.init(clipboardReader, preferences.altClipboardReader);

		Progress.setTask("Starting instance listener", 0.03f);
		Profiler.start("Init instance listener");
		activeInstanceProvider = ActiveInstanceProviderFactory.createPlatformSpecificActiveInstanceProvider();

		Profiler.stop();
	}

	private void initModel() {
		Progress.setTask("Creating calculator data", 0.07f);
		Profiler.start("Init DataState");
		ModelState modelState = disposeHandler.add(new ModelState(preferences));
		domainModel = modelState.domainModel;
		actionExecutor = modelState.actionExecutor;
		environmentState = modelState.environmentState;
		dataState = modelState.dataState;
		domainModelImportExportService = new DomainModelImportExportService(domainModel,
				new TempFileAccessor("Strongy-save-state.txt"), preferences);
		domainModelImportExportService.triggerDeserialization();
		domainModel.deleteHistory();
		Profiler.stop();
	}

	private void initInputHandlers() {
		Progress.setTask("Initializing input handlers", 0.08f);
		coordinateInputSource = disposeHandler.add(new CoordinateInputSource(clipboardReader));
		IEnderEyeThrowFactory enderEyeThrowFactory = new EnderEyeThrowFactory(preferences, dataState.boatDataState());
		disposeHandler.add(new PlayerPositionInputHandler(coordinateInputSource, dataState, actionExecutor, preferences,
				enderEyeThrowFactory));
		disposeHandler.add(new F3ILocationInputHandler(coordinateInputSource, dataState, actionExecutor, preferences));
		disposeHandler.add(new ActiveInstanceInputHandler(activeInstanceProvider, domainModel, dataState,
				actionExecutor, preferences));
		disposeHandler.add(new HotkeyInputHandler(preferences, domainModel, dataState, actionExecutor));
		buttonInputHandler = new ButtonInputHandler(domainModel, dataState, actionExecutor);
	}

	private void initDataProcessors() {
		Progress.setTask("Initializing information message generators", 0.09f);
		Profiler.start("Init info message list");
		informationMessageList = new InformationMessageList();
		Profiler.stop();
	}

	private void initUI() {
		Progress.setTask("Loading themes", 0.30f);
		Profiler.start("Init StyleManager");
		Theme.loadThemes(preferences);
		styleManager = new StyleManager(Theme.get(preferences.theme.get()), SizePreference.get(preferences.size.get()));
		preferences.size.whenModified().subscribeEDT(size -> styleManager.setSizePreference(SizePreference.get(size)));
		preferences.theme.whenModified()
				.subscribeEDT(theme_uid -> styleManager.currentTheme.setTheme(Theme.get(theme_uid)));

		Progress.setTask("Creating main window", 0.93f);
		Profiler.stopAndStart("Create frame");
		StrongyFrame = new StrongyFrame(styleManager, preferences, new GithubUpdateChecker(), dataState,
				buttonInputHandler, informationMessageList);

		Progress.setTask("Creating settings window", 0.95f);
		Profiler.stopAndStart("Create settings window");
		StrongyFrame.getSettingsButton().addActionListener(__ -> getOrCreateOptionsFrame().toggleWindow(StrongyFrame));

		Progress.setTask("Settings fonts and colors", 0.99f);
		Profiler.stopAndStart("Init fonts, colors, bounds");
		styleManager.init();
		Profiler.stop();
	}

	private void postInit() {
		Progress.setTask("Finishing up gui", 1f);
		Profiler.start("Post init");

		clipboardReader.start();

		Profiler.start("Init info message generators");
		informationMessageList
				.AddInformationMessageProvider(new McVersionWarningProvider(activeInstanceProvider, preferences));
		informationMessageList
				.AddInformationMessageProvider(new MismeasureWarningProvider(dataState, environmentState, preferences));
		informationMessageList.AddInformationMessageProvider(new PortalLinkingWarningProvider(dataState, preferences));
		informationMessageList
				.AddInformationMessageProvider(new CombinedCertaintyInformationProvider(dataState, preferences));
		informationMessageList.AddInformationMessageProvider(
				new NextThrowDirectionInformationProvider(dataState, environmentState, preferences));
		Profiler.stop();

		autoResetTimer = new AutoResetTimer(dataState, domainModel, actionExecutor, preferences);

		obsOverlay = new OBSOverlay(StrongyFrame, preferences, dataState, domainModel, new StrongyOverlayImageWriter(),
				1000);
		StrongyHttpServer = new StrongyHttpServer(dataState, domainModel, informationMessageList, preferences);

		StrongyFrame.checkIfOffScreen();
		StrongyFrame.setVisible(true);

		Runtime.getRuntime().addShutdownHook(onShutdown());
		Profiler.stop();
	}

	private OptionsFrame getOrCreateOptionsFrame() {
		if (optionsFrame == null) {
			optionsFrame = new OptionsFrame(styleManager, preferences,
					new CalibratorFactory(environmentState.calculatorSettings(), coordinateInputSource, preferences),
					activeInstanceProvider, actionExecutor);
			styleManager.init();
		}
		return optionsFrame;
	}

	private Thread onShutdown() {
		return new Thread("Shutdown") {
			@Override
			public void run() {
				preferences.windowX.set(StrongyFrame.getX());
				preferences.windowY.set(StrongyFrame.getY());
				domainModelImportExportService.onShutdown();
				disposeHandler.dispose();
				obsOverlay.dispose();
				autoResetTimer.dispose();
				informationMessageList.dispose();
				StrongyHttpServer.dispose();
			}
		};
	}

}
