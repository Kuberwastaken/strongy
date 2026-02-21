package strongy.model.input;

import strongy.event.DisposeHandler;
import strongy.event.IDisposable;
import strongy.io.preferences.StrongyPreferences;
import strongy.model.actions.IAction;
import strongy.model.actions.IActionExecutor;
import strongy.model.actions.alladvancements.TryAddAllAdvancementsStructureAction;
import strongy.model.actions.boat.ReduceBoatAngleMod360Action;
import strongy.model.actions.boat.SetBoatAngleAction;
import strongy.model.actions.common.SetPlayerPositionAction;
import strongy.model.actions.endereye.AddEnderEyeThrowAction;
import strongy.model.actions.endereye.ChangeLastAngleAction;
import strongy.model.actions.util.JointAction;
import strongy.model.datastate.IDataState;
import strongy.model.datastate.common.IDetailedPlayerPosition;
import strongy.model.datastate.common.ILimitedPlayerPosition;
import strongy.model.datastate.common.IPlayerPositionInputSource;
import strongy.model.datastate.endereye.IEnderEyeThrow;
import strongy.model.datastate.endereye.IEnderEyeThrowFactory;

/**
 * Listens to a stream of player position inputs and decides if/how the inputs should affect the data state.
 */
public class PlayerPositionInputHandler implements IDisposable {

	private final IDataState dataState;
	private final IActionExecutor actionExecutor;
	private final StrongyPreferences preferences;
	private final IEnderEyeThrowFactory enderEyeThrowFactory;

	final DisposeHandler disposeHandler = new DisposeHandler();

	public PlayerPositionInputHandler(IPlayerPositionInputSource playerPositionInputSource, IDataState dataState, IActionExecutor actionExecutor, StrongyPreferences preferences, IEnderEyeThrowFactory enderEyeThrowFactory) {
		this.dataState = dataState;
		this.actionExecutor = actionExecutor;
		this.preferences = preferences;
		this.enderEyeThrowFactory = enderEyeThrowFactory;
		disposeHandler.add(playerPositionInputSource.whenNewDetailedPlayerPositionInputted().subscribe(this::onNewDetailedPlayerPositionInputted));
		disposeHandler.add(playerPositionInputSource.whenNewLimitedPlayerPositionInputted().subscribe(this::onNewLimitedPlayerPositionInputted));
	}

	private void onNewDetailedPlayerPositionInputted(IDetailedPlayerPosition detailedPlayerPosition) {
		IAction setPlayerPositionAction = new SetPlayerPositionAction(dataState, detailedPlayerPosition);
		IAction actionForNewThrow = getActionForInputtedPlayerPosition(detailedPlayerPosition);
		if (actionForNewThrow == null) {
			actionExecutor.executeImmediately(setPlayerPositionAction);
			return;
		}
		actionExecutor.executeImmediately(setPlayerPositionAction, actionForNewThrow);
	}

	private IAction getActionForInputtedPlayerPosition(IDetailedPlayerPosition playerPosition) {
		if (dataState.locked().get())
			return null;

		if (preferences.usePreciseAngle.get() && dataState.boatDataState().enteringBoat().get())
			return new SetBoatAngleAction(dataState.boatDataState(), playerPosition.horizontalAngle(), preferences);

		if (preferences.usePreciseAngle.get() && dataState.boatDataState().reducingModulo360().get())
			return new ReduceBoatAngleMod360Action(dataState.boatDataState(), playerPosition.horizontalAngle(), preferences.sensitivityAutomatic.get());

		if (dataState.allAdvancementsDataState().allAdvancementsModeEnabled().get())
			return new TryAddAllAdvancementsStructureAction(dataState, playerPosition, preferences);

		if (!playerPosition.isInOverworld())
			return null;

		if (playerPosition.lookingBelowHorizon())
			return null;

		IEnderEyeThrow enderEyeThrowToAdd = enderEyeThrowFactory.createEnderEyeThrowFromDetailedPlayerPosition(playerPosition);
		if (shouldSkipAddingThrow(enderEyeThrowToAdd))
			return null;

		return new AddEnderEyeThrowAction(dataState, enderEyeThrowToAdd);
	}

	private void onNewLimitedPlayerPositionInputted(ILimitedPlayerPosition playerPosition) {
		IAction setPlayerPositionAction = new SetPlayerPositionAction(dataState, playerPosition);
		IAction actionForNewThrow = getActionForInputtedLimitedPlayerPosition(playerPosition);
		if (actionForNewThrow == null) {
			actionExecutor.executeImmediately(setPlayerPositionAction);
			return;
		}
		actionExecutor.executeImmediately(setPlayerPositionAction, actionForNewThrow);
	}

	private IAction getActionForInputtedLimitedPlayerPosition(ILimitedPlayerPosition playerPosition) {
		if (dataState.locked().get())
			return null;

		if (!playerPosition.isInOverworld())
			return null;

		IEnderEyeThrow enderEyeThrowToAdd = enderEyeThrowFactory.createEnderEyeThrowFromLimitedPlayerPosition(playerPosition);
		if (shouldSkipAddingThrow(enderEyeThrowToAdd))
			return null;

		IAction action = new AddEnderEyeThrowAction(dataState, enderEyeThrowToAdd);

		if (playerPosition.correctionIncrements() != 0)
			action = new JointAction(action, new ChangeLastAngleAction(dataState, preferences, playerPosition.correctionIncrements()));

		return action;
	}

	private boolean shouldSkipAddingThrow(IEnderEyeThrow enderEyeThrow) {
		if (dataState.getThrowList().size() == 0)
			return false;

		IEnderEyeThrow lastThrow = dataState.getThrowList().getLast();
		return lastThrow.xInOverworld() == enderEyeThrow.xInOverworld() &&
			   lastThrow.zInOverworld() == enderEyeThrow.zInOverworld() &&
			   lastThrow.horizontalAngleWithoutCorrection() == enderEyeThrow.horizontalAngleWithoutCorrection();
	}

	@Override
	public void dispose() {
		disposeHandler.dispose();
	}

}
