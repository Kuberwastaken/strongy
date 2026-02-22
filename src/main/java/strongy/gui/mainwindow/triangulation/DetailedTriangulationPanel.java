package strongy.gui.mainwindow.triangulation;

import javafx.scene.layout.VBox;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.model.datastate.calculator.ICalculatorResult;
import strongy.model.datastate.stronghold.ChunkPrediction;

public class DetailedTriangulationPanel extends ThemedPanel {
	private final VBox chunksBox;
	private final StyleManager styleManager;

	public DetailedTriangulationPanel(StyleManager styleManager) {
		super(styleManager);
		this.styleManager = styleManager;
		chunksBox = new VBox(2);
		getChildren().addAll(new ChunkPanelHeader(styleManager), chunksBox);
	}

	public void setResult(ICalculatorResult result) {
		chunksBox.getChildren().clear();
		if (result != null && result.success()) {
			for (ChunkPrediction p : result.getTopPredictions()) {
				chunksBox.getChildren().add(new ChunkPanel(styleManager, p));
			}
		}
	}
}