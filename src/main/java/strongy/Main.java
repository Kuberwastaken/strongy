package strongy;

import java.util.Locale;
import java.util.Optional;

import strongy.gui.GUI;
import strongy.gui.splash.Progress;
import strongy.gui.splash.Splash;
import strongy.io.ErrorHandler;
import strongy.io.KeyboardListener;
import strongy.io.preferences.StrongyPreferences;
import strongy.io.preferences.SavedPreferences;
import strongy.model.datastate.statistics.ApproximatedDensity;
import strongy.util.I18n;
import strongy.util.Logger;
import strongy.util.Profiler;

public class Main {

	public static final String VERSION = "2.0.0";

	public static void main(String[] args) {
		ErrorHandler errorHandler = new ErrorHandler();
		try {
			start();
		} catch (Exception e) {
			errorHandler.handleStartupException(e);
		}
	}

	private static void start() {
		boolean isSplashScreenDisabled = Optional.ofNullable(System.getenv("XDG_SESSION_TYPE")).isPresent(); // Splash
																												// screen
																												// does
																												// not
																												// work
																												// for
																												// linux
																												// (wayland)
																												// so we
																												// turn
																												// it
																												// off
		Progress.init(new Splash(isSplashScreenDisabled));
		Progress.setTask("Loading language", 0.02f);
		Profiler.start("Initialize language");
		Logger.log("Language: " + I18n.get("lang"));

		Progress.setTask("Loading preferences", 0.04f);
		Profiler.stopAndStart("Initialize preferences");
		StrongyPreferences preferences = new StrongyPreferences(new SavedPreferences());

		Progress.setTask("Calculating approximated stronghold density", 0.05f);
		Profiler.stopAndStart("Calculate approximated density");
		ApproximatedDensity.init();

		Progress.setTask("Starting keyboard listener", 0.08f);
		Profiler.stopAndStart("Register keyboard listener");
		KeyboardListener.preInit();

		System.setProperty("apple.awt.application.name", "Strongy");
		Progress.startCompoundTask("", 1f);
		Profiler.stopAndStart("Initialize GUI");
		Locale.setDefault(Locale.US);
		new GUI(preferences);
		Progress.endCompoundTask();

		Profiler.stop();
		Profiler.print();
	}
}
