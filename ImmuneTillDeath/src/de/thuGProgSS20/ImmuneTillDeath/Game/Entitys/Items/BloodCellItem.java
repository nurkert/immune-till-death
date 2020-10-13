package de.thuGProgSS20.ImmuneTillDeath.Game.Entitys.Items;

import java.util.Random;
import java.util.Timer;

import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.GContent;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.GLocation;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.GObject;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.GVelocity;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Front.GAnimatedTexture;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Front.GParticle;
import de.thuGProgSS20.ImmuneTillDeath.Game.Entitys.Player;

public class BloodCellItem extends Item {

	public BloodCellItem(GLocation location) {
		super(location);
		texture = new GAnimatedTexture("/textures/bloodCell.png");
		getVelocity().setAngle(getLocation().getAngle());
		init();
	}
	
	private void init() {

	}

	@Override
	public void collide(GObject object) {
		if (!isDead() && object instanceof Player) {
			Player player = (Player) object;
			Timer timer = new Timer();
			player.setHealth(player.getHealth() + 32.5);;
			
			kill();
		} else
			super.collide(object);
	}

	@Override
	public void handle(double diff, GContent world) {
		if (new Random().nextInt(10) < 1)
			for (int i = 0; i < diff / 20; i++) {
				GLocation loc = getLocation().copy();

				double radius = new Random().nextDouble() * 10 - new Random().nextInt(15) + getHitRadius();
				double addX = new GVelocity(loc.getAngle(), radius).getX();
				double addY = new GVelocity(loc.getAngle(), radius).getY();
				loc.addX(addX);
				loc.addY(addY);

				loc.setAngle(getLocation().getAngle());
				loc.setAngle(loc.getInvertAngle());

				getLocation().getWorld().place(new GParticle(getColorOfTexture(), loc, new Random().nextInt(2) + 3,
						new Random().nextInt(500) + 500));

			}
		getVelocity().setPower(diff * 300);
		getLocation().addAnlge((float) (2 * diff));
		super.handle(diff, world);
	}

}
