package strongy.model.datastate;

import strongy.event.DisposeHandler;
import strongy.event.IDisposable;
import strongy.io.preferences.enums.DefaultBoatType;
import strongy.model.datastate.alladvancements.AllAdvancementsDataState;
import strongy.model.datastate.alladvancements.IAllAdvancementsDataState;
import strongy.model.datastate.blind.BlindResult;
import strongy.model.datastate.calculator.CalculatorManager;
import strongy.model.datastate.calculator.ICalculatorResult;
import strongy.model.datastate.common.IPlayerPosition;
import strongy.model.datastate.common.ResultType;
import strongy.model.datastate.common.ResultTypeProvider;
import strongy.model.datastate.divine.DivineContext;
import strongy.model.datastate.divine.DivineResult;
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
import strongy.model.domainmodel.ListComponent;
import strongy.model.environmentstate.IEnvironmentState;

public class DataState implements IDataState, IDisposable {

	final AllAdvancementsDataState allAdvancementsDataState;
	final BoatDataState boatDataState;

	private final DataComponent<Boolean> locked;

	private final DivineContext divineContext;
	private final ListComponent<IEnderEyeThrow> throwSet;
	private final DataComponent<IPlayerPosition> playerPosition;

	private final CalculatorManager calculatorManager;
	private final ResultTypeProvider resultTypeProvider;

	private final DisposeHandler disposeHandler = new DisposeHandler();

	public DataState(IDomainModel domainModel, IEnvironmentState environmentState) {
		this(domainModel, environmentState, DefaultBoatType.GRAY);
	}

	public DataState(IDomainModel domainModel, IEnvironmentState environmentState, DefaultBoatType defaultBoatType) {
		divineContext = disposeHandler.add(new DivineContext(domainModel));
		throwSet = new ListComponent<>("throw_set", domainModel, 10);
		playerPosition = new DataComponent<>("player_position", domainModel);
		locked = new DataComponent<>("is_locked", domainModel, false);

		calculatorManager = disposeHandler.add(new CalculatorManager(domainModel, environmentState, throwSet, playerPosition, divineContext));
		allAdvancementsDataState = disposeHandler.add(new AllAdvancementsDataState(calculatorManager.topPrediction(), playerPosition, domainModel, environmentState));
		boatDataState = new BoatDataState(domainModel, defaultBoatType);

		resultTypeProvider = disposeHandler.add(new ResultTypeProvider(this, domainModel));
	}

	@Override
	public IAllAdvancementsDataState allAdvancementsDataState() {
		return allAdvancementsDataState;
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
		return calculatorManager.calculatorResult();
	}

	@Override
	public IDomainModelComponent<ChunkPrediction> topPrediction() {
		return calculatorManager.topPrediction();
	}

	@Override
	public IDomainModelComponent<BlindResult> blindResult() {
		return calculatorManager.blindResult();
	}

	@Override
	public IDomainModelComponent<DivineResult> divineResult() {
		return calculatorManager.divineResult();
	}

	@Override
	public IDomainModelComponent<ResultType> resultType() {
		return resultTypeProvider.resultType();
	}

	@Override
	public IBoatDataState boatDataState() {
		return boatDataState;
	}

	@Override
	public void dispose() {
		disposeHandler.dispose();
	}

}
