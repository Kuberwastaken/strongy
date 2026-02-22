package strongy.gui.components.inputfields;

import strongy.gui.style.StyleManager;

/**
 * A text field that only allows decimal (numeric) input.
 */
public class DecimalTextField extends ThemedTextField {

	public DecimalTextField(StyleManager styleManager) {
		super(styleManager);
		setupFilter();
	}

	public DecimalTextField(StyleManager styleManager, String text) {
		super(styleManager, text);
		setupFilter();
	}

	private void setupFilter() {
		textProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal != null && !newVal.matches("-?\\d*\\.?\\d*")) {
				setText(oldVal);
			}
		});
	}
}
