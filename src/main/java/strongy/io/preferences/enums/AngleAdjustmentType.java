package strongy.io.preferences.enums;

import strongy.io.preferences.IMultipleChoicePreferenceDataType;
import strongy.util.I18n;

public enum AngleAdjustmentType implements IMultipleChoicePreferenceDataType {

	SUBPIXEL(I18n.get("settings.angle_adjustment.subpixel")),
	TALL(I18n.get("settings.angle_adjustment.tall_resolution")),
	CUSTOM(I18n.get("settings.angle_adjustment.custom_adjustment"));

	final String name;

	AngleAdjustmentType(String string) {
		name = string;
	}

	@Override
	public String choiceName() {
		return name;
	}
}