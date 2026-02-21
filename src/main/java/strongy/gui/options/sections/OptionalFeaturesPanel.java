package strongy.gui.options.sections;

import strongy.event.DisposeHandler;
import strongy.gui.components.layout.ThemedTabbedPane;
import strongy.gui.style.StyleManager;
import strongy.io.preferences.StrongyPreferences;
import strongy.util.I18n;

public class OptionalFeaturesPanel extends ThemedTabbedPane {

	public OptionalFeaturesPanel(StyleManager styleManager, StrongyPreferences preferences, DisposeHandler disposeHandler) {
		super(styleManager);
		addTab(I18n.get("settings.general"), new GeneralOptionalFeaturesOptionsPanel(styleManager, preferences));
		addTab(I18n.get("settings.all_advancements"), new AllAdvancementsOptionsPanel(styleManager, preferences));
		addTab(I18n.get("settings.angle_adjustment"), new AngleAdjustmentOptionsPanel(styleManager, preferences, disposeHandler));
		addTab(I18n.get("settings.boat_eye"), new BoatMeasurementOptionsPanel(styleManager, preferences, disposeHandler));
	}

}
