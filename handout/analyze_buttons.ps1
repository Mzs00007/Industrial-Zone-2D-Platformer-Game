Add-Type -AssemblyName System.Drawing

$dir = "C:\Users\ZAID SIDDIQUI\OneDrive - University of Stirling\stir uni\SEMESTERS\sem6 2026\CSCU9N6\N6AssignmentCode\handout\Resources\industrial-zone\gui\6 Buttons"

Write-Host "Button Sprite Sheet Analysis"
Write-Host "============================="

Get-ChildItem "$dir\GUI_ButtonColorMap_Variant_*.png" | Sort-Object Name | ForEach-Object {
    $img = [System.Drawing.Image]::FromFile($_.FullName)
    $w = $img.Width
    $h = $img.Height
    $r = [math]::Round($w / $h, 3)
    
    # Determine orientation
    if ($w -gt $h) {
        $orientation = "HORIZONTAL (frames side-by-side)"
    } elseif ($h -gt $w) {
        $orientation = "VERTICAL (states stacked)"
    } else {
        $orientation = "SQUARE"
    }
    
    Write-Host ("{0,-40} {1:D4} x {2:D4}  ratio:{3:F3}  -  {4}" -f $_.Name, $w, $h, $r, $orientation)
    $img.Dispose()
}
