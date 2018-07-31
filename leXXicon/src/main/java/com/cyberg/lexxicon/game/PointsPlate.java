package com.cyberg.lexxicon.game;

import processing.core.PApplet;

import com.cyberg.lexxicon.Main;
import com.cyberg.lexxicon.R;
import com.cyberg.lexxicon.environment.CrossVariables;

import java.math.BigDecimal;

public class PointsPlate {
  
  private Main mFather;
  private boolean mCheatMode = false;

	public PointsPlate(Main father) {
  	mFather = father;
  }

  public void setCheatMode(boolean aValue) {
    mCheatMode = aValue;
  }

  private boolean getCheatMode() {
    return mCheatMode;
  }

  public void update (float tX, float tY) throws Exception {
	  if (CrossVariables.OVERALL_STATE == CrossVariables.OVERALL_INFINITE) {
	    updateInfinite(tX, tY);
    }
    else {
	    updateLevels(tX, tY);
    }
  }

  public void updateLevels(float tX, float tY) throws Exception {
		mFather.pushStyle();
		BigDecimal timeLeft = new BigDecimal(CrossVariables.TIMEOUT_SAGA_MODE_TIME_LEFT / 1000).setScale(0, BigDecimal.ROUND_HALF_UP);
		String timerString = mFather.getResources().getString(R.string.timer) + timeLeft.toPlainString();
		mFather.textFont(mFather.mWordFont);
		mFather.textSize(PApplet.round(CrossVariables.POINTS_FONT_SIZE / CrossVariables.RESIZE_FACTOR_X));
		mFather.textAlign(PApplet.LEFT);
		mFather.fill(50);
		mFather.text(timerString, PApplet.round((CrossVariables.POINTS_POSITION_X) / CrossVariables.RESIZE_FACTOR_X),
								 PApplet.round((CrossVariables.POINTS_POSITION_Y + 5) / CrossVariables.RESIZE_FACTOR_Y));
		mFather.fill(0, 255, 0);
		mFather.text(timerString, PApplet.round((CrossVariables.POINTS_POSITION_X) / CrossVariables.RESIZE_FACTOR_X),
								 PApplet.round(CrossVariables.POINTS_POSITION_Y / CrossVariables.RESIZE_FACTOR_Y));
		mFather.textAlign(PApplet.RIGHT);
		mFather.fill(50);
		mFather.text(mFather.getResources().getString(R.string.mancanti) + CrossVariables.WORDS_LEFT_TO_COMPOSE, mFather.width - PApplet.round((CrossVariables.POINTS_POSITION_X) / CrossVariables.RESIZE_FACTOR_X),
				 				 PApplet.round((CrossVariables.POINTS_POSITION_Y + 5) / CrossVariables.RESIZE_FACTOR_Y));
		mFather.fill(0, 255, 0);
		mFather.text(mFather.getResources().getString(R.string.mancanti) + CrossVariables.WORDS_LEFT_TO_COMPOSE, mFather.width - PApplet.round((CrossVariables.POINTS_POSITION_X) / CrossVariables.RESIZE_FACTOR_X),
				 				 PApplet.round(CrossVariables.POINTS_POSITION_Y / CrossVariables.RESIZE_FACTOR_Y));
		mFather.popStyle();
		if (mCheatMode) {
      if (overRect(0, PApplet.round(CrossVariables.POINTS_POSITION_Y / CrossVariables.RESIZE_FACTOR_Y), tX, tY)) {
        CrossVariables.GAME_STATE = CrossVariables.GAME_WORD_LIST;
      }
    }
  }

  public void updateInfinite(float tX, float tY) throws Exception {
    mFather.pushStyle();
    mFather.textFont(mFather.mWordFont);
    mFather.textSize(PApplet.round(CrossVariables.POINTS_FONT_SIZE / CrossVariables.RESIZE_FACTOR_X));
    mFather.textAlign(PApplet.LEFT);
    mFather.fill(50);
    mFather.text(mFather.getResources().getString(R.string.punti) + CrossVariables.POINTS_EARNED, PApplet.round((CrossVariables.POINTS_POSITION_X) / CrossVariables.RESIZE_FACTOR_X),
        PApplet.round((CrossVariables.POINTS_POSITION_Y + 5) / CrossVariables.RESIZE_FACTOR_Y));
    mFather.fill(0, 255, 0);
    mFather.text(mFather.getResources().getString(R.string.punti) + CrossVariables.POINTS_EARNED, PApplet.round((CrossVariables.POINTS_POSITION_X) / CrossVariables.RESIZE_FACTOR_X),
        PApplet.round(CrossVariables.POINTS_POSITION_Y / CrossVariables.RESIZE_FACTOR_Y));
    mFather.textAlign(PApplet.RIGHT);
    mFather.fill(50);
    mFather.text(mFather.getResources().getString(R.string.possibili) + CrossVariables.foundCount, mFather.width - PApplet.round((CrossVariables.POINTS_POSITION_X) / CrossVariables.RESIZE_FACTOR_X),
        PApplet.round((CrossVariables.POINTS_POSITION_Y + 5) / CrossVariables.RESIZE_FACTOR_Y));
    mFather.fill(0, 255, 0);
    mFather.text(mFather.getResources().getString(R.string.possibili) + CrossVariables.foundCount, mFather.width - PApplet.round((CrossVariables.POINTS_POSITION_X) / CrossVariables.RESIZE_FACTOR_X),
        PApplet.round(CrossVariables.POINTS_POSITION_Y / CrossVariables.RESIZE_FACTOR_Y));
    mFather.popStyle();
    if (mCheatMode) {
      if (overRect(0, PApplet.round(CrossVariables.POINTS_POSITION_Y / CrossVariables.RESIZE_FACTOR_Y), tX, tY)) {
        CrossVariables.GAME_STATE = CrossVariables.GAME_WORD_LIST;
      }
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