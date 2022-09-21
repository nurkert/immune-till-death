package eu.nurkert.ImmuneTillDeath.Engine.Back.Events.Essential;

import java.util.ArrayList;

import eu.nurkert.ImmuneTillDeath.Engine.Back.GObject;
import eu.nurkert.ImmuneTillDeath.Engine.Back.Events.GEvent;

public class HandleObjectsGEvent extends GEvent {

	ArrayList<GObject> objects;
	
	public HandleObjectsGEvent(double diff, ArrayList<GObject> objects) {
		super(diff);
		this.objects = objects;
	}

	public ArrayList<GObject> getObjects() {
		return objects;
	}
}
