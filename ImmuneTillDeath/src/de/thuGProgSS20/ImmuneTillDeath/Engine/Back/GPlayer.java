package de.thuGProgSS20.ImmuneTillDeath.Engine.Back;

import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.GObject.Collidable;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.Events.GEventHandler.GEventListener;
import de.thuGProgSS20.ImmuneTillDeath.Game.Entitys.EntityType;

public abstract class GPlayer extends GEntity implements Collidable, GEventListener {

	GLocation viewCenter;

	public GPlayer(GLocation location, GLocation viewCenter, double speed, double hitRadius) {
		super(location, speed, hitRadius, EntityType.PLAYER);
		this.viewCenter = viewCenter;

//		GEventHandler.register(this);
	}

	/**
	 * 
	 * Viewcenter/("camera" center) follows player entity smooth
	 */
	@Override
	public void handle(double diff, GContent world) {
		super.handle(diff, world);

		double dX = ((getLocation().getX() - viewCenter.getX()) / 4) * diff * 10;
		double dY = ((getLocation().getY() - viewCenter.getY()) / 4) * diff * 10;
		viewCenter.add(dX, dY);
	}


//	public void collide(GObject object) {
////		damage(2);
//	}

	
}
