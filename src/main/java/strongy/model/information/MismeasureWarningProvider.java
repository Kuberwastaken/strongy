package strongy.model.information;

import strongy.event.IObservable;
import strongy.io.preferences.StrongyPreferences;
import strongy.model.datastate.IDataState;
import strongy.model.datastate.calculator.ICalculatorResult;
import strongy.model.datastate.common.ResultType;
import strongy.model.datastate.endereye.IEnderEyeThrow;
import strongy.model.datastate.stronghold.ChunkPrediction;
import strongy.model.environmentstate.IEnvironmentState;
import strongy.model.environmentstate.StandardDeviationSettings;
import strongy.util.I18n;

public class MismeasureWarningProvider extends InformationMessageProvider {

	private final IDataState dataState;
	private final IObservable<StandardDeviationSettings> standardDeviationSettings;

	public MismeasureWarningProvider(IDataState dataState, IEnvironmentState environmentState, StrongyPreferences preferences) {
		super(preferences.informationMismeasureEnabled);
		this.dataState = dataState;
		this.standardDeviationSettings = environmentState.standardDeviationSettings();
		raiseInformationMessageChanged();
		disposeHandler.add(dataState.calculatorResult().subscribe(this::raiseInformationMessageChanged));
		disposeHandler.add(dataState.resultType().subscribe(this::raiseInformationMessageChanged));
		disposeHandler.add(standardDeviationSettings.subscribe(this::raiseInformationMessageChanged));
	}

	@Override
	protected boolean shouldShowInformationMessage() {
		if (dataState.resultType().get() != ResultType.TRIANGULATION)
			return false;

		ICalculatorResult calculatorResult = dataState.calculatorResult().get();
		if (calculatorResult == null || !calculatorResult.success())
			return false;

		ChunkPrediction bestPrediction = calculatorResult.getBestPrediction();
		double likelihood = 1;
		double expectedLikelihood = 1;
		for (IEnderEyeThrow t : dataState.getThrowList()) {
			double error = bestPrediction.getAngleError(t);
			double sigma = t.getStandardDeviation(standardDeviationSettings.get());
			likelihood *= Math.exp(-0.5 * (error / sigma) * (error / sigma));
			expectedLikelihood *= 1.0 / Math.sqrt(2);
		}

		return (likelihood / expectedLikelihood) < 0.01;
	}

	private InformationMessage warningMessage = null;

	@Override
	protected InformationMessage getInformationMessage() {
		if (warningMessage == null)
			warningMessage = new InformationMessage(InformationMessageSeverity.WARNING, "MISMEASURE", I18n.get("information.mismeasure"));
		return warningMessage;
	}

}
