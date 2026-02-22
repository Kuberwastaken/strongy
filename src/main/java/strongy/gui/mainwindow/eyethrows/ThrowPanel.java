package strongy.gui.mainwindow.eyethrows;

import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.model.datastate.endereye.IEnderEyeThrow;

public class ThrowPanel extends ThemedPanel {

	public final Label x, z, alpha;

	public ThrowPanel(StyleManager styleManager, IEnderEyeThrow t) {
		super(styleManager);
		HBox layout = new HBox(8);
		x = new Label(String.format("%.1f", t.xInOverworld()));
		z = new Label(String.format("%.1f", t.zInOverworld()));
		alpha = new Label(String.format("%.2f", t.horizontalAngle()));
		layout.getChildren().addAll(x, z, alpha);
		getChildren().add(layout);
	}
}
