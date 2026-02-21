package strongy.io.mcinstance;

import java.io.IOException;

import com.sun.jna.Platform;
import strongy.io.mcinstance.mac.MacActiveInstanceListener;
import strongy.io.mcinstance.windows.WindowsActiveInstanceListener;
import strongy.util.Logger;

public abstract class ActiveInstanceProviderFactory {

	public static IActiveInstanceProvider createPlatformSpecificActiveInstanceProvider() {
		try {
			if (Platform.getOSType() == Platform.WINDOWS) {
				return new WindowsActiveInstanceListener();
			} else if (Platform.isMac()) {
                return new MacActiveInstanceListener();
            }
			return new UnsupportedOSActiveInstanceProvider();
		} catch (IOException exception) {
			Logger.log("Cannot monitor active Minecraft instance.");
			Logger.log(exception);
		}
		return new UnsupportedOSActiveInstanceProvider();
	}

}
