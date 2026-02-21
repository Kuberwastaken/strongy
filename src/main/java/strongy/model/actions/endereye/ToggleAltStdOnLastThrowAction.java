package strongy.model.actions.endereye;

import strongy.io.preferences.StrongyPreferences;
import strongy.model.actions.IAction;
import strongy.model.datastate.IDataState;
import strongy.model.datastate.endereye.IEnderEyeThrow;
import strongy.model.domainmodel.IListComponent;

public class ToggleAltStdOnLastThrowAction implements IAction {

	private final IDataState dataState;
	private final StrongyPreferences preferences;

	public ToggleAltStdOnLastThrowAction(IDataState dataState, StrongyPreferences preferences) {
		this.dataState = dataState;
		this.preferences = preferences;
	}

	@Override
	public void execute() {
		if (dataState.locked().get() || dataState.getThrowList().size() == 0)
			return;

		if (!preferences.useAltStd.get())
			return;

		IListComponent<IEnderEyeThrow> throwList = dataState.getThrowList();
		IEnderEyeThrow lastThrow = throwList.get(throwList.size() - 1);
		IEnderEyeThrow newThrow = lastThrow.withToggledAltStd();
		if (lastThrow == newThrow)
			return;

		throwList.replace(lastThrow, newThrow);
	}

}
