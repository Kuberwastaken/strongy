package strongy.gui.frames;

import javafx.stage.Stage;
import strongy.gui.style.StyleManager;
import strongy.io.preferences.StrongyPreferences;

public class NotificationsFrame extends ThemedFrame {
	public NotificationsFrame(StyleManager styleManager, StrongyPreferences preferences) {
		super(styleManager, preferences, "Notifications");
	}

	@Override
	protected void onExitButtonClicked() {
		setVisible(false);
	}
}
