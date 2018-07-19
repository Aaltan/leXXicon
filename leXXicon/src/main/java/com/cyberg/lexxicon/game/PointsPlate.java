package com.cyberg.lexxicon.game;

import processing.core.PApplet;

import com.cyberg.lexxicon.Main;
import com.cyberg.lexxicon.environment.CrossVariables;

public class PointsPlate {
  
  private Main mFather;
  
  public PointsPlate(Main father) {
  	mFather = father;
  }

  public void update(float tX, float tY) throws Exception {
		mFather.pushStyle();
		mFather.textFont(mFather.mWordFont);
		mFather.textSize(PApplet.round(CrossVariables.POINTS_FONT_SIZE / CrossVariables.RESIZE_FACTOR_X));
		mFather.textAlign(PApplet.LEFT);
		mFather.fill(50);
		mFather.text("Punti:" + CrossVariables.POINTS_EARNED, PApplet.round((CrossVariables.POINTS_POSITION_X) / CrossVariables.RESIZE_FACTOR_X), 
								 PApplet.round((CrossVariables.POINTS_POSITION_Y + 5) / CrossVariables.RESIZE_FACTOR_Y));
		mFather.fill(0, 255, 0);
		mFather.text("Punti:" + CrossVariables.POINTS_EARNED, PApplet.round((CrossVariables.POINTS_POSITION_X) / CrossVariables.RESIZE_FACTOR_X), 
								 PApplet.round(CrossVariables.POINTS_POSITION_Y / CrossVariables.RESIZE_FACTOR_Y));
		mFather.textAlign(PApplet.RIGHT);
		mFather.fill(50);
		mFather.text("Possibili:" + CrossVariables.foundCount, mFather.width - PApplet.round((CrossVariables.POINTS_POSITION_X) / CrossVariables.RESIZE_FACTOR_X), 
				 				 PApplet.round((CrossVariables.POINTS_POSITION_Y + 5) / CrossVariables.RESIZE_FACTOR_Y));
		mFather.fill(0, 255, 0);
		mFather.text("Possibili:" + CrossVariables.foundCount, mFather.width - PApplet.round((CrossVariables.POINTS_POSITION_X) / CrossVariables.RESIZE_FACTOR_X), 
				 				 PApplet.round(CrossVariables.POINTS_POSITION_Y / CrossVariables.RESIZE_FACTOR_Y));
		mFather.popStyle();
		if (overRect(0, PApplet.round(CrossVariables.POINTS_POSITION_Y / CrossVariables.RESIZE_FACTOR_Y), tX, tY)) {
			CrossVariables.GAME_STATE = CrossVariables.GAME_WORD_LIST;
		}
  }

	private boolean overRect(float lX, float lY, float tX, float tY) {
		if (tX > lX && tX < mFather.width && 
				tY > lY && tY < (lY + (30 / CrossVariables.RESIZE_FACTOR_Y))) {
			return true;
		} 
		else {
			return false;
		}
	}	  
}