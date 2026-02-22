package strongy.gui.options.sections;

import javafx.geometry.Pos;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.io.preferences.StrongyPreferences;
import strongy.event.DisposeHandler;
import strongy.model.actions.IActionExecutor;
import strongy.model.datastate.calibrator.ICalibratorFactory;
import strongy.gui.frames.OptionsFrame;

public class AdvancedOptionsPanel extends ThemedPanel {
	public AdvancedOptionsPanel(StyleManager styleManager, StrongyPreferences preferences,
			ICalibratorFactory calibratorFactory, IActionExecutor actionExecutor, OptionsFrame owner,
			DisposeHandler disposeHandler) {
		super(styleManager);
		setSpacing(8);
		setAlignment(Pos.TOP_LEFT);
		// Stub for compilation
	}
}
