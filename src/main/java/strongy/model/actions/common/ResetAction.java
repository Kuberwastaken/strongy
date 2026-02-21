package strongy.model.actions.common;

import strongy.model.actions.IAction;
import strongy.model.domainmodel.IDomainModel;

public class ResetAction implements IAction {

	private final IDomainModel domainModel;

	public ResetAction(IDomainModel domainModel) {
		this.domainModel = domainModel;
	}

	@Override
	public void execute() {
		domainModel.reset();
	}
}
