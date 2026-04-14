/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.util.HashMap;

class AnimationAndSpriteLoader.2
extends HashMap<String, AnimationAndSpriteLoader.AnimationMetadata> {
    AnimationAndSpriteLoader.2() {
        AnimationAndSpriteLoader.AnimationMetadata animationMetadata = new AnimationAndSpriteLoader.AnimationMetadata("drone_jet_bomb", "Resources/industrial-zone/enemies/drones/01_Drone_JetDroneVariant_BombPayload");
        animationMetadata.animationNames = new String[]{"idle", "alert", "bomb_drop", "taking_damage", "death"};
        animationMetadata.addAnimation(0, 4, 100, "none", "drone_idle_hum", 0.0f);
        animationMetadata.addAnimation(1, 6, 80, "detection_ping", "drone_alert", 0.0f);
        animationMetadata.addAnimation(2, 8, 50, "bomb_drop_effect|chain_boom", "bomb_launch", 0.6f);
        animationMetadata.addAnimation(3, 4, 60, "damage_flash", "drone_damage", 0.0f);
        animationMetadata.addAnimation(4, 10, 40, "explosion_vfx|debris_scatter", "drone_explosion", 0.0f);
        this.put("drone_jet_bomb", animationMetadata);
        AnimationAndSpriteLoader.AnimationMetadata animationMetadata2 = new AnimationAndSpriteLoader.AnimationMetadata("player_cyborg", "Resources/industrial-zone/characters");
        animationMetadata2.animationNames = new String[]{"idle", "run", "jump", "attack", "taking_damage", "death"};
        animationMetadata2.addAnimation(0, 4, 100, "none", "none", 0.0f);
        animationMetadata2.addAnimation(1, 8, 50, "run_dust", "footstep", 0.5f);
        animationMetadata2.addAnimation(2, 6, 60, "jump_effect", "jump_sound", 0.0f);
        animationMetadata2.addAnimation(3, 6, 40, "attack_slash|hit_marker", "attack_sound", 0.5f);
        animationMetadata2.addAnimation(4, 4, 60, "damage_flash|knockback", "pain_sound", 0.0f);
        animationMetadata2.addAnimation(5, 8, 50, "explosion_vfx|death_particles", "death_sound", 0.0f);
        this.put("player_cyborg", animationMetadata2);
        AnimationAndSpriteLoader.AnimationMetadata animationMetadata3 = new AnimationAndSpriteLoader.AnimationMetadata("effect_bomb_explosion", "Resources/industrial-zone/vfx");
        animationMetadata3.animationNames = new String[]{"explosion", "shockwave", "particles"};
        animationMetadata3.addAnimation(0, 16, 25, "chain_reaction_trigger", "chain_explosion", 0.0f);
        animationMetadata3.addAnimation(1, 12, 30, "knockback_force", "shockwave_sound", 0.3f);
        animationMetadata3.addAnimation(2, 20, 20, "none", "debris_scatter", 0.0f);
        this.put("effect_bomb_explosion", animationMetadata3);
    }
}
