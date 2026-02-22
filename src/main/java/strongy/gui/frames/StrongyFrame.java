package strongy.gui.frames;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;

import strongy.Main;
import strongy.gui.style.SizePreference;
import strongy.gui.style.StyleManager;
import strongy.io.preferences.StrongyPreferences;
import strongy.io.updatechecker.IUpdateChecker;
import strongy.model.datastate.IDataState;
import strongy.model.information.InformationMessageList;
import strongy.model.input.IButtonInputHandler;
import strongy.util.I18n;

/**
 * Main application window — JavaFX version.
 */
public class StrongyFrame extends ThemedFrame {

	private final StrongyPreferences preferences;
	private final VBox contentArea;
	private Label versionLabel;
	private Button settingsButton;
	private Button themeToggleButton;
	private boolean minimized = false;
	private Scene scene;

	public StrongyFrame(StyleManager styleManager, StrongyPreferences preferences,
			IUpdateChecker updateChecker, IDataState dataState,
			IButtonInputHandler buttonInputHandler,
			InformationMessageList informationMessageList) {
		super(styleManager, preferences, "Strongy");
		this.preferences = preferences;

		// Create the title bar content
		createTitleBar(styleManager, updateChecker);

		// Main content
		contentArea = new VBox();
		contentArea.getStyleClass().add("themed-panel");
		contentArea.setSpacing(0);

		// Create the main content components
		createComponents(dataState, buttonInputHandler, informationMessageList);

		root.getChildren().add(contentArea);

		// Build scene
		scene = new Scene(root, styleManager.size.WIDTH, 400);
		styleManager.manageScene(scene);
		stage.setScene(scene);

		// Restore position
		stage.setX(preferences.windowX.get());
		stage.setY(preferences.windowY.get());
	}

	/**
	 * Creates the scene separately for use by StrongyApp when using an existing
	 * Stage.
	 */
	public StrongyFrame(Stage existingStage, StyleManager styleManager, StrongyPreferences preferences,
			IUpdateChecker updateChecker, IDataState dataState,
			IButtonInputHandler buttonInputHandler,
			InformationMessageList informationMessageList) {
		super(styleManager, preferences, "Strongy");
		this.preferences = preferences;

		createTitleBar(styleManager, updateChecker);

		contentArea = new VBox();
		contentArea.getStyleClass().add("themed-panel");
		contentArea.setSpacing(0);

		createComponents(dataState, buttonInputHandler, informationMessageList);

		root.getChildren().add(contentArea);

		scene = new Scene(root, styleManager.size.WIDTH, 400);
		styleManager.manageScene(scene);

		// Use the existing stage from StrongyApp
		existingStage.setScene(scene);
		existingStage.setX(preferences.windowX.get());
		existingStage.setY(preferences.windowY.get());
	}

	private void createTitleBar(StyleManager styleManager, IUpdateChecker updateChecker) {
		// Version label
		versionLabel = new Label("v" + Main.VERSION);
		versionLabel.getStyleClass().add("version-text");

		// Spacer
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		// Theme toggle button
		themeToggleButton = new Button("Theme");
		themeToggleButton.getStyleClass().add("theme-toggle");
		themeToggleButton.setOnAction(e -> {
			boolean isDark = styleManager.toggleDarkLight();
			themeToggleButton.setText(isDark ? "Theme_D" : "Theme_L");
		});

		// Settings button
		settingsButton = new Button("⚙");
		settingsButton.getStyleClass().add("title-bar-button");

		// Minimize button
		Button minimizeButton = new Button("—");
		minimizeButton.getStyleClass().add("title-bar-button");
		minimizeButton.setOnAction(e -> toggleMinimized());

		// Exit button
		Button exitButton = new Button("✕");
		exitButton.getStyleClass().addAll("title-bar-button", "exit-button");
		exitButton.setOnAction(e -> onExitButtonClicked());

		// Clear the default title bar and rebuild
		titleBar.getChildren().clear();
		titleBar.getChildren().addAll(titleLabel, versionLabel, spacer,
				themeToggleButton, settingsButton, minimizeButton, exitButton);
	}

	private void createComponents(IDataState dataState,
			IButtonInputHandler buttonInputHandler,
			InformationMessageList informationMessageList) {
		// Main text area - shows the current prediction/status
		Label statusLabel = new Label(I18n.get("waiting"));
		statusLabel.getStyleClass().add("themed-label");
		statusLabel.setWrapText(true);
		statusLabel.setMaxWidth(Double.MAX_VALUE);

		// Prediction display area
		VBox predictionArea = new VBox(4);
		predictionArea.getStyleClass().add("themed-panel");

		Label headerLabel = new Label(I18n.get("waiting"));
		headerLabel.getStyleClass().add("themed-label-header");

		predictionArea.getChildren().add(headerLabel);

		// Wire data state subscriptions for prediction updates
		disposeHandler.add(dataState.calculatorResult().subscribe(result -> {
			javafx.application.Platform.runLater(() -> {
				if (result == null || !result.success()) {
					headerLabel.setText(I18n.get("waiting"));
					return;
				}
				var best = result.getBestPrediction();
				if (best != null) {
					headerLabel.setText(String.format("(%d, %d)  —  %.1f%%",
							best.chunk.x, best.chunk.z, best.chunk.weight * 100));
				}
			});
		}));

		contentArea.getChildren().addAll(predictionArea);
	}

	private void toggleMinimized() {
		minimized = !minimized;
		contentArea.setVisible(!minimized);
		contentArea.setManaged(!minimized);
		stage.sizeToScene();
	}

	public Button getSettingsButton() {
		return settingsButton;
	}

	public boolean isIdle() {
		return true; // Placeholder
	}

	@Override
	protected void onExitButtonClicked() {
		preferences.windowX.set((int) stage.getX());
		preferences.windowY.set((int) stage.getY());
		javafx.application.Platform.exit();
		System.exit(0);
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
