package com.cyberg.lexxicon.structs;

import com.cyberg.lexxicon.environment.CrossVariables;

import processing.core.PApplet;
import processing.core.PImage;

public class BonusStruct {
  
  private PImage mImage;
  private int mBonusType;
  private int mImageW = 0;
  private int mImageH = 0;  
  private int mDurationFrame = -1;
  private PApplet mFather;
  
  public BonusStruct(PApplet aFather, String anImage, int bonusType) {
    mFather = aFather;
    mImage = mFather.loadImage(anImage);
    mImage.resize(PApplet.round(CrossVariables.BONUS_STANDARD_X / CrossVariables.RESIZE_FACTOR_X), PApplet.round(CrossVariables.BONUS_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y));
    mImage.loadPixels();
    mImageW = mImage.width;
    mImageH = mImage.height;
    mBonusType = bonusType;
  }
    
  public BonusStruct(PApplet aFather, PImage anImage, int bonusType) {
    mFather = aFather;
    mImage = anImage;
    mImageW = mImage.width;
    mImageH = mImage.height;
    mBonusType = bonusType;
  }
  
  public PImage getImage() {
    return mImage;
  }

  public int getType() {
    return mBonusType;
  }

  public void setImageW(int aValue) {
  	mImageW = aValue;
  }
  
  public int getImageW() {
  	return mImageW;
  }

  public void setImageH(int aValue) {
  	mImageH = aValue;
  }
  
  public int getImageH() {
  	return mImageH;
  }

  public void setDurationFrame(int aValue) {
    mDurationFrame = aValue;
  }  
  
  public int getDurationFrame() {
    return mDurationFrame;
  }    
}