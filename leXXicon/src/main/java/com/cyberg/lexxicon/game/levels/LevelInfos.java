package com.cyberg.lexxicon.game.levels;

public class LevelInfos {

  private int mLevelType = -1;
  private int mTimerMax = -1;
  private int mWordsLeft = -1;
  private int mWordNumbMin = -1;
  private int mWordNumbMax = -1;
  private int mOperationType = -1;
  private boolean mBonusShown = false;
  private int mNrSuggestionBonus = 0;
  private int mNrBombBonus = 0;
  private int mNrIceBonus = 0;
  private int mNrWipeBonus = 0;
  private int mNrNewBoardBonus = 0;
  private int mMinWordLength = 3;

  public LevelInfos(int levelType, int timerMax, int wordsLeft, int nrSuggestionBonus, int nrBombBonus,
                    int nrIceBonus, int nrWipeBonus, int nrNewBoardBonus, int minWordLength, int wordNumbMin, int wordNumbMax, int operationType) {
    mLevelType = levelType;
    mTimerMax = timerMax;
    mWordsLeft = wordsLeft;
    mBonusShown = (nrSuggestionBonus > -1 || nrBombBonus > -1 || nrIceBonus > -1 || nrWipeBonus > -1 || nrNewBoardBonus > -1);
    mNrSuggestionBonus = nrSuggestionBonus;
    mNrBombBonus = nrBombBonus;
    mNrIceBonus = nrIceBonus;
    mNrWipeBonus = nrWipeBonus;
    mNrNewBoardBonus = nrNewBoardBonus;
    mMinWordLength = Math.max(3, minWordLength); // Minimo 3 lettere sempre
    mWordNumbMin = wordNumbMin;
    mWordNumbMax = wordNumbMax;
    mOperationType = operationType;
  }

  public int getLevelType() {
    return mLevelType;
  }

  public int getTimerMax() {
    return mTimerMax;
  }

  public int getWordsLeft() {
    return mWordsLeft;
  }

  public boolean getBonusShown() {
    return mBonusShown;
  }

  public int getWordNumbMin() {
    return mWordNumbMin;
  }

  public int getWordNumbMax() {
    return mWordNumbMax;
  }

  public int getOperationType() {
    return mOperationType;
  }

  public int getNrSuggestionBonus() {
    return mNrSuggestionBonus;
  }

  public int getNrBombBonus() {
    return mNrBombBonus;
  }

  public int getNrIceBonus() {
    return mNrIceBonus;
  }

  public int getNrWipeBonus() {
    return mNrWipeBonus;
  }

  public int getNrNewBoardBonus() {
    return mNrNewBoardBonus;
  }

  public int getMinWordLength() {
    return mMinWordLength;
  }
}
