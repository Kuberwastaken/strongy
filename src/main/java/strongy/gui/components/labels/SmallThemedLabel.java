package strongy.gui.components.labels;

import strongy.gui.style.SizePreference;
import strongy.gui.style.StyleManager;

/**
 * A smaller themed label — uses TEXT_SIZE_SMALL instead of TEXT_SIZE_MEDIUM.
 */
public class SmallThemedLabel extends ThemedLabel {

	public SmallThemedLabel(StyleManager styleManager) {
		super(styleManager);
		setStyle(getStyle() + " -fx-font-size: " + styleManager.size.TEXT_SIZE_SMALL + "px;");
	}

	public SmallThemedLabel(StyleManager styleManager, String text) {
		super(styleManager, text);
		setStyle(getStyle() + " -fx-font-size: " + styleManager.size.TEXT_SIZE_SMALL + "px;");
	}

	@Override
	public int getTextSize(SizePreference p) {
		return p.TEXT_SIZE_SMALL;
	}
}
