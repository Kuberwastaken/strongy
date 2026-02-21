package strongy.io.api.queries;

public class PingQuery implements IQuery {

	public String get() {
		return "Strongy HTTP server is active!";
	}

	@Override
	public boolean supportsSubscriptions() {
		return false;
	}

}
