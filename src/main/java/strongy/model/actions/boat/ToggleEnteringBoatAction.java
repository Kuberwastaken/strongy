package strongy.model.actions.boat;

import strongy.model.actions.IAction;
import strongy.model.datastate.IDataState;
import strongy.model.datastate.highprecision.BoatState;
import strongy.model.datastate.highprecision.IBoatDataState;

public class ToggleEnteringBoatAction implements IAction {

	private final IDataState dataState;

	public ToggleEnteringBoatAction(IDataState dataState) {
		this.dataState = dataState;
	}

	@Override
	public void execute() {
		if (dataState.locked().get())
			return;

		IBoatDataState boatDataState = dataState.boatDataState();
		boatDataState.enteringBoat().set(!boatDataState.enteringBoat().get());
		boatDataState.boatState().set(boatDataState.enteringBoat().get() ? BoatState.MEASURING : BoatState.NONE);
		boatDataState.boatAngle().set(null);
	}
}
