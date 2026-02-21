package strongy.io.api.queries;

import strongy.model.datastate.IDataState;

public interface IQuery {

	String get();

	boolean supportsSubscriptions();

}
