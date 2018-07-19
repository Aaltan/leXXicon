package com.cyberg.lexxicon.game;

public class EmptySlot {
  
  private int mRow;
  private int mCol;
  
  public EmptySlot(int row, int col) {
    mRow = row;
    mCol = col;
  }
  
  public int getRow() {
    return mRow;
  }
  
  public String getSRow() {
    return "" + mRow;
  }
  
  public int getCol() {
    return mCol;
  }
}