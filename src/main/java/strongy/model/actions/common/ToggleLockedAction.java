package strongy.model.actions.common;

import strongy.model.actions.IAction;
import strongy.model.datastate.IDataState;
import strongy.model.datastate.common.ResultType;

public class ToggleLockedAction implements IAction {

	private final IDataState dataState;

	public ToggleLockedAction(IDataState dataState) {
		this.dataState = dataState;
	}

	@Override
	public void execute() {
		if (dataState.resultType().get() == ResultType.NONE && !dataState.locked().get())
			return;
		dataState.locked().set(!dataState.locked().get());
	}

}
