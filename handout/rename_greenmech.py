import os
path = "c:\\Users\\ZAID SIDDIQUI\\OneDrive - University of Stirling\\stir uni\\SEMESTERS\\sem6 2026\\CSCU9N6\\N6AssignmentCode\\handout\\Resources\\industrial-zone\\characters\\bosses\\GreenMech"
renames = {
    "1.png": "01_Boss_GreenMech_Idle1_4Frames1Row_MechStandingIdleCannonsReady_DefaultIdle_Loop_150ms.png",
    "3.png": "03_Boss_GreenMech_Idle3_4Frames1Row_MechStandingIdleAltVariant_DefaultIdle_Loop_150ms.png",
    "4.png": "04_Boss_GreenMech_Idle4_4Frames1Row_MechStandingIdleFinalVariant_DefaultIdle_Loop_150ms.png",
    "6.png": "06_Boss_GreenMech_Charge_6Frames1Row_MechChargingEnergyAttack_ChargeAbility_BuildUp_200ms.png",
    "7.png": "07_Boss_GreenMech_Attack1_4Frames1Row_StationaryHeavyCannonBlast_RangedAttack_PlayOnce_100ms.png",
    "8.png": "08_Boss_GreenMech_Attack2_5Frames1Row_LegStompWithFireComboPunch_HeavyAttack_PlayOnce_100ms.png",
    "9.png": "09_Boss_GreenMech_Hit_2Frames1Row_MechTakingDamageReaction_TakeDamage_PlayOnce_100ms.png"
}
for old, new in renames.items():
    old_path = os.path.join(path, old)
    new_path = os.path.join(path, new)
    if os.path.exists(old_path):
        os.rename(old_path, new_path)
        print(f"✓ {old} → {new}")
    else:
        print(f"✗ {old} not found")
print("\n=== FINAL LIST ===")
files = sorted([f for f in os.listdir(path) if f.endswith('.png')])
for f in files:
    print(f)
