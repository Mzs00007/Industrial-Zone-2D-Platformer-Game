#!/usr/bin/env python3
"""
Organize boss animation files into character-specific subdirectories.
"""

import os
import shutil
from pathlib import Path

bosses_dir = r"Resources\industrial-zone\characters\bosses"
os.chdir(bosses_dir)

# Boss character names extracted from filenames
boss_mappings = {
    "GreenMech": ["01_", "02_", "03_", "04_", "05_", "06_", "07_", "08_", "09_", "10_", "11_", "12_"],
    "GolfSoldier": ["13_", "14_", "15_", "16_", "17_", "18_", "19_", "20_", "21_", "27_"],
    "GolfCart": ["22_", "23_", "24_", "25_", "26_"]
}

# Ensure subdirectories exist
for boss_name in boss_mappings:
    boss_dir = Path(boss_name)
    if not boss_dir.exists():
        boss_dir.mkdir(parents=True, exist_ok=True)
        print(f"✓ Created directory: {boss_name}")

# Move files to appropriate directories
for boss_name, prefixes in boss_mappings.items():
    boss_dir = Path(boss_name)
    
    for file in Path(".").glob("*.png"):
        filename = file.name
        # Check if this file belongs to this boss
        if any(filename.startswith(prefix) for prefix in prefixes):
            dest = boss_dir / filename
            if dest.exists():
                print(f"⚠ File already exists: {filename} in {boss_name}/")
            else:
                shutil.move(filename, str(dest))
                print(f"✓ Moved {filename} → {boss_name}/")

print("\n✓ Boss directory organization complete!")
print("\nFinal structure:")
for boss_name in boss_mappings:
    count = len(list(Path(boss_name).glob("*.png")))
    print(f"  {boss_name}/: {count} files")
