/*
 * Decompiled with CFR 0.152.
 */
package animation;

import game2D.GameCore;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.CallSite;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

public class AnimationAndSpriteLoader
extends GameCore {
    private static final long serialVersionUID = 1L;
    public static final String PLAYER_BASE = "Resources/industrial-zone/characters/player/";
    public static final String BOSS_BASE = "Resources/industrial-zone/characters/bosses/";
    public static final String ENEMY_BASE = "Resources/industrial-zone/characters/enemies/";
    public static final String DRONE_BASE = "Resources/industrial-zone/characters/enemies/drones/";
    public static final String SCIFI_BASE = "Resources/industrial-zone/characters/enemies/sci-fi-antagonists/";
    public static final String L1_TILES_BASE = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/";
    public static final String L1_BG_BASE = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/";
    public static final String L1_OBJECTS_BASE = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/";
    public static final String L1_ANIMATED_BASE = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/";
    public static final String L2_TILES_BASE = "Resources/industrial-zone/1 Tiles/power-station-level-2/1 Tiles/";
    public static final String L2_BG_BASE = "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/";
    public static final String L2_BG_DAY = "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/Day/";
    public static final String L2_BG_NIGHT = "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/Night/";
    public static final String L2_OBJECTS_BASE = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/";
    public static final String L2_OBJECTS_TUBE = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/";
    public static final String L2_OBJECTS_DECOR = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/";
    public static final String L2_OBJECTS_LINES = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/3 Power lines/";
    public static final String L2_ANIMATED_BASE = "Resources/industrial-zone/1 Tiles/power-station-level-2/4 Animated objects/";
    public static final String AUDIO_BASE = "Resources/industrial-zone/audio/";
    public static final String AUDIO_MUSIC_MIDI = "Resources/industrial-zone/audio/music_midi/";
    public static final String AUDIO_MUSIC_WAV = "Resources/industrial-zone/audio/music_wav/";
    public static final String AUDIO_SFX = "Resources/industrial-zone/audio/sfx/";
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
    public static final String WEAPONS_BASE = "Resources/industrial-zone/weapons/";
    public static final String WEAPON_1 = "Resources/industrial-zone/weapons/1/";
    public static final String WEAPON_1_CHAR = "Resources/industrial-zone/weapons/1/1 Characters/";
    public static final String WEAPON_1_CHAR_BIKER = "Resources/industrial-zone/weapons/1/1 Characters/1 Biker/";
    public static final String WEAPON_1_CHAR_PUNK = "Resources/industrial-zone/weapons/1/1 Characters/2 Punk/";
    public static final String WEAPON_1_CHAR_CYBER = "Resources/industrial-zone/weapons/1/1 Characters/3 Cyborg/";
    public static final String WEAPON_1_GUNS = "Resources/industrial-zone/weapons/1/2 Guns/";
    public static final String WEAPON_1_HANDS = "Resources/industrial-zone/weapons/1/3 Hands/";
    public static final String WEAPON_1_HANDS_BIKER = "Resources/industrial-zone/weapons/1/3 Hands/1 Biker/";
    public static final String WEAPON_1_HANDS_PUNK = "Resources/industrial-zone/weapons/1/3 Hands/2 Punk/";
    public static final String WEAPON_1_HANDS_CYBER = "Resources/industrial-zone/weapons/1/3 Hands/3 Cyborg/";
    public static final String WEAPON_1_EFFECTS = "Resources/industrial-zone/weapons/1/4 Shoot_effects/";
    public static final String WEAPON_1_BULLETS = "Resources/industrial-zone/weapons/1/5 Bullets/";
    public static final String WEAPON_2 = "Resources/industrial-zone/weapons/2/";
    public static final String WEAPON_2_CHAR = "Resources/industrial-zone/weapons/2/1 Characters/";
    public static final String WEAPON_2_CHAR_BIKER = "Resources/industrial-zone/weapons/2/1 Characters/1 Biker/";
    public static final String WEAPON_2_CHAR_PUNK = "Resources/industrial-zone/weapons/2/1 Characters/2 Punk/";
    public static final String WEAPON_2_CHAR_CYBER = "Resources/industrial-zone/weapons/2/1 Characters/3 Cyborg/";
    public static final String WEAPON_2_GUNS = "Resources/industrial-zone/weapons/2/2 Guns/";
    public static final String WEAPON_2_HANDS = "Resources/industrial-zone/weapons/2/3 Hands/";
    public static final String WEAPON_2_HANDS_BIKER = "Resources/industrial-zone/weapons/2/3 Hands/1 Biker/";
    public static final String WEAPON_2_HANDS_PUNK = "Resources/industrial-zone/weapons/2/3 Hands/2 Punk/";
    public static final String WEAPON_2_HANDS_CYBER = "Resources/industrial-zone/weapons/2/3 Hands/3 Cyborg/";
    public static final String WEAPON_2_EFFECTS = "Resources/industrial-zone/weapons/2/4 Shoot_effects/";
    public static final String WEAPON_2_BULLETS = "Resources/industrial-zone/weapons/2/5 Bullets/";
    public static final String KEYBOARD_KEYS = "Resources/industrial-zone/KeyBoard_Keys/";
    public static final String MOUSE_KEYS = "Resources/industrial-zone/Mouse_keys/";

    // ==================== ASSET CATEGORY ENUMS ====================
    // Comprehensive enums for all asset paths from assets-manifest.json

    /**
     * VFX Asset Categories - Special Effects and Animations
     * Paths: Resources/industrial-zone/vfx/
     * Total: 110+ assets (Smoke, Blood, Sparks, Particles, Other, Extra)
     */
    public enum VFXAssetType {
        SMOKE("1 Smoke", VFX_SMOKE),
        BLOOD("2 Blood", VFX_BLOOD),
        SPARKS("3 Sparks", VFX_SPARKS),
        PARTICLES("4 Particles", VFX_PARTICLES),
        OTHER("5 Other", VFX_OTHER),
        EXTRA("6 Extra", VFX_EXTRA),
        EXTRA_CHARACTER("6 Extra/Character", VFX_EXTRA_CHARACTER),
        EXTRA_OBJECTS("6 Extra/Objects", VFX_EXTRA_OBJECTS),
        EXTRA_BOX1("6 Extra/Objects/Box1", VFX_EXTRA_BOX1),
        EXTRA_BOX2("6 Extra/Objects/Box2", VFX_EXTRA_BOX2),
        EXTRA_BUSH("6 Extra/Objects/Bush", VFX_EXTRA_BUSH),
        EXTRA_CAPSULE("6 Extra/Objects/Capsule", VFX_EXTRA_CAPSULE);

        private final String displayName;
        private final String basePath;

        VFXAssetType(String displayName, String basePath) {
            this.displayName = displayName;
            this.basePath = basePath;
        }

        public String getDisplayName() { return displayName; }
        public String getBasePath() { return basePath; }
    }

    /**
     * Character Asset Categories - Player, Bosses, Enemies, Drones
     * Paths: Resources/industrial-zone/characters/
     * Total: 80+ assets (Biker, Punk, Cyborg + Bosses + Enemies + Drones)
     */
    public enum CharacterAssetType {
        PLAYER("Player Base", PLAYER_BASE),
        BOSS("Bosses", BOSS_BASE),
        ENEMY("Enemies", ENEMY_BASE),
        DRONE("Drones", DRONE_BASE),
        SCI_FI("Sci-Fi Antagonists", SCIFI_BASE);

        private final String displayName;
        private final String basePath;

        CharacterAssetType(String displayName, String basePath) {
            this.displayName = displayName;
            this.basePath = basePath;
        }

        public String getDisplayName() { return displayName; }
        public String getBasePath() { return basePath; }
    }

    /**
     * Tile Asset Categories - Level Specific Tilesets
     * Paths: Resources/industrial-zone/1 Tiles/
     * Total: 200+ assets (Level1 + Level2 + Objects + Animated)
     */
    public enum TileAssetType {
        // Level 1 Assets
        L1_TILES("Level 1 Tiles", L1_TILES_BASE),
        L1_BACKGROUND("Level 1 Background", L1_BG_BASE),
        L1_OBJECTS("Level 1 Objects", L1_OBJECTS_BASE),
        L1_ANIMATED("Level 1 Animated Objects", L1_ANIMATED_BASE),
        
        // Level 2 Assets
        L2_TILES("Level 2 Tiles", L2_TILES_BASE),
        L2_BACKGROUND("Level 2 Background", L2_BG_BASE),
        L2_BACKGROUND_DAY("Level 2 Background Day", L2_BG_DAY),
        L2_BACKGROUND_NIGHT("Level 2 Background Night", L2_BG_NIGHT),
        L2_OBJECTS("Level 2 Objects", L2_OBJECTS_BASE),
        L2_OBJECTS_TUBE("Level 2 Objects Tubes", L2_OBJECTS_TUBE),
        L2_OBJECTS_DECOR("Level 2 Objects Decoration", L2_OBJECTS_DECOR),
        L2_OBJECTS_LINES("Level 2 Objects Power Lines", L2_OBJECTS_LINES),
        L2_ANIMATED("Level 2 Animated Objects", L2_ANIMATED_BASE);

        private final String displayName;
        private final String basePath;

        TileAssetType(String displayName, String basePath) {
            this.displayName = displayName;
            this.basePath = basePath;
        }

        public String getDisplayName() { return displayName; }
        public String getBasePath() { return basePath; }
    }

    /**
     * GUI Asset Categories - User Interface Elements
     * Paths: Resources/industrial-zone/gui/
     * Total: 340+ assets (Frames, Bars, Icons, Buttons, Numbers, Cursors, etc.)
     */
    public enum GUIAssetType {
        BASE("GUI Base", GUI_BASE),
        FRAMES("Frames", GUI_FRAMES),
        BARS("Bars (Health, Energy, etc)", GUI_BARS),
        ICONS("Icons", GUI_ICONS),
        ICONS_BUTTONS("Icon Buttons", GUI_ICONS_BUTTONS),
        ICONS_GENERAL("General Icons", GUI_ICONS_ICONS),
        PALETTE("Color Palette", GUI_PALETTE),
        LOGO("Logo", GUI_LOGO),
        BUTTONS("Buttons", GUI_BUTTONS),
        NUMBERS("Numbers", GUI_NUMBERS),
        CURSORS("Cursors", GUI_CURSORS),
        OTHER("Other UI Elements", GUI_OTHER),
        OTHER_DECOR("Decorations", GUI_OTHER_DECOR),
        OTHER_SKILLS("Skill Icons", GUI_OTHER_SKILLS),
        FONT("Fonts", GUI_FONT),
        FONT_IMAGES("Font Images", GUI_FONT_IMAGES),
        CARD_ANIM("Card Animations", GUI_CARD_ANIM),
        ASSETS_BASE("GUI Assets", GUI_ASSETS_BASE),
        BUTTONS_BASE("GUI Buttons", GUI_BUTTONS_BASE),
        PANELS_BASE("GUI Panels", GUI_PANELS_BASE),
        TRANSITIONS_BASE("Transitions", GUI_TRANSITIONS_BASE),
        ICONS_BASE("Icons Base", GUI_ICONS_BASE),
        BACKGROUNDS_BASE("Backgrounds", GUI_BACKGROUNDS_BASE),
        LOGOS_BASE("Logos", GUI_LOGOS_BASE),
        TEXT_BASE("Text Elements", GUI_TEXT_BASE);

        private final String displayName;
        private final String basePath;

        GUIAssetType(String displayName, String basePath) {
            this.displayName = displayName;
            this.basePath = basePath;
        }

        public String getDisplayName() { return displayName; }
        public String getBasePath() { return basePath; }
    }

    /**
     * Audio Asset Categories - Music and Sound Effects
     * Paths: Resources/industrial-zone/audio/
     * Total: 150+ assets (Music MIDI, Music WAV, SFX)
     */
    public enum AudioAssetType {
        BASE("Audio Base", AUDIO_BASE),
        MUSIC_MIDI("Music (MIDI)", AUDIO_MUSIC_MIDI),
        MUSIC_WAV("Music (WAV)", AUDIO_MUSIC_WAV),
        SFX("Sound Effects", AUDIO_SFX);

        private final String displayName;
        private final String basePath;

        AudioAssetType(String displayName, String basePath) {
            this.displayName = displayName;
            this.basePath = basePath;
        }

        public String getDisplayName() { return displayName; }
        public String getBasePath() { return basePath; }
    }

    /**
     * Weapon Asset Categories - Guns, Hands, Effects
     * Paths: Resources/industrial-zone/weapons/
     * Total: 40+ assets (Weapon 1 + Weapon 2, each with characters, guns, hands, effects, bullets)
     */
    public enum WeaponAssetType {
        BASE("Weapons Base", WEAPONS_BASE),
        WEAPON_1("Weapon 1", WEAPON_1),
        WEAPON_1_CHAR("Weapon 1 Characters", WEAPON_1_CHAR),
        WEAPON_1_CHAR_BIKER("Weapon 1 Biker", WEAPON_1_CHAR_BIKER),
        WEAPON_1_CHAR_PUNK("Weapon 1 Punk", WEAPON_1_CHAR_PUNK),
        WEAPON_1_CHAR_CYBER("Weapon 1 Cyborg", WEAPON_1_CHAR_CYBER),
        WEAPON_1_GUNS("Weapon 1 Guns", WEAPON_1_GUNS),
        WEAPON_1_HANDS("Weapon 1 Hands", WEAPON_1_HANDS),
        WEAPON_1_HANDS_BIKER("Weapon 1 Hands Biker", WEAPON_1_HANDS_BIKER),
        WEAPON_1_HANDS_PUNK("Weapon 1 Hands Punk", WEAPON_1_HANDS_PUNK),
        WEAPON_1_HANDS_CYBER("Weapon 1 Hands Cyborg", WEAPON_1_HANDS_CYBER),
        WEAPON_1_EFFECTS("Weapon 1 Effects", WEAPON_1_EFFECTS),
        WEAPON_1_BULLETS("Weapon 1 Bullets", WEAPON_1_BULLETS),
        WEAPON_2("Weapon 2", WEAPON_2),
        WEAPON_2_CHAR("Weapon 2 Characters", WEAPON_2_CHAR),
        WEAPON_2_CHAR_BIKER("Weapon 2 Biker", WEAPON_2_CHAR_BIKER),
        WEAPON_2_CHAR_PUNK("Weapon 2 Punk", WEAPON_2_CHAR_PUNK),
        WEAPON_2_CHAR_CYBER("Weapon 2 Cyborg", WEAPON_2_CHAR_CYBER),
        WEAPON_2_GUNS("Weapon 2 Guns", WEAPON_2_GUNS),
        WEAPON_2_HANDS("Weapon 2 Hands", WEAPON_2_HANDS),
        WEAPON_2_HANDS_BIKER("Weapon 2 Hands Biker", WEAPON_2_HANDS_BIKER),
        WEAPON_2_HANDS_PUNK("Weapon 2 Hands Punk", WEAPON_2_HANDS_PUNK),
        WEAPON_2_HANDS_CYBER("Weapon 2 Hands Cyborg", WEAPON_2_HANDS_CYBER),
        WEAPON_2_EFFECTS("Weapon 2 Effects", WEAPON_2_EFFECTS),
        WEAPON_2_BULLETS("Weapon 2 Bullets", WEAPON_2_BULLETS);

        private final String displayName;
        private final String basePath;

        WeaponAssetType(String displayName, String basePath) {
            this.displayName = displayName;
            this.basePath = basePath;
        }

        public String getDisplayName() { return displayName; }
        public String getBasePath() { return basePath; }
    }

    /**
     * Input Asset Categories - Keyboard and Mouse Keys
     * Paths: Resources/industrial-zone/KeyBoard_Keys/ and Resources/industrial-zone/Mouse_keys/
     * Total: 60+ assets (Keyboard keys + Mouse buttons)
     */
    public enum InputAssetType {
        KEYBOARD("Keyboard Keys", KEYBOARD_KEYS),
        MOUSE("Mouse Keys", MOUSE_KEYS);

        private final String displayName;
        private final String basePath;

        InputAssetType(String displayName, String basePath) {
            this.displayName = displayName;
            this.basePath = basePath;
        }

        public String getDisplayName() { return displayName; }
        public String getBasePath() { return basePath; }
    }

    /**
     * Master Asset Category - Top level categorization for all assets
     * Used for switching and routing to specific asset type enums
     */
    public enum AssetCategory {
        VFX("Special Effects", VFX_BASE),
        CHARACTERS("Characters", "Resources/industrial-zone/characters/"),
        TILES("Level Tiles", "Resources/industrial-zone/1 Tiles/"),
        GUI("GUI Elements", GUI_BASE),
        AUDIO("Audio", AUDIO_BASE),
        WEAPONS("Weapons", WEAPONS_BASE),
        INPUT("Input Keys", "Resources/industrial-zone/");

        private final String displayName;
        private final String basePath;

        AssetCategory(String displayName, String basePath) {
            this.displayName = displayName;
            this.basePath = basePath;
        }

        public String getDisplayName() { return displayName; }
        public String getBasePath() { return basePath; }
    }

    private static final Map<String, AssetType> assetRegistry = new HashMap<String, AssetType>();
    private static final boolean VERBOSE_LOGGING = true;
    private static final Map<String, AnimationMetadata> ANIMATION_METADATA_REGISTRY = new HashMap<String, AnimationMetadata>(){
        {
            AnimationMetadata animationMetadata = new AnimationMetadata("drone_jet_bomb", "Resources/industrial-zone/enemies/drones/01_Drone_JetDroneVariant_BombPayload");
            animationMetadata.animationNames = new String[]{"idle", "alert", "bomb_drop", "taking_damage", "death"};
            animationMetadata.addAnimation(0, 4, 100, "none", "drone_idle_hum", 0.0f);
            animationMetadata.addAnimation(1, 6, 80, "detection_ping", "drone_alert", 0.0f);
            animationMetadata.addAnimation(2, 8, 50, "bomb_drop_effect|chain_boom", "bomb_launch", 0.6f);
            animationMetadata.addAnimation(3, 4, 60, "damage_flash", "drone_damage", 0.0f);
            animationMetadata.addAnimation(4, 10, 40, "explosion_vfx|debris_scatter", "drone_explosion", 0.0f);
            this.put("drone_jet_bomb", animationMetadata);
            AnimationMetadata animationMetadata2 = new AnimationMetadata("player_cyborg", "Resources/industrial-zone/characters");
            animationMetadata2.animationNames = new String[]{"idle", "run", "jump", "attack", "taking_damage", "death"};
            animationMetadata2.addAnimation(0, 4, 100, "none", "none", 0.0f);
            animationMetadata2.addAnimation(1, 8, 50, "run_dust", "footstep", 0.5f);
            animationMetadata2.addAnimation(2, 6, 60, "jump_effect", "jump_sound", 0.0f);
            animationMetadata2.addAnimation(3, 6, 40, "attack_slash|hit_marker", "attack_sound", 0.5f);
            animationMetadata2.addAnimation(4, 4, 60, "damage_flash|knockback", "pain_sound", 0.0f);
            animationMetadata2.addAnimation(5, 8, 50, "explosion_vfx|death_particles", "death_sound", 0.0f);
            this.put("player_cyborg", animationMetadata2);
            AnimationMetadata animationMetadata3 = new AnimationMetadata("effect_bomb_explosion", "Resources/industrial-zone/vfx");
            animationMetadata3.animationNames = new String[]{"explosion", "shockwave", "particles"};
            animationMetadata3.addAnimation(0, 16, 25, "chain_reaction_trigger", "chain_explosion", 0.0f);
            animationMetadata3.addAnimation(1, 12, 30, "knockback_force", "shockwave_sound", 0.3f);
            animationMetadata3.addAnimation(2, 20, 20, "none", "debris_scatter", 0.0f);
            this.put("effect_bomb_explosion", animationMetadata3);
        }
    };
    private static final Map<String, AudioTrack> MUSIC_REGISTRY = new HashMap<String, AudioTrack>(){
        {
            this.put("level1_theme", new AudioTrack("level1_theme", "Resources/industrial-zone/audio/music/level1_ambient.mid", 0.6f, true));
            AudioTrack audioTrack = new AudioTrack("level1_combat", "Resources/industrial-zone/audio/music/level1_combat.mid", 0.7f, true);
            audioTrack.setBPM(130);
            audioTrack.setFades(300.0f, 500.0f);
            this.put("level1_combat", audioTrack);
            this.put("level2_theme", new AudioTrack("level2_theme", "Resources/industrial-zone/audio/music/level2_ambient.mid", 0.6f, true));
            AudioTrack audioTrack2 = new AudioTrack("boss_theme", "Resources/industrial-zone/audio/music/boss_theme.mid", 0.8f, true);
            audioTrack2.setBPM(160);
            audioTrack2.setFades(100.0f, 800.0f);
            this.put("boss_theme", audioTrack2);
            this.put("menu_theme", new AudioTrack("menu_theme", "Resources/industrial-zone/audio/music/menu_theme.mid", 0.5f, true));
            this.put("character_select", new AudioTrack("character_select", "Resources/industrial-zone/audio/music/character_select.mid", 0.55f, true));
            AudioTrack audioTrack3 = new AudioTrack("victory_theme", "Resources/industrial-zone/audio/music/victory_theme.mid", 0.7f, false);
            audioTrack3.setFades(200.0f, 1000.0f);
            this.put("victory_theme", audioTrack3);
            AudioTrack audioTrack4 = new AudioTrack("game_over_theme", "Resources/industrial-zone/audio/music/game_over.mid", 0.6f, false);
            audioTrack4.setFades(100.0f, 2000.0f);
            this.put("game_over_theme", audioTrack4);
        }
    };
    public static final String GUI_ASSETS_BASE = "Resources/industrial-zone/gui/";
    public static final String GUI_BUTTONS_BASE = "Resources/industrial-zone/gui/buttons/";
    public static final String GUI_PANELS_BASE = "Resources/industrial-zone/gui/panels/";
    public static final String GUI_TRANSITIONS_BASE = "Resources/industrial-zone/gui/transitions/";
    public static final String GUI_ICONS_BASE = "Resources/industrial-zone/gui/icons/";
    public static final String GUI_BACKGROUNDS_BASE = "Resources/industrial-zone/gui/backgrounds/";
    public static final String GUI_LOGOS_BASE = "Resources/industrial-zone/gui/logos/";
    public static final String GUI_TEXT_BASE = "Resources/industrial-zone/gui/text/";

    public AnimationAndSpriteLoader() {
        System.out.println("[AnimationAndSpriteLoader] \u2713 Initialized as GameCore subclass with asset loaders");
    }

    public static ParallaxSystem createLevel1ParallaxSystem() {
        ParallaxSystem parallaxSystem = new ParallaxSystem();
        String string = L1_BG_BASE;
        float[] fArray = new float[]{0.0f, 0.15f, 0.25f, 0.4f, 0.6f};
        String[] stringArray = new String[]{"BG_Layer1_SkyBase_SolidLavenderGrey_StaticFill_DrawFirstNoScroll.png", "BG_Layer2_FractalTreeSilhouette_MintSkyBlackCracks_ParallaxFactor015.png", "BG_Layer3_FarFactorySilhouette_LightBlueIndustrial_ParallaxFactor025.png", "BG_Layer4_MidFactorySilhouette_MediumBluePipeDetail_ParallaxFactor040.png", "BG_Layer5_NearFactorySilhouette_DarkNavyLargeTank_ParallaxFactor060.png"};
        for (int i = 0; i < stringArray.length; ++i) {
            BufferedImage bufferedImage = AnimationAndSpriteLoader.loadImageAssetSilent(string + stringArray[i]);
            if (bufferedImage != null) {
                ParallaxSystem.ParallexLayer parallexLayer = new ParallaxSystem.ParallexLayer(bufferedImage, fArray[i], i);
                parallaxSystem.addLayer(parallexLayer);
                continue;
            }
            System.err.println("[WARN] Level 1 parallax layer failed to load: " + string + stringArray[i]);
        }
        return parallaxSystem;
    }

    public static ParallaxSystem createLevel2ParallaxSystemDay() {
        ParallaxSystem parallaxSystem = new ParallaxSystem();
        String string = L2_BG_DAY;
        float[] fArray = new float[]{0.0f, 0.15f, 0.25f, 0.4f, 0.6f};
        String[] stringArray = new String[]{"BG_Layer1_SkyBase_LightGreyWhiteGradient_StaticFill_DrawFirst.png", "BG_Layer2_FactoryLeft_LightBlueDetail_ParallaxFactor015.png", "BG_Layer3_FactoryTall_LightBlueChimney_ParallaxFactor025.png", "BG_Layer4_DistantFactory_FaintSilhouette_ParallaxFactor040.png", "BG_Layer5_FactoryRight_LightBlueVariant_ParallaxFactor060.png"};
        for (int i = 0; i < stringArray.length; ++i) {
            BufferedImage bufferedImage = AnimationAndSpriteLoader.loadImageAssetSilent(string + stringArray[i]);
            if (bufferedImage != null) {
                ParallaxSystem.ParallexLayer parallexLayer = new ParallaxSystem.ParallexLayer(bufferedImage, fArray[i], i);
                parallaxSystem.addLayer(parallexLayer);
                continue;
            }
            System.err.println("[WARN] Level 2 Day parallax layer failed to load: " + string + stringArray[i]);
        }
        return parallaxSystem;
    }

    public static ParallaxSystem createLevel2ParallaxSystemNight() {
        ParallaxSystem parallaxSystem = new ParallaxSystem();
        String string = L2_BG_NIGHT;
        float[] fArray = new float[]{0.0f, 0.15f, 0.25f, 0.4f, 0.6f};
        String[] stringArray = new String[]{"BG_Layer1_SkyBase_DarkGreyGradient_StaticFill_DrawFirst.png", "BG_Layer2_FactoryLeft_DarkGreySilhouette_ParallaxFactor015.png", "BG_Layer3_FactoryTall_DarkGreyCentre_ParallaxFactor025.png", "BG_Layer4_DistantFactory_VeryDarkFaint_ParallaxFactor040.png", "BG_Layer5_FactoryRight_DarkGreyVariant_ParallaxFactor060.png"};
        for (int i = 0; i < stringArray.length; ++i) {
            BufferedImage bufferedImage = AnimationAndSpriteLoader.loadImageAssetSilent(string + stringArray[i]);
            if (bufferedImage != null) {
                ParallaxSystem.ParallexLayer parallexLayer = new ParallaxSystem.ParallexLayer(bufferedImage, fArray[i], i);
                parallaxSystem.addLayer(parallexLayer);
                continue;
            }
            System.err.println("[WARN] Level 2 Night parallax layer failed to load: " + string + stringArray[i]);
        }
        return parallaxSystem;
    }

    /*
     * WARNING - void declaration
     */
    public static AssetType load(String string, String string2) {
        AssetType assetType;
        block30: {
            Object object;
            AnimationAndSpriteLoader.log("Loading asset: " + string + " from " + string2);
            File file = new File(string2);
            if (!file.exists()) {
                AnimationAndSpriteLoader.logError("Asset file not found: " + string2);
                return null;
            }
            if (assetRegistry.containsKey(string)) {
                return assetRegistry.get(string);
            }
            assetType = null;
            String string3 = string.toLowerCase();
            String string4 = string2.toLowerCase();
            if (string3.contains("transporter") || string3.contains("drone") || string3.contains("hover") || string3.contains("helicopter")) {
                assetType = string3.contains("hover") || string3.contains("platform") ? new TransporterDroneLoader(string, string2, TransporterDroneLoader.TransporterType.HOVER_PLATFORM) : (string3.contains("helicopter") || string3.contains("chopper") ? new TransporterDroneLoader(string, string2, TransporterDroneLoader.TransporterType.HELICOPTER) : new TransporterDroneLoader(string, string2, TransporterDroneLoader.TransporterType.HOVER_PLATFORM));
            } else if (string3.contains("path") || string3.contains("route") || string3.contains("trajectory")) {
                assetType = new TransporterPathLoader(string, 0.0f, 0.0f, 0.0f, 0.0f, 1000L, TransporterPathLoader.PathType.LINEAR);
            } else if (string3.contains("remote") || string3.contains("transport") && string3.contains("character")) {
                object = CharacterRemoteAnimationLoader.CharacterType.CYBORG;
                if (string3.contains("cyborg")) {
                    object = CharacterRemoteAnimationLoader.CharacterType.CYBORG;
                } else if (string3.contains("punk")) {
                    object = CharacterRemoteAnimationLoader.CharacterType.PUNK;
                } else if (string3.contains("biker")) {
                    object = CharacterRemoteAnimationLoader.CharacterType.BIKER;
                }
                assetType = new CharacterRemoteAnimationLoader(string, string2, (CharacterRemoteAnimationLoader.CharacterType)((Object)object));
            } else if (string3.contains("interaction") || string3.contains("zone") || string3.contains("trigger")) {
                assetType = new InteractionZoneLoader(string, 0.0f, 0.0f, 100.0f, InteractionZoneLoader.ZoneShape.CIRCLE, 'E');
            }
            if (string4.matches(".*\\d+frames1row.*") || string4.contains("_horiz")) {
                AnimationAndSpriteLoader.log("\u2713 DETECTED: Horizontal spritesheet pattern matched!");
                AnimationAndSpriteLoader.log("  Filename: " + string4);
                try {
                    object = ImageIO.read(new File(string2));
                    if (object != null) {
                        void string7;
                        int n = object.getWidth();
                        int n2 = object.getHeight();
                        int[] nArray = new int[]{2, 4, 6, 8, 10, 12, 14, 16, 20, 24};
                        int n3 = 4;
                        int n4 = n / 4;
                        double d = Double.MAX_VALUE;
                        ArrayList<CallSite> arrayList = new ArrayList<CallSite>();
                        int[] nArray2 = nArray;
                        int n5 = nArray2.length;
                        boolean bl = false;
                        while (string7 < n5) {
                            int n6 = nArray2[string7];
                            if (n % n6 == 0) {
                                int n7 = n / n6;
                                int n8 = n2;
                                if (n7 >= 32 && n7 <= 256) {
                                    double d2 = (double)n7 / (double)n8;
                                    double d3 = Math.abs(d2 - 1.0);
                                    double d4 = d3 * 1000.0;
                                    String string5 = String.format("%.2f:1", d2);
                                    String string6 = String.format("%.1f", d4);
                                    arrayList.add((CallSite)((Object)(n6 + " frames \u2192 " + n7 + "x" + n8 + "px (aspect: " + string5 + ", score: " + string6 + ")")));
                                    if (d4 < d) {
                                        d = d4;
                                        n3 = n6;
                                        n4 = n7;
                                    }
                                }
                            }
                            ++string7;
                        }
                        int n8 = n2;
                        AnimationAndSpriteLoader.log("\u2713 METAL-FIRST Detection: " + n3 + " frames (via actual image divisors)");
                        AnimationAndSpriteLoader.log("  Image dimensions: " + n + "x" + n2 + "px (TRUTH)");
                        AnimationAndSpriteLoader.log("  Frame calculation: " + n + " \u00f7 " + n3 + " = " + n4 + "x" + n8 + "px per frame");
                        AnimationAndSpriteLoader.log("  Aspect ratio: " + String.format("%.2f:1", (double)n4 / (double)n8) + " (preference: SQUARE > all else)");
                        if (!arrayList.isEmpty()) {
                            AnimationAndSpriteLoader.log("  All valid divisors (ranked by squareness): ");
                            for (String string8 : arrayList) {
                                AnimationAndSpriteLoader.log("    \u2022 " + string8);
                            }
                        }
                        SpriteMetadata spriteMetadata = new SpriteMetadata(n, n2, n3, n4, n8);
                        AnimationAndSpriteLoader.log(spriteMetadata.toString());
                        AnimationAndSpriteLoader.log("  \ud83d\udca1 Use ~" + spriteMetadata.suggestedMs + "ms per frame for smooth playback");
                        assetType = new HorizontalSpritesheetLoader(string, string2, n4, n8, n3);
                        break block30;
                    }
                    AnimationAndSpriteLoader.logError("Failed to load image: " + string2);
                    assetType = new SingleSpriteLoader(string, string2);
                }
                catch (Exception exception) {
                    AnimationAndSpriteLoader.logError("Failed to auto-detect horizontal spritesheet: " + exception.getMessage());
                    AnimationAndSpriteLoader.logError("Falling back to SingleSpriteLoader");
                    assetType = new SingleSpriteLoader(string, string2);
                }
            } else if (string3.contains("_vert") || string3.contains("_vertical")) {
                int n = 64;
                int n9 = 64;
                int n10 = 4;
                assetType = new VerticalSpritesheetLoader(string, string2, n, n9, n10);
            } else if (string3.contains("_grid")) {
                int n = 4;
                int n11 = 4;
                int n12 = 64;
                int n13 = 64;
                assetType = new GridSpritesheetLoader(string, string2, n, n11, n12, n13);
            } else {
                assetType = new SingleSpriteLoader(string, string2);
            }
        }
        if (assetType != null && assetType.load()) {
            assetRegistry.put(string, assetType);
            AnimationAndSpriteLoader.log("\u2713 Asset loaded and cached: " + string);
            return assetType;
        }
        AnimationAndSpriteLoader.logError("Failed to load asset: " + string);
        return null;
    }

    private static int getAnimationIndex(String string) {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>(){
            {
                this.put("idle", 0);
                this.put("idle2", 1);
                this.put("walk", 2);
                this.put("run", 3);
                this.put("dash", 4);
                this.put("jump", 5);
                this.put("doublejump", 6);
                this.put("fall", 7);
                this.put("land", 8);
                this.put("climb", 9);
                this.put("hang", 10);
                this.put("pullup", 11);
                this.put("punch", 12);
                this.put("attack1", 13);
                this.put("attack2", 14);
                this.put("attack3", 15);
                this.put("walkattack", 16);
                this.put("runattack", 17);
                this.put("hurt", 18);
                this.put("death", 19);
                this.put("use", 20);
                this.put("sitdown", 21);
                this.put("angry", 22);
                this.put("happy", 23);
                this.put("talk", 24);
                this.put("scan", 25);
                this.put("walkscan", 26);
                this.put("forward", 27);
                this.put("back", 28);
                this.put("fire1", 29);
                this.put("fire2", 30);
                this.put("fire3", 31);
                this.put("landing", 32);
                this.put("movement", 33);
                this.put("drop", 34);
                this.put("capsule", 35);
                this.put("fly", 36);
                this.put("bomb", 37);
                this.put("walk2", 38);
            }
        };
        return hashMap.getOrDefault(string, 0);
    }

    private static String formatAnimationName(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).replaceAll("([A-Z])", "_$1").replaceAll("^_", "").toUpperCase();
    }

    public static AnimationMetadata getAnimationMetadata(String string) {
        return ANIMATION_METADATA_REGISTRY.get(string);
    }

    public static void registerAnimationMetadata(String string, AnimationMetadata animationMetadata) {
        ANIMATION_METADATA_REGISTRY.put(string, animationMetadata);
        AnimationAndSpriteLoader.log("\u2713 Registered animation metadata: " + string);
    }

    public static Set<String> getRegisteredMetadataKeys() {
        return ANIMATION_METADATA_REGISTRY.keySet();
    }

    public static AudioTrack getAudioTrack(String string) {
        return MUSIC_REGISTRY.get(string);
    }

    public static void registerAudioTrack(String string, AudioTrack audioTrack) {
        MUSIC_REGISTRY.put(string, audioTrack);
        AnimationAndSpriteLoader.log("\u2713 Registered audio track: " + string);
    }

    public static Set<String> getRegisteredMusicKeys() {
        return MUSIC_REGISTRY.keySet();
    }

    public static String getMusicRegistryInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\u2550 MUSIC REGISTRY \u2550\n");
        for (String string : AnimationAndSpriteLoader.getRegisteredMusicKeys()) {
            AudioTrack audioTrack = MUSIC_REGISTRY.get(string);
            stringBuilder.append(String.format("  \u2022 %s: %s (vol=%.0f%%, loop=%s, BPM=%d)\n", string, audioTrack.filename, Float.valueOf(audioTrack.volumeLevel * 100.0f), audioTrack.isLooping, audioTrack.bpm));
        }
        return stringBuilder.toString();
    }

    private static void log(String string) {
        System.out.println("[AnimationLoader] " + string);
    }

    private static void logError(String string) {
        System.err.println("[AnimationLoader ERROR] " + string);
    }

    private static BufferedImage loadImageAssetSilent(String string) {
        try {
            File file = new File(string);
            if (!file.exists()) {
                return null;
            }
            return ImageIO.read(file);
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
    }

    public static PlayerController createPlayer(float f, float f2) {
        PhysicsUnitSystem.PhysicsBody physicsBody = new PhysicsUnitSystem.PhysicsBody(f, f2, 1.0f, 0.5f);
        return new PlayerController(physicsBody, new InputHandler());
    }

    public static EnemyController createEnemy(float f, float f2, float f3) {
        PhysicsUnitSystem.PhysicsBody physicsBody = new PhysicsUnitSystem.PhysicsBody(f, f2, 0.8f, 0.4f);
        return new EnemyController(physicsBody, f3);
    }

    public static BossController createBoss(float f, float f2) {
        PhysicsUnitSystem.PhysicsBody physicsBody = new PhysicsUnitSystem.PhysicsBody(f, f2, 2.0f, 1.0f);
        return new BossController(physicsBody);
    }

    public static String getTile(char c) {
        return TileRegistry.getTile(c);
    }

    public static Set<Character> getAllTileCodes() {
        return TileRegistry.getAllCodes();
    }

    public static boolean hasTile(char c) {
        return TileRegistry.hasTile(c);
    }

    public static int getTileCount() {
        return TileRegistry.getTileCount();
    }

    public static String getPlayerBasePath() {
        return PLAYER_BASE;
    }

    public static String getBossBasePath() {
        return BOSS_BASE;
    }

    public static String getEnemyBasePath() {
        return ENEMY_BASE;
    }

    public static String getDroneBasePath() {
        return DRONE_BASE;
    }

    public static String getSciFiEnemyPath() {
        return SCIFI_BASE;
    }

    public static String getLevel1TilesPath() {
        return L1_TILES_BASE;
    }

    public static String getLevel1BackgroundPath() {
        return L1_BG_BASE;
    }

    public static String getLevel1ObjectsPath() {
        return L1_OBJECTS_BASE;
    }

    public static String getLevel1AnimatedPath() {
        return L1_ANIMATED_BASE;
    }

    public static String getLevel2TilesPath() {
        return L2_TILES_BASE;
    }

    public static String getLevel2BackgroundPath() {
        return L2_BG_BASE;
    }

    public static String getLevel2BackgroundDayPath() {
        return L2_BG_DAY;
    }

    public static String getLevel2BackgroundNightPath() {
        return L2_BG_NIGHT;
    }

    public static String getLevel2ObjectsPath() {
        return L2_OBJECTS_BASE;
    }

    public static String getLevel2ObjectsTubePath() {
        return L2_OBJECTS_TUBE;
    }

    public static String getLevel2ObjectsDecorPath() {
        return L2_OBJECTS_DECOR;
    }

    public static String getLevel2ObjectsPowerLinesPath() {
        return L2_OBJECTS_LINES;
    }

    public static String getLevel2AnimatedPath() {
        return L2_ANIMATED_BASE;
    }

    public static String getGUIBasePath() {
        return "Resources/industrial-zone/gui/";
    }

    public static String getGUIFramesPath() {
        return GUI_FRAMES;
    }

    public static String getGUIBarsPath() {
        return GUI_BARS;
    }

    public static String getGUIIconsPath() {
        return GUI_ICONS;
    }

    public static String getGUIIconsButtonsPath() {
        return GUI_ICONS_BUTTONS;
    }

    public static String getGUIIconsRegularPath() {
        return GUI_ICONS_ICONS;
    }

    public static String getGUIPalettePath() {
        return GUI_PALETTE;
    }

    public static String getGUILogoPath() {
        return GUI_LOGO;
    }

    public static String getGUIButtonsPath() {
        return GUI_BUTTONS;
    }

    public static String getGUINumbersPath() {
        return GUI_NUMBERS;
    }

    public static String getGUICursorsPath() {
        return GUI_CURSORS;
    }

    public static String getGUIDecorPath() {
        return GUI_OTHER_DECOR;
    }

    public static String getGUISkillIconsPath() {
        return GUI_OTHER_SKILLS;
    }

    public static String getGUIFontPath() {
        return GUI_FONT;
    }

    public static String getGUIFontImagesPath() {
        return GUI_FONT_IMAGES;
    }

    public static String getGUICardAnimationsPath() {
        return GUI_CARD_ANIM;
    }

    public static String getVFXBasePath() {
        return VFX_BASE;
    }

    public static String getVFXSmokePath() {
        return VFX_SMOKE;
    }

    public static String getVFXBloodPath() {
        return VFX_BLOOD;
    }

    public static String getVFXSparksPath() {
        return VFX_SPARKS;
    }

    public static String getVFXParticlesPath() {
        return VFX_PARTICLES;
    }

    public static String getVFXOtherPath() {
        return VFX_OTHER;
    }

    public static String getVFXExtraCharacterPath() {
        return VFX_EXTRA_CHARACTER;
    }

    public static String getVFXExtraObjectsPath() {
        return VFX_EXTRA_OBJECTS;
    }

    public static String getVFXExtraBox1Path() {
        return VFX_EXTRA_BOX1;
    }

    public static String getVFXExtraBox2Path() {
        return VFX_EXTRA_BOX2;
    }

    public static String getVFXExtraBushPath() {
        return VFX_EXTRA_BUSH;
    }

    public static String getVFXExtraCapsulePath() {
        return VFX_EXTRA_CAPSULE;
    }

    public static String getWeaponsBasePath() {
        return WEAPONS_BASE;
    }

    public static String getWeapon1Path() {
        return WEAPON_1;
    }

    public static String getWeapon1CharactersPath() {
        return WEAPON_1_CHAR;
    }

    public static String getWeapon1BikerPath() {
        return WEAPON_1_CHAR_BIKER;
    }

    public static String getWeapon1PunkPath() {
        return WEAPON_1_CHAR_PUNK;
    }

    public static String getWeapon1CyborgPath() {
        return WEAPON_1_CHAR_CYBER;
    }

    public static String getWeapon1GunsPath() {
        return WEAPON_1_GUNS;
    }

    public static String getWeapon1HandsPath() {
        return WEAPON_1_HANDS;
    }

    public static String getWeapon1HandsBikerPath() {
        return WEAPON_1_HANDS_BIKER;
    }

    public static String getWeapon1HandsPunkPath() {
        return WEAPON_1_HANDS_PUNK;
    }

    public static String getWeapon1HandsCyborgPath() {
        return WEAPON_1_HANDS_CYBER;
    }

    public static String getWeapon1ShootEffectsPath() {
        return WEAPON_1_EFFECTS;
    }

    public static String getWeapon1BulletsPath() {
        return WEAPON_1_BULLETS;
    }

    public static String getWeapon2Path() {
        return WEAPON_2;
    }

    public static String getWeapon2CharactersPath() {
        return WEAPON_2_CHAR;
    }

    public static String getWeapon2BikerPath() {
        return WEAPON_2_CHAR_BIKER;
    }

    public static String getWeapon2PunkPath() {
        return WEAPON_2_CHAR_PUNK;
    }

    public static String getWeapon2CyborgPath() {
        return WEAPON_2_CHAR_CYBER;
    }

    public static String getWeapon2GunsPath() {
        return WEAPON_2_GUNS;
    }

    public static String getWeapon2HandsPath() {
        return WEAPON_2_HANDS;
    }

    public static String getWeapon2HandsBikerPath() {
        return WEAPON_2_HANDS_BIKER;
    }

    public static String getWeapon2HandsPunkPath() {
        return WEAPON_2_HANDS_PUNK;
    }

    public static String getWeapon2HandsCyborgPath() {
        return WEAPON_2_HANDS_CYBER;
    }

    public static String getWeapon2ShootEffectsPath() {
        return WEAPON_2_EFFECTS;
    }

    public static String getWeapon2BulletsPath() {
        return WEAPON_2_BULLETS;
    }

    public static String getAudioBasePath() {
        return AUDIO_BASE;
    }

    public static String getAudioMusicMidiPath() {
        return AUDIO_MUSIC_MIDI;
    }

    public static String getAudioMusicWavPath() {
        return AUDIO_MUSIC_WAV;
    }

    public static String getAudioSFXPath() {
        return AUDIO_SFX;
    }

    public static String getKeyboardKeysPath() {
        return KEYBOARD_KEYS;
    }

    public static String getMouseKeysPath() {
        return MOUSE_KEYS;
    }

    public static Map<String, String> getAssetPathsMap() {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        linkedHashMap.put("\ud83c\udfad Players", AnimationAndSpriteLoader.getPlayerBasePath());
        linkedHashMap.put("\ud83c\udfad Bosses", AnimationAndSpriteLoader.getBossBasePath());
        linkedHashMap.put("\ud83c\udfad Drones", AnimationAndSpriteLoader.getDroneBasePath());
        linkedHashMap.put("\ud83c\udfad Sci-Fi Enemies", AnimationAndSpriteLoader.getSciFiEnemyPath());
        linkedHashMap.put("\ud83c\udfed L1 Tiles", AnimationAndSpriteLoader.getLevel1TilesPath());
        linkedHashMap.put("\ud83c\udfed L1 Background", AnimationAndSpriteLoader.getLevel1BackgroundPath());
        linkedHashMap.put("\ud83c\udfed L1 Objects", AnimationAndSpriteLoader.getLevel1ObjectsPath());
        linkedHashMap.put("\ud83c\udfed L1 Animated", AnimationAndSpriteLoader.getLevel1AnimatedPath());
        linkedHashMap.put("\ud83c\udfed L2 Tiles", AnimationAndSpriteLoader.getLevel2TilesPath());
        linkedHashMap.put("\ud83c\udfed L2 Background", AnimationAndSpriteLoader.getLevel2BackgroundPath());
        linkedHashMap.put("\ud83c\udfed L2 BG Day", AnimationAndSpriteLoader.getLevel2BackgroundDayPath());
        linkedHashMap.put("\ud83c\udfed L2 BG Night", AnimationAndSpriteLoader.getLevel2BackgroundNightPath());
        linkedHashMap.put("\ud83c\udfed L2 Objects", AnimationAndSpriteLoader.getLevel2ObjectsPath());
        linkedHashMap.put("\ud83c\udfed L2 Objects (Tubes)", AnimationAndSpriteLoader.getLevel2ObjectsTubePath());
        linkedHashMap.put("\ud83c\udfed L2 Objects (Decor)", AnimationAndSpriteLoader.getLevel2ObjectsDecorPath());
        linkedHashMap.put("\ud83c\udfed L2 Objects (Power Lines)", AnimationAndSpriteLoader.getLevel2ObjectsPowerLinesPath());
        linkedHashMap.put("\ud83c\udfed L2 Animated", AnimationAndSpriteLoader.getLevel2AnimatedPath());
        linkedHashMap.put("\ud83c\udfa8 Frames", AnimationAndSpriteLoader.getGUIFramesPath());
        linkedHashMap.put("\ud83c\udfa8 Bars", AnimationAndSpriteLoader.getGUIBarsPath());
        linkedHashMap.put("\ud83c\udfa8 Icons", AnimationAndSpriteLoader.getGUIIconsPath());
        linkedHashMap.put("\ud83c\udfa8 Buttons", AnimationAndSpriteLoader.getGUIButtonsPath());
        linkedHashMap.put("\ud83c\udfa8 Numbers", AnimationAndSpriteLoader.getGUINumbersPath());
        linkedHashMap.put("\ud83c\udfa8 Cursors", AnimationAndSpriteLoader.getGUICursorsPath());
        linkedHashMap.put("\ud83c\udfa8 Font", AnimationAndSpriteLoader.getGUIFontPath());
        linkedHashMap.put("\ud83c\udfa8 Card Animations", AnimationAndSpriteLoader.getGUICardAnimationsPath());
        linkedHashMap.put("\u2728 Smoke", AnimationAndSpriteLoader.getVFXSmokePath());
        linkedHashMap.put("\u2728 Blood", AnimationAndSpriteLoader.getVFXBloodPath());
        linkedHashMap.put("\u2728 Sparks", AnimationAndSpriteLoader.getVFXSparksPath());
        linkedHashMap.put("\u2728 Particles", AnimationAndSpriteLoader.getVFXParticlesPath());
        linkedHashMap.put("\u2728 Extra (Character)", AnimationAndSpriteLoader.getVFXExtraCharacterPath());
        linkedHashMap.put("\u2728 Extra (Box 1)", AnimationAndSpriteLoader.getVFXExtraBox1Path());
        linkedHashMap.put("\u2728 Extra (Box 2)", AnimationAndSpriteLoader.getVFXExtraBox2Path());
        linkedHashMap.put("\u2728 Extra (Bush)", AnimationAndSpriteLoader.getVFXExtraBushPath());
        linkedHashMap.put("\u2728 Extra (Capsule)", AnimationAndSpriteLoader.getVFXExtraCapsulePath());
        linkedHashMap.put("\ud83d\udd2b Weapon 1 (Biker)", AnimationAndSpriteLoader.getWeapon1BikerPath());
        linkedHashMap.put("\ud83d\udd2b Weapon 1 (Punk)", AnimationAndSpriteLoader.getWeapon1PunkPath());
        linkedHashMap.put("\ud83d\udd2b Weapon 1 (Cyborg)", AnimationAndSpriteLoader.getWeapon1CyborgPath());
        linkedHashMap.put("\ud83d\udd2b Weapon 1 (Guns)", AnimationAndSpriteLoader.getWeapon1GunsPath());
        linkedHashMap.put("\ud83d\udd2b Weapon 1 (Effects)", AnimationAndSpriteLoader.getWeapon1ShootEffectsPath());
        linkedHashMap.put("\ud83d\udd2b Weapon 1 (Bullets)", AnimationAndSpriteLoader.getWeapon1BulletsPath());
        linkedHashMap.put("\ud83d\udd2b Weapon 2 (Biker)", AnimationAndSpriteLoader.getWeapon2BikerPath());
        linkedHashMap.put("\ud83d\udd2b Weapon 2 (Punk)", AnimationAndSpriteLoader.getWeapon2PunkPath());
        linkedHashMap.put("\ud83d\udd2b Weapon 2 (Cyborg)", AnimationAndSpriteLoader.getWeapon2CyborgPath());
        linkedHashMap.put("\ud83d\udd2b Weapon 2 (Guns)", AnimationAndSpriteLoader.getWeapon2GunsPath());
        linkedHashMap.put("\ud83d\udd2b Weapon 2 (Effects)", AnimationAndSpriteLoader.getWeapon2ShootEffectsPath());
        linkedHashMap.put("\ud83d\udd2b Weapon 2 (Bullets)", AnimationAndSpriteLoader.getWeapon2BulletsPath());
        linkedHashMap.put("\ud83d\udd0a Music MIDI", AnimationAndSpriteLoader.getAudioMusicMidiPath());
        linkedHashMap.put("\ud83d\udd0a Music WAV", AnimationAndSpriteLoader.getAudioMusicWavPath());
        linkedHashMap.put("\ud83d\udd0a SFX", AnimationAndSpriteLoader.getAudioSFXPath());
        linkedHashMap.put("\u2328\ufe0f  Keyboard Keys", AnimationAndSpriteLoader.getKeyboardKeysPath());
        linkedHashMap.put("\u2328\ufe0f  Mouse Keys", AnimationAndSpriteLoader.getMouseKeysPath());
        return linkedHashMap;
    }

    public static float pixelsToMeters(float f) {
        return PhysicsUnitSystem.toMeters(f);
    }

    public static float metersToPixels(float f) {
        return PhysicsUnitSystem.toPixels(f);
    }

    public static float getGravity() {
        return -9.81f;
    }

    public static float getTileSizeMeters() {
        return 1.0f;
    }

    public static float getTileSizePixels() {
        return 32.0f;
    }

    public static float getPixelsPerMeter() {
        return 32.0f;
    }

    public static boolean validateAssetDirectories() {
        String[] stringArray = new String[]{PLAYER_BASE, BOSS_BASE, ENEMY_BASE, DRONE_BASE, SCIFI_BASE, L1_TILES_BASE, L1_BG_BASE, L1_OBJECTS_BASE, L1_ANIMATED_BASE, L2_TILES_BASE, L2_BG_BASE, L2_OBJECTS_BASE, L2_ANIMATED_BASE, "Resources/industrial-zone/gui/", GUI_FRAMES, GUI_BARS, GUI_ICONS, VFX_BASE, VFX_SMOKE, VFX_BLOOD, WEAPONS_BASE, WEAPON_1, WEAPON_2, AUDIO_BASE, AUDIO_MUSIC_MIDI, AUDIO_SFX, KEYBOARD_KEYS, MOUSE_KEYS};
        int n = 0;
        for (String string : stringArray) {
            File file = new File(string);
            if (!file.exists() || !file.isDirectory()) continue;
            ++n;
        }
        return n == stringArray.length;
    }

    public static int getTotalAssetPaths() {
        return 78;
    }

    public static String printDiagnostics() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557\n");
        stringBuilder.append("\u2551        ANIMATION & SPRITE LOADER - DIAGNOSTICS                  \u2551\n");
        stringBuilder.append("\u255a\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255d\n\n");
        stringBuilder.append("\ud83d\udcca ASSET CONFIGURATION SUMMARY:\n");
        stringBuilder.append("\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\n");
        stringBuilder.append("Total Asset Path Constants: ").append(AnimationAndSpriteLoader.getTotalAssetPaths()).append("\n");
        stringBuilder.append("Tile Registry Size: ").append(AnimationAndSpriteLoader.getTileCount()).append(" tiles\n");
        stringBuilder.append("\n");
        stringBuilder.append("\ud83d\udcc1 PUBLIC API CATEGORIES:\n");
        stringBuilder.append("\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\n");
        stringBuilder.append("\u2713 Character Paths: getPlayerBasePath(), getBossBasePath(), etc. (5 methods)\n");
        stringBuilder.append("\u2713 Level 1 Paths: getLevel1TilesPath(), getLevel1BackgroundPath(), etc. (4 methods)\n");
        stringBuilder.append("\u2713 Level 2 Paths: getLevel2TilesPath(), Day/Night variants, Objects, etc. (9 methods)\n");
        stringBuilder.append("\u2713 GUI Paths: Frames, Bars, Icons, Buttons, Font, Animations, etc. (16 methods)\n");
        stringBuilder.append("\u2713 VFX Paths: Smoke, Blood, Sparks, Particles, Extra effects, etc. (12 methods)\n");
        stringBuilder.append("\u2713 Weapon Paths: Weapon1/2 with Character variants, Guns, Hands, Effects (26 methods)\n");
        stringBuilder.append("\u2713 Audio Paths: Music MIDI/WAV, SFX directories (4 methods)\n");
        stringBuilder.append("\u2713 Input Paths: Keyboard and Mouse key assets (2 methods)\n");
        stringBuilder.append("\n");
        stringBuilder.append("\u2699\ufe0f  FACTORY METHODS (Object Creation):\n");
        stringBuilder.append("\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\n");
        stringBuilder.append("\u2713 createPlayer(x, y) \u2192 PlayerController with physics\n");
        stringBuilder.append("\u2713 createEnemy(x, y, radius) \u2192 EnemyController with AI\n");
        stringBuilder.append("\u2713 createBoss(x, y) \u2192 BossController with combat states\n");
        stringBuilder.append("\u2713 createProjectile(...) \u2192 Projectile objects [STRUCTURE READY]\n");
        stringBuilder.append("\u2713 createVFX(...) \u2192 Visual effects [STRUCTURE READY]\n");
        stringBuilder.append("\n");
        stringBuilder.append("\ud83d\udd0d REGISTRY METHODS:\n");
        stringBuilder.append("\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\n");
        stringBuilder.append("\u2713 getTile(char code) \u2192 String path\n");
        stringBuilder.append("\u2713 getAllTileCodes() \u2192 Set<Character>\n");
        stringBuilder.append("\u2713 hasTile(char code) \u2192 boolean\n");
        stringBuilder.append("\u2713 getTileCount() \u2192 ").append(AnimationAndSpriteLoader.getTileCount()).append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("\u26a1 PHYSICS UTILITIES:\n");
        stringBuilder.append("\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\n");
        stringBuilder.append("\u2713 pixelsToMeters(float) \u2192 float\n");
        stringBuilder.append("\u2713 metersToPixels(float) \u2192 float\n");
        stringBuilder.append("\u2713 getGravity() \u2192 ").append(AnimationAndSpriteLoader.getGravity()).append(" m/s\u00b2\n");
        stringBuilder.append("\u2713 getTileSizeMeters() \u2192 ").append(AnimationAndSpriteLoader.getTileSizeMeters()).append(" m\n");
        stringBuilder.append("\u2713 getTileSizePixels() \u2192 ").append(AnimationAndSpriteLoader.getTileSizePixels()).append(" px\n");
        stringBuilder.append("\u2713 getPixelsPerMeter() \u2192 ").append(AnimationAndSpriteLoader.getPixelsPerMeter()).append(" px/m\n");
        stringBuilder.append("\n");
        stringBuilder.append("\u2705 SYSTEM STATUS: READY FOR EXTERNAL USE\n");
        stringBuilder.append("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\n\n");
        return stringBuilder.toString();
    }

    public static boolean isGameIntegrationReady() {
        return AnimationAndSpriteLoader.validateAssetDirectories() && AnimationAndSpriteLoader.getTileCount() == 65;
    }

    static {
        AnimationAndSpriteLoader.log("AnimationAndSpriteLoader initialized");
        AnimationAndSpriteLoader.log("Available loader types:");
        AnimationAndSpriteLoader.log("  - SingleSpriteLoader: Individual sprites");
        AnimationAndSpriteLoader.log("  - HorizontalSpritesheetLoader: Single-row frames");
        AnimationAndSpriteLoader.log("  - GridSpritesheetLoader: Grid-based frames");
        AnimationAndSpriteLoader.log("  - GridFrameAnimationLoader: Auto-detect grid from filename");
        AnimationAndSpriteLoader.log("  - SequenceFrameAnimationLoader: Directory of numbered frames");
        AnimationAndSpriteLoader.log("  - StateVariantLoader: State-indexed variants");
        AnimationAndSpriteLoader.log("  - CategorySpriteRegistry: Named sprite registry");
    }

    public static class ParallaxSystem {
        private List<ParallexLayer> layers = new ArrayList<ParallexLayer>();
        private float currentCameraX = 0.0f;

        public void addLayer(ParallexLayer parallexLayer3) {
            this.layers.add(parallexLayer3);
            this.layers.sort((parallexLayer, parallexLayer2) -> Integer.compare(parallexLayer.getLayerIndex(), parallexLayer2.getLayerIndex()));
        }

        public void updateCamera(float f) {
            this.currentCameraX = f;
            for (ParallexLayer parallexLayer : this.layers) {
                parallexLayer.update(f);
            }
        }

        public void render(Graphics2D graphics2D, int n, int n2) {
            for (ParallexLayer parallexLayer : this.layers) {
                parallexLayer.render(graphics2D, n, n2, this.currentCameraX);
            }
        }

        public void clearLayers() {
            this.layers.clear();
        }

        public int getLayerCount() {
            return this.layers.size();
        }

        public static class ParallexLayer {
            private BufferedImage image;
            private float parallaxDepth;
            private float currentOffsetX = 0.0f;
            private int layerIndex;
            private float imageWidth;
            private float imageHeight;

            public ParallexLayer(BufferedImage bufferedImage, float f, int n) {
                this.image = bufferedImage;
                this.parallaxDepth = Math.max(0.0f, Math.min(1.0f, f));
                this.layerIndex = n;
                this.imageWidth = bufferedImage != null ? (float)bufferedImage.getWidth() : 0.0f;
                this.imageHeight = bufferedImage != null ? (float)bufferedImage.getHeight() : 0.0f;
            }

            public void update(float f) {
                this.currentOffsetX = f * this.parallaxDepth;
            }

            public void render(Graphics2D graphics2D, int n, int n2, float f) {
                if (this.image == null) {
                    return;
                }
                float f2 = f * this.parallaxDepth % this.imageWidth;
                if (f2 < 0.0f) {
                    f2 += this.imageWidth;
                }
                int n3 = (int)(-f2);
                int n4 = (int)(this.imageWidth - f2);
                graphics2D.drawImage(this.image, n3, 0, (int)this.imageWidth, n2, null);
                graphics2D.drawImage(this.image, n4, 0, (int)this.imageWidth, n2, null);
            }

            public int getLayerIndex() {
                return this.layerIndex;
            }

            public float getParallaxDepth() {
                return this.parallaxDepth;
            }

            public float getCurrentOffset() {
                return this.currentOffsetX;
            }
        }
    }

    public static abstract class AssetType {
        protected String assetName;
        protected String filePath;
        protected BufferedImage image;
        protected int width;
        protected int height;

        public AssetType(String string, String string2) {
            this.assetName = string;
            this.filePath = string2;
        }

        public abstract boolean load();

        public abstract BufferedImage getFrame(int var1);

        public abstract int getFrameCount();

        public abstract int getFrameWidth();

        public abstract int getFrameHeight();

        public String getAssetName() {
            return this.assetName;
        }

        public String getFilePath() {
            return this.filePath;
        }

        public BufferedImage getSourceImage() {
            return this.image;
        }

        protected void loadImageFile() throws IOException {
            this.image = ImageIO.read(new File(this.filePath));
            if (this.image == null) {
                throw new IOException("Failed to load image: " + this.filePath);
            }
            this.width = this.image.getWidth();
            this.height = this.image.getHeight();
        }
    }

    public static class TransporterDroneLoader
    extends AssetType {
        private TransporterType type;
        private float movementSpeed;
        private String currentState;
        private Map<String, HorizontalSpritesheetLoader> stateLoaders;
        private Map<String, int[]> playerOffsets;
        private String originTileName;
        private String destinationTileName;
        private String zoneName;

        public TransporterType getTransporterType() {
            return this.type;
        }

        public TransporterDroneLoader(String string, String string2, TransporterType transporterType) {
            super(string, string2);
            this.type = transporterType;
            this.movementSpeed = transporterType.defaultSpeed;
            this.currentState = "idle";
            this.stateLoaders = new HashMap<String, HorizontalSpritesheetLoader>();
            this.playerOffsets = new HashMap<String, int[]>();
            this.originTileName = "UNSET";
            this.destinationTileName = "UNSET";
            this.zoneName = "Generic Zone";
        }

        public void setPlacementContext(String string, String string2, String string3) {
            this.originTileName = string;
            this.destinationTileName = string2;
            this.zoneName = string3;
        }

        @Override
        public boolean load() {
            try {
                AnimationAndSpriteLoader.log("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
                AnimationAndSpriteLoader.log("Loading Transporter: " + this.assetName + " (" + this.type.description + ")");
                AnimationAndSpriteLoader.log("Zone: " + this.zoneName);
                AnimationAndSpriteLoader.log("Placement: " + this.originTileName + " \u2192 " + this.destinationTileName);
                AnimationAndSpriteLoader.log("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
                this.loadState("idle", this.filePath, 4, 100);
                this.loadState("walk", this.filePath, 4, 100);
                this.loadState("drop", this.filePath, 4, 150);
                this.loadState("deploy", this.filePath, 4, 150);
                this.loadState("special", this.filePath, 4, 120);
                if (this.type == TransporterType.HOVER_PLATFORM) {
                    this.playerOffsets.put("all", new int[]{0, -50});
                } else {
                    this.playerOffsets.put("all", new int[]{0, 60});
                }
                AnimationAndSpriteLoader.log("\u2713 Transporter loaded: " + this.assetName);
                AnimationAndSpriteLoader.log("  Type: " + this.type.description);
                AnimationAndSpriteLoader.log("  Speed: " + this.movementSpeed + " px/sec");
                AnimationAndSpriteLoader.log("  Positioning: " + this.type.positionMode);
                AnimationAndSpriteLoader.log("  States: " + this.stateLoaders.size() + " (idle, walk, drop, deploy, special)");
                AnimationAndSpriteLoader.log("  Origin Tile: " + this.originTileName);
                AnimationAndSpriteLoader.log("  Destination Tile: " + this.destinationTileName);
                AnimationAndSpriteLoader.log("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
                return true;
            }
            catch (Exception exception) {
                AnimationAndSpriteLoader.logError("Failed to load transporter: " + this.assetName);
                AnimationAndSpriteLoader.logError("Path: " + this.filePath);
                AnimationAndSpriteLoader.logError("Type: " + this.type.description);
                AnimationAndSpriteLoader.logError("Reason: " + exception.getMessage());
                return false;
            }
        }

        private void loadState(String string, String string2, int n, int n2) {
            String string3 = this.filePath + "/" + string;
            String string4 = string3 + "/" + string + "_4Frames.png";
            HorizontalSpritesheetLoader horizontalSpritesheetLoader = new HorizontalSpritesheetLoader(this.assetName + "_" + string, string4, 64, 64, n);
            if (horizontalSpritesheetLoader.load()) {
                this.stateLoaders.put(string, horizontalSpritesheetLoader);
                AnimationAndSpriteLoader.log("  \u2713 State loaded: " + string + " (" + n + " frames @ " + n2 + "ms)");
            } else {
                AnimationAndSpriteLoader.logError("  \u2717 Failed to load state: " + string);
            }
        }

        public BufferedImage getFrame(String string, int n) {
            HorizontalSpritesheetLoader horizontalSpritesheetLoader = this.stateLoaders.get(string);
            if (horizontalSpritesheetLoader == null) {
                AnimationAndSpriteLoader.logError("State not found: " + string + ". Available: " + String.valueOf(this.stateLoaders.keySet()));
                return null;
            }
            return horizontalSpritesheetLoader.getFrame(n);
        }

        @Override
        public BufferedImage getFrame(int n) {
            return this.getFrame(this.currentState, n);
        }

        public void setState(String string) {
            if (this.stateLoaders.containsKey(string)) {
                this.currentState = string;
                AnimationAndSpriteLoader.log("Transporter state changed: " + string);
            } else {
                AnimationAndSpriteLoader.logError("Invalid transporter state: " + string);
            }
        }

        public int[] getPlayerPositionOffset() {
            return this.playerOffsets.getOrDefault("all", new int[]{0, 0});
        }

        @Override
        public int getFrameCount() {
            HorizontalSpritesheetLoader horizontalSpritesheetLoader = this.stateLoaders.get(this.currentState);
            return horizontalSpritesheetLoader != null ? horizontalSpritesheetLoader.getFrameCount() : 4;
        }

        @Override
        public int getFrameWidth() {
            return 64;
        }

        @Override
        public int getFrameHeight() {
            return 64;
        }

        public static enum TransporterType {
            HOVER_PLATFORM("Hover Platform - Player stands on top", 350.0f, "ON_TOP"),
            HELICOPTER("Helicopter - Player hangs from cable", 450.0f, "HANGING");

            public final String description;
            public final float defaultSpeed;
            public final String positionMode;

            private TransporterType(String string2, float f, String string3) {
                this.description = string2;
                this.defaultSpeed = f;
                this.positionMode = string3;
            }
        }

        public static enum TransporterState {
            IDLE("Waiting for player at platform"),
            WALK("Moving horizontally along path"),
            DROP("Descending to pick up player"),
            DEPLOY("Ascending after dropping player"),
            SPECIAL("Special effect/capsule activation");

            public final String description;

            private TransporterState(String string2) {
                this.description = string2;
            }
        }
    }

    public static class TransporterPathLoader
    extends AssetType {
        private float startX;
        private float startY;
        private float endX;
        private float endY;
        private long duration;
        private PathType pathType;
        private List<float[]> waypoints;

        public TransporterPathLoader(String string, float f, float f2, float f3, float f4, long l, PathType pathType) {
            super(string, "");
            this.startX = f;
            this.startY = f2;
            this.endX = f3;
            this.endY = f4;
            this.duration = l;
            this.pathType = pathType;
            this.waypoints = new ArrayList<float[]>();
        }

        @Override
        public boolean load() {
            try {
                this.generateWaypoints();
                AnimationAndSpriteLoader.log("\u2713 Path loaded: " + this.assetName);
                AnimationAndSpriteLoader.log("  From: (" + this.startX + ", " + this.startY + ") \u2192 To: (" + this.endX + ", " + this.endY + ")");
                AnimationAndSpriteLoader.log("  Duration: " + this.duration + "ms");
                AnimationAndSpriteLoader.log("  Type: " + this.pathType.description);
                AnimationAndSpriteLoader.log("  Waypoints: " + this.waypoints.size());
                return true;
            }
            catch (Exception exception) {
                AnimationAndSpriteLoader.logError("Failed to load path: " + this.assetName);
                AnimationAndSpriteLoader.logError("Reason: " + exception.getMessage());
                return false;
            }
        }

        private void generateWaypoints() {
            this.waypoints.clear();
            int n = Math.max(10, (int)(this.duration / 50L));
            for (int i = 0; i <= n; ++i) {
                float f;
                float f2 = (float)i / (float)n;
                this.waypoints.add(new float[]{f, switch (this.pathType.ordinal()) {
                    case 0 -> {
                        f = this.startX + (this.endX - this.startX) * f2;
                        yield this.startY + (this.endY - this.startY) * f2;
                    }
                    case 1 -> {
                        float var6_6 = (this.startX + this.endX) / 2.0f;
                        float var7_7 = Math.min(this.startY, this.endY) - 100.0f;
                        f = (1.0f - f2) * (1.0f - f2) * this.startX + 2.0f * (1.0f - f2) * f2 * var6_6 + f2 * f2 * this.endX;
                        yield (1.0f - f2) * (1.0f - f2) * this.startY + 2.0f * (1.0f - f2) * f2 * var7_7 + f2 * f2 * this.endY;
                    }
                    case 2 -> {
                        f = this.startX + (this.endX - this.startX) * f2;
                        float var8_8 = Math.min(this.startY, this.endY) - 150.0f;
                        yield this.startY + (var8_8 - this.startY) * (4.0f * f2 * (1.0f - f2)) + (this.endY - this.startY) * f2;
                    }
                    default -> {
                        f = this.startX + (this.endX - this.startX) * f2;
                        yield this.startY + (this.endY - this.startY) * f2;
                    }
                }});
            }
        }

        public float[] getPositionAtTime(long l) {
            if (l <= 0L) {
                return new float[]{this.startX, this.startY};
            }
            if (l >= this.duration) {
                return new float[]{this.endX, this.endY};
            }
            float f = (float)l / (float)this.duration;
            int n = (int)(f * (float)(this.waypoints.size() - 1));
            float[] fArray = this.waypoints.get(Math.min(n, this.waypoints.size() - 1));
            return new float[]{fArray[0], fArray[1]};
        }

        public float getProgressPercent(long l) {
            return Math.min(1.0f, (float)l / (float)this.duration);
        }

        public boolean isComplete(long l) {
            return l >= this.duration;
        }

        @Override
        public BufferedImage getFrame(int n) {
            return null;
        }

        @Override
        public int getFrameCount() {
            return (int)(this.duration / 50L);
        }

        @Override
        public int getFrameWidth() {
            return 0;
        }

        @Override
        public int getFrameHeight() {
            return 0;
        }

        public static enum PathType {
            LINEAR("Straight line movement"),
            CURVED("Smooth bezier curve path"),
            PARABOLIC("Arc-shaped movement");

            public final String description;

            private PathType(String string2) {
                this.description = string2;
            }
        }
    }

    public static class CharacterRemoteAnimationLoader
    extends AssetType {
        private CharacterType character;
        private Map<String, SingleSpriteLoader> poses;
        private int syncFrameRateMs;

        public CharacterRemoteAnimationLoader(String string, String string2, CharacterType characterType) {
            super(string, string2);
            this.character = characterType;
            this.poses = new HashMap<String, SingleSpriteLoader>();
            this.syncFrameRateMs = 100;
        }

        @Override
        public boolean load() {
            try {
                AnimationAndSpriteLoader.log("Loading Character Remote Animation: " + this.assetName + " (" + this.character.description + ")");
                this.loadPose("standing_on_platform", this.filePath + this.character.name().toLowerCase() + "_standing_on_platform.png");
                this.loadPose("hanging_from_rope", this.filePath + this.character.name().toLowerCase() + "_hanging_from_rope.png");
                this.loadSwingPoses();
                this.loadPose("falling", this.filePath + this.character.name().toLowerCase() + "_falling.png");
                AnimationAndSpriteLoader.log("\u2713 Character remote animation loaded: " + this.assetName);
                AnimationAndSpriteLoader.log("  Character: " + this.character.description);
                AnimationAndSpriteLoader.log("  Synchronized frame rate: " + this.syncFrameRateMs + "ms");
                return true;
            }
            catch (Exception exception) {
                AnimationAndSpriteLoader.logError("Failed to load character remote animation: " + this.assetName);
                AnimationAndSpriteLoader.logError("Reason: " + exception.getMessage());
                return false;
            }
        }

        private void loadPose(String string, String string2) {
            SingleSpriteLoader singleSpriteLoader = new SingleSpriteLoader(this.assetName + "_" + string, string2);
            if (singleSpriteLoader.load()) {
                this.poses.put(string, singleSpriteLoader);
                AnimationAndSpriteLoader.log("  \u2713 Pose loaded: " + string);
            }
        }

        private void loadSwingPoses() {
            String string = this.filePath + this.character.name().toLowerCase() + "_swing_4Frames.png";
            HorizontalSpritesheetLoader horizontalSpritesheetLoader = new HorizontalSpritesheetLoader(this.assetName + "_swinging", string, 64, 64, 4);
            if (horizontalSpritesheetLoader.load()) {
                for (int i = 0; i < horizontalSpritesheetLoader.getFrameCount(); ++i) {
                    final int n = i;
                    final HorizontalSpritesheetLoader horizontalSpritesheetLoader2 = horizontalSpritesheetLoader;
                    SingleSpriteLoader singleSpriteLoader = new SingleSpriteLoader(this, this.assetName + "_swinging_" + i, string){

                        @Override
                        public BufferedImage getFrame(int n2) {
                            return horizontalSpritesheetLoader2.getFrame(n);
                        }
                    };
                    this.poses.put("swinging_" + i, singleSpriteLoader);
                }
                AnimationAndSpriteLoader.log("  \u2713 Swing animation loaded (4 frames)");
            }
        }

        public BufferedImage getFrame(String string) {
            SingleSpriteLoader singleSpriteLoader = this.poses.get(string);
            if (singleSpriteLoader == null) {
                AnimationAndSpriteLoader.logError("Pose not found: " + string);
                return null;
            }
            return singleSpriteLoader.getFrame(0);
        }

        public BufferedImage getSwingFrame(float f) {
            int n = Math.round(f * 3.0f) % 4;
            return this.getFrame("swinging_" + n);
        }

        @Override
        public BufferedImage getFrame(int n) {
            return this.getFrame("standing_on_platform");
        }

        @Override
        public int getFrameCount() {
            return 1;
        }

        @Override
        public int getFrameWidth() {
            return 64;
        }

        @Override
        public int getFrameHeight() {
            return 64;
        }

        public static enum CharacterType {
            CYBORG("Cyborg - Heavy built, stable"),
            PUNK("Punk - Slim, agile"),
            BIKER("Biker - Medium, balanced");

            public final String description;

            private CharacterType(String string2) {
                this.description = string2;
            }
        }
    }

    public static class InteractionZoneLoader
    extends AssetType {
        private float centerX;
        private float centerY;
        private float radius;
        private float width;
        private float height;
        private ZoneShape shape;
        private char interactionKey;
        private boolean isActive;

        public InteractionZoneLoader(String string, float f, float f2, float f3, ZoneShape zoneShape, char c) {
            super(string, "");
            this.centerX = f;
            this.centerY = f2;
            this.radius = f3;
            this.shape = zoneShape;
            this.interactionKey = c;
            this.isActive = true;
        }

        @Override
        public boolean load() {
            AnimationAndSpriteLoader.log("\u2713 Interaction zone loaded: " + this.assetName);
            AnimationAndSpriteLoader.log("  Shape: " + this.shape.description);
            AnimationAndSpriteLoader.log("  Center: (" + this.centerX + ", " + this.centerY + ")");
            AnimationAndSpriteLoader.log("  Radius: " + this.radius + "px");
            AnimationAndSpriteLoader.log("  Key: '" + this.interactionKey + "'");
            return true;
        }

        public boolean isPlayerInZone(float f, float f2) {
            if (!this.isActive) {
                return false;
            }
            switch (this.shape.ordinal()) {
                case 0: {
                    float f3 = (f - this.centerX) * (f - this.centerX) + (f2 - this.centerY) * (f2 - this.centerY);
                    return f3 <= this.radius * this.radius;
                }
                case 1: {
                    return f >= this.centerX - this.width / 2.0f && f <= this.centerX + this.width / 2.0f && f2 >= this.centerY - this.height / 2.0f && f2 <= this.centerY + this.height / 2.0f;
                }
            }
            return false;
        }

        public float[] getClosestPoint(float f, float f2) {
            if (this.shape == ZoneShape.CIRCLE) {
                float f3 = f - this.centerX;
                float f4 = f2 - this.centerY;
                float f5 = (float)Math.sqrt(f3 * f3 + f4 * f4);
                if (f5 == 0.0f) {
                    return new float[]{this.centerX, this.centerY};
                }
                return new float[]{this.centerX + f3 / f5 * this.radius, this.centerY + f4 / f5 * this.radius};
            }
            return new float[]{this.centerX, this.centerY};
        }

        public void setActive(boolean bl) {
            this.isActive = bl;
        }

        public char getInteractionKey() {
            return this.interactionKey;
        }

        public ZoneShape getShape() {
            return this.shape;
        }

        @Override
        public BufferedImage getFrame(int n) {
            return null;
        }

        @Override
        public int getFrameCount() {
            return 1;
        }

        @Override
        public int getFrameWidth() {
            return (int)this.radius;
        }

        @Override
        public int getFrameHeight() {
            return (int)this.radius;
        }

        public static enum ZoneShape {
            CIRCLE("Circular interaction zone"),
            RECTANGLE("Rectangular interaction zone"),
            POLYGON("Complex polygon zone");

            public final String description;

            private ZoneShape(String string2) {
                this.description = string2;
            }
        }
    }

    public static class SpriteMetadata {
        public int imageWidth;
        public int imageHeight;
        public int frameCount;
        public int frameWidth;
        public int frameHeight;
        public int totalPixelsPerFrame;
        public String complexity;
        public int suggestedMs;

        public SpriteMetadata(int n, int n2, int n3, int n4, int n5) {
            this.imageWidth = n;
            this.imageHeight = n2;
            this.frameCount = n3;
            this.frameWidth = n4;
            this.frameHeight = n5;
            this.totalPixelsPerFrame = n4 * n5;
            if (n4 <= 32 || n5 <= 32) {
                this.complexity = "LOW";
                this.suggestedMs = 120;
            } else if (n4 > 64 && n5 > 80) {
                this.complexity = "HIGH";
                this.suggestedMs = 80;
            } else {
                this.complexity = "MEDIUM";
                this.suggestedMs = 100;
            }
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\n");
            stringBuilder.append("  SPRITE METADATA ANALYSIS\n");
            stringBuilder.append("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\n");
            stringBuilder.append("  Spritesheet Size:  ").append(this.imageWidth).append("\u00d7").append(this.imageHeight).append("px\n");
            stringBuilder.append("  Frame Count:       ").append(this.frameCount).append(" frames\n");
            stringBuilder.append("  Frame Dimensions:  ").append(this.frameWidth).append("\u00d7").append(this.frameHeight).append("px\n");
            stringBuilder.append("  Pixels/Frame:      ").append(this.totalPixelsPerFrame).append("px\u00b2\n");
            stringBuilder.append("  Complexity:        ").append(this.complexity).append("\n");
            stringBuilder.append("  Suggested Timing:  ").append(this.suggestedMs).append("ms/frame\n");
            stringBuilder.append("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\n");
            return stringBuilder.toString();
        }
    }

    public static class HorizontalSpritesheetLoader
    extends AssetType {
        private int frameWidth;
        private int frameHeight;
        private int frameCount;
        private List<BufferedImage> frames;

        public HorizontalSpritesheetLoader(String string, String string2, int n, int n2, int n3) {
            block9: {
                super(string, string2);
                this.frameWidth = n;
                this.frameHeight = n2;
                if (n3 <= 0) {
                    try {
                        BufferedImage bufferedImage = ImageIO.read(new File(string2));
                        if (bufferedImage != null) {
                            int[] nArray = new int[]{2, 4, 6, 8, 10, 12, 14, 16, 20, 24};
                            int n4 = 4;
                            double d = Double.MAX_VALUE;
                            for (int n5 : nArray) {
                                double d2;
                                double d3;
                                double d4;
                                int n6;
                                if (bufferedImage.getWidth() % n5 != 0 || (n6 = bufferedImage.getWidth() / n5) < 32 || n6 > 256 || !((d4 = (d3 = Math.abs((d2 = (double)n6 / (double)bufferedImage.getHeight()) - 1.0)) * 1000.0) < d)) continue;
                                d = d4;
                                n4 = n5;
                            }
                            this.frameCount = n4;
                            this.frameWidth = bufferedImage.getWidth() / n4;
                            if (this.frameHeight <= 0) {
                                this.frameHeight = bufferedImage.getHeight();
                            }
                        } else {
                            this.frameCount = 4;
                            if (this.frameHeight <= 0) {
                                this.frameHeight = 48;
                            }
                        }
                        break block9;
                    }
                    catch (Exception exception) {
                        this.frameCount = 4;
                        if (this.frameHeight <= 0) {
                            this.frameHeight = 48;
                        }
                        break block9;
                    }
                }
                this.frameCount = n3;
            }
            this.frames = new ArrayList<BufferedImage>();
        }

        @Override
        public boolean load() {
            try {
                this.loadImageFile();
                if (this.frameHeight <= 0) {
                    this.frameHeight = this.height;
                }
                if (this.frameWidth <= 0 && this.frameCount > 0) {
                    this.frameWidth = this.width / this.frameCount;
                }
                this.extractFrames();
                AnimationAndSpriteLoader.log("\u2713 Horizontal spritesheet loaded: " + this.assetName);
                AnimationAndSpriteLoader.log("  Frames: " + this.frameCount + " | Size: " + this.frameWidth + "x" + this.frameHeight + "px");
                AnimationAndSpriteLoader.log("  Source: " + this.width + "x" + this.height + "px");
                return true;
            }
            catch (Exception exception) {
                AnimationAndSpriteLoader.logError("Failed to load horizontal spritesheet: " + this.assetName);
                AnimationAndSpriteLoader.logError("Path: " + this.filePath);
                AnimationAndSpriteLoader.logError("Config: " + this.frameWidth + "x" + this.frameHeight + ", " + this.frameCount + " frames");
                AnimationAndSpriteLoader.logError("Reason: " + exception.getMessage());
                return false;
            }
        }

        private void extractFrames() {
            this.frames.clear();
            for (int i = 0; i < this.frameCount; ++i) {
                int n = i * this.frameWidth;
                if (n + this.frameWidth > this.width) {
                    AnimationAndSpriteLoader.logError("Frame " + i + " exceeds image width at x=" + n);
                    continue;
                }
                BufferedImage bufferedImage = this.image.getSubimage(n, 0, this.frameWidth, this.frameHeight);
                this.frames.add(bufferedImage);
            }
        }

        @Override
        public BufferedImage getFrame(int n) {
            if (n < 0 || n >= this.frames.size()) {
                AnimationAndSpriteLoader.logError("Frame index out of bounds: " + n + " (total: " + this.frames.size() + ")");
                return null;
            }
            return this.frames.get(n);
        }

        @Override
        public int getFrameCount() {
            return this.frameCount;
        }

        @Override
        public int getFrameWidth() {
            return this.frameWidth;
        }

        @Override
        public int getFrameHeight() {
            return this.frameHeight;
        }

        public SpriteMetadata getMetadata() {
            return new SpriteMetadata(this.width, this.height, this.frameCount, this.frameWidth, this.frameHeight);
        }

        public String getAnalysisReport() {
            SpriteMetadata spriteMetadata = this.getMetadata();
            return spriteMetadata.toString() + "  Suggested Timing: " + spriteMetadata.suggestedMs + "ms/frame (for complexity: " + spriteMetadata.complexity + ")\n";
        }
    }

    public static class SingleSpriteLoader
    extends AssetType {
        public SingleSpriteLoader(String string, String string2) {
            super(string, string2);
        }

        @Override
        public boolean load() {
            try {
                this.loadImageFile();
                AnimationAndSpriteLoader.log("\u2713 Single sprite loaded: " + this.assetName + " (" + this.width + "x" + this.height + "px)");
                return true;
            }
            catch (IOException iOException) {
                AnimationAndSpriteLoader.logError("Failed to load single sprite: " + this.assetName);
                AnimationAndSpriteLoader.logError("Path: " + this.filePath);
                AnimationAndSpriteLoader.logError("Reason: " + iOException.getMessage());
                return false;
            }
        }

        @Override
        public BufferedImage getFrame(int n) {
            if (n != 0) {
                AnimationAndSpriteLoader.logError("SingleSpriteLoader only has 1 frame, requested: " + n);
                return null;
            }
            return this.image;
        }

        @Override
        public int getFrameCount() {
            return 1;
        }

        @Override
        public int getFrameWidth() {
            return this.width;
        }

        @Override
        public int getFrameHeight() {
            return this.height;
        }
    }

    public static class VerticalSpritesheetLoader
    extends AssetType {
        private int frameWidth;
        private int frameHeight;
        private int frameCount;
        private List<BufferedImage> frames;

        public VerticalSpritesheetLoader(String string, String string2, int n, int n2, int n3) {
            super(string, string2);
            this.frameWidth = n;
            this.frameHeight = n2;
            this.frameCount = n3;
            this.frames = new ArrayList<BufferedImage>();
        }

        @Override
        public boolean load() {
            try {
                this.loadImageFile();
                this.extractFrames();
                AnimationAndSpriteLoader.log("\u2713 Vertical spritesheet loaded: " + this.assetName);
                AnimationAndSpriteLoader.log("  Frames: " + this.frameCount + " | Size: " + this.frameWidth + "x" + this.frameHeight + "px");
                AnimationAndSpriteLoader.log("  Source: " + this.width + "x" + this.height + "px");
                return true;
            }
            catch (Exception exception) {
                AnimationAndSpriteLoader.logError("Failed to load vertical spritesheet: " + this.assetName);
                AnimationAndSpriteLoader.logError("Path: " + this.filePath);
                AnimationAndSpriteLoader.logError("Config: " + this.frameWidth + "x" + this.frameHeight + ", " + this.frameCount + " frames");
                AnimationAndSpriteLoader.logError("Reason: " + exception.getMessage());
                return false;
            }
        }

        private void extractFrames() {
            this.frames.clear();
            for (int i = 0; i < this.frameCount; ++i) {
                int n = i * this.frameHeight;
                if (n + this.frameHeight > this.height) {
                    AnimationAndSpriteLoader.logError("Frame " + i + " exceeds image height at y=" + n);
                    continue;
                }
                BufferedImage bufferedImage = this.image.getSubimage(0, n, this.frameWidth, this.frameHeight);
                this.frames.add(bufferedImage);
            }
        }

        @Override
        public BufferedImage getFrame(int n) {
            if (n < 0 || n >= this.frames.size()) {
                AnimationAndSpriteLoader.logError("Frame index out of bounds: " + n + " (total: " + this.frames.size() + ")");
                return null;
            }
            return this.frames.get(n);
        }

        @Override
        public int getFrameCount() {
            return this.frameCount;
        }

        @Override
        public int getFrameWidth() {
            return this.frameWidth;
        }

        @Override
        public int getFrameHeight() {
            return this.frameHeight;
        }
    }

    public static class GridSpritesheetLoader
    extends AssetType {
        private int frameWidth;
        private int frameHeight;
        private int columns;
        private int rows;
        private List<BufferedImage> frames;

        public GridSpritesheetLoader(String string, String string2, int n, int n2, int n3, int n4) {
            super(string, string2);
            this.frameWidth = n;
            this.frameHeight = n2;
            this.columns = n3;
            this.rows = n4;
            this.frames = new ArrayList<BufferedImage>();
        }

        @Override
        public boolean load() {
            try {
                this.loadImageFile();
                this.extractGridFrames();
                AnimationAndSpriteLoader.log("\u2713 Grid spritesheet loaded: " + this.assetName);
                AnimationAndSpriteLoader.log("  Grid: " + this.columns + "x" + this.rows + " | Frame size: " + this.frameWidth + "x" + this.frameHeight + "px");
                AnimationAndSpriteLoader.log("  Total frames: " + this.frames.size() + " | Source: " + this.width + "x" + this.height + "px");
                return true;
            }
            catch (Exception exception) {
                AnimationAndSpriteLoader.logError("Failed to load grid spritesheet: " + this.assetName);
                AnimationAndSpriteLoader.logError("Path: " + this.filePath);
                AnimationAndSpriteLoader.logError("Config: " + this.frameWidth + "x" + this.frameHeight + ", Grid: " + this.columns + "x" + this.rows);
                AnimationAndSpriteLoader.logError("Reason: " + exception.getMessage());
                return false;
            }
        }

        private void extractGridFrames() {
            this.frames.clear();
            for (int i = 0; i < this.rows; ++i) {
                for (int j = 0; j < this.columns; ++j) {
                    int n = j * this.frameWidth;
                    int n2 = i * this.frameHeight;
                    if (n + this.frameWidth > this.width || n2 + this.frameHeight > this.height) {
                        AnimationAndSpriteLoader.log("Skipping frame at grid[" + i + "][" + j + "] - exceeds bounds");
                        continue;
                    }
                    BufferedImage bufferedImage = this.image.getSubimage(n, n2, this.frameWidth, this.frameHeight);
                    this.frames.add(bufferedImage);
                }
            }
        }

        @Override
        public BufferedImage getFrame(int n) {
            if (n < 0 || n >= this.frames.size()) {
                AnimationAndSpriteLoader.logError("Frame index out of bounds: " + n + " (total: " + this.frames.size() + ")");
                return null;
            }
            return this.frames.get(n);
        }

        @Override
        public int getFrameCount() {
            return this.frames.size();
        }

        @Override
        public int getFrameWidth() {
            return this.frameWidth;
        }

        @Override
        public int getFrameHeight() {
            return this.frameHeight;
        }
    }

    public static class AnimationMetadata {
        public String spriteKey;
        public String spriteFile;
        public String[] animationNames;
        public int[] frameCounts;
        public int[] frameMs;
        public String[] vfxTriggers;
        public String[] soundTriggers;
        public float[] triggerFramePercent;

        public AnimationMetadata(String string, String string2) {
            this.spriteKey = string;
            this.spriteFile = string2;
            this.animationNames = new String[0];
            this.frameCounts = new int[0];
            this.frameMs = new int[0];
            this.vfxTriggers = new String[0];
            this.soundTriggers = new String[0];
            this.triggerFramePercent = new float[0];
        }

        public void addAnimation(int n, int n2, int n3, String string, String string2, float f) {
            if (n >= this.frameCounts.length) {
                int[] nArray = new int[n + 1];
                int[] nArray2 = new int[n + 1];
                String[] stringArray = new String[n + 1];
                String[] stringArray2 = new String[n + 1];
                float[] fArray = new float[n + 1];
                System.arraycopy(this.frameCounts, 0, nArray, 0, this.frameCounts.length);
                System.arraycopy(this.frameMs, 0, nArray2, 0, this.frameMs.length);
                System.arraycopy(this.vfxTriggers, 0, stringArray, 0, this.vfxTriggers.length);
                System.arraycopy(this.soundTriggers, 0, stringArray2, 0, this.soundTriggers.length);
                System.arraycopy(this.triggerFramePercent, 0, fArray, 0, this.triggerFramePercent.length);
                this.frameCounts = nArray;
                this.frameMs = nArray2;
                this.vfxTriggers = stringArray;
                this.soundTriggers = stringArray2;
                this.triggerFramePercent = fArray;
            }
            this.frameCounts[n] = n2;
            this.frameMs[n] = n3;
            this.vfxTriggers[n] = string != null ? string : "none";
            this.soundTriggers[n] = string2 != null ? string2 : "none";
            this.triggerFramePercent[n] = f;
        }
    }

    public static class AudioTrack {
        public String trackKey;
        public String filename;
        public float volumeLevel;
        public boolean isLooping;
        public float fadeInMs;
        public float fadeOutMs;
        public int bpm;

        public AudioTrack(String string, String string2, float f, boolean bl) {
            this.trackKey = string;
            this.filename = string2;
            this.volumeLevel = f;
            this.isLooping = bl;
            this.fadeInMs = 500.0f;
            this.fadeOutMs = 500.0f;
            this.bpm = 120;
        }

        public void setFades(float f, float f2) {
            this.fadeInMs = f;
            this.fadeOutMs = f2;
        }

        public void setBPM(int n) {
            this.bpm = n;
        }
    }

    public static class PhysicsUnitSystem {
        public static final float PIXELS_PER_METER = 32.0f;
        public static final float METERS_PER_PIXEL = 0.03125f;
        public static final float TILE_SIZE_METERS = 1.0f;
        public static final float TILE_SIZE_PIXELS = 32.0f;
        public static final float GRAVITY = -9.81f;
        public static final float TIME_STEP = 0.016666668f;
        public static final float LINEAR_DAMPING = 0.85f;
        public static final float ANGULAR_DAMPING = 0.1f;
        public static final float AIR_DAMPING = 0.15f;
        public static final float STANDARD_JUMP_HEIGHT = 2.0f;
        public static final float STANDARD_JUMP_VELOCITY = PhysicsUnitSystem.jumpVelocity(2.0f);
        public static final float SMALL_JUMP_HEIGHT = 0.75f;
        public static final float SMALL_JUMP_VELOCITY = PhysicsUnitSystem.jumpVelocity(0.75f);
        public static final float HIGH_JUMP_HEIGHT = 4.0f;
        public static final float HIGH_JUMP_VELOCITY = PhysicsUnitSystem.jumpVelocity(4.0f);

        public static float toMeters(float f) {
            return f * 0.03125f;
        }

        public static float toPixels(float f) {
            return f * 32.0f;
        }

        public static float jumpVelocity(float f) {
            float f2 = Math.abs(-9.81f);
            return -((float)Math.sqrt(2.0f * f2 * f));
        }

        public static float distance(float f, float f2, float f3) {
            return f * f3 + 0.5f * f2 * f3 * f3;
        }

        public static float fallTime(float f) {
            float f2 = Math.abs(-9.81f);
            return (float)Math.sqrt(2.0f * f / f2);
        }

        public static float impactVelocity(float f) {
            float f2 = Math.abs(-9.81f);
            return -((float)Math.sqrt(2.0f * f2 * f));
        }

        public static class PhysicsBody {
            public Vector2D position;
            public Vector2D velocity;
            public Vector2D acceleration;
            public Vector2D forces;
            public float mass;
            public float radius;
            public boolean isGrounded;
            public boolean isAffectedByGravity;

            public PhysicsBody(float f, float f2, float f3, float f4) {
                this.position = new Vector2D(f, f2);
                this.velocity = new Vector2D();
                this.acceleration = new Vector2D();
                this.forces = new Vector2D();
                this.mass = f3 > 0.0f ? f3 : 1.0f;
                this.radius = f4;
                this.isGrounded = false;
                this.isAffectedByGravity = true;
            }

            public void applyForce(float f, float f2) {
                this.forces.x += f;
                this.forces.y += f2;
            }

            public void applyForce(Vector2D vector2D) {
                this.forces.add(vector2D);
            }

            public void clearForces() {
                this.forces.x = 0.0f;
                this.forces.y = 0.0f;
            }

            public void setMaxVelocity(float f) {
                float f2 = this.velocity.magnitude();
                if (f2 > f) {
                    this.velocity.multiply(f / f2);
                }
            }

            public void applyDamping(float f, float f2) {
                float f3 = 1.0f - f * f2;
                f3 = Math.max(0.0f, f3);
                this.velocity.multiply(f3);
            }

            public void update(float f) {
                if (f <= 0.0f) {
                    return;
                }
                if (this.isAffectedByGravity && !this.isGrounded) {
                    this.forces.y += this.mass * -9.81f;
                }
                this.acceleration.x = this.forces.x / this.mass;
                this.acceleration.y = this.forces.y / this.mass;
                this.velocity.x += this.acceleration.x * f;
                this.velocity.y += this.acceleration.y * f;
                if (!this.isGrounded) {
                    this.applyDamping(0.15f, f);
                } else {
                    this.applyDamping(0.85f, f);
                }
                this.position.x += this.velocity.x * f;
                this.position.y += this.velocity.y * f;
                this.clearForces();
            }

            public float getLeft() {
                return this.position.x - this.radius;
            }

            public float getRight() {
                return this.position.x + this.radius;
            }

            public float getTop() {
                return this.position.y - this.radius;
            }

            public float getBottom() {
                return this.position.y + this.radius;
            }

            public boolean collidesWithAABB(PhysicsBody physicsBody) {
                return !(this.getRight() < physicsBody.getLeft() || this.getLeft() > physicsBody.getRight() || this.getBottom() < physicsBody.getTop() || this.getTop() > physicsBody.getBottom());
            }

            public float getScreenX() {
                return this.position.x * 32.0f;
            }

            public float getScreenY() {
                return this.position.y * 32.0f;
            }

            public String toString() {
                return String.format("PhysicsBody{pos:%s, vel:%s, mass:%.1fkg, radius:%.2fm}", this.position, this.velocity, Float.valueOf(this.mass), Float.valueOf(this.radius));
            }
        }

        public static class Vector2D {
            public float x;
            public float y;

            public Vector2D() {
                this.x = 0.0f;
                this.y = 0.0f;
            }

            public Vector2D(float f, float f2) {
                this.x = f;
                this.y = f2;
            }

            public Vector2D(Vector2D vector2D) {
                this.x = vector2D.x;
                this.y = vector2D.y;
            }

            public void add(Vector2D vector2D) {
                this.x += vector2D.x;
                this.y += vector2D.y;
            }

            public void add(float f, float f2) {
                this.x += f;
                this.y += f2;
            }

            public void subtract(Vector2D vector2D) {
                this.x -= vector2D.x;
                this.y -= vector2D.y;
            }

            public void multiply(float f) {
                this.x *= f;
                this.y *= f;
            }

            public float dot(Vector2D vector2D) {
                return this.x * vector2D.x + this.y * vector2D.y;
            }

            public float magnitude() {
                return (float)Math.sqrt(this.x * this.x + this.y * this.y);
            }

            public float magnitudeSquared() {
                return this.x * this.x + this.y * this.y;
            }

            public void normalize() {
                float f = this.magnitude();
                if (f > 0.0f) {
                    this.x /= f;
                    this.y /= f;
                }
            }

            public Vector2D toPixels() {
                return new Vector2D(this.x * 32.0f, this.y * 32.0f);
            }

            public Vector2D toMeters() {
                return new Vector2D(this.x * 0.03125f, this.y * 0.03125f);
            }

            public String toString() {
                return String.format("[%.2f, %.2f]", Float.valueOf(this.x), Float.valueOf(this.y));
            }
        }
    }

    public static class PlayerController
    extends EntityAnimationController {
        private InputHandler baseInput;
        private InputController inputController;
        private boolean isGrounded;
        private boolean isInAir;
        private int jumpCount;

        public PlayerController(PhysicsUnitSystem.PhysicsBody physicsBody, InputHandler inputHandler) {
            super(physicsBody);
            this.baseInput = inputHandler;
            this.inputController = new InputController(inputHandler);
            this.isGrounded = true;
            this.isInAir = false;
            this.jumpCount = 0;
        }

        @Override
        protected void initializeAssets() {
            this.stateToAssetPath.put(AnimationState.IDLE, "Resources/industrial-zone/characters/biker/01_Player_Biker_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png");
            this.stateToAssetPath.put(AnimationState.WALK_LEFT, "Resources/industrial-zone/characters/biker/03_Player_Biker_Walk_6Frames1Row_WalkingCycleForward_Movement_Loop_100ms.png");
            this.stateToAssetPath.put(AnimationState.WALK_RIGHT, "Resources/industrial-zone/characters/biker/03_Player_Biker_Walk_6Frames1Row_WalkingCycleForward_Movement_Loop_100ms.png");
            this.stateToAssetPath.put(AnimationState.JUMP, "Resources/industrial-zone/characters/biker/06_Player_Biker_Jump_4Frames1Row_JumpRiseArc_JumpStart_PlayOnce_80ms.png");
            this.stateToAssetPath.put(AnimationState.DOUBLE_JUMP, "Resources/industrial-zone/characters/biker/07_Player_Biker_DoubleJump_6Frames1Row_MidAirFlipBoost_SecondJump_PlayOnce_80ms.png");
            this.stateToAssetPath.put(AnimationState.FALL, "Resources/industrial-zone/characters/biker/08_Player_Biker_Fall_4Frames1Row_FallingDescend_AirFall_Loop_100ms.png");
            this.stateToAssetPath.put(AnimationState.DASH_LEFT, "Resources/industrial-zone/characters/biker/05_Player_Biker_Dash_6Frames1Row_DashSlideForward_QuickDash_PlayOnce_60ms.png");
            this.stateToAssetPath.put(AnimationState.DASH_RIGHT, "Resources/industrial-zone/characters/biker/05_Player_Biker_Dash_6Frames1Row_DashSlideForward_QuickDash_PlayOnce_60ms.png");
            this.stateToAssetPath.put(AnimationState.CLIMB, "Resources/industrial-zone/characters/biker/09_Player_Biker_Climb_6Frames1Row_LadderClimbCycle_ClimbLadder_Loop_120ms.png");
            this.stateToAssetPath.put(AnimationState.HANG, "Resources/industrial-zone/characters/biker/10_Player_Biker_Hang_3Frames1Row_LedgeHangHold_HangingIdle_Loop_150ms.png");
            this.stateToAssetPath.put(AnimationState.ATTACK_MELEE, "Resources/industrial-zone/characters/biker/12_Player_Biker_Punch_5Frames1Row_StandingPunchCombo_MeleeAttack_PlayOnce_70ms.png");
            this.stateToAssetPath.put(AnimationState.ATTACK_RANGE, "Resources/industrial-zone/characters/biker/15_Player_Biker_Attack3_7Frames1Row_EnergyWaveRelease_Attack3_PlayOnce_70ms.png");
            this.stateToAssetPath.put(AnimationState.HURT, "Resources/industrial-zone/characters/biker/18_Player_Biker_Hurt_2Frames1Row_HitReactionGhostFlinch_TakeDamage_PlayOnce_100ms.png");
            this.stateToAssetPath.put(AnimationState.DEATH, "Resources/industrial-zone/characters/biker/19_Player_Biker_Death_6Frames1Row_DeathFallSequence_PlayerDeath_PlayOnce_120ms.png");
        }

        @Override
        protected void initializeTransitions() {
            this.validTransitions.put(AnimationState.IDLE, new StateTransition(AnimationState.IDLE, AnimationState.WALK_RIGHT, 1.0f, 1.0f, true, "Idle to walk right"));
        }

        @Override
        public void update(float f) {
            super.update(f);
            this.updateAirState(f);
            this.handleInput();
        }

        private void updateAirState(float f) {
            if (this.isInAir && this.physics.velocity.y < 0.0f && this.currentState != AnimationState.FALL) {
                this.transitionTo(AnimationState.FALL);
            }
            if (this.isInAir && this.isGrounded && this.currentState != AnimationState.LAND) {
                this.transitionTo(AnimationState.LAND);
                this.isInAir = false;
            }
        }

        private void handleInput() {
            AnimationState animationState = this.inputController.updateAndGetState();
            if (animationState != this.currentState) {
                this.transitionTo(animationState);
            }
            this.applyPhysicsForState(animationState);
        }

        private void applyPhysicsForState(AnimationState animationState) {
            switch (animationState.ordinal()) {
                case 1: {
                    this.physics.velocity.x = -5.0f;
                    break;
                }
                case 2: {
                    this.physics.velocity.x = 5.0f;
                    break;
                }
                case 7: {
                    this.physics.velocity.x = -15.0f;
                    break;
                }
                case 8: {
                    this.physics.velocity.x = 15.0f;
                    break;
                }
                case 3: {
                    this.physics.velocity.y = PhysicsUnitSystem.STANDARD_JUMP_VELOCITY;
                    this.isInAir = true;
                    this.isGrounded = false;
                    break;
                }
                case 4: {
                    this.physics.velocity.y = PhysicsUnitSystem.HIGH_JUMP_VELOCITY;
                    break;
                }
                case 5: {
                    this.physics.isAffectedByGravity = true;
                    break;
                }
                case 12: 
                case 13: {
                    this.physics.velocity.x = 0.0f;
                    break;
                }
                case 9: {
                    this.physics.isAffectedByGravity = false;
                    this.physics.velocity.y = -3.0f;
                    break;
                }
                case 10: {
                    this.physics.velocity.x = 0.0f;
                    this.physics.velocity.y = 0.0f;
                    this.physics.isAffectedByGravity = false;
                    break;
                }
                case 11: {
                    this.physics.velocity.x = 0.0f;
                    this.physics.velocity.y = -1.5f;
                    this.physics.isAffectedByGravity = false;
                    break;
                }
                case 0: {
                    this.physics.velocity.x = 0.0f;
                    break;
                }
            }
        }

        @Override
        protected void updatePhysicsForState(AnimationState animationState, float f) {
            switch (animationState.ordinal()) {
                case 3: {
                    this.physics.isAffectedByGravity = true;
                    this.physics.isGrounded = false;
                    break;
                }
                case 4: {
                    this.physics.isAffectedByGravity = true;
                    this.physics.isGrounded = false;
                    break;
                }
                case 5: {
                    this.physics.isAffectedByGravity = true;
                    break;
                }
                case 9: {
                    this.physics.isAffectedByGravity = false;
                    this.physics.velocity.y = -3.0f;
                    break;
                }
                case 0: 
                case 1: 
                case 2: {
                    this.physics.isGrounded = true;
                    this.physics.velocity.y = 0.0f;
                    break;
                }
            }
        }

        public void setGrounded(boolean bl) {
            this.isGrounded = bl;
            if (bl) {
                this.jumpCount = 0;
                this.isInAir = false;
            } else {
                this.isInAir = true;
            }
        }

        public boolean isGrounded() {
            return this.isGrounded;
        }

        public boolean isInAir() {
            return this.isInAir;
        }

        public int getJumpCount() {
            return this.jumpCount;
        }
    }

    public static class InputHandler {
        private boolean[] keyPressed = new boolean[256];
        private boolean[] keyReleased = new boolean[256];
        private long[] lastKeyTime = new long[256];
        private static final long DOUBLE_TAP_WINDOW = 250L;
        public static final int KEY_UP = 38;
        public static final int KEY_DOWN = 40;
        public static final int KEY_LEFT = 37;
        public static final int KEY_RIGHT = 39;
        public static final int KEY_SPACE = 32;
        public static final int KEY_SHIFT = 16;

        public void onKeyDown(int n) {
            this.keyPressed[n] = true;
            this.keyReleased[n] = false;
        }

        public void onKeyUp(int n) {
            this.keyPressed[n] = false;
            this.keyReleased[n] = true;
        }

        public boolean isKeyPressed(int n) {
            return this.keyPressed[n];
        }

        public boolean isKeyReleased(int n) {
            return this.keyReleased[n];
        }

        public boolean isDoubleTap(int n) {
            long l = System.currentTimeMillis();
            long l2 = l - this.lastKeyTime[n];
            if (l2 < 250L && l2 > 0L) {
                this.lastKeyTime[n] = 0L;
                return true;
            }
            if (this.isKeyReleased(n)) {
                this.lastKeyTime[n] = l;
            }
            return false;
        }

        public void clearFrame() {
            for (int i = 0; i < this.keyReleased.length; ++i) {
                this.keyReleased[i] = false;
            }
        }
    }

    public static class EnemyController
    extends EntityAnimationController {
        private float detectionRadius;
        private boolean isAlerted;
        private AIBehavior aiBehavior;

        public EnemyController(PhysicsUnitSystem.PhysicsBody physicsBody, float f) {
            super(physicsBody);
            this.detectionRadius = f;
            this.isAlerted = false;
            this.aiBehavior = null;
        }

        public void setAIBehavior(AIBehavior aIBehavior) {
            this.aiBehavior = aIBehavior;
        }

        @Override
        protected void initializeAssets() {
            this.stateToAssetPath.put(AnimationState.ENEMY_IDLE, "Resources/industrial-zone/characters/enemies/drones/1/01_EnemyDrone_UfoSaucer_Idle_3Frames1Row_HoveringStationary_DefaultIdle_Loop_150ms.png");
            this.stateToAssetPath.put(AnimationState.ENEMY_WALK, "Resources/industrial-zone/characters/enemies/drones/1/02_EnemyDrone_UfoSaucer_Movement_4Frames1Row_SmoothHoveringMove_Movement_Loop_100ms.png");
            this.stateToAssetPath.put(AnimationState.ENEMY_CHASE, "Resources/industrial-zone/characters/enemies/drones/1/03_EnemyDrone_UfoSaucer_Chase_4Frames1Row_FastHoveringPursuit_Chase_Loop_80ms.png");
            this.stateToAssetPath.put(AnimationState.ENEMY_ATTACK, "Resources/industrial-zone/characters/enemies/drones/1/04_EnemyDrone_UfoSaucer_Attack_3Frames1Row_EnergyBeamCharge_Attack_PlayOnce_100ms.png");
            this.stateToAssetPath.put(AnimationState.ENEMY_HURT, "Resources/industrial-zone/characters/enemies/drones/1/04_EnemyDrone_UfoSaucer_Attack_3Frames1Row_EnergyBeamCharge_Attack_PlayOnce_100ms.png");
            this.stateToAssetPath.put(AnimationState.ENEMY_DEATH, "Resources/industrial-zone/characters/enemies/drones/1/05_EnemyDrone_UfoSaucer_Death_4Frames1Row_ExplosionDisappear_Death_PlayOnce_120ms.png");
        }

        @Override
        protected void initializeTransitions() {
        }

        @Override
        protected void updatePhysicsForState(AnimationState animationState, float f) {
            switch (animationState.ordinal()) {
                case 16: {
                    this.physics.isAffectedByGravity = true;
                    this.physics.velocity.x = 0.0f;
                    break;
                }
                case 18: {
                    this.physics.isAffectedByGravity = true;
                    this.physics.velocity.x = 4.0f;
                    break;
                }
                case 19: {
                    this.physics.velocity.x = 0.0f;
                    break;
                }
                default: {
                    this.physics.isAffectedByGravity = true;
                }
            }
        }

        public void updateAI(PhysicsUnitSystem.PhysicsBody physicsBody) {
            if (this.aiBehavior != null) {
                AnimationState animationState = this.aiBehavior.updateBehavior(physicsBody.position);
                this.transitionTo(animationState);
            } else {
                float f = Math.abs(this.physics.position.x - physicsBody.position.x);
                if (f < this.detectionRadius) {
                    if (!this.isAlerted) {
                        this.isAlerted = true;
                        this.physics.velocity.x *= 1.25f;
                    }
                    this.transitionTo(AnimationState.ENEMY_CHASE);
                } else {
                    if (this.isAlerted) {
                        this.isAlerted = false;
                        this.physics.velocity.x *= 0.8f;
                    }
                    this.transitionTo(AnimationState.ENEMY_IDLE);
                }
            }
        }

        public boolean isAlerted() {
            return this.isAlerted;
        }

        public float getDetectionRadius() {
            return this.detectionRadius;
        }

        public static String getEnemyAssetPath(EnemyType enemyType, String string) {
            switch (enemyType.ordinal()) {
                case 0: {
                    return "Resources/industrial-zone/characters/enemies/drones/1/" + EnemyController.getUfoSaucerAsset(string);
                }
                case 1: {
                    return "Resources/industrial-zone/characters/enemies/drones/2/" + EnemyController.getJetDroneAsset(string);
                }
                case 2: {
                    return "Resources/industrial-zone/characters/enemies/drones/3/" + EnemyController.getTransportDroneAsset(string);
                }
                case 3: {
                    return "Resources/industrial-zone/characters/enemies/punks/" + EnemyController.getPunkAsset(string);
                }
                case 4: {
                    return "Resources/industrial-zone/characters/enemies/rugby/" + EnemyController.getRugbyPlayerAsset(string);
                }
            }
            return "Resources/industrial-zone/characters/enemies/drones/1/" + EnemyController.getUfoSaucerAsset(string);
        }

        private static String getUfoSaucerAsset(String string) {
            switch (string.toUpperCase()) {
                case "IDLE": {
                    return "01_EnemyDrone_UfoSaucer_Idle_3Frames1Row_HoveringStationary_DefaultIdle_Loop_150ms.png";
                }
                case "WALK": {
                    return "02_EnemyDrone_UfoSaucer_Movement_4Frames1Row_SmoothHoveringMove_Movement_Loop_100ms.png";
                }
                case "CHASE": {
                    return "03_EnemyDrone_UfoSaucer_Chase_4Frames1Row_FastHoveringPursuit_Chase_Loop_80ms.png";
                }
                case "ATTACK": {
                    return "04_EnemyDrone_UfoSaucer_Attack_3Frames1Row_EnergyBeamCharge_Attack_PlayOnce_100ms.png";
                }
                case "HURT": {
                    return "04_EnemyDrone_UfoSaucer_Attack_3Frames1Row_EnergyBeamCharge_Attack_PlayOnce_100ms.png";
                }
                case "DEATH": {
                    return "05_EnemyDrone_UfoSaucer_Death_4Frames1Row_ExplosionDisappear_Death_PlayOnce_120ms.png";
                }
            }
            return "01_EnemyDrone_UfoSaucer_Idle_3Frames1Row_HoveringStationary_DefaultIdle_Loop_150ms.png";
        }

        private static String getJetDroneAsset(String string) {
            switch (string.toUpperCase()) {
                case "IDLE": {
                    return "01_EnemyDrone_JetDrone_Idle_Hovering_FastMovingUnit_Loop_150ms.png";
                }
                case "WALK": {
                    return "02_EnemyDrone_JetDrone_Flight_QuickZoom_HighSpeedMovement_Loop_80ms.png";
                }
                case "CHASE": {
                    return "03_EnemyDrone_JetDrone_Pursuit_ZoomRush_InterceptTarget_Loop_60ms.png";
                }
                case "ATTACK": {
                    return "04_EnemyDrone_JetDrone_BeamAttack_MissileShot_RangedHit_PlayOnce_100ms.png";
                }
                case "HURT": {
                    return "05_EnemyDrone_JetDrone_ReactDamage_JetFlutter_ShieldFlash_PlayOnce_100ms.png";
                }
                case "DEATH": {
                    return "06_EnemyDrone_JetDrone_Explode_CrashBurn_Destruction_PlayOnce_120ms.png";
                }
            }
            return "01_EnemyDrone_JetDrone_Idle_Hovering_FastMovingUnit_Loop_150ms.png";
        }

        private static String getTransportDroneAsset(String string) {
            switch (string.toUpperCase()) {
                case "IDLE": {
                    return "01_EnemyDrone_TransportDrone_Idle_Stationary_CargoHold_Loop_200ms.png";
                }
                case "WALK": {
                    return "02_EnemyDrone_TransportDrone_Lumber_SlowMovement_HeavyLoad_Loop_120ms.png";
                }
                case "CHASE": {
                    return "03_EnemyDrone_TransportDrone_Pursue_ModerateSpeed_LoadedPursuit_Loop_100ms.png";
                }
                case "ATTACK": {
                    return "04_EnemyDrone_TransportDrone_UnloadAttack_CargoRelease_AreaDamage_PlayOnce_110ms.png";
                }
                case "HURT": {
                    return "05_EnemyDrone_TransportDrone_DamageTaken_ShakeLurch_Rumble_PlayOnce_100ms.png";
                }
                case "DEATH": {
                    return "06_EnemyDrone_TransportDrone_CrashLanding_Explosion_DebrisSpray_PlayOnce_150ms.png";
                }
            }
            return "01_EnemyDrone_TransportDrone_Idle_Stationary_CargoHold_Loop_200ms.png";
        }

        private static String getPunkAsset(String string) {
            switch (string.toUpperCase()) {
                case "IDLE": {
                    return "01_Enemy_Punk_Idle_2Frames_AggressiveStance_WaitingFight_Loop_150ms.png";
                }
                case "WALK": {
                    return "02_Enemy_Punk_Walk_4Frames_RegularPace_PatrolMovement_Loop_120ms.png";
                }
                case "CHASE": {
                    return "03_Enemy_Punk_Run_6Frames_SprintAttacking_PursuitRun_Loop_80ms.png";
                }
                case "ATTACK": {
                    return "04_Enemy_Punk_Punch_5Frames_ComboStrike_MeleeBeat_PlayOnce_80ms.png";
                }
                case "HURT": {
                    return "05_Enemy_Punk_TakeDamage_3Frames_Stagger_DamageFlinch_PlayOnce_100ms.png";
                }
                case "DEATH": {
                    return "06_Enemy_Punk_Knockout_4Frames_FallSequence_Collapse_PlayOnce_120ms.png";
                }
            }
            return "01_Enemy_Punk_Idle_2Frames_AggressiveStance_WaitingFight_Loop_150ms.png";
        }

        private static String getRugbyPlayerAsset(String string) {
            switch (string.toUpperCase()) {
                case "IDLE": {
                    return "01_Enemy_RugbyPlayer_Idle_3Frames_PowerStance_ReadyCharge_Loop_150ms.png";
                }
                case "WALK": {
                    return "02_Enemy_RugbyPlayer_Walk_4Frames_HeavyStep_GroundShake_Loop_120ms.png";
                }
                case "CHASE": {
                    return "03_Enemy_RugbyPlayer_Charge_5Frames_RushAttack_FullSpeedCharge_Loop_90ms.png";
                }
                case "ATTACK": {
                    return "04_Enemy_RugbyPlayer_Tackle_6Frames_BodySlam_GroundImpact_PlayOnce_90ms.png";
                }
                case "HURT": {
                    return "05_Enemy_RugbyPlayer_Stun_3Frames_KnockBack_DamageStagger_PlayOnce_100ms.png";
                }
                case "DEATH": {
                    return "06_Enemy_RugbyPlayer_Defeat_5Frames_FallDown_FinalCollapse_PlayOnce_120ms.png";
                }
            }
            return "01_Enemy_RugbyPlayer_Idle_3Frames_PowerStance_ReadyCharge_Loop_150ms.png";
        }

        public static enum EnemyType {
            UFO_SAUCER,
            JET_DRONE,
            TRANSPORT_DRONE,
            PUNK,
            RUGBY_PLAYER;

        }
    }

    public static class BossController
    extends EntityAnimationController {
        private float healthPercent = 1.0f;
        private int attackPatternIndex = 0;
        private AIBehavior aiBehavior = null;

        public BossController(PhysicsUnitSystem.PhysicsBody physicsBody) {
            super(physicsBody);
        }

        public void setAIBehavior(AIBehavior aIBehavior) {
            this.aiBehavior = aIBehavior;
        }

        @Override
        protected void initializeAssets() {
            this.stateToAssetPath.put(AnimationState.BOSS_IDLE, "Resources/industrial-zone/characters/bosses/GreenMech/01_Boss_GreenMech_Idle_4Frames1Row_MechStandingStationary_DefaultIdle_Loop_150ms.png");
            this.stateToAssetPath.put(AnimationState.BOSS_ATTACK_PHASE1, "Resources/industrial-zone/characters/bosses/GreenMech/07_Boss_GreenMech_Attack_DirectStrike_MeleePhase1_PlayOnce_90ms.png");
            this.stateToAssetPath.put(AnimationState.BOSS_ATTACK_PHASE2, "Resources/industrial-zone/characters/bosses/GreenMech/08_Boss_GreenMech_Attack2_AdvancedCombo_MeleePhase2_PlayOnce_80ms.png");
            this.stateToAssetPath.put(AnimationState.BOSS_SPECIAL, "Resources/industrial-zone/characters/bosses/GreenMech/06_Boss_GreenMech_Charge_PoweringUp_SpecialAttack_PlayOnce_100ms.png");
            this.stateToAssetPath.put(AnimationState.BOSS_WEAK, "Resources/industrial-zone/characters/bosses/GreenMech/09_Boss_GreenMech_Hit_DamageTaken_BossWeak_PlayOnce_100ms.png");
            this.stateToAssetPath.put(AnimationState.BOSS_DEATH, "Resources/industrial-zone/characters/bosses/GreenMech/10_Boss_GreenMech_Death_MechCollapse_BossDeath_PlayOnce_120ms.png");
        }

        @Override
        protected void initializeTransitions() {
        }

        @Override
        protected void updatePhysicsForState(AnimationState animationState, float f) {
            switch (animationState.ordinal()) {
                case 23: 
                case 24: 
                case 25: {
                    this.physics.velocity.x = 0.0f;
                    break;
                }
                case 22: {
                    this.physics.velocity.x = 0.0f;
                    break;
                }
            }
        }

        public void updateBehavior(PhysicsUnitSystem.Vector2D vector2D) {
            if (this.aiBehavior != null) {
                AnimationState animationState = this.aiBehavior.updateBehavior(vector2D);
                this.transitionTo(animationState);
            } else {
                int n = 0;
                n = this.healthPercent > 0.5f ? 0 : (this.healthPercent > 0.25f ? 1 : 2);
                this.attackPatternIndex = (this.attackPatternIndex + 1) % 3;
                switch (n) {
                    case 0: {
                        if (this.attackPatternIndex == 0) {
                            this.transitionTo(AnimationState.BOSS_ATTACK_PHASE1);
                            break;
                        }
                        this.transitionTo(AnimationState.BOSS_IDLE);
                        break;
                    }
                    case 1: {
                        if (this.attackPatternIndex <= 1) {
                            this.transitionTo(AnimationState.BOSS_ATTACK_PHASE2);
                            break;
                        }
                        this.transitionTo(AnimationState.BOSS_IDLE);
                        break;
                    }
                    case 2: {
                        if (this.attackPatternIndex == 0) {
                            this.transitionTo(AnimationState.BOSS_SPECIAL);
                            break;
                        }
                        if (this.attackPatternIndex == 1) {
                            this.transitionTo(AnimationState.BOSS_ATTACK_PHASE2);
                            break;
                        }
                        this.transitionTo(AnimationState.BOSS_IDLE);
                    }
                }
            }
        }

        public void takeDamage(float f) {
            this.healthPercent -= f;
            if (this.healthPercent < 0.0f) {
                this.healthPercent = 0.0f;
            }
            this.transitionTo(AnimationState.BOSS_WEAK);
        }

        public int getAttackPatternIndex() {
            return this.attackPatternIndex;
        }

        public float getHealthPercent() {
            return this.healthPercent;
        }

        public boolean isPhase2() {
            return this.healthPercent <= 0.5f && this.healthPercent > 0.25f;
        }

        public boolean isPhase3() {
            return this.healthPercent <= 0.25f;
        }

        public static String getBossAssetPath(BossType bossType, String string) {
            String string2 = AnimationAndSpriteLoader.BOSS_BASE;
            switch (bossType.ordinal()) {
                case 0: {
                    return string2 + "GreenMech/" + BossController.getGreenMechAsset(string);
                }
                case 1: {
                    return string2 + "GolfCartSoldier/" + BossController.getGolfCartSoldierAsset(string);
                }
                case 2: {
                    return string2 + "RugbyGuy/" + BossController.getRugbyGuyAsset(string);
                }
            }
            return string2 + "GreenMech/" + BossController.getGreenMechAsset(string);
        }

        private static String getGreenMechAsset(String string) {
            switch (string.toUpperCase()) {
                case "IDLE": {
                    return "01_Boss_GreenMech_Idle_4Frames1Row_MechStandingStationary_DefaultIdle_Loop_150ms.png";
                }
                case "ATTACK1": {
                    return "07_Boss_GreenMech_Attack_DirectStrike_MeleePhase1_PlayOnce_90ms.png";
                }
                case "ATTACK2": {
                    return "08_Boss_GreenMech_Attack2_AdvancedCombo_MeleePhase2_PlayOnce_80ms.png";
                }
                case "CHARGE": {
                    return "06_Boss_GreenMech_Charge_PoweringUp_SpecialAttack_PlayOnce_100ms.png";
                }
                case "HIT": {
                    return "09_Boss_GreenMech_Hit_DamageTaken_BossWeak_PlayOnce_100ms.png";
                }
                case "DEATH": {
                    return "10_Boss_GreenMech_Death_MechCollapse_BossDeath_PlayOnce_120ms.png";
                }
            }
            return "01_Boss_GreenMech_Idle_4Frames1Row_MechStandingStationary_DefaultIdle_Loop_150ms.png";
        }

        private static String getGolfCartSoldierAsset(String string) {
            switch (string.toUpperCase()) {
                case "IDLE": {
                    return "01_Boss_GolfCartSoldier_Idle_[STATEINDEX]_Aiming_RangedIdle_Loop_150ms.png";
                }
                case "ATTACK1": {
                    return "03_Boss_GolfCartSoldier_Attack_SingleShot_RangedAttack_PlayOnce_100ms.png";
                }
                case "ATTACK2": {
                    return "04_Boss_GolfCartSoldier_Attack2_DoubleShot_RangedPhase2_PlayOnce_90ms.png";
                }
                case "CHARGE": {
                    return "05_Boss_GolfCartSoldier_Charge_AmmoPack_SpecialAttack_PlayOnce_120ms.png";
                }
                case "HIT": {
                    return "08_Boss_GolfCartSoldier_Hit_ShieldFlash_DamageSoak_PlayOnce_100ms.png";
                }
                case "DEATH": {
                    return "11_Boss_GolfCartSoldier_Death_VehicleExplosion_BossDeath_PlayOnce_150ms.png";
                }
            }
            return "01_Boss_GolfCartSoldier_Idle_1_Aiming_RangedIdle_Loop_150ms.png";
        }

        private static String getRugbyGuyAsset(String string) {
            switch (string.toUpperCase()) {
                case "IDLE": {
                    return "01_Boss_RugbyGuy_Idle_3Frames_PowerStance_ReadyForRush_Loop_150ms.png";
                }
                case "ATTACK1": {
                    return "02_Boss_RugbyGuy_Charge_OnRush_MeleeCharge_PlayOnce_70ms.png";
                }
                case "ATTACK2": {
                    return "03_Boss_RugbyGuy_GrappleStart_TackleInitiate_GrappleMelee_PlayOnce_80ms.png";
                }
                case "CHARGE": {
                    return "04_Boss_RugbyGuy_PowerUp_MuscleTense_SpecialRush_PlayOnce_100ms.png";
                }
                case "HIT": {
                    return "05_Boss_RugbyGuy_Knockback_StaggerReaction_DamageTaken_PlayOnce_100ms.png";
                }
                case "DEATH": {
                    return "06_Boss_RugbyGuy_Collapse_FinalBlow_BossDeath_PlayOnce_120ms.png";
                }
            }
            return "01_Boss_RugbyGuy_Idle_3Frames_PowerStance_ReadyForRush_Loop_150ms.png";
        }

        public static enum BossType {
            GREEN_MECH,
            GOLF_CART_SOLDIER,
            RUGBY_GUY;

        }
    }

    public static class TileRegistry {
        private static final Map<Character, String> REGISTRY = new TreeMap<Character, String>();

        public static String getTile(char c) {
            return REGISTRY.getOrDefault(Character.valueOf(c), null);
        }

        public static Set<Character> getAllCodes() {
            return REGISTRY.keySet();
        }

        public static boolean hasTile(char c) {
            return REGISTRY.containsKey(Character.valueOf(c));
        }

        public static int getTileCount() {
            return REGISTRY.size();
        }

        static {
            REGISTRY.put(Character.valueOf('A'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/01_Platform_SolidBlock_FlatTopFull_DarkPurple_PrimaryWalkableFloorTile.png");
            REGISTRY.put(Character.valueOf('P'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/16_Platform_SolidBlock_FlatTop_FaintDotRivetPattern_WornIndustrialFloorFill.png");
            REGISTRY.put(Character.valueOf('C'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/03_Platform_SolidBlock_FlatTopMid_MutedBluePurple_StandardFloorFill.png");
            REGISTRY.put(Character.valueOf('U'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/21_Structure_SolidBlock_LargeFullFlat_HeavyDarkTone_HeavyStructuralWallOrFloor.png");
            REGISTRY.put(Character.valueOf('V'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/22_Structure_SolidBlock_SmallNotchTopRight_EdgeDetailAccent_StructuralTransitionCap.png");
            REGISTRY.put(Character.valueOf('E'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/05_Structure_SolidBlock_SmallStampTopRight_OffsetAccent_ArchitecturalDetailFill.png");
            REGISTRY.put(Character.valueOf('D'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/04_Corner_InnerTopRight_LShapeCutout_SolidEdge_WallMeetsFloorJoinTopRight.png");
            REGISTRY.put(Character.valueOf('F'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/06_Corner_InnerTopLeft_NotchedTopLeft_SolidEdge_WallMeetsFloorJoinTopLeft.png");
            REGISTRY.put(Character.valueOf('J'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/10_Corner_ExteriorTopRight_SmallDarkSquare_OffsetTopRight_WallTopRightCapAccent.png");
            REGISTRY.put(Character.valueOf('T'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/20_Corner_ExteriorTopRight_SmallTwoToneSquare_OffsetAccent_WallTopEdgeDecoCap.png");
            REGISTRY.put(Character.valueOf('S'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/19_Corner_DiagonalHalfBlock_TopRightToBottomLeft_TwoToneBlue_SlopedTransitionOrDeco.png");
            REGISTRY.put(Character.valueOf('X'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/24_Corner_DiagonalHalfBlock_BottomRightToTopLeft_LargeSplit_SlopedSurfaceTransition.png");
            REGISTRY.put(Character.valueOf('b'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/28_Corner_DiagonalHalfBlock_BottomLeftToTopRight_PointingRight_RightFacingSlopeBlock.png");
            REGISTRY.put(Character.valueOf('c'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/29_Corner_DiagonalHalfBlock_TopLeftToBottomRight_PointingDown_LeftFacingSlopeBlock.png");
            REGISTRY.put(Character.valueOf('l'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/38_Corner_DiagonalHalfBlock_BottomLeftToTopRight_DarkPurpleTone_SlopedSurfaceTransition.png");
            REGISTRY.put(Character.valueOf('d'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/30_Corner_SmallDiagonal_TopRightCornerOnly_MinorSlopedAccent_TransitionOrDecoDetail.png");
            REGISTRY.put(Character.valueOf('2'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/55_Edge_SmallMutedCornerCap_TopRightAligned_MutedTone_MinimalTransitionEdgeDetail.png");
            REGISTRY.put(Character.valueOf('Y'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/25_Edge_MinimalCornerCap_ThinStripTopRight_SmallSquareAccent_CornerEdgeDetailTile.png");
            REGISTRY.put(Character.valueOf('H'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/08_Wall_VerticalColumn_NarrowCentreAligned_TallRectangle_ShaftOrPillarMidFill.png");
            REGISTRY.put(Character.valueOf('M'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/13_Wall_ThinVerticalColumn_NarrowCentreStrip_TallSlender_PipeCoverOrWallDivider.png");
            REGISTRY.put(Character.valueOf('O'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/15_Wall_VerticalEdgeStrip_NarrowRightAligned_TallDarkBar_RightWallEdgeCapTile.png");
            REGISTRY.put(Character.valueOf('t'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/46_Wall_VerticalEdgeStrip_NarrowLeftAligned_PurpleTallBar_LeftWallEdgeCapTile.png");
            REGISTRY.put(Character.valueOf('G'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/07_Panel_GridSurface_2x2QuadDivided_FlatIndustrialFace_WallOrFloorPanelFill.png");
            REGISTRY.put(Character.valueOf('K'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/11_Panel_GridSurface_2x2LargeGrid_ProminentDividers_LargeIndustrialWallOrFloorPanel.png");
            REGISTRY.put(Character.valueOf('L'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/12_Panel_DetailBlock_RectangularInsetTopRight_IndustrialSurface_WallOrFloorFillDetail.png");
            REGISTRY.put(Character.valueOf('N'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/14_Panel_InsetDetail_SmallSquareEmbeddedTopRight_RecessedAccent_DecorativeWallFill.png");
            REGISTRY.put(Character.valueOf('Q'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/17_Panel_InsetDetail_SmallSquareEmbeddedBottomCentre_RecessedAccent_LowerWallFill.png");
            REGISTRY.put(Character.valueOf('u'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/47_Panel_HorizontalStripeBlock_EvenSpacedLines_FullSquare_WallFillOrFloorStripePanel.png");
            REGISTRY.put(Character.valueOf('3'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/56_Panel_InsetDetail_SmallSquareEmbeddedLeftCentre_RecessedAccent_LeftWallDetailPanel.png");
            REGISTRY.put(Character.valueOf('4'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/57_Panel_InsetDetail_SmallSquareEmbeddedRightCentre_RecessedAccent_RightWallDetailPanel.png");
            REGISTRY.put(Character.valueOf('8'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/61_Panel_TechControlDetail_SmallInsetDotCentred_BlueGreyTone_MachineryControlPanelWall.png");
            REGISTRY.put(Character.valueOf('@'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/64_Panel_InsetDetail_MediumSquareEmbeddedCentre_RecessedLarger_CentralMachineDetailPanel.png");
            REGISTRY.put(Character.valueOf('R'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/18_Edge_HorizontalShelfBar_NarrowCentreAligned_FlatTop_LedgeSurfaceOrPlatformEdge.png");
            REGISTRY.put(Character.valueOf('Z'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/26_Edge_BoltedShelfLedge_NarrowHorizontal_BoltRivetSides_SupportedIndustrialLedge.png");
            REGISTRY.put(Character.valueOf('a'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/27_Edge_WideLedgeBar_HorizontalWideFlat_FlatWalkableTop_LedgePlatformTransition.png");
            REGISTRY.put(Character.valueOf('W'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/23_Edge_BracketShelf_NarrowHorizontalBar_BottomCentreNotch_IndustrialSupportBracket.png");
            REGISTRY.put(Character.valueOf('v'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/48_Edge_GapStripeBar_NarrowHorizontalStrip_WideGapLinePattern_PipeEdgeOrLedgeAccent.png");
            REGISTRY.put(Character.valueOf('B'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/02_Hazard_BreakableBlock_LargeXCrosshatch_PurpleOnDark_WarningSurfaceOrDestr.png");
            REGISTRY.put(Character.valueOf('I'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/09_Hazard_WarningSurface_SingleDiagonalRedStripe_BlueBase_ContactDamageHazar.png");
            REGISTRY.put(Character.valueOf('e'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/31_Hazard_FullStripeBlock_DiagonalRedOrangeStripes_FullCoverage_ContactDamageHazardFloor.png");
            REGISTRY.put(Character.valueOf('f'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/32_Hazard_FullStripeBlock_DiagonalAltAngleStripes_FullCoverage_AlternateHazardSurface.png");
            REGISTRY.put(Character.valueOf('g'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/33_Hazard_ZigzagCrisscrossStripe_MultiDiagonalPattern_FullCoverage_IntensiveHazardZone.png");
            REGISTRY.put(Character.valueOf('h'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/34_Hazard_WideSpacedStripeBlock_DiagonalWideGap_ContactDamage_ModerateHazardBarrier.png");
            REGISTRY.put(Character.valueOf('i'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/35_Hazard_DenseStripeBlock_DiagonalDensePacking_ContactDamage_HighDensityHazardSurface.png");
            REGISTRY.put(Character.valueOf('j'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/36_Hazard_CrosshatchXBlock_LargeBoldXPattern_ContactDamageOrBarrier_NoGoZoneWarning.png");
            REGISTRY.put(Character.valueOf('n'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/40_Hazard_PartialStripeBlock_DenseRedStripesRightSide_ContactDamage_HalfHazardTransition.png");
            REGISTRY.put(Character.valueOf('o'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/41_Hazard_FullStripeBlock_DiagonalOrangeRedFull_ContactDamage_HazardFloorSurface.png");
            REGISTRY.put(Character.valueOf('p'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/42_Hazard_CrossedStripeBlock_DiagonalNarrowCrossed_ContactDamage_HazardBarrierVariant.png");
            REGISTRY.put(Character.valueOf('q'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/43_Hazard_WideGapStripeBlock_DiagonalLargeSpacing_ContactDamage_HazardZoneMarkerTile.png");
            REGISTRY.put(Character.valueOf('r'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/44_Hazard_ThinLineStripeBlock_DiagonalFineLines_ContactDamage_LightHazardWarningMarker.png");
            REGISTRY.put(Character.valueOf('s'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/45_Corner_ExteriorTopRight_SmallWarmRedOrange_HazardTone_HazardZoneTopRightCornerCap.png");
            REGISTRY.put(Character.valueOf('w'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/49_Hazard_MediumStripeBlock_DiagonalMediumSpacing_ContactDamage_MidIntensityHazardZone.png");
            REGISTRY.put(Character.valueOf('x'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/50_Hazard_BrightStripeBlock_DiagonalVividBright_ContactDamage_HighVisibilityHazardZone.png");
            REGISTRY.put(Character.valueOf('y'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/51_Hazard_DenseOrangeStripe_DiagonalDenseOrangePacked_ContactDamage_HotHazardSurface.png");
            REGISTRY.put(Character.valueOf('z'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/52_Hazard_AltAngleStripeBlock_DiagonalVariantAngle_ContactDamage_AltHazardFloorVariant.png");
            REGISTRY.put(Character.valueOf('5'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/58_Hazard_FullStripeBlock_DiagonalRedOrangeVariantB_ContactDamage_HazardFloorVariantB.png");
            REGISTRY.put(Character.valueOf('6'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/59_Hazard_NarrowOrangeStripeBlock_DiagonalTightSpacing_ContactDamage_TightPackedHazard.png");
            REGISTRY.put(Character.valueOf('7'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/60_Hazard_WideOrangeStripeBlock_DiagonalLooseSpacing_ContactDamage_WideSpacedHazardZone.png");
            REGISTRY.put(Character.valueOf('0'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/53_Hazard_ElectricEnergyStripe_VerticalLightningLine_InstantDamage_ElectricShockHazard.png");
            REGISTRY.put(Character.valueOf('1'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/54_Hazard_GlowingEnergyBar_VerticalGradientBrightCentre_InstantDamage_EnergyBeamHazard.png");
            REGISTRY.put(Character.valueOf('!'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/63_Hazard_EnergyBarrierStrip_TallNarrowBrightGradientGlow_InstantDamage_VerticalEnergyBarrier.png");
            REGISTRY.put(Character.valueOf('k'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/37_Deco_CircleMarker_LargeSolidCircleCentred_PurpleOnDark_ButtonPortalInteractiveDeco.png");
            REGISTRY.put(Character.valueOf('m'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/39_Deco_CircleMarker_SolidCircleCentredAltShade_SlightlyDifferentPurple_PickupOrDecoSpot.png");
            REGISTRY.put(Character.valueOf('9'), "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/62_Deco_DiamondXGridPattern_SmallScaleRepeating_PurpleOnDark_DecorativeWallOrFloorFill.png");
        }
    }

    public static class PresetFrameSets {
        public static FrameTileBuilder createStandardBlueFrame() {
            return new FrameTileBuilder("StandardBlue").setCornerTopLeft("01_GUI_Frame_CornerTopLeft_TallLShapePiece_WindowCorner.png").setCornerTopRight("03_GUI_Frame_CornerTopRight_TallLShapeMirror_WindowCorner.png").setCornerBottomLeft("19_GUI_Frame_CornerBottomLeft_LShapeCorner_WindowCorner.png").setCornerBottomRight("27_GUI_Frame_CornerBottomRight_DiagonalAngleTrim_WindowCorner.png").setEdgeTop("02_GUI_Frame_EdgeTopBar_HorizontalBlueAccentStrip_WindowTopEdge.png").setEdgeBottom("20_GUI_Frame_EdgeBottomBar_PlainDarkStrip_WindowBottomEdge.png").setEdgeLeft("05_GUI_Frame_EdgeLeftStrip_TallNarrowVerticalBar_WindowLeftEdge.png").setEdgeRight("06_GUI_Frame_EdgeRightStrip_TallNarrowVerticalBar_WindowRightEdge.png").setInterior("07_GUI_Frame_FillSolidNavy_LargeFullBlock_WindowFill.png");
        }

        public static FrameTileBuilder createInsetBorderFrame() {
            return new FrameTileBuilder("InsetBorder").setCornerTopLeft("09_GUI_Frame_CornerInsetTopLeft_InsetBorderSquare_WindowCorner.png").setCornerTopRight("30_GUI_Frame_CornerTopRightFull_BracketTShape_WindowCorner.png").setCornerBottomLeft("21_GUI_Frame_CornerBottomLeftFull_InsetSquareCorner_WindowCorner.png").setCornerBottomRight("29_GUI_Frame_CornerBottomRightFull_BracketCorner_WindowCorner.png").setEdgeTop("36_GUI_Frame_EdgeTopBarVariant_LighterStripe_WindowTopEdge.png").setEdgeBottom("24_GUI_Frame_EdgeBottomBar_DotLineTexture_WindowBottomEdge.png").setEdgeLeft("15_GUI_Frame_EdgeLeftStrip_TallLighterBlueTrim_WindowLeftEdge.png").setEdgeRight("28_GUI_Frame_EdgeRightStrip_TallBlueAccent_WindowRightEdge.png").setInterior("11_GUI_Frame_FillDiagonalTexture_FaintDiagLines_WindowFill.png");
        }

        public static FrameTileBuilder createDarkTechFrame() {
            return new FrameTileBuilder("DarkTech").setCornerTopLeft("32_GUI_Frame_CornerTopLeft_TriangleCut_WindowCorner.png").setCornerTopRight("34_GUI_Frame_CornerTopRight_DiagonalCut_WindowCorner.png").setCornerBottomLeft("73_GUI_Frame_CornerBottomLeft_PlainCorner_WindowCorner.png").setCornerBottomRight("78_GUI_Frame_CornerBottomRight_PlainCorner_WindowCorner.png").setEdgeTop("67_GUI_Frame_EdgeTopBar_PlainBar_WindowTopEdge.png").setEdgeBottom("75_GUI_Frame_EdgeBottomBar_PlainHorizStrip_WindowBottomEdge.png").setEdgeLeft("12_GUI_Frame_EdgeLeftStrip_TallDarkerBar_WindowLeftEdge.png").setEdgeRight("22_GUI_Frame_EdgeRightStrip_TallNarrowPlain_WindowRightEdge.png").setInterior("40_GUI_Frame_FillSolidDarkNavy_FullBlock_WindowFill.png");
        }

        public static FrameTileBuilder createCyanAccentFrame() {
            return new FrameTileBuilder("CyanAccent").setCornerTopLeft("04_GUI_Frame_CornerTopLeftRivet_RedDotAccents_WindowCorner.png").setCornerTopRight("31_GUI_Frame_CornerTopRight_SmallInsetLightTrim_WindowCorner.png").setCornerBottomLeft("76_GUI_Frame_CornerBottomSmallNotch_BottomNotchCorner_WindowCorner.png").setCornerBottomRight("81_GUI_Frame_CornerTopRight_LightTrimCorner_WindowCorner.png").setEdgeTop("44_GUI_Frame_EdgeHorizBar_DenseDotLineTexture_WindowEdge.png").setEdgeBottom("54_GUI_Frame_EdgeBottomBar_LightBlueAccentLine_WindowBottomEdge.png").setEdgeLeft("10_GUI_Frame_EdgeLeftStrip_TallBlueTintBar_WindowLeftEdge.png").setEdgeRight("18_GUI_Frame_EdgeRightStrip_TallBlueAccentLine_WindowRightEdge.png").setInterior("38_GUI_Frame_FillSolidNavy_WideRectNoBorder_WindowFill.png");
        }

        public static FrameTileBuilder createDiagonalEdgeFrame() {
            return new FrameTileBuilder("DiagonalEdge").setCornerTopLeft("64_GUI_Frame_CornerDiagonalCutTopLeft_WindowCorner.png").setCornerTopRight("33_GUI_Frame_CornerTopRight_TriangleCutMirror_WindowCorner.png").setCornerBottomLeft("73_GUI_Frame_CornerBottomLeft_PlainCorner_WindowCorner.png").setCornerBottomRight("78_GUI_Frame_CornerBottomRight_PlainCorner_WindowCorner.png").setEdgeTop("02_GUI_Frame_EdgeTopBar_HorizontalBlueAccentStrip_WindowTopEdge.png").setEdgeBottom("50_GUI_Frame_EdgeBottomBar_HorizStripTexture_WindowBottomEdge.png").setEdgeLeft("47_GUI_Frame_EdgeDiagonalStrip_AngledTopLeftSlant_WindowEdge.png").setEdgeRight("56_GUI_Frame_EdgeDiagonalStrip_AngledLineTexture_WindowEdge.png").setInterior("45_GUI_Frame_FillSolidNavy_PlainFlatBlock_WindowFill.png");
        }

        public static FrameTileBuilder createHexagonalFrame() {
            return new FrameTileBuilder("Hexagonal").setCornerTopLeft("63_GUI_Frame_CornerHexagonal_LightGreyOctagonShape_WindowCorner.png").setCornerTopRight("30_GUI_Frame_CornerTopRightFull_BracketTShape_WindowCorner.png").setCornerBottomLeft("21_GUI_Frame_CornerBottomLeftFull_InsetSquareCorner_WindowCorner.png").setCornerBottomRight("29_GUI_Frame_CornerBottomRightFull_BracketCorner_WindowCorner.png").setEdgeTop("67_GUI_Frame_EdgeTopBar_PlainBar_WindowTopEdge.png").setEdgeBottom("75_GUI_Frame_EdgeBottomBar_PlainHorizStrip_WindowBottomEdge.png").setEdgeLeft("13_GUI_Frame_EdgeLeftStrip_TallThinLighter_WindowLeftEdge.png").setEdgeRight("55_GUI_Frame_EdgeRightStrip_NarrowSlightlyWider_WindowRightEdge.png").setInterior("46_GUI_Frame_FillSolidNavy_WideFlatRect_WindowFill.png");
        }

        public static FrameTileBuilder createSimpleCleanFrame() {
            return new FrameTileBuilder("SimpleClean").setCornerTopLeft("73_GUI_Frame_CornerBottomLeft_PlainCorner_WindowCorner.png").setCornerTopRight("78_GUI_Frame_CornerBottomRight_PlainCorner_WindowCorner.png").setCornerBottomLeft("19_GUI_Frame_CornerBottomLeft_LShapeCorner_WindowCorner.png").setCornerBottomRight("27_GUI_Frame_CornerBottomRight_DiagonalAngleTrim_WindowCorner.png").setEdgeTop("36_GUI_Frame_EdgeTopBarVariant_LighterStripe_WindowTopEdge.png").setEdgeBottom("54_GUI_Frame_EdgeBottomBar_LightBlueAccentLine_WindowBottomEdge.png").setEdgeLeft("43_GUI_Frame_EdgeLeftStrip_NarrowDarkVertical_WindowLeftEdge.png").setEdgeRight("48_GUI_Frame_EdgeRightStrip_NarrowVerticalBar_WindowRightEdge.png").setInterior("66_GUI_Frame_FillSolidNavyLighter_SlightlyLighter_WindowFill.png");
        }

        public static FrameTileBuilder createRivetIndustrialFrame() {
            return new FrameTileBuilder("RivetIndustrial").setCornerTopLeft("04_GUI_Frame_CornerTopLeftRivet_RedDotAccents_WindowCorner.png").setCornerTopRight("31_GUI_Frame_CornerTopRight_SmallInsetLightTrim_WindowCorner.png").setCornerBottomLeft("21_GUI_Frame_CornerBottomLeftFull_InsetSquareCorner_WindowCorner.png").setCornerBottomRight("29_GUI_Frame_CornerBottomRightFull_BracketCorner_WindowCorner.png").setEdgeTop("02_GUI_Frame_EdgeTopBar_HorizontalBlueAccentStrip_WindowTopEdge.png").setEdgeBottom("24_GUI_Frame_EdgeBottomBar_DotLineTexture_WindowBottomEdge.png").setEdgeLeft("14_GUI_Frame_EdgeLeftStrip_TallWiderWithTrim_WindowLeftEdge.png").setEdgeRight("18_GUI_Frame_EdgeRightStrip_TallBlueAccentLine_WindowRightEdge.png").setInterior("65_GUI_Frame_FillSolidDarkNavy_PlainBlock_WindowFill.png");
        }
    }

    public static class FrameTileBuilder {
        private String frameName;
        private String cornerTopLeft;
        private String cornerTopRight;
        private String cornerBottomLeft;
        private String cornerBottomRight;
        private String edgeTop;
        private String edgeBottom;
        private String edgeLeft;
        private String edgeRight;
        private String interior;
        private BufferedImage imgCornerTL;
        private BufferedImage imgCornerTR;
        private BufferedImage imgCornerBL;
        private BufferedImage imgCornerBR;
        private BufferedImage imgEdgeTop;
        private BufferedImage imgEdgeBottom;
        private BufferedImage imgEdgeLeft;
        private BufferedImage imgEdgeRight;
        private BufferedImage imgInterior;

        public FrameTileBuilder(String string) {
            this.frameName = string;
        }

        public FrameTileBuilder setCornerTopLeft(String string) {
            this.cornerTopLeft = string;
            return this;
        }

        public FrameTileBuilder setCornerTopRight(String string) {
            this.cornerTopRight = string;
            return this;
        }

        public FrameTileBuilder setCornerBottomLeft(String string) {
            this.cornerBottomLeft = string;
            return this;
        }

        public FrameTileBuilder setCornerBottomRight(String string) {
            this.cornerBottomRight = string;
            return this;
        }

        public FrameTileBuilder setEdgeTop(String string) {
            this.edgeTop = string;
            return this;
        }

        public FrameTileBuilder setEdgeBottom(String string) {
            this.edgeBottom = string;
            return this;
        }

        public FrameTileBuilder setEdgeLeft(String string) {
            this.edgeLeft = string;
            return this;
        }

        public FrameTileBuilder setEdgeRight(String string) {
            this.edgeRight = string;
            return this;
        }

        public FrameTileBuilder setInterior(String string) {
            this.interior = string;
            return this;
        }

        public void validate() {
            StringBuilder stringBuilder = new StringBuilder();
            if (this.cornerTopLeft == null || this.cornerTopLeft.isEmpty()) {
                stringBuilder.append("\u2717 Corner-TopLeft missing\n");
            }
            if (this.cornerTopRight == null || this.cornerTopRight.isEmpty()) {
                stringBuilder.append("\u2717 Corner-TopRight missing\n");
            }
            if (this.cornerBottomLeft == null || this.cornerBottomLeft.isEmpty()) {
                stringBuilder.append("\u2717 Corner-BottomLeft missing\n");
            }
            if (this.cornerBottomRight == null || this.cornerBottomRight.isEmpty()) {
                stringBuilder.append("\u2717 Corner-BottomRight missing\n");
            }
            if (this.edgeTop == null || this.edgeTop.isEmpty()) {
                stringBuilder.append("\u2717 Edge-Top missing\n");
            }
            if (this.edgeBottom == null || this.edgeBottom.isEmpty()) {
                stringBuilder.append("\u2717 Edge-Bottom missing\n");
            }
            if (this.edgeLeft == null || this.edgeLeft.isEmpty()) {
                stringBuilder.append("\u2717 Edge-Left missing\n");
            }
            if (this.edgeRight == null || this.edgeRight.isEmpty()) {
                stringBuilder.append("\u2717 Edge-Right missing\n");
            }
            if (this.interior == null || this.interior.isEmpty()) {
                stringBuilder.append("\u2717 Interior missing\n");
            }
            if (stringBuilder.length() > 0) {
                throw new IllegalStateException("Frame '" + this.frameName + "' is incomplete:\n" + stringBuilder.toString());
            }
            AnimationAndSpriteLoader.log("\u2713 Frame '" + this.frameName + "' validated successfully");
        }

        public boolean loadAll() {
            this.validate();
            String string = "Resources/industrial-zone/gui/1 Frames";
            try {
                this.imgCornerTL = ImageIO.read(new File(string + "/" + this.cornerTopLeft));
                this.imgCornerTR = ImageIO.read(new File(string + "/" + this.cornerTopRight));
                this.imgCornerBL = ImageIO.read(new File(string + "/" + this.cornerBottomLeft));
                this.imgCornerBR = ImageIO.read(new File(string + "/" + this.cornerBottomRight));
                this.imgEdgeTop = ImageIO.read(new File(string + "/" + this.edgeTop));
                this.imgEdgeBottom = ImageIO.read(new File(string + "/" + this.edgeBottom));
                this.imgEdgeLeft = ImageIO.read(new File(string + "/" + this.edgeLeft));
                this.imgEdgeRight = ImageIO.read(new File(string + "/" + this.edgeRight));
                this.imgInterior = ImageIO.read(new File(string + "/" + this.interior));
                AnimationAndSpriteLoader.log("\u2713 Frame '" + this.frameName + "' loaded: all 9 tiles");
                return true;
            }
            catch (IOException iOException) {
                AnimationAndSpriteLoader.logError("Failed to load frame '" + this.frameName + "': " + iOException.getMessage());
                return false;
            }
        }

        public BufferedImage getCornerTopLeft() {
            return this.imgCornerTL;
        }

        public BufferedImage getCornerTopRight() {
            return this.imgCornerTR;
        }

        public BufferedImage getCornerBottomLeft() {
            return this.imgCornerBL;
        }

        public BufferedImage getCornerBottomRight() {
            return this.imgCornerBR;
        }

        public BufferedImage getEdgeTop() {
            return this.imgEdgeTop;
        }

        public BufferedImage getEdgeBottom() {
            return this.imgEdgeBottom;
        }

        public BufferedImage getEdgeLeft() {
            return this.imgEdgeLeft;
        }

        public BufferedImage getEdgeRight() {
            return this.imgEdgeRight;
        }

        public BufferedImage getInterior() {
            return this.imgInterior;
        }
    }

    public static class ButtonVariants {
        public static final String BASE_DIRECTORY = "Resources/industrial-zone/gui/6 Buttons";
        public static final int TOTAL_BUTTON_VARIANTS = 10;
        public static final int TYPICAL_FRAMES_PER_BUTTON = 4;

        public static VerticalSpritesheetLoader loadButtonVariant(String string, String string2, int n, int n2, int n3) {
            return new VerticalSpritesheetLoader(string, AnimationAndSpriteLoader.GUI_BUTTONS + string2, n, n2, n3);
        }

        public static final class RedCancelButtonVariant {
            public static final String VARIANT_NAME = "Cancel (Red)";
            public static final String SPRITESHEET = "10_GUI_Button_RedCancel_4StatesVertical_CancelColor.png";
            public static final int NORMAL_STATE = 0;
            public static final int HOVER_STATE = 1;
            public static final int PRESSED_STATE = 2;
            public static final int DISABLED_STATE = 3;
            public static final int FRAME_WIDTH = 64;
            public static final int FRAME_HEIGHT = 24;
            public static final int TRANSITION_TIMING_MS = 80;
        }

        public static final class GreenConfirmButtonVariant {
            public static final String VARIANT_NAME = "Confirm (Green)";
            public static final String SPRITESHEET = "09_GUI_Button_GreenConfirm_4StatesVertical_SuccessColor.png";
            public static final int NORMAL_STATE = 0;
            public static final int HOVER_STATE = 1;
            public static final int PRESSED_STATE = 2;
            public static final int DISABLED_STATE = 3;
            public static final int FRAME_WIDTH = 64;
            public static final int FRAME_HEIGHT = 24;
            public static final int TRANSITION_TIMING_MS = 80;
        }

        public static final class CyanLargeButtonVariant {
            public static final String VARIANT_NAME = "Large Cyan";
            public static final String SPRITESHEET = "08_GUI_Button_CyanLarge_4StatesVertical_FullWidthButton.png";
            public static final int NORMAL_STATE = 0;
            public static final int HOVER_STATE = 1;
            public static final int PRESSED_STATE = 2;
            public static final int DISABLED_STATE = 3;
            public static final int FRAME_WIDTH = 128;
            public static final int FRAME_HEIGHT = 32;
            public static final int TRANSITION_TIMING_MS = 90;
        }

        public static final class OrangeWarningButtonVariant {
            public static final String VARIANT_NAME = "Warning/Danger";
            public static final String SPRITESHEET = "07_GUI_Button_OrangeWarning_4StatesVertical_AlertColor.png";
            public static final int NORMAL_STATE = 0;
            public static final int HOVER_STATE = 1;
            public static final int PRESSED_STATE = 2;
            public static final int DISABLED_STATE = 3;
            public static final int FRAME_WIDTH = 64;
            public static final int FRAME_HEIGHT = 24;
            public static final int TRANSITION_TIMING_MS = 80;
        }

        public static final class GlassButtonVariant {
            public static final String VARIANT_NAME = "Glass Panel";
            public static final String SPRITESHEET = "06_GUI_Button_Glass_4StatesVertical_TranslucentReflective.png";
            public static final int NORMAL_STATE = 0;
            public static final int HOVER_STATE = 1;
            public static final int PRESSED_STATE = 2;
            public static final int DISABLED_STATE = 3;
            public static final int FRAME_WIDTH = 64;
            public static final int FRAME_HEIGHT = 24;
            public static final int TRANSITION_TIMING_MS = 120;
        }

        public static final class PressurePlateButtonVariant {
            public static final String VARIANT_NAME = "Pressure Plate";
            public static final String SPRITESHEET = "05_GUI_Button_PressurePlate_4StatesVertical_PhysicalDepression.png";
            public static final int NORMAL_STATE = 0;
            public static final int HOVER_STATE = 1;
            public static final int PRESSED_STATE = 2;
            public static final int DISABLED_STATE = 3;
            public static final int FRAME_WIDTH = 64;
            public static final int FRAME_HEIGHT = 24;
            public static final int TRANSITION_TIMING_MS = 100;
        }

        public static final class CyanAccentButtonVariant {
            public static final String VARIANT_NAME = "Cyan Accent";
            public static final String SPRITESHEET = "04_GUI_Button_CyanAccent_4StatesVertical_BlueHighlight.png";
            public static final int NORMAL_STATE = 0;
            public static final int HOVER_STATE = 1;
            public static final int PRESSED_STATE = 2;
            public static final int DISABLED_STATE = 3;
            public static final int FRAME_WIDTH = 64;
            public static final int FRAME_HEIGHT = 24;
            public static final int TRANSITION_TIMING_MS = 80;
        }

        public static final class MetalButtonVariant {
            public static final String VARIANT_NAME = "Industrial Metal";
            public static final String SPRITESHEET = "03_GUI_Button_Metal_4StatesVertical_RivetedSteelLook.png";
            public static final int NORMAL_STATE = 0;
            public static final int HOVER_STATE = 1;
            public static final int PRESSED_STATE = 2;
            public static final int DISABLED_STATE = 3;
            public static final int FRAME_WIDTH = 64;
            public static final int FRAME_HEIGHT = 24;
            public static final int TRANSITION_TIMING_MS = 90;
        }

        public static final class HoloButtonVariant {
            public static final String VARIANT_NAME = "Holographic";
            public static final String SPRITESHEET = "02_GUI_Button_Holographic_4StatesVertical_TechGlowEffect.png";
            public static final int NORMAL_STATE = 0;
            public static final int HOVER_STATE = 1;
            public static final int PRESSED_STATE = 2;
            public static final int DISABLED_STATE = 3;
            public static final int FRAME_WIDTH = 64;
            public static final int FRAME_HEIGHT = 24;
            public static final int TRANSITION_TIMING_MS = 100;
        }

        public static final class StandardButtonVariant {
            public static final String VARIANT_NAME = "Standard Blue";
            public static final String SPRITESHEET = "01_GUI_Button_Standard_4StatesVertical_NormalHoverPressDisabled.png";
            public static final int NORMAL_STATE = 0;
            public static final int HOVER_STATE = 1;
            public static final int PRESSED_STATE = 2;
            public static final int DISABLED_STATE = 3;
            public static final int FRAME_WIDTH = 64;
            public static final int FRAME_HEIGHT = 24;
            public static final int TRANSITION_TIMING_MS = 80;
        }
    }

    public static class CharacterCardAnimationAssets {
        public static final int CARD_ANIMATION_COUNT = 3;
        public static final int ESTIMATED_FRAME_WIDTH = 96;
        public static final int ESTIMATED_FRAME_HEIGHT = 96;

        public static class PunkCardAsset {
            public static final String CHARACTER = "Punk";
            public static final String BASE_DIR = "Resources/industrial-zone/characters/player/punk";
            public static final String CARD_IDLE_PRIMARY = "01_Player_Punk_Idle_5Frames1Row_CasualStandingPosture_DefaultIdle_Loop_150ms.png";
            public static final String CARD_IDLE_SECONDARY = "02_Player_Punk_Idle2_5Frames1Row_HipstrutIdle_SecondaryIdle_Loop_150ms.png";
            public static final int LOOP_TIMING_MS = 150;
            public static final boolean SEAMLESS_LOOP = true;
        }

        public static class CyborgCardAsset {
            public static final String CHARACTER = "Cyborg";
            public static final String BASE_DIR = "Resources/industrial-zone/characters/player/cyborg";
            public static final String CARD_IDLE_PRIMARY = "01_Player_Cyborg_Idle_4Frames1Row_StandingTechBreathing_DefaultIdle_Loop_150ms.png";
            public static final String CARD_IDLE_SECONDARY = "02_Player_Cyborg_Idle2_5Frames1Row_AlternateStandingSecondaryIdle_Loop_150ms.png";
            public static final int LOOP_TIMING_MS = 150;
            public static final boolean SEAMLESS_LOOP = true;
        }

        public static class BikerCardAsset {
            public static final String CHARACTER = "Biker";
            public static final String BASE_DIR = "Resources/industrial-zone/characters/player/biker";
            public static final String CARD_IDLE_PRIMARY = "01_Player_Biker_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png";
            public static final String CARD_IDLE_SECONDARY = "02_Player_Biker_Idle2_5Frames1Row_AlternateStandLoop_SecondaryIdle_Loop_150ms.png";
            public static final int LOOP_TIMING_MS = 150;
            public static final boolean SEAMLESS_LOOP = true;
        }
    }

    public static class GUIFrameAssetProperties {
        public static final String BASE_DIRECTORY = "Resources/industrial-zone/gui/1 Frames";
        public static final int TOTAL_FRAME_PIECES = 82;

        public static class MasterReference {
            public static final String MASTER_SPRITESHEET = "82_GUI_Frame_MasterSpritesheet_AllFramePiecesLayout_Reference.png";
            public static final String DESCRIPTION = "Complete visual reference of all 82 GUI frame pieces. Use for preview and layout planning. Individual files should be loaded for actual rendering.";
        }

        public static class PanelPieces {
            public static final String CATEGORY = "Panels";
            public static final int PIECE_COUNT = 18;
            public static final String[] WIDE_PANELS = new String[]{"16_GUI_Frame_PanelWideRect_TealCyanAccentStripe_DividerBar.png", "17_GUI_Frame_PanelWideRect_DarkerNoAccent_DividerBar.png", "25_GUI_Frame_PanelWideRect_WiderPlainDark_DividerBar.png", "26_GUI_Frame_PanelWideRect_DarkerVariant_DividerBar.png", "49_GUI_Frame_PanelHorizBar_MediumWidth_DividerBar.png", "58_GUI_Frame_PanelWideRect_InsetBorderWider_PanelCell.png"};
            public static final String[] INSET_SQUARES = new String[]{"37_GUI_Frame_PanelInsetSquare_SingleCellDarkBorder_PanelCell.png", "51_GUI_Frame_PanelInsetSquare_SingleCell_PanelCell.png", "60_GUI_Frame_PanelMedium_InsetSquareTaller_PanelCell.png", "79_GUI_Frame_PanelInsetSquare_DarkBorderSquare_PanelCell.png", "80_GUI_Frame_PanelInsetSquare_LighterVariant_PanelCell.png"};
            public static final String[] TWO_CELL_PANELS = new String[]{"41_GUI_Frame_Panel2Cell_TwoInsetSquares_PanelCell.png", "42_GUI_Frame_Panel2CellVariant_TwoInsetSquaresDiffShade_PanelCell.png", "52_GUI_Frame_Panel2CellVariant2_TwoInsetSquares_PanelCell.png", "53_GUI_Frame_Panel2CellVariant3_TwoInsetSlightBorderDiff_PanelCell.png", "61_GUI_Frame_Panel2Cell_TwoInsetSquaresWithIcons_PanelCell.png", "62_GUI_Frame_Panel2CellVariant_TwoInsetSquaresIconsAlt_PanelCell.png"};
            public static final String[] GRID_PANELS = new String[]{"35_GUI_Frame_PanelHorizDivider_TwoRowDarkNavy_DividerBar.png", "70_GUI_Frame_Panel2CellWideDivider_TwoCellBlock_DividerBar.png", "71_GUI_Frame_Panel2CellGrid_TwoCellGridBlock_PanelCell.png"};
            public static final String[] DECORATIVE_PANELS = new String[]{"08_GUI_Frame_PanelCrossDiamond_BlueGeometricPattern_DecorativeFill.png", "23_GUI_Frame_PanelWideRect_TechDotTexture_DividerBar.png"};
        }

        public static class FillPieces {
            public static final String CATEGORY = "Fills";
            public static final int PIECE_COUNT = 12;
            public static final String[] FILL_FILES = new String[]{"07_GUI_Frame_FillSolidNavy_LargeFullBlock_WindowFill.png", "38_GUI_Frame_FillSolidNavy_WideRectNoBorder_WindowFill.png", "40_GUI_Frame_FillSolidDarkNavy_FullBlock_WindowFill.png", "45_GUI_Frame_FillSolidNavy_PlainFlatBlock_WindowFill.png", "46_GUI_Frame_FillSolidNavy_WideFlatRect_WindowFill.png", "65_GUI_Frame_FillSolidDarkNavy_PlainBlock_WindowFill.png", "66_GUI_Frame_FillSolidNavyLighter_SlightlyLighter_WindowFill.png", "68_GUI_Frame_FillSolidNavy_PlainBlock_WindowFill.png", "74_GUI_Frame_FillSolidNavy_PlainBlock_WindowFill.png", "77_GUI_Frame_FillSolidNavyDark_DarkBlock_WindowFill.png", "11_GUI_Frame_FillDiagonalTexture_FaintDiagLines_WindowFill.png", "39_GUI_Frame_FillSolidNavy_WiderVariant_WindowFill.png", "59_GUI_Frame_FillSolidNavy_WideFlatRect_WindowFill.png"};
        }

        public static class EdgePieces {
            public static final String CATEGORY = "Edges";
            public static final int PIECE_COUNT = 22;
            public static final String[] TOP_EDGES = new String[]{"02_GUI_Frame_EdgeTopBar_HorizontalBlueAccentStrip_WindowTopEdge.png", "36_GUI_Frame_EdgeTopBarVariant_LighterStripe_WindowTopEdge.png", "44_GUI_Frame_EdgeHorizBar_DenseDotLineTexture_WindowEdge.png", "50_GUI_Frame_EdgeBottomBar_HorizStripTexture_WindowBottomEdge.png", "67_GUI_Frame_EdgeTopBar_PlainBar_WindowTopEdge.png"};
            public static final String[] BOTTOM_EDGES = new String[]{"20_GUI_Frame_EdgeBottomBar_PlainDarkStrip_WindowBottomEdge.png", "24_GUI_Frame_EdgeBottomBar_DotLineTexture_WindowBottomEdge.png", "50_GUI_Frame_EdgeBottomBar_HorizStripTexture_WindowBottomEdge.png", "54_GUI_Frame_EdgeBottomBar_LightBlueAccentLine_WindowBottomEdge.png", "75_GUI_Frame_EdgeBottomBar_PlainHorizStrip_WindowBottomEdge.png"};
            public static final String[] LEFT_EDGES = new String[]{"05_GUI_Frame_EdgeLeftStrip_TallNarrowVerticalBar_WindowLeftEdge.png", "10_GUI_Frame_EdgeLeftStrip_TallBlueTintBar_WindowLeftEdge.png", "12_GUI_Frame_EdgeLeftStrip_TallDarkerBar_WindowLeftEdge.png", "13_GUI_Frame_EdgeLeftStrip_TallThinLighter_WindowLeftEdge.png", "14_GUI_Frame_EdgeLeftStrip_TallWiderWithTrim_WindowLeftEdge.png", "15_GUI_Frame_EdgeLeftStrip_TallLighterBlueTrim_WindowLeftEdge.png", "43_GUI_Frame_EdgeLeftStrip_NarrowDarkVertical_WindowLeftEdge.png", "57_GUI_Frame_EdgeLeftStrip_NarrowDarkSlightVariant_WindowLeftEdge.png", "69_GUI_Frame_EdgeLeftStrip_LeftEdgeBar_WindowLeftEdge.png"};
            public static final String[] RIGHT_EDGES = new String[]{"06_GUI_Frame_EdgeRightStrip_TallNarrowVerticalBar_WindowRightEdge.png", "18_GUI_Frame_EdgeRightStrip_TallBlueAccentLine_WindowRightEdge.png", "22_GUI_Frame_EdgeRightStrip_TallNarrowPlain_WindowRightEdge.png", "28_GUI_Frame_EdgeRightStrip_TallBlueAccent_WindowRightEdge.png", "48_GUI_Frame_EdgeRightStrip_NarrowVerticalBar_WindowRightEdge.png", "55_GUI_Frame_EdgeRightStrip_NarrowSlightlyWider_WindowRightEdge.png", "72_GUI_Frame_EdgeThinRightStrip_ThinVerticalRight_WindowRightEdge.png"};
            public static final String[] DIAGONAL_EDGES = new String[]{"47_GUI_Frame_EdgeDiagonalStrip_AngledTopLeftSlant_WindowEdge.png", "56_GUI_Frame_EdgeDiagonalStrip_AngledLineTexture_WindowEdge.png"};
        }

        public static class CornerPieces {
            public static final String CATEGORY = "Corners";
            public static final int PIECE_COUNT = 16;
            public static final String[] CORNER_FILES = new String[]{"01_GUI_Frame_CornerTopLeft_TallLShapePiece_WindowCorner.png", "03_GUI_Frame_CornerTopRight_TallLShapeMirror_WindowCorner.png", "19_GUI_Frame_CornerBottomLeft_LShapeCorner_WindowCorner.png", "27_GUI_Frame_CornerBottomRight_DiagonalAngleTrim_WindowCorner.png", "09_GUI_Frame_CornerInsetTopLeft_InsetBorderSquare_WindowCorner.png", "21_GUI_Frame_CornerBottomLeftFull_InsetSquareCorner_WindowCorner.png", "29_GUI_Frame_CornerBottomRightFull_BracketCorner_WindowCorner.png", "30_GUI_Frame_CornerTopRightFull_BracketTShape_WindowCorner.png", "04_GUI_Frame_CornerTopLeftRivet_RedDotAccents_WindowCorner.png", "31_GUI_Frame_CornerTopRight_SmallInsetLightTrim_WindowCorner.png", "32_GUI_Frame_CornerTopLeft_TriangleCut_WindowCorner.png", "33_GUI_Frame_CornerTopRight_TriangleCutMirror_WindowCorner.png", "34_GUI_Frame_CornerTopRight_DiagonalCut_WindowCorner.png", "63_GUI_Frame_CornerHexagonal_LightGreyOctagonShape_WindowCorner.png", "64_GUI_Frame_CornerDiagonalCutTopLeft_WindowCorner.png", "76_GUI_Frame_CornerBottomSmallNotch_BottomNotchCorner_WindowCorner.png"};
        }
    }

    public static class GUIAssetLoader {
        public static HorizontalSpritesheetLoader loadGUIElement(String string, String string2, int n) {
            HorizontalSpritesheetLoader horizontalSpritesheetLoader = new HorizontalSpritesheetLoader("GUI_" + string, string2, 0, 0, 0);
            if (horizontalSpritesheetLoader.load()) {
                System.out.println("[GUIAssetLoader] \u2713 Loaded: " + string + " (" + horizontalSpritesheetLoader.getFrameCount() + " frames)");
                return horizontalSpritesheetLoader;
            }
            System.out.println("[GUIAssetLoader] \u26a0 Failed to load: " + string + " from " + string2);
            return null;
        }

        public static Map<String, HorizontalSpritesheetLoader> loadGUIButtonStates(String string, String string2) {
            LinkedHashMap<String, HorizontalSpritesheetLoader> linkedHashMap = new LinkedHashMap<String, HorizontalSpritesheetLoader>();
            String string3 = string2 + "GUI_ButtonColorMap_" + string + ".png";
            try {
                BufferedImage bufferedImage = ImageIO.read(new File(string3));
                if (bufferedImage == null) {
                    System.out.println("[GUIAssetLoader] \u26a0 Button sheet not found: " + string3);
                    return linkedHashMap;
                }
                String string4 = GUIAssetLoader.detectSpriteOrientation(bufferedImage);
                System.out.println("[GUIAssetLoader] Button orientation: " + string4 + " (" + bufferedImage.getWidth() + "x" + bufferedImage.getHeight() + ")");
                int[] nArray = GUIAssetLoader.detectGridDimensions(bufferedImage, string4);
                System.out.println("[GUIAssetLoader] Grid detected: " + nArray[0] + " rows \u00d7 " + nArray[1] + " cols");
                String[] stringArray = new String[]{"Idle", "Hover", "Press", "Disabled"};
                if ("VERTICAL".equals(string4)) {
                    int n = nArray[1];
                    int n2 = bufferedImage.getHeight() / nArray[0];
                    int n3 = bufferedImage.getWidth() / nArray[1];
                    for (int i = 0; i < Math.min(stringArray.length, nArray[0]); ++i) {
                        BufferedImage bufferedImage2 = GUIAssetLoader.extractStateFromVertical(bufferedImage, i, nArray[1], nArray[0]);
                        if (bufferedImage2 == null) continue;
                        File file = new File(System.getProperty("java.io.tmpdir") + "/game_assets");
                        file.mkdirs();
                        String string5 = file.getAbsolutePath() + "/Button_" + string + "_" + stringArray[i] + "_temp.png";
                        try {
                            ImageIO.write((RenderedImage)bufferedImage2, "PNG", new File(string5));
                            HorizontalSpritesheetLoader horizontalSpritesheetLoader = new HorizontalSpritesheetLoader("Button_" + string + "_" + stringArray[i], string5, n3, n2, n);
                            if (!horizontalSpritesheetLoader.load()) continue;
                            linkedHashMap.put(stringArray[i].toLowerCase(), horizontalSpritesheetLoader);
                            System.out.println("[GUIAssetLoader] \u2713 Extracted state: " + stringArray[i] + " (" + n + " frames, " + n3 + "x" + n2 + ")");
                            continue;
                        }
                        catch (IOException iOException) {
                            System.out.println("[GUIAssetLoader] \u2717 Failed to save temp state: " + stringArray[i] + " - " + iOException.getMessage());
                        }
                    }
                } else {
                    System.out.println("[GUIAssetLoader] \u26a0 Horizontal layout not yet implemented for buttons");
                }
                if (!linkedHashMap.isEmpty()) {
                    System.out.println("[GUIAssetLoader] \u2713 Loaded button states: " + String.join((CharSequence)", ", linkedHashMap.keySet()));
                }
            }
            catch (IOException iOException) {
                System.out.println("[GUIAssetLoader] \u2717 Failed to load button sheet: " + iOException.getMessage());
            }
            return linkedHashMap;
        }

        public static BufferedImage loadStaticImage(String string, String string2) {
            try {
                BufferedImage bufferedImage = ImageIO.read(new File(string2));
                if (bufferedImage != null) {
                    System.out.println("[GUIAssetLoader] \u2713 Loaded image: " + string + " (" + bufferedImage.getWidth() + "x" + bufferedImage.getHeight() + ")");
                    return bufferedImage;
                }
            }
            catch (IOException iOException) {
                System.out.println("[GUIAssetLoader] \u26a0 Failed to load image: " + string);
            }
            return null;
        }

        public static HorizontalSpritesheetLoader loadGUICard(String string, String string2) {
            String[] stringArray = new String[]{string2 + "Card_" + string + ".png", string2 + string + "_Card.png", string2 + string + ".png"};
            BufferedImage bufferedImage = null;
            String string3 = null;
            for (String string4 : stringArray) {
                bufferedImage = ImageIO.read(new File(string4));
                if (bufferedImage == null) continue;
                string3 = string4;
                break;
            }
            if (bufferedImage == null) {
                System.out.println("[GUIAssetLoader] \u26a0 Card not found with any naming convention: " + string);
                return null;
            }
            System.out.println("[GUIAssetLoader] Loaded card from: " + string3);
            String string5 = GUIAssetLoader.detectSpriteOrientation(bufferedImage);
            int n = GUIAssetLoader.detectFrameCount(bufferedImage, string5);
            System.out.println("[GUIAssetLoader] Card detected: " + string5 + " layout, " + n + " frames (" + bufferedImage.getWidth() + "x" + bufferedImage.getHeight() + ")");
            File file = new File(System.getProperty("java.io.tmpdir") + "/game_assets");
            file.mkdirs();
            String string6 = file.getAbsolutePath() + "/Card_" + string + "_temp.png";
            try {
                ImageIO.write((RenderedImage)bufferedImage, "PNG", new File(string6));
                int n2 = bufferedImage.getWidth() / n;
                int n3 = bufferedImage.getHeight();
                HorizontalSpritesheetLoader horizontalSpritesheetLoader = new HorizontalSpritesheetLoader("Card_" + string, string6, n2, n3, n);
                if (horizontalSpritesheetLoader.load()) {
                    System.out.println("[GUIAssetLoader] \u2713 Loaded card: " + string + " (" + n + " frames, " + n2 + "x" + n3 + ")");
                    return horizontalSpritesheetLoader;
                }
                return null;
            }
            catch (IOException iOException) {
                try {
                    System.out.println("[GUIAssetLoader] \u2717 Failed to save card temp file: " + iOException.getMessage());
                    return null;
                }
                catch (IOException iOException2) {
                    System.out.println("[GUIAssetLoader] \u2717 Failed to load card: " + string + " - " + iOException2.getMessage());
                    return null;
                }
            }
        }

        public static HorizontalSpritesheetLoader loadTransition(String string, String string2) {
            String string3 = string2 + string + "_20Frames.png";
            return GUIAssetLoader.loadGUIElement("Transition_" + string, string3, 20);
        }

        public static Map<String, HorizontalSpritesheetLoader> loadBatchElements(String string, String ... stringArray) {
            LinkedHashMap<String, HorizontalSpritesheetLoader> linkedHashMap = new LinkedHashMap<String, HorizontalSpritesheetLoader>();
            for (String string2 : stringArray) {
                String string3 = string + string2 + "_Idle_4Frames.png";
                HorizontalSpritesheetLoader horizontalSpritesheetLoader = GUIAssetLoader.loadGUIElement(string2, string3, 4);
                if (horizontalSpritesheetLoader == null) continue;
                linkedHashMap.put(string2, horizontalSpritesheetLoader);
            }
            System.out.println("[GUIAssetLoader] \u2713 Batch loaded " + linkedHashMap.size() + "/" + stringArray.length + " elements");
            return linkedHashMap;
        }

        public static String detectSpriteOrientation(BufferedImage bufferedImage) {
            if (bufferedImage == null) {
                return "UNKNOWN";
            }
            float f = (float)bufferedImage.getWidth() / (float)bufferedImage.getHeight();
            if (f < 0.8f) {
                return "VERTICAL";
            }
            if (f > 1.25f) {
                return "HORIZONTAL";
            }
            return "SQUARE";
        }

        public static int[] detectGridDimensions(BufferedImage bufferedImage, String string) {
            if (bufferedImage == null) {
                return new int[]{1, 1};
            }
            float f = (float)bufferedImage.getWidth() / (float)bufferedImage.getHeight();
            if ("VERTICAL".equals(string)) {
                if (f >= 0.45f && f <= 0.55f) {
                    return new int[]{4, 2};
                }
                if (f < 0.34f) {
                    return new int[]{3, 1};
                }
                return new int[]{2, 1};
            }
            if ("HORIZONTAL".equals(string)) {
                float f2 = 1.0f / f;
                return new int[]{1, Math.max(2, (int)f2)};
            }
            return new int[]{1, 1};
        }

        public static BufferedImage extractStateFromVertical(BufferedImage bufferedImage, int n, int n2, int n3) {
            if (bufferedImage == null || n >= n3 || n2 <= 0) {
                return null;
            }
            int n4 = bufferedImage.getWidth();
            int n5 = bufferedImage.getHeight();
            int n6 = n4 / n2;
            int n7 = n5 / n3;
            int n8 = n * n7;
            System.out.println("[GUIAssetLoader] Extracting row " + n + ": " + n6 + "x" + n7 + " cells");
            BufferedImage bufferedImage2 = new BufferedImage(n4, n7, 2);
            bufferedImage.getRGB(0, n8, n4, n7, bufferedImage2.getRGB(0, 0, n4, n7, null, 0, n4), 0, n4);
            return bufferedImage2;
        }

        public static int detectFrameCount(BufferedImage bufferedImage, String string) {
            if (bufferedImage == null) {
                return 1;
            }
            if ("HORIZONTAL".equals(string)) {
                int n = bufferedImage.getWidth() / bufferedImage.getHeight();
                return Math.max(1, n);
            }
            if ("VERTICAL".equals(string)) {
                int[] nArray = GUIAssetLoader.detectGridDimensions(bufferedImage, string);
                return nArray[1];
            }
            return 1;
        }

        public static int getFrameCountFromMetadata(String string) {
            try {
                int n;
                BufferedImage bufferedImage = ImageIO.read(new File(string));
                if (bufferedImage == null) {
                    return -1;
                }
                int n2 = bufferedImage.getWidth();
                if (n2 > (n = bufferedImage.getHeight()) * 2) {
                    int n3 = n2 / n;
                    return n3;
                }
                return 1;
            }
            catch (IOException iOException) {
                return -1;
            }
        }
    }

    public static class GUIAnimationRegistry {
        private static final Map<String, GUIAnimationPattern> GUI_ANIMATIONS = new LinkedHashMap<String, GUIAnimationPattern>();

        public static GUIAnimationPattern getPattern(String string) {
            return GUI_ANIMATIONS.get(string);
        }

        public static boolean hasPattern(String string) {
            return GUI_ANIMATIONS.containsKey(string);
        }

        public static Collection<String> listPatterns() {
            return GUI_ANIMATIONS.keySet();
        }

        public static Map<String, GUIAnimationPattern> getAllPatterns() {
            return new LinkedHashMap<String, GUIAnimationPattern>(GUI_ANIMATIONS);
        }

        public static void printRegistry() {
            System.out.println("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
            System.out.println("GUI ANIMATION REGISTRY - " + GUI_ANIMATIONS.size() + " patterns");
            System.out.println("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
            for (String string : GUI_ANIMATIONS.keySet()) {
                GUIAnimationPattern gUIAnimationPattern = GUI_ANIMATIONS.get(string);
                System.out.println("  \u2713 " + String.format("%-20s", string) + " | " + gUIAnimationPattern.frameCount + " frames @ " + gUIAnimationPattern.timingMs + "ms | " + (gUIAnimationPattern.looping ? "LOOP" : "ONCE"));
            }
            System.out.println("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\n");
        }

        static {
            GUI_ANIMATIONS.put("button_hover", new GUIAnimationPattern("Button_Hover", 4, 80, true, "Button hover state animation"));
            GUI_ANIMATIONS.put("button_press", new GUIAnimationPattern("Button_Press", 3, 60, false, "Button press feedback animation"));
            GUI_ANIMATIONS.put("button_active", new GUIAnimationPattern("Button_Active", 4, 100, true, "Button active state pulsing"));
            GUI_ANIMATIONS.put("fade_in", new GUIAnimationPattern("FadeIn", 20, 25, false, "Screen fade-in transition"));
            GUI_ANIMATIONS.put("fade_out", new GUIAnimationPattern("FadeOut", 20, 25, false, "Screen fade-out transition"));
            GUI_ANIMATIONS.put("slide_right", new GUIAnimationPattern("Slide_Right", 10, 40, false, "Slide in from left transition"));
            GUI_ANIMATIONS.put("slide_left", new GUIAnimationPattern("Slide_Left", 10, 40, false, "Slide out to left transition"));
            GUI_ANIMATIONS.put("menu_idle", new GUIAnimationPattern("Menu_Idle", 5, 150, true, "Menu idle breathing animation"));
            GUI_ANIMATIONS.put("menu_background", new GUIAnimationPattern("MenuBackground_Idle", 5, 150, true, "Menu background animation"));
            GUI_ANIMATIONS.put("card_hover", new GUIAnimationPattern("Card_Hover", 4, 100, true, "Card hover effect"));
            GUI_ANIMATIONS.put("card_select", new GUIAnimationPattern("Card_Select", 6, 80, false, "Card selection animation"));
            GUI_ANIMATIONS.put("logo_entrance", new GUIAnimationPattern("Logo_Entrance", 8, 80, false, "Logo entrance animation"));
            GUI_ANIMATIONS.put("logo_idle", new GUIAnimationPattern("Logo_Idle", 4, 150, true, "Logo idle pulsing"));
            GUI_ANIMATIONS.put("slider_active", new GUIAnimationPattern("Slider_Active", 3, 100, true, "Slider thumb active state"));
            GUI_ANIMATIONS.put("toggle_on", new GUIAnimationPattern("Toggle_On", 4, 80, false, "Toggle switch on animation"));
            GUI_ANIMATIONS.put("toggle_off", new GUIAnimationPattern("Toggle_Off", 4, 80, false, "Toggle switch off animation"));
            GUI_ANIMATIONS.put("icon_spin", new GUIAnimationPattern("Icon_Spin", 8, 60, true, "Spinning icon animation"));
            GUI_ANIMATIONS.put("icon_pulse", new GUIAnimationPattern("Icon_Pulse", 4, 150, true, "Icon pulsing animation"));
        }
    }

    public static class GUIAnimationPattern {
        public final String filename;
        public final int frameCount;
        public final int timingMs;
        public final boolean looping;
        public final String description;

        public GUIAnimationPattern(String string, int n, int n2, boolean bl, String string2) {
            this.filename = string;
            this.frameCount = n;
            this.timingMs = n2;
            this.looping = bl;
            this.description = string2;
        }
    }

    public static class ImpactEffectSystem {
        public static double[] calculateKnockback(int n, int n2, int n3, int n4, double d, double d2) {
            double d3 = d / d2;
            double d4 = n3 - n;
            double d5 = n4 - n2;
            double d6 = Math.sqrt(d4 * d4 + d5 * d5);
            if (d6 > 0.0) {
                d4 /= d6;
                d5 /= d6;
            }
            return new double[]{d4 * d3, d5 * d3};
        }

        public static int[] getExplosionDamageInRadius(int n, int n2, int n3, int n4) {
            return new int[0];
        }

        public static enum ImpactType {
            SPLAT("Blood splatter effects"),
            EXPLOSION("Area damage explosion"),
            RICOCHET("Bounce off surfaces"),
            SCREEN_SHAKE("Camera shake effect");

            public final String description;

            private ImpactType(String string2) {
                this.description = string2;
            }
        }
    }

    public static class TracerEffectSystem {
        public static TracerType getTracerForGunType(WeaponSystemCore.GunType gunType) {
            switch (gunType.ordinal()) {
                case 0: 
                case 1: {
                    return TracerType.STANDARD_A;
                }
                case 2: 
                case 3: 
                case 4: {
                    return TracerType.DOTTED;
                }
                case 5: {
                    return TracerType.WAVE;
                }
                case 6: 
                case 7: {
                    return TracerType.HEAVY;
                }
                case 8: {
                    return TracerType.LASER;
                }
                case 9: {
                    return TracerType.OUTLINE;
                }
            }
            return TracerType.STANDARD_A;
        }

        public static enum TracerType {
            STANDARD_A("Narrow"),
            STANDARD_SCATTER("Scatter"),
            DOTTED("Dotted"),
            SLASH("Slash"),
            HEAVY("Heavy"),
            BOLD("Bold"),
            WAVE("Wave"),
            JAGGED("Jagged"),
            LASER("Laser"),
            OUTLINE("Outline");

            public final String style;

            private TracerType(String string2) {
                this.style = string2;
            }
        }
    }

    public static class EnemyProjectileRegistry {
        public static EnemyProjectileType getProjectileForEnemy(String string) {
            if (string.contains("RugbyGuy")) {
                return EnemyProjectileType.RUGBY_BALL;
            }
            if (string.contains("Knight") || string.contains("2")) {
                return EnemyProjectileType.GHOST_ORB;
            }
            if (string.contains("Platform") || string.contains("6")) {
                return EnemyProjectileType.CAPSULE_PROJECTILE;
            }
            if (string.contains("Tank") || string.contains("1")) {
                return EnemyProjectileType.COMBAT_LASER;
            }
            return EnemyProjectileType.COMBAT_LASER;
        }

        public static enum EnemyProjectileType {
            RUGBY_BALL("RugbyBall", "Resources/industrial-zone/characters/RugbyGuy/03_Boss_RugbyGuy_Projectile_1Frame_RugbyBallThrow_RangedAttackProjectile_Single_Instant.png", WeaponSystemCore.TrajectoryType.ARC, 30, 10.0),
            GHOST_ORB("GhostOrb", "Resources/industrial-zone/characters/2/02_Enemy_ArmouredKnight_Projectile_1Frame1Row_SingleProjectileSprite_Projectile_Loop_100ms.png", WeaponSystemCore.TrajectoryType.HOMING, 20, 8.0),
            CAPSULE_PROJECTILE("CapsuleProjectile", "Resources/industrial-zone/characters/6/04_EnemyDrone_HoverPlatform_CapsuleProjectileAttack_7Frames1Row.png", WeaponSystemCore.TrajectoryType.STRAIGHT, 40, 5.0),
            COMBAT_LASER("CombatLaser", "Resources/industrial-zone/characters/1/03_Enemy_CombatTank_Attack1_4Frames1Row_TurretStraightShot_RangedAttack_PlayOnce_80ms.png", WeaponSystemCore.TrajectoryType.STRAIGHT, 25, 12.0);

            public final String name;
            public final String spritePath;
            public final WeaponSystemCore.TrajectoryType trajectoryType;
            public final int baseDamage;
            public final double velocity;

            private EnemyProjectileType(String string2, String string3, WeaponSystemCore.TrajectoryType trajectoryType, int n2, double d) {
                this.name = string2;
                this.spritePath = string3;
                this.trajectoryType = trajectoryType;
                this.baseDamage = n2;
                this.velocity = d;
            }
        }
    }

    public static class ProjectileRegistry {
        static BulletProperties[] BULLET_TYPES = new BulletProperties[]{new BulletProperties('A', "Single", 1.0, 0, false), new BulletProperties('B', "Single", 1.0, 0, false), new BulletProperties('C', "Single", 1.0, 0, false), new BulletProperties('D', "VariantA", 1.1, 0, false), new BulletProperties('E', "VariantA", 1.1, 0, false), new BulletProperties('F', "Single", 1.0, 0, false), new BulletProperties('G', "VariantA", 1.3, 1, true), new BulletProperties('H', "Single", 1.2, 0, false), new BulletProperties('I', "Single", 1.2, 2, true), new BulletProperties('J', "Single", 1.5, 0, false)};

        public static BulletProperties getBulletProperties(char c) {
            int n = c - 65;
            if (n >= 0 && n < BULLET_TYPES.length) {
                return BULLET_TYPES[n];
            }
            return BULLET_TYPES[0];
        }

        public static class BulletProperties {
            public char bulletType;
            public String variant;
            public double baseDamageMultiplier;
            public int maxPenetrations;
            public boolean isPenetrating;

            public BulletProperties(char c, String string, double d, int n, boolean bl) {
                this.bulletType = c;
                this.variant = string;
                this.baseDamageMultiplier = d;
                this.maxPenetrations = n;
                this.isPenetrating = bl;
            }
        }
    }

    public static class DamageCalculationSystem {
        public static int calculateDamage(int n, double d, double d2, HitLocation hitLocation, DifficultyLevel difficultyLevel) {
            double d3 = (double)n * d * Math.max(0.25, d2) * hitLocation.damageMultiplier * difficultyLevel.damageMultiplier;
            return (int)d3;
        }

        public static double getDistanceFalloff(double d, double d2) {
            if (d >= d2) {
                return 0.0;
            }
            return 1.0 - Math.pow(d / d2, 2.0);
        }

        public static enum HitLocation {
            HEAD(1.5, "Critical"),
            TORSO(1.0, "Standard"),
            LIMB(0.75, "Reduced");

            public final double damageMultiplier;
            public final String description;

            private HitLocation(double d, String string2) {
                this.damageMultiplier = d;
                this.description = string2;
            }
        }

        public static enum DifficultyLevel {
            EASY(0.7),
            NORMAL(1.0),
            HARD(1.3),
            NIGHTMARE(1.7);

            public final double damageMultiplier;

            private DifficultyLevel(double d) {
                this.damageMultiplier = d;
            }
        }
    }

    public static class ProjectilePhysicsSystem {

        public static class HomingTrajectory {
            public double velocityX;
            public double velocityY;
            public double speed = 8.0;
            public double maxTurnSpeed = 0.1;

            public HomingTrajectory(double d, double d2) {
                this.velocityX = d;
                this.velocityY = d2;
            }

            public void trackTarget(int n, int n2, int n3, int n4) {
                double d = n3 - n;
                double d2 = n4 - n2;
                double d3 = Math.sqrt(d * d + d2 * d2);
                if (d3 > 0.0) {
                    double d4;
                    double d5 = Math.atan2(this.velocityY, this.velocityX);
                    double d6 = Math.atan2(d2 /= d3, d /= d3);
                    for (d4 = d6 - d5; d4 > Math.PI; d4 -= Math.PI * 2) {
                    }
                    while (d4 < -Math.PI) {
                        d4 += Math.PI * 2;
                    }
                    double d7 = Math.max(-this.maxTurnSpeed, Math.min(this.maxTurnSpeed, d4));
                    double d8 = d5 + d7;
                    this.velocityX = this.speed * Math.cos(d8);
                    this.velocityY = this.speed * Math.sin(d8);
                }
            }
        }

        public static class ArcTrajectory {
            public double velocityX;
            public double velocityY;
            public double gravity = 0.3;

            public ArcTrajectory(double d, double d2) {
                this.velocityX = d;
                this.velocityY = d2;
            }

            public void update(int n, int n2) {
                this.velocityY += this.gravity;
                n += (int)this.velocityX;
                n2 += (int)this.velocityY;
            }
        }

        public static class StraightTrajectory {
            public double velocityX;
            public double velocityY;

            public StraightTrajectory(double d, double d2) {
                this.velocityX = d;
                this.velocityY = d2;
            }

            public void update(int n, int n2) {
                n += (int)this.velocityX;
                n2 += (int)this.velocityY;
            }

            public double getSpeed() {
                return Math.sqrt(this.velocityX * this.velocityX + this.velocityY * this.velocityY);
            }
        }
    }

    public static class WeaponSystemCore {

        public static enum PlayerCharacter {
            BIKER("Biker", 30),
            PUNK("Punk", 25),
            CYBORG("Cyborg", 28);

            public final String name;
            public final int baseHealth;

            private PlayerCharacter(String string2, int n2) {
                this.name = string2;
                this.baseHealth = n2;
            }
        }

        public static enum GripPose {
            HORIZONTAL(0, "Horizontal", 35, 15),
            DIAGONAL_DOWN(1, "DiagonalDown", 30, 25),
            VERTICAL_UP(2, "VerticalUp", 28, 5),
            VERTICAL_ALT(3, "VerticalAlt", 28, 8),
            ANGLE_DOWN(4, "AngleDown", 32, 20),
            LOW_GRIP(5, "LowGrip", 25, 35),
            ANGLE_UP_LEFT(6, "AngleUpLeft", 20, 5),
            VERTICAL_B(7, "VerticalB", 28, -5),
            HORIZONTAL_B(8, "HorizontalB", 38, 12),
            DIAGONAL_LONG(9, "DiagonalLong", 40, 15);

            public final int index;
            public final String name;
            public final int muzzleOffsetX;
            public final int muzzleOffsetY;

            private GripPose(int n2, String string2, int n3, int n4) {
                this.index = n2;
                this.name = string2;
                this.muzzleOffsetX = n3;
                this.muzzleOffsetY = n4;
            }
        }

        public static enum TrajectoryType {
            STRAIGHT("Straight", 0.0, 0),
            ARC("Arc", 0.3, 100),
            HOMING("Homing", 0.0, 0),
            CURVED("Curved", 0.0, 50);

            public final String name;
            public final double gravity;
            public final int turningSpeed;

            private TrajectoryType(String string2, double d, int n2) {
                this.name = string2;
                this.gravity = d;
                this.turningSpeed = n2;
            }
        }

        public static enum GunType {
            PISTOL_A(1, "Pistol", 15, 2.0, 8.0),
            PISTOL_B(2, "Pistol", 15, 2.0, 8.0),
            COMPACT_C(3, "Compact", 12, 3.0, 12.0),
            COMPACT_D(4, "Compact", 12, 3.0, 12.0),
            COMPACT_E(5, "Compact", 12, 3.0, 12.0),
            DETAIL_F(6, "Detail", 18, 1.8, 9.0),
            RIFLE_G(7, "Rifle", 25, 1.0, 14.0),
            RIFLE_H(8, "Rifle", 25, 1.0, 14.0),
            SCIFI_I(9, "Sci-Fi", 20, 1.0, 16.0),
            SPECIAL_J(10, "Special", 35, 0.5, 10.0);

            public final int typeIndex;
            public final String className;
            public final int baseDamage;
            public final double fireRatePerSec;
            public final double projectileVelocity;

            private GunType(int n2, String string2, int n3, double d, double d2) {
                this.typeIndex = n2;
                this.className = string2;
                this.baseDamage = n3;
                this.fireRatePerSec = d;
                this.projectileVelocity = d2;
            }

            public String getGunClass() {
                return "TypeGun" + this.className;
            }
        }
    }

    public static class ObjectPlacementRulesEngine {
        public static final String TYPE_PLACEMENT_RULES = "object_placement_rules_engine";
        public static final Map<String, PlacementRule> PLACEMENT_MATRIX = new LinkedHashMap<String, PlacementRule>(){
            {
                this.put("RED", new PlacementRule("RED", new String[]{"TrapHammer_SwingingBlade", "TrapSpike_UpDown", "TrapElectric_Arc", "Barrel_Damaged", "Barrel_Standard", "FireExtinguisher_Standard", "WarningSign_Triangle", "Platform_Moving_Red", "Portal_LevelEntry"}, new String[]{"Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Hazard_Hammer_6Frames1Row_RedOrangeSwingArc_DamageFrames3to5_Loop90ms.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Barrel_SmallDamagedTipped_RedWithSpill_FloorHazardDeco_DamagedVariant.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Barrel_UprightRedMetal_StandardLabel_FloorDecorOrPushable_VariantA.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_FireExtinguisher_StandardUprightRed_WallMountedDeco_SafetyPropA.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Sign_RedWarningTriangle_HazardAheadWarning_FloorPoleSign_DirectionalA.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Platform_MovingRed_6Frames1Row_SlidingLeftRight_PlayerRideable_Loop100ms.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Portal_LevelEntry_4Frames1Row_RedChevronGateOpening_LevelTransition_PlayOnce120ms.png"}));
                this.put("BLUE", new PlacementRule("BLUE", new String[]{"Money_Collectible", "Bench_Blue", "Screen_Blue_Flicker", "Locker_Blue", "Bucket_Blue", "Crate_Dark", "Monitor_Deco"}, new String[]{"Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Collectible_Money_6Frames1Row_GreenBanknotesSpinFlip_CurrencyPickup_Loop80ms.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Bench_BlueMetal_FlatTopWorkbench_WallPlacedDeco_IndustrialFurniture.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Deco_Screen1_4Frames1Row_BlueMonitorFlicker_WallPanelTechDeco_Loop150ms.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Locker_BlueDoubleDoor_TallMetal_WallPlacedDeco_IndustrialLockerBlue.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Bucket_SmallBlueWithHandle_FloorClutter_AmbientDeco_JanitorialProp.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Crate_DarkWoodSealed_SmallSquare_FloorStackable_StandardCrateA.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Deco_Screen1_4Frames1Row_BlueMonitorFlicker_WallPanelTechDeco_Loop150ms.png"}));
                this.put("GREEN", new PlacementRule("GREEN", new String[]{"Card_Collectible", "Box_Cardboard", "Desk_Wooden", "Board_Wooden", "Crate_Light", "Crate_Locked", "Locker_Red"}, new String[]{"Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Collectible_Card_6Frames1Row_WhiteBlueSpinningFloat_PickupItem_Loop80ms.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Box_CardboardLight_MediumLighter_FloorStackable_CardboardVariantD.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Desk_WoodenWorkstation_DrawersAndSurface_FloorPlacedDeco_WorkstationDesk.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Board_SingleWoodenNoticeBoard_PapersOnWall_WallMountedDeco_VariantA.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Crate_LightWoodPanelled_MediumSquare_FloorStackable_LightWoodVariantB.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Crate_RedLockedClasp_SecuredContainer_InteractiveOrDeco_LockedCrate.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Locker_RedDoubleDoor_TallMetal_WallPlacedDeco_IndustrialLockerA.png"}));
                this.put("YELLOW", new PlacementRule("YELLOW", new String[]{"Warning_Sign", "Box_Light", "Crate_Light", "WarningSign_Question"}, new String[]{"Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Sign_RedWarningTriangle_HazardAheadWarning_FloorPoleSign_DirectionalA.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Box_CardboardLight_MediumLighter_FloorStackable_CardboardVariantD.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Crate_LightWoodPanelled_MediumSquare_FloorStackable_LightWoodVariantB.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Sign_RedLollipopQuestionMark_HintOrMystery_FloorPoleSign_DirectionalB.png"}));
                this.put("ANY", new PlacementRule("ANY", new String[]{"Ladder_Climbable", "Fence_Generic", "Conveyor_Belt", "Chest_Interactive", "Platform_Moving", "Flag_Landmark", "Mop_Deco", "Box_Generic", "Plank_Generic"}, new String[]{"Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Ladder_TallFullHeight_BlueGreyRungs_ShaftWallClimb_ClimbableA.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Fence_SingleLargeFrame_GateShape_ZoneEntranceDeco_FenceGateFrame.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Platform_ConveyorFull_4Frames1Row_FullWidthBeltRunning_MovesPlayerRight_Loop80ms.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Interactive_Chest_8Frames1Row_OrangeRedLidOpenSequence_PlayOnce100ms.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Flag_TallRedBannerPole_GoldEmblem_CheckpointOrBossGateLandmark.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Mop_WoodenHandleBrownHead_FloorLeaningDeco_JanitorialAmbientProp.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Box_OpenTopColoured_ItemsVisible_FloorDeco_OpenBoxWithContents.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Box_ThinFlatPlank_NarrowLow_FloorDeco_FlatPlankOrLidPiece.png"}));
            }
        };

        public static String[] getObjectsForTileset(String string) {
            PlacementRule placementRule = PLACEMENT_MATRIX.get(string.toUpperCase());
            if (placementRule != null) {
                return placementRule.filePaths;
            }
            return ObjectPlacementRulesEngine.PLACEMENT_MATRIX.get((Object)"ANY").filePaths;
        }

        public static String getRandomObjectForTileset(String string) {
            String[] stringArray = ObjectPlacementRulesEngine.getObjectsForTileset(string);
            if (stringArray.length > 0) {
                return stringArray[(int)(Math.random() * (double)stringArray.length)];
            }
            return null;
        }

        public static class PlacementRule {
            public String tileColor;
            public String[] objectTypes;
            public String[] filePaths;

            public PlacementRule(String string, String[] stringArray, String[] stringArray2) {
                this.tileColor = string;
                this.objectTypes = stringArray;
                this.filePaths = stringArray2;
            }
        }
    }

    public static class Level2EnvironmentSystem {
        public static final String TYPE_LEVEL2_ENV = "level2_environment_system";
        public static final String DIRECTORY_LEVEL2_OBJECTS = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects";
        public static final String ANIM_CARD_L2 = "Resources/industrial-zone/1 Tiles/power-station-level-2/4 Animated objects/Anim_Collectible_Card_6Frames1Row_BlueSpinningFloat_PickupItem_Loop80ms.png";
        public static final String ANIM_MONEY_L2 = "Resources/industrial-zone/1 Tiles/power-station-level-2/4 Animated objects/Anim_Collectible_Money_6Frames1Row_GreenBanknotesSpinFlip_CurrencyPickup_Loop80ms.png";
        public static final String ANIM_CHEST_L2 = "Resources/industrial-zone/1 Tiles/power-station-level-2/4 Animated objects/Anim_Interactive_Chest_8Frames1Row_BlueTealLidOpenSequence_PlayOnce100ms.png";
        public static final String ANIM_TRAP_L2 = "Resources/industrial-zone/1 Tiles/power-station-level-2/4 Animated objects/Anim_Hazard_Turret_MultiFrame1Row_TurretFiringProjectile_DamageOnFire_Loop120ms.png";

        public static class PowerLineComponents {
            public static final String DIR_POWER = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/3 Power lines";
            public static final String PYLON_BASE_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/3 Power lines/Prop_Pylon_BaseOnly_VariantB_BlueMetal_PylonFeetAlt.png";
            public static final String PYLON_FULL_A = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/3 Power lines/Prop_Pylon_FullTall_WithAntennaTop_BlueMetal_PylonFullA.png";
            public static final String PYLON_FULL_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/3 Power lines/Prop_Pylon_FullTall_AltTopVariant_BlueMetal_PylonFullB.png";
            public static final String PYLON_LOWER_A = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/3 Power lines/Prop_Pylon_LowerHalf_MediumHeight_BlueMetal_PylonBodyA.png";
            public static final String PYLON_LOWER_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/3 Power lines/Prop_Pylon_LowerHalf_VariantB_BlueMetal_PylonBodyB.png";
            public static final String PYLON_MID = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/3 Power lines/Prop_Pylon_MidSection_VariantB_BlueMetal_PylonMidBody.png";
            public static final String WIRE_DIAGONAL_A = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/3 Power lines/Prop_Wire_DiagonalCable_ThinSingle_PowerLineSegmentA.png";
            public static final String WIRE_DIAGONAL_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/3 Power lines/Prop_Wire_DiagonalCable_ThinAltAngle_PowerLineSegmentB.png";
        }

        public static class DecorationComponents {
            public static final String DIR_DECO = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration";
            public static final String ANTENNA_BRACKET = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Antenna_SmallBracket_HorizontalMount_RooftopAerialB.png";
            public static final String ANTENNA_TALL = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Antenna_TallPole_ThinVertical_RooftopAerialA.png";
            public static final String BUILDING_FACTORY_A = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Building_FactoryStructureA_LargeBackdrop_SceneryA.png";
            public static final String BUILDING_FACTORY_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Building_FactoryStructureB_LargeBackdrop_SceneryB.png";
            public static final String RIVET_A = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Deco_Rivet_TinyDot_BoltDetail_WallRivetA.png";
            public static final String RIVET_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Deco_Rivet_TinyDotVariant_BoltDetail_WallRivetB.png";
            public static final String SHELF_COLORED = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Deco_Shelf_ColouredRack_SmallShelfUnit_WallMountA.png";
            public static final String DEVICE_SMALL = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Deco_SmallDevice_Coloured_MiscEquipment_FloorDeco.png";
            public static final String ITEM_SMALL = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Deco_SmallItem_Accessory_MiscDetail_FloorOrWall.png";
            public static final String TABLET_BLUE = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Deco_Tablet_SmallBlue_TechDevice_WallOrFloor.png";
            public static final String WAVE_ARCH = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Deco_WaveArch_DarkBlue_ArchShape_CeilingDeco.png";
            public static final String WIRE_SEGMENT_A = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Deco_WireSegment_TinyHorizontal_CableDetail_WallA.png";
            public static final String WIRE_SEGMENT_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Deco_WireSegment_TinyVariant_CableDetailB_WallB.png";
            public static final String CHAIR_OFFICE = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Furniture_OfficeChair_BlueSeat_BreakRoomProp.png";
            public static final String SHELF_NARROW = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Furniture_Shelf_MetalNarrow_IndustrialRackA.png";
            public static final String SHELF_WIDE = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Furniture_Shelf_MetalWide_IndustrialRackB.png";
            public static final String SCREEN_CONTROL = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Screen_ControlPanel_LargePinkHighlight_AltConsole.png";
            public static final String SCREEN_MONITOR_A = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Screen_MonitorSingle_TechPanel_WallScreenA.png";
            public static final String SCREEN_MONITOR_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Screen_MonitorDouble_TechPanel_WallScreenB.png";
            public static final String SCREEN_MONITOR_C = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Screen_MonitorDetail_TechPanel_WallScreenC.png";
            public static final String SIGN_WARNING_A = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Sign_Warning_YellowTypeA_HazardNoticeA.png";
            public static final String SIGN_WARNING_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Sign_Warning_YellowTypeB_HazardNoticeB.png";
            public static final String SIGN_WARNING_C = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Sign_Warning_YellowSmall_HazardNoticeC.png";
            public static final String TANK_FACTORY_A = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Tank_Industrial_LabelledAC3_LargeTankA.png";
            public static final String TANK_FACTORY_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/2 Decoration/Prop_Tank_Industrial_LabelledEFG2_LargeTankB.png";
        }

        public static class PipeComponents {
            public static final String DIR_PIPES = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube";
            public static final String PIPE_ARCH_LOOP = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/Prop_Pipe_ArchLoop_RedMetal_CurvedSection_WallDeco.png";
            public static final String PIPE_CONNECTOR_A = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/Prop_Pipe_Connector_DarkBlue_SmallJoint_PipeJoinA.png";
            public static final String PIPE_CONNECTOR_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/Prop_Pipe_Connector_DarkBlue_SmallJointVariant_PipeJoinB.png";
            public static final String PIPE_ELBOW = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/Prop_Pipe_ElbowBend_DarkBlueGrey_LBend_CornerJoin.png";
            public static final String PIPE_END_CAP_A = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/Prop_Pipe_EndCap_DarkNavy_SmallCap_PipeTerminatorA.png";
            public static final String PIPE_END_CAP_B = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/Prop_Pipe_EndCap_DarkBlue_SmallCap_PipeTerminatorB.png";
            public static final String PIPE_HORIZONTAL = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/Prop_Pipe_Horizontal_BlueWithClamp_BracketedRun_WallMount.png";
            public static final String PIPE_TJUNCTION = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/Prop_Pipe_TJunction_DarkNavy_ThreeWayConnector_PipeHub.png";
            public static final String PIPE_VERTICAL_RED = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/Prop_Pipe_Vertical_RedMetal_StraightSegment_WallRun.png";
            public static final String PIPE_VERTICAL_TALL = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/Prop_Pipe_Vertical_TallBlue_FullHeightRun_BackgroundPipe.png";
            public static final String PIPE_VERTICAL_THIN = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects/1 Tube/Prop_Pipe_Vertical_ThinDarkBlue_StripRun_WallDetail.png";
        }
    }

    public static class AnimatedObjectsSystem {
        public static final String TYPE_ANIMATED_OBJECTS = "animated_objects_system";
        public static final String ANIM_CARD_COLLECTIBLE = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Collectible_Card_6Frames1Row_WhiteBlueSpinningFloat_PickupItem_Loop80ms.png";
        public static final int ANIM_CARD_FRAMES = 6;
        public static final int ANIM_CARD_TIMING_MS = 80;
        public static final String ANIM_CARD_SPAWN_COLOR = "GREEN";
        public static final String ANIM_MONEY_COLLECTIBLE = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Collectible_Money_6Frames1Row_GreenBanknotesSpinFlip_CurrencyPickup_Loop80ms.png";
        public static final int ANIM_MONEY_FRAMES = 6;
        public static final int ANIM_MONEY_TIMING_MS = 80;
        public static final String ANIM_MONEY_SPAWN_COLOR = "BLUE";
        public static final String ANIM_SCREEN_BLUE = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Deco_Screen1_4Frames1Row_BlueMonitorFlicker_WallPanelTechDeco_Loop150ms.png";
        public static final int ANIM_SCREEN_BLUE_FRAMES = 4;
        public static final int ANIM_SCREEN_BLUE_TIMING_MS = 150;
        public static final String ANIM_SCREEN_REDBLUE = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Deco_Screen2_4Frames1Row_RedBlueMonitorFlicker_WallPanelAltDeco_Loop150ms.png";
        public static final int ANIM_SCREEN_REDBLUE_FRAMES = 4;
        public static final int ANIM_SCREEN_REDBLUE_TIMING_MS = 150;
        public static final String ANIM_HAZARD_HAMMER = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Hazard_Hammer_6Frames1Row_RedOrangeSwingArc_DamageFrames3to5_Loop90ms.png";
        public static final int ANIM_HAMMER_FRAMES = 6;
        public static final int ANIM_HAMMER_TIMING_MS = 90;
        public static final int ANIM_HAMMER_DAMAGE_FRAME_START = 3;
        public static final int ANIM_HAMMER_DAMAGE_FRAME_END = 5;
        public static final String ANIM_HAMMER_SPAWN_COLOR = "RED";
        public static final String ANIM_CHEST_INTERACTIVE = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Interactive_Chest_8Frames1Row_OrangeRedLidOpenSequence_PlayOnce100ms.png";
        public static final int ANIM_CHEST_FRAMES = 8;
        public static final int ANIM_CHEST_TIMING_MS = 100;
        public static final boolean ANIM_CHEST_LOOPS = false;
        public static final String ANIM_CONVEYOR_FAST = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Platform_ConveyorFastVariant_4Frames1Row_AltSpeedOrDirection_Loop60ms.png";
        public static final int ANIM_CONVEYOR_FAST_FRAMES = 4;
        public static final int ANIM_CONVEYOR_FAST_TIMING_MS = 60;
        public static final String ANIM_CONVEYOR_FULL = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Platform_ConveyorFull_4Frames1Row_FullWidthBeltRunning_MovesPlayerRight_Loop80ms.png";
        public static final int ANIM_CONVEYOR_FULL_FRAMES = 4;
        public static final int ANIM_CONVEYOR_FULL_TIMING_MS = 80;
        public static final String ANIM_CONVEYOR_DIRECTION = "RIGHT";
        public static final String ANIM_CONVEYOR_SECTION = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Platform_ConveyorSection_4Frames1Row_RepeatableTileSegment_Loop80ms.png";
        public static final int ANIM_CONVEYOR_SECTION_FRAMES = 4;
        public static final int ANIM_CONVEYOR_SECTION_TIMING_MS = 80;
        public static final String ANIM_CONVEYOR_SHORT = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Platform_ConveyorShortA_4Frames1Row_ShorterBeltSegment_MovesPlayerRight_Loop80ms.png";
        public static final int ANIM_CONVEYOR_SHORT_FRAMES = 4;
        public static final int ANIM_CONVEYOR_SHORT_TIMING_MS = 80;
        public static final String ANIM_PLATFORM_MOVING_RED = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Platform_MovingRed_6Frames1Row_SlidingLeftRight_PlayerRideable_Loop100ms.png";
        public static final int ANIM_PLATFORM_MOVING_FRAMES = 6;
        public static final int ANIM_PLATFORM_MOVING_TIMING_MS = 100;
        public static final String ANIM_PLATFORM_MOVING_COLOR = "RED";
        public static final String ANIM_PORTAL_LEVEL_ENTRY = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Portal_LevelEntry_4Frames1Row_RedChevronGateOpening_LevelTransition_PlayOnce120ms.png";
        public static final int ANIM_PORTAL_FRAMES = 4;
        public static final int ANIM_PORTAL_TIMING_MS = 120;
        public static final boolean ANIM_PORTAL_LOOPS = false;
    }

    public static class StaticPropsSystem {
        public static final String TYPE_STATIC_PROPS = "static_props_system";
        public static final String DIRECTORY_LEVEL1_OBJECTS = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects";
        public static final String DIRECTORY_LEVEL1_ANIM = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects";
        public static final String DIRECTORY_LEVEL2_OBJECTS = "Resources/industrial-zone/1 Tiles/power-station-level-2/3 Objects";
        public static final String DIRECTORY_LEVEL2_ANIM = "Resources/industrial-zone/1 Tiles/power-station-level-2/4 Animated objects";
        public static final String PROP_BARREL_SMALL_DAMAGED = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Barrel_SmallDamagedTipped_RedWithSpill_FloorHazardDeco_DamagedVariant.png";
        public static final String PROP_BARREL_TALL_DARK = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Barrel_TallDarkRed_WornIndustrial_FloorDecorOrPushable_TallVariant.png";
        public static final String PROP_BARREL_UPRIGHT_STANDARD = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Barrel_UprightRedMetal_StandardLabel_FloorDecorOrPushable_VariantA.png";
        public static final String PROP_BARREL_UPRIGHT_ALT = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Barrel_UprightRedMetal_AltLabelVariant_FloorDecorOrPushable_VariantB.png";
        public static final String PROP_BENCH_BLUE_METAL = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Bench_BlueMetal_FlatTopWorkbench_WallPlacedDeco_IndustrialFurniture.png";
        public static final String PROP_BOARD_SINGLE = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Board_SingleWoodenNoticeBoard_PapersOnWall_WallMountedDeco_VariantA.png";
        public static final String PROP_BOARD_DOUBLE = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Board_DoubleWoodenNoticeBoard_MorePapers_WallMountedDeco_VariantB.png";
        public static final String PROP_BOARD_TWIN = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Board_TwinPanelWoodenBoards_SideBySide_WallMountedDeco_VariantC.png";
        public static final String PROP_BOX_CARDBOARD_LIGHT = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Box_CardboardLight_MediumLighter_FloorStackable_CardboardVariantD.png";
        public static final String PROP_BOX_CARDBOARD_TAPED = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Box_CardboardTaped_MediumBrown_FloorStackable_CardboardVariantC.png";
        public static final String PROP_BOX_OPEN_TOP = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Box_OpenTopColoured_ItemsVisible_FloorDeco_OpenBoxWithContents.png";
        public static final String PROP_BOX_SQUASHED = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Box_SquashedFlat_BrokenEmpty_FloorDebrisDeco_CrushedBox.png";
        public static final String PROP_BOX_PLANK = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Box_ThinFlatPlank_NarrowLow_FloorDeco_FlatPlankOrLidPiece.png";
        public static final String PROP_BUCKET_SMALL_BLUE = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Bucket_SmallBlueWithHandle_FloorClutter_AmbientDeco_JanitorialProp.png";
        public static final String PROP_CRATE_DARK_WOOD = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Crate_DarkWoodSealed_SmallSquare_FloorStackable_StandardCrateA.png";
        public static final String PROP_CRATE_LIGHT_WOOD = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Crate_LightWoodPanelled_MediumSquare_FloorStackable_LightWoodVariantB.png";
        public static final String PROP_CRATE_RED_LOCKED = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Crate_RedLockedClasp_SecuredContainer_InteractiveOrDeco_LockedCrate.png";
        public static final String PROP_DESK_WOODEN = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Desk_WoodenWorkstation_DrawersAndSurface_FloorPlacedDeco_WorkstationDesk.png";
        public static final String PROP_FENCE_GATE_FRAME = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Fence_SingleLargeFrame_GateShape_ZoneEntranceDeco_FenceGateFrame.png";
        public static final String PROP_FENCE_THREE_POSTS = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Fence_ThreeRedPostsWire_WiderSegment_ZoneDividerDeco_FenceSegmentB.png";
        public static final String PROP_FENCE_TWO_POSTS = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Fence_TwoRedPostsWire_ShortSegment_ZoneDividerDeco_FenceSegmentA.png";
        public static final String PROP_FIRE_EXT_STANDARD = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_FireExtinguisher_StandardUprightRed_WallMountedDeco_SafetyPropA.png";
        public static final String PROP_FIRE_EXT_KNOCKED = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_FireExtinguisher_SmallKnockedOver_FloorDebris_SafetyPropDamaged.png";
        public static final String PROP_FIRE_EXT_SPRAYING = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_FireExtinguisher_ActiveSprayingFlame_InUseDeco_SafetyPropActive.png";
        public static final String PROP_FLAG_TALL_RED = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Flag_TallRedBannerPole_GoldEmblem_CheckpointOrBossGateLandmark.png";
        public static final String PROP_LADDER_SHORT = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Ladder_ShortHorizontalRung_BlueCrossbar_PlatformConnector_Short.png";
        public static final String PROP_LADDER_TALL_ALT = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Ladder_TallAltSpacing_BlueGreyRungs_ShaftWallClimb_ClimbableB.png";
        public static final String PROP_LADDER_TALL_FULL = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Ladder_TallFullHeight_BlueGreyRungs_ShaftWallClimb_ClimbableA.png";
        public static final String PROP_LOCKER_BLUE_DOUBLE = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Locker_BlueDoubleDoor_TallMetal_WallPlacedDeco_IndustrialLockerBlue.png";
        public static final String PROP_LOCKER_RED_DOUBLE = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Locker_RedDoubleDoor_TallMetal_WallPlacedDeco_IndustrialLockerA.png";
        public static final String PROP_LOCKER_RED_SINGLE_OPEN = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Locker_RedSingleOpenDoor_TallMetal_WallPlacedDeco_IndustrialLockerOpen.png";
        public static final String PROP_MOP_WOODEN = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Mop_WoodenHandleBrownHead_FloorLeaningDeco_JanitorialAmbientProp.png";
        public static final String PROP_SIGN_WARNING_TRIANGLE = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Sign_RedWarningTriangle_HazardAheadWarning_FloorPoleSign_DirectionalA.png";
        public static final String PROP_SIGN_QUESTION_LOLLIPOP = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Sign_RedLollipopQuestionMark_HintOrMystery_FloorPoleSign_DirectionalB.png";
        public static final String[] UI_DIGITS = new String[]{"Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/UI_Digit_Zero_GreyPixelFont_HUDScoreDisplay_SingleGlyph.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/UI_Digit_One_GreyPixelFont_HUDScoreDisplay_SingleGlyph.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/UI_Digit_Two_GreyPixelFont_HUDScoreDisplay_SingleGlyph.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/UI_Digit_Three_GreyPixelFont_HUDScoreDisplay_SingleGlyph.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/UI_Digit_Four_GreyPixelFont_HUDScoreDisplay_SingleGlyph.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/UI_Digit_Five_GreyPixelFont_HUDScoreDisplay_SingleGlyph.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/UI_Digit_Six_GreyPixelFont_HUDScoreDisplay_SingleGlyph.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/UI_Digit_Seven_GreyPixelFont_HUDScoreDisplay_SingleGlyph.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/UI_Digit_Eight_GreyPixelFont_HUDScoreDisplay_SingleGlyph.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/UI_Digit_Nine_GreyPixelFont_HUDScoreDisplay_SingleGlyph.png"};
    }

    public static class CompleteSpriteChainsWorkflow {
        public static final String DOCUMENTATION = "Complete 10-phase workflow with visual diagram above";
        public static final String[] PHASE_SEQUENCE = new String[]{"PHASE 1: Load character base animation (24 types available)", "PHASE 2: Check if armed (load weapon overlay or melee)", "PHASE 3: Load weapon animation (10 types per character)", "PHASE 4: Get gun sprite (20 weapons, 5 tiers)", "PHASE 5: Get hand grip pose (10 angles \u00d7 3 characters)", "PHASE 6: Render armed character (4-layer composite)", "PHASE 7: On fire: spawn bullet sprite (13 types)", "PHASE 8: Display tracer effect entire flight (10 types)", "PHASE 9: On impact: spawn spark VFX (8 types, 4 frames each)", "PHASE 10: Clean up temporary sprites, return to Phase 1"};
    }

    public static class ImpactVfxSparksChain {
        public static final String PHASE_NAME = "PHASE 7: IMPACT VFX EFFECTS";
        public static final String SPARK_SMALL_GOLD = "Resources/industrial-zone/vfx/3 Sparks/01_VFX_Sparks_Burst_4Frames1Row_SmallSparseGold_Impact_PlayOnce_80ms.png";
        public static final String SPARK_MEDIUM_BOLD = "Resources/industrial-zone/vfx/3 Sparks/05_VFX_Sparks_Burst_4Frames1Row_MediumBoldBurst_Impact_PlayOnce_80ms.png";
        public static final String SPARK_LARGE_SCATTER = "Resources/industrial-zone/vfx/3 Sparks/08_VFX_Sparks_Burst_4Frames1Row_LargeWideScatter_Impact_PlayOnce_80ms.png";
        public static final int FRAMES_PER_SPARK = 4;
        public static final int TIMING_MS_PER_FRAME = 80;
        public static final int TOTAL_DURATION_MS = 320;
        public static final int TOTAL_SPARK_EFFECTS = 8;
    }

    public static class BulletSpriteChain {
        public static final String PHASE_NAME = "PHASE 6: BULLET SPRITES";
        public static final String BULLET_A = "Resources/industrial-zone/weapons/1/5 Bullets/01_Weapon_Bullet_TypeA_Single_StaticSprite.png";
        public static final String BULLET_B = "Resources/industrial-zone/weapons/1/5 Bullets/02_Weapon_Bullet_TypeB_Single_StaticSprite.png";
        public static final String BULLET_D_VA = "Resources/industrial-zone/weapons/1/5 Bullets/04_Weapon_Bullet_TypeD_VariantA_StaticSprite.png";
        public static final String BULLET_G_VA = "Resources/industrial-zone/weapons/1/5 Bullets/09_Weapon_Bullet_TypeG_VariantA_StaticSprite.png";
        public static final int TOTAL_BULLET_TYPES = 13;
    }

    public static class ProjectileTracerEffectChain {
        public static final String PHASE_NAME = "PHASE 5: PROJECTILE TRACERS";
        public static final String TRACER_A_NARROW = "Resources/industrial-zone/weapons/1/4 Shoot_effects/01_Weapon_ShootFX_Tracer_TypeA_VariantNarrow_StaticSprite.png";
        public static final String TRACER_B_DOTTED = "Resources/industrial-zone/weapons/1/4 Shoot_effects/03_Weapon_ShootFX_Tracer_TypeB_VariantDotted_StaticSprite.png";
        public static final String TRACER_C_HEAVY = "Resources/industrial-zone/weapons/1/4 Shoot_effects/05_Weapon_ShootFX_Tracer_TypeC_VariantHeavy_StaticSprite.png";
        public static final String TRACER_E_LASER = "Resources/industrial-zone/weapons/1/4 Shoot_effects/09_Weapon_ShootFX_Tracer_TypeE_VariantLaser_StaticSprite.png";
        public static final int TOTAL_TRACER_TYPES = 10;
    }

    public static class HandGripPosesChain {
        public static final String PHASE_NAME = "PHASE 4: HAND GRIP POSES";
        public static final String BIKER_GRIP_HORIZONTAL = "Resources/industrial-zone/weapons/1/3 Hands/1 Biker/01_Weapon_Hand_Biker_GripHorizontal_WeaponHoldPose_StaticSprite.png";
        public static final String BIKER_GRIP_VERTICAL = "Resources/industrial-zone/weapons/1/3 Hands/1 Biker/03_Weapon_Hand_Biker_GripVertical_WeaponHoldPose_StaticSprite.png";
        public static final String BIKER_GRIP_DIAGONAL_UP = "Resources/industrial-zone/weapons/1/3 Hands/1 Biker/05_Weapon_Hand_Biker_GripDiagonalUp_WeaponHoldPose_StaticSprite.png";
        public static final int GRIP_ANGLES_PER_CHARACTER = 10;
        public static final int TOTAL_GRIP_POSES = 30;
    }

    public static class GunWeaponSpriteChain {
        public static final String PHASE_NAME = "PHASE 3: GUN WEAPON SPRITES";
        public static final String GUN_PISTOL_A_DARK = "Resources/industrial-zone/weapons/1/2 Guns/01_Weapon_Gun_Pistol_TypeA_VariantDark_StaticSprite.png";
        public static final String GUN_PISTOL_A_LIGHT = "Resources/industrial-zone/weapons/1/2 Guns/02_Weapon_Gun_Pistol_TypeA_VariantLight_StaticSprite.png";
        public static final String GUN_COMPACT_C = "Resources/industrial-zone/weapons/1/2 Guns/05_Weapon_Gun_Compact_TypeC_VariantDark_StaticSprite.png";
        public static final String GUN_RIFLE_G_DARK = "Resources/industrial-zone/weapons/1/2 Guns/13_Weapon_Gun_Rifle_TypeG_VariantDark_StaticSprite.png";
        public static final String GUN_SPECIAL_J = "Resources/industrial-zone/weapons/1/2 Guns/19_Weapon_Gun_Special_TypeJ_VariantTeal_StaticSprite.png";
        public static final int TOTAL_GUN_TYPES = 20;
        public static final int GUN_TIERS = 5;
    }

    public static class WeaponOverlayAnimationChain {
        public static final String PHASE_NAME = "PHASE 2: WEAPON OVERLAY ANIMATIONS";
        public static final String BIKER_WEAPON_IDLE_VA = "Resources/industrial-zone/weapons/1/1 Characters/1 Biker/01_Weapon_Biker_Idle_VariantA_4Frames1Row_WeaponIdleStand_Loop_150ms.png";
        public static final String BIKER_WEAPON_IDLE_VB = "Resources/industrial-zone/weapons/1/1 Characters/1 Biker/02_Weapon_Biker_Idle_VariantB_4Frames1Row_WeaponIdleStand_Loop_150ms.png";
        public static final String BIKER_WEAPON_JUMP_VA = "Resources/industrial-zone/weapons/1/1 Characters/1 Biker/03_Weapon_Biker_Jump_VariantA_3Frames1Row_WeaponJumpArc_PlayOnce_80ms.png";
        public static final String BIKER_WEAPON_RUN_VA = "Resources/industrial-zone/weapons/1/1 Characters/1 Biker/05_Weapon_Biker_Run_VariantA_5Frames1Row_WeaponRunCycle_Loop_80ms.png";
        public static final String BIKER_WEAPON_WALK_VA = "Resources/industrial-zone/weapons/1/1 Characters/1 Biker/09_Weapon_Biker_Walk_VariantA_4Frames1Row_WeaponWalkCycle_Loop_100ms.png";
        public static final int WEAPON_STATES_PER_CHARACTER = 5;
        public static final int VARIANTS_PER_STATE = 2;
        public static final int TOTAL_WEAPON_ANIMATIONS_PER_CHARACTER = 10;
    }

    public static class CharacterBaseAnimationChain {
        public static final String PHASE_NAME = "PHASE 1: CHARACTER BASE ANIMATIONS";
        public static final String BIKER_IDLE = "Resources/industrial-zone/characters/player/biker/01_Player_Biker_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png";
        public static final String BIKER_WALK = "Resources/industrial-zone/characters/player/biker/03_Player_Biker_Walk_5Frames1Row_WalkingCycleForward_Movement_Loop_100ms.png";
        public static final String BIKER_RUN = "Resources/industrial-zone/characters/player/biker/04_Player_Biker_Run_5Frames1Row_FullRunCycle_FastMovement_Loop_80ms.png";
        public static final String BIKER_JUMP = "Resources/industrial-zone/characters/player/biker/06_Player_Biker_Jump_3Frames1Row_JumpRiseArc_JumpStart_PlayOnce_80ms.png";
        public static final String BIKER_FALL = "Resources/industrial-zone/characters/player/biker/08_Player_Biker_Fall_4Frames1Row_FallingDescend_AirFall_Loop_100ms.png";
        public static final String BIKER_PUNCH = "Resources/industrial-zone/characters/player/biker/12_Player_Biker_Punch_5Frames1Row_StandingPunchCombo_MeleeAttack_PlayOnce_70ms.png";
        public static final String BIKER_DEATH = "Resources/industrial-zone/characters/player/biker/19_Player_Biker_Death_6Frames1Row_DeathFallSequence_PlayerDeath_PlayOnce_120ms.png";
        public static final int TOTAL_BASE_ANIMATIONS_PER_CHARACTER = 24;
        public static final int TOTAL_FRAMES_PER_CHARACTER = 120;
    }

    public static class SpriteChainInterconnectionSystem {
        public static final String TYPE_SPRITE_CHAINS = "complete_sprite_chains";
        public static final int CHAIN_LAYERS_ARMED = 4;
        public static final String[] ARM_SEQUENCE = new String[]{"Load_Base_Animation", "Check_Armed_Status", "Load_Overlay_Animation", "Fetch_Gun_Sprite", "Get_Hand_Grip_Pose", "Render_4_Layer_Composite"};
        public static final String[] FIRE_SEQUENCE = new String[]{"Play_Charge_Animation", "Spawn_Bullet", "Display_Tracer_Effect", "Bullet_Travels", "Collision_Detection", "Spawn_Impact_VFX", "Play_Impact_Audio", "Cleanup_Bullet"};
        public static final Map<String, String[]> OBJECT_SPAWN_RULES = new LinkedHashMap<String, String[]>(){
            {
                this.put("RED", new String[]{"TrapSpike_Up_Down", "TrapBlade_Horizontal", "TrapElectric_Arc", "LaserField_Horizontal"});
                this.put("BLUE", new String[]{"Money_Coin", "MoneyBag_Large", "Collectible_Cash"});
                this.put("GREEN", new String[]{"Card_Standard", "CardPack_Bundle", "Collectible_Key"});
                this.put("YELLOW", new String[]{"Star_Bonus", "StarsBundle_Multi", "PowerUp_Shield"});
                this.put("ANY", new String[]{"Box_Wooden", "Crate_Metal", "Barrel_Explosive"});
            }
        };
        public static final Map<String, Integer> OBJECT_ANIMATION_FRAMES = new LinkedHashMap<String, Integer>(){
            {
                this.put("Money_Coin", 6);
                this.put("Card_Standard", 6);
                this.put("Star_Bonus", 4);
                this.put("TrapSpike_Up_Down", 2);
                this.put("Box_Wooden", 0);
                this.put("Barrel_Explosive", 0);
            }
        };
        public static final Map<String, Integer> OBJECT_ANIMATION_TIMING = new LinkedHashMap<String, Integer>(){
            {
                this.put("Money_Coin", 100);
                this.put("Card_Standard", 100);
                this.put("Star_Bonus", 150);
                this.put("TrapSpike_Up_Down", 400);
            }
        };

        public static String[] getObjectsForTileset(String string) {
            return OBJECT_SPAWN_RULES.getOrDefault(string.toUpperCase(), new String[0]);
        }

        public static int getAnimationFrames(String string) {
            return OBJECT_ANIMATION_FRAMES.getOrDefault(string, 0);
        }

        public static int getAnimationTiming(String string) {
            return OBJECT_ANIMATION_TIMING.getOrDefault(string, 100);
        }
    }

    public static class TilesetCompositionSystem {
        public static final String TYPE_TILESET_COMPOSITION = "tileset_composition_system";
        public static final String COLOR_RED = "Red";
        public static final String COLOR_BLUE = "Blue";
        public static final String COLOR_GREEN = "Green";
        public static final String COLOR_YELLOW = "Yellow";
        public static final String COLOR_PURPLE = "Purple";
        public static final String COLOR_ORANGE = "Orange";
        public static final String POS_LEFT_CORNER = "LeftCorner";
        public static final String POS_CENTER = "Center";
        public static final String POS_RIGHT_CORNER = "RightCorner";
        public static final String POS_TOP_LEFT = "TopLeft";
        public static final String POS_TOP_CENTER = "TopCenter";
        public static final String POS_TOP_RIGHT = "TopRight";
        public static final String POS_MID_LEFT = "MidLeft";
        public static final String POS_MID_CENTER = "MidCenter";
        public static final String POS_MID_RIGHT = "MidRight";
        public static final String POS_BOT_LEFT = "BotLeft";
        public static final String POS_BOT_CENTER = "BotCenter";
        public static final String POS_BOT_RIGHT = "BotRight";
        public static final int TILE_WIDTH = 32;
        public static final int TILE_HEIGHT = 32;
        public static final String[][] PATTERN_HORIZONTAL = new String[][]{{"LeftCorner", "Center", "RightCorner"}};
        public static final String[][] PATTERN_VERTICAL = new String[][]{{"TopLeft"}, {"MidCenter"}, {"BotRight"}};
        public static final String[][] PATTERN_BOX_3x3 = new String[][]{{"TopLeft", "TopCenter", "TopRight"}, {"MidLeft", "MidCenter", "MidRight"}, {"BotLeft", "BotCenter", "BotRight"}};

        public static String getTileFile(String string, String string2) {
            String string3 = "Resources/industrial-zone/1 Tiles/Level1/2 Background_level_1";
            return string3 + "/" + string + "_" + string2 + ".png";
        }

        public static String[] getHorizontalPlatform(String string, int n) {
            if (n < 3) {
                AnimationAndSpriteLoader.logError("Platform width must be at least 3 tiles");
                return null;
            }
            String[] stringArray = new String[n];
            stringArray[0] = TilesetCompositionSystem.getTileFile(string, POS_LEFT_CORNER);
            for (int i = 1; i < n - 1; ++i) {
                stringArray[i] = TilesetCompositionSystem.getTileFile(string, POS_CENTER);
            }
            stringArray[n - 1] = TilesetCompositionSystem.getTileFile(string, POS_RIGHT_CORNER);
            return stringArray;
        }
    }

    public static class SoundEffectsRegistry {
        public static final String TYPE_SFX = "sound_effects_system";
        public static final String DIRECTORY = "Resources/industrial-zone/audio/sfx";
        public static final String CATEGORY_IMPACT = "impact";
        public static final String CATEGORY_MOVEMENT = "movement";
        public static final String CATEGORY_INTERACTION = "interaction";
        public static final String CATEGORY_FEEDBACK = "feedback";
        public static final String CATEGORY_ENEMY = "enemy";
        public static final String CATEGORY_ENVIRONMENT = "environment";
        public static final String SFX_BULLET_HIT = "Laser_sword_1.wav";
        public static final String SFX_EXPLOSION = "Explosion.wav";
        public static final String SFX_BOMB_DROP = "Bomb_drop.wav";
        public static final String SFX_FOOTSTEP_1 = "Samurai_footstep_1.wav";
        public static final String SFX_FOOTSTEP_2 = "Samurai_footstep_2.wav";
        public static final String SFX_WHOOSH = "Flying_platform_attack_1.wav";
        public static final String SFX_DOOR_OPEN = "Door_with_password.wav";
        public static final String SFX_DOOR_CLOSE = "Sliding_doors.wav";
        public static final String SFX_DOOR_LOCKED = "Unlocked_chest.wav";
        public static final String SFX_CHEST_OPEN = "Unlocked_chest.wav";
        public static final String SFX_COLLECTIBLE = "Click_digital_1.wav";
        public static final String SFX_BELL = "Bell_on_the_door.wav";
        public static final String SFX_ZIPLINE = "Zipline_loopable.wav";
        public static final String SFX_PORTAL = "Portal_1.wav";
        public static final String SFX_ELEVATOR = "Elevator_motor.wav";
        public static final String SFX_ROLLER_DOOR = "Roller_doors.wav";
        public static final String SFX_PLATFORM = "Lift_mechanism.wav";
        public static final String SFX_ROBOT_ATTACK_1 = "Flying_platform_attack_1.wav";
        public static final String SFX_ROBOT_ATTACK_2 = "Flying_platform_attack_2.wav";
        public static final String SFX_ROBOT_WALK = "Hovering_robot_walk_loopable.wav";
        public static final String SFX_ROBOT_STING = "Hovering_robot_sting.wav";
        public static final String SFX_KARATEKA_ATTACK = "Karateka_attack.wav";
        public static final String SFX_SAMURAI_DEATH = "Samurai_death.wav";
        public static final String SFX_CLICK_1 = "Click_digital_1.wav";
        public static final String SFX_CLICK_2 = "Click_digital_2.wav";
        public static final String SFX_LASER_SWORD = "Laser_sword_2.wav";
        public static final String[] ALL_SFX_FILES = new String[]{"Alternative_theme_Chinese_Street.wav", "Battle_theme_Chinese_Street.wav", "Bell_on_the_door.wav", "Bomb_drop.wav", "Calm_theme_Chinese_Street.wav", "Click_digital_1.wav", "Click_digital_2.wav", "Creating_wooden_door.wav", "Door_with_password.wav", "Elevator_motor.wav", "Explosion.wav", "Flying_platform_attack_1.wav", "Flying_platform_attack_2.wav", "Hovering_robot_sting.wav", "Hovering_robot_walk_loopable.wav", "Karateka_attack.wav", "Laser_sword_1.wav", "Laser_sword_2.wav", "Lift_mechanism.wav", "Main_theme_Chinese_Street.wav", "Melody_of_attraction_loopable.wav", "Melody_of_the_win.wav", "Portal_1.wav", "Portal_2.wav", "Portal_closing.wav", "Portal_moving.wav", "Push_slide_door.wav", "Roller_doors.wav", "Samurai_death.wav", "Samurai_footstep_1.wav", "Samurai_footstep_2.wav", "Sliding_doors.wav", "Stealthy_theme_loopable.wav", "Unlocked_chest.wav", "Zipline_loopable.wav"};
        public static final float VOLUME_IMPACT = 1.0f;
        public static final float VOLUME_MOVEMENT = 0.7f;
        public static final float VOLUME_INTERACTION = 0.8f;
        public static final float VOLUME_FEEDBACK = 0.6f;
        public static final float VOLUME_ENEMY = 0.9f;
        public static final float VOLUME_ENVIRONMENT = 0.75f;

        public static String getSoundFile(String string) {
            for (String string2 : ALL_SFX_FILES) {
                if (!string2.equalsIgnoreCase(string) && !string2.replace(".wav", "").equalsIgnoreCase(string)) continue;
                return AnimationAndSpriteLoader.AUDIO_SFX + string2;
            }
            AnimationAndSpriteLoader.logError("Sound effect not found: " + string);
            return null;
        }

        public static String getSoundFileByIndex(int n) {
            if (n >= 0 && n < ALL_SFX_FILES.length) {
                return AnimationAndSpriteLoader.AUDIO_SFX + ALL_SFX_FILES[n];
            }
            AnimationAndSpriteLoader.logError("Invalid SFX index: " + n);
            return null;
        }

        public static float getVolumeForCategory(String string) {
            switch (string.toLowerCase()) {
                case "impact": {
                    return 1.0f;
                }
                case "movement": {
                    return 0.7f;
                }
                case "interaction": {
                    return 0.8f;
                }
                case "feedback": {
                    return 0.6f;
                }
                case "enemy": {
                    return 0.9f;
                }
                case "environment": {
                    return 0.75f;
                }
            }
            return 1.0f;
        }
    }

    public static class MusicAudioRegistry {
        public static final String TYPE_MUSIC = "music_audio_system";
        public static final String DIRECTORY_MIDI = "Resources/industrial-zone/audio/music_midi";
        public static final String DIRECTORY_WAV = "Resources/industrial-zone/audio/music_wav";
        public static final int TOTAL_MIDI_TRACKS = 5;
        public static final String[] MIDI_TRACKS = new String[]{"Track 1.mid", "Track 2.mid", "Track 3.mid", "Track 4.mid", "Track 5.mid"};
        public static final String MUSIC_MAIN_THEME_CHINESE = "Main_theme_Chinese_Street.wav";
        public static final String MUSIC_BATTLE_THEME = "Battle_theme_Chinese_Street.wav";
        public static final String MUSIC_CALM_THEME = "Calm_theme_Chinese_Street.wav";
        public static final String MUSIC_ALTERNATIVE_THEME = "Alternative_theme_Chinese_Street_treat.wav";
        public static final String MUSIC_STEALTHY_THEME = "Stealthy_theme_loopable.wav";
        public static final String[] MUSIC_WAV_TRACKS = new String[]{"Alternative_theme_Chinese_Street_treat.wav", "Battle_theme_Chinese_Street.wav", "Bell_on_the_door.wav", "Bomb_drop.wav", "Calm_theme_Chinese_Street.wav", "Click_digital_1.wav", "Click_digital_2.wav", "Creating_wooden_door.wav", "Door_with_password.wav", "Elevator_motor.wav", "Explosion.wav", "Flying_platform_attack_1.wav", "Flying_platform_attack_2.wav", "Hovering_robot_sting.wav", "Hovering_robot_walk_loopable.wav", "Karateka_attack.wav", "Laser_sword_1.wav", "Laser_sword_2.wav", "Lift_mechanism.wav", "Main_theme_Chinese_Street.wav", "Melody_of_attraction_loopable.wav", "Melody_of_the_win.wav", "Portal_1.wav", "Portal_2.wav", "Portal_closing.wav", "Portal_moving.wav", "Push_slide_door.wav", "Roller_doors.wav", "Samurai_death.wav", "Samurai_footstep_1.wav", "Samurai_footstep_2.wav", "Sliding_doors.wav", "Stealthy_theme_loopable.wav", "Unlocked_chest.wav", "Zipline_loopable.wav"};
        public static final String CATEGORY_MAIN_THEME = "main_theme";
        public static final String CATEGORY_BATTLE = "battle";
        public static final String CATEGORY_EXPLORATION = "exploration";
        public static final String CATEGORY_BOSS = "boss";
        public static final String CATEGORY_AMBIENT = "ambient";
        public static final String CATEGORY_MENU = "menu";
        public static final float DEFAULT_VOLUME = 1.0f;
        public static final float LOOP_CROSSFADE_MS = 50.0f;

        public static String getMidiTrack(int n) {
            if (n >= 0 && n < 5) {
                return AnimationAndSpriteLoader.AUDIO_MUSIC_MIDI + MIDI_TRACKS[n];
            }
            AnimationAndSpriteLoader.logError("Invalid MIDI track index: " + n);
            return null;
        }

        public static String getMusicWavTrack(int n) {
            if (n >= 0 && n < MUSIC_WAV_TRACKS.length) {
                return AnimationAndSpriteLoader.AUDIO_MUSIC_WAV + MUSIC_WAV_TRACKS[n];
            }
            AnimationAndSpriteLoader.logError("Invalid music WAV track index: " + n);
            return null;
        }

        public static String getMusicByName(String string) {
            for (String string2 : MUSIC_WAV_TRACKS) {
                if (!string2.equalsIgnoreCase(string)) continue;
                return AnimationAndSpriteLoader.AUDIO_MUSIC_WAV + string2;
            }
            AnimationAndSpriteLoader.logError("Music track not found: " + string);
            return null;
        }
    }

    public static class AdvancedBulletProperties {
        public static final String TYPE_BULLETS = "advanced_bullets";
        public static final String DIRECTORY = "Resources/industrial-zone/weapons/2/5 Bullets";
        public static final int TOTAL_BULLET_TYPES = 10;
        public static final int VARIANTS_PER_BULLET = 2;
        public static final int TOTAL_BULLET_SPRITES = 20;
        public static final String BULLET_BASIC = "Basic_Bullet";
        public static final String BULLET_TRACER_YELLOW = "Tracer_Yellow";
        public static final String BULLET_TRACER_CYAN = "Tracer_Cyan";
        public static final String BULLET_TRACER_ORANGE = "Tracer_Orange";
        public static final String BULLET_HEAVY = "Heavy_Round";
        public static final String BULLET_EXPLOSIVE = "Explosive";
        public static final String BULLET_ARMOR_PIERCING = "Armor_Piercing";
        public static final String BULLET_ELECTRIC = "Electric_Round";
        public static final String BULLET_SCATTER = "Scatter_Shot";
        public static final String BULLET_EXOTIC = "Exotic_Round";
        public static final String[] BULLET_FILES = new String[]{"1.png", "1_2.png", "2.png", "2_2.png", "3.png", "3_2.png", "4.png", "4_2.png", "5.png", "5_2.png", "6.png", "6_2.png", "7.png", "7_2.png", "8.png", "8_2.png", "9.png", "9_2.png", "10.png", "10_2.png"};
        public static final int BULLET_WIDTH = 8;
        public static final int BULLET_HEIGHT = 6;
        public static final String COLOR_BULLET_YELLOW = "#FFFF00";
        public static final String COLOR_BULLET_ORANGE = "#FF9900";
        public static final String COLOR_BULLET_CYAN = "#00FFFF";
        public static final String COLOR_BULLET_WHITE = "#FFFFFF";
        public static final String COLOR_BULLET_RED = "#FF0000";
        public static final float VELOCITY_BASIC = 8.0f;
        public static final float VELOCITY_HEAVY = 6.5f;
        public static final float VELOCITY_TRACER = 9.0f;
        public static final float VELOCITY_SCATTER = 7.0f;

        public static String getBulletFile(int n, int n2) {
            if (n < 1 || n > 10 || n2 < 1 || n2 > 2) {
                AnimationAndSpriteLoader.logError("Invalid bullet type: " + n + " or variant: " + n2);
                return null;
            }
            int n3 = (n - 1) * 2 + (n2 - 1);
            return AnimationAndSpriteLoader.WEAPON_2_BULLETS + BULLET_FILES[n3];
        }

        public static float getVelocity(String string) {
            if (string.contains("HEAVY")) {
                return 6.5f;
            }
            if (string.contains("TRACER")) {
                return 9.0f;
            }
            if (string.contains("SCATTER")) {
                return 7.0f;
            }
            return 8.0f;
        }
    }

    public static class ShootEffectsProperties {
        public static final String TYPE_SHOOT_EFFECTS = "shoot_effects_system";
        public static final String DIRECTORY = "Resources/industrial-zone/weapons/2/4 Shoot_effects";
        public static final int TOTAL_EFFECT_TYPES = 10;
        public static final int FRAMES_PER_EFFECT = 6;
        public static final int TIMING_MS = 60;
        public static final String EFFECT_TYPE_01 = "Basic_Flash";
        public static final String EFFECT_TYPE_02 = "Spark_Burst";
        public static final String EFFECT_TYPE_03 = "Cyan_Tracer";
        public static final String EFFECT_TYPE_04 = "Red_Tracer";
        public static final String EFFECT_TYPE_05 = "Yellow_Burst";
        public static final String EFFECT_TYPE_06 = "Blue_Spark";
        public static final String EFFECT_TYPE_07 = "White_Flash";
        public static final String EFFECT_TYPE_08 = "Orange_Burst";
        public static final String EFFECT_TYPE_09 = "Electric_Effect";
        public static final String EFFECT_TYPE_10 = "Heavy_Recoil_Flash";
        public static final String[] EFFECT_FILES = new String[]{"6_1.png", "6_2.png", "7_1.png", "7_2.png", "8_1.png", "8_2.png", "9_1.png", "9_2.png", "10_1.png", "10_2.png"};
        public static final int EFFECT_WIDTH = 40;
        public static final int EFFECT_HEIGHT = 40;
        public static final String COLOR_MUZZLE_YELLOW = "#FFDD00";
        public static final String COLOR_MUZZLE_ORANGE = "#FF9900";
        public static final String COLOR_MUZZLE_CYAN = "#00FFFF";
        public static final String COLOR_MUZZLE_RED = "#FF3333";

        public static String getEffectFile(int n) {
            if (n < 6 || n > 10) {
                AnimationAndSpriteLoader.logError("Invalid shoot effect type: " + n);
                return null;
            }
            int n2 = (n - 6) * 2;
            if (n2 >= EFFECT_FILES.length) {
                AnimationAndSpriteLoader.logError("Shoot effect file index out of bounds: " + n2);
                return null;
            }
            return AnimationAndSpriteLoader.WEAPON_2_EFFECTS + EFFECT_FILES[n2];
        }

        public static AnimationConfig getEffectAnimation(int n) {
            return new AnimationConfig(6, 60, "Muzzle flash effect " + n + " - plays on weapon fire");
        }
    }

    public static class HandPosesExtendedProperties {
        public static final String TYPE_HAND_POSES = "hand_poses_extended";
        public static final String DIRECTORY_BASE = "Resources/industrial-zone/weapons/2/3 Hands";
        public static final int HAND_POSES_PER_CHARACTER = 10;
        public static final int TOTAL_CHARACTERS = 3;
        public static final int TOTAL_HAND_POSES = 30;
        public static final String[] BIKER_HANDS = new String[]{"1.png", "2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "9.png", "10.png"};
        public static final String[] PUNK_HANDS = new String[]{"1.png", "2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "9.png", "10.png"};
        public static final String[] CYBORG_HANDS = new String[]{"1.png", "2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "9.png", "10.png"};
        public static final int HAND_WIDTH = 24;
        public static final int HAND_HEIGHT = 24;
        public static final String COLOR_SKIN_BIKER = "#FFB366";
        public static final String COLOR_SKIN_PUNK = "#FF9999";
        public static final String COLOR_SKIN_CYBORG = "#99CCFF";
        public static final int[] POSE_ANGLES = new int[]{0, 36, 72, 108, 144, 180, 216, 252, 288, 324};

        public static String getHandPose(String string, int n) {
            if (n < 0 || n >= 10) {
                AnimationAndSpriteLoader.logError("Invalid hand pose index: " + n);
                return null;
            }
            String string2 = "";
            String[] stringArray = null;
            if ("biker".equalsIgnoreCase(string)) {
                string2 = "1 Biker";
                stringArray = BIKER_HANDS;
            } else if ("punk".equalsIgnoreCase(string)) {
                string2 = "2 Punk";
                stringArray = PUNK_HANDS;
            } else if ("cyborg".equalsIgnoreCase(string)) {
                string2 = "3 Cyborg";
                stringArray = CYBORG_HANDS;
            } else {
                AnimationAndSpriteLoader.logError("Unknown character for hand poses: " + string);
                return null;
            }
            return AnimationAndSpriteLoader.WEAPON_2_HANDS + string2 + "/" + stringArray[n];
        }

        public static int getAngleForPose(int n) {
            if (n >= 0 && n < POSE_ANGLES.length) {
                return POSE_ANGLES[n];
            }
            return 0;
        }
    }

    public static class GunsExtendedProperties {
        public static final String TYPE_GUNS_EXTENDED = "guns_extended_system";
        public static final String DIRECTORY = "Resources/industrial-zone/weapons/2/2 Guns";
        public static final int TOTAL_GUN_TYPES = 10;
        public static final int VARIANTS_PER_GUN = 2;
        public static final int TOTAL_GUN_SPRITES = 20;
        public static final String GUN_TYPE_01 = "Gun_01";
        public static final String GUN_TYPE_02 = "Gun_02";
        public static final String GUN_TYPE_03 = "Gun_03";
        public static final String GUN_TYPE_04 = "Gun_04";
        public static final String GUN_TYPE_05 = "Gun_05";
        public static final String GUN_TYPE_06 = "Gun_06";
        public static final String GUN_TYPE_07 = "Gun_07";
        public static final String GUN_TYPE_08 = "Gun_08";
        public static final String GUN_TYPE_09 = "Gun_09";
        public static final String GUN_TYPE_10 = "Gun_10";
        public static final String[] GUN_FILES = new String[]{"1_1.png", "1_2.png", "2_1.png", "2_2.png", "3_1.png", "3_2.png", "4_1.png", "4_2.png", "5_1.png", "5_2.png", "6_1.png", "6_2.png", "7_1.png", "7_2.png", "8_1.png", "8_2.png", "9_1.png", "9_2.png", "10_1.png", "10_2.png"};
        public static final int GUN_WIDTH_DEFAULT = 32;
        public static final int GUN_HEIGHT_DEFAULT = 16;
        public static final String COLOR_PRIMARY = "#00CCFF";
        public static final String COLOR_SECONDARY = "#333333";

        public static String getGunFile(int n, int n2) {
            if (n < 1 || n > 10 || n2 < 1 || n2 > 2) {
                AnimationAndSpriteLoader.logError("Invalid gun type: " + n + " or variant: " + n2);
                return null;
            }
            int n3 = (n - 1) * 2 + (n2 - 1);
            return AnimationAndSpriteLoader.WEAPON_2_GUNS + GUN_FILES[n3];
        }

        public static String getGunType(int n) {
            switch (n) {
                case 1: {
                    return GUN_TYPE_01;
                }
                case 2: {
                    return GUN_TYPE_02;
                }
                case 3: {
                    return GUN_TYPE_03;
                }
                case 4: {
                    return GUN_TYPE_04;
                }
                case 5: {
                    return GUN_TYPE_05;
                }
                case 6: {
                    return GUN_TYPE_06;
                }
                case 7: {
                    return GUN_TYPE_07;
                }
                case 8: {
                    return GUN_TYPE_08;
                }
                case 9: {
                    return GUN_TYPE_09;
                }
                case 10: {
                    return GUN_TYPE_10;
                }
            }
            return null;
        }
    }

    public static class LevelWeaponPlacementSystem {
        private int[][] tileMap;
        private int mapWidth;
        private int mapHeight;
        private List<WeaponPlacement> placements;

        public LevelWeaponPlacementSystem(int[][] nArray, int n, int n2) {
            this.tileMap = nArray;
            this.mapWidth = n;
            this.mapHeight = n2;
            this.placements = new ArrayList<WeaponPlacement>();
        }

        public boolean placeWeapon(String string, int n, int n2, int n3, int n4) {
            WeaponPlacement weaponPlacement = new WeaponPlacement(string, n, n2, n3, n4);
            if (n3 < 0 || n3 >= this.mapWidth || n4 < 0 || n4 >= this.mapHeight) {
                weaponPlacement.validationReason = "Out of bounds";
                this.placements.add(weaponPlacement);
                return false;
            }
            int n5 = this.tileMap[n4][n3];
            weaponPlacement.placementTile = String.valueOf(n5);
            if (!this.isTileValidForWeapon(n5, string)) {
                weaponPlacement.validationReason = "Tile " + n5 + " not in valid set for " + string;
                this.placements.add(weaponPlacement);
                return false;
            }
            if (!this.hasAdjacentSupportTile(n3, n4)) {
                weaponPlacement.validationReason = "No support tile below";
                this.placements.add(weaponPlacement);
                return false;
            }
            weaponPlacement.isValid = true;
            weaponPlacement.validationReason = "Placed successfully on tile " + n5;
            this.placements.add(weaponPlacement);
            return true;
        }

        private boolean isTileValidForWeapon(int n, String string) {
            if (n == 52 || n == 58) {
                return false;
            }
            if (n == 55 || n >= 77 && n <= 80) {
                return false;
            }
            if (n < 3) {
                return false;
            }
            return n >= 3 && n <= 74;
        }

        private boolean hasAdjacentSupportTile(int n, int n2) {
            int n3;
            if (n2 + 1 < this.mapHeight && this.isWalkableTile(n3 = this.tileMap[n2 + 1][n])) {
                return true;
            }
            return n2 == this.mapHeight - 1;
        }

        private boolean isWalkableTile(int n) {
            return n >= 3 && n <= 74 && n != 52 && n != 58 && n != 55 && (n < 77 || n > 80);
        }

        public List<int[]> findValidSpawnPoints(String string, float f) {
            ArrayList<int[]> arrayList = new ArrayList<int[]>();
            for (int i = 0; i < this.mapHeight; ++i) {
                for (int j = 0; j < this.mapWidth; ++j) {
                    int n = this.tileMap[i][j];
                    if (!this.isTileValidForWeapon(n, string) || !this.hasAdjacentSupportTile(j, i) || !(Math.random() < (double)f)) continue;
                    arrayList.add(new int[]{j, i});
                }
            }
            return arrayList;
        }

        public List<WeaponPlacement> getAllPlacements() {
            return new ArrayList<WeaponPlacement>(this.placements);
        }

        public String getPlacementSummary() {
            int n = this.placements.size();
            int n2 = 0;
            for (WeaponPlacement weaponPlacement : this.placements) {
                if (!weaponPlacement.isValid) continue;
                ++n2;
            }
            return String.format("Weapon Placements: %d total, %d valid (%.1f%% success)", n, n2, Float.valueOf(100.0f * (float)n2 / (float)Math.max(1, n)));
        }

        public static class WeaponPlacement {
            public String gunFile;
            public int screenX;
            public int screenY;
            public int tileX;
            public int tileY;
            public String placementTile;
            public String validationReason;
            public boolean isValid;

            public WeaponPlacement(String string, int n, int n2, int n3, int n4) {
                this.gunFile = string;
                this.screenX = n;
                this.screenY = n2;
                this.tileX = n3;
                this.tileY = n4;
                this.isValid = false;
            }
        }
    }

    public static class PhysicsCollisionSystem {
        public void updateBulletPosition(BulletSpawner.BulletInstance bulletInstance, long l) {
            float f = (float)l / 1000.0f;
            bulletInstance.x += bulletInstance.velocityX * f;
            bulletInstance.y += bulletInstance.velocityY * f;
            bulletInstance.velocityY += 200.0f * f;
        }

        public CollisionResult checkCollisions(BulletSpawner.BulletInstance bulletInstance, List<Integer> list, List<Object> list2, List<Object> list3) {
            CollisionResult collisionResult = new CollisionResult();
            if (this.checkTileCollision(bulletInstance, list, collisionResult)) {
                collisionResult.hasCollided = true;
                collisionResult.targetType = "tile";
                return collisionResult;
            }
            if (this.checkEnemyCollision(bulletInstance, list2, collisionResult)) {
                collisionResult.hasCollided = true;
                collisionResult.targetType = "enemy";
                return collisionResult;
            }
            if (this.checkObjectCollision(bulletInstance, list3, collisionResult)) {
                collisionResult.hasCollided = true;
                collisionResult.targetType = "object";
                return collisionResult;
            }
            return collisionResult;
        }

        private boolean checkTileCollision(BulletSpawner.BulletInstance bulletInstance, List<Integer> list, CollisionResult collisionResult) {
            int n = (int)(bulletInstance.x - 4.0f);
            int n2 = (int)(bulletInstance.x + 4.0f);
            int n3 = (int)(bulletInstance.y - 4.0f);
            int n4 = (int)(bulletInstance.y + 4.0f);
            for (int i = n / 32; i <= n2 / 32; ++i) {
                for (int j = n3 / 32; j <= n4 / 32; ++j) {
                    int n5 = j * 20 + i;
                    if (!list.contains(n5)) continue;
                    collisionResult.impactX = bulletInstance.x;
                    collisionResult.impactY = bulletInstance.y;
                    collisionResult.damage = this.calculateDamage(bulletInstance);
                    return true;
                }
            }
            return false;
        }

        private boolean checkEnemyCollision(BulletSpawner.BulletInstance bulletInstance, List<Object> list, CollisionResult collisionResult) {
            for (Object object : list) {
                try {
                    float f;
                    float f2;
                    float f3;
                    float f4 = ((Float)object.getClass().getMethod("getX", new Class[0]).invoke(object, new Object[0])).floatValue();
                    float f5 = bulletInstance.x - f4;
                    float f6 = f5 * f5 + (f3 = bulletInstance.y - (f2 = ((Float)object.getClass().getMethod("getY", new Class[0]).invoke(object, new Object[0])).floatValue())) * f3;
                    if (!(f6 <= (f = ((Float)object.getClass().getMethod("getRadius", new Class[0]).invoke(object, new Object[0])).floatValue()) * f)) continue;
                    collisionResult.target = object;
                    collisionResult.impactX = bulletInstance.x;
                    collisionResult.impactY = bulletInstance.y;
                    collisionResult.damage = this.calculateDamage(bulletInstance);
                    return true;
                }
                catch (Exception exception) {
                }
            }
            return false;
        }

        private boolean checkObjectCollision(BulletSpawner.BulletInstance bulletInstance, List<Object> list, CollisionResult collisionResult) {
            for (Object object : list) {
                try {
                    float f = ((Float)object.getClass().getMethod("getX", new Class[0]).invoke(object, new Object[0])).floatValue();
                    float f2 = ((Float)object.getClass().getMethod("getY", new Class[0]).invoke(object, new Object[0])).floatValue();
                    float f3 = ((Float)object.getClass().getMethod("getWidth", new Class[0]).invoke(object, new Object[0])).floatValue();
                    float f4 = ((Float)object.getClass().getMethod("getHeight", new Class[0]).invoke(object, new Object[0])).floatValue();
                    if (!(bulletInstance.x >= f) || !(bulletInstance.x <= f + f3) || !(bulletInstance.y >= f2) || !(bulletInstance.y <= f2 + f4)) continue;
                    collisionResult.target = object;
                    collisionResult.impactX = bulletInstance.x;
                    collisionResult.impactY = bulletInstance.y;
                    collisionResult.damage = this.calculateDamage(bulletInstance);
                    return true;
                }
                catch (Exception exception) {
                }
            }
            return false;
        }

        private int calculateDamage(BulletSpawner.BulletInstance bulletInstance) {
            int n = 10;
            if (bulletInstance.bulletType.contains("E")) {
                n = 15;
            } else if (bulletInstance.bulletType.contains("D")) {
                n = 18;
            } else if (bulletInstance.bulletType.contains("A")) {
                n = 10;
            }
            float f = (float)Math.sqrt(bulletInstance.velocityX * bulletInstance.velocityX + bulletInstance.velocityY * bulletInstance.velocityY) * (float)(System.currentTimeMillis() - bulletInstance.spawnTime) / 1000.0f;
            if (f > 500.0f) {
                float f2 = Math.max(0.1f, 1.0f - (f - 500.0f) / 1000.0f);
                n = (int)((float)n * f2);
            }
            return Math.max(1, n);
        }

        public boolean isBulletOutOfBounds(BulletSpawner.BulletInstance bulletInstance, int n, int n2) {
            return bulletInstance.x < -50.0f || bulletInstance.x > (float)(n + 50) || bulletInstance.y < -50.0f || bulletInstance.y > (float)(n2 + 50);
        }

        public static class CollisionResult {
            public boolean hasCollided = false;
            public String targetType = "none";
            public Object target = null;
            public int damage = 0;
            public float impactX;
            public float impactY;
        }
    }

    public static class WeaponRenderingSystem {
        private Map<String, BufferedImage> gunImageCache = new HashMap<String, BufferedImage>();
        private Map<String, BufferedImage> bulletImageCache = new HashMap<String, BufferedImage>();

        public BufferedImage renderArmedCharacter(String string, BufferedImage bufferedImage, String string2, int n, int n2) {
            try {
                String string3;
                BufferedImage bufferedImage2;
                BufferedImage bufferedImage3 = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), 2);
                Graphics2D graphics2D = bufferedImage3.createGraphics();
                graphics2D.drawImage((Image)bufferedImage, 0, 0, null);
                int[] nArray = this.getGunOffset(string, n2);
                BufferedImage bufferedImage4 = this.loadGunImage(string2);
                if (bufferedImage4 != null) {
                    graphics2D.drawImage((Image)bufferedImage4, nArray[0], nArray[1], null);
                }
                if ((bufferedImage2 = this.loadHandPoseImage(string3 = HandGripSelector.selectGripPose(string, n))) != null) {
                    int[] nArray2 = this.getHandOffset(string, n2);
                    graphics2D.drawImage((Image)bufferedImage2, nArray2[0], nArray2[1], null);
                }
                this.applyAnimationStateEffects(graphics2D, n2, n);
                graphics2D.dispose();
                return bufferedImage3;
            }
            catch (Exception exception) {
                AnimationAndSpriteLoader.logError("Failed to render armed character: " + string);
                AnimationAndSpriteLoader.logError("Gun: " + string2 + ", State: " + n2);
                return bufferedImage;
            }
        }

        private int[] getGunOffset(String string, int n) {
            switch (n) {
                case 0: {
                    return new int[]{40, 30};
                }
                case 1: {
                    return new int[]{38, 25};
                }
                case 3: {
                    return new int[]{50, 20};
                }
                case 4: {
                    return new int[]{35, 40};
                }
                case 5: {
                    return new int[]{45, 28};
                }
            }
            return new int[]{40, 30};
        }

        private int[] getHandOffset(String string, int n) {
            int[] nArray = this.getGunOffset(string, n);
            return new int[]{nArray[0] + 5, nArray[1] + 8};
        }

        private void applyAnimationStateEffects(Graphics2D graphics2D, int n, int n2) {
            switch (n) {
                case 3: {
                    graphics2D.setColor(new Color(255, 150, 0, 80));
                    graphics2D.fillOval(55, 15, 15, 15);
                    break;
                }
                case 6: {
                    graphics2D.setColor(new Color(255, 0, 0, 60));
                    graphics2D.fillRect(0, 0, 64, 64);
                }
            }
        }

        private BufferedImage loadGunImage(String string) {
            if (this.gunImageCache.containsKey(string)) {
                return this.gunImageCache.get(string);
            }
            try {
                String string2 = AnimationAndSpriteLoader.WEAPONS_BASE + string;
                BufferedImage bufferedImage = ImageIO.read(new File(string2));
                this.gunImageCache.put(string, bufferedImage);
                return bufferedImage;
            }
            catch (Exception exception) {
                AnimationAndSpriteLoader.logError("Failed to load gun image: " + string);
                return null;
            }
        }

        private BufferedImage loadHandPoseImage(String string) {
            if (string == null) {
                return null;
            }
            if (this.gunImageCache.containsKey(string)) {
                return this.gunImageCache.get(string);
            }
            try {
                BufferedImage bufferedImage = ImageIO.read(new File(string));
                this.gunImageCache.put(string, bufferedImage);
                return bufferedImage;
            }
            catch (Exception exception) {
                AnimationAndSpriteLoader.logError("Failed to load hand pose: " + string);
                return null;
            }
        }

        public BufferedImage renderBullet(BulletSpawner.BulletInstance bulletInstance, String string) {
            try {
                BufferedImage bufferedImage = this.loadBulletImage(string);
                if (bufferedImage == null) {
                    return null;
                }
                double d = Math.toRadians(bulletInstance.directionAngle);
                return this.rotateBullet(bufferedImage, d);
            }
            catch (Exception exception) {
                AnimationAndSpriteLoader.logError("Failed to render bullet: " + string);
                return null;
            }
        }

        private BufferedImage loadBulletImage(String string) {
            if (this.bulletImageCache.containsKey(string)) {
                return this.bulletImageCache.get(string);
            }
            try {
                String string2 = "Resources/industrial-zone/projectiles/" + string;
                BufferedImage bufferedImage = ImageIO.read(new File(string2));
                this.bulletImageCache.put(string, bufferedImage);
                return bufferedImage;
            }
            catch (Exception exception) {
                return null;
            }
        }

        private BufferedImage rotateBullet(BufferedImage bufferedImage, double d) {
            int n = bufferedImage.getWidth();
            int n2 = bufferedImage.getHeight();
            BufferedImage bufferedImage2 = new BufferedImage(n, n2, 2);
            Graphics2D graphics2D = bufferedImage2.createGraphics();
            graphics2D.translate(n / 2, n2 / 2);
            graphics2D.rotate(d);
            graphics2D.translate(-n / 2, -n2 / 2);
            graphics2D.drawImage((Image)bufferedImage, 0, 0, null);
            graphics2D.dispose();
            return bufferedImage2;
        }

        public void preloadCharacterAssets(String string, List<String> list) {
            String[] stringArray = HandGripSelector.getAllGripAngles(string);
            for (String string2 : stringArray) {
                this.loadHandPoseImage(string2);
            }
            for (String string3 : list) {
                this.loadGunImage(string3);
            }
        }
    }

    public static class InteractiveObjectAssignmentMatrix {
        public static final String REGISTRY_TYPE = "object_assignment_matrix";
        public static final String ASSIGNMENT_RULE_1 = "MONEY: Only on SAFE tilesets (any horizontal brick, panel middles). 35% spawn rate.";
        public static final String ASSIGNMENT_RULE_2 = "CARD: Rare (10%) on elevated SAFE tilesets only. Acts as power-up reward.";
        public static final String ASSIGNMENT_RULE_3 = "DECO_BLUE: On INDUSTRIAL tilesets (panels, doors). 25% spawn rate for tech aesthetic.";
        public static final String ASSIGNMENT_RULE_4 = "DECO_RED: On HAZARD/PANELS near hazards. 40% spawn rate above slopes/ramps.";
        public static final String ASSIGNMENT_RULE_5 = "Never place collectibles on slopes, small units, or edge borders.";
        public static final String ASSIGNMENT_RULE_6 = "Never mix safe and hazard aesthetics - red screens don't go in peaceful zones.";
        public static final String VALIDATION_METHOD = "Run level validator: Check each placed object against its VALID_PARENT_TILESETS list.";
    }

    public static class AnimatedObjectPlacementRules {
        public static final String REGISTRY_TYPE = "object_placement";

        public static class DecoScreenRedPlacement {
            public static final String OBJECT_TYPE = "deco_screen_red";
            public static final String OBJECT_FILE = "Anim_Deco_Screen2_4Frames1Row_RedBlueMonitorFlicker_WallPanelAltDeco_Loop150ms.png";
            public static final String ANIMATION_DESCRIPTION = "Red alarm monitor flicker - plays 4 frames at 150ms with intense red glow";
            public static final String[] HAZARD_ZONE_TILES = new String[]{"PanelStructures.PANEL_FILE_52", "PanelStructures.PANEL_FILE_58", "Above_PANEL_FILE_52_or_58"};
            public static final String[] HAZARD_ADJACENT_TILES = new String[]{"PanelStructures.PANEL_FILE_51", "PanelStructures.PANEL_FILE_59", "PanelStructures.PANEL_FILE_62", "EdgeBorders.EDGE_FILE_79", "EdgeBorders.EDGE_FILE_80"};
            public static final String CLUSTER_2_SCREENS = "Two red screens: Both above slope entry. Spacing: 1 tile apart horizontal. Creates twin-alarm effect. Placement: Left screen (PANEL_FILE_52 area), right screen (PANEL_FILE_58 area). Purpose: Double alert = serious danger.";
            public static final String CLUSTER_4_SCREENS = "Four red screens: 2\u00d72 grid above hazard zone. Spacing: Checker pattern, 1-2 tiles. Creates intense alarm field. Purpose: EXTREME DANGER - progression gate. Player sees this and knows: 'I will die if unprepared.'";
            public static final String CLUSTER_RULE = "Minimum 2 screens per hazard zone. Never use isolated red screens. Exceptions: Very small hazard areas (1\u00d71 single slope tile) = 1 red screen acceptable.";
            public static final String[] FORBIDDEN_TILES = new String[]{"HorizontalStripeBrickPanels.ANY", "DoorGateElements.DOOR_FILE_0", "DoorGateElements.DOOR_FILE_2", "Player_Start_Zone", "Collectible_Money_Zones", "Safe_Platform_Clusters"};
            public static final String VISUAL_HIERARCHY = "Red screens are HIGHER PRIORITY than blue. If red is visible, blue is ignored. Red flicker is FASTER and BRIGHTER than blue. Red glow expands more aggressively. Audio: Red screens emit HIGH-PITCH warning (1000Hz + 1500Hz harmonics) \u00d7 urgent tempo.";
            public static final String SPATIAL_RULES = "Rule 1: Red screen NEVER below player. Always at player eye-level or above. Rule 2: Red screens visible from safe zone (5+ tile sightline). Rule 3: Red zones create visual 'no-go' atmosphere. Rule 4: Path design: Blue areas \u2192 Yellow transition \u2192 Red danger zones (aesthetic gradient).";
            public static final String PROGRESSION_CONTEXT = "Level 1: No red screens (safe intro). Level 2: Introduce 1-2 red zones (2 clusters of 2 screens each). Challenge: Heavy red screen usage (3+ clusters of 4 screens = maximum alert state).";
            public static final String PLACEMENT_STRATEGY = "Cluster 2-4 red screens in dangerous zones to warn player.";
            public static final float SPAWN_FREQUENCY = 0.4f;
            public static final String VISUAL_NOTE = "Red flicker creates alarm/alert atmosphere";
            public static final String TECHNICAL_NOTES = "Animation: 4 frames at 150ms = 600ms cycle, BUT frame 1-2-1-2 pattern (emphasis on red moments). Glow: Red glow pulsates more aggressively than blue - expands \u00b15px per frame. Audio: 1000Hz + 1500Hz sine wave \u00d7 3 harmonics, 200ms ON/100ms OFF pulse. Offset: Mount at +15px (slightly lower than blue to feel 'threatening'). Screen Flicker: Brightness variance 80%-100% (more extreme variation = more alarming). Rotation: Slight 10-degree tilt variance (\u00d7-5\u00b0, \u00d70\u00b0, \u00d7+5\u00b0) = unstable feeling. No interaction: Purely visual deco - cannot be disabled by player.";
        }

        public static class DecoScreenBluePlacement {
            public static final String OBJECT_TYPE = "deco_screen_blue";
            public static final String OBJECT_FILE = "Anim_Deco_Screen1_4Frames1Row_BlueMonitorFlicker_WallPanelTechDeco_Loop150ms.png";
            public static final String ANIMATION_DESCRIPTION = "Blue monitor flicker effect - plays 4 frames at 150ms with active glow";
            public static final String[] PRIMARY_MOUNT_TILES = new String[]{"PanelStructures.PANEL_FILE_61", "PanelStructures.PANEL_FILE_64", "PanelStructures.PANEL_FILE_73"};
            public static final String[] SECONDARY_MOUNT_TILES = new String[]{"PanelStructures.PANEL_FILE_56", "PanelStructures.PANEL_FILE_57", "DoorGateElements.DOOR_FILE_0", "DoorGateElements.DOOR_FILE_2"};
            public static final String PAIR_PATTERN = "Two screens mounted vertically on same panel (PANEL_FILE_64 top + PANEL_FILE_64 below). Spacing: Centered, 2 tiles apart. Purpose: Control room duality. Visual impact: Symmetrical.";
            public static final String TRIPLE_PATTERN = "Three screens in triangle: Primary at center (PANEL_FILE_61 or 64), secondaries on left/right (PANEL_FILE_56/57). Spacing: 2-3 tiles. Purpose: Surveillance hub. Visual impact: High density, command center feel.";
            public static final String ISOLATED_RULE = "Single screens should not be used. Minimum 2 screens per installation.";
            public static final String[] FORBIDDEN_TILES = new String[]{"HorizontalStripeBrickPanels.ANY", "EdgeBorders.ANY", "PanelStructures.PANEL_FILE_52", "PanelStructures.PANEL_FILE_58", "BrickSmallUnits.ANY", "Player_Spawn_Zone_5tile_radius"};
            public static final String ZONE_CONTEXT = "Blue screens ONLY in control rooms, tech facilities, and secured zones. They indicate active monitoring and organizational control. Contrast with red screens by placing blue in 'safe' tech areas.";
            public static final String NARRATIVE_CONTEXT = "Blue screens represent: Corporate surveillance, automated systems, secure access. Player finding blue screens = finding control center = strategic location. Often placed above collectibles = guarded rewards.";
            public static final String GAME_HINT_CONTEXT = "Blue screens are visual feedback: 'You are in a controlled/secured area now.'";
            public static final String PLACEMENT_STRATEGY = "Mount on walls for tech atmosphere. Use in pairs or triplet clusters.";
            public static final float SPAWN_FREQUENCY = 0.25f;
            public static final String VISUAL_NOTE = "Flickers creating sense of active surveillance/control";
            public static final String TECHNICAL_NOTES = "Animation: 4 frames at 150ms = 600ms total cycle (slower than collectibles - emphasizes stasis). Glow: Blue glow expands/contracts with frame - draws attention subtly. Audio: Low soft hum (60Hz sine wave \u00d7 2 harmonics) - background ambient. Offset: Mount at +20px vertical to simulate mounted-on-panel placement. Rotation: Slight 5-degree tilt variation between screens in cluster (\u00d7-2\u00b0, \u00d70\u00b0, \u00d7+2\u00b0) = more organic feel. No interaction: Purely visual deco - player cannot activate/disable.";
        }

        public static class CollectibleCardPlacement {
            public static final String OBJECT_TYPE = "collectible_card";
            public static final String OBJECT_FILE = "Anim_Collectible_Card_6Frames1Row_WhiteBlueSpinningFloat_PickupItem_Loop80ms.png";
            public static final String ANIMATION_DESCRIPTION = "Rotating holographic card - plays 6 frames at 80ms with active glow";
            public static final String[] TIER_1_ALCOVE_TILES = new String[]{"PanelStructures.PANEL_FILE_9", "PanelStructures.PANEL_FILE_18", "PanelStructures.PANEL_FILE_21", "PanelStructures.PANEL_FILE_27"};
            public static final String[] TIER_2_FLOATING_ANCHORS = new String[]{"HorizontalStripeBrickPanels_Elevated", "PanelStructures.PANEL_FILE_15", "PanelStructures.PANEL_FILE_24"};
            public static final String SHIELD_CARD_CONTEXT = "Shield Card placement: TIER_1_ALCOVE. Reason: Protection is vital, hide to reward exploration.";
            public static final String DAMAGE_CARD_CONTEXT = "DoubleDamage Card placement: TIER_1_ALCOVE. Reason: Power spike should feel earned.";
            public static final String SPEED_CARD_CONTEXT = "SpeedBoost Card placement: TIER_2_FLOATING. Reason: Speed cards feel 'light', place in air.";
            public static final String HEALTH_CARD_CONTEXT = "HealthRestore Card placement: TIER_2_FLOATING. Reason: Recovery items less urgent, can be floating.";
            public static final String COMBO_CARD_CONTEXT = "ComboMultiplier Card placement: TIER_1_ALCOVE. Reason: Skill-based card, hide for skilled players.";
            public static final String[] FORBIDDEN_TILES = new String[]{"PanelStructures.PANEL_FILE_52", "PanelStructures.PANEL_FILE_58", "EdgeBorders.ANY", "HorizontalStripeBrickPanels_BaseLevel", "BrickSmallUnits.ANY"};
            public static final String PROXIMITY_CONTEXT = "Card must be 5+ tiles away from: Doors, Money spawns, Transporter zones. Card should be 2-3 tiles away from: Hazard areas (to entice risk). Card visible if player is within 10 tiles (sightline matters).";
            public static final String LEVEL_1_CONTEXT = "Level 1: 1-2 cards only. Placement: Safe hidden alcoves. Purpose: Teach discovery mechanic.";
            public static final String LEVEL_2_CONTEXT = "Level 2: 3-4 cards. Placement: Mix of alcoves and floating. Purpose: Reward skill exploration.";
            public static final String CHALLENGE_CONTEXT = "Challenge Rooms: 1 card per room. Placement: Highest difficulty platform. Purpose: Gate progression on mastery.";
            public static final String PLACEMENT_STRATEGY = "Hide in difficult-to-reach areas. Use for rewards and progression gates.";
            public static final float SPAWN_FREQUENCY = 0.1f;
            public static final String DIFFICULTY_CONTEXT = "Typically only on Level 2 (harder) or challenge areas";
            public static final String TECHNICAL_NOTES = "Interaction radius: 100px (larger than money - forces player to commit). Animation: 6 frames at 80ms = 480ms loop with active glow effect. Audio: Ascending chime melody (Do-Re-Mi-Fa-Sol 5 notes) with 3x reverb. Visual: Glow expands/contracts with frame - signals importance. Behavior: Slight vertical bobbing (\u00b115px, 2 second cycle).";
        }

        public static class CollectibleMoneyPlacement {
            public static final String OBJECT_TYPE = "collectible_money";
            public static final String OBJECT_FILE = "Anim_Collectible_Money_6Frames1Row_GreenBanknotesSpinFlip_CurrencyPickup_Loop80ms.png";
            public static final String ANIMATION_DESCRIPTION = "Spinning banknote with flip rotation - plays 6 frames at 80ms intervals";
            public static final String[] TIER_1_PRIMARY_TILES = new String[]{"HorizontalStripeBrickPanels_Any_Color", "PanelStructures.PANEL_FILE_3", "PanelStructures.PANEL_FILE_6", "PanelStructures.PANEL_FILE_12", "PanelStructures.PANEL_FILE_15"};
            public static final String[] TIER_2_SECONDARY_TILES = new String[]{"PanelStructures.PANEL_FILE_9", "PanelStructures.PANEL_FILE_21", "PanelStructures.PANEL_FILE_27", "DoorGateElements.DOOR_FILE_0", "DoorGateElements.DOOR_FILE_2"};
            public static final String[] TIER_3_INDUSTRIAL_TILES = new String[]{"PanelStructures.PANEL_FILE_56", "PanelStructures.PANEL_FILE_61", "PanelStructures.PANEL_FILE_64", "PanelStructures.PANEL_FILE_70"};
            public static final String[] FORBIDDEN_TILES = new String[]{"PanelStructures.PANEL_FILE_52", "PanelStructures.PANEL_FILE_58", "EdgeBorders.EDGE_FILE_55", "EdgeBorders.EDGE_FILE_77", "EdgeBorders.EDGE_FILE_78", "EdgeBorders.EDGE_FILE_79", "EdgeBorders.EDGE_FILE_80", "BrickSmallUnits.ANY"};
            public static final String PLACEMENT_CONTEXT = "Money appears as reward for navigation. Spacing of 3-5 tiles prevents clustering. Vary height: 40% same level (easy), 35% 1 level up (moderate), 25% 2+ levels (challenge). Always place within player's line of sight when approaching tile. Clustering 2-3 money on elevated platforms creates 'treasure spots'.";
            public static final String LEVEL_DISTRIBUTION = "Level 1: 35% placement - Easy discovery. Level 2: 40% placement - More challenging. Challenge Areas: 50% placement - Reward for skill.";
            public static final String PLACEMENT_STRATEGY = "Place on prominent SAFE platforms. Space 3-5 tiles apart to encourage exploration.";
            public static final float SPAWN_FREQUENCY = 0.35f;
            public static final String TECHNICAL_NOTES = "Collision detection: 32\u00d732 origin point at tile center. Animation: 6 frames at 80ms = 480ms total loop. Interaction: Auto-pickup within 50px radius. Audio: Coin-chime sound at +2 octaves pitched randomly \u00b150Hz per pickup.";
        }
    }

    public static class UniversalWeaponPickup {
        public static final String SYSTEM_TYPE = "universal_weapon_pickup";

        public static void pickupWeapon(String string, String string2) {
            if (!CharacterWeaponState.isValidCharacter(string)) {
                System.out.println("ERROR: Invalid character ID: " + string);
                return;
            }
            CharacterWeaponState.EquippedWeapons.equipWeapon(string, string2);
            System.out.println(string + " equipped: " + string2);
        }

        public static void dropWeapon(String string) {
            CharacterWeaponState.EquippedWeapons.dropWeapon(string);
            System.out.println(string + " dropped weapon");
        }

        public static String[] getAvailableGunsOnLevel() {
            return GunProperties.ALL_GUN_SPRITES;
        }
    }

    public static class WeaponFireSystem {
        public static final String SYSTEM_TYPE = "weapon_fire_system";

        public static FireSequence executeFire(String string, float f, float f2, int n, CharacterAnimationStateMachine.CharacterAnimationState characterAnimationState) {
            String string2 = CharacterWeaponState.EquippedWeapons.getEquippedWeapon(string);
            String string3 = HandGripSelector.selectGripPose(string, n);
            BulletSpawner.BulletInstance bulletInstance = BulletSpawner.fireWeapon(string2, f, f2, n);
            characterAnimationState.transitionTo(3);
            characterAnimationState.fire();
            FireSequence fireSequence = new FireSequence(string, f, f2, n);
            fireSequence.gunFile = string2;
            fireSequence.spawnedBullet = bulletInstance;
            fireSequence.handGripUsed = string3;
            return fireSequence;
        }

        public static void logFireSequence(FireSequence fireSequence) {
            System.out.println("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
            System.out.println("WEAPON FIRE SEQUENCE EXECUTED");
            System.out.println("Character: " + fireSequence.characterId);
            System.out.println("Gun: " + fireSequence.gunFile);
            System.out.println("Aim Angle: " + fireSequence.aimAngle + "/10");
            System.out.println("Hand Grip: " + fireSequence.handGripUsed);
            System.out.println("Bullet Type: " + fireSequence.spawnedBullet.bulletType);
            System.out.println("Spawn Pos: (" + fireSequence.firingX + ", " + fireSequence.firingY + ")");
            System.out.println("Velocity: (" + fireSequence.spawnedBullet.velocityX + ", " + fireSequence.spawnedBullet.velocityY + ")");
            System.out.println("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        }

        public static class FireSequence {
            public String characterId;
            public String gunFile;
            public int aimAngle;
            public float firingX;
            public float firingY;
            public BulletSpawner.BulletInstance spawnedBullet;
            public String handGripUsed;
            public long fireTime;

            public FireSequence(String string, float f, float f2, int n) {
                this.characterId = string;
                this.firingX = f;
                this.firingY = f2;
                this.aimAngle = n;
                this.fireTime = System.currentTimeMillis();
            }
        }
    }

    public static class CharacterAnimationStateMachine {
        public static final String SYSTEM_TYPE = "character_animation_state";
        public static final int STATE_IDLE = 0;
        public static final int STATE_RUN = 1;
        public static final int STATE_JUMP = 2;
        public static final int STATE_FIRE = 3;
        public static final int STATE_MELEE = 4;
        public static final int STATE_RELOAD = 5;
        public static final int STATE_HIT = 6;
        public static final int STATE_DIE = 7;
        public static final String[] STATE_NAMES = new String[]{"IDLE", "RUN", "JUMP", "FIRE", "MELEE", "RELOAD", "HIT", "DIE"};

        public static CharacterAnimationState createStateFor(String string) {
            return new CharacterAnimationState(string);
        }

        public static class CharacterAnimationState {
            public int currentState = 0;
            public String characterId;
            public boolean isArmed;
            public String equippedGun;
            public int aimAngle = 0;
            public boolean isFiring = false;
            public long lastFireTime = 0L;
            public int fireRateMs = 200;

            public CharacterAnimationState(String string) {
                this.characterId = string;
            }

            public void transitionTo(int n) {
                if (n >= 0 && n < STATE_NAMES.length) {
                    this.currentState = n;
                }
            }

            public String getCurrentStateName() {
                return STATE_NAMES[this.currentState];
            }

            public boolean canFire() {
                long l = System.currentTimeMillis();
                return l - this.lastFireTime >= (long)this.fireRateMs;
            }

            public void fire() {
                if (this.canFire() && this.isArmed) {
                    this.lastFireTime = System.currentTimeMillis();
                    this.isFiring = true;
                }
            }
        }
    }

    public static class BulletSpawner {
        public static final String SYSTEM_TYPE = "bullet_spawner";

        public static String selectBulletForGun(String string) {
            if (string.contains("Pistol")) {
                return "01_Weapon_Bullet_TypeA_Single_StaticSprite.png";
            }
            if (string.contains("Compact")) {
                return "02_Weapon_Bullet_TypeB_Single_StaticSprite.png";
            }
            if (string.contains("Detail")) {
                return "04_Weapon_Bullet_TypeD_VariantA_StaticSprite.png";
            }
            if (string.contains("Rifle")) {
                return "06_Weapon_Bullet_TypeE_VariantA_StaticSprite.png";
            }
            if (string.contains("Special")) {
                return "09_Weapon_Bullet_TypeG_VariantA_StaticSprite.png";
            }
            return "01_Weapon_Bullet_TypeA_Single_StaticSprite.png";
        }

        public static BulletInstance fireWeapon(String string, float f, float f2, int n) {
            String string2 = BulletSpawner.selectBulletForGun(string);
            float f3 = (float)n * 36.0f;
            return new BulletInstance(string2, f, f2, f3);
        }

        public static class BulletInstance {
            public String bulletType;
            public float x;
            public float y;
            public float velocityX;
            public float velocityY;
            public float directionAngle;
            public long spawnTime;

            public BulletInstance(String string, float f, float f2, float f3) {
                this.bulletType = string;
                this.x = f;
                this.y = f2;
                this.directionAngle = f3;
                this.spawnTime = System.currentTimeMillis();
                float f4 = 5.0f;
                this.velocityX = f4 * (float)Math.cos(Math.toRadians(f3));
                this.velocityY = f4 * (float)Math.sin(Math.toRadians(f3));
            }
        }
    }

    public static class HandGripSelector {
        public static final String SYSTEM_TYPE = "hand_grip_selector";

        public static String selectGripPose(String string, int n) {
            int n2 = Math.max(0, Math.min(9, n));
            switch (string.toLowerCase()) {
                case "biker": {
                    return WeaponHandPoses.BikerHands.ALL_GRIP_POSES[n2];
                }
                case "punk": {
                    return WeaponHandPoses.PunkHands.ALL_GRIP_POSES[n2];
                }
                case "cyborg": {
                    return WeaponHandPoses.CyborgHands.ALL_GRIP_POSES[n2];
                }
            }
            return WeaponHandPoses.BikerHands.ALL_GRIP_POSES[0];
        }

        public static String getHandPoseDirectory(String string) {
            switch (string.toLowerCase()) {
                case "biker": {
                    return "Resources/industrial-zone/weapons/1/3 Hands/1 Biker";
                }
                case "punk": {
                    return "Resources/industrial-zone/weapons/1/3 Hands/2 Punk";
                }
                case "cyborg": {
                    return "Resources/industrial-zone/weapons/1/3 Hands/3 Cyborg";
                }
            }
            return "Resources/industrial-zone/weapons/1/3 Hands/1 Biker";
        }

        public static String[] getAllGripAngles(String string) {
            switch (string.toLowerCase()) {
                case "biker": {
                    return WeaponHandPoses.BikerHands.ALL_GRIP_POSES;
                }
                case "punk": {
                    return WeaponHandPoses.PunkHands.ALL_GRIP_POSES;
                }
                case "cyborg": {
                    return WeaponHandPoses.CyborgHands.ALL_GRIP_POSES;
                }
            }
            return WeaponHandPoses.BikerHands.ALL_GRIP_POSES;
        }
    }

    public static class CharacterWeaponState {
        public static final String SYSTEM_TYPE = "character_weapon_state";
        public static final String CHARACTER_BIKER = "biker";
        public static final String CHARACTER_PUNK = "punk";
        public static final String CHARACTER_CYBORG = "cyborg";
        public static final String[] AVAILABLE_CHARACTERS = new String[]{"biker", "punk", "cyborg"};

        public static boolean isValidCharacter(String string) {
            for (String string2 : AVAILABLE_CHARACTERS) {
                if (!string2.equals(string)) continue;
                return true;
            }
            return false;
        }

        public static class EquippedWeapons {
            private static Map<String, String> weaponMap = new HashMap<String, String>();

            public static void equipWeapon(String string, String string2) {
                if (CharacterWeaponState.isValidCharacter(string)) {
                    weaponMap.put(string, string2);
                }
            }

            public static String getEquippedWeapon(String string) {
                String string2 = weaponMap.getOrDefault(string, null);
                return string2 != null ? string2 : "01_Weapon_Gun_Pistol_TypeA_VariantDark_StaticSprite.png";
            }

            public static boolean isArmed(String string) {
                return weaponMap.containsKey(string) && weaponMap.get(string) != null;
            }

            public static void dropWeapon(String string) {
                weaponMap.remove(string);
            }
        }
    }

    public static class SpriteChainSystems {
        public static final String REGISTRY_TYPE = "sprite_chain";

        public static class InteractiveObjectChain {
            public static final String CHAIN_NAME = "interactive_object";
            public static final String STEP_1_COLLISION = "Character collides with collectible";
            public static final String STEP_1_EXAMPLE = "   Example: CollectibleMoney at position (100, 50)";
            public static final String STEP_2_DETECT = "Identify object type from metadata";
            public static final String STEP_2_EXAMPLE = "   Type = 'collectible_money' from InteractiveObjectProperties.CollectibleMoney.OBJECT_TYPE";
            public static final String STEP_3_ANIMATION = "Load animation sequence for effect";
            public static final String STEP_3_EXAMPLE = "   File: InteractiveObjectProperties.CollectibleMoney.FILE";
            public static final String STEP_3_FRAMES = "   6 frames, 80ms each = fast spinning pickup effect";
            public static final String STEP_4_EFFECT = "Play effect immediately or on contact";
            public static final String STEP_4_EXAMPLE = "   Money gives immediate bonus score";
            public static final String STEP_4_EXAMPLE_2 = "   Card might trigger special power-up sequence";
            public static final String STEP_5_VFX = "Play associated VFX if impact-based";
            public static final String STEP_5_EXAMPLE = "   CollectibleCard might trigger special glow effect";
            public static final String STEP_6_AUDIO = "Play pickup sound effect";
            public static final String STEP_6_EXAMPLE = "   Different sounds for money vs cards (coming in audio batch)";
        }

        public static class CharacterVisualChain {
            public static final String CHAIN_NAME = "character_visual";
            public static final String START_POINT = "PlayerCharacterProperties.BIKER/CYBORG/PUNK";
            public static final String STEP_1_BASE = "PlayerCharacterAnimations - Load character movement animations";
            public static final String STEP_1_DETAIL = "   Example: BikerAnimations.IDLE";
            public static final String STEP_2_CURRENT_STATE = "Select animation based on current action";
            public static final String STEP_2_DETAIL = "   If idle: use IDLE animation (4 frames)";
            public static final String STEP_2_DETAIL_2 = "   If running: use RUN animation (5 frames, 80ms)";
            public static final String STEP_3_CHECK_ARMED = "Check if character is holding weapon";
            public static final String STEP_3A_UNARMED = "FALSE: Render base character animation only";
            public static final String STEP_3B_ARMED = "TRUE: Proceed to weapon assembly chain";
            public static final String STEP_4_WEAPON_TYPE = "Get equipped weapon (GunProperties)";
            public static final String STEP_4_DETAIL = "   Example: GUN_PISTOL_TYPE_A_DARK";
            public static final String STEP_5_WEAPON_ANIMATION = "Get weapon animation for current state";
            public static final String STEP_5_DETAIL = "   Example: WeaponBikerAnimations.IDLE_VARIANT_A if idle with gun";
            public static final String STEP_6_HAND_POSE = "Get hand grip pose for current aiming angle";
            public static final String STEP_6_DETAIL = "   Example: WeaponHandPoses.BikerHands.GRIP_HORIZONTAL (0\u00b0)";
            public static final String STEP_7_RENDER = "Render: Base Animation + Weapon Animation + Hand Pose + Gun Sprite";
            public static final String STEP_7_DETAIL = "   Composite renders all 4 layers";
        }

        public static class WeaponFireChain {
            public static final String CHAIN_NAME = "weapon_fire";
            public static final String START_POINT = "GunProperties.GUN_[TYPE]_[VARIANT]";
            public static final String STEP_1_WEAPON_SELECT = "GunProperties - Select specific gun file";
            public static final String STEP_2_CHARACTER_EQUIP = "WeaponCharacterAnimations - Load character weapon idle animation";
            public static final String STEP_2_DETAIL = "   Example: WeaponBikerAnimations.IDLE_VARIANT_A";
            public static final String STEP_3_HAND_GRIP = "WeaponHandPoses - Apply grip pose overlay";
            public static final String STEP_3_DETAIL = "   Example: WeaponHandPoses.BikerHands.GRIP_HORIZONTAL";
            public static final String STEP_4_AIMING = "WeaponHandPoses - Cycle through 10 grip angles as player aims";
            public static final String STEP_4_DETAIL = "   Grips 0-9 cover full 360\u00b0 aiming circle";
            public static final String STEP_5_FIRE = "WeaponCharacterAnimations - Play fire/attack animation";
            public static final String STEP_5_DETAIL = "   Example: WeaponBikerAnimations.FIRE_VARIANT_A";
            public static final String STEP_6_PROJECTILE = "BulletProperties - Spawn ammo sprite";
            public static final String STEP_6_DETAIL = "   Example: BulletProperties.BULLET_TYPE_A (brass round)";
            public static final String STEP_7_TRACER = "ProjectileTracerProperties - Show bullet trajectory";
            public static final String STEP_7_DETAIL = "   Example: ProjectileTracerProperties.TRACER_TYPE_A_VARIANT_NARROW";
            public static final String STEP_8_IMPACT = "VfxAssetProperties - Play collision effect";
            public static final String STEP_8_DETAIL = "   Example: VfxAssetProperties.IMPACT_SPARK_BURST_GOLD_VAR1";
            public static final String STEP_9_DAMAGE = "Character properties - Apply damage value";
            public static final String STEP_9_DETAIL = "   Determines gameplay outcome";
        }
    }

    public static class TileCompositionPatterns {
        public static final String REGISTRY_TYPE = "tile_composition";

        public static class DecorationTileSystem {
            public static final String PATTERN_NAME = "DecoTileAccents";
            public static final String PATTERN_TYPE = "decoration_visual";
            public static final String DECO_DIAMOND_GRID = "62_Deco_DiamondXGridPattern_SmallScaleRepeating_PurpleOnDark_DecorativeWallO.png";
            public static final String DECO_GRADIENT_COLUMN = "70_Deco_GradientColumnStrip_TallNarrowPurpleGradient_BackgroundAccent_Vertic.png";
            public static final String DECO_LIGHT_STRIP_SINGLE = "74_Deco_IndicatorLightStrip_WideShortRectangle_RedLightRowSingle_MachinerySt.png";
            public static final String DECO_LIGHT_STRIP_EXTENDED = "75_Deco_IndicatorLightStrip_WideShortLonger_RedLightRowExtended_MachinerySta.png";
            public static final String DECO_LIGHT_STRIP_ALT = "76_Deco_IndicatorLightStrip_WideShortVariant_RedLightsSpacedAlt_AlternateSta.png";
            public static final String[] DECO_FILES = new String[]{"62_Deco_DiamondXGridPattern_SmallScaleRepeating_PurpleOnDark_DecorativeWallO.png", "70_Deco_GradientColumnStrip_TallNarrowPurpleGradient_BackgroundAccent_Vertic.png", "74_Deco_IndicatorLightStrip_WideShortRectangle_RedLightRowSingle_MachinerySt.png", "75_Deco_IndicatorLightStrip_WideShortLonger_RedLightRowExtended_MachinerySta.png", "76_Deco_IndicatorLightStrip_WideShortVariant_RedLightsSpacedAlt_AlternateSta.png"};
            public static final String NOTE = "Use decoration pieces to add visual detail without affecting walkability. Creates industrial atmosphere.";
        }

        public static class EdgeBorderAssemblyPattern {
            public static final String PATTERN_NAME = "EdgeBorderFrame";
            public static final String PATTERN_TYPE = "border_frame";
            public static final String EDGE_TOP_VARIANT_1 = "55_Edge_SmallMutedCornerCap_TopRightAligned_MutedTone_MinimalTransitionEdgeD.png";
            public static final String EDGE_DIVIDER_PLAIN = "77_Edge_PlainHorizontalDividerBar_NarrowThinStrip_SolidColour_FloorCeilingDi.png";
            public static final String EDGE_DIVIDER_WIDE = "78_Edge_WideHorizontalDividerBar_NarrowMediumStrip_SolidColour_FloorCeilingD.png";
            public static final String EDGE_DIVIDER_ALT = "79_Edge_AltShadeHorizontalBar_NarrowThinStrip_AlternateColourTone_LedgeOrDiv.png";
            public static final String EDGE_ACCENT_RED = "80_Edge_RedAccentHorizontalBar_NarrowThinStrip_RedHighlightTone_HazardLevelI.png";
            public static final String[] EDGE_FILES = new String[]{"55_Edge_SmallMutedCornerCap_TopRightAligned_MutedTone_MinimalTransitionEdgeD.png", "77_Edge_PlainHorizontalDividerBar_NarrowThinStrip_SolidColour_FloorCeilingDi.png", "78_Edge_WideHorizontalDividerBar_NarrowMediumStrip_SolidColour_FloorCeilingD.png", "79_Edge_AltShadeHorizontalBar_NarrowThinStrip_AlternateColourTone_LedgeOrDiv.png", "80_Edge_RedAccentHorizontalBar_NarrowThinStrip_RedHighlightTone_HazardLevelI.png"};
            public static final String NOTE = "Frame edge pieces around tile sections to prevent raw boundaries. Use dividers for floor/ceiling separation.";
        }

        public static class PanelStructureWallPattern {
            public static final String PATTERN_NAME = "PanelStructureWall";
            public static final String PATTERN_TYPE = "panel_assembly";
            public static final String INSET_DETAIL_TOP = "56_Panel_InsetDetail_SmallSquareEmbeddedLeftCentre_RecessedAccent_LeftWallDe.png";
            public static final String INSET_DETAIL_CENTER = "57_Panel_InsetDetail_SmallSquareEmbeddedRightCentre_RecessedAccent_RightWall.png";
            public static final String PANEL_DETAIL_FRAMED = "73_Panel_FramedInsetBlock_SmallSquareCentreFrame_RecessedScreen_TechMonitorO.png";
            public static final String PANEL_TECH_CONTROL = "61_Panel_TechControlDetail_SmallInsetDotCentred_BlueGreyTone_MachineryContro.png";
            public static final String PANEL_LARGE_INSET = "64_Panel_InsetDetail_MediumSquareEmbeddedCentre_RecessedLarger_CentralMachin.png";
            public static final String HAZARD_SLOPE_1 = "52_Hazard_AltAngleStripeBlock_DiagonalVariantAngle_ContactDamage_AltHazardFl.png";
            public static final String HAZARD_SLOPE_2 = "58_Hazard_FullStripeBlock_DiagonalRedOrangeVariantB_ContactDamage_HazardFloo.png";
            public static final String[] PANEL_FILES = new String[]{"56_Panel_InsetDetail_SmallSquareEmbeddedLeftCentre_RecessedAccent_LeftWallDe.png", "57_Panel_InsetDetail_SmallSquareEmbeddedRightCentre_RecessedAccent_RightWall.png", "73_Panel_FramedInsetBlock_SmallSquareCentreFrame_RecessedScreen_TechMonitorO.png", "61_Panel_TechControlDetail_SmallInsetDotCentred_BlueGreyTone_MachineryContro.png", "64_Panel_InsetDetail_MediumSquareEmbeddedCentre_RecessedLarger_CentralMachin.png", "52_Hazard_AltAngleStripeBlock_DiagonalVariantAngle_ContactDamage_AltHazardFl.png", "58_Hazard_FullStripeBlock_DiagonalRedOrangeVariantB_ContactDamage_HazardFloo.png"};
            public static final String NOTE = "Use panel pieces for structure, slopes for navigation surfaces";
        }

        public static class BrickSmallUnitWallPattern {
            public static final String PATTERN_NAME = "BrickSmallUnitWall";
            public static final String PATTERN_TYPE = "modular_grid";
            public static final String UNIT_VARIANT_A = "17_Brick_SmallUnit_BluePurple_WallFillTileA.png";
            public static final String UNIT_VARIANT_B = "18_Brick_SmallUnit_DarkNavy_WallFillTitleB.png";
            public static final String UNIT_VARIANT_C = "19_Brick_SmallUnit_MidBlue_WallFillTileC.png";
            public static final String NOTE = "Mix 3-6 unit variants in grid for visual complexity. No pattern needed - random placement OK.";
            public static final int RECOMMENDED_WALL_HEIGHT_TILES = 4;
        }

        public static class HorizontalBrickPlatformPattern {
            public static final String PATTERN_NAME = "HorizontalBrickPlatform";
            public static final String PATTERN_TYPE = "horizontal_strip";
            public static final String LEFT_EDGE_FILE = "02_Panel_HorizStripeBrick_BluePurple_SolidFloorVariantB.png";
            public static final String CENTER_TILE_FILE = "03_Panel_HorizStripeBrick_MidBlue_SolidFloorVariantC.png";
            public static final String RIGHT_EDGE_FILE = "04_Panel_HorizStripeBrick_DarkBlue_SolidFloorVariantD.png";
            public static final String NOTE = "Combine LEFT + (CENTER \u00d7 N) + RIGHT to create platform of any length";
            public static final int MIN_WIDTH_TILES = 1;
            public static final int MAX_WIDTH_TILES = 20;
        }
    }

    public static class WeaponHandPoses {
        public static final String BODY_PART_TYPE = "weapon_hand";
        public static final int GRIP_POSES_PER_CHARACTER = 10;

        public static class CyborgHands {
            public static final String CHARACTER = "Cyborg";
            public static final String DIRECTORY = "Resources/industrial-zone/weapons/1/3 Hands/3 Cyborg";
            public static final String GRIP_VERTICAL = "01_Weapon_Hand_Cyborg_GripVertical_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_DIAGONAL_DOWN = "02_Weapon_Hand_Cyborg_GripDiagonalDown_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_HORIZONTAL = "03_Weapon_Hand_Cyborg_GripHorizontal_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_DIAGONAL_UP_ALT = "04_Weapon_Hand_Cyborg_GripDiagonalUpAlt_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_VERTICAL_UP = "05_Weapon_Hand_Cyborg_GripVerticalUp_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_VERTICAL_ALT = "06_Weapon_Hand_Cyborg_GripVerticalAlt_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_DIAGONAL_LONG = "07_Weapon_Hand_Cyborg_GripDiagonalLong_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_HORIZONTAL_BLUE = "08_Weapon_Hand_Cyborg_GripHorizontalBlue_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_COMPACT_ANGLE = "09_Weapon_Hand_Cyborg_GripCompactAngle_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_LONG_BLUE = "10_Weapon_Hand_Cyborg_GripLongBlue_WeaponHoldPose_StaticSprite.png";
            public static final String[] ALL_GRIP_POSES = new String[]{"01_Weapon_Hand_Cyborg_GripVertical_WeaponHoldPose_StaticSprite.png", "02_Weapon_Hand_Cyborg_GripDiagonalDown_WeaponHoldPose_StaticSprite.png", "03_Weapon_Hand_Cyborg_GripHorizontal_WeaponHoldPose_StaticSprite.png", "04_Weapon_Hand_Cyborg_GripDiagonalUpAlt_WeaponHoldPose_StaticSprite.png", "05_Weapon_Hand_Cyborg_GripVerticalUp_WeaponHoldPose_StaticSprite.png", "06_Weapon_Hand_Cyborg_GripVerticalAlt_WeaponHoldPose_StaticSprite.png", "07_Weapon_Hand_Cyborg_GripDiagonalLong_WeaponHoldPose_StaticSprite.png", "08_Weapon_Hand_Cyborg_GripHorizontalBlue_WeaponHoldPose_StaticSprite.png", "09_Weapon_Hand_Cyborg_GripCompactAngle_WeaponHoldPose_StaticSprite.png", "10_Weapon_Hand_Cyborg_GripLongBlue_WeaponHoldPose_StaticSprite.png"};
        }

        public static class PunkHands {
            public static final String CHARACTER = "Punk";
            public static final String DIRECTORY = "Resources/industrial-zone/weapons/1/3 Hands/2 Punk";
            public static final String GRIP_VERTICAL = "01_Weapon_Hand_Punk_GripVertical_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_DIAGONAL_DOWN = "02_Weapon_Hand_Punk_GripDiagonalDown_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_HORIZONTAL = "03_Weapon_Hand_Punk_GripHorizontal_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_DIAGONAL_UP = "04_Weapon_Hand_Punk_GripDiagonalUp_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_VERTICAL_ALT = "05_Weapon_Hand_Punk_GripVerticalAlt_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_VERTICAL_B = "06_Weapon_Hand_Punk_GripVerticalB_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_DIAGONAL_LONG = "07_Weapon_Hand_Punk_GripDiagonalLong_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_HORIZONTAL_COMP = "08_Weapon_Hand_Punk_GripHorizontalCompactAngle_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_COMPACT_ANGLE = "09_Weapon_Hand_Punk_GripCompactAngle_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_COMPACT_LOW = "10_Weapon_Hand_Punk_GripCompactLow_WeaponHoldPose_StaticSprite.png";
            public static final String[] ALL_GRIP_POSES = new String[]{"01_Weapon_Hand_Punk_GripVertical_WeaponHoldPose_StaticSprite.png", "02_Weapon_Hand_Punk_GripDiagonalDown_WeaponHoldPose_StaticSprite.png", "03_Weapon_Hand_Punk_GripHorizontal_WeaponHoldPose_StaticSprite.png", "04_Weapon_Hand_Punk_GripDiagonalUp_WeaponHoldPose_StaticSprite.png", "05_Weapon_Hand_Punk_GripVerticalAlt_WeaponHoldPose_StaticSprite.png", "06_Weapon_Hand_Punk_GripVerticalB_WeaponHoldPose_StaticSprite.png", "07_Weapon_Hand_Punk_GripDiagonalLong_WeaponHoldPose_StaticSprite.png", "08_Weapon_Hand_Punk_GripHorizontalCompactAngle_WeaponHoldPose_StaticSprite.png", "09_Weapon_Hand_Punk_GripCompactAngle_WeaponHoldPose_StaticSprite.png", "10_Weapon_Hand_Punk_GripCompactLow_WeaponHoldPose_StaticSprite.png"};
        }

        public static class BikerHands {
            public static final String CHARACTER = "Biker";
            public static final String DIRECTORY = "Resources/industrial-zone/weapons/1/3 Hands/1 Biker";
            public static final String GRIP_HORIZONTAL = "01_Weapon_Hand_Biker_GripHorizontal_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_DIAGONAL_DOWN = "02_Weapon_Hand_Biker_GripDiagonalDown_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_VERTICAL = "03_Weapon_Hand_Biker_GripVertical_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_VERTICAL_UP_ALT = "04_Weapon_Hand_Biker_GripVerticalUpAlt_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_DIAGONAL_UP = "05_Weapon_Hand_Biker_GripDiagonalUp_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_LOW = "06_Weapon_Hand_Biker_GripLow_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_ANGLE_UP = "07_Weapon_Hand_Biker_GripAngleUp_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_VERTICAL_B = "08_Weapon_Hand_Biker_GripVerticalB_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_HORIZONTAL_B = "09_Weapon_Hand_Biker_GripHorizontalB_WeaponHoldPose_StaticSprite.png";
            public static final String GRIP_LONG_WEAPON = "10_Weapon_Hand_Biker_GripLongWeapon_WeaponHoldPose_StaticSprite.png";
            public static final String[] ALL_GRIP_POSES = new String[]{"01_Weapon_Hand_Biker_GripHorizontal_WeaponHoldPose_StaticSprite.png", "02_Weapon_Hand_Biker_GripDiagonalDown_WeaponHoldPose_StaticSprite.png", "03_Weapon_Hand_Biker_GripVertical_WeaponHoldPose_StaticSprite.png", "04_Weapon_Hand_Biker_GripVerticalUpAlt_WeaponHoldPose_StaticSprite.png", "05_Weapon_Hand_Biker_GripDiagonalUp_WeaponHoldPose_StaticSprite.png", "06_Weapon_Hand_Biker_GripLow_WeaponHoldPose_StaticSprite.png", "07_Weapon_Hand_Biker_GripAngleUp_WeaponHoldPose_StaticSprite.png", "08_Weapon_Hand_Biker_GripVerticalB_WeaponHoldPose_StaticSprite.png", "09_Weapon_Hand_Biker_GripHorizontalB_WeaponHoldPose_StaticSprite.png", "10_Weapon_Hand_Biker_GripLongWeapon_WeaponHoldPose_StaticSprite.png"};
        }
    }

    public static class WeaponCyborgAnimations {
        public static final String CHARACTER = "Cyborg";
        public static final String ANIMATION_TYPE = "weapon_held";
        public static final String DIRECTORY = "Resources/industrial-zone/weapons/1/1 Characters/3 Cyborg";
        public static final int TOTAL_ANIMATIONS = 5;
        public static final Map<String, AnimationConfig> WEAPON_ANIMATIONS = new LinkedHashMap<String, AnimationConfig>(){
            {
                this.put("idle_a", new AnimationConfig(3, 50, "Cyborg standing still, armed with weapon"));
                this.put("idle_b", new AnimationConfig(3, 50, "Cyborg standing still variant 2, armed"));
                this.put("jump_a", new AnimationConfig(3, 80, "Cyborg jumping arc with weapon"));
                this.put("jump_b", new AnimationConfig(3, 80, "Cyborg jumping arc variant 2"));
                this.put("run_a", new AnimationConfig(5, 80, "Cyborg running with weapon"));
                this.put("run_b", new AnimationConfig(5, 80, "Cyborg running variant 2"));
                this.put("sitdown_a", new AnimationConfig(3, 120, "Cyborg sitting down, defensive position"));
                this.put("walk_a", new AnimationConfig(4, 100, "Cyborg walking with weapon"));
                this.put("walk_b", new AnimationConfig(4, 100, "Cyborg walking variant 2"));
            }
        };
    }

    public static class WeaponPunkAnimations {
        public static final String CHARACTER = "Punk";
        public static final String ANIMATION_TYPE = "weapon_held";
        public static final String DIRECTORY = "Resources/industrial-zone/weapons/2/1 Characters/2 Punk";
        public static final int TOTAL_ANIMATIONS = 5;
        public static final Map<String, AnimationConfig> WEAPON_ANIMATIONS = new LinkedHashMap<String, AnimationConfig>(){
            {
                this.put("idle_a", new AnimationConfig(3, 50, "Punk standing still, armed with weapon"));
                this.put("idle_b", new AnimationConfig(3, 50, "Punk standing still variant 2, armed"));
                this.put("jump_a", new AnimationConfig(3, 80, "Punk jumping arc with weapon"));
                this.put("jump_b", new AnimationConfig(3, 80, "Punk jumping arc variant 2"));
                this.put("run_a", new AnimationConfig(5, 80, "Punk running with weapon"));
                this.put("run_b", new AnimationConfig(5, 80, "Punk running variant 2"));
                this.put("sitdown_a", new AnimationConfig(3, 120, "Punk sitting down, defensive position"));
                this.put("walk_a", new AnimationConfig(4, 100, "Punk walking with weapon"));
                this.put("walk_b", new AnimationConfig(4, 100, "Punk walking variant 2"));
            }
        };
    }

    public static class GunProperties {
        public static final String WEAPON_TYPE = "gun";
        public static final String DIRECTORY = "Resources/industrial-zone/weapons/1/2 Guns";
        public static final int GUN_CLASSES = 5;
        public static final int LIGHTING_VARIANTS = 2;
        public static final String GUN_PISTOL_TYPE_A_DARK = "01_Weapon_Gun_Pistol_TypeA_VariantDark_StaticSprite.png";
        public static final String GUN_PISTOL_TYPE_A_LIGHT = "02_Weapon_Gun_Pistol_TypeA_VariantLight_StaticSprite.png";
        public static final String GUN_PISTOL_TYPE_B_DARK = "03_Weapon_Gun_Pistol_TypeB_VariantDark_StaticSprite.png";
        public static final String GUN_PISTOL_TYPE_B_LIGHT = "04_Weapon_Gun_Pistol_TypeB_VariantLight_StaticSprite.png";
        public static final String GUN_COMPACT_TYPE_C_DARK = "05_Weapon_Gun_Compact_TypeC_VariantDark_StaticSprite.png";
        public static final String GUN_COMPACT_TYPE_C_LIGHT = "06_Weapon_Gun_Compact_TypeC_VariantLight_StaticSprite.png";
        public static final String GUN_COMPACT_TYPE_D_DARK = "07_Weapon_Gun_Compact_TypeD_VariantDark_StaticSprite.png";
        public static final String GUN_COMPACT_TYPE_D_LIGHT = "08_Weapon_Gun_Compact_TypeD_VariantLight_StaticSprite.png";
        public static final String GUN_DETAIL_TYPE_E_DARK = "09_Weapon_Gun_Detail_TypeE_VariantDark_StaticSprite.png";
        public static final String GUN_DETAIL_TYPE_E_LIGHT = "10_Weapon_Gun_Detail_TypeE_VariantLight_StaticSprite.png";
        public static final String GUN_DETAIL_TYPE_F_DARK = "11_Weapon_Gun_Detail_TypeF_VariantDark_StaticSprite.png";
        public static final String GUN_DETAIL_TYPE_F_LIGHT = "12_Weapon_Gun_Detail_TypeF_VariantLight_StaticSprite.png";
        public static final String GUN_RIFLE_TYPE_G_DARK = "13_Weapon_Gun_Rifle_TypeG_VariantDark_StaticSprite.png";
        public static final String GUN_RIFLE_TYPE_G_LIGHT = "14_Weapon_Gun_Rifle_TypeG_VariantLight_StaticSprite.png";
        public static final String GUN_RIFLE_TYPE_H_DARK = "15_Weapon_Gun_Rifle_TypeH_VariantDark_StaticSprite.png";
        public static final String GUN_RIFLE_TYPE_H_LIGHT = "16_Weapon_Gun_Rifle_TypeH_VariantLight_StaticSprite.png";
        public static final String GUN_SPECIAL_TYPE_I_DARK = "19_Weapon_Gun_Special_TypeI_VariantDark_StaticSprite.png";
        public static final String GUN_SPECIAL_TYPE_I_LIGHT = "20_Weapon_Gun_Special_TypeI_VariantRed_StaticSprite.png";
        public static final String GUN_SPECIAL_TYPE_J_DARK = "21_Weapon_Gun_Special_TypeJ_VariantDark_StaticSprite.png";
        public static final String GUN_SPECIAL_TYPE_J_LIGHT = "22_Weapon_Gun_Special_TypeJ_VariantBlueAlt_StaticSprite.png";
        public static final String[] ALL_GUN_SPRITES = new String[]{"01_Weapon_Gun_Pistol_TypeA_VariantDark_StaticSprite.png", "02_Weapon_Gun_Pistol_TypeA_VariantLight_StaticSprite.png", "03_Weapon_Gun_Pistol_TypeB_VariantDark_StaticSprite.png", "04_Weapon_Gun_Pistol_TypeB_VariantLight_StaticSprite.png", "05_Weapon_Gun_Compact_TypeC_VariantDark_StaticSprite.png", "06_Weapon_Gun_Compact_TypeC_VariantLight_StaticSprite.png", "07_Weapon_Gun_Compact_TypeD_VariantDark_StaticSprite.png", "08_Weapon_Gun_Compact_TypeD_VariantLight_StaticSprite.png", "09_Weapon_Gun_Detail_TypeE_VariantDark_StaticSprite.png", "10_Weapon_Gun_Detail_TypeE_VariantLight_StaticSprite.png", "11_Weapon_Gun_Detail_TypeF_VariantDark_StaticSprite.png", "12_Weapon_Gun_Detail_TypeF_VariantLight_StaticSprite.png", "13_Weapon_Gun_Rifle_TypeG_VariantDark_StaticSprite.png", "14_Weapon_Gun_Rifle_TypeG_VariantLight_StaticSprite.png", "15_Weapon_Gun_Rifle_TypeH_VariantDark_StaticSprite.png", "16_Weapon_Gun_Rifle_TypeH_VariantLight_StaticSprite.png", "19_Weapon_Gun_Special_TypeI_VariantDark_StaticSprite.png", "20_Weapon_Gun_Special_TypeI_VariantRed_StaticSprite.png", "21_Weapon_Gun_Special_TypeJ_VariantDark_StaticSprite.png", "22_Weapon_Gun_Special_TypeJ_VariantBlueAlt_StaticSprite.png"};
    }

    public static class BulletProperties {
        public static final String PROJECTILE_TYPE = "bullet";
        public static final String DIRECTORY = "Resources/industrial-zone/weapons/1/5 Bullets";
        public static final int TOTAL_BULLET_TYPES = 13;
        public static final String BULLET_TYPE_A = "01_Weapon_Bullet_TypeA_Single_StaticSprite.png";
        public static final String BULLET_TYPE_B = "02_Weapon_Bullet_TypeB_Single_StaticSprite.png";
        public static final String BULLET_TYPE_C = "03_Weapon_Bullet_TypeC_Single_StaticSprite.png";
        public static final String BULLET_TYPE_D_A = "04_Weapon_Bullet_TypeD_VariantA_StaticSprite.png";
        public static final String BULLET_TYPE_D_B = "05_Weapon_Bullet_TypeD_VariantB_StaticSprite.png";
        public static final String BULLET_TYPE_E_A = "06_Weapon_Bullet_TypeE_VariantA_StaticSprite.png";
        public static final String BULLET_TYPE_E_B = "07_Weapon_Bullet_TypeE_VariantB_StaticSprite.png";
        public static final String BULLET_TYPE_F = "08_Weapon_Bullet_TypeF_Single_StaticSprite.png";
        public static final String BULLET_TYPE_G_A = "09_Weapon_Bullet_TypeG_VariantA_StaticSprite.png";
        public static final String BULLET_TYPE_G_B = "10_Weapon_Bullet_TypeG_VariantB_StaticSprite.png";
        public static final String BULLET_TYPE_H = "11_Weapon_Bullet_TypeH_Single_StaticSprite.png";
        public static final String BULLET_TYPE_I = "12_Weapon_Bullet_TypeI_Single_StaticSprite.png";
        public static final String BULLET_TYPE_J = "13_Weapon_Bullet_TypeJ_Single_StaticSprite.png";
        public static final String[] ALL_BULLET_SPRITES = new String[]{"01_Weapon_Bullet_TypeA_Single_StaticSprite.png", "02_Weapon_Bullet_TypeB_Single_StaticSprite.png", "03_Weapon_Bullet_TypeC_Single_StaticSprite.png", "04_Weapon_Bullet_TypeD_VariantA_StaticSprite.png", "05_Weapon_Bullet_TypeD_VariantB_StaticSprite.png", "06_Weapon_Bullet_TypeE_VariantA_StaticSprite.png", "07_Weapon_Bullet_TypeE_VariantB_StaticSprite.png", "08_Weapon_Bullet_TypeF_Single_StaticSprite.png", "09_Weapon_Bullet_TypeG_VariantA_StaticSprite.png", "10_Weapon_Bullet_TypeG_VariantB_StaticSprite.png", "11_Weapon_Bullet_TypeH_Single_StaticSprite.png", "12_Weapon_Bullet_TypeI_Single_StaticSprite.png", "13_Weapon_Bullet_TypeJ_Single_StaticSprite.png"};
        public static final String[] BULLET_DESCRIPTIONS = new String[]{"Basic pistol round", "Standard rifle round", "Heavy sniper round", "Energy cell variant A", "Energy cell variant B", "Plasma shot variant A", "Plasma shot variant B", "Explosive round", "Armor-piercing variant A", "Armor-piercing variant B", "Hollow point", "Incendiary", "Tranquilizer"};
    }

    public static class ProjectileTracerProperties {
        public static final String FX_TYPE = "projectile_tracer";
        public static final String DIRECTORY = "Resources/industrial-zone/weapons/1/4 Shoot effects";
        public static final int TRACER_TYPES = 5;
        public static final int VARIANTS_PER_TYPE = 2;
        public static final String TRACER_TYPE_A_NARROW = "01_Weapon_ShootFX_Tracer_TypeA_VariantNarrow_StaticSprite.png";
        public static final String TRACER_TYPE_A_SCATTER = "02_Weapon_ShootFX_Tracer_TypeA_VariantScatter_StaticSprite.png";
        public static final String TRACER_TYPE_B_DOTTED = "03_Weapon_ShootFX_Tracer_TypeB_VariantDotted_StaticSprite.png";
        public static final String TRACER_TYPE_B_SLASH = "04_Weapon_ShootFX_Tracer_TypeB_VariantSlash_StaticSprite.png";
        public static final String TRACER_TYPE_C_HEAVY = "05_Weapon_ShootFX_Tracer_TypeC_VariantHeavy_StaticSprite.png";
        public static final String TRACER_TYPE_C_BOLD = "06_Weapon_ShootFX_Tracer_TypeC_VariantBold_StaticSprite.png";
        public static final String TRACER_TYPE_D_WAVE = "07_Weapon_ShootFX_Tracer_TypeD_VariantWave_StaticSprite.png";
        public static final String TRACER_TYPE_D_JAGGED = "08_Weapon_ShootFX_Tracer_TypeD_VariantJagged_StaticSprite.png";
        public static final String TRACER_TYPE_E_LASER = "09_Weapon_ShootFX_Tracer_TypeE_VariantLaser_StaticSprite.png";
        public static final String TRACER_TYPE_E_OUTLINE = "10_Weapon_ShootFX_Tracer_TypeE_VariantOutline_StaticSprite.png";
        public static final String[] ALL_TRACER_FILES = new String[]{"01_Weapon_ShootFX_Tracer_TypeA_VariantNarrow_StaticSprite.png", "02_Weapon_ShootFX_Tracer_TypeA_VariantScatter_StaticSprite.png", "03_Weapon_ShootFX_Tracer_TypeB_VariantDotted_StaticSprite.png", "04_Weapon_ShootFX_Tracer_TypeB_VariantSlash_StaticSprite.png", "05_Weapon_ShootFX_Tracer_TypeC_VariantHeavy_StaticSprite.png", "06_Weapon_ShootFX_Tracer_TypeC_VariantBold_StaticSprite.png", "07_Weapon_ShootFX_Tracer_TypeD_VariantWave_StaticSprite.png", "08_Weapon_ShootFX_Tracer_TypeD_VariantJagged_StaticSprite.png", "09_Weapon_ShootFX_Tracer_TypeE_VariantLaser_StaticSprite.png", "10_Weapon_ShootFX_Tracer_TypeE_VariantOutline_StaticSprite.png"};
    }

    public static class CharacterHandPositionSystem {
        public static final String SYSTEM_TYPE = "hand_positioning";
        public static final int CHARACTER_TYPES = 3;

        public static class HandAnimationTiming {
            public static final int TRANSITION_EASE_MS = 50;
            public static final int IDLE_FRAME_RATE = 100;
            public static final int ACTION_FRAME_RATE = 60;
            public static final float ROTATION_SPEED = 8.0f;
            public static final float INTERPOLATION_SMOOTHNESS = 0.85f;
        }

        public static class HandPositionRegistry {
            public static final String getCharacterType(String string) {
                if (string.contains("female")) {
                    return "female_soldier";
                }
                if (string.contains("male")) {
                    return "male_soldier";
                }
                if (string.contains("brawler")) {
                    return "brawler";
                }
                return "male_soldier";
            }

            public static final float getHandSpeedMultiplier(String string) {
                switch (string) {
                    case "female_soldier": {
                        return 1.15f;
                    }
                    case "male_soldier": {
                        return 1.0f;
                    }
                    case "brawler": {
                        return 0.8f;
                    }
                }
                return 1.0f;
            }

            public static final float getReachDistance(String string) {
                switch (string) {
                    case "female_soldier": {
                        return 85.0f;
                    }
                    case "male_soldier": {
                        return 90.0f;
                    }
                    case "brawler": {
                        return 100.0f;
                    }
                }
                return 90.0f;
            }

            public static final float getGripStrength(String string) {
                switch (string) {
                    case "female_soldier": {
                        return 0.85f;
                    }
                    case "male_soldier": {
                        return 0.95f;
                    }
                    case "brawler": {
                        return 1.2f;
                    }
                }
                return 1.0f;
            }
        }

        public static class BrawlerHandProfile {
            public static final String CHARACTER_TYPE = "brawler";
            public static final String CHARACTER_DESC = "Melee bruiser - powerful grip, slow but devastating";
            public static final float HAND_SPEED_MULTIPLIER = 0.8f;
            public static final float REACH_DISTANCE = 100.0f;
            public static final float GRIP_STRENGTH = 1.2f;
            public static final int LEFT_HAND_X = -22;
            public static final int LEFT_HAND_Y = -12;
            public static final int RIGHT_HAND_X = 22;
            public static final int RIGHT_HAND_Y = -12;

            public static class HandJoints {
                public static final int WRIST_OFFSET_X = 8;
                public static final int WRIST_OFFSET_Y = 5;
                public static final int PALM_OFFSET_X = 12;
                public static final int PALM_OFFSET_Y = 8;
                public static final int FINGER_SPREAD = 6;
            }

            public static class AnimationOffsets {
                public static final int REACH_X_OFFSET = 32;
                public static final int REACH_Y_OFFSET = -8;
                public static final int REACH_DURATION_MS = 400;
                public static final int MELEE_X_OFFSET = 45;
                public static final int MELEE_Y_OFFSET = 12;
                public static final int MELEE_DURATION_MS = 550;
                public static final int AIM_X_OFFSET = 38;
                public static final int AIM_Y_OFFSET = -10;
                public static final int AIM_DURATION_MS = 250;
                public static final int PICKUP_X_OFFSET = 28;
                public static final int PICKUP_Y_OFFSET = 15;
                public static final int PICKUP_DURATION_MS = 550;
            }
        }

        public static class MaleSoldierHandProfile {
            public static final String CHARACTER_TYPE = "male_soldier";
            public static final String CHARACTER_DESC = "Standard soldier - balanced strength and control";
            public static final float HAND_SPEED_MULTIPLIER = 1.0f;
            public static final float REACH_DISTANCE = 90.0f;
            public static final float GRIP_STRENGTH = 0.95f;
            public static final int LEFT_HAND_X = -20;
            public static final int LEFT_HAND_Y = -10;
            public static final int RIGHT_HAND_X = 20;
            public static final int RIGHT_HAND_Y = -10;

            public static class HandJoints {
                public static final int WRIST_OFFSET_X = 6;
                public static final int WRIST_OFFSET_Y = 4;
                public static final int PALM_OFFSET_X = 10;
                public static final int PALM_OFFSET_Y = 6;
                public static final int FINGER_SPREAD = 5;
            }

            public static class AnimationOffsets {
                public static final int REACH_X_OFFSET = 28;
                public static final int REACH_Y_OFFSET = -3;
                public static final int REACH_DURATION_MS = 350;
                public static final int MELEE_X_OFFSET = 40;
                public static final int MELEE_Y_OFFSET = 8;
                public static final int MELEE_DURATION_MS = 450;
                public static final int AIM_X_OFFSET = 32;
                public static final int AIM_Y_OFFSET = -12;
                public static final int AIM_DURATION_MS = 220;
                public static final int PICKUP_X_OFFSET = 22;
                public static final int PICKUP_Y_OFFSET = 12;
                public static final int PICKUP_DURATION_MS = 500;
            }
        }

        public static class FemaleSoldierHandProfile {
            public static final String CHARACTER_TYPE = "female_soldier";
            public static final String CHARACTER_DESC = "Elite soldier - agile with precise hand control";
            public static final float HAND_SPEED_MULTIPLIER = 1.15f;
            public static final float REACH_DISTANCE = 85.0f;
            public static final float GRIP_STRENGTH = 0.85f;
            public static final int LEFT_HAND_X = -18;
            public static final int LEFT_HAND_Y = -8;
            public static final int RIGHT_HAND_X = 18;
            public static final int RIGHT_HAND_Y = -8;

            public static class HandJoints {
                public static final int WRIST_OFFSET_X = 5;
                public static final int WRIST_OFFSET_Y = 3;
                public static final int PALM_OFFSET_X = 8;
                public static final int PALM_OFFSET_Y = 5;
                public static final int FINGER_SPREAD = 4;
            }

            public static class AnimationOffsets {
                public static final int REACH_X_OFFSET = 25;
                public static final int REACH_Y_OFFSET = -5;
                public static final int REACH_DURATION_MS = 300;
                public static final int MELEE_X_OFFSET = 35;
                public static final int MELEE_Y_OFFSET = 5;
                public static final int MELEE_DURATION_MS = 400;
                public static final int AIM_X_OFFSET = 30;
                public static final int AIM_Y_OFFSET = -15;
                public static final int AIM_DURATION_MS = 200;
                public static final int PICKUP_X_OFFSET = 20;
                public static final int PICKUP_Y_OFFSET = 10;
                public static final int PICKUP_DURATION_MS = 450;
            }
        }
    }

    public static class SplashLogoProperties {
        public static final String UI_TYPE = "splash_logo";
        public static final String DIRECTORY = "Resources/industrial-zone/gui/5 Logo";
        public static final int LOGO_VARIANTS = 3;
        public static final String LOGO_COMPACT = "GUI_Logo_IndustrialZone_Compact.png";
        public static final String LOGO_FULL = "GUI_Logo_IndustrialZone_Full.png";
        public static final String LOGO_MINIMAL = "GUI_Logo_IndustrialZone_Minimal.png";
        public static final String[] LOGO_FILES = new String[]{"GUI_Logo_IndustrialZone_Compact.png", "GUI_Logo_IndustrialZone_Full.png", "GUI_Logo_IndustrialZone_Minimal.png"};
        public static final String LOGO_COLOR = "#4A4A9E";
        public static final String LOGO_ACCENT = "#FFD700";
        public static final String VISUAL_STYLE = "Digital/Industrial";

        public static final String getLogoFile(int n) {
            if (n < 0 || n >= LOGO_FILES.length) {
                return LOGO_COMPACT;
            }
            return LOGO_FILES[n];
        }

        public static class LogoTextOverlays {

            public static class MinimalOverlay {
                public static final String TEXT_CONTENT = "NEXUS";
                public static final int OVERLAY_X = 10;
                public static final int OVERLAY_Y = 10;
                public static final String FONT_COLOR = "#00FF00";
                public static final int FONT_SIZE = 16;
                public static final String FONT_WEIGHT = "bold";
                public static final String TEXT_ALIGNMENT = "center";
                public static final String TEXT_EFFECT = "glow_effect";
            }

            public static class FullOverlay {
                public static final String TEXT_CONTENT = "3359098";
                public static final int OVERLAY_X = 10;
                public static final int OVERLAY_Y = 10;
                public static final String FONT_COLOR = "#4A4A9E";
                public static final int FONT_SIZE = 14;
                public static final String FONT_WEIGHT = "bold";
                public static final String TEXT_ALIGNMENT = "center";
            }

            public static class CompactOverlay {
                public static final String TEXT_CONTENT = "CSCU9N6";
                public static final int OVERLAY_X = 10;
                public static final int OVERLAY_Y = 10;
                public static final String FONT_COLOR = "#FFD700";
                public static final int FONT_SIZE = 12;
                public static final String FONT_WEIGHT = "bold";
                public static final String TEXT_ALIGNMENT = "center";
            }
        }
    }

    public static class WeaponBikerAnimations {
        public static final String CHARACTER = "Biker";
        public static final String ANIMATION_TYPE = "weapon_held";
        public static final String DIRECTORY = "Resources/industrial-zone/weapons/1/1 Characters/1 Biker";
        public static final int TOTAL_ANIMATIONS = 5;
        public static final int IDLE_VARIANT_A_FRAMES = 4;
        public static final int IDLE_VARIANT_A_TIMING_MS = 50;
        public static final String IDLE_VARIANT_A_FILE = "01_Weapon_Biker_Idle_VariantA_4Frames1Row_WeaponIdleStand_Loop_1_50ms.png";
        public static final int IDLE_VARIANT_B_FRAMES = 4;
        public static final int IDLE_VARIANT_B_TIMING_MS = 50;
        public static final String IDLE_VARIANT_B_FILE = "02_Weapon_Biker_Idle_VariantB_4Frames1Row_WeaponIdleStand_Loop_1_50ms.png";
        public static final int JUMP_VARIANT_A_FRAMES = 3;
        public static final int JUMP_VARIANT_A_TIMING_MS = 80;
        public static final String JUMP_VARIANT_A_FILE = "03_Weapon_Biker_Jump_VariantA_3Frames1Row_WeaponJumpArcPlayOnce_80ms.png";
        public static final int JUMP_VARIANT_B_FRAMES = 3;
        public static final int JUMP_VARIANT_B_TIMING_MS = 80;
        public static final String JUMP_VARIANT_B_FILE = "04_Weapon_Biker_Jump_VariantB_3Frames1Row_WeaponJumpArcPlayOnce_80ms.png";
        public static final int RUN_VARIANT_A_FRAMES = 5;
        public static final int RUN_VARIANT_A_TIMING_MS = 80;
        public static final String RUN_VARIANT_A_FILE = "05_Weapon_Biker_Run_VariantA_5Frames1Row_WeaponRunCycle_Loop_1_80ms.png";
        public static final int RUN_VARIANT_B_FRAMES = 5;
        public static final int RUN_VARIANT_B_TIMING_MS = 80;
        public static final String RUN_VARIANT_B_FILE = "06_Weapon_Biker_Run_VariantB_5Frames1Row_WeaponRunCycle_Loop_1_80ms.png";
        public static final int SITDOWN_VARIANT_A_FRAMES = 4;
        public static final int SITDOWN_VARIANT_A_TIMING_MS = 120;
        public static final String SITDOWN_VARIANT_A_FILE = "07_Weapon_Biker_Sitdown_VariantA_4Frames1Row_WeaponSitDown_PlayOnce_120ms.png";
        public static final int WALK_VARIANT_A_FRAMES = 4;
        public static final int WALK_VARIANT_A_TIMING_MS = 100;
        public static final String WALK_VARIANT_A_FILE = "09_Weapon_Biker_Walk_VariantA_4Frames1Row_WeaponWalkCycle_Loop_100ms.png";
        public static final int WALK_VARIANT_B_FRAMES = 4;
        public static final int WALK_VARIANT_B_TIMING_MS = 100;
        public static final String WALK_VARIANT_B_FILE = "10_Weapon_Biker_Walk_VariantB_4Frames1Row_WeaponWalkCycle_Loop_100ms.png";
        public static final Map<String, AnimationConfig> WEAPON_ANIMATIONS = new LinkedHashMap<String, AnimationConfig>(){
            {
                this.put("idle_a", new AnimationConfig(4, 50, "Biker standing still, armed with weapon"));
                this.put("idle_b", new AnimationConfig(4, 50, "Biker standing still variant 2, armed"));
                this.put("jump_a", new AnimationConfig(3, 80, "Biker jumping arc with weapon"));
                this.put("jump_b", new AnimationConfig(3, 80, "Biker jumping arc variant 2"));
                this.put("run_a", new AnimationConfig(5, 80, "Biker running with weapon"));
                this.put("run_b", new AnimationConfig(5, 80, "Biker running variant 2"));
                this.put("sitdown_a", new AnimationConfig(4, 120, "Biker sitting down, defensive position"));
                this.put("walk_a", new AnimationConfig(4, 100, "Biker walking with weapon"));
                this.put("walk_b", new AnimationConfig(4, 100, "Biker walking variant 2"));
            }
        };
    }

    public static class MouseKeyBindings {
        public static final String INPUT_TYPE = "mouse_key";
        public static final String DIRECTORY = "Resources/industrial-zone/Mouse_keys";
        public static final int TOTAL_MOUSE_BINDINGS = 18;
        public static final String[] MOUSE_ACTION_BINDINGS = new String[]{"Mouse_LeftClick_Blue_Level1Tutorial_Display_PrimaryAction.png", "Mouse_LeftClick_Red_Level2Combat_Display_PrimaryAttack.png", "Mouse_MiddleClick_Blue_Level1Tutorial_Display_SecondaryAction.png", "Mouse_MiddleClick_Red_Level2Combat_Display_SecondaryAttack.png", "Mouse_Move_FourDirections_Camer_OrCrosshairMove_Tutorial.png", "Mouse_MoveDiagonal_FourDiagonals_AimOrCrosshairMove_Tutorial.png", "Mouse_MoveDown_ArrowDown_ScrollDownOrAimDown_Tutorial.png", "Mouse_MoveLeft_ArrowLeft_ScrollLeftOrAimLeft_Tutorial.png", "Mouse_MoveLeftRight_BothArrows_HorizontalScrollOrAim_Tutorial.png", "Mouse_MoveRight_ArrowRight_ScrollRightOrAimRight_Tutorial.png", "Mouse_MoveUp_ArrowUp_ScrollUpOrAimUp_Tutorial.png", "Mouse_MoveUpDown_BothArrows_VerticalScrollOrAim_Tutorial.png", "Mouse_Neutral_NoHighlight_DefaultState_Display.png", "Mouse_RightClick_Blue_Level1Tutorial_Display_AimOrContext.png", "Mouse_RightClick_Red_Level2Combat_Display_AimOrContext.png", "Mouse_ScrollDown_Blue_Level1Tutorial_Display_ZoomOutOrPrevWeapon.png", "Mouse_ScrollUp_Blue_Level1Tutorial_Display_ZoomInOrNextWeapon.png", "Mouse_ScrollWheel_Red_Level2Combat_Display_ZoomOrWeaponSwap.png"};

        public static final String getMouseBinding(String string, boolean bl) {
            String string2 = string.toUpperCase();
            for (String string3 : MOUSE_ACTION_BINDINGS) {
                if (!string3.contains(string2)) continue;
                if (bl && string3.contains("Red_Level2")) {
                    return string3;
                }
                if (bl || !string3.contains("Blue_Level1")) continue;
                return string3;
            }
            return MOUSE_ACTION_BINDINGS[0];
        }
    }

    public static class KeyboardKeyBindings {
        public static final String INPUT_TYPE = "keyboard_key";
        public static final String DIRECTORY = "Resources/industrial-zone/KeyBoard_Keys";
        public static final int TOTAL_KEY_BINDINGS = 100;
        public static final String[] NUMBER_KEY_BINDINGS = new String[]{"Key_0_Number_SelectItemOrQuickslot0_CombatAction.png", "Key_1_Number_SelectWeapon1OrAbility1_CombatAction.png", "Key_2_Number_SelectWeapon2OrAbility2_CombatAction.png", "Key_3_Number_SelectWeapon3OrAbility3_CombatAction.png", "Key_4_Number_SelectWeapon4OrAbility4_CombatAction.png", "Key_5_Number_SelectWeapon5OrAbility5_CombatAction.png", "Key_6_Number_SelectItem1OrQuickslot6_CombatAction.png", "Key_7_Number_SelectItem2OrQuickslot7_CombatAction.png", "Key_8_Number_SelectItem3OrQuickslot8_CombatAction.png", "Key_9_Number_SelectItem4OrQuickslot9_CombatAction.png"};
        public static final String[] LETTER_KEY_BINDINGS = new String[]{"Key_A_Letter_MoveLeftOrStrafeLeft_MovementTutorial.png", "Key_Alt_Modifier_AlternateMode_OrAim_CombatAction.png", "Key_ArrowDown_Direction_MoveDownOrCrouch_MovementTutorial.png", "Key_ArrowLeft_Direction_MoveLeft_MovementTutorial.png", "Key_ArrowRight_Direction_MoveRight_MovementTutorial.png", "Key_ArrowUp_Direction_MoveUp_OrScrollMenu_MovementTutorial.png", "Key_B_Letter_BombOrSpecialAttack_CombatAction.png", "Key_C_Letter_CrouchOrShield_CombatAction.png", "Key_Caps_Lock_Special_Alternate_Modifier_UIControl.png", "Key_Comma_Symbol_PreviousTargetOrDecreaseValue_UIControl.png", "Key_Ctrl_Modifier_CrouchOrSprintMod_MovementTutorial.png", "Key_D_Letter_MoveRightOrStrafeRight_MovementTutorial.png", "Key_E_Letter_ActionOrFireWeapon_CombatAction.png", "Key_Enter_Special_ConfirmOrInte_raction_UINavigation.png", "Key_Escape_Function_PauseGame_UIControl.png", "Key_F1_Function_DebugToggleOrMap_UIControl.png", "Key_F2_Function_ExtraAction1_UIControl.png", "Key_F3_Function_Inventory_OrStat_UIControl.png", "Key_F4_Function_Audio_ToggleOrSettings_UIControl.png", "Key_F5_Function_QuickSave_Orch_start_UIControl.png", "Key_F6_Function_QuickLoadOrRe_start_UIControl.png", "Key_F7_Function_ScreenshotOrRe_wind_UIControl.png", "Key_F8_Function_SpeedToggleOrRewind_UIControl.png", "Key_G_Letter_ThrowGrenade_OrPowerup_CombatAction.png", "Key_H_Letter_Inventory_Open_UINavigation.png", "Key_I_Letter_InventoryOpen_UINavigation.png", "Key_J_Letter_JournalOrLump_UINavigation.png", "Key_K_Letter_KickOrStillTree_CombatAction.png", "Key_L_Letter_LockOnTargetOrLight_CombatAction.png", "Key_M_Letter_MapOrMeleeAttack_CombatAction.png", "Key_Minus_Symbol_ZoomOutOrDecreaseValue_UIControl.png", "Key_N_Letter_NextObje_ctiveOrMap_UINavigation.png", "Key_O_Letter_Objective_OrMap_UINavigation.png", "Key_P_Letter_PauseGame_UIControl.png", "Key_Period_Symbol_NextTargetOrIncreaseValue_UIControl.png", "Key_Q_Letter_SwapOpenOrSpawn_CombatAction.png", "Key_R_Letter_Reload_CombatAction.png", "Key_S_Letter_MoveDown_OrCrouch_MovementTutorial.png", "Key_ScrollDown_Direction_ZoomOutOrPrevItem_UIControl.png", "Key_ScrollUp_Direction_ZoomInOrNextItem_UIControl.png", "Key_Spacebar_Special_Jump_OrConfirm_MovementTutorial.png", "Key_T_Letter_UseItemOrToggle_UIControl.png", "Key_Tab_Special_InventoryOrMap_UINavigation.png", "Key_U_Letter_UseMelecOrPowerup_CombatAction.png", "Key_V_Letter_VoiceChat_Or_ViewMode_UIControl.png", "Key_W_Letter_MoveUpOrCombat_MovementTutorial.png", "Key_X_Letter_ExecuteOrDodge_CombatAction.png", "Key_Y_Letter_ExtraAction1OrYes_UIControl.png", "Key_Z_Letter_ZoomOrRollLeft_CombatAction.png"};
        public static final String SPECIAL_KEY_ESCAPE = "Key_Escape_Function_PauseOrQuit_OrMenu_UIControl.png";
        public static final String SPECIAL_KEY_ENTER = "Key_Enter_Special_ConfirmOrInteaction_UINavigation.png";
        public static final String SPECIAL_KEY_TAB = "Key_Tab_Special_InventoryOrMap_UINavigation.png";

        public static final String getKeyBinding(String string, String string2) {
            return "Key_" + string + "_" + string2 + "_CombatAction.png";
        }
    }

    public static class FontProperties {
        public static final String UI_TYPE = "font";
        public static final String DIRECTORY = "Resources/industrial-zone/gui/10 Font/images";
        public static final int CHARACTER_COUNT = 60;
        public static final String FONT_STYLE = "Monospace Pixel Font";
        public static final String[] FONT_CHARACTER_FILES = new String[]{"1.01.png", "1.02.png", "1.03.png", "1.04.png", "1.05.png", "1.06.png", "1.07.png", "1.08.png", "1.09.png", "1.10.png", "1.11.png", "1.12.png", "1.13.png", "1.14.png", "1.15.png", "1.16.png", "1.17.png", "1.18.png", "1.19.png", "1.20.png", "1.21.png", "1.22.png", "1.23.png", "1.24.png", "1.25.png", "1.26.png", "1.27.png", "1.28.png", "1.29.png", "1.30.png", "1.31.png", "1.32.png", "1.33.png", "1.34.png", "1.35.png", "1.36.png", "1.37.png", "1.38.png", "1.39.png", "1.40.png", "1.41.png", "1.42.png", "1.43.png", "1.44.png", "1.45.png", "1.46.png", "1.47.png", "1.48.png", "1.49.png", "1.50.png", "1.51.png", "1.52.png", "1.53.png", "1.54.png", "1.55.png", "1.56.png", "1.57.png", "1.58.png", "1.59.png", "1.60.png", "1.61.png", "1.62.png", "1.63.png"};

        public static final String getCharacterFile(int n) {
            if (n < 0 || n >= FONT_CHARACTER_FILES.length) {
                return FONT_CHARACTER_FILES[0];
            }
            return FONT_CHARACTER_FILES[n];
        }
    }

    public static class SkillIconProperties {
        public static final String UI_TYPE = "skill_icon";
        public static final String DIRECTORY = "Resources/industrial-zone/gui/9 Other/2 Skill icons";
        public static final int SKILL_ICON_COUNT = 20;
        public static final String[] SKILL_ICON_FILES = new String[]{"01_GUI_SkillIcon_Eye_VisibilityOrReveal_SkillIcon.png", "02_GUI_SkillIcon_ChevronUp_BoostOrLevelUp_SkillIcon.png", "03_GUI_SkillIcon_Radiation_HazardOrPoison_SkillIcon.png", "04_GUI_SkillIcon_Mushroom_ExplosionOrBlast_SkillIcon.png", "05_GUI_SkillIcon_Hourglass_TimerOrSlowTime_SkillIcon.png", "06_GUI_SkillIcon_Crosshair_TargetOrAim_SkillIcon.png", "07_GUI_SkillIcon_House_BaseOrRespawn_SkillIcon.png", "08_GUI_SkillIcon_Device_TechOrInterface_SkillIcon.png", "09_GUI_SkillIcon_Question_UnknownOrHelp_SkillIcon.png", "10_GUI_SkillIcon_Exclaim_AlertOrWarning_SkillIcon.png", "11_GUI_SkillIcon_Plus_HealOrAddOrHealth_SkillIcon.png", "12_GUI_SkillIcon_DiagCross_CancelOrMultiply_SkillIcon.png", "13_GUI_SkillIcon_Tick_ConfirmOrSelect_SkillIcon.png", "14_GUI_SkillIcon_ArrowUp_UploadOrJump_SkillIcon.png", "15_GUI_SkillIcon_Knife_MeleeOrAttack_SkillIcon.png", "16_GUI_SkillIcon_Heart_HealthOrLife_SkillIcon.png", "17_GUI_SkillIcon_Refresh_ResetOrCooldown_SkillIcon.png", "18_GUI_SkillIcon_Shield_DefenceOrBlock_SkillIcon.png", "19_GUI_SkillIcon_Skull_DeathOrDanger_SkillIcon.png", "20_GUI_SkillIcon_ArrowDown_DownloadOrDrop_SkillIcon.png"};
        public static final String[] SKILL_NAMES = new String[]{"Visibility", "Boost", "Radiation", "Explosion", "Timer", "Target", "Base", "Tech", "Help", "Alert", "Heal", "Cancel", "Death", "Jump", "Melee", "Health", "Refresh", "Defense", "Death", "Drop"};
    }

    public static class GUIDecorProperties {
        public static final String UI_TYPE = "decor";
        public static final String DIRECTORY = "Resources/industrial-zone/gui/9 Other/1 Decor";
        public static final int DECOR_ELEMENTS = 8;
        public static final String[] DECOR_FILES = new String[]{"01_GUI_Decor_GlowBars_FourVerticalNeonRods_Decoration.png", "02_GUI_Decor_RibbonZigzag_PinkStackedLShape_Decoration.png", "03_GUI_Decor_CableTwist_RedBlueHelixWire_Decoration.png", "04_GUI_Decor_CableConnector_BlueRedPlugWires_Decoration.png", "05_GUI_Decor_CableLoose_SingleRedWire_Decoration.png", "06_GUI_Decor_CableLoose_SingleTealWire_Decoration.png", "07_GUI_Decor_CablePlug_SingleBlueConnector_Decoration.png", "08_GUI_Decor_CableCoil_BlueLoopedWire_Decoration.png"};
        public static final String[] DECOR_DESCRIPTIONS = new String[]{"Vertical neon glow bars (4 rods) - frame accent", "Pink L-shaped ribbon - decorative corner detail", "Red/blue helical cable twist - corner accent", "Blue/red cable plug connector - connection point", "Single red loose wire - dangling accent", "Single teal loose wire - alternative accent", "Single blue cable plug - connector element", "Blue looped cable coil - wall detail accent"};
    }

    public static class CursorProperties {
        public static final String UI_TYPE = "cursor";
        public static final String DIRECTORY = "Resources/industrial-zone/gui/8 Cursors";
        public static final int CURSOR_VARIANTS = 4;
        public static final String CURSOR_DEFAULT = "01_GUI_Cursor_White_DefaultPointer.png";
        public static final String CURSOR_TARGETING = "02_GUI_Cursor_Blue_TargetingPointer.png";
        public static final String CURSOR_ATTACK = "03_GUI_Cursor_Red_AttackPointer.png";
        public static final String CURSOR_CONFIRM = "04_GUI_Cursor_Green_ConfirmPointer.png";
        public static final String[] CURSOR_FILES = new String[]{"01_GUI_Cursor_White_DefaultPointer.png", "02_GUI_Cursor_Blue_TargetingPointer.png", "03_GUI_Cursor_Red_AttackPointer.png", "04_GUI_Cursor_Green_ConfirmPointer.png"};

        public static final String getCursorForState(String string) {
            switch (string.toLowerCase()) {
                case "aiming": 
                case "targeting": {
                    return CURSOR_TARGETING;
                }
                case "attack": 
                case "attacking": {
                    return CURSOR_ATTACK;
                }
                case "confirm": 
                case "ready": {
                    return CURSOR_CONFIRM;
                }
            }
            return CURSOR_DEFAULT;
        }
    }

    public static class GUINumberElements {
        public static final String UI_TYPE = "number_element";
        public static final String DIRECTORY = "Resources/industrial-zone/gui/7 Numbers";
        public static final int DIGIT_COUNT = 10;
        public static final String[] DIGIT_STYLED_FILES = new String[]{"GUI_Number_Digit0_Zero.png", "01_GUI_Number_Digit1_StyledGlyph_Decorative.png", "02_GUI_Number_Digit2_StyledGlyph_Decorative.png", "03_GUI_Number_Digit3_StyledGlyph_Decorative.png", "04_GUI_Number_Digit4_StyledGlyph_Decorative.png", "05_GUI_Number_Digit5_StyledGlyph_Decorative.png", "06_GUI_Number_Digit6_StyledGlyph_Decorative.png", "07_GUI_Number_Digit7_StyledGlyph_Decorative.png", "08_GUI_Number_Digit8_StyledGlyph_Decorative.png", "09_GUI_Number_Digit9_StyledGlyph_Decorative.png"};
        public static final String PUNCTUATION_DOT = "GUI_Number_Symbol_Dot_Decimal.png";
        public static final String PUNCTUATION_COMMA = "GUI_Number_Symbol_Comma_Separator.png";
        public static final String SYMBOL_K = "GUI_Number_Symbol_K_Suffix.png";
        public static final String SYMBOL_M = "GUI_Number_Symbol_M_Million.png";
        public static final String SYMBOL_PLUS = "GUI_Number_Symbol_Plus_Addition.png";
        public static final String SYMBOL_B = "GUI_Number_Symbol_B_Thousand.png";

        public static final String getDigitFile(int n) {
            if (n < 0 || n > 9) {
                return DIGIT_STYLED_FILES[0];
            }
            return DIGIT_STYLED_FILES[n];
        }
    }

    public static class AnimationConfig {
        public final int frameCount;
        public final int timingMs;
        public final String description;

        public AnimationConfig(int n, int n2, String string) {
            this.frameCount = n;
            this.timingMs = n2;
            this.description = string;
        }

        public String toString() {
            return this.frameCount + " frames @ " + this.timingMs + "ms - " + this.description;
        }
    }

    public static class DroneAnimationConfigs {

        public static class HoverPlatform {
            public static final String DRONE_TYPE = "hover_platform";
            public static final String BASE_PATH = "Resources/industrial-zone/characters/enemies/drones/6";
            public static final Map<String, AnimationConfig> ANIMATIONS = new LinkedHashMap<String, AnimationConfig>(){
                {
                    this.put("walk", new AnimationConfig(4, 150, "Primary hovering movement"));
                    this.put("walk2", new AnimationConfig(4, 100, "Alternative movement pattern"));
                    this.put("drop", new AnimationConfig(5, 100, "Platform deployment sequence"));
                    this.put("capsule", new AnimationConfig(7, 100, "Capsule projectile launch"));
                }
            };

            public static GridFrameAnimationLoader loadAnimation(String string) {
                AnimationConfig animationConfig = ANIMATIONS.get(string);
                if (animationConfig == null) {
                    return null;
                }
                GridFrameAnimationLoader gridFrameAnimationLoader = new GridFrameAnimationLoader("hover_platform_" + string, "Resources/industrial-zone/characters/enemies/drones/6/0" + (AnimationAndSpriteLoader.getAnimationIndex(string) + 1) + "_EnemyDrone_HoverPlatform*" + animationConfig.frameCount + "*Frames1Row*.png");
                gridFrameAnimationLoader.load();
                return gridFrameAnimationLoader;
            }
        }

        public static class ArmoredTruckVariant {
            public static final String DRONE_TYPE = "armored_truck_variant";
            public static final String BASE_PATH = "Resources/industrial-zone/characters/enemies/drones/5_2";
            public static final Map<String, AnimationConfig> ANIMATIONS = new LinkedHashMap<String, AnimationConfig>(){
                {
                    this.put("idle", new AnimationConfig(4, 150, "Variant idle engined"));
                    this.put("movement", new AnimationConfig(4, 100, "Variant driving movement"));
                    this.put("death", new AnimationConfig(5, 120, "Variant destruction sequence"));
                }
            };

            public static GridFrameAnimationLoader loadAnimation(String string) {
                AnimationConfig animationConfig = ANIMATIONS.get(string);
                if (animationConfig == null) {
                    return null;
                }
                GridFrameAnimationLoader gridFrameAnimationLoader = new GridFrameAnimationLoader("armored_truck_variant_" + string, "Resources/industrial-zone/characters/enemies/drones/5_2/0" + (AnimationAndSpriteLoader.getAnimationIndex(string) + 1) + "_EnemyDrone_ArmoredTruckVariant*" + animationConfig.frameCount + "*Frames1Row*.png");
                gridFrameAnimationLoader.load();
                return gridFrameAnimationLoader;
            }
        }

        public static class ArmoredTruck {
            public static final String DRONE_TYPE = "armored_truck";
            public static final String BASE_PATH = "Resources/industrial-zone/characters/enemies/drones/5";
            public static final Map<String, AnimationConfig> ANIMATIONS = new LinkedHashMap<String, AnimationConfig>(){
                {
                    this.put("idle", new AnimationConfig(4, 150, "Engine idling stationary"));
                    this.put("movement", new AnimationConfig(4, 100, "Forward driving movement"));
                    this.put("death", new AnimationConfig(5, 120, "Vehicle destruction explosion"));
                }
            };

            public static GridFrameAnimationLoader loadAnimation(String string) {
                AnimationConfig animationConfig = ANIMATIONS.get(string);
                if (animationConfig == null) {
                    return null;
                }
                GridFrameAnimationLoader gridFrameAnimationLoader = new GridFrameAnimationLoader("armored_truck_" + string, "Resources/industrial-zone/characters/enemies/drones/5/0" + (AnimationAndSpriteLoader.getAnimationIndex(string) + 1) + "_EnemyDrone_ArmoredTruck*" + animationConfig.frameCount + "*Frames1Row*.png");
                gridFrameAnimationLoader.load();
                return gridFrameAnimationLoader;
            }
        }

        public static class HelicopterDrone {
            public static final String DRONE_TYPE = "helicopter";
            public static final String BASE_PATH = "Resources/industrial-zone/characters/enemies/drones/4";
            public static final Map<String, AnimationConfig> ANIMATIONS = new LinkedHashMap<String, AnimationConfig>(){
                {
                    this.put("idle", new AnimationConfig(4, 150, "Rotor spinning hovering"));
                    this.put("patrol", new AnimationConfig(4, 100, "Patrol flight movement"));
                    this.put("landing", new AnimationConfig(16, 80, "Extended landing sequence descent"));
                    this.put("death", new AnimationConfig(1, 120, "Ghost silhouette death"));
                }
            };

            public static GridFrameAnimationLoader loadAnimation(String string) {
                AnimationConfig animationConfig = ANIMATIONS.get(string);
                if (animationConfig == null) {
                    return null;
                }
                GridFrameAnimationLoader gridFrameAnimationLoader = new GridFrameAnimationLoader("helicopter_" + string, "Resources/industrial-zone/characters/enemies/drones/4/0" + (AnimationAndSpriteLoader.getAnimationIndex(string) + 1) + "_EnemyDrone_HelicopterDrone*" + animationConfig.frameCount + "*Frames1Row*.png");
                gridFrameAnimationLoader.load();
                return gridFrameAnimationLoader;
            }
        }

        public static class HoverShooterDrone {
            public static final String DRONE_TYPE = "hovershooter";
            public static final String BASE_PATH = "Resources/industrial-zone/characters/enemies/drones/3";
            public static final Map<String, AnimationConfig> ANIMATIONS = new LinkedHashMap<String, AnimationConfig>(){
                {
                    this.put("idle", new AnimationConfig(4, 150, "Stationary floating idle"));
                    this.put("forward", new AnimationConfig(4, 100, "Moving forward while tracking"));
                    this.put("back", new AnimationConfig(4, 100, "Reversing retreat movement"));
                    this.put("fire1", new AnimationConfig(16, 50, "Rapid burst fire mode 1"));
                    this.put("fire2", new AnimationConfig(16, 50, "Rapid burst fire mode 2"));
                    this.put("fire3", new AnimationConfig(16, 50, "Rapid burst fire mode 3"));
                    this.put("death", new AnimationConfig(8, 80, "Explosion death sequence"));
                }
            };

            public static GridFrameAnimationLoader loadAnimation(String string) {
                AnimationConfig animationConfig = ANIMATIONS.get(string);
                if (animationConfig == null) {
                    return null;
                }
                GridFrameAnimationLoader gridFrameAnimationLoader = new GridFrameAnimationLoader("hovershooter_" + string, "Resources/industrial-zone/characters/enemies/drones/3/0" + (AnimationAndSpriteLoader.getAnimationIndex(string) + 1) + "_EnemyDrone_HoverShooter*" + animationConfig.frameCount + "*Frames1Row*.png");
                gridFrameAnimationLoader.load();
                return gridFrameAnimationLoader;
            }
        }

        public static class JetDrone {
            public static final String DRONE_TYPE = "jet_drone";
            public static final String BASE_PATH = "Resources/industrial-zone/characters/enemies/drones/2";
            public static final Map<String, AnimationConfig> ANIMATIONS = new LinkedHashMap<String, AnimationConfig>(){
                {
                    this.put("fly", new AnimationConfig(6, 80, "Aerial flight forward movement"));
                    this.put("bomb", new AnimationConfig(8, 80, "Bomb projectile payload drop"));
                }
            };

            public static GridFrameAnimationLoader loadAnimation(String string) {
                AnimationConfig animationConfig = ANIMATIONS.get(string);
                if (animationConfig == null) {
                    return null;
                }
                GridFrameAnimationLoader gridFrameAnimationLoader = new GridFrameAnimationLoader("jet_drone_" + string, "Resources/industrial-zone/characters/enemies/drones/2/0" + (AnimationAndSpriteLoader.getAnimationIndex(string) + 1) + "_EnemyDrone_JetDrone*" + animationConfig.frameCount + "*Frames1Row*.png");
                gridFrameAnimationLoader.load();
                return gridFrameAnimationLoader;
            }
        }

        public static class UfoSaucerDrone {
            public static final String DRONE_TYPE = "ufo_saucer";
            public static final String BASE_PATH = "Resources/industrial-zone/characters/enemies/drones/1";
            public static final Map<String, AnimationConfig> ANIMATIONS = new LinkedHashMap<String, AnimationConfig>(){
                {
                    this.put("idle", new AnimationConfig(4, 150, "UFO hovering in place with beam"));
                    this.put("walk", new AnimationConfig(4, 100, "Horizontal hovering movement"));
                    this.put("scan", new AnimationConfig(8, 100, "Beam scanning full rotation"));
                    this.put("walkscan", new AnimationConfig(6, 100, "Moving while scanning area"));
                    this.put("death", new AnimationConfig(5, 120, "Destruction sequence explosion"));
                }
            };

            public static GridFrameAnimationLoader loadAnimation(String string) {
                AnimationConfig animationConfig = ANIMATIONS.get(string);
                if (animationConfig == null) {
                    return null;
                }
                GridFrameAnimationLoader gridFrameAnimationLoader = new GridFrameAnimationLoader("ufo_saucer_" + string, "Resources/industrial-zone/characters/enemies/drones/1/0" + (AnimationAndSpriteLoader.getAnimationIndex(string) + 1) + "_EnemyDrone_UfoSaucer*" + animationConfig.frameCount + "*Frames1Row*.png");
                gridFrameAnimationLoader.load();
                return gridFrameAnimationLoader;
            }
        }
    }

    public static class PlayerCharacterAnimations {

        public static class PunkAnimations {
            public static final String CHARACTER = "punk";
            public static final String BASE_PATH = "Resources/industrial-zone/characters/player/punk";
            public static final Map<String, AnimationConfig> ANIMATIONS = new LinkedHashMap<String, AnimationConfig>(){
                {
                    this.put("idle", new AnimationConfig(5, 150, "Casual standing posture"));
                    this.put("idle2", new AnimationConfig(5, 150, "Hip strut idle"));
                    this.put("walk", new AnimationConfig(5, 100, "Confident walking style"));
                    this.put("run", new AnimationConfig(6, 80, "Fast sprint running"));
                    this.put("dash", new AnimationConfig(4, 60, "Quick slide forward"));
                    this.put("jump", new AnimationConfig(3, 80, "High vertical jump"));
                    this.put("doublejump", new AnimationConfig(4, 80, "Aerial flip jump"));
                    this.put("fall", new AnimationConfig(3, 100, "Falling descent"));
                    this.put("land", new AnimationConfig(2, 80, "Impact landing"));
                    this.put("climb", new AnimationConfig(4, 120, "Parkour climbing"));
                    this.put("hang", new AnimationConfig(4, 150, "Extended hanging"));
                    this.put("pullup", new AnimationConfig(7, 80, "Acrobatic pull-up"));
                    this.put("punch", new AnimationConfig(6, 70, "Quick combo punches"));
                    this.put("attack1", new AnimationConfig(5, 70, "First combo attack"));
                    this.put("attack2", new AnimationConfig(6, 70, "Second attack variant"));
                    this.put("attack3", new AnimationConfig(6, 70, "Triple strike combo"));
                    this.put("walkattack", new AnimationConfig(5, 80, "Walking attack flow"));
                    this.put("runattack", new AnimationConfig(6, 70, "Running strike combo"));
                    this.put("hurt", new AnimationConfig(2, 100, "Flinch reaction"));
                    this.put("death", new AnimationConfig(5, 120, "Dramatic fall death"));
                    this.put("use", new AnimationConfig(5, 100, "Casual object use"));
                    this.put("sitdown", new AnimationConfig(3, 120, "Relaxed sitting"));
                    this.put("angry", new AnimationConfig(5, 150, "Rage expression"));
                    this.put("happy", new AnimationConfig(5, 150, "Victory celebration"));
                    this.put("talk", new AnimationConfig(5, 120, "Casual talking"));
                }
            };

            public static GridFrameAnimationLoader loadAnimation(String string) {
                AnimationConfig animationConfig = ANIMATIONS.get(string);
                if (animationConfig == null) {
                    AnimationAndSpriteLoader.logError("Punk animation not found: " + string);
                    return null;
                }
                GridFrameAnimationLoader gridFrameAnimationLoader = new GridFrameAnimationLoader("player_punk_" + string, "Resources/industrial-zone/characters/player/punk/0" + (AnimationAndSpriteLoader.getAnimationIndex(string) + 1) + "_Player_Punk_" + AnimationAndSpriteLoader.formatAnimationName(string) + "_" + animationConfig.frameCount + "Frames1Row_*.png");
                if (gridFrameAnimationLoader.load()) {
                    AnimationAndSpriteLoader.log("\u2713 Loaded Punk animation: " + string);
                }
                return gridFrameAnimationLoader;
            }
        }

        public static class CyborgAnimations {
            public static final String CHARACTER = "cyborg";
            public static final String BASE_PATH = "Resources/industrial-zone/characters/player/cyborg";
            public static final Map<String, AnimationConfig> ANIMATIONS = new LinkedHashMap<String, AnimationConfig>(){
                {
                    this.put("idle", new AnimationConfig(4, 150, "Standing tech breathing"));
                    this.put("idle2", new AnimationConfig(5, 150, "Secondary idle with arm movement"));
                    this.put("walk", new AnimationConfig(5, 100, "Tech-enhanced walking"));
                    this.put("run", new AnimationConfig(5, 80, "Fast synchronized running"));
                    this.put("dash", new AnimationConfig(4, 60, "Boost dash with tech effect"));
                    this.put("jump", new AnimationConfig(3, 80, "Powered jump arc"));
                    this.put("doublejump", new AnimationConfig(5, 80, "Second mid-air jump"));
                    this.put("fall", new AnimationConfig(3, 100, "Falling with stabilization"));
                    this.put("land", new AnimationConfig(2, 80, "Powered landing impact"));
                    this.put("climb", new AnimationConfig(4, 120, "Climbing with arm assists"));
                    this.put("hang", new AnimationConfig(3, 150, "Hanging with grip strength"));
                    this.put("pullup", new AnimationConfig(7, 80, "Powered pull-up animation"));
                    this.put("punch", new AnimationConfig(5, 70, "Enhanced punch combo"));
                    this.put("attack1", new AnimationConfig(5, 70, "First cyber attack"));
                    this.put("attack2", new AnimationConfig(6, 70, "Extended combo attack"));
                    this.put("attack3", new AnimationConfig(6, 70, "Triple attack variation"));
                    this.put("walkattack", new AnimationConfig(5, 80, "Walking attack movement"));
                    this.put("runattack", new AnimationConfig(5, 70, "Running strike"));
                    this.put("hurt", new AnimationConfig(2, 100, "Damage reaction"));
                    this.put("death", new AnimationConfig(5, 120, "Shutdown sequence"));
                    this.put("use", new AnimationConfig(5, 100, "Tech interface interaction"));
                    this.put("sitdown", new AnimationConfig(3, 120, "Crouching posture"));
                    this.put("angry", new AnimationConfig(5, 150, "Aggression mode emote"));
                    this.put("happy", new AnimationConfig(5, 150, "System success emote"));
                    this.put("talk", new AnimationConfig(5, 120, "Voice synthesis talk"));
                }
            };

            public static GridFrameAnimationLoader loadAnimation(String string) {
                AnimationConfig animationConfig = ANIMATIONS.get(string);
                if (animationConfig == null) {
                    AnimationAndSpriteLoader.logError("Cyborg animation not found: " + string);
                    return null;
                }
                GridFrameAnimationLoader gridFrameAnimationLoader = new GridFrameAnimationLoader("player_cyborg_" + string, "Resources/industrial-zone/characters/player/cyborg/0" + (AnimationAndSpriteLoader.getAnimationIndex(string) + 1) + "_Player_Cyborg_" + AnimationAndSpriteLoader.formatAnimationName(string) + "_" + animationConfig.frameCount + "Frames1Row_*.png");
                if (gridFrameAnimationLoader.load()) {
                    AnimationAndSpriteLoader.log("\u2713 Loaded Cyborg animation: " + string);
                }
                return gridFrameAnimationLoader;
            }
        }

        public static class BikerAnimations {
            public static final String CHARACTER = "biker";
            public static final String BASE_PATH = "Resources/industrial-zone/characters/player/biker";
            public static final Map<String, AnimationConfig> ANIMATIONS = new LinkedHashMap<String, AnimationConfig>(){
                {
                    this.put("idle", new AnimationConfig(4, 150, "Standing breathing loop, default idle"));
                    this.put("idle2", new AnimationConfig(5, 150, "Alternate standing posture"));
                    this.put("walk", new AnimationConfig(5, 100, "Walking cycle, forward movement"));
                    this.put("run", new AnimationConfig(5, 80, "Fast running, full cycle"));
                    this.put("dash", new AnimationConfig(4, 60, "Quick dash, sliding forward"));
                    this.put("jump", new AnimationConfig(3, 80, "Single jump arc start to apex"));
                    this.put("doublejump", new AnimationConfig(5, 80, "Second jump mid-air"));
                    this.put("fall", new AnimationConfig(4, 100, "Falling descent animation"));
                    this.put("land", new AnimationConfig(2, 80, "Landing impact frame"));
                    this.put("climb", new AnimationConfig(4, 120, "Ladder climbing cycle"));
                    this.put("hang", new AnimationConfig(3, 150, "Hanging from ledge"));
                    this.put("pullup", new AnimationConfig(7, 80, "Pulling self up onto platform"));
                    this.put("punch", new AnimationConfig(6, 70, "Standing punch combo"));
                    this.put("attack1", new AnimationConfig(5, 70, "First attack variation"));
                    this.put("attack2", new AnimationConfig(6, 70, "Second attack with combo"));
                    this.put("attack3", new AnimationConfig(7, 70, "Extended attack sequence"));
                    this.put("walkattack", new AnimationConfig(5, 80, "Attacking while walking"));
                    this.put("runattack", new AnimationConfig(6, 70, "Fast running attack"));
                    this.put("hurt", new AnimationConfig(2, 100, "Recoil from damage"));
                    this.put("death", new AnimationConfig(5, 120, "Death sequence, fall over"));
                    this.put("use", new AnimationConfig(5, 100, "Using object interaction"));
                    this.put("sitdown", new AnimationConfig(3, 120, "Sitting on ground"));
                    this.put("angry", new AnimationConfig(5, 150, "Angry expression emote"));
                    this.put("happy", new AnimationConfig(5, 150, "Happy/victorious emote"));
                    this.put("talk", new AnimationConfig(5, 120, "Talking/dialogue animation"));
                }
            };

            public static GridFrameAnimationLoader loadAnimation(String string) {
                AnimationConfig animationConfig = ANIMATIONS.get(string);
                if (animationConfig == null) {
                    AnimationAndSpriteLoader.logError("Biker animation not found: " + string);
                    return null;
                }
                String string2 = String.format("%03d_Player_Biker_%s_%dFrames1Row_*.png", AnimationAndSpriteLoader.getAnimationIndex(string) + 1, AnimationAndSpriteLoader.formatAnimationName(string), animationConfig.frameCount);
                GridFrameAnimationLoader gridFrameAnimationLoader = new GridFrameAnimationLoader("player_biker_" + string, "Resources/industrial-zone/characters/player/biker/" + string2);
                if (gridFrameAnimationLoader.load()) {
                    AnimationAndSpriteLoader.log("\u2713 Loaded Biker animation: " + string + " (" + animationConfig.frameCount + " frames, " + animationConfig.timingMs + "ms)");
                }
                return gridFrameAnimationLoader;
            }
        }
    }

    public static class UIElementProperties {

        public static class DigitDisplayElements {
            public static final String UI_TYPE = "digit_display";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects";
            public static final String FONT_NAME = "Pixel Font Grey";
            public static final String COLOR = "Grey #808080";
            public static final int DIGIT_COUNT = 10;
            public static final int GLYPH_WIDTH = 8;
            public static final int GLYPH_HEIGHT = 8;
            public static final String[] DIGIT_FILES = new String[]{"UI_Digit_Zero_GreyPixelFont_HUDSCoreDisplay_SingleGlyph.png", "UI_Digit_One_GreyPixelFont_HUDSCoreDisplay_SingleGlyph.png", "UI_Digit_Two_GreyPixelFont_HUDSCoreDisplay_SingleGlyph.png", "UI_Digit_Three_GreyPixelFont_HUDSCoreDisplay_SingleGlyph.png", "UI_Digit_Four_GreyPixelFont_HUDSCoreDisplay_SingleGlyph.png", "UI_Digit_Five_GreyPixelFont_HUDSCoreDisplay_SingleGlyph.png", "UI_Digit_Six_GreyPixelFont_HUDSCoreDisplay_SingleGlyph.png", "UI_Digit_Seven_GreyPixelFont_HUDSCoreDisplay_SingleGlyph.png", "UI_Digit_Eight_GreyPixelFont_HUDSCoreDisplay_SingleGlyph.png", "UI_Digit_Nine_GreyPixelFont_HUDSCoreDisplay_SingleGlyph.png"};

            public static final String getDigitFile(int n) {
                if (n < 0 || n > 9) {
                    return DIGIT_FILES[0];
                }
                return DIGIT_FILES[n];
            }
        }
    }

    public static class GUITilesetSystem {
        public static final String SYSTEM_TYPE = "window_frame_tileset";
        public static final String DIRECTORY = "Resources/industrial-zone/gui/1 Frames";
        public static final int TOTAL_PIECES = 82;
        public static final String ASSEMBLY_PATTERN = "Modular corner + edge + fill system";
        public static final String LAYOUT_METHOD = "Grid-based composition with stretched edges";

        public static GridSpritesheetLoader loadTilesetSystem() {
            GridSpritesheetLoader gridSpritesheetLoader = new GridSpritesheetLoader("gui_tileset_system", DIRECTORY, 64, 64, 8, 10);
            if (gridSpritesheetLoader.load()) {
                AnimationAndSpriteLoader.log("\u2713 Loaded GUI tileset system with 82 modular pieces");
                AnimationAndSpriteLoader.log("  Corner pieces: 16");
                AnimationAndSpriteLoader.log("  Edge pieces: 20");
                AnimationAndSpriteLoader.log("  Fill pieces: 12");
                AnimationAndSpriteLoader.log("  Panel pieces: 14");
                AnimationAndSpriteLoader.log("  Divider pieces: 6");
                AnimationAndSpriteLoader.log("  Special pieces: 3");
            }
            return gridSpritesheetLoader;
        }

        public static class CornerPieces {
            public static final int COUNT = 16;
            public static final String[] CORNER_FILES = new String[]{"01_GUI_Frame_CornerTopLeft_TallLShapePiece_WindowCorner.png", "03_GUI_Frame_CornerTopRight_TallLShapeMirror_WindowCorner.png", "19_GUI_Frame_CornerBottomLeft_LShapeCorner_WindowCorner.png", "27_GUI_Frame_CornerBottomRight_DiagonalAngleTrim_WindowCorner.png", "04_GUI_Frame_CornerTopLeftRivet_RedDotAccents_WindowCorner.png", "09_GUI_Frame_CornerInsetTopLeft_InsetBorderSquare_WindowCorner.png", "21_GUI_Frame_CornerBottomLeftFull_InsetSquareCorner_WindowCorner.png", "29_GUI_Frame_CornerBottomRightFull_BracketCorner_WindowCorner.png", "30_GUI_Frame_CornerTopRightFull_BracketTShape_WindowCorner.png", "31_GUI_Frame_CornerTopRight_SmallInsetLightTrim_WindowCorner.png", "32_GUI_Frame_CornerTopLeft_TriangleCut_WindowCorner.png", "33_GUI_Frame_CornerTopRight_TriangleCutMirror_WindowCorner.png", "34_GUI_Frame_CornerTopRight_DiagonalCut_WindowCorner.png", "63_GUI_Frame_CornerHexagonal_LightGreyOctagonShape_WindowCorner.png", "64_GUI_Frame_CornerDiagonalCutTopLeft_WindowCorner.png", "73_GUI_Frame_CornerBottomLeft_PlainCorner_WindowCorner.png"};
            public static final String[] DESCRIPTIONS = new String[]{"Top-Left Standard", "Top-Right Standard", "Bottom-Left Standard", "Bottom-Right Standard", "Top-Left Rivet (decorative dots)", "Top-Left Inset (border)", "Bottom-Left Inset", "Bottom-Right Bracket", "Top-Right Bracket", "Top-Right Light Trim", "Top-Left Triangle Cut", "Top-Right Triangle Cut", "Top-Right Diagonal", "Hexagonal/Octagon", "Diagonal Top-Left", "Plain Bottom-Left"};
        }

        public static class EdgePieces {
            public static final int COUNT = 20;
            public static final String RENDERING = "Stretch horizontally (top/bottom) or vertically (left/right)";
            public static final String[] TOP_EDGE_FILES = new String[]{"02_GUI_Frame_EdgeTopBar_HorizontalBlueAccentStrip_WindowTopEdge.png", "36_GUI_Frame_EdgeTopBarVariant_LighterStripe_WindowTopEdge.png", "67_GUI_Frame_EdgeTopBar_PlainBar_WindowTopEdge.png"};
            public static final String[] BOTTOM_EDGE_FILES = new String[]{"20_GUI_Frame_EdgeBottomBar_PlainDarkStrip_WindowBottomEdge.png", "24_GUI_Frame_EdgeBottomBar_DotLineTexture_WindowBottomEdge.png", "50_GUI_Frame_EdgeBottomBar_HorizStripTexture_WindowBottomEdge.png", "54_GUI_Frame_EdgeBottomBar_LightBlueAccentLine_WindowBottomEdge.png", "75_GUI_Frame_EdgeBottomBar_PlainHorizStrip_WindowBottomEdge.png"};
            public static final String[] LEFT_EDGE_FILES = new String[]{"05_GUI_Frame_EdgeLeftStrip_TallNarrowVerticalBar_WindowLeftEdge.png", "10_GUI_Frame_EdgeLeftStrip_TallBlueTintBar_WindowLeftEdge.png", "12_GUI_Frame_EdgeLeftStrip_TallDarkerBar_WindowLeftEdge.png", "13_GUI_Frame_EdgeLeftStrip_TallThinLighter_WindowLeftEdge.png", "14_GUI_Frame_EdgeLeftStrip_TallWiderWithTrim_WindowLeftEdge.png", "15_GUI_Frame_EdgeLeftStrip_TallLighterBlueTrim_WindowLeftEdge.png", "43_GUI_Frame_EdgeLeftStrip_NarrowDarkVertical_WindowLeftEdge.png", "57_GUI_Frame_EdgeLeftStrip_NarrowDarkSlightVariant_WindowLeftEdge.png", "69_GUI_Frame_EdgeLeftStrip_LeftEdgeBar_WindowLeftEdge.png"};
            public static final String[] RIGHT_EDGE_FILES = new String[]{"06_GUI_Frame_EdgeRightStrip_TallNarrowVerticalBar_WindowRightEdge.png", "18_GUI_Frame_EdgeRightStrip_TallBlueAccentLine_WindowRightEdge.png", "22_GUI_Frame_EdgeRightStrip_TallNarrowPlain_WindowRightEdge.png", "28_GUI_Frame_EdgeRightStrip_TallBlueAccent_WindowRightEdge.png", "48_GUI_Frame_EdgeRightStrip_NarrowVerticalBar_WindowRightEdge.png", "55_GUI_Frame_EdgeRightStrip_NarrowSlightlyWider_WindowRightEdge.png", "72_GUI_Frame_EdgeThinRightStrip_ThinVerticalRight_WindowRightEdge.png"};
        }

        public static class FillPieces {
            public static final int COUNT = 12;
            public static final String[] FILL_FILES = new String[]{"07_GUI_Frame_FillSolidNavy_LargeFullBlock_WindowFill.png", "11_GUI_Frame_FillDiagonalTexture_FaintDiagLines_WindowFill.png", "38_GUI_Frame_FillSolidNavy_WideRectNoBorder_WindowFill.png", "39_GUI_Frame_FillSolidNavy_WiderVariant_WindowFill.png", "40_GUI_Frame_FillSolidDarkNavy_FullBlock_WindowFill.png", "45_GUI_Frame_FillSolidNavy_PlainFlatBlock_WindowFill.png", "46_GUI_Frame_FillSolidNavy_WideFlatRect_WindowFill.png", "59_GUI_Frame_FillSolidNavy_WideFlatRect_WindowFill.png", "65_GUI_Frame_FillSolidDarkNavy_PlainBlock_WindowFill.png", "66_GUI_Frame_FillSolidNavyLighter_SlightlyLighter_WindowFill.png", "68_GUI_Frame_FillSolidNavy_PlainBlock_WindowFill.png", "74_GUI_Frame_FillSolidNavy_PlainBlock_WindowFill.png"};
            public static final String[] FILL_TYPES = new String[]{"Large Solid Navy", "Diagonal Textured", "Wide Navy", "Wider Navy", "Dark Navy Full", "Plain Flat", "Wide Flat", "Wide Flat Variant", "Dark Plain", "Light Navy", "Standard Block", "Plain Block"};
        }

        public static class PanelPieces {
            public static final int COUNT = 14;
            public static final String[] PANEL_FILES = new String[]{"37_GUI_Frame_PanelInsetSquare_SingleCellDarkBorder_PanelCell.png", "41_GUI_Frame_Panel2Cell_TwoInsetSquares_PanelCell.png", "42_GUI_Frame_Panel2CellVariant_TwoInsetSquaresDiffShade_PanelCell.png", "51_GUI_Frame_PanelInsetSquare_SingleCell_PanelCell.png", "52_GUI_Frame_Panel2CellVariant2_TwoInsetSquares_PanelCell.png", "53_GUI_Frame_Panel2CellVariant3_TwoInsetSlightBorderDiff_PanelCell.png", "58_GUI_Frame_PanelWideRect_InsetBorderWider_PanelCell.png", "60_GUI_Frame_PanelMedium_InsetSquareTaller_PanelCell.png", "61_GUI_Frame_Panel2Cell_TwoInsetSquaresWithIcons_PanelCell.png", "62_GUI_Frame_Panel2CellVariant_TwoInsetSquaresIconsAlt_PanelCell.png", "71_GUI_Frame_Panel2CellGrid_TwoCellGridBlock_PanelCell.png", "79_GUI_Frame_PanelInsetSquare_DarkBorderSquare_PanelCell.png", "80_GUI_Frame_PanelInsetSquare_LighterVariant_PanelCell.png", "82_GUI_Frame_MasterSpritesheet_AllFramePiecesLayout_Reference.png"};
            public static final String[] PANEL_TYPES = new String[]{"Single Cell Dark", "Dual Cell Standard", "Dual Cell Shade Variant", "Single Cell Plain", "Dual Cell Variant 2", "Dual Cell Variant 3", "Wide Rect Inset", "Medium Tall Cell", "Dual Cell with Icons", "Dual Cell with Icons Alt", "2-Cell Grid Block", "Dark Border Single", "Light Variant Single", "Master Reference Spritesheet"};
        }

        public static class DividerPieces {
            public static final int COUNT = 6;
            public static final String[] DIVIDER_FILES = new String[]{"16_GUI_Frame_PanelWideRect_TealCyanAccentStripe_DividerBar.png", "17_GUI_Frame_PanelWideRect_DarkerNoAccent_DividerBar.png", "23_GUI_Frame_PanelWideRect_TechDotTexture_DividerBar.png", "25_GUI_Frame_PanelWideRect_WiderPlainDark_DividerBar.png", "26_GUI_Frame_PanelWideRect_DarkerVariant_DividerBar.png", "35_GUI_Frame_PanelHorizDivider_TwoRowDarkNavy_DividerBar.png", "49_GUI_Frame_PanelHorizBar_MediumWidth_DividerBar.png", "70_GUI_Frame_Panel2CellWideDivider_TwoCellBlock_DividerBar.png"};
            public static final String[] DIVIDER_TYPES = new String[]{"Teal Cyan Accent", "Dark No Accent", "Tech Dot Texture", "Wide Plain Dark", "Dark Variant", "Horizontal Two-Row", "Medium Horizontal", "Two-Cell Wide"};
        }

        public static class SpecialPieces {
            public static final int COUNT = 3;
            public static final String[] SPECIAL_FILES = new String[]{"47_GUI_Frame_EdgeDiagonalStrip_AngledTopLeftSlant_WindowEdge.png", "56_GUI_Frame_EdgeDiagonalStrip_AngledLineTexture_WindowEdge.png", "76_GUI_Frame_CornerBottomSmallNotch_BottomNotchCorner_WindowCorner.png"};
            public static final String[] SPECIAL_TYPES = new String[]{"Diagonal Top-Left Slant", "Diagonal Angled Line Texture", "Bottom Small Notch"};
        }

        public static class AdaptiveTileSelection {
            public static final String ALGORITHM = "Neighbor-based tile variant selection";
            public static final String BENEFIT = "Automatically place correct tile piece without manual specification";

            public static final class ExampleLevel1Tilemap {
                public static final String LEVEL = "Industrial Zone Level 1";
                public static final String MAP_COORDINATE = "Position (10, 8) in level tilemap";
                public static final String NEIGHBORS = "Top: FLOOR_SOLID (Edge-Top)\nBottom: EMPTY (Interior)\nLeft: CORNER (Edge-Left)\nRight: FLOOR_SOLID (Edge-Top)";
                public static final String AUTO_SELECTION = "= CORNER-TL (matches top+left adjacency)";
            }
        }

        public static class TileAdjacencyRules {
            public static final String CONCEPT = "Tile Connection Mathematics";
            public static final String PURPOSE = "Ensure tiles only connect to valid neighbors";

            public static final class CornerBRAdjacency {
                public static final String TILE_TYPE = "CORNER-BOTTOM-RIGHT";
                public static final String TOP_NEIGHBOR_VALID = "EDGE-RIGHT or CORNER-TR";
                public static final String LEFT_NEIGHBOR_VALID = "EDGE-BOTTOM or CORNER-BL";
                public static final String PHYSICS = "Blocks movement from bottom and right sides";
                public static final String COLLISION_FACES = "Bottom face and Right face are solid";
            }

            public static final class CornerBLAdjacency {
                public static final String TILE_TYPE = "CORNER-BOTTOM-LEFT";
                public static final String TOP_NEIGHBOR_VALID = "EDGE-LEFT or CORNER-TL";
                public static final String RIGHT_NEIGHBOR_VALID = "EDGE-BOTTOM or CORNER-BR";
                public static final String PHYSICS = "Blocks movement from bottom and left sides";
                public static final String COLLISION_FACES = "Bottom face and Left face are solid";
            }

            public static final class EdgeBottomAdjacency {
                public static final String TILE_TYPE = "EDGE-BOTTOM (Ceiling)";
                public static final String LEFT_NEIGHBOR_VALID = "EDGE-BOTTOM or CORNER-BL";
                public static final String RIGHT_NEIGHBOR_VALID = "EDGE-BOTTOM or CORNER-BR";
                public static final String TOP_NEIGHBOR_VALID = "INTERIOR";
                public static final String PHYSICS = "Ceiling blocking upward movement";
                public static final String COLLISION_FACES = "Bottom face is solid";
            }

            public static final class EdgeRightAdjacency {
                public static final String TILE_TYPE = "EDGE-RIGHT (Wall)";
                public static final String TOP_NEIGHBOR_VALID = "EDGE-RIGHT or CORNER-TR";
                public static final String BOTTOM_NEIGHBOR_VALID = "EDGE-RIGHT or CORNER-BR";
                public static final String LEFT_NEIGHBOR_VALID = "INTERIOR";
                public static final String PHYSICS = "Vertical wall blocking right side";
                public static final String COLLISION_FACES = "Right face is solid";
            }

            public static final class InteriorAdjacency {
                public static final String TILE_TYPE = "INTERIOR (Full Solid)";
                public static final String[] ALL_NEIGHBORS_VALID = new String[]{"Any INTERIOR", "Any EDGE", "Any CORNER"};
                public static final String PHYSICS = "Full solid block (no visibility)";
                public static final String COLLISION_OPTIMIZATION = "Often NOT checked by physics (exterior edges handle it)";
                public static final boolean INTERIOR_VISIBLE = false;
            }

            public static final class EdgeLeftAdjacency {
                public static final String TILE_TYPE = "EDGE-LEFT (Wall)";
                public static final String TOP_NEIGHBOR_VALID = "EDGE-LEFT or CORNER-TL";
                public static final String BOTTOM_NEIGHBOR_VALID = "EDGE-LEFT or CORNER-BL";
                public static final String RIGHT_NEIGHBOR_VALID = "INTERIOR";
                public static final String PHYSICS = "Vertical wall blocking left side";
                public static final String COLLISION_FACES = "Left face is solid (player blocks here)";
                public static final boolean INTERIOR_VISIBLE = false;
            }

            public static final class CornerTRAdjacency {
                public static final String TILE_TYPE = "CORNER-TOP-RIGHT";
                public static final String LEFT_NEIGHBOR_VALID = "EDGE-TOP or CORNER-TL";
                public static final String BOTTOM_NEIGHBOR_VALID = "EDGE-RIGHT or CORNER-BR";
                public static final String PHYSICS = "Blocks movement from top and right sides";
                public static final String COLLISION_FACES = "Top face and Right face are solid";
                public static final boolean INTERIOR_VISIBLE = false;
            }

            public static final class EdgeTopAdjacency {
                public static final String TILE_TYPE = "EDGE-TOP (Primary Walkable)";
                public static final String LEFT_NEIGHBOR_VALID = "EDGE-TOP or CORNER-TL";
                public static final String RIGHT_NEIGHBOR_VALID = "EDGE-TOP or CORNER-TR";
                public static final String BOTTOM_NEIGHBOR_VALID = "INTERIOR or EDGE-BOTTOM";
                public static final String PHYSICS = "Primary walkable surface";
                public static final String COLLISION_FACES = "Top face is solid (player stands here)";
                public static final boolean INTERIOR_VISIBLE = false;
            }

            public static final class CornerTLAdjacency {
                public static final String TILE_TYPE = "CORNER-TOP-LEFT";
                public static final String RIGHT_NEIGHBOR_VALID = "EDGE-TOP or CORNER-TR";
                public static final String BOTTOM_NEIGHBOR_VALID = "EDGE-LEFT or CORNER-BL";
                public static final String PHYSICS = "Blocks movement from top and left sides";
                public static final String COLLISION_FACES = "Top face and Left face are solid";
                public static final boolean INTERIOR_VISIBLE = false;
            }
        }
    }

    public static class GUIButtonSystemProperties {

        public static class StandardUIIcons {
            public static final String UI_TYPE = "standard_icon";
            public static final String DIRECTORY = "Resources/industrial-zone/gui/3 Icons/Icons";
            public static final int TOTAL_ICONS = 40;
            public static final String ICON_PURPOSE = "Symbolic UI elements for menus and HUD";
            public static final String IMAGE_SIZE = "16\u00d716 to 32\u00d732 pixels (scalable)";
            public static final String[] ICON_FILES = new String[]{"Icon_Arrow_Up_Navigation_MenuScroll.png", "Icon_Arrow_Down_Navigation_MenuScroll.png", "Icon_Arrow_Left_Navigation_Movement.png", "Icon_Arrow_Right_Navigation_Movement.png", "Icon_Arrow_Back_Navigation_MenuReturn.png", "Icon_Check_Confirm_Success_MenuAccept.png", "Icon_Cross_Cancel_Close_MenuDeny.png", "Icon_Plus_Add_Increase_Inventory.png", "Icon_Minus_Remove_Decrease_Inventory.png", "Icon_Settings_Options_Config_Menu.png", "Icon_Menu_Hamburger_Options_Button.png", "Icon_Play_Start_Begin_Media.png", "Icon_Pause_Hold_Stop_Media.png", "Icon_Stop_End_Exit_Media.png", "Icon_FastForward_Skip_Next_Media.png", "Icon_Rewind_Previous_Back_Media.png", "Icon_Home_Main_Hub_Menu.png", "Icon_Save_Store_Write_File.png", "Icon_Load_Open_Read_File.png", "Icon_Exit_Quit_Close_App.png", "Icon_Help_Question_Support_Info.png", "Icon_Info_Details_Information_HUD.png", "Icon_Lock_Secure_Password_Security.png", "Icon_Unlock_UnSecure_Open_Security.png", "Icon_User_Profile_Person_Account.png", "Icon_Users_Group_Team_Multiplayer.png", "Icon_Chat_Talk_Speech_Message.png", "Icon_Email_Mail_Message_Inbox.png", "Icon_Phone_Call_Contact_Communication.png", "Icon_Download_Receive_Import_File.png", "Icon_Alert_Attention_Warning_Danger.png", "Icon_Warning_Caution_Risk_Hazard.png", "Icon_Info_Notification_Message_Log.png", "Icon_Help_Support_Question_FAQ.png", "Icon_Star_Favorite_Rating_Quality.png", "Icon_Heart_Love_Health_Status.png", "Icon_Sword_Attack_Combat_Battle.png", "Icon_Shield_Defense_Protection_Guard.png", "Icon_Battery_Power_Energy_Status.png", "Icon_Volume_Audio_Sound_Speaker.png"};
            public static final String[] ICON_DESCRIPTIONS = new String[]{"Pointing up arrow", "Pointing down arrow", "Pointing left arrow", "Pointing right arrow", "Back/Return arrow", "Checkmark", "X/Close mark", "Plus/Add symbol", "Minus/Remove symbol", "Settings/Gear", "Menu/Hamburger", "Play/Start button", "Pause symbol", "Stop symbol", "Fast-forward symbol", "Rewind symbol", "Home/House icon", "Save/Floppy disk", "Load/Open folder", "Exit/Door out", "Help/Question mark", "Info/Circle i", "Padlock/Lock", "Open padlock/Unlock", "Person/User", "Multiple people", "Speech bubble", "Envelope/Email", "Phone", "Download arrow", "Alert/Exclamation", "Warning/Triangle", "Info notification", "Question mark help", "Star shape", "Heart shape", "Sword/Blade", "Shield shape", "Battery indicator", "Speaker/Volume"};

            public static CategorySpriteRegistry loadUIIcons() {
                CategorySpriteRegistry categorySpriteRegistry = new CategorySpriteRegistry("standard_ui_icons", DIRECTORY);
                if (categorySpriteRegistry.load()) {
                    AnimationAndSpriteLoader.log("\u2713 Loaded 40 standard UI icons from Resources/industrial-zone/gui/3 Icons/Icons");
                    AnimationAndSpriteLoader.log("  Icon categories: Navigation, Actions, Media, System, Communication, Alerts, Misc");
                }
                return categorySpriteRegistry;
            }
        }

        public static class HUDBarSystem {
            public static final String UI_TYPE = "hud_bar";
            public static final String DIRECTORY = "Resources/industrial-zone/gui/2 Bars";
            public static final int TOTAL_STATES = 13;
            public static final String RENDERING_FORMAT = "Static PNG, no animation";
            public static final String HUD_POSITION = "Top-left corner (standard location)";

            public static final class EnergyBarStates {
                public static final String BAR_TYPE = "energy_bar";
                public static final String COLOR = "Blue/Cyan #00DDFF";
                public static final String PURPOSE = "Display player energy/mana percentage";
                public static final String[] ENERGY_BAR_FILES = new String[]{"09_GUI_Bar_EnergyBar_Full100pct_BlueCyanFillDarkFrame_HUD.png", "10_GUI_Bar_EnergyBar_80pct_BlueFill_HUD.png", "11_GUI_Bar_EnergyBar_60pct_CyanFill_HUD.png", "12_GUI_Bar_EnergyBar_40pct_LightBlueFill_HUD.png", "13_GUI_Bar_EnergyBar_20pct_OrangeFlashing_HUD.png", "14_GUI_Bar_EnergyBar_Empty0pct_GreyEmpty_HUD.png"};
                public static final int[] PERCENTAGE_VALUES = new int[]{100, 80, 60, 40, 20, 0};
                public static final String[] STATE_NAMES = new String[]{"Full", "Good", "Okay", "Low", "Critical", "Empty"};
            }

            public static final class HealthBarStates {
                public static final String BAR_TYPE = "health_bar";
                public static final String COLOR = "Red/Orange #FF6B35";
                public static final String PURPOSE = "Display player health percentage";
                public static final String[] HEALTH_BAR_FILES = new String[]{"01_GUI_Bar_HealthBar_Full100pct_RedOrangeFillDarkFrame_HUD.png", "02_GUI_Bar_HealthBar_80pct_RedOrangeFill_HUD.png", "03_GUI_Bar_HealthBar_60pct_OrangeFill_HUD.png", "04_GUI_Bar_HealthBar_40pct_OrangeFill_HUD.png", "05_GUI_Bar_HealthBar_20pct_DarkRedFill_HUD.png", "06_GUI_Bar_HealthBar_Critical5pct_RedFlashing_HUD.png", "07_GUI_Bar_HealthBar_Empty0pct_GreyEmpty_HUD.png"};
                public static final int[] PERCENTAGE_VALUES = new int[]{100, 80, 60, 40, 20, 5, 0};
                public static final String[] STATE_NAMES = new String[]{"Full", "Good", "Okay", "Low", "Critical", "Ultra-Critical", "Dead"};

                public static BufferedImage getHealthBar(int n) {
                    if (n >= 100) {
                        return null;
                    }
                    if (n >= 80) {
                        return null;
                    }
                    if (n >= 60) {
                        return null;
                    }
                    if (n >= 40) {
                        return null;
                    }
                    if (n >= 20) {
                        return null;
                    }
                    if (n > 0) {
                        return null;
                    }
                    return null;
                }
            }
        }

        public class HollowVariants {
            public static final String[] HOLLOW_VARIANT_FILES = new String[]{"GUI_Button_State_Variant02_11.png", "GUI_Button_State_Variant02_12.png", "GUI_Button_State_Variant02_13.png", "GUI_Button_State_Variant02_14.png", "GUI_Button_State_Variant02_15.png", "GUI_Button_State_Variant02_16.png", "GUI_Button_State_Variant02_17.png", "GUI_Button_State_Variant02_18.png", "GUI_Button_State_Variant02_19.png", "GUI_Button_State_Variant02_20.png"};
            public static final String[] HOLLOW_DESCRIPTIONS = new String[]{"Blue Hollow - Secondary audio options", "Green Hollow - Advanced graphics settings", "Orange Hollow - Performance tuning", "Red Hollow - Safety confirmation steps", "Purple Hollow - Experimental features", "Yellow Hollow - Optional warnings", "Cyan Hollow - Accessibility fine-tuning", "Pink Hollow - Theme customization details", "Lime Hollow - Upcoming features preview", "White Hollow - Display configuration options"};

            public HollowVariants(GUIButtonSystemProperties gUIButtonSystemProperties) {
            }
        }

        public static class ButtonStateVariants {
            public static final String UI_TYPE = "toggle_switch";
            public static final String DIRECTORY = "Resources/industrial-zone/gui/3 Icons/Buttons2";
            public static final int TOTAL_VARIANTS = 10;
            public static final String STATE_PURPOSE = "Colored toggle switches for binary on/off controls";
            public static final String SWITCH_PATTERN = "Each variant: OFF state (grey) | ON state (colored)";
            public static final String[] SWITCH_TYPES = new String[]{"Audio Toggle", "Graphics Toggle", "Difficulty Toggle", "Subtitle Toggle", "Vibration Toggle", "Motion Toggle", "Effects Toggle", "Music Toggle", "UI Scale Toggle", "FPS Toggle"};
            public static final String[] STATE_VARIANT_FILES = new String[]{"GUI_Button_State_Variant02_01.png", "GUI_Button_State_Variant02_02.png", "GUI_Button_State_Variant02_03.png", "GUI_Button_State_Variant02_04.png", "GUI_Button_State_Variant02_05.png", "GUI_Button_State_Variant02_06.png", "GUI_Button_State_Variant02_07.png", "GUI_Button_State_Variant02_08.png", "GUI_Button_State_Variant02_09.png", "GUI_Button_State_Variant02_10.png"};
            public static final String[] VARIANT_DESCRIPTIONS = new String[]{"Blue Toggle - Audio/Sound settings", "Green Toggle - Graphics quality", "Orange Toggle - Performance mode", "Red Toggle - Danger/Adult content", "Purple Toggle - Special features", "Yellow Toggle - Warning/caution state", "Cyan Toggle - Accessibility options", "Pink Toggle - Color/theme customization", "Lime Toggle - New/unlocked features", "White Toggle - Display/screen options"};

            public static SingleSpriteLoader loadToggleSwitch(int n) {
                if (n < 1 || n > 10) {
                    AnimationAndSpriteLoader.logError("Toggle switch variant out of range: " + n);
                    return null;
                }
                String string = String.format("GUI_Button_State_Variant02_%02d.png", n);
                SingleSpriteLoader singleSpriteLoader = new SingleSpriteLoader("toggle_switch_" + n, DIRECTORY);
                if (singleSpriteLoader.load()) {
                    AnimationAndSpriteLoader.log("\u2713 Loaded toggle switch variant " + n + " (" + SWITCH_TYPES[n - 1] + "): " + string);
                }
                return singleSpriteLoader;
            }
        }

        public static class ButtonColorMaps {
            public static final String UI_TYPE = "button_animation_spritesheet";
            public static final String DIRECTORY = "Resources/industrial-zone/gui/6 Buttons";
            public static final int TOTAL_VARIANTS = 10;
            public static final String VARIANT_PURPOSE = "Animated button frame sequences for interactive button rendering";
            public static final String IMAGE_FORMAT = "PNG vertical sprite sheet (4-6 frames stacked)";
            public static final String ANIMATION_TYPE = "Press animation sequence";
            public static final String[] COLOR_MAP_FILES = new String[]{"GUI_ButtonColorMap_Variant_01.png", "GUI_ButtonColorMap_Variant_02.png", "GUI_ButtonColorMap_Variant_03.png", "GUI_ButtonColorMap_Variant_04.png", "GUI_ButtonColorMap_Variant_05.png", "GUI_ButtonColorMap_Variant_06.png", "GUI_ButtonColorMap_Variant_07.png", "GUI_ButtonColorMap_Variant_08.png", "GUI_ButtonColorMap_Variant_09.png", "GUI_ButtonColorMap_Variant_10.png"};
            public static final String[] VARIANT_DESCRIPTIONS = new String[]{"Standard Blue Button - Smooth press animation", "Green Success Button - Confirm action animation", "Orange Warning Button - Alert state animation", "Red Danger Button - Destructive action animation", "Purple Special Button - Menu navigation animation", "Navy Background Button - Background action animation", "Cyan Highlight Button - Focus state animation", "Gold Premium Button - Special reward animation", "Grey Disabled Button - Inactive state animation", "Pink Neon Button - Attention-grabbing animation"};

            public static VerticalSpritesheetLoader loadAnimatedButton(int n) {
                if (n < 1 || n > 10) {
                    AnimationAndSpriteLoader.logError("Button animation variant out of range: " + n);
                    return null;
                }
                String string = String.format("GUI_ButtonColorMap_Variant_%02d.png", n);
                VerticalSpritesheetLoader verticalSpritesheetLoader = new VerticalSpritesheetLoader("button_animation_" + n, DIRECTORY, 128, 256, 4);
                if (verticalSpritesheetLoader.load()) {
                    AnimationAndSpriteLoader.log("\u2713 Loaded animated button variant " + n + ": " + string);
                    AnimationAndSpriteLoader.log("  Animation frames: " + verticalSpritesheetLoader.getFrameCount() + " (Idle \u2192 Hover \u2192 Press \u2192 Release)");
                }
                return verticalSpritesheetLoader;
            }
        }
    }

    public static class StaticPropProperties {

        public static class SignProps {
            public static final String PROP_TYPE = "sign";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects";
            public static final String[] SIGN_FILES = new String[]{"Prop_Sign_RedLollipopQuestionMark_HintOrMysteryPointerSign_DirectionalA.png", "Prop_Sign_RedWarningTriangle_HazardAheadWarning_FloorPoleSign_DirectionalA.png"};
        }

        public static class MopProps {
            public static final String PROP_TYPE = "mop";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects";
            public static final String FILE = "Prop_Mop_WoodenHandleBrownHead_FloorLeaningDeco_JanitorialProp.png";
        }

        public static class LockerProps {
            public static final String PROP_TYPE = "locker";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects";
            public static final String[] LOCKER_FILES = new String[]{"Prop_Locker_BlueDoubleDoor_TallMetal_WallPlacedDeco_IndustrialLock.png", "Prop_Locker_RedDoubleDoor_TallMetal_WallPlacedDeco_IndustrialLock.png"};
        }

        public static class LadderProps {
            public static final String PROP_TYPE = "ladder";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects";
            public static final String[] LADDER_FILES = new String[]{"Prop_Ladder_ShortHorizontalRung_BlueGreCrossbar_PlatformConnector_Short.png", "Prop_Ladder_TallFullHeight_BlueGreyeShaftWallClimb_ClimbableA.png"};
        }

        public static class FlagProps {
            public static final String PROP_TYPE = "flag";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects";
            public static final String FILE = "Prop_Flag_TallRedBannerPole_GoldEmblem_CheckpointOrBossGateLandmark.png";
        }

        public static class FireExtinguisherProps {
            public static final String PROP_TYPE = "fire_extinguisher";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects";
            public static final String[] EXTINGUISHER_FILES = new String[]{"Prop_FireExtinguisher_ActiveSprayingFlame_InUseDeco_SafetyPropActive.png", "Prop_FireExtinguisher_SmallKnocked_Over_FloorDebris_SafetyPropDamaged.png"};
        }

        public static class FenceProps {
            public static final String PROP_TYPE = "fence";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects";
            public static final int FENCE_VARIANTS = 3;
            public static final String[] FENCE_FILES = new String[]{"Prop_Fence_SingleLargeFrame_Gate_Shape_ZoneEntranceDeco_FenceGateFrame.png", "Prop_Fence_ThreeRedPostsWire_WidSegment_ZoneDividerDeco_FenceSegmentB.png", "Prop_Fence_TwoRedPostsWire_ShortSegment_ZoneDividerDeco_FenceSegmentA.png"};
        }

        public static class BucketProps {
            public static final String PROP_TYPE = "bucket";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects";
            public static final String FILE = "Prop_Bucket_SmallBlueWithHandle_FloorClutter_AmbientDeco_JanitorialProp.png";
        }

        public static class BoxCrateProps {
            public static final String PROP_TYPE = "box_crate";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects";
            public static final int BOX_VARIANTS = 4;
            public static final String[] BOX_FILES = new String[]{"Prop_Box_CardboardLight_MediumLight_FloorStackable_Cardboard Variant.png", "Prop_Box_CardboardTaped_Medium_Brown_FloorStackable_CardboardVariant.png", "Prop_Crate_DarkWoodSealed_Small_Square_FloorStackable_StandardCrate.png", "Prop_Crate_RedLockedClassp_Secure_FloorStackable_LockedCrate.png"};
        }

        public static class Benchprops {
            public static final String PROP_TYPE = "bench";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects";
            public static final String[] BENCH_FILES = new String[]{"Prop_Bench_BlueMetall_FlatTopWork_FloorPlacedDeco_IndustrialFurniture.png", "Prop_Bench_WallPlacedDeco_IndusstrialFurniture.png"};
        }

        public static class Barrelprops {
            public static final String PROP_TYPE = "barrel";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects";
            public static final String[] BARREL_FILES = new String[]{"Prop_Barrel_SmallDamagedTipped_RedWithSpill_FloorHazardDeco_DamagedVariant.png", "Prop_Barrel_TallDarkRed_WornIndustrial_FloorDecorOrPushable_TallVariaIndel01.png"};
        }
    }

    public static class InteractiveObjectProperties {

        public static class DecoScreenRedMonitor {
            public static final String OBJECT_NAME = "Deco Screen Red";
            public static final String OBJECT_TYPE = "deco_screen_red";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects";
            public static final String COLOR = "Alert Red #FF4444";
            public static final int TOTAL_FRAMES = 4;
            public static final int TIMING_MS = 150;
            public static final int DURATION_MS = 600;
            public static final boolean LOOPS = true;
            public static final String ANIMATION_PATTERN = "HorizontalSpritesheet (4Frames1Row)";
            public static final int FRAME_WIDTH = 128;
            public static final int FRAME_HEIGHT = 80;
            public static final String FILE = "Anim_Deco_Screen2_4Frames1Row_RedBlueMonitorFlicker_WallPanelAltDeco_Loop150ms.png";
        }

        public static class DecoScreenBlueMonitor {
            public static final String OBJECT_NAME = "Deco Screen Blue";
            public static final String OBJECT_TYPE = "deco_screen_blue";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects";
            public static final String COLOR = "Tech Blue #0066CC";
            public static final int TOTAL_FRAMES = 4;
            public static final int TIMING_MS = 150;
            public static final int DURATION_MS = 600;
            public static final boolean LOOPS = true;
            public static final String ANIMATION_PATTERN = "HorizontalSpritesheet (4Frames1Row)";
            public static final int FRAME_WIDTH = 128;
            public static final int FRAME_HEIGHT = 80;
            public static final String FILE = "Anim_Deco_Screen1_4Frames1Row_BlueMonitorFlicker_WallPanelTechDeco_Loop150ms.png";
        }

        public static class CollectibleCard {
            public static final String OBJECT_NAME = "Card Collectible";
            public static final String OBJECT_TYPE = "collectible_card";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects";
            public static final String COLOR = "White/Blue #FFFFFF/#0066FF";
            public static final int TOTAL_FRAMES = 6;
            public static final int TIMING_MS = 80;
            public static final int DURATION_MS = 480;
            public static final boolean LOOPS = true;
            public static final String ANIMATION_PATTERN = "HorizontalSpritesheet (6Frames1Row)";
            public static final int FRAME_WIDTH = 64;
            public static final int FRAME_HEIGHT = 64;
            public static final String FILE = "Anim_Collectible_Card_6Frames1Row_WhiteBlueSpinningFloat_PickupItem_Loop80ms.png";
        }

        public static class CollectibleMoney {
            public static final String OBJECT_NAME = "Money Collectible";
            public static final String OBJECT_TYPE = "collectible_money";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects";
            public static final String COLOR = "Green #90EE90";
            public static final int TOTAL_FRAMES = 6;
            public static final int TIMING_MS = 80;
            public static final int DURATION_MS = 480;
            public static final boolean LOOPS = true;
            public static final String ANIMATION_PATTERN = "HorizontalSpritesheet (6Frames1Row)";
            public static final int FRAME_WIDTH = 48;
            public static final int FRAME_HEIGHT = 48;
            public static final String FILE = "Anim_Collectible_Money_6Frames1Row_GreenBanknotesSpinFlip_CurrencyPickup_Loop80ms.png";
        }
    }

    public static class ParallaxBackgroundSystem {
        public static final String SYSTEM_NAME = "PARALLAX BACKGROUND RENDERING";
        public static final String DESCRIPTION = "8-phase pipeline for layered background scrolling";

        public static class ContinuousGameplayLoop {
            public static final String DESCRIPTION = "Complete parallax rendering during gameplay";
            public static final String WORKFLOW = "At Level Start (Once):\n  \u2192 Phase 1: Initialize - Load all layer images\n  \u2192 Phase 2: Select variant (Day vs Night for Level 2 only)\n\nEvery Frame (60 FPS, repeated infinitely):\n  \u2192 Phase 3: Calculate scroll offsets based on new player position\n  \u2192 Phase 4: Render all 5 layers back-to-front\n  \u2192 Phase 5: Apply overlay blend if transitioning (Level 2 only)\n  \u2192 Phase 6: Update offsets for next frame\n  \u2192 Phase 7: Wrap layers at edges for seamless scrolling\n  \u2192 Phase 8: Composite final background to display\n\nResult: Smooth scrolling parallax depth effect with seamless wrapping";

            public static final class Level2Optimizations {
                public static final String LEVEL = "Power Station";
                public static final String OPTIMIZATION = "Partial overhead only during transitions (45sec windows)";
                public static final String FRAME_TIME = "~15ms (includes blending math when active)";
            }

            public static final class Level1Optimizations {
                public static final String LEVEL = "Industrial Zone";
                public static final String OPTIMIZATION = "No day/night blending overhead";
                public static final String FRAME_TIME = "~13-14ms (faster than Level 2)";
            }
        }

        public static class Phase8FinalComposite {
            public static final String PHASE_NAME = "PHASE 8: FINAL COMPOSITE & RENDER";
            public static final String DESCRIPTION = "Blend all layers into final displayable background";

            public static final class CompositeProcess {
                public static final String RENDER_PIPELINE = "1. CREATE empty canvas (screen resolution)\n2. FOR EACH layer (back to front):\n   - Get calculated offset\n   - Draw layer at offset position\n   - If wrapping: draw layer again at (offset + width)\n3. IF day/night transition active:\n   - Apply overlay blending with current alpha\n4. DISPLAY final composite to screen";
                public static final String PERFORMANCE = "Target FPS: 60 frames per second\nTime per frame: 16.67ms\n5 layers \u00d7 3ms = 15ms (leaves 1.67ms headroom)\nOptimization: Pre-loaded PNG files minimize CPU overhead";
            }
        }

        public static class Phase7LayerWrapping {
            public static final String PHASE_NAME = "PHASE 7: LAYER WRAPPING";
            public static final String TRIGGER = "When scroll offset > layer width";
            public static final String ACTION = "Draw layer twice: original + offset repeat";

            public static final class WrappingExample {
                public static final String SCENARIO = "Player at X=5000, Parallax 0.4x, Layer width 1920px";
                public static final int RAW_OFFSET = 2000;
                public static final int WRAPPED = 80;
                public static final String DRAW_LOGIC = "Tile 1: Draw at position -80px\nTile 2: Draw at position -80 + 1920 = 1840px\nResult: Seamless visual transition, player never sees edge";
            }
        }

        public static class Phase6FactorUpdate {
            public static final String PHASE_NAME = "PHASE 6: PARALLAX FACTOR UPDATE";
            public static final String FREQUENCY = "Every frame (60 FPS = every 16.67ms)";

            public static final class UpdateProcess {
                public static final String STEP1 = "GET current camera X (player position)";
                public static final String STEP2 = "FOR each layer: newOffset = cameraX \u00d7 parallaxFactor";
                public static final String STEP3 = "WRAP: newOffset = newOffset % layerWidth (seamless)";
                public static final String STEP4 = "UPDATE layer render position";
                public static final String STEP5 = "NEXT FRAME: Repeat with new position";
                public static final String SMOOTH_EXAMPLE = "Frame 1: playerX=100, far_layer(0.25x) offset=25px\nFrame 2: playerX=105, far_layer offset=26.25px\nFrame 3: playerX=110, far_layer offset=27.5px\nResult: Smooth continuous scrolling by small increments";
            }
        }

        public static class Phase5OverlayBlending {
            public static final String PHASE_NAME = "PHASE 5: OVERLAY BLENDING";
            public static final String APPLIES_TO = "Level 2 (Power Station) ONLY";

            public static final class BlendingMechanic {
                public static final String OVERLAY_FILE = "Overlay.png (blending mask)";
                public static final float TRANSITION_DURATION_SECONDS = 45.0f;
                public static final String BLEND_FORMULA = "finalColor = dayColor \u00d7 (1 - alpha) + nightColor \u00d7 alpha\nwhere alpha goes from 0.0 \u2192 1.0 over 45 seconds\nResult: Smooth gradient transition across entire screen";

                public static final class SunsetTransition {
                    public static final String TIME_WINDOW = "5:30 PM to 6:15 PM";
                    public static final String DIRECTION = "Day \u2192 Night (alpha: 1.0 \u2192 0.0)";
                }

                public static final class SunriseTransition {
                    public static final String TIME_WINDOW = "5:45 AM to 6:30 AM";
                    public static final String DIRECTION = "Night \u2192 Day (alpha: 0.0 \u2192 1.0)";
                }
            }
        }

        public static class Phase4LayerRendering {
            public static final String PHASE_NAME = "PHASE 4: LAYER RENDERING (BACK TO FRONT)";
            public static final String PRINCIPLE = "Painters Algorithm - render distant objects first";

            public static final class RenderOrderLevel2 {
                public static final String ORDER = "STEP 1: Sky (0.0x) - Static day/night background\nSTEP 2: Far Tower (0.15x) - Slow scroll\nSTEP 3: Mid Tower (0.30x) - Medium scroll\nSTEP 4: Near Structure (0.45x) - Faster scroll\nSTEP 5: Foreground (0.60x) - Fastest scroll (closest)";
            }

            public static final class RenderOrderLevel1 {
                public static final String ORDER = "STEP 1: Sky Base (0.0x) - Draw first, stays static, fills background\nSTEP 2: Fractal Tree (0.1x) - Draw second, very slow scroll\nSTEP 3: Far Factory (0.25x) - Draw third, slow scroll\nSTEP 4: Mid Factory (0.35x) - Draw fourth, medium scroll\nSTEP 5: Near Factory (0.55x) - Draw last, fastest scroll (foreground)";
            }
        }

        public static class Phase3ScrollCalculation {
            public static final String PHASE_NAME = "PHASE 3: SCROLL OFFSET CALCULATION";
            public static final String FORMULA = "layerScrollOffset = playerX * parallaxFactor";

            public static final class ExampleLevel2 {
                public static final String SCENARIO = "Player at X=4000, Near Structure (0.45x)";
                public static final int PLAYER_X = 4000;
                public static final float PARALLAX_FACTOR = 0.45f;
                public static final int RAW_SCROLL = 1800;
                public static final int LAYER_WIDTH = 1920;
                public static final int WRAPPED_SCROLL = 1800;
                public static final String RESULT = "Near layer scrolls 1800px (within bounds)";
            }

            public static final class ExampleLevel1 {
                public static final String SCENARIO = "Player at X=2000, Mid Factory layer (0.35x)";
                public static final int PLAYER_X = 2000;
                public static final float PARALLAX_FACTOR = 0.35f;
                public static final int RAW_SCROLL = 700;
                public static final int LAYER_WIDTH = 1920;
                public static final int WRAPPED_SCROLL = 700;
                public static final String RESULT = "Mid layer scrolls 700px to the left";
            }
        }

        public static class Phase2VariantSelection {
            public static final String PHASE_NAME = "PHASE 2: VARIANT SELECTION";

            public static final class Level2VariantLogic {
                public static final String LEVEL = "Power Station Level 2";
                public static final String LOGIC = "SELECT based on game time";
                public static final String DAY_PERIOD = "6:00 AM to 6:00 PM \u2192 Use Day variant";
                public static final String NIGHT_PERIOD = "6:00 PM to 6:00 AM \u2192 Use Night variant";
                public static final String TRANSITION_AT_6AM = "Sunrise: Fade from Night \u2192 Day over 45 seconds";
                public static final String TRANSITION_AT_6PM = "Sunset: Fade from Day \u2192 Night over 45 seconds";
            }

            public static final class Level1VariantLogic {
                public static final String LEVEL = "Industrial Zone Level 1";
                public static final String LOGIC = "SKIP PHASE 2: Use single consistent daylight variant always";
                public static final String REASON = "Level 1 has no time-of-day system; always displays industrial daylight";
            }
        }

        public static class Phase1Initialization {
            public static final String PHASE_NAME = "PHASE 1: LAYER ASSET INITIALIZATION";

            public static final class Level2LoadSequence {
                public static final String LEVEL = "Power Station Level 2";
                public static final int LAYER_COUNT_PER_VARIANT = 5;
                public static final int TOTAL_VARIANT_SETS = 2;
                public static final String LOAD_TYPE = "Dual Variant with Overlay Blending";
                public static final String[] DAY_LAYERS = new String[]{"Sky (0.0x - static day sky)", "Far Tower (0.15x - daylight)", "Mid Tower (0.30x - daylight)", "Near Structure (0.45x - daylight)", "Foreground (0.60x - daylight)"};
                public static final String[] NIGHT_LAYERS = new String[]{"Sky (0.0x - static night sky)", "Far Tower (0.15x - nighttime)", "Mid Tower (0.30x - nighttime)", "Near Structure (0.45x - nighttime)", "Foreground (0.60x - nighttime)"};
                public static final String OVERLAY_ASSET = "Overlay.png (blending mask for smooth transitions)";
            }

            public static final class Level1LoadSequence {
                public static final String LEVEL = "Industrial Zone Level 1";
                public static final int LAYER_COUNT = 5;
                public static final String LOAD_TYPE = "Single Static Variant";
                public static final String[] LAYERS = new String[]{"Sky Base (0.0x parallax - static)", "Fractal Tree (0.1x parallax - very slow)", "Far Factory (0.25x parallax - slow)", "Mid Factory (0.35x parallax - medium)", "Near Factory (0.55x parallax - fast)"};
            }
        }
    }

    public static class TilesetProperties {

        public static class CeilingTiles {
            public static final String TILE_TYPE = "ceiling_tile";
            public static final String TILE_FAMILY = "Ceiling Elements";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/power-station-level-2/1 Tiles";
            public static final int CEILING_COUNT = 4;
            public static final String[] CEILING_FILES = new String[]{"61_Solid_LightGreyBlue_FlatStructural_CeilingTileA.png", "62_Solid_LightGreyBlue_FlatStructural_CeilingTileB.png", "63_Solid_LightGreyBlue_FlatStructural_CeilingTileC.png", "64_Solid_LightGreyBlue_FlatStructural_CeilingTileD.png"};
        }

        public static class DoorGateElements {
            public static final String TILE_TYPE = "door_gate";
            public static final String TILE_FAMILY = "Interactive Elements";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/power-station-level-2/1 Tiles";
            public static final int DOOR_COUNT = 8;
            public static final String[] DOOR_FILES = new String[]{"53_Panel_GreyTech_InsetBevel_DarkPlatformD.png", "54_Panel_GreyTech_InsetBevel_DarkPlatformE.png", "55_Panel_GreyTech_InsetBevel_DarkPlatformF.png", "56_Panel_GreyTech_InsetBevel_DarkPlatformG.png", "57_Door_GateFrame_PanelStyle_ClosedDoorA.png", "58_Door_GateFrame_PanelStyle_ClosedDoorB.png", "59_Door_GateFrame_PanelStyle_ClosedDoorC.png", "60_Door_GateFrame_PanelStyle_ClosedDoorD.png"};
        }

        public static class EdgeBorderElements {
            public static final String TILE_TYPE = "edge_border";
            public static final String TILE_FAMILY = "Border Details";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/power-station-level-2/1 Tiles";
            public static final int EDGE_COUNT = 8;
            public static final String[] EDGE_FILES = new String[]{"45_Solid_FlatBlue_FullBlock_CeilingOrPlatformA.png", "46_Solid_FlatBlue_FullBlock_CeilingOrPlatformB.png", "47_Panel_InsetDetail_BlueTech_WallInlayA.png", "48_Panel_InsetDetail_BlueTech_WallInlayB.png", "49_Solid_MagentaPurple_FullBlock_AccentStructure.png", "50_Panel_GreyTech_InsetBevel_DarkPlatformA.png", "51_Panel_GreyTech_InsetBevel_DarkPlatformB.png", "52_Panel_GreyTech_InsetBevel_DarkPlatformC.png"};
        }

        public static class PanelStructures {
            public static final String TILE_TYPE = "panel_structure";
            public static final String TILE_FAMILY = "Architectural Elements";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/power-station-level-2/1 Tiles";
            public static final int PANEL_COUNT = 22;
            public static final String[] PANEL_FILES = new String[]{"23_Edge_PanelBorder_BluePurple_WallEdgeTop.png", "24_Edge_PanelBorder_DarkBlue_WallEdgeBottom.png", "25_Edge_PanelBorder_MidBlue_WallEdgeLeft.png", "26_Edge_PanelBorder_NavyTone_WallEdgeRight.png", "27_Edge_PanelBorder_SlatePurple_WallEdgeVariantA.png", "28_Edge_PanelBorder_IndigoTone_WallEdgeVariantB.png", "29_Solid_PurpleBrick_FullBlock_HeavyWallA.png", "30_Solid_PurpleBrick_FullBlock_HeavyWallB.png", "31_Solid_PurpleBrick_FullBlock_HeavyWallC.png", "32_Solid_PurpleBrick_FullBlock_HeavyWallD.png", "33_Slope_DiagonalCut_TopLeftTriangle_RampUpLeft.png", "34_Slope_DiagonalCut_TopRightTriangle_RampUpRight.png", "35_Panel_MixedEdge_BlueGrey_StructuralDetailA.png", "36_Panel_MixedEdge_BlueGrey_StructuralDetailB.png", "37_Panel_MixedEdge_BlueGrey_StructuralDetailC.png", "38_Panel_MixedEdge_BlueGrey_StructuralDetailD.png", "39_Panel_MixedEdge_BlueGrey_StructuralDetailE.png", "40_Panel_MixedEdge_BlueGrey_StructuralDetailF.png", "41_Slope_DarkDiagonal_CutA_SteepRampLeft.png", "42_Slope_DarkDiagonal_CutB_SteepRampRight.png", "43_Slope_DarkDiagonal_CutC_ShallowRampLeft.png", "44_Slope_DarkDiagonal_CutD_ShallowRampRight.png"};
        }

        public static class BrickSmallUnits {
            public static final String TILE_TYPE = "brick_small";
            public static final String TILE_FAMILY = "Modular Construction";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/power-station-level-2/1 Tiles";
            public static final int UNIT_VARIANTS = 6;
            public static final String[] UNIT_FILES = new String[]{"17_Brick_SmallUnit_BluePurple_WallFillTileA.png", "18_Brick_SmallUnit_DarkNavy_WallFillTileB.png", "19_Brick_SmallUnit_MidBlue_WallFillTileC.png", "20_Brick_SmallUnit_SlatePurple_WallFillTileD.png", "21_Brick_SmallUnit_DeepIndigo_WallFillTileE.png", "22_Brick_SmallUnit_CoolGrey_WallFillTileF.png"};
        }

        public static class HorizontalStripeBrickPanels {
            public static final String TILE_TYPE = "brick_horizontal_stripe";
            public static final String TILE_FAMILY = "Panel Construction";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/power-station-level-2/1 Tiles";
            public static final String DESIGN = "Horizontal stripe pattern with solid fill";
            public static final int TOTAL_COLOR_VARIANTS = 15;
            public static final String[] COLOR_SCHEMES = new String[]{"Blue Purple #6A4C93", "Deep Navy #0F0E1F", "Dark Navy #1A1A2E", "Light Blue #4A7BA7", "Deep Navy Lavender #2D1B5E", "Slate Purple #2D2D5F", "Steel Blue #2C3E50", "Cool Grey #3D3D5D", "Indigo Tone #3D0066", "Soft Lavender #4A4A7A", "Muted Teal #2D5F6F", "Night Blue #1A3A52", "Dusk Purple #3D2D5A", "Tech Blue #004488", "Deep Slate #2D2D3D"};
            public static final String[] TILE_FILES = new String[]{"02_Panel_HorizStripeBrick_BluePurple_SolidFloorVariantB.png", "03_Panel_HorizStripeBrick_MidBlue_SolidFloorVariantC.png", "04_Panel_HorizStripeBrick_DarkBlue_SolidFloorVariantD.png", "05_Panel_HorizStripeBrick_DarkPurple_SolidFloorVariantE.png", "06_Panel_HorizStripeBrick_DeepNavy_SolidFloorVariantF.png", "07_Panel_HorizStripeBrick_LightBlue_SolidFloorVariantG.png", "08_Panel_HorizStripeBrick_MediumBlue_SolidFloorVariantH.png", "09_Panel_HorizStripeBrick_SlatePurple_SolidFloorVariantI.png", "10_Panel_HorizStripeBrick_SteelBlue_SolidFloorVariantJ.png", "11_Panel_HorizStripeBrick_CoolGrey_SolidFloorVariantK.png", "12_Panel_HorizStripeBrick_IndigoTone_SolidFloorVariantL.png", "13_Panel_HorizStripeBrick_SoftLavender_SolidFloorVariantM.png", "14_Panel_HorizStripeBrick_DuskBlue_SolidFloorVariantN.png", "15_Panel_HorizStripeBrick_NightBlue_SolidFloorVariantO.png", "16_Panel_HorizStripeBrick_MutedTeal_SolidFloorVariantP.png"};
        }
    }

    public static class ParallaxRenderingPipeline {

        public static class CompleteParallaxWorkflow {
            public static final String DOCUMENTATION = "Complete parallax rendering pipeline for both levels";
            public static final String[] FULL_SEQUENCE = new String[]{"PHASE 1: Initialize - Load all 5 layer images into memory", "PHASE 2: Select - Choose Day/Night variant (Level 2 only) or skip (Level 1)", "PHASE 3: Calculate - Compute scroll offset for each layer (parallaxFactor \u00d7 playerX)", "PHASE 4: Render - Draw layers back-to-front with calculated offsets", "PHASE 5: Blend - Apply overlay blending if transitioning day/night (Level 2 only)", "PHASE 6: Update - Recalculate offsets every frame (60 FPS)", "PHASE 7: Wrap - Handle seamless layer repetition at edges", "PHASE 8: Composite - Combine all layers into final background"};
            public static final String CONTINUOUS_GAMEPLAY = "During actual gameplay:\n- Phases 3-8 repeat EVERY FRAME\n- Phase 1-2 happen ONCE at level load\n- Player position updates continuously\n- Parallax offsets smooth and responsive\n- Creates depth illusion through layer speed variance\n- Seamless infinite scrolling in both directions\n";

            public static final class ComparisonSummary {
                public static final String COMPARISON_TITLE = "LEVEL 1 vs LEVEL 2 PARALLAX SYSTEMS";
                public static final String COMPARISON_TABLE = "\u250c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u252c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u252c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2510\n\u2502 Feature             \u2502 Level 1 (Industrial) \u2502 Level 2 (Power St.)  \u2502\n\u251c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u253c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u253c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2524\n\u2502 Layers              \u2502 5                    \u2502 5                    \u2502\n\u2502 Parallax Factors    \u2502 0.0, 0.1, 0.25,     \u2502 0.0, 0.15, 0.30,    \u2502\n\u2502                     \u2502 0.35, 0.55           \u2502 0.45, 0.60           \u2502\n\u2502 Day/Night Variants  \u2502 Single static        \u2502 Day + Night          \u2502\n\u2502 Transitions         \u2502 None                 \u2502 Yes (6am & 6pm)      \u2502\n\u2502 Overlay Blending    \u2502 Not used             \u2502 Overlay.png (45sec)  \u2502\n\u2502 Sky Appearance      \u2502 Lavender grey        \u2502 Light blue (day) or  \u2502\n\u2502                     \u2502 (always)             \u2502 Dark grey (night)    \u2502\n\u2502 Pipeline Phases     \u2502 1,3-8 (skip 2,5)     \u2502 1-8 (all)            \u2502\n\u2502 Frame Time          \u2502 ~13-14ms             \u2502 ~15ms                \u2502\n\u2502 Complexity          \u2502 Simplified           \u2502 Full featured        \u2502\n\u2502 File Count          \u2502 6 (1 composite+5)    \u2502 11 (5 day+5 night+1  \u2502\n\u2502                     \u2502 or 5 layers          \u2502 overlay)             \u2502\n\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2534\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2534\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2518\n";
            }

            public static final class Level2SpecificWorkflow {
                public static final String LEVEL = "Power Station Level 2";
                public static final String VARIANT_TYPE = "DUAL VARIANT WITH DAY/NIGHT TRANSITIONS";
                public static final String FULL_PIPELINE = "Level 2 Parallax Pipeline (Full Featured):\n\nINITIALIZATION (Once at load):\n1. Load both Day variant (5 files) and Night variant (5 files)\n2. Load Overlay.png for blending mask\n3. Determine current game time\n4. Phase 2: Select appropriate variant (Day 6:00-18:00 or Night 18:00-6:00)\n5. Proceed to Phase 3 with selected variant\n\nGAMEPLAY LOOP (Every frame, 60 FPS):\nPhase 3: Calculate offsets using factors [0.0, 0.15, 0.30, 0.45, 0.60]\nPhase 4: Render 5 layers back-to-front with calculated offsets\nPhase 5: IF transitioning day/night: Apply overlay blending with smooth alpha\nPhase 6: Update offsets based on new player position\nPhase 7: Wrap layers at edges (modulo width)\nPhase 8: Composite final background and display\n\nTRANSITIONS (At 6:00 AM and 6:00 PM):\n- Trigger: Game time reaches transition hour\n- Duration: 45 seconds smooth blend\n- Method: Alpha blend overlay mask between Day and Night variants\n- Formula: finalColor = dayColor * (1 - blendFactor) + nightColor * blendFactor\n- blendFactor goes from 0.0 \u2192 1.0 over 45 seconds\n\nPERFORMANCE: ~15ms per frame (includes blending math during transitions)\n";
            }

            public static final class Level1SpecificWorkflow {
                public static final String LEVEL = "Industrial Zone Level 1";
                public static final String VARIANT_TYPE = "SINGLE STATIC VARIANT";
                public static final String SIMPLIFIED_PIPELINE = "Level 1 Parallax Pipeline (Simplified):\n\nINITIALIZATION (Once at load):\n1. Load 5 layer files from Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/\n2. Skip day/night selection (Phase 2 SKIPPED)\n3. All 5 layers use consistent \"industrial daylight\" appearance\n4. Proceed to Phase 3 immediately\n\nGAMEPLAY LOOP (Every frame, 60 FPS):\nPhase 3: Calculate offsets using factors [0.0, 0.1, 0.25, 0.35, 0.55]\nPhase 4: Render 5 layers back-to-front with calculated offsets\nPhase 5: Skip overlay blending (no day/night)\nPhase 6: Update offsets based on new player position\nPhase 7: Wrap layers at edges (modulo width)\nPhase 8: Composite final background and display\n\nNO TRANSITIONS:\n- No day/night blending\n- No overlay alpha changes\n- Consistent appearance throughout gameplay\n- Simplified per-frame computation (no blending math)\n\nPERFORMANCE: ~13-14ms per frame (slightly faster than Level 2 due to no blending)\n";
            }
        }

        public static class Phase8FinalComposite {
            public static final String PHASE_NAME = "PHASE 8: FINAL COMPOSITE & RENDER";
            public static final String DESCRIPTION = "Blend all layers and draw final background";

            public static final class RenderPerformance {
                public static final String TARGET_FPS = "60 frames per second";
                public static final String FRAME_TIME = "16.67 milliseconds per frame";
                public static final int LAYERS_TO_RENDER = 5;
                public static final int AVG_TIME_PER_LAYER = 3;
                public static final String TOTAL_TIME = "~15ms (leaves 1.67ms headroom)";
                public static final String OPTIMIZATION = "Parallax uses pre-loaded PNG - minimal CPU overhead";
            }

            public static final class CompositeProcess {
                public static final String STEP1 = "CREATE: Empty canvas (screen resolution, e.g., 1920x1080)";
                public static final String STEP2 = "LOOP: For each layer (back to front):";
                public static final String STEP2A = "  - Get calculated scroll offset for layer";
                public static final String STEP2B = "  - Draw Layer 1 at position (offset, 0)";
                public static final String STEP2C = "  - If wrapping: Draw Layer 1 at (offset + layerWidth, 0)";
                public static final String STEP3 = "IF day/night transition active:";
                public static final String STEP3A = "  - Apply overlay blending with current transition alpha";
                public static final String STEP4 = "DRAW: Final composite to screen";
                public static final String VISUAL_EXAMPLE = "Final Background Rendering (60 FPS):\n\nLAYER STACK (back to front):\n\u250c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2510  \u2190 Layer 5 (Near) - fastest parallax\n\u2502 Layer 5: Near Factory Element     \u2502     offset = 1650px (continuous update)\n\u251c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2524\n\u2502 Layer 4: Mid Factory Element      \u2502  \u2190 Layer 4 (Mid) - medium parallax\n\u2502                                   \u2502     offset = 1050px\n\u251c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2524\n\u2502 Layer 3: Far Factory Background   \u2502  \u2190 Layer 3 (Far) - slow parallax\n\u2502                                   \u2502     offset = 750px\n\u251c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2524\n\u2502 Layer 2: Fractal Tree Overlay     \u2502  \u2190 Layer 2 (Tree) - very slow\n\u2502                                   \u2502     offset = 300px\n\u251c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2524\n\u2502 Layer 1: Sky Base (Static)        \u2502  \u2190 Layer 1 (Sky) - no parallax\n\u2502                                   \u2502     offset = 0px (always)\n\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2518\n\nResult displayed to player:\n- Smooth scrolling parallax depth effect\n- Layers move at different speeds\n- Seamless wrapping at edges\n- Seamless day/night transitions (Layer 2 only)\n";
            }
        }

        public static class Phase7LayerWrapping {
            public static final String PHASE_NAME = "PHASE 7: LAYER WRAPPING";
            public static final String DESCRIPTION = "Repeat layers seamlessly when they reach edges";

            public static final class WrappingMathematicsLevel1 {
                public static final String LEVEL = "Industrial Zone Level 1";
                public static final int ESTIMATED_LAYER_WIDTH = 1920;
                public static final float[] PARALLAX_FACTORS = new float[]{0.0f, 0.1f, 0.25f, 0.35f, 0.55f};
                public static final String[] WRAP_EXAMPLES_AT_PLAYER_X_3000 = new String[]{"Sky (0.0x): offset = 3000 * 0.0 = 0px (no wrap, static)", "Tree (0.1x): offset = 3000 * 0.1 = 300px, wrapped = 300 % 1920 = 300px", "Far (0.25x): offset = 3000 * 0.25 = 750px, wrapped = 750 % 1920 = 750px", "Mid (0.35x): offset = 3000 * 0.35 = 1050px, wrapped = 1050 % 1920 = 1050px", "Near (0.55x): offset = 3000 * 0.55 = 1650px, wrapped = 1650 % 1920 = 1650px"};
            }

            public static final class WrappingMechanic {
                public static final String TRIGGER = "When scroll offset > layer width";
                public static final String ACTION = "Draw layer twice: original position + offset position";
                public static final String WRAPPING_ALGORITHM = "Seamless Wrap Example:\n\nLayer width = 1920 pixels\nPlayer at X = 1500\nParallax factor = 0.4x\n\nCalculation:\n1. Raw offset = 1500 * 0.4 = 600px\n2. Wrapped = 600 % 1920 = 600px (no wrap needed yet)\n3. Draw layer at position: -600, 0, 1920 screen\n\nWhen Player reaches X = 5000:\n1. Raw offset = 5000 * 0.4 = 2000px\n2. Wrapped = 2000 % 1920 = 80px (WRAP OCCURRED)\n3. First tile:  Draw at -80px\n4. Second tile: Draw at -80 + 1920 = 1840px (seamless transition)\n5. Result: Screen shows end of first tile + beginning of first tile\n\nVisual effect: Infinite seamless scrolling\n";
            }
        }

        public static class Phase6FactorUpdate {
            public static final String PHASE_NAME = "PHASE 6: PARALLAX FACTOR UPDATE";
            public static final String DESCRIPTION = "Recalculate layer offsets based on new camera position";
            public static final String FREQUENCY = "Every frame (60 FPS = every 16.67ms)";

            public static final class UpdateProcess {
                public static final String STEP1 = "GET: Current camera X position (player position)";
                public static final String STEP2 = "FOR EACH layer: newOffset = cameraX * parallaxFactor";
                public static final String STEP3 = "WRAP: newOffset = newOffset % layerWidth (for seamless wrapping)";
                public static final String STEP4 = "STORE: Update layer render position";
                public static final String STEP5 = "NEXT FRAME: Repeat with new camera position";
                public static final String SMOOTH_SCROLLING_EXPLANATION = "Continuous Update Example (60 FPS):\n\nFrame 1: playerX = 100, far_layer_offset = 100 * 0.25 = 25px\nFrame 2: playerX = 105, far_layer_offset = 105 * 0.25 = 26.25px\nFrame 3: playerX = 110, far_layer_offset = 110 * 0.25 = 27.5px\nFrame 4: playerX = 115, far_layer_offset = 115 * 0.25 = 28.75px\nFrame 5: playerX = 120, far_layer_offset = 120 * 0.25 = 30px\n\nNear layer (0.55x) updates faster:\nFrame 1: playerX = 100, near_layer_offset = 100 * 0.55 = 55px\nFrame 2: playerX = 105, near_layer_offset = 105 * 0.55 = 57.75px\nFrame 3: playerX = 110, near_layer_offset = 110 * 0.55 = 60.5px\n\nResult: Near layers scroll faster  = depth effect!\n";
            }
        }

        public static class Phase5OverlayBlending {
            public static final String PHASE_NAME = "PHASE 5: OVERLAY BLENDING";
            public static final String DESCRIPTION = "Blend day/night variants during transitions";
            public static final String APPLIES_TO = "Level 2 (Power Station) only";

            public static final class TransitionTiming {
                public static final String SUNRISE_START = "5:45 AM";
                public static final String SUNRISE_END = "6:30 AM";
                public static final String SUNRISE_DIRECTION = "Night \u2192 Day";
                public static final String SUNSET_START = "5:30 PM";
                public static final String SUNSET_END = "6:15 PM";
                public static final String SUNSET_DIRECTION = "Day \u2192 Night";
            }

            public static final class BlendingMechanic {
                public static final String OVERLAY_FILE = "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/Overlay.png";
                public static final String BLEND_PURPOSE = "Smooth transition between day and night backgrounds";
                public static final float TRANSITION_DURATION = 45.0f;
                public static final float BLEND_SPEED = 0.75f;
                public static final String BLEND_ALGORITHM = "For each pixel at time T during 45-second transition:\n1. Load both Day and Night layer images\n2. Load Overlay.png (blending mask)\n3. For each pixel:\n   - If overlay alpha = 0: Use Day image (100%)\n   - If overlay alpha = 1: Use Night image (100%)\n   - If overlay alpha = 0.5: Blend 50% Day + 50% Night\n\nBlend formula per pixel:\nfinalColor = dayColor * (1 - blendFactor) + nightColor * blendFactor\n\nWhere blendFactor goes from 0.0 \u2192 1.0 over 45 seconds\n\nResult: Smooth gradient transition from day to night\n(or night to day at morning transition)\n";
            }
        }

        public static class Phase4LayerRendering {
            public static final String PHASE_NAME = "PHASE 4: LAYER RENDERING ORDER";
            public static final String DESCRIPTION = "Draw layers back-to-front with calculated offsets";
            public static final String ALGORITHM = "Painters Algorithm (back to front)";

            public static final class RenderingOrderLevel2 {
                public static final String LEVEL = "Power Station Level 2 (Day/Night)";
                public static final String RENDER_ORDER = "STEP 1: Layer 1 (Sky) - Parallax 0.0x\n        \u2022 Draw first (static background)\n        \u2022 Uses Day/1.png OR Night/1.png\n        \u2022 No scrolling\n\nSTEP 2: Layer 2 (Far Tower) - Parallax 0.15x\n        \u2022 Draw second\n        \u2022 Uses Day/BG_Layer2_FactoryLeft_LightBlueDetail_ParallaxFactor015.png OR Night/2.png\n        \u2022 Slow subtle movement\n        \u2022 Calculated offset: playerX * 0.15\n\nSTEP 3: Layer 3 (Mid Tower) - Parallax 0.30x\n        \u2022 Draw third\n        \u2022 Uses Day/3.png OR Night/3.png\n        \u2022 Medium movement\n        \u2022 Calculated offset: playerX * 0.30\n\nSTEP 4: Layer 4 (Near Structure) - Parallax 0.45x\n        \u2022 Draw fourth\n        \u2022 Uses Day/4.png OR Night/4.png\n        \u2022 Faster movement\n        \u2022 Calculated offset: playerX * 0.45\n\nSTEP 5: Layer 5 (Foreground) - Parallax 0.60x\n        \u2022 Draw last (closest element)\n        \u2022 Uses Day/5.png OR Night/5.png\n        \u2022 Fastest parallax movement\n        \u2022 Calculated offset: playerX * 0.60\n        \u2022 Creates strongest depth effect\n";
            }

            public static final class RenderingOrderLevel1 {
                public static final String LEVEL = "Industrial Zone Level 1";
                public static final String RENDER_ORDER = "STEP 1: Layer 1 (Sky Base) - Parallax 0.0x\n        \u2022 Draw first (background)\n        \u2022 No parallax scrolling (stays static)\n        \u2022 Fills entire background area\n        \u2022 Scroll offset: 0 pixels (always)\n\nSTEP 2: Layer 2 (Fractal Tree) - Parallax 0.1x\n        \u2022 Draw second\n        \u2022 Very slow parallax movement\n        \u2022 Calculated offset: playerX * 0.1\n        \u2022 Updates rarely (slow scrolling effect)\n\nSTEP 3: Layer 3 (Far Factory) - Parallax 0.25x\n        \u2022 Draw third\n        \u2022 Slow parallax movement\n        \u2022 Calculated offset: playerX * 0.25\n        \u2022 Moves slower than camera\n\nSTEP 4: Layer 4 (Mid Factory) - Parallax 0.35x\n        \u2022 Draw fourth\n        \u2022 Medium parallax movement\n        \u2022 Calculated offset: playerX * 0.35\n        \u2022 More responsive than far layer\n\nSTEP 5: Layer 5 (Near Factory) - Parallax 0.55x\n        \u2022 Draw last (foreground)\n        \u2022 Fastest parallax movement\n        \u2022 Calculated offset: playerX * 0.55\n        \u2022 Most responsive to camera, creates depth\n";
            }
        }

        public static class Phase3ScrollCalculation {
            public static final String PHASE_NAME = "PHASE 3: SCROLL OFFSET CALCULATION";
            public static final String DESCRIPTION = "Compute scroll offset for each layer";

            public static final class Level2ScrollFactors {
                public static final String LEVEL = "Power Station Level 2";
                public static final float[] FACTORS = new float[]{0.0f, 0.15f, 0.3f, 0.45f, 0.6f};
                public static final String[] LAYER_NAMES = new String[]{"Sky", "Far Tower", "Mid Tower", "Near Structure", "Foreground"};

                public static final class ScrollBehavior {
                    public static final String SKY_BEHAVIOR = "0.0x - NO SCROLL (Static sky)";
                    public static final String FAR_BEHAVIOR = "0.15x - SLOW (Distant tower, subtle movement)";
                    public static final String MID_BEHAVIOR = "0.30x - MEDIUM-SLOW (Mid-ground structure)";
                    public static final String NEAR_BEHAVIOR = "0.45x - MEDIUM-FAST (Near structure, noticeable movement)";
                    public static final String FORE_BEHAVIOR = "0.60x - FAST (Foreground, most responsive)";
                }
            }

            public static final class Level1ScrollFactors {
                public static final String LEVEL = "Industrial Zone Level 1";
                public static final float[] FACTORS = new float[]{0.0f, 0.1f, 0.25f, 0.35f, 0.55f};
                public static final String[] LAYER_NAMES = new String[]{"Sky", "Fractal Tree", "Far Factory", "Mid Factory", "Near Factory"};

                public static final class ScrollBehavior {
                    public static final String SKY_BEHAVIOR = "0.0x - NO SCROLL (Static sky, stays in place)";
                    public static final String TREE_BEHAVIOR = "0.1x - VERY SLOW (Barely moves, very distant)";
                    public static final String FAR_BEHAVIOR = "0.25x - SLOW (Quarter speed of camera, far background)";
                    public static final String MID_BEHAVIOR = "0.35x - MEDIUM (Third speed of camera, mid-ground)";
                    public static final String NEAR_BEHAVIOR = "0.55x - FAST (Half speed of camera, closest)";
                }
            }

            public static final class ScrollFormulaExplanation {
                public static final String FORMULA = "layerScrollOffset = playerX * parallaxFactor";
                public static final String EXAMPLE_CALCULATION = "Assume:\n- Camera X position (player): 500 pixels\n- Layer parallax factor: 0.3x\n- Layer image width: 1920 pixels\n\nCalculate:\n1. Raw scroll = 500 * 0.3 = 150 pixels\n2. Wrapped scroll = 150 % 1920 = 150 pixels (layer scrolls 150px left)\n\nAnother example:\n- Camera X: 2000 pixels\n- Layer parallax: 0.6x\n- Layer width: 1920 pixels\n\nCalculate:\n1. Raw scroll = 2000 * 0.6 = 1200 pixels\n2. Wrapped scroll = 1200 % 1920 = 1200 pixels (within one tile)\n\nExample with wrapping:\n- Camera X: 4000 pixels\n- Layer parallax: 0.8x\n- Layer width: 1920 pixels\n\nCalculate:\n1. Raw scroll = 4000 * 0.8 = 3200 pixels\n2. Wrapped scroll = 3200 % 1920 = 1280 pixels\n3. Draw first tile at -1280, then second tile at 640 (seamless)\n";
            }
        }

        public static class Phase2VariantSelection {
            public static final String PHASE_NAME = "PHASE 2: DAY/NIGHT VARIANT SELECTION";
            public static final String DESCRIPTION = "Choose day or night variant based on game time";

            public static final class TransitionMechanic {
                public static final String TRIGGER = "When time crosses 6:00 or 18:00";
                public static final String APPLIES_TO = "Level 2 (Power Station) ONLY";
                public static final String DURATION = "45 seconds smooth blend";
                public static final String BLENDING_FILE = "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/BG_Overlay_BlueYellowDiagonalGradient_ColourAtmosphere_DrawLast.png";
                public static final String METHOD = "Alpha blend overlay mask between variants";
                public static final float BLEND_SPEED = 0.02f;
                public static final String LEVEL_1_NOTE = "Level 1 has NO transitions - static appearance throughout gameplay";
            }

            public static final class NightVariantRules {
                public static final String VARIANT_NAME = "NIGHT";
                public static final String LEVEL = "Power Station Level 2 ONLY";
                public static final String TIME_RANGE = "18:00 to 6:00 (12 hours)";
                public static final String SKY_COLOR = "Dark gray/black - Minimal light";
                public static final String SILHOUETTES = "Dimmed visibility - Subtle gray shapes";
                public static final String VISIBILITY = "50-70% - Limited detail, atmosphere heavy";
                public static final String LAYER_FILES = "Night/* files";
                public static final String[] LAYERS = new String[]{"Night/1.png - Sky: Dark gray night atmosphere, possibly stars/moon", "Night/2.png - Far tower: Dimmed silhouette (0.15x parallax)", "Night/3.png - Mid structure: Gray-blue limited detail (0.30x)", "Night/4.png - Near structure: Very dark, minimal piping visible (0.45x)", "Night/5.png - Foreground: Darkest, only outline visible (0.60x)"};
            }

            public static final class DayVariantRules {
                public static final String VARIANT_NAME = "DAY";
                public static final String LEVEL = "Power Station Level 2 ONLY";
                public static final String TIME_RANGE = "6:00 to 18:00 (12 hours)";
                public static final String SKY_COLOR = "Light blue - Full brightness";
                public static final String SILHOUETTES = "Clear visibility - Distinct black shapes";
                public static final String VISIBILITY = "100% - Maximum detail visible";
                public static final String LAYER_FILES = "Day/* files";
                public static final String[] LAYERS = new String[]{"Day/1.png - Sky: Bright blue daylight atmosphere", "Day/BG_Layer2_FactoryLeft_LightBlueDetail_ParallaxFactor015.png - Far tower: Clear light blue silhouette", "Day/3.png - Mid structure: Medium blue detail", "Day/4.png - Near structure: Darker blue with visible piping", "Day/5.png - Foreground: Closest structure, darkest blue"};
            }

            public static final class Level1StaticVariant {
                public static final String LEVEL = "Industrial Zone Level 1";
                public static final String VARIANT_TYPE = "SINGLE STATIC VARIANT (No day/night)";
                public static final String APPLIES_TO_LEVEL_1 = "Level 1 skips this phase entirely";
                public static final String REASON = "Level 1 has no day/night cycle - uses fixed industrial daylight appearance";
                public static final String LEVEL_1_PROCESS = "PHASE 2 BEHAVIOR FOR LEVEL 1:\n\nInstead of selecting variants:\n- Load all 5 layers immediately (all use \"industrial daylight\" appearance)\n- No day/night state to check\n- No overlay blending needed\n- Skip directly to Phase 3\n\nAvailable single variant:\n- Layer 1 (Sky): BG_Layer1_SkyBase_SolidLavenderGrey_StaticFill_DrawFirstNoScroll.png\n- Layer 2 (Tree): BG_Layer2_FractalTreeSilhouette_MintSkyBlackCracks_ParallaxFactor015.png\n- Layer 3 (Far): BG_Layer3_FarFactorySilhouette_LightBlueIndustrial_ParallaxFactor025.png\n- Layer 4 (Mid): BG_Layer4_MidFactorySilhouette_MediumBluePipeDetail_ParallaxFactor040.png\n- Layer 5 (Near): BG_Layer5_NearFactorySilhouette_DarkNavyLargeTank_ParallaxFactor060.png\n\nAll layers use consistent \"industrial daylight\" color palette:\n- Sky: Lavender grey\n- Parallax layers: Various shades of blue (light \u2192 dark as they approach)\n- Overall atmosphere: Clear daytime industrial zone\n";
            }
        }

        public static class Phase1Initialization {
            public static final String PHASE_NAME = "PHASE 1: BACKGROUND INITIALIZATION";
            public static final String DESCRIPTION = "Load all parallax layers into memory";

            public static final class Level2Init {
                public static final String LEVEL_TYPE = "Power Station Level 2 (Day/Night)";
                public static final int TOTAL_LAYERS = 5;
                public static final String DAY_DIRECTORY = "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/Day";
                public static final String NIGHT_DIRECTORY = "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/Night";
                public static final String OVERLAY_FILE = "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/BG_Overlay_BlueYellowDiagonalGradient_ColourAtmosphere_DrawLast.png";
                public static final String[] DAY_FILES = new String[]{"Day/BG_Layer1_SkyBase_LightGreyWhiteGradient_StaticFill_DrawFirst.png", "Day/BG_Layer2_FactoryLeft_LightBlueDetail_ParallaxFactor015.png", "Day/BG_Layer3_FactoryTall_LightBlueChimney_ParallaxFactor025.png", "Day/BG_Layer4_DistantFactory_FaintSilhouette_ParallaxFactor040.png", "Day/BG_Layer5_FactoryRight_LightBlueVariant_ParallaxFactor060.png"};
                public static final String[] NIGHT_FILES = new String[]{"Night/BG_Layer1_SkyBase_DarkGreyGradient_StaticFill_DrawFirst.png", "Night/BG_Layer2_FactoryLeft_DarkGreySilhouette_ParallaxFactor015.png", "Night/BG_Layer3_FactoryTall_DarkGreyCentre_ParallaxFactor025.png", "Night/BG_Layer4_DistantFactory_VeryDarkFaint_ParallaxFactor040.png", "Night/BG_Layer5_FactoryRight_DarkGreyVariant_ParallaxFactor060.png"};
            }

            public static final class Level1Init {
                public static final String LEVEL_TYPE = "Industrial Zone Level 1";
                public static final int TOTAL_LAYERS = 5;
                public static final String[] FILES_TO_LOAD = new String[]{"Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Composite_FullLayeredSkyline_AllLayersCombined_SingleDrawFallback.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer1_SkyBase_SolidLavenderGrey_StaticFill_DrawFirstNoScroll.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer2_FractalTreeSilhouette_MintSkyBlackCracks_ParallaxFactor015.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer3_FarFactorySilhouette_LightBlueIndustrial_ParallexFactor025.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer4_MidFactorySilhouette_MediumBluePipeDetail_ParallaxFactor040.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer5_NearFactorySilhouette_DarkNavyLargeTank_ParallaxFactor060.png"};
                public static final int ESTIMATED_LAYER_WIDTH = 1920;
                public static final int ESTIMATED_LAYER_HEIGHT = 1080;
            }
        }
    }

    public static class LevelBackgroundProperties {

        public static class PowerStationLevel2Background {
            public static final String LEVEL_NAME = "Power Station Level 2";
            public static final String LEVEL_TYPE = "power_station_level_2";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2";
            public static final String THEME = "Power station with day/night cycle";
            public static final int TOTAL_LAYERS = 5;
            public static final boolean HAS_DAY_NIGHT = true;
            public static final boolean HAS_OVERLAY = true;
            public static final String[] DAY_VARIANT = new String[]{"Day/BG_Layer1_SkyBase_LightGreyWhiteGradient_StaticFill_DrawFirst.png", "Day/BG_Layer2_FactoryLeft_LightBlueDetail_ParallaxFactor015.png", "Day/BG_Layer3_FactoryTall_LightBlueChimney_ParallaxFactor025.png", "Day/BG_Layer4_DistantFactory_FaintSilhouette_ParallaxFactor040.png", "Day/BG_Layer5_FactoryRight_LightBlueVariant_ParallaxFactor060.png"};
            public static final String[] NIGHT_VARIANT = new String[]{"Night/BG_Layer1_SkyBase_DarkGreyGradient_StaticFill_DrawFirst.png", "Night/BG_Layer2_FactoryLeft_DarkGreySilhouette_ParallaxFactor015.png", "Night/BG_Layer3_FactoryTall_DarkGreyCentre_ParallaxFactor025.png", "Night/BG_Layer4_DistantFactory_VeryDarkFaint_ParallaxFactor040.png", "Night/BG_Layer5_FactoryRight_DarkGreyVariant_ParallaxFactor060.png"};
            public static final String OVERLAY_FILE = "Resources/industrial-zone/1 Tiles/power-station-level-2/2 Background_level_2/BG_Overlay_BlueYellowDiagonalGradient_ColourAtmosphere_DrawLast.png";
            public static final float[] PARALLAX_FACTORS = new float[]{0.0f, 0.15f, 0.3f, 0.45f, 0.6f};
            public static final String[] LAYER_NAMES = new String[]{"Sky", "Far Tower", "Mid Tower", "Near Structure", "Foreground"};
            public static final String[] LAYER_DESCRIPTIONS = new String[]{"Static sky background - no parallax (0.0x)", "Distant tower silhouette - slow parallax (0.15x)", "Mid-ground structure - medium parallax (0.30x)", "Near structure detail - faster parallax (0.45x)", "Foreground element - fastest parallax (0.60x)"};
        }

        public static class IndustrialZoneLevel1Background {
            public static final String LEVEL_NAME = "Industrial Zone Level 1";
            public static final String LEVEL_TYPE = "industrial_zone_level_1";
            public static final String DIRECTORY = "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1";
            public static final String THEME = "Industrial factory with silhouette parallax";
            public static final int TOTAL_LAYERS = 5;
            public static final boolean HAS_DAY_NIGHT = false;
            public static final boolean HAS_OVERLAY = false;
            public static final String[] LAYER_FILES = new String[]{"Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Composite_FullLayeredSkyline_AllLayersCombined_SingleDrawFallback.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer1_SkyBase_SolidLavenderGrey_StaticFill_DrawFirstNoScroll.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer2_FractalTreeSilhouette_MintSkyBlackCracks_ParallaxFactor015.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer3_FarFactorySilhouette_LightBlueIndustrial_ParallaxFactor025.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer4_MidFactorySilhouette_MediumBluePipeDetail_ParallaxFactor040.png", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer5_NearFactorySilhouette_DarkNavyLargeTank_ParallaxFactor060.png"};
            public static final float[] PARALLAX_FACTORS = new float[]{0.0f, 0.1f, 0.25f, 0.35f, 0.55f};
            public static final String[] LAYER_NAMES = new String[]{"Sky Base", "Fractal Tree", "Far Factory", "Mid Factory", "Near Factory"};
            public static final String[] LAYER_DESCRIPTIONS = new String[]{"Static sky background - no parallax (0.0x)", "Distant fractal tree with black cracks - very slow parallax (0.1x)", "Far factory silhouette in light blue - slow parallax (0.25x)", "Mid factory with piping detail - medium parallax (0.35x)", "Near factory with large tank - fastest parallax (0.55x)"};
            public static final String[] VISUAL_DETAILS = new String[]{"Layer 1: Lavender-grey solid fill, immobile background", "Layer 2: Black procedural tree branches, mint tint, subtle movement", "Layer 3: Light blue factory shapes, very distant feel, slow scroll", "Layer 4: Medium blue pipes and details, noticeably moving layers", "Layer 5: Dark navy large tank/structure, closest element, most responsive"};
        }
    }

    public static class CharacterVfxEffects {

        public static class GenericCharacterVfx {
            public static final String DIRECTORY = "Resources/industrial-zone/vfx/6 Extra/Character";
            public static final String USAGE = "Applies to all characters";
            public static final String[] EFFECT_FILES = new String[]{"06_VFX_Char_Generic_Fall_4Frames1Row_ArmsUpFallVFX_Loop_100ms.png", "07_VFX_Char_Generic_Hurt_2Frames1Row_HurtFlashVFX_PlayOnce_100ms.png", "08_VFX_Char_Generic_Walk_5Frames1Row_WalkCycleColorVFX_Loop_100ms.png"};
        }

        public static class BikerCharacterVfx {
            public static final String CHARACTER = "biker";
            public static final String DIRECTORY = "Resources/industrial-zone/vfx/6 Extra/Character";
            public static final String COLOR_ACCENT = "Magenta #E84B8A";
            public static final String[] EFFECT_FILES = new String[]{"01_VFX_Char_Biker_Death_6Frames1Row_DeathTumbleColorVFX_PlayOnce_120ms.png", "02_VFX_Char_Biker_DoubleJump_6Frames1Row_MidAirFlipColorVFX_PlayOnce_80ms.png", "03_VFX_Char_Biker_Hurt_2Frames1Row_HurtFlinchRedGhostVFX_PlayOnce_100ms.png", "04_VFX_Char_Biker_Jump_4Frames1Row_JumpArcColorVFX_PlayOnce_80ms.png", "05_VFX_Char_Biker_Run_5Frames1Row_RunCycleColorVFX_Loop_80ms.png"};
        }
    }

    public static class AmbientParticleVfx {

        public static class PortalVfx {
            public static final String EFFECT_NAME = "Teleport Portal";
            public static final String EFFECT_TYPE = "portal";
            public static final String DIRECTORY = "Resources/industrial-zone/vfx/5 Other";
            public static final String COLOR = "Portal Purple #8B00FF";
            public static final String EFFECT_PURPOSE = "Teleportation effect, level transition";
            public static final int TOTAL_FRAMES = 2;
            public static final int TIMING_MS = 100;
            public static final int DURATION_MS = 200;
            public static final boolean LOOPS = false;
            public static final String[] PORTAL_FILES = new String[]{"09_VFX_Portal_Frame01_LargePortalOpeningA_Portal_PlayOnce_100ms.png", "10_VFX_Portal_Frame02_LargePortalOpeningB_Portal_PlayOnce_100ms.png"};
        }

        public static class SmokeWispsVfx {
            public static final String EFFECT_NAME = "Smoke Wisps";
            public static final String EFFECT_TYPE = "smoke_wisp";
            public static final String DIRECTORY = "Resources/industrial-zone/vfx/5 Other";
            public static final String COLOR = "Gray/White #C0C0C0";
            public static final String EFFECT_PURPOSE = "Ambient atmosphere, fire effect, smoke trails";
            public static final int VARIATIONS = 2;
            public static final int FRAMES_PER_WISP = 6;
            public static final int TIMING_MS = 100;
            public static final int DURATION_MS = 600;
            public static final boolean LOOPS = true;
            public static final boolean TALL_EFFECT = true;
            public static final String[] WISP_FILES = new String[]{"11_VFX_Smoke_Wisps_6Frames1Row_TallTealWispVariantA_Ambient_Loop_120ms.png", "12_VFX_Smoke_Wisps_6Frames1Row_TallTealWispVariantB_Ambient_Loop_120ms.png"};
        }

        public static class StarbustVfx {
            public static final String EFFECT_NAME = "Starburst Effect";
            public static final String EFFECT_TYPE = "starburst";
            public static final String DIRECTORY = "Resources/industrial-zone/vfx/5 Other";
            public static final String COLOR = "Gold/Yellow #FFD700";
            public static final String EFFECT_PURPOSE = "Magic sparkles, growth effect, ambient magic";
            public static final int VARIATIONS = 4;
            public static final String ANIMATION_PATTERN = "Mixed GridFrame (6/6/14/4 Frames)";
            public static final String[] STARBURST_FILES = new String[]{"01_VFX_Stars_Burst_6Frames1Row_TinyGoldSparse_Ambient_Loop_100ms.png", "02_VFX_Stars_Burst_6Frames1Row_SmallGoldUniform_Ambient_Loop_100ms.png", "03_VFX_Stars_Burst_14Frames1Row_MultiSizeGrowSequence_Impact_PlayOnce_80ms.png", "04_VFX_Stars_Burst_4Frames1Row_SmallStarMorph_Ambient_Loop_100ms.png"};
        }

        public static class ParticleEffectsVfx {
            public static final String EFFECT_NAME = "Ambient Particles";
            public static final String EFFECT_TYPE = "particle";
            public static final String DIRECTORY = "Resources/industrial-zone/vfx/4 Particles";
            public static final int TOTAL_VARIATIONS = 12;
            public static final int FRAMES_PER_PARTICLE = 4;
            public static final int TIMING_MS = 100;
            public static final int DURATION_MS = 400;
            public static final boolean LOOPS = true;
            public static final String ANIMATION_PATTERN = "GridFrameAnimation (4Frames1Row Looping)";
            public static final String[] PARTICLE_FILES = new String[]{"01_VFX_Particles_Green_4Frames1Row_TinyGreenSparse_Ambient_Loop_100ms.png", "02_VFX_Particles_Green_4Frames1Row_SmallGreenMedium_Ambient_Loop_100ms.png", "03_VFX_Particles_Green_4Frames1Row_GreenWideSpread_Ambient_Loop_100ms.png", "04_VFX_Particles_Blue_4Frames1Row_TinyBlueSparse_Ambient_Loop_100ms.png", "05_VFX_Particles_Blue_4Frames1Row_SmallBlueMedium_Ambient_Loop_100ms.png", "06_VFX_Particles_Blue_4Frames1Row_BlueWideSpread_Ambient_Loop_100ms.png", "07_VFX_Particles_Orange_4Frames1Row_TinyOrangeSparse_Ambient_Loop_100ms.png", "08_VFX_Particles_Orange_4Frames1Row_SmallOrangeMedium_Ambient_Loop_100ms.png", "09_VFX_Particles_Orange_4Frames1Row_OrangeWideScatter_Ambient_Loop_100ms.png", "10_VFX_Particles_Yellow_4Frames1Row_TinyYellowSparse_Ambient_Loop_100ms.png", "11_VFX_Particles_Yellow_4Frames1Row_SmallYellowMedium_Ambient_Loop_100ms.png", "12_VFX_Particles_Yellow_4Frames1Row_YellowWideScatter_Ambient_Loop_100ms.png"};
        }
    }

    public static class ImpactBurstVfx {

        public static class CyanShardVfx {
            public static final String EFFECT_NAME = "Cyan Energy Shard";
            public static final String EFFECT_TYPE = "cyan_shard";
            public static final String DIRECTORY = "Resources/industrial-zone/vfx/5 Other";
            public static final String COLOR = "Cyan Energy #00FFFF";
            public static final String EFFECT_PURPOSE = "Energy attack, shield hit, electrical discharge";
            public static final int SHARD_VARIATIONS = 4;
            public static final int TIMING_MS = 80;
            public static final String ANIMATION_PATTERN = "GridFrameAnimation (Varies 4-8 Frames1Row)";
            public static final String[] SHARD_FILES = new String[]{"05_VFX_CyanShards_Scatter_8Frames1Row_EnergyShardScattered_Impact_PlayOnce_80ms.png", "06_VFX_CyanShards_Scatter_6Frames1Row_EnergyLinesSpase_Impact_PlayOnce_80ms.png", "07_VFX_CyanShards_Scatter_4Frames1Row_CrossShardVerySpase_Impact_PlayOnce_80ms.png", "08_VFX_CyanShards_Scatter_4Frames1Row_GroundEnergyShapes_Impact_PlayOnce_80ms.png"};
        }

        public static class SparkBurstVfx {
            public static final String EFFECT_NAME = "Spark Burst Impact";
            public static final String EFFECT_TYPE = "spark_burst";
            public static final String DIRECTORY = "Resources/industrial-zone/vfx/3 Sparks";
            public static final String COLOR = "Gold/Yellow #FFD700";
            public static final String EFFECT_PURPOSE = "Melee hit, metal collision, energy spark";
            public static final int BURST_VARIATIONS = 8;
            public static final int FRAMES_PER_BURST = 4;
            public static final int TIMING_MS = 80;
            public static final int DURATION_MS = 320;
            public static final String ANIMATION_PATTERN = "GridFrameAnimation (4Frames1Row)";
            public static final String[] BURST_FILES = new String[]{"01_VFX_Sparks_Burst_4Frames1Row_SmallSparseGold_Impact_PlayOnce_80ms.png", "02_VFX_Sparks_Burst_4Frames1Row_SmallDenseGold_Impact_PlayOnce_80ms.png", "03_VFX_Sparks_Burst_4Frames1Row_WideThinScatter_Impact_PlayOnce_80ms.png", "04_VFX_Sparks_Burst_4Frames1Row_SmallAngledUpward_Impact_PlayOnce_80ms.png", "05_VFX_Sparks_Burst_4Frames1Row_MediumBoldBurst_Impact_PlayOnce_80ms.png", "06_VFX_Sparks_Burst_4Frames1Row_TinyFaintTrail_Impact_PlayOnce_80ms.png", "07_VFX_Sparks_Burst_4Frames1Row_MediumMixedAngles_Impact_PlayOnce_80ms.png", "08_VFX_Sparks_Burst_4Frames1Row_LargeWideScatter_Impact_PlayOnce_80ms.png"};
        }
    }

    public static class DestructibleObjectVfx {

        public static class BoxDestructionVfx {
            public static final String OBJECT_NAME = "Box Crate Destruction";
            public static final String OBJECT_TYPE = "box";
            public static final String DIRECTORY = "Resources/industrial-zone/vfx/6 Extra/Objects";
            public static final String COLOR_INTACT = "Wood Brown #8B4513";
            public static final String COLOR_DEBRIS = "Dust Gray #A9A9A9";
            public static final int TOTAL_FRAMES = 8;
            public static final int TIMING_MS = 100;
            public static final int DURATION_MS = 800;
            public static final String ANIMATION_PATTERN = "Extended Destruction Sequence";
            public static final String[] FRAME_FILES = new String[]{"01_VFX_Box1_Destroy_Frame01_CrateIntact_DestroyAnim_PlayOnce_100ms.png", "02_VFX_Box1_Destroy_Frame02_CrateSightDamage_DestroyAnim_PlayOnce_100ms.png", "03_VFX_Box1_Destroy_Frame03_CrateBreaking_DestroyAnim_PlayOnce_100ms.png", "04_VFX_Box1_Destroy_Frame04_CrateMostlyGone_DestroyAnim_PlayOnce_100ms.png", "05_VFX_Box1_Destroy_Frame05_CrateDebrisFlat_DestroyAnim_PlayOnce_100ms.png", "06_VFX_Box2_Destroy_Frame01_Additional Destruction_PlayOnce_100ms.png", "07_VFX_Box2_Destroy_Frame02_Continue Destruction_PlayOnce_100ms.png", "08_VFX_Box2_Destroy_Frame03_Final Debris_PlayOnce_100ms.png"};
        }

        public static class CapsuleDestructionVfx {
            public static final String OBJECT_NAME = "Capsule Destruction";
            public static final String OBJECT_TYPE = "capsule";
            public static final String DIRECTORY = "Resources/industrial-zone/vfx/6 Extra/Objects";
            public static final String COLOR_INTACT = "Tech Blue #0066CC";
            public static final String COLOR_DEBRIS = "Metal Gray #696969";
            public static final int TOTAL_FRAMES = 4;
            public static final int TIMING_MS = 100;
            public static final int DURATION_MS = 400;
            public static final String ANIMATION_PATTERN = "GridFrameAnimation (4Frames1Row)";
            public static final String[] FRAME_FILES = new String[]{"01_VFX_Capsule_Frame01_CapsuleIntact_DestroyAnim_PlayOnce_100ms.png", "02_VFX_Capsule_Frame02_CapsuleCracked_DestroyAnim_PlayOnce_100ms.png", "03_VFX_Capsule_Frame03_CapsuleBreaking_DestroyAnim_PlayOnce_100ms.png", "04_VFX_Capsule_Frame04_CapsuleDebrisFlat_DestroyAnim_PlayOnce_100ms.png"};
        }

        public static class BushDestructionVfx {
            public static final String OBJECT_NAME = "Bush Destruction";
            public static final String OBJECT_TYPE = "bush";
            public static final String DIRECTORY = "Resources/industrial-zone/vfx/6 Extra/Objects";
            public static final String COLOR_INITIAL = "Green #2D5016";
            public static final String COLOR_DEBRIS = "Brown Dust #8B7355";
            public static final int TOTAL_FRAMES = 4;
            public static final int TIMING_MS = 100;
            public static final int DURATION_MS = 400;
            public static final String ANIMATION_PATTERN = "GridFrameAnimation (4Frames1Row)";
            public static final String FILE = "01_VFX_Bush_Frame01_LargeFullBush_DecoOrDestroy_PlayOnce_100ms.png";
            public static final String[] FRAME_FILES = new String[]{"01_VFX_Bush_Frame01_LargeFullBush_DecoOrDestroy_PlayOnce_100ms.png", "02_VFX_Bush_Frame02_MediumBush_DecoOrDestroy_PlayOnce_100ms.png", "03_VFX_Bush_Frame03_SmallBush_DecoOrDestroy_PlayOnce_100ms.png", "04_VFX_Bush_Frame04_TinyBushShrub_DecoOrDestroy_PlayOnce_100ms.png"};
        }
    }

    public static class InfantryEnemyAssetProperties {

        public static class BrawlerEnemy {
            public static final String SOLDIER_NAME = "Infantry Brawler";
            public static final String SOLDIER_TYPE = "brawler";
            public static final String BUILD = "Stocky, heavily armored";
            public static final String SPECIALTY = "Strong melee, slow movement";
            public static final int ESTIMATED_WIDTH = 80;
            public static final int ESTIMATED_HEIGHT = 96;
            public static final String WALK_FILE = "03_PlayerCharacter_RemoteOperator_Cyborg_WalkCycle_6Frames1Row.png";
        }

        public static class MaleSoldier {
            public static final String SOLDIER_NAME = "Infantry Male";
            public static final String SOLDIER_TYPE = "male_soldier";
            public static final String HELMET_COLOR = "Green #4A6741";
            public static final String SPECIALTY = "Balanced fighter";
            public static final int ESTIMATED_WIDTH = 64;
            public static final int ESTIMATED_HEIGHT = 96;
            public static final String WALK_FILE = "01_PlayerCharacter_RemoteOperator_Biker_WalkCycle_6Frames1Row.png.png";
        }

        public static class FemaleSoldier {
            public static final String SOLDIER_NAME = "Infantry Female";
            public static final String SOLDIER_TYPE = "female_soldier";
            public static final String HAIR_COLOR = "Red #FF4444";
            public static final String SPECIALTY = "Fast, agile combatant";
            public static final int ESTIMATED_WIDTH = 64;
            public static final int ESTIMATED_HEIGHT = 96;
            public static final String WALK_FILE = "02_PlayerCharacter_RemoteOperator_Punk_WalkCycle_6Frames1Row.png";
        }
    }

    public static class AdvancedEnemyAssetProperties {

        public static class WingedWarriorEnemy {
            public static final String ENEMY_NAME = "Winged Warrior";
            public static final String ENEMY_TYPE = "winged_warrior";
            public static final String DIRECTORY = "Resources/industrial-zone/characters/enemies/sci-fi-antagonists/3";
            public static final String COLOR_BODY = "Blue #4A7BA7";
            public static final String COLOR_WINGS = "Purple Vanes #9D4EDD";
            public static final String COLOR_PROJECTILE = "Red #FF4444";
            public static final int ESTIMATED_WIDTH = 96;
            public static final int ESTIMATED_HEIGHT = 128;
            public static final int ANIMATION_COUNT = 11;
            public static final boolean HAS_AERIAL_COMBAT = true;
            public static final String[] ANIMATION_FILES = new String[]{"01_Enemy_WingedWarrior_Idle_4Frames1Row_StandingPatrolldle_DefaultIdle_Loop_150ms.png", "02_Enemy_WingedWarrior_Walk_6Frames1Row_WalkingAdvance_Movement_Loop_100ms.png", "03_Enemy_WingedWarrior_Attack1_6Frames1Row_KickStrikeCombo_MeleeAttack_PlayOnce_70ms.png", "04_Enemy_WingedWarrior_Attack2_6Frames1Row_ExtendedReachSlash_MeleeAttack_PlayOnce_80ms.png", "05_Enemy_WingedWarrior_Attack3_5Frames1Row_WingCapeSashSweep_MeleeAttack_PlayOnce_70ms.png", "06_Enemy_WingedWarrior_Attack4b_4Frames1Row_JumpAerialSlamBurst_Attack_PlayOnce_80ms.png", "07_Enemy_WingedWarrior_Attack4b_4Frames1Row_GroundSlamBurstRoll_Attack_PlayOnce_80ms.png", "08_Enemy_WingedWarrior_Special_6Frames1Row_WingFlareChargeAbility_Special_PlayOnce_100ms.png", "09_Enemy_WingedWarrior_Projectile_1Frames1Row_SingleRedProjectileSprite_Projectile_Loop_100ms.png", "10_Enemy_WingedWarrior_Hurt_2Frames1Row_HitFlinchRedGhost_TakeDamage_PlayOnce_100ms.png", "11_Enemy_WingedWarrior_Death_6Frames1Row_FallAndSlideDeathDeath_PlayOnce_120ms.png"};
        }

        public static class ArmoredKnightEnemy {
            public static final String ENEMY_NAME = "Armored Knight";
            public static final String ENEMY_TYPE = "armored_knight";
            public static final String DIRECTORY = "Resources/industrial-zone/characters/enemies/sci-fi-antagonists/2";
            public static final String COLOR_ARMOR = "Blue Metallic #4A5F8F";
            public static final String COLOR_ENERGY = "Purple Glow #9D4EDD";
            public static final String COMBAT_STYLE = "Melee + ranged energy attacks";
            public static final int ESTIMATED_WIDTH = 112;
            public static final int ESTIMATED_HEIGHT = 128;
            public static final int ANIMATION_COUNT = 10;
            public static final String[] ANIMATION_FILES = new String[]{"01_Enemy_ArmoredKnight_Idle_4Frames1Row_StandingPatrolIdle_DefaultIdle_Loop_150ms.png", "02_Enemy_ArmoredKnight_Walk_5Frames1Row_WalkingAdvance_Movement_Loop_100ms.png", "03_Enemy_ArmoredKnight_Attack1_4Frames1Row_BladeSashForward_MeleeAttack_PlayOnce_70ms.png", "04_Enemy_ArmoredKnight_Attack2_4Frames1Row_ExtendedReachSlash_MeleeAttack_PlayOnce_80ms.png", "05_Enemy_ArmoredKnight_Attack3_5Frames1Row_HeavySwingEnergyA_Attack_PlayOnce_80ms.png", "06_Enemy_ArmoredKnight_Attack3b_4Frames1Row_MultiAttackPart2Combo_Attack_PlayOnce_80ms.png", "07_Enemy_ArmoredKnight_Special_6Frames1Row_EnergyChargeGlowAbility_Special_PlayOnce_100ms.png", "08_Enemy_ArmoredKnight_Projectile_1Frames1Row_SingleProjectileSprite_Projectile_Loop_100ms.png", "09_Enemy_ArmoredKnight_Hurt_2Frames1Row_HitFlinchGoldenGhost_TakeDamage_PlayOnce_100ms.png", "10_Enemy_ArmoredKnight_Death_6Frames1Row_DeathCollapseGhost_Death_PlayOnce_120ms.png"};
        }

        public static class CombatTankEnemy {
            public static final String ENEMY_NAME = "Combat Tank";
            public static final String ENEMY_TYPE = "combat_tank";
            public static final String DIRECTORY = "Resources/industrial-zone/characters/enemies/sci-fi-antagonists/1";
            public static final String COLOR_HULL = "Dark Teal #1C3A47";
            public static final String COLOR_TURRET = "Orange #FF8C00";
            public static final String COMBAT_STYLE = "Ranged turret-based attacks";
            public static final int ESTIMATED_WIDTH = 128;
            public static final int ESTIMATED_HEIGHT = 96;
            public static final int ANIMATION_COUNT = 12;
            public static final String[] ANIMATION_FILES = new String[]{"01_Enemy_CombatTank_Idle_4Frames1Row_TurretIdlePatrol_DefaultIdle_Loop_150ms.png", "02_Enemy_CombatTank_Walk_4Frames1Row_VehicleDrivingAdvance_Movement_Loop_100ms.png", "03_Enemy_CombatTank_Attack1_4Frames1Row_TurretStraightShot_Attack_PlayOnce_80ms.png", "04_Enemy_CombatTank_Attack2_4Frames1Row_TurretAngledShot_Attack_PlayOnce_80ms.png", "05_Enemy_CombatTank_Attack3a_4Frames1Row_MultiAttackPart1Combo_Attack_PlayOnce_80ms.png", "06_Enemy_CombatTank_Attack3b_4Frames1Row_MultiAttackPart2Combo_Attack_PlayOnce_80ms.png", "07_Enemy_CombatTank_Attack3c_4Frames1Row_LaserBeamFireCombo_Attack_PlayOnce_80ms.png", "08_Enemy_CombatTank_Attack4_4Frames1Row_HeavyAttackWindupFollowThru_Attack_PlayOnce_100ms.png", "09_Enemy_CombatTank_Special_6Frames1Row_OperatorReactsAndFires_Special_PlayOnce_100ms.png", "10_Enemy_CombatTank_Hurt_2Frames1Row_HitReactionSilhouette_TakeDamage_PlayOnce_100ms.png", "11_Enemy_CombatTank_Death_5Frames1Row_VehicleDestructionSequence_Death_PlayOnce_120ms.png", "12_Enemy_CombatTank_Hurt3_2Frames1Row_SilhouetteTakeDamage_PlayOnce_100ms.png"};
        }
    }

    public static class VfxAssetProperties {

        public static class BloodVfx {
            public static final String VFX_NAME = "Blood Splatter";
            public static final String VFX_TYPE = "blood";
            public static final String DIRECTORY = "Resources/industrial-zone/vfx/2 Blood";
            public static final String EFFECT_PURPOSE = "Melee impact, damage feedback";
            public static final String COLOR = "Blood Red #8B0000";
            public static final int SPLATTER_TYPES = 8;
            public static final int FRAMES_PER_SPLATTER = 4;
            public static final int TIMING_MS = 80;
            public static final String ANIMATION_PATTERN = "GridFrameAnimation (4Frames1Row)";
            public static final String[] SPLATTER_FILES = new String[]{"01_VFX_Blood_Splatter_4Frames1Row_SmallWideSpread_Impact_PlayOnce_80ms.png", "02_VFX_Blood_Splatter_4Frames1Row_SmallLooseParticles_Impact_PlayOnce_80ms.png", "03_VFX_Blood_Splatter_4Frames1Row_MediumBlobShapes_Impact_PlayOnce_80ms.png", "04_VFX_Blood_Splatter_4Frames1Row_TinyFineDotsImpact_PlayOnce_80ms.png", "05_VFX_Blood_Splatter_4Frames1Row_MediumChunksImpact_PlayOnce_80ms.png", "06_VFX_Blood_Splatter_4Frames1Row_LargeBoldImpact_Impact_PlayOnce_80ms.png", "07_VFX_Blood_Splatter_4Frames1Row_HorizontalElongated_Impact_PlayOnce_80ms.png", "08_VFX_Blood_Splatter_4Frames1Row_ArcSwipeShape_Impact_PlayOnce_80ms.png"};
        }

        public static class SmokeVfx {
            public static final String VFX_NAME = "Smoke Cloud";
            public static final String VFX_TYPE = "smoke";
            public static final String DIRECTORY = "Resources/industrial-zone/vfx/1 Smoke";
            public static final String EFFECT_PURPOSE = "Explosion, impact, dissipation";
            public static final String COLOR = "White-Gray Gradient #FFFFFF-#808080";
            public static final int TOTAL_FRAMES = 18;
            public static final int TIMING_MS = 80;
            public static final int DURATION_MS = 1440;
            public static final String ANIMATION_PATTERN = "SequenceFrameAnimation";
            public static final String[] FRAME_FILES = new String[]{"01_VFX_Smoke_Frame01_DenseThickCloud_SmokeAnim_Loop_80ms.png", "02_VFX_Smoke_Frame02_DenseCloud_SmokeAnim_Loop_80ms.png", "03_VFX_Smoke_Frame03_DenseCloud_SmokeAnim_Loop_80ms.png", "04_VFX_Smoke_Frame04_DenseWithWisps_SmokeAnim_Loop_80ms.png", "05_VFX_Smoke_Frame05_DissipatingWisps_SmokeAnim_Loop_80ms.png", "06_VFX_Smoke_Frame06_DissipatgLarger_SmokeAnim_Loop_80ms.png", "07_VFX_Smoke_Frame07_ThinningCloud_SmokeAnim_Loop_80ms.png", "08_VFX_Smoke_Frame08_SparseDotsSmoke_SmokeAnim_Loop_80ms.png", "09_VFX_Smoke_Frame09_VerySparse_SmokeAnim_Loop_80ms.png", "10_VFX_Smoke_Frame10_FineParticles_SmokeAnim_Loop_80ms.png", "11_VFX_Smoke_Frame11_MediumDensity_SmokeAnim_Loop_80ms.png", "12_VFX_Smoke_Frame12_MediumWisps_SmokeAnim_Loop_80ms.png", "13_VFX_Smoke_Frame13_WideSparse_SmokeAnim_Loop_80ms.png", "14_VFX_Smoke_Frame14_FaintParticles_SmokeAnim_Loop_80ms.png", "15_VFX_Smoke_Frame15_NearTransparent_SmokeAnim_Loop_80ms.png", "16_VFX_Smoke_Frame16_FadingThin_SmokeAnim_Loop_80ms.png", "17_VFX_Smoke_Frame17_AlmostGone_SmokeAnim_Loop_80ms.png", "18_VFX_Smoke_Frame18_LastFaintest_SmokeAnim_Loop_80ms.png"};
        }
    }

    public static class BossCharacterAssetProperties {

        public static class GolfCartSoldierBoss {
            public static final String BOSS_NAME = "Golf Cart Soldier";
            public static final String BOSS_TYPE = "golf_cart_soldier";
            public static final String DIRECTORY = "Resources/industrial-zone/characters/bosses/GolfCartSoldier";
            public static final String COLOR_UNIFORM = "Military Tan #BF8F54";
            public static final String COLOR_CART = "Gray-Teal #5A7A8C";
            public static final String COMBAT_STYLE = "Hybrid melee soldier and vehicle combat system";
            public static final int ESTIMATED_WIDTH = 224;
            public static final int ESTIMATED_HEIGHT = 224;
            public static final int ANIMATION_COUNT = 11;
            public static final boolean HAS_VEHICLE_MODE = true;
            public static final String[] ANIMATION_FILES = new String[]{"01_Boss_GolfSoldier_Idle_4Frames1Row_SoldierStandingIdle_DefaultIdle_Loop_150ms.png", "02_Boss_GolfSoldier_Walk_5Frames1Row_SoldierWalkingWithWeapon_Movement_Loop_100ms.png", "03_Boss_GolfSoldier_Attack_5Frames1Row_MeleeWeaponSweep_MeleeAttack_PlayOnce_70ms.png", "04_Boss_GolfSoldier_Hurt1_2Frames1Row_HitReactionVariant_TakeDamage_PlayOnce_100ms.png", "05_Boss_GolfSoldier_Death_4Frames1Row_CrawlingCollapseDeath_Death_PlayOnce_120ms.png", "06_Boss_GolfSoldier_Hurt2_2Frames1Row_SoldierHurtVariant_TakeDamage_PlayOnce_100ms.png", "07_Boss_GolfCart_Idle_4Frames1Row_CartIdleDriverSeated_DefaultIdle_Loop_150ms.png", "08_Boss_GolfCart_IdleEmpty_4Frames1Row_CartEmptyNoDriver_DefaultIdle_Loop_150ms.png", "09_Boss_GolfCart_Walk_4Frames1Row_CartMovingDriverSteering_Movement_Loop_100ms.png", "10_Boss_GolfCart_FastOut_5Frames1Row_CartFastDriveLeaningForward_Movement_Loop_80ms.png", "11_Boss_GolfCart_Death_6Frames1Row_CartDestructionDriverInCart_Death_PlayOnce_120ms.png"};
        }

        public static class RugbyGuyBoss {
            public static final String BOSS_NAME = "Rugby Guy";
            public static final String BOSS_TYPE = "rugby_guy";
            public static final String DIRECTORY = "Resources/industrial-zone/characters/bosses/RugbyGuy";
            public static final String COLOR_UNIFORM = "Green Player #2D5016";
            public static final String COLOR_ACCENTS = "Red Tribal #D94341";
            public static final String COMBAT_STYLE = "Rugby warrior with projectile and melee attacks";
            public static final int ESTIMATED_WIDTH = 224;
            public static final int ESTIMATED_HEIGHT = 224;
            public static final int ANIMATION_COUNT = 6;
            public static final boolean HAS_PROJECTILE = true;
            public static final String[] ANIMATION_FILES = new String[]{"01_Boss_RugbyGuy_Idle1_4Frames1Row_RugbyPlayerStanceCalmReady_DefaultIdle_Loop_150ms.png", "02_Boss_RugbyGuy_Idle2_6Frames1Row_RugbyPlayerStanceVariant_DefaultIdle_Loop_150ms.png", "03_Boss_RugbyGuy_Projectile_1Frame_RugbyBallThrow_RangedAttackProjectile_Single_Instant.png", "04_Boss_RugbyGuy_Charge_6Frames1Row_RugbyPlayerChargingForwardMomentum_ChargeAbility_BuildUp_120ms.png", "05_Boss_RugbyGuy_Attack1_4Frames1Row_RugbyPlayerTackleAndHorizontalPush_MeleeAttack_PlayOnce_100ms.png", "06_Boss_RugbyGuy_Attack2_8Frames1Row_RugbyPlayerStrongArmSwingCombo_HeavyAttack_PlayOnce_80ms.png"};
        }

        public static class GreenMechBoss {
            public static final String BOSS_NAME = "Green Mech";
            public static final String BOSS_TYPE = "green_mech";
            public static final String DIRECTORY = "Resources/industrial-zone/characters/bosses/GreenMech";
            public static final String COLOR_ARMOR = "Military Green #4A6741";
            public static final String COLOR_VENTS = "Cyan Energy #00FFFF";
            public static final String COMBAT_STYLE = "Heavy mechanical walker with cannon attacks";
            public static final int ESTIMATED_WIDTH = 256;
            public static final int ESTIMATED_HEIGHT = 256;
            public static final int ANIMATION_COUNT = 10;
            public static final String[] ANIMATION_FILES = new String[]{"01_Boss_GreenMech_Idle1_4Frames1Row_MechStandingIdleCannonsReady_DefaultIdle_Loop_150ms.png", "02_Boss_GreenMech_Idle2_4Frames1Row_MechStandingIdleVariant_DefaultIdle_Loop_150ms.png", "03_Boss_GreenMech_Idle3_4Frames1Row_MechStandingIdleAltVariant_DefaultIdle_Loop_150ms.png", "04_Boss_GreenMech_Idle4_4Frames1Row_MechStandingIdleFinalVariant_DefaultIdle_Loop_150ms.png", "05_Boss_GreenMech_Walk_5Frames1Row_MechSteppingForwardMovement_Movement_Loop_100ms.png", "06_Boss_GreenMech_Charge_6Frames1Row_MechChargingEnergyAttack_ChargeAbility_BuildUp_200ms.png", "07_Boss_GreenMech_Attack1_4Frames1Row_StationaryHeavyCannonBlast_RangedAttack_PlayOnce_100ms.png", "08_Boss_GreenMech_Attack2_5Frames1Row_LegStompWithFireComboPunch_HeavyAttack_PlayOnce_100ms.png", "09_Boss_GreenMech_Hit_2Frames1Row_MechTakingDamageReaction_TakeDamage_PlayOnce_100ms.png", "10_Boss_GreenMech_Death_4Frames1Row_MechCollapsing_DeathSequence_PlayOnce_120ms.png"};
        }
    }

    public static class DroneEnemyAssetProperties {

        public static class HoverPlatformProperties {
            public static final String DRONE_NAME = "Hover Platform";
            public static final String DRONE_TYPE = "hover_platform";
            public static final String DIRECTORY = "Resources/industrial-zone/characters/enemies/drones/6";
            public static final String COMBAT_BEHAVIOR = "Deployment platform with capsule drops";
            public static final String COLOR_HULL = "Metallic Blue-Purple #3B3B7F";
            public static final String COLOR_VENTS = "Orange Deployment #FF7F50";
            public static final int ESTIMATED_WIDTH = 96;
            public static final int ESTIMATED_HEIGHT = 96;
            public static final int ANIMATION_COUNT = 4;
            public static final String[] ANIMATION_CONFIGS = new String[]{"01_Drone_HoverPlatform_Walk_4Frames1Row_PrimaryHoveringMovement_Movement_Loop_150ms.png", "02_Drone_HoverPlatform_Walk2_4Frames1Row_AlternativeMovementPattern_Movement_Loop_100ms.png", "03_Drone_HoverPlatform_Drop_5Frames1Row_PlatformDeploymentSequence_Special_PlayOnce_100ms.png", "04_Drone_HoverPlatform_Capsule_7Frames1Row_CapsuleProjectileLaunch_Attack_PlayOnce_100ms.png"};
        }

        public static class ArmoredTruckVariantProperties {
            public static final String DRONE_NAME = "Armored Truck (Variant)";
            public static final String DRONE_TYPE = "armored_truck_variant";
            public static final String DIRECTORY = "Resources/industrial-zone/characters/enemies/drones/5_2";
            public static final String COMBAT_BEHAVIOR = "Faction variant - identical to Type 5";
            public static final String COLOR_ARMOR = "Orange Faction #FF8C42";
            public static final String COLOR_ACCENT = "Red Panels #D94341";
            public static final int ESTIMATED_WIDTH = 96;
            public static final int ESTIMATED_HEIGHT = 64;
            public static final int ANIMATION_COUNT = 3;
            public static final String[] ANIMATION_CONFIGS = new String[]{"01_Drone_ArmoredTruckVariant_Idle_4Frames1Row_VariantIdleEngined_DefaultIdle_Loop_150ms.png", "02_Drone_ArmoredTruckVariant_Walk_4Frames1Row_VariantMovingForwardFrontView_Movement_Loop_100ms.png", "03_Drone_ArmoredTruckVariant_Death_5Frames1Row_VariantDestructionExplosionFlash_Death_PlayOnce_120ms.png"};
        }

        public static class ArmoredTruckProperties {
            public static final String DRONE_NAME = "Armored Truck";
            public static final String DRONE_TYPE = "armored_truck";
            public static final String DIRECTORY = "Resources/industrial-zone/characters/enemies/drones/5";
            public static final String COMBAT_BEHAVIOR = "Ground tank with ramming attacks";
            public static final String COLOR_ARMOR = "Metallic Gray #5A5A5A";
            public static final String COLOR_ACCENT = "Blue Panels #4A7BA7";
            public static final int ESTIMATED_WIDTH = 96;
            public static final int ESTIMATED_HEIGHT = 64;
            public static final int ANIMATION_COUNT = 3;
            public static final String[] ANIMATION_CONFIGS = new String[]{"01_Drone_ArmoredTruck_Idle_4Frames1Row_StationaryIdleEngineing_DefaultIdle_Loop_150ms.png", "02_Drone_ArmoredTruck_Movement_4Frames1Row_MovingForwardDrivingMovement_Movement_Loop_100ms.png", "03_Drone_ArmoredTruck_Death_5Frames1Row_VehicleDestructionExplosion_Death_PlayOnce_120ms.png"};
        }

        public static class HelicopterProperties {
            public static final String DRONE_NAME = "Helicopter Drone";
            public static final String DRONE_TYPE = "helicopter";
            public static final String DIRECTORY = "Resources/industrial-zone/characters/enemies/drones/4";
            public static final String COMBAT_BEHAVIOR = "Patrol unit with extended landing sequence";
            public static final String COLOR_HULL = "Military Green #4A6741";
            public static final String COLOR_TAIL = "Black #000000";
            public static final int ESTIMATED_WIDTH = 128;
            public static final int ESTIMATED_HEIGHT = 96;
            public static final int ANIMATION_COUNT = 4;
            public static final int LANDING_FRAMES = 16;
            public static final String[] ANIMATION_CONFIGS = new String[]{"01_Drone_Helicopter_Idle_4Frames1Row_RotorSpinningHovering_DefaultIdle_Loop_150ms.png", "02_Drone_Helicopter_Patrol_4Frames1Row_PatrolFlightMovement_Movement_Loop_100ms.png", "03_Drone_Helicopter_Landing_16Frames1Row_ExtendedLandingSequenceDescend_Special_PlayOnce_80ms.png", "04_Drone_Helicopter_Death_1Frames1Row_GhostSilhouetteDeath_Death_PlayOnce_120ms.png"};
        }

        public static class HoverShooterProperties {
            public static final String DRONE_NAME = "Hover Shooter";
            public static final String DRONE_TYPE = "hovershooter";
            public static final String DIRECTORY = "Resources/industrial-zone/characters/enemies/drones/3";
            public static final String COMBAT_BEHAVIOR = "Stationary rapid-fire gunner, 3 fire modes";
            public static final String COLOR_HULL = "Light Gray #B0B0B0";
            public static final String COLOR_BARREL = "Orange Gun #FF8C00";
            public static final int ESTIMATED_WIDTH = 96;
            public static final int ESTIMATED_HEIGHT = 96;
            public static final int ANIMATION_COUNT = 7;
            public static final int RAPID_FIRE_INTERVAL_MS = 50;
            public static final String[] ANIMATION_CONFIGS = new String[]{"01_Drone_HoverShooter_Idle_4Frames1Row_HoveringIdleFloat_DefaultIdle_Loop_150ms.png", "02_Drone_HoverShooter_Forward_4Frames1Row_MovingForward Movement_Loop_100ms.png", "03_Drone_HoverShooter_Back_4Frames1Row_MovingBackwardRetreat_Movement_Loop_100ms.png", "04_Drone_HoverShooter_Fire1_16Frames1Row_RapidFireBurstMode1_Attack_PlayOnce_50ms.png", "05_Drone_HoverShooter_Fire2_16Frames1Row_RapidFireBurstMode2_Attack_PlayOnce_50ms.png", "06_Drone_HoverShooter_Fire3_16Frames1Row_RapidFireBurstMode3_Attack_PlayOnce_50ms.png", "07_Drone_HoverShooter_Death_8Frames1Row_ExplosionDeathSequence_Death_PlayOnce_80ms.png"};
        }

        public static class JetDroneProperties {
            public static final String DRONE_NAME = "Jet Drone";
            public static final String DRONE_TYPE = "jet_drone";
            public static final String DIRECTORY = "Resources/industrial-zone/characters/enemies/drones/2";
            public static final String COMBAT_BEHAVIOR = "Aerial fighter with missile attacks";
            public static final String COLOR_HULL = "Dark Metallic Blue #1C3A47";
            public static final String COLOR_WEAPON = "Red Vents #FF4444";
            public static final int ESTIMATED_WIDTH = 80;
            public static final int ESTIMATED_HEIGHT = 64;
            public static final int ANIMATION_COUNT = 2;
            public static final String[] ANIMATION_CONFIGS = new String[]{"01_Drone_JetDrone_Fly_6Frames1Row_AerialFighterFlyingVariant_Movement_Loop_80ms.png", "02_Drone_JetDrone_Bomb_8Frames1Row_BombPayloadProjectile_Attack_Loop_80ms.png"};
        }

        public static class UfoSaucerProperties {
            public static final String DRONE_NAME = "UFO Saucer";
            public static final String DRONE_TYPE = "ufo_saucer";
            public static final String DIRECTORY = "Resources/industrial-zone/characters/enemies/drones/1";
            public static final String COMBAT_BEHAVIOR = "Hovering scanner with beam attack";
            public static final String COLOR_HULL = "Dark Red #8B2E2E";
            public static final String COLOR_BEAM = "Cyan Scan #00FFFF";
            public static final int ESTIMATED_WIDTH = 64;
            public static final int ESTIMATED_HEIGHT = 64;
            public static final int ANIMATION_COUNT = 5;
            public static final String[] ANIMATION_CONFIGS = new String[]{"01_Drone_UfoSaucer_Idle_4Frames1Row_SaucerHoveringIdleNoBeam_DefaultIdle_Loop_150ms.png", "02_Drone_UfoSaucer_Walk_4Frames1Row_SaucerHorizontalMoveNoBeam_Movement_Loop_100ms.png", "03_Drone_UfoSaucer_Scan_8Frames1Row_SaucerStationaryScanBeamAttack_Loop_100ms.png", "04_Drone_UfoSaucer_WalkScan_6Frames1Row_SaucerMovingWithScanBeamAttack_Movement_Loop_100ms.png", "05_Drone_UfoSaucer_Death_5FramesRow_SaucerDestructionExplosion_Death_PlayOnce_120ms.png"};
        }
    }

    public static class PlayerCharacterAssetProperties {

        public static class PunkProperties {
            public static final String CHARACTER_NAME = "Punk";
            public static final String CHARACTER_TYPE = "punk";
            public static final String BASE_DIRECTORY = "Resources/industrial-zone/characters/player/punk";
            public static final String COLOR_PRIMARY = "Red/Orange #FF6B35";
            public static final String COLOR_SECONDARY = "Black Leather #1A1A1A";
            public static final String SPECIALTY = "High air control, parkour mobility";
            public static final int ESTIMATED_FRAME_WIDTH = 64;
            public static final int ESTIMATED_FRAME_HEIGHT = 96;
            public static final int TOTAL_ANIMATIONS = 24;
            public static final String[] ANIMATION_FILES = new String[]{"01_Player_Punk_Idle_5Frames1Row_CasualStandingPosture_DefaultIdle_Loop_150ms.png", "02_Player_Punk_Idle2_5Frames1Row_HipstrutIdle_SecondaryIdle_Loop_150ms.png", "03_Player_Punk_Walk_5Frames1Row_ConfidentWalkingStyle_ForwardMovement_Loop_100ms.png", "04_Player_Punk_Run_6Frames1Row_FastSprintRunning_FastMovement_Loop_80ms.png", "05_Player_Punk_Dash_4Frames1Row_QuickSlideForward_QuickDash_PlayOnce_60ms.png", "06_Player_Punk_Jump_3Frames1Row_HighVerticalJump_JumpStart_PlayOnce_80ms.png", "07_Player_Punk_DoubleJump_4Frames1Row_AerialFlipJump_SecondJump_PlayOnce_80ms.png", "08_Player_Punk_Fall_3Frames1Row_FallingDescent_AirFall_Loop_100ms.png", "09_Player_Punk_Climb_4Frames1Row_ParkourClimbingMovement_ClimbLadder_Loop_120ms.png", "10_Player_Punk_Hang_4Frames1Row_ExtendedHangingLedge_LedgeHang_Loop_150ms.png", "11_Player_Punk_Pullup_7Frames1Row_AcrobaticPullUpAnimation_PlayOnce_80ms.png", "12_Player_Punk_Punch_6Frames1Row_QuickComboPunches_MeleeAttack_PlayOnce_70ms.png", "13_Player_Punk_Attack1_5Frames1Row_FirstComboAttack_Attack1_PlayOnce_70ms.png", "14_Player_Punk_Attack2_6Frames1Row_SecondAttackVariant_Attack2_PlayOnce_70ms.png", "15_Player_Punk_Attack3_6Frames1Row_TripleStrikeCombo_Attack3_PlayOnce_70ms.png", "16_Player_Punk_WalkAttack_5Frames1Row_WalkingAttackFlow_MovAttack_PlayOnce_80ms.png", "17_Player_Punk_RunAttack_6Frames1Row_RunningStrikeCombo_RunAttack_PlayOnce_70ms.png", "18_Player_Punk_Hurt_2Frames1Row_FlincheReactionDamage_TakeDamage_PlayOnce_100ms.png", "19_Player_Punk_Death_5Frames1Row_DramaticFallDeathSequence_PlayerDeath_PlayOnce_120ms.png", "20_Player_Punk_Use_5Frames1Row_CasualObjectUseInteraction_Interact_PlayOnce_100ms.png", "21_Player_Punk_Sitdown_3Frames1Row_RelaxedSittingPosture_Sit_PlayOnce_120ms.png", "22_Player_Punk_Angry_5Frames1Row_RageExpressionEmote_Emote_Loop_150ms.png", "23_Player_Punk_Happy_5Frames1Row_VictoryCelebrationEmote_Emote_Loop_150ms.png", "24_Player_Punk_Talk_5Frames1Row_CasualTalkingDialogue_Dialogue_Loop_120ms.png"};
        }

        public static class CyborgProperties {
            public static final String CHARACTER_NAME = "Cyborg";
            public static final String CHARACTER_TYPE = "cyborg";
            public static final String BASE_DIRECTORY = "Resources/industrial-zone/characters/player/cyborg";
            public static final String COLOR_PRIMARY = "Cyan Blue #00DDFF";
            public static final String COLOR_SECONDARY = "Purple Electronics #9D4EDD";
            public static final String SPECIALTY = "Tech-enhanced movement, powered attacks";
            public static final int ESTIMATED_FRAME_WIDTH = 64;
            public static final int ESTIMATED_FRAME_HEIGHT = 96;
            public static final int TOTAL_ANIMATIONS = 24;
            public static final String[] ANIMATION_FILES = new String[]{"01_Player_Cyborg_Idle_4Frames1Row_StandingTechBreathing_DefaultIdle_Loop_150ms.png", "02_Player_Cyborg_Idle2_5Frames1Row_AlternateStandingSecondaryIdle_Loop_150ms.png", "03_Player_Cyborg_Walk_5Frames1Row_TechEnhancedWalkingCycleForwardMovement_Loop_100ms.png", "04_Player_Cyborg_Run_5Frames1Row_FastSynchronizedRunningCycle_Loop_80ms.png", "05_Player_Cyborg_Dash_4Frames1Row_BoostDashWithTechEffect_QuickDash_PlayOnce_60ms.png", "06_Player_Cyborg_Jump_3Frames1Row_PoweredJumpArc_JumpStart_PlayOnce_80ms.png", "07_Player_Cyborg_DoubleJump_5Frames1Row_SecondMidAirJump_SecondJump_PlayOnce_80ms.png", "08_Player_Cyborg_Fall_3Frames1Row_FallingWithStabilization_AirFall_Loop_100ms.png", "09_Player_Cyborg_Climb_4Frames1Row_ClimbingWithArmAssists_ClimbLadder_Loop_120ms.png", "10_Player_Cyborg_Hang_3Frames1Row_HangingWithGripStrength_LedgeHang_Loop_150ms.png", "11_Player_Cyborg_Pullup_7Frames1Row_PoweredPullUpAnimation_PlayOnce_80ms.png", "12_Player_Cyborg_Punch_5Frames1Row_EnhancedPunchCombo_MeleeAttack_PlayOnce_70ms.png", "13_Player_Cyborg_Attack1_5Frames1Row_FirstCyberAttack_Attack1_PlayOnce_70ms.png", "14_Player_Cyborg_Attack2_6Frames1Row_ExtendedComboAttack_Attack2_PlayOnce_70ms.png", "15_Player_Cyborg_Attack3_6Frames1Row_TripleAttackVariation_Attack3_PlayOnce_70ms.png", "16_Player_Cyborg_WalkAttack_5Frames1Row_WalkingAttackMovement_MovAttack_PlayOnce_80ms.png", "17_Player_Cyborg_RunAttack_5Frames1Row_RunningStrike_RunAttack_PlayOnce_70ms.png", "18_Player_Cyborg_Hurt_2Frames1Row_DamageReactionFlinch_TakeDamage_PlayOnce_100ms.png", "19_Player_Cyborg_Death_5Frames1Row_ShutdownSequence_PlayerDeath_PlayOnce_120ms.png", "20_Player_Cyborg_Use_5Frames1Row_TechInterfaceInteraction_Interact_PlayOnce_100ms.png", "21_Player_Cyborg_Sitdown_3Frames1Row_CrouchingPosture_Sit_PlayOnce_120ms.png", "22_Player_Cyborg_Angry_5Frames1Row_AggressionModeEmote_Emote_Loop_150ms.png", "23_Player_Cyborg_Happy_5Frames1Row_SystemSuccessEmote_Emote_Loop_150ms.png", "24_Player_Cyborg_Talk_5Frames1Row_VoiceSynthesisTalk_Dialogue_Loop_120ms.png"};
        }

        public static class BikerProperties {
            public static final String CHARACTER_NAME = "Biker";
            public static final String CHARACTER_TYPE = "biker";
            public static final String BASE_DIRECTORY = "Resources/industrial-zone/characters/player/biker";
            public static final String COLOR_PRIMARY = "Magenta Pink #E84B8A";
            public static final String COLOR_SECONDARY = "Black/Dark Gray";
            public static final String SPECIALTY = "Fast attacks, aggressive combos";
            public static final int ESTIMATED_FRAME_WIDTH = 64;
            public static final int ESTIMATED_FRAME_HEIGHT = 96;
            public static final int TOTAL_ANIMATIONS = 24;
            public static final String[] ANIMATION_FILES = new String[]{"01_Player_Biker_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png", "02_Player_Biker_Idle2_5Frames1Row_AlternateStandLoop_SecondaryIdle_Loop_150ms.png", "03_Player_Biker_Walk_5Frames1Row_WalkingCycle_ForwardMovement_Loop_100ms.png", "04_Player_Biker_Run_5Frames1Row_FullRunCycle_FastMovement_Loop_80ms.png", "05_Player_Biker_Dash_4Frames1Row_DashSlideForward_QuickDashPayOnce_60ms.png", "06_Player_Biker_Jump_3Frames1Row_JumpRiseArc_JumpStart_PlayOnce_80ms.png", "07_Player_Biker_DoubleJump_5Frames1Row_MidAirJumpBoost_SecondumJump_PlayOnce_80ms.png", "08_Player_Biker_Fall_4Frames1Row_FallingDescent_AirFall_Loop_100ms.png", "09_Player_Biker_Climb_4Frames1Row_LadderClimbing_ClimbLadder_Loop_120ms.png", "10_Player_Biker_Hang_3Frames1Row_HangingholdLedge_LedgeHang_Loop_150ms.png", "11_Player_Biker_Pullup_7Frames1Row_PullupPullUproPlatform_PlayOnce_80ms.png", "12_Player_Biker_Punch_6Frames1Row_StandingPunchCombo_MeleeAttack_PlayOnce_70ms.png", "13_Player_Biker_Attack1_5Frames1Row_ComboHit1Swing_AttackSwing_1_PlayOnce_70ms.png", "14_Player_Biker_Attack2_6Frames1Row_ComboHit2Swing_Attack2_PlayOnce_70ms.png", "15_Player_Biker_Attack3_7Frames1Row_ComboHit3ReleaseAttack3_PlayOnce_70ms.png", "16_Player_Biker_WalkAttack_5Frames1Row_WalkingAttackSwing_MovAttack_PlayOnce_80ms.png", "17_Player_Biker_RunAttack_6Frames1Row_RunningAttackSwing_RunAttack_PlayOnce_70ms.png", "18_Player_Biker_Hurt_2Frames1Row_HitReactionFlinch_TakeDamage_PlayOnce_100ms.png", "19_Player_Biker_Death_5Frames1Row_DeathFallSequence_PlayerDeath_PlayOnce_120ms.png", "20_Player_Biker_Use_5Frames1Row_InteractUseObject_Interact_PlayOnce_100ms.png", "21_Player_Biker_Sitdown_3Frames1Row_SitDownTransition_Sit_PlayOnce_120ms.png", "22_Player_Biker_Angry_5Frames1Row_AngryExpressionEmote_Emote_Loop_150ms.png", "23_Player_Biker_Happy_5Frames1Row_HappyExpressionEmote_Emote_Loop_150ms.png", "24_Player_Biker_Talk_5Frames1Row_TalkingMouthMove_Dialogue_Loop_120ms.png"};
        }
    }

    public static class CategorySpriteRegistry
    extends AssetType {
        private String dirPath;
        private Map<String, BufferedImage> spriteRegistry;
        private Map<String, String> spriteToPath;

        public CategorySpriteRegistry(String string, String string2) {
            super(string, string2);
            this.dirPath = string2;
            this.spriteRegistry = new HashMap<String, BufferedImage>();
            this.spriteToPath = new HashMap<String, String>();
        }

        @Override
        public boolean load() {
            try {
                File file = new File(this.dirPath);
                if (!file.isDirectory()) {
                    throw new IOException("Path is not a directory: " + this.dirPath);
                }
                this.buildRegistry(file);
                if (this.spriteRegistry.isEmpty()) {
                    throw new IOException("No sprites found in directory");
                }
                AnimationAndSpriteLoader.log("\u2713 Category sprite registry initialized: " + this.assetName);
                AnimationAndSpriteLoader.log("  Sprites registered: " + this.spriteRegistry.size());
                AnimationAndSpriteLoader.log("  Directory: " + this.dirPath);
                return true;
            }
            catch (Exception exception) {
                AnimationAndSpriteLoader.logError("Failed to initialize sprite registry: " + this.assetName);
                AnimationAndSpriteLoader.logError("Path: " + this.dirPath);
                AnimationAndSpriteLoader.logError("Reason: " + exception.getMessage());
                return false;
            }
        }

        private void buildRegistry(File file2) throws IOException {
            File[] fileArray = file2.listFiles(file -> file.isFile() && this.isPNGFile(file.getName()));
            if (fileArray == null) {
                fileArray = new File[]{};
            }
            for (File file3 : fileArray) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(file3);
                    if (bufferedImage == null) continue;
                    String string = this.generateSpriteKey(file3.getName());
                    this.spriteRegistry.put(string, bufferedImage);
                    this.spriteToPath.put(string, file3.getAbsolutePath());
                }
                catch (IOException iOException) {
                    AnimationAndSpriteLoader.logError("Skipped sprite: " + file3.getName() + " - " + iOException.getMessage());
                }
            }
        }

        private String generateSpriteKey(String string) {
            String string2 = string.replaceAll("\\.png$", "");
            string2 = string2.toLowerCase().replaceAll("\\s+", "_");
            string2 = string2.replaceAll("[^a-z0-9_]", "");
            return string2;
        }

        private boolean isPNGFile(String string) {
            return string.toLowerCase().endsWith(".png");
        }

        public BufferedImage getSprite(String string) {
            BufferedImage bufferedImage = this.spriteRegistry.get(string);
            if (bufferedImage != null) {
                return bufferedImage;
            }
            for (String string2 : this.spriteRegistry.keySet()) {
                if (!string2.equalsIgnoreCase(string) && !string2.contains(string)) continue;
                return this.spriteRegistry.get(string2);
            }
            AnimationAndSpriteLoader.logError("Sprite not found: " + string);
            return null;
        }

        public String[] listSprites() {
            return this.spriteRegistry.keySet().toArray(new String[0]);
        }

        public String getSpritePath(String string) {
            return this.spriteToPath.get(string);
        }

        @Override
        public BufferedImage getFrame(int n) {
            String[] stringArray = this.spriteRegistry.keySet().toArray(new String[0]);
            if (n < 0 || n >= stringArray.length) {
                AnimationAndSpriteLoader.logError("Sprite index out of bounds: " + n + " (total: " + stringArray.length + ")");
                return null;
            }
            return this.spriteRegistry.get(stringArray[n]);
        }

        @Override
        public int getFrameCount() {
            return this.spriteRegistry.size();
        }

        @Override
        public int getFrameWidth() {
            if (this.spriteRegistry.isEmpty()) {
                return 0;
            }
            BufferedImage bufferedImage = this.spriteRegistry.values().iterator().next();
            return bufferedImage.getWidth();
        }

        @Override
        public int getFrameHeight() {
            if (this.spriteRegistry.isEmpty()) {
                return 0;
            }
            BufferedImage bufferedImage = this.spriteRegistry.values().iterator().next();
            return bufferedImage.getHeight();
        }
    }

    public static class StateVariantLoader
    extends AssetType {
        private String[] stateKeys;
        private Map<String, BufferedImage> variantMap;
        private List<BufferedImage> variantList;
        private String dirPath;

        public StateVariantLoader(String string, String string2, String[] stringArray) {
            super(string, string2);
            this.dirPath = string2;
            this.stateKeys = stringArray;
            this.variantMap = new HashMap<String, BufferedImage>();
            this.variantList = new ArrayList<BufferedImage>();
        }

        @Override
        public boolean load() {
            try {
                File file = new File(this.dirPath);
                if (!file.isDirectory()) {
                    throw new IOException("Path is not a directory: " + this.dirPath);
                }
                this.loadVariantSprites(file);
                if (this.variantMap.isEmpty()) {
                    throw new IOException("No variant sprites found");
                }
                AnimationAndSpriteLoader.log("\u2713 State variant loader initialized: " + this.assetName);
                AnimationAndSpriteLoader.log("  States loaded: " + this.variantMap.size());
                for (String string : this.variantMap.keySet()) {
                    BufferedImage bufferedImage = this.variantMap.get(string);
                    AnimationAndSpriteLoader.log("    [" + string + "] " + bufferedImage.getWidth() + "x" + bufferedImage.getHeight() + "px");
                }
                return true;
            }
            catch (Exception exception) {
                AnimationAndSpriteLoader.logError("Failed to load state variants: " + this.assetName);
                AnimationAndSpriteLoader.logError("Path: " + this.dirPath);
                AnimationAndSpriteLoader.logError("Reason: " + exception.getMessage());
                return false;
            }
        }

        private void loadVariantSprites(File file2) throws IOException {
            Object[] objectArray = file2.listFiles(file -> file.isFile() && this.isPNGFile(file.getName()));
            if (objectArray == null) {
                objectArray = new File[]{};
            }
            Arrays.sort(objectArray);
            for (Object object : objectArray) {
                BufferedImage bufferedImage = ImageIO.read((File)object);
                if (bufferedImage == null) continue;
                String string = ((File)object).getName();
                for (String string2 : this.stateKeys) {
                    if (!string.contains(string2)) continue;
                    this.variantMap.put(string2, bufferedImage);
                    this.variantList.add(bufferedImage);
                    AnimationAndSpriteLoader.log("  Loaded: " + string2 + " <- " + string);
                    break;
                }
                if (this.variantMap.containsValue(bufferedImage)) continue;
                String string3 = "variant_" + this.variantList.size();
                this.variantMap.put(string3, bufferedImage);
                this.variantList.add(bufferedImage);
            }
        }

        private boolean isPNGFile(String string) {
            return string.toLowerCase().endsWith(".png");
        }

        public BufferedImage getVariant(String string) {
            BufferedImage bufferedImage = this.variantMap.get(string);
            if (bufferedImage == null) {
                AnimationAndSpriteLoader.logError("State variant not found: " + string);
            }
            return bufferedImage;
        }

        public BufferedImage getVariant(int n) {
            if (n < 0 || n >= this.variantList.size()) {
                AnimationAndSpriteLoader.logError("Variant index out of bounds: " + n + " (total: " + this.variantList.size() + ")");
                return null;
            }
            return this.variantList.get(n);
        }

        @Override
        public BufferedImage getFrame(int n) {
            return this.getVariant(n);
        }

        @Override
        public int getFrameCount() {
            return this.variantList.size();
        }

        @Override
        public int getFrameWidth() {
            if (this.variantList.isEmpty()) {
                return 0;
            }
            return this.variantList.get(0).getWidth();
        }

        @Override
        public int getFrameHeight() {
            if (this.variantList.isEmpty()) {
                return 0;
            }
            return this.variantList.get(0).getHeight();
        }

        public int getVariantCount() {
            return this.variantMap.size();
        }
    }

    public static class SequenceFrameAnimationLoader
    extends AssetType {
        private int expectedFrameCount;
        private List<BufferedImage> frames;
        private List<String> framePaths;

        public SequenceFrameAnimationLoader(String string, String string2, int n) {
            super(string, string2);
            this.expectedFrameCount = n;
            this.frames = new ArrayList<BufferedImage>();
            this.framePaths = new ArrayList<String>();
        }

        @Override
        public boolean load() {
            try {
                File file = new File(this.filePath);
                if (!file.isDirectory()) {
                    throw new IOException("Path is not a directory: " + this.filePath);
                }
                this.loadSequenceFrames(file);
                if (this.frames.isEmpty()) {
                    throw new IOException("No frame files found in directory");
                }
                AnimationAndSpriteLoader.log("\u2713 Sequence animation loaded: " + this.assetName);
                AnimationAndSpriteLoader.log("  Frames loaded: " + this.frames.size() + " (expected: " + this.expectedFrameCount + ")");
                if (!this.frames.isEmpty()) {
                    BufferedImage bufferedImage = this.frames.get(0);
                    AnimationAndSpriteLoader.log("  Frame size: " + bufferedImage.getWidth() + "x" + bufferedImage.getHeight() + "px");
                }
                return true;
            }
            catch (Exception exception) {
                AnimationAndSpriteLoader.logError("Failed to load sequence animation: " + this.assetName);
                AnimationAndSpriteLoader.logError("Path: " + this.filePath);
                AnimationAndSpriteLoader.logError("Reason: " + exception.getMessage());
                return false;
            }
        }

        private void loadSequenceFrames(File file3) throws IOException {
            File[] fileArray = file3.listFiles();
            if (fileArray == null) {
                throw new IOException("Unable to list files in directory");
            }
            Arrays.sort(fileArray, (file, file2) -> {
                int n = this.extractFrameNumber(file.getName());
                int n2 = this.extractFrameNumber(file2.getName());
                return Integer.compare(n, n2);
            });
            this.frames.clear();
            this.framePaths.clear();
            for (File file4 : fileArray) {
                if (!file4.isFile() || !this.isPNGFile(file4.getName())) continue;
                try {
                    BufferedImage bufferedImage = ImageIO.read(file4);
                    if (bufferedImage == null) continue;
                    this.frames.add(bufferedImage);
                    this.framePaths.add(file4.getAbsolutePath());
                }
                catch (IOException iOException) {
                    AnimationAndSpriteLoader.logError("Skipped frame file: " + file4.getName() + " - " + iOException.getMessage());
                }
            }
        }

        private int extractFrameNumber(String string) {
            Pattern pattern = Pattern.compile("(\\d+)");
            Matcher matcher = pattern.matcher(string);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
            return Integer.MAX_VALUE;
        }

        private boolean isPNGFile(String string) {
            return string.toLowerCase().endsWith(".png");
        }

        @Override
        public BufferedImage getFrame(int n) {
            if (n < 0 || n >= this.frames.size()) {
                AnimationAndSpriteLoader.logError("Frame index out of bounds: " + n + " (total: " + this.frames.size() + ")");
                return null;
            }
            return this.frames.get(n);
        }

        @Override
        public int getFrameCount() {
            return this.frames.size();
        }

        @Override
        public int getFrameWidth() {
            if (this.frames.isEmpty()) {
                return 0;
            }
            return this.frames.get(0).getWidth();
        }

        @Override
        public int getFrameHeight() {
            if (this.frames.isEmpty()) {
                return 0;
            }
            return this.frames.get(0).getHeight();
        }

        public List<String> getFramePaths() {
            return new ArrayList<String>(this.framePaths);
        }
    }

    public static class GridFrameAnimationLoader
    extends AssetType {
        private int frameWidth;
        private int frameHeight;
        private int frameCount;
        private int columns;
        private List<BufferedImage> frames = new ArrayList<BufferedImage>();

        public GridFrameAnimationLoader(String string, String string2) {
            super(string, string2);
        }

        @Override
        public boolean load() {
            try {
                this.loadImageFile();
                this.parseGridConfigFromFilename();
                this.extractGridFrames();
                AnimationAndSpriteLoader.log("\u2713 Grid frame animation loaded: " + this.assetName);
                AnimationAndSpriteLoader.log("  Grid layout: " + this.columns + " columns | " + this.frameCount + " total frames");
                AnimationAndSpriteLoader.log("  Frame size: " + this.frameWidth + "x" + this.frameHeight + "px");
                AnimationAndSpriteLoader.log("  Source: " + this.width + "x" + this.height + "px");
                return true;
            }
            catch (Exception exception) {
                AnimationAndSpriteLoader.logError("Failed to load grid frame animation: " + this.assetName);
                AnimationAndSpriteLoader.logError("Path: " + this.filePath);
                AnimationAndSpriteLoader.logError("Reason: " + exception.getMessage());
                return false;
            }
        }

        private void parseGridConfigFromFilename() throws IllegalArgumentException {
            String string = new File(this.filePath).getName();
            String string2 = string.toLowerCase();
            Pattern pattern = Pattern.compile("(\\d+)Frames(\\d+)(?:Row|Rows|Col|Cols)", 2);
            Matcher matcher = pattern.matcher(string);
            if (string2.contains("1row") || string2.contains("1rows")) {
                int[] nArray = new int[]{2, 4, 6, 8, 10, 12, 14, 16, 20, 24};
                int n = 4;
                int n2 = this.width / 4;
                double d = Double.MAX_VALUE;
                for (int n3 : nArray) {
                    double d2;
                    double d3;
                    double d4;
                    int n4;
                    if (this.width % n3 != 0 || (n4 = this.width / n3) < 32 || n4 > 256 || !((d4 = (d3 = Math.abs((d2 = (double)n4 / (double)this.height) - 1.0)) * 1000.0) < d)) continue;
                    d = d4;
                    n = n3;
                    n2 = n4;
                }
                this.frameCount = n;
                this.columns = n;
                this.frameWidth = n2;
                this.frameHeight = this.height;
                AnimationAndSpriteLoader.logError("[METADATA-FIRST] Horizontal layout: analyzing image metadata");
                AnimationAndSpriteLoader.logError("  Image: " + this.width + "x" + this.height + ", Detected frames: " + this.frameCount + " (" + this.frameWidth + "x" + this.frameHeight + " each)");
                return;
            }
            if (matcher.find()) {
                int n = Integer.parseInt(matcher.group(1));
                int n5 = Integer.parseInt(matcher.group(2));
                if (string.contains("Row") || string.contains("row")) {
                    this.columns = this.frameCount = n;
                    this.frameWidth = this.width / this.columns;
                    this.frameHeight = this.height;
                } else if (string.contains("Col") || string.contains("col")) {
                    this.frameCount = n;
                    this.columns = n5;
                    this.frameWidth = this.width / this.columns;
                    this.frameHeight = this.height / (this.frameCount / this.columns);
                } else {
                    this.columns = this.frameCount = n;
                    this.frameWidth = this.width / this.columns;
                    this.frameHeight = this.height;
                }
            } else {
                throw new IllegalArgumentException("Cannot parse grid layout from filename: " + string + "\nExpected format like: '4Frames1Row' or '6Frames2Rows'");
            }
        }

        private void extractGridFrames() {
            this.frames.clear();
            int n = (this.frameCount + this.columns - 1) / this.columns;
            for (int i = 0; i < this.frameCount; ++i) {
                int n2 = i / this.columns;
                int n3 = i % this.columns;
                int n4 = n3 * this.frameWidth;
                int n5 = n2 * this.frameHeight;
                if (n4 + this.frameWidth > this.width || n5 + this.frameHeight > this.height) {
                    AnimationAndSpriteLoader.logError("Frame " + i + " exceeds bounds at (" + n4 + "," + n5 + ")");
                    continue;
                }
                BufferedImage bufferedImage = this.image.getSubimage(n4, n5, this.frameWidth, this.frameHeight);
                this.frames.add(bufferedImage);
            }
            AnimationAndSpriteLoader.log("  Extracted " + this.frames.size() + " frames from " + n + " rows, " + this.columns + " columns");
        }

        @Override
        public BufferedImage getFrame(int n) {
            if (n < 0 || n >= this.frames.size()) {
                AnimationAndSpriteLoader.logError("Frame index out of bounds: " + n + " (total: " + this.frames.size() + ")");
                return null;
            }
            return this.frames.get(n);
        }

        @Override
        public int getFrameCount() {
            return this.frameCount;
        }

        @Override
        public int getFrameWidth() {
            return this.frameWidth;
        }

        @Override
        public int getFrameHeight() {
            return this.frameHeight;
        }
    }

    public static class GameStateManager {
        private PlayerController player;
        private List<EnemyController> enemies = new ArrayList<EnemyController>();
        private BossController boss;
        private EnvironmentController environment;
        private List<ProjectileController> projectiles = new ArrayList<ProjectileController>();
        private List<VFXController> vfxEffects = new ArrayList<VFXController>();
        private InputHandler input = new InputHandler();

        public GameStateManager() {
            this.environment = new EnvironmentController(1);
        }

        public void initialize(PhysicsUnitSystem.PhysicsBody physicsBody) {
            this.player = new PlayerController(physicsBody, this.input);
        }

        public void update(float f) {
            int n;
            this.input.clearFrame();
            if (this.player != null) {
                this.player.update(f);
                this.player.physics.update(f);
            }
            for (EnemyController entityAnimationController2 : this.enemies) {
                entityAnimationController2.update(f);
                if (this.player != null) {
                    entityAnimationController2.updateAI(this.player.physics);
                }
                entityAnimationController2.physics.update(f);
            }
            if (this.boss != null) {
                this.boss.update(f);
                if (this.player != null) {
                    this.boss.updateBehavior(this.player.physics.position);
                } else {
                    this.boss.updateBehavior(new PhysicsUnitSystem.Vector2D(0.0f, 0.0f));
                }
                this.boss.physics.update(f);
            }
            for (n = this.projectiles.size() - 1; n >= 0; --n) {
                ProjectileController projectileController = this.projectiles.get(n);
                projectileController.update(f);
                if (projectileController.isAlive()) continue;
                this.projectiles.remove(n);
            }
            for (n = this.vfxEffects.size() - 1; n >= 0; --n) {
                VFXController vFXController = this.vfxEffects.get(n);
                vFXController.update(f);
                if (!vFXController.isComplete()) continue;
                this.vfxEffects.remove(n);
            }
        }

        public void addEnemy(EnemyController enemyController) {
            this.enemies.add(enemyController);
        }

        public void setBoss(BossController bossController) {
            this.boss = bossController;
        }

        public void addProjectile(ProjectileController projectileController) {
            this.projectiles.add(projectileController);
        }

        public void addVFX(VFXController vFXController) {
            this.vfxEffects.add(vFXController);
        }

        public PlayerController getPlayer() {
            return this.player;
        }

        public List<EnemyController> getEnemies() {
            return this.enemies;
        }

        public BossController getBoss() {
            return this.boss;
        }

        public InputHandler getInput() {
            return this.input;
        }

        public EnvironmentController getEnvironment() {
            return this.environment;
        }
    }

    public static class VFXController
    extends EntityAnimationController {
        private long effectStartTime = System.currentTimeMillis();

        public VFXController(PhysicsUnitSystem.PhysicsBody physicsBody) {
            super(physicsBody);
        }

        @Override
        protected void initializeAssets() {
            this.stateToAssetPath.put(AnimationState.SPARKLE_BURST, "Resources/industrial-zone/vfx/1 Smoke/01_VFX_Smoke_Frame01_DenseThickCloud_SmokeAnim_Loop_80ms.png");
            this.stateToAssetPath.put(AnimationState.IMPACT_HIT, "Resources/industrial-zone/vfx/2 Blood/01_VFX_Blood_Splatter_4Frames1Row_SmallWideSpread_Impact_PlayOnce_80ms.png");
            this.stateToAssetPath.put(AnimationState.ENERGY_BEAM, "Resources/industrial-zone/vfx/3 Sparks/01_VFX_Sparks_Burst_4Frames1Row_SmallSparseGold_Impact_PlayOnce_80ms.png");
            this.stateToAssetPath.put(AnimationState.EXPLOSION, "Resources/industrial-zone/vfx/4 Particles/01_VFX_Particles_Green_4Frames1Row_TinyGreenSparse_Ambient_Loop_100ms.png");
        }

        @Override
        protected void initializeTransitions() {
        }

        @Override
        protected void updatePhysicsForState(AnimationState animationState, float f) {
        }

        public boolean isComplete() {
            long l = System.currentTimeMillis() - this.effectStartTime;
            return l > (long)(this.currentState.frameCount * this.currentState.frameTimingMs);
        }

        public static String getVFXAssetPath(VFXType vFXType, String string) {
            switch (vFXType.ordinal()) {
                case 0: {
                    return AnimationAndSpriteLoader.VFX_SMOKE + VFXController.getSmokeEffect(string);
                }
                case 1: {
                    return AnimationAndSpriteLoader.VFX_BLOOD + VFXController.getBloodEffect(string);
                }
                case 2: {
                    return AnimationAndSpriteLoader.VFX_SPARKS + VFXController.getSparkEffect(string);
                }
                case 3: {
                    return AnimationAndSpriteLoader.VFX_PARTICLES + VFXController.getParticleEffect(string);
                }
                case 4: {
                    return AnimationAndSpriteLoader.VFX_OTHER + VFXController.getStarEffect(string);
                }
                case 5: {
                    return AnimationAndSpriteLoader.VFX_EXTRA_CHARACTER + VFXController.getCharacterEffect(string);
                }
                case 6: {
                    return AnimationAndSpriteLoader.VFX_EXTRA_OBJECTS + VFXController.getDestructionEffect(string);
                }
            }
            return "Resources/industrial-zone/vfx/1 Smoke/01_VFX_Smoke_Frame01_DenseThickCloud_SmokeAnim_Loop_80ms.png";
        }

        private static String getSmokeEffect(String string) {
            switch (string.toUpperCase()) {
                case "DENSE": {
                    return "01_VFX_Smoke_Frame01_DenseThickCloud_SmokeAnim_Loop_80ms.png";
                }
                case "MEDIUM": {
                    return "02_VFX_Smoke_Frame10_MediumDensity_ThinningSmoke_Loop_90ms.png";
                }
                case "LIGHT": {
                    return "03_VFX_Smoke_Frame18_LightDissipating_FadingSmoke_Loop_100ms.png";
                }
            }
            return "01_VFX_Smoke_Frame01_DenseThickCloud_SmokeAnim_Loop_80ms.png";
        }

        private static String getBloodEffect(String string) {
            switch (string.toUpperCase()) {
                case "SPLATTER_1": {
                    return "01_VFX_Blood_Splatter_4Frames1Row_SmallWideSpread_Impact_PlayOnce_80ms.png";
                }
                case "SPLATTER_2": {
                    return "02_VFX_Blood_Splatter_4Frames1Row_MediumBurst_ImpactMid_PlayOnce_80ms.png";
                }
                case "SPLATTER_3": {
                    return "03_VFX_Blood_Splatter_4Frames1Row_LargeExplosion_ImpactBig_PlayOnce_80ms.png";
                }
                case "SPLATTER_4": {
                    return "04_VFX_Blood_Splatter_4Frames1Row_VerticalSpray_ImpactUp_PlayOnce_80ms.png";
                }
                case "SPLATTER_5": {
                    return "05_VFX_Blood_Splatter_4Frames1Row_DiagonalDrip_ImpactDown_PlayOnce_80ms.png";
                }
                case "SPLATTER_6": {
                    return "06_VFX_Blood_Splatter_4Frames1Row_WideShatter_ImpactWide_PlayOnce_80ms.png";
                }
                case "SPLATTER_7": {
                    return "07_VFX_Blood_Splatter_4Frames1Row_HeavyDrop_ImpactHeavy_PlayOnce_80ms.png";
                }
                case "SPLATTER_8": {
                    return "08_VFX_Blood_Splatter_4Frames1Row_TinyScatter_ImpactSmall_PlayOnce_80ms.png";
                }
            }
            return "01_VFX_Blood_Splatter_4Frames1Row_SmallWideSpread_Impact_PlayOnce_80ms.png";
        }

        private static String getSparkEffect(String string) {
            switch (string.toUpperCase()) {
                case "GOLD_BURST": {
                    return "01_VFX_Sparks_Burst_4Frames1Row_SmallSparseGold_Impact_PlayOnce_80ms.png";
                }
                case "RED_BURST": {
                    return "02_VFX_Sparks_Burst_4Frames1Row_MediumIntenseRed_Impact_PlayOnce_80ms.png";
                }
                case "BLUE_BURST": {
                    return "03_VFX_Sparks_Burst_4Frames1Row_LargeElectricBlue_Impact_PlayOnce_80ms.png";
                }
                case "WHITE_BURST": {
                    return "04_VFX_Sparks_Burst_4Frames1Row_BrightWhiteFlash_Impact_PlayOnce_80ms.png";
                }
            }
            return "01_VFX_Sparks_Burst_4Frames1Row_SmallSparseGold_Impact_PlayOnce_80ms.png";
        }

        private static String getParticleEffect(String string) {
            switch (string.toUpperCase()) {
                case "GREEN": {
                    return "01_VFX_Particles_Green_4Frames1Row_TinyGreenSparse_Ambient_Loop_100ms.png";
                }
                case "BLUE": {
                    return "02_VFX_Particles_Blue_4Frames1Row_TinyBlueSparse_Ambient_Loop_100ms.png";
                }
                case "ORANGE": {
                    return "03_VFX_Particles_Orange_4Frames1Row_TinyOrangeSparse_Ambient_Loop_100ms.png";
                }
                case "CYAN": {
                    return "04_VFX_Particles_Cyan_4Frames1Row_TinyCyanDense_Energy_Loop_100ms.png";
                }
            }
            return "01_VFX_Particles_Green_4Frames1Row_TinyGreenSparse_Ambient_Loop_100ms.png";
        }

        private static String getStarEffect(String string) {
            switch (string.toUpperCase()) {
                case "STAR_BURST": {
                    return "01_VFX_Stars_Burst_GoldenSparkles_CelebrationEffect_Loop.png";
                }
                case "CYAN_SHARDS": {
                    return "02_VFX_Shards_Cyan_CrystalBreak_GlassEffect_PlayOnce.png";
                }
                case "ENERGY_SHARDS": {
                    return "03_VFX_Shards_Electric_EnergyBurst_StaticCrackle_PlayOnce.png";
                }
            }
            return "01_VFX_Stars_Burst_GoldenSparkles_CelebrationEffect_Loop.png";
        }

        private static String getCharacterEffect(String string) {
            switch (string.toUpperCase()) {
                case "DEATH": {
                    return "01_VFX_Character_Biker_Death_ExplosionRing_RedBlackSmoke_PlayOnce.png";
                }
                case "DOUBLEJUMP": {
                    return "02_VFX_Character_Biker_DoubleJump_BlueEnergyRing_PropulsionAura_PlayOnce.png";
                }
                case "HURT": {
                    return "03_VFX_Character_Biker_Hurt_RedFlash_DamageIndicator_PlayOnce.png";
                }
                case "JUMP": {
                    return "04_VFX_Character_Biker_Jump_GroundDust_BootThrustSmoke_PlayOnce.png";
                }
                case "RUN": {
                    return "05_VFX_Character_Biker_Run_SpeedLines_MomentumTrail_Loop.png";
                }
            }
            return "01_VFX_Character_Biker_Death_ExplosionRing_RedBlackSmoke_PlayOnce.png";
        }

        private static String getDestructionEffect(String string) {
            switch (string.toUpperCase()) {
                case "BOX_DESTROY_1": {
                    return "01_VFX_Objects_Box1_Destruction_WoodSplinter_5Frame.png";
                }
                case "BOX_DESTROY_2": {
                    return "02_VFX_Objects_Box2_Destruction_Crumble_4Frame.png";
                }
                case "BUSH_DESTROY": {
                    return "03_VFX_Objects_Bush_Destruction_Leaves_4Frame.png";
                }
                case "CAPSULE_DESTROY": {
                    return "04_VFX_Objects_Capsule_Destruction_Rupture_5Frame.png";
                }
                case "METAL_BREAK": {
                    return "05_VFX_Objects_Metal_Destruction_Clang_3Frame.png";
                }
            }
            return "01_VFX_Objects_Box1_Destruction_WoodSplinter_5Frame.png";
        }

        public static enum VFXType {
            SMOKE,
            BLOOD,
            SPARKS,
            PARTICLES,
            STARS,
            CHARACTER_EFFECTS,
            DESTRUCTION;

        }
    }

    public static class ProjectileController
    extends EntityAnimationController {
        private float damage;
        private float lifetime;
        private long spawnTime;

        public ProjectileController(PhysicsUnitSystem.PhysicsBody physicsBody, float f) {
            super(physicsBody);
            this.damage = f;
            this.lifetime = 5.0f;
            this.spawnTime = System.currentTimeMillis();
        }

        @Override
        protected void initializeAssets() {
            this.stateToAssetPath.put(AnimationState.SPARKLE_BURST, "Resources/industrial-zone/weapons/1/5 Bullets/01_Weapon_Bullet_TypeA_Single_StaticSprite.png");
            this.stateToAssetPath.put(AnimationState.IMPACT_HIT, "Resources/industrial-zone/weapons/1/5 Bullets/02_Weapon_Bullet_TypeB_Single_StaticSprite.png");
            this.stateToAssetPath.put(AnimationState.ENERGY_BEAM, "Resources/industrial-zone/weapons/1/5 Bullets/03_Weapon_Bullet_TypeC_Single_StaticSprite.png");
            this.stateToAssetPath.put(AnimationState.EXPLOSION, "Resources/industrial-zone/weapons/1/5 Bullets/06_Weapon_Bullet_TypeE_VariantA_StaticSprite.png");
        }

        @Override
        protected void initializeTransitions() {
        }

        @Override
        protected void updatePhysicsForState(AnimationState animationState, float f) {
            this.physics.isAffectedByGravity = true;
            this.physics.update(f);
        }

        public boolean isAlive() {
            long l = System.currentTimeMillis() - this.spawnTime;
            return (float)l < this.lifetime * 1000.0f;
        }

        public float getDamage() {
            return this.damage;
        }
    }

    public static class EnvironmentController {
        private Map<Integer, AnimationState> tileStates = new HashMap<Integer, AnimationState>();
        private Map<String, String> tileAssets = new HashMap<String, String>();
        private Map<String, String> backgroundAssets = new HashMap<String, String>();
        private Map<String, String> objectAssets = new HashMap<String, String>();
        private Map<String, String> animatedObjectAssets = new HashMap<String, String>();
        private float parallaxOffset = 0.0f;
        private int currentLevel;

        public EnvironmentController(int n) {
            this.currentLevel = n;
            this.initializeAssets();
        }

        private void initializeAssets() {
            this.tileAssets.put("FLOOR_SOLID_PRIMARY", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/01_Platform_SolidBlock_FlatTopFull_DarkPurple_PrimaryWalkableFloorTile.png");
            this.tileAssets.put("FLOOR_STANDARD", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/03_Platform_SolidBlock_FlatTopMid_MutedBluePurple_StandardFloorFill.png");
            this.tileAssets.put("CORNER_INNER_RIGHT", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/04_Corner_InnerTopRight_LShapeCutout_SolidEdge_WallMeetsFloorJoinTopRight.png");
            this.tileAssets.put("CORNER_INNER_LEFT", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/06_Corner_InnerTopLeft_NotchedTopLeft_SolidEdge_WallMeetsFloorJoinTopLeft.png");
            this.tileAssets.put("WALL_VERTICAL", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/08_Wall_VerticalColumn_NarrowCentreAligned_TallRectangle_ShaftOrPillarMidFil.png");
            this.tileAssets.put("PANEL_GRID", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/07_Panel_GridSurface_2x2QuadDivided_FlatIndustrialFace_WallOrFloorPanelFill.png");
            this.tileAssets.put("HAZARD_BREAKABLE", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/02_Hazard_BreakableBlock_LargeXCrosshatch_PurpleOnDark_WarningSurfaceOrDestr.png");
            this.tileAssets.put("HAZARD_WARNING", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/1 Tiles/09_Hazard_WarningSurface_SingleDiagonalRedStripe_BlueBase_ContactDamageHazar.png");
            this.backgroundAssets.put("SKY_BASE", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer1_SkyBase_SolidLavenderGrey_StaticFill_DrawFirstNoScroll.png");
            this.backgroundAssets.put("PARALLAX_LAYER_1", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer2_FractalTreeSilhouette_MintSkyBlackCracks_ParallaxFactor015.png");
            this.backgroundAssets.put("PARALLAX_LAYER_2", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer3_FarFactorySilhouette_LightBlueIndustrial_ParallaxFactor025.png");
            this.backgroundAssets.put("PARALLAX_LAYER_3", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer4_MidFactorySilhouette_MediumBluePipeDetail_ParallexFactor040.png");
            this.backgroundAssets.put("PARALLAX_LAYER_4", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/2 Background_level_1/BG_Layer5_NearFactorySilhouette_DarkNavyLargeTank_ParallaxFactor060.png");
            this.objectAssets.put("PROP_BARREL_UPRIGHT", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Barrel_UprightRedMetal_StandardLabel_FloorDecorOrPushable_VariantA.png");
            this.objectAssets.put("PROP_BARREL_TALL", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Barrel_TallDarkRed_WornIndustrial_FloorDecorOrPushable_TallVariant.png");
            this.objectAssets.put("PROP_BENCH", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Bench_BlueMetal_FlatTopWorkbench_WallPlacedDeco_IndustrialFurniture.png");
            this.objectAssets.put("PROP_BOARD_SINGLE", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/3 Objects/Prop_Board_SingleWoodenNoticeBoard_PapersOnWall_WallMountedDeco_VariantA.png");
            this.animatedObjectAssets.put("COLLECTIBLE_COIN", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Collectible_Money_6Frames1Row_GreenBanknotesSpinFlip_CurrencyPickup_Loop80ms.png");
            this.animatedObjectAssets.put("COLLECTIBLE_CARD", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Collectible_Card_6Frames1Row_WhiteBlueSpinningFloat_PickupItem_Loop80ms.png");
            this.animatedObjectAssets.put("CONVEYOR_BELT", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Platform_ConveyorFull_4Frames1Row_FullWidthBeltRunning_MovesPlayerRight_Loop80ms.png");
            this.animatedObjectAssets.put("MOVING_PLATFORM", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Platform_MovingRed_6Frames1Row_SlidingLeftRight_PlayerRideable_Loop100ms.png");
            this.animatedObjectAssets.put("SCREEN_DECORATION", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Deco_Screen1_4Frames1Row_BlueMonitorFlicker_WallPanelTechDeco_Loop150ms.png");
            this.animatedObjectAssets.put("CHEST_INTERACTIVE", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Interactive_Chest_8Frames1Row_OrangeRedLidOpenSequence_PlayOnce100ms.png");
            this.animatedObjectAssets.put("HAZARD_HAMMER", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Hazard_Hammer_6Frames1Row_RedOrangeSwingArc_DamageFrames3to5_Loop90ms.png");
            this.animatedObjectAssets.put("PORTAL_LEVEL_ENTRY", "Resources/industrial-zone/1 Tiles/Industrial_zone_level_1/4 Animated objects/Anim_Portal_LevelEntry_4Frames1Row_RedChevronGateOpening_LevelTransition_PlayOnce100ms.png");
        }

        public void updateParallax(float f) {
            this.parallaxOffset = f * 0.3f;
        }

        public void setTileAnimation(int n, AnimationState animationState) {
            this.tileStates.put(n, animationState);
        }

        public AnimationState getTileAnimation(int n) {
            return this.tileStates.getOrDefault(n, AnimationState.TILE_DEFAULT);
        }

        public String getTileAsset(String string) {
            return this.tileAssets.getOrDefault(string, null);
        }

        public String getBackgroundAsset(String string) {
            return this.backgroundAssets.getOrDefault(string, null);
        }

        public String getObjectAsset(String string) {
            return this.objectAssets.getOrDefault(string, null);
        }

        public String getAnimatedObjectAsset(String string) {
            return this.animatedObjectAssets.getOrDefault(string, null);
        }

        public float getParallaxOffset() {
            return this.parallaxOffset;
        }

        public int getCurrentLevel() {
            return this.currentLevel;
        }
    }

    public static class DroneController
    extends EntityAnimationController {
        private static final float DRONE_HEIGHT_OFFSET = 1.5f;
        private float detectionRadius;
        private boolean isAlerted;
        private AIBehavior aiBehavior;
        private float baseGroundY;

        public DroneController(PhysicsUnitSystem.PhysicsBody physicsBody, float f, float f2) {
            super(physicsBody);
            this.detectionRadius = f;
            this.isAlerted = false;
            this.baseGroundY = f2;
            this.aiBehavior = null;
            this.physics.position.y = f2 - 1.5f;
        }

        public void setAIBehavior(AIBehavior aIBehavior) {
            this.aiBehavior = aIBehavior;
        }

        @Override
        protected void initializeAssets() {
            this.stateToAssetPath.put(AnimationState.ENEMY_IDLE, "Resources/industrial-zone/characters/enemies/drones/1/01_EnemyDrone_UfoSaucer_Idle_3Frames1Row_HoveringStationary_DefaultIdle_Loop_150ms.png");
            this.stateToAssetPath.put(AnimationState.ENEMY_WALK, "Resources/industrial-zone/characters/enemies/drones/1/02_EnemyDrone_UfoSaucer_Movement_4Frames1Row_SmoothHoveringMove_Movement_Loop_100ms.png");
            this.stateToAssetPath.put(AnimationState.ENEMY_CHASE, "Resources/industrial-zone/characters/enemies/drones/1/03_EnemyDrone_UfoSaucer_Chase_4Frames1Row_FastHoveringPursuit_Chase_Loop_80ms.png");
            this.stateToAssetPath.put(AnimationState.ENEMY_ATTACK, "Resources/industrial-zone/characters/enemies/drones/1/04_EnemyDrone_UfoSaucer_Attack_3Frames1Row_EnergyBeamCharge_Attack_PlayOnce_100ms.png");
            this.stateToAssetPath.put(AnimationState.ENEMY_HURT, "Resources/industrial-zone/characters/enemies/drones/1/04_EnemyDrone_UfoSaucer_Attack_3Frames1Row_EnergyBeamCharge_Attack_PlayOnce_100ms.png");
            this.stateToAssetPath.put(AnimationState.ENEMY_DEATH, "Resources/industrial-zone/characters/enemies/drones/1/05_EnemyDrone_UfoSaucer_Death_4Frames1Row_ExplosionDisappear_Death_PlayOnce_120ms.png");
        }

        @Override
        protected void initializeTransitions() {
        }

        @Override
        protected void updatePhysicsForState(AnimationState animationState, float f) {
            this.physics.isAffectedByGravity = false;
            switch (animationState.ordinal()) {
                case 16: {
                    this.physics.velocity.x = 0.0f;
                    this.physics.velocity.y = 0.0f;
                    break;
                }
                case 17: {
                    this.physics.velocity.x = 2.0f;
                    this.physics.velocity.y = 0.0f;
                    break;
                }
                case 18: {
                    this.physics.velocity.x = 5.0f;
                    this.physics.velocity.y = 0.0f;
                    break;
                }
                case 19: {
                    this.physics.velocity.x = 0.0f;
                    this.physics.velocity.y = 0.0f;
                    break;
                }
                default: {
                    this.physics.velocity.y = 0.0f;
                }
            }
        }

        public void updateAI(PhysicsUnitSystem.PhysicsBody physicsBody) {
            this.physics.position.y = this.baseGroundY - 1.5f;
            if (this.aiBehavior != null) {
                AnimationState animationState = this.aiBehavior.updateBehavior(physicsBody.position);
                this.transitionTo(animationState);
            } else {
                float f = Math.abs(this.physics.position.x - physicsBody.position.x);
                if (f < this.detectionRadius) {
                    if (!this.isAlerted) {
                        this.isAlerted = true;
                        this.physics.velocity.x *= 1.25f;
                    }
                    this.transitionTo(AnimationState.ENEMY_CHASE);
                } else {
                    if (this.isAlerted) {
                        this.isAlerted = false;
                        this.physics.velocity.x *= 0.8f;
                    }
                    this.transitionTo(AnimationState.ENEMY_IDLE);
                }
            }
        }

        public boolean isAlerted() {
            return this.isAlerted;
        }

        public float getDetectionRadius() {
            return this.detectionRadius;
        }

        public float getAltitude() {
            return 1.5f;
        }
    }

    public static class BossAIBehavior
    extends AIBehavior {
        private float healthPercent = 1.0f;
        private BossPhase currentPhase = BossPhase.PHASE_1;
        private int attackPattern = 0;
        private float combatDistance = 3.0f;

        public BossAIBehavior(PhysicsUnitSystem.PhysicsBody physicsBody, float f, float f2) {
            super(physicsBody, f, f2);
        }

        @Override
        public AnimationState updateBehavior(PhysicsUnitSystem.Vector2D vector2D) {
            float f = this.getDistanceTo(vector2D);
            this.updatePhase();
            if (f > this.attackRange) {
                int n = this.getDirectionTo(vector2D);
                this.entityBody.velocity.x = n > 0 ? 2.5f : -2.5f;
                this.currentBehaviorState = AnimationState.ENEMY_CHASE;
            } else {
                this.entityBody.velocity.x = 0.0f;
                this.currentBehaviorState = this.selectAttackState();
            }
            return this.currentBehaviorState;
        }

        private void updatePhase() {
            this.currentPhase = this.healthPercent > 0.75f ? BossPhase.PHASE_1 : (this.healthPercent > 0.25f ? BossPhase.PHASE_2 : BossPhase.PHASE_3);
        }

        private AnimationState selectAttackState() {
            this.attackPattern = (this.attackPattern + 1) % 3;
            switch (this.currentPhase.ordinal()) {
                case 0: {
                    return AnimationState.BOSS_ATTACK_PHASE1;
                }
                case 1: {
                    return AnimationState.BOSS_ATTACK_PHASE2;
                }
                case 2: {
                    return AnimationState.BOSS_SPECIAL;
                }
            }
            return AnimationState.BOSS_IDLE;
        }

        public void updateHealth(float f) {
            this.healthPercent = Math.max(0.0f, Math.min(1.0f, f));
        }

        public float getHealthPercent() {
            return this.healthPercent;
        }

        public BossPhase getCurrentPhase() {
            return this.currentPhase;
        }

        public static enum BossPhase {
            PHASE_1,
            PHASE_2,
            PHASE_3;

        }
    }

    public static class DroneAIBehavior
    extends AIBehavior {
        public static final float DRONE_HEIGHT_OFFSET = 1.5f;
        private DronePattern pattern;
        private float sweepDistance = 8.0f;
        private float hoverAltitude = 1.5f;

        public DroneAIBehavior(PhysicsUnitSystem.PhysicsBody physicsBody, float f, float f2, DronePattern dronePattern) {
            super(physicsBody, f, f2);
            this.pattern = dronePattern;
            this.hoverAltitude = physicsBody.position.y + 1.5f;
        }

        @Override
        public AnimationState updateBehavior(PhysicsUnitSystem.Vector2D vector2D) {
            float f = this.getDistanceTo(vector2D);
            this.maintainFlightAltitude();
            if (f < this.detectionRadius) {
                this.isAlerted = true;
                if (f < this.attackRange) {
                    this.currentBehaviorState = AnimationState.ENEMY_ATTACK;
                } else {
                    this.currentBehaviorState = AnimationState.ENEMY_CHASE;
                    int n = this.getDirectionTo(vector2D);
                    this.entityBody.velocity.x = n > 0 ? 5.5f : -5.5f;
                }
            } else {
                this.isAlerted = false;
                switch (this.pattern.ordinal()) {
                    case 0: {
                        this.currentBehaviorState = AnimationState.ENEMY_IDLE;
                        this.entityBody.velocity.x = 0.0f;
                        break;
                    }
                    case 1: {
                        this.updateSweepPattern();
                        break;
                    }
                    case 2: {
                        this.updateSpiralPattern();
                        break;
                    }
                    case 3: {
                        this.currentBehaviorState = AnimationState.ENEMY_IDLE;
                    }
                }
            }
            return this.currentBehaviorState;
        }

        private void maintainFlightAltitude() {
            this.entityBody.velocity.y = 0.0f;
            this.entityBody.position.y = this.hoverAltitude;
        }

        private void updateSweepPattern() {
            float f = this.entityBody.position.x;
            this.entityBody.velocity.x = f < this.hoverAltitude - this.sweepDistance ? 1.5f : (f > this.hoverAltitude + this.sweepDistance ? -1.5f : 1.5f);
            this.currentBehaviorState = AnimationState.ENEMY_WALK;
        }

        private void updateSpiralPattern() {
            long l = System.currentTimeMillis() % 4000L;
            float f = (float)l / 4000.0f * 2.0f * (float)Math.PI;
            this.entityBody.velocity.x = (float)Math.cos(f) * 2.0f;
            this.currentBehaviorState = AnimationState.ENEMY_WALK;
        }

        public void setPattern(DronePattern dronePattern) {
            this.pattern = dronePattern;
        }

        public static enum DronePattern {
            HOVER,
            SWEEP,
            SPIRAL,
            AGGRESSIVE_PURSUIT;

        }
    }

    public static class EnemyAIBehavior
    extends AIBehavior {
        private EnemyPattern pattern;
        private float patrolDistance = 5.0f;
        private float patrolStartX;
        private boolean patrolDirectionRight = true;

        public EnemyAIBehavior(PhysicsUnitSystem.PhysicsBody physicsBody, float f, float f2, EnemyPattern enemyPattern) {
            super(physicsBody, f, f2);
            this.pattern = enemyPattern;
            this.patrolStartX = physicsBody.position.x;
        }

        @Override
        public AnimationState updateBehavior(PhysicsUnitSystem.Vector2D vector2D) {
            float f = this.getDistanceTo(vector2D);
            if (f < this.detectionRadius) {
                this.isAlerted = true;
                this.alertTime = System.currentTimeMillis();
                if (f < this.attackRange) {
                    this.currentBehaviorState = AnimationState.ENEMY_ATTACK;
                    this.entityBody.velocity.x = 0.0f;
                } else {
                    this.currentBehaviorState = AnimationState.ENEMY_CHASE;
                    int n = this.getDirectionTo(vector2D);
                    this.entityBody.velocity.x = n > 0 ? 4.0f : -4.0f;
                }
            } else {
                this.isAlerted = false;
                this.currentBehaviorState = AnimationState.ENEMY_IDLE;
                this.entityBody.velocity.x = 0.0f;
                switch (this.pattern.ordinal()) {
                    case 0: {
                        this.updatePatrol();
                        break;
                    }
                    case 1: {
                        break;
                    }
                    case 2: {
                        break;
                    }
                }
            }
            return this.currentBehaviorState;
        }

        private void updatePatrol() {
            float f = Math.abs(this.entityBody.position.x - this.patrolStartX);
            if (f >= this.patrolDistance) {
                this.patrolDirectionRight = !this.patrolDirectionRight;
            }
            this.entityBody.velocity.x = this.patrolDirectionRight ? 1.5f : -1.5f;
            this.currentBehaviorState = AnimationState.ENEMY_WALK;
        }

        public void setPattern(EnemyPattern enemyPattern) {
            this.pattern = enemyPattern;
        }

        public static enum EnemyPattern {
            PATROL_HORIZONTAL,
            PATROL_STATIONARY,
            AGGRESSIVE,
            TACTICAL;

        }
    }

    public static abstract class AIBehavior {
        protected PhysicsUnitSystem.PhysicsBody entityBody;
        protected PhysicsUnitSystem.Vector2D targetPosition;
        protected AnimationState currentBehaviorState = AnimationState.ENEMY_IDLE;
        protected float detectionRadius;
        protected float attackRange;
        protected boolean isAlerted;
        protected long alertTime;

        public AIBehavior(PhysicsUnitSystem.PhysicsBody physicsBody, float f, float f2) {
            this.entityBody = physicsBody;
            this.detectionRadius = f;
            this.attackRange = f2;
            this.isAlerted = false;
            this.alertTime = System.currentTimeMillis();
        }

        public abstract AnimationState updateBehavior(PhysicsUnitSystem.Vector2D var1);

        protected float getDistanceTo(PhysicsUnitSystem.Vector2D vector2D) {
            if (vector2D == null) {
                return Float.MAX_VALUE;
            }
            float f = this.entityBody.position.x - vector2D.x;
            float f2 = this.entityBody.position.y - vector2D.y;
            return (float)Math.sqrt(f * f + f2 * f2);
        }

        protected int getDirectionTo(PhysicsUnitSystem.Vector2D vector2D) {
            if (vector2D == null) {
                return 0;
            }
            float f = vector2D.x - this.entityBody.position.x;
            if (Math.abs(f) < 0.1f) {
                return 0;
            }
            return f > 0.0f ? 1 : -1;
        }

        public AnimationState getCurrentState() {
            return this.currentBehaviorState;
        }

        public boolean isAlerted() {
            return this.isAlerted;
        }

        public void setAlerted(boolean bl) {
            this.isAlerted = bl;
            this.alertTime = System.currentTimeMillis();
        }
    }

    public static abstract class EntityAnimationController {
        protected AnimationState currentState;
        protected AnimationState previousState;
        protected long stateStartTime;
        protected int currentFrame;
        protected PhysicsUnitSystem.PhysicsBody physics;
        protected float stateAnimationSpeed;
        protected Map<AnimationState, String> stateToAssetPath;
        protected Map<AnimationState, StateTransition> validTransitions;

        public EntityAnimationController(PhysicsUnitSystem.PhysicsBody physicsBody) {
            this.physics = physicsBody;
            this.currentState = AnimationState.IDLE;
            this.previousState = AnimationState.IDLE;
            this.stateStartTime = System.currentTimeMillis();
            this.currentFrame = 0;
            this.stateToAssetPath = new HashMap<AnimationState, String>();
            this.validTransitions = new HashMap<AnimationState, StateTransition>();
            this.initializeAssets();
            this.initializeTransitions();
        }

        protected abstract void initializeAssets();

        protected abstract void initializeTransitions();

        public void update(float f) {
            long l = System.currentTimeMillis() - this.stateStartTime;
            AnimationState animationState = this.currentState;
            int n = animationState.frameTimingMs;
            int n2 = animationState.frameCount;
            this.currentFrame = (int)(l / (long)n) % n2;
        }

        protected abstract void updatePhysicsForState(AnimationState var1, float var2);

        public boolean transitionTo(AnimationState animationState) {
            if (!this.isValidTransition(this.currentState, animationState)) {
                return false;
            }
            this.previousState = this.currentState;
            this.currentState = animationState;
            this.stateStartTime = System.currentTimeMillis();
            this.currentFrame = 0;
            return true;
        }

        protected boolean isValidTransition(AnimationState animationState, AnimationState animationState2) {
            return true;
        }

        public AnimationState getCurrentState() {
            return this.currentState;
        }

        public int getCurrentFrame() {
            return this.currentFrame;
        }

        public String getAssetPath() {
            return this.stateToAssetPath.getOrDefault((Object)this.currentState, "");
        }
    }

    public static class InputController {
        private InputHandler baseInput;
        private AnimationState currentState = AnimationState.IDLE;
        private AnimationState previousState = AnimationState.IDLE;
        private boolean isMoving = false;
        private boolean facingRight = true;
        public static final int KEY_W = 87;
        public static final int KEY_K = 75;
        public static final int KEY_L = 76;
        public static final int KEY_H = 72;
        public static final int KEY_T = 84;
        public static final int KEY_Q = 81;
        public static final int KEY_R = 82;
        public static final int KEY_E = 69;
        public static final int KEY_X = 88;
        public static final int KEY_V = 86;
        public static final int KEY_F = 70;
        public static final int KEY_P = 80;
        public static final int KEY_CTRL = 17;
        public static final int MOUSE_LEFT = -1;

        public InputController(InputHandler inputHandler) {
            this.baseInput = inputHandler;
        }

        public AnimationState updateAndGetState() {
            this.previousState = this.currentState;
            if (this.baseInput.isKeyPressed(16)) {
                if (this.baseInput.isKeyPressed(37)) {
                    this.currentState = AnimationState.DASH_LEFT;
                    this.facingRight = false;
                    return this.currentState;
                }
                if (this.baseInput.isKeyPressed(39)) {
                    this.currentState = AnimationState.DASH_RIGHT;
                    this.facingRight = true;
                    return this.currentState;
                }
            }
            if (this.baseInput.isKeyPressed(75)) {
                this.currentState = this.baseInput.isKeyPressed(16) ? AnimationState.ATTACK_MELEE : (this.baseInput.isKeyPressed(17) ? AnimationState.ATTACK_MELEE : AnimationState.ATTACK_MELEE);
                return this.currentState;
            }
            if (this.baseInput.isKeyPressed(76)) {
                this.currentState = AnimationState.ATTACK_RANGE;
                return this.currentState;
            }
            if (this.baseInput.isKeyPressed(72)) {
                this.currentState = AnimationState.HANG;
                return this.currentState;
            }
            if (this.baseInput.isKeyPressed(84)) {
                this.currentState = AnimationState.IDLE;
                return this.currentState;
            }
            if (this.baseInput.isKeyPressed(81)) {
                this.currentState = AnimationState.IDLE;
                return this.currentState;
            }
            if (this.baseInput.isKeyPressed(87)) {
                this.currentState = AnimationState.CLIMB;
                return this.currentState;
            }
            if (this.baseInput.isKeyPressed(88)) {
                this.currentState = AnimationState.WALL_SLIDE;
                return this.currentState;
            }
            if (this.baseInput.isKeyPressed(32)) {
                this.currentState = AnimationState.JUMP;
                return this.currentState;
            }
            if (this.baseInput.isKeyPressed(37)) {
                this.currentState = AnimationState.WALK_LEFT;
                this.facingRight = false;
                this.isMoving = true;
                return this.currentState;
            }
            if (this.baseInput.isKeyPressed(39)) {
                this.currentState = AnimationState.WALK_RIGHT;
                this.facingRight = true;
                this.isMoving = true;
                return this.currentState;
            }
            this.currentState = AnimationState.IDLE;
            this.isMoving = false;
            return this.currentState;
        }

        public AnimationState getCurrentState() {
            return this.currentState;
        }

        public AnimationState getPreviousState() {
            return this.previousState;
        }

        public boolean isFacingRight() {
            return this.facingRight;
        }

        public boolean isMoving() {
            return this.isMoving;
        }

        public boolean isInputPressed(AnimationState animationState) {
            switch (animationState.ordinal()) {
                case 1: {
                    return this.baseInput.isKeyPressed(37);
                }
                case 2: {
                    return this.baseInput.isKeyPressed(39);
                }
                case 3: {
                    return this.baseInput.isKeyPressed(32);
                }
                case 7: {
                    return this.baseInput.isKeyPressed(16) && this.baseInput.isKeyPressed(37);
                }
                case 8: {
                    return this.baseInput.isKeyPressed(16) && this.baseInput.isKeyPressed(39);
                }
                case 9: {
                    return this.baseInput.isKeyPressed(87);
                }
                case 12: {
                    return this.baseInput.isKeyPressed(75);
                }
                case 13: {
                    return this.baseInput.isKeyPressed(76);
                }
                case 10: {
                    return this.baseInput.isKeyPressed(72);
                }
                case 11: {
                    return this.baseInput.isKeyPressed(88);
                }
            }
            return false;
        }

        public InputHandler getBaseInput() {
            return this.baseInput;
        }
    }

    public static class StateTransition {
        public AnimationState fromState;
        public AnimationState toState;
        public float gravityMultiplier;
        public float velocityMultiplier;
        public boolean canInterrupt;
        public String description;

        public StateTransition(AnimationState animationState, AnimationState animationState2, float f, float f2, boolean bl, String string) {
            this.fromState = animationState;
            this.toState = animationState2;
            this.gravityMultiplier = f;
            this.velocityMultiplier = f2;
            this.canInterrupt = bl;
            this.description = string;
        }

        public String toString() {
            return String.format("%s \u2192 %s (gravity:%.2f, vel:%.2f, interrupt:%s)", new Object[]{this.fromState, this.toState, Float.valueOf(this.gravityMultiplier), Float.valueOf(this.velocityMultiplier), this.canInterrupt});
        }
    }

    public static enum AnimationState {
        IDLE("idle", 1, 0),
        WALK_LEFT("walk_left", 8, 100),
        WALK_RIGHT("walk_right", 8, 100),
        JUMP("jump", 6, 80),
        DOUBLE_JUMP("double_jump", 8, 70),
        FALL("fall", 4, 100),
        LAND("land", 4, 80),
        DASH_LEFT("dash_left", 6, 60),
        DASH_RIGHT("dash_right", 6, 60),
        CLIMB("climb", 6, 120),
        HANG("hang", 2, 150),
        WALL_SLIDE("wall_slide", 4, 100),
        ATTACK_MELEE("attack_melee", 6, 70),
        ATTACK_RANGE("attack_range", 4, 80),
        HURT("hurt", 3, 100),
        DEATH("death", 8, 100),
        ENEMY_IDLE("enemy_idle", 2, 200),
        ENEMY_WALK("enemy_walk", 6, 120),
        ENEMY_CHASE("enemy_chase", 8, 80),
        ENEMY_ATTACK("enemy_attack", 5, 100),
        ENEMY_HURT("enemy_hurt", 3, 80),
        ENEMY_DEATH("enemy_death", 6, 120),
        BOSS_IDLE("boss_idle", 1, 200),
        BOSS_ATTACK_PHASE1("boss_phase1", 8, 90),
        BOSS_ATTACK_PHASE2("boss_phase2", 10, 80),
        BOSS_SPECIAL("boss_special", 12, 70),
        BOSS_WEAK("boss_weak", 4, 100),
        BOSS_DEATH("boss_death", 10, 120),
        TILE_DEFAULT("tile_default", 1, 0),
        TILE_ANIMATED("tile_animated", 4, 150),
        HAZARD_ACTIVE("hazard", 4, 100),
        SPARKLE_BURST("sparkle", 8, 60),
        IMPACT_HIT("impact", 6, 80),
        ENERGY_BEAM("energy", 5, 100),
        EXPLOSION("explosion", 10, 70),
        GUI_IDLE("gui_idle", 1, 0),
        GUI_BUTTON_HOVER("button_hover", 1, 0),
        GUI_BUTTON_PRESS("button_press", 3, 100);

        public final String filename;
        public final int frameCount;
        public final int frameTimingMs;

        private AnimationState(String string2, int n2, int n3) {
            this.filename = string2;
            this.frameCount = n2;
            this.frameTimingMs = n3;
        }
    }
}
