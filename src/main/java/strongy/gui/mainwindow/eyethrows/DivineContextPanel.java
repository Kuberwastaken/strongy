package strongy.gui.mainwindow.eyethrows;

import javafx.scene.layout.VBox;
import strongy.gui.components.labels.ThemedLabel;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.model.datastate.divine.IDivineContext;

public class DivineContextPanel extends ThemedPanel {
	private final ThemedLabel label;

	public DivineContextPanel(StyleManager styleManager) {
		super(styleManager);
		label = new ThemedLabel(styleManager, "Divine: None");
		getChildren().add(label);
	}

	public void updateContext(IDivineContext context) {
		if (context != null && context.getFossil() != null) {
			label.setText("Fossil: " + context.getFossil().x);
		} else {
			label.setText("Divine: None");
		}
	}
}
