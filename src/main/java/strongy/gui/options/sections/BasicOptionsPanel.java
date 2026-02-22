package strongy.gui.options.sections;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.io.preferences.StrongyPreferences;
import strongy.io.mcinstance.IActiveInstanceProvider;

public class BasicOptionsPanel extends ThemedPanel {
	public BasicOptionsPanel(StyleManager styleManager, StrongyPreferences preferences,
			IActiveInstanceProvider activeInstanceProvider) {
		super(styleManager);
		setSpacing(8);
		setAlignment(Pos.TOP_LEFT);
		// Basic preference UI controls will go here, hooked directly to preferences.
		// For now, this stub allows compilation.
	}
}
