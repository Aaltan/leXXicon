package com.cyberg.lexxicon.structs;

import java.util.Arrays;
import java.util.Collections;

import android.util.Log;

import processing.core.PApplet;
import processing.core.PImage;

public class ObjectFactory {
  
  private LetterStruct EMPTY;
	private LetterStruct ACAPO;
	private LetterStruct MENO;
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

  private LetterStruct _0;
  private LetterStruct _1;
  private LetterStruct _2;
  private LetterStruct _3;
  private LetterStruct _4;
  private LetterStruct _5;
  private LetterStruct _6;
  private LetterStruct _7;
  private LetterStruct _8;
  private LetterStruct _9;

  private PApplet mFather;
  private String[] mLetterBag;
  
  public ObjectFactory(PApplet aFather) {
    mFather = aFather;
    createLetters();
    createLetterBag();
  }
  
  public void createLetters() {
    EMPTY = new LetterStruct(mFather, "Empty.png", 0, 0, " ");
		ACAPO = new LetterStruct(mFather, "acapo1.png", 0, 0, "=");
		MENO = new LetterStruct(mFather, "Meno.png", 0, 0, "-");
    A = new LetterStruct(mFather, "A.png", 1, 9, "A");
    B = new LetterStruct(mFather, "B.png", 5, 2, "B");
    C = new LetterStruct(mFather, "C.png", 2, 2, "C");
    D = new LetterStruct(mFather, "D.png", 5, 4, "D");
    E = new LetterStruct(mFather, "E.png", 1, 12, "E");
    F = new LetterStruct(mFather, "F.png", 5, 2, "F");
    G = new LetterStruct(mFather, "G.png", 8, 3, "G");
    H = new LetterStruct(mFather, "H.png", 8, 2, "H");
    I = new LetterStruct(mFather, "I.png", 1, 9, "I");
    L = new LetterStruct(mFather, "L.png", 3, 4, "L");
    M = new LetterStruct(mFather, "M.png", 3, 3, "M");
    N = new LetterStruct(mFather, "N.png", 3, 3, "N");
    O = new LetterStruct(mFather, "O.png", 1, 8, "O");
    P = new LetterStruct(mFather, "P.png", 5, 2, "P");
    Q = new LetterStruct(mFather, "Q.png", 10, 1, "Q");
    R = new LetterStruct(mFather, "R.png", 2, 6, "R");
    S = new LetterStruct(mFather, "S.png", 2, 4, "S");
    T = new LetterStruct(mFather, "T.png", 2, 6, "T");
    U = new LetterStruct(mFather, "U.png", 3, 4, "U");
    V = new LetterStruct(mFather, "V.png", 5, 2, "V");
    Z = new LetterStruct(mFather, "Z.png", 8, 1, "Z");
    J = new LetterStruct(mFather, "J.png", 10, 1, "J");
    K = new LetterStruct(mFather, "K.png", 5, 1, "K");
    Y = new LetterStruct(mFather, "Y.png", 4, 2, "Y");
    X = new LetterStruct(mFather, "X.png", 10, 1, "X");
    W = new LetterStruct(mFather, "W.png", 10, 2, "W");
    _0 = new LetterStruct(mFather, "0G.png", 0, 0, "0");
    _1 = new LetterStruct(mFather, "1G.png", 1, 0, "1");
    _2 = new LetterStruct(mFather, "2G.png", 2, 0, "2");
    _3 = new LetterStruct(mFather, "3G.png", 3, 0, "3");
    _4 = new LetterStruct(mFather, "4G.png", 4, 0, "4");
    _5 = new LetterStruct(mFather, "5G.png", 5, 0, "5");
    _6 = new LetterStruct(mFather, "6G.png", 6, 0, "6");
    _7 = new LetterStruct(mFather, "7G.png", 7, 0, "7");
    _8 = new LetterStruct(mFather, "8G.png", 8, 0, "8");
    _9 = new LetterStruct(mFather, "9G.png", 9, 0, "9");
  }

  private void createLetterBag() {
  	String[] anArray = new String[A.getChance() + B.getChance() + C.getChance() + D.getChance() + E.getChance() +
                                  F.getChance() + G.getChance() + H.getChance() + I.getChance() +
                                  L.getChance() + M.getChance() + N.getChance() + O.getChance() +
                                  P.getChance() + Q.getChance() + R.getChance() + S.getChance() +
                                  T.getChance() + U.getChance() + V.getChance() + Z.getChance() +
                                  J.getChance() + K.getChance() + Y.getChance() + X.getChance() + W.getChance()];
  	int startIdx = 0;
  	int stopIdx = A.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "A";
  	}
  	startIdx = stopIdx;
  	stopIdx += B.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "B";
  	}
  	startIdx = stopIdx;
  	stopIdx += C.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "C";
  	}
  	startIdx = stopIdx;
  	stopIdx += D.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "D";
  	}
  	startIdx = stopIdx;
  	stopIdx += E.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "E";
  	}
  	startIdx = stopIdx;
  	stopIdx += F.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "F";
  	}
  	startIdx = stopIdx;
  	stopIdx += G.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "G";
  	}
  	startIdx = stopIdx;
  	stopIdx += H.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "H";
  	}
  	startIdx = stopIdx;
  	stopIdx += I.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "I";
  	}
  	startIdx = stopIdx;
  	stopIdx += L.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "L";
  	}
  	startIdx = stopIdx;
  	stopIdx += M.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "M";
  	}
  	startIdx = stopIdx;
  	stopIdx += N.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "N";
  	}
  	startIdx = stopIdx;
  	stopIdx += O.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "O";
  	}
  	startIdx = stopIdx;
  	stopIdx += P.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "P";
  	}
  	startIdx = stopIdx;
  	stopIdx += Q.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "Q";
  	}
  	startIdx = stopIdx;
  	stopIdx += R.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "R";
  	}
  	startIdx = stopIdx;
  	stopIdx += S.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "S";
  	}
  	startIdx = stopIdx;
  	stopIdx += T.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "T";
  	}
  	startIdx = stopIdx;
  	stopIdx += U.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "U";
  	}
  	startIdx = stopIdx;
  	stopIdx += V.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "V";
  	}
  	startIdx = stopIdx;
  	stopIdx += Z.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "Z";
  	}
  	startIdx = stopIdx;
  	stopIdx += J.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "J";
  	}
  	startIdx = stopIdx;
  	stopIdx += K.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "K";
  	}
  	startIdx = stopIdx;
  	stopIdx += Y.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "Y";
  	}
  	startIdx = stopIdx;
  	stopIdx += X.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "X";
  	}
  	startIdx = stopIdx;
  	stopIdx += W.getChance();
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = "W";
  	}
  	Collections.shuffle(Arrays.asList(anArray));
  	mLetterBag = anArray;
  }

	public LetterStruct getNumber() {
		int rndNumber = PApplet.round(mFather.random(48, 57));
		String aNum = Character.toString((char)rndNumber);
		return getLetter(aNum);
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
  
  public LetterStruct getLetter(String aLetter) {
  	return getLetter(aLetter, true);
  }

  public LetterStruct getLetter(String aLetter, boolean clone) {
    int asciiVal = -1;
    try {
      asciiVal = (int) aLetter.charAt(0);
    }
    catch (NumberFormatException _NFE) {}

  	try {
	    switch (asciiVal) {
      	case 32:                     // " " Ascii Space Value
      		if (clone) {
      			return new LetterStruct((PImage)EMPTY.getImage().clone(), EMPTY.getPoints(), EMPTY.getChance(), EMPTY.getLetter());
      		}
      		else {
      			return EMPTY;
      		}
				case 45:                     // "-"
					if (clone) {
						return new LetterStruct((PImage)MENO.getImage().clone(), MENO.getPoints(), MENO.getChance(), MENO.getLetter());
					}
					else {
						return MENO;
					}
				case 61:                     // "="
					if (clone) {
						return new LetterStruct((PImage)ACAPO.getImage().clone(), ACAPO.getPoints(), ACAPO.getChance(), ACAPO.getLetter());
					}
					else {
						return ACAPO;
					}
        case 48:                     // "0"
          if (clone) {
            return new LetterStruct((PImage)_0.getImage().clone(), _0.getPoints(), _0.getChance(), _0.getLetter());
          }
          else {
            return _0;
          }
        case 49:                     // "1"
          if (clone) {
            return new LetterStruct((PImage)_1.getImage().clone(), _1.getPoints(), _1.getChance(), _1.getLetter());
          }
          else {
            return _1;
          }
        case 50:                     // "2"
          if (clone) {
            return new LetterStruct((PImage)_2.getImage().clone(), _2.getPoints(), _2.getChance(), _2.getLetter());
          }
          else {
            return _2;
          }
        case 51:                     // "3"
          if (clone) {
            return new LetterStruct((PImage)_3.getImage().clone(), _3.getPoints(), _3.getChance(), _3.getLetter());
          }
          else {
            return _3;
          }
        case 52:                     // "4"
          if (clone) {
            return new LetterStruct((PImage)_4.getImage().clone(), _4.getPoints(), _4.getChance(), _4.getLetter());
          }
          else {
            return _4;
          }
        case 53:                     // "5"
          if (clone) {
            return new LetterStruct((PImage)_5.getImage().clone(), _5.getPoints(), _5.getChance(), _5.getLetter());
          }
          else {
            return _5;
          }
        case 54:                     // "6"
          if (clone) {
            return new LetterStruct((PImage)_6.getImage().clone(), _6.getPoints(), _6.getChance(), _6.getLetter());
          }
          else {
            return _6;
          }
        case 55:                     // "7"
          if (clone) {
            return new LetterStruct((PImage)_7.getImage().clone(), _7.getPoints(), _7.getChance(), _7.getLetter());
          }
          else {
            return _7;
          }
        case 56:                     // "8"
          if (clone) {
            return new LetterStruct((PImage)_8.getImage().clone(), _8.getPoints(), _8.getChance(), _8.getLetter());
          }
          else {
            return _8;
          }
        case 57:                     // "9"
          if (clone) {
            return new LetterStruct((PImage)_9.getImage().clone(), _9.getPoints(), _9.getChance(), _9.getLetter());
          }
          else {
            return _9;
          }
	      case 65:                    // "A"
	      	if (clone) {
	      		return new LetterStruct((PImage)A.getImage().clone(), A.getPoints(), A.getChance(), A.getLetter());
	      	}
	      	else {
	      		return A;
	      	}
	      case 66:                    // "B"
	      	if (clone) {
	      		return new LetterStruct((PImage)B.getImage().clone(), B.getPoints(), B.getChance(), B.getLetter());
	      	}
	      	else {
	      		return B;
	      	}
	      case 67:                    // "C"
	      	if (clone) {
	      		return new LetterStruct((PImage)C.getImage().clone(), C.getPoints(), C.getChance(), C.getLetter());
	      	}
	      	else {
	      		return C;
	      	}
	      case 68:                    // "d"
	      	if (clone) {
	      		return new LetterStruct((PImage)D.getImage().clone(), D.getPoints(), D.getChance(), D.getLetter());
	      	}
	      	else {
	      		return D;
	      	}
	      case 69:                    // "E"
	      	if (clone) {
	      		return new LetterStruct((PImage)E.getImage().clone(), E.getPoints(), E.getChance(), E.getLetter());
	      	}
	      	else {
	      		return E;
	      	}
	      case 70:                    // "F"
	      	if (clone) {
	      		return new LetterStruct((PImage)F.getImage().clone(), F.getPoints(), F.getChance(), F.getLetter());
	      	}
	      	else {
	      		return F;
	      	}
	      case 71:                    // "G"
	      	if (clone) {
	      		return new LetterStruct((PImage)G.getImage().clone(), G.getPoints(), G.getChance(), G.getLetter());
	      	}
	      	else {
	      		return G;
	      	}
	      case 72:                    // "H"
	      	if (clone) {
	      		return new LetterStruct((PImage)H.getImage().clone(), H.getPoints(), H.getChance(), H.getLetter());
	      	}
	      	else {
	      		return H;
	      	}
	      case 73:                    // "I"
	      	if (clone) {
	      		return new LetterStruct((PImage)I.getImage().clone(), I.getPoints(), I.getChance(), I.getLetter());
	      	}
	      	else {
	      		return I;
	      	}
        case 74:                    // "J"
          if (clone) {
            return new LetterStruct((PImage)J.getImage().clone(), J.getPoints(), J.getChance(), J.getLetter());
          }
          else {
            return J;
          }
        case 75:                    // "K"
          if (clone) {
            return new LetterStruct((PImage)K.getImage().clone(), K.getPoints(), K.getChance(), K.getLetter());
          }
          else {
            return K;
          }
	      case 76:                    // "L"
	      	if (clone) {
	      		return new LetterStruct((PImage)L.getImage().clone(), L.getPoints(), L.getChance(), L.getLetter());
	      	}
	      	else {
	      		return L;
	      	}
	      case 77:                    // "M"
	      	if (clone) {
	      		return new LetterStruct((PImage)M.getImage().clone(), M.getPoints(), M.getChance(), M.getLetter());
	      	}
	      	else {
	      		return M;
	      	}
	      case 78:                    // "N"
	      	if (clone) {
	      		return new LetterStruct((PImage)N.getImage().clone(), N.getPoints(), N.getChance(), N.getLetter());
	      	}
	      	else {
	      		return N;
	      	}
	      case 79:                    // "O"
	      	if (clone) {
	      		return new LetterStruct((PImage)O.getImage().clone(), O.getPoints(), O.getChance(), O.getLetter());
	      	}
	      	else {
	      		return O;
	      	}
	      case 80:                    // "P"
	      	if (clone) {
	      		return new LetterStruct((PImage)P.getImage().clone(), P.getPoints(), P.getChance(), P.getLetter());
	      	}
	      	else {
	      		return P;
	      	}
	      case 81:                    // "Q"
	      	if (clone) {
	      		return new LetterStruct((PImage)Q.getImage().clone(), Q.getPoints(), Q.getChance(), Q.getLetter());
	      	}
	      	else {
	      		return Q;
	      	}
	      case 82:                    // "R"
	      	if (clone) {
	      		return new LetterStruct((PImage)R.getImage().clone(), R.getPoints(), R.getChance(), R.getLetter());
	      	}
	      	else {
	      		return R;
	      	}
	      case 83:                    // "S"
	      	if (clone) {
	      		return new LetterStruct((PImage)S.getImage().clone(), S.getPoints(), S.getChance(), S.getLetter());
	      	}
	      	else {
	      		return S;
	      	}
	      case 84:                    // "T"
	      	if (clone) {
	      		return new LetterStruct((PImage)T.getImage().clone(), T.getPoints(), T.getChance(), T.getLetter());
	      	}
	      	else {
	      		return T;
	      	}
	      case 85:                    // "U"
	      	if (clone) {
	      		return new LetterStruct((PImage)U.getImage().clone(), U.getPoints(), U.getChance(), U.getLetter());
	      	}
	      	else {
	      		return U;
	      	}
	      case 86:                    // "V"
	      	if (clone) {
	      		return new LetterStruct((PImage)V.getImage().clone(), V.getPoints(), V.getChance(), V.getLetter());
	      	}
	      	else {
	      		return V;
	      	}
        case 87:                    // "W"
          if (clone) {
            return new LetterStruct((PImage)W.getImage().clone(), W.getPoints(), W.getChance(), W.getLetter());
          }
          else {
            return W;
          }
        case 88:                    // "X"
          if (clone) {
            return new LetterStruct((PImage)X.getImage().clone(), X.getPoints(), X.getChance(), X.getLetter());
          }
          else {
            return X;
          }
        case 89:                    // "Y"
          if (clone) {
            return new LetterStruct((PImage)Y.getImage().clone(), Y.getPoints(), Y.getChance(), Y.getLetter());
          }
          else {
            return Y;
          }
	      case 90:                    // "Z"
	      	if (clone) {
	      		return new LetterStruct((PImage)Z.getImage().clone(), Z.getPoints(), Z.getChance(), Z.getLetter());
	      	}
	      	else {
	      		return Z;
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