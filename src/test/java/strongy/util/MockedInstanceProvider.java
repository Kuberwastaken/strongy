package strongy.util;

import strongy.event.ObservableField;
import strongy.io.mcinstance.IActiveInstanceProvider;
import strongy.io.mcinstance.IMinecraftWorldFile;
import strongy.io.mcinstance.MinecraftInstance;

public class MockedInstanceProvider implements IActiveInstanceProvider {

	public final ObservableField<MinecraftInstance> currentInstance = new ObservableField<MinecraftInstance>(null);
	public final ObservableField<IMinecraftWorldFile> currentWorldFile = new ObservableField<IMinecraftWorldFile>(null);

	public MockedInstanceProvider() {
	}

	@Override
	public ObservableField<MinecraftInstance> activeMinecraftInstance() {
		return currentInstance;
	}

	@Override
	public ObservableField<IMinecraftWorldFile> activeMinecraftWorld() {
		return currentWorldFile;
	}

	@Override
	public ObservableField<IMinecraftWorldFile> whenActiveMinecraftWorldModified() {
		return currentWorldFile;
	}

	@Override
	public boolean supportsReadingActiveMinecraftWorld() {
		return true;
	}

}
