package strongy.gui.components.labels;

import javafx.scene.paint.Color;
import strongy.gui.components.ThemedComponent;
import strongy.gui.style.SizePreference;
import strongy.gui.style.StyleManager;
import strongy.gui.style.theme.ColorMap;

/**
 * A label that interpolates its text color based on a certainty value.
 */
public class ColorMapLabel extends ThemedLabel {

	private ColorMap colorMap;

	public ColorMapLabel(StyleManager styleManager) {
		super(styleManager);
		this.colorMap = styleManager.currentTheme.CERTAINTY_COLOR_MAP.get();
		styleManager.currentTheme.whenModified().subscribe(ct ->
			javafx.application.Platform.runLater(() -> this.colorMap = ct.CERTAINTY_COLOR_MAP.get())
		);
	}

	public ColorMapLabel(StyleManager styleManager, String text) {
		super(styleManager, text);
		this.colorMap = styleManager.currentTheme.CERTAINTY_COLOR_MAP.get();
	}

	public void setCertainty(double certainty) {
		if (colorMap != null) {
			Color c = colorMap.getColor(certainty);
			setStyle(String.format("-fx-text-fill: #%02X%02X%02X;",
					(int)(c.getRed() * 255),
					(int)(c.getGreen() * 255),
					(int)(c.getBlue() * 255)));
		}
	}

	@Override
	public int getTextSize(SizePreference p) {
		return p.TEXT_SIZE_MEDIUM;
	}
}