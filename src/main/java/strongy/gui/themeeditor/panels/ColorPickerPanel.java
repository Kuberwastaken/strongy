package strongy.gui.themeeditor.panels;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import strongy.event.DisposeHandler;
import strongy.event.IDisposable;
import strongy.event.ISubscribable;
import strongy.gui.components.inputfields.HexThemedTextField;
import strongy.gui.components.layout.LabeledField;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.util.I18n;

public class ColorPickerPanel extends ThemedPanel implements IDisposable {

	private static final int GAP = 10;

	final ColorChooserPanel colorChooserPanel;

	final DisposeHandler disposeHandler = new DisposeHandler();

	public ColorPickerPanel(StyleManager styleManager) {
		super(styleManager);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));

		colorChooserPanel = new ColorChooserPanel();
		HexThemedTextField hexTextField = new HexThemedTextField(styleManager);
		hexTextField.setColor(colorChooserPanel.getColor());

		add(new LabeledField(styleManager, I18n.get("settings.themeeditor.hex_colon"), hexTextField, false));
		add(colorChooserPanel);

		disposeHandler.add(colorChooserPanel.whenColorChanged().subscribe(hexTextField::setColor));
		disposeHandler.add(hexTextField.whenTextChanged().subscribe(text -> colorChooserPanel.setColor(Color.decode(text))));
	}

	public void setColor(Color color) {
		colorChooserPanel.setColor(color);
	}

	public ISubscribable<Color> whenColorChanged() {
		return colorChooserPanel.whenColorChanged();
	}

	@Override
	public void dispose() {
		disposeHandler.dispose();
	}

}
