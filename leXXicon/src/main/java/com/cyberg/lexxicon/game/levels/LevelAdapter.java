package com.cyberg.lexxicon.game.levels;

import com.cyberg.lexxicon.environment.CrossVariables;
import com.cyberg.lexxicon.game.levels.LevelConfiguration;
import com.cyberg.lexxicon.game.levels.LevelConfiguration.Level;
import com.cyberg.lexxicon.game.levels.LevelConfiguration.BonusType;
import com.cyberg.lexxicon.game.levels.LevelConfiguration.GameMode;
import com.cyberg.lexxicon.game.levels.LevelInfos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Adapter che converte le configurazioni del nuovo sistema
 * nel formato LevelInfos esistente
 */
public class LevelAdapter {

  private static final Random random = new Random();

  /**
   * Costruisce l'array di LevelInfos per CrossVariables
   * Sostituisce il metodo buildLevelInfos() esistente
   */
  public static void buildAllLevels() {
    CrossVariables.LEVEL_INFOS = new LevelInfos[CrossVariables.MAX_LEVELS];

    for (int i = 0; i < CrossVariables.MAX_LEVELS; i++) {
      int levelNumber = i + 1;
      Level levelConfig = LevelConfiguration.getLevel(levelNumber);

      if (levelConfig != null) {
        CrossVariables.LEVEL_INFOS[i] = convertToLevelInfos(levelConfig);
      } else {
        // Fallback per livelli non definiti
        CrossVariables.LEVEL_INFOS[i] = createDefaultLevel(levelNumber);
      }
    }

    if (CrossVariables.DEBUG) {
      printLevelStatistics();
    }
  }

  /**
   * Converte una configurazione Level nel formato LevelInfos legacy
   */
  public static LevelInfos convertToLevelInfos(Level level) {
    // Determina il tipo di livello per CrossVariables
    int levelType = getLevelType(level.mode);

    // Converte il tempo in millisecondi
    int timerMax = level.timeSeconds * 1000;

    int[] bonuses;
    if (level.showBonuses) {  // USA IL NUOVO CAMPO
      // Usa i bonus normalmente
      bonuses = convertBonuses(level.bonuses);
    }
    else {
      // Forza tutto a -1 per non mostrare i bonus
      bonuses = new int[] {-1, -1, -1, -1, -1};
    }

    // Parametri specifici per tipo di gioco
    int minWordLength = level.minWordLength;
    int wordNumbMin = 0;
    int wordNumbMax = 0;
    int operationType = CrossVariables.MATH_MODE_NONE;

    switch (level.mode) {
      case FIND_WORD:
        // Per FIND_WORD usiamo i parametri di default ma con minWordLength maggiore
        minWordLength = Math.max(4, level.minWordLength);
        break;

      case MATH_SOMMA:
        // Configura parametri per modalità matematica
        wordNumbMin = level.minNumbers;
        wordNumbMax = level.maxSum;
        operationType = CrossVariables.MATH_MODE_ADD;
        minWordLength = 0; // Non usato per MATH
        break;

      case BINARY:
        // Configura parametri per modalità binaria
        wordNumbMin = level.minBinaryLength;
        wordNumbMax = level.maxBinaryLength;
        minWordLength = 0; // Non usato per BINARY
        break;

      default:
        // CANONICA e CON_AIUTI usano i parametri standard
        break;
    }

    return new LevelInfos(
        levelType,
        timerMax,
        level.targetCount,
        bonuses[0], // suggestion
        bonuses[1], // bomb
        bonuses[2], // ice
        bonuses[3], // wipe
        bonuses[4], // newBoard
        minWordLength,
        wordNumbMin,
        wordNumbMax,
        operationType
    );
  }

  /**
   * Converte GameMode nel tipo di livello per CrossVariables
   */
  private static int getLevelType(GameMode mode) {
    switch (mode) {
      case CANONICA:
      case CON_AIUTI:
        return CrossVariables.LEVEL_TYPE_WORDS;
      case FIND_WORD:
        return CrossVariables.LEVEL_TYPE_FIND;
      case MATH_SOMMA:
        return CrossVariables.LEVEL_TYPE_MATH;
      case BINARY:
        return CrossVariables.LEVEL_TYPE_BINARY;
      default:
        return CrossVariables.LEVEL_TYPE_WORDS;
    }
  }

  /**
   * Converte la lista di bonus nel formato array per LevelInfos
   * @return array [suggestion, bomb, ice, wipe, newBoard]
   */
  private static int[] convertBonuses(List<BonusType> bonusList) {
    int[] result = new int[5]; // [suggestion, bomb, ice, wipe, newBoard]

    for (BonusType bonus : bonusList) {
      BonusType actualBonus = bonus;

      // Risolvi bonus casuali
      if (bonus == BonusType.RANDOM) {
        actualBonus = LevelConfiguration.resolveRandomBonus();
      }

      switch (actualBonus) {
        case SUGGESTION:
          result[0]++;
          break;
        case BOMB:
          result[1]++;
          break;
        case ICE:
          result[2]++;
          break;
        case WIPE:
          result[3]++;
          break;
        case NEW_BOARD:
          result[4]++;
          break;
      }
    }

    return result;
  }

  /**
   * Crea un livello di default per numeri non configurati
   */
  private static LevelInfos createDefaultLevel(int levelNumber) {
    // Livello di default progressivamente più difficile
    int difficulty = (levelNumber - 1) / 10; // 0-9

    return new LevelInfos(
        CrossVariables.LEVEL_TYPE_WORDS,
        Math.max(30000, 120000 - (difficulty * 10000)), // Tempo da 2 min a 30 sec
        Math.min(3 + difficulty, 15), // Parole da 3 a 15
        0, 0, 0, 0, 0, // Nessun bonus di default
        Math.min(3 + difficulty/3, 7), // Min word length da 3 a 7
        -1, -1, CrossVariables.MATH_MODE_NONE
    );
  }

  /**
   * Stampa statistiche sui livelli per debug
   */
  private static void printLevelStatistics() {
    android.util.Log.d("LevelAdapter", "=== STATISTICHE LIVELLI ===");

    for (int page = 0; page < 10; page++) {
      int start = page * 10;
      int end = Math.min(start + 9, 99);
      if (start > 0) start++; // I livelli partono da 1

      android.util.Log.d("LevelAdapter",
          String.format("Pagina %d-%d:", start, end));

      java.util.Map<String, Integer> stats =
          LevelConfiguration.getPageStatistics(start, end);

      for (java.util.Map.Entry<String, Integer> entry : stats.entrySet()) {
        if (entry.getValue() > 0) {
          android.util.Log.d("LevelAdapter",
              "  " + entry.getKey() + ": " + entry.getValue());
        }
      }
    }
  }

  /**
   * Ottiene una descrizione testuale del livello corrente
   * Utile per debug e per mostrare info all'utente
   */
  public static String getCurrentLevelDescription() {
    if (CrossVariables.LEVELS_SELECTED_NUM <= 0) {
      return "Nessun livello selezionato";
    }

    return LevelConfiguration.getLevelDescription(
        CrossVariables.LEVELS_SELECTED_NUM);
  }

  /**
   * Verifica che la distribuzione dei livelli rispetti i vincoli
   * per ogni pagina (usato per testing)
   */
  public static boolean validateLevelDistribution() {
    for (int page = 0; page < 10; page++) {
      int start = page * 10 + (page == 0 ? 1 : 0);
      int end = Math.min(start + 9, 99);

      int canonicaCount = 0;
      int conAiutiCount = 0;
      int findWordCount = 0;
      int mathCount = 0;
      int binaryCount = 0;

      for (int i = start; i <= end; i++) {
        Level level = LevelConfiguration.getLevel(i);
        if (level != null) {
          switch (level.mode) {
            case CANONICA:
              canonicaCount++;
              break;
            case CON_AIUTI:
              conAiutiCount++;
              break;
            case FIND_WORD:
              findWordCount++;
              break;
            case MATH_SOMMA:
              mathCount++;
              break;
            case BINARY:
              binaryCount++;
              break;
          }
        }
      }

      int totalCanoniche = canonicaCount + conAiutiCount;
      int totalMathBinary = mathCount + binaryCount;

      // Verifica vincoli
      if (totalCanoniche < 4 || totalCanoniche > 7) {
        android.util.Log.e("LevelAdapter",
            String.format("Pagina %d-%d: Canoniche+Aiuti=%d (deve essere 4-7)",
                start, end, totalCanoniche));
        return false;
      }

      if (findWordCount < 1 || findWordCount > 2) {
        android.util.Log.e("LevelAdapter",
            String.format("Pagina %d-%d: FindWord=%d (deve essere 1-2)",
                start, end, findWordCount));
        return false;
      }

      if (totalMathBinary > 3) {
        android.util.Log.e("LevelAdapter",
            String.format("Pagina %d-%d: Math+Binary=%d (max 3)",
                start, end, totalMathBinary));
        return false;
      }
    }

    return true;
  }

  /**
   * Metodo helper per ottenere il nome della modalità in italiano
   */
  public static String getGameModeName(int levelNumber) {
    Level level = LevelConfiguration.getLevel(levelNumber);
    if (level == null) return "Sconosciuto";

    switch (level.mode) {
      case CANONICA:
        return "Classico";
      case CON_AIUTI:
        return "Con Aiuti";
      case FIND_WORD:
        return "Trova Parole";
      case MATH_SOMMA:
        return "Matematica";
      case BINARY:
        return "Binario";
      default:
        return "Sconosciuto";
    }
  }

  /**
   * Ottiene il colore del tema per la pagina corrente
   * Utile per differenziare visivamente le pagine
   */
  public static int getPageThemeColor(int levelNumber) {
    int page = (levelNumber - 1) / 10;

    // Colori RGB per ogni pagina (puoi personalizzarli)
    int[][] colors = {
        {100, 200, 100}, // 1-9: Verde chiaro (Tutorial)
        {100, 150, 200}, // 10-19: Blu chiaro
        {150, 100, 200}, // 20-29: Viola chiaro
        {200, 150, 100}, // 30-39: Arancio chiaro
        {200, 100, 100}, // 40-49: Rosso chiaro
        {200, 100, 150}, // 50-59: Rosa
        {150, 200, 100}, // 60-69: Verde lime
        {100, 200, 200}, // 70-79: Ciano
        {200, 200, 100}, // 80-89: Giallo
        {255, 215, 0}    // 90-99: Oro (Leggenda)
    };

    if (page < colors.length) {
      return (255 << 24) | (colors[page][0] << 16) |
          (colors[page][1] << 8) | colors[page][2];
    }

    return 0xFFCCCCCC; // Grigio di default
  }
}