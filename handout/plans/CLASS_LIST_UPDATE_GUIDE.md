# CLASS PATHS LIST AUTO-UPDATE GUIDE

## Purpose
Automatically update `CLASS_PATHS_LIST.txt` with all your compiled `.class` files from the `bin/` directory.

## Quick Start

### After Compiling Your Java Files

Run this command in PowerShell:

```powershell
cd C:\Users\ZAID SIDDIQUI\OneDrive - University of Stirling\stir uni\SEMESTERS\sem6 2026\CSCU9N6\N6AssignmentCode\handout
& .\update_class_list.ps1
```

Or simply:
```powershell
.\update_class_list.ps1
```

## What It Does

1. **Scans** the `bin/` directory for all compiled `.class` files
2. **Filters** out inner classes (files with `$` in the name)
3. **Generates** relative paths from working directory
4. **Sorts** alphabetically
5. **Updates** `CLASS_PATHS_LIST.txt` with the current list

## Output

```
=====================================================
UPDATING CLASS PATHS LIST...
=====================================================
Working Directory: C:\Users\...\handout
Scanning compiled classes...
SUCCESS: Updated CLASS_PATHS_LIST.txt with 241 classes

===== UPDATE SUMMARY =====
Classes found: 241
Output file: CLASS_PATHS_LIST.txt
Updated: 04/02/2026 20:24:40
=========================
```

## Integration into Your Workflow

### Option 1: Manual (After Each Compile)
After you compile your Java files using your normal build process:
```powershell
.\update_class_list.ps1
```

### Option 2: Create a Batch File (Optional)
Create `compile_and_update.bat` in the handout directory:
```batch
@echo off
REM Compile your Java files with your existing build script
call build.bat

REM Update the class list
powershell -NoProfile -ExecutionPolicy Bypass -File ".\update_class_list.ps1"
pause
```

Then just run: `compile_and_update.bat`

## Files Involved

- **`update_class_list.ps1`** - The update script (run this after compilation)
- **`CLASS_PATHS_LIST.txt`** - The generated output file with all class paths
- **`bin/`** - Your compiled classes directory (source of truth)

## Troubleshooting

### Script won't run
If you get "Script disabled" error, run PowerShell as Admin and type:
```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

### No classes found
Make sure you compiled your Java files first:
- Check that `bin/` directory exists
- Check that `.class` files are present in `bin/`

### Different number of classes
The script counts "main" classes only (excludes inner classes with `$` in filename).
- Total .class files will be higher (1009 in your case)
- Main classes = 241 (what the script counts)

## Example Output File Format

`CLASS_PATHS_LIST.txt` contains one class path per line:
```
bin\ai\AI.class
bin\ai\AttackState.class
bin\animation\AnimationAndSpriteLoader.class
...
bin\weapons\WeaponRenderer.class
```

## That's It!

Just run the script after each compile session to keep `CLASS_PATHS_LIST.txt` in sync with your latest build.
