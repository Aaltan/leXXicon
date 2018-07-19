package com.cyberg.lexxicon.multitouch;

import processing.core.PVector;

public class FlickEvent extends TouchEvent {
	public float x;
	public float y;
	public PVector velocity;

	public FlickEvent(float x, float y, PVector velocity) {
		this.x = x;
		this.y = y;
		this.velocity = velocity;
	}
}
