package strongy.io.preferences.enums;

import strongy.io.preferences.IMultipleChoicePreferenceDataType;
import strongy.util.I18n;

public enum StrongholdDisplayType implements IMultipleChoicePreferenceDataType {

	FOURFOUR("(4, 4)"), EIGHTEIGHT("(8, 8)"), CHUNK(I18n.get("chunk"));

	final String name;

	StrongholdDisplayType(String string) {
		name = string;
	}

	@Override
	public String choiceName() {
		return name;
	}
}