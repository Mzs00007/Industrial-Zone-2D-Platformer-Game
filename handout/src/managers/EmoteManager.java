package managers;

import entities.PlayerBase;

/**
 * EmoteManager — Handles emote triggering and display
 * 
 * Manages emote key bindings (P/Z/X/C/V) and tracks the currently active emote
 * for UI display. Each emote has a fixed duration before returning to idle.
 */
public class EmoteManager {

    // ===== EMOTE DEFINITIONS =====
    public enum Emote {
        NONE           ("NONE",        PlayerBase.AnimState.IDLE,       0.0f),
        POSE           ("POSE",        PlayerBase.AnimState.USE,        1.5f),   // P
        THUMBS_UP      ("THUMBS UP",   PlayerBase.AnimState.HAPPY,      1.5f),   // Z
        PEACE          ("PEACE SIGN",  PlayerBase.AnimState.TALK,       1.5f),   // X
        CLAP           ("CLAP",        PlayerBase.AnimState.ANGRY,      1.5f),   // C
        WAVE           ("WAVE",        PlayerBase.AnimState.SITDOWN,    1.5f);   // V
        
        public final String displayName;
        public final PlayerBase.AnimState animationState;
        public final float durationSeconds;
        
        Emote(String name, PlayerBase.AnimState anim, float duration) {
            this.displayName = name;
            this.animationState = anim;
            this.durationSeconds = duration;
        }
    }
    
    // ===== STATE =====
    private Emote currentEmote = Emote.NONE;
    private float emoteTimer = 0f;
    private PlayerBase player;
    
    // ===== KEY MAPPING =====
    private static final int KEY_POSE    = 'P';  // VK_P
    private static final int KEY_THUMBS  = 'Z';  // VK_Z
    private static final int KEY_PEACE   = 'X';  // VK_X
    private static final int KEY_CLAP    = 'C';  // VK_C
    private static final int KEY_WAVE    = 'V';  // VK_V
    
    // ===== CONSTRUCTOR =====
    public EmoteManager(PlayerBase player) {
        this.player = player;
    }
    
    // ===== UPDATE =====
    /** Called once per frame to update emote duration and return to idle when done */
    public void update(float delta) {
        if (currentEmote != Emote.NONE) {
            emoteTimer += delta;
            if (emoteTimer >= currentEmote.durationSeconds) {
                // Emote duration expired, return to idle
                cancelEmote();
            }
        }
    }
    
    // ===== TRIGGER EMOTES BY KEY =====
    /** Trigger an emote by key code (P, Z, X, C, V) */
    public void triggerEmoteByKey(int keyCode) {
        // Normalize to uppercase letter
        char key = Character.toUpperCase((char) keyCode);
        
        Emote emote = Emote.NONE;
        switch (key) {
            case 'P': emote = Emote.POSE;      break;
            case 'Z': emote = Emote.THUMBS_UP; break;
            case 'X': emote = Emote.PEACE;     break;
            case 'C': emote = Emote.CLAP;      break;
            case 'V': emote = Emote.WAVE;      break;
        }
        
        if (emote != Emote.NONE) {
            triggerEmote(emote);
        }
    }
    
    /** Trigger a specific emote */
    public void triggerEmote(Emote emote) {
        if (player == null || !player.isAlive()) return;
        
        // Don't interrupt during combat or critical states
        if (!canPlayEmote()) return;
        
        currentEmote = emote;
        emoteTimer = 0f;
        player.setState(emote.animationState);
    }
    
    /** Cancel the current emote and return to idle */
    public void cancelEmote() {
        currentEmote = Emote.NONE;
        emoteTimer = 0f;
    }
    
    // ===== STATE CHECKS =====
    /** Return true if an emote is currently active */
    public boolean hasActiveEmote() {
        return currentEmote != Emote.NONE;
    }
    
    /** Return true if the player can play an emote */
    private boolean canPlayEmote() {
        if (player == null) return false;
        
        // Check internal player state via reflection or just allow during common states
        // For now, we'll create a public getter in PlayerBase
        return true;  // Allow emotes during any state
    }
    
    // ===== GETTERS =====
    public Emote getCurrentEmote() { return currentEmote; }
    public String getCurrentEmoteName() { return currentEmote.displayName; }
    public float getEmoteTimer() { return emoteTimer; }
    public float getEmoteDuration() { return currentEmote.durationSeconds; }
    public float getEmoteProgress() { 
        if (currentEmote == Emote.NONE) return 0f;
        return Math.min(1f, emoteTimer / currentEmote.durationSeconds);
    }
    
    // ===== DEBUG =====
    public String getEmoteDebugInfo() {
        if (currentEmote == Emote.NONE) return "No emote active";
        return String.format("%s (%.1f/%.1f sec)", 
            currentEmote.displayName, 
            emoteTimer, 
            currentEmote.durationSeconds);
    }
}
