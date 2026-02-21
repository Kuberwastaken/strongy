package strongy.io.preferences.enums;

import strongy.io.preferences.IMultipleChoicePreferenceDataType;
import strongy.util.I18n;

public enum AngleAdjustmentDisplayType implements IMultipleChoicePreferenceDataType {

	ANGLE_CHANGE(I18n.get("settings.angle_adjustment.angle_change")),
	INCREMENTS(I18n.get("settings.angle_adjustment.increments"));

	final String name;

	AngleAdjustmentDisplayType(String string) {
		name = string;
	}

	@Override
	public String choiceName() {
		return name;
	}
}