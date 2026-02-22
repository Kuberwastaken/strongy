package strongy.gui.mainwindow.boateye;

import javafx.scene.layout.VBox;
import strongy.gui.components.labels.ThemedLabel;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;

// Placeholder conversion. Will need proper JavaFX Canvas/ImageView logic.
public class BoatIcon extends ThemedPanel {
	public BoatIcon(StyleManager styleManager, strongy.model.datastate.highprecision.BoatState state) {
		super(styleManager);
		getChildren().add(new ThemedLabel(styleManager, "Boat"));
	}
}
