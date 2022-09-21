package eu.nurkert.ImmuneTillDeath.Engine.Back;

public interface GMonster {

	public boolean hasTarget();
	public void setTarget(GObject object);
	public GObject getTarget();
}
