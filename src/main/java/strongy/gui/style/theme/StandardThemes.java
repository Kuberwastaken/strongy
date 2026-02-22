package strongy.gui.style.theme;

import javafx.scene.paint.Color;

import strongy.io.preferences.StrongyPreferences;
import strongy.io.preferences.SavedPreferences;

public class StandardThemes {

	// @formatter:off
	static final String template =
			"class %sTheme extends Theme {\r\n"
			+ "\r\n"
			+ "public static final int UID = ;\r\n"
			+ "\r\n"
			+ "\tpublic %sTheme() {\r\n"
			+ "\t\tsuper(\"%s\", UID);\r\n"
			+ "\t}\r\n"
			+ "\r\n"
			+ "\t@Override\r\n"
			+ "\tprotected void loadTheme() {\r\n"
			+ "\t\tloaded = true;\r\n"
			+ "\r\n"
			+ " %s"
			+ "\t}\r\n"
			+ "}";
	// @formatter:on

	public static void main(String[] args) {
		StrongyPreferences preferences = new StrongyPreferences(new SavedPreferences());
		Theme.loadThemes(preferences);

		for (CustomTheme theme : Theme.getCustomThemes()) {
			String name = theme.name.get();
			String nameNoSpace = name.replaceAll(" ", "");
			String colorsStringBuilder = String.format("\t\tCOLOR_NEUTRAL = createColor(Color.web(\"%s\"));\r\n",
					theme.COLOR_NEUTRAL.hex()) +
					String.format("\t\tCOLOR_STRONGEST = createColor(Color.web(\"%s\"));\r\n",
							theme.COLOR_STRONGEST.hex())
					+
					String.format("\t\tCOLOR_EXIT_BUTTON_HOVER = createColor(Color.web(\"%s\"));\r\n",
							theme.COLOR_EXIT_BUTTON_HOVER.hex())
					+
					String.format("\t\tCOLOR_DIVIDER = createColor(Color.web(\"%s\"));\r\n", theme.COLOR_DIVIDER.hex())
					+
					String.format("\t\tCOLOR_DIVIDER_DARK = createColor(Color.web(\"%s\"));\r\n",
							theme.COLOR_DIVIDER_DARK.hex())
					+
					String.format("\t\tCOLOR_SLIGHTLY_STRONG = createColor(Color.web(\"%s\"));\r\n",
							theme.COLOR_SLIGHTLY_STRONG.hex())
					+
					String.format("\t\tCOLOR_SLIGHTLY_WEAK = createColor(Color.web(\"%s\"));\r\n",
							theme.COLOR_SLIGHTLY_WEAK.hex())
					+
					String.format("\t\tTEXT_COLOR_SLIGHTLY_WEAK = createColor(Color.web(\"%s\"));\r\n",
							theme.TEXT_COLOR_SLIGHTLY_WEAK.hex())
					+
					String.format("\t\tTEXT_COLOR_SLIGHTLY_STRONG = createColor(Color.web(\"%s\"));\r\n",
							theme.TEXT_COLOR_SLIGHTLY_STRONG.hex())
					+
					String.format("\t\tTEXT_COLOR_WEAK = createColor(Color.web(\"%s\"));\r\n",
							theme.TEXT_COLOR_WEAK.hex())
					+
					String.format("\t\tTEXT_COLOR_NEUTRAL = createColor(Color.web(\"%s\"));\r\n",
							theme.TEXT_COLOR_NEUTRAL.hex())
					+
					String.format("\t\tTEXT_COLOR_HEADER = createColor(Color.web(\"%s\"));\r\n",
							theme.TEXT_COLOR_HEADER.hex())
					+
					String.format("\t\tCOLOR_STRONG = createColor(Color.web(\"%s\"));\r\n", theme.COLOR_STRONG.hex()) +
					String.format("\t\tCOLOR_SATURATED = createColor(Color.web(\"%s\"));\r\n",
							theme.COLOR_SATURATED.hex())
					+
					String.format("\t\tCOLOR_POSITIVE = createColor(Color.web(\"%s\"));\r\n",
							theme.COLOR_POSITIVE.hex())
					+
					String.format("\t\tCOLOR_NEGATIVE = createColor(Color.web(\"%s\"));\r\n",
							theme.COLOR_NEGATIVE.hex())
					+
					String.format(
							"\t\tTEXT_COLOR_TITLE = createColor(Color.web(\"%s\"));\r\n", theme.TEXT_COLOR_TITLE.hex())
					+
					String.format(
							"\t\tCOLOR_GRADIENT_0 = createColor(Color.web(\"%s\"));\r\n", theme.COLOR_GRADIENT_0.hex())
					+
					String.format("\t\tCOLOR_GRADIENT_50 = createColor(Color.web(\"%s\"));\r\n",
							theme.COLOR_GRADIENT_50.hex())
					+
					String.format("\t\tCOLOR_GRADIENT_100 = createColor(Color.web(\"%s\"));\r\n",
							theme.COLOR_GRADIENT_100.hex());
			System.out.printf(template + "%n", nameNoSpace, nameNoSpace, name, colorsStringBuilder);
		}
	}

}

class DarkTheme extends Theme {

	public static final int UID = 1;

	public DarkTheme() {
		super("Dark", UID);
	}

	@Override
	protected void loadTheme() {
		loaded = true;
		COLOR_NEUTRAL = createColor(Color.web("#33383D"));
		COLOR_STRONGEST = createColor(Color.web("#212529"));
		COLOR_EXIT_BUTTON_HOVER = createColor(Color.web("#F04747"));
		COLOR_DIVIDER = createColor(Color.web("#2A2E32"));
		COLOR_DIVIDER_DARK = createColor(Color.web("#212529"));
		COLOR_SLIGHTLY_STRONG = createColor(Color.web("#31353A"));
		COLOR_SLIGHTLY_WEAK = createColor(Color.web("#373C42"));
		TEXT_COLOR_SLIGHTLY_WEAK = createColor(Color.WHITE);
		TEXT_COLOR_SLIGHTLY_STRONG = createColor(Color.web("#E5E5E5"));
		TEXT_COLOR_WEAK = createColor(Color.GRAY);
		TEXT_COLOR_NEUTRAL = createColor(Color.LIGHTGRAY);
		TEXT_COLOR_HEADER = createColor(Color.web("#E5E5E5"));
		COLOR_STRONG = createColor(Color.web("#2D3238"));
		COLOR_SATURATED = createColor(Color.web("#57EBA3"));
		COLOR_POSITIVE = createColor(Color.web("#75CC6C"));
		COLOR_NEGATIVE = createColor(Color.web("#CC6E72"));
		TEXT_COLOR_TITLE = createColor(Color.WHITE);

		COLOR_GRADIENT_0 = createColor(Color.RED);
		COLOR_GRADIENT_50 = createColor(Color.YELLOW);
		COLOR_GRADIENT_100 = createColor(Color.web("#00CE29"));
	}
}

class BlueTheme extends Theme {

	public static final int UID = 2;

	public BlueTheme() {
		super("Blue", UID);
	}

	@Override
	protected void loadTheme() {
		loaded = true;
		COLOR_STRONGEST = createColor(Color.web("#1C1C27"));
		COLOR_DIVIDER = createColor(Color.web("#212130"));
		COLOR_DIVIDER_DARK = createColor(Color.web("#1C1C27"));
		COLOR_STRONG = createColor(Color.web("#252538"));
		COLOR_SLIGHTLY_STRONG = createColor(Color.web("#27273D"));
		COLOR_NEUTRAL = createColor(Color.web("#28293D"));
		COLOR_SLIGHTLY_WEAK = createColor(Color.web("#2B2D44"));
		COLOR_EXIT_BUTTON_HOVER = createColor(Color.web("#F04747"));
		TEXT_COLOR_SLIGHTLY_WEAK = createColor(Color.WHITE);
		TEXT_COLOR_SLIGHTLY_STRONG = createColor(Color.web("#E5E5E5"));
		TEXT_COLOR_WEAK = createColor(Color.GRAY);
		TEXT_COLOR_NEUTRAL = createColor(Color.LIGHTGRAY);
		TEXT_COLOR_HEADER = createColor(Color.web("#E5E5E5"));
		COLOR_SATURATED = createColor(Color.web("#57EBA3"));
		COLOR_POSITIVE = createColor(Color.web("#75CC6C"));
		COLOR_NEGATIVE = createColor(Color.web("#CC6E72"));
		TEXT_COLOR_TITLE = createColor(Color.WHITE);

		COLOR_GRADIENT_0 = createColor(Color.RED);
		COLOR_GRADIENT_50 = createColor(Color.YELLOW);
		COLOR_GRADIENT_100 = createColor(Color.web("#00CE29"));
	}
}

class LightTheme extends Theme {

	public static final int UID = 3;

	public LightTheme() {
		super("Light", UID);
	}

	@Override
	protected void loadTheme() {
		loaded = true;
		COLOR_NEUTRAL = createColor(Color.web("#F5F5F5"));
		COLOR_DIVIDER = createColor(Color.web("#D8D8D8"));
		COLOR_DIVIDER_DARK = createColor(Color.web("#C1C1C1"));
		COLOR_EXIT_BUTTON_HOVER = createColor(Color.web("#F04747"));
		COLOR_STRONGEST = createColor(Color.web("#C1C1C1"));
		COLOR_SLIGHTLY_STRONG = createColor(Color.web("#EFEFEF"));
		COLOR_SLIGHTLY_WEAK = createColor(Color.web("#F9F9F9"));
		TEXT_COLOR_SLIGHTLY_WEAK = createColor(Color.BLACK);
		TEXT_COLOR_SLIGHTLY_STRONG = createColor(Color.web("#191919"));
		TEXT_COLOR_WEAK = createColor(Color.web("#888888"));
		TEXT_COLOR_NEUTRAL = createColor(Color.DARKGRAY);
		TEXT_COLOR_HEADER = createColor(Color.web("#191919"));
		COLOR_STRONG = createColor(Color.web("#E5E5E5"));
		COLOR_SATURATED = createColor(Color.web("#BAD7EF"));
		COLOR_POSITIVE = createColor(Color.web("#1E9910"));
		COLOR_NEGATIVE = createColor(Color.web("#991017"));
		TEXT_COLOR_TITLE = createColor(Color.web("#373737"));

		COLOR_GRADIENT_0 = createColor(Color.RED);
		COLOR_GRADIENT_50 = createColor(Color.YELLOW);
		COLOR_GRADIENT_100 = createColor(Color.web("#00CE29"));
	}
}

class BastionTheme extends Theme {

	public static final int UID = 4;

	public BastionTheme() {
		super("Bastion", UID);
	}

	@Override
	protected void loadTheme() {
		loaded = true;
		COLOR_NEUTRAL = createColor(Color.web("#312F3B"));
		COLOR_STRONGEST = createColor(Color.web("#191413"));
		COLOR_EXIT_BUTTON_HOVER = createColor(Color.web("#F04747"));
		COLOR_DIVIDER = createColor(Color.web("#251E1F"));
		COLOR_DIVIDER_DARK = createColor(Color.web("#1B1E21"));
		COLOR_SLIGHTLY_STRONG = createColor(Color.web("#2C272E"));
		COLOR_SLIGHTLY_WEAK = createColor(Color.web("#39353E"));
		TEXT_COLOR_SLIGHTLY_WEAK = createColor(Color.web("#FFE189"));
		TEXT_COLOR_SLIGHTLY_STRONG = createColor(Color.web("#E5E5E5"));
		TEXT_COLOR_WEAK = createColor(Color.web("#AE804A"));
		TEXT_COLOR_NEUTRAL = createColor(Color.web("#B3770C"));
		TEXT_COLOR_HEADER = createColor(Color.web("#D88F11"));
		COLOR_STRONG = createColor(Color.web("#28242C"));
		COLOR_SATURATED = createColor(Color.web("#52DD97"));
		COLOR_POSITIVE = createColor(Color.web("#75CC6C"));
		COLOR_NEGATIVE = createColor(Color.web("#CC6E72"));
		TEXT_COLOR_TITLE = createColor(Color.web("#FFA700"));
		COLOR_GRADIENT_0 = createColor(Color.web("#FF0000"));
		COLOR_GRADIENT_50 = createColor(Color.web("#FFFF00"));
		COLOR_GRADIENT_100 = createColor(Color.web("#00CE29"));
	}
}

class NetherbrickTheme extends Theme {

	public static final int UID = 5;

	public NetherbrickTheme() {
		super("Nether brick", UID);
	}

	@Override
	protected void loadTheme() {
		loaded = true;
		COLOR_NEUTRAL = createColor(Color.web("#32181D"));
		COLOR_STRONGEST = createColor(Color.web("#201214"));
		COLOR_EXIT_BUTTON_HOVER = createColor(Color.web("#F04747"));
		COLOR_DIVIDER = createColor(Color.web("#261318"));
		COLOR_DIVIDER_DARK = createColor(Color.web("#120A0D"));
		COLOR_SLIGHTLY_STRONG = createColor(Color.web("#301619"));
		COLOR_SLIGHTLY_WEAK = createColor(Color.web("#381C21"));
		TEXT_COLOR_SLIGHTLY_WEAK = createColor(Color.web("#FFE69C"));
		TEXT_COLOR_SLIGHTLY_STRONG = createColor(Color.web("#FFE097"));
		TEXT_COLOR_WEAK = createColor(Color.web("#A38746"));
		TEXT_COLOR_NEUTRAL = createColor(Color.web("#FFDF80"));
		TEXT_COLOR_HEADER = createColor(Color.web("#FFDA7A"));
		COLOR_STRONG = createColor(Color.web("#2C1317"));
		COLOR_SATURATED = createColor(Color.web("#56CC82"));
		COLOR_POSITIVE = createColor(Color.web("#75CC6C"));
		COLOR_NEGATIVE = createColor(Color.web("#CC6E72"));
		TEXT_COLOR_TITLE = createColor(Color.web("#FFC54A"));
		COLOR_GRADIENT_0 = createColor(Color.web("#FF0000"));
		COLOR_GRADIENT_50 = createColor(Color.web("#FFFF00"));
		COLOR_GRADIENT_100 = createColor(Color.web("#00CE29"));
	}
}

class BambooTheme extends Theme {

	public static final int UID = 6;

	public BambooTheme() {
		super("Bamboo", UID);
	}

	@Override
	protected void loadTheme() {
		loaded = true;
		COLOR_NEUTRAL = createColor(Color.web("#F0FFE0"));
		COLOR_STRONGEST = createColor(Color.web("#364D1D"));
		COLOR_EXIT_BUTTON_HOVER = createColor(Color.web("#F04747"));
		COLOR_DIVIDER = createColor(Color.web("#2D4514"));
		COLOR_DIVIDER_DARK = createColor(Color.web("#20300F"));
		COLOR_SLIGHTLY_STRONG = createColor(Color.web("#688B3A"));
		COLOR_SLIGHTLY_WEAK = createColor(Color.web("#F9FFF2"));
		TEXT_COLOR_SLIGHTLY_WEAK = createColor(Color.web("#314E17"));
		TEXT_COLOR_SLIGHTLY_STRONG = createColor(Color.web("#FFFFFF"));
		TEXT_COLOR_WEAK = createColor(Color.web("#B0C797"));
		TEXT_COLOR_NEUTRAL = createColor(Color.web("#314E17"));
		TEXT_COLOR_HEADER = createColor(Color.web("#FFFFFF"));
		COLOR_STRONG = createColor(Color.web("#648838"));
		COLOR_SATURATED = createColor(Color.web("#8EFFA3"));
		COLOR_POSITIVE = createColor(Color.web("#1E9910"));
		COLOR_NEGATIVE = createColor(Color.web("#991017"));
		TEXT_COLOR_TITLE = createColor(Color.web("#FFFFFF"));
		COLOR_GRADIENT_0 = createColor(Color.web("#FF0000"));
		COLOR_GRADIENT_50 = createColor(Color.web("#9C9C00"));
		COLOR_GRADIENT_100 = createColor(Color.web("#00CE29"));
	}
}

class NinjabrainTheme extends Theme {

	public static final int UID = 7;

	public NinjabrainTheme() {
		super("Ninjabrain", UID);
	}

	@Override
	protected void loadTheme() {
		loaded = true;
		COLOR_NEUTRAL = createColor(Color.web("#FFF4F4"));
		COLOR_STRONGEST = createColor(Color.web("#232323"));
		COLOR_EXIT_BUTTON_HOVER = createColor(Color.web("#F04747"));
		COLOR_DIVIDER = createColor(Color.web("#C5B6B6"));
		COLOR_DIVIDER_DARK = createColor(Color.web("#343434"));
		COLOR_SLIGHTLY_STRONG = createColor(Color.web("#FFD7DE"));
		COLOR_SLIGHTLY_WEAK = createColor(Color.web("#FFFAFA"));
		TEXT_COLOR_SLIGHTLY_WEAK = createColor(Color.web("#000000"));
		TEXT_COLOR_SLIGHTLY_STRONG = createColor(Color.web("#191919"));
		TEXT_COLOR_WEAK = createColor(Color.web("#888888"));
		TEXT_COLOR_NEUTRAL = createColor(Color.web("#404040"));
		TEXT_COLOR_HEADER = createColor(Color.web("#191919"));
		COLOR_STRONG = createColor(Color.web("#FFD4DC"));
		COLOR_SATURATED = createColor(Color.web("#FFFFFF"));
		COLOR_POSITIVE = createColor(Color.web("#1E9910"));
		COLOR_NEGATIVE = createColor(Color.web("#991017"));
		TEXT_COLOR_TITLE = createColor(Color.web("#FFFFFF"));
		COLOR_GRADIENT_0 = createColor(Color.web("#FF0000"));
		COLOR_GRADIENT_50 = createColor(Color.web("#7E7E00"));
		COLOR_GRADIENT_100 = createColor(Color.web("#00CE29"));
	}
}

class CouriwayTheme extends Theme {

	public static final int UID = 8;

	public CouriwayTheme() {
		super("Couriway", UID);
	}

	@Override
	protected void loadTheme() {
		loaded = true;
		COLOR_NEUTRAL = createColor(Color.web("#40324E"));
		COLOR_STRONGEST = createColor(Color.web("#322045"));
		COLOR_EXIT_BUTTON_HOVER = createColor(Color.web("#F04747"));
		COLOR_DIVIDER = createColor(Color.web("#191122"));
		COLOR_DIVIDER_DARK = createColor(Color.web("#502568"));
		COLOR_SLIGHTLY_STRONG = createColor(Color.web("#FFBB3A"));
		COLOR_SLIGHTLY_WEAK = createColor(Color.web("#543F66"));
		TEXT_COLOR_SLIGHTLY_WEAK = createColor(Color.web("#FFFFFF"));
		TEXT_COLOR_SLIGHTLY_STRONG = createColor(Color.web("#E5E5E5"));
		TEXT_COLOR_WEAK = createColor(Color.web("#A88EC1"));
		TEXT_COLOR_NEUTRAL = createColor(Color.web("#FFFFFF"));
		TEXT_COLOR_HEADER = createColor(Color.web("#2E1F36"));
		COLOR_STRONG = createColor(Color.web("#FDB838"));
		COLOR_SATURATED = createColor(Color.web("#FFFFA3"));
		COLOR_POSITIVE = createColor(Color.web("#75CC6C"));
		COLOR_NEGATIVE = createColor(Color.web("#CC6E72"));
		TEXT_COLOR_TITLE = createColor(Color.web("#FFFFFF"));
		COLOR_GRADIENT_0 = createColor(Color.web("#FF0000"));
		COLOR_GRADIENT_50 = createColor(Color.web("#FFFF00"));
		COLOR_GRADIENT_100 = createColor(Color.web("#00CE29"));
	}
}

class FeinbergTheme extends Theme {

	public static final int UID = 9;

	public FeinbergTheme() {
		super("Feinberg", UID);
	}

	@Override
	protected void loadTheme() {
		loaded = true;
		COLOR_NEUTRAL = createColor(Color.web("#FFC7EF"));
		COLOR_STRONGEST = createColor(Color.web("#2F5D8A"));
		COLOR_EXIT_BUTTON_HOVER = createColor(Color.web("#F04747"));
		COLOR_DIVIDER = createColor(Color.web("#54688C"));
		COLOR_DIVIDER_DARK = createColor(Color.web("#375268"));
		COLOR_SLIGHTLY_STRONG = createColor(Color.web("#99CCFB"));
		COLOR_SLIGHTLY_WEAK = createColor(Color.web("#FFEBF8"));
		TEXT_COLOR_SLIGHTLY_WEAK = createColor(Color.web("#000000"));
		TEXT_COLOR_SLIGHTLY_STRONG = createColor(Color.web("#000000"));
		TEXT_COLOR_WEAK = createColor(Color.web("#D2EEFF"));
		TEXT_COLOR_NEUTRAL = createColor(Color.web("#000000"));
		TEXT_COLOR_HEADER = createColor(Color.web("#191919"));
		COLOR_STRONG = createColor(Color.web("#95C9F9"));
		COLOR_SATURATED = createColor(Color.web("#BFFFFF"));
		COLOR_POSITIVE = createColor(Color.web("#21E70B"));
		COLOR_NEGATIVE = createColor(Color.web("#FC000D"));
		TEXT_COLOR_TITLE = createColor(Color.web("#FFFFFF"));
		COLOR_GRADIENT_0 = createColor(Color.web("#FF0000"));
		COLOR_GRADIENT_50 = createColor(Color.web("#8E8E00"));
		COLOR_GRADIENT_100 = createColor(Color.web("#00CB28"));
	}
}

class DarklavenderTheme extends Theme {

	public static final int UID = 10;

	public DarklavenderTheme() {
		super("Dark lavender", UID);
	}

	@Override
	protected void loadTheme() {
		loaded = true;
		COLOR_NEUTRAL = createColor(Color.web("#474767"));
		COLOR_STRONGEST = createColor(Color.web("#3F3E5F"));
		COLOR_EXIT_BUTTON_HOVER = createColor(Color.web("#F04747"));
		COLOR_DIVIDER = createColor(Color.web("#3E3862"));
		COLOR_DIVIDER_DARK = createColor(Color.web("#34324A"));
		COLOR_SLIGHTLY_STRONG = createColor(Color.web("#585781"));
		COLOR_SLIGHTLY_WEAK = createColor(Color.web("#676297"));
		TEXT_COLOR_SLIGHTLY_WEAK = createColor(Color.web("#FFFFFF"));
		TEXT_COLOR_SLIGHTLY_STRONG = createColor(Color.web("#E5E5E5"));
		TEXT_COLOR_WEAK = createColor(Color.web("#AAAAF2"));
		TEXT_COLOR_NEUTRAL = createColor(Color.web("#C6C6FF"));
		TEXT_COLOR_HEADER = createColor(Color.web("#E5E5E5"));
		COLOR_STRONG = createColor(Color.web("#54547F"));
		COLOR_SATURATED = createColor(Color.web("#7EFFEA"));
		COLOR_POSITIVE = createColor(Color.web("#51E042"));
		COLOR_NEGATIVE = createColor(Color.web("#EB4E55"));
		TEXT_COLOR_TITLE = createColor(Color.web("#F9B4FF"));
		COLOR_GRADIENT_0 = createColor(Color.web("#FF0000"));
		COLOR_GRADIENT_50 = createColor(Color.web("#FFFF00"));
		COLOR_GRADIENT_100 = createColor(Color.web("#00CE29"));
	}
}
