package strongy.io;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import strongy.event.IObservable;
import strongy.event.ObservableField;
import strongy.io.preferences.StrongyPreferences;
import strongy.util.Logger;

/**
 * Reads clipboard content using event-driven FlavorListener instead of polling.
 * In standard mode, clipboard changes are detected automatically.
 * In alt clipboard reader mode, reads are triggered by forceRead()
 * (hotkey-driven).
 */
public class ClipboardReader implements IClipboardProvider, FlavorListener {

	private final StrongyPreferences preferences;

	final Clipboard clipboard;
	String lastClipboardString;

	final ObservableField<String> clipboardString;

	public ClipboardReader(StrongyPreferences preferences) {
		this.preferences = preferences;
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboardString = new ObservableField<>(null, true);
		lastClipboardString = "";
	}

	public IObservable<String> clipboardText() {
		return clipboardString;
	}

	/**
	 * Start listening for clipboard changes. Replaces the old Thread-based polling.
	 */
	public void start() {
		clipboard.addFlavorListener(this);
	}

	/**
	 * Called by the system when clipboard content changes (standard mode).
	 */
	@Override
	public void flavorsChanged(FlavorEvent e) {
		if (preferences.altClipboardReader.get()) {
			// In alt mode, we only read on explicit forceRead() calls
			return;
		}
		readClipboard();
	}

	/**
	 * Force an immediate clipboard read (used by alt clipboard reader mode via
	 * hotkey).
	 */
	public void forceRead() {
		// Small delay to let the game update the clipboard after F3+C
		new Thread(() -> {
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			readClipboard();
		}, "Clipboard force read").start();
	}

	private synchronized void readClipboard() {
		try {
			if (!clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
				return;
			}
			String text = (String) clipboard.getData(DataFlavor.stringFlavor);
			if (text == null)
				return;
			if (text.length() > 1000) {
				text = text.substring(0, 1000);
			}
			if (!lastClipboardString.equals(text)) {
				onClipboardUpdated(text);
				lastClipboardString = text;
			}
		} catch (UnsupportedFlavorException | IllegalStateException | IOException ex) {
			Logger.log("Clipboard read error: " + ex.getMessage());
		}
	}

	private void onClipboardUpdated(String clipboard) {
		clipboardString.set(clipboard);
	}

}
