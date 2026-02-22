package strongy.gui.components.panels;

import javafx.scene.layout.VBox;
import strongy.gui.components.ThemedComponent;
import strongy.gui.style.StyleManager;

/**
 * A themed panel that restricts horizontal growth.
 * In JavaFX this is simply a VBox with maxWidth set.
 */
public class HorizontalRestrictedThemedPanel extends VBox implements ThemedComponent {

	public HorizontalRestrictedThemedPanel(StyleManager styleManager) {
		getStyleClass().add("themed-panel");
		setMaxWidth(USE_PREF_SIZE);
	}

	public HorizontalRestrictedThemedPanel(StyleManager styleManager, boolean useStrongBg) {
		if (useStrongBg) {
			getStyleClass().add("header-panel");
		} else {
			getStyleClass().add("themed-panel");
		}
		setMaxWidth(USE_PREF_SIZE);
	}
}
