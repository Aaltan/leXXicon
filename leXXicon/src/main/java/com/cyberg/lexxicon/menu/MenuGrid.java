package com.cyberg.lexxicon.menu;

import processing.core.PApplet;

import com.cyberg.lexxicon.Main;
import com.cyberg.lexxicon.environment.CrossVariables;
import com.cyberg.lexxicon.structs.LetterStruct;
import com.cyberg.lexxicon.structs.ObjectFactory;
import com.lancer.android.processing.traer.physics.ParticleSystem;

public class MenuGrid {

	private Main mFather;
	private ParticleSystem mPS;
	private int mNumRows;
	private int mNumCols;
	private int mLeftX;
	private int mTopY;
	private int mVSpace;
	private MenuObjectSlot[][] mSlots;
	public ObjectFactory mObjFactory;
	private int[][] mArrayMenu = { { CrossVariables.T_LETTER, CrossVariables.U_LETTER, CrossVariables.T_LETTER, CrossVariables.O_LETTER, CrossVariables.R_LETTER, CrossVariables.I_LETTER, CrossVariables.A_LETTER, CrossVariables.L_LETTER },
																 { 0, CrossVariables.V_LETTER, CrossVariables.E_LETTER, CrossVariables.R_LETTER, CrossVariables.S_LETTER, CrossVariables.U_LETTER, CrossVariables.S_LETTER, 0 },
																 { CrossVariables.I_LETTER, CrossVariables.N_LETTER, CrossVariables.F_LETTER, CrossVariables.I_LETTER, CrossVariables.N_LETTER, CrossVariables.I_LETTER, CrossVariables.T_LETTER, CrossVariables.E_LETTER },
																 { 0, 0, CrossVariables.S_LETTER, CrossVariables.A_LETTER, CrossVariables.G_LETTER, CrossVariables.A_LETTER, 0, 0 },
																 { 0, 0, 0, 0, 0, 0, 0, 0 },
																 { 0, 0, CrossVariables.M_LETTER, CrossVariables.E_LETTER, CrossVariables.N_LETTER, CrossVariables.U_LETTER, 0, 0 },
																 { 0, 0, 0, 0, 0, 0, 0, 0 },
																 { 0, 0, 0, 0, 0, 0, 0, 0 } };

	public MenuGrid(Main aFather, ParticleSystem aPS,
									ObjectFactory objFactory, int numRows, int numCols, int leftX, int topY) {
		mFather = aFather;
		mPS = aPS;
		mObjFactory = objFactory;
		mNumRows = numRows;
		mNumCols = numCols;
		mLeftX = leftX;
		mTopY = topY;
		mVSpace = PApplet.round(CrossVariables.MENU_IMAGE_STANDARD_Y_SPACE / CrossVariables.RESIZE_FACTOR_Y);
		mSlots = new MenuObjectSlot[mNumRows][mNumCols];
		for (int r = 0; r < mNumRows; r++) {
			for (int c = 0; c < mNumCols; c++) {
				mSlots[r][c] = new MenuObjectSlot(mFather, mPS, null, 0, 0, 0, mVSpace, false);
			}
		}
	}

	public void fillGrid() {
		int offsetY = mFather.height;
		for (int r = 0; r < mNumRows; r++) {
			for (int c = 0; c < mNumCols; c++) {
				LetterStruct aLetter = mObjFactory.getLetter(mArrayMenu[r][c]);
				aLetter.setImageH(PApplet.round(CrossVariables.MENU_IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y));
				aLetter.setImageW(PApplet.round(CrossVariables.MENU_IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X));
				// Metto in negativo l'offset Y, in quanto devono partire da fuori
				// schermo
				if (!mSlots[r][c].isFull()) {
					mSlots[r][c] = new MenuObjectSlot(mFather, mPS, aLetter, mTopY, 
																				    mLeftX + (c * aLetter.getImageW()),
																				    -(offsetY + (aLetter.getImageH() * 2 * r)), 
																				    mVSpace, true);
				}
			}
		}
	}
	
	public void cleanGrid() {
		for (int r = 0; r < mNumRows; r++) {
			for (int c = 0; c < mNumCols; c++) {
				mSlots[r][c].destroyParticles();
				mSlots[r][c] = new MenuObjectSlot(mFather, mPS, null, 0, 0, 0, mVSpace, false);
			}
		}
	}

	public void removeObject(int row, int col) {
		mSlots[row][col] = new MenuObjectSlot(mFather, mPS, null, 0, 0, 0, mVSpace, false);
	}
	
	public void update(float tX, float tY) throws Exception {
		for (int c = 0; c < mNumCols; c++) {
			for (int r = 0; r < mNumRows; r++) {
				if (r == 5) {
					mFather.tint(200, 200, 200);
				}
				mSlots[r][c].update(r, c, tX, tY);
			}
		}
	}
		
	public void updateSelected(float tX, float tY) throws Exception {
		for (int c = 0; c < mNumCols; c++) {
			for (int r = 0; r < mNumRows; r++) {
				if (r == 5) {
					mFather.tint(160, 160, 160);
				}
				mSlots[r][c].updateSelected(r, c, tX, tY);
			}
		}
	}
		
	public int getLeftX() {
		return mLeftX;
	}
	
	public int getTopY() {
		return mFather.height - mTopY - (mNumRows * PApplet.round(CrossVariables.MENU_IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y + mVSpace));
	}
	
	public int getGridWidth() {
		return mNumCols * PApplet.round(CrossVariables.MENU_IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X);
	}
	
	public int getGridHeight() {
		return mNumRows * PApplet.round(CrossVariables.MENU_IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y + mVSpace);
	}
	
	public MenuObjectSlot[][] getSlots() {
		return mSlots;
	}	
	
	public int getRows() {
		return mNumRows;
	}
	
	public int getCols() {
		return mNumCols;
	}	
}