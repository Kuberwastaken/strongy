package strongy.gui.options.sections;

import java.awt.FlowLayout;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import strongy.gui.components.preferences.RadioButtonPanel;
import strongy.gui.frames.OptionsFrame;
import strongy.gui.options.LanguageOption;
import strongy.gui.style.StyleManager;
import strongy.io.LanguageResources;
import strongy.io.preferences.StrongyPreferences;
import strongy.util.I18n;

public class LanguageOptionsPanel extends JPanel {

	public LanguageOptionsPanel(StyleManager styleManager, StrongyPreferences preferences) {
		setOpaque(false);
		setAlignmentX(0);
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		setBorder(new EmptyBorder(2 * OptionsFrame.PADDING, 2 * OptionsFrame.PADDING, 2 * OptionsFrame.PADDING, 2 * OptionsFrame.PADDING));
		LanguageOption[] languageOptions = createLanguageOptions();
		add(new RadioButtonPanel(styleManager, I18n.get("settings.language.hint"),
				languageOptions,
				getChosenLanguageOption(preferences.language.get(), languageOptions),
				languageOption -> preferences.language.set(languageOption.locale.toLanguageTag())));
	}

	private LanguageOption[] createLanguageOptions() {
		List<Locale> locales = LanguageResources.getSupportedLocales();
		LanguageOption[] languageOptions = new LanguageOption[locales.size()];
		for (int i = 0; i < languageOptions.length; i++) {
			languageOptions[i] = new LanguageOption(locales.get(i));
		}
		return languageOptions;
	}

	private LanguageOption getChosenLanguageOption(String chosenLanguageTag, LanguageOption[] languageOptions) {
		return Arrays.stream(languageOptions)
				.filter(languageOption -> languageOption.locale.toLanguageTag().equals(chosenLanguageTag))
				.findFirst()
				.orElseThrow(() -> new RuntimeException(String.format("Language %s was not found in language options.", chosenLanguageTag)));
	}

}
