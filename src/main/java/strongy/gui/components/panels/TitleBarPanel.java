package strongy.gui.components.panels;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.Window;

import strongy.gui.components.ThemedComponent;
import strongy.gui.style.StyleManager;

/**
 * Title bar panel — JavaFX replacement using HBox.
 */
public class TitleBarPanel extends HBox implements ThemedComponent {

	private final HBox buttonsBox;
	private double dragOffsetX, dragOffsetY;

	public TitleBarPanel(StyleManager styleManager, Window owner) {
		getStyleClass().add("title-bar");
		setAlignment(Pos.CENTER_LEFT);

		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		buttonsBox = new HBox();
		buttonsBox.setAlignment(Pos.CENTER_RIGHT);

		getChildren().addAll(spacer, buttonsBox);

		// Drag support
		if (owner instanceof Stage stage) {
			setOnMousePressed(e -> {
				dragOffsetX = e.getScreenX() - stage.getX();
				dragOffsetY = e.getScreenY() - stage.getY();
			});
			setOnMouseDragged(e -> {
				stage.setX(e.getScreenX() - dragOffsetX);
				stage.setY(e.getScreenY() - dragOffsetY);
			});
		}
	}

	public void addButton(Button button) {
		buttonsBox.getChildren().add(button);
	}

	public void insertContent(int index, Node node) {
		getChildren().add(index, node);
	}
}