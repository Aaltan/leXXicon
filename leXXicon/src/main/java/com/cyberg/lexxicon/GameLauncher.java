package com.cyberg.lexxicon;

import com.cyberg.lexxicon.environment.CrossVariables;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import java.util.Locale;

public class GameLauncher extends Activity {

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

		pbLoading = (ProgressBar) findViewById(R.id.loadingPB);
		pbLoading.setVisibility(View.VISIBLE);
		iLoading = (ImageView) findViewById(R.id.iLoading);
		iLoading.setVisibility(View.VISIBLE);

		// Avvia automaticamente il caricamento del dizionario
		startProgram();
	}

	private void startProgram() {
		// Determina automaticamente la lingua del dispositivo
		int selectedDict = getDeviceLanguageDictionary();

		new Thread() {
			@Override
			public void run() {
				CrossVariables.loadDictionary(getApplicationContext(), selectedDict);
				finish();
			}
		}.start();
	}

	/**
	 * Determina il dizionario da utilizzare in base alla lingua del dispositivo
	 * @return Codice del dizionario da utilizzare
	 */
	private int getDeviceLanguageDictionary() {
		Locale deviceLocale = Locale.getDefault();
		String language = deviceLocale.getLanguage().toLowerCase();
		String country = deviceLocale.getCountry().toLowerCase();

		// Log per debug
		android.util.Log.d("GameLauncher", "Device language: " + language + ", country: " + country);

		switch (language) {
			case "it":
				return CrossVariables.DICT_ITALIAN;
			case "en":
				return CrossVariables.DICT_ENGLISH;
			case "fr":
				return CrossVariables.DICT_FRENCH;
			case "de":
				return CrossVariables.DICT_GERMAN;
			case "es":
				return CrossVariables.DICT_SPANISH;
			default:
				// Fallback all'inglese se la lingua non è supportata
				android.util.Log.d("GameLauncher", "Language not supported, falling back to English");
				return CrossVariables.DICT_ENGLISH;
		}
	}

	@Override
	public void finish() {
		Intent main = new Intent(getApplicationContext(), Main.class);
		startActivity(main);
		super.finish();
	}
}