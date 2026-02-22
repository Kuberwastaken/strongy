package strongy.gui.components.preferences;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import strongy.gui.components.ThemedComponent;
import strongy.gui.components.inputfields.DecimalTextField;
import strongy.gui.style.StyleManager;
import strongy.io.preferences.DoublePreference;

public class DoublePreferencePanel extends HBox implements ThemedComponent {

	private final DoublePreference preference;
	private final DecimalTextField textField;

	public DoublePreferencePanel(StyleManager styleManager, String text, DoublePreference preference) {
		this.preference = preference;
		getStyleClass().add("preference-row");
		setAlignment(Pos.CENTER_LEFT);

		Label label = new Label(text);
		label.getStyleClass().add("themed-label-strong");

		textField = new DecimalTextField(styleManager);
		textField.setPrefWidth(60);
		textField.setText(String.valueOf(preference.get()));

		textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
			if (!newVal) { // focus lost
				updatePreference();
			}
		});

		textField.setOnAction(e -> updatePreference()); // Enter key

		getChildren().addAll(label, textField);
	}

	private void updatePreference() {
		try {
			double val = Double.parseDouble(textField.getText());
			preference.set(val);
		} catch (NumberFormatException ignored) {
			textField.setText(String.valueOf(preference.get()));
		}
	}
}