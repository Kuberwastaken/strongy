package strongy.model.datastate.calculator;

import strongy.model.datastate.blind.BlindResult;
import strongy.model.datastate.divine.DivineResult;
import strongy.model.datastate.stronghold.ChunkPrediction;
import strongy.model.domainmodel.IDomainModelComponent;

public interface ICalculatorManager {

	IDomainModelComponent<ICalculatorResult> calculatorResult();

	IDomainModelComponent<ChunkPrediction> topPrediction();

	IDomainModelComponent<BlindResult> blindResult();

	IDomainModelComponent<DivineResult> divineResult();

}
