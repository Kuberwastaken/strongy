package strongy.gui.options.sections;

import javafx.geometry.Pos;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.io.preferences.StrongyPreferences;
import strongy.event.DisposeHandler;

public class OptionalFeaturesPanel extends ThemedPanel {
	public OptionalFeaturesPanel(StyleManager styleManager, StrongyPreferences preferences,
			DisposeHandler disposeHandler) {
		super(styleManager);
		setSpacing(8);
		setAlignment(Pos.TOP_LEFT);
		// Stub for compilation
	}
}
