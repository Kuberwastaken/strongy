package strongy.gui.components.layout;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import strongy.gui.components.labels.ThemedLabel;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;

public class TitledDivider extends ThemedPanel {

	public TitledDivider(StyleManager styleManager, String title) {
		super(styleManager);
		javafx.scene.layout.HBox layout = new javafx.scene.layout.HBox(5);
		layout.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

		ThemedLabel label = new ThemedLabel(styleManager, title);
		Divider divider = new Divider(styleManager);
		javafx.scene.layout.HBox.setHgrow(divider, javafx.scene.layout.Priority.ALWAYS);

		layout.getChildren().addAll(label, divider);
		getChildren().add(layout);
	}

}
