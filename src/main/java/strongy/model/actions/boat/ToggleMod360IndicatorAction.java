package strongy.model.actions.boat;

import strongy.model.actions.IAction;
import strongy.model.datastate.IDataState;
import strongy.model.datastate.highprecision.IBoatDataState;

public class ToggleMod360IndicatorAction implements IAction {

	private final IDataState dataState;

	public ToggleMod360IndicatorAction(IDataState dataState) {
		this.dataState = dataState;
	}

	@Override
	public void execute() {
		if (dataState.locked().get())
			return;

		IBoatDataState boatDataState = dataState.boatDataState();
		boatDataState.reducingModulo360().set(!boatDataState.reducingModulo360().get());
	}
}
