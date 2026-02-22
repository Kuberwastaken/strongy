package strongy.gui.components.labels;

import javafx.scene.paint.Color;
import strongy.gui.style.StyleManager;
import strongy.gui.style.theme.WrappedColor;

/**
 * A label whose text color can be dynamically set.
 */
public class ColoredLabel extends ThemedLabel {

	public ColoredLabel(StyleManager styleManager) {
		super(styleManager);
	}

	public ColoredLabel(StyleManager styleManager, String text) {
		super(styleManager, text);
	}

	public void setColor(Color color) {
		if (color != null) {
			setStyle(String.format("-fx-text-fill: #%02X%02X%02X;",
					(int)(color.getRed() * 255),
					(int)(color.getGreen() * 255),
					(int)(color.getBlue() * 255)));
		}
	}
}
