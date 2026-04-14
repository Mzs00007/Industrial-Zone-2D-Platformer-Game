/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.util.HashMap;

class AnimationAndSpriteLoader.3
extends HashMap<String, AnimationAndSpriteLoader.AudioTrack> {
    AnimationAndSpriteLoader.3() {
        this.put("level1_theme", new AnimationAndSpriteLoader.AudioTrack("level1_theme", "Resources/industrial-zone/audio/music/level1_ambient.mid", 0.6f, true));
        AnimationAndSpriteLoader.AudioTrack audioTrack = new AnimationAndSpriteLoader.AudioTrack("level1_combat", "Resources/industrial-zone/audio/music/level1_combat.mid", 0.7f, true);
        audioTrack.setBPM(130);
        audioTrack.setFades(300.0f, 500.0f);
        this.put("level1_combat", audioTrack);
        this.put("level2_theme", new AnimationAndSpriteLoader.AudioTrack("level2_theme", "Resources/industrial-zone/audio/music/level2_ambient.mid", 0.6f, true));
        AnimationAndSpriteLoader.AudioTrack audioTrack2 = new AnimationAndSpriteLoader.AudioTrack("boss_theme", "Resources/industrial-zone/audio/music/boss_theme.mid", 0.8f, true);
        audioTrack2.setBPM(160);
        audioTrack2.setFades(100.0f, 800.0f);
        this.put("boss_theme", audioTrack2);
        this.put("menu_theme", new AnimationAndSpriteLoader.AudioTrack("menu_theme", "Resources/industrial-zone/audio/music/menu_theme.mid", 0.5f, true));
        this.put("character_select", new AnimationAndSpriteLoader.AudioTrack("character_select", "Resources/industrial-zone/audio/music/character_select.mid", 0.55f, true));
        AnimationAndSpriteLoader.AudioTrack audioTrack3 = new AnimationAndSpriteLoader.AudioTrack("victory_theme", "Resources/industrial-zone/audio/music/victory_theme.mid", 0.7f, false);
        audioTrack3.setFades(200.0f, 1000.0f);
        this.put("victory_theme", audioTrack3);
        AnimationAndSpriteLoader.AudioTrack audioTrack4 = new AnimationAndSpriteLoader.AudioTrack("game_over_theme", "Resources/industrial-zone/audio/music/game_over.mid", 0.6f, false);
        audioTrack4.setFades(100.0f, 2000.0f);
        this.put("game_over_theme", audioTrack4);
    }
}
