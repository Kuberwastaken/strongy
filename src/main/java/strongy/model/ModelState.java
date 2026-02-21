package strongy.model;

import strongy.event.DisposeHandler;
import strongy.event.IDisposable;
import strongy.io.preferences.StrongyPreferences;
import strongy.model.actions.ActionExecutor;
import strongy.model.actions.IActionExecutor;
import strongy.model.datastate.DataState;
import strongy.model.datastate.IDataState;
import strongy.model.domainmodel.DomainModel;
import strongy.model.domainmodel.IDomainModel;
import strongy.model.environmentstate.EnvironmentState;
import strongy.model.environmentstate.IEnvironmentState;

public class ModelState implements IDisposable {

	public final IDomainModel domainModel;
	public final IActionExecutor actionExecutor;
	public final IEnvironmentState environmentState;
	public final IDataState dataState;

	private final DisposeHandler disposeHandler = new DisposeHandler();

	public ModelState(StrongyPreferences preferences) {
		DomainModel domainModel = disposeHandler.add(new DomainModel());
		this.domainModel = domainModel;
		actionExecutor = new ActionExecutor(domainModel);
		environmentState = disposeHandler.add(new EnvironmentState(domainModel, preferences));
		dataState = disposeHandler.add(new DataState(domainModel, environmentState, preferences.defaultBoatType.get()));
		domainModel.finishInitialization();
	}

	@Override
	public void dispose() {
		disposeHandler.dispose();
	}
}
