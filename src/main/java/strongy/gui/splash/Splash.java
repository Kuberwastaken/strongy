package strongy.gui.splash;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import strongy.gui.style.StyleManager;

// Simple splash stub 
public class Splash {
	private Stage stage;

	public Splash() {
		try {
			// Using Platform.runLater just in case this is called off-thread,
			// though StrongyApp.java doesn't explicitly use the old Splash anymore.
			javafx.application.Platform.runLater(() -> {
				stage = new Stage(StageStyle.UNDECORATED);
				VBox root = new VBox();
				root.getChildren().add(new Label("Loading Strongy..."));
				stage.setScene(new Scene(root, 300, 200));
				stage.show();
			});
		} catch (Exception ignored) {
		}
	}

	public void dispose() {
		if (stage != null) {
			javafx.application.Platform.runLater(() -> stage.close());
		}
	}

	public void setProgress(float p) {
	}
}