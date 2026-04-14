package utils;

/**
 * CharacterAssetMapper - Utility class for mapping character names and states to asset paths
 * 
 * This is a clean utility class that centralizes the mapping of character names to their
 * animation asset directories. It follows the pattern of static asset path references
 * similar to AnimationAndSpriteLoader.
 * 
 * Usage:
 *   String bikerDir = CharacterAssetMapper.getCharacterDir("Biker");
 *   String walkRunDir = CharacterAssetMapper.getCharacterStateDir("Punk", "walk_run");
 */
public class CharacterAssetMapper {
    
    // Character base directories
    private static final String PLAYER_BASE = "Resources/industrial-zone/characters/player";
    
    // Character names and their subdirectories
    private static final String BIKER = PLAYER_BASE + "/Biker";
    private static final String PUNK = PLAYER_BASE + "/Punk";
    private static final String CYBORG = PLAYER_BASE + "/Cyborg";
    
    // Animation state subdirectories (common across all characters)
    private static final String IDLE = "/idle";
    private static final String WALK_RUN = "/walk_run";
    private static final String JUMP = "/jump";
    private static final String CROUCH = "/crouch";
    private static final String ATTACK = "/attack";
    private static final String RUN = "/run";
    
    /**
     * Get the base directory for a character
     * @param characterName "Biker", "Punk", or "Cyborg"
     * @return The base path for that character
     */
    public static String getCharacterDir(String characterName) {
        return switch (characterName) {
            case "Biker" -> BIKER;
            case "Punk" -> PUNK;
            case "Cyborg" -> CYBORG;
            default -> PLAYER_BASE;
        };
    }
    
    /**
     * Get the directory for a character in a specific animation state
     * @param characterName "Biker", "Punk", or "Cyborg"
     * @param stateName "idle", "walk_run", "jump", "crouch", "attack", "run"
     * @return The full path to that animation state directory
     */
    public static String getCharacterStateDir(String characterName, String stateName) {
        String baseDir = getCharacterDir(characterName);
        String stateSubdir = switch (stateName) {
            case "idle" -> IDLE;
            case "walk_run" -> WALK_RUN;
            case "jump" -> JUMP;
            case "crouch" -> CROUCH;
            case "attack" -> ATTACK;
            case "run" -> RUN;
            default -> "";
        };
        return baseDir + stateSubdir;
    }
    
    /**
     * Check if a character name is valid
     * @param characterName The character to check
     * @return true if the character is Biker, Punk, or Cyborg
     */
    public static boolean isValidCharacter(String characterName) {
        return characterName != null && 
               (characterName.equals("Biker") || 
                characterName.equals("Punk") || 
                characterName.equals("Cyborg"));
    }
    
    /**
     * Get all available characters
     * @return Array of character names
     */
    public static String[] getAvailableCharacters() {
        return new String[]{"Biker", "Punk", "Cyborg"};
    }
}
