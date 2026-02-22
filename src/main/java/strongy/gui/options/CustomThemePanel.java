package strongy.gui.options;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.gui.style.theme.CustomTheme;
import strongy.gui.frames.OptionsFrame;

public class CustomThemePanel extends ThemedPanel {
	public CustomThemePanel(StyleManager styleManager, CustomTheme theme, OptionsFrame frame) {
		super(styleManager);
		getChildren().add(new Label("Custom Theme Settings"));
	}
}
