package com.cyberg.lexxicon.utils;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class GlobalExceptionHandler implements UncaughtExceptionHandler  {
	
	private final Context mContext;
	private final Class<?> mActivityClass;

	public GlobalExceptionHandler(Context context, Class<?> c) {
		mContext = context;
		mActivityClass = c;
	}

	public void uncaughtException(Thread thread, Throwable exception) {
		Log.v("GlobalExceptionHandler", "UncaughtException", exception);
		VariousUtils.salvaLogCat();		
		Intent exIntent = new Intent(mContext, WBException.class);
		String pkg = mContext.getPackageName();					
		exIntent.putExtra(pkg + ".classe", mActivityClass.getName());
		mContext.startActivity(exIntent);
		android.os.Process.killProcess(android.os.Process.myPid());		
		System.exit(0);
	}	
}