package strongy.gui.mainwindow.main;

import javafx.scene.layout.VBox;
import strongy.gui.components.labels.ThemedLabel;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;

public class DivinePanel extends ThemedPanel {
	public DivinePanel(StyleManager styleManager) {
		super(styleManager);
		getChildren().add(new ThemedLabel(styleManager, "Divine Calculation"));
	}

	public void updateResult(strongy.model.datastate.calculator.ICalculatorResult result) {
		// Stub
	}
}