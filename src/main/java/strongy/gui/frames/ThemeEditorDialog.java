package strongy.gui.frames;

import javafx.stage.Stage;
import strongy.gui.style.StyleManager;
import strongy.io.preferences.StrongyPreferences;
import strongy.gui.style.theme.CustomTheme;

public class ThemeEditorDialog extends ThemedFrame {
	public ThemeEditorDialog(StyleManager styleManager, StrongyPreferences preferences, Stage owner,
			CustomTheme theme) {
		super(styleManager, preferences, "Theme Editor");
	}

	@Override
	protected void onExitButtonClicked() {
		dispose();
	}
}
