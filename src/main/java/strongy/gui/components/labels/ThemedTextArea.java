package strongy.gui.components.labels;

import javafx.scene.control.TextArea;
import strongy.gui.components.ThemedComponent;
import strongy.gui.style.StyleManager;

/**
 * A themed multi-line text area — JavaFX replacement.
 */
public class ThemedTextArea extends TextArea implements ThemedComponent {

	public ThemedTextArea(StyleManager styleManager) {
		getStyleClass().add("themed-text-field");
		setEditable(false);
		setWrapText(true);
	}

	public ThemedTextArea(StyleManager styleManager, String text) {
		super(text);
		getStyleClass().add("themed-text-field");
		setEditable(false);
		setWrapText(true);
	}
}
