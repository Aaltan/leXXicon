package com.cyberg.lexxicon.structs;

import com.cyberg.lexxicon.environment.CrossVariables;

import processing.core.PApplet;
import processing.core.PImage;

public class LetterStruct {
  
  private PImage mImage;
  private int mPoints;
  private int mChance;
  private String mLetter = "";
  private int mBonus = 0;
  private int mImageW = 0;
  private int mImageH = 0;  
  private boolean mSelected = false;
  private boolean mMarked = false;
  private int mDurationFrame = -1;
  private PApplet mFather;
  
  public LetterStruct(PApplet aFather, String anImage, int points, int chance, String letter) {
    mFather = aFather;
    mImage = mFather.loadImage(anImage);
    mImage.resize(PApplet.round(CrossVariables.IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X), PApplet.round(CrossVariables.IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y));
    mImage.loadPixels();
    mImageW = mImage.width;
    mImageH = mImage.height;
    mPoints = points;
    mChance = chance;
    mLetter = letter;
  }
    
  public LetterStruct(PImage anImage, int points, int chance, String letter) {
    mImage = anImage;
    mImageW = mImage.width;
    mImageH = mImage.height;
    mPoints = points;
    mChance = chance;
    mLetter = letter;
  }
  
  public PImage getImage() {
    return mImage;
  }
  
  public int getPoints() {
    return mPoints;
  }
  
  public int getChance() {
    return mChance;
  }

  public String getLetter() {
    return mLetter;
  }
  
  public void setBonus(int aBonus) {
    mBonus = aBonus;
  }

  public int getBonus() {
    return mBonus;
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
  
  public void setSelected(boolean aValue) {
  	mSelected = aValue;
  }
  
  public boolean getSelected() {
  	return mSelected;
  }
  
  public void setMarked(boolean aValue) {
  	mMarked = aValue;
  }
  
  public boolean getMarked() {
  	return mMarked;
  }  
}