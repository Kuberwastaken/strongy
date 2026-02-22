package strongy.gui.style.theme;

import javafx.scene.paint.Color;
import java.util.ArrayList;

import strongy.event.ISubscribable;
import strongy.event.ObservableField;
import strongy.gui.themeeditor.ThemeSerializer;
import strongy.util.Assert;

public class CustomTheme extends Theme {

	private final ArrayList<ConfigurableColor> configurableColors;

	private final ObservableField<String> themeString = new ObservableField<String>();

	public CustomTheme() {
		this("temp", "", 0);
	}

	public CustomTheme(String name, String themeString, int uid) {
		super(name, uid);
		this.themeString.set(themeString);
		configurableColors = new ArrayList<ConfigurableColor>();

		COLOR_STRONGEST = createColor(Color.web("#1C1C27"), "Title bar", "a");
		COLOR_STRONG = createColor(Color.web("#2D3238"), "Header background", "b");
		COLOR_SLIGHTLY_WEAK = createColor(Color.web("#2B2D44"), "Result background", "c");
		COLOR_NEUTRAL = createColor(Color.web("#28293D"), "Throws background", "d");
		COLOR_DIVIDER = createColor(Color.web("#212130"), "Dividers", "e");
		COLOR_DIVIDER_DARK = createColor(Color.web("#1C1C27"), "Header Dividers", "f");
		TEXT_COLOR_SLIGHTLY_WEAK = createColor(Color.WHITE, "Text", "h");
		TEXT_COLOR_TITLE = createColor(Color.WHITE, "Title text", "n");
		TEXT_COLOR_NEUTRAL = createColor(Color.LIGHTGRAY, "Throws text", "k");
		TEXT_COLOR_SLIGHTLY_STRONG = createColor(Color.web("#E5E5E5"), "Divine text", "i");
		TEXT_COLOR_WEAK = createColor(Color.GRAY, "Version text", "j");
		TEXT_COLOR_HEADER = createColor(Color.web("#E5E5E5"), "Header text", "o");
		COLOR_POSITIVE = createColor(Color.web("#75CC6C"), "Subpixel +", "l");
		COLOR_NEGATIVE = createColor(Color.web("#CC6E72"), "Subpixel -", "m");

		COLOR_GRADIENT_100 = createColor(Color.web("#00CE29"), "Certainty 100%", "r");
		COLOR_GRADIENT_50 = createColor(Color.YELLOW, "Certainty 50%", "q");
		COLOR_GRADIENT_0 = createColor(Color.RED, "Certainty 0%", "p");

		COLOR_SLIGHTLY_STRONG = createColor(Color.web("#31353A"), COLOR_STRONG);
		COLOR_SATURATED = createColor(Color.web("#57EBA3"), COLOR_STRONG);

		COLOR_EXIT_BUTTON_HOVER = createColor(Color.web("#F04747"));
	}

	@Override
	protected void loadTheme() {
		loaded = true;
		setFromTheme(ThemeSerializer.deserialize(themeString.get()));
	}

	public void setFromTheme(Theme theme) {
		setFromTheme(theme, false);
	}

	public void setFromTheme(Theme theme, boolean copyName) {
		if (theme == null)
			return;
		COLOR_STRONGEST.set(theme.COLOR_STRONGEST);
		COLOR_STRONG.set(theme.COLOR_STRONG);
		COLOR_SLIGHTLY_WEAK.set(theme.COLOR_SLIGHTLY_WEAK);
		COLOR_NEUTRAL.set(theme.COLOR_NEUTRAL);
		COLOR_DIVIDER.set(theme.COLOR_DIVIDER);
		COLOR_DIVIDER_DARK.set(theme.COLOR_DIVIDER_DARK);
		COLOR_EXIT_BUTTON_HOVER.set(theme.COLOR_EXIT_BUTTON_HOVER);
		TEXT_COLOR_SLIGHTLY_WEAK.set(theme.TEXT_COLOR_SLIGHTLY_WEAK);
		TEXT_COLOR_SLIGHTLY_STRONG.set(theme.TEXT_COLOR_SLIGHTLY_STRONG);
		TEXT_COLOR_WEAK.set(theme.TEXT_COLOR_WEAK);
		TEXT_COLOR_NEUTRAL.set(theme.TEXT_COLOR_NEUTRAL);
		TEXT_COLOR_HEADER.set(theme.TEXT_COLOR_HEADER);
		COLOR_POSITIVE.set(theme.COLOR_POSITIVE);
		COLOR_NEGATIVE.set(theme.COLOR_NEGATIVE);
		TEXT_COLOR_TITLE.set(theme.TEXT_COLOR_TITLE);

		COLOR_GRADIENT_100.set(theme.COLOR_GRADIENT_100);
		COLOR_GRADIENT_50.set(theme.COLOR_GRADIENT_50);
		COLOR_GRADIENT_0.set(theme.COLOR_GRADIENT_0);

		String newThemeString = ThemeSerializer.serialize(this);
		if (!themeString.get().contentEquals(newThemeString))
			themeString.set(newThemeString);

		if (copyName)
			name.set(theme.name.get());

		whenModified.notifySubscribers(this);
	}

	public ISubscribable<String> whenThemeStringChanged() {
		return themeString;
	}

	public String getThemeString() {
		return themeString.get();
	}

	public ArrayList<ConfigurableColor> getConfigurableColors() {
		return configurableColors;
	}

	public ConfigurableColor getConfigurableColor(String uid) {
		for (ConfigurableColor configurableColor : configurableColors) {
			if (uid.contentEquals(configurableColor.uid)) {
				return configurableColor;
			}
		}
		return null;
	}

	protected WrappedColor createColor(Color color, WrappedColor parent) {
		WrappedColor wc = super.createColor(color);
		double dr = color.getRed() - parent.color().getRed();
		double dg = color.getGreen() - parent.color().getGreen();
		double db = color.getBlue() - parent.color().getBlue();
		parent.whenColorChanged().subscribe(c -> wc.set(Color.color(
				clamp(c.getRed() + dr),
				clamp(c.getGreen() + dg),
				clamp(c.getBlue() + db))));
		return wc;
	}

	private static double clamp(double v) {
		if (v < 0)
			return 0;
		if (v > 1)
			return 1;
		return v;
	}

	protected WrappedColor createColor(Color color, String name, String uid) {
		Assert.isTrue(uid.length() == 1);
		Assert.isTrue(configurableColors.stream().noneMatch(cc -> cc.uid == uid));
		WrappedColor wc = super.createColor(color);
		configurableColors.add(new ConfigurableColor(wc, name, uid));
		return wc;
	}

	public boolean isEquivalentTo(CustomTheme other) {
		if (other.configurableColors.size() != this.configurableColors.size())
			return false;
		if (!this.name.get().contentEquals(other.name.get()))
			return false;
		for (ConfigurableColor configurableColor : configurableColors) {
			if (!other.getConfigurableColor(configurableColor.uid).color.isEquivalentTo(configurableColor.color)) {
				return false;
			}
		}
		return true;
	}

	public void setName(String newName) {
		this.name.set(newName);
	}

}