# Rename numbered folders to package names for clean structure
$baseDir = "c:\Users\ZAID SIDDIQUI\OneDrive - University of Stirling\stir uni\SEMESTERS\sem6 2026\CSCU9N6\N6AssignmentCode\handout\src"

Write-Host "===============================================================" -ForegroundColor Cyan
Write-Host "RENAMING FOLDERS TO MATCH PACKAGE STRUCTURE" -ForegroundColor Yellow
Write-Host "===============================================================" -ForegroundColor Cyan

$folderMappings = @(
    @{ Old = "1_Framework"; New = "framework" },
    @{ Old = "2_Managers"; New = "managers" },
    @{ Old = "3_Controllers"; New = "controllers" },
    @{ Old = "4_Entities"; New = "entities" },
    @{ Old = "5_Animation"; New = "animation" },
    @{ Old = "6_Physics"; New = "physics" },
    @{ Old = "7_AI"; New = "ai" },
    @{ Old = "8_Utilities"; New = "utilities" },
    @{ Old = "9_Enums"; New = "enums" },
    @{ Old = "10_Interfaces"; New = "interfaces" },
    @{ Old = "11_Exceptions"; New = "exceptions" },
    @{ Old = "12_Tests"; New = "tests" },
    @{ Old = "13_Duplicates"; New = "duplicates" }
)

$successCount = 0
$failCount = 0

foreach ($mapping in $folderMappings) {
    $oldPath = Join-Path $baseDir $mapping.Old
    $newPath = Join-Path $baseDir $mapping.New
    
    if (Test-Path $oldPath) {
        try {
            Rename-Item -Path $oldPath -NewName $mapping.New -ErrorAction Stop
            Write-Host ("[DONE] " + $mapping.Old + " -> " + $mapping.New) -ForegroundColor Green
            $successCount++
        } catch {
            Write-Host ("[FAIL] Could not rename " + $mapping.Old) -ForegroundColor Red
            $failCount++
        }
    } else {
        Write-Host ("[SKIP] Folder not found: " + $mapping.Old) -ForegroundColor Gray
    }
}

Write-Host ""
Write-Host "===============================================================" -ForegroundColor Cyan
Write-Host "Renamed successfully: $successCount folders" -ForegroundColor Green
Write-Host "Failed: $failCount folders" -ForegroundColor Yellow
Write-Host "===============================================================" -ForegroundColor Cyan

# List final structure
Write-Host ""
Write-Host "NEW FOLDER STRUCTURE:" -ForegroundColor Yellow
Get-ChildItem -Path $baseDir -Directory | ForEach-Object { Write-Host ("  " + $_.Name) }
