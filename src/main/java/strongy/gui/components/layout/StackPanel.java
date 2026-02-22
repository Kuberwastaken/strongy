package strongy.gui.components.layout;

import javafx.scene.layout.VBox;
import javafx.scene.Node;

/**
 * Empty wrapper around VBox, previously used in Swing to manage stacked
 * components.
 */
public class StackPanel extends VBox {
	public StackPanel() {
		setSpacing(0);
	}

	public void add(Node node) {
		getChildren().add(node);
	}
}
