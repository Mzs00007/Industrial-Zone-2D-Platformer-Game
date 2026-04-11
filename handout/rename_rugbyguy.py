import os
path = "c:\\Users\\ZAID SIDDIQUI\\OneDrive - University of Stirling\\stir uni\\SEMESTERS\\sem6 2026\\CSCU9N6\\N6AssignmentCode\\handout\\Resources\\industrial-zone\\characters\\bosses\\RugbyGuy"
renames = {
    "1.png": "01_Boss_RugbyGuy_Idle1_4Frames1Row_RugbyPlayerStanceCalmReady_DefaultIdle_Loop_150ms.png",
    "2.png": "02_Boss_RugbyGuy_Idle2_6Frames1Row_RugbyPlayerStanceVariant_DefaultIdle_Loop_150ms.png",
    "3.png": "03_Boss_RugbyGuy_Projectile_1Frame_RugbyBallThrow_RangedAttackProjectile_Single_Instant.png",
    "4.png": "04_Boss_RugbyGuy_Charge_6Frames1Row_RugbyPlayerChargingForwardMomentum_ChargeAbility_BuildUp_120ms.png",
    "5.png": "05_Boss_RugbyGuy_Attack1_4Frames1Row_RugbyPlayerTackleAndHorizontalPush_MeleeAttack_PlayOnce_100ms.png",
    "6.png": "06_Boss_RugbyGuy_Attack2_8Frames1Row_RugbyPlayerStrongArmSwingCombo_HeavyAttack_PlayOnce_80ms.png"
}
for old, new in renames.items():
    old_path = os.path.join(path, old)
    new_path = os.path.join(path, new)
    old_path_ext = "\\\\?\\" + os.path.abspath(old_path)
    new_path_ext = "\\\\?\\" + os.path.abspath(new_path)
    try:
        if os.path.exists(old_path):
            os.rename(old_path_ext, new_path_ext)
            print(f"✓ {old} → {new}")
    except Exception as e:
        print(f"ERROR {old}: {str(e)[:80]}")
files = sorted([f for f in os.listdir(path) if f.endswith('.png')])
print(f"\n=== FINAL LIST ({len(files)} files) ===")
for f in files:
    print(f)
