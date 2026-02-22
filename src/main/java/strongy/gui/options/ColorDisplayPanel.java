package strongy.gui.options;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.gui.style.theme.WrappedColor;

public class ColorDisplayPanel extends ThemedPanel {
	public ColorDisplayPanel(StyleManager styleManager, WrappedColor color, String name) {
		super(styleManager);
		Label l = new Label(name);
		color.whenColorChanged().subscribe(c -> {
			javafx.application.Platform.runLater(() -> {
				setStyle(String.format("-fx-background-color: #%02X%02X%02X;",
						(int) (c.getRed() * 255), (int) (c.getGreen() * 255), (int) (c.getBlue() * 255)));
			});
		});
		getChildren().add(l);
	}
}