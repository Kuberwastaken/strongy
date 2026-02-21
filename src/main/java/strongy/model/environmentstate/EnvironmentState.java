package strongy.model.environmentstate;

import strongy.event.DisposeHandler;
import strongy.event.IDisposable;
import strongy.event.IObservable;
import strongy.event.Observable;
import strongy.io.preferences.StrongyPreferences;
import strongy.model.datastate.calculator.Calculator;
import strongy.model.datastate.calculator.ICalculator;
import strongy.model.domainmodel.EnvironmentComponent;
import strongy.model.domainmodel.IDomainModel;
import strongy.model.domainmodel.IEnvironmentComponent;

/**
 * The state of external variables affecting the DataState. It is distinct from the DataState
 * because changes to the EnvironmentState are not undoable or resettable, for example settings
 * changes or the state of the Minecraft world.
 */
public class EnvironmentState implements IEnvironmentState, IDisposable {

	private final StrongyPreferences preferences;

	private final IEnvironmentComponent<ICalculator> calculator;
	private final IEnvironmentComponent<CalculatorSettings> calculatorSettings;
	private final IEnvironmentComponent<StandardDeviationSettings> standardDeviationSettings;
	private final IEnvironmentComponent<Boolean> allAdvancementsModeEnabled;

	private final DisposeHandler disposeHandler = new DisposeHandler();

	public EnvironmentState(IDomainModel domainModel, StrongyPreferences preferences) {
		this.preferences = preferences;

		IObservable<CalculatorSettings> calculatorSettings = disposeHandler.add(Observable
				.inferFrom(this::createCalculatorSettings)
				.dependsOn(preferences.useAdvStatistics, preferences.mcVersion));
		this.calculatorSettings = EnvironmentComponent.of(domainModel, calculatorSettings, disposeHandler);

		IObservable<StandardDeviationSettings> standardDeviationSettings = disposeHandler.add(Observable
				.inferFrom(this::createStandardDeviationSettings)
				.dependsOn(preferences.sigma, preferences.sigmaAlt, preferences.sigmaManual, preferences.sigmaBoat));
		this.standardDeviationSettings = EnvironmentComponent.of(domainModel, standardDeviationSettings, disposeHandler);

		IObservable<ICalculator> calculator = disposeHandler.add(Observable
				.inferFrom(this::createCalculator)
				.dependsOn(calculatorSettings, standardDeviationSettings));
		this.calculator = EnvironmentComponent.of(domainModel, calculator, disposeHandler);

		IObservable<Boolean> allAdvancementsModeEnabled = disposeHandler.add(Observable
				.inferFrom(preferences.allAdvancements::get)
				.dependsOn(preferences.allAdvancements));
		this.allAdvancementsModeEnabled = EnvironmentComponent.of(domainModel, allAdvancementsModeEnabled, disposeHandler);
	}

	@Override
	public IEnvironmentComponent<ICalculator> calculator() {
		return calculator;
	}

	@Override
	public IEnvironmentComponent<CalculatorSettings> calculatorSettings() {
		return calculatorSettings;
	}

	@Override
	public IEnvironmentComponent<StandardDeviationSettings> standardDeviationSettings() {
		return standardDeviationSettings;
	}

	@Override
	public IEnvironmentComponent<Boolean> allAdvancementsModeEnabled() {
		return allAdvancementsModeEnabled;
	}

	private ICalculator createCalculator() {
		return new Calculator(calculatorSettings.get(), standardDeviationSettings.get());
	}

	private CalculatorSettings createCalculatorSettings() {
		return new CalculatorSettings(preferences.useAdvStatistics.get(), preferences.mcVersion.get());
	}

	private StandardDeviationSettings createStandardDeviationSettings() {
		return new StandardDeviationSettings(preferences.sigma.get(), preferences.sigmaAlt.get(), preferences.sigmaManual.get(), preferences.sigmaBoat.get());
	}

	@Override
	public void dispose() {
		disposeHandler.dispose();
	}
}
