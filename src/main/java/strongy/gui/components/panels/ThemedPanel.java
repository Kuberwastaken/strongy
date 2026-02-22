package strongy.gui.components.panels;

import javafx.scene.layout.VBox;
import strongy.gui.components.ThemedComponent;
import strongy.gui.style.StyleManager;

/**
 * A themed VBox panel — JavaFX replacement for the Swing JPanel subclass.
 */
public class ThemedPanel extends VBox implements ThemedComponent {

	public ThemedPanel(StyleManager styleManager) {
		getStyleClass().add("themed-panel");
	}

	public ThemedPanel(StyleManager styleManager, boolean useStrongBg) {
		if (useStrongBg) {
			getStyleClass().add("header-panel");
		} else {
			getStyleClass().add("themed-panel");
		}
	}
}
