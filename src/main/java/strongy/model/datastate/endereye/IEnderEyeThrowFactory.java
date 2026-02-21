package strongy.model.datastate.endereye;

import strongy.model.datastate.common.IDetailedPlayerPosition;
import strongy.model.datastate.common.ILimitedPlayerPosition;

public interface IEnderEyeThrowFactory {

	IEnderEyeThrow createEnderEyeThrowFromDetailedPlayerPosition(IDetailedPlayerPosition detailedPlayerPosition);

	IEnderEyeThrow createEnderEyeThrowFromLimitedPlayerPosition(ILimitedPlayerPosition playerPosition);

}
