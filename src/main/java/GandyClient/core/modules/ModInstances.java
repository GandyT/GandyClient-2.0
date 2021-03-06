package GandyClient.core.modules;

import GandyClient.core.gui.hud.HUDManager;
import GandyClient.core.modules.impl.ModBlockOverlay;
import GandyClient.core.modules.impl.ModCapeModifier;
import GandyClient.core.modules.impl.ModFPS;
import GandyClient.core.modules.impl.ModFullBright;
import GandyClient.core.modules.impl.ModInvisibleArmor;
import GandyClient.core.modules.impl.ModKeystrokes;
import GandyClient.core.modules.impl.ModOldAnimations;
import GandyClient.core.modules.impl.ModPerspective;
import GandyClient.core.modules.impl.ModPotionEffects;
import GandyClient.core.modules.impl.ModSettings;
import GandyClient.core.modules.impl.ModTimeChanger;
import GandyClient.core.modules.impl.ModXYZ;
import GandyClient.core.modules.impl.autogg.ModAutoGG;
import GandyClient.core.modules.impl.itemphysics.ModItemPhysics;
import GandyClient.core.modules.impl.togglesprintsneak.ModToggleSprintSneak;
import net.minecraft.client.Minecraft;

public class ModInstances {
	
private static ModFPS modFPS;
	
	private static ModKeystrokes modKeystrokes;
	
	private static ModToggleSprintSneak modToggleSprintSneak;
	
	private static ModPotionEffects modPotionEffects;
	
	private static ModXYZ modXYZ;
	
	private static ModFullBright modFullBright;
	
	private static ModAutoGG modAutoGG;
	
	private static ModPerspective modPerspective;
	
	private static ModOldAnimations modOldAnimations;
	
	private static ModBlockOverlay modBlockOverlay;
	
	private static ModTimeChanger modTimeChanger;
	
	private static ModItemPhysics modItemPhysics;
	
	private static ModInvisibleArmor modInvisibleArmor = new ModInvisibleArmor();
	
	private static ModSettings modSettings;
	
	private static ModCapeModifier modCapeModifier;
	
	public static void register (ModuleManager api) {		
		modKeystrokes = new ModKeystrokes();
		api.register(modKeystrokes);
		SettingsManager.getInstance().register(modKeystrokes.getSettings());
		
		modFPS = new ModFPS();
		api.register(modFPS);
		SettingsManager.getInstance().register(modFPS.getSettings());
		
		modToggleSprintSneak = new ModToggleSprintSneak();
		api.register(modToggleSprintSneak);
		SettingsManager.getInstance().register(modToggleSprintSneak.getSettings());
		
		modPotionEffects = new ModPotionEffects();
		api.register(modPotionEffects);
		SettingsManager.getInstance().register(modPotionEffects.getSettings());
		
		modXYZ = new ModXYZ();
		api.register(modXYZ);
		SettingsManager.getInstance().register(modXYZ.getSettings());
		
		modFullBright = new ModFullBright();
		api.register(modFullBright);
		if (Minecraft.getMinecraft().gameSettings.gammaSetting == 100f) {
			HUDManager.getInstance().register(modFullBright);
		}
		SettingsManager.getInstance().register(modFullBright.getSettings());
		
		modAutoGG = new ModAutoGG();
		api.register(modAutoGG);
		SettingsManager.getInstance().register(modAutoGG.getSettings());
		
		modPerspective = new ModPerspective();
		api.register(modPerspective);
		SettingsManager.getInstance().register(modPerspective.getSettings());
		
		modOldAnimations = new ModOldAnimations();
		api.register(modOldAnimations);
		SettingsManager.getInstance().register(modOldAnimations.getSettings());
		
		modBlockOverlay = new ModBlockOverlay();
		api.register(modBlockOverlay);
		SettingsManager.getInstance().register(modBlockOverlay.getSettings());
		
		modTimeChanger = new ModTimeChanger();
		api.register(modTimeChanger);
		SettingsManager.getInstance().register(modTimeChanger.getSettings());
		
		modItemPhysics = new ModItemPhysics();
		api.register(modItemPhysics);
		SettingsManager.getInstance().register(modItemPhysics.getSettings());
		
		modInvisibleArmor = new ModInvisibleArmor();
		api.register(modInvisibleArmor);
		SettingsManager.getInstance().register(modInvisibleArmor.getSettings());
		
		modCapeModifier = new ModCapeModifier();
		api.register(modCapeModifier);
		SettingsManager.getInstance().register(modCapeModifier.getSettings());
		
		modSettings = new ModSettings();
		SettingsManager.getInstance().register(modSettings.getSettings());
	}
	
	public static ModToggleSprintSneak getModToggleSprintSneak () {
		return modToggleSprintSneak;
	}
	
	public static ModAutoGG getModAutoGG () {
		return modAutoGG;
	}
	
	public static ModPerspective getModPerspective () {
		return modPerspective;
	}
	
}
