package strongy.model.datastate.calibrator;

import strongy.event.IObservable;
import strongy.io.preferences.StrongyPreferences;
import strongy.model.datastate.common.IPlayerPositionInputSource;
import strongy.model.environmentstate.CalculatorSettings;

public class CalibratorFactory implements ICalibratorFactory {

	private final IObservable<CalculatorSettings> calculatorSettings;
	private final IPlayerPositionInputSource playerPositionInputSource;
	private final StrongyPreferences preferences;

	public CalibratorFactory(IObservable<CalculatorSettings> calculatorSettings, IPlayerPositionInputSource playerPositionInputSource, StrongyPreferences preferences) {
		this.calculatorSettings = calculatorSettings;
		this.playerPositionInputSource = playerPositionInputSource;
		this.preferences = preferences;
	}

	@Override
	public Calibrator createCalibrator(boolean isBoatCalibrator, boolean isManualCalibrator) {
		return new Calibrator(calculatorSettings.get(), playerPositionInputSource, preferences, isBoatCalibrator, isManualCalibrator);
	}

}
