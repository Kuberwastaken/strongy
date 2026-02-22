package strongy.gui.components.preferences;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import strongy.gui.components.ThemedComponent;
import strongy.gui.style.StyleManager;
import strongy.io.preferences.BooleanPreference;

public class CheckboxPanel extends HBox implements ThemedComponent {

	public CheckboxPanel(StyleManager styleManager, String text, BooleanPreference preference) {
		getStyleClass().add("preference-row");
		setAlignment(Pos.CENTER_LEFT);

		Label label = new Label(text);
		label.getStyleClass().add("themed-label-strong");

		CustomCheckbox checkbox = new CustomCheckbox(styleManager, preference);

		getChildren().addAll(label, checkbox);

		setOnMouseClicked(e -> {
			if (e.getTarget() != checkbox) {
				checkbox.fire();
			}
		});
	}
}