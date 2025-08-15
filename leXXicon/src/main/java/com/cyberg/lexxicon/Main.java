package com.cyberg.lexxicon;

import java.util.ArrayList;

import com.cyberg.lexxicon.environment.CrossVariables;
import com.cyberg.lexxicon.game.BonusFactory;
import com.cyberg.lexxicon.game.BonusPlate;
import com.cyberg.lexxicon.game.infinite.InfiniteGameCore;
import com.cyberg.lexxicon.game.LetterGrid;
import com.cyberg.lexxicon.game.PointsPlate;
import com.cyberg.lexxicon.game.SuggestionPlate;
import com.cyberg.lexxicon.game.levels.LevelConfiguration;
import com.cyberg.lexxicon.game.levels.LevelsGameCore;
import com.cyberg.lexxicon.saga.SagaFactory;
import com.cyberg.lexxicon.saga.SagaGrid;
import com.cyberg.lexxicon.saga.SagaCore;
import com.cyberg.lexxicon.menu.MenuCore;
import com.cyberg.lexxicon.menu.MenuGrid;
import com.cyberg.lexxicon.multitouch.DragEvent;
import com.cyberg.lexxicon.multitouch.FlickEvent;
import com.cyberg.lexxicon.multitouch.PinchEvent;
import com.cyberg.lexxicon.multitouch.RotateEvent;
import com.cyberg.lexxicon.multitouch.TapEvent;
import com.cyberg.lexxicon.multitouch.TouchPoint;
import com.cyberg.lexxicon.multitouch.TouchProcessor;
import com.cyberg.lexxicon.structs.ObjectFactory;
import com.cyberg.lexxicon.testutils.DisplayWords;
import com.cyberg.lexxicon.utils.GlobalExceptionHandler;
import com.lancer.android.processing.traer.physics.ParticleSystem;

import ketai.data.KetaiSQLite;
import ketai.ui.KetaiGesture;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.WindowManager;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Main extends PApplet {
	
	private KetaiGesture gesture;
	private TouchProcessor touch;
	private PImage sfondo;
	private PImage sfondoMenu;
	public ParticleSystem mPS;
	public BonusFactory mBonusFactory;
	private BonusPlate mBonusPlate;
	private SuggestionPlate mSuggestionPlate;
	public boolean fingerDown = false;
	public float mTouchX = -1f;
	public float mTouchY = -1f;
	public PFont mWordFont;
	public PFont mDefaultFont;
	private DisplayWords mDisplayWords;
	
	public ObjectFactory mObjFactory;
	public SagaFactory mSagaFactory;
	
	private InfiniteGameCore mInfiniteGameCore;
	private LevelsGameCore mLevelsGameCore;
	private MenuCore mMenuCore;
	private SagaCore mSagaCore;

	public LetterGrid mLetterGrid;
	private MenuGrid mMenuGrid;
	private SagaGrid mSagaGrid;
	// Gestione transizioni (gestisce devices con navigazione gestuale anzichè buttons)
	private boolean mInTransition = false;
	private long mLastStateChange = 0;
	private static final long STATE_CHANGE_COOLDOWN = 300; // ms

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// Per Android 10+ gestisci le aree di esclusione gesture
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			getWindow().getDecorView().setSystemGestureExclusionRects(
					new ArrayList<>() // Lascia vuoto per permettere gesture ovunque
			);
		}
  }

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			// Quando la finestra riprende il focus, reset del touch
			fingerDown = false;
			mTouchX = -1f;
			mTouchY = -1f;
		}
	}
  
  public void setup() {
  	CrossVariables.initStatics(width, height, getApplicationContext());
  	Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler(this, Main.class));
		//dropDB();
	  checkDB();
	  sfondo = loadImage("sfondoingame.jpg");		
	  sfondoMenu = loadImage("sfondomenu.jpg");		
	  mWordFont = loadFont("ErasITC-Demi-20.vlw");
	  mDefaultFont = createFont("SansSerif", 20, true, null);
	  sfondo.resize(width, height);
	  sfondoMenu.resize(width, height);
		gesture = new KetaiGesture(this);
	  touch = new TouchProcessor(this);
	  mPS = new ParticleSystem(CrossVariables.PHYSICS_GRAVITY, CrossVariables.PHYSICS_DRAG); 
	  mObjFactory = new ObjectFactory(this);	  
	  mSagaFactory = new SagaFactory(this);
	  int ofX = round(CrossVariables.GRID_OFFSET_X / CrossVariables.RESIZE_FACTOR_X);
	  int ofY = round(CrossVariables.GRID_OFFSET_Y / CrossVariables.RESIZE_FACTOR_Y);
	  mLetterGrid = new LetterGrid(this, mPS, mObjFactory, CrossVariables.GRID_NUMBER_OF_ROWS, CrossVariables.GRID_NUMBER_OF_COLS, ofX, ofY);
	  CrossVariables.POINTS_PLATE = new PointsPlate(this);
	  mDisplayWords = new DisplayWords(this);
	  mBonusFactory = new BonusFactory(this);
	  mBonusPlate = new BonusPlate(this);
	  mSuggestionPlate = new SuggestionPlate(this);
	  mInfiniteGameCore = new InfiniteGameCore(this, mPS, sfondo, mLetterGrid, mBonusPlate, mSuggestionPlate, mDisplayWords);
	  // To-Do - Modify LevelsGameCore (now just a clone of InfiniteGameCore)
		mLevelsGameCore = new LevelsGameCore(this, mPS, sfondo, mLetterGrid, mBonusPlate, mSuggestionPlate, mDisplayWords);
	  int menuOfX = round((CrossVariables.SCREEN_STANDARD_X - (CrossVariables.MENU_IMAGE_STANDARD_X * 8)) / 2 / CrossVariables.RESIZE_FACTOR_X);
	  int menuOfY = round(CrossVariables.MENU_GRID_OFFSET_Y / CrossVariables.RESIZE_FACTOR_Y);
	  mMenuGrid = new MenuGrid(this, mPS, mObjFactory, 8, 8, menuOfX, menuOfY);
	  mMenuCore = new MenuCore(this, mPS, sfondoMenu, mMenuGrid);
	  mSagaGrid = new SagaGrid(this, mSagaFactory);
	  mSagaCore = new SagaCore(this, sfondoMenu, mSagaGrid, mObjFactory);
	  frameRate(30);

		// Aggiungi questa riga per testare
		if (CrossVariables.DEBUG) {
			testLevelSystem();
		}
	}
  
  public void mousePressed() {
  }

  public void mouseDragged() {
  }

  public void mouseReleased() {
  }

  public void draw() {
		if (CrossVariables.DEBUG) {
			android.util.Log.d("TouchDebug", "State: " + CrossVariables.OVERALL_STATE +
					"/" + getSubState() +
					", Touch: " + mTouchX + "," + mTouchY +
					", FingerDown: " + fingerDown +
					", TouchPoints: " + touch.touchPoints.size() +
					", Frame: " + frameCount);
		}

	  touch.analyse();
	  touch.sendEvents();
	  ArrayList<TouchPoint> touchPoints = touch.getPoints();
	  if (fingerDown && mTouchX == -1f && mTouchY == -1f) {
	  	if (touchPoints.size() > 0) {
		  	TouchPoint aTP = touchPoints.get(0);
		  	mTouchX = aTP.x;
		  	mTouchY = aTP.y;
	  	}
	  }
	  else {
	  	if (!fingerDown) {
		  	mTouchX = -1f;
		  	mTouchY = -1f;
	  	}
	  }
	  try {
	  	switch (CrossVariables.OVERALL_STATE) {
	  		case CrossVariables.OVERALL_MENU:
	  			drawMenu();
	  			break;
	  		case CrossVariables.OVERALL_SAGA:
	  			drawSaga();
	  			break;
	  		case CrossVariables.OVERALL_INFINITE:
	  			drawInfinite();
	  			break;
				case CrossVariables.OVERALL_LEVEL_MODE:
					drawLevelMode();
					break;
	  		case CrossVariables.OVERALL_VS:
	  			drawVS();
	  			break;
	  		case CrossVariables.OVERALL_CREDITS:
	  			drawCredits();
	  			break;
	  	}
	  }
	  catch (Exception _Ex) {
	  	_Ex.printStackTrace();
	  }
  }

	private int getSubState() {
		switch (CrossVariables.OVERALL_STATE) {
			case CrossVariables.OVERALL_MENU:
				return CrossVariables.MENU_STATE;
			case CrossVariables.OVERALL_SAGA:
				return CrossVariables.SAGA_STATE;
			case CrossVariables.OVERALL_INFINITE:
			case CrossVariables.OVERALL_LEVEL_MODE:
				return CrossVariables.GAME_STATE;
			default:
				return -1;
		}
	}

  private void drawMenu() throws Exception {
  	mMenuCore.update(mTouchX, mTouchY);
  }
    
  private void drawSaga() throws Exception {
		mSagaCore.update(mTouchX, mTouchY);
  }
    
  private void drawInfinite() throws Exception {
  	mInfiniteGameCore.update(mTouchX, mTouchY);
  }

	private void drawLevelMode() throws Exception {
		mLevelsGameCore.update(mTouchX, mTouchY);
	}

	private void drawVS() throws Exception {
  	// Non Ancora Disponibile
  	mMenuCore.reset();
		CrossVariables.OVERALL_STATE = CrossVariables.MENU_BOARD;
  }
  
  private void drawCredits() throws Exception {
  	// Non Ancora Disponibile
  	mMenuCore.reset();
		CrossVariables.OVERALL_STATE = CrossVariables.MENU_BOARD;
  }

  private void dropDB() {
		CrossVariables.DB = new KetaiSQLite( this);
		CrossVariables.DB.execute(CrossVariables.DROP_STATS);
    CrossVariables.DB.execute(CrossVariables.DROP_LEVELS);
		CrossVariables.DB.execute(CrossVariables.CREATE_STATS);
		CrossVariables.DB.execute(CrossVariables.CREATE_LEVELS);
	}

	private void checkDB() {
		CrossVariables.DB = new KetaiSQLite( this);
		if (CrossVariables.DB.connect()) {
			if (!CrossVariables.DB.tableExists("Levels")) {
			  CrossVariables.DB.execute(CrossVariables.DROP_STATS);
				CrossVariables.DB.execute(CrossVariables.CREATE_STATS);
				CrossVariables.DB.execute(CrossVariables.CREATE_LEVELS);
			}
			else {
			  String aSQLStmt = "SELECT * FROM Levels ORDER BY levelnumber DESC";
        CrossVariables.DB.query(aSQLStmt);
        if (CrossVariables.DB.next()) {
          CrossVariables.STATUS_LEVELSCOMPLETED = CrossVariables.DB.getInt("levelnumber");
          // Not used yet
          // CrossVariables.STATUS_RECORDID = CrossVariables.DB.getInt("id");
          // CrossVariables.STATUS_PLAYERNAME = CrossVariables.DB.getString("name");
          // Not used yet
        }
      }
      CrossVariables.DB.close();
		}
	}

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent intent) {
 	}

	public void onBackPressed() {
		switch (CrossVariables.OVERALL_STATE) {
			case CrossVariables.OVERALL_MENU:
				finish();
				break;
			case CrossVariables.OVERALL_SAGA:
				manageSagaBack();
				break;
			case CrossVariables.OVERALL_INFINITE:
				manageInfiniteBack();
				break;
			case CrossVariables.OVERALL_LEVEL_MODE:
				manageLevelModeBack();
				break;
			case CrossVariables.OVERALL_VS:
				manageVSBack();
				break;
			case CrossVariables.GAME_OVER:
				finish();
				break;
		}
	}

	public void manageSagaBack() {
		switch (CrossVariables.SAGA_STATE) {
			case CrossVariables.SAGA_BOARD:
				// Dalla griglia dei livelli torna al menu principale
				mSagaCore.reset();
				CrossVariables.OVERALL_STATE = CrossVariables.OVERALL_MENU;
				break;
			case CrossVariables.SAGA_SELECTED_EFFECT:
				// Durante l'animazione di selezione, torna alla griglia
				CrossVariables.SAGA_STATE = CrossVariables.SAGA_BOARD;
				CrossVariables.LEVELS_SELECTED_NUM = -1;
				break;
			case CrossVariables.SAGA_LEVEL_INSTRUCTION:
				// Dalle istruzioni torna alla griglia dei livelli
				CrossVariables.LEVEL_INSTR_LETTERS_SHOWN = 0;
				CrossVariables.LEVEL_INSTR_ANIM_PLAY_LEFT = -1;
				CrossVariables.LEVELS_SELECTED_NUM = -1;
				CrossVariables.SAGA_STATE = CrossVariables.SAGA_BOARD;
				break;
			case CrossVariables.SAGA_LEVEL_START_ANIM:
				// Durante l'animazione di inizio, torna alle istruzioni
				CrossVariables.SAGA_STATE = CrossVariables.SAGA_LEVEL_INSTRUCTION;
				break;
			default:
				// Fallback: torna al menu
				mSagaCore.reset();
				CrossVariables.OVERALL_STATE = CrossVariables.OVERALL_MENU;
				break;
		}
	}

	public void manageInfiniteBack() {
		switch (CrossVariables.GAME_STATE) {
			case CrossVariables.GAME_BOARD:
			case CrossVariables.GAME_SUGGESTION_EFFECT:
			case CrossVariables.GAME_BOMB_EFFECT:
			case CrossVariables.GAME_ICE_EFFECT:
			case CrossVariables.GAME_WIPE_EFFECT:
			case CrossVariables.GAME_NEW_EFFECT:
				// Durante il gioco, mostra conferma senza fermare il timer
				mInfiniteGameCore.showExitConfirmation();
				break;
			case CrossVariables.GAME_WORD_LIST:
				// Dalla lista parole torna al gioco
				CrossVariables.GAME_STATE = CrossVariables.GAME_BOARD;
				break;
			case CrossVariables.GAME_OVER:
				// Da game over torna al menu
				mInfiniteGameCore.reset();
				CrossVariables.OVERALL_STATE = CrossVariables.OVERALL_MENU;
				break;
			default:
				// Altri stati: torna al menu
				mInfiniteGameCore.reset();
				CrossVariables.OVERALL_STATE = CrossVariables.OVERALL_MENU;
				break;
		}
	}

	public void manageLevelModeBack() {
		switch (CrossVariables.GAME_STATE) {
			case CrossVariables.GAME_BOARD:
			case CrossVariables.GAME_SUGGESTION_EFFECT:
			case CrossVariables.GAME_BOMB_EFFECT:
			case CrossVariables.GAME_ICE_EFFECT:
			case CrossVariables.GAME_WIPE_EFFECT:
			case CrossVariables.GAME_NEW_EFFECT:
				// Durante il gioco, mostra conferma senza fermare il timer
				mLevelsGameCore.showExitConfirmation();
				break;
			case CrossVariables.GAME_WORD_LIST:
				// Dalla lista parole torna al gioco
				CrossVariables.GAME_STATE = CrossVariables.GAME_BOARD;
				break;
			case CrossVariables.GAME_OVER:
			case CrossVariables.GAME_WIN:
				// Da game over/win torna alla griglia livelli
				CrossVariables.BONUS_USE_FRAMES_LEFT = 0; // Salta l'animazione
				mLevelsGameCore.backToLevelGrid();
				break;
			default:
				// Altri stati: torna alla griglia
				mLevelsGameCore.backToLevelGrid();
				break;
		}
	}

	public void manageVSBack() {
		
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

  public int sketchWidth() { 
  	return displayWidth;
//  	return 1440;
  }
  
  public int sketchHeight() { 
  	return displayHeight; 
//  	return 2560; 
  }
  
  public String sketchRenderer() { 
  	return P3D; 
  }
  
	// * -------------------------------- *
	// * - MultiTouch Processor - Start - *
	// * -------------------------------- *
	public void onTap(TapEvent event) {
	}

	public void onFlick(FlickEvent event) {
	}

	public void onDrag(DragEvent event) {
		mTouchX = event.x;
		mTouchY = event.y;
	}

	public void onRotate(RotateEvent event ) {
	}

	public void onPinch(PinchEvent event ) {
	}

	public boolean surfaceTouchEvent(MotionEvent event) {
		// Previeni touch durante il cooldown dopo cambio stato
		if (System.currentTimeMillis() - mLastStateChange < STATE_CHANGE_COOLDOWN) {
			return true;
		}

		int action = event.getAction();
		int code = action & MotionEvent.ACTION_MASK;

		// Gestisci ACTION_CANCEL (gesto di sistema Android)
		if (code == MotionEvent.ACTION_CANCEL) {
			// Reset completo dello stato touch
			fingerDown = false;
			mTouchX = -1f;
			mTouchY = -1f;
			if (touch != null) {
				touch.cancelAllTouches();
			}
			return true;
		}

		// Gestione normale
		super.surfaceTouchEvent(event);

		int index = action >> MotionEvent.ACTION_POINTER_ID_SHIFT;
		float x = event.getX(index);
		float y = event.getY(index);
		int id = event.getPointerId(index);

		// Pass to TouchProcessor
		if (code == MotionEvent.ACTION_DOWN || code == MotionEvent.ACTION_POINTER_DOWN) {
			fingerDown = true;
			touch.pointDown(x, y, id);
		}
		else if (code == MotionEvent.ACTION_UP || code == MotionEvent.ACTION_POINTER_UP) {
			fingerDown = false;
			touch.pointUp(event.getPointerId(index));
		}
		else if (code == MotionEvent.ACTION_MOVE) {
			int numPointers = event.getPointerCount();
			for (int i = 0; i < numPointers; i++) {
				id = event.getPointerId(i);
				x = event.getX(i);
				y = event.getY(i);
				touch.pointMoved(x, y, id);
			}
		}

		return gesture.surfaceTouchEvent(event);
	}
	// * ------------------------------ *
	// * - MultiTouch Processor - End - *
	// * ------------------------------ *

	// Metodo per cambi di stato sicuri
	public void changeState(int newOverallState, int newSubState) {
		// Reset touch state
		fingerDown = false;
		mTouchX = -1f;
		mTouchY = -1f;

		// Marca il tempo del cambio
		mLastStateChange = System.currentTimeMillis();

		// Reset del TouchProcessor
		if (touch != null) {
			touch.cancelAllTouches();
		}

		// Cambia stato
		CrossVariables.OVERALL_STATE = newOverallState;
		if (newSubState != -1) {
			switch (newOverallState) {
				case CrossVariables.OVERALL_MENU:
					CrossVariables.MENU_STATE = newSubState;
					break;
				case CrossVariables.OVERALL_SAGA:
					CrossVariables.SAGA_STATE = newSubState;
					break;
				case CrossVariables.OVERALL_INFINITE:
				case CrossVariables.OVERALL_LEVEL_MODE:
					CrossVariables.GAME_STATE = newSubState;
					break;
			}
		}
	}

  @Override
	public void finish() {
		android.os.Process.killProcess(android.os.Process.myPid());
		super.finish();
	}

	/**
	 * Esegue i test del sistema livelli (chiamalo da onCreate o setup per debug)
	 */
	private void testLevelSystem() {
		// Test solo in modalità DEBUG
		if (!CrossVariables.DEBUG) return;

		android.util.Log.d("LevelTest", "=====================================");
		android.util.Log.d("LevelTest", "TEST SISTEMA LIVELLI");
		android.util.Log.d("LevelTest", "=====================================");

		// Test 1: Verifica esistenza livelli
		int missingLevels = 0;
		for (int i = 1; i <= 99; i++) {
			if (LevelConfiguration.getLevel(i) == null) {
				android.util.Log.e("LevelTest", "ERRORE: Livello " + i + " mancante!");
				missingLevels++;
			}
		}

		if (missingLevels == 0) {
			android.util.Log.d("LevelTest", "✓ Tutti i 99 livelli definiti");
		} else {
			android.util.Log.e("LevelTest", "✗ Mancano " + missingLevels + " livelli");
		}

		// Test 2: Verifica distribuzione per ogni pagina
		android.util.Log.d("LevelTest", "\n--- Distribuzione Modalità per Pagina ---");

		for (int page = 0; page < 10; page++) {
			int start = page * 10 + 1;
			int end = Math.min(start + 8, 99);
			if (page == 0) end = 9;

			// Conta modalità
			int canonica = 0, conAiuti = 0, findWord = 0, mathSomma = 0, binary = 0;

			for (int i = start; i <= end; i++) {
				LevelConfiguration.Level level = LevelConfiguration.getLevel(i);
				if (level != null) {
					switch (level.mode) {
						case CANONICA: canonica++; break;
						case CON_AIUTI: conAiuti++; break;
						case FIND_WORD: findWord++; break;
						case MATH_SOMMA: mathSomma++; break;
						case BINARY: binary++; break;
					}
				}
			}

			// Verifica vincoli
			int totalBase = canonica + conAiuti;
			int totalMath = mathSomma + binary;

			String status = "✓";
			if (totalBase < 4 || totalBase > 7) status = "✗";
			if (findWord < 1 || findWord > 2) status = "✗";
			if (totalMath > 3) status = "✗";

			android.util.Log.d("LevelTest",
					String.format("%s Pag %d-%d: CAN=%d, AIUTI=%d, FIND=%d, MATH=%d, BIN=%d",
							status, start, end, canonica, conAiuti, findWord, mathSomma, binary));
		}

		// Test 3: Stampa alcuni livelli di esempio
		android.util.Log.d("LevelTest", "\n--- Esempi Livelli ---");

		int[] sampleLevels = {1, 5, 10, 25, 50, 75, 99};
		for (int num : sampleLevels) {
			LevelConfiguration.Level level = LevelConfiguration.getLevel(num);
			if (level != null) {
				String desc = String.format("L%02d: %s - %d items in %d:%02d",
						num,
						level.mode.name(),
						level.targetCount,
						level.timeSeconds / 60,
						level.timeSeconds % 60);

				// Aggiungi dettagli specifici per modalità
				switch (level.mode) {
					case CANONICA:
					case CON_AIUTI:
						desc += " (min " + level.minWordLength + " lettere)";
						if (!level.bonuses.isEmpty()) {
							desc += " [" + level.bonuses.size() + " bonus]";
						}
						break;
					case MATH_SOMMA:
						desc += String.format(" (min %d nums, max %d)",
								level.minNumbers, level.maxSum);
						break;
					case BINARY:
						desc += String.format(" (%d-%d bit)",
								level.minBinaryLength, level.maxBinaryLength);
						break;
				}

				android.util.Log.d("LevelTest", desc);
			}
		}

		android.util.Log.d("LevelTest", "=====================================");
		android.util.Log.d("LevelTest", "FINE TEST");
		android.util.Log.d("LevelTest", "=====================================");
	}
}
