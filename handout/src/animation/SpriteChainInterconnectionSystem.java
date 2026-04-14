/*
 * Decompiled with CFR 0.152.
 */
package animation;

import java.util.LinkedHashMap;
import java.util.Map;

public static class AnimationAndSpriteLoader.SpriteChainInterconnectionSystem {
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
