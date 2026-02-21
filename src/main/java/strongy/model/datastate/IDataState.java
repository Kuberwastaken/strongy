package strongy.model.datastate;

import strongy.model.datastate.alladvancements.IAllAdvancementsDataState;
import strongy.model.datastate.blind.BlindResult;
import strongy.model.datastate.calculator.ICalculatorResult;
import strongy.model.datastate.common.IPlayerPosition;
import strongy.model.datastate.common.ResultType;
import strongy.model.datastate.divine.DivineResult;
import strongy.model.datastate.divine.IDivineContext;
import strongy.model.datastate.endereye.IEnderEyeThrow;
import strongy.model.datastate.highprecision.IBoatDataState;
import strongy.model.datastate.stronghold.ChunkPrediction;
import strongy.model.domainmodel.IDataComponent;
import strongy.model.domainmodel.IDomainModelComponent;
import strongy.model.domainmodel.IListComponent;

public interface IDataState {

	IAllAdvancementsDataState allAdvancementsDataState();

	IBoatDataState boatDataState();

	IDivineContext getDivineContext();

	IListComponent<IEnderEyeThrow> getThrowList();

	IDataComponent<IPlayerPosition> playerPosition();

	IDataComponent<Boolean> locked();

	IDomainModelComponent<ICalculatorResult> calculatorResult();

	IDomainModelComponent<ChunkPrediction> topPrediction();

	IDomainModelComponent<BlindResult> blindResult();

	IDomainModelComponent<DivineResult> divineResult();

	IDomainModelComponent<ResultType> resultType();

	default double getBestCertainty() {
		ICalculatorResult calculatorResult = calculatorResult().get();
		return calculatorResult != null && calculatorResult.success() ? calculatorResult.getBestPrediction().chunk.weight : 0;
	}

}
