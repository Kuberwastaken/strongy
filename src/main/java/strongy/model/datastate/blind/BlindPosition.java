package strongy.model.datastate.blind;

import strongy.model.datastate.common.IPlayerPosition;

public class BlindPosition {
	public final double x, z;

	public BlindPosition(double x, double z) {
		this.x = x;
		this.z = z;
	}

	public BlindPosition(IPlayerPosition playerPos) {
		this(playerPos.xInPlayerDimension(), playerPos.zInPlayerDimension());
	}

}
