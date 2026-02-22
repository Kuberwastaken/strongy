package strongy.gui.mainwindow.information;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import strongy.Main;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.model.information.InformationMessage;
import strongy.model.information.InformationMessageSeverity;
import java.io.InputStream;

public class InformationTextPanel extends HBox {

	public InformationTextPanel(StyleManager styleManager, InformationMessage message) {
		getStyleClass().add("info-panel");
		setSpacing(6);
		setAlignment(Pos.CENTER_LEFT);

		String iconPath = getIconPath(message.severity);
		if (iconPath != null) {
			try {
				InputStream is = Main.class.getResourceAsStream(iconPath);
				if (is != null) {
					ImageView icon = new ImageView(new Image(is));
					icon.setFitWidth(16);
					icon.setFitHeight(16);
					getChildren().add(icon);
				}
			} catch (Exception ignored) {
			}
		}

		Label textLabel = new Label(message.message);
		textLabel.getStyleClass().add("info-label");
		textLabel.setWrapText(true);
		getChildren().add(textLabel);
	}

	private String getIconPath(InformationMessageSeverity severity) {
		switch (severity) {
			case INFO:
				return "/info_icon.png";
			case WARNING:
				return "/warning_icon.png";
			case ERROR:
				return "/warning_icon.png"; // Needs an error icon
			default:
				return null;
		}
	}
}
