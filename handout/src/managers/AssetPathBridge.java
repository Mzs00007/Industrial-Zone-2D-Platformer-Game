package managers;

import managers.AssetRegistry;

/**
 * ════════════════════════════════════════════════════════════════════════════
 * ASSET PATH BRIDGE v1.0
 * 
 * Unified repository for all asset paths extracted from assets-manifest.json
 * Bridges old hardcoded paths in AnimationAndSpriteLoader to consolidated system
 * 
 * Usage: Replace direct String constants with getters that reference metadata
 * ════════════════════════════════════════════════════════════════════════════
 */
public class AssetPathBridge {
    
    // ════════════════════════════════════════════════════════════════════════
    // CHARACTER PATHS (PLAYER + ENEMIES + BOSSES)
    // ════════════════════════════════════════════════════════════════════════
    
    public static class Characters {
        public static final String PLAYER_BASE = "Resources/industrial-zone/characters/player/";
        public static final String BOSS_BASE = "Resources/industrial-zone/characters/bosses/";
        public static final String ENEMY_BASE = "Resources/industrial-zone/characters/enemies/";
        public static final String DRONE_BASE = "Resources/industrial-zone/characters/enemies/drones/";
        public static final String SCIFI_BASE = "Resources/industrial-zone/characters/enemies/sci-fi-antagonists/";
    }
    
    // ════════════════════════════════════════════════════════════════════════
    // LEVEL 1 TILE PATHS (Industrial Zone)
    // ════════════════════════════════════════════════════════════════════════
    
    public static class Level1Tiles {
        public static final String L1_TILES_BASE = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/";
        public static final String L1_BG_BASE = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/";
        public static final String L1_OBJECTS_BASE = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/";
        public static final String L1_ANIMATED_BASE = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/";
    }
    
    // ════════════════════════════════════════════════════════════════════════
    // LEVEL 2 TILE PATHS (Power Station)
    // ════════════════════════════════════════════════════════════════════════
    
    public static class Level2Tiles {
        public static final String L2_TILES_BASE = "Resources/industrial-zone/1 Tiles/power-station-level-2/1 Tiles/";
        public static final String L2_BG_BASE = "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/";
        public static final String L2_BG_DAY = "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/Day/";
        public static final String L2_BG_NIGHT = "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/Night/";
        public static final String L2_OBJECTS_BASE = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/";
        public static final String L2_OBJECTS_TUBE = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/";
        public static final String L2_OBJECTS_DECOR = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/";
        public static final String L2_OBJECTS_LINES = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/3 Power lines/";
        public static final String L2_ANIMATED_BASE = "Resources/industrial-zone/1 Tiles/power-station-level-2/4 Animated objects/";
    }
    
    // ════════════════════════════════════════════════════════════════════════
    // AUDIO PATHS
    // ════════════════════════════════════════════════════════════════════════
    
    public static class Audio {
        public static final String AUDIO_BASE = "Resources/industrial-zone/audio/";
        public static final String AUDIO_MUSIC_MIDI = "Resources/industrial-zone/audio/music_midi/";
        public static final String AUDIO_MUSIC_WAV = "Resources/industrial-zone/audio/music_wav/";
        public static final String AUDIO_SFX = "Resources/industrial-zone/audio/sfx/";
    }
    
    // ════════════════════════════════════════════════════════════════════════
    // GUI PATHS (Frames, Bars, Icons, Buttons, Cursors, Font, etc.)
    // ════════════════════════════════════════════════════════════════════════
    
    public static class GUI {
        public static final String GUI_BASE = "Resources/industrial-zone/gui/";
        public static final String GUI_FRAMES = "Resources/industrial-zone/gui/1 Frames/";
        public static final String GUI_BARS = "Resources/industrial-zone/gui/2 Bars/";
        public static final String GUI_ICONS = "Resources/industrial-zone/gui/3 Icons/";
        public static final String GUI_ICONS_BUTTONS = "Resources/industrial-zone/gui/3 Icons/Buttons2/";
        public static final String GUI_ICONS_ICONS = "Resources/industrial-zone/gui/3 Icons/Icons/";
        public static final String GUI_PALETTE = "Resources/industrial-zone/gui/4 Palette/";
        public static final String GUI_LOGO = "Resources/industrial-zone/gui/5 Logo/";
        public static final String GUI_BUTTONS = "Resources/industrial-zone/gui/6 Buttons/";
        public static final String GUI_NUMBERS = "Resources/industrial-zone/gui/7 Numbers/";
        public static final String GUI_CURSORS = "Resources/industrial-zone/gui/8 Cursors/";
        public static final String GUI_OTHER = "Resources/industrial-zone/gui/9 Other/";
        public static final String GUI_OTHER_DECOR = "Resources/industrial-zone/gui/9 Other/1 Decor/";
        public static final String GUI_OTHER_SKILLS = "Resources/industrial-zone/gui/9 Other/2 Skill icons/";
        public static final String GUI_FONT = "Resources/industrial-zone/gui/10 Font/";
        public static final String GUI_FONT_IMAGES = "Resources/industrial-zone/gui/10 Font/images/";
        public static final String GUI_CARD_ANIM = "Resources/industrial-zone/gui/card-animations/";
    }
    
    // ════════════════════════════════════════════════════════════════════════
    // VFX PATHS (Visual Effects)
    // ════════════════════════════════════════════════════════════════════════
    
    public static class VFX {
        public static final String VFX_BASE = "Resources/industrial-zone/vfx/";
        public static final String VFX_SMOKE = "Resources/industrial-zone/vfx/1 Smoke/";
        public static final String VFX_BLOOD = "Resources/industrial-zone/vfx/2 Blood/";
        public static final String VFX_SPARKS = "Resources/industrial-zone/vfx/3 Sparks/";
        public static final String VFX_PARTICLES = "Resources/industrial-zone/vfx/4 Particles/";
        public static final String VFX_OTHER = "Resources/industrial-zone/vfx/5 Other/";
        public static final String VFX_EXTRA = "Resources/industrial-zone/vfx/6 Extra/";
        public static final String VFX_EXTRA_CHARACTER = "Resources/industrial-zone/vfx/6 Extra/Character/";
        public static final String VFX_EXTRA_OBJECTS = "Resources/industrial-zone/vfx/6 Extra/Objects/";
        public static final String VFX_EXTRA_BOX1 = "Resources/industrial-zone/vfx/6 Extra/Objects/Box1/";
        public static final String VFX_EXTRA_BOX2 = "Resources/industrial-zone/vfx/6 Extra/Objects/Box2/";
        public static final String VFX_EXTRA_BUSH = "Resources/industrial-zone/vfx/6 Extra/Objects/Bush/";
        public static final String VFX_EXTRA_CAPSULE = "Resources/industrial-zone/vfx/6 Extra/Objects/Capsule/";
    }
    
    // ════════════════════════════════════════════════════════════════════════
    // WEAPON PATHS (Organized by Weapon Type and Character)
    // ════════════════════════════════════════════════════════════════════════
    
    public static class Weapons {
        // Weapon Set 1 Base
        public static final String WEAPONS_BASE = "Resources/industrial-zone/weapons/";
        public static final String WEAPON_1 = "Resources/industrial-zone/weapons/1/";
        
        // Weapon 1 - Characters
        public static final String WEAPON_1_CHAR = "Resources/industrial-zone/weapons/1/1 Characters/";
        public static final String WEAPON_1_CHAR_BIKER = "Resources/industrial-zone/weapons/1/1 Characters/1 Biker/";
        public static final String WEAPON_1_CHAR_PUNK = "Resources/industrial-zone/weapons/1/1 Characters/2 Punk/";
        public static final String WEAPON_1_CHAR_CYBER = "Resources/industrial-zone/weapons/1/1 Characters/3 Cyborg/";
        
        // Weapon 1 - Accessories
        public static final String WEAPON_1_GUNS = "Resources/industrial-zone/weapons/1/2 Guns/";
        public static final String WEAPON_1_HANDS = "Resources/industrial-zone/weapons/1/3 Hands/";
        public static final String WEAPON_1_HANDS_BIKER = "Resources/industrial-zone/weapons/1/3 Hands/1 Biker/";
        public static final String WEAPON_1_HANDS_PUNK = "Resources/industrial-zone/weapons/1/3 Hands/2 Punk/";
        public static final String WEAPON_1_HANDS_CYBER = "Resources/industrial-zone/weapons/1/3 Hands/3 Cyborg/";
        public static final String WEAPON_1_EFFECTS = "Resources/industrial-zone/weapons/1/4 Shoot_effects/";
        public static final String WEAPON_1_BULLETS = "Resources/industrial-zone/weapons/1/5 Bullets/";
        
        // Weapon Set 2
        public static final String WEAPON_2 = "Resources/industrial-zone/weapons/2/";
        public static final String WEAPON_2_CHAR = "Resources/industrial-zone/weapons/2/1 Characters/";
        public static final String WEAPON_2_CHAR_BIKER = "Resources/industrial-zone/weapons/2/1 Characters/1 Biker/";
        public static final String WEAPON_2_CHAR_PUNK = "Resources/industrial-zone/weapons/2/1 Characters/2 Punk/";
        public static final String WEAPON_2_CHAR_CYBER = "Resources/industrial-zone/weapons/2/1 Characters/3 Cyborg/";
    }
    
    // ════════════════════════════════════════════════════════════════════════
    // KEYBOARD & MOUSE INPUT KEYS PATHS
    // ════════════════════════════════════════════════════════════════════════
    
    public static class InputKeys {
        public static final String KEYBOARD_KEYS_BASE = "Resources/industrial-zone/keyboard_keys/";
        public static final String MOUSE_KEYS_BASE = "Resources/industrial-zone/mouse_keys/";
    }
    
    // ════════════════════════════════════════════════════════════════════════
    // PRIMARY ACCESS METHODS - Use these instead of hardcoded constants
    // ════════════════════════════════════════════════════════════════════════
    
    /**
     * Get base path for character assets (player, bosses, enemies)
     */
    public static String getCharacterPath(String type) {
        return switch(type.toLowerCase()) {
            case "player" -> Characters.PLAYER_BASE;
            case "boss", "bosses" -> Characters.BOSS_BASE;
            case "enemy", "enemies" -> Characters.ENEMY_BASE;
            case "drone", "drones" -> Characters.DRONE_BASE;
            case "scifi" -> Characters.SCIFI_BASE;
            default -> null;
        };
    }
    
    /**
     * Get the unified asset manager
     */
    public static AssetManager_001_UnifiedLoader getAssetManager() {
        return AssetManager_001_UnifiedLoader.getInstance();
    }
    
    /**
     * Get metadata with frame counts and timings
     */
    public static AssetMetadata getMetadata() {
        return AssetMetadata.getInstance();
    }
    
    /**
     * Load image using unified system
     */
    public static java.awt.image.BufferedImage loadImage(String relativePath) {
        return getAssetManager().loadImage(relativePath);
    }
    
    /**
     * Extract frames from sprite sheet using metadata
     */
    public static java.awt.image.BufferedImage[] getSpriteFrames(String path, int frameCount) {
        return getAssetManager().getSpriteFrames(path, frameCount);
    }
}
