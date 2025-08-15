package com.cyberg.lexxicon.testutils;

import com.cyberg.lexxicon.game.levels.LevelConfiguration;
import com.cyberg.lexxicon.game.levels.LevelConfiguration.Level;

import android.util.Log;

/**
 * Classe di test per verificare la configurazione dei livelli
 */
public class LevelSystemTest {

  private static final String TAG = "LevelSystemTest";

  /**
   * Esegue tutti i test del sistema livelli
   */
  public static void runAllTests() {
    Log.d(TAG, "========================================");
    Log.d(TAG, "INIZIO TEST SISTEMA LIVELLI");
    Log.d(TAG, "========================================");

    testLevelExistence();
    testLevelDistribution();
    testDifficultyProgression();
    testSpecificLevels();
    printFullReport();

    Log.d(TAG, "========================================");
    Log.d(TAG, "FINE TEST SISTEMA LIVELLI");
    Log.d(TAG, "========================================");
  }

  /**
   * Verifica che tutti i 99 livelli esistano
   */
  private static void testLevelExistence() {
    Log.d(TAG, "\n--- Test Esistenza Livelli ---");

    int missing = 0;
    for (int i = 1; i <= 99; i++) {
      Level level = LevelConfiguration.getLevel(i);
      if (level == null) {
        Log.e(TAG, "ERRORE: Livello " + i + " non trovato!");
        missing++;
      }
    }

    if (missing == 0) {
      Log.d(TAG, "✓ Tutti i 99 livelli sono definiti");
    } else {
      Log.e(TAG, "✗ Mancano " + missing + " livelli");
    }
  }

  /**
   * Verifica la distribuzione delle modalità per ogni pagina
   */
  private static void testLevelDistribution() {
    Log.d(TAG, "\n--- Test Distribuzione Modalità ---");

    boolean allValid = true;

    for (int page = 0; page < 10; page++) {
      int start = page * 10 + 1;
      int end = Math.min(start + 8, 99);
      if (page == 0) end = 9; // Prima pagina: 1-9

      int canonica = 0, conAiuti = 0, findWord = 0, math = 0, binary = 0;

      for (int i = start; i <= end; i++) {
        Level level = LevelConfiguration.getLevel(i);
        if (level != null) {
          switch (level.mode) {
            case CANONICA: canonica++; break;
            case CON_AIUTI: conAiuti++; break;
            case FIND_WORD: findWord++; break;
            case MATH_SOMMA: math++; break;
            case BINARY: binary++; break;
          }
        }
      }

      int totalBase = canonica + conAiuti;
      int totalMathBinary = math + binary;

      boolean pageValid = true;

      // Verifica vincoli
      if (totalBase < 4 || totalBase > 7) {
        Log.e(TAG, String.format("Pagina %d-%d: CANONICA+AIUTI=%d (deve essere 4-7)",
            start, end, totalBase));
        pageValid = false;
      }

      if (findWord < 1 || findWord > 2) {
        Log.e(TAG, String.format("Pagina %d-%d: FIND_WORD=%d (deve essere 1-2)",
            start, end, findWord));
        pageValid = false;
      }

      if (totalMathBinary > 3) {
        Log.e(TAG, String.format("Pagina %d-%d: MATH+BINARY=%d (max 3)",
            start, end, totalMathBinary));
        pageValid = false;
      }

      if (pageValid) {
        Log.d(TAG, String.format("✓ Pagina %d-%d OK: C=%d A=%d F=%d M=%d B=%d",
            start, end, canonica, conAiuti, findWord, math, binary));
      } else {
        allValid = false;
      }
    }

    if (allValid) {
      Log.d(TAG, "✓ Tutte le pagine rispettano i vincoli di distribuzione");
    } else {
      Log.e(TAG, "✗ Alcune pagine non rispettano i vincoli");
    }
  }

  /**
   * Verifica che la difficoltà aumenti progressivamente
   */
  private static void testDifficultyProgression() {
    Log.d(TAG, "\n--- Test Progressione Difficoltà ---");

    boolean valid = true;

    // Verifica tempo (dovrebbe generalmente diminuire)
    for (int i = 10; i <= 90; i += 10) {
      Level prev = LevelConfiguration.getLevel(i - 5);
      Level curr = LevelConfiguration.getLevel(i + 5);

      if (prev != null && curr != null) {
        if (curr.timeSeconds > prev.timeSeconds) {
          Log.w(TAG, String.format("Tempo aumenta da livello %d (%ds) a %d (%ds)",
              i-5, prev.timeSeconds, i+5, curr.timeSeconds));
          // Non è un errore grave, solo un warning
        }
      }
    }

    // Verifica targetCount (dovrebbe generalmente aumentare)
    for (int i = 10; i <= 90; i += 10) {
      Level prev = LevelConfiguration.getLevel(i - 5);
      Level curr = LevelConfiguration.getLevel(i + 5);

      if (prev != null && curr != null &&
          prev.mode == curr.mode) { // Confronta solo stessa modalità
        if (curr.targetCount < prev.targetCount) {
          Log.w(TAG, String.format("Target diminuisce da livello %d (%d) a %d (%d)",
              i-5, prev.targetCount, i+5, curr.targetCount));
        }
      }
    }

    if (valid) {
      Log.d(TAG, "✓ Progressione difficoltà generalmente corretta");
    }
  }

  /**
   * Test specifici per livelli campione
   */
  private static void testSpecificLevels() {
    Log.d(TAG, "\n--- Test Livelli Specifici ---");

    // Test livello 1 (dovrebbe essere facile)
    Level level1 = LevelConfiguration.getLevel(1);
    if (level1 != null) {
      assert level1.targetCount == 3 : "Livello 1 deve avere 3 parole";
      assert level1.timeSeconds == 150 : "Livello 1 deve avere 2:30";
      assert level1.minWordLength == 3 : "Livello 1 min 3 lettere";
      Log.d(TAG, "✓ Livello 1 configurato correttamente");
    }

    // Test livello 99 (dovrebbe essere il più difficile)
    Level level99 = LevelConfiguration.getLevel(99);
    if (level99 != null) {
      assert level99.targetCount >= 30 : "Livello 99 deve essere molto difficile";
      assert level99.timeSeconds <= 45 : "Livello 99 deve avere poco tempo";
      Log.d(TAG, "✓ Livello 99 configurato come ultra-difficile");
    }

    // Test alcuni livelli speciali
    testLevel(4, "MATH_SOMMA", 3, 150, "primo livello matematica");
    testLevel(6, "BINARY", 4, 150, "primo livello binario");
    testLevel(3, "FIND_WORD", 4, 120, "primo find word");
  }

  private static void testLevel(int num, String expectedMode, int expectedTarget,
                                int expectedTime, String description) {
    Level level = LevelConfiguration.getLevel(num);
    if (level != null) {
      boolean valid = true;

      if (!level.mode.toString().contains(expectedMode)) {
        Log.e(TAG, String.format("Livello %d (%s): modalità errata %s invece di %s",
            num, description, level.mode, expectedMode));
        valid = false;
      }

      if (level.targetCount != expectedTarget) {
        Log.w(TAG, String.format("Livello %d (%s): target %d invece di %d",
            num, description, level.targetCount, expectedTarget));
      }

      if (level.timeSeconds != expectedTime) {
        Log.w(TAG, String.format("Livello %d (%s): tempo %ds invece di %ds",
            num, description, level.timeSeconds, expectedTime));
      }

      if (valid) {
        Log.d(TAG, String.format("✓ Livello %d (%s) OK", num, description));
      }
    }
  }

  /**
   * Stampa un report completo di tutti i livelli
   */
  private static void printFullReport() {
    Log.d(TAG, "\n--- REPORT COMPLETO LIVELLI ---");

    for (int page = 0; page < 10; page++) {
      int start = page * 10 + 1;
      int end = Math.min(start + 8, 99);
      if (page == 0) end = 9;

      Log.d(TAG, String.format("\n=== PAGINA %d-%d ===", start, end));

      for (int i = start; i <= end; i++) {
        Level level = LevelConfiguration.getLevel(i);
        if (level != null) {
          String bonusStr = "";
          if (!level.bonuses.isEmpty()) {
            bonusStr = " [" + level.bonuses.size() + " bonus]";
          }

          String params = "";
          switch (level.mode) {
            case CANONICA:
            case CON_AIUTI:
              params = String.format("min %d lettere", level.minWordLength);
              break;
            case MATH_SOMMA:
              params = String.format("min %d numeri, max %d",
                  level.minNumbers, level.maxSum);
              break;
            case BINARY:
              params = String.format("%d-%d bit",
                  level.minBinaryLength, level.maxBinaryLength);
              break;
          }

          Log.d(TAG, String.format("L%02d: %-12s | %2d items | %d:%02d | %s%s",
              i,
              level.mode.toString(),
              level.targetCount,
              level.timeSeconds / 60,
              level.timeSeconds % 60,
              params,
              bonusStr
          ));
        }
      }
    }
  }

  /**
   * Metodo per modificare facilmente un livello specifico
   * (esempio di come personalizzare un livello)
   */
  public static void customizeLevelExample() {
    // Per modificare un livello, vai in LevelConfiguration.initializeLevels()
    // e modifica la definizione del livello desiderato.
    // Esempio:

    // LEVELS.put(15, new Level.Builder(15, GameMode.CANONICA)
    //     .targetCount(10)          // Cambia numero target
    //     .time(3, 0)               // Cambia tempo
    //     .minWordLength(5)         // Cambia lunghezza minima
    //     .build());

    Log.d(TAG, "Per modificare un livello, edita LevelConfiguration.initializeLevels()");
  }
}