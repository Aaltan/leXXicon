package com.cyberg.lexxicon.game;

import processing.core.PApplet;

import com.cyberg.lexxicon.Main;
import com.cyberg.lexxicon.environment.CrossVariables;

public class WordPlate {
  
  private int mFrameTicks;
  private String mWord;
  private Main mFather;
  private WordStatus mStatus;
  private int mFrameElapsed;

  public enum WordStatus {
    CORRECT,      // Verde
    ERROR,        // Rosso
    TOO_SHORT     // Grigio
  }

  // MODIFICA: Nuovo costruttore con WordStatus
  public WordPlate(Main father, int frameTicks, String word, WordStatus status) {
    mFather = father;
    mFrameTicks = frameTicks;
    mWord = word;
    mStatus = status;
    mFrameElapsed = 0;
  }
  public WordPlate(Main father, int frameTicks, String word, boolean error) {
    this(father, frameTicks, word, error ? WordStatus.ERROR : WordStatus.CORRECT);
  }

  public void update() throws Exception {
  	mFrameElapsed++;
  	if (mFrameElapsed < mFrameTicks) {
  		mFather.pushStyle();
			mFather.textFont(mFather.mWordFont);
			mFather.textSize(PApplet.round(CrossVariables.WORD_FONT_SIZE / CrossVariables.RESIZE_FACTOR_X));
			mFather.textAlign(PApplet.CENTER);
  		mFather.fill(50);
			mFather.text(chooseTypeGameView(mWord), mFather.width / 2, PApplet.round((CrossVariables.WORD_POSITION_Y + 5) / CrossVariables.RESIZE_FACTOR_Y));
      // Colore basato sul tipo di stato
      switch (mStatus) {
        case CORRECT:
          mFather.fill(0, 255, 0);  // Verde
          break;
        case ERROR:
          mFather.fill(255, 0, 0);  // Rosso
          break;
        case TOO_SHORT:
          mFather.fill(150, 150, 150);  // Grigio
          break;
      }
      mFather.text(chooseTypeGameView(mWord), mFather.width / 2, PApplet.round(CrossVariables.WORD_POSITION_Y / CrossVariables.RESIZE_FACTOR_Y));
  		mFather.popStyle();
  	}
  }

  private String chooseTypeGameView(String aString) {
    String aReturnValue = aString;
    switch (CrossVariables.LEVEL_TYPE_ACTUAL_GAME) {
      case CrossVariables.LEVEL_TYPE_MATH:
        aReturnValue = mathGameView(aString);
        break;
      default:
        break;
    }
    return aReturnValue;
  }

  private String mathGameView(String aString) {
    String aReturnValue = "";
    String mathSign = "";
    switch (CrossVariables.MATH_ACTUAL_MODE) {
      case CrossVariables.MATH_MODE_ADD:
        mathSign = "+";
        break;
      case CrossVariables.MATH_MODE_SUBTRACT:
        mathSign = "-";
        break;
      case CrossVariables.MATH_MODE_MULTIPY:
        mathSign = "*";
        break;
      case CrossVariables.MATH_MODE_DIVIDE:
        mathSign = "/";
        break;
    }
    for (int i=0; i<aString.length(); i++) {
      if (i==0) {
        aReturnValue += aString.substring(i, i+1);
      }
      else {
        aReturnValue += mathSign + aString.substring(i, i+1);
      }
    }
    return aReturnValue;
  }
}