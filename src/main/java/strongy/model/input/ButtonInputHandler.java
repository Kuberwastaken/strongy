package strongy.model.input;

import strongy.model.actions.IActionExecutor;
import strongy.model.actions.alladvancements.RemoveStructureAction;
import strongy.model.actions.common.ResetAction;
import strongy.model.actions.common.SetFossilAction;
import strongy.model.actions.endereye.RemoveEnderEyeThrowAction;
import strongy.model.datastate.IDataState;
import strongy.model.datastate.common.StructureInformation;
import strongy.model.datastate.endereye.IEnderEyeThrow;
import strongy.model.domainmodel.IDomainModel;

public class ButtonInputHandler implements IButtonInputHandler {

	private final IDomainModel domainModel;
	private final IDataState dataState;
	private final IActionExecutor actionExecutor;

	public ButtonInputHandler(IDomainModel domainModel, IDataState dataState, IActionExecutor actionExecutor) {
		this.domainModel = domainModel;
		this.dataState = dataState;
		this.actionExecutor = actionExecutor;
	}

	@Override
	public void onResetButtonPressed() {
		actionExecutor.executeImmediately(new ResetAction(domainModel));
	}

	@Override
	public void onUndoButtonPressed() {
		domainModel.undoUnderWriteLock();
	}

	@Override
	public void onRedoButtonPressed() {
		domainModel.redoUnderWriteLock();
	}

	@Override
	public void onRemoveFossilButtonPressed() {
		actionExecutor.executeImmediately(new SetFossilAction(dataState.getDivineContext(), null));
	}

	@Override
	public void onRemoveThrowButtonPressed(IEnderEyeThrow throwToRemove) {
		actionExecutor.executeImmediately(new RemoveEnderEyeThrowAction(dataState, throwToRemove));
	}

	@Override
	public void onRemoveAllAdvancementsStructureButtonPressed(StructureInformation structureInformation) {
		actionExecutor.executeImmediately(new RemoveStructureAction(dataState.allAdvancementsDataState(), structureInformation));
	}

}
