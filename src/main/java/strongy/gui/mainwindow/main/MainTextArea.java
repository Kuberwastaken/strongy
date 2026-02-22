package strongy.gui.mainwindow.main;

import strongy.gui.components.labels.ThemedTextArea;
import strongy.gui.style.StyleManager;

public class MainTextArea extends ThemedTextArea {
	public MainTextArea(StyleManager styleManager) {
		super(styleManager);
	}

	public void updateResult(strongy.model.datastate.calculator.ICalculatorResult result) {
		if (result != null && result.success()) {
			var best = result.getBestPrediction();
			if (best != null) {
				setText("Stronghold at: " + best.chunk.x + ", " + best.chunk.z);
				return;
			}
		}
		setText("Waiting for throws...");
	}
}
