#!/usr/bin/env python3
"""
Asset Scanner: Discover all PNG files in leaf directories
Maps asset paths to actual files for implementation
"""

import os
import json
from pathlib import Path
from collections import defaultdict

RESOURCES_ROOT = r"Resources/industrial-zone"

def is_leaf_directory(path):
    """Check if directory contains only files (no subdirectories)"""
    if not os.path.isdir(path):
        return False
    entries = os.listdir(path)
    for entry in entries:
        full_path = os.path.join(path, entry)
        if os.path.isdir(full_path):
            return False
    return True

def scan_assets():
    """Recursively scan for PNG files in leaf directories"""
    asset_map = defaultdict(list)
    
    if not os.path.exists(RESOURCES_ROOT):
        print(f"ERROR: {RESOURCES_ROOT} not found")
        return asset_map
    
    # Walk through all directories
    for root, dirs, files in os.walk(RESOURCES_ROOT):
        # Only process leaf directories (no subdirs)
        if len(dirs) == 0 and len(files) > 0:
            # Check if this directory contains PNG files
            png_files = [f for f in files if f.lower().endswith('.png')]
            if png_files:
                # Make path relative for asset loading
                rel_path = os.path.relpath(root, ".").replace("\\", "/")
                asset_map[rel_path] = sorted(png_files)
    
    return asset_map

def organize_by_category(asset_map):
    """Organize assets by category for implementation"""
    categories = {
        "characters": {},
        "tiles": {},
        "backgrounds": {},
        "gui": {},
        "vfx": {},
        "audio": {},
        "weapons": {}
    }
    
    for directory, files in sorted(asset_map.items()):
        dir_lower = directory.lower()
        
        if "character" in dir_lower:
            categories["characters"][directory] = files
        elif "tile" in dir_lower:
            categories["tiles"][directory] = files
        elif "background" in dir_lower:
            categories["backgrounds"][directory] = files
        elif "gui" in dir_lower or "button" in dir_lower or "icon" in dir_lower:
            categories["gui"][directory] = files
        elif "vfx" in dir_lower or "effect" in dir_lower or "particle" in dir_lower:
            categories["vfx"][directory] = files
        elif "audio" in dir_lower or "music" in dir_lower or "sfx" in dir_lower:
            categories["audio"][directory] = files
        elif "weapon" in dir_lower:
            categories["weapons"][directory] = files
    
    return categories

def generate_implementation_map():
    """Generate a map of what needs to be implemented"""
    asset_map = scan_assets()
    
    if not asset_map:
        print("⚠️  No PNG files found. Checking directory structure...")
        return {}
    
    categories = organize_by_category(asset_map)
    
    # Generate report
    print("=" * 80)
    print("ASSET DISCOVERY REPORT")
    print("=" * 80)
    
    total_files = 0
    total_dirs = 0
    
    for category, dirs in categories.items():
        if dirs:
            print(f"\n📁 {category.upper()}")
            print(f"   Directories: {len(dirs)}")
            for directory, files in sorted(dirs.items()):
                print(f"   ✓ {directory}/")
                print(f"      Files: {len(files)} PNG files")
                total_files += len(files)
                total_dirs += 1
                # Show first 3 files
                for f in files[:3]:
                    print(f"        - {f}")
                if len(files) > 3:
                    print(f"        ... and {len(files)-3} more")
    
    print(f"\n{'='*80}")
    print(f"TOTAL: {total_dirs} leaf directories, {total_files} PNG files")
    print(f"{'='*80}\n")
    
    return {
        "asset_map": dict(asset_map),
        "categories": {k: {d: list(f) for d, f in v.items()} for k, v in categories.items()},
        "stats": {
            "total_directories": total_dirs,
            "total_files": total_files
        }
    }

if __name__ == "__main__":
    result = generate_implementation_map()
    
    # Save to JSON for reference
    if result:
        with open("asset_inventory.json", "w") as f:
            json.dump(result, f, indent=2)
        print("✅ Saved to asset_inventory.json")
