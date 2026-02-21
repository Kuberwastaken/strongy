package strongy.model.datastate.calculator;

import strongy.event.IObservable;
import strongy.event.IReadOnlyList;
import strongy.model.datastate.blind.BlindPosition;
import strongy.model.datastate.blind.BlindResult;
import strongy.model.datastate.common.IPlayerPosition;
import strongy.model.datastate.divine.DivineResult;
import strongy.model.datastate.divine.IDivineContext;
import strongy.model.datastate.endereye.IEnderEyeThrow;

public interface ICalculator {

	ICalculatorResult triangulate(IReadOnlyList<IEnderEyeThrow> eyeThrows, IObservable<IPlayerPosition> playerPos, IDivineContext divineContext);

	BlindResult blind(BlindPosition b, IDivineContext divineContext);

	DivineResult divine(IDivineContext divineContext);

}
