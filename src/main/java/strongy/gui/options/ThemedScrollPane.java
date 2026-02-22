package strongy.gui.options;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import strongy.gui.components.ThemedComponent;
import strongy.gui.style.StyleManager;

public class ThemedScrollPane extends ScrollPane implements ThemedComponent {

	public ThemedScrollPane(StyleManager styleManager) {
		getStyleClass().add("themed-scroll-pane");
		setFitToWidth(true);
		setHbarPolicy(ScrollBarPolicy.NEVER);
		setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
	}

	public ThemedScrollPane(StyleManager styleManager, Region content) {
		this(styleManager);
		setContent(content);
	}
}
