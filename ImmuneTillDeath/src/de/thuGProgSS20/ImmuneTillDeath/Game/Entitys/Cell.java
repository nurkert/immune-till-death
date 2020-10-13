package de.thuGProgSS20.ImmuneTillDeath.Game.Entitys;

import java.awt.Image;
import java.util.Random;

import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.GContent;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.GEntity;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.GLocation;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.GObject;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.GVelocity;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Front.GParticle;
import de.thuGProgSS20.ImmuneTillDeath.Engine.Front.GTexture;
import de.thuGProgSS20.ImmuneTillDeath.Game.Entitys.Items.BloodCellItem;
import de.thuGProgSS20.ImmuneTillDeath.Game.Entitys.Items.SyringeItem;
import de.thuGProgSS20.ImmuneTillDeath.Game.Entitys.Mobs.Covid19;
import de.thuGProgSS20.ImmuneTillDeath.Game.Entitys.Mobs.Virus;

public class Cell extends GEntity {

	GTexture[] textures;
	boolean infected;

	public Cell(GLocation location) {
		super(location, 0, 42, EntityType.NEUTRAL);
		textures = new GTexture[2];
		textures[0] = new GTexture("/textures/infected_cell.png");
		textures[1] = new GTexture("/textures/cell.png");
		texture = textures[1];
		infected = false;
		setLayor(GLayor.MAIN4);
	}

	long lastSpawn = System.currentTimeMillis();

	@Override
	public void handle(double diff, GContent world) {
		getLocation().addAnlge((float) (0.05 * diff));
		super.handle(diff, world);
		getVelocity().setPower(0);

		for (int i = 0; i < diff * 0.5; i++) {
			GLocation loc = getLocation().copy();
			double radius = new Random().nextDouble() * 10 - new Random().nextInt(15) + getHitRadius();
			double addX = new GVelocity(loc.getAngle(), radius).getX();
			double addY = new GVelocity(loc.getAngle(), radius).getY();
			loc.addX(addX);
			loc.addY(addY);
			getLocation().getWorld().place(new GParticle(getColorOfTexture(), loc, new Random().nextInt(2) + 3,
					new Random().nextInt(500) + 500));

		}
		if (!getLocation().getWorld().isFreezed())
			if (isInfected()) {
				if (System.currentTimeMillis() - lastSpawn > 5000) {

					Random random = new Random();
					if (getLocation().getWorld().getAmount(EntityType.ENEMY) < 48
							&& getLocation().getWorld().getPlayer().getLocation().distance(getLocation()) < 256) {
						for (int i = 0; i < random.nextInt(4) + random.nextInt(4); i++) {
							GLocation loc = getLocation().copy();
							loc.add(random.nextInt(50) - 25, random.nextInt(50) - 25);

							Virus virus = new Random().nextBoolean() ? new Covid19(loc, 4) : new Virus(loc, 3);

							getLocation().getWorld().place(virus);
						}
					}
					if (random.nextInt(4) == 0 && getLocation().getWorld().getAmount(EntityType.ITEM) < 128
							&& getLocation().getWorld().getPlayer().getLocation().distance(getLocation()) < 1024) {
						Player player = getLocation().getWorld().getPlayer();

						GLocation loc = player.getLocation().copy();
						do {
							double x = getLocation().getX() + new Random().nextInt(1024) - 512;
							double y = getLocation().getY() + new Random().nextInt(1024) - 512;
							loc = new GLocation(x, y, loc.getWorld());
						} while (getLocation().distance(loc) > 512 || getLocation().distance(loc) < 64);
						SyringeItem item = new SyringeItem(loc);

						getLocation().getWorld().place(item);

					}
					lastSpawn = System.currentTimeMillis();
				}
			} else if (System.currentTimeMillis() - lastSpawn > 5000) {

				Random random = new Random();
				if (getLocation().getWorld().getAmount(EntityType.ITEM) < 128
						&& getLocation().getWorld().getPlayer().getLocation().distance(getLocation()) < 256)
					for (int i = 0; i < random.nextInt(3) + 3; i++) {
						GLocation loc = getLocation().copy();
						double radius = getHitRadius() - 7;
						double addX = new GVelocity(loc.getAngle(), radius).getX();
						double addY = new GVelocity(loc.getAngle(), radius).getY();
						loc.addX(addX);
						loc.addY(addY);
						loc.setAngle(loc.copy().getAngle());
						BloodCellItem item = new BloodCellItem(loc);

						getLocation().getWorld().place(item);
					}
				lastSpawn = System.currentTimeMillis();
			}

		super.handle(diff, world);
	}

	@Override
	public Image getTexture() {
		return super.getTexture();
	}

	@Override
	public boolean isDead() {
		return false;
	}

	@Override
	public boolean isUseless() {
		return false;
	}

	@Override
	public void collide(GObject object) {
//		toggleInfected();
		super.collide(object);
	}

	long lastToggle = -1L;

	public void toggleInfected() {
		if (System.currentTimeMillis() - lastToggle < 1000)
			return;
		else
			lastToggle = System.currentTimeMillis();

		infected = !infected;
		texture = textures[infected ? 0 : 1];

		for (int i = 0; i < 300; i++) {
			GLocation loc = getLocation().copy();
			double radius = new Random().nextDouble() * getHitRadius();
			double addX = new GVelocity(loc.getAngle(), radius).getX();
			double addY = new GVelocity(loc.getAngle(), radius).getY();
			loc.addX(addX);
			loc.addY(addY);
			getLocation().getWorld().place(new GParticle(getColorOfTexture(), loc, new Random().nextInt(2) + 1,
					new Random().nextInt(900) + 100));

		}

		if (!infected)
			lastSpawn = -1L;
	}

	public boolean isInfected() {
		return infected;
	}

}
