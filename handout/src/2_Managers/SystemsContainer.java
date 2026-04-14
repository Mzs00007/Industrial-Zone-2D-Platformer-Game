package managers;

import java.util.HashMap;
import java.util.Map;

public class SystemsContainer {
    
    // ════════════════════════════════════════════════════════════════════════════
    // SYSTEM INSTANCES - Stored as Object to avoid circular imports
    // ════════════════════════════════════════════════════════════════════════════
    
    private Map<String, Object> systems = new HashMap<>();
    private boolean initialized = false;
    private static final String TAG = "[SystemsContainer]";
    
    // ════════════════════════════════════════════════════════════════════════════
    // INITIALIZATION
    // ════════════════════════════════════════════════════════════════════════════
    
    /**
     * Main initialization method: creates and initializes all systems
     * in proper dependency order using reflection
     */
    public void initialize() {
        if (initialized) {
            System.out.println(TAG + " Systems already initialized, skipping...");
            return;
        }
        
        System.out.println(TAG + " Initializing game systems...");
        
        try {
            // PHASE 1: Core foundation systems (using reflection to avoid circular imports)
            initializeSystemByClassName("animation.AnimationAndSpriteLoader", "animationAndSpriteLoader");
            initializeSystemByClassName("animation.InputController", "inputController");
            initializeSystemByClassName("core.CoreSystem", "coreSystem");
            
            // PHASE 2: Rendering pipeline
            initializeSystemByClassName("physics.PhysicsSystem", "physicsSystem");
            initializeSystemByClassName("rendering.RenderingSystem", "renderingSystem");
            initializeSystemByClassName("ui.UISystem", "uiSystem");
            
            // PHASE 3: World systems
            initializeSystemByClassName("camera.CameraSystem", "cameraSystem");
            initializeSystemByClassName("tiles.TileMapSystem", "tileMapSystem");
            initializeSystemByClassName("ai.AISystem", "aiSystem");
            
            // PHASE 4: Game logic systems
            initializeSystemByClassName("combat.CombatSystem", "combatSystem");
            initializeSystemByClassName("dialogue.DialogueSystem", "dialogueSystem");
            initializeSystemByClassName("objectives.ObjectiveSystem", "objectiveSystem");
            
            // PHASE 5: Content systems
            initializeSystemByClassName("audio.AudioSystem", "audioSystem");
            initializeSystemByClassName("vfx.VFXSystem", "vfxSystem");
            initializeSystemByClassName("levels.LevelSystem", "levelSystem");
            initializeSystemByClassName("config.Config", "config");
            initializeSystemByClassName("utils.UtilsSystem", "utilsSystem");
            
            initialized = true;
            System.out.println(TAG + " All systems initialized successfully!");
            
        } catch (Exception e) {
            System.err.println(TAG + " ERROR during initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Initialize a single system using reflection (avoids circular imports)
     */
    private void initializeSystemByClassName(String className, String systemName) {
        try {
            Class<?> systemClass = Class.forName(className);
            Object instance = systemClass.getDeclaredConstructor().newInstance();
            systems.put(systemName, instance);
            System.out.println(TAG + " ✓ " + systemName + " initialized");
        } catch (ClassNotFoundException e) {
            System.err.println(TAG + " ✗ Class not found: " + className);
        } catch (Exception e) {
            System.err.println(TAG + " ✗ Failed to initialize " + systemName + ": " + e.getMessage());
        }
    }
    
    // ════════════════════════════════════════════════════════════════════════════
    // GETTER METHODS - Access to all systems (generic Object access)
    // ════════════════════════════════════════════════════════════════════════════
    
    /**
     * Get system by name
     * @param systemName The name of the system (e.g., "renderingSystem", "aiSystem")
     * @return The system object or null if not found
     */
    public Object getSystem(String systemName) {
        return systems.get(systemName);
    }
    
    /**
     * Get system by name with type casting
     * @param systemName The name of the system
     * @param type The expected type
     * @return The system object cast to the specified type
     */
    public <T> T getSystem(String systemName, Class<T> type) {
        Object system = systems.get(systemName);
        if (system != null && type.isAssignableFrom(system.getClass())) {
            return (T) system;
        }
        return null;
    }
    
    // ════════════════════════════════════════════════════════════════════════════
    // UTILITY METHODS
    // ════════════════════════════════════════════════════════════════════════════
    
    /**
     * Check if all systems are initialized
     */
    public boolean isInitialized() {
        return initialized;
    }
    
    /**
     * Shutdown all systems gracefully
     */
    public void shutdown() {
        System.out.println(TAG + " Shutting down all systems...");
        initialized = false;
    }
    
    /**
     * Get all systems as a map (for iteration, debugging)
     */
    public Map<String, Object> getAllSystems() {
        return new HashMap<>(systems);
    }
    
    /**
     * Debug: Print initialization status of all systems
     */
    public void printStatus() {
        System.out.println("\n" + TAG + " SYSTEM STATUS:");
        System.out.println("  Initialized: " + initialized);
        System.out.println("  Total systems: " + systems.size());
        for (String systemName : systems.keySet()) {
            System.out.println("  " + systemName + ": " + (systems.get(systemName) != null ? "✓" : "✗"));
        }
    }
}
