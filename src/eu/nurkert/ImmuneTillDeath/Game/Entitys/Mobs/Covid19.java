package eu.nurkert.ImmuneTillDeath.Game.Entitys.Mobs;

import eu.nurkert.ImmuneTillDeath.Engine.Back.GLocation;
import eu.nurkert.ImmuneTillDeath.Engine.Front.GTexture;
import eu.nurkert.ImmuneTillDeath.Engine.Back.Events.GEventHandler;

public class Covid19 extends Virus implements GEventHandler.GEventListener {
	long ts_created = System.currentTimeMillis();

	public Covid19(GLocation location, double speed) {
		super(location, speed);
		texture = new GTexture("/textures/viruses/covid-19.png");
		setHitRadius(9);
	}

//	@GEventMethod
//	public void on(HandleObjectsGEvent event) {
//		long lifetime = System.currentTimeMillis() + 1 - ts_created;
//		getVelocity().add(getLocation().getAngle(), speed * event.getDiff());
//	}

//	@Override
//	public void kill() {
//
//		for(int i = 0; i < 60; i++) {
//			GLocation loc = getLocation().copy();
//			int radius = new Random().nextInt(8);
//			double addX = new GVelocity(loc.getAngle(), radius).getX();
//			double addY = new GVelocity(loc.getAngle(), radius).getY();
//			loc.addX(addX);
//			loc.addY(addY);
//			loc.setAngle((float) (new Random().nextFloat()*2*Math.PI));
//			getLocation().getWorld().place(new GParticle(new Random().nextInt(5) > 2 ? new Color(160, 160, 160) : new Color(81, 12, 12), loc, new Random().nextInt(5)+2, new Random().nextInt(400) + 100));
//		}
//		super.kill();
//	}
}
