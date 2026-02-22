package strongy.gui.components.layout;

import javafx.scene.layout.Region;
import strongy.gui.components.ThemedComponent;
import strongy.gui.style.StyleManager;

/**
 * A horizontal divider line.
 */
public class Divider extends Region implements ThemedComponent {

	public Divider(StyleManager styleManager) {
		getStyleClass().add("divider");
		setMinHeight(1);
		setMaxHeight(1);
	}
}
