package strongy.gui.components.inputfields;

import strongy.gui.style.StyleManager;

/**
 * A text field that only allows hex characters.
 */
public class HexThemedTextField extends ThemedTextField {

	public HexThemedTextField(StyleManager styleManager) {
		super(styleManager);
		setupFilter();
	}

	private void setupFilter() {
		textProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal != null && !newVal.matches("[0-9a-fA-F#]*")) {
				setText(oldVal);
			}
		});
	}
}
