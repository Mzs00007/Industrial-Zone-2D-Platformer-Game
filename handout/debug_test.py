import os
path = "c:\\Users\\ZAID SIDDIQUI\\OneDrive - University of Stirling\\stir uni\\SEMESTERS\\sem6 2026\\CSCU9N6\\N6AssignmentCode\\handout\\Resources\\industrial-zone\\characters\\bosses\\GolfCartSoldier"
files_in_dir = os.listdir(path)
print(f"Files in directory: {len(files_in_dir)}")
test_file = files_in_dir[0]
test_path = os.path.join(path, test_file)
print(f"Test file: {test_file}")
print(f"Test path exists: {os.path.exists(test_path)}")
test_path_ext = "\\\\?\\" + os.path.abspath(test_path)
print(f"Extended path: {test_path_ext}")
print(f"Extended path exists: {os.path.exists(test_path_ext)}")
