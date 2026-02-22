package strongy.gui.mainwindow.main;

import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.model.input.IButtonInputHandler;
import strongy.util.I18n;

public class MainButtonPanel extends ThemedPanel {

	public MainButtonPanel(StyleManager styleManager, strongy.gui.frames.StrongyFrame frame,
			IButtonInputHandler buttonInputHandler) {
		super(styleManager);
		HBox layout = new HBox(8);

		Button btnUndo = new Button(I18n.get("undo"));
		btnUndo.getStyleClass().add("flat-button");
		btnUndo.setOnAction(e -> buttonInputHandler.onUndoButtonPressed());

		Button btnReset = new Button(I18n.get("reset"));
		btnReset.getStyleClass().add("flat-button");
		btnReset.setOnAction(e -> buttonInputHandler.onResetButtonPressed());

		layout.getChildren().addAll(btnUndo, btnReset);
		getChildren().add(layout);
	}
}
