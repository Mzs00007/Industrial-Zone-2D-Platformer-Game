#!/usr/bin/env python3
"""
Parse assets-manifest.json and generate complete Java enum files
"""
import json
import re
from pathlib import Path
from collections import defaultdict

# Load manifest
manifest_path = Path("handout/assets-manifest.json")
with open(manifest_path) as f:
    manifest = json.load(f)

# Group assets by category
assets_by_category = defaultdict(list)
for asset in manifest.get("assetCategories", {}).get("tiles", []):
    assets_by_category["tiles"].append(asset)
for asset in manifest.get("assetCategories", {}).get("vfx", []):
    assets_by_category["vfx"].append(asset)
for asset in manifest.get("assetCategories", {}).get("audio", []):
    assets_by_category["audio"].append(asset)
for asset in manifest.get("assetCategories", {}).get("characters", []):
    assets_by_category["characters"].append(asset)
for asset in manifest.get("assetCategories", {}).get("gui", []):
    assets_by_category["gui"].append(asset)
for asset in manifest.get("assetCategories", {}).get("weapons", []):
    assets_by_category["weapons"].append(asset)
for asset in manifest.get("assetCategories", {}).get("keyboard_keys", []):
    assets_by_category["keyboard_keys"].append(asset)
for asset in manifest.get("assetCategories", {}).get("mouse_keys", []):
    assets_by_category["mouse_keys"].append(asset)

# Generate summary
print("=== MANIFEST PARSE RESULTS ===")
for category, assets in assets_by_category.items():
    print(f"{category}: {len(assets)} assets")
    if assets:
        # Show first asset as sample
        print(f"  Sample: {assets[0]['name']}")
        print(f"  Path: {assets[0]['relativePath']}")

print(f"\nTotal categories: {len(assets_by_category)}")
print(f"Total assets: {sum(len(v) for v in assets_by_category.values())}")
