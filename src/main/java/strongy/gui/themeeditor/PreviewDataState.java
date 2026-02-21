package strongy.gui.themeeditor;

import java.util.List;

import strongy.model.datastate.IDataState;
import strongy.model.datastate.alladvancements.IAllAdvancementsDataState;
import strongy.model.datastate.blind.BlindResult;
import strongy.model.datastate.calculator.ICalculatorResult;
import strongy.model.datastate.common.DetachedDomainModel;
import strongy.model.datastate.common.IPlayerPosition;
import strongy.model.datastate.common.ResultType;
import strongy.model.datastate.divine.DivineContext;
import strongy.model.datastate.divine.DivineResult;
import strongy.model.datastate.divine.Fossil;
import strongy.model.datastate.divine.IDivineContext;
import strongy.model.datastate.endereye.IEnderEyeThrow;
import strongy.model.datastate.highprecision.BoatDataState;
import strongy.model.datastate.highprecision.IBoatDataState;
import strongy.model.datastate.stronghold.ChunkPrediction;
import strongy.model.domainmodel.DataComponent;
import strongy.model.domainmodel.IDataComponent;
import strongy.model.domainmodel.IDomainModel;
import strongy.model.domainmodel.IDomainModelComponent;
import strongy.model.domainmodel.IListComponent;
import strongy.model.domainmodel.InferredComponent;
import strongy.model.domainmodel.ListComponent;

public class PreviewDataState implements IDataState {

	private final IBoatDataState boatDataState;
	private final IAllAdvancementsDataState allAdvancementsDataState;

	private final DivineContext divineContext;
	private final ListComponent<IEnderEyeThrow> throwSet;
	private final DataComponent<Boolean> locked;
	private final DataComponent<IPlayerPosition> playerPosition;

	private final DataComponent<ResultType> resultType;
	private final InferredComponent<ICalculatorResult> calculatorResult;
	private final InferredComponent<ChunkPrediction> topPrediction;
	private final InferredComponent<BlindResult> blindResult;
	private final InferredComponent<DivineResult> divineResult;

	public PreviewDataState(ICalculatorResult result, List<IEnderEyeThrow> eyeThrows, Fossil f) {
		this();
		calculatorResult.set(result);
		topPrediction.set(result.getBestPrediction());
		for (IEnderEyeThrow t : eyeThrows) {
			throwSet.add(t);
		}
		divineContext.fossil.set(f);
	}

	public PreviewDataState() {
		IDomainModel domainModel = new DetachedDomainModel();
		divineContext = new DivineContext(domainModel);
		throwSet = new ListComponent<>("", domainModel, 10);
		playerPosition = new DataComponent<>("", domainModel, null);
		locked = new DataComponent<>("", domainModel, false);
		resultType = new DataComponent<>("", domainModel, ResultType.NONE);
		calculatorResult = new InferredComponent<>(domainModel);
		topPrediction = new InferredComponent<>(domainModel);
		blindResult = new InferredComponent<>(domainModel);
		divineResult = new InferredComponent<>(domainModel);

		boatDataState = new BoatDataState(domainModel);
		allAdvancementsDataState = new PreviewAllAdvancementsDataState();
	}

	@Override
	public IDivineContext getDivineContext() {
		return divineContext;
	}

	@Override
	public IListComponent<IEnderEyeThrow> getThrowList() {
		return throwSet;
	}

	@Override
	public IDataComponent<IPlayerPosition> playerPosition() {
		return playerPosition;
	}

	@Override
	public IDataComponent<Boolean> locked() {
		return locked;
	}

	@Override
	public IDomainModelComponent<ICalculatorResult> calculatorResult() {
		return calculatorResult;
	}

	@Override
	public IDomainModelComponent<ChunkPrediction> topPrediction() {
		return topPrediction;
	}

	@Override
	public IDomainModelComponent<BlindResult> blindResult() {
		return blindResult;
	}

	@Override
	public IDomainModelComponent<DivineResult> divineResult() {
		return divineResult;
	}

	@Override
	public IDomainModelComponent<ResultType> resultType() {
		return resultType;
	}

	@Override
	public IAllAdvancementsDataState allAdvancementsDataState() {
		return allAdvancementsDataState;
	}

	@Override
	public IBoatDataState boatDataState() {
		return boatDataState;
	}

}
