package strongy.io.mcinstance;

import strongy.io.preferences.enums.McVersion;

public class McInstanceTestAdapter {

	public static void setMinecraftInstanceVersion(MinecraftInstance minecraftInstance, McVersion mcVersion){
		minecraftInstance.setMcVersion(mcVersion);
	}

}
