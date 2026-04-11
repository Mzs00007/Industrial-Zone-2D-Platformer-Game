#!/usr/bin/env python3
"""
Asset Path Generator
Scans the Resources folder and generates Java code with actual asset paths
"""

import os
from pathlib import Path
from collections import defaultdict

def scan_assets():
    """Scan Resources folder and organize by category"""
    resources_root = Path("Resources/industrial-zone")
    assets_by_category = defaultdict(list)
    
    for root, dirs, files in os.walk(resources_root):
        for file in sorted(files):
            if file.endswith('.png'):
                full_path = os.path.join(root, file)
                rel_path = os.path.relpath(full_path, "Resources")
                rel_path = rel_path.replace("\\", "/")
                
                # Determine category
                parts = rel_path.split("/")
                if len(parts) >= 2:
                    category = parts[1]  # characters, vfx, weapons, etc
                    assets_by_category[category].append({
                        "name": file,
                        "path": f"Resources/{rel_path}",
                        "fullparts": parts
                    })
    
    return assets_by_category

def print_asset_mapping():
    """Print organized asset mapping"""
    assets = scan_assets()
    
    print("=" * 120)
    print("COMPLETE ASSET PATH MAPPING")
    print("=" * 120)
    
    for category in sorted(assets.keys()):
        files = assets[category]
        print(f"\n{category.upper()} ({len(files)} files)")
        print("-" * 120)
        
        # Group by subcategory
        by_subcat = defaultdict(list)
        for item in files:
            if len(item['fullparts']) >= 3:
                subcat = item['fullparts'][2]
            else:
                subcat = "[root]"
            by_subcat[subcat].append(item)
        
        for subcat in sorted(by_subcat.keys()):
            items = by_subcat[subcat]
            if subcat != "[root]":
                print(f"\n  {subcat}: {len(items)} files")
            
            # Show first 3 files in this subcategory
            for item in items[:3]:
                print(f"    {item['path']}")
            
            if len(items) > 3:
                print(f"    ... and {len(items) - 3} more")
    
    print("\n" + "=" * 120)
    print(f"TOTAL: {sum(len(v) for v in assets.values())} PNG files found")
    print("=" * 120)

def generate_player_assets():
    """Generate player asset paths"""
    assets = scan_assets()
    player_files = [a['path'] for a in assets.get('characters', []) 
                    if 'Biker' in a['name'] or 'Player' in a['name']]
    
    print("\n\n### PLAYER ASSETS ###")
    for f in sorted(player_files)[:30]:
        print(f'            stateToAssetPath.put(AnimationState.???, "{f}");')

def generate_vfx_assets():
    """Generate VFX asset paths"""
    assets = scan_assets()
    vfx_files = assets.get('vfx', [])
    
    print("\n\n### VFX ASSETS ###")
    
    # Group by subcategory
    by_type = defaultdict(list)
    for f in vfx_files:
        if len(f['fullparts']) >= 3:
            vfx_type = f['fullparts'][2]
        else:
            vfx_type = "other"
        by_type[vfx_type].append(f)
    
    for vfx_type in sorted(by_type.keys()):
        files = by_type[vfx_type]
        print(f"\n### {vfx_type.upper()} ({len(files)} files)")
        for f in sorted(files)[:5]:
            print(f'    "{f["path"]}",')
        if len(files) > 5:
            print(f"     ... and {len(files) - 5} more")

if __name__ == "__main__":
    print_asset_mapping()
    generate_player_assets()
    generate_vfx_assets()
