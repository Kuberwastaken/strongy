package strongy.gui.mainwindow.main;

import javafx.scene.layout.VBox;
import strongy.gui.components.labels.ThemedLabel;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;

public class BlindPanel extends ThemedPanel {
	public BlindPanel(StyleManager styleManager) {
		super(styleManager);
		getChildren().add(new ThemedLabel(styleManager, "Blind Calculation"));
	}

	public void updateResult(strongy.model.datastate.calculator.ICalculatorResult result) {
		// Stub
	}
}