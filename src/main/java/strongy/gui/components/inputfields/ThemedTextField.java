package strongy.gui.components.inputfields;

import javafx.scene.control.TextField;
import strongy.gui.components.ThemedComponent;
import strongy.gui.style.StyleManager;

/**
 * A themed text field — JavaFX replacement.
 */
public class ThemedTextField extends TextField implements ThemedComponent {

	public ThemedTextField(StyleManager styleManager) {
		getStyleClass().add("themed-text-field");
	}

	public ThemedTextField(StyleManager styleManager, String text) {
		super(text);
		getStyleClass().add("themed-text-field");
	}
}