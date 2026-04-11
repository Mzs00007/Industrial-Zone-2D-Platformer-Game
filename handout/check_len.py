import os
path = "c:\\Users\\ZAID SIDDIQUI\\OneDrive - University of Stirling\\stir uni\\SEMESTERS\\sem6 2026\\CSCU9N6\\N6AssignmentCode\\handout\\Resources\\industrial-zone\\characters\\bosses\\GreenMech"
new_name = "01_Boss_GreenMech_Idle1_4Frames1Row_MechStandingIdleCannonsReady_DefaultIdle_Loop_150ms.png"
full_path = os.path.join(path, new_name)
print(f"Path length: {len(path)}")
print(f"New name length: {len(new_name)}")
print(f"Full path length: {len(full_path)}")
print(f"Windows limit: 260 (without UNC prefix)")
print(f"Exceeds limit: {len(full_path) >= 260}")
