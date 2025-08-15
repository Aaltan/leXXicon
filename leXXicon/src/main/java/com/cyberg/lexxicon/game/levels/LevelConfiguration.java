package com.cyberg.lexxicon.game.levels;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Classe principale per la configurazione di tutti i livelli del gioco
 * Permette una facile modifica e aggiunta di nuovi livelli
 */
public class LevelConfiguration {

  // Tipi di modalità di gioco
  public enum GameMode {
    CANONICA("Trova N parole"),
    CON_AIUTI("Trova N parole con aiuti"),
    FIND_WORD("Trova le parole mostrate"),
    MATH_SOMMA("Ottieni i valori sommando"),
    BINARY("Trova le sequenze binarie");

    private final String description;

    GameMode(String description) {
      this.description = description;
    }

    public String getDescription() {
      return description;
    }
  }

  // Tipi di bonus disponibili
  public enum BonusType {
    SUGGESTION("Suggerimento"),
    BOMB("Bomba"),
    ICE("Ghiaccio"),
    WIPE("Pulisci lettere"),
    NEW_BOARD("Nuova griglia"),
    RANDOM("Casuale");

    private final String description;

    BonusType(String description) {
      this.description = description;
    }

    public String getDescription() {
      return description;
    }
  }

  /**
   * Classe per definire un singolo livello con tutti i suoi parametri
   */
  public static class Level {
    public final int levelNumber;
    public final GameMode mode;
    public final int targetCount;        // Numero di parole/sequenze/valori da trovare
    public final int timeSeconds;        // Tempo in secondi
    public final int minWordLength;      // Lunghezza minima parola (per CANONICA/CON_AIUTI)
    public final List<BonusType> bonuses; // Lista bonus disponibili
    public final int minNumbers;         // Per MATH: minimo numeri da sommare
    public final int maxSum;             // Per MATH: valore massimo della somma
    public final int minBinaryLength;    // Per BINARY: lunghezza minima sequenza
    public final int maxBinaryLength;    // Per BINARY: lunghezza massima sequenza
    public final boolean showBonuses;  // Aggiungi dopo gli altri campi final

    private Level(Builder builder) {
      this.levelNumber = builder.levelNumber;
      this.mode = builder.mode;
      this.targetCount = builder.targetCount;
      this.timeSeconds = builder.timeSeconds;
      this.minWordLength = builder.minWordLength;
      this.bonuses = new ArrayList<>(builder.bonuses);
      this.minNumbers = builder.minNumbers;
      this.maxSum = builder.maxSum;
      this.minBinaryLength = builder.minBinaryLength;
      this.maxBinaryLength = builder.maxBinaryLength;
      // Mostra i bonus se sono in modalità canonica o con aiuti
      this.showBonuses = builder.mode == GameMode.CANONICA || builder.mode == GameMode.CON_AIUTI;
    }

    // Builder pattern per costruzione flessibile
    public static class Builder {
      private final int levelNumber;
      private final GameMode mode;
      private int targetCount = 3;
      private int timeSeconds = 120;
      private int minWordLength = 3;
      private List<BonusType> bonuses = new ArrayList<>();
      private int minNumbers = 2;
      private int maxSum = 20;
      private int minBinaryLength = 3;
      private int maxBinaryLength = 5;

      public Builder(int levelNumber, GameMode mode) {
        this.levelNumber = levelNumber;
        this.mode = mode;
      }

      public Builder targetCount(int val) {
        targetCount = val;
        return this;
      }

      public Builder time(int minutes, int seconds) {
        timeSeconds = minutes * 60 + seconds;
        return this;
      }

      public Builder minWordLength(int val) {
        minWordLength = val;
        return this;
      }

      public Builder addBonus(BonusType bonus) {
        bonuses.add(bonus);
        return this;
      }

      public Builder addRandomBonuses(int count) {
        for (int i = 0; i < count; i++) {
          bonuses.add(BonusType.RANDOM);
        }
        return this;
      }

      public Builder mathParams(int minNums, int max) {
        minNumbers = minNums;
        maxSum = max;
        return this;
      }

      public Builder binaryParams(int min, int max) {
        minBinaryLength = min;
        maxBinaryLength = max;
        return this;
      }

      public Level build() {
        return new Level(this);
      }
    }
  }

  // Mappa principale dei livelli
  private static final Map<Integer, Level> LEVELS = new HashMap<>();

  // Inizializzazione statica di tutti i livelli
  static {
    initializeLevels();
  }

  /**
   * Inizializza tutti i 99 livelli del gioco
   */
  private static void initializeLevels() {
    // ====== PAGINA 1-9: TUTORIAL ======
    LEVELS.put(1, new Level.Builder(1, GameMode.CANONICA)
        .targetCount(3)
        .time(2, 30)
        .minWordLength(3)
        .build());

    LEVELS.put(2, new Level.Builder(2, GameMode.CON_AIUTI)
        .targetCount(4)
        .time(2, 0)
        .minWordLength(3)
        .addBonus(BonusType.SUGGESTION)
        .build());

    LEVELS.put(3, new Level.Builder(3, GameMode.FIND_WORD)
        .targetCount(4)
        .time(2, 0)
        .build());

    LEVELS.put(4, new Level.Builder(4, GameMode.MATH_SOMMA)
        .targetCount(3)
        .time(2, 30)
        .mathParams(2, 19)
        .build());

    LEVELS.put(5, new Level.Builder(5, GameMode.CON_AIUTI)
        .targetCount(3)
        .time(3, 0)
        .minWordLength(4)
        .addRandomBonuses(2)
        .build());

    LEVELS.put(6, new Level.Builder(6, GameMode.BINARY)
        .targetCount(4)
        .time(2, 30)
        .binaryParams(3, 5)
        .build());

    LEVELS.put(7, new Level.Builder(7, GameMode.CANONICA)
        .targetCount(3)
        .time(3, 0)
        .minWordLength(4)
        .build());

    LEVELS.put(8, new Level.Builder(8, GameMode.MATH_SOMMA)
        .targetCount(3)
        .time(2, 30)
        .mathParams(3, 25)
        .build());

    LEVELS.put(9, new Level.Builder(9, GameMode.CANONICA)
        .targetCount(5)
        .time(2, 0)
        .minWordLength(3)
        .build());

    // ====== PAGINA 10-19: PRINCIPIANTE ======
    LEVELS.put(10, new Level.Builder(10, GameMode.CANONICA)
        .targetCount(4)
        .time(2, 30)
        .minWordLength(3)
        .build());

    LEVELS.put(11, new Level.Builder(11, GameMode.CON_AIUTI)
        .targetCount(5)
        .time(2, 0)
        .minWordLength(3)
        .addBonus(BonusType.BOMB)
        .build());

    LEVELS.put(12, new Level.Builder(12, GameMode.CANONICA)
        .targetCount(5)
        .time(2, 15)
        .minWordLength(4)
        .build());

    LEVELS.put(13, new Level.Builder(13, GameMode.FIND_WORD)
        .targetCount(5)
        .time(1, 45)
        .build());

    LEVELS.put(14, new Level.Builder(14, GameMode.CON_AIUTI)
        .targetCount(6)
        .time(2, 0)
        .minWordLength(3)
        .addBonus(BonusType.ICE)
        .addBonus(BonusType.SUGGESTION)
        .build());

    LEVELS.put(15, new Level.Builder(15, GameMode.MATH_SOMMA)
        .targetCount(4)
        .time(2, 0)
        .mathParams(3, 30)
        .build());

    LEVELS.put(16, new Level.Builder(16, GameMode.CANONICA)
        .targetCount(6)
        .time(2, 30)
        .minWordLength(4)
        .build());

    LEVELS.put(17, new Level.Builder(17, GameMode.BINARY)
        .targetCount(5)
        .time(2, 0)
        .binaryParams(3, 6)
        .build());

    LEVELS.put(18, new Level.Builder(18, GameMode.CON_AIUTI)
        .targetCount(7)
        .time(2, 15)
        .minWordLength(3)
        .addRandomBonuses(3)
        .build());

    LEVELS.put(19, new Level.Builder(19, GameMode.CANONICA)
        .targetCount(8)
        .time(2, 0)
        .minWordLength(3)
        .build());

    // ====== PAGINA 20-29: INTERMEDIO ======
    generateIntermediateLevels();

    // ====== PAGINA 30-39: INTERMEDIO AVANZATO ======
    generateAdvancedIntermediateLevels();

    // ====== PAGINA 40-49: ESPERTO ======
    generateExpertLevels();

    // ====== PAGINA 50-59: ESPERTO AVANZATO ======
    generateAdvancedExpertLevels();

    // ====== PAGINA 60-69: MAESTRO ======
    generateMasterLevels();

    // ====== PAGINA 70-79: MAESTRO AVANZATO ======
    generateAdvancedMasterLevels();

    // ====== PAGINA 80-89: CAMPIONE ======
    generateChampionLevels();

    // ====== PAGINA 90-99: LEGGENDA ======
    generateLegendLevels();
  }

  // Metodi helper per generare livelli delle varie difficoltà
  private static void generateIntermediateLevels() {
    // Livelli 20-29
    for (int i = 20; i <= 29; i++) {
      int pageLevel = i - 19; // 1-10 nella pagina

      if (pageLevel <= 4) {
        // Canonica
        LEVELS.put(i, new Level.Builder(i, GameMode.CANONICA)
            .targetCount(6 + pageLevel/2)
            .time(2, 30 - (pageLevel * 5))
            .minWordLength(4)
            .build());
      } else if (pageLevel == 5) {
        // Find Word
        LEVELS.put(i, new Level.Builder(i, GameMode.FIND_WORD)
            .targetCount(6)
            .time(1, 30)
            .build());
      } else if (pageLevel <= 7) {
        // Con Aiuti
        LEVELS.put(i, new Level.Builder(i, GameMode.CON_AIUTI)
            .targetCount(8)
            .time(2, 0)
            .minWordLength(4)
            .addRandomBonuses(2)
            .build());
      } else if (pageLevel == 8) {
        // Math
        LEVELS.put(i, new Level.Builder(i, GameMode.MATH_SOMMA)
            .targetCount(5)
            .time(1, 45)
            .mathParams(3, 35)
            .build());
      } else {
        // Binary
        LEVELS.put(i, new Level.Builder(i, GameMode.BINARY)
            .targetCount(6)
            .time(1, 45)
            .binaryParams(4, 7)
            .build());
      }
    }
  }

  private static void generateAdvancedIntermediateLevels() {
    // Livelli 30-39 - Pattern simile ma più difficile
    for (int i = 30; i <= 39; i++) {
      int pageLevel = i - 29;

      // Mix di modalità con difficoltà crescente
      if (pageLevel % 3 == 1) {
        LEVELS.put(i, new Level.Builder(i, GameMode.CANONICA)
            .targetCount(8 + pageLevel/2)
            .time(2, 0)
            .minWordLength(5)
            .build());
      } else if (pageLevel % 3 == 2) {
        LEVELS.put(i, new Level.Builder(i, GameMode.CON_AIUTI)
            .targetCount(10)
            .time(1, 45)
            .minWordLength(4)
            .addRandomBonuses(3)
            .build());
      } else {
        if (pageLevel < 5) {
          LEVELS.put(i, new Level.Builder(i, GameMode.FIND_WORD)
              .targetCount(7)
              .time(1, 30)
              .build());
        } else if (pageLevel < 8) {
          LEVELS.put(i, new Level.Builder(i, GameMode.MATH_SOMMA)
              .targetCount(6)
              .time(1, 30)
              .mathParams(4, 40)
              .build());
        } else {
          LEVELS.put(i, new Level.Builder(i, GameMode.BINARY)
              .targetCount(7)
              .time(1, 30)
              .binaryParams(5, 8)
              .build());
        }
      }
    }
  }

  private static void generateExpertLevels() {
    // Livelli 40-49
    for (int i = 40; i <= 49; i++) {
      int pageLevel = i - 39;

      if (pageLevel <= 3) {
        LEVELS.put(i, new Level.Builder(i, GameMode.CANONICA)
            .targetCount(10 + pageLevel)
            .time(1, 45)
            .minWordLength(5)
            .build());
      } else if (pageLevel <= 6) {
        LEVELS.put(i, new Level.Builder(i, GameMode.CON_AIUTI)
            .targetCount(12)
            .time(1, 30)
            .minWordLength(5)
            .addBonus(BonusType.NEW_BOARD)
            .addRandomBonuses(2)
            .build());
      } else if (pageLevel == 7) {
        LEVELS.put(i, new Level.Builder(i, GameMode.FIND_WORD)
            .targetCount(8)
            .time(1, 15)
            .build());
      } else if (pageLevel == 8) {
        LEVELS.put(i, new Level.Builder(i, GameMode.MATH_SOMMA)
            .targetCount(7)
            .time(1, 15)
            .mathParams(4, 45)
            .build());
      } else {
        LEVELS.put(i, new Level.Builder(i, GameMode.BINARY)
            .targetCount(8)
            .time(1, 15)
            .binaryParams(5, 9)
            .build());
      }
    }
  }

  private static void generateAdvancedExpertLevels() {
    // Livelli 50-59
    for (int i = 50; i <= 59; i++) {
      int pageLevel = i - 49;

      // Difficoltà molto alta
      if (pageLevel <= 4) {
        LEVELS.put(i, new Level.Builder(i, GameMode.CANONICA)
            .targetCount(12 + pageLevel)
            .time(1, 30)
            .minWordLength(6)
            .build());
      } else if (pageLevel <= 7) {
        LEVELS.put(i, new Level.Builder(i, GameMode.CON_AIUTI)
            .targetCount(15)
            .time(1, 15)
            .minWordLength(5)
            .addRandomBonuses(4)
            .build());
      } else if (pageLevel == 8) {
        LEVELS.put(i, new Level.Builder(i, GameMode.FIND_WORD)
            .targetCount(10)
            .time(1, 0)
            .build());
      } else {
        LEVELS.put(i, new Level.Builder(i, GameMode.MATH_SOMMA)
            .targetCount(8)
            .time(1, 0)
            .mathParams(5, 50)
            .build());
      }
    }
  }

  private static void generateMasterLevels() {
    // Livelli 60-69
    for (int i = 60; i <= 69; i++) {
      int pageLevel = i - 59;

      if (pageLevel <= 3) {
        LEVELS.put(i, new Level.Builder(i, GameMode.CANONICA)
            .targetCount(15 + pageLevel)
            .time(1, 15)
            .minWordLength(6)
            .build());
      } else if (pageLevel <= 6) {
        LEVELS.put(i, new Level.Builder(i, GameMode.CON_AIUTI)
            .targetCount(18)
            .time(1, 0)
            .minWordLength(6)
            .addBonus(BonusType.NEW_BOARD)
            .addBonus(BonusType.WIPE)
            .addRandomBonuses(3)
            .build());
      } else if (pageLevel == 7) {
        LEVELS.put(i, new Level.Builder(i, GameMode.FIND_WORD)
            .targetCount(12)
            .time(0, 50)
            .build());
      } else if (pageLevel == 8) {
        LEVELS.put(i, new Level.Builder(i, GameMode.BINARY)
            .targetCount(10)
            .time(1, 0)
            .binaryParams(6, 10)
            .build());
      } else {
        LEVELS.put(i, new Level.Builder(i, GameMode.MATH_SOMMA)
            .targetCount(10)
            .time(0, 50)
            .mathParams(5, 60)
            .build());
      }
    }
  }

  private static void generateAdvancedMasterLevels() {
    // Livelli 70-79 - Molto difficili
    for (int i = 70; i <= 79; i++) {
      int pageLevel = i - 69;

      if (pageLevel <= 5) {
        LEVELS.put(i, new Level.Builder(i, GameMode.CANONICA)
            .targetCount(18 + pageLevel)
            .time(1, 0)
            .minWordLength(7)
            .build());
      } else if (pageLevel <= 7) {
        LEVELS.put(i, new Level.Builder(i, GameMode.CON_AIUTI)
            .targetCount(20)
            .time(0, 50)
            .minWordLength(6)
            .addRandomBonuses(5)
            .build());
      } else if (pageLevel == 8) {
        LEVELS.put(i, new Level.Builder(i, GameMode.FIND_WORD)
            .targetCount(15)
            .time(0, 45)
            .build());
      } else {
        LEVELS.put(i, new Level.Builder(i, GameMode.MATH_SOMMA)
            .targetCount(12)
            .time(0, 45)
            .mathParams(6, 70)
            .build());
      }
    }
  }

  private static void generateChampionLevels() {
    // Livelli 80-89 - Estremi
    for (int i = 80; i <= 89; i++) {
      int pageLevel = i - 79;

      if (pageLevel <= 4) {
        LEVELS.put(i, new Level.Builder(i, GameMode.CANONICA)
            .targetCount(20 + pageLevel*2)
            .time(0, 50)
            .minWordLength(7)
            .build());
      } else if (pageLevel <= 6) {
        LEVELS.put(i, new Level.Builder(i, GameMode.CON_AIUTI)
            .targetCount(25)
            .time(0, 45)
            .minWordLength(7)
            .addRandomBonuses(6)
            .build());
      } else if (pageLevel == 7) {
        LEVELS.put(i, new Level.Builder(i, GameMode.BINARY)
            .targetCount(12)
            .time(0, 45)
            .binaryParams(7, 12)
            .build());
      } else if (pageLevel == 8) {
        LEVELS.put(i, new Level.Builder(i, GameMode.FIND_WORD)
            .targetCount(18)
            .time(0, 40)
            .build());
      } else {
        LEVELS.put(i, new Level.Builder(i, GameMode.MATH_SOMMA)
            .targetCount(15)
            .time(0, 40)
            .mathParams(6, 80)
            .build());
      }
    }
  }

  private static void generateLegendLevels() {
    // Livelli 90-99 - Leggendari (più difficili in assoluto)
    for (int i = 90; i <= 99; i++) {
      int pageLevel = i - 89;

      if (pageLevel <= 3) {
        LEVELS.put(i, new Level.Builder(i, GameMode.CANONICA)
            .targetCount(25 + pageLevel*2)
            .time(0, 45)
            .minWordLength(8)
            .build());
      } else if (pageLevel <= 5) {
        LEVELS.put(i, new Level.Builder(i, GameMode.CON_AIUTI)
            .targetCount(30)
            .time(0, 40)
            .minWordLength(7)
            .addRandomBonuses(7)
            .build());
      } else if (pageLevel == 6) {
        LEVELS.put(i, new Level.Builder(i, GameMode.FIND_WORD)
            .targetCount(20)
            .time(0, 35)
            .build());
      } else if (pageLevel == 7) {
        LEVELS.put(i, new Level.Builder(i, GameMode.BINARY)
            .targetCount(15)
            .time(0, 40)
            .binaryParams(8, 15)
            .build());
      } else if (pageLevel == 8) {
        LEVELS.put(i, new Level.Builder(i, GameMode.MATH_SOMMA)
            .targetCount(18)
            .time(0, 35)
            .mathParams(7, 99)
            .build());
      } else {
        // Livello 99 - Il più difficile
        LEVELS.put(i, new Level.Builder(i, GameMode.CANONICA)
            .targetCount(40)
            .time(0, 30)
            .minWordLength(8)
            .build());
      }
    }
  }

  /**
   * Ottiene la configurazione di un livello
   */
  public static Level getLevel(int levelNumber) {
    return LEVELS.get(levelNumber);
  }

  /**
   * Converte un bonus RANDOM in un bonus specifico
   */
  public static BonusType resolveRandomBonus() {
    Random rand = new Random();
    BonusType[] realBonuses = {
        BonusType.SUGGESTION,
        BonusType.BOMB,
        BonusType.ICE,
        BonusType.WIPE,
        BonusType.NEW_BOARD
    };
    return realBonuses[rand.nextInt(realBonuses.length)];
  }

  /**
   * Ottiene informazioni leggibili sul livello per il debug
   */
  public static String getLevelDescription(int levelNumber) {
    Level level = getLevel(levelNumber);
    if (level == null) return "Livello non trovato";

    StringBuilder desc = new StringBuilder();
    desc.append("Livello ").append(levelNumber).append(": ");
    desc.append(level.mode.toString()).append("\n");

    switch (level.mode) {
      case CANONICA:
        desc.append("Trova ").append(level.targetCount).append(" parole");
        desc.append(" (min ").append(level.minWordLength).append(" lettere)");
        break;
      case CON_AIUTI:
        desc.append("Trova ").append(level.targetCount).append(" parole");
        desc.append(" (min ").append(level.minWordLength).append(" lettere)");
        desc.append(" - Aiuti: ").append(level.bonuses.size());
        break;
      case FIND_WORD:
        desc.append("Trova ").append(level.targetCount).append(" parole mostrate");
        break;
      case MATH_SOMMA:
        desc.append("Ottieni ").append(level.targetCount).append(" valori");
        desc.append(" (min ").append(level.minNumbers).append(" cifre, max ").append(level.maxSum).append(")");
        break;
      case BINARY:
        desc.append("Trova ").append(level.targetCount).append(" sequenze");
        desc.append(" (").append(level.minBinaryLength).append("-").append(level.maxBinaryLength).append(" bit)");
        break;
    }

    desc.append("\nTempo: ").append(level.timeSeconds / 60).append(":").append(String.format("%02d", level.timeSeconds % 60));

    return desc.toString();
  }

  /**
   * Ottiene statistiche sulla distribuzione delle modalità per pagina
   */
  public static Map<String, Integer> getPageStatistics(int startLevel, int endLevel) {
    Map<String, Integer> stats = new HashMap<>();

    for (GameMode mode : GameMode.values()) {
      stats.put(mode.toString(), 0);
    }

    for (int i = startLevel; i <= endLevel && i <= 99; i++) {
      Level level = getLevel(i);
      if (level != null) {
        String key = level.mode.toString();
        stats.put(key, stats.get(key) + 1);
      }
    }

    return stats;
  }
}