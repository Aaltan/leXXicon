package com.cyberg.lexxicon.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import processing.core.PApplet;

import com.cyberg.lexxicon.Main;
import com.cyberg.lexxicon.environment.CrossVariables;
import com.cyberg.lexxicon.structs.BonusStruct;
import com.cyberg.lexxicon.structs.LetterStruct;
import com.cyberg.lexxicon.structs.ObjectFactory;
import com.cyberg.lexxicon.utils.VariousUtils;
import com.lancer.android.processing.traer.physics.ParticleSystem;

public class LetterGrid {

	private Main mFather;
	private ParticleSystem mPS;
	private int mNumRows;
	private int mNumCols;
	private int mLeftX;
	private int mTopY;
	private ObjectSlot[][] mSlots;
	public ObjectFactory mObjFactory;
	private BonusStruct mBonusAnim;

	public LetterGrid(Main aFather, ParticleSystem aPS,
										ObjectFactory objFactory, int numRows, int numCols, int leftX, int topY) {
		mFather = aFather;
		mPS = aPS;
		mObjFactory = objFactory;
		mNumRows = numRows;
		mNumCols = numCols;
		mLeftX = leftX;
		mTopY = topY;
		mSlots = new ObjectSlot[mNumRows][mNumCols];
		for (int r = 0; r < mNumRows; r++) {
			for (int c = 0; c < mNumCols; c++) {
				mSlots[r][c] = new ObjectSlot(mFather, mPS, null, 0, 0, 0, false);
			}
		}
	}

	public void fillGrid() {
		int offsetY = mFather.height;
		for (int r = 0; r < mNumRows; r++) {
			for (int c = 0; c < mNumCols; c++) {
				LetterStruct aLetter = mObjFactory.getLetter();
				// Metto in negativo l'offset Y, in quanto devono partire da fuori
				// schermo
				if (!mSlots[r][c].isFull()) {
					mSlots[r][c] = new ObjectSlot(mFather, mPS, aLetter, mTopY, 
																				mLeftX + (c * aLetter.getImage().width),
																				-(offsetY + (aLetter.getImage().height * 3 * r)), true);
				}
			}
		}
		solveGrid();		
	}
	
	public void cleanGrid() {
		for (int r = 0; r < mNumRows; r++) {
			for (int c = 0; c < mNumCols; c++) {
				mSlots[r][c].destroyParticles();
				mSlots[r][c] = new ObjectSlot(mFather, mPS, null, 0, 0, 0, false);
			}
		}
	}

	public void removeObject(int row, int col) {
		mSlots[row][col] = new ObjectSlot(mFather, mPS, null, 0, 0, 0, false);
	}
	
	public void update(float tX, float tY) throws Exception {
		boolean inError = false;
		if (CrossVariables.MARKED_FOR_SWAP.size() > 0) {
			if (CrossVariables.correctWord()) {
				int pointsEarned = VariousUtils.getPoints(mFather, CrossVariables.COMPOSED_WORD);
				CrossVariables.TIMEOUT_TIME_LEFT = CrossVariables.TIMEOUT_STANDARD;
				CrossVariables.POINTS_EARNED += pointsEarned;
				CrossVariables.WORD_PLATE = new WordPlate(mFather, 200, new String(CrossVariables.COMPOSED_WORD), inError);
				mBonusAnim = mFather.mBonusFactory.getBonus(CrossVariables.COMPOSED_WORD);
				if (mBonusAnim != null) {
					CrossVariables.BONUS_GRANTED = mBonusAnim.getType();
					mBonusAnim.setDurationFrame(CrossVariables.BONUS_ANIM_FRAMES);
				}
				CrossVariables.COMPOSED_WORD = "";
				swapSlots();
			}
			else if (CrossVariables.BONUS_SLOTS_CLEAN) {
				CrossVariables.BONUS_SLOTS_CLEAN = false;
				swapSlots();
			}
			else {
				inError = true;
				CrossVariables.WORD_PLATE = new WordPlate(mFather, 200, new String(CrossVariables.COMPOSED_WORD), inError);
				CrossVariables.COMPOSED_WORD = "";
				cleanSlots();
			}
		}
		else {
			if (CrossVariables.COMPOSED_WORD.trim().length() != 0) {
				CrossVariables.WORD_PLATE = new WordPlate(mFather, 200, new String(CrossVariables.COMPOSED_WORD), inError);
			}
		}
		for (int c = 0; c < mNumCols; c++) {
			for (int r = 0; r < mNumRows; r++) {
				mSlots[r][c].update(r, c, tX, tY);
			}
		}
		if (mBonusAnim != null) {
			animateBonus();
		}
	}
	
	public void updateBonus(float tX, float tY) throws Exception {
		for (int c = 0; c < mNumCols; c++) {
			for (int r = 0; r < mNumRows; r++) {
				mSlots[r][c].update(r, c, tX, tY);
			}
		}
	}
	
	public void markBonus(float[] tX, float[] tY) throws Exception {
		for (int c = 0; c < mNumCols; c++) {
			for (int r = 0; r < mNumRows; r++) {
				for (int i=0; i<tX.length; i++) {
					mSlots[r][c].updateBonus(r, c, tX[i], tY[i]);
				}
			}
		}
	}
	
	public void markBonus(ArrayList<float[]> markPts) throws Exception {
		for (int c = 0; c < mNumCols; c++) {
			for (int r = 0; r < mNumRows; r++) {
				for (int i=0; i<markPts.size(); i++) {
					mSlots[r][c].updateBonus(r, c, markPts.get(i)[0], markPts.get(i)[1]);
				}
			}
		}
	}
	
	private void cleanSlots() {
		for (int i=0; i<CrossVariables.MARKED_FOR_SWAP.size(); i++) {
			int eR = CrossVariables.MARKED_FOR_SWAP.get(i).getRow();
			int eC = CrossVariables.MARKED_FOR_SWAP.get(i).getCol();
			mSlots[eR][eC].setFull(true);
			LetterStruct aLS = (LetterStruct) mSlots[eR][eC].getObject();
			aLS.setMarked(false);
			aLS.setSelected(false);
			aLS.setImageW(PApplet.round(CrossVariables.IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X));
			aLS.setImageH(PApplet.round(CrossVariables.IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y));
		}
		CrossVariables.MARKED_FOR_SWAP = new ArrayList<EmptySlot>();
	}
	
	private void swapSlots() {
		Collections.sort(CrossVariables.MARKED_FOR_SWAP, new CustomComparator());		
		for (int i=0; i<CrossVariables.MARKED_FOR_SWAP.size(); i++) {
			int eR = CrossVariables.MARKED_FOR_SWAP.get(i).getRow();
			int eC = CrossVariables.MARKED_FOR_SWAP.get(i).getCol();
			for (int r=eR; r<(mNumRows-1); r++) {
				ObjectSlot tempSlot = mSlots[r+1][eC];
				mSlots[r+1][eC] = mSlots[r][eC];
				mSlots[r][eC] = tempSlot;
			}
			for (int r=eR; r<mNumRows; r++) {
				if (!mSlots[r][eC].isFull()) {
					CrossVariables.addFillSlot(r, eC);
				}
			}
		}
		CrossVariables.MARKED_FOR_SWAP = new ArrayList<EmptySlot>();
		if (CrossVariables.MARKED_FOR_FILL.size() > 0) {
			fillEmptySlots();
		}
	}
	
	private void fillEmptySlots() {
		for (int i=0; i<CrossVariables.MARKED_FOR_FILL.size(); i++) {
			LetterStruct aLetter = mObjFactory.getLetter();
			// Metto in negativo l'offset Y, in quanto devono partire da fuori
			// schermo
			int r = CrossVariables.MARKED_FOR_FILL.get(i).getRow();
			int c = CrossVariables.MARKED_FOR_FILL.get(i).getCol();
			mSlots[r][c].destroyParticles();
			mSlots[r][c] = new ObjectSlot(mFather, mPS, aLetter, mTopY, 
																		mLeftX + (c * aLetter.getImage().width),
																		-(aLetter.getImage().height * 2 * r), true);
		}
		CrossVariables.MARKED_FOR_FILL = new ArrayList<EmptySlot>();
		solveGrid();
	}
	
	private void solveGrid() {
		final String[] gridRows = new String[5];
		for (int r=0; r<CrossVariables.GRID_NUMBER_OF_ROWS; r++) {
			String aRow = "";
			for (int c=0; c<CrossVariables.GRID_NUMBER_OF_COLS; c++) {
				aRow += ((LetterStruct)mSlots[r][c].getObject()).getLetter();
			}
			gridRows[r] = aRow;
		}
		mFather.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				CrossVariables.solveBoard(gridRows);
			}			
		});
	}
	
	private void animateBonus() {
		if (mBonusAnim.getDurationFrame() == 0) {
			switch (mBonusAnim.getType()) {
				case CrossVariables.BONUS_TYPE_SUGGESTION:
					CrossVariables.NR_BONUS_SUGGESTION++;
					break;
				case CrossVariables.BONUS_TYPE_BOMB:
					CrossVariables.NR_BONUS_BOMB++;
					break;
				case CrossVariables.BONUS_TYPE_ICE:
					CrossVariables.NR_BONUS_ICE++;
					break;
				case CrossVariables.BONUS_TYPE_WIPE_LETTERS:
					CrossVariables.NR_BONUS_WIPE_LETTERS++;
					break;
				case CrossVariables.BONUS_TYPE_NEW_BOARD:
					CrossVariables.NR_BONUS_NEW_BOARD++;
					break;
			}
			mBonusAnim = null;
			CrossVariables.BONUS_ANIM_OFFSET_X = 0;
			CrossVariables.BONUS_ANIM_OFFSET_Y = 0;
			CrossVariables.BONUS_ANIM_SIGN_X = 1;
			CrossVariables.BONUS_ANIM_SIGN_Y = 1;
			CrossVariables.BONUS_GRANTED = -1;
			return;
		}
		if (mBonusAnim.getDurationFrame() % 5 == 0) {
			CrossVariables.BONUS_ANIM_SIGN_X = -CrossVariables.BONUS_ANIM_SIGN_X;
		}		
		if (mBonusAnim.getDurationFrame() % 4 == 0) {
			CrossVariables.BONUS_ANIM_SIGN_Y = -CrossVariables.BONUS_ANIM_SIGN_Y;
		}		
		CrossVariables.BONUS_ANIM_OFFSET_X += PApplet.round((((mBonusAnim.getDurationFrame() % 5) * 2) / CrossVariables.RESIZE_FACTOR_X) * 
																				  CrossVariables.BONUS_ANIM_SIGN_X);
		CrossVariables.BONUS_ANIM_OFFSET_Y += PApplet.round((((mBonusAnim.getDurationFrame() % 4) * 2) / CrossVariables.RESIZE_FACTOR_Y) * 
			  																	CrossVariables.BONUS_ANIM_SIGN_Y);
		mBonusAnim.setDurationFrame(mBonusAnim.getDurationFrame() - 1);
	}
	
	public int getLeftX() {
		return mLeftX;
	}
	
	public int getTopY() {
		return mFather.height - mTopY - (mNumRows * PApplet.round(CrossVariables.IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y));
	}
	
	public int getGridWidth() {
		return mNumCols * PApplet.round(CrossVariables.IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X);
	}
	
	public int getGridHeight() {
		return mNumRows * PApplet.round(CrossVariables.IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y);
	}
	
	public ObjectSlot[][] getSlots() {
		return mSlots;
	}
	
	private class CustomComparator implements Comparator<EmptySlot> {
    @Override
    public int compare(EmptySlot eS1, EmptySlot eS2) {
    	return eS2.getSRow().compareTo(eS1.getSRow());
    }
	}
}