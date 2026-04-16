package managers;

/**
 * CardTracker - Tracks all card-related events in the game
 * - Cards collected (from chests/pickups)
 * - Cards used (checkpoint unlocks)
 * - Card balance
 * - Statistics
 */
public class CardTracker {
    
    private int cardsCollected = 0;      // Total cards collected this session
    private int cardsUsed = 0;           // Total cards spent at checkpoints
    private int currentBalance = 0;      // Cards - (used at checkpoints)
    
    // Per-level tracking
    private int level1CardsCollected = 0;
    private int level1CardsUsed = 0;
    private int level2CardsCollected = 0;
    private int level2CardsUsed = 0;
    
    // Checkpoint milestones
    private boolean[] checkpointUnlocked = new boolean[6];  // 6 checkpoints max
    
    public CardTracker() {
    }
    
    // ===== COLLECTION =====
    public void onCardCollected(int level) {
        cardsCollected++;
        currentBalance++;
        if (level == 1) {
            level1CardsCollected++;
        } else if (level == 2) {
            level2CardsCollected++;
        }
        System.out.printf("[CardTracker] Card collected! Total: %d | Balance: %d | Level %d: +%d%n",
            cardsCollected, currentBalance, level,
            (level == 1) ? level1CardsCollected : level2CardsCollected);
    }
    
    // ===== USAGE =====
    public boolean tryUseCards(int amount, int checkpointIdx, int level) {
        if (currentBalance < amount) {
            System.out.printf("[CardTracker] INSUFFICIENT CARDS! Need %d, have %d%n", amount, currentBalance);
            return false;
        }
        
        cardsUsed += amount;
        currentBalance -= amount;
        checkpointUnlocked[checkpointIdx] = true;
        
        if (level == 1) {
            level1CardsUsed += amount;
        } else if (level == 2) {
            level2CardsUsed += amount;
        }
        
        System.out.printf("[CardTracker] Checkpoint %d unlocked! Used %d cards | Balance: %d | Level %d%n",
            checkpointIdx, amount, currentBalance, level);
        return true;
    }
    
    // ===== QUERIES =====
    public int getBalance() { return currentBalance; }
    public int getTotalCollected() { return cardsCollected; }
    public int getTotalUsed() { return cardsUsed; }
    public boolean isCheckpointUnlocked(int idx) {
        return (idx < checkpointUnlocked.length) && checkpointUnlocked[idx];
    }
    
    // ===== STATS =====
    public void printSummary() {
        System.out.println("\n========== CARD TRACKER SUMMARY ==========");
        System.out.println("Total Collected: " + cardsCollected);
        System.out.println("Total Used:      " + cardsUsed);
        System.out.println("Current Balance: " + currentBalance);
        System.out.println("\n--- Level 1 ---");
        System.out.println("Collected: " + level1CardsCollected);
        System.out.println("Used:      " + level1CardsUsed);
        System.out.println("\n--- Level 2 ---");
        System.out.println("Collected: " + level2CardsCollected);
        System.out.println("Used:      " + level2CardsUsed);
        System.out.println("\n--- Checkpoints Unlocked ---");
        for (int i = 0; i < checkpointUnlocked.length; i++) {
            if (checkpointUnlocked[i]) {
                System.out.println("  ✓ Checkpoint " + (i + 1));
            }
        }
        System.out.println("==========================================\n");
    }
    
    public void reset() {
        cardsCollected = 0;
        cardsUsed = 0;
        currentBalance = 0;
        level1CardsCollected = 0;
        level1CardsUsed = 0;
        level2CardsCollected = 0;
        level2CardsUsed = 0;
        checkpointUnlocked = new boolean[6];
    }
}
