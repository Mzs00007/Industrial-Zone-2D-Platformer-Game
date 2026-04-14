import os
import re

base = r"c:\Users\ZAID SIDDIQUI\OneDrive - University of Stirling\stir uni\SEMESTERS\sem6 2026\CSCU9N6\N6AssignmentCode\handout\src\9_Enums"

files_to_fix = {
    "WeaponAssets.java": "WEAPON",
    "VFXAssets.java": "VFX"
}

for filename, prefix in files_to_fix.items():
    filepath = os.path.join(base, filename)
    
    # Read file as bytes
    with open(filepath, 'rb') as f:
        content_bytes = f.read()
    
    # Remove UTF-8 BOM if present
    if content_bytes.startswith(b'\xef\xbb\xbf'):
        content_bytes = content_bytes[3:]
    
    # Convert to string
    content = content_bytes.decode('utf-8')
    
    # Replace numeric-only enum constants: "    1(" -> "    PREFIX_1("
    content = re.sub(r'(\s+)(\d+)\("', rf'\1{prefix}_\2("', content)
    
    # Write back without BOM
    with open(filepath, 'wb') as f:
        f.write(content.encode('utf-8'))
    
    print(f"Fixed: {filename}")

print("Done!")
