package strongy.gui.mainwindow.triangulation;

import javafx.scene.layout.HBox;
import strongy.gui.components.labels.ThemedLabel;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.model.datastate.stronghold.ChunkPrediction;

public class ChunkPanel extends ThemedPanel {
	public ChunkPanel(StyleManager styleManager, ChunkPrediction p) {
		super(styleManager);
		HBox layout = new HBox(8);
		layout.getChildren().add(new ThemedLabel(styleManager, p.chunk.x + ", " + p.chunk.z));
		getChildren().add(layout);
	}
}
