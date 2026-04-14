/*
 * Decompiled with CFR 0.152.
 */
package core_game_entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AssetChainCoordinator {
    private static final boolean VERBOSE_LOGGING = true;
    private static Map<String, AssetChain> chainRegistry = new HashMap<String, AssetChain>();
    private static Map<String, AssetChainCoordinator> entityCoordinators = new HashMap<String, AssetChainCoordinator>();
    public static AssetChain CHAIN_COMBAT_FIRE = new AssetChain("combat_fire", "PlayerEntities");
    public static AssetChain CHAIN_DAMAGE_RESPONSE = new AssetChain("damage_response", "VFXEntities");
    public static AssetChain CHAIN_ENEMY_SPAWN = new AssetChain("enemy_spawn", "Enemies");
    public static AssetChain CHAIN_BOSS_BATTLE = new AssetChain("boss_battle", "BossEntities");
    public static AssetChain CHAIN_HAZARD_HIT = new AssetChain("hazard_hit", "TilesEntities");

    private static void log(String string) {
        System.out.println("[AssetChainCoordinator] " + string);
    }

    private static void logError(String string) {
        System.err.println("[AssetChainCoordinator] ERROR: " + string);
    }

    public static AssetChain getAssetChain(String string) {
        AssetChainCoordinator.log("Retrieving chain: " + string);
        return chainRegistry.get(string);
    }

    public static List<String> getAssetsFromEntityInChain(String string, String string2) {
        AssetChain assetChain = chainRegistry.get(string);
        if (assetChain == null) {
            AssetChainCoordinator.logError("Chain not found: " + string);
            return new ArrayList<String>();
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Map.Entry<String, String> entry : assetChain.assetToEntityMap.entrySet()) {
            if (!entry.getValue().equals(string2)) continue;
            arrayList.add(entry.getKey());
        }
        if (!arrayList.isEmpty()) {
            AssetChainCoordinator.log("Found " + arrayList.size() + " assets from " + string2 + " in " + string);
        }
        return arrayList;
    }

    public static List<AssetChain> getFollowingChains(String string) {
        AssetChain assetChain = chainRegistry.get(string);
        if (assetChain == null) {
            AssetChainCoordinator.logError("Chain not found: " + string);
            return new ArrayList<AssetChain>();
        }
        ArrayList<AssetChain> arrayList = new ArrayList<AssetChain>();
        for (String string2 : assetChain.chainsIntoNext) {
            AssetChain assetChain2 = chainRegistry.get(string2);
            if (assetChain2 == null) continue;
            arrayList.add(assetChain2);
        }
        if (!arrayList.isEmpty()) {
            AssetChainCoordinator.log("Chain " + string + " leads to " + arrayList.size() + " following chains");
        }
        return arrayList;
    }

    public static Set<String> getEntitiesInChain(String string) {
        AssetChain assetChain = chainRegistry.get(string);
        if (assetChain == null) {
            AssetChainCoordinator.logError("Chain not found: " + string);
            return new HashSet<String>();
        }
        return new HashSet<String>(assetChain.assetToEntityMap.values());
    }

    public static void printChainFlow(String string) {
        AssetChain assetChain = chainRegistry.get(string);
        if (assetChain == null) {
            System.out.println("[AssetChainCoordinator] ERROR: Chain not found: " + string);
            return;
        }
        System.out.println("\n========================================================================");
        System.out.println("ASSET CHAIN FLOW: " + string.toUpperCase());
        System.out.println("========================================================================");
        String string2 = assetChain.sourcEntity;
        System.out.println("START -> [" + string2 + "]");
        for (String string3 : assetChain.assetSequence) {
            String string4 = assetChain.assetToEntityMap.get(string3);
            if (!string4.equals(string2)) {
                System.out.println("         |");
                System.out.println("         v");
                string2 = string4;
            }
            System.out.println("       [" + string2 + "] -> " + string3);
        }
        if (!assetChain.chainsIntoNext.isEmpty()) {
            System.out.println("         |");
            System.out.println("         v");
            System.out.println("  CHAINS INTO:");
            for (String string3 : assetChain.chainsIntoNext) {
                System.out.println("       -> " + string3);
            }
        }
        System.out.println();
    }

    public static void printAllChains() {
        System.out.println("\n========================================================================");
        System.out.println("COMPLETE ASSET CHAIN ECOSYSTEM");
        System.out.println("========================================================================");
        for (AssetChain iterator2 : chainRegistry.values()) {
            AssetChainCoordinator.printChainFlow(iterator2.chainId);
        }
        System.out.println("\n========================================================================");
        System.out.println("ENTITY INVOLVEMENT SUMMARY");
        System.out.println("========================================================================");
        HashSet hashSet = new HashSet();
        for (AssetChain assetChain : chainRegistry.values()) {
            hashSet.addAll(assetChain.assetToEntityMap.values());
        }
        Iterator iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            String string = (String)iterator.next();
            System.out.println("\n[" + string + "]");
            for (AssetChain assetChain : chainRegistry.values()) {
                List<String> list = AssetChainCoordinator.getAssetsFromEntityInChain(assetChain.chainId, string);
                if (list.isEmpty()) continue;
                System.out.println("  In chain '" + assetChain.chainId + "':");
                for (String string2 : list) {
                    System.out.println("    - " + string2);
                }
            }
        }
    }

    static {
        CHAIN_COMBAT_FIRE.addAsset("character_shoot_pose", "PlayerEntities");
        CHAIN_COMBAT_FIRE.addAsset("weapon_fire_animation", "WeaponsEntities");
        CHAIN_COMBAT_FIRE.addAsset("muzzle_flash", "WeaponsEntities");
        CHAIN_COMBAT_FIRE.addAsset("projectile_trail", "WeaponsEntities");
        CHAIN_COMBAT_FIRE.addAsset("impact_spark", "VFXEntities");
        CHAIN_COMBAT_FIRE.addTrigger("mouse_click");
        CHAIN_COMBAT_FIRE.chainInto("damage_response");
        AssetChainCoordinator.log("Initialized COMBAT_FIRE chain with 5 assets");
        CHAIN_DAMAGE_RESPONSE.addAsset("blood_spray", "VFXEntities");
        CHAIN_DAMAGE_RESPONSE.addAsset("knockback_animation", "VFXEntities");
        CHAIN_DAMAGE_RESPONSE.addAsset("pain_sound", "AudioEntities");
        CHAIN_DAMAGE_RESPONSE.addTrigger("collision_detected");
        AssetChainCoordinator.log("Initialized DAMAGE_RESPONSE chain with 3 assets");
        CHAIN_ENEMY_SPAWN.addAsset("enemy_materialize_effect", "VFXEntities");
        CHAIN_ENEMY_SPAWN.addAsset("enemy_idle_animation", "Enemies");
        CHAIN_ENEMY_SPAWN.addTrigger("spawn_interval_elapsed");
        CHAIN_ENEMY_SPAWN.chainInto("damage_response");
        AssetChainCoordinator.log("Initialized ENEMY_SPAWN chain with 2 assets");
        CHAIN_BOSS_BATTLE.addAsset("boss_idle", "BossEntities");
        CHAIN_BOSS_BATTLE.addAsset("boss_phase2_transition", "BossEntities");
        CHAIN_BOSS_BATTLE.addAsset("boss_phase3_transition", "BossEntities");
        CHAIN_BOSS_BATTLE.addAsset("boss_attack_sequence", "BossEntities");
        CHAIN_BOSS_BATTLE.addAsset("boss_death_explosion", "BossEntities");
        CHAIN_BOSS_BATTLE.addAsset("victory_portal_effect", "VFXEntities");
        CHAIN_BOSS_BATTLE.addTrigger("boss_health_below_66%");
        CHAIN_BOSS_BATTLE.addTrigger("boss_health_below_33%");
        AssetChainCoordinator.log("Initialized BOSS_BATTLE chain with 6 assets and 2 triggers");
        CHAIN_HAZARD_HIT.addAsset("tile_hazard_sprite", "TilesEntities");
        CHAIN_HAZARD_HIT.addAsset("hazard_damage_effect", "VFXEntities");
        CHAIN_HAZARD_HIT.addTrigger("collision_with_hazard");
        CHAIN_HAZARD_HIT.chainInto("damage_response");
        AssetChainCoordinator.log("Initialized HAZARD_HIT chain with 2 assets");
        chainRegistry.put("combat_fire", CHAIN_COMBAT_FIRE);
        chainRegistry.put("damage_response", CHAIN_DAMAGE_RESPONSE);
        chainRegistry.put("enemy_spawn", CHAIN_ENEMY_SPAWN);
        chainRegistry.put("boss_battle", CHAIN_BOSS_BATTLE);
        chainRegistry.put("hazard_hit", CHAIN_HAZARD_HIT);
        AssetChainCoordinator.log("All 5 chains registered to chainRegistry");
    }

    public static class AssetChain {
        public String chainId;
        public String sourcEntity;
        public List<String> assetSequence = new ArrayList<String>();
        public Map<String, String> assetToEntityMap = new HashMap<String, String>();
        public List<String> triggerConditions = new ArrayList<String>();
        public List<String> chainsIntoNext = new ArrayList<String>();

        public AssetChain(String string, String string2) {
            this.chainId = string;
            this.sourcEntity = string2;
        }

        public void addAsset(String string, String string2) {
            this.assetSequence.add(string);
            this.assetToEntityMap.put(string, string2);
        }

        public void addTrigger(String string) {
            this.triggerConditions.add(string);
        }

        public void chainInto(String string) {
            this.chainsIntoNext.add(string);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Chain: ").append(this.chainId).append("\n");
            stringBuilder.append("  Source: ").append(this.sourcEntity).append("\n");
            stringBuilder.append("  Assets: ");
            for (String string : this.assetSequence) {
                stringBuilder.append("\n    - ").append(string);
                stringBuilder.append(" (").append(this.assetToEntityMap.get(string)).append(")");
            }
            stringBuilder.append("\n  Triggers: ").append(this.triggerConditions);
            stringBuilder.append("\n  Chains Into: ").append(this.chainsIntoNext);
            return stringBuilder.toString();
        }
    }
}
