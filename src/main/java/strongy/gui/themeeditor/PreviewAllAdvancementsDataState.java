package strongy.gui.themeeditor;

import strongy.model.datastate.alladvancements.AllAdvancementsStructureType;
import strongy.model.datastate.alladvancements.IAllAdvancementsDataState;
import strongy.model.datastate.alladvancements.IAllAdvancementsPosition;
import strongy.model.datastate.common.DetachedDomainModel;
import strongy.model.datastate.common.StructureInformation;
import strongy.model.domainmodel.DataComponent;
import strongy.model.domainmodel.IDataComponent;
import strongy.model.domainmodel.IDomainModel;
import strongy.model.domainmodel.IDomainModelComponent;
import strongy.model.domainmodel.InferredComponent;

public class PreviewAllAdvancementsDataState implements IAllAdvancementsDataState {

	IDomainModel domainModel = new DetachedDomainModel();

	@Override
	public IDomainModelComponent<Boolean> allAdvancementsModeEnabled() {
		return new DataComponent<>("", domainModel, false);
	}

	@Override
	public IDataComponent<Boolean> hasEnteredEnd() {
		return new DataComponent<>("", domainModel);
	}

	@Override
	public IDataComponent<IAllAdvancementsPosition> getAllAdvancementsPosition(AllAdvancementsStructureType allAdvancementsStructureType) {
		return new DataComponent<>("", domainModel);
	}

	@Override
	public IDomainModelComponent<StructureInformation> getStructureInformation(AllAdvancementsStructureType allAdvancementsStructureType) {
		return new InferredComponent<>(null);
	}
}
