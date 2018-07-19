package com.cyberg.lexxicon.utils;

import com.cyberg.lexxicon.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WBException extends Activity {
	
	private String mClass = "";
	private TextView mExText;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exception);
		String pkg = getPackageName();
		Intent currentIntent = getIntent();
		mClass = currentIntent.getStringExtra(pkg + ".classe");		
		
		String aMsg = "ATTENZIONE !\n\n'" + mClass + "'\nha generato un errore non gestito.\nCi scusiamo per il disagio.\nInviare la segnalazione\nall'helpdesk.\n\nRiavviare leXXicon.\n";
		
		mExText = (TextView) findViewById(R.id.txtException);
		mExText.setText(aMsg);
		
		Button exitBtn = (Button) findViewById(R.id.btnException);
		exitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();				
			}			
		});
	}

	@Override
	public void finish() {		
		super.finish();
		android.os.Process.killProcess(android.os.Process.myPid());		
		System.exit(0);
	}	
}