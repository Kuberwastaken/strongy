package strongy.gui.mainwindow.eyethrows;

import javafx.scene.layout.VBox;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.model.datastate.IDataState;
import strongy.model.actions.IActionExecutor;

public class EnderEyePanel extends ThemedPanel {

	public final ThrowPanelHeader header;
	public final VBox throwsList;
	private final StyleManager styleManager;

	public EnderEyePanel(StyleManager styleManager, IDataState dataState, IActionExecutor actionExecutor) {
		super(styleManager);
		this.styleManager = styleManager;
		setSpacing(2);

		header = new ThrowPanelHeader(styleManager);
		throwsList = new VBox(2);

		getChildren().addAll(header, throwsList);

		dataState.getThrowList().subscribe(this::updateThrows);
	}

	public void updateThrows(strongy.event.IReadOnlyList<strongy.model.datastate.endereye.IEnderEyeThrow> throwsList) {
		this.throwsList.getChildren().clear();
		for (strongy.model.datastate.endereye.IEnderEyeThrow t : throwsList) {
			this.throwsList.getChildren().add(new ThrowPanel(styleManager, t));
		}
	}
}
