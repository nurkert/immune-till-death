package de.thuGProgSS20.ImmuneTillDeath.Game.Entitys.Items;

import java.awt.Color;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.GLocation;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.GObject;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.GVelocity;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Front.GParticle;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Front.GTexture;
import de.thuGProgSS20.ImmuneTillDeath.Game.Entitys.Player;

public class SyringeItem extends Item {

	Timer timer;

	public SyringeItem(GLocation location) {
		super(location);
		texture = new GTexture("/textures/needle.png");
		timer = new Timer();
		init();
	}

	@Override
	public void setUseless(boolean useless) {
		if (useless)
			timer.cancel();
		super.setUseless(useless);
	}

	@Override
	public void kill() {
		timer.cancel();
		super.kill();
	}

	private void init() {

		for (int i = 0; i < 50; i++) {
			GLocation loc = getLocation().copy();
			double radius = new Random().nextDouble() * getHitRadius();
			double addX = new GVelocity(loc.getAngle(), radius).getX();
			double addY = new GVelocity(loc.getAngle(), radius).getY();
			loc.addX(addX);
			loc.addY(addY);
			getLocation().getWorld().place(new GParticle(getColorOfTexture(), loc, new Random().nextInt(2) + 1,
					new Random().nextInt(900) + 100));

		}

		GLocation loc = getLocation().copy();
		loc.setAngle((float) (getLocation().getAngle() + Math.PI * 1 / 2 + 0.8));
		double addX = new GVelocity(loc.getAngle(), 5.5).getX();
		double addY = new GVelocity(loc.getAngle(), 5.5).getY();
		loc.addX(addX);
		loc.addY(addY);

		timer.schedule(new TimerTask() {

			@Override
			public void run() {
//				loc
				GLocation spread = loc.copy();
				spread.setAngle((float) (loc.getAngle()));
				spread.addAnlge((float) (new Random().nextDouble() * 1 / 2 * Math.PI - Math.PI * 1 / 4));
				getLocation().getWorld().place(new GParticle(Color.CYAN, spread, 10, new Random().nextInt(400) + 100));
			}
		}, 100, 100);
	}

	@Override
	public void collide(GObject object) {
		if (!isDead() && object instanceof Player) {
			((Player) object).setPower(((Player) object).getPower() + 4);
			kill();
		}
		super.collide(object);
	}

}
