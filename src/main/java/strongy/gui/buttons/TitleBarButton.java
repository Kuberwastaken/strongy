package strongy.gui.buttons;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import strongy.Main;
import strongy.gui.style.StyleManager;

import java.io.InputStream;

public class TitleBarButton extends FlatButton {

	public TitleBarButton(StyleManager styleManager, String iconPath) {
		super(styleManager, "", iconPath);
		getStyleClass().add("title-bar-button");
	}

	// Constructor for JavaFX images
	public TitleBarButton(StyleManager styleManager, Image image) {
		super(styleManager, "");
		getStyleClass().add("title-bar-button");
		ImageView iv = new ImageView(image);
		iv.setFitWidth(16);
		iv.setFitHeight(16);
		setGraphic(iv);
	}
}
