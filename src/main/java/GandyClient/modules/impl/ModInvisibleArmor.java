package GandyClient.modules.impl;

import GandyClient.gui.hud.ScreenPosition;
import GandyClient.modules.ModDraggable;
import GandyClient.modules.Setting;

public class ModInvisibleArmor extends ModDraggable {
	private String type = "Invisible Armor";
	private boolean isUseGl = false;
	private Setting settings;
	
	public ModInvisibleArmor () {
		super();
		this.settings = new Setting(this.getClass().getSimpleName());
		if (!settings.getSettings().containsKey("ENABLED")) {
			settings.updateSetting("ENABLED", 0);
		}
		
		if (!settings.getSettings().containsKey("HEADBAND")) {
			settings.updateSetting("HEADBAND", 1);
		}
		
		if (!settings.getSettings().containsKey("SHOW_IRON")) {
			settings.updateSetting("SHOW_IRON", 0);
		}
		
		if (!settings.getSettings().containsKey("SHOW_GOLD")) {
			settings.updateSetting("SHOW_GOLD", 0);
		}
		
		if (!settings.getSettings().containsKey("SHOW_DIAMOND")) {
			settings.updateSetting("SHOW_DIAMOND", 0);
		}
	}
	
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public boolean isUseGl () {
		return isUseGl;
	}

	@Override
	public void render(ScreenPosition pos) {
		
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return type;
	}
	@Override 
	public String getSimpleName () {
		return this.getClass().getSimpleName();
	}

	@Override
	public Setting getSettings() {
		// TODO Auto-generated method stub
		return settings;
	}
}
