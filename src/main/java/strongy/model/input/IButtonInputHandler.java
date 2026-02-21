package strongy.model.input;

import strongy.model.datastate.common.StructureInformation;
import strongy.model.datastate.endereye.IEnderEyeThrow;

public interface IButtonInputHandler {

	void onResetButtonPressed();

	void onUndoButtonPressed();

	void onRedoButtonPressed();

	void onRemoveFossilButtonPressed();

	void onRemoveThrowButtonPressed(IEnderEyeThrow throwToRemove);

	void onRemoveAllAdvancementsStructureButtonPressed(StructureInformation structureInformation);

}
