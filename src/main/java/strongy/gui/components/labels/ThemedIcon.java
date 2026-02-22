package strongy.gui.components.labels;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import strongy.Main;
import strongy.gui.components.ThemedComponent;
import strongy.gui.style.StyleManager;

import java.io.InputStream;

/**
 * A themed icon label — displays an image using JavaFX ImageView.
 */
public class ThemedIcon extends Label implements ThemedComponent {

	private final ImageView imageView;

	public ThemedIcon(StyleManager styleManager, String resourcePath) {
		imageView = new ImageView();
		try {
			InputStream is = Main.class.getResourceAsStream("/" + resourcePath);
			if (is != null) {
				Image image = new Image(is);
				imageView.setImage(image);
				imageView.setFitWidth(16);
				imageView.setFitHeight(16);
				imageView.setPreserveRatio(true);
			}
		} catch (Exception ignored) {}
		setGraphic(imageView);
	}

	public void setIconSize(double size) {
		imageView.setFitWidth(size);
		imageView.setFitHeight(size);
	}
}
