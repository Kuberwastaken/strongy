package strongy.util;

import strongy.event.IObservable;
import strongy.event.ISubscribable;
import strongy.event.ObservableField;
import strongy.io.mcinstance.IActiveInstanceProvider;
import strongy.io.mcinstance.IMinecraftWorldFile;
import strongy.io.mcinstance.MinecraftInstance;

public class FakeActiveInstanceProvider implements IActiveInstanceProvider {

	public final ObservableField<MinecraftInstance> currentInstance = new ObservableField<>(null);
	public final ObservableField<IMinecraftWorldFile> currentWorldFile = new ObservableField<>(null);

	public FakeActiveInstanceProvider() {
	}

	@Override
	public IObservable<MinecraftInstance> activeMinecraftInstance() {
		return currentInstance;
	}

	@Override
	public IObservable<IMinecraftWorldFile> activeMinecraftWorld() {
		return currentWorldFile;
	}

	@Override
	public ISubscribable<IMinecraftWorldFile> whenActiveMinecraftWorldModified() {
		return currentWorldFile;
	}

	@Override
	public boolean supportsReadingActiveMinecraftWorld() {
		return true;
	}

}
