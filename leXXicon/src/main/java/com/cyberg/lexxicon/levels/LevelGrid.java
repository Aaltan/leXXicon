package com.cyberg.lexxicon.levels;


import com.cyberg.lexxicon.Main;
import com.cyberg.lexxicon.environment.CrossVariables;
import com.cyberg.lexxicon.structs.LevelStruct;

public class LevelGrid {

	private Main mFather;
	private LevelObjectSlot[] mSlots;
	public LevelFactory mLvlFactory;

	public LevelGrid(Main aFather, LevelFactory lvlFactory) {
		mFather = aFather;
		mLvlFactory = lvlFactory;
		mSlots = new LevelObjectSlot[9];
	}

	public void fillGrid() {
		int fromLevel = (CrossVariables.LEVELS_CURRENT_PAGE - 1) * CrossVariables.LEVELS_PER_PAGE + 1;
		int toLevel = fromLevel + CrossVariables.LEVELS_PER_PAGE - 1;
		for (int i = fromLevel; i <= toLevel; i++) {
		  if (mSlots[i - fromLevel] != null &&
         (mSlots[i - fromLevel].getLevel() >= fromLevel && mSlots[i - fromLevel].getLevel() <= toLevel)) {
		    break;
      }
			LevelStruct aLevel = mLvlFactory.getLevel(i);
			mSlots[i - fromLevel] = new LevelObjectSlot(mFather, aLevel, i);
		}
	}
	
	public void update(float tX, float tY) throws Exception {
		int fromLevel = (CrossVariables.LEVELS_CURRENT_PAGE - 1) * CrossVariables.LEVELS_PER_PAGE + 1;
		int toLevel = fromLevel + CrossVariables.LEVELS_PER_PAGE - 1;
		for (int i = fromLevel; i <= toLevel; i++) {
			mSlots[i - fromLevel].update(tX, tY);
		}
	}
		
	public void updateSelected(float tX, float tY) throws Exception {
		int fromLevel = (CrossVariables.LEVELS_CURRENT_PAGE - 1) * CrossVariables.LEVELS_PER_PAGE + 1;
		int toLevel = fromLevel + CrossVariables.LEVELS_PER_PAGE - 1;
		for (int i = fromLevel; i <= toLevel; i++) {
			mSlots[i - fromLevel].updateSelected(tX, tY);
		}
	}
		
	public LevelObjectSlot[] getSlots() {
		return mSlots;
	}

}