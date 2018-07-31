package com.cyberg.lexxicon.game;

import processing.core.PApplet;
import processing.core.PImage;

import com.cyberg.lexxicon.Main;
import com.cyberg.lexxicon.environment.CrossVariables;

public class SuggestionPlate {
  
  private int mFrameTotals;
  private int mFrameToCenter;
  private int mFramePause;
  private int mFrameToRight;
  private int mTextOffsetY;
  private String mWord;
  private Main mFather;
  private int mFrameElapsed;
  private int mPosY = -1;
  private int mStartX = -1;
  private int mStepX = -1;
  private PImage mImage;
  private boolean mOneMoreStep = true;
  
  public SuggestionPlate(Main father) {
  	mFather = father;
    mImage = mFather.loadImage("suggplate.png");
    mImage.resize(PApplet.round(CrossVariables.SUGGESTION_PLATE_X / CrossVariables.RESIZE_FACTOR_X), PApplet.round(CrossVariables.SUGGESTION_PLATE_Y / CrossVariables.RESIZE_FACTOR_Y));
    mImage.loadPixels(); 
  }

  public void initializeAnim(int frameToCenter, int framePause, int frameToRight, String word) {
    mFrameToCenter = frameToCenter;
    mFramePause = framePause;
    mFrameToRight = frameToRight;
    mFrameTotals = mFrameToCenter + mFramePause + mFrameToRight; 
    mWord = word;
    mFrameElapsed = 0;
    mPosY = PApplet.round((mFather.displayHeight - mImage.height) / 2);
    mTextOffsetY = PApplet.round(CrossVariables.SUGGESTION_OFFSET_Y / CrossVariables.RESIZE_FACTOR_Y);
    mStartX = -mImage.width;
    mStepX = PApplet.round((mFather.displayWidth + mImage.width) / (mFrameToCenter + mFrameToRight));
    mOneMoreStep = true;
  }

  public void initializeStatic(int posY) {
    mStartX = PApplet.round((mFather.displayWidth - mImage.width) / 2);
  }

  public void update() throws Exception {
    if (CrossVariables.OVERALL_STATE == CrossVariables.OVERALL_LEVEL_MODE &&
        (CrossVariables.LEVEL_TYPE_ACTUAL_GAME == CrossVariables.LEVEL_TYPE_FIND ||
         CrossVariables.LEVEL_TYPE_ACTUAL_GAME == CrossVariables.LEVEL_TYPE_BINARY ||
         CrossVariables.LEVEL_TYPE_ACTUAL_GAME == CrossVariables.LEVEL_TYPE_MATH) &&
        CrossVariables.GAME_STATE != CrossVariables.GAME_WIN &&
        CrossVariables.GAME_STATE != CrossVariables.GAME_OVER) {
      updateStatic();
    }
    else {
      updateAnim();
    }
  }

  private void updateStatic() throws Exception {
    mFather.pushStyle();
    mFather.image(mImage, mStartX, 0);
    mFather.textFont(mFather.mWordFont);
    mFather.textSize(PApplet.round(CrossVariables.WORD_FONT_SIZE / CrossVariables.RESIZE_FACTOR_X));
    mFather.textAlign(PApplet.CENTER);
    mFather.fill(50);
    mFather.text(CrossVariables.LEVEL_WORD_TO_FIND.toUpperCase(), mFather.width / 2,
        PApplet.round((CrossVariables.WORD_FIND_POSITION_Y + 5) / CrossVariables.RESIZE_FACTOR_Y));
    mFather.fill(0, 255, 0);
    mFather.text(CrossVariables.LEVEL_WORD_TO_FIND.toUpperCase(), mFather.width / 2,
        PApplet.round(CrossVariables.WORD_FIND_POSITION_Y / CrossVariables.RESIZE_FACTOR_Y));
    mFather.popStyle();
  }

  private void updateAnim()  throws Exception {
  	mFrameElapsed++;
  	if (mFrameElapsed < mFrameTotals) {
  		mFather.pushStyle();
  		mFather.image(mImage, mStartX, mPosY);
			mFather.textFont(mFather.mWordFont);
			mFather.textSize(PApplet.round(CrossVariables.WORD_FONT_SIZE / CrossVariables.RESIZE_FACTOR_X));
			mFather.textAlign(PApplet.CENTER);
  		mFather.fill(50);
			mFather.text(mWord, mStartX + (mImage.width) / 2, mPosY + (mImage.height / 2) + mTextOffsetY + (5 / CrossVariables.RESIZE_FACTOR_X));
			mFather.fill(0, 255, 0);
			mFather.text(mWord, mStartX + (mImage.width) / 2, mPosY + (mImage.height / 2) + mTextOffsetY);
  		mFather.popStyle();
  		if (mFrameElapsed < mFrameToCenter ||
  				mFrameElapsed > (mFrameToCenter + mFramePause)) {
  			mStartX += mStepX;
  		}
  		else if (mOneMoreStep) {
  			mStartX += mStepX;
  			mOneMoreStep = false;
  		}
  	}
  }
}