package strongy.model.actions.common;

import strongy.model.actions.IAction;
import strongy.model.datastate.divine.Fossil;
import strongy.model.datastate.divine.IDivineContext;

public class SetFossilAction implements IAction {

	private final IDivineContext divineContext;
	private final Fossil fossil;

	public SetFossilAction(IDivineContext divineContext, Fossil fossil) {
		this.divineContext = divineContext;
		this.fossil = fossil;
	}

	@Override
	public void execute() {
		divineContext.fossil().set(fossil);
	}

}
