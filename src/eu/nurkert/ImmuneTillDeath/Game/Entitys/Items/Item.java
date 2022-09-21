package eu.nurkert.ImmuneTillDeath.Game.Entitys.Items;

import eu.nurkert.ImmuneTillDeath.Engine.Back.GEntity;
import eu.nurkert.ImmuneTillDeath.Engine.Back.GLocation;
import eu.nurkert.ImmuneTillDeath.Engine.Back.GObject;
import eu.nurkert.ImmuneTillDeath.Game.Entitys.EntityType;

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
