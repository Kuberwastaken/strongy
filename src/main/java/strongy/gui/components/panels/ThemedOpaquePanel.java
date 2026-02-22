package strongy.gui.components.panels;

import javafx.scene.layout.VBox;
import strongy.gui.components.ThemedComponent;
import strongy.gui.style.StyleManager;

public class ThemedOpaquePanel extends VBox implements ThemedComponent {
	public ThemedOpaquePanel(StyleManager styleManager) {
		getStyleClass().add("themed-panel");
	}
}
