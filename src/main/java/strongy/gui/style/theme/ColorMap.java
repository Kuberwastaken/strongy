package strongy.gui.style.theme;

import javafx.scene.paint.Color;

public class ColorMap {

	private final Color c0;
	private final Color c50;
	private final Color c100;

	public ColorMap(Color c0, Color c50, Color c100) {
		this.c0 = c0;
		this.c50 = c50;
		this.c100 = c100;
	}

	public Color getColor(double certainty) {
		if (certainty <= 0)
			return c0;
		if (certainty >= 1)
			return c100;
		if (certainty <= 0.5) {
			double t = certainty * 2;
			return c0.interpolate(c50, t);
		} else {
			double t = (certainty - 0.5) * 2;
			return c50.interpolate(c100, t);
		}
	}

	/**
	 * Return CSS-compatible hex color string for a given certainty.
	 */
	public String getCssColor(double certainty) {
		Color c = getColor(certainty);
		return String.format("#%02X%02X%02X",
				(int) (c.getRed() * 255),
				(int) (c.getGreen() * 255),
				(int) (c.getBlue() * 255));
	}

}
