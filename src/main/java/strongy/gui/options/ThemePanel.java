package strongy.gui.options;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.gui.style.theme.Theme;
import strongy.gui.frames.OptionsFrame;

public class ThemePanel extends ThemedPanel {
	public ThemePanel(StyleManager styleManager, Theme theme, OptionsFrame frame) {
		super(styleManager);
		getChildren().add(new Label(theme.toString()));
	}
}