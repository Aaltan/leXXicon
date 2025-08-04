package com.cyberg.lexxicon.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.cyberg.lexxicon.Main;

public class VariousUtils {

  public static int getPoints(Main aFather, String composedWord) {
  	int aReturnValue = 0;
  	for (int i=0; i<composedWord.length(); i++) {
  		aReturnValue += aFather.mObjFactory.getLetter((composedWord.substring(i, i+1)), false).getPoints();
  	}
  	return aReturnValue;
  }
  
	public static void salvaLogCat(){
		StringBuilder log = null;
		try {			 
			Process process = Runtime.getRuntime().exec("logcat -d -v time");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			log = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null)
				log.append(line + "\n");
		} 
		catch (IOException e) {
			
		}       
		final String logString = new String(log.toString());
		File dir = new File (Environment.getExternalStorageDirectory() + "/debug/");
		dir.mkdirs();
                File file = new File(dir, "leXXlog.txt");
		try {		
			FileOutputStream fOut = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fOut); 
			osw.write(logString);            
			osw.flush();
			osw.close();		
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String justify(String aString, int places, String filler, boolean left) {
		String aReturnValue = aString;
		if (aReturnValue.length() > places) {
			if (left) {
				aReturnValue = aReturnValue.substring(aReturnValue.length() - places);
			} else {
				aReturnValue = aReturnValue.substring(0, places);
			}
		}
		for (int i = aString.length(); i < places; i++) {
			if (left) {
				aReturnValue = filler + aReturnValue;
			}
			else {
				aReturnValue = aReturnValue + filler;
			}
		}
		return aReturnValue;
	}

  public static String getAppVersion(Context aContext) {
    String aReturnValue = "leXXicon";
    PackageManager aPM = aContext.getPackageManager();
    try {
      PackageInfo aPI = aPM.getPackageInfo(aContext.getPackageName(), 0);
      aReturnValue += " V." + aPI.versionName;
    }
    catch (Exception _Ex) {
      Log.v("getAppVersion", "Errore in retrieve Package Info", _Ex);
    }
    return aReturnValue;
  }

  public static boolean isNumeric(String s) {
    return s != null && s.matches("[-+]?\\d*\\.?\\d+");
  }
}
