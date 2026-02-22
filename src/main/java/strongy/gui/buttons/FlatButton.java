package strongy.gui.buttons;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import strongy.Main;
import strongy.gui.components.ThemedComponent;
import strongy.gui.style.StyleManager;
import strongy.gui.style.theme.WrappedColor;

import java.io.InputStream;

public class FlatButton extends Button implements ThemedComponent {

	public FlatButton(StyleManager styleManager, String text) {
		super(text);
		getStyleClass().add("flat-button");
	}

	public FlatButton(StyleManager styleManager, String text, String iconPath) {
		super(text);
		getStyleClass().add("flat-button");
		try {
			InputStream is = Main.class.getResourceAsStream(iconPath);
			if (is != null) {
				ImageView iv = new ImageView(new Image(is));
				iv.setFitWidth(16);
				iv.setFitHeight(16);
				setGraphic(iv);
			}
		} catch (Exception ignored) {
		}
	}

	public void setHoverColor(WrappedColor color) {
		// Custom hover colors handled via CSS classes globally in JavaFX,
		// but this method stub is needed for source compatibility
	}

	public void setBackgroundColor(WrappedColor color) {
		if (color != null && color.color() != null) {
			setStyle("-fx-background-color: " + color.hex() + ";");
		}
	}
}
