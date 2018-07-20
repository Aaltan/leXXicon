package com.cyberg.lexxicon.structs;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

import com.cyberg.lexxicon.environment.CrossVariables;

public class LevelSagaStruct {

	private PApplet mFather = null;
	private PImage mImage = null;
	private int mLevelNum = -1;
  private int mImageW = 0;
  private int mImageH = 0;  
  private boolean mSelected = false;
  private boolean mMarked = false;
  private int mDurationFrame = -1;
  private ArrayList<NumberStruct> mNumbers = null;
  private ArrayList<StarStruct> mStars = null;
	
	public LevelSagaStruct(PApplet aFather, String anImage) {
		mFather = aFather;
    mImage = mFather.loadImage(anImage);
    mImage.resize(PApplet.round(CrossVariables.SAGA_IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X), PApplet.round(CrossVariables.SAGA_IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y));
    mImage.loadPixels();
    mImageW = mImage.width;
    mImageH = mImage.height;
	}
	
	public LevelSagaStruct(PImage anImage, ArrayList<NumberStruct> numbers, ArrayList<StarStruct> stars, int levelNum) {
		mImage = anImage;
    mImageW = mImage.width;
    mImageH = mImage.height;
		mNumbers = numbers;
		mStars = stars;
		mLevelNum = levelNum;
	}
	
  public PImage getImage() {
    return mImage;
  }
  
	public int getLevelNum() {
		return mLevelNum;
	}
	
  public int getImageW() {
  	return mImageW;
  }

  public int getImageH() {
  	return mImageH;
  }

  public int getDurationFrame() {
    return mDurationFrame;
  }
  
  public boolean getSelected() {
  	return mSelected;
  }
  
  public boolean getMarked() {
  	return mMarked;
  }
  
  public ArrayList<NumberStruct> getNumbers() {
  	return mNumbers;
  }
  
  public ArrayList<StarStruct> getStars() {
  	return mStars;
  }
  
	public void setLevelNum(int aValue) {
		mLevelNum = aValue;
	}
		
  public void setImageW(int aValue) {
  	mImageW = aValue;
  }
  
  public void setImageH(int aValue) {
  	mImageH = aValue;
  }
	
  public void setDurationFrame(int aValue) {
    mDurationFrame = aValue;
  }  
  
  public void setSelected(boolean aValue) {
  	mSelected = aValue;
  }
  
  public void setMarked(boolean aValue) {
  	mMarked = aValue;
  }  
  
  public void setNumbers(ArrayList<NumberStruct> aValue) {
  	mNumbers = aValue;
  }
  
  public void setStars(ArrayList<StarStruct> aValue) {
  	mStars = aValue;
  }  
}
