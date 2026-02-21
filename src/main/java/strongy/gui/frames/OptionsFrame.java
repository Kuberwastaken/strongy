package strongy.gui.frames;

import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JFrame;

import strongy.gui.components.layout.ThemedTabbedPane;
import strongy.gui.options.sections.AdvancedOptionsPanel;
import strongy.gui.options.sections.BasicOptionsPanel;
import strongy.gui.options.sections.HotkeyOptionsPanel;
import strongy.gui.options.sections.LanguageOptionsPanel;
import strongy.gui.options.sections.ObsOptionsPanel;
import strongy.gui.options.sections.OptionalFeaturesPanel;
import strongy.gui.options.sections.ThemeSelectionPanel;
import strongy.gui.style.StyleManager;
import strongy.io.KeyboardListener;
import strongy.io.mcinstance.IActiveInstanceProvider;
import strongy.io.preferences.StrongyPreferences;
import strongy.model.actions.IActionExecutor;
import strongy.model.datastate.calibrator.ICalibratorFactory;
import strongy.util.I18n;

public class OptionsFrame extends ThemedFrame {

	private final ThemedTabbedPane tabbedPane;

	public static int WINDOW_WIDTH = 560;
	public static int COLUMN_WIDTH = WINDOW_WIDTH / 2;
	public static final int PADDING = 6;

	private static final String TITLE_TEXT = I18n.get("settings");

	public OptionsFrame(StyleManager styleManager, StrongyPreferences preferences, ICalibratorFactory calibratorFactory, IActiveInstanceProvider activeInstanceProvider, IActionExecutor actionExecutor) {
		super(styleManager, preferences, TITLE_TEXT);
		setLayout(null);
		tabbedPane = new ThemedTabbedPane(styleManager);
		add(tabbedPane);

		tabbedPane.addTab(I18n.get("settings.basic"), new BasicOptionsPanel(styleManager, preferences, activeInstanceProvider));
		tabbedPane.addTab(I18n.get("settings.advanced"), new AdvancedOptionsPanel(styleManager, preferences, calibratorFactory, actionExecutor, this, disposeHandler));
		tabbedPane.addTab(I18n.get("settings.theme"), new ThemeSelectionPanel(styleManager, preferences, this));
		tabbedPane.addTab(I18n.get("settings.keyboard_shortcuts"), new HotkeyOptionsPanel(styleManager, preferences));
		tabbedPane.addTab(I18n.get("settings.overlay"), new ObsOptionsPanel(styleManager, preferences, disposeHandler));
		tabbedPane.addTab(I18n.get("settings.language"), new LanguageOptionsPanel(styleManager, preferences));
		tabbedPane.addTab(I18n.get("settings.optional_features"), new OptionalFeaturesPanel(styleManager, preferences, disposeHandler));

		// Title bar
		titlebarPanel.setFocusable(true);

		// Subscriptions
		disposeHandler.add(preferences.alwaysOnTop.whenModified().subscribeEDT(this::setAlwaysOnTop));
	}

	public void updateBounds(StyleManager styleManager) {
		WINDOW_WIDTH = styleManager.size.WIDTH / 4 * (I18n.localeRequiresExtraSpace() ? 9 : 7);
		COLUMN_WIDTH = WINDOW_WIDTH / 2;
		int titleBarHeight = titlebarPanel.getPreferredSize().height;
		int panelHeight = tabbedPane.getPreferredSize().height;
		titlebarPanel.setBounds(0, 0, WINDOW_WIDTH, titleBarHeight);
		super.updateBounds(styleManager);
		tabbedPane.setBounds(0, titleBarHeight, WINDOW_WIDTH, panelHeight);
		setSize(WINDOW_WIDTH, titleBarHeight + panelHeight);
		setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), styleManager.size.WINDOW_ROUNDING, styleManager.size.WINDOW_ROUNDING));
	}

	@Override
	protected void onExitButtonClicked() {
		close();
	}

	public void close() {
		setVisible(false);
		if (KeyboardListener.registered) {
			KeyboardListener.instance.cancelConsumer();
		}
	}

	public void toggleWindow(JFrame parent) {
		if (isVisible()) {
			close();
		} else {
			setVisible(true);
			Rectangle bounds = parent.getBounds();
			setLocation(bounds.x + 40, bounds.y + 30);
		}
	}

}