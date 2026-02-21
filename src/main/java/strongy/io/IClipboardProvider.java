package strongy.io;

import strongy.event.IObservable;

public interface IClipboardProvider {

	IObservable<String> clipboardText();

}
