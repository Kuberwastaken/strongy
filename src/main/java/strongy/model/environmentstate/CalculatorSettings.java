package strongy.model.environmentstate;

import strongy.io.preferences.StrongyPreferences;
import strongy.io.preferences.enums.McVersion;

public class CalculatorSettings {

	public final int numberOfReturnedPredictions;
	public final boolean useAdvancedStatistics;
	public final McVersion mcVersion;
	public final boolean multiStrongholdTracking;

	public CalculatorSettings() {
		numberOfReturnedPredictions = 5;
		useAdvancedStatistics = true;
		mcVersion = McVersion.PRE_119;
		multiStrongholdTracking = false;
	}

	public CalculatorSettings(boolean useAdvancedStatistics, McVersion mcVersion) {
		numberOfReturnedPredictions = 5;
		this.useAdvancedStatistics = useAdvancedStatistics;
		this.mcVersion = mcVersion;
		this.multiStrongholdTracking = false;
	}

	public CalculatorSettings(StrongyPreferences preferences) {
		numberOfReturnedPredictions = 5;
		useAdvancedStatistics = preferences.useAdvStatistics.get();
		mcVersion = preferences.mcVersion.get();
		multiStrongholdTracking = preferences.multiStrongholdTracking.get();
	}

}
