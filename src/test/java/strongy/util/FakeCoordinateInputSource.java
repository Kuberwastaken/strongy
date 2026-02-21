package strongy.util;

import strongy.event.ISubscribable;
import strongy.event.ObservableProperty;
import strongy.model.datastate.common.IDetailedPlayerPosition;
import strongy.model.datastate.common.ILimitedPlayerPosition;
import strongy.model.datastate.common.IPlayerPositionInputSource;
import strongy.model.datastate.endereye.F3IData;
import strongy.model.input.IF3ILocationInputSource;

public class FakeCoordinateInputSource implements IPlayerPositionInputSource, IF3ILocationInputSource {

	public final ObservableProperty<IDetailedPlayerPosition> whenNewDetailedPlayerPositionInputted;
	public final ObservableProperty<ILimitedPlayerPosition> whenNewLimitedPlayerPositionInputted;
	public final ObservableProperty<F3IData> whenNewF3IInputted;

	public FakeCoordinateInputSource() {
		whenNewDetailedPlayerPositionInputted = new ObservableProperty<>();
		whenNewLimitedPlayerPositionInputted = new ObservableProperty<>();
		whenNewF3IInputted = new ObservableProperty<>();
	}

	public ISubscribable<IDetailedPlayerPosition> whenNewDetailedPlayerPositionInputted() {
		return whenNewDetailedPlayerPositionInputted;
	}

	public ISubscribable<ILimitedPlayerPosition> whenNewLimitedPlayerPositionInputted() {
		return whenNewLimitedPlayerPositionInputted;
	}

	public ISubscribable<F3IData> whenNewF3ILocationInputted() {
		return whenNewF3IInputted;
	}
}
