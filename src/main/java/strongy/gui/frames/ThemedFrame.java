package strongy.gui.frames;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import strongy.Main;
import strongy.event.DisposeHandler;
import strongy.event.IDisposable;
import strongy.gui.style.StyleManager;
import strongy.io.preferences.StrongyPreferences;

import java.io.InputStream;

/**
 * Base class for themed JavaFX windows (replaces Swing ThemedFrame which
 * extended JFrame).
 */
public abstract class ThemedFrame implements IDisposable {

	protected final Stage stage;
	protected final VBox root;
	protected final HBox titleBar;
	protected final Label titleLabel;
	protected final StyleManager styleManager;

	protected final DisposeHandler disposeHandler = new DisposeHandler();

	// For window dragging
	private double dragOffsetX;
	private double dragOffsetY;

	public ThemedFrame(StyleManager styleManager, StrongyPreferences preferences, String title) {
		this.styleManager = styleManager;
		this.stage = new Stage(StageStyle.UNDECORATED);
		stage.setTitle(title);
		stage.setAlwaysOnTop(preferences.alwaysOnTop.get());

		// Set icon
		try {
			InputStream is = Main.class.getResourceAsStream("/icon.png");
			if (is != null) {
				stage.getIcons().add(new Image(is));
			}
		} catch (Exception ignored) {
		}

		// Root layout
		root = new VBox();
		root.getStyleClass().add("themed-panel");

		// Title bar
		titleBar = new HBox();
		titleBar.getStyleClass().add("title-bar");
		titleBar.setAlignment(Pos.CENTER_LEFT);

		// Drag support
		titleBar.setOnMousePressed(e -> {
			dragOffsetX = e.getScreenX() - stage.getX();
			dragOffsetY = e.getScreenY() - stage.getY();
		});
		titleBar.setOnMouseDragged(e -> {
			stage.setX(e.getScreenX() - dragOffsetX);
			stage.setY(e.getScreenY() - dragOffsetY);
		});

		// Title text
		titleLabel = new Label(title);
		titleLabel.getStyleClass().add("title-text");

		// Spacer
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		// Exit button
		Button exitButton = createExitButton();

		titleBar.getChildren().addAll(titleLabel, spacer, exitButton);
		root.getChildren().add(titleBar);
	}

	private Button createExitButton() {
		Button button = new Button("✕");
		button.getStyleClass().addAll("title-bar-button", "exit-button");
		button.setOnAction(e -> onExitButtonClicked());
		return button;
	}

	protected abstract void onExitButtonClicked();

	public Stage getStage() {
		return stage;
	}

	public VBox getRoot() {
		return root;
	}

	public HBox getTitleBar() {
		return titleBar;
	}

	public void setVisible(boolean visible) {
		if (visible) {
			stage.show();
		} else {
			stage.hide();
		}
	}

	public boolean isVisible() {
		return stage.isShowing();
	}

	public int getX() {
		return (int) stage.getX();
	}

	public int getY() {
		return (int) stage.getY();
	}

	public void setLocation(int x, int y) {
		stage.setX(x);
		stage.setY(y);
	}

	public void checkIfOffScreen() {
		javafx.stage.Screen primary = javafx.stage.Screen.getPrimary();
		javafx.geometry.Rectangle2D bounds = primary.getVisualBounds();
		if (stage.getX() < bounds.getMinX() || stage.getX() > bounds.getMaxX()
				|| stage.getY() < bounds.getMinY() || stage.getY() > bounds.getMaxY()) {
			stage.setX(100);
			stage.setY(100);
		}
	}

	@Override
	public void dispose() {
		disposeHandler.dispose();
		stage.close();
	}
}
