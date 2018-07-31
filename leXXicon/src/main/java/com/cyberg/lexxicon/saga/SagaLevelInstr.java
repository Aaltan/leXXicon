package com.cyberg.lexxicon.saga;

import com.cyberg.lexxicon.Main;
import com.cyberg.lexxicon.R;
import com.cyberg.lexxicon.environment.CrossVariables;
import com.cyberg.lexxicon.structs.LetterStruct;
import com.cyberg.lexxicon.structs.ObjectFactory;
import com.cyberg.lexxicon.utils.VariousUtils;

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
    mInstructions = new LetterStruct[CrossVariables.LEVEL_INSTR_GRID_ROWS][CrossVariables.LEVEL_INSTR_GRID_COLS];
    composeInstructions();
  }

  private void composeInstructions() {
    addChars(0, (mFather.getResources().getString(R.string.lvltitle) + VariousUtils.justify(("" + mLevel), 3, "0", true)));
    String lvlString = "lvl" + VariousUtils.justify(("" + mLevel), 3, "0", true);
    addChars(1, (mFather.getResources().getString(mFather.getResources().getIdentifier(lvlString + "title", "string",mFather.getApplicationContext().getPackageName()))));
    addChars(3, (mFather.getResources().getString(mFather.getResources().getIdentifier(lvlString + "content", "string",mFather.getApplicationContext().getPackageName()))));
    addChars(13, (mFather.getResources().getString(R.string.lvlstart)));
  }

  private void addChars(int aRow, String aString) {
    int row = aRow;
    int col = 0;
    for (int i=0; i<aString.length(); i++) {
      if (col > (CrossVariables.LEVEL_INSTR_GRID_COLS - 1)) {
        col = 0;
        row++;
      }
      String aChar = aString.substring(i, i+1);
      mInstructions[row][col] = mObjFactory.getLetter(aChar);
      col++;
    }
  }

  public void update(float aTX, float aTY) {
    int offsetX = PApplet.round((CrossVariables.SCREEN_STANDARD_X -
        (CrossVariables.LEVEL_INSTR_IMAGE_STANDARD_X * CrossVariables.LEVEL_INSTR_GRID_COLS) -
        (CrossVariables.LEVEL_INSTR_IMAGE_STANDARD_X_SPACE * (CrossVariables.LEVEL_INSTR_GRID_COLS - 1))) / 2 / CrossVariables.RESIZE_FACTOR_X);
    int offsetY = PApplet.round((CrossVariables.SCREEN_STANDARD_Y -
        (CrossVariables.LEVEL_INSTR_IMAGE_STANDARD_Y * CrossVariables.LEVEL_INSTR_GRID_ROWS) -
        (CrossVariables.LEVEL_INSTR_IMAGE_STANDARD_Y_SPACE * (CrossVariables.LEVEL_INSTR_GRID_ROWS - 1))) / 2 / CrossVariables.RESIZE_FACTOR_Y);

    if (mFather.frameCount % CrossVariables.LEVEL_INSTR_DISPLAY_DELAY == 0) {
      if (CrossVariables.LEVEL_INSTR_LETTERS_SHOWN < 200) {
        CrossVariables.LEVEL_INSTR_LETTERS_SHOWN++;
      }
    }
    int r = 0;
    int c = 0;
    for (int i=0; i<CrossVariables.LEVEL_INSTR_LETTERS_SHOWN; i++) {
      if (r == 0) mFather.tint(200, 200, 200);
      if (r == 1) mFather.tint(225, 225, 225);
      if (r == 13) {
        mFather.tint(255, 255, 0);
      }
      if (r<CrossVariables.LEVEL_INSTR_GRID_ROWS) {
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
    int yTop = offsetY + ((CrossVariables.LEVEL_INSTR_GRID_ROWS - 1) * (mImageFH + mSpaceY));
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

    if (mFather.frameCount % CrossVariables.LEVEL_INSTR_DISPLAY_DELAY == 0) {
      if (CrossVariables.LEVEL_INSTR_LETTERS_SHOWN < 200) {
        CrossVariables.LEVEL_INSTR_LETTERS_SHOWN++;
      }
    }
    int r = 0;
    int c = 0;
    for (int i=0; i<CrossVariables.LEVEL_INSTR_LETTERS_SHOWN; i++) {
      if (r == 0) mFather.tint(200, 200, 200);
      if (r == 1) mFather.tint(225, 225, 225);
      if (r == 13) {
        mFather.tint(255, 255, 0);
      }
      if (r<CrossVariables.LEVEL_INSTR_GRID_ROWS) {
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
