package strongy.gui.options.sections;

import javafx.geometry.Pos;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.io.preferences.StrongyPreferences;
import strongy.gui.frames.OptionsFrame;

public class ThemeSelectionPanel extends ThemedPanel {
	public ThemeSelectionPanel(StyleManager styleManager, StrongyPreferences preferences, OptionsFrame frame) {
		super(styleManager);
		setSpacing(8);
		setAlignment(Pos.TOP_LEFT);
		// Stub
	}
}