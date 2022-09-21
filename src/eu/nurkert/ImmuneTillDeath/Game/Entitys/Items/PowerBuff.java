package eu.nurkert.ImmuneTillDeath.Game.Entitys.Items;

import eu.nurkert.ImmuneTillDeath.Engine.Back.GLocation;
import eu.nurkert.ImmuneTillDeath.Engine.Front.GTexture;

public class PowerBuff extends Item {

	public PowerBuff(GLocation location) {
		super(location);
		texture = new GTexture("/textures/mask.png");
	}
	
}
