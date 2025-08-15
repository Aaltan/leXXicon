package com.cyberg.lexxicon.environment;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.cyberg.lexxicon.Main;
import com.cyberg.lexxicon.game.EmptySlot;
import com.cyberg.lexxicon.game.PointsPlate;
import com.cyberg.lexxicon.game.WordPlate;
import com.cyberg.lexxicon.game.levels.LevelAdapter;
import com.cyberg.lexxicon.game.levels.LevelConfiguration;
import com.cyberg.lexxicon.game.levels.LevelInfos;
import com.cyberg.lexxicon.solver.DictionaryEntry;
import com.cyberg.lexxicon.solver.Solver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import ketai.data.KetaiSQLite;

public class CrossVariables {
	
	public final static boolean DEBUG = false;
	
	// Resize Variables
  public final static float SCREEN_STANDARD_X = 480.0f;
  public final static float SCREEN_STANDARD_Y = 800.0f;
  public final static float IMAGE_STANDARD_X = 90;
  public final static float IMAGE_STANDARD_Y = 90;
  public final static float BONUS_STANDARD_X = 70;
  public final static float BONUS_STANDARD_Y = 70;
  public final static float SUGGESTION_PLATE_X = 400;
  public final static float SUGGESTION_PLATE_Y = 110;
  public final static float SUGGESTION_OFFSET_Y = 10;
  public final static float TOUCH_OFFSET_X = 10;
  public final static float TOUCH_OFFSET_Y = 10;
  public final static int OBJECTS_ANIMATION_FRAMES = 10;
  public final static int GRID_OFFSET_X = 15;
  public final static int GRID_OFFSET_Y = 80;
  public final static int GRID_NUMBER_OF_ROWS = 5;
  public final static int GRID_NUMBER_OF_COLS = 5;
  public final static int STANDARD_FRAMERATE = 60;
  
  public static String USER_COUNTRY = "";
  
  // * ---------------------------------------------- *
  // * -                  Solver                    - *
  // * ---------------------------------------------- *
  private static Solver mSolver;
	private static Handler mHandler = null;
  public static boolean SOLVER_ERROR = false;
  public static int SOLVER_PROGRESS = -1;
  // Constants
  public static final int ALPHABET_SIZE = 5;  // up to 2^5 = 32 letters
  public static final int BOARD_SIZE    = 5;  // 5x5 board
  public static final int[] moves = {-BOARD_SIZE-1, -BOARD_SIZE, -BOARD_SIZE+1, 
  																	 -1,                         +1,
  																	 +BOARD_SIZE-1, +BOARD_SIZE, +BOARD_SIZE+1};
  
  // Technically constant (calculated here for flexibility, but should be fixed)
  public static int maxWordLength = 0;
  
  // Variables that depend on the actual game layout
  public static int[] board = new int[BOARD_SIZE*BOARD_SIZE]; // Letters in board
  public static boolean[] possibleTriplets = new boolean[1 << (ALPHABET_SIZE*3)];
  public static DictionaryEntry[] candidateWords;
  public static int candidateCount;
  public static int[] usedBoardPositions;
  public static DictionaryEntry[] foundWords;
  public static int foundCount = -1;  
  
  public static DictionaryEntry[] dictionary; // Processed word list
  public static int[] boardTripletIndices; // List of all 3-letter moves in board coordinates
  
  public final static int JOB_STARTED = 101;
  public final static int JOB_IN_PROGRESS = 102;
  public final static int JOB_ENDED = 103;
  public final static int JOB_ABORTED = 104;
  // * ---------------------------------------------- *
  // * -                  Solver                    - *
  // * ---------------------------------------------- *

  // * ---------------------------------------------- *
  // * -                Dictionary                  - *
  // * ---------------------------------------------- *
  public final static int DICT_ITALIAN = 0;
  public final static int DICT_ENGLISH = 1;
  public final static int DICT_FRENCH = 2;
  public final static int DICT_GERMAN = 3;
  public final static int DICT_SPANISH = 4;
  // Legacy - mantenuto per compatibilità ma deprecato
  @Deprecated
  public final static int DICT_BERGAMO = 1000;
  // * ---------------------------------------------- *
  // * -                Dictionary                  - *
  // * ---------------------------------------------- *

  // * ---------------------------------------------- *
  // * -          Bonus Variables - Start           - *
  // * ---------------------------------------------- *
  public static int NR_BONUS_SUGGESTION = 0;
  public static int NR_BONUS_BOMB = 0;
  public static int NR_BONUS_ICE = 0;
  public static int NR_BONUS_WIPE_LETTERS = 0;
  public static int NR_BONUS_NEW_BOARD = 0;  
  public static final int BONUS_TYPE_BOMB = 0;
  public static final int BONUS_TYPE_SUGGESTION = 1;
  public static final int BONUS_TYPE_ICE = 2;
  public static final int BONUS_TYPE_WIPE_LETTERS = 3;
  public static final int BONUS_TYPE_NEW_BOARD = 4;
  public final static int BONUS_FONT_SIZE = 40;
  public final static int BONUS_POSITION_Y = 10;
  public final static int BONUS_ANIM_FRAMES = 40;
  public static int BONUS_USED = -1;
  public static int BONUS_GRANTED = -1;
  public static int BONUS_ANIM_OFFSET_X = 0;
  public static int BONUS_ANIM_SIGN_X = 1;
  public static int BONUS_ANIM_OFFSET_Y = 0;
  public static int BONUS_ANIM_SIGN_Y = 1;
  // Bonus Chance 'n' over 1000 (Overall) - Initial Value
  public static int BONUS_CHANCE = 15;
  // Bonus Chances percentage
  public static final int BONUS_CHANCE_BOMB = 50;
  public static final int BONUS_CHANCE_SUGGESTION = 23;
  public static final int BONUS_CHANCE_ICE = 14;
  public static final int BONUS_CHANCE_WIPE_LETTERS = 9;
  public static final int BONUS_CHANCE_NEW_BOARD = 4;
  /*
  public static final int BONUS_CHANCE_SUGGESTION = 20;
  public static final int BONUS_CHANCE_BOMB = 20;
  public static final int BONUS_CHANCE_ICE = 20;
  public static final int BONUS_CHANCE_WIPE_LETTERS = 20;
  public static final int BONUS_CHANCE_NEW_BOARD = 20;
  */
  // Bonus Standard Wait Time (in fps)
  public static final int BONUS_WAIT_FRAMES = 15;
  public static int BONUS_FRAMES_LEFT = -1;
  // In Bonus animation effect variables
  public static final int BONUS_USE_ANIM_FRAMES = 10;
  public static int BONUS_USE_FRAMES_LEFT = -1;
  public static boolean BONUS_SLOTS_CLEAN = false;
  public static int[] BONUS_ARRAY;
  // * ---------------------------------------------- *
  // * -          Bonus Variables - End             - *
  // * ---------------------------------------------- *
  
  // * ---------------------------------------------- *
  // * -          Menu Variables - Start            - *
  // * ---------------------------------------------- *
  public static final int MENU_USE_ANIM_FRAMES = 10;
  public static int MENU_USE_FRAMES_LEFT = -1;
  // Menu States
  public static int MENU_STATE = -1;
  public static final int MENU_BOARD = 0;
  public static final int MENU_SELECTED_EFFECT = 1;
	// Phases Init Variables
	public static boolean MENU_INIT = false;
	// Graphic Variables
  public final static float MENU_IMAGE_STANDARD_X = 55;
  public final static float MENU_IMAGE_STANDARD_Y = 55;
  public final static float MENU_IMAGE_STANDARD_Y_SPACE = 25;  
  public final static int MENU_GRID_OFFSET_Y = 40;
  // * ---------------------------------------------- *
  // * -           Menu Variables - End             - *
  // * ---------------------------------------------- *
  
  // * ---------------------------------------------- *
  // * -       Saga Board Variables - Start         - *
  // * ---------------------------------------------- *
  public static int SAGA_STATE = -1;
  public static final int SAGA_BOARD = 0;
  public static final int SAGA_SELECTED_EFFECT = 1;
  public static final int SAGA_LEVEL_INSTRUCTION = 2;
  public static final int SAGA_LEVEL_START_ANIM = 3;
	// Phases Init Variables
	public static boolean SAGA_INIT = false;
	// Graphic Variables
  public final static float SAGA_IMAGE_STANDARD_X = 130;
  public final static float SAGA_IMAGE_STANDARD_Y = 130;
  public final static float SAGA_NUMBER_IMAGE_STANDARD_X = 50;
  public final static float SAGA_NUMBER_IMAGE_STANDARD_Y = 60;
  public final static float SAGA_STAR_IMAGE_STANDARD_X = 24;
  public final static float SAGA_STAR_IMAGE_STANDARD_Y = 24;
  public final static int SAGA_GRID_OFFSET_X = 30;
  public final static int SAGA_GRID_OFFSET_Y = 140;
  public final static int SAGA_GRID_SPACING_X = 15;
  public final static int SAGA_GRID_SPACING_Y = 25;
  public final static int SAGA_NUMBERS_OFFSET_X = 14;
  public final static int SAGA_NUMBERS_OFFSET_Y = 18;
  public final static int SAGA_STARS_OFFSET_X = 22;
  public final static int SAGA_STARS_OFFSET_Y = 103;
  public final static int SAGA_STARS_SPACING_X = 7;
  // Current level saga page
  public static int SAGA_CURRENT_PAGE = 1;
  public final static int SAGA_PER_PAGE = 9;
  public final static int SAGA_MAX_PAGES = 11;
  // Levels Arrows
  public final static float SAGA_ARROW_IMAGE_STANDARD_X = 128;
  public final static float SAGA_ARROW_IMAGE_STANDARD_Y = 128;
  public final static int SAGA_ARROW_OFFSET_X = 30;
  public final static int SAGA_ARROW_OFFSET_Y = 600;
  // Configurazione avanzata livelli
  public static final int MAX_LEVELS = 99;
  public static final int LEVELS_PER_WORLD = 15; // 6 mondi + tutorial
  public static final int TUTORIAL_LEVELS = 9;   // Livelli 1-9

  // Tipi di mondo/tema
  public static final int WORLD_TUTORIAL = 0;    // Livelli 1-9
  public static final int WORLD_BASICS = 1;      // Livelli 10-19
  public static final int WORLD_MATH = 2;        // Livelli 20-29
  public static final int WORLD_BINARY = 3;      // Livelli 30-39
  public static final int WORLD_SPEED = 4;       // Livelli 40-49
  public static final int WORLD_CHALLENGE = 5;   // Livelli 50-59
  public static final int WORLD_MASTER = 6;      // Livelli 60-69
  public static final int WORLD_CHAMPION = 7;    // Livelli 70-79
  public static final int WORLD_EXPERT = 8;      // Livelli 80-89
  public static final int WORLD_LEGEND = 9;      // Livelli 90-99

  // Difficoltà progressive
  public static final int DIFFICULTY_EASY = 0;
  public static final int DIFFICULTY_MEDIUM = 1;
  public static final int DIFFICULTY_HARD = 2;
  public static final int DIFFICULTY_EXPERT = 3;
  // * ---------------------------------------------- *
  // * -        Saga Board Variables - End          - *
  // * ---------------------------------------------- *
  // * ---------------------------------------------- *
  // * -          Level Variables - Start           - *
  // * ---------------------------------------------- *
  // Level Selected Variables (mantenute per compatibilità)
  public static boolean LEVEL_INIT = false;
  public static int LEVELS_SELECTED_NUM = -1;
  public final static int LEVELS_ANIM_FRAMES = 40;
  public static int LEVELS_FRAMES_LEFT = -1;
  public static int LEVELS_ANIM_OFFSET_X = 0;
  public static int LEVELS_ANIM_SIGN_X = 1;
  public static int LEVELS_ANIM_OFFSET_Y = 0;
  public static int LEVELS_ANIM_SIGN_Y = 1;

  // Graphic Variables for instructions (mantenute)
  public final static int LEVEL_INSTR_DISPLAY_DELAY = 1;
  public final static int LEVEL_INSTR_ANIM_PLAY_FRAMES = 10;
  public static int LEVEL_INSTR_ANIM_PLAY_LEFT = -1;
  public static int LEVEL_INSTR_LETTERS_SHOWN = 0;
  public final static int LEVEL_INSTR_GRID_ROWS = 15;
  public final static int LEVEL_INSTR_GRID_COLS = 14;
  public final static float LEVEL_INSTR_IMAGE_STANDARD_X = 30;
  public final static float LEVEL_INSTR_IMAGE_STANDARD_Y = 30;
  public final static float LEVEL_INSTR_IMAGE_STANDARD_X_SPACE = 2;
  public final static float LEVEL_INSTR_IMAGE_STANDARD_Y_SPACE = 2;

  // Level Configuration Section (mantenute)
  public static final int LEVEL_TYPE_WORDS = 0;
  public static final int LEVEL_TYPE_FIND = 1;
  public static final int LEVEL_TYPE_BINARY = 2;
  public static final int LEVEL_TYPE_MATH = 3;

  // Modo attuale del livello math
  public static int MATH_ACTUAL_MODE = -1;
  public static final int MATH_MODE_NONE = -1;

  public static final int MATH_MODE_ADD = 0;
  public static final int MATH_MODE_SUBTRACT = 1;
  public static final int MATH_MODE_MULTIPY = 2;
  public static final int MATH_MODE_DIVIDE = 3;

  public static int LEVEL_TYPE_ACTUAL_GAME = -1;
  public static String LEVEL_WORD_TO_FIND = "";
  public static int LEVEL_NUMBER_TO_FIND = -1;
  public static String LEVEL_BINARY_TO_FIND = "";

  // Array espanso per tutti i livelli
  public static LevelInfos[] LEVEL_INFOS = new LevelInfos[MAX_LEVELS];

  // * ---------------------------------------------- *
  // * -           Level Variables - End            - *
  // * ---------------------------------------------- *

  // * ---------------------------------------------- *
  // * -         Game Variables - Start             - *
  // * ---------------------------------------------- *
  // Game Board States
  public static int GAME_STATE = -1;
  public static final int GAME_BOARD = 0;
  public static final int GAME_WORD_LIST = 1;
  public static final int GAME_SUGGESTION_EFFECT = 2;
  public static final int GAME_BOMB_EFFECT = 3;
  public static final int GAME_ICE_EFFECT = 4;
  public static final int GAME_WIPE_EFFECT = 5;
  public static final int GAME_NEW_EFFECT = 6;
  public static final int GAME_WIN = 98;
  public static final int GAME_OVER = 99;
	// Phases Init Variables
	public static boolean GAME_INIT = false;
  // * ---------------------------------------------- *
  // * -          Game Variables - End              - *
  // * ---------------------------------------------- *

  // * ---------------------------------------------- *
  // * -         Status Variables - Start           - *
  // * ---------------------------------------------- *
  public static int STATUS_RECORDID = -1;
  public static int STATUS_LEVELSCOMPLETED = 0;
  public static String STATUS_PLAYERNAME = "";
  // * ---------------------------------------------- *
  // * -         Status Variables - End             - *
  // * ---------------------------------------------- *

  // Timers variables for Infinite Mode (in Milliseconds)
  public static final long TIMEOUT_INFINITE_MODE_STANDARD = 60000;
  public static long TIMEOUT_INFINITE_MODE_LIMIT_WARINING = 10000;
  public static long TIMEOUT_INFINITE_MODE_TIME_LEFT = 0;
  public static long TIMEOUT_INFINITE_MODE_PREVIOUS_TIME = 0;

  // Goals and Timers variables for Saga Mode (time in Milliseconds)
  public static int WORDS_LEFT_TO_COMPOSE = 0;
  public static final long TIMEOUT_SAGA_MODE_STANDARD = 60000;
  public static long TIMEOUT_SAGA_MODE_LIMIT_WARINING = 10000;
  public static long TIMEOUT_SAGA_MODE_TIME_LEFT = 0;
  public static long TIMEOUT_SAGA_MODE_PREVIOUS_TIME = 0;

  // Words Plate
  public final static int WORD_FONT_SIZE = 50;
  public final static int WORD_POSITION_Y = 170;
  public static WordPlate WORD_PLATE = new WordPlate(null, -1, "", false);

  // Points Plate
  public final static int POINTS_FONT_SIZE = 30;
  public final static int POINTS_POSITION_X = 20;
  public final static int POINTS_POSITION_Y = 220;
  public static PointsPlate POINTS_PLATE;
  public static int POINTS_EARNED = 0;

  // Word Find Type-Game letters size
  public final static int WORD_FIND_FONT_SIZE = 50;
  public final static int WORD_FIND_POSITION_Y = 67;

  
  // Physics Variables
  public static float PHYSICS_GRAVITY = 0.5f;
  public static float PHYSICS_DRAG = 0.01f;
  public static float PARTICLE_MASS = 0.15f;
  public static float SPRING_STRENGTH = 0.2f;
  public static float SPRING_DAMPING = 0.03f;
  
  public static final float PHYSICS_MIN_Y = 300;  
  public static final float PHYSICS_MIN_GRAVITY = 0.35f;
  public static final float PHYSICS_MIN_DRAG = 0.033f;
  public static final float PARTICLE_MIN_MASS = 0.1f;
  public static final float SPRING_MIN_STREGTH = 0.1f;
  public static final float SPRING_MIN_DAMPING = 0.02f;
  
  public static final float PHYSICS_INC_GRAVITY = 0.001f;
  public static final float PHYSICS_INC_DRAG = -0.000003f;
  public static final float PARTICLE_INC_MASS = 0.00008f;
  public static final float SPRING_INC_STRENGTH = 0.00009f;
  public static final float SPRING_INC_DAMPING = 0.00005f;
    
  public static float RESIZE_FACTOR_X = -1;
  public static float RESIZE_FACTOR_Y = -1;

  public static ArrayList<EmptySlot> MARKED_FOR_SWAP = new ArrayList<EmptySlot>();
  public static ArrayList<EmptySlot> MARKED_FOR_FILL = new ArrayList<EmptySlot>();
  
  public static String COMPOSED_WORD = "";
    
  // Overall States
  public static int OVERALL_STATE = -1;
  public static final int OVERALL_MENU = 0;
  public static final int OVERALL_SAGA = 1;
  public static final int OVERALL_INFINITE = 2;
  public static final int OVERALL_VS = 3;
  public static final int OVERALL_CREDITS = 4;
  public static final int OVERALL_LEVEL_MODE = 5;

	// DB Variables
	public static KetaiSQLite DB;

  // Bouncing Touch Safe Variables
  public static long LAST_TOUCH_TIME = 0;
  public static final long TOUCH_COOLDOWN_MS = 100; // 100ms cooldown tra touch significativi


  public static final String DROP_STATS = "DROP TABLE Stats";
  public static final String CREATE_STATS = "CREATE TABLE Stats (" +
      "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
      "name VARCHAR(100) default '', " +
      "points INTEGER default 0)";
  public static final String INSERT_STATS = "INSERT INTO Stats (name, points) VALUES ('Guest', 0)";

  public static final String DROP_LEVELS = "DROP TABLE Levels";
  public static final String CREATE_LEVELS = "CREATE TABLE Levels (" +
      "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
      "levelnumber INTEGER default 0, " +
      "levelstars INTEGER default 0)";


  public static void initStatics(float width, float height, Context aCtx) {
    RESIZE_FACTOR_X = SCREEN_STANDARD_X / width;
    RESIZE_FACTOR_Y = SCREEN_STANDARD_Y / height;
    PHYSICS_GRAVITY = PHYSICS_MIN_GRAVITY;
    PHYSICS_DRAG = PHYSICS_MIN_DRAG;
    PARTICLE_MASS = PARTICLE_MIN_MASS;
    SPRING_STRENGTH = SPRING_MIN_STREGTH;
    SPRING_DAMPING = SPRING_MIN_DAMPING;
    GAME_STATE = GAME_BOARD;
    OVERALL_STATE = OVERALL_MENU;
    MENU_STATE = MENU_BOARD;
    SAGA_STATE = SAGA_BOARD;
    TIMEOUT_INFINITE_MODE_TIME_LEFT = TIMEOUT_INFINITE_MODE_STANDARD;

    float diffY = height - PHYSICS_MIN_Y;
    if (diffY > 1) {
      PHYSICS_GRAVITY = PHYSICS_GRAVITY + (PHYSICS_INC_GRAVITY * diffY);
      PHYSICS_DRAG = PHYSICS_DRAG + (PHYSICS_INC_DRAG * diffY);
      PARTICLE_MASS = PARTICLE_MASS + (PARTICLE_INC_MASS * diffY);
      SPRING_STRENGTH = SPRING_STRENGTH + (SPRING_INC_STRENGTH * diffY);
      SPRING_DAMPING = SPRING_DAMPING + (SPRING_INC_DAMPING * diffY);
    }

    // USA IL NUOVO SISTEMA DI LIVELLI
    LevelAdapter.buildAllLevels();

    // Validazione opzionale per debug
    if (DEBUG) {
      boolean valid = LevelAdapter.validateLevelDistribution();
      android.util.Log.d("CrossVariables", "Level distribution valid: " + valid);

      // Stampa alcuni livelli di esempio
      for (int i = 1; i <= 10; i++) {
        android.util.Log.d("CrossVariables", LevelConfiguration.getLevelDescription(i));
      }
    }
  }

  public static void loadDictionary(Context aCtx, int aDict) {
    USER_COUNTRY = aCtx.getResources().getConfiguration().locale.getDisplayCountry();
    String dictFile = "";

    switch (aDict) {
      case DICT_ITALIAN:
        dictFile = "Italiano.txt";
        break;
      case DICT_ENGLISH:
        dictFile = "English.txt";
        break;
      case DICT_FRENCH:
        dictFile = "French.txt";
        break;
      case DICT_GERMAN:
        dictFile = "German.txt";
        break;
      case DICT_SPANISH:
        dictFile = "Spanish.txt";
        break;
      case DICT_BERGAMO: // Legacy support
        dictFile = "Bergamasco.txt";
        break;
      default:
        dictFile = "English.txt"; // Fallback
        break;
    }

    try {
      // The following can be pre-computed and should be replaced by constants
      CrossVariables.dictionary = buildDictionary(dictFile, aCtx);
      CrossVariables.boardTripletIndices = buildTripletIndices();
      // The following only needs to run once at the beginning of the program
      CrossVariables.candidateWords     = new DictionaryEntry[2000]; // WAAAY too generous
      CrossVariables.foundWords         = new DictionaryEntry[2000]; // WAAAY too generous
      CrossVariables.usedBoardPositions = new int[CrossVariables.maxWordLength];
      createBonusArray();
    }
    catch (IOException _IOE) {
      SOLVER_ERROR = true;
      String errorMsg = getLocalizedErrorMessage(aCtx, aDict);
      Toast.makeText(aCtx, errorMsg, Toast.LENGTH_LONG).show();
    }
  }

  /**
   * Restituisce un messaggio di errore localizzato
   */
  private static String getLocalizedErrorMessage(Context aCtx, int dictType) {
    switch (dictType) {
      case DICT_ITALIAN:
        return "Dizionario Italiano NON trovato";
      case DICT_FRENCH:
        return "Dictionnaire Français NON trouvé";
      case DICT_GERMAN:
        return "Deutsches Wörterbuch NICHT gefunden";
      case DICT_SPANISH:
        return "Diccionario Español NO encontrado";
      case DICT_ENGLISH:
      default:
        return "English Dictionary NOT found";
    }
  }
  
  private static DictionaryEntry[] buildDictionary(String fileName, Context aCtx) throws IOException {
  	BufferedReader fileReader = new BufferedReader(new InputStreamReader(aCtx.getAssets().open(fileName)));
    String word = fileReader.readLine();
    ArrayList<DictionaryEntry> result = new ArrayList<DictionaryEntry>();
    while (word!=null) {
      if (word.length() >= 3) {
        word = word.toUpperCase();
        if (word.length() > CrossVariables.maxWordLength) {
        	CrossVariables.maxWordLength = word.length();
        }
        DictionaryEntry entry = new DictionaryEntry();
        entry.letters  = new int[word.length()  ];
        entry.triplets = new int[word.length()-2];
        int i=0;
        for (char letter: word.toCharArray()) {
          entry.letters[i] = (byte) letter - 65; // Convert ASCII to 0..25
          if (i>=2) {
            entry.triplets[i-2] = (((entry.letters[i-2]  << CrossVariables.ALPHABET_SIZE) +
                                     entry.letters[i-1]) << CrossVariables.ALPHABET_SIZE) +
                                     entry.letters[i];
          }
          i++;
        }
        result.add(entry);
      }
      word = fileReader.readLine();
    }
    fileReader.close();
    return result.toArray(new DictionaryEntry[result.size()]);
  }  
  
  private static int[] buildTripletIndices() {
    ArrayList<Integer> result = new ArrayList<Integer>();
    for (int a=0; a<CrossVariables.BOARD_SIZE*CrossVariables.BOARD_SIZE; a++) {
      for (int bm: CrossVariables.moves) {
        int b=a+bm;
        if ((b>=0) && (b<CrossVariables.board.length) && !isWrap(a, b)) {
          for (int cm: CrossVariables.moves) {
            int c=b+cm;
            if ((c>=0) && (c<CrossVariables.board.length) && (c!=a) && !isWrap(b, c)) {
              result.add(a);
              result.add(b);
              result.add(c);
            }
          }
        }
      }
    }
    int[] result2 = new int[result.size()];
    int i=0;
    for (Integer r: result) {
      result2[i++] = r;
    }
    return result2;
  }
  
  public static boolean isWrap(int a, int b) { // Checks if move a->b wraps board edge (like 3->4)
    return Math.abs(a % CrossVariables.BOARD_SIZE - b % CrossVariables.BOARD_SIZE)>1;
  }

  /**
   * Reset completo dello stato touch - da usare nelle transizioni
   */
  public static void resetTouchState(Main mainInstance) {
    if (mainInstance != null) {
      mainInstance.fingerDown = false;
      mainInstance.mTouchX = -1f;
      mainInstance.mTouchY = -1f;
    }
  }

  public static void solveBoard(String[] aBoard) {  
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				SOLVER_PROGRESS = msg.getData().getInt("type");
			}
		};
    SOLVER_PROGRESS = CrossVariables.JOB_IN_PROGRESS;
		mSolver = new Solver(mHandler, aBoard);
		mSolver.run();
  }

  public static String getWord(DictionaryEntry entry) {
    char[] result = new char[entry.letters.length];
    int i=0;
    for (int letter: entry.letters) {
      result[i++] = (char) (letter+97);
    }
    return new String(result);
  }
  
  public static boolean correctWord() {
  	boolean aReturnValue = false;
  	switch (OVERALL_STATE) {
      case OVERALL_INFINITE:
        aReturnValue = matchFound();
        break;
      case OVERALL_LEVEL_MODE:
        switch (LEVEL_TYPE_ACTUAL_GAME) {
          case LEVEL_TYPE_WORDS:
            // Controllo lunghezza minima del livello
            if (LEVELS_SELECTED_NUM > 0 && LEVELS_SELECTED_NUM <= MAX_LEVELS) {
              LevelInfos levelInfo = LEVEL_INFOS[LEVELS_SELECTED_NUM - 1];
              int minLength = levelInfo.getMinWordLength();

              // Controlla prima la lunghezza
              if (COMPOSED_WORD.trim().length() < minLength) {
                if (DEBUG) {
                  android.util.Log.d("CrossVariables","Parola troppo corta: " + COMPOSED_WORD.length() + " < " + minLength);
                }
                return false;
              }
            }
            aReturnValue = matchFound();
            break;
          case LEVEL_TYPE_FIND:
          case LEVEL_TYPE_BINARY:
            if (LEVEL_WORD_TO_FIND.trim().equalsIgnoreCase(COMPOSED_WORD.trim())) {
              aReturnValue = true;
            }
            break;
          case LEVEL_TYPE_MATH:
            aReturnValue = checkMath();
            break;
        }
        break;
    }
  	return aReturnValue;
  }

  private static boolean matchFound() {
    boolean aReturnValue = false;
    for (int i = 0; i < foundCount; i++) {
      if (getWord(foundWords[i]).trim().equalsIgnoreCase(COMPOSED_WORD.trim())) {
        aReturnValue = true;
        break;
      }
    }
    return aReturnValue;
  }

  private static boolean checkMath() {
    boolean aReturnValue = false;
    int sumValue = 0;
    for (int i=0; i<COMPOSED_WORD.length(); i++) {
      String aChar = COMPOSED_WORD.substring(i, i+1);
      int aValue = 0;
      try {
        aValue = Integer.parseInt(aChar);
      }
      catch (NumberFormatException _NFE) {}
      sumValue += aValue;
    }
    if (sumValue == LEVEL_NUMBER_TO_FIND) {
      aReturnValue = true;
    }
    return aReturnValue;
  }
  
  public static void addSwapSlot(int r, int c) {
  	MARKED_FOR_SWAP.add(new EmptySlot(r, c));
  }
  
  public static void removeSwapSlot(EmptySlot anElement) {
  	MARKED_FOR_SWAP.remove(anElement);
  }
  
  public static void addFillSlot(int r, int c) {
  	MARKED_FOR_FILL.add(new EmptySlot(r, c));
  }
  
  public static void removeFillSlot(EmptySlot anElement) {
  	MARKED_FOR_FILL.remove(anElement);
  } 
  
  private static void createBonusArray() {
  	int[] anArray = new int[BONUS_CHANCE_BOMB + BONUS_CHANCE_SUGGESTION + BONUS_CHANCE_ICE +
  	                        BONUS_CHANCE_WIPE_LETTERS + BONUS_CHANCE_NEW_BOARD];
  	int startIdx = 0;
  	int stopIdx = BONUS_CHANCE_BOMB;
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = BONUS_TYPE_BOMB;
  	}
  	startIdx = stopIdx;
  	stopIdx += BONUS_CHANCE_SUGGESTION;
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = BONUS_TYPE_SUGGESTION;
  	}
  	startIdx = stopIdx;
  	stopIdx += BONUS_CHANCE_ICE;
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = BONUS_TYPE_ICE;
  	}
  	startIdx = stopIdx;
  	stopIdx += BONUS_CHANCE_WIPE_LETTERS;
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = BONUS_TYPE_WIPE_LETTERS;
  	}
  	startIdx = stopIdx;
  	stopIdx += BONUS_CHANCE_NEW_BOARD;
  	for (int i=startIdx; i<stopIdx; i++) {
  		anArray[i] = BONUS_TYPE_NEW_BOARD;
  	}
  	Collections.shuffle(Arrays.asList(anArray));
  	BONUS_ARRAY = anArray;
  }

  /**
   * Determina il mondo per un dato livello
   */
  public static int getWorldForLevel(int levelNumber) {
    if (levelNumber <= 9) return WORLD_TUTORIAL;
    if (levelNumber <= 19) return WORLD_BASICS;
    if (levelNumber <= 29) return WORLD_MATH;
    if (levelNumber <= 39) return WORLD_BINARY;
    if (levelNumber <= 49) return WORLD_SPEED;
    if (levelNumber <= 59) return WORLD_CHALLENGE;
    if (levelNumber <= 69) return WORLD_MASTER;
    if (levelNumber <= 79) return WORLD_CHAMPION;
    if (levelNumber <= 89) return WORLD_EXPERT;
    return WORLD_LEGEND;
  }

  /**
   * Determina la posizione del livello nel suo mondo
   */
  public static int getLevelInWorld(int levelNumber) {
    return ((levelNumber - 1) % 10) + 1;
  }

  /**
   * Determina la difficoltà basata sulla posizione nel mondo
   */
  public static int getDifficultyForLevel(int levelNumber) {
    int page = (levelNumber - 1) / 10;
    if (page <= 1) return DIFFICULTY_EASY;
    if (page <= 3) return DIFFICULTY_MEDIUM;
    if (page <= 6) return DIFFICULTY_HARD;
    return DIFFICULTY_EXPERT;
  }
}
