package com.cyberg.lexxicon.levels;

import java.util.ArrayList;

import com.cyberg.lexxicon.environment.CrossVariables;
import com.cyberg.lexxicon.structs.LevelStruct;
import com.cyberg.lexxicon.structs.NumberStruct;
import com.cyberg.lexxicon.structs.StarStruct;

import processing.core.PApplet;

public class LevelObjectSlot {

	private PApplet mFather;
	private Object mObject;
	private int mLevel;

	public LevelObjectSlot(PApplet aFather, Object anObject, int aLevel) {
		mFather = aFather;
		mObject = anObject;
		mLevel = aLevel;
	}

	public Object getObject() {
		return mObject;
	}
	
	public void update(float tX, float tY) throws Exception {
		LevelStruct aLS = (LevelStruct) mObject;
		int fromLevel = (CrossVariables.LEVELS_CURRENT_PAGE - 1) * CrossVariables.LEVELS_PER_PAGE + 1;
		int toLevel = fromLevel + CrossVariables.LEVELS_PER_PAGE - 1;
		int levelNum = aLS.getLevelNum();
		int levelRow = PApplet.floor(((levelNum - 1) % 9) / 3);
		int levelCol = ((levelNum - 1) % 9) - (levelRow * 3);
		int offsetX = PApplet.round(
								 (CrossVariables.LEVEL_GRID_OFFSET_X / CrossVariables.RESIZE_FACTOR_X) +
									levelCol * (CrossVariables.LEVEL_IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X) +
									levelCol * (CrossVariables.LEVEL_GRID_SPACING_X / CrossVariables.RESIZE_FACTOR_X));
		int offsetY = PApplet.round(
								 (CrossVariables.LEVEL_GRID_OFFSET_Y / CrossVariables.RESIZE_FACTOR_Y) +
									levelRow * (CrossVariables.LEVEL_IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y) +
									levelRow * (CrossVariables.LEVEL_GRID_SPACING_Y / CrossVariables.RESIZE_FACTOR_Y));
		ArrayList<NumberStruct> nS = aLS.getNumbers();
		ArrayList<StarStruct> sS = aLS.getStars();
		if ((CrossVariables.STATUS_LEVELSCOMPLETED + 1) < levelNum) {
			mFather.tint(255, 128);
		}
		mFather.image(aLS.getImage(), offsetX, offsetY, aLS.getImageW(), aLS.getImageH());
		for (int i=0; i<nS.size(); i++) {
			float multiplier = i;
			if (nS.size() == 1) {
				multiplier = 0.5f;
			}
			mFather.image(nS.get(i).getImage(),
										offsetX + (CrossVariables.LEVEL_NUMBERS_OFFSET_X / CrossVariables.RESIZE_FACTOR_X) + (nS.get(i).getImageW() * multiplier),
										offsetY + (CrossVariables.LEVEL_NUMBERS_OFFSET_Y / CrossVariables.RESIZE_FACTOR_Y), nS.get(i).getImageW(), nS.get(i).getImageH());
		}
		for (int i=0; i<sS.size(); i++) {
			mFather.image(sS.get(i).getImage(),
										offsetX + (CrossVariables.LEVEL_STARS_OFFSET_X / CrossVariables.RESIZE_FACTOR_X) + ((sS.get(i).getImageW() + (CrossVariables.LEVEL_STARS_SPACING_X / CrossVariables.RESIZE_FACTOR_X)) * i),
										offsetY + (CrossVariables.LEVEL_STARS_OFFSET_Y / CrossVariables.RESIZE_FACTOR_Y), sS.get(i).getImageW(), sS.get(i).getImageH());
		}
		mFather.noTint();
	}
	
	public void updateSelected(float tX, float tY) throws Exception {
	}

	public boolean overRect(float tX, float tY) {
		return false;
	}

	public int getLevel() {
		return mLevel;
	}
}