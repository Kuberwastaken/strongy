package strongy.io.mcinstance;

import strongy.event.IObservable;
import strongy.event.ISubscribable;

public interface IActiveInstanceProvider {

	IObservable<MinecraftInstance> activeMinecraftInstance();

	IObservable<IMinecraftWorldFile> activeMinecraftWorld();

	ISubscribable<IMinecraftWorldFile> whenActiveMinecraftWorldModified();

	boolean supportsReadingActiveMinecraftWorld();

}
