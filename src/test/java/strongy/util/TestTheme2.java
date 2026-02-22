package strongy.util;

import javafx.scene.paint.Color;

import strongy.gui.style.theme.Theme;

public class TestTheme2 extends Theme {

	public static final int UID = 1001;

	public TestTheme2() {
		super("Test theme", UID);
		loadTheme();
	}

	@Override
	protected void loadTheme() {
		COLOR_NEUTRAL = createColor(Color.web("#000000"));
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