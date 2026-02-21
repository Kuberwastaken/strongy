package strongy.util;

import strongy.event.IObservable;
import strongy.event.ObservableField;
import strongy.io.IClipboardProvider;

public class FakeClipboardProvider implements IClipboardProvider {

	private final ObservableField<String> clipboard = new ObservableField<String>("");

	@Override
	public IObservable<String> clipboardText() {
		return clipboard;
	}

}
