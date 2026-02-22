package strongy.gui.components.inputfields;

import strongy.gui.style.StyleManager;

/**
 * A text field with a maximum character limit.
 */
public class LimitedThemedTextField extends ThemedTextField {

	private final int maxLength;

	public LimitedThemedTextField(StyleManager styleManager, int maxLength) {
		super(styleManager);
		this.maxLength = maxLength;
		setupFilter();
	}

	private void setupFilter() {
		textProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal != null && newVal.length() > maxLength) {
				setText(oldVal);
			}
		});
	}
}
