package com.cyberg.lexxicon.menu;

import com.cyberg.lexxicon.Main;
import com.cyberg.lexxicon.environment.CrossVariables;
import com.cyberg.lexxicon.structs.LetterStruct;
import com.lancer.android.processing.traer.physics.ParticleSystem;

import processing.core.PImage;

public class MenuCore {
	
	private Main mFather;
	private ParticleSystem mPS;
	private PImage mSfondo;
	private MenuGrid mMenuGrid;
	private int mMenuSelected = -1;
	
	public MenuCore(Main aFather, ParticleSystem aPS, PImage sfondo, MenuGrid menuGrid) {
		mFather = aFather;
		mPS = aPS;
		mSfondo = sfondo;
		mMenuGrid = menuGrid;
	}
	  
  public void update(float aTX, float aTY) throws Exception {
  	if (!CrossVariables.MENU_INIT) {
  		mMenuGrid.fillGrid();
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
  	mMenuGrid.update(aTX, aTY);
  	menuSelect(aTX, aTY);
  }
  
  private void drawMenuEffect(float aTX, float aTY) throws Exception {
	  mFather.image(mSfondo, 0, 0);	  
  	CrossVariables.MENU_USE_FRAMES_LEFT--;
  	if (CrossVariables.MENU_USE_FRAMES_LEFT == 0) {
  		reset();
  	}
  	else {
  		mMenuGrid.updateSelected(aTX, aTY);
  	}
  }
  
  public void reset() {
		CrossVariables.MENU_STATE = CrossVariables.MENU_BOARD;
		CrossVariables.OVERALL_STATE = mMenuSelected;
		mMenuGrid.cleanGrid();
		CrossVariables.MENU_INIT = false;
  }
  
  private void menuSelect(float aTX, float aTY) {
  	int selectedRow = -1;
  	for (int r=0; r<mMenuGrid.getRows(); r++) {
  		for (int c=0; c<mMenuGrid.getCols(); c++) {
  			if (mMenuGrid.getSlots()[r][c].overRect(aTX, aTY)) {
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
			for (int i=0; i<mMenuGrid.getCols(); i++) {
				LetterStruct aLS = (LetterStruct) mMenuGrid.getSlots()[selectedRow][i].getObject();
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
  			CrossVariables.POINTS_PLATE.setCheatMode(true);
  			break;
  		case 3:
  			mMenuSelected = CrossVariables.OVERALL_SAGA;
  			break;
  	}  	
  }    
}
