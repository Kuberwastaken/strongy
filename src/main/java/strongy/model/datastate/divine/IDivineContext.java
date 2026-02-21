package strongy.model.datastate.divine;

import strongy.model.datastate.blind.BlindPosition;
import strongy.model.domainmodel.IDataComponent;

public interface IDivineContext {

	/**
	 * @return The x-coordinate of the divine fossil if one exists, null otherwise.
	 */
	Fossil getFossil();

	IDataComponent<Fossil> fossil();

	boolean hasDivine();

	double relativeDensity();

	double getDensityAtAngleBeforeSnapping(double phi);

	/**
	 * Returns the closest of the three divine coords that are a distance r from
	 * (0,0)
	 */
	BlindPosition getClosestCoords(double x, double z, double r);

}
