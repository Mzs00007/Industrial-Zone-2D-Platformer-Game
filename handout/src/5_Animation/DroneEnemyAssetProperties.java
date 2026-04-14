/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.DroneEnemyAssetProperties {

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
