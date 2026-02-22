package strongy.gui.components.panels;

import javafx.scene.layout.VBox;
import strongy.gui.components.ThemedComponent;
import strongy.gui.style.StyleManager;

public class ResizablePanel extends VBox implements ThemedComponent {
	public ResizablePanel() {
		getStyleClass().add("themed-panel");
	}
}
