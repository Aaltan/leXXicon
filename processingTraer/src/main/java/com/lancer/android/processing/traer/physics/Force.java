/*
 * May 29, 2005
 */

package com.lancer.android.processing.traer.physics;

/**
 * @author jeffrey traer bernstein
 *
 */
public interface Force
{
	public void turnOn();
	public void turnOff();
	public boolean isOn();
	public boolean isOff();
	public void apply();
}
