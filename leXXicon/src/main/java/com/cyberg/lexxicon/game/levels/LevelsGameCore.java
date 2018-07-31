package com.cyberg.lexxicon.game.levels;

import com.cyberg.lexxicon.Main;
import com.cyberg.lexxicon.R;
import com.cyberg.lexxicon.environment.CrossVariables;
import com.cyberg.lexxicon.game.BonusPlate;
import com.cyberg.lexxicon.game.LetterGrid;
import com.cyberg.lexxicon.game.SuggestionPlate;
import com.cyberg.lexxicon.game.WordPlate;
import com.cyberg.lexxicon.structs.LetterStruct;
import com.cyberg.lexxicon.testutils.DisplayWords;
import com.lancer.android.processing.traer.physics.ParticleSystem;

import java.math.BigDecimal;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class LevelsGameCore {
	
	private Main mFather;
	private ParticleSystem mPS;
	private PImage mSfondo;
	private LetterGrid mLetterGrid;
	private SuggestionPlate mSuggestionPlate;
	private BonusPlate mBonusPlate;
	private DisplayWords mDisplayWords;
	
	public LevelsGameCore(Main aFather, ParticleSystem aPS, PImage sfondo,
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
    	configureLevel();
    	if (CrossVariables.LEVEL_TYPE_ACTUAL_GAME == CrossVariables.LEVEL_TYPE_MATH) {
        mLetterGrid.fillGridNumbers();
      }
      else {
        mLetterGrid.fillGridLetters();
      }
      CrossVariables.GAME_INIT = true;
      CrossVariables.TIMEOUT_SAGA_MODE_PREVIOUS_TIME = System.currentTimeMillis();
    }
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
      case CrossVariables.GAME_WIN:
        mPS.tick();
        drawGameWin();
        break;
      case CrossVariables.GAME_OVER:
        mPS.tick();
        drawGameOver();
        break;
    }
  }

  private void configureLevel() {
    CrossVariables.LEVEL_TYPE_ACTUAL_GAME = CrossVariables.LEVEL_INFOS[CrossVariables.LEVELS_SELECTED_NUM - 1].getLevelType();
    CrossVariables.NR_BONUS_BOMB = CrossVariables.LEVEL_INFOS[CrossVariables.LEVELS_SELECTED_NUM - 1].getNrBombBonus();
    CrossVariables.NR_BONUS_ICE = CrossVariables.LEVEL_INFOS[CrossVariables.LEVELS_SELECTED_NUM - 1].getNrIceBonus();
    CrossVariables.NR_BONUS_NEW_BOARD = CrossVariables.LEVEL_INFOS[CrossVariables.LEVELS_SELECTED_NUM - 1].getNrNewBoardBonus();
    CrossVariables.NR_BONUS_SUGGESTION = CrossVariables.LEVEL_INFOS[CrossVariables.LEVELS_SELECTED_NUM - 1].getNrSuggestionBonus();
    CrossVariables.NR_BONUS_WIPE_LETTERS = CrossVariables.LEVEL_INFOS[CrossVariables.LEVELS_SELECTED_NUM - 1].getNrWipeBonus();
    CrossVariables.TIMEOUT_SAGA_MODE_TIME_LEFT = CrossVariables.LEVEL_INFOS[CrossVariables.LEVELS_SELECTED_NUM - 1].getTimerMax();
    CrossVariables.WORDS_LEFT_TO_COMPOSE = CrossVariables.LEVEL_INFOS[CrossVariables.LEVELS_SELECTED_NUM - 1].getWordsLeft();
    CrossVariables.MATH_ACTUAL_MODE = CrossVariables.LEVEL_INFOS[CrossVariables.LEVELS_SELECTED_NUM - 1].getOperationType();
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
      CrossVariables.TIMEOUT_SAGA_MODE_TIME_LEFT -= (currentTime - CrossVariables.TIMEOUT_SAGA_MODE_PREVIOUS_TIME);
      CrossVariables.TIMEOUT_SAGA_MODE_PREVIOUS_TIME = currentTime;
      int rndX = 0;
      int rndY = 0;
      if (CrossVariables.TIMEOUT_SAGA_MODE_TIME_LEFT <= CrossVariables.TIMEOUT_SAGA_MODE_LIMIT_WARINING) {
        rndX = PApplet.round(mFather.random(-2, 2) / CrossVariables.RESIZE_FACTOR_X);
        rndY = PApplet.round(mFather.random(-2, 2) / CrossVariables.RESIZE_FACTOR_Y);
      }
      mFather.translate(rndX, rndY);
      mFather.image(mSfondo, 0, 0);
      drawRelatedObjects(aTX, aTY);
      if (!checkGameWin()) {
        checkGameOver();
      }
    }
  }

  private void drawRelatedObjects(float aTX, float aTY) throws Exception {
    CrossVariables.WORD_PLATE.update();
    CrossVariables.POINTS_PLATE.update(-1, -1);
    if (CrossVariables.LEVEL_INFOS[CrossVariables.LEVELS_SELECTED_NUM - 1].getBonusShown()) {
      mBonusPlate.update(aTX, aTY);
    }
    else {
      // Show the word to find
      if (CrossVariables.LEVEL_WORD_TO_FIND.trim().equalsIgnoreCase("")) {
        CrossVariables.LEVEL_WORD_TO_FIND = getRandomWord();
        int posY = PApplet.round(CrossVariables.WORD_FIND_POSITION_Y / CrossVariables.RESIZE_FACTOR_Y);
        mSuggestionPlate.initializeStatic(posY);
      }
      mSuggestionPlate.update();
    }
    mLetterGrid.update(aTX, aTY);
  }

  private String getRandomWord() {
	  String aReturnValue = "";
	  switch (CrossVariables.LEVEL_TYPE_ACTUAL_GAME) {
      case CrossVariables.LEVEL_TYPE_FIND:
        aReturnValue = extractWord();
        break;
      case CrossVariables.LEVEL_TYPE_MATH:
        aReturnValue = extractValue();
        break;
    }
    return aReturnValue;
  }

  private String extractWord() {
    String aReturnValue = "NO WORDS";
    ArrayList<String> words = new ArrayList<String>();
    for (int i=0; i<CrossVariables.foundCount; i++) {
      String foundString = CrossVariables.getWord(CrossVariables.foundWords[i]);
      if (foundString.length() >= CrossVariables.LEVEL_INFOS[CrossVariables.LEVELS_SELECTED_NUM - 1].getWordNumbMin()) {
        words.add(foundString);
      }
    }
    if (words.size() > 0) {
      int rndI = PApplet.round(mFather.random(0, words.size()));
      aReturnValue = words.get(rndI);
    }
    else {
      if (CrossVariables.foundCount > 0) {
        int rndI = PApplet.round(mFather.random(0, CrossVariables.foundCount));
        aReturnValue = CrossVariables.getWord(CrossVariables.foundWords[rndI]);
      }
    }
    return aReturnValue.toUpperCase();
  }

  private String extractValue() {
    String aReturnValue = "";
    int minRnd = CrossVariables.LEVEL_INFOS[CrossVariables.LEVELS_SELECTED_NUM - 1].getWordNumbMin();
    int maxRnd = CrossVariables.LEVEL_INFOS[CrossVariables.LEVELS_SELECTED_NUM - 1].getWordNumbMax();
    switch (CrossVariables.MATH_ACTUAL_MODE) {
      case CrossVariables.MATH_MODE_ADD:
        aReturnValue = mFather.getResources().getString(R.string.somma);
        int rndValue = PApplet.round(mFather.random(minRnd, maxRnd));
        CrossVariables.LEVEL_NUMBER_TO_FIND = rndValue;
        aReturnValue += rndValue;
        break;
      case CrossVariables.MATH_MODE_SUBTRACT:
        break;
      case CrossVariables.MATH_MODE_MULTIPY:
        break;
      case CrossVariables.MATH_MODE_DIVIDE:
        break;
    }
    return aReturnValue.toUpperCase();
  }

  private boolean checkGameWin() {
	  boolean aReturnValue = false;
    if (CrossVariables.WORDS_LEFT_TO_COMPOSE == 0) {
      CrossVariables.GAME_STATE = CrossVariables.GAME_WIN;
      int frameToCenter = 30;
      int framePause = 50;
      int frameToRight = 30;
      mSuggestionPlate.initializeAnim(frameToCenter, framePause, frameToRight, mFather.getResources().getString(R.string.finelivello));
      CrossVariables.BONUS_USE_FRAMES_LEFT = frameToCenter + framePause + frameToRight;
      aReturnValue = true;
    }
    return aReturnValue;
  }

  private void checkGameOver() {
    if (CrossVariables.foundCount == 0 ||
        CrossVariables.TIMEOUT_SAGA_MODE_TIME_LEFT <= 0) {
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
    if (CrossVariables.LEVEL_INFOS[CrossVariables.LEVELS_SELECTED_NUM - 1].getBonusShown()) {
      mBonusPlate.update(-1, -1);
    }
    mLetterGrid.updateBonus(-1, -1);
    mSuggestionPlate.update();
    if (CrossVariables.BONUS_USE_FRAMES_LEFT == -1) {
      reset();
    }
  }

  private void drawGameWin() throws Exception {
    CrossVariables.BONUS_USE_FRAMES_LEFT--;
    mFather.tint(0, 255, 0);
    mFather.image(mSfondo, 0, 0);
    mFather.noTint();
    CrossVariables.WORD_PLATE.update();
    CrossVariables.POINTS_PLATE.update(-1, -1);
    if (CrossVariables.LEVEL_INFOS[CrossVariables.LEVELS_SELECTED_NUM - 1].getBonusShown()) {
      mBonusPlate.update(-1, -1);
    }
    mLetterGrid.updateBonus(-1, -1);
    mSuggestionPlate.update();
    if (CrossVariables.BONUS_USE_FRAMES_LEFT == -1) {
      computeStars();
      reset();
    }
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
            float[] xY = { mLetterGrid.getSlots()[r][c].getCenterX(), mLetterGrid.getSlots()[r][c].getCenterY() };
            slotsToWipe.add(xY);
          }
        }
        mLetterGrid.markBonus(slotsToWipe);
        break;
    }
  }

  private void computeStars() {
	  int starUnit = PApplet.round( CrossVariables.LEVEL_INFOS[CrossVariables.LEVELS_SELECTED_NUM - 1].getTimerMax() / 3);
	  int threeStars = CrossVariables.LEVEL_INFOS[CrossVariables.LEVELS_SELECTED_NUM - 1].getTimerMax() - starUnit;
    int twoStars = CrossVariables.LEVEL_INFOS[CrossVariables.LEVELS_SELECTED_NUM - 1].getTimerMax() - (starUnit * 2);
    int starsEarned = 0;
    if (CrossVariables.TIMEOUT_SAGA_MODE_TIME_LEFT >= threeStars) {
      starsEarned = 3;
    }
    else if (CrossVariables.TIMEOUT_SAGA_MODE_TIME_LEFT >= twoStars) {
      starsEarned = 2;
    }
    else {
      starsEarned = 1;
    }
    if (CrossVariables.DB.connect()) {
      String aSQLStmt = "SELECT * FROM Levels WHERE levelnumber = " + CrossVariables.LEVELS_SELECTED_NUM;
      CrossVariables.DB.query(aSQLStmt);
      if (CrossVariables.DB.next()) {
        int oldStars = CrossVariables.DB.getInt("levelstars");
        if (oldStars < starsEarned) {
          aSQLStmt = "UPDATE levels SET levelstars = " + starsEarned + " WHERE levelnumber = " + CrossVariables.LEVELS_SELECTED_NUM;
          CrossVariables.DB.execute(aSQLStmt);
        }
      }
      else {
        aSQLStmt = "INSERT INTO Levels (levelnumber, levelstars) VALUES (" +
                  "" + CrossVariables.LEVELS_SELECTED_NUM + "," +
                  "" + starsEarned + ")";
        CrossVariables.DB.execute(aSQLStmt);
      }
      if (CrossVariables.LEVELS_SELECTED_NUM > CrossVariables.STATUS_LEVELSCOMPLETED) {
        CrossVariables.STATUS_LEVELSCOMPLETED = CrossVariables.LEVELS_SELECTED_NUM;
      }
      CrossVariables.DB.close();
    }
  }

  public void reset() {
		mLetterGrid.cleanGrid();
		CrossVariables.GAME_STATE = CrossVariables.GAME_BOARD;
		CrossVariables.OVERALL_STATE = CrossVariables.MENU_BOARD;
		CrossVariables.BONUS_USED = -1;
		CrossVariables.TIMEOUT_SAGA_MODE_TIME_LEFT = CrossVariables.TIMEOUT_SAGA_MODE_STANDARD;
		CrossVariables.GAME_INIT = false;
		CrossVariables.NR_BONUS_SUGGESTION = 0;
		CrossVariables.NR_BONUS_BOMB = 0;
		CrossVariables.NR_BONUS_ICE = 0;
		CrossVariables.NR_BONUS_WIPE_LETTERS = 0;
		CrossVariables.NR_BONUS_NEW_BOARD = 0;  
		CrossVariables.POINTS_EARNED = 0;
		CrossVariables.COMPOSED_WORD = "";
		CrossVariables.LEVEL_WORD_TO_FIND = "";
		CrossVariables.LEVEL_NUMBER_TO_FIND = -1;
		CrossVariables.MATH_ACTUAL_MODE = -1;
		CrossVariables.LEVEL_TYPE_ACTUAL_GAME = -1;
		CrossVariables.WORD_PLATE = new WordPlate(mFather, -1, "", true);
  }
}
