package strongy.gui.components.labels;

import javafx.scene.control.Label;
import strongy.gui.components.ThemedComponent;
import strongy.gui.style.SizePreference;
import strongy.gui.style.StyleManager;
import strongy.gui.style.theme.WrappedColor;

/**
 * A themed label — JavaFX replacement. Extends JavaFX Label.
 */
public class ThemedLabel extends Label implements ThemedComponent, ILabel {

	public ThemedLabel(StyleManager styleManager) {
		getStyleClass().add("themed-label");
	}

	public ThemedLabel(StyleManager styleManager, String text) {
		super(text);
		getStyleClass().add("themed-label");
	}

	public ThemedLabel(StyleManager styleManager, String text, boolean bold) {
		super(text);
		if (bold) {
			getStyleClass().add("themed-label-strong");
		} else {
			getStyleClass().add("themed-label");
		}
	}

	public int getTextSize(SizePreference p) {
		return p.TEXT_SIZE_MEDIUM;
	}

	public void setForegroundColor(WrappedColor color) {
		if (color != null && color.color() != null) {
			setStyle("-fx-text-fill: " + color.hex() + ";");
			color.whenColorChanged().subscribe(c -> {
				javafx.application.Platform.runLater(() ->
					setStyle("-fx-text-fill: " + color.hex() + ";")
				);
			});
		}
	}
}
