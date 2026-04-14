$srcPath = "c:\Users\ZAID SIDDIQUI\OneDrive - University of Stirling\stir uni\SEMESTERS\sem6 2026\CSCU9N6\N6AssignmentCode\handout\src"
 
Write-Host "Starting bulk package and import fixes..." -ForegroundColor Cyan

$fixedCount = 0
$javaFiles = Get-ChildItem -Path $srcPath -Recurse -Filter "*.java" | Where-Object {
    $path = $_.FullName
    (-not ($path -like "*game2D*"))
}

Write-Host "Found $($javaFiles.Count) files to process" -ForegroundColor Yellow

foreach ($file in $javaFiles) {
    $content = Get-Content $file.FullName -Raw -Encoding UTF8 -ErrorAction SilentlyContinue
    if ($null -eq $content) { continue }
    
    $originalContent = $content
    $folderName = $file.Directory.Name
    
    # Fix package declarations
    if ($folderName -eq "2_Managers" -and $content -match 'package core') {
        $content = $content -replace 'package core[^;]*;', 'package managers;'
        $fixedCount++
    }
    elseif ($folderName -eq "4_Entities" -and $content -match 'package core_game_entities') {
        $content = $content -replace 'package core_game_entities[^;]*;', 'package entities;'
        $fixedCount++
    }
    elseif ($folderName -eq "3_Controllers" -and $content -match 'package (gui|rendering)') {
        $content = $content -replace 'package (gui|rendering)[^;]*;', 'package controllers;'
        $fixedCount++
    }
    
    # Fix imports
    $content = $content -replace 'import core\.', 'import managers.'
    $content = $content -replace 'import config\.', 'import managers.'
    $content = $content -replace 'import gui\.', 'import controllers.'
    $content = $content -replace 'import rendering\.', 'import controllers.'
    $content = $content -replace 'import audio\.', 'import utilities.'
    $content = $content -replace 'import core_game_entities\.', 'import entities.'
    $content = $content -replace 'import assets\.enums\.', 'import enums.'
    
    if ($content -ne $originalContent) {
        [System.IO.File]::WriteAllText($file.FullName, $content, [System.Text.Encoding]::UTF8)
    }
}

Write-Host "Files processed: $($javaFiles.Count)" -ForegroundColor Green
Write-Host "Files fixed: $fixedCount" -ForegroundColor Green
