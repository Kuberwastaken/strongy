package strongy;

import javafx.application.Application;
import strongy.io.ErrorHandler;

public class Main {

	public static final String VERSION = "2.0.0";

	public static void main(String[] args) {
		ErrorHandler errorHandler = new ErrorHandler();
		try {
			Application.launch(StrongyApp.class, args);
		} catch (Exception e) {
			errorHandler.handleStartupException(e);
		}
	}
}
