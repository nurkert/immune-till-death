package eu.nurkert.ImmuneTillDeath.Engine.Back.Events.Essential;

import eu.nurkert.ImmuneTillDeath.Engine.Back.GEntity;
import eu.nurkert.ImmuneTillDeath.Engine.Back.Events.GEvent;

public class EntityDeathGEvent extends GEvent {
	
	GEntity entity;

	public EntityDeathGEvent(GEntity entity) {
		super(-1);
		this.entity = entity;
	}

	public GEntity getEntity() {
		return entity;
	}
}
