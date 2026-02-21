package strongy.gui.options;

import java.awt.Dimension;

import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.gui.style.theme.WrappedColor;

public class ColorDisplayPanel extends ThemedPanel {

	public ColorDisplayPanel(StyleManager styleManager, WrappedColor color) {
		super(styleManager);
		setBackgroundColor(color);
	}

	@Override
	public void updateSize(StyleManager styleManager) {
		super.updateSize(styleManager);
		int textSize = getTextSize(styleManager.size);
		setPreferredSize(new Dimension(textSize * 2, textSize * 2));
	}
}