package eu.nurkert.ImmuneTillDeath.Engine.Back.Events.Essential;

import eu.nurkert.ImmuneTillDeath.Engine.Back.GContent;
import eu.nurkert.ImmuneTillDeath.Engine.Back.Events.GEvent;

public class KeyPressedGEvent extends GEvent {

	char key;
	int keycode;
	GContent world;
	
	public KeyPressedGEvent(double diff, char key, int keycode, GContent world) {
		super(diff);
		this.key = key;
		this.keycode = keycode;
		this.world = world;
	}
	
	public char getKey() {
		return key;
	}
	
	public GContent getWorld() {
		return this.world;
	}
	
	public int getKeycode() {
		return keycode;
	}
}
