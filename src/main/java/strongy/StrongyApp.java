package strongy;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import strongy.gui.GUI;
import strongy.gui.style.StyleManager;
import strongy.io.KeyboardListener;
import strongy.io.preferences.StrongyPreferences;
import strongy.io.preferences.SavedPreferences;
import strongy.model.datastate.statistics.ApproximatedDensity;
import strongy.util.I18n;
import strongy.util.Logger;
import strongy.util.Profiler;

import java.util.Locale;
import java.util.Objects;

/**
 * JavaFX Application entry point for Strongy.
 */
public class StrongyApp extends Application {

    private StrongyPreferences preferences;
    private GUI gui;

    @Override
    public void init() {
        // Heavy init on the launcher thread (NOT the FX thread)
        Profiler.start("Initialize language");
        Logger.log("Language: " + I18n.get("lang"));

        Profiler.stopAndStart("Initialize preferences");
        preferences = new StrongyPreferences(new SavedPreferences());

        Profiler.stopAndStart("Calculate approximated density");
        ApproximatedDensity.init();

        Profiler.stopAndStart("Register keyboard listener");
        KeyboardListener.preInit();
        Profiler.stop();

        Locale.setDefault(Locale.US);
    }

    @Override
    public void start(Stage primaryStage) {
        Profiler.start("Initialize GUI");

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Strongy");
        primaryStage.setAlwaysOnTop(preferences.alwaysOnTop.get());

        // Set icon
        try {
            Image icon = new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream("/icon.png")));
            primaryStage.getIcons().add(icon);
        } catch (Exception e) {
            Logger.log("Could not load app icon: " + e.getMessage());
        }

        // Create GUI (this wires model, input handlers, and builds scene content)
        gui = new GUI(preferences, primaryStage);

        Profiler.stop();
        Profiler.print();
    }

    @Override
    public void stop() {
        if (gui != null) {
            gui.shutdown();
        }
        Platform.exit();
    }
}
