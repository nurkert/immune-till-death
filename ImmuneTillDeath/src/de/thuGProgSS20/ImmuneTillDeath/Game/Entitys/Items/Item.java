package de.thuGProgSS20.ImmuneTillDeath.Game.Entitys.Items;

import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.GEntity;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.GLocation;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.GObject;
import de.thuGProgSS20.ImmuneTillDeath.Game.Entitys.EntityType;

public abstract class Item extends GEntity{

	public Item(GLocation location) {
		super(location, 0, 5, EntityType.ITEM);
		setLayor(GLayor.MAIN1);
	}
	

	@Override
	public void collide(GObject object) {
		// TODO Auto-generated method stub
		
	}
	
}
