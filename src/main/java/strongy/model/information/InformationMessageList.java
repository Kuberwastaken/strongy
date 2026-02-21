package strongy.model.information;

import java.util.HashMap;

import strongy.event.DisposeHandler;
import strongy.event.IDisposable;
import strongy.event.ObservableList;

public class InformationMessageList extends ObservableList<InformationMessage> implements IDisposable {

	final HashMap<InformationMessageProvider, InformationMessage> informationMessages;

	final DisposeHandler disposeHandler = new DisposeHandler();

	public InformationMessageList() {
		informationMessages = new HashMap<>();
	}

	public void AddInformationMessageProvider(InformationMessageProvider informationMessageProvider) {
		whenInformationMessageChanged(informationMessageProvider);
		disposeHandler.add(informationMessageProvider.subscribe(__ -> whenInformationMessageChanged(informationMessageProvider)));
	}

	private synchronized void whenInformationMessageChanged(InformationMessageProvider provider) {
		InformationMessage message = provider.get();
		if (informationMessages.containsKey(provider)) {
			remove(informationMessages.get(provider));
			informationMessages.remove(provider);
		}
		if (message != null) {
			add(message);
			informationMessages.put(provider, message);
		}
	}

	@Override
	public void dispose() {
		disposeHandler.dispose();
	}

}
