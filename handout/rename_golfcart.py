import os
path = "c:\\Users\\ZAID SIDDIQUI\\OneDrive - University of Stirling\\stir uni\\SEMESTERS\\sem6 2026\\CSCU9N6\\N6AssignmentCode\\handout\\Resources\\industrial-zone\\characters\\bosses\\GolfCartSoldier"
renames = {
    "13_Boss_GolfSoldier_Idle_4Frames1Row_SoldierStandingIdle_DefaultIdle_Loop_150ms.png": "01_Boss_GolfSoldier_Idle_4Frames1Row_SoldierStandingIdle_DefaultIdle_Loop_150ms.png",
    "14_Boss_GolfSoldier_Walk_5Frames1Row_SoldierWalkingWithWeapon_Movement_Loop_100ms.png": "02_Boss_GolfSoldier_Walk_5Frames1Row_SoldierWalkingWithWeapon_Movement_Loop_100ms.png",
    "15_Boss_GolfSoldier_Attack_5Frames1Row_MeleeWeaponSweep_MeleeAttack_PlayOnce_70ms.png": "03_Boss_GolfSoldier_Attack_5Frames1Row_MeleeWeaponSweep_MeleeAttack_PlayOnce_70ms.png",
    "19_Boss_GolfSoldier_Hurt2_2Frames1Row_HitReactionVariant_TakeDamage_PlayOnce_100ms.png": "04_Boss_GolfSoldier_Hurt1_2Frames1Row_HitReactionVariant_TakeDamage_PlayOnce_100ms.png",
    "21_Boss_GolfSoldier_Death2_4Frames1Row_CrawlingCollapseDeath_Death_PlayOnce_120ms.png": "05_Boss_GolfSoldier_Death_4Frames1Row_CrawlingCollapseDeath_Death_PlayOnce_120ms.png",
    "27_Boss_GolfSoldier_Hurt3_2Frames1Row_SoldierHurtVariant_TakeDamage_PlayOnce_100ms.png": "06_Boss_GolfSoldier_Hurt2_2Frames1Row_SoldierHurtVariant_TakeDamage_PlayOnce_100ms.png",
    "22_Boss_GolfCart_Idle_4Frames1Row_CartIdleDriverSeated_DefaultIdle_Loop_150ms.png": "07_Boss_GolfCart_Idle_4Frames1Row_CartIdleDriverSeated_DefaultIdle_Loop_150ms.png",
    "23_Boss_GolfCart_IdleEmpty_4Frames1Row_CartEmptyNoDriver_DefaultIdle_Loop_150ms.png": "08_Boss_GolfCart_IdleEmpty_4Frames1Row_CartEmptyNoDriver_DefaultIdle_Loop_150ms.png",
    "24_Boss_GolfCart_Walk_4Frames1Row_CartMovingDriverSteering_Movement_Loop_100ms.png": "09_Boss_GolfCart_Walk_4Frames1Row_CartMovingDriverSteering_Movement_Loop_100ms.png",
    "25_Boss_GolfCart_Out_5Frames1Row_CartFastDriveLeaningForward_Movement_Loop_80ms.png": "10_Boss_GolfCart_FastOut_5Frames1Row_CartFastDriveLeaningForward_Movement_Loop_80ms.png",
    "26_Boss_GolfCart_Death_6Frames1Row_CartDestructionDriverInCart_Death_PlayOnce_120ms.png": "11_Boss_GolfCart_Death_6Frames1Row_CartDestructionDriverInCart_Death_PlayOnce_120ms.png"
}
for old, new in renames.items():
    old_path = os.path.join(path, old)
    new_path = os.path.join(path, new)
    old_path_ext = "\\\\?\\" + os.path.abspath(old_path)
    new_path_ext = "\\\\?\\" + os.path.abspath(new_path)
    try:
        os.rename(old_path_ext, new_path_ext)
        print(f"✓ {old[:50]}")
    except Exception as e:
        print(f"ERROR {old}: {str(e)[:60]}")
files = sorted([f for f in os.listdir(path) if f.endswith('.png')])
print(f"\n=== FINAL LIST ({len(files)} files) ===")
for f in files:
    print(f)
