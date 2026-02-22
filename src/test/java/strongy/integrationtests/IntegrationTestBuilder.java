package strongy.integrationtests;

import java.util.Locale;

import strongy.event.DisposeHandler;
import strongy.gui.frames.StrongyFrame;
import strongy.gui.mainwindow.boateye.BoatIcon;
import strongy.gui.mainwindow.eyethrows.EnderEyePanel;
import strongy.gui.mainwindow.eyethrows.EnderEyePanelTestAdapter;
import strongy.gui.mainwindow.main.MainTextArea;
import strongy.gui.mainwindow.main.MainTextAreaTestAdapter;
import strongy.gui.style.StyleManager;
import strongy.io.mcinstance.IMinecraftWorldFile;
import strongy.io.mcinstance.McInstanceTestAdapter;
import strongy.io.mcinstance.MinecraftInstance;
import strongy.io.preferences.HotkeyPreference;
import strongy.io.preferences.StrongyPreferences;
import strongy.io.preferences.UnsavedPreferences;
import strongy.io.preferences.enums.AllAdvancementsToggleType;
import strongy.io.preferences.enums.AngleAdjustmentType;
import strongy.io.preferences.enums.MainViewType;
import strongy.io.preferences.enums.McVersion;
import strongy.io.preferences.enums.StrongholdDisplayType;
import strongy.model.ModelState;
import strongy.model.actions.IActionExecutor;
import strongy.model.datastate.IDataState;
import strongy.model.datastate.calibrator.CalibratorFactory;
import strongy.model.datastate.common.IDetailedPlayerPosition;
import strongy.model.datastate.endereye.CoordinateInputSource;
import strongy.model.datastate.endereye.EnderEyeThrowFactory;
import strongy.model.datastate.endereye.F3IData;
import strongy.model.datastate.endereye.IEnderEyeThrowFactory;
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
import strongy.model.input.PlayerPositionInputHandler;
import strongy.util.Assert;
import strongy.util.FakeCoordinateInputSource;
import strongy.util.FakeMinecraftWorldFile;
import strongy.util.FakeUpdateChecker;
import strongy.util.MockedClipboardReader;
import strongy.util.MockedInstanceProvider;
import strongy.util.TestTheme2;
import strongy.util.TestUtils;

public class IntegrationTestBuilder {

	public final StrongyPreferences preferences;
	public final IDomainModel domainModel;

	public final IActionExecutor actionExecutor;
	public final IEnvironmentState environmentState;
	public final IDataState dataState;

	private CoordinateInputSource coordinateInputSource;
	private FakeCoordinateInputSource fakeCoordinateInputSource;
	private MockedClipboardReader clipboardReader;
	private MockedInstanceProvider activeInstanceProvider;

	private PlayerPositionInputHandler playerPositionInputHandler;
	private PlayerPositionInputHandler fakePlayerPositionInputHandler;
	private F3ILocationInputHandler f3iLocationInputHandler;
	private F3ILocationInputHandler fakeF3ILocationInputHandler;
	private HotkeyInputHandler hotkeyInputHandler;
	private ButtonInputHandler buttonInputHandler;
	private ActiveInstanceInputHandler activeInstanceInputHandler;

	private StyleManager styleManager;

	public IntegrationTestBuilder() {
		Locale.setDefault(Locale.US);
		preferences = new StrongyPreferences(new UnsavedPreferences());
		ModelState modelState = new ModelState(preferences);
		domainModel = modelState.domainModel;
		actionExecutor = modelState.actionExecutor;
		environmentState = modelState.environmentState;
		dataState = modelState.dataState;
	}

	public IntegrationTestBuilder withProSettings() {
		preferences.sigma.set(0.005f);
		preferences.view.set(MainViewType.DETAILED);
		preferences.strongholdDisplayType.set(StrongholdDisplayType.CHUNK);
		preferences.useAltStd.set(true);
		return this;
	}

	public IntegrationTestBuilder withAllInformationMessagesSettings() {
		preferences.informationDirectionHelpEnabled.set(true);
		preferences.informationMismeasureEnabled.set(true);
		preferences.informationCombinedCertaintyEnabled.set(true);
		preferences.informationMismeasureEnabled.set(true);
		return this;
	}

	public IntegrationTestBuilder withMcVersionSetting(McVersion mcVersion) {
		preferences.mcVersion.set(mcVersion);
		return this;
	}

	public IntegrationTestBuilder withAllAdvancementsSettings() {
		preferences.allAdvancements.set(true);
		preferences.allAdvancementsToggleType.set(AllAdvancementsToggleType.Automatic);
		return this;
	}

	public IntegrationTestBuilder withObsOverlaySettings() {
		preferences.overlayHideDelay.set(10);
		preferences.overlayAutoHide.set(true);
		preferences.overlayHideWhenLocked.set(true);
		preferences.useOverlay.set(true);
		return this;
	}

	public IntegrationTestBuilder withBoatSettings() {
		preferences.sigmaBoat.set(0.001f);
		preferences.sensitivityAutomatic.set(0.065292805);
		preferences.resolutionHeight.set(16384);
		preferences.angleAdjustmentType.set(AngleAdjustmentType.TALL);
		preferences.usePreciseAngle.set(true);
		preferences.view.set(MainViewType.DETAILED);
		preferences.strongholdDisplayType.set(StrongholdDisplayType.CHUNK);
		return this;
	}

	public IntegrationTestBuilder withDoogileBoatSettings() {
		preferences.sigmaBoat.set(0.0007f);
		preferences.sensitivityAutomatic.set(0.00467673);
		preferences.resolutionHeight.set(16384);
		preferences.usePreciseAngle.set(true);
		preferences.view.set(MainViewType.DETAILED);
		preferences.strongholdDisplayType.set(StrongholdDisplayType.CHUNK);
		return this;
	}

	public void setClipboard(String clipboardString) {
		if (clipboardReader == null)
			clipboardReader = new MockedClipboardReader();
		if (coordinateInputSource == null)
			coordinateInputSource = new CoordinateInputSource(clipboardReader);
		if (playerPositionInputHandler == null)
			playerPositionInputHandler = createPlayerPositionInputHandler();
		if (f3iLocationInputHandler == null)
			f3iLocationInputHandler = new F3ILocationInputHandler(coordinateInputSource, dataState, actionExecutor,
					preferences);
		clipboardReader.setClipboard(clipboardString);
	}

	public void inputSubpixelCorrections(int correctionCount) {
		for (int i = 0; i < correctionCount; i++) {
			triggerHotkey(preferences.hotkeyIncrement);
		}
		for (int i = 0; i < -correctionCount; i++) {
			triggerHotkey(preferences.hotkeyDecrement);
		}
	}

	public void inputStandardDeviationToggle() {
		triggerHotkey(preferences.hotkeyAltStd);
	}

	public void clickRemoveFossilButton() {
		if (buttonInputHandler == null)
			buttonInputHandler = new ButtonInputHandler(domainModel, dataState, actionExecutor);
		buttonInputHandler.onRemoveFossilButtonPressed();
	}

	public void triggerHotkey(HotkeyPreference hotkeyPreference) {
		if (hotkeyInputHandler == null)
			hotkeyInputHandler = new HotkeyInputHandler(preferences, domainModel, dataState, actionExecutor);
		hotkeyPreference.execute();
	}

	public void setActiveMinecraftWorld(IMinecraftWorldFile minecraftWorld) {
		setActiveMinecraftWorld(minecraftWorld, McVersion.PRE_119);
	}

	public void setActiveMinecraftWorld(IMinecraftWorldFile minecraftWorld, McVersion mcVersion) {
		if (activeInstanceProvider == null)
			activeInstanceProvider = new MockedInstanceProvider();
		if (activeInstanceInputHandler == null)
			activeInstanceInputHandler = new ActiveInstanceInputHandler(activeInstanceProvider, domainModel, dataState,
					actionExecutor, preferences);
		McInstanceTestAdapter.setMinecraftInstanceVersion(minecraftWorld.minecraftInstance(), mcVersion);
		activeInstanceProvider.activeMinecraftWorld().set(minecraftWorld);
		activeInstanceProvider.activeMinecraftInstance().set(minecraftWorld.minecraftInstance());
	}

	public MainTextAreaTestAdapter createMainTextArea() {
		if (styleManager == null)
			styleManager = TestUtils.createStyleManager();
		if (buttonInputHandler == null)
			buttonInputHandler = new ButtonInputHandler(domainModel, dataState, actionExecutor);
		return new MainTextAreaTestAdapter(new MainTextArea(styleManager));
	}

	public EnderEyePanelTestAdapter createEnderEyePanel() {
		if (styleManager == null)
			styleManager = TestUtils.createStyleManager();
		if (buttonInputHandler == null)
			buttonInputHandler = new ButtonInputHandler(domainModel, dataState, actionExecutor);
		return new EnderEyePanelTestAdapter(new EnderEyePanel(styleManager, dataState, actionExecutor));
	}

	public StrongyFrame createStrongyFrame() {
		if (styleManager == null)
			styleManager = TestUtils.createStyleManager();
		if (buttonInputHandler == null)
			buttonInputHandler = new ButtonInputHandler(domainModel, dataState, actionExecutor);
		StrongyFrame frame = new StrongyFrame(styleManager, preferences, new FakeUpdateChecker(), dataState,
				buttonInputHandler, new InformationMessageList());
		styleManager.init();
		return frame;
	}

	public CalibratorFactory createCalibratorFactory() {
		if (fakeCoordinateInputSource == null)
			fakeCoordinateInputSource = new FakeCoordinateInputSource();
		return new CalibratorFactory(environmentState.calculatorSettings(), fakeCoordinateInputSource, preferences);
	}

	public InformationMessageList createInformationMessageList() {
		if (activeInstanceProvider == null)
			activeInstanceProvider = new MockedInstanceProvider();

		InformationMessageList informationMessageList = new InformationMessageList();
		informationMessageList
				.AddInformationMessageProvider(new McVersionWarningProvider(activeInstanceProvider, preferences));
		informationMessageList
				.AddInformationMessageProvider(new MismeasureWarningProvider(dataState, environmentState, preferences));
		informationMessageList.AddInformationMessageProvider(new PortalLinkingWarningProvider(dataState, preferences));
		informationMessageList
				.AddInformationMessageProvider(new CombinedCertaintyInformationProvider(dataState, preferences));
		informationMessageList.AddInformationMessageProvider(
				new NextThrowDirectionInformationProvider(dataState, environmentState, preferences));
		return informationMessageList;
	}

	public BoatIcon createBoatIcon() {
		if (styleManager == null)
			styleManager = TestUtils.createStyleManager();
		return new BoatIcon(styleManager, dataState.boatDataState().boatState().get());
	}

	public void swapTheme() {
		Assert.isNotNull(styleManager, "Create something that uses a StyleManager first!");
		styleManager.currentTheme.setTheme(new TestTheme2());
	}

	public void addDummyEnderEyeThrow() {
		TestUtils.addDummyEnderEyeThrow(domainModel, dataState);
	}

	public void inputDetailedPlayerPosition(IDetailedPlayerPosition detailedPlayerPosition) {
		if (fakePlayerPositionInputHandler == null)
			fakePlayerPositionInputHandler = createFakePlayerPositionInputHandler();
		fakeCoordinateInputSource.whenNewDetailedPlayerPositionInputted.notifySubscribers(detailedPlayerPosition);
	}

	public void inputF3I(F3IData f3IData) {
		if (fakeCoordinateInputSource == null)
			fakeCoordinateInputSource = new FakeCoordinateInputSource();
		if (fakeF3ILocationInputHandler == null)
			fakeF3ILocationInputHandler = new F3ILocationInputHandler(fakeCoordinateInputSource, dataState,
					actionExecutor, preferences);
		fakeCoordinateInputSource.whenNewF3IInputted.notifySubscribers(f3IData);
	}

	public void resetCalculator() {
		if (buttonInputHandler == null)
			buttonInputHandler = new ButtonInputHandler(domainModel, dataState, actionExecutor);
		buttonInputHandler.onResetButtonPressed();
	}

	public void enterNewWorld() {
		setActiveMinecraftWorld(new FakeMinecraftWorldFile(new MinecraftInstance("panda"), "gargamel", false));
	}

	public void enterEnd() {
		if (activeInstanceProvider.activeMinecraftWorld().get() == null)
			setActiveMinecraftWorld(new FakeMinecraftWorldFile(new MinecraftInstance("instance 1"), "world1", false));
		IMinecraftWorldFile world = activeInstanceProvider.activeMinecraftWorld().get();
		setActiveMinecraftWorld(new FakeMinecraftWorldFile(world.minecraftInstance(), world.name(), true));
	}

	private PlayerPositionInputHandler createPlayerPositionInputHandler() {
		if (coordinateInputSource == null)
			coordinateInputSource = new CoordinateInputSource(clipboardReader);
		IEnderEyeThrowFactory enderEyeThrowFactory = new EnderEyeThrowFactory(preferences, dataState.boatDataState());
		return new PlayerPositionInputHandler(coordinateInputSource, dataState, actionExecutor, preferences,
				enderEyeThrowFactory);
	}

	private PlayerPositionInputHandler createFakePlayerPositionInputHandler() {
		if (fakeCoordinateInputSource == null)
			fakeCoordinateInputSource = new FakeCoordinateInputSource();
		IEnderEyeThrowFactory enderEyeThrowFactory = new EnderEyeThrowFactory(preferences, dataState.boatDataState());
		return new PlayerPositionInputHandler(fakeCoordinateInputSource, dataState, actionExecutor, preferences,
				enderEyeThrowFactory);
	}

}
