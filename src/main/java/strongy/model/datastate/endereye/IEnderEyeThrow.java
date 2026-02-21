package strongy.model.datastate.endereye;

import java.io.Serializable;

import strongy.model.datastate.common.IOverworldRay;
import strongy.model.datastate.common.IPlayerPosition;
import strongy.model.datastate.common.LimitedPlayerPosition;
import strongy.model.environmentstate.StandardDeviationSettings;

public interface IEnderEyeThrow extends IOverworldRay, Serializable {

	double verticalAngle();

	double horizontalAngleWithoutCorrection();

	double correction();

	int correctionIncrements();

	double getStandardDeviation(StandardDeviationSettings standardDeviationHandler);

	double getExpectedStandardDeviationForNextEnderEyeThrow(StandardDeviationSettings standardDeviationHandler);

	IEnderEyeThrow withCorrection(double correction, int correctionIncrements);

	IEnderEyeThrow withToggledAltStd();

	EnderEyeThrowType getType();

	default IPlayerPosition getPlayerPosition() {
		return new LimitedPlayerPosition(xInOverworld(), zInOverworld(), horizontalAngle(), correctionIncrements());
	}

}
