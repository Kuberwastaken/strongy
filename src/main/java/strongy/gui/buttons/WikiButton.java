package strongy.gui.buttons;

import strongy.gui.style.StyleManager;

public class WikiButton extends FlatButton {

	public WikiButton(StyleManager styleManager, String url) {
		super(styleManager, "Wiki", "/help_icon.png");
		getStyleClass().add("flat-button");

		setOnAction(e -> {
			try {
				java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
			} catch (Exception ignored) {
			}
		});
	}
}
