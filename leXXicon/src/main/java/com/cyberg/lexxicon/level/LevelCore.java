package com.cyberg.lexxicon.level;

import com.cyberg.lexxicon.Main;
import com.cyberg.lexxicon.environment.CrossVariables;
import com.cyberg.lexxicon.structs.LetterStruct;
import com.lancer.android.processing.traer.physics.ParticleSystem;

import processing.core.PImage;

public class LevelCore {

	private Main mFather;
	private ParticleSystem mPS;
	private PImage mSfondo;
	private LevelInstrGrid mLIGrid;
	private int mMenuSelected = -1;

	public LevelCore(Main aFather, ParticleSystem aPS, PImage sfondo, LevelInstrGrid liGrid) {
		mFather = aFather;
		mPS = aPS;
		mSfondo = sfondo;
		mLIGrid = liGrid;
	}
	  
  public void update(float aTX, float aTY) throws Exception {
  	if (!CrossVariables.LEVEL_INIT) {
  		mLIGrid.fillGrid();
  		CrossVariables.MENU_INIT = true;
  	}
  	mPS.tick();
  	switch (CrossVariables.MENU_STATE) {
  		case CrossVariables.MENU_BOARD:
  			drawMenuBoard(aTX, aTY);
  			break;
  		case CrossVariables.MENU_SELECTED_EFFECT:
  			drawMenuEffect(aTX, aTY);
  			break;
  	}
  }
    
  private void drawMenuBoard(float aTX, float aTY) throws Exception {
	  mFather.image(mSfondo, 0, 0);
		mLIGrid.update(aTX, aTY);
  	menuSelect(aTX, aTY);
  }
  
  private void drawMenuEffect(float aTX, float aTY) throws Exception {
	  mFather.image(mSfondo, 0, 0);	  
  	CrossVariables.MENU_USE_FRAMES_LEFT--;
  	if (CrossVariables.MENU_USE_FRAMES_LEFT == 0) {
  		reset();
  	}
  	else {
			mLIGrid.updateSelected(aTX, aTY);
  	}
  }
  
  public void reset() {
		CrossVariables.LEVEL_INIT = false;
		CrossVariables.LEVELS_SELECTED_NUM = CrossVariables.MENU_BOARD;
		CrossVariables.OVERALL_STATE = CrossVariables.OVERALL_MENU;
		mLIGrid.cleanGrid();
  }
  
  private void menuSelect(float aTX, float aTY) {
  	int selectedRow = -1;
  	for (int r=0; r<mLIGrid.getRows(); r++) {
  		for (int c=0; c<mLIGrid.getCols(); c++) {
  			if (mLIGrid.getSlots()[r][c].overRect(aTX, aTY)) {
  				selectedRow = r;
  				break;
  			}
  		}
  		if (selectedRow != -1) {
  			break;
  		}
  	}
  	if (selectedRow == 0 || selectedRow == 1 || selectedRow == 2 || selectedRow == 3) {
			// Put menu in select effect mode
			CrossVariables.MENU_STATE = CrossVariables.MENU_SELECTED_EFFECT;
			// Set Animation Frames
			CrossVariables.MENU_USE_FRAMES_LEFT = CrossVariables.MENU_USE_ANIM_FRAMES;
			// Mark all row as 'selected'
			for (int i=0; i<mLIGrid.getCols(); i++) {
				LetterStruct aLS = (LetterStruct)mLIGrid.getSlots()[selectedRow][i].getObject();
				aLS.setSelected(true);  				
				aLS.setDurationFrame(CrossVariables.OBJECTS_ANIMATION_FRAMES);
				if (!aLS.getMarked()) {
					aLS.setMarked(true);
				}
			}
  	}
  	switch (selectedRow) {
  		case 0:
  			mMenuSelected = CrossVariables.OVERALL_CREDITS;
  			break;
  		case 1:
  			mMenuSelected = CrossVariables.OVERALL_VS;
  			break;
  		case 2:
  			mMenuSelected = CrossVariables.OVERALL_INFINITE;
  			break;
  		case 3:
  			mMenuSelected = CrossVariables.OVERALL_SAGA;
  			break;
  	}  	
  }    
}
