package com.cyberg.lexxicon.multitouch;

public class RotateEvent extends TouchEvent {
	
	public float centerX;
	public float centerY;
	public float angle; // delta, in radians
	public int numberOfPoints;

	public RotateEvent(float centerX, float centerY, float angle, int n) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.angle = angle;
	}
	
}
