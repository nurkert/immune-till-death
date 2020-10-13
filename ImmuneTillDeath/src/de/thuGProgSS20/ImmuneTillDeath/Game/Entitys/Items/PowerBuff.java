package de.thuGProgSS20.ImmuneTillDeath.Game.Entitys.Items;

import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.GLocation;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Front.GTexture;

public class PowerBuff extends Item {

	public PowerBuff(GLocation location) {
		super(location);
		texture = new GTexture("/textures/mask.png");
	}
	
}
