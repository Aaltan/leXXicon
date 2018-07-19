package com.cyberg.lexxicon.game;

import com.cyberg.lexxicon.Main;
import com.cyberg.lexxicon.environment.CrossVariables;
import com.cyberg.lexxicon.structs.BonusStruct;
import com.cyberg.lexxicon.utils.VariousUtils;

import android.util.Log;

import processing.core.PApplet;
import processing.core.PImage;

public class BonusFactory {
  
  private BonusStruct BONUS_SUGGESTION;
  private BonusStruct BONUS_BOMB;
  private BonusStruct BONUS_ICE;
  private BonusStruct BONUS_WIPE_LETTERS;
  private BonusStruct BONUS_NEW_BOARD;
  
  private Main mFather;
  
  public BonusFactory(Main aFather) {
    mFather = aFather;
    createBonuses();
  }
  
  public void createBonuses() {
    BONUS_BOMB = new BonusStruct(mFather, "bonusbomb.png", CrossVariables.BONUS_TYPE_BOMB);
    BONUS_SUGGESTION = new BonusStruct(mFather, "bonussuggestion.png", CrossVariables.BONUS_TYPE_SUGGESTION);
    BONUS_ICE = new BonusStruct(mFather, "bonusice.png", CrossVariables.BONUS_TYPE_ICE);
    BONUS_WIPE_LETTERS = new BonusStruct(mFather, "bonuswipeletters.png", CrossVariables.BONUS_TYPE_WIPE_LETTERS);
    BONUS_NEW_BOARD = new BonusStruct(mFather, "bonusnewboard.png", CrossVariables.BONUS_TYPE_NEW_BOARD);
  }
  
  public BonusStruct getBonus(String composedWord) {
  	BonusStruct aReturnValue = null;
  	int pointsValue = VariousUtils.getPoints(mFather, composedWord);
  	int bonusChecker = pointsValue * CrossVariables.BONUS_CHANCE;
    int rndBonus = PApplet.round(mFather.random(0, 1000));
    if (rndBonus <= bonusChecker) {
      int bonusRndValue = PApplet.round(mFather.random(0, (CrossVariables.BONUS_ARRAY.length - 1)));
      aReturnValue = getBonus(CrossVariables.BONUS_ARRAY[bonusRndValue]);
    }
    return aReturnValue;
  }
  
  public BonusStruct getBonus(BonusStruct aBonus) {
  	BonusStruct aReturnValue = null;
  	try {
  		aReturnValue = new BonusStruct(mFather, (PImage)aBonus.getImage().clone(), aBonus.getType());
  	}
	  catch (Exception _Ex) {
	  	Log.v("***********", "getBonus", _Ex);
	  	return null;
	  }
  	return aReturnValue;
  }
  
  public BonusStruct getBonus(int bonusType) {
  	BonusStruct aReturnValue = null;
  	try {
  		switch (bonusType) {
				case CrossVariables.BONUS_TYPE_SUGGESTION:
		  		aReturnValue = new BonusStruct(mFather, (PImage)BONUS_SUGGESTION.getImage().clone(), BONUS_SUGGESTION.getType());
					break;
  			case CrossVariables.BONUS_TYPE_BOMB:
  	  		aReturnValue = new BonusStruct(mFather, (PImage)BONUS_BOMB.getImage().clone(), BONUS_BOMB.getType());
  				break;
  			case CrossVariables.BONUS_TYPE_ICE:
  	  		aReturnValue = new BonusStruct(mFather, (PImage)BONUS_ICE.getImage().clone(), BONUS_ICE.getType());
  				break;
  			case CrossVariables.BONUS_TYPE_WIPE_LETTERS:
  	  		aReturnValue = new BonusStruct(mFather, (PImage)BONUS_WIPE_LETTERS.getImage().clone(), BONUS_WIPE_LETTERS.getType());
  				break;
  			case CrossVariables.BONUS_TYPE_NEW_BOARD:
  	  		aReturnValue = new BonusStruct(mFather, (PImage)BONUS_NEW_BOARD.getImage().clone(), BONUS_NEW_BOARD.getType());
  				break;
  		}
  	}
	  catch (Exception _Ex) {
	  	Log.v("***********", "getBonus", _Ex);
	  	return null;
	  }
  	return aReturnValue;
  }     
}