package com.cyberg.lexxicon.testutils;

import processing.core.PApplet;

import com.cyberg.lexxicon.Main;
import com.cyberg.lexxicon.environment.CrossVariables;

public class DisplayWords {
  
  private Main mFather;
  
  public DisplayWords(Main father) {
  	mFather = father;
  }

  public void update() throws Exception {  	
		mFather.pushStyle();
		mFather.textFont(mFather.mWordFont);
		mFather.textSize(PApplet.round(30 / CrossVariables.RESIZE_FACTOR_X));
		mFather.textAlign(PApplet.LEFT);
		int w = 0;
		int y = 40;
		String riga = "";
		mFather.fill(0);
    for (int i=0; i<CrossVariables.foundCount; i++) {
      if ((w % 7) == 0) {
    		mFather.text(riga, 10, y);
        y+= PApplet.round(20 / CrossVariables.RESIZE_FACTOR_Y);
        riga=CrossVariables.getWord(CrossVariables.foundWords[i]) + ", ";
      } 
      else {
        riga += CrossVariables.getWord(CrossVariables.foundWords[i]) + ", ";
      }
      w++;
    }
    if ((w % 7) != 0) {
  		mFather.text(riga, 10, y);
    }
		mFather.popStyle();
  }
}