package com.cyberg.lexxicon;

import java.util.ArrayList;

import com.cyberg.lexxicon.environment.CrossVariables;
import com.cyberg.lexxicon.game.BonusFactory;
import com.cyberg.lexxicon.game.BonusPlate;
import com.cyberg.lexxicon.game.infinite.InfiniteGameCore;
import com.cyberg.lexxicon.game.LetterGrid;
import com.cyberg.lexxicon.game.PointsPlate;
import com.cyberg.lexxicon.game.SuggestionPlate;
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
import android.os.Bundle;
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
	private float mTouchX = -1f;
	private float mTouchY = -1f;
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

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
	}
  
  public void mousePressed() {
  }

  public void mouseDragged() {
  }

  public void mouseReleased() {
  }

  public void draw() {
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
		mSagaCore.reset();
		CrossVariables.OVERALL_STATE = CrossVariables.MENU_BOARD;
	}

	public void manageInfiniteBack() {
  	switch (CrossVariables.GAME_STATE) {
			case CrossVariables.GAME_BOARD:
				mInfiniteGameCore.reset();
				break;
			case CrossVariables.GAME_WORD_LIST:
				CrossVariables.GAME_STATE = CrossVariables.GAME_BOARD;
				break;
			case CrossVariables.GAME_OVER:
				mInfiniteGameCore.reset();
				break;
  	}
	}

	public void manageLevelModeBack() {
		switch (CrossVariables.GAME_STATE) {
			case CrossVariables.GAME_BOARD:
				mLevelsGameCore.reset();
				break;
			case CrossVariables.GAME_WORD_LIST:
				CrossVariables.GAME_STATE = CrossVariables.GAME_BOARD;
				break;
			case CrossVariables.GAME_OVER:
				mLevelsGameCore.reset();
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
		// call to keep mouseX and mouseY constants updated
		super.surfaceTouchEvent(event);
		 // extract the action code & the pointer ID
		int action = event.getAction();
		int code   = action & MotionEvent.ACTION_MASK;
		int index  = action >> MotionEvent.ACTION_POINTER_ID_SHIFT;
		
		float x = event.getX(index);
		float y = event.getY(index);
		int id  = event.getPointerId(index);
		
		// pass the events to the TouchProcessor
		if ( code == MotionEvent.ACTION_DOWN || code == MotionEvent.ACTION_POINTER_DOWN) {
			fingerDown = true;
		  touch.pointDown(x, y, id);
		}
		else if (code == MotionEvent.ACTION_UP || code == MotionEvent.ACTION_POINTER_UP) {
			fingerDown = false;
		  touch.pointUp(event.getPointerId(index));
		}
		else if ( code == MotionEvent.ACTION_MOVE) {
		  int numPointers = event.getPointerCount();
		  for (int i=0; i < numPointers; i++) {
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
	
  @Override
	public void finish() {
		android.os.Process.killProcess(android.os.Process.myPid());
		super.finish();
	}
}
