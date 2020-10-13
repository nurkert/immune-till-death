package de.thuGProgSS20.ImmuneTillDeath.Engine.Back.Events.Essential;

import de.thuGProgSS20.ImmuneTillDeath.Engine.Back.Events.GEvent;

public class MouseClickGEvent extends GEvent {

	int mousePressedX, mousePressedY;

	public MouseClickGEvent(double diff, int mousePressedX, int mousePressedY) {
		super(diff);
		this.mousePressedX = mousePressedX;
		this.mousePressedY = mousePressedY;
	}

	public int getMousePressedX() {
		return mousePressedX;
	}

	public int getMousePressedY() {
		return mousePressedY;
	}

	
}
