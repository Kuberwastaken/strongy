package strongy.gui.components.preferences;

import javafx.scene.control.CheckBox;
import strongy.gui.components.ThemedComponent;
import strongy.gui.style.StyleManager;
import strongy.io.preferences.BooleanPreference;

/**
 * A Checkbox that binds to a BooleanPreference.
 */
public class CustomCheckbox extends CheckBox implements ThemedComponent {

	private final BooleanPreference preference;

	public CustomCheckbox(StyleManager styleManager, BooleanPreference preference) {
		this.preference = preference;
		getStyleClass().add("check-box");

		// Bind from preference to UI
		setSelected(preference.get());
		preference.whenModified().subscribe(value -> javafx.application.Platform.runLater(() -> setSelected(value)));

		// Bind from UI to preference
		setOnAction(e -> {
			preference.set(isSelected());
		});
	}
}
