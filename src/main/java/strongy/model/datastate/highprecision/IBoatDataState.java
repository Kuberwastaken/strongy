package strongy.model.datastate.highprecision;

import strongy.model.domainmodel.IDataComponent;

public interface IBoatDataState {

	IDataComponent<Boolean> enteringBoat();

	IDataComponent<Boolean> reducingModulo360();

	IDataComponent<Float> boatAngle();

	IDataComponent<BoatState> boatState();

}
