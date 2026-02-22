package strongy.gui.components.layout;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import strongy.gui.components.ThemedComponent;
import strongy.gui.style.StyleManager;

/**
 * A themed TabPane.
 */
public class ThemedTabbedPane extends TabPane implements ThemedComponent {

	public ThemedTabbedPane(StyleManager styleManager) {
		getStyleClass().add("tab-pane");
		setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
	}

	public void addTab(String title, Region content) {
		Tab tab = new Tab(title);
		tab.setContent(content);
		getTabs().add(tab);
	}
}
