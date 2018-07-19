package com.cyberg.lexxicon.multitouch;

public class TapEvent extends TouchEvent {
	
	public static final int SINGLE = 0;
	public static final int DOUBLE = 1;

	public float x;
	public float y;
	public int type;

	public TapEvent(float x, float y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}

	public boolean isSingleTap() {
		return (type == SINGLE) ? true : false;
	}

	public boolean isDoubleTap() {
		return (type == DOUBLE) ? true : false;
	}

}
