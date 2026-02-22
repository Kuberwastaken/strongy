package strongy.gui;

import javafx.application.Platform;
import javafx.stage.Stage;

import strongy.event.DisposeHandler;
import strongy.gui.frames.StrongyFrame;
import strongy.gui.frames.OptionsFrame;
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

/**
 * Main class for the user interface — JavaFX version.
 */
public class GUI {

	private final StrongyPreferences preferences;

	private ClipboardReader clipboardReader;
	private IActiveInstanceProvider activeInstanceProvider;
	private AutoResetTimer autoResetTimer;

	private StyleManager styleManager;
	private StrongyFrame strongyFrame;
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
	private StrongyHttpServer strongyHttpServer;

	private final DisposeHandler disposeHandler = new DisposeHandler();

	/**
	 * Constructor for use with an existing Stage from StrongyApp.
	 */
	public GUI(StrongyPreferences preferences, Stage primaryStage) {
		this.preferences = preferences;
		initInputMethods();
		initModel();
		initInputHandlers();
		initDataProcessors();
		initUI(primaryStage);
		postInit();
	}

	private void initInputMethods() {
		clipboardReader = new ClipboardReader(preferences);
		KeyboardListener.init(clipboardReader, preferences.altClipboardReader);
		activeInstanceProvider = ActiveInstanceProviderFactory.createPlatformSpecificActiveInstanceProvider();
	}

	private void initModel() {
		ModelState modelState = disposeHandler.add(new ModelState(preferences));
		domainModel = modelState.domainModel;
		actionExecutor = modelState.actionExecutor;
		environmentState = modelState.environmentState;
		dataState = modelState.dataState;
		domainModelImportExportService = new DomainModelImportExportService(domainModel,
				new TempFileAccessor("Strongy-save-state.txt"), preferences);
		domainModelImportExportService.triggerDeserialization();
		domainModel.deleteHistory();
	}

	private void initInputHandlers() {
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
		informationMessageList = new InformationMessageList();
	}

	private void initUI(Stage primaryStage) {
		Theme.loadThemes(preferences);
		styleManager = new StyleManager(Theme.get(preferences.theme.get()), SizePreference.get(preferences.size.get()));
		preferences.size.whenModified()
				.subscribe(size -> Platform.runLater(() -> styleManager.setSizePreference(SizePreference.get(size))));
		preferences.theme.whenModified().subscribe(
				theme_uid -> Platform.runLater(() -> styleManager.currentTheme.setTheme(Theme.get(theme_uid))));

		strongyFrame = new StrongyFrame(styleManager, preferences, new GithubUpdateChecker(), dataState,
				buttonInputHandler, informationMessageList);

		// Settings button opens settings
		strongyFrame.getSettingsButton().setOnAction(__ -> {
			if (optionsFrame == null) {
				optionsFrame = new OptionsFrame(styleManager, preferences,
						new CalibratorFactory(environmentState.calculatorSettings(), coordinateInputSource,
								preferences),
						activeInstanceProvider, actionExecutor);
				styleManager.init();
			}
			optionsFrame.toggleWindow(strongyFrame.getStage());
		});

		styleManager.init();
	}

	private void postInit() {
		clipboardReader.start();

		informationMessageList
				.AddInformationMessageProvider(new McVersionWarningProvider(activeInstanceProvider, preferences));
		informationMessageList
				.AddInformationMessageProvider(new MismeasureWarningProvider(dataState, environmentState, preferences));
		informationMessageList.AddInformationMessageProvider(new PortalLinkingWarningProvider(dataState, preferences));
		informationMessageList
				.AddInformationMessageProvider(new CombinedCertaintyInformationProvider(dataState, preferences));
		informationMessageList.AddInformationMessageProvider(
				new NextThrowDirectionInformationProvider(dataState, environmentState, preferences));

		autoResetTimer = new AutoResetTimer(dataState, domainModel, actionExecutor, preferences);

		obsOverlay = new OBSOverlay(strongyFrame, preferences, dataState, domainModel, new StrongyOverlayImageWriter(),
				1000);
		strongyHttpServer = new StrongyHttpServer(dataState, domainModel, informationMessageList, preferences);

		strongyFrame.checkIfOffScreen();
		strongyFrame.setVisible(true);

		Runtime.getRuntime().addShutdownHook(onShutdown());
	}

	/**
	 * Called by StrongyApp.stop() for graceful shutdown.
	 */
	public void shutdown() {
		preferences.windowX.set(strongyFrame.getX());
		preferences.windowY.set(strongyFrame.getY());
		domainModelImportExportService.onShutdown();
		disposeHandler.dispose();
		obsOverlay.dispose();
		autoResetTimer.dispose();
		informationMessageList.dispose();
		strongyHttpServer.dispose();
	}

	private Thread onShutdown() {
		return new Thread("Shutdown") {
			@Override
			public void run() {
				shutdown();
			}
		};
	}
}
