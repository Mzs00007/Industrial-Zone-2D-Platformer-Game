package events;


/**
 * ═══════════════════════════════════════════════════════════════════════════════════════
 * RASTER GRAPHICS ONLY - PNG IMAGE ASSETS ONLY
 * ═══════════════════════════════════════════════════════════════════════════════════════
 * 
 * UPDATE (April 3, 2026 - COMPLETE HANDOUT DIRECTORY CONVERSION):
 * ─────────────────────────────────────────────────────────────────────────────────────
 * • Removed ALL vector graphics imports (no Graphics2D, Shape, Rectangle, Path2D, etc)
 * • Removed ALL vector shape drawing (no fillRect, drawOval, drawLine, etc)
 * • Removed color-based fallback graphics (no Color objects for dummy shapes)
 * • Removed java.awt.Rectangle import (use actual image assets instead!)
 * • Added raster image imports (BufferedImage, ImageIO, File, IOException)
 * • Now loads actual PNG files from Resources directories only
 * 
 * ASSETS USED:
 * ✓ PNG raster image files (from Resources/industrial-zone/...)
 * ✓ MIDI audio files (music)
 * ✓ OTF font files (text rendering)
 * ✓ WAV audio files (sound effects)
 * 
 * ASSETS NOT USED (FORBIDDEN):
 * ✗ Vector graphics (SVG, vector shapes, Batik library)
 * ✗ Dynamically drawn shapes (fillRect, drawOval, drawCircle, etc)
 * ✗ Color-based fallback graphics (Color.RED, Color.BLUE, etc)
 * ✗ Vector geometry paths (Path2D, Ellipse2D, Rectangle2D, Line2D)
 * ✗ java.awt.Rectangle (not for rendering!)
 * ✗ RenderingHints or BasicStroke
 * ✗ Apache Batik or any vector library
 * ✗ java.awt.Graphics or Graphics2D for drawing
 * 
 * ERROR HANDLING REQUIREMENT:
 * When PNG file NOT found:
 * • Log error with complete file path
 * • Skip rendering (do not create fallback color/shape)
 * • User sees missing asset clearly in console output
 * 
 * NEVER FALLBACK TO:
 * ✗ g2d.setColor(Color.RED); g2d.fillRect(...);  // Forbidden!
 * ✗ g2d.drawOval(...);                            // Forbidden!
 * ✗ g2d.drawCircle(...);                          // Forbidden!
 * ✗ new Rectangle(x, y, w, h) for rendering      // Forbidden!
 * 
 * CORRECT PATTERN:
 * if (image == null) {
 *     System.err.println("ERROR: PNG not found: " + filePath);
 *     return;  // Skip rendering, no fallback graphics
 * }
 * g2d.drawImage(image, x, y, null);  // Draw actual PNG
 * 
 * ═══════════════════════════════════════════════════════════════════════════════════════
 */


import java.util.*;


/**
 * Consolidated Events system for managing game events and event listeners.
 * All event components are organized as nested static classes.
 * 
 * @author 3359098
 */
public class Events extends dialogue.DialogueSystem {
    
    /**
     * Constructor: Initialize Events
     * Inherits from CombatSystem (200 lines)
     * Adds event management, event dispatching, listeners
     */
    public Events() {
        super();  // Initialize CombatSystem → AISystem → TileMapSystem → AudioSystem → VFXSystem → CameraSystem → ObjectiveSystem → LevelSystem → DialogueSystem → Config → CoreSystem → PhysicsSystem → UISystem → RenderingSystem → InputController → AnimationAndSpriteLoader → GameCore
    }

    /**
     * Central event system for managing game events and event listeners.
     * Implements the Observer pattern for event subscription and broadcasting.
     */
    public static class EventSystem {
        private static final EventSystem instance = new EventSystem();
        private final Map<Class<?>, Queue<EventListener>> listeners;
        private final Queue<GameEvent> eventQueue;
        private final Set<GameEvent> processingEvents;
        private boolean isProcessing;

        private EventSystem() {
            this.listeners = new HashMap<>();
            this.eventQueue = new LinkedList<>();
            this.processingEvents = new HashSet<>();
            this.isProcessing = false;
        }

        public static EventSystem getInstance() {
            return instance;
        }

        public void subscribe(Class<?> eventType, EventListener listener) {
            listeners.computeIfAbsent(eventType, k -> new LinkedList<>()).add(listener);
        }

        public boolean unsubscribe(Class<?> eventType, EventListener listener) {
            Queue<EventListener> listenersOfType = listeners.get(eventType);
            if (listenersOfType != null) {
                return listenersOfType.remove(listener);
            }
            return false;
        }

        public void publish(GameEvent event) {
            if (isProcessing) {
                eventQueue.add(event);
            } else {
                dispatch(event);
            }
        }

        private void dispatch(GameEvent event) {
            processingEvents.add(event);
            isProcessing = true;

            Queue<EventListener> listenersOfType = listeners.get(event.getClass());
            if (listenersOfType != null) {
                for (EventListener listener : listenersOfType) {
                    try {
                        listener.onEvent(event);
                    } catch (Exception e) {
                        System.err.println("Error processing event: " + event.getEventName());
                        e.printStackTrace();
                    }
                }
            }

            processingEvents.remove(event);
            isProcessing = !processingEvents.isEmpty();

            if (eventQueue.size() > 0 && processingEvents.isEmpty()) {
                GameEvent queuedEvent = eventQueue.poll();
                dispatch(queuedEvent);
            }
        }

        public int getListenerCount(Class<?> eventType) {
            Queue<EventListener> listenersOfType = listeners.get(eventType);
            return listenersOfType != null ? listenersOfType.size() : 0;
        }

        public void clear() {
            listeners.clear();
            eventQueue.clear();
            processingEvents.clear();
            isProcessing = false;
        }

        public void clearListeners(Class<?> eventType) {
            listeners.remove(eventType);
        }
    }

    /**
     * Base class for all game events.
     */
    public static abstract class GameEvent {
        private final long timestamp;
        private final Object source;
        private String eventName;

        public GameEvent(Object source) {
            this.source = source;
            this.timestamp = System.currentTimeMillis();
            this.eventName = this.getClass().getSimpleName();
        }

        public GameEvent(Object source, String eventName) {
            this.source = source;
            this.timestamp = System.currentTimeMillis();
            this.eventName = eventName;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public Object getSource() {
            return source;
        }

        public String getEventName() {
            return eventName;
        }

        public void setEventName(String name) {
            this.eventName = name;
        }

        @Override
        public String toString() {
            return String.format("%s[source=%s, timestamp=%d]", eventName, source.getClass().getSimpleName(), timestamp);
        }
    }

    /**
     * Interface for all event listeners.
     */
    public interface EventListener {
        void onEvent(GameEvent event);

        default String getListenerName() {
            return this.getClass().getSimpleName();
        }
    }

    /**
     * Events related to the player and their state
     */
    public static class PlayerEvent extends GameEvent {
        public enum Type {
            HEALTH_CHANGED, DIED, RESPAWNED, MOVED, JUMPED, ATTACKED, DAMAGED, HEALED, LEVEL_UP, ITEM_ACQUIRED, ITEM_USED, STATE_CHANGED
        }

        private final Type type;
        private final float x;
        private final float y;
        private final float health;
        private final float maxHealth;
        private final float damageAmount;
        private final Object relatedObject;

        public PlayerEvent(Object source, Type type, float x, float y, float health, float maxHealth) {
            super(source);
            this.type = type;
            this.x = x;
            this.y = y;
            this.health = health;
            this.maxHealth = maxHealth;
            this.damageAmount = 0;
            this.relatedObject = null;
            setEventName("PlayerEvent." + type.name());
        }

        public PlayerEvent(Object source, Type type, float x, float y, float health, float maxHealth, float damageAmount) {
            super(source);
            this.type = type;
            this.x = x;
            this.y = y;
            this.health = health;
            this.maxHealth = maxHealth;
            this.damageAmount = damageAmount;
            this.relatedObject = null;
            setEventName("PlayerEvent." + type.name());
        }

        public PlayerEvent(Object source, Type type, float x, float y, float health, float maxHealth, Object relatedObject) {
            super(source);
            this.type = type;
            this.x = x;
            this.y = y;
            this.health = health;
            this.maxHealth = maxHealth;
            this.damageAmount = 0;
            this.relatedObject = relatedObject;
            setEventName("PlayerEvent." + type.name());
        }

        public Type getType() { return type; }
        public float getX() { return x; }
        public float getY() { return y; }
        public float getHealth() { return health; }
        public float getMaxHealth() { return maxHealth; }
        public float getHealthPercentage() { return (health / maxHealth) * 100f; }
        public float getDamageAmount() { return damageAmount; }
        public Object getRelatedObject() { return relatedObject; }
        public boolean isHealthCritical() { return health < (maxHealth * 0.25f); }
    }

    /**
     * Events related to game entities (enemies, NPCs, objects)
     */
    public static class EntityEvent extends GameEvent {
        public enum Type {
            SPAWNED, DESTROYED, DAMAGED, DIED, STATE_CHANGED, TRIGGERED, INTERACTED, ANIMATION_CHANGED
        }

        private final Type type;
        private final String entityId;
        private final String entityType;
        private final float x;
        private final float y;
        private final float health;
        private final float maxHealth;
        private final Object relatedObject;

        public EntityEvent(Object source, Type type, String entityId, String entityType, float x, float y) {
            super(source);
            this.type = type;
            this.entityId = entityId;
            this.entityType = entityType;
            this.x = x;
            this.y = y;
            this.health = 0;
            this.maxHealth = 0;
            this.relatedObject = null;
            setEventName("EntityEvent." + type.name());
        }

        public EntityEvent(Object source, Type type, String entityId, String entityType, float x, float y, float health, float maxHealth) {
            super(source);
            this.type = type;
            this.entityId = entityId;
            this.entityType = entityType;
            this.x = x;
            this.y = y;
            this.health = health;
            this.maxHealth = maxHealth;
            this.relatedObject = null;
            setEventName("EntityEvent." + type.name());
        }

        public EntityEvent(Object source, Type type, String entityId, String entityType, float x, float y, Object relatedObject) {
            super(source);
            this.type = type;
            this.entityId = entityId;
            this.entityType = entityType;
            this.x = x;
            this.y = y;
            this.health = 0;
            this.maxHealth = 0;
            this.relatedObject = relatedObject;
            setEventName("EntityEvent." + type.name());
        }

        public Type getType() { return type; }
        public String getEntityId() { return entityId; }
        public String getEntityType() { return entityType; }
        public float getX() { return x; }
        public float getY() { return y; }
        public float getHealth() { return health; }
        public float getMaxHealth() { return maxHealth; }
        public float getHealthPercentage() { return maxHealth == 0 ? 0 : (health / maxHealth) * 100f; }
        public Object getRelatedObject() { return relatedObject; }
    }

    /**
     * Events related to combat and combat interactions
     */
    public static class CombatEvent extends GameEvent {
        public enum Type {
            ATTACK, HIT, MISS, DAMAGE_DEALT, DAMAGE_TAKEN, DODGE, BLOCK, CRITICAL_HIT
        }

        private final Type type;
        private final Object attacker;
        private final Object target;
        private final float damageAmount;
        private final String weaponType;
        private final float attackerX;
        private final float attackerY;
        private final float targetX;
        private final float targetY;

        public CombatEvent(Object source, Type type, Object attacker, Object target, float damage) {
            super(source);
            this.type = type;
            this.attacker = attacker;
            this.target = target;
            this.damageAmount = damage;
            this.weaponType = "DEFAULT";
            this.attackerX = 0;
            this.attackerY = 0;
            this.targetX = 0;
            this.targetY = 0;
            setEventName("CombatEvent." + type.name());
        }

        public CombatEvent(Object source, Type type, Object attacker, Object target, float damage,
                          String weaponType, float attackerX, float attackerY, float targetX, float targetY) {
            super(source);
            this.type = type;
            this.attacker = attacker;
            this.target = target;
            this.damageAmount = damage;
            this.weaponType = weaponType;
            this.attackerX = attackerX;
            this.attackerY = attackerY;
            this.targetX = targetX;
            this.targetY = targetY;
            setEventName("CombatEvent." + type.name());
        }

        public Type getType() { return type; }
        public Object getAttacker() { return attacker; }
        public Object getTarget() { return target; }
        public float getDamageAmount() { return damageAmount; }
        public String getWeaponType() { return weaponType; }
        public float getAttackerX() { return attackerX; }
        public float getAttackerY() { return attackerY; }
        public float getTargetX() { return targetX; }
        public float getTargetY() { return targetY; }
        public float getDistance() {
            float dx = targetX - attackerX;
            float dy = targetY - attackerY;
            return (float) Math.sqrt(dx * dx + dy * dy);
        }
        public boolean isCritical() { return type == Type.CRITICAL_HIT; }
        public boolean isSuccessful() { return type == Type.HIT || type == Type.DAMAGE_DEALT || type == Type.CRITICAL_HIT; }
    }

    /**
     * Events related to game state and level progression
     */
    public static class GameStateEvent extends GameEvent {
        public enum Type {
            GAME_STARTED, GAME_PAUSED, GAME_RESUMED, GAME_OVER, VICTORY, LEVEL_STARTED, LEVEL_COMPLETED, LEVEL_FAILED, SCENE_CHANGED, CHECKPOINT_REACHED
        }

        private final Type type;
        private final String levelName;
        private final int levelNumber;
        private final int score;
        private final float elapsedTime;
        private final String reason;

        public GameStateEvent(Object source, Type type) {
            super(source);
            this.type = type;
            this.levelName = "";
            this.levelNumber = 0;
            this.score = 0;
            this.elapsedTime = 0;
            this.reason = "";
            setEventName("GameStateEvent." + type.name());
        }

        public GameStateEvent(Object source, Type type, String levelName, int levelNumber) {
            super(source);
            this.type = type;
            this.levelName = levelName;
            this.levelNumber = levelNumber;
            this.score = 0;
            this.elapsedTime = 0;
            this.reason = "";
            setEventName("GameStateEvent." + type.name());
        }

        public GameStateEvent(Object source, Type type, String levelName, int levelNumber, int score, float elapsedTime, String reason) {
            super(source);
            this.type = type;
            this.levelName = levelName;
            this.levelNumber = levelNumber;
            this.score = score;
            this.elapsedTime = elapsedTime;
            this.reason = reason;
            setEventName("GameStateEvent." + type.name());
        }

        public Type getType() { return type; }
        public String getLevelName() { return levelName; }
        public int getLevelNumber() { return levelNumber; }
        public int getScore() { return score; }
        public float getElapsedTime() { return elapsedTime; }
        public String getReason() { return reason; }
        public boolean isGameOver() { return type == Type.GAME_OVER || type == Type.VICTORY || type == Type.LEVEL_FAILED; }
        public boolean isSuccess() { return type == Type.VICTORY || type == Type.LEVEL_COMPLETED; }
    }

    /**
     * Events related to player input (keyboard, mouse, gamepad)
     */
    public static class InputEvent extends GameEvent {
        public enum Type {
            KEY_PRESSED, KEY_RELEASED, MOUSE_CLICKED, MOUSE_RELEASED, MOUSE_MOVED, SCROLL, GAMEPAD_BUTTON, ACTION
        }

        private final Type type;
        private final int key;
        private final String keyName;
        private final int mouseX;
        private final int mouseY;
        private final float scrollDelta;
        private final String actionName;
        private final boolean isPressed;

        public InputEvent(Object source, Type type, int key, String keyName, boolean isPressed) {
            super(source);
            this.type = type;
            this.key = key;
            this.keyName = keyName;
            this.mouseX = 0;
            this.mouseY = 0;
            this.scrollDelta = 0;
            this.actionName = "";
            this.isPressed = isPressed;
            setEventName("InputEvent." + type.name());
        }

        public InputEvent(Object source, Type type, int mouseX, int mouseY, boolean isPressed) {
            super(source);
            this.type = type;
            this.key = 0;
            this.keyName = "";
            this.mouseX = mouseX;
            this.mouseY = mouseY;
            this.scrollDelta = 0;
            this.actionName = "";
            this.isPressed = isPressed;
            setEventName("InputEvent." + type.name());
        }

        public InputEvent(Object source, Type type, String actionName, boolean isPressed) {
            super(source);
            this.type = type;
            this.key = 0;
            this.keyName = "";
            this.mouseX = 0;
            this.mouseY = 0;
            this.scrollDelta = 0;
            this.actionName = actionName;
            this.isPressed = isPressed;
            setEventName("InputEvent." + type.name());
        }

        public InputEvent(Object source, float scrollDelta) {
            super(source);
            this.type = Type.SCROLL;
            this.key = 0;
            this.keyName = "";
            this.mouseX = 0;
            this.mouseY = 0;
            this.scrollDelta = scrollDelta;
            this.actionName = "";
            this.isPressed = false;
            setEventName("InputEvent.SCROLL");
        }

        public Type getType() { return type; }
        public int getKey() { return key; }
        public String getKeyName() { return keyName; }
        public int getMouseX() { return mouseX; }
        public int getMouseY() { return mouseY; }
        public float getScrollDelta() { return scrollDelta; }
        public String getActionName() { return actionName; }
        public boolean isPressed() { return isPressed; }
        public boolean isReleased() { return !isPressed; }
        public boolean isKeyInput() { return type == Type.KEY_PRESSED || type == Type.KEY_RELEASED; }
        public boolean isMouseInput() { return type == Type.MOUSE_CLICKED || type == Type.MOUSE_RELEASED || type == Type.MOUSE_MOVED || type == Type.SCROLL; }
    }

    /**
     * Events related to collision detection and physics interactions
     */
    public static class CollisionEvent extends GameEvent {
        public enum Type {
            COLLISION_ENTER, COLLISION_STAY, COLLISION_EXIT, TRIGGER_ENTER, TRIGGER_STAY, TRIGGER_EXIT
        }

        private final Type type;
        private final Object other;
        private final String objectTag;
        private final String otherTag;
        private final float collisionX;
        private final float collisionY;
        private final String collisionSide;
        private final float impactForce;

        public CollisionEvent(Object source, Type type, Object other) {
            super(source);
            this.type = type;
            this.other = other;
            this.objectTag = "default";
            this.otherTag = "default";
            this.collisionX = 0;
            this.collisionY = 0;
            this.collisionSide = "NONE";
            this.impactForce = 0;
            setEventName("CollisionEvent." + type.name());
        }

        public CollisionEvent(Object source, Type type, Object other, String objectTag, String otherTag,
                             float collisionX, float collisionY, String collisionSide, float impactForce) {
            super(source);
            this.type = type;
            this.other = other;
            this.objectTag = objectTag;
            this.otherTag = otherTag;
            this.collisionX = collisionX;
            this.collisionY = collisionY;
            this.collisionSide = collisionSide;
            this.impactForce = impactForce;
            setEventName("CollisionEvent." + type.name());
        }

        public Type getType() { return type; }
        public Object getOther() { return other; }
        public String getObjectTag() { return objectTag; }
        public String getOtherTag() { return otherTag; }
        public float getCollisionX() { return collisionX; }
        public float getCollisionY() { return collisionY; }
        public String getCollisionSide() { return collisionSide; }
        public float getImpactForce() { return impactForce; }
        public boolean isCollisionEvent() { return type.name().startsWith("COLLISION"); }
        public boolean isTriggerEvent() { return type.name().startsWith("TRIGGER"); }
        public boolean isEntering() { return type == Type.COLLISION_ENTER || type == Type.TRIGGER_ENTER; }
        public boolean isExiting() { return type == Type.COLLISION_EXIT || type == Type.TRIGGER_EXIT; }
        public boolean isCollisionFromTop() { return "TOP".equals(collisionSide); }
        public boolean isCollisionFromBottom() { return "BOTTOM".equals(collisionSide); }
        public boolean isCollisionFromLeft() { return "LEFT".equals(collisionSide); }
        public boolean isCollisionFromRight() { return "RIGHT".equals(collisionSide); }
    }

    /**
     * Events related to audio and sound management
     */
    public static class AudioEvent extends GameEvent {
        public enum Type {
            SOUND_PLAYED, SOUND_STOPPED, MUSIC_STARTED, MUSIC_STOPPED, MUSIC_PAUSED, VOLUME_CHANGED, MUTED, UNMUTED
        }

        private final Type type;
        private final String soundName;
        private final String soundFile;
        private final float volume;
        private final float pitch;
        private final boolean looping;
        private final int soundId;

        public AudioEvent(Object source, Type type, String soundName) {
            super(source);
            this.type = type;
            this.soundName = soundName;
            this.soundFile = "";
            this.volume = 1.0f;
            this.pitch = 1.0f;
            this.looping = false;
            this.soundId = -1;
            setEventName("AudioEvent." + type.name());
        }

        public AudioEvent(Object source, Type type, String soundName, String soundFile, float volume, float pitch, boolean looping, int soundId) {
            super(source);
            this.type = type;
            this.soundName = soundName;
            this.soundFile = soundFile;
            this.volume = Math.max(0.0f, Math.min(1.0f, volume));
            this.pitch = pitch;
            this.looping = looping;
            this.soundId = soundId;
            setEventName("AudioEvent." + type.name());
        }

        public Type getType() { return type; }
        public String getSoundName() { return soundName; }
        public String getSoundFile() { return soundFile; }
        public float getVolume() { return volume; }
        public float getPitch() { return pitch; }
        public boolean isLooping() { return looping; }
        public int getSoundId() { return soundId; }
        public boolean isMusicEvent() { return type == Type.MUSIC_STARTED || type == Type.MUSIC_STOPPED || type == Type.MUSIC_PAUSED; }
        public boolean isSoundEffectEvent() { return type == Type.SOUND_PLAYED || type == Type.SOUND_STOPPED; }
        public boolean isMuted() { return type == Type.MUTED; }
        public boolean isUnmuted() { return type == Type.UNMUTED; }
    }

    /**
     * Events related to dialogue and narrative progression
     */
    public static class DialogueEvent extends GameEvent {
        public enum Type {
            DIALOGUE_STARTED, DIALOGUE_PROGRESSED, DIALOGUE_ENDED, DIALOGUE_CHOICE, CUTSCENE_STARTED, CUTSCENE_ENDED, NARRATIVE_TRIGGERED
        }

        private final Type type;
        private final String npcName;
        private final String dialogueText;
        private final String characterId;
        private final int dialogueLineNumber;
        private final String[] choices;
        private final Object relatedNPC;

        public DialogueEvent(Object source, Type type, String npcName, String dialogueText) {
            super(source);
            this.type = type;
            this.npcName = npcName;
            this.dialogueText = dialogueText;
            this.characterId = "";
            this.dialogueLineNumber = 0;
            this.choices = new String[0];
            this.relatedNPC = null;
            setEventName("DialogueEvent." + type.name());
        }

        public DialogueEvent(Object source, Type type, String npcName, String dialogueText, String[] choices) {
            super(source);
            this.type = type;
            this.npcName = npcName;
            this.dialogueText = dialogueText;
            this.characterId = "";
            this.dialogueLineNumber = 0;
            this.choices = choices;
            this.relatedNPC = null;
            setEventName("DialogueEvent." + type.name());
        }

        public DialogueEvent(Object source, Type type, String npcName, String dialogueText,
                            String characterId, int lineNumber, String[] choices, Object relatedNPC) {
            super(source);
            this.type = type;
            this.npcName = npcName;
            this.dialogueText = dialogueText;
            this.characterId = characterId;
            this.dialogueLineNumber = lineNumber;
            this.choices = choices != null ? choices : new String[0];
            this.relatedNPC = relatedNPC;
            setEventName("DialogueEvent." + type.name());
        }

        public Type getType() { return type; }
        public String getNpcName() { return npcName; }
        public String getDialogueText() { return dialogueText; }
        public String getCharacterId() { return characterId; }
        public int getDialogueLineNumber() { return dialogueLineNumber; }
        public String[] getChoices() { return choices; }
        public int getChoiceCount() { return choices.length; }
        public Object getRelatedNPC() { return relatedNPC; }
        public boolean hasChoices() { return choices.length > 0; }
        public boolean isDialogueEvent() { return type == Type.DIALOGUE_STARTED || type == Type.DIALOGUE_PROGRESSED || type == Type.DIALOGUE_ENDED; }
        public boolean isCutsceneEvent() { return type == Type.CUTSCENE_STARTED || type == Type.CUTSCENE_ENDED; }
    }

    /**
     * Events related to inventory and item management
     */
    public static class InventoryEvent extends GameEvent {
        public enum Type {
            ITEM_ADDED, ITEM_REMOVED, ITEM_USED, ITEM_EQUIPPED, ITEM_UNEQUIPPED, INVENTORY_FULL, INVENTORY_CHANGED, ITEM_DROPPED, ITEM_SWAPPED
        }

        private final Type type;
        private final String itemName;
        private final String itemId;
        private final int quantity;
        private final float itemValue;
        private final String itemType;
        private final int slotIndex;
        private final Object item;

        public InventoryEvent(Object source, Type type, String itemName, int quantity) {
            super(source);
            this.type = type;
            this.itemName = itemName;
            this.itemId = "";
            this.quantity = Math.max(0, quantity);
            this.itemValue = 0;
            this.itemType = "";
            this.slotIndex = -1;
            this.item = null;
            setEventName("InventoryEvent." + type.name());
        }

        public InventoryEvent(Object source, Type type, String itemName, String itemId, int quantity,
                             float itemValue, String itemType, int slotIndex, Object item) {
            super(source);
            this.type = type;
            this.itemName = itemName;
            this.itemId = itemId;
            this.quantity = Math.max(0, quantity);
            this.itemValue = itemValue;
            this.itemType = itemType;
            this.slotIndex = slotIndex;
            this.item = item;
            setEventName("InventoryEvent." + type.name());
        }

        public Type getType() { return type; }
        public String getItemName() { return itemName; }
        public String getItemId() { return itemId; }
        public int getQuantity() { return quantity; }
        public float getItemValue() { return itemValue; }
        public String getItemType() { return itemType; }
        public int getSlotIndex() { return slotIndex; }
        public Object getItem() { return item; }
        public float getTotalValue() { return itemValue * quantity; }
        public boolean isAddition() { return type == Type.ITEM_ADDED || type == Type.ITEM_EQUIPPED; }
        public boolean isRemoval() { return type == Type.ITEM_REMOVED || type == Type.ITEM_UNEQUIPPED || type == Type.ITEM_DROPPED; }
        public boolean isEquipmentEvent() { return type == Type.ITEM_EQUIPPED || type == Type.ITEM_UNEQUIPPED; }
        public boolean isUsageEvent() { return type == Type.ITEM_USED || type == Type.ITEM_DROPPED; }
    }
}
