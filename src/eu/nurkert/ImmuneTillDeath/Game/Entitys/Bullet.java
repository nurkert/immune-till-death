package eu.nurkert.ImmuneTillDeath.Game.Entitys;

import java.util.Random;

import eu.nurkert.ImmuneTillDeath.Engine.Back.GContent;
import eu.nurkert.ImmuneTillDeath.Engine.Back.GEntity;
import eu.nurkert.ImmuneTillDeath.Engine.Back.GLocation;
import eu.nurkert.ImmuneTillDeath.Engine.Back.GMonster;
import eu.nurkert.ImmuneTillDeath.Engine.Back.GObject;
import eu.nurkert.ImmuneTillDeath.Engine.Back.GPlayer;
import eu.nurkert.ImmuneTillDeath.Engine.Back.GVelocity;
import eu.nurkert.ImmuneTillDeath.Engine.Back.Events.GEventHandler.GEventListener;
import eu.nurkert.ImmuneTillDeath.Engine.Front.GParticle;
import eu.nurkert.ImmuneTillDeath.Engine.Front.GTexture;

public class Bullet extends GEntity implements GEventListener {
	int radius = 3;
	final int LIFETIME = 1000;
	long ts_created;
	GEntity shooter;
	
	public Bullet(GEntity shooter, GLocation location, double speed) {
		super(location, speed, 2, EntityType.OTHER);
		texture = new GTexture("/textures/projectile.png");
		ts_created = System.currentTimeMillis();
		this.shooter = shooter;
	}

	@Override
	public void collide(GObject object) {
		if (object instanceof GEntity && !(object instanceof GPlayer) && !(((GEntity) object).isDead())) {
			if (object instanceof GMonster) {
				((GEntity) object).kill();
				if(shooter instanceof Player) 
					((Player) shooter).addScore();
				
			}
			else if (object instanceof Cell && ((Cell) object).isInfected()) 
				((Cell) object).toggleInfected();
			
			kill();
		}
	}

	@Override
	public void handle(double diff, GContent world) {
		GLocation loc = getLocation().copy();
		loc.setAngle(getLocation().getInvertAngle());

		loc.addAnlge((float) (new Random().nextDouble() * Math.PI - Math.PI / 2));
		double addX = new GVelocity(loc.getAngle(), 2).getX();
		double addY = new GVelocity(loc.getAngle(), 2).getY();
		loc.addX(addX);
		loc.addY(addY);

		getLocation().getWorld().place(new GParticle(getColorOfTexture(), loc, 1, new Random().nextInt(100) + 10));
		super.handle(diff, world);
		if (this.LIFETIME < System.currentTimeMillis() - this.ts_created) {
			this.kill();
			return;
		}

		getVelocity().add(getLocation().getAngle(), speed * diff * 175);
	}

	public GEntity getShooter() {
		return shooter;
	}
}
