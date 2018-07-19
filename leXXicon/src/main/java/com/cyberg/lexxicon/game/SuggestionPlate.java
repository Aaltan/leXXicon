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
    mStartX = -mImage.width;
    mStepX = PApplet.round((mFather.displayWidth + mImage.width) / (mFrameToCenter + mFrameToRight));
    mOneMoreStep = true;
  }

  public void update() throws Exception {
  	mFrameElapsed++;
  	if (mFrameElapsed < mFrameTotals) {
  		mFather.pushStyle();
  		mFather.image(mImage, mStartX, mPosY);
			mFather.textFont(mFather.mWordFont);
			mFather.textSize(PApplet.round(CrossVariables.WORD_FONT_SIZE / CrossVariables.RESIZE_FACTOR_X));
			mFather.textAlign(PApplet.CENTER);
  		mFather.fill(50);
			mFather.text(mWord, mStartX + (mImage.width) / 2, mPosY + (mImage.height / 2) + (5 / CrossVariables.RESIZE_FACTOR_X));
			mFather.fill(0, 255, 0);
			mFather.text(mWord, mStartX + (mImage.width) / 2, mPosY + (mImage.height / 2));
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