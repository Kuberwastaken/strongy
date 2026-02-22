package strongy.gui.components.preferences;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import strongy.gui.components.ThemedComponent;
import strongy.gui.style.StyleManager;
import strongy.io.preferences.IMultipleChoicePreferenceDataType;
import strongy.io.preferences.MultipleChoicePreference;

public class RadioButtonPanel extends VBox implements ThemedComponent {

	private final ToggleGroup toggleGroup = new ToggleGroup();

	public RadioButtonPanel(StyleManager styleManager, String text, MultipleChoicePreference<?> preference) {
		getStyleClass().add("preference-row");
		setAlignment(Pos.CENTER_LEFT);
		setSpacing(4);

		Label titleLabel = new Label(text);
		titleLabel.getStyleClass().add("themed-label-strong");
		getChildren().add(titleLabel);

		VBox radioBox = new VBox(4);
		radioBox.setAlignment(Pos.CENTER_LEFT);

		for (IMultipleChoicePreferenceDataType option : preference.getChoices()) {
			RadioButton rb = new RadioButton(option.choiceName());
			rb.getStyleClass().add("radio-button");
			rb.setToggleGroup(toggleGroup);
			if (option == preference.get()) {
				rb.setSelected(true);
			}

			rb.setOnAction(e -> {
				if (rb.isSelected()) {
					((MultipleChoicePreference<IMultipleChoicePreferenceDataType>) preference).set(option);
				}
			});

			radioBox.getChildren().add(rb);
		}

		getChildren().add(radioBox);

		preference.whenModified().subscribe(val -> {
			javafx.application.Platform.runLater(() -> {
				for (javafx.scene.control.Toggle toggle : toggleGroup.getToggles()) {
					RadioButton rb = (RadioButton) toggle;
					if (rb.getText().equals(val.choiceName())) {
						toggleGroup.selectToggle(rb);
						break;
					}
				}
			});
		});
	}
}