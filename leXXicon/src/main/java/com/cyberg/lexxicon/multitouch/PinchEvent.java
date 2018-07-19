package com.cyberg.lexxicon.multitouch;

public class PinchEvent extends TouchEvent {
	
	public float centerX;
	public float centerY;
	public float amount; // in pixels
	public int numberOfPoints;

	public PinchEvent(float centerX, float centerY, float amount, int n) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.amount = amount;
	}

}
