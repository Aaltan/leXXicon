package com.cyberg.lexxicon.levels;

import com.cyberg.lexxicon.Main;
import com.cyberg.lexxicon.environment.CrossVariables;

import processing.core.PApplet;
import processing.core.PImage;

public class LevelsCore {
	
	private Main mFather;
	private PImage mSfondo;
	private PImage mArrowLeft;
	private PImage mArrowRight;
	private LevelGrid mLevelGrid;
	
	public LevelsCore(Main aFather, PImage sfondo, LevelGrid levelGrid) {
		mFather = aFather;
		mSfondo = sfondo;
		mLevelGrid = levelGrid;		
    mArrowLeft = mFather.loadImage("left.png");
    mArrowLeft.resize(PApplet.round(CrossVariables.ARROW_IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X), PApplet.round(CrossVariables.ARROW_IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y));
    mArrowLeft.loadPixels();		
    mArrowRight = mFather.loadImage("right.png");
    mArrowRight.resize(PApplet.round(CrossVariables.ARROW_IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X), PApplet.round(CrossVariables.ARROW_IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y));
    mArrowRight.loadPixels();
	}
	  
  public void update(float aTX, float aTY) throws Exception {
 		mLevelGrid.fillGrid();
  	switch (CrossVariables.LEVELS_STATE) {
  		case CrossVariables.LEVELS_BOARD:
  			drawLevelBoard(aTX, aTY);
  			break;
  		case CrossVariables.LEVELS_SELECTED_EFFECT:
  			drawLevelEffect(aTX, aTY);
  			break;
  	}
  	if (CrossVariables.LEVELS_ARROWS_FRAME_LEFT <= 0) {
			checkArrows(aTX, aTY);
		}
		else {
  		CrossVariables.LEVELS_ARROWS_FRAME_LEFT--;
		}
  }
    
  private void drawLevelBoard(float aTX, float aTY) throws Exception {
	  mFather.image(mSfondo, 0, 0);	  
  	mLevelGrid.update(aTX, aTY);
  	levelSelect(aTX, aTY);
  	drawArrows(aTX, aTY);
  }
  
  private void drawLevelEffect(float aTX, float aTY) throws Exception {
	  mFather.image(mSfondo, 0, 0);	  
  	CrossVariables.LEVELS_USE_FRAMES_LEFT--;
  	if (CrossVariables.LEVELS_USE_FRAMES_LEFT == 0) {
  		reset();
  	}
  	else {
  		mLevelGrid.updateSelected(aTX, aTY);
  	}
  }
  
  private void drawArrows(float aTX, float aTY) {
  	if (CrossVariables.LEVELS_CURRENT_PAGE > 1) {
			mFather.image(mArrowLeft, 
									 CrossVariables.ARROW_OFFSET_X / CrossVariables.RESIZE_FACTOR_X,  
									 CrossVariables.ARROW_OFFSET_Y / CrossVariables.RESIZE_FACTOR_Y, mArrowLeft.width, mArrowLeft.height);
  	}
  	if (CrossVariables.LEVELS_CURRENT_PAGE < CrossVariables.LEVELS_MAX_PAGES) {
			mFather.image(mArrowRight, 
									 mFather.sketchWidth() - mArrowRight.width - CrossVariables.ARROW_OFFSET_X / CrossVariables.RESIZE_FACTOR_X,  
									 CrossVariables.ARROW_OFFSET_Y / CrossVariables.RESIZE_FACTOR_Y, mArrowRight.width, mArrowRight.height);
  	}
  }
  
  public void reset() {
		CrossVariables.LEVELS_STATE = CrossVariables.LEVELS_BOARD;
		CrossVariables.OVERALL_STATE = CrossVariables.OVERALL_MENU;
		CrossVariables.LEVELS_INIT = false;
  }
  
  private void levelSelect(float aTX, float aTY) {
  	
  }
  
	private void checkArrows(float tX, float tY) {
  	float lXLeft = CrossVariables.ARROW_OFFSET_X / CrossVariables.RESIZE_FACTOR_X;
  	float lYLeft = CrossVariables.ARROW_OFFSET_Y / CrossVariables.RESIZE_FACTOR_Y;
  	float lXRight = mFather.sketchWidth() - mArrowRight.width - CrossVariables.ARROW_OFFSET_X / CrossVariables.RESIZE_FACTOR_X;
  	float lYRight = CrossVariables.ARROW_OFFSET_Y / CrossVariables.RESIZE_FACTOR_Y;
		if (tX > lXLeft && tX < (lXLeft + mArrowLeft.width) && 
				tY > lYLeft && tY < (lYLeft + mArrowLeft.height)) {
			if (CrossVariables.LEVELS_CURRENT_PAGE > 1) {
				CrossVariables.LEVELS_CURRENT_PAGE--;
				CrossVariables.LEVELS_ARROWS_FRAME_LEFT = CrossVariables.LEVELS_ARROWS_DELAY;
			}
		} 
		if (tX > lXRight && tX < (lXRight + mArrowRight.width) && 
				tY > lYRight && tY < (lYRight + mArrowRight.height)) {
			if (CrossVariables.LEVELS_CURRENT_PAGE < CrossVariables.LEVELS_MAX_PAGES) {
				CrossVariables.LEVELS_CURRENT_PAGE++;
				CrossVariables.LEVELS_ARROWS_FRAME_LEFT = CrossVariables.LEVELS_ARROWS_DELAY;
			}
		} 
	}	    	
}
