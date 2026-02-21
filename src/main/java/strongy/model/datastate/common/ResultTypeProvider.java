package strongy.model.datastate.common;

import strongy.event.DisposeHandler;
import strongy.event.IDisposable;
import strongy.model.datastate.IDataState;
import strongy.model.datastate.alladvancements.IAllAdvancementsDataState;
import strongy.model.datastate.calculator.ICalculatorResult;
import strongy.model.datastate.divine.Fossil;
import strongy.model.domainmodel.IDomainModel;
import strongy.model.domainmodel.IDomainModelComponent;
import strongy.model.domainmodel.InferredComponent;

public class ResultTypeProvider implements IDisposable {

	private final InferredComponent<ResultType> resultType;

	private final IAllAdvancementsDataState allAdvancementsDataState;
	private final IDomainModelComponent<ICalculatorResult> calculatorResult;
	private final IDomainModelComponent<IPlayerPosition> playerPosition;
	private final IDomainModelComponent<Fossil> fossil;

	private final DisposeHandler disposeHandler = new DisposeHandler();

	public ResultTypeProvider(IDataState dataState, IDomainModel domainModel) {
		resultType = new InferredComponent<>(domainModel, ResultType.NONE);
		allAdvancementsDataState = dataState.allAdvancementsDataState();
		calculatorResult = dataState.calculatorResult();
		playerPosition = dataState.playerPosition();
		fossil = dataState.getDivineContext().fossil();

		disposeHandler.add(allAdvancementsDataState.allAdvancementsModeEnabled().subscribeInternal(this::updateResultType));
		disposeHandler.add(calculatorResult.subscribeInternal(this::updateResultType));
		disposeHandler.add(playerPosition.subscribeInternal(this::updateResultType));
		disposeHandler.add(fossil.subscribeInternal(this::updateResultType));
	}

	public IDomainModelComponent<ResultType> resultType() {
		return resultType;
	}

	private ResultType getExpectedResultType() {
		if (allAdvancementsDataState.allAdvancementsModeEnabled().get())
			return ResultType.ALL_ADVANCEMENTS;

		if (calculatorResult.get() != null && calculatorResult.get().success())
			return ResultType.TRIANGULATION;

		if (calculatorResult.get() != null)
			return ResultType.FAILED;

		if (playerPosition.get() != null && playerPosition.get().isInNether())
			return ResultType.BLIND;

		if (fossil.get() != null)
			return ResultType.DIVINE;

		return ResultType.NONE;
	}

	private void updateResultType() {
		resultType.set(getExpectedResultType());
	}

	@Override
	public void dispose() {
		disposeHandler.dispose();
	}
}
