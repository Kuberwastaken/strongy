package strongy.gui.components.layout;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import strongy.gui.style.StyleManager;

/**
 * A layout component that pairs a label with another Node.
 */
public class LabeledField extends HBox {

	public LabeledField(StyleManager styleManager, String labelText, Node field) {
		setAlignment(Pos.CENTER_LEFT);
		setSpacing(8);

		Label label = new Label(labelText);
		label.getStyleClass().add("themed-label-strong");

		getChildren().addAll(label, field);
	}
}
