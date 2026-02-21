package strongy.gui.options.sections;

import javax.swing.border.EmptyBorder;

import strongy.gui.components.layout.StackPanel;
import strongy.gui.components.layout.TitledDivider;
import strongy.gui.components.preferences.CheckboxPanel;
import strongy.gui.frames.OptionsFrame;
import strongy.gui.style.StyleManager;
import strongy.io.preferences.StrongyPreferences;
import strongy.util.I18n;

public class GeneralOptionalFeaturesOptionsPanel extends StackPanel {

	public GeneralOptionalFeaturesOptionsPanel(StyleManager styleManager, StrongyPreferences preferences) {
		super(3 * OptionsFrame.PADDING);
		setOpaque(false);
		setBorder(new EmptyBorder(2 * OptionsFrame.PADDING, 2 * OptionsFrame.PADDING, 2 * OptionsFrame.PADDING, 2 * OptionsFrame.PADDING));

		add(new CheckboxPanel(styleManager, I18n.get("settings.show_angle_updates"), preferences.showAngleUpdates));
		add(new TitledDivider(styleManager, "Information messages"));
		add(new CheckboxPanel(styleManager, I18n.get("settings.information.enable_mismeasure_warning"), preferences.informationMismeasureEnabled));
		add(new CheckboxPanel(styleManager, I18n.get("settings.information.enable_portal_linking_warning"), preferences.informationPortalLinkingEnabled));
		add(new CheckboxPanel(styleManager, I18n.get("settings.information.enable_combined_certainty_information"), preferences.informationCombinedCertaintyEnabled));
		add(new CheckboxPanel(styleManager, I18n.get("settings.information.enable_direction_help_information"), preferences.informationDirectionHelpEnabled));
	}

}
