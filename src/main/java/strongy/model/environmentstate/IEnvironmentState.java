package strongy.model.environmentstate;

import strongy.model.datastate.calculator.ICalculator;
import strongy.model.domainmodel.IEnvironmentComponent;

/**
 * The state of external variables affecting the DataState. It is distinct from the DataState
 * because changes to the EnvironmentState are not undoable or resettable, for example settings
 * changes or the state of the Minecraft world.
 */
public interface IEnvironmentState {

	IEnvironmentComponent<ICalculator> calculator();

	IEnvironmentComponent<CalculatorSettings> calculatorSettings();

	IEnvironmentComponent<StandardDeviationSettings> standardDeviationSettings();

	IEnvironmentComponent<Boolean> allAdvancementsModeEnabled();

}
