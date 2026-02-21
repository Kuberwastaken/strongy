package strongy.model.domainmodel;

import java.util.function.Consumer;

import strongy.event.IDisposable;
import strongy.event.ISubscribable;
import strongy.event.ObservableProperty;
import strongy.event.Subscription;

public class EventLocker<T> implements ISubscribable<T>, IDisposable {

	private final ObservableProperty<T> event;

	boolean locked = false;
	boolean hasReceivedEventDuringLock = false;
	T lastEvent = null;

	private final Subscription subscription;

	public EventLocker(ISubscribable<T> subscribable) {
		event = new ObservableProperty<>();
		subscription = subscribable.subscribe(this::onEventReceived);
	}

	private void onEventReceived(T value) {
		if (!locked) {
			event.notifySubscribers(value);
			return;
		}
		hasReceivedEventDuringLock = true;
		lastEvent = value;
	}

	void lock() {
		locked = true;
		hasReceivedEventDuringLock = false;
		lastEvent = null;
	}

	void unlockAndReleaseEvents() {
		locked = false;
		if (hasReceivedEventDuringLock) {
			event.notifySubscribers(lastEvent);
		}
		lastEvent = null;
		hasReceivedEventDuringLock = false;
	}

	@Override
	public Subscription subscribe(Consumer<T> subscriber) {
		return event.subscribe(subscriber);
	}

	@Override
	public void dispose() {
		subscription.dispose();
	}
}
