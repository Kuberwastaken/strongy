package strongy.io;

import javax.swing.Timer;

import strongy.event.DisposeHandler;
import strongy.event.IDisposable;
import strongy.io.preferences.StrongyPreferences;
import strongy.model.actions.IActionExecutor;
import strongy.model.actions.common.ResetAction;
import strongy.model.datastate.IDataState;
import strongy.model.domainmodel.IDomainModel;

public class AutoResetTimer extends Timer implements IDisposable {

	private static final int AUTO_RESET_DELAY = 15 * 60 * 1000;

	private final StrongyPreferences preferences;

	final DisposeHandler disposeHandler = new DisposeHandler();

	public AutoResetTimer(IDataState dataState, IDomainModel domainModel, IActionExecutor actionExecutor, StrongyPreferences preferences) {
		super(AUTO_RESET_DELAY, null);
		this.preferences = preferences;
		addActionListener(p -> {
			if (!dataState.locked().get())
				actionExecutor.executeImmediately(new ResetAction(domainModel));
			restart();
			stop();
		});

		refresh();
		disposeHandler.add(domainModel.whenModified().subscribe(this::refresh));
		disposeHandler.add(preferences.autoReset.whenModified().subscribe(this::refresh));
	}

	private void refresh() {
		restart();
		if (preferences.autoReset.get()) {
			start();
		} else {
			stop();
		}
	}

	@Override
	public void dispose() {
		disposeHandler.dispose();
	}

}
