package strongy.gui.mainwindow.alladvancements;

import javax.swing.border.EmptyBorder;

import strongy.event.DisposeHandler;
import strongy.event.IDisposable;
import strongy.event.IObservable;
import strongy.gui.buttons.FlatButton;
import strongy.gui.style.StyleManager;
import strongy.model.datastate.common.StructureInformation;
import strongy.model.input.IButtonInputHandler;

public class RemoveStructureButton extends FlatButton implements IDisposable {

	final DisposeHandler disposeHandler = new DisposeHandler();

	public RemoveStructureButton(StyleManager styleManager, IObservable<StructureInformation> structurePosition, IButtonInputHandler buttonInputHandler) {
		super(styleManager, "-");
		setBackgroundColor(styleManager.currentTheme.COLOR_SLIGHTLY_WEAK);
		setForegroundColor(styleManager.currentTheme.TEXT_COLOR_NEUTRAL);
		setHoverColor(styleManager.currentTheme.COLOR_EXIT_BUTTON_HOVER);
		setBorder(new EmptyBorder(0, 6, 0, 6));
		addActionListener(__ -> {
			if (structurePosition.get() != null)
				buttonInputHandler.onRemoveAllAdvancementsStructureButtonPressed(structurePosition.get());
		});

		updateVisibility(structurePosition.get());
		structurePosition.subscribeEDT(this::updateVisibility);
	}

	private void updateVisibility(StructureInformation structureInformation) {
		setVisible(structureInformation != null);
	}

	@Override
	public void dispose() {
		disposeHandler.dispose();
	}
}
