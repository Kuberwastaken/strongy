package strongy.model.actions.boat;

import strongy.model.actions.IAction;
import strongy.model.datastate.IDataState;
import strongy.model.datastate.highprecision.IBoatDataState;

public class ResetBoatStateAction implements IAction {

	private final IDataState dataState;

	public ResetBoatStateAction(IDataState dataState) {
		this.dataState = dataState;
	}

	@Override
	public void execute() {
		if (dataState.locked().get())
			return;

		IBoatDataState boatDataState = dataState.boatDataState();
		boatDataState.enteringBoat().reset();
		boatDataState.reducingModulo360().reset();
		boatDataState.boatState().reset();
		boatDataState.boatAngle().reset();
	}
}
