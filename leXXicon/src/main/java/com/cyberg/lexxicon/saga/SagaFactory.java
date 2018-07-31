package com.cyberg.lexxicon.saga;

import java.util.ArrayList;

import android.util.Log;

import com.cyberg.lexxicon.environment.CrossVariables;
import com.cyberg.lexxicon.structs.LevelNumberStruct;
import com.cyberg.lexxicon.structs.StarStruct;
import com.cyberg.lexxicon.structs.LevelSagaStruct;

import ketai.data.KetaiSQLite;
import processing.core.PApplet;
import processing.core.PImage;

public class SagaFactory {
  
  private LevelSagaStruct mLevel;
  private PApplet mFather;
  private ArrayList<LevelNumberStruct> mLevelNumbers = new ArrayList<LevelNumberStruct>();
  private StarStruct mStar = null;
  
  public SagaFactory(PApplet aFather) {
    mFather = aFather;
    createLSS();
    createLSObj();
  }
  
  public void createLSS() {
    mLevel = new LevelSagaStruct(mFather, "LevelTile.png");
  }
  
  public void createLSObj() {
  	for (int i=0; i<10; i++) {
  		String anImage = "";
  		switch (i) {
  			case 0:
  				anImage = "0.png";
  				break;
  			case 1:
  				anImage = "1.png";
  				break;
  			case 2:
  				anImage = "2.png";
  				break;
  			case 3:
  				anImage = "3.png";
  				break;
  			case 4:
  				anImage = "4.png";
  				break;
  			case 5:
  				anImage = "5.png";
  				break;
  			case 6:
  				anImage = "6.png";
  				break;
  			case 7:
  				anImage = "7.png";
  				break;
  			case 8:
  				anImage = "8.png";
  				break;
  			case 9:
  				anImage = "9.png";
  				break;
  		}
  		mLevelNumbers.add(new LevelNumberStruct(mFather, anImage, i));
  	}
  	mStar = new StarStruct(mFather, "Star.png");
  }
    
  public LevelSagaStruct getLSS(int aLevel) {
  	LevelSagaStruct aReturnValue = null;
  	int decadi = 0;
  	int unita = aLevel;
		if (aLevel >= 10) {
			decadi = PApplet.floor(aLevel / 10);
			unita = aLevel - (decadi * 10);
		}
		ArrayList<LevelNumberStruct> numbers = getNumbers(decadi, unita);
		ArrayList<StarStruct> stars = getStars(aLevel);
		try {
			aReturnValue = new LevelSagaStruct((PImage)mLevel.getImage().clone(), numbers, stars, aLevel);
		}
	  catch (Exception _Ex) {
	  	Log.v("SagaFactory", "getLSS", _Ex);
	  	return null;
	  }
		return aReturnValue;
  }
  
  private ArrayList<LevelNumberStruct> getNumbers(int decadi, int unita) {
  	ArrayList<LevelNumberStruct> aReturnValue = new ArrayList<LevelNumberStruct>();
  	try {
	  	if (decadi > 0) {
	  		LevelNumberStruct aNS = new LevelNumberStruct((PImage)mLevelNumbers.get(decadi).getImage().clone(), decadi);
	  		aReturnValue.add(aNS);
			}
  		LevelNumberStruct aNS = new LevelNumberStruct((PImage)mLevelNumbers.get(unita).getImage().clone(), unita);
  		aReturnValue.add(aNS);
  	}
	  catch (Exception _Ex) {
	  	Log.v("SagaFactory", "getNumbers", _Ex);
	  	return null;
	  }
  	return aReturnValue;
  }  
  
  private ArrayList<StarStruct> getStars(int aLevel) {
  	ArrayList<StarStruct> aReturnValue = new ArrayList<StarStruct>();
  	try {
	    int nrOfStar = getDBLevel(aLevel);
	    for (int i=0; i<nrOfStar; i++) {
	    	StarStruct aSS = new StarStruct((PImage)mStar.getImage().clone());
	    	aReturnValue.add(aSS);
	    }
  	}
	  catch (Exception _Ex) {
	  	Log.v("LevelFactory", "getStars", _Ex);
	  	return null;
	  }
  	return aReturnValue;
  }

  private int getDBLevel(int aLevel) {
  	int nrOfStars = 0;
		CrossVariables.DB = new KetaiSQLite(mFather);
		if (CrossVariables.DB.connect()) {
			String aSQLStmt = "SELECT levelstars FROM Levels WHERE levelnumber = " + aLevel;
			CrossVariables.DB.query(aSQLStmt);
			if (CrossVariables.DB.next()) {
			  nrOfStars = CrossVariables.DB.getInt("levelstars");
			}
		}
  	return nrOfStars;
	}
}