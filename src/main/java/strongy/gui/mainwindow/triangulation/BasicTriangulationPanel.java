package strongy.gui.mainwindow.triangulation;

import javafx.scene.layout.VBox;
import strongy.gui.components.labels.ThemedLabel;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.model.datastate.calculator.ICalculatorResult;
import strongy.util.I18n;

public class BasicTriangulationPanel extends ThemedPanel {

	private final ThemedLabel resultLabel;

	public BasicTriangulationPanel(StyleManager styleManager) {
		super(styleManager);
		resultLabel = new ThemedLabel(styleManager, I18n.get("waiting"));
		getChildren().add(resultLabel);
	}

	public void setResult(ICalculatorResult result) {
		if (result != null && result.success()) {
			var best = result.getBestPrediction();
			if (best != null) {
				resultLabel.setText(String.format("Location: (%d, %d)", best.chunk.x * 16 + 8, best.chunk.z * 16 + 8));
				return;
			}
		}
		resultLabel.setText(I18n.get("waiting"));
	}
}