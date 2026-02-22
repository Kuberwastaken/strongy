package strongy.gui.style;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.text.Font;

import strongy.Main;
import strongy.gui.style.theme.CurrentTheme;
import strongy.gui.style.theme.Theme;
import strongy.util.I18n;
import strongy.util.Logger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Manages CSS stylesheets and theme switching for JavaFX scenes.
 */
public class StyleManager {

	private boolean initialized = false;

	public final CurrentTheme currentTheme;
	public SizePreference size;

	private final ArrayList<Scene> managedScenes = new ArrayList<>();
	private String currentThemeCssPath = "/css/dark.css";

	private Font font;

	public StyleManager(Theme theme, SizePreference size) {
		currentTheme = new CurrentTheme();
		currentTheme.setTheme(theme);
		this.size = size;

		loadFont();
		currentTheme.whenModified().subscribe(__ -> updateAllScenes());
	}

	/**
	 * Register a JavaFX Scene to be managed by this StyleManager.
	 * Applies base.css + current theme CSS immediately.
	 */
	public void manageScene(Scene scene) {
		managedScenes.add(scene);
		applyStylesheets(scene);
	}

	/**
	 * Switch between dark and light CSS themes.
	 */
	public void setThemeCss(String cssPath) {
		this.currentThemeCssPath = cssPath;
		updateAllScenes();
	}

	/**
	 * Toggle between dark and light themes.
	 */
	public boolean toggleDarkLight() {
		if (currentThemeCssPath.contains("dark")) {
			currentThemeCssPath = "/css/light.css";
		} else {
			currentThemeCssPath = "/css/dark.css";
		}
		updateAllScenes();
		return currentThemeCssPath.contains("dark");
	}

	public boolean isDarkTheme() {
		return currentThemeCssPath.contains("dark");
	}

	private void applyStylesheets(Scene scene) {
		scene.getStylesheets().clear();
		String baseUrl = Objects.requireNonNull(
				getClass().getResource("/css/base.css")).toExternalForm();
		String themeUrl = Objects.requireNonNull(
				getClass().getResource(currentThemeCssPath)).toExternalForm();
		scene.getStylesheets().addAll(baseUrl, themeUrl);
	}

	private void updateAllScenes() {
		if (Platform.isFxApplicationThread()) {
			managedScenes.forEach(this::applyStylesheets);
		} else {
			Platform.runLater(() -> managedScenes.forEach(this::applyStylesheets));
		}
	}

	public Font getFont() {
		return font;
	}

	public void setSizePreference(SizePreference size) {
		this.size = size;
		// Size changes will be handled via CSS classes + scene root style
		updateAllScenes();
	}

	private void loadFont() {
		try {
			InputStream is = Main.class.getResourceAsStream("/OpenSans-Regular.ttf");
			if (is != null) {
				font = Font.loadFont(is, 12);
				is.close();
			}
		} catch (Exception e) {
			Logger.log("Could not load font: " + e.getMessage());
		}
		if (font == null) {
			font = Font.getDefault();
		}
	}

	public void init() {
		initialized = true;
		updateAllScenes();
	}
}
