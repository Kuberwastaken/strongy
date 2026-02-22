package strongy.integrationtests;

import java.util.Locale;

import javafx.application.Platform;

import strongy.gui.frames.CalibrationDialog;
import strongy.model.datastate.calibrator.CalibratorFactory;
import strongy.util.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

public class CalibrationIntegrationTests {

	@DisabledIfEnvironmentVariable(named = "CI", matches = "true")
	@Test
	public void canOpenDialog() {
		// Arrange
		IntegrationTestBuilder integrationTestBuilder = new IntegrationTestBuilder();
		Locale.setDefault(Locale.US);
		CalibratorFactory calibratorFactory = integrationTestBuilder.createCalibratorFactory();

		// Act + Assert
		try {

			Platform.runLater(() -> {
				CalibrationDialog dialog = new CalibrationDialog(TestUtils.createStyleManager(),
						integrationTestBuilder.preferences, null, calibratorFactory);
			});
		} catch (Exception e) {
			Assertions.fail(e);
		}
	}

	@DisabledIfEnvironmentVariable(named = "CI", matches = "true")
	@Test
	public void canOpenDialog_manualCalibration() {
		// Arrange
		IntegrationTestBuilder integrationTestBuilder = new IntegrationTestBuilder();
		Locale.setDefault(Locale.US);
		CalibratorFactory calibratorFactory = integrationTestBuilder.createCalibratorFactory();

		// Act + Assert
		try {

			Platform.runLater(() -> {
				CalibrationDialog dialog = new CalibrationDialog(TestUtils.createStyleManager(),
						integrationTestBuilder.preferences, null, calibratorFactory);
			});
		} catch (Exception e) {
			Assertions.fail(e);
		}
	}

}
