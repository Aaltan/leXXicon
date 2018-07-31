package com.cyberg.lexxicon.saga;


import com.cyberg.lexxicon.Main;
import com.cyberg.lexxicon.environment.CrossVariables;
import com.cyberg.lexxicon.structs.LevelSagaStruct;

import processing.core.PApplet;

public class SagaGrid {

	private Main mFather;
	private SagaObjectSlot[] mSlots;
	public SagaFactory mSagaFactory;

	public SagaGrid(Main aFather, SagaFactory lvlFactory) {
		mFather = aFather;
		mSagaFactory = lvlFactory;
		mSlots = new SagaObjectSlot[9];
	}

	public void fillGrid() {
		int fromLevel = (CrossVariables.SAGA_CURRENT_PAGE - 1) * CrossVariables.SAGA_PER_PAGE + 1;
		int toLevel = fromLevel + CrossVariables.SAGA_PER_PAGE - 1;
		for (int i = fromLevel; i <= toLevel; i++) {
		  if (mSlots[i - fromLevel] != null &&
         (mSlots[i - fromLevel].getLevel() >= fromLevel && mSlots[i - fromLevel].getLevel() <= toLevel)) {
		    break;
      }
			LevelSagaStruct aLevel = mSagaFactory.getLSS(i);
			mSlots[i - fromLevel] = new SagaObjectSlot(mFather, aLevel, i);
		}
	}

	public void resetSlots() {
		mSlots = new SagaObjectSlot[9];
	}
	
	public void update(float tX, float tY) throws Exception {
		int fromLevel = (CrossVariables.SAGA_CURRENT_PAGE - 1) * CrossVariables.SAGA_PER_PAGE + 1;
		int toLevel = fromLevel + CrossVariables.SAGA_PER_PAGE - 1;
		for (int i = fromLevel; i <= toLevel; i++) {
			mSlots[i - fromLevel].update(tX, tY);
		}
	}
		
	public void updateSelected(float tX, float tY) throws Exception {
    int fromLevel = (CrossVariables.SAGA_CURRENT_PAGE - 1) * CrossVariables.SAGA_PER_PAGE + 1;
    int toLevel = fromLevel + CrossVariables.SAGA_PER_PAGE - 1;
    for (int i = fromLevel; i <= toLevel; i++) {
      mSlots[i - fromLevel].updateSelected(tX, tY);
    }
    if (CrossVariables.LEVELS_FRAMES_LEFT % 5 == 0) {
      CrossVariables.LEVELS_ANIM_SIGN_X = -CrossVariables.LEVELS_ANIM_SIGN_X;
    }
    if (CrossVariables.LEVELS_FRAMES_LEFT % 4 == 0) {
      CrossVariables.LEVELS_ANIM_SIGN_Y = -CrossVariables.LEVELS_ANIM_SIGN_Y;
    }
    CrossVariables.LEVELS_ANIM_OFFSET_X += PApplet.round((((CrossVariables.LEVELS_FRAMES_LEFT % 5) * 2) / CrossVariables.RESIZE_FACTOR_X) *
        CrossVariables.LEVELS_ANIM_SIGN_X);
    CrossVariables.LEVELS_ANIM_OFFSET_Y += PApplet.round((((CrossVariables.LEVELS_FRAMES_LEFT  % 4) * 2) / CrossVariables.RESIZE_FACTOR_Y) *
        CrossVariables.LEVELS_ANIM_SIGN_Y);
	}
		
	public SagaObjectSlot[] getSlots() {
		return mSlots;
	}

}