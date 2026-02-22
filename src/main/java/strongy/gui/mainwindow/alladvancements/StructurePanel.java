package strongy.gui.mainwindow.alladvancements;

import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.model.datastate.alladvancements.AllAdvancementsStructureType;

public class StructurePanel extends ThemedPanel {
	private Label locationLabel;

	public StructurePanel(StyleManager styleManager, AllAdvancementsStructureType str) {
		super(styleManager);
		HBox layout = new HBox(8);
		layout.getChildren().add(new Label(str.name()));
		locationLabel = new Label();
		layout.getChildren().add(locationLabel);
		getChildren().add(layout);
	}

	public String getLocationText() {
		return locationLabel.getText();
	}
}
