package com.cyberg.lexxicon.level;

import com.cyberg.lexxicon.Main;
import com.cyberg.lexxicon.environment.CrossVariables;
import com.cyberg.lexxicon.menu.MenuObjectSlot;
import com.cyberg.lexxicon.structs.LetterStruct;
import com.cyberg.lexxicon.structs.ObjectFactory;
import com.lancer.android.processing.traer.physics.ParticleSystem;

import processing.core.PApplet;

public class LevelInstrGrid {

	private Main mFather;
	private ParticleSystem mPS;
	private int mNumRows;
	private int mNumCols;
	private int mLeftX;
	private int mTopY;
	private int mVSpace;
	private LevelInstrObjectSlot[][] mSlots;
	public ObjectFactory mObjFactory;
	private int[][] mArrayInst = { { CrossVariables.R_LETTER, CrossVariables.O_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER,CrossVariables.EMPTY_LETTER,CrossVariables.EMPTY_LETTER,CrossVariables.EMPTY_LETTER,CrossVariables.EMPTY_LETTER,CrossVariables.EMPTY_LETTER,CrossVariables.EMPTY_LETTER },
																 { CrossVariables.R_LETTER, CrossVariables.E_LETTER, CrossVariables.A_LETTER, CrossVariables.C_LETTER, CrossVariables.H_LETTER, CrossVariables.E_LETTER, CrossVariables.S_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.Z_LETTER, CrossVariables.E_LETTER},
																 { CrossVariables.F_LETTER, CrossVariables.O_LETTER, CrossVariables.R_LETTER, CrossVariables.E_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.T_LETTER, CrossVariables.I_LETTER, CrossVariables.M_LETTER, CrossVariables.E_LETTER, CrossVariables.R_LETTER},
													 			 { CrossVariables.E_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.W_LETTER, CrossVariables.O_LETTER, CrossVariables.R_LETTER, CrossVariables.D_LETTER, CrossVariables.S_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.B_LETTER, CrossVariables.E_LETTER},
																 { CrossVariables.M_LETTER, CrossVariables.P_LETTER, CrossVariables.O_LETTER, CrossVariables.S_LETTER, CrossVariables.E_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.T_LETTER, CrossVariables.H_LETTER, CrossVariables.R_LETTER, CrossVariables.E_LETTER},
															   { CrossVariables.E_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.L_LETTER, CrossVariables.E_LETTER, CrossVariables.V_LETTER, CrossVariables.E_LETTER, CrossVariables.L_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.C_LETTER, CrossVariables.O_LETTER},
																 { CrossVariables.T_LETTER, CrossVariables.O_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.C_LETTER, CrossVariables.O_LETTER, CrossVariables.M_LETTER, CrossVariables.P_LETTER, CrossVariables.L_LETTER, CrossVariables.E_LETTER, CrossVariables.T_LETTER},
																 { CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER },
																 { CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER },
																 { CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER, CrossVariables.EMPTY_LETTER } };

	public LevelInstrGrid(Main aFather, ParticleSystem aPS,
												ObjectFactory objFactory, int numRows, int numCols, int leftX, int topY) {
		mFather = aFather;
		mPS = aPS;
		mObjFactory = objFactory;
		mNumRows = numRows;
		mNumCols = numCols;
		mLeftX = leftX;
		mTopY = topY;
		mVSpace = PApplet.round(CrossVariables.LEVEL_INSTR_IMAGE_STANDARD_Y_SPACE / CrossVariables.RESIZE_FACTOR_Y);
		mSlots = new LevelInstrObjectSlot[mNumRows][mNumCols];
		for (int r = 0; r < mNumRows; r++) {
			for (int c = 0; c < mNumCols; c++) {
				mSlots[r][c] = new LevelInstrObjectSlot(mFather, mPS, null, 0, 0, 0, mVSpace, false);
			}
		}
	}

	public void fillGrid() {
		int offsetY = mFather.height;
		for (int r = 0; r < mNumRows; r++) {
			for (int c = 0; c < mNumCols; c++) {
				LetterStruct aLetter = mObjFactory.getLetter(mArrayInst[r][c]);
				aLetter.setImageH(PApplet.round(CrossVariables.LEVEL_INSTR_IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y));
				aLetter.setImageW(PApplet.round(CrossVariables.LEVEL_INSTR_IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X));
				// Metto in negativo l'offset Y, in quanto devono partire da fuori
				// schermo
				if (!mSlots[r][c].isFull()) {
					mSlots[r][c] = new LevelInstrObjectSlot(mFather, mPS, aLetter, mTopY,
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
				mSlots[r][c] = new LevelInstrObjectSlot(mFather, mPS, null, 0, 0, 0, mVSpace, false);
			}
		}
	}

	public void removeObject(int row, int col) {
		mSlots[row][col] = new LevelInstrObjectSlot(mFather, mPS, null, 0, 0, 0, mVSpace, false);
	}
	
	public void update(float tX, float tY) throws Exception {
		for (int c = 0; c < mNumCols; c++) {
			for (int r = 0; r < mNumRows; r++) {
				mSlots[r][c].update(r, c, tX, tY);
			}
		}
	}
		
	public void updateSelected(float tX, float tY) throws Exception {
		for (int c = 0; c < mNumCols; c++) {
			for (int r = 0; r < mNumRows; r++) {
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
	
	public LevelInstrObjectSlot[][] getSlots() {
		return mSlots;
	}	
	
	public int getRows() {
		return mNumRows;
	}
	
	public int getCols() {
		return mNumCols;
	}	
}