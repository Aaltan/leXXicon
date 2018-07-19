package com.cyberg.lexxicon.solver;

import java.util.Arrays;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.cyberg.lexxicon.environment.CrossVariables;

public class Solver extends Thread {
	
	private Handler mHandler;
	private String[] mBoard;
	
  public Solver(Handler aHandler, String[] board) {
  	mHandler = aHandler;
  	mBoard = board;
  }
  
  public void solveGrid() {
  	sendMessageToHost(CrossVariables.JOB_STARTED);
  	initializeBoard(mBoard);
    setPossibleTriplets();
    checkWordTriplets();
    checkWords();
  	sendMessageToHost(CrossVariables.JOB_ENDED);
  }
  
  private void initializeBoard(String[] letters) {
    for (int row=0; row<CrossVariables.BOARD_SIZE; row++) {
      for (int col=0; col<CrossVariables.BOARD_SIZE; col++) {
      	CrossVariables.board[row*CrossVariables.BOARD_SIZE + col] = (byte) letters[row].charAt(col) - 65;
      }
    }
  }

  private void setPossibleTriplets() {
    Arrays.fill(CrossVariables.possibleTriplets, false); // Reset list
    int i=0;
    while (i<CrossVariables.boardTripletIndices.length) {
      int triplet = (((CrossVariables.board[CrossVariables.boardTripletIndices[i++]]  << CrossVariables.ALPHABET_SIZE) +
      								 CrossVariables.board[CrossVariables.boardTripletIndices[i++]]) << CrossVariables.ALPHABET_SIZE) +
                       CrossVariables.board[CrossVariables.boardTripletIndices[i++]];
      CrossVariables.possibleTriplets[triplet] = true; 
    }
  }

  private void checkWordTriplets() {
  	CrossVariables.candidateCount = 0;
    for (DictionaryEntry entry: CrossVariables.dictionary) {
      boolean ok = true;
      int len = entry.triplets.length;
      for (int t=0; (t<len) && ok; t++) {
        ok = CrossVariables.possibleTriplets[entry.triplets[t]];
      }
      if (ok) {
      	CrossVariables.candidateWords[CrossVariables.candidateCount++] = entry;
      }
    }
  }

  private void checkWords() { // Can probably be optimized a lot
  	CrossVariables.foundCount = 0;
    for (int i=0; i<CrossVariables.candidateCount; i++) {
      DictionaryEntry candidate = CrossVariables.candidateWords[i];
      for (int j=0; j<CrossVariables.board.length; j++) {
        if (CrossVariables.board[j]==candidate.letters[0]) { 
        	CrossVariables.usedBoardPositions[0] = j;
          if (checkNextLetters(candidate, 1, j)) {
          	CrossVariables.foundWords[CrossVariables.foundCount++] = candidate;
            break;
          }
        }
      }
    }
  }

  private boolean checkNextLetters(DictionaryEntry candidate, int letter, int pos) {
    if (letter == candidate.letters.length) {
      return true;
    }
    int match = candidate.letters[letter];
    for (int move: CrossVariables.moves) {
      int next=pos+move;
      if ((next>=0) && (next<CrossVariables.board.length) && (CrossVariables.board[next]==match) && !CrossVariables.isWrap(pos, next)) {
        boolean ok = true;
        for (int i=0; (i<letter) && ok; i++) {
          ok = CrossVariables.usedBoardPositions[i]!=next;
        }
        if (ok) {
        	CrossVariables.usedBoardPositions[letter] = next;
          if (checkNextLetters(candidate, letter+1, next)) return true;
        }
      }
    }   
    return false;
  }
  
  private void sendMessageToHost(int aType) {
    Message msg = mHandler.obtainMessage();
    Bundle aBundle = new Bundle();
    switch (aType) {
	  	case CrossVariables.JOB_STARTED:
	      aBundle.putInt("type", aType);
	      break;
    	case CrossVariables.JOB_ENDED:
        aBundle.putInt("type", aType);
        break;
    	case CrossVariables.JOB_ABORTED:
        aBundle.putInt("type", aType);
        break;
    }
    msg.setData(aBundle);
    mHandler.dispatchMessage(msg);
  }
  
	@Override
	public void run() {
		solveGrid();
	}
}