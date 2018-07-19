package com.cyberg.lexxicon.structs;

import java.util.Arrays;
import java.util.Collections;

import android.util.Log;

import com.cyberg.lexxicon.environment.CrossVariables;

import processing.core.PApplet;
import processing.core.PImage;

public class ObjectFactory {
  
  private LetterStruct EMPTY;
  private LetterStruct A;
  private LetterStruct B;
  private LetterStruct C;
  private LetterStruct D;
  private LetterStruct E;
  private LetterStruct F;
  private LetterStruct G;
  private LetterStruct H;
  private LetterStruct I;
  private LetterStruct L;
  private LetterStruct M;
  private LetterStruct N;
  private LetterStruct O;
  private LetterStruct P;
  private LetterStruct Q;
  private LetterStruct R;
  private LetterStruct S;
  private LetterStruct T;
  private LetterStruct U;
  private LetterStruct V;
  private LetterStruct Z;
  private LetterStruct J;  
  private LetterStruct K;  
  private LetterStruct Y;  
  private LetterStruct X;  
  private LetterStruct W;
      
  private PApplet mFather;
  private int[] mLetterBag;
  
  public ObjectFactory(PApplet aFather) {
    mFather = aFather;
    createLetters();
    createLetterBag();
  }
  
  public void createLetters() {
    EMPTY = new LetterStruct(mFather, "Empty.png", 0, 0, CrossVariables.EMPTY_LETTER, " ");
    A = new LetterStruct(mFather, "A.png", 1, 9, CrossVariables.A_LETTER, "A");
    B = new LetterStruct(mFather, "B.png", 5, 2, CrossVariables.B_LETTER, "B");
    C = new LetterStruct(mFather, "C.png", 2, 2, CrossVariables.C_LETTER, "C");
    D = new LetterStruct(mFather, "D.png", 5, 4, CrossVariables.D_LETTER, "D");
    E = new LetterStruct(mFather, "E.png", 1, 12, CrossVariables.E_LETTER, "E");
    F = new LetterStruct(mFather, "F.png", 5, 2, CrossVariables.F_LETTER, "F");
    G = new LetterStruct(mFather, "G.png", 8, 3, CrossVariables.G_LETTER, "G");
    H = new LetterStruct(mFather, "H.png", 8, 2, CrossVariables.H_LETTER, "H");
    I = new LetterStruct(mFather, "I.png", 1, 9, CrossVariables.I_LETTER, "I");
    L = new LetterStruct(mFather, "L.png", 3, 4, CrossVariables.L_LETTER, "L");
    M = new LetterStruct(mFather, "M.png", 3, 2, CrossVariables.M_LETTER, "M");
    N = new LetterStruct(mFather, "N.png", 3, 6, CrossVariables.N_LETTER, "N");
    O = new LetterStruct(mFather, "O.png", 1, 8, CrossVariables.O_LETTER, "O");
    P = new LetterStruct(mFather, "P.png", 5, 2, CrossVariables.P_LETTER, "P");
    Q = new LetterStruct(mFather, "Q.png", 10, 1, CrossVariables.Q_LETTER, "Q");
    R = new LetterStruct(mFather, "R.png", 2, 6, CrossVariables.R_LETTER, "R");
    S = new LetterStruct(mFather, "S.png", 2, 4, CrossVariables.S_LETTER, "S");
    T = new LetterStruct(mFather, "T.png", 2, 6, CrossVariables.T_LETTER, "T");
    U = new LetterStruct(mFather, "U.png", 3, 4, CrossVariables.U_LETTER, "U");
    V = new LetterStruct(mFather, "V.png", 5, 2, CrossVariables.V_LETTER, "V");
    Z = new LetterStruct(mFather, "Z.png", 8, 1, CrossVariables.Z_LETTER, "Z");
    J = new LetterStruct(mFather, "J.png", 10, 1, CrossVariables.J_LETTER, "J");
    K = new LetterStruct(mFather, "K.png", 5, 1, CrossVariables.K_LETTER, "K");
    Y = new LetterStruct(mFather, "Y.png", 4, 2, CrossVariables.Y_LETTER, "Y");
    X = new LetterStruct(mFather, "X.png", 10, 1, CrossVariables.X_LETTER, "X");
    W = new LetterStruct(mFather, "W.png", 10, 2, CrossVariables.W_LETTER, "W");
  }
  
  private void createLetterBag() {
  	int[] anArray = new int[A.getChance() + B.getChance() + C.getChance() + D.getChance() + E.getChance() +
  	                        F.getChance() + G.getChance() + H.getChance() + I.getChance() +
  	                        L.getChance() + M.getChance() + N.getChance() + O.getChance() +
  	                        P.getChance() + Q.getChance() + R.getChance() + S.getChance() +
  	                        T.getChance() + U.getChance() + V.getChance() + Z.getChance() +
  	                        J.getChance() + K.getChance() + Y.getChance() + X.getChance() + W.getChance()];
  	int startIdx = 0;
  	int stopIdx = A.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.A_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += B.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.B_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += C.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.C_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += D.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.D_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += E.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.E_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += F.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.F_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += G.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.G_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += H.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.H_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += I.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.I_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += L.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.L_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += M.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.M_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += N.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.N_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += O.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.O_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += P.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.P_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += Q.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.Q_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += R.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.R_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += S.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.S_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += T.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.T_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += U.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.U_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += V.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.V_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += Z.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.Z_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += J.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.J_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += K.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.K_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += Y.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.Y_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += X.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.X_LETTER;
  	}
  	startIdx = stopIdx;
  	stopIdx += W.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = CrossVariables.W_LETTER;
  	}
  	Collections.shuffle(Arrays.asList(anArray));
  	mLetterBag = anArray;
  }
  
  public LetterStruct getLetter() {
  	int maxRnd = A.getChance() + B.getChance() + C.getChance() + E.getChance() +
                 F.getChance() + G.getChance() + H.getChance() + I.getChance() +
                 L.getChance() + M.getChance() + N.getChance() + O.getChance() +
                 P.getChance() + Q.getChance() + R.getChance() + S.getChance() +
                 T.getChance() + U.getChance() + V.getChance() + Z.getChance() +
                 J.getChance() + K.getChance() + Y.getChance() + X.getChance() + W.getChance() - 1;
    int rndLetter = PApplet.round(mFather.random(0, maxRnd));
    return getLetter(mLetterBag[rndLetter]);
  }
  
  public LetterStruct getLetter(int aLetter) {
  	return getLetter(aLetter, true);
  }
  
  public LetterStruct getLetter(int aLetter, boolean clone) {
  	try {
	    switch (aLetter) {
      	case CrossVariables.EMPTY_LETTER:
      		if (clone) {
      			return new LetterStruct((PImage)EMPTY.getImage().clone(), EMPTY.getPoints(), EMPTY.getChance(), EMPTY.getLetterNumber(), EMPTY.getLetter());
      		}
      		else {
      			return EMPTY;
      		}
	      case CrossVariables.A_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)A.getImage().clone(), A.getPoints(), A.getChance(), A.getLetterNumber(), A.getLetter());
	      	}
	      	else {
	      		return A;
	      	}
	      case CrossVariables.B_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)B.getImage().clone(), B.getPoints(), B.getChance(), B.getLetterNumber(), B.getLetter());
	      	}
	      	else {
	      		return B;
	      	}
	      case CrossVariables.C_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)C.getImage().clone(), C.getPoints(), C.getChance(), C.getLetterNumber(), C.getLetter());
	      	}
	      	else {
	      		return C;
	      	}
	      case CrossVariables.D_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)D.getImage().clone(), D.getPoints(), D.getChance(), D.getLetterNumber(), D.getLetter());
	      	}
	      	else {
	      		return D;
	      	}
	      case CrossVariables.E_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)E.getImage().clone(), E.getPoints(), E.getChance(), E.getLetterNumber(), E.getLetter());
	      	}
	      	else {
	      		return E;
	      	}
	      case CrossVariables.F_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)F.getImage().clone(), F.getPoints(), F.getChance(), F.getLetterNumber(), F.getLetter());
	      	}
	      	else {
	      		return F;
	      	}
	      case CrossVariables.G_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)G.getImage().clone(), G.getPoints(), G.getChance(), G.getLetterNumber(), G.getLetter());
	      	}
	      	else {
	      		return G;
	      	}
	      case CrossVariables.H_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)H.getImage().clone(), H.getPoints(), H.getChance(), H.getLetterNumber(), H.getLetter());
	      	}
	      	else {
	      		return H;
	      	}
	      case CrossVariables.I_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)I.getImage().clone(), I.getPoints(), I.getChance(), I.getLetterNumber(), I.getLetter());
	      	}
	      	else {
	      		return I;
	      	}
	      case CrossVariables.L_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)L.getImage().clone(), L.getPoints(), L.getChance(), L.getLetterNumber(), L.getLetter());
	      	}
	      	else {
	      		return L;
	      	}
	      case CrossVariables.M_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)M.getImage().clone(), M.getPoints(), M.getChance(), M.getLetterNumber(), M.getLetter());
	      	}
	      	else {
	      		return M;
	      	}
	      case CrossVariables.N_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)N.getImage().clone(), N.getPoints(), N.getChance(), N.getLetterNumber(), N.getLetter());
	      	}
	      	else {
	      		return N;
	      	}
	      case CrossVariables.O_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)O.getImage().clone(), O.getPoints(), O.getChance(), O.getLetterNumber(), O.getLetter());
	      	}
	      	else {
	      		return O;
	      	}
	      case CrossVariables.P_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)P.getImage().clone(), P.getPoints(), P.getChance(), P.getLetterNumber(), P.getLetter());
	      	}
	      	else {
	      		return P;
	      	}
	      case CrossVariables.Q_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)Q.getImage().clone(), Q.getPoints(), Q.getChance(), Q.getLetterNumber(), Q.getLetter());
	      	}
	      	else {
	      		return Q;
	      	}
	      case CrossVariables.R_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)R.getImage().clone(), R.getPoints(), R.getChance(), R.getLetterNumber(), R.getLetter());
	      	}
	      	else {
	      		return R;
	      	}
	      case CrossVariables.S_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)S.getImage().clone(), S.getPoints(), S.getChance(), S.getLetterNumber(), S.getLetter());
	      	}
	      	else {
	      		return S;
	      	}
	      case CrossVariables.T_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)T.getImage().clone(), T.getPoints(), T.getChance(), T.getLetterNumber(), T.getLetter());
	      	}
	      	else {
	      		return T;
	      	}
	      case CrossVariables.U_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)U.getImage().clone(), U.getPoints(), U.getChance(), U.getLetterNumber(), U.getLetter());
	      	}
	      	else {
	      		return U;
	      	}
	      case CrossVariables.V_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)V.getImage().clone(), V.getPoints(), V.getChance(), V.getLetterNumber(), V.getLetter());
	      	}
	      	else {
	      		return V;
	      	}
	      case CrossVariables.Z_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)Z.getImage().clone(), Z.getPoints(), Z.getChance(), Z.getLetterNumber(), Z.getLetter());
	      	}
	      	else {
	      		return Z;
	      	}
	      case CrossVariables.J_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)J.getImage().clone(), J.getPoints(), J.getChance(), J.getLetterNumber(), J.getLetter());
	      	}
	      	else {
	      		return J;
	      	}
	      case CrossVariables.K_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)K.getImage().clone(), K.getPoints(), K.getChance(), K.getLetterNumber(), K.getLetter());
	      	}
	      	else {
	      		return K;
	      	}
	      case CrossVariables.X_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)X.getImage().clone(), X.getPoints(), X.getChance(), X.getLetterNumber(), X.getLetter());
	      	}
	      	else {
	      		return X;
	      	}
	      case CrossVariables.Y_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)Y.getImage().clone(), Y.getPoints(), Y.getChance(), Y.getLetterNumber(), Y.getLetter());
	      	}
	      	else {
	      		return Y;
	      	}
	      case CrossVariables.W_LETTER:
	      	if (clone) {
	      		return new LetterStruct((PImage)W.getImage().clone(), W.getPoints(), W.getChance(), W.getLetterNumber(), W.getLetter());
	      	}
	      	else {
	      		return W;
	      	}
	      default:
	      	return null;
	    }
  	}
	  catch (Exception _Ex) {
	  	Log.v("***********", "getLetter", _Ex);
	  	return null;
	  }
  }  
}