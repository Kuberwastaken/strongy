package strongy.gui.mainwindow.information;

import javafx.scene.layout.VBox;
import javafx.application.Platform;
import java.util.List;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.model.information.InformationMessageList;
import strongy.model.information.InformationMessage;

public class InformationListPanel extends ThemedPanel {

	public InformationListPanel(StyleManager styleManager, InformationMessageList informationMessageList) {
		super(styleManager);
		setSpacing(4);

		informationMessageList.subscribeEDT(messages -> {
			Platform.runLater(() -> updateInformationMessages(messages, styleManager));
		});
	}

	private void updateInformationMessages(Iterable<InformationMessage> messages, StyleManager styleManager) {
		getChildren().clear();
		for (InformationMessage message : messages) {
			getChildren().add(new InformationTextPanel(styleManager, message));
		}
	}
}
