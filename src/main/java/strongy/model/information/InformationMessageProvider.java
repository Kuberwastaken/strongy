package strongy.model.information;

import strongy.event.DisposeHandler;
import strongy.event.IDisposable;
import strongy.event.ObservableField;
import strongy.io.preferences.BooleanPreference;

public abstract class InformationMessageProvider extends ObservableField<InformationMessage> implements IDisposable {

	final DisposeHandler disposeHandler = new DisposeHandler();

	BooleanPreference enabledPreference;

	InformationMessageProvider() {
	}

	InformationMessageProvider(BooleanPreference enabledPreference) {
		this.enabledPreference = enabledPreference;
		disposeHandler.add(enabledPreference.whenModified().subscribe(this::raiseInformationMessageChanged));
	}

	protected abstract boolean shouldShowInformationMessage();

	protected abstract InformationMessage getInformationMessage();

	protected void raiseInformationMessageChanged() {
		boolean disabledByPreference = enabledPreference != null && !enabledPreference.get();
		InformationMessage informationMessageToShow = !disabledByPreference && shouldShowInformationMessage() ? getInformationMessage() : null;
		set(informationMessageToShow);
	}

	@Override
	public void dispose() {
		disposeHandler.dispose();
	}

}
