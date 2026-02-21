package strongy.io.api;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;
import strongy.event.DisposeHandler;
import strongy.event.IDisposable;
import strongy.io.preferences.StrongyPreferences;
import strongy.model.datastate.IDataState;
import strongy.model.domainmodel.IDomainModel;
import strongy.model.information.InformationMessageList;
import strongy.util.Logger;

public class StrongyHttpServer implements IDisposable {

	private final IDataState dataState;
	private final IDomainModel domainModel;
	private final InformationMessageList informationMessageList;
	private final StrongyPreferences preferences;
	private final DisposeHandler disposeHandler = new DisposeHandler();

	private HttpServer httpServer;
	private ApiV1HttpHandler apiV1HttpHandler;
	private ExecutorService executorService;
	private Exception error;

	public StrongyHttpServer(IDataState dataState, IDomainModel domainModel, InformationMessageList informationMessageList, StrongyPreferences preferences) {
		this.dataState = dataState;
		this.domainModel = domainModel;
		this.informationMessageList = informationMessageList;
		this.preferences = preferences;
		updateHttpServerStatus();

		disposeHandler.add(preferences.enableHttpServer.whenModified().subscribe(this::updateHttpServerStatus));
	}

	private void updateHttpServerStatus() {
		if (preferences.enableHttpServer.get()) {
			if (httpServer == null)
				startHttpServer(52533);
		} else {
			if (httpServer != null)
				stopHttpServer();
		}
	}

	private void startHttpServer(int port) {
		error = null;
		try {
			httpServer = HttpServer.create(new InetSocketAddress(port), 0);
		} catch (IOException e) {
			Logger.log(e.toString());
			error = e;
			return;
		}
		if (executorService == null)
			executorService = Executors.newFixedThreadPool(1);
		apiV1HttpHandler = new ApiV1HttpHandler(dataState, domainModel, informationMessageList, executorService);
		httpServer.createContext("/api/v1", apiV1HttpHandler);
		httpServer.setExecutor(executorService);
		httpServer.start();
		Logger.log("HTTP server started on port " + port);
	}

	private void stopHttpServer() {
		apiV1HttpHandler.dispose();
		apiV1HttpHandler = null;
		httpServer.stop(0);
		httpServer = null;
		Logger.log("HTTP server stopped");
	}

	@Override
	public void dispose() {
		if (httpServer != null) {
			httpServer.stop(0);
		}
		if (apiV1HttpHandler != null) {
			apiV1HttpHandler.dispose();
		}
		disposeHandler.dispose();
	}
}

