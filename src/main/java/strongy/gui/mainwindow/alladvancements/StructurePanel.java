package strongy.gui.mainwindow.alladvancements;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.border.MatteBorder;

import strongy.event.DisposeHandler;
import strongy.event.IDisposable;
import strongy.event.IObservable;
import strongy.event.Subscription;
import strongy.gui.components.labels.ThemedLabel;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.gui.style.theme.ColumnLayout;
import strongy.gui.style.theme.WrappedColor;
import strongy.model.datastate.common.StructureInformation;
import strongy.model.input.IButtonInputHandler;

public class StructurePanel extends ThemedPanel implements IDisposable {

	final ThemedLabel location;
	final ThemedLabel nether;
	final ThemedLabel angle;

	private final boolean showNether;
	private final boolean showBorder;
	private final WrappedColor borderColor;

	private final DisposeHandler disposeHandler = new DisposeHandler();
	private Subscription angleSubscription;

	public StructurePanel(StyleManager styleManager, IButtonInputHandler buttonInputHandler, IObservable<StructureInformation> structurePosition, ImageIcon icon, boolean addDeleteButton, boolean showBorder, boolean showNether) {
		super(styleManager);
		setOpaque(true);
		ThemedLabel iconLabel = new ThemedLabel(styleManager, true);
		iconLabel.setIcon(icon);
		location = new ThemedLabel(styleManager, true);
		nether = new ThemedLabel(styleManager, true);
		angle = new ThemedLabel(styleManager, true);
		Component deleteButton = addDeleteButton ? createDeleteButton(styleManager, structurePosition, buttonInputHandler) : new ThemedLabel(styleManager);

		ColumnLayout layout = new ColumnLayout(0);
		layout.setRelativeWidth(iconLabel, 0.4f);
		layout.setRelativeWidth(location, 1.9f);
		layout.setRelativeWidth(nether, 1.7f);
		layout.setRelativeWidth(angle, 0.8f);
		layout.setRelativeWidth(deleteButton, 0.4f, true);
		setLayout(layout);

		add(iconLabel);
		add(location);
		add(nether);
		add(angle);
		add(deleteButton);

		this.showNether = showNether;
		this.showBorder = showBorder;
		borderColor = styleManager.currentTheme.COLOR_DIVIDER;
		setBackgroundColor(styleManager.currentTheme.COLOR_SLIGHTLY_WEAK);

		location.setForegroundColor(styleManager.currentTheme.TEXT_COLOR_SLIGHTLY_WEAK);
		nether.setForegroundColor(styleManager.currentTheme.TEXT_COLOR_SLIGHTLY_WEAK);
		angle.setForegroundColor(styleManager.currentTheme.TEXT_COLOR_SLIGHTLY_WEAK);

		onStructurePositionUpdated(structurePosition.get());
		disposeHandler.add(structurePosition.subscribeEDT(this::onStructurePositionUpdated));
	}

	private RemoveStructureButton createDeleteButton(StyleManager styleManager, IObservable<StructureInformation> structurePosition, IButtonInputHandler buttonInputHandler) {
		RemoveStructureButton removeStructureButton = new RemoveStructureButton(styleManager, structurePosition, buttonInputHandler);
		removeStructureButton.setForegroundColor(styleManager.currentTheme.TEXT_COLOR_SLIGHTLY_WEAK);
		disposeHandler.add(removeStructureButton);
		return removeStructureButton;
	}

	public void onStructurePositionUpdated(StructureInformation structureInformation) {
		if (structureInformation != null) {
			location.setText(String.format("(%.0f, %.0f)", structureInformation.xInOverworld(), structureInformation.zInOverworld()));
			angle.setText(String.format("%.1f", structureInformation.getTravelAngle()));
			if (showNether)
				nether.setText(String.format("(%.0f, %.0f)", structureInformation.xInNether(), structureInformation.zInNether()));
		} else {
			location.setText("");
			nether.setText("");
			angle.setText("");
		}

		if (angleSubscription != null) {
			angleSubscription.dispose();
			angleSubscription = null;
		}
		if (structureInformation != null)
			angleSubscription = structureInformation.whenRelativePlayerPositionChanged().subscribeEDT(this::onStructurePositionUpdated);
	}

	public String getLocationText() {
		return location.getText();
	}

	@Override
	public void updateColors() {
		super.updateColors();
		if (showBorder)
			setBorder(new MatteBorder(0, 0, 1, 0, borderColor.color()));
	}

	@Override
	public void dispose() {
		disposeHandler.dispose();
		if (angleSubscription != null)
			angleSubscription.dispose();
	}
}
