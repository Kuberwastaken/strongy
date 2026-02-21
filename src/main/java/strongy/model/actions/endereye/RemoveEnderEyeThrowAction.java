package strongy.model.actions.endereye;

import strongy.model.actions.IAction;
import strongy.model.datastate.IDataState;
import strongy.model.datastate.endereye.IEnderEyeThrow;

public class RemoveEnderEyeThrowAction implements IAction {

	private final IDataState dataState;
	private final IEnderEyeThrow throwToRemove;

	public RemoveEnderEyeThrowAction(IDataState dataState, IEnderEyeThrow throwToRemove) {
		this.dataState = dataState;
		this.throwToRemove = throwToRemove;
	}

	@Override
	public void execute() {
		dataState.getThrowList().remove(throwToRemove);
	}

}
