package strongy.gui.components.preferences;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import strongy.gui.components.ThemedComponent;
import strongy.gui.style.StyleManager;
import strongy.io.preferences.HotkeyPreference;
import strongy.util.I18n;

public class HotkeyPanel extends HBox implements ThemedComponent {

	private final HotkeyPreference preference;
	private final Button button;
	private boolean isListening = false;

	public HotkeyPanel(StyleManager styleManager, String text, HotkeyPreference preference) {
		this.preference = preference;
		getStyleClass().add("preference-row");
		setAlignment(Pos.CENTER_LEFT);
		setSpacing(8);

		Label label = new Label(text);
		label.getStyleClass().add("themed-label-strong");

		button = new Button();
		button.getStyleClass().add("flat-button");
		updateButtonText();

		button.setOnAction(e -> {
			if (!isListening) {
				isListening = true;
				button.setText("Press a key...");
				button.requestFocus();
			} else {
				// Cancel
				isListening = false;
				updateButtonText();
			}
		});

		button.setOnKeyPressed(e -> {
			if (isListening) {
				preference.setCode(e.getCode().getCode());
				isListening = false;
				updateButtonText();
			}
		});

		getChildren().addAll(label, button);
	}

	private void updateButtonText() {
		if (preference.getCode() < 0) {
			button.setText(I18n.get("settings.not_in_use"));
		} else {
			// Basic formatting placeholder, you may want to refine this
			button.setText(javafx.scene.input.KeyCode.values()[preference.getCode()].getName());
		}
	}
}