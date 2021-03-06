package GandyClient.core.modules;

import java.util.Set;

import com.google.common.collect.Sets;

public class SettingsManager {
	private static SettingsManager instance = null;
	private Set<Setting> settings = Sets.newHashSet();
	
	public static SettingsManager getInstance () {
		if (instance == null) instance = new SettingsManager();
		return instance;
	}
	
	public void register (Setting setting) {
		settings.add(setting);
	}
	
	public void updateSetting (String modName, String optionName, int value) {
		String mod = modName.toUpperCase(); // this is simple name
		String property = optionName.toUpperCase();
		
		for (Setting setting : settings) {
			if (setting == null) {
				// no setting for this mod
			} else if (setting.getModName().equalsIgnoreCase(mod)) {
				setting.updateSetting(property, value);
				return;
			}
		}
	}
	
	public int getSettingValue (String modName, String optionName) {
		String mod = modName.toUpperCase();
		
		for (Setting setting : settings) {
			if (setting == null) {
				// no setting for this mod
			} else if (setting.getModName().equalsIgnoreCase(mod)) {
				return setting.getSettings().get(optionName);
			}
		}
		
		return 0;
	}
}
