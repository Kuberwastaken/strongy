package strongy.model.actions.common;

import strongy.model.actions.IAction;
import strongy.model.datastate.IDataState;

public class SetLockedAction implements IAction {

	private final IDataState dataState;
	private final boolean locked;

	public SetLockedAction(IDataState dataState, boolean locked) {
		this.dataState = dataState;
		this.locked = locked;
	}

	@Override
	public void execute() {
		dataState.locked().set(locked);
	}

}
