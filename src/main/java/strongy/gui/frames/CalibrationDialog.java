package strongy.gui.frames;

import javafx.stage.Stage;
import strongy.gui.style.StyleManager;
import strongy.io.preferences.StrongyPreferences;
import strongy.model.datastate.calibrator.ICalibratorFactory;

public class CalibrationDialog extends ThemedFrame {
	public CalibrationDialog(StyleManager styleManager, StrongyPreferences preferences, Stage owner,
			ICalibratorFactory calibratorFactory) {
		super(styleManager, preferences, "Calibration");
	}

	@Override
	protected void onExitButtonClicked() {
		dispose();
	}
}
