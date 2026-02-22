package strongy.gui.buttons;

import javafx.application.Platform;
import javafx.scene.control.Tooltip;
import strongy.gui.style.StyleManager;
import strongy.io.updatechecker.IUpdateChecker;
import java.awt.Desktop;
import java.net.URI;
import strongy.util.I18n;

public class NotificationsButton extends FlatButton {

	private final IUpdateChecker updateChecker;

	public NotificationsButton(StyleManager styleManager, IUpdateChecker updateChecker) {
		super(styleManager, "", "/notifications_icon.png");
		this.updateChecker = updateChecker;
		getStyleClass().add("title-bar-button");
		setVisible(false);

		if (updateChecker != null) {
			updateChecker.check(url -> {
				Platform.runLater(this::showNotification);
			});
		}

		setOnAction(e -> {
			try {
				if (updateChecker != null) {
					Desktop.getDesktop().browse(new URI("https://github.com/Kuberwastaken/strongy/releases"));
				}
			} catch (Exception ignored) {
			}
		});
	}

	private void showNotification() {
		setVisible(true);
		Tooltip t = new Tooltip(I18n.get("update_available"));
		t.getStyleClass().add("tooltip");
		setTooltip(t);
	}
}
