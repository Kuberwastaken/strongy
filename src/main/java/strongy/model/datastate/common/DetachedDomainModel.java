package strongy.model.datastate.common;

import java.io.ObjectInput;
import java.io.ObjectOutput;

import strongy.event.ISubscribable;
import strongy.event.ObservableProperty;
import strongy.model.domainmodel.IDomainModel;
import strongy.model.domainmodel.IFundamentalComponent;
import strongy.model.domainmodel.IInferredComponent;

public class DetachedDomainModel implements IDomainModel {

	ISubscribable<IDomainModel> whenModified = new ObservableProperty<>();

	@Override
	public void registerFundamentalComponent(IFundamentalComponent<?, ?> fundamentalComponent) {
	}

	@Override
	public void registerInferredComponent(IInferredComponent<?> inferredComponent) {
	}

	@Override
	public <T> ISubscribable<T> createExternalEventFor(ISubscribable<T> subscribable) {
		return subscribable;
	}

	@Override
	public void checkWriteAccess() {
	}

	@Override
	public void acquireWriteLock() {
	}

	@Override
	public void releaseWriteLock() {
	}

	@Override
	public void reset() {
	}

	@Override
	public void undoUnderWriteLock() {
	}

	@Override
	public void redoUnderWriteLock() {
	}

	@Override
	public void deleteHistory() {
	}

	@Override
	public boolean isReset() {
		return true;
	}

	@Override
	public boolean isExternalSubscriptionRegistrationAllowed() {
		return true;
	}

	@Override
	public boolean isInternalSubscriptionRegistrationAllowed() {
		return true;
	}

	@Override
	public ISubscribable<IDomainModel> whenModified() {
		return whenModified;
	}

	@Override
	public Runnable applyWriteLock(Runnable runnable) {
		return runnable;
	}

	@Override
	public void serialize(ObjectOutput objectOutput) {
	}

	@Override
	public void deserialize(ObjectInput objectInput) {
	}
}
