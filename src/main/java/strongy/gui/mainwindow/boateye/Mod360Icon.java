package strongy.gui.mainwindow.boateye;

import javafx.scene.layout.VBox;
import strongy.gui.components.labels.ThemedLabel;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;

// Placeholder conversion. Will need proper JavaFX logic.
public class Mod360Icon extends ThemedPanel {
	public Mod360Icon(StyleManager styleManager, boolean enabled) {
		super(styleManager);
		getChildren().add(new ThemedLabel(styleManager, "Mod"));
	}
}
