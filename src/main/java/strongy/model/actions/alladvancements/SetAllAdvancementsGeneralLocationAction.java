package strongy.model.actions.alladvancements;

import strongy.model.actions.IAction;
import strongy.model.datastate.alladvancements.AllAdvancementsPosition;
import strongy.model.datastate.alladvancements.AllAdvancementsStructureType;
import strongy.model.datastate.alladvancements.IAllAdvancementsDataState;
import strongy.model.datastate.endereye.F3IData;

public class SetAllAdvancementsGeneralLocationAction implements IAction {

	private final IAllAdvancementsDataState allAdvancementsDataState;
	private final AllAdvancementsPosition allAdvancementsPosition;

	public SetAllAdvancementsGeneralLocationAction(IAllAdvancementsDataState allAdvancementsDataState, F3IData f3iData) {
		this.allAdvancementsDataState = allAdvancementsDataState;
		this.allAdvancementsPosition = new AllAdvancementsPosition(f3iData.x, f3iData.z);
	}

	@Override
	public void execute() {
		allAdvancementsDataState.getAllAdvancementsPosition(AllAdvancementsStructureType.GeneralLocation).set(allAdvancementsPosition);
	}
}
