package com.cyberg.lexxicon;

import com.cyberg.lexxicon.environment.CrossVariables;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class GameLauncher extends Activity {

	private Button bItalian;
	private Button bBergamo;
  private ProgressBar pbLoading;
  private ImageView iLoading;

	@Override
	public void onCreate(Bundle savedInstanceState) {
    Window window = getWindow();
    // Take up as much area as possible
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
    // This does the actual full screen work
    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		bItalian = (Button) findViewById(R.id.bItalian);
		bItalian.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startProgram(CrossVariables.DICT_ITALIAN);
			}
		});
		bBergamo = (Button) findViewById(R.id.bBergamo);
		bBergamo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startProgram(CrossVariables.DICT_BERGAMO);
			}
		});
    pbLoading = (ProgressBar) findViewById(R.id.loadingPB);
    pbLoading.setVisibility(View.INVISIBLE);
    iLoading = (ImageView) findViewById(R.id.iLoading);
    iLoading.setVisibility(View.INVISIBLE);
	}

	private void startProgram(final int aDict) {
    bItalian.setVisibility(View.GONE);
    bBergamo.setVisibility(View.GONE);
    pbLoading.setVisibility(View.VISIBLE);
    iLoading.setVisibility(View.VISIBLE);
    new Thread() {
      @Override
      public void run() {
        CrossVariables.loadDictionary(getApplicationContext(), aDict);
        finish();
      }
    }.start();
  }

	@Override
	public void finish() {
		Intent main = new Intent(getApplicationContext(), Main.class);
  	startActivity(main);
		super.finish();
	}	
}