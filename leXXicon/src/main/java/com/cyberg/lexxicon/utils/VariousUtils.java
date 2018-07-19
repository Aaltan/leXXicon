package com.cyberg.lexxicon.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.os.Environment;

import com.cyberg.lexxicon.Main;

public class VariousUtils {

  public static int getPoints(Main aFather, String composedWord) {
  	int aReturnValue = 0;
  	for (int i=0; i<composedWord.length(); i++) {
  		aReturnValue += aFather.mObjFactory.getLetter((composedWord.charAt(i) - 64), false).getPoints(); 
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
		File file = new File(dir, "WBLogCat.txt");
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
}
