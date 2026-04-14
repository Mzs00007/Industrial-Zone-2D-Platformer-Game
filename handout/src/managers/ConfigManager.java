package managers;

import managers.Config;
import java.util.Properties;

/**
 * ConfigManager - Consolidated configuration system
 * 
 * Merges:
 * - config/Config.java
 * 
 * Manages game configuration, settings, and properties.
 */
public class ConfigManager {
    private Config config;
    private boolean isInitialized = false;
    
    /**
     * Constructor - instantiable manager (no static getInstance)
     */
    public ConfigManager() {
        this.config = new Config();
    }
    
    /**
     * Initialize configuration system
     */
    public void initialize() {
        if (isInitialized) return;
        try {
            System.out.println("[ConfigManager] Initializing configuration system...");
            config.loadConfig();
            isInitialized = true;
            System.out.println("[ConfigManager] ✓ Configuration system initialized");
        } catch (Exception e) {
            System.err.println("[ConfigManager ERROR] " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Get configuration value by key
     */
    public String getProperty(String key) {
        if (config != null) {
            return config.getProperty(key);
        }
        return null;
    }
    
    /**
     * Get configuration value with default
     */
    public String getProperty(String key, String defaultValue) {
        if (config != null) {
            String value = config.getProperty(key);
            return value != null ? value : defaultValue;
        }
        return defaultValue;
    }
    
    /**
     * Get integer property
     */
    public int getIntProperty(String key, int defaultValue) {
        try {
            String value = getProperty(key);
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    /**
     * Get boolean property
     */
    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }
    
    /**
     * Get float property
     */
    public float getFloatProperty(String key, float defaultValue) {
        try {
            String value = getProperty(key);
            return value != null ? Float.parseFloat(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    /**
     * Set property
     */
    public void setProperty(String key, String value) {
        if (config != null) {
            config.setProperty(key, value);
        }
    }
    
    /**
     * Save configuration
     */
    public void saveConfig() {
        if (config != null) {
            config.saveConfig();
        }
    }
    
    /**
     * Reload configuration
     */
    public void reloadConfig() {
        if (config != null) {
            config.loadConfig();
        }
    }
    
    /**
     * Shutdown configuration system
     */
    public void shutdown() {
        if (config != null) {
            config.saveConfig();
        }
        isInitialized = false;
    }
}
