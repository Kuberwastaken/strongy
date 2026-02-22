package strongy.gui.components.layout;

import javafx.scene.layout.VBox;
import javafx.scene.Node;

/**
 * Empty wrapper around VBox, previously used in Swing.
 */
public class StretchPanel extends VBox {
	public void add(Node node) {
		getChildren().add(node);
	}
}
