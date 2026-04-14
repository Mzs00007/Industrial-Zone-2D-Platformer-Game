# PowerShell Script to Bulk Fix Package Declarations and Imports

$srcPath = "c:\Users\ZAID SIDDIQUI\OneDrive - University of Stirling\stir uni\SEMESTERS\sem6 2026\CSCU9N6\N6AssignmentCode\handout\src"

Write-Host "Starting bulk package and import fixes..." -ForegroundColor Cyan
Write-Host ""

$fixedCount = 0
$unchangedCount = 0
$errorCount = 0

# Get all Java files except game2D and root Game.java
$javaFiles = Get-ChildItem -Path $srcPath -Recurse -Filter "*.java" | Where-Object {
    $path = $_.FullName
    -not ($path -like "*game2D*") -and -not ($_.Directory.Name -eq "src" -and $_.Name -in @("Game.java", "Game_minimal.java", "Enemy.java", "GameTest.java"))
}

Write-Host "Found $($javaFiles.Count) files to process" -ForegroundColor Yellow
Write-Host ""

foreach ($file in $javaFiles) {
    try {
        $content = Get-Content $file.FullName -Raw -Encoding UTF8
        $originalContent = $content
        $folderName = $file.Directory.Name
        $changes = @()
        
        # Fix package declarations based on folder
        if ($folderName -eq "2_Managers") {
            if ($content -match 'package (core|config|core\.assets);') {
                $content = $content -replace '^package (core|config|core\.assets);', 'package managers;'
                $changes += "package -> managers"
            }
        }
        elseif ($folderName -eq "4_Entities") {
            if ($content -match 'package core_game_entities') {
                $content = $content -replace '^package core_game_entities[\w.]*;', 'package entities;'
                $changes += "package -> entities"
            }
        }
        elseif ($folderName -eq "3_Controllers") {
            if ($content -match 'package (gui|rendering)') {
                $content = $content -replace '^package (gui[\w.]*|rendering);', 'package controllers;'
                $changes += "package -> controllers"
            }
        }
        
        # Fix imports
        $oldImports = @(
            @('import core\.', 'import managers.'),
            @('import config\.', 'import managers.'),
            @('import gui\.', 'import controllers.'),
            @('import rendering\.', 'import controllers.'),
            @('import audio\.', 'import utilities.'),
            @('import core_game_entities\.', 'import entities.'),
            @('import assets\.enums\.', 'import enums.')
        )
        
        foreach ($pair in $oldImports) {
            if ($content -match [regex]::Escape($pair[0])) {
                $content = $content -replace [regex]::Escape($pair[0]), $pair[1]
                $changes += "fixed imports: $($pair[0])..."
                break
            }
        }
        
        # Write back if changed
        if ($content -ne $originalContent) {
            [System.IO.File]::WriteAllText($file.FullName, $content, [System.Text.Encoding]::UTF8)
            $fixedCount++
            $relPath = $file.FullName -replace [regex]::Escape($srcPath), ""
            Write-Host "✓ $relPath" -ForegroundColor Green
            foreach ($change in $changes) {
                Write-Host "  -> $change" -ForegroundColor Gray
            }
        } else {
            $unchangedCount++
        }
        
    } catch {
        $errorCount++
        Write-Host "✗ Error processing $($file.Name): $_" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "=====================================================================" -ForegroundColor Cyan
Write-Host "SUMMARY:" -ForegroundColor Cyan
Write-Host "  Files fixed: $fixedCount" -ForegroundColor Green
Write-Host "  Unchanged: $unchangedCount"
Write-Host "  Errors: $errorCount" -ForegroundColor Red
Write-Host "  Total processed: $($javaFiles.Count)"
Write-Host "=====================================================================" -ForegroundColor Cyan
