package strongy.gui.frames;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;

import strongy.Main;
import strongy.event.DisposeHandler;
import strongy.event.IDisposable;
import strongy.gui.buttons.FlatButton;
import strongy.gui.buttons.TitleBarButton;
import strongy.gui.components.RefreshWindowOnMonitorChangeListener;
import strongy.gui.components.labels.ThemedLabel;
import strongy.gui.components.panels.TitleBarPanel;
import strongy.gui.style.SizePreference;
import strongy.gui.style.StyleManager;
import strongy.gui.style.theme.WrappedColor;
import strongy.io.preferences.StrongyPreferences;

public abstract class ThemedDialog extends JDialog implements IDisposable {

	private final StyleManager styleManager;

	protected final TitleBarPanel titlebarPanel;
	protected final ThemedLabel titletextLabel;

	final WrappedColor bgCol;

	protected final DisposeHandler disposeHandler = new DisposeHandler();

	public ThemedDialog(StyleManager styleManager, StrongyPreferences preferences, JFrame owner, String title) {
		super(owner, title);
		this.styleManager = styleManager;
		styleManager.registerThemedDialog(this);
		setModal(true);
		setUndecorated(true); // Remove borders
		setAlwaysOnTop(preferences.alwaysOnTop.get()); // Always focused
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		titlebarPanel = new TitleBarPanel(styleManager, this);
		add(titlebarPanel);
		titletextLabel = new ThemedLabel(styleManager, title, true) {

			@Override
			public int getTextSize(SizePreference p) {
				return p.TEXT_SIZE_TITLE_LARGE;
			}
		};
		titletextLabel.setForegroundColor(styleManager.currentTheme.TEXT_COLOR_TITLE);
		titlebarPanel.add(titletextLabel);
		titlebarPanel.addButton(createExitButton(styleManager));

		bgCol = styleManager.currentTheme.COLOR_NEUTRAL;

		addComponentListener(new RefreshWindowOnMonitorChangeListener(this));
	}

	private FlatButton createExitButton(StyleManager styleManager) {
		URL iconURL = Main.class.getResource("/exit_icon.png");
		ImageIcon img = new ImageIcon(iconURL);
		FlatButton button = new TitleBarButton(styleManager, img);
		button.setHoverColor(styleManager.currentTheme.COLOR_EXIT_BUTTON_HOVER);
		button.addActionListener(__ -> onExitButtonClicked());
		return button;
	}

	protected abstract void onExitButtonClicked();

	public TitleBarPanel getTitleBar() {
		return titlebarPanel;
	}

	public void updateBounds(StyleManager styleManager) {
		int titlebarHeight = titlebarPanel.getPreferredSize().height;
		titletextLabel.setBounds((titlebarHeight - styleManager.size.TEXT_SIZE_TITLE_LARGE) / 2, 0, titletextLabel.getPreferredSize().width, titlebarHeight);
	}

	public void updateFontsAndColors() {
		getContentPane().setBackground(bgCol.color());
		setBackground(bgCol.color());
	}

	public void checkIfOffScreen() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		for (GraphicsDevice gd : ge.getScreenDevices()) {
			if (gd.getDefaultConfiguration().getBounds().contains(getBounds())) {
				return;
			}
		}
		setLocation(100, 100);
	}

	@Override
	public void dispose() {
		super.dispose();
		disposeHandler.dispose();
		styleManager.unregisterThemedDialog(this);
	}

}
