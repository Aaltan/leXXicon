package com.cyberg.lexxicon.multitouch;

import processing.core.PApplet;

public class TouchPoint {
	public float x;
	public float y;
	public float px;
	public float py;
	public int id;

	// used for gesture detection
	public float angle;
	public float oldAngle;
	public float pinch;
	public float oldPinch;

	// -------------------------------------------------------------------------------------
	public TouchPoint(float x, float y, int id) {
		this.x = x;
		this.y = y;
		this.px = x;
		this.py = y;
		this.id = id;
	}

	// -------------------------------------------------------------------------------------
	public void update(float x, float y) {
		px = this.x;
		py = this.y;
		this.x = x;
		this.y = y;
	}

	// -------------------------------------------------------------------------------------
	public void initGestureData(float cx, float cy) {
		pinch = oldPinch = PApplet.dist(x, y, cx, cy);
		angle = oldAngle = PApplet.atan2((y - cy), (x - cx));
	}

	// -------------------------------------------------------------------------------------
	// delta x -- int to get rid of some noise
	public int dx() {
		return PApplet.parseInt(x - px);
	}

	// -------------------------------------------------------------------------------------
	// delta y -- int to get rid of some noise
	public int dy() {
		return PApplet.parseInt(y - py);
	}
	
	// -------------------------------------------------------------------------------------
	public void setAngle(float angle) {
		oldAngle = this.angle;
		this.angle = angle;
	}

	// -------------------------------------------------------------------------------------
	public void setPinch(float pinch) {
		oldPinch = this.pinch;
		this.pinch = pinch;
	}
}
