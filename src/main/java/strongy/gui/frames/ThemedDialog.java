package strongy.gui.frames;

import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.stage.Screen;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;

import strongy.event.DisposeHandler;
import strongy.event.IDisposable;
import strongy.gui.buttons.FlatButton;
import strongy.gui.buttons.TitleBarButton;
import strongy.gui.components.labels.ThemedLabel;
import strongy.gui.components.panels.TitleBarPanel;
import strongy.gui.style.SizePreference;
import strongy.gui.style.StyleManager;
import strongy.gui.style.theme.WrappedColor;
import strongy.io.preferences.StrongyPreferences;

public abstract class ThemedDialog extends Stage implements IDisposable {

	private final StyleManager styleManager;

	protected final TitleBarPanel titlebarPanel;
	protected final ThemedLabel titletextLabel;
	protected final VBox contentPane;
	private final Scene scene;

	final WrappedColor bgCol;

	protected final DisposeHandler disposeHandler = new DisposeHandler();

	public ThemedDialog(StyleManager styleManager, StrongyPreferences preferences, Window owner, String title) {
		this.styleManager = styleManager;
		if (owner != null) {
			initOwner(owner);
		}
		initModality(Modality.APPLICATION_MODAL);
		initStyle(StageStyle.UNDECORATED); // Remove borders
		setAlwaysOnTop(preferences.alwaysOnTop.get()); // Always focused

		contentPane = new VBox();
		scene = new Scene(contentPane);
		styleManager.manageScene(scene);
		setScene(scene);

		titlebarPanel = new TitleBarPanel(styleManager, this);
		contentPane.getChildren().add(titlebarPanel);

		titletextLabel = new ThemedLabel(styleManager, title, true) {
			@Override
			public int getTextSize(SizePreference p) {
				return p.TEXT_SIZE_TITLE_LARGE;
			}
		};
		titletextLabel.setForegroundColor(styleManager.currentTheme.TEXT_COLOR_TITLE);
		titlebarPanel.getChildren().add(titletextLabel);
		titlebarPanel.addButton(createExitButton(styleManager));

		bgCol = styleManager.currentTheme.COLOR_NEUTRAL;
	}

	private FlatButton createExitButton(StyleManager styleManager) {
		FlatButton button = new TitleBarButton(styleManager, "/exit_icon.png");
		button.setHoverColor(styleManager.currentTheme.COLOR_EXIT_BUTTON_HOVER);
		button.setOnAction(__ -> onExitButtonClicked());
		return button;
	}

	protected abstract void onExitButtonClicked();

	public TitleBarPanel getTitleBar() {
		return titlebarPanel;
	}

	public void updateBounds(StyleManager styleManager) {
		// JavaFX uses layout managers naturally, we don't necessarily bounds-check
		// manually,
		// but we can enforce some layout updates if needed.
	}

	public void updateFontsAndColors() {
		contentPane.setBackground(new Background(new BackgroundFill(bgCol.color(), CornerRadii.EMPTY, Insets.EMPTY)));
	}

	public void checkIfOffScreen() {
		for (Screen screen : Screen.getScreens()) {
			if (screen.getBounds().contains(getX(), getY(), getWidth(), getHeight())) {
				return;
			}
		}
		setX(100);
		setY(100);
	}

	@Override
	public void dispose() {
		disposeHandler.dispose();
		close();
	}

}
