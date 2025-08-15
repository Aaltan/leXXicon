package com.cyberg.lexxicon.testutils;

import com.cyberg.lexxicon.environment.CrossVariables;
import com.cyberg.lexxicon.game.levels.LevelConfiguration;
import com.cyberg.lexxicon.game.levels.LevelInfos;
import com.cyberg.lexxicon.game.levels.LevelAdapter;

import android.util.Log;

/**
 * Classe helper per debuggare problemi con i livelli
 */
public class LevelDebugHelper {

  private static final String TAG = "LevelDebug";

  /**
   * Stampa informazioni dettagliate sul livello corrente
   * Chiamalo da LevelsGameCore.configureLevel() dopo aver configurato tutto
   */
  public static void debugCurrentLevel() {
    int levelNum = CrossVariables.LEVELS_SELECTED_NUM;
    if (levelNum <= 0 || levelNum > 99) {
      Log.e(TAG, "Numero livello non valido: " + levelNum);
      return;
    }

    Log.d(TAG, "=====================================");
    Log.d(TAG, "DEBUG LIVELLO " + levelNum);
    Log.d(TAG, "=====================================");

    // Info dal nuovo sistema
    LevelConfiguration.Level newLevel = LevelConfiguration.getLevel(levelNum);
    if (newLevel != null) {
      Log.d(TAG, "--- Configurazione Nuova ---");
      Log.d(TAG, "Modalità: " + newLevel.mode.name() + " (" + newLevel.mode.getDescription() + ")");
      Log.d(TAG, "Target: " + newLevel.targetCount);
      Log.d(TAG, "Tempo: " + newLevel.timeSeconds + " secondi");
      Log.d(TAG, "Bonus count: " + newLevel.bonuses.size());

      if (!newLevel.bonuses.isEmpty()) {
        StringBuilder bonusStr = new StringBuilder("Bonus: ");
        for (LevelConfiguration.BonusType b : newLevel.bonuses) {
          bonusStr.append(b.name()).append(" ");
        }
        Log.d(TAG, bonusStr.toString());
      }

      switch (newLevel.mode) {
        case FIND_WORD:
          Log.d(TAG, "Tipo: FIND_WORD - Dovrebbe mostrare parole da trovare");
          break;
        case MATH_SOMMA:
          Log.d(TAG, "Tipo: MATH - Min numeri: " + newLevel.minNumbers + ", Max: " + newLevel.maxSum);
          break;
        case BINARY:
          Log.d(TAG, "Tipo: BINARY - Lunghezza: " + newLevel.minBinaryLength + "-" + newLevel.maxBinaryLength);
          break;
      }
    }

    // Info dal sistema legacy (CrossVariables)
    LevelInfos oldLevel = CrossVariables.LEVEL_INFOS[levelNum - 1];
    if (oldLevel != null) {
      Log.d(TAG, "--- Configurazione Legacy (CrossVariables) ---");
      Log.d(TAG, "Tipo livello: " + CrossVariables.LEVEL_TYPE_ACTUAL_GAME +
          " (0=WORDS, 1=FIND, 2=BINARY, 3=MATH)");
      Log.d(TAG, "Timer: " + CrossVariables.TIMEOUT_SAGA_MODE_TIME_LEFT + " ms");
      Log.d(TAG, "Words left: " + CrossVariables.WORDS_LEFT_TO_COMPOSE);
      Log.d(TAG, "Bonus shown: " + oldLevel.getBonusShown());

      Log.d(TAG, "Bonus impostati:");
      Log.d(TAG, "  Suggestion: " + CrossVariables.NR_BONUS_SUGGESTION);
      Log.d(TAG, "  Bomb: " + CrossVariables.NR_BONUS_BOMB);
      Log.d(TAG, "  Ice: " + CrossVariables.NR_BONUS_ICE);
      Log.d(TAG, "  Wipe: " + CrossVariables.NR_BONUS_WIPE_LETTERS);
      Log.d(TAG, "  New Board: " + CrossVariables.NR_BONUS_NEW_BOARD);

      if (CrossVariables.LEVEL_TYPE_ACTUAL_GAME == CrossVariables.LEVEL_TYPE_FIND) {
        Log.d(TAG, "!!! MODALITÀ FIND_WORD ATTIVA !!!");
        Log.d(TAG, "BonusShown = " + oldLevel.getBonusShown() + " (dovrebbe essere FALSE)");
        Log.d(TAG, "Se BonusShown è TRUE, i bonus verranno mostrati invece della parola!");
      }

      if (CrossVariables.LEVEL_TYPE_ACTUAL_GAME == CrossVariables.LEVEL_TYPE_MATH) {
        Log.d(TAG, "!!! MODALITÀ MATH ATTIVA !!!");
        Log.d(TAG, "Math mode: " + CrossVariables.MATH_ACTUAL_MODE);
        Log.d(TAG, "Min: " + oldLevel.getWordNumbMin() + ", Max: " + oldLevel.getWordNumbMax());
      }

      if (CrossVariables.LEVEL_TYPE_ACTUAL_GAME == CrossVariables.LEVEL_TYPE_BINARY) {
        Log.d(TAG, "!!! MODALITÀ BINARY ATTIVA !!!");
        Log.d(TAG, "Binary length: " + oldLevel.getWordNumbMin() + "-" + oldLevel.getWordNumbMax());
      }
    }

    Log.d(TAG, "=====================================");
  }

  /**
   * Verifica che il livello 3 sia configurato correttamente
   */
  public static void verifyLevel3() {
    Log.d(TAG, "=== VERIFICA SPECIFICA LIVELLO 3 ===");

    LevelConfiguration.Level level3 = LevelConfiguration.getLevel(3);
    if (level3 == null) {
      Log.e(TAG, "ERRORE: Livello 3 non trovato!");
      return;
    }

    Log.d(TAG, "Modalità livello 3: " + level3.mode.name());
    Log.d(TAG, "Dovrebbe essere: FIND_WORD");

    if (level3.mode != LevelConfiguration.GameMode.FIND_WORD) {
      Log.e(TAG, "ERRORE: Livello 3 non è FIND_WORD!");
    }

    Log.d(TAG, "Numero bonus: " + level3.bonuses.size());
    Log.d(TAG, "Dovrebbe essere: 0");

    if (!level3.bonuses.isEmpty()) {
      Log.e(TAG, "ERRORE: Livello 3 ha dei bonus!");
    }

    // Simula la conversione
    LevelInfos converted = LevelAdapter.convertToLevelInfos(level3);
    Log.d(TAG, "Dopo conversione:");
    Log.d(TAG, "  Tipo: " + converted.getLevelType() + " (dovrebbe essere 1 per FIND)");
    Log.d(TAG, "  BonusShown: " + converted.getBonusShown() + " (dovrebbe essere FALSE)");
    Log.d(TAG, "  Suggestion bonus: " + converted.getNrSuggestionBonus() + " (dovrebbe essere -1)");

    if (converted.getBonusShown()) {
      Log.e(TAG, "ERRORE CRITICO: BonusShown è TRUE per un livello FIND_WORD!");
      Log.e(TAG, "Questo farà mostrare i bonus invece della parola da trovare!");
    }

    Log.d(TAG, "=== FINE VERIFICA ===");
  }
}