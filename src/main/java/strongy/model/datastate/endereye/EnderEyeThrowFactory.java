package strongy.model.datastate.endereye;

import strongy.io.preferences.StrongyPreferences;
import strongy.model.datastate.common.IDetailedPlayerPosition;
import strongy.model.datastate.common.ILimitedPlayerPosition;
import strongy.model.datastate.highprecision.BoatEnderEyeThrow;
import strongy.model.datastate.highprecision.IBoatDataState;
import strongy.util.Assert;

public class EnderEyeThrowFactory implements IEnderEyeThrowFactory {

	private final StrongyPreferences preferences;
	private final IBoatDataState boatDataState;

	public EnderEyeThrowFactory(StrongyPreferences preferences, IBoatDataState boatDataState) {
		this.preferences = preferences;
		this.boatDataState = boatDataState;
	}

	@Override
	public IEnderEyeThrow createEnderEyeThrowFromDetailedPlayerPosition(IDetailedPlayerPosition detailedPlayerPosition) {
		Assert.isTrue(detailedPlayerPosition.isInOverworld());

		boolean isBoatThrow = preferences.usePreciseAngle.get() && boatDataState.boatAngle().get() != null;
		if (isBoatThrow)
			return new BoatEnderEyeThrow(detailedPlayerPosition, preferences, boatDataState.boatAngle().get());

		return new NormalEnderEyeThrow(detailedPlayerPosition, preferences.crosshairCorrection.get());
	}

	@Override
	public IEnderEyeThrow createEnderEyeThrowFromLimitedPlayerPosition(ILimitedPlayerPosition playerPosition) {
		Assert.isTrue(playerPosition.isInOverworld());

		boolean isBoatThrow = preferences.usePreciseAngle.get() && boatDataState.boatAngle().get() != null;
		if (isBoatThrow)
			return new BoatEnderEyeThrow(playerPosition, preferences, boatDataState.boatAngle().get());

		return new ManualEnderEyeThrow(playerPosition, preferences.crosshairCorrection.get());
	}

}
