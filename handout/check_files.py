import os
path = "c:\\Users\\ZAID SIDDIQUI\\OneDrive - University of Stirling\\stir uni\\SEMESTERS\\sem6 2026\\CSCU9N6\\N6AssignmentCode\\handout\\Resources\\industrial-zone\\characters\\bosses\\GreenMech"
print(f"Checking path: {path}")
print(f"Path exists: {os.path.exists(path)}")
if os.path.exists(path):
    files = os.listdir(path)
    print(f"Files: {files}")
    for old in ["1.png", "3.png", "4.png", "6.png", "7.png", "8.png", "9.png"]:
        fpath = os.path.join(path, old)
        print(f"{old}: {os.path.exists(fpath)}")
