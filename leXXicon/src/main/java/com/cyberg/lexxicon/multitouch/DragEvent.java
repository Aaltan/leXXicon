package com.cyberg.lexxicon.multitouch;

public class DragEvent extends TouchEvent {
	
	public float x; // position
	public float y;
	public float dx; // movement
	public float dy;
	public int numberOfPoints;

	public DragEvent(float x, float y, float dx, float dy, int n) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		numberOfPoints = n;
	}
}
