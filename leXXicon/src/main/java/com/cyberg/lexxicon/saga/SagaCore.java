package com.cyberg.lexxicon.saga;

import com.cyberg.lexxicon.Main;
import com.cyberg.lexxicon.environment.CrossVariables;
import com.cyberg.lexxicon.structs.ObjectFactory;

import processing.core.PApplet;
import processing.core.PImage;

public class SagaCore {
	
	private Main mFather;
	private PImage mSfondo;
	private PImage mArrowLeft;
	private PImage mArrowRight;
	private SagaGrid mSagaGrid;
	private ObjectFactory mObjFactory;
	private SagaLevelInstr mSagaLevelInstr;
	private int mActualFC = -1;
	private int mDelay = 1;

	public SagaCore(Main aFather, PImage sfondo, SagaGrid sagaGrid, ObjectFactory objFactory) {
		mFather = aFather;
		mSfondo = sfondo;
		mSagaGrid = sagaGrid;
		mObjFactory = objFactory;
    mArrowLeft = mFather.loadImage("left.png");
    mArrowLeft.resize(PApplet.round(CrossVariables.SAGA_ARROW_IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X), PApplet.round(CrossVariables.SAGA_ARROW_IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y));
    mArrowLeft.loadPixels();		
    mArrowRight = mFather.loadImage("right.png");
    mArrowRight.resize(PApplet.round(CrossVariables.SAGA_ARROW_IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X), PApplet.round(CrossVariables.SAGA_ARROW_IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y));
    mArrowRight.loadPixels();
	}
	  
  public void update(float aTX, float aTY) throws Exception {
 		mSagaGrid.fillGrid();
  	switch (CrossVariables.SAGA_STATE) {
  		case CrossVariables.SAGA_BOARD:
  			drawSagaBoard(aTX, aTY);
				if (mActualFC == -1 || mFather.frameCount > (mActualFC + mDelay)) {
					checkArrows(aTX, aTY);
				}
  			break;
  		case CrossVariables.SAGA_SELECTED_EFFECT:
  			drawSagaEffect(aTX, aTY);
				if (mActualFC == -1 || mFather.frameCount > (mActualFC + mDelay)) {
					checkArrows(aTX, aTY);
				}
  			break;
			case CrossVariables.SAGA_LEVEL_INSTRUCTION:
				drawSagaInstructions(aTX, aTY);
				break;
      case CrossVariables.SAGA_LEVEL_START_ANIM:
        drawSagaLevelStartAnim(aTX, aTY);
        break;
  	}
  }
    
  private void drawSagaBoard(float aTX, float aTY) throws Exception {
	  mFather.image(mSfondo, 0, 0);	  
  	mSagaGrid.update(aTX, aTY);
  	levelSelect(aTX, aTY);
  	drawArrows(aTX, aTY);
  }
  
  private void drawSagaEffect(float aTX, float aTY) throws Exception {
	  mFather.image(mSfondo, 0, 0);	  
  	CrossVariables.LEVELS_FRAMES_LEFT--;
  	if (CrossVariables.LEVELS_FRAMES_LEFT <= 0) {
      CrossVariables.LEVELS_ANIM_OFFSET_X = 0;
      CrossVariables.LEVELS_ANIM_OFFSET_Y = 0;
      CrossVariables.LEVELS_ANIM_SIGN_X = 1;
      CrossVariables.LEVELS_ANIM_SIGN_Y = 1;
  		showInstructions();
  	}
  	else {
  		mSagaGrid.updateSelected(aTX, aTY);
      drawArrows(aTX, aTY);
  	}
  }
  
  private void drawArrows(float aTX, float aTY) {
  	if (CrossVariables.SAGA_CURRENT_PAGE > 1) {
			mFather.image(mArrowLeft, 
									 CrossVariables.SAGA_ARROW_OFFSET_X / CrossVariables.RESIZE_FACTOR_X,
									 CrossVariables.SAGA_ARROW_OFFSET_Y / CrossVariables.RESIZE_FACTOR_Y, mArrowLeft.width, mArrowLeft.height);
  	}
  	if (CrossVariables.SAGA_CURRENT_PAGE < CrossVariables.SAGA_MAX_PAGES) {
			mFather.image(mArrowRight, 
									 mFather.sketchWidth() - mArrowRight.width - CrossVariables.SAGA_ARROW_OFFSET_X / CrossVariables.RESIZE_FACTOR_X,
									 CrossVariables.SAGA_ARROW_OFFSET_Y / CrossVariables.RESIZE_FACTOR_Y, mArrowRight.width, mArrowRight.height);
  	}
  }

  private void drawSagaInstructions(float aTX, float aTY) {
	  mFather.image(mSfondo, 0, 0);
	  mSagaLevelInstr.update(aTX, aTY);
  }

  private void drawSagaLevelStartAnim(float aTX, float aTY) {
    mFather.image(mSfondo, 0, 0);
    if (CrossVariables.LEVEL_INSTR_ANIM_PLAY_LEFT >= -1) {
      CrossVariables.LEVEL_INSTR_ANIM_PLAY_LEFT--;
      mSagaLevelInstr.updateSelected(aTX, aTY);
    }
    else {
      startLevel();
    }
  }

  public void showInstructions() {
    CrossVariables.SAGA_STATE = CrossVariables.SAGA_LEVEL_INSTRUCTION;
    mSagaLevelInstr = new SagaLevelInstr(mFather, mSfondo, CrossVariables.LEVELS_SELECTED_NUM, mObjFactory);
  }

  public void reset() {
    CrossVariables.MENU_STATE = CrossVariables.MENU_BOARD;
    CrossVariables.SAGA_STATE = CrossVariables.SAGA_BOARD;
    CrossVariables.SAGA_CURRENT_PAGE = 1;
    CrossVariables.LEVEL_INSTR_LETTERS_SHOWN = 0;
    CrossVariables.LEVEL_INIT = false;
    CrossVariables.LEVELS_SELECTED_NUM = -1;
  }

  public void startLevel() {
    CrossVariables.MENU_STATE = CrossVariables.MENU_BOARD;
    CrossVariables.SAGA_STATE = CrossVariables.SAGA_BOARD;
    CrossVariables.SAGA_CURRENT_PAGE = 1;
    CrossVariables.LEVEL_INSTR_LETTERS_SHOWN = 0;
    CrossVariables.LEVEL_INIT = false;
    CrossVariables.OVERALL_STATE = CrossVariables.OVERALL_LEVEL_MODE;
    CrossVariables.POINTS_PLATE.setCheatMode(false);
    mSagaGrid.resetSlots();
  }

  private void levelSelect(float aTX, float aTY) {
		int fromLevel = (CrossVariables.SAGA_CURRENT_PAGE - 1) * CrossVariables.SAGA_PER_PAGE + 1;
		int toLevel = fromLevel + CrossVariables.SAGA_PER_PAGE - 1;
		for (int i = fromLevel; i <= toLevel; i++) {
      if (mSagaGrid.getSlots()[i - fromLevel].overRect(aTX, aTY)) {
				if (CrossVariables.DEBUG)
					android.util.Log.d("SagaCore", "Entering level, resetting touch");

        CrossVariables.LEVELS_SELECTED_NUM = i;
        CrossVariables.SAGA_STATE = CrossVariables.SAGA_SELECTED_EFFECT;
        CrossVariables.LEVELS_FRAMES_LEFT = CrossVariables.LEVELS_ANIM_FRAMES;
				CrossVariables.resetTouchState(mFather);
        break;
      }
		}
  }
  
	private void checkArrows(float tX, float tY) {
  	float lXLeft = CrossVariables.SAGA_ARROW_OFFSET_X / CrossVariables.RESIZE_FACTOR_X;
  	float lYLeft = CrossVariables.SAGA_ARROW_OFFSET_Y / CrossVariables.RESIZE_FACTOR_Y;
  	float lXRight = mFather.sketchWidth() - mArrowRight.width - CrossVariables.SAGA_ARROW_OFFSET_X / CrossVariables.RESIZE_FACTOR_X;
  	float lYRight = CrossVariables.SAGA_ARROW_OFFSET_Y / CrossVariables.RESIZE_FACTOR_Y;
		if (tX > lXLeft && tX < (lXLeft + mArrowLeft.width) && 
				tY > lYLeft && tY < (lYLeft + mArrowLeft.height)) {
			if (CrossVariables.SAGA_CURRENT_PAGE > 1) {
				CrossVariables.SAGA_CURRENT_PAGE--;
        mActualFC = mFather.frameCount;
			}
		} 
		if (tX > lXRight && tX < (lXRight + mArrowRight.width) && 
				tY > lYRight && tY < (lYRight + mArrowRight.height)) {
			if (CrossVariables.SAGA_CURRENT_PAGE < CrossVariables.SAGA_MAX_PAGES) {
				CrossVariables.SAGA_CURRENT_PAGE++;
        mActualFC = mFather.frameCount;
			}
		} 
	}	    	
}
