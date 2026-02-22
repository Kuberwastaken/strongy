package strongy.gui.style.theme;

import javafx.scene.paint.Color;

import strongy.event.ISubscribable;
import strongy.event.ObservableProperty;

public class WrappedColor {

	private Color color;

	private final ObservableProperty<Color> whenColorChanged = new ObservableProperty<>();

	public void set(WrappedColor other) {
		if (this.color != null && this.color.equals(other.color()))
			return;
		this.color = other.color();
		whenColorChanged.notifySubscribers(color);
	}

	public void set(Color color) {
		if (this.color != null && this.color.equals(color))
			return;
		this.color = color;
		whenColorChanged.notifySubscribers(color);
	}

	public ISubscribable<Color> whenColorChanged() {
		return whenColorChanged;
	}

	public Color color() {
		return color;
	}

	public boolean isEquivalentTo(WrappedColor other) {
		if (color == null || other.color == null)
			return false;
		return toHexInt() == other.toHexInt();
	}

	public String hex() {
		Color c = color();
		return String.format("#%02X%02X%02X",
				(int) (c.getRed() * 255),
				(int) (c.getGreen() * 255),
				(int) (c.getBlue() * 255));
	}

	public Color interpolate(Color other, float t) {
		return color().interpolate(other, t);
	}

	/**
	 * Convert to CSS-compatible color string.
	 */
	public String toCss() {
		return hex();
	}

	private int toHexInt() {
		Color c = color();
		int r = (int) (c.getRed() * 255);
		int g = (int) (c.getGreen() * 255);
		int b = (int) (c.getBlue() * 255);
		return (r << 16) | (g << 8) | b;
	}
}
