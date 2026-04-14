#!/usr/bin/env python3
"""
Bulk Package Declaration and Import Fixer
Fixes all remaining files in organized folders to use correct packages
"""

import os
import re
from pathlib import Path

# Configuration: folder -> (old_packages, new_package)
PACKAGE_FIXES = {
    '2_Managers': (
        # Old packages that need to change to managers
        [r'package core;', r'package config;', r'package core\.assets;'],
        'managers'
    ),
    '4_Entities': (
        [r'package core_game_entities;', r'package core_game_entities\..*?;'],
        'entities'
    ),
    '3_Controllers': (
        [r'package gui;', r'package gui\.screens;', r'package rendering;'],
        'controllers'
    ),
    '6_Physics': (
        [r'package physics;'],
        'physics'
    ),
    '7_AI': (
        [r'package ai;'],
        'ai'
    ),
    '5_Animation': (
        [r'package animation;'],
        'animation'
    ),
    '1_Framework': (
        [r'package framework;'],
        'framework'
    ),
}

IMPORT_FIXES = [
    # (pattern_to_find, replacement)
    (r'import core\.([\w.]+);', r'import managers.\1;'),
    (r'import config\.([\w.]+);', r'import managers.\1;'),
    (r'import core\.assets\.([\w.]+);', r'import managers.\1;'),
    (r'import gui\.([\w.]+);', r'import controllers.\1;'),
    (r'import gui\.screens\.([\w.]+);', r'import controllers.\1;'),
    (r'import rendering\.([\w.]+);', r'import controllers.\1;'),
    (r'import core_game_entities\.([\w.]+);', r'import entities.\1;'),
    (r'import audio\.([\w.]+);', r'import utilities.\1;'),
    (r'import core_game_entities\.audio\.([\w.]+);', r'import utilities.\1;'),
    (r'import assets\.enums\.([\w.]+);', r'import enums.\1;'),
    # Also simple package imports
    (r'import core;\s*$', r'import managers;', re.MULTILINE),
    (r'import config;\s*$', r'import managers;', re.MULTILINE),
    (r'import gui;\s*$', r'import controllers;', re.MULTILINE),
    (r'import rendering;\s*$', r'import controllers;', re.MULTILINE),
    (r'import audio;\s*$', r'import utilities;', re.MULTILINE),
]

def fix_file(filepath):
    """
    Fix a single Java file's package and imports
    Returns (success, file_path, changes_made)
    """
    try:
        with open(filepath, 'r', encoding='utf-8') as f:
            original_content = f.read()
        
        content = original_content
        changes = []
        
        # Get folder name
        folder = Path(filepath).parent.name
        
        if folder in PACKAGE_FIXES:
            old_patterns, new_package = PACKAGE_FIXES[folder]
            
            # Fix package declaration
            for pattern in old_patterns:
                if re.search(pattern, content):
                    # Replace first occurrence of old package with new package
                    new_pattern = f'package {new_package};'
                    content = re.sub(
                        r'\bpackage\s+[\w.]+;',
                        new_pattern,
                        content,
                        count=1
                    )
                    changes.append(f"{folder}: package -> {new_package}")
                    break  # Only replace first package declaration
        
        # Fix imports
        for pattern in IMPORT_FIXES:
            if len(pattern) == 2:
                old_import, new_import = pattern
                if re.search(old_import, content):
                    content = re.sub(old_import, new_import, content)
                    changes.append(f"import: {old_import[:40]}...")
            else:
                old_import, new_import, flags = pattern
                if re.search(old_import, content, flags):
                    content = re.sub(old_import, new_import, content, flags=flags)
                    changes.append(f"import: {old_import[:40]}...")
        
        # Only write if content changed
        if content != original_content:
            with open(filepath, 'w', encoding='utf-8') as f:
                f.write(content)
            return True, filepath, changes
        else:
            return False, filepath, []
            
    except Exception as e:
        return False, filepath, [f"ERROR: {str(e)}"]

def main():
    """Process all Java files in src folder"""
    src_path = Path('c:\\Users\\ZAID SIDDIQUI\\OneDrive - University of Stirling\\stir uni\\SEMESTERS\\sem6 2026\\CSCU9N6\\N6AssignmentCode\\handout\\src')
    
    if not src_path.exists():
        print(f"ERROR: Cannot find {src_path}")
        return
    
    java_files = list(src_path.rglob('*.java'))
    print(f"Found {len(java_files)} Java files")
    print()
    
    fixed_count = 0
    error_count = 0
    unchanged_count = 0
    
    # Skip game2D folder and root level Game.java
    java_files = [
        f for f in java_files 
        if 'game2D' not in str(f) and not (f.parent == src_path and f.name in ['Game.java', 'Game_minimal.java', 'Enemy.java', 'GameTest.java'])
    ]
    
    print("Processing files...")
    print()
    
    for filepath in sorted(java_files):
        success, path, changes = fix_file(filepath)
        
        if success and changes:
            fixed_count += 1
            rel_path = path.relative_to(src_path)
            print(f"✓ {rel_path}")
            for change in changes:
                print(f"  → {change}")
        elif not success:
            error_count += 1
            print(f"✗ {filepath.name}")
        else:
            unchanged_count += 1
    
    print()
    print("=" * 80)
    print(f"SUMMARY:")
    print(f"  Files fixed: {fixed_count}")
    print(f"  Unchanged: {unchanged_count}")
    print(f"  Errors: {error_count}")
    print(f"  Total processed: {len(java_files)}")
    print("=" * 80)

if __name__ == '__main__':
    main()
