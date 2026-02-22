package strongy.gui.mainwindow.alladvancements;

import javafx.scene.layout.VBox;
import strongy.gui.components.labels.ThemedLabel;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;

public class AllAdvancementsPanel extends ThemedPanel {
	public AllAdvancementsPanel(StyleManager styleManager) {
		super(styleManager);
		getChildren().add(new ThemedLabel(styleManager, "All Advancements (WIP)"));
	}
}
