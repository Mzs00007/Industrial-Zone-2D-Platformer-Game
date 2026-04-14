package managers;

import events.Events;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EventManager - Consolidated event system
 * 
 * Merges:
 * - events/Events.java
 * 
 * Manages game events, event listeners, and event broadcasting.
 */
public class EventManager {
    private Events.EventSystem eventSystem;
    private boolean isInitialized = false;
    
    /**
     * Constructor - instantiable manager (no static getInstance)
     */
    public EventManager() {
        this.eventSystem = new Events.EventSystem();
    }
    
    /**
     * Initialize event system
     */
    public void initialize() {
        if (isInitialized) return;
        try {
            System.out.println("[EventManager] Initializing event system...");
            eventSystem.initialize();
            isInitialized = true;
            System.out.println("[EventManager] ✓ Event system initialized");
        } catch (Exception e) {
            System.err.println("[EventManager ERROR] " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Subscribe to an event
     */
    public void subscribe(String eventType, Events.EventListener listener) {
        if (eventSystem != null) {
            eventSystem.subscribe(eventType, listener);
        }
    }
    
    /**
     * Unsubscribe from an event
     */
    public void unsubscribe(String eventType, Events.EventListener listener) {
        if (eventSystem != null) {
            eventSystem.unsubscribe(eventType, listener);
        }
    }
    
    /**
     * Publish an event
     */
    public void publish(String eventType, Events.GameEvent event) {
        if (eventSystem != null) {
            eventSystem.publish(eventType, event);
        }
    }
    
    /**
     * Publish an event without data
     */
    public void publish(String eventType) {
        if (eventSystem != null) {
            eventSystem.publish(eventType, null);
        }
    }
    
    /**
     * Update event system
     */
    public void update(long deltaMillis) {
        if (!isInitialized) return;
        eventSystem.update(deltaMillis);
    }
    
    /**
     * Clear all listeners
     */
    public void clearAllListeners() {
        if (eventSystem != null) {
            eventSystem.clearAllListeners();
        }
    }
    
    /**
     * Shutdown event system
     */
    public void shutdown() {
        if (eventSystem != null) {
            eventSystem.shutdown();
        }
        isInitialized = false;
    }
}
