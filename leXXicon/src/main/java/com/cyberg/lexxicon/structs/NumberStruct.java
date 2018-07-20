package com.cyberg.lexxicon.structs;

import processing.core.PApplet;
import processing.core.PImage;

import com.cyberg.lexxicon.environment.CrossVariables;

public class NumberStruct {
	
	private PApplet mFather = null;
	private PImage mImage = null;
  private int mImageW = 0;
  private int mImageH = 0;  
  private int mNumber = -1;
	
	public NumberStruct(PApplet aFather, String anImage, int number) {
		mFather = aFather;
    mImage = mFather.loadImage(anImage);
    mImage.resize(PApplet.round(CrossVariables.SAGA_NUMBER_IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X), PApplet.round(CrossVariables.SAGA_NUMBER_IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y));
    mImage.loadPixels();
    mImageW = mImage.width;
    mImageH = mImage.height;
    mNumber = number;
	}
	
	public NumberStruct(PImage anImage, int number) {
    mImage = anImage;
    mImageW = mImage.width;
    mImageH = mImage.height;
    mNumber = number;
  }
  
	public PImage getImage() {
    return mImage;
  }
  
  public int getImageW() {
  	return mImageW;
  }
  
  public int getImageH() {
  	return mImageH;
  }
  
  public int getNumber() {
  	return mNumber;
  }
  
  public void setImageW(int aValue) {
  	mImageW = aValue;
  }
  
  public void setImageH(int aValue) {
  	mImageH = aValue;
  }  
}
