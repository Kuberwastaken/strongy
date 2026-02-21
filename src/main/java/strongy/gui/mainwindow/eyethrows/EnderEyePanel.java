package strongy.gui.mainwindow.eyethrows;

import javax.swing.BoxLayout;

import strongy.gui.components.ThemedComponent;
import strongy.gui.components.panels.ResizablePanel;
import strongy.gui.style.StyleManager;
import strongy.io.preferences.StrongyPreferences;
import strongy.model.datastate.IDataState;
import strongy.model.input.IButtonInputHandler;

public class EnderEyePanel extends ResizablePanel implements ThemedComponent {

	public static final int DEFAULT_SHOWN_THROWS = 3;

	private final ThrowPanelHeader throwPanelHeader;
	final ThrowPanel[] throwPanels;
	private final DivineContextPanel divineContextPanel;

	public EnderEyePanel(StyleManager styleManager, StrongyPreferences preferences, IDataState dataState, IButtonInputHandler buttonInputHandler) {
		styleManager.registerThemedComponent(this);
		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		throwPanelHeader = new ThrowPanelHeader(styleManager, preferences.showAngleErrors);
		add(throwPanelHeader);
		throwPanels = new ThrowPanel[dataState.getThrowList().maxCapacity()];
		divineContextPanel = new DivineContextPanel(styleManager, dataState.getDivineContext(), buttonInputHandler, this::whenDivineContextVisibilityUpdated);
		add(divineContextPanel);
		for (int i = 0; i < dataState.getThrowList().maxCapacity(); i++) {
			throwPanels[i] = new ThrowPanel(styleManager, dataState, buttonInputHandler, dataState.topPrediction(), i, () -> whenSizeModified.notifySubscribers(this), preferences);
			add(throwPanels[i]);
		}
		throwPanels[2].setDivineContextPanel(divineContextPanel);
		// Subscriptions
		disposeHandler.add(preferences.showAngleErrors.whenModified().subscribeEDT(this::setAngleErrorsEnabled));
	}

	private void whenDivineContextVisibilityUpdated() {
		throwPanels[2].updateVisibility();
		whenSizeModified.notifySubscribers(this);
	}

	private void setAngleErrorsEnabled(boolean b) {
		throwPanelHeader.setAngleErrorsEnabled(b);
	}

	@Override
	public void updateColors() {
	}

	@Override
	public void updateSize(StyleManager styleManager) {
	}

	@Override
	public void dispose() {
		super.dispose();
		for (ThrowPanel p : throwPanels) {
			p.dispose();
		}
		divineContextPanel.dispose();
	}
}
