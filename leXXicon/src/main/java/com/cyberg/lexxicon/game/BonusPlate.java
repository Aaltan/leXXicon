package com.cyberg.lexxicon.game;

import processing.core.PApplet;

import com.cyberg.lexxicon.Main;
import com.cyberg.lexxicon.environment.CrossVariables;
import com.cyberg.lexxicon.structs.BonusStruct;

public class BonusPlate {
  
  private Main mFather;
  private BonusStruct mSuggestBonus;
  private BonusStruct mBombBonus;
  private BonusStruct mIceBonus;
  private BonusStruct mWipeBonus;
  private BonusStruct mNewBonus;
  
  public BonusPlate(Main father) {
  	mFather = father;
		mBombBonus = mFather.mBonusFactory.getBonus(CrossVariables.BONUS_TYPE_BOMB);
  	mSuggestBonus = mFather.mBonusFactory.getBonus(CrossVariables.BONUS_TYPE_SUGGESTION);
  	mIceBonus = mFather.mBonusFactory.getBonus(CrossVariables.BONUS_TYPE_ICE);
  	mWipeBonus = mFather.mBonusFactory.getBonus(CrossVariables.BONUS_TYPE_WIPE_LETTERS);
  	mNewBonus = mFather.mBonusFactory.getBonus(CrossVariables.BONUS_TYPE_NEW_BOARD);
  }

  public void update(float tX, float tY) throws Exception {
  	updateBonus(mBombBonus, tX, tY);
		updateBonus(mSuggestBonus, tX, tY);
  	updateBonus(mIceBonus, tX, tY);
  	updateBonus(mWipeBonus, tX, tY);
  	updateBonus(mNewBonus, tX, tY);
  }
  
  private void updateBonus(BonusStruct aBS, float tX, float tY) {  	
  	int animOffsetX = 0;
  	int animOffsetY = 0;
  	if (CrossVariables.BONUS_GRANTED == aBS.getType()) {
  		animOffsetX = CrossVariables.BONUS_ANIM_OFFSET_X;
  		animOffsetY = CrossVariables.BONUS_ANIM_OFFSET_Y;
  	}
		int standardDistance = PApplet.round(mFather.width / 5);
		int standardXOffset = PApplet.round((standardDistance - PApplet.round(CrossVariables.BONUS_STANDARD_X / CrossVariables.RESIZE_FACTOR_X)) / 2);
		int posX = (standardDistance * aBS.getType()) + standardXOffset;
		int posY = PApplet.round(CrossVariables.BONUS_POSITION_Y / CrossVariables.RESIZE_FACTOR_Y);
		int nrOfBonuses = 0;
		switch (aBS.getType()) {
			case CrossVariables.BONUS_TYPE_SUGGESTION:
				nrOfBonuses = CrossVariables.NR_BONUS_SUGGESTION;
				break;
			case CrossVariables.BONUS_TYPE_BOMB:
				nrOfBonuses = CrossVariables.NR_BONUS_BOMB;
				break;
			case CrossVariables.BONUS_TYPE_ICE:
				nrOfBonuses = CrossVariables.NR_BONUS_ICE;
				break;
			case CrossVariables.BONUS_TYPE_WIPE_LETTERS:
				nrOfBonuses = CrossVariables.NR_BONUS_WIPE_LETTERS;
				break;
			case CrossVariables.BONUS_TYPE_NEW_BOARD:
				nrOfBonuses = CrossVariables.NR_BONUS_NEW_BOARD;
				break;
		}
		mFather.pushStyle();
		mFather.image(aBS.getImage(), posX + animOffsetX, posY + animOffsetY);
		mFather.textFont(mFather.mWordFont);
		mFather.textSize(PApplet.round(CrossVariables.BONUS_FONT_SIZE / CrossVariables.RESIZE_FACTOR_X));
		mFather.textAlign(PApplet.CENTER);
		mFather.fill(50);
		int shadowOffsetX = PApplet.round(3 / CrossVariables.RESIZE_FACTOR_X);
		int shadowOffsetY = PApplet.round(3 / CrossVariables.RESIZE_FACTOR_Y);
		mFather.text("" + nrOfBonuses, PApplet.round(posX + PApplet.round(CrossVariables.BONUS_STANDARD_X / CrossVariables.RESIZE_FACTOR_X / 2) + shadowOffsetX), 
				         posY + PApplet.round(CrossVariables.BONUS_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y) +
				         PApplet.round((CrossVariables.BONUS_FONT_SIZE * 2 / 3) / CrossVariables.RESIZE_FACTOR_Y) +
				         shadowOffsetY); 
		mFather.fill(0, 255, 0);
		mFather.text("" + nrOfBonuses, PApplet.round(posX + PApplet.round(CrossVariables.BONUS_STANDARD_X / CrossVariables.RESIZE_FACTOR_X / 2)), 
        				 posY + PApplet.round(CrossVariables.BONUS_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y) + 
				         PApplet.round((CrossVariables.BONUS_FONT_SIZE * 2 / 3) / CrossVariables.RESIZE_FACTOR_Y));
		mFather.popStyle();
		if (overRect(aBS, posX, posY, tX, tY)) {
			// Do Bonus use stuff
			CrossVariables.BONUS_FRAMES_LEFT = CrossVariables.BONUS_WAIT_FRAMES;
			switch (aBS.getType()) {
				case CrossVariables.BONUS_TYPE_BOMB:
					if (CrossVariables.NR_BONUS_BOMB > 0) {
						CrossVariables.NR_BONUS_BOMB--;
						CrossVariables.BONUS_USED = CrossVariables.BONUS_TYPE_BOMB;
					}
					break;
				case CrossVariables.BONUS_TYPE_SUGGESTION:
					if (CrossVariables.NR_BONUS_SUGGESTION > 0) {
						CrossVariables.NR_BONUS_SUGGESTION--;
						CrossVariables.BONUS_USED = CrossVariables.BONUS_TYPE_SUGGESTION;
					}
					break;
				case CrossVariables.BONUS_TYPE_ICE:
					if (CrossVariables.NR_BONUS_ICE > 0) {
						CrossVariables.NR_BONUS_ICE--;
						CrossVariables.BONUS_USED = CrossVariables.BONUS_TYPE_ICE;
					}
					break;
				case CrossVariables.BONUS_TYPE_WIPE_LETTERS:
					if (CrossVariables.NR_BONUS_WIPE_LETTERS > 0) {
						CrossVariables.NR_BONUS_WIPE_LETTERS--;
						CrossVariables.BONUS_USED = CrossVariables.BONUS_TYPE_WIPE_LETTERS;
					}
					break;
				case CrossVariables.BONUS_TYPE_NEW_BOARD:
					if (CrossVariables.NR_BONUS_NEW_BOARD > 0) {
						CrossVariables.NR_BONUS_NEW_BOARD--;
						CrossVariables.BONUS_USED = CrossVariables.BONUS_TYPE_NEW_BOARD;
					}
					break;
			}
		}
  }

	private boolean overRect(BonusStruct aBS, float lX, float lY, float tX, float tY) {
		if (tX > lX && tX < (lX + aBS.getImage().width) && 
				tY > lY && tY < (lY + aBS.getImage().height)) {
			return true;
		} 
		else {
			return false;
		}
	}	  
}