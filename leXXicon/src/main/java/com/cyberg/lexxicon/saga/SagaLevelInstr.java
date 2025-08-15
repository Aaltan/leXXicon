package com.cyberg.lexxicon.saga;

import com.cyberg.lexxicon.Main;
import com.cyberg.lexxicon.R;
import com.cyberg.lexxicon.environment.CrossVariables;
import com.cyberg.lexxicon.game.levels.LevelConfiguration;
import com.cyberg.lexxicon.game.levels.LevelInfos;
import com.cyberg.lexxicon.structs.LetterStruct;
import com.cyberg.lexxicon.structs.ObjectFactory;
import com.cyberg.lexxicon.utils.VariousUtils;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;

public class SagaLevelInstr {

  private Main mFather;
  private ObjectFactory mObjFactory;
  private PImage mSfondo;
  private int mLevel = -1;
  private LetterStruct[][] mInstructions;
  private int mImageW = -1;
  private int mImageFW = -1;
  private int mImageH = -1;
  private int mImageFH = -1;
  private int mSpaceY = -1;
  private int mSpaceX = -1;
  private int mImageTickW = -1;
  private int mImageTickH = -1;
  private int mLastRow = -1;

  public SagaLevelInstr(Main aFather, PImage sfondo, int levelSelected, ObjectFactory objFactory) {
    mFather = aFather;
    mSfondo = sfondo;
    mLevel = levelSelected;
    mObjFactory = objFactory;
    mImageW = PApplet.round(CrossVariables.LEVEL_INSTR_IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X);
    mImageFW = mImageW;
    mImageH = PApplet.round(CrossVariables.LEVEL_INSTR_IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y);
    mImageFH = mImageH;
    mSpaceX = PApplet.round(CrossVariables.LEVEL_INSTR_IMAGE_STANDARD_X_SPACE / CrossVariables.RESIZE_FACTOR_X);
    mSpaceY = PApplet.round(CrossVariables.LEVEL_INSTR_IMAGE_STANDARD_Y_SPACE / CrossVariables.RESIZE_FACTOR_Y);
    mImageTickW = PApplet.round(mImageW / CrossVariables.LEVEL_INSTR_ANIM_PLAY_FRAMES);
    mImageTickH = PApplet.round(mImageH / CrossVariables.LEVEL_INSTR_ANIM_PLAY_FRAMES);
    prepareInstructions();
  }

  private void prepareInstructions() {
    mInstructions = new LetterStruct[CrossVariables.LEVEL_INSTR_GRID_ROWS + 1][CrossVariables.LEVEL_INSTR_GRID_COLS];
    composeInstructions();
  }

  /**
   * Aggiunge le istruzioni del livello con layout dinamico
   */
  private void composeInstructions() {
    // Riga 0: Titolo del livello (invariato)
    addChars(0, (mFather.getResources().getString(R.string.lvltitle) + VariousUtils.justify(("" + mLevel), 3, "0", true)));

    // Ottieni info del livello
    LevelConfiguration.Level level = LevelConfiguration.getLevel(mLevel);
    LevelInfos levelInfo = CrossVariables.LEVEL_INFOS[mLevel - 1];

    if (level == null || levelInfo == null) return;

    // Riga 1: Titolo modalità
    String modeTitle = getModeTitle(level.mode);
    addChars(1, modeTitle.toUpperCase());

    // Riga 3: Descrizione principale
    String description = getModeDescription(level);
    addChars(3, description.toUpperCase());

    // Riga 5: vuota (separatore)
    addChars(mLastRow, " ");

    // Riga 6: Tempo
    int minutes = level.timeSeconds / 60;
    int seconds = level.timeSeconds % 60;
    String timeText = mFather.getResources().getString(R.string.mode_time, minutes, seconds);
    addChars(mLastRow, timeText.toUpperCase());

    // Riga 8: Aiuti (solo per CON_AIUTI)
    if (level.mode == LevelConfiguration.GameMode.CON_AIUTI && !level.bonuses.isEmpty()) {
      // Riga 5: vuota (separatore)
      addChars(mLastRow, " ");
      // Conta i bonus totali
      int totalHelps = level.bonuses.size();
      String helpsText = mFather.getResources().getString(R.string.mode_helps, totalHelps);
      addChars(mLastRow, helpsText.toUpperCase());
    }

    // Riga 15: "GIOCA" (invariato)
    addChars(15, mFather.getResources().getString(R.string.lvlstart).toUpperCase());
  }

  private String getModeTitle(LevelConfiguration.GameMode mode) {
    switch (mode) {
      case CANONICA:
        return mFather.getResources().getString(R.string.mode_title_classic);
      case CON_AIUTI:
        return mFather.getResources().getString(R.string.mode_title_with_helps);
      case FIND_WORD:
        return mFather.getResources().getString(R.string.mode_title_find_words);
      case MATH_SOMMA:
        return mFather.getResources().getString(R.string.mode_title_math);
      case BINARY:
        return mFather.getResources().getString(R.string.mode_title_binary);
      default:
        return "UNKNOWN";
    }
  }

  private String getModeDescription(LevelConfiguration.Level level) {
    switch (level.mode) {
      case CANONICA:
        return mFather.getResources().getString(R.string.mode_desc_classic, level.targetCount, level.minWordLength);
      case CON_AIUTI:
        return mFather.getResources().getString(R.string.mode_desc_with_helps, level.targetCount, level.minWordLength);
      case FIND_WORD:
        return mFather.getResources().getString(R.string.mode_desc_find, level.targetCount);
      case MATH_SOMMA:
        return mFather.getResources().getString(R.string.mode_desc_math, level.minNumbers, level.targetCount);
      case BINARY:
        return mFather.getResources().getString(R.string.mode_desc_binary,level.targetCount);
      default:
        return "";
    }
  }

  /**
   * Aggiunge caratteri su una singola riga
   */
  private void addChars(int aRow, String aString) {
    if (aRow > CrossVariables.LEVEL_INSTR_GRID_ROWS || aString == null) return;

    List<String> righe = new ArrayList<String>();
    while (aString.length() > CrossVariables.LEVEL_INSTR_GRID_COLS) {
      String row = aString.substring(0, CrossVariables.LEVEL_INSTR_GRID_COLS);
      righe.add(row);
      aString = aString.substring(CrossVariables.LEVEL_INSTR_GRID_COLS).trim();
    }
    righe.add(aString);

    for (int r = 0; r < righe.size(); r++) {
      String sRow = righe.get(r);
      int strLen = sRow.length();
      int offset = (CrossVariables.LEVEL_INSTR_GRID_COLS - strLen) / 2;
      if (offset < 0)
        return;
      int col = 0;
      for (int i = 0; i <sRow.length() && col < CrossVariables.LEVEL_INSTR_GRID_COLS; i++) {
        String aChar = sRow.substring(i, i + 1);
        mInstructions[aRow][offset] = mObjFactory.getLetter(aChar);
        col++;
        offset++;
      }
      aRow++;
    }
    mLastRow = aRow;
  }

  public void update(float aTX, float aTY) {
    int offsetX = PApplet.round((CrossVariables.SCREEN_STANDARD_X -
        (CrossVariables.LEVEL_INSTR_IMAGE_STANDARD_X * CrossVariables.LEVEL_INSTR_GRID_COLS) -
        (CrossVariables.LEVEL_INSTR_IMAGE_STANDARD_X_SPACE * (CrossVariables.LEVEL_INSTR_GRID_COLS - 1))) / 2 / CrossVariables.RESIZE_FACTOR_X);
    int offsetY = PApplet.round((CrossVariables.SCREEN_STANDARD_Y -
        (CrossVariables.LEVEL_INSTR_IMAGE_STANDARD_Y * CrossVariables.LEVEL_INSTR_GRID_ROWS) -
        (CrossVariables.LEVEL_INSTR_IMAGE_STANDARD_Y_SPACE * (CrossVariables.LEVEL_INSTR_GRID_ROWS - 1))) / 2 / CrossVariables.RESIZE_FACTOR_Y);

    CrossVariables.LEVEL_INSTR_LETTERS_SHOWN++;

    int r = 0;
    int c = 0;
    for (int i=0; i<CrossVariables.LEVEL_INSTR_LETTERS_SHOWN; i++) {
      if (r == 0) mFather.tint(200, 200, 200);
      if (r == 1) mFather.tint(225, 225, 225);
      if (r == 15) {
        mFather.tint(255, 255, 0);
      }
      if (r<=CrossVariables.LEVEL_INSTR_GRID_ROWS) {
        if (mInstructions[r][c] != null) {
          if (!mInstructions[r][c].getLetter().trim().equalsIgnoreCase("")) {
            mFather.image(mInstructions[r][c].getImage(), offsetX + (c * (mImageFW + mSpaceX)) + ((mImageFW - mImageW)/2),
                       offsetY + (r * (mImageFH + mSpaceY)) + ((mImageFH - mImageH)/2), mImageW, mImageH);
          }
        }
        c++;
        if (c % CrossVariables.LEVEL_INSTR_GRID_COLS == 0) {
          c = 0;
          r++;
        }
      }
      mFather.noTint();
    }
    int playButtonRow = CrossVariables.LEVEL_INSTR_GRID_ROWS; // Riga del pulsante PLAY
    int yTop = offsetY + (playButtonRow * (mImageFH + mSpaceY));
    int yBottom = yTop + mImageH;
    if (aTX >= offsetX && aTX < (mFather.width - offsetX) && aTY >= yTop && aTY <= yBottom) {
      CrossVariables.LEVEL_INSTR_ANIM_PLAY_LEFT = CrossVariables.LEVEL_INSTR_ANIM_PLAY_FRAMES;
      CrossVariables.SAGA_STATE = CrossVariables.SAGA_LEVEL_START_ANIM;
    }
  }

  public void updateSelected(float aTX, float aTY) {
    int offsetX = PApplet.round((CrossVariables.SCREEN_STANDARD_X -
        (CrossVariables.LEVEL_INSTR_IMAGE_STANDARD_X * CrossVariables.LEVEL_INSTR_GRID_COLS) -
        (CrossVariables.LEVEL_INSTR_IMAGE_STANDARD_X_SPACE * (CrossVariables.LEVEL_INSTR_GRID_COLS - 1))) / 2 / CrossVariables.RESIZE_FACTOR_X);
    int offsetY = PApplet.round((CrossVariables.SCREEN_STANDARD_Y -
        (CrossVariables.LEVEL_INSTR_IMAGE_STANDARD_Y * CrossVariables.LEVEL_INSTR_GRID_ROWS) -
        (CrossVariables.LEVEL_INSTR_IMAGE_STANDARD_Y_SPACE * (CrossVariables.LEVEL_INSTR_GRID_ROWS - 1))) / 2 / CrossVariables.RESIZE_FACTOR_Y);

    CrossVariables.LEVEL_INSTR_LETTERS_SHOWN++;

    int r = 0;
    int c = 0;
    for (int i=0; i<CrossVariables.LEVEL_INSTR_LETTERS_SHOWN; i++) {
      if (r == 0) mFather.tint(200, 200, 200);
      if (r == 1) mFather.tint(225, 225, 225);
      if (r == 15) {
        mFather.tint(255, 255, 0);
      }
      if (r<=CrossVariables.LEVEL_INSTR_GRID_ROWS) {
        if (mInstructions[r][c] != null) {
          if (!mInstructions[r][c].getLetter().trim().equalsIgnoreCase("")) {
            mFather.image(mInstructions[r][c].getImage(), offsetX + (c * (mImageFW + mSpaceX)) + ((mImageFW - mImageW)/2),
                offsetY + (r * (mImageFH + mSpaceY)) + ((mImageFH - mImageH)/2), mImageW, mImageH);
          }
        }
        c++;
        if (c % CrossVariables.LEVEL_INSTR_GRID_COLS == 0) {
          c = 0;
          r++;
        }
      }
      mFather.noTint();
    }
    mImageH -= mImageTickH;
    mImageW -= mImageTickW;
  }
}
