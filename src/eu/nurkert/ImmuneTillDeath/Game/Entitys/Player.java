package eu.nurkert.ImmuneTillDeath.Game.Entitys;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Random;

import eu.nurkert.ImmuneTillDeath.Engine.Back.GContent;
import eu.nurkert.ImmuneTillDeath.Engine.Back.GContent.GFreezeReason;
import eu.nurkert.ImmuneTillDeath.Engine.Back.GLocation;
import eu.nurkert.ImmuneTillDeath.Engine.Back.GObject;
import eu.nurkert.ImmuneTillDeath.Engine.Back.GPlayer;
import eu.nurkert.ImmuneTillDeath.Engine.Back.GVelocity;
import eu.nurkert.ImmuneTillDeath.Engine.Back.Events.GEventHandler.GEventMethod;
import eu.nurkert.ImmuneTillDeath.Engine.Back.Events.GEventHandler.GEventMethod.GEventPriority;
import eu.nurkert.ImmuneTillDeath.Engine.Back.Events.Essential.KeyPressedGEvent;
import eu.nurkert.ImmuneTillDeath.Engine.Back.Events.Essential.MouseClickGEvent;
import eu.nurkert.ImmuneTillDeath.Engine.Front.GAnimatedTexture;
import eu.nurkert.ImmuneTillDeath.Engine.Front.GParticle;
import eu.nurkert.ImmuneTillDeath.Engine.Front.GTexture;
import eu.nurkert.ImmuneTillDeath.Game.World;
import eu.nurkert.ImmuneTillDeath.Game.Entitys.Items.Item;

public class Player extends GPlayer {
	GTexture spritze;
	long lastShot = System.currentTimeMillis();
//	private boolean isPowerModeOn;
	private long cooldown;
	private double power;
	boolean paused;
	int score;

	public Player(GLocation location, GLocation viewCenter) {
		super(location, viewCenter, 4, 9);
		texture = new GAnimatedTexture("/textures/player.png");
		setHealth(getMaxHealth());
		power = 10D;
		cooldown = 300;
		score = 0;
//		paused = Main.frame.getPanel().togglePaused();
	}

	@Override
	public void draw(BufferedImage image, ImageObserver observer, GLocation viewCenter, double screenFactor) {
		super.draw(image, observer, viewCenter, screenFactor);
	}

	@GEventMethod(ignoreCancelled = true, priority = GEventPriority.HIGHER)
	public void on(KeyPressedGEvent event) {
//		if (System.currentTimeMillis() - ts_powerModeOn > 8000)
//			this.isPowerModeOn = false;

//		System.out.println(event.getKey());

		if (event.getKeycode() == 27) {
			if (getLocation().getWorld().isFreezed())
				getLocation().getWorld().unfreeze();
			else
				getLocation().getWorld().freeze(GFreezeReason.PAUSED);
		} else if (!getLocation().getWorld().isFreezed())

			switch (event.getKey()) {
			case 'w':
				getVelocity().add(getLocation().getAngle(), speed * 3);// event.getDiff()
																		// *
																		// 1000);
				break;
			case 's':
				getVelocity().add(getLocation().getInvertAngle(), speed * 1.5);// event.getDiff()
																				// *
																				// 1000);
				break;
			case 'a':
				getLocation().addAnlge(-1 * (float) (3 * event.getDiff()));
				break;
			case 'd':
				getLocation().addAnlge((float) (3 * event.getDiff()));
				break;
			case ' ':
				if (System.currentTimeMillis() - lastShot > cooldown) {
					if (getPower() < 0.25)
						return;
					setPower(getPower() - 0.35);
					GLocation loc = this.getLocation().copy();
					loc.setAngle(getLocation().getAngle());

					double addX = new GVelocity(loc.getAngle(), 11).getX();
					double addY = new GVelocity(loc.getAngle(), 11).getY();
					loc.addX(addX);
					loc.addY(addY);

					event.getWorld().place(new Bullet(this, 
							new GLocation(loc.getX(), loc.getY(), (World) event.getWorld()).setAngle(loc.getAngle()),
							50));
					this.lastShot = System.currentTimeMillis();
				}
				break;
			default:
				break;
			}
	}

	@GEventMethod(ignoreCancelled = true, priority = GEventPriority.FOURTWENTY)
	public void on(MouseClickGEvent event) {
//		paused = Main.frame.getPanel().togglePaused();
		if (getLocation().getWorld().isFreezed()) {
			if(getLocation().getWorld().getFreezeReason() == GFreezeReason.GAMEOVER)
				resetScore();
			getLocation().getWorld().unfreeze();
		
		}else
			getLocation().getWorld().freeze(GFreezeReason.PAUSED);
	}

	public double getPower() {
		return power;
	}

	@Override
	public void handle(double diff, GContent world) {
		setPower(getPower() + diff * 0.05);
		super.handle(diff, world);
	}

	public void setPower(double power) {
		if (power < 0)
			this.power = 0;
		else if (power > 10)
			this.power = 10;
		else
			this.power = power;
	}

//	public void activatePowerMode() {
//		this.isPowerModeOn = true;
//		this.ts_powerModeOn = System.currentTimeMillis();
//
//	}

	@Override
	public void collide(GObject object) {
		super.collide(object);
		GLocation loc = getLocation().copy();

		double addX = new GVelocity(loc.getAngle(), 8).getX();
		double addY = new GVelocity(loc.getAngle(), 8).getY();
		loc.addX(addX);
		loc.addY(addY);

		if (!(object instanceof Bullet) && !(object instanceof Item)) {

			for (int i = 0; i < 20; i++)
				getLocation().getWorld()
						.place(new GParticle(getColorOfTexture(), loc, 1, new Random().nextInt(250) + 50));
		}
		super.collide(object);
	}

	@Override
	public void kill() {
		setHealth(getMaxHealth());
		setPower(10);
		for (int i = 0; i < Math.pow(getHitRadius(), 2); i++) {
			GLocation loc = getLocation().copy();
			double radius = new Random().nextDouble() * getHitRadius();
			double addX = new GVelocity(loc.getAngle(), radius).getX();
			double addY = new GVelocity(loc.getAngle(), radius).getY();
			loc.addX(addX);
			loc.addY(addY);
			loc.setAngle((float) (new Random().nextFloat() * 2 * Math.PI));
			getLocation().getWorld().place(new GParticle(getColorOfTexture(), loc, new Random().nextInt(5) + 2,
					new Random().nextInt(400) + 100));

		}

		GLocation loc = getLocation();
		while (getLocation().distance(loc) < 128) {
			double x = getLocation().getX() + new Random().nextInt(512) - 256;
			double y = getLocation().getY() + new Random().nextInt(512) - 256;
			loc = new GLocation(x, y, loc.getWorld());
		}
		teleport(loc);

		System.out.println("Score: " + getScore());
		

		
		getLocation().getWorld().freeze(GFreezeReason.GAMEOVER);
	}
	
	public void addScore() {
		score++;
	}
	
	public void resetScore() {
		score = 0;
	}
	
	public int getScore() {
		return score;
	}
}
