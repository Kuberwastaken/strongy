package strongy.gui.mainwindow.triangulation;

import javafx.scene.layout.HBox;
import strongy.gui.components.labels.ThemedLabel;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.util.I18n;

public class ChunkPanelHeader extends ThemedPanel {
	public ChunkPanelHeader(StyleManager styleManager) {
		super(styleManager, true);
		HBox layout = new HBox(8);
		layout.getChildren().add(new ThemedLabel(styleManager, I18n.get("location_x_z")));
		getChildren().add(layout);
	}
}
