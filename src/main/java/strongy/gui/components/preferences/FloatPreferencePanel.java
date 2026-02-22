package strongy.gui.components.preferences;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import strongy.gui.components.ThemedComponent;
import strongy.gui.components.inputfields.DecimalTextField;
import strongy.gui.style.StyleManager;
import strongy.io.preferences.FloatPreference;

public class FloatPreferencePanel extends HBox implements ThemedComponent {

	private final FloatPreference preference;
	private final DecimalTextField textField;

	public FloatPreferencePanel(StyleManager styleManager, String text, FloatPreference preference) {
		this.preference = preference;
		getStyleClass().add("preference-row");
		setAlignment(Pos.CENTER_LEFT);

		Label label = new Label(text);
		label.getStyleClass().add("themed-label-strong");

		textField = new DecimalTextField(styleManager);
		textField.setPrefWidth(60);
		textField.setText(String.valueOf(preference.get()));

		textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
			if (!newVal) {
				updatePreference();
			}
		});

		textField.setOnAction(e -> updatePreference());

		getChildren().addAll(label, textField);
	}

	private void updatePreference() {
		try {
			float val = Float.parseFloat(textField.getText());
			preference.set(val);
		} catch (NumberFormatException ignored) {
			textField.setText(String.valueOf(preference.get()));
		}
	}
}