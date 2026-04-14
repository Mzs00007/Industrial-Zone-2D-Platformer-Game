$srcDir = "c:\Users\ZAID SIDDIQUI\OneDrive - University of Stirling\stir uni\SEMESTERS\sem6 2026\CSCU9N6\N6AssignmentCode\handout\src"

Write-Host "============================================================" -ForegroundColor Cyan
Write-Host "FIXING ALL PACKAGE DECLARATIONS AND IMPORTS" -ForegroundColor Yellow
Write-Host "============================================================" -ForegroundColor Cyan

# Define all replacement patterns
$replacements = @(
    # Framework packages
    @{ Old = "package framework;"; New = "package framework;" },
    
    # Managers packages (most critical)
    @{ Old = "package core;"; New = "package managers;" },
    @{ Old = "package config;"; New = "package managers;" },
    @{ Old = "package core.assets;"; New = "package managers;" },
    
    # Controllers packages
    @{ Old = "package gui;"; New = "package controllers;" },
    @{ Old = "package gui.screens;"; New = "package controllers;" },
    @{ Old = "package rendering;"; New = "package controllers;" },
    @{ Old = "package ui;"; New = "package controllers;" },
    
    # Entities packages
    @{ Old = "package core_game_entities;"; New = "package entities;" },
    @{ Old = "package core_game_entities.audio;"; New = "package entities;" },
    
    # Already correct - just verify
    @{ Old = "package animation;"; New = "package animation;" },
    @{ Old = "package physics;"; New = "package physics;" },
    @{ Old = "package ai;"; New = "package ai;" },
    @{ Old = "package utilities;"; New = "package utilities;" },
    @{ Old = "package enums;"; New = "package enums;" }
)

# Import replacements
$importReplacements = @(
    @{ Old = "import core\."; New = "import managers." },
    @{ Old = "import config\."; New = "import managers." },
    @{ Old = "import gui\."; New = "import controllers." },
    @{ Old = "import rendering\."; New = "import controllers." },
    @{ Old = "import core_game_entities\."; New = "import entities." }
)

$filesProcessed = 0
$filesModified = 0
$errors = @()

# Get all Java files
$javaFiles = Get-ChildItem -Path $srcDir -Recurse -Filter "*.java" -File

Write-Host "Found $($javaFiles.Count) Java files to process" -ForegroundColor Green
Write-Host ""

foreach ($file in $javaFiles) {
    $filesProcessed++
    $fileModified = $false
    
    try {
        $content = [System.IO.File]::ReadAllText($file.FullName, [System.Text.Encoding]::UTF8)
        $originalContent = $content
        
        # Fix package declarations
        foreach ($replacement in $replacements) {
            if ($content -match [regex]::Escape($replacement.Old)) {
                $content = $content -replace [regex]::Escape($replacement.Old), $replacement.New
                $fileModified = $true
            }
        }
        
        # Fix import statements
        foreach ($importReplacement in $importReplacements) {
            if ($content -match $importReplacement.Old) {
                $content = $content -replace $importReplacement.Old, $importReplacement.New
                $fileModified = $true
            }
        }
        
        # Write back if modified
        if ($fileModified -and $content -ne $originalContent) {
            [System.IO.File]::WriteAllText($file.FullName, $content, [System.Text.Encoding]::UTF8)
            $filesModified++
            Write-Host "[FIXED] $($file.Name)" -ForegroundColor Green
        } elseif ($filesProcessed % 50 -eq 0) {
            Write-Host "[OK] $($file.Name)" -ForegroundColor Gray
        }
        
    } catch {
        $errors += @{ File = $file.FullName; Error = $_.Exception.Message }
        Write-Host "[ERROR] $($file.Name): $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "============================================================" -ForegroundColor Cyan
Write-Host "FILES PROCESSED: $filesProcessed" -ForegroundColor Yellow
Write-Host "FILES MODIFIED: $filesModified" -ForegroundColor Green
Write-Host "ERRORS: $($errors.Count)" -ForegroundColor $(if ($errors.Count -eq 0) { "Green" } else { "Red" })
Write-Host "============================================================" -ForegroundColor Cyan

if ($errors.Count -gt 0) {
    Write-Host ""
    Write-Host "ERRORS:" -ForegroundColor Red
    foreach ($error in $errors) {
        Write-Host "  $($error.File): $($error.Error)" -ForegroundColor Red
    }
}
