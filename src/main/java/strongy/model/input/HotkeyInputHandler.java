package strongy.model.input;

import strongy.event.DisposeHandler;
import strongy.event.IDisposable;
import strongy.io.preferences.StrongyPreferences;
import strongy.io.preferences.enums.AllAdvancementsToggleType;
import strongy.model.actions.IActionExecutor;
import strongy.model.actions.alladvancements.SetHasEnteredEndAction;
import strongy.model.actions.boat.ResetBoatStateAction;
import strongy.model.actions.boat.ToggleEnteringBoatAction;
import strongy.model.actions.boat.ToggleMod360IndicatorAction;
import strongy.model.actions.common.ResetAction;
import strongy.model.actions.common.ToggleLockedAction;
import strongy.model.actions.endereye.ChangeLastAngleAction;
import strongy.model.actions.endereye.ToggleAltStdOnLastThrowAction;
import strongy.model.datastate.IDataState;
import strongy.model.datastate.alladvancements.IAllAdvancementsDataState;
import strongy.model.datastate.highprecision.BoatState;
import strongy.model.domainmodel.IDomainModel;

public class HotkeyInputHandler implements IDisposable {

	private final StrongyPreferences preferences;
	private final IDomainModel domainModel;
	private final IDataState dataState;
	private final IActionExecutor actionExecutor;

	private final DisposeHandler disposeHandler = new DisposeHandler();

	public HotkeyInputHandler(StrongyPreferences preferences, IDomainModel domainModel, IDataState dataState, IActionExecutor actionExecutor) {
		this.preferences = preferences;
		this.domainModel = domainModel;
		this.dataState = dataState;
		this.actionExecutor = actionExecutor;

		disposeHandler.add(preferences.hotkeyReset.whenTriggered().subscribe(this::resetIfNotLocked));
		disposeHandler.add(preferences.hotkeyUndo.whenTriggered().subscribe(this::undoIfNotLocked));
		disposeHandler.add(preferences.hotkeyRedo.whenTriggered().subscribe(this::redoIfNotLocked));
		disposeHandler.add(preferences.hotkeyIncrement.whenTriggered().subscribe(__ -> changeLastAngleIfNotLocked(1)));
		disposeHandler.add(preferences.hotkeyDecrement.whenTriggered().subscribe(__ -> changeLastAngleIfNotLocked(-1)));
		disposeHandler.add(preferences.hotkeyAltStd.whenTriggered().subscribe(this::toggleAltStdIfNotLocked));
		disposeHandler.add(preferences.hotkeyBoat.whenTriggered().subscribe(this::toggleEnteringBoatIfNotLocked));
		disposeHandler.add(preferences.hotkeyMod360.whenTriggered().subscribe(this::toggleMod360IndicatorIfNotLocked));
		disposeHandler.add(preferences.hotkeyLock.whenTriggered().subscribe(__ -> actionExecutor.executeImmediately(new ToggleLockedAction(dataState))));
		disposeHandler.add(preferences.usePreciseAngle.whenModified().subscribe(this::resetBoatState));
		disposeHandler.add(preferences.hotkeyToggleAllAdvancementsMode.whenTriggered().subscribe(this::onToggleAllAdvancementsModeHotkeyTriggered));
	}

	private void resetIfNotLocked() {
		if (!dataState.locked().get())
			actionExecutor.executeImmediately(new ResetAction(domainModel));
	}

	private void undoIfNotLocked() {
		if (!dataState.locked().get())
			domainModel.undoUnderWriteLock();
	}

	private void redoIfNotLocked() {
		if (!dataState.locked().get())
			domainModel.redoUnderWriteLock();
	}

	private void changeLastAngleIfNotLocked(int correctionIncrements) {
		if (!dataState.locked().get() && !dataState.allAdvancementsDataState().allAdvancementsModeEnabled().get())
			actionExecutor.executeImmediately(new ChangeLastAngleAction(dataState, preferences, correctionIncrements));
	}

	private void toggleAltStdIfNotLocked() {
		if (!dataState.locked().get() && !dataState.allAdvancementsDataState().allAdvancementsModeEnabled().get())
			actionExecutor.executeImmediately(new ToggleAltStdOnLastThrowAction(dataState, preferences));
	}

	private void toggleEnteringBoatIfNotLocked() {
		if (preferences.usePreciseAngle.get() && !dataState.locked().get() && !dataState.allAdvancementsDataState().allAdvancementsModeEnabled().get()) {
			if (dataState.boatDataState().reducingModulo360().get())
				actionExecutor.executeImmediately(new ToggleMod360IndicatorAction(dataState), new ToggleEnteringBoatAction(dataState));
			else
				actionExecutor.executeImmediately(new ToggleEnteringBoatAction(dataState));
		}
	}

	private void toggleMod360IndicatorIfNotLocked() {
		if (preferences.usePreciseAngle.get() && dataState.boatDataState().boatState().get() == BoatState.VALID && !dataState.locked().get() && !dataState.allAdvancementsDataState().allAdvancementsModeEnabled().get()) {
			actionExecutor.executeImmediately(new ToggleMod360IndicatorAction(dataState));
		}
	}

	private void resetBoatState() {
		if (!preferences.usePreciseAngle.get())
			actionExecutor.executeImmediately(new ResetBoatStateAction(dataState));
	}

	private void onToggleAllAdvancementsModeHotkeyTriggered() {
		if (preferences.allAdvancementsToggleType.get() == AllAdvancementsToggleType.Hotkey) {
			IAllAdvancementsDataState allAdvancementsDataState = dataState.allAdvancementsDataState();
			actionExecutor.executeImmediately(new SetHasEnteredEndAction(allAdvancementsDataState, !allAdvancementsDataState.hasEnteredEnd().get()));
		}
	}

	@Override
	public void dispose() {
		disposeHandler.dispose();
	}
}
