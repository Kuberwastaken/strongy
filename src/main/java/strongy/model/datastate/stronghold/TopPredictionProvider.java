package strongy.model.datastate.stronghold;

import strongy.event.IDisposable;
import strongy.event.Subscription;
import strongy.model.datastate.calculator.ICalculatorResult;
import strongy.model.domainmodel.IDomainModel;
import strongy.model.domainmodel.IDomainModelComponent;
import strongy.model.domainmodel.InferredComponent;

public class TopPredictionProvider implements IDisposable {

	private final InferredComponent<ChunkPrediction> topPrediction;

	private final Subscription calculatorResultSubscription;

	public TopPredictionProvider(IDomainModel domainModel, IDomainModelComponent<ICalculatorResult> calculatorResult) {
		calculatorResultSubscription = calculatorResult.subscribeInternal(this::updateTopPrediction);
		topPrediction = new InferredComponent<>(domainModel);
	}

	private void updateTopPrediction(ICalculatorResult calculatorResult) {
		if (calculatorResult == null || !calculatorResult.success()) {
			topPrediction.set(null);
			return;
		}
		topPrediction.set(calculatorResult.getBestPrediction());
	}

	public IDomainModelComponent<ChunkPrediction> topPrediction() {
		return topPrediction;
	}

	@Override
	public void dispose() {
		calculatorResultSubscription.dispose();
	}
}
