package com.cyberg.lexxicon.game.infinite;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.cyberg.lexxicon.Main;
import com.cyberg.lexxicon.environment.CrossVariables;
import com.cyberg.lexxicon.game.BonusPlate;
import com.cyberg.lexxicon.game.LetterGrid;
import com.cyberg.lexxicon.game.SuggestionPlate;
import com.cyberg.lexxicon.game.WordPlate;
import com.cyberg.lexxicon.structs.LetterStruct;
import com.cyberg.lexxicon.testutils.DisplayWords;
import com.lancer.android.processing.traer.physics.ParticleSystem;

import processing.core.PApplet;
import processing.core.PImage;

public class InfiniteGameCore {
	
	private Main mFather;
	private ParticleSystem mPS;
	private PImage mSfondo;
	private LetterGrid mLetterGrid;
	private BonusPlate mBonusPlate;
	private SuggestionPlate mSuggestionPlate;
	private DisplayWords mDisplayWords;

	private boolean mShowExitConfirmation = false;
	private long mConfirmationStartTime = 0;
	private final long CONFIRMATION_TIMEOUT = 3000; // 3 secondi

	
	public InfiniteGameCore(Main aFather, ParticleSystem aPS, PImage sfondo,
													LetterGrid letterGrid, BonusPlate bonusPlate,
													SuggestionPlate suggestionPlate, DisplayWords displayWords) {
		mFather = aFather;
		mPS = aPS;
		mSfondo = sfondo;
		mLetterGrid = letterGrid;
		mBonusPlate = bonusPlate;
		mSuggestionPlate = suggestionPlate;
		mDisplayWords = displayWords;
	}

	public void update(float aTX, float aTY) throws Exception {
		if (!CrossVariables.GAME_INIT) {
			mLetterGrid.fillGridLetters();
			CrossVariables.GAME_INIT = true;
			CrossVariables.TIMEOUT_INFINITE_MODE_PREVIOUS_TIME = System.currentTimeMillis();
		}

		// Continua sempre l'aggiornamento del timer, anche durante la conferma
		if (CrossVariables.GAME_STATE == CrossVariables.GAME_BOARD ||
				CrossVariables.GAME_STATE == CrossVariables.GAME_SUGGESTION_EFFECT ||
				CrossVariables.GAME_STATE == CrossVariables.GAME_BOMB_EFFECT ||
				CrossVariables.GAME_STATE == CrossVariables.GAME_ICE_EFFECT ||
				CrossVariables.GAME_STATE == CrossVariables.GAME_WIPE_EFFECT ||
				CrossVariables.GAME_STATE == CrossVariables.GAME_NEW_EFFECT) {
			updateInfiniteTimer();
		}

		// Aggiornamento normale del gioco
		switch (CrossVariables.GAME_STATE) {
			case CrossVariables.GAME_BOARD:
				mPS.tick();
				drawGameBoard(aTX, aTY);
				break;
			case CrossVariables.GAME_WORD_LIST:
				drawWordList();
				break;
			case CrossVariables.GAME_SUGGESTION_EFFECT:
				mPS.tick();
				drawSuggestionEffect();
				break;
			case CrossVariables.GAME_BOMB_EFFECT:
				mPS.tick();
				drawBombEffect();
				break;
			case CrossVariables.GAME_ICE_EFFECT:
				mPS.tick();
				drawIceEffect();
				break;
			case CrossVariables.GAME_WIPE_EFFECT:
				mPS.tick();
				drawWipeEffect();
				break;
			case CrossVariables.GAME_NEW_EFFECT:
				mPS.tick();
				drawNewEffect();
				break;
			case CrossVariables.GAME_OVER:
				mPS.tick();
				drawGameOver();
				break;
		}

		// Gestisci la conferma di uscita se attiva (sovrapposta al gioco)
		if (mShowExitConfirmation) {
			handleExitConfirmation(aTX, aTY);
		}
	}

	/**
	 * Aggiorna solo il timer della modalità infinita
	 */
	private void updateInfiniteTimer() {
		long currentTime = System.currentTimeMillis();
		CrossVariables.TIMEOUT_INFINITE_MODE_TIME_LEFT -= (currentTime - CrossVariables.TIMEOUT_INFINITE_MODE_PREVIOUS_TIME);
		CrossVariables.TIMEOUT_INFINITE_MODE_PREVIOUS_TIME = currentTime;
	}

	/**
	 * Mostra la conferma di uscita senza fermare il timer
	 */
	public void showExitConfirmation() {
		mShowExitConfirmation = true;
		mConfirmationStartTime = System.currentTimeMillis();
	}

	/**
	 * Gestisce la conferma di uscita
	 */
	public void handleExitConfirmation(float aTX, float aTY) {
		if (!mShowExitConfirmation) return;

		// Auto-nasconde dopo timeout
		if (System.currentTimeMillis() - mConfirmationStartTime > CONFIRMATION_TIMEOUT) {
			mShowExitConfirmation = false;
			return;
		}

		// Disegna il dialog di conferma
		drawExitConfirmationDialog(aTX, aTY);
	}


  private void drawWordList() throws Exception {
  	mFather.background(255, 148, 0);
  	mDisplayWords.update();
  }
  
  private void drawGameBoard(float aTX, float aTY) throws Exception {
  	if (CrossVariables.BONUS_USED != -1) {
			mFather.tint(0, 153, 204);
  	  mFather.image(mSfondo, 0, 0);	  
			mFather.noTint();
  		bonusManage(aTX, aTY);
  	}
  	else {
  		long currentTime = System.currentTimeMillis();
    	CrossVariables.TIMEOUT_INFINITE_MODE_TIME_LEFT -= (currentTime - CrossVariables.TIMEOUT_INFINITE_MODE_PREVIOUS_TIME);
    	CrossVariables.TIMEOUT_INFINITE_MODE_PREVIOUS_TIME = currentTime;
  		int rndX = 0;
  		int rndY = 0;
    	if (CrossVariables.TIMEOUT_INFINITE_MODE_TIME_LEFT <= CrossVariables.TIMEOUT_INFINITE_MODE_LIMIT_WARINING) {
    		rndX = PApplet.round(mFather.random(-2, 2) / CrossVariables.RESIZE_FACTOR_X);
    		rndY = PApplet.round(mFather.random(-2, 2) / CrossVariables.RESIZE_FACTOR_Y);
    	}
    	mFather.translate(rndX, rndY);
  	  mFather.image(mSfondo, 0, 0);
	  	CrossVariables.WORD_PLATE.update();
	  	CrossVariables.POINTS_PLATE.update(aTX, aTY);
	  	mBonusPlate.update(aTX, aTY);
	  	mLetterGrid.update(aTX, aTY);
	  	checkGameOver();
  	}
  }
  
  private void checkGameOver() {
  	if (CrossVariables.foundCount == 0 ||
  			CrossVariables.TIMEOUT_INFINITE_MODE_TIME_LEFT <= 0) {
  		CrossVariables.GAME_STATE = CrossVariables.GAME_OVER;
      int frameToCenter = 30;
      int framePause = 50;
      int frameToRight = 30;
      mSuggestionPlate.initializeAnim(frameToCenter, framePause, frameToRight, "GAME OVER");
  		CrossVariables.BONUS_USE_FRAMES_LEFT = frameToCenter + framePause + frameToRight;
  	}
  }
  
  private void drawSuggestionEffect() throws Exception {
  	CrossVariables.BONUS_USE_FRAMES_LEFT--;
		mFather.tint(0, 153, 204);
	  mFather.image(mSfondo, 0, 0);	  
		mFather.noTint();
  	if (CrossVariables.BONUS_USE_FRAMES_LEFT == -1) {
  		CrossVariables.GAME_STATE = CrossVariables.GAME_BOARD;
  		CrossVariables.BONUS_USED = -1;
  	}
  	CrossVariables.WORD_PLATE.update();
  	CrossVariables.POINTS_PLATE.update(-1, -1);
  	mBonusPlate.update(-1, -1);
  	mLetterGrid.updateBonus(-1, -1);
  	mSuggestionPlate.update();
  }
  
  private void drawBombEffect() throws Exception {
  	CrossVariables.BONUS_USE_FRAMES_LEFT--;
  	if (CrossVariables.BONUS_USE_FRAMES_LEFT == -1) {
  		CrossVariables.GAME_STATE = CrossVariables.GAME_BOARD;
  		CrossVariables.BONUS_USED = -1;
  		CrossVariables.BONUS_SLOTS_CLEAN = true;
  	}
  	CrossVariables.WORD_PLATE.update();
  	CrossVariables.POINTS_PLATE.update(-1, -1);
  	mBonusPlate.update(-1, -1);
  	mLetterGrid.updateBonus(-1, -1);
  }
  
  private void drawIceEffect() throws Exception {
  	CrossVariables.BONUS_USE_FRAMES_LEFT--;
  	if (CrossVariables.BONUS_USE_FRAMES_LEFT == -1) {
  		CrossVariables.GAME_STATE = CrossVariables.GAME_BOARD;
  		CrossVariables.BONUS_USED = -1;
  		CrossVariables.BONUS_SLOTS_CLEAN = true;
  	}
  	CrossVariables.WORD_PLATE.update();
  	CrossVariables.POINTS_PLATE.update(-1, -1);
  	mBonusPlate.update(-1, -1);
  	mLetterGrid.updateBonus(-1, -1);
  }
  
  private void drawWipeEffect() throws Exception {
  	CrossVariables.BONUS_USE_FRAMES_LEFT--;
  	if (CrossVariables.BONUS_USE_FRAMES_LEFT == -1) {
  		CrossVariables.GAME_STATE = CrossVariables.GAME_BOARD;
  		CrossVariables.BONUS_USED = -1;
  		CrossVariables.BONUS_SLOTS_CLEAN = true;
  	}
  	CrossVariables.WORD_PLATE.update();
  	CrossVariables.POINTS_PLATE.update(-1, -1);
  	mBonusPlate.update(-1, -1);
  	mLetterGrid.updateBonus(-1, -1);
  }
  
  private void drawNewEffect() throws Exception {
  	CrossVariables.BONUS_USE_FRAMES_LEFT--;
  	if (CrossVariables.BONUS_USE_FRAMES_LEFT == -1) {
  		CrossVariables.GAME_STATE = CrossVariables.GAME_BOARD;
  		CrossVariables.BONUS_USED = -1;
  		CrossVariables.BONUS_SLOTS_CLEAN = true;
  	}
  	CrossVariables.WORD_PLATE.update();
  	CrossVariables.POINTS_PLATE.update(-1, -1);
  	mBonusPlate.update(-1, -1);
  	mLetterGrid.updateBonus(-1, -1);
  }
  
  private void drawGameOver() throws Exception {
  	CrossVariables.BONUS_USE_FRAMES_LEFT--;
		mFather.tint(0, 0, 0);
	  mFather.image(mSfondo, 0, 0);	  
		mFather.noTint();
  	CrossVariables.WORD_PLATE.update();
  	CrossVariables.POINTS_PLATE.update(-1, -1);
  	mBonusPlate.update(-1, -1);
  	mLetterGrid.updateBonus(-1, -1);
  	mSuggestionPlate.update();
  	if (CrossVariables.BONUS_USE_FRAMES_LEFT == -1) {
  		reset();
  	}
  }
  
  public void reset() {
		mLetterGrid.cleanGrid();
		CrossVariables.GAME_STATE = CrossVariables.GAME_BOARD;
		CrossVariables.OVERALL_STATE = CrossVariables.MENU_BOARD;
		CrossVariables.BONUS_USED = -1;
		CrossVariables.TIMEOUT_INFINITE_MODE_TIME_LEFT = CrossVariables.TIMEOUT_INFINITE_MODE_STANDARD;
		CrossVariables.GAME_INIT = false;
		CrossVariables.NR_BONUS_SUGGESTION = 0;
		CrossVariables.NR_BONUS_BOMB = 0;
		CrossVariables.NR_BONUS_ICE = 0;
		CrossVariables.NR_BONUS_WIPE_LETTERS = 0;
		CrossVariables.NR_BONUS_NEW_BOARD = 0;  
		CrossVariables.POINTS_EARNED = 0;
		CrossVariables.COMPOSED_WORD = "";
		CrossVariables.WORD_PLATE = new WordPlate(mFather, -1, "", true);
  }
  
  private void bonusManage(float aTX, float aTY) throws Exception {
  	CrossVariables.WORD_PLATE.update();
  	CrossVariables.POINTS_PLATE.update(-1, -1);
  	mBonusPlate.update(-1, -1);
  	mLetterGrid.update(-1, -1);
  	switch (CrossVariables.BONUS_USED) {
			case CrossVariables.BONUS_TYPE_SUGGESTION:
		  	// Put the game in bonus anim state					
	  		CrossVariables.GAME_STATE = CrossVariables.GAME_SUGGESTION_EFFECT;
	  		String longestOne = "";
	      for (int i=0; i<CrossVariables.foundCount; i++) {
	      	String currentW = CrossVariables.getWord(CrossVariables.foundWords[i]);
	      	if (currentW.length() > longestOne.length()) {
	      		longestOne = currentW;
	      	}
	      }
	      int frameToCenter = 30;
	      int framePause = 50;
	      int frameToRight = 30;
	      mSuggestionPlate.initializeAnim(frameToCenter, framePause, frameToRight, longestOne.toUpperCase());
	  		CrossVariables.BONUS_USE_FRAMES_LEFT = frameToCenter + framePause + frameToRight;
				break;
			case CrossVariables.BONUS_TYPE_BOMB:
				if (aTX > mLetterGrid.getLeftX() && aTX < (mLetterGrid.getLeftX() + mLetterGrid.getGridWidth()) && 
						aTY > mLetterGrid.getTopY() && aTY < (mLetterGrid.getTopY() + mLetterGrid.getGridHeight())) {
			  	// Put the game in bonus anim state					
		  		CrossVariables.GAME_STATE = CrossVariables.GAME_BOMB_EFFECT;
		  		CrossVariables.BONUS_USE_FRAMES_LEFT = CrossVariables.BONUS_USE_ANIM_FRAMES;
		    	// Calculate touch points of slots to clean
		    	float[] tX = {aTX};
		    	float[] tY = {aTY};
		    	mLetterGrid.markBonus(tX, tY);
		  	}
				break;
			case CrossVariables.BONUS_TYPE_ICE:
				if (aTX > mLetterGrid.getLeftX() && aTX < (mLetterGrid.getLeftX() + mLetterGrid.getGridWidth()) && 
						aTY > mLetterGrid.getTopY() && aTY < (mLetterGrid.getTopY() + mLetterGrid.getGridHeight())) {
			  	// Put the game in bonus anim state					
		  		CrossVariables.GAME_STATE = CrossVariables.GAME_ICE_EFFECT;
		  		CrossVariables.BONUS_USE_FRAMES_LEFT = CrossVariables.BONUS_USE_ANIM_FRAMES;
		    	// Calculate touch points of slots to clean
		    	float[] tX = {(aTX - (CrossVariables.IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X)),
		    								(aTX + (CrossVariables.IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X)),
		    								 aTX,
		    								 aTX,
		    								 aTX};	
		    	float[] tY = { aTY,
		    								 aTY,
		    								 aTY,
		    								(aTY - (CrossVariables.IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y)),
		  									(aTY + (CrossVariables.IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y)) };	
		    	mLetterGrid.markBonus(tX, tY);
		  	}
				break;
			case CrossVariables.BONUS_TYPE_WIPE_LETTERS:
				if (aTX > mLetterGrid.getLeftX() && aTX < (mLetterGrid.getLeftX() + mLetterGrid.getGridWidth()) && 
						aTY > mLetterGrid.getTopY() && aTY < (mLetterGrid.getTopY() + mLetterGrid.getGridHeight())) {
			  	// Put the game in bonus anim state					
		  		CrossVariables.GAME_STATE = CrossVariables.GAME_WIPE_EFFECT;
		  		CrossVariables.BONUS_USE_FRAMES_LEFT = CrossVariables.BONUS_USE_ANIM_FRAMES;
		    	// Get letter choosed
		  		int aRow = (CrossVariables.GRID_NUMBER_OF_ROWS - 1) - (new BigDecimal((aTY - mLetterGrid.getTopY()) / (CrossVariables.IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y))).intValue();
		  		int aCol = (new BigDecimal((aTX - mLetterGrid.getLeftX()) / (CrossVariables.IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X))).intValue();
		  		LetterStruct aLS = (LetterStruct)mLetterGrid.getSlots()[aRow][aCol].getObject();
		  		String letterChoosed = aLS.getLetter();
		  		// Scan grid to identify same letters
		  		ArrayList<float[]> slotsToWipe = new ArrayList<float[]>();
		  		for (int r=0; r<CrossVariables.GRID_NUMBER_OF_ROWS; r++) {
		  			for (int c=0; c<CrossVariables.GRID_NUMBER_OF_COLS; c++) {
							LetterStruct gLS = (LetterStruct)mLetterGrid.getSlots()[r][c].getObject();
				  		if (gLS.getLetter().trim().equalsIgnoreCase(letterChoosed.trim())) {
				  			// Get x and y
				  			float[] xY = { mLetterGrid.getSlots()[r][c].getCenterX(),
				  										 mLetterGrid.getSlots()[r][c].getCenterY() };
				  			slotsToWipe.add(xY);
				  		}
		  			}
		  		}
		    	mLetterGrid.markBonus(slotsToWipe);
		  	}
				break;
  		case CrossVariables.BONUS_TYPE_NEW_BOARD:
	  		CrossVariables.GAME_STATE = CrossVariables.GAME_WIPE_EFFECT;
	  		CrossVariables.BONUS_USE_FRAMES_LEFT = CrossVariables.BONUS_USE_ANIM_FRAMES;
	  		// Mark all slots to wipe
	  		ArrayList<float[]> slotsToWipe = new ArrayList<float[]>();
	  		for (int r=0; r<CrossVariables.GRID_NUMBER_OF_ROWS; r++) {
	  			for (int c=0; c<CrossVariables.GRID_NUMBER_OF_COLS; c++) {
		  			// Get x and y
		  			float[] xY = { mLetterGrid.getSlots()[r][c].getCenterX(),
		  										 mLetterGrid.getSlots()[r][c].getCenterY() };
		  			slotsToWipe.add(xY);
	  			}
	  		}
	    	mLetterGrid.markBonus(slotsToWipe);
  			break;
  	}
  }

	/**
	 * Disegna il dialog di conferma
	 */
	private void drawExitConfirmationDialog(float aTX, float aTY) {
		// Overlay semi-trasparente
		mFather.pushStyle();
		mFather.fill(0, 0, 0, 128);
		mFather.rect(0, 0, mFather.width, mFather.height);

		// Dialog box
		float dialogW = mFather.width * 0.8f;
		float dialogH = mFather.height * 0.3f;
		float dialogX = (mFather.width - dialogW) / 2;
		float dialogY = (mFather.height - dialogH) / 2;

		mFather.fill(50, 50, 50);
		mFather.stroke(200, 200, 200);
		mFather.strokeWeight(2);
		mFather.rect(dialogX, dialogY, dialogW, dialogH);

		// Testo di conferma
		mFather.textFont(mFather.mWordFont);
		mFather.textSize(PApplet.round(24 / CrossVariables.RESIZE_FACTOR_X));
		mFather.textAlign(PApplet.CENTER);
		mFather.fill(255, 255, 255);
		mFather.text(getLocalizedString("exit_confirmation"),
				mFather.width / 2,
				dialogY + dialogH * 0.4f);

		// Pulsanti
		float buttonW = dialogW * 0.35f;
		float buttonH = dialogH * 0.25f;
		float yesButtonX = dialogX + dialogW * 0.15f;
		float noButtonX = dialogX + dialogW * 0.5f;
		float buttonY = dialogY + dialogH * 0.65f;

		// Pulsante SÌ
		mFather.fill(200, 50, 50);
		mFather.rect(yesButtonX, buttonY, buttonW, buttonH);
		mFather.fill(255, 255, 255);
		mFather.textSize(PApplet.round(20 / CrossVariables.RESIZE_FACTOR_X));
		mFather.text(getLocalizedString("yes"),
				yesButtonX + buttonW / 2,
				buttonY + buttonH * 0.7f);

		// Pulsante NO
		mFather.fill(50, 200, 50);
		mFather.rect(noButtonX, buttonY, buttonW, buttonH);
		mFather.fill(255, 255, 255);
		mFather.text(getLocalizedString("no"),
				noButtonX + buttonW / 2,
				buttonY + buttonH * 0.7f);

		mFather.popStyle();

		// Gestisci touch sui pulsanti
		if (aTX != -1 && aTY != -1) {
			// Touch su SÌ - esci dalla modalità infinita
			if (aTX >= yesButtonX && aTX <= yesButtonX + buttonW &&
					aTY >= buttonY && aTY <= buttonY + buttonH) {
				mShowExitConfirmation = false;
				backToMenu();
			}
			// Touch su NO - continua il gioco
			else if (aTX >= noButtonX && aTX <= noButtonX + buttonW &&
					aTY >= buttonY && aTY <= buttonY + buttonH) {
				mShowExitConfirmation = false;
			}
		}
	}

	/**
	 * Torna al menu principale
	 */
	public void backToMenu() {
		// Reset completo della modalità infinita
		reset();

		// Torna al menu
		CrossVariables.OVERALL_STATE = CrossVariables.OVERALL_MENU;
	}

	/**
	 * Ottiene stringhe localizzate per i dialog
	 */
	private String getLocalizedString(String key) {
		try {
			int resId = mFather.getResources().getIdentifier(key, "string",
					mFather.getApplicationContext().getPackageName());
			if (resId != 0) {
				return mFather.getResources().getString(resId);
			}
		} catch (Exception e) {
			android.util.Log.e("InfiniteGameCore", "Error getting localized string: " + key, e);
		}

		// Fallback in inglese
		switch (key) {
			case "exit_confirmation": return "QUIT CURRENT GAME?";
			case "yes": return "YES";
			case "no": return "NO";
			default: return key;
		}
	}
}
