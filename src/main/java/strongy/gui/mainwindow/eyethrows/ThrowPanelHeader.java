package strongy.gui.mainwindow.eyethrows;

import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.util.I18n;

public class ThrowPanelHeader extends ThemedPanel {
	public ThrowPanelHeader(StyleManager styleManager) {
		super(styleManager, true); // use header bg
		HBox layout = new HBox(8);
		Label x = new Label("X");
		Label z = new Label("Z");
		Label alpha = new Label(I18n.get("angle"));
		layout.getChildren().addAll(x, z, alpha);
		getChildren().add(layout);
	}
}
