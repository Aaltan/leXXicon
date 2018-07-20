package com.cyberg.lexxicon.saga;

import java.util.ArrayList;

import com.cyberg.lexxicon.environment.CrossVariables;
import com.cyberg.lexxicon.structs.LevelSagaStruct;
import com.cyberg.lexxicon.structs.NumberStruct;
import com.cyberg.lexxicon.structs.StarStruct;

import processing.core.PApplet;

public class SagaObjectSlot {

	private PApplet mFather;
	private Object mObject;
	private int mLevel;

	public SagaObjectSlot(PApplet aFather, Object anObject, int aLevel) {
		mFather = aFather;
		mObject = anObject;
		mLevel = aLevel;
	}

	public Object getObject() {
		return mObject;
	}
	
	public void update(float tX, float tY) throws Exception {
		LevelSagaStruct aLS = (LevelSagaStruct) mObject;
		int fromLevel = (CrossVariables.SAGA_CURRENT_PAGE - 1) * CrossVariables.SAGA_PER_PAGE + 1;
		int toLevel = fromLevel + CrossVariables.SAGA_PER_PAGE - 1;
		int levelNum = aLS.getLevelNum();
		int levelRow = PApplet.floor(((levelNum - 1) % 9) / 3);
		int levelCol = ((levelNum - 1) % 9) - (levelRow * 3);
		int offsetX = PApplet.round(
								 (CrossVariables.SAGA_GRID_OFFSET_X / CrossVariables.RESIZE_FACTOR_X) +
									levelCol * (CrossVariables.SAGA_IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X) +
									levelCol * (CrossVariables.SAGA_GRID_SPACING_X / CrossVariables.RESIZE_FACTOR_X));
		int offsetY = PApplet.round(
								 (CrossVariables.SAGA_GRID_OFFSET_Y / CrossVariables.RESIZE_FACTOR_Y) +
									levelRow * (CrossVariables.SAGA_IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y) +
									levelRow * (CrossVariables.SAGA_GRID_SPACING_Y / CrossVariables.RESIZE_FACTOR_Y));
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
										offsetX + (CrossVariables.SAGA_NUMBERS_OFFSET_X / CrossVariables.RESIZE_FACTOR_X) + (nS.get(i).getImageW() * multiplier),
										offsetY + (CrossVariables.SAGA_NUMBERS_OFFSET_Y / CrossVariables.RESIZE_FACTOR_Y), nS.get(i).getImageW(), nS.get(i).getImageH());
		}
		for (int i=0; i<sS.size(); i++) {
			mFather.image(sS.get(i).getImage(),
										offsetX + (CrossVariables.SAGA_STARS_OFFSET_X / CrossVariables.RESIZE_FACTOR_X) + ((sS.get(i).getImageW() + (CrossVariables.SAGA_STARS_SPACING_X / CrossVariables.RESIZE_FACTOR_X)) * i),
										offsetY + (CrossVariables.SAGA_STARS_OFFSET_Y / CrossVariables.RESIZE_FACTOR_Y), sS.get(i).getImageW(), sS.get(i).getImageH());
		}
		mFather.noTint();
	}
	
	public void updateSelected(float tX, float tY) throws Exception {
    LevelSagaStruct aLS = (LevelSagaStruct) mObject;
    int fromLevel = (CrossVariables.SAGA_CURRENT_PAGE - 1) * CrossVariables.SAGA_PER_PAGE + 1;
    int toLevel = fromLevel + CrossVariables.SAGA_PER_PAGE - 1;
    int levelNum = aLS.getLevelNum();
    int levelRow = PApplet.floor(((levelNum - 1) % 9) / 3);
    int levelCol = ((levelNum - 1) % 9) - (levelRow * 3);
    int offsetX = PApplet.round(
        (CrossVariables.SAGA_GRID_OFFSET_X / CrossVariables.RESIZE_FACTOR_X) +
            levelCol * (CrossVariables.SAGA_IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X) +
            levelCol * (CrossVariables.SAGA_GRID_SPACING_X / CrossVariables.RESIZE_FACTOR_X));
    if (levelNum == CrossVariables.LEVELS_SELECTED_NUM) {
      offsetX += CrossVariables.LEVELS_ANIM_OFFSET_X;
    }
    int offsetY = PApplet.round(
        (CrossVariables.SAGA_GRID_OFFSET_Y / CrossVariables.RESIZE_FACTOR_Y) +
            levelRow * (CrossVariables.SAGA_IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y) +
            levelRow * (CrossVariables.SAGA_GRID_SPACING_Y / CrossVariables.RESIZE_FACTOR_Y));
    if (levelNum == CrossVariables.LEVELS_SELECTED_NUM) {
      offsetY += CrossVariables.LEVELS_ANIM_OFFSET_Y;
    }
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
          offsetX + (CrossVariables.SAGA_NUMBERS_OFFSET_X / CrossVariables.RESIZE_FACTOR_X) + (nS.get(i).getImageW() * multiplier),
          offsetY + (CrossVariables.SAGA_NUMBERS_OFFSET_Y / CrossVariables.RESIZE_FACTOR_Y), nS.get(i).getImageW(), nS.get(i).getImageH());
    }
    for (int i=0; i<sS.size(); i++) {
      mFather.image(sS.get(i).getImage(),
          offsetX + (CrossVariables.SAGA_STARS_OFFSET_X / CrossVariables.RESIZE_FACTOR_X) + ((sS.get(i).getImageW() + (CrossVariables.SAGA_STARS_SPACING_X / CrossVariables.RESIZE_FACTOR_X)) * i),
          offsetY + (CrossVariables.SAGA_STARS_OFFSET_Y / CrossVariables.RESIZE_FACTOR_Y), sS.get(i).getImageW(), sS.get(i).getImageH());
    }
    mFather.noTint();
	}

	public boolean overRect(float tX, float tY) {
		LevelSagaStruct aLS = (LevelSagaStruct) mObject;
    int levelNum = aLS.getLevelNum();
    int levelRow = PApplet.floor(((levelNum - 1) % 9) / 3);
    int levelCol = ((levelNum - 1) % 9) - (levelRow * 3);
    int offsetX = PApplet.round(
        (CrossVariables.SAGA_GRID_OFFSET_X / CrossVariables.RESIZE_FACTOR_X) +
            levelCol * (CrossVariables.SAGA_IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X) +
            levelCol * (CrossVariables.SAGA_GRID_SPACING_X / CrossVariables.RESIZE_FACTOR_X));
    int offsetY = PApplet.round(
        (CrossVariables.SAGA_GRID_OFFSET_Y / CrossVariables.RESIZE_FACTOR_Y) +
            levelRow * (CrossVariables.SAGA_IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y) +
            levelRow * (CrossVariables.SAGA_GRID_SPACING_Y / CrossVariables.RESIZE_FACTOR_Y));
		if (tX > (offsetX) && tX < (offsetX + aLS.getImage().width) &&
				tY > (offsetY) && tY < (offsetY + aLS.getImage().height)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int getLevel() {
		return mLevel;
	}
}