package strongy.util;

import strongy.event.IObservable;
import strongy.event.ObservableField;
import strongy.io.IClipboardProvider;

public class MockedClipboardReader implements IClipboardProvider {

	private final ObservableField<String> clipboard = new ObservableField<>("");

	public void setClipboard(String string) {
		clipboard.set(string);
	}

	@Override
	public IObservable<String> clipboardText() {
		return clipboard;
	}
}
