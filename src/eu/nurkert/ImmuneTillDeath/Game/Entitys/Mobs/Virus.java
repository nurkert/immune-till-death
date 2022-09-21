package eu.nurkert.ImmuneTillDeath.Game.Entitys.Mobs;

import java.awt.Image;
import java.util.Random;

import eu.nurkert.ImmuneTillDeath.Engine.Back.GContent;
import eu.nurkert.ImmuneTillDeath.Engine.Back.GEntity;
import eu.nurkert.ImmuneTillDeath.Engine.Back.GLocation;
import eu.nurkert.ImmuneTillDeath.Engine.Back.GMonster;
import eu.nurkert.ImmuneTillDeath.Engine.Back.GObject;
import eu.nurkert.ImmuneTillDeath.Engine.Back.GPlayer;
import eu.nurkert.ImmuneTillDeath.Engine.Back.GVelocity;
import eu.nurkert.ImmuneTillDeath.Engine.Front.GTexture;
import eu.nurkert.ImmuneTillDeath.Game.Entitys.Cell;
import eu.nurkert.ImmuneTillDeath.Game.Entitys.EntityType;
import eu.nurkert.ImmuneTillDeath.Engine.Back.Events.GEventHandler;

public class Virus extends GEntity implements GEventHandler.GEventListener, GMonster {

	GObject target;
	float angle;

	public Virus(GLocation location, double speed) {
		super(location, speed, 7, EntityType.ENEMY);
		texture = new GTexture("/textures/viruses/virus.png");
		target = null;
		angle = location.getAngle();
		setLayor(GLayor.MAIN2);
	}

	@Override
	public void handle(double diff, GContent world) {
		if (hasTarget()) {
			double power = getVelocity().getPower();
			GVelocity velocity = new GVelocity(target.getLocation().getX() - getLocation().getX(),
					target.getLocation().getY() - getLocation().getY());
			float angle = (velocity.getAngle() - getLocation().getAngle()) / 10;
			getLocation().addAnlge(angle);
//		getVelocity().setPower(speed * diff * 175);
		}
		getVelocity().add(getLocation().getAngle(), speed * diff * 175);

		super.handle(diff, world);
	}

	

	@Override
	public Image getTexture() {
		return rotate(super.texture.getBufImg(),  angle);
	}

	@Override
	public void collide(GObject object) {
		super.collide(object);
//		System.out.println("test virus collide");
		if (object instanceof GPlayer) {
			((GPlayer) object).damage(5 + new Random().nextInt(4) + new Random().nextInt(4));
//			((GPlayer) object).damage(1000);
//			System.out.println("test asd  as d as d a sd as da d a ds");
		} else if(object instanceof Cell && !((Cell) object).isInfected()) {
			((Cell) object).toggleInfected();
			setTarget(null);
		}
	}

	@Override
	public boolean wouldCollide(GObject object) {
		if(object instanceof Cell && !((Cell) object).isInfected())
			setTarget(object);
		else if ((object instanceof GMonster && ((GMonster) object).hasTarget() && !hasTarget()
				&& getLocation().distance(object.getLocation()) < 30))
			setTarget(((GMonster) object).getTarget());
		else if (object instanceof GPlayer)
			setTarget(object);
		

		return super.wouldCollide(object);
	}

	@Override
	public boolean hasTarget() {
		return target != null;
	}

	@Override
	public void setTarget(GObject target) {
		this.target = target;
	}

	@Override
	public GObject getTarget() {
		return target;
	}

}
