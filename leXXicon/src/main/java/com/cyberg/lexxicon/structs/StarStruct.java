package com.cyberg.lexxicon.structs;

import processing.core.PApplet;
import processing.core.PImage;

import com.cyberg.lexxicon.environment.CrossVariables;

public class StarStruct {
	
	private PApplet mFather = null;
	private PImage mImage = null;
  private int mImageW = 0;
  private int mImageH = 0;  
	
	public StarStruct(PApplet father, String anImage) {
		mFather = father;
    mImage = mFather.loadImage(anImage);
    mImage.resize(PApplet.round(CrossVariables.STAR_IMAGE_STANDARD_X / CrossVariables.RESIZE_FACTOR_X), PApplet.round(CrossVariables.STAR_IMAGE_STANDARD_Y / CrossVariables.RESIZE_FACTOR_Y));
    mImage.loadPixels();
    mImageW = mImage.width;
    mImageH = mImage.height;
	}
	
	public StarStruct(PImage anImage) {
    mImage = anImage;
    mImageW = mImage.width;
    mImageH = mImage.height;
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
  
  public void setImageW(int aValue) {
  	mImageW = aValue;
  }
  
  public void setImageH(int aValue) {
  	mImageH = aValue;
  }  
}
