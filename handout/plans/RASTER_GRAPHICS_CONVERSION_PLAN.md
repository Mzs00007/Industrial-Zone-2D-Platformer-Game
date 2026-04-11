# RASTER GRAPHICS CONVERSION PLAN
## Complete Vector Graphics Removal from Project

**Date:** April 2, 2026  
**Status:** GameMinimal.java ✅ COMPLETE - Raster Graphics Only  
**Scope:** Project-wide removal of vector graphics calls

---

## ✅ COMPLETED: GameMinimal.java

**Current State:** Pure raster graphics implementation  
**Method:** All rendering uses `g.drawImage()` exclusively

### What Was Changed:
- ✅ Removed all `fillRect()` calls
- ✅ Removed all `drawString()` calls
- ✅ Removed all `setColor()` calls
- ✅ Removed all `setFont()` calls
- ✅ Removed all vector element rendering

### Current Implementation:
```java
// ONLY uses these methods for rendering:
g.drawImage(backgroundImage, x, y, null);
g.drawImage(hudPanelImage, 0, y, getWidth(), height, null);
level1Parallax.render(g, getWidth(), getHeight());  // Uses drawImage internally
```

---

## ❌ TODO: Project-Wide Vector Graphics Removal

### Files Requiring Fixes:

#### **1. Game.java** (HIGH PRIORITY)
```
Lines with vector graphics: 35+ instances
Issues:
  - fillRect() calls (lines: 1602, 1699, 1731, 1809, 1863, 1896, 1923, 1924, 1947, 1950, 1976, 1979, 1985, etc.)
  - drawString() calls (lines: 1647,1650, 1659, 1663-1665, 1669, 1674, 1678-1680, 1684-1688, 1692, etc.)
  - setColor() calls (lines: 1601, 1645, 1658, 1662, 1668, 1673, 1677, 1682, 1683, 1691, 1698, etc.)
  - setFont() calls (lines: 1646, 1649, 1657, 1661, 1667, 1672, 1676, 1682, 1686, 1692, 1814, etc.)
  - drawLine() calls (lines: 1739, 1742)
  - fillOval() calls (lines: 1947, 1976)
  - drawOval() calls (lines: 1950, 1979)
  - fillPolygon() calls (line: 1924)

Fix Strategy:
  → Load UI assets as PNG images
  → Replace character selection screen with sprite-based rendering
  → Use image-based health bars instead of filled rectangles
  → Load checkpoint markers as PNG icons
  → Replace all debug text with text-as-image assets
```

#### **2. AnimationAndSpriteLoader.java** (HIGH PRIORITY)
```
Lines with vector graphics: 4 instances
Issues:
  - fillOval() calls (line: 12325)
  - fillRect() calls (line: 12329)
  - setColor() calls (lines: 12324, 12328)

Fix Strategy:
  → Remove debug visualizations
  → Delete colored placeholder graphics
  → Use actual asset images only
```

#### **3. Asset Generator-Related Files** (MEDIUM PRIORITY)
```
Files: AssetGenerator.java
Lines with vector graphics: 20+ instances
Issues:
  - setColor() calls (lines: 146, 154, 158, 164, 177, 181, 185, 197, 201, 205)
  - fillRect() calls (lines: 147, 161, 178, 186, 198, 202)
  - drawString() calls (line: 165)

Fix Strategy:
  → Keep asset generation as-is (only runs once to create assets)
  → Ensure generated assets are saved as PNG files
  → Remove any debug text rendering from the output
```

#### **4. CharacterAnimationTester.java** (MEDIUM PRIORITY)
```
Lines with vector graphics: 5+  instances
Issues:
  - fillRect() calls (line: 448)
  - setColor() calls (lines: 444, 446)
  - drawString() calls (line: 411)
  - setFont() calls (line: 410)

Fix Strategy:
  → Load GUI window backgrounds as PNG
  → Replace debug text with text image overlays
  → Keep only drawImage() calls for all GUI rendering
```

#### **5. GUIEntities.java** (HIGH PRIORITY - UI-heavy)
```
Lines with vector graphics: 30+ instances
Issues:
  - drawString() calls (lines: 1915, 1919, 1923, 1937, 1942, 1954-1966, 1985, 1991, 1997, 2003, 2008, 2133, 2134, 2144-2146, etc.)
  - setFont() calls (lines: 1981, 2133, 2144)
  - setColor() calls (lines: 1984, 1990, 1996, 2002, 2007, 2145, 2148, 2169, 2173)

Fix Strategy:
  → Replace all text rendering with PNG-based text assets
  → Load button states from sprite sheets
  → Use icon sprites for all UI elements
  → Create text-as-image for all labels/titles
```

#### **6. Characters.java** (MEDIUM PRIORITY)
```
Lines with vector graphics: 8 instances
Issues:
  - fillRect() calls (lines: 336, 660, 676, 704)
  - setColor() calls (lines: 335, 659, 675, 703, 705)
  - drawString() calls (line: 706)

Fix Strategy:
  → Replace fallback magenta rectangles with actual character sprites
  → Ensure all character assets load properly from PNG files
  → Remove "NO ASSET" text label rendering
```

#### **7. WeaponsEntities.java** (LOW PRIORITY)
```
Lines with vector graphics: 4 instances
Issues:
  - fillOval() calls (line: 1478)
  - drawOval() calls (line: 1483)
  - setColor() calls (lines: 1477, 1482, 1486)
  - drawString() calls (line: 1489)

Fix Strategy:
  → Replace glow effects with pre-rendered PNG images
  → Replace weapon labels with text-as-image assets
```

#### **8. TilesEntities.java** (LOW PRIORITY)
```
Lines with vector graphics: 4 instances
Issues:
  - fillRect() calls (line: 1152)
  - setColor() calls (lines: 1145, 1147, 1149)

Fix Strategy:
  → Replace hazard visual indicators with PNG overlays
  → Use damage effect sprites instead of colored rectangles
```

#### **9. AssetsAnimationAndLoadingTester.java** (LOW PRIORITY - Testing UI)
```
Lines with vector graphics: 15+ instances
Issues:
  - fillRect() calls (lines: 560, 576, 596)
  - setColor() calls (lines: 559, 575, 582, 588, 595, 599, 606)
  - drawString() calls (lines: 585, 591, 601, 608)
  - setFont() calls (lines: 581, 587, 598, 605)

Fix Strategy:
  → Load test background patterns as PNG images
  → Replace test text with PNG text overlays
```

---

## 🎯 IMPLEMENTATION PRIORITY

### Phase 1 (CRITICAL - For Submission)
1. ✅ GameMinimal.java - **COMPLETE**
2. Game.java - Remove all UI text and debug rendering

### Phase 2 (IMPORTANT - Polish)
1. GUIEntities.java - Replace all text with image-based UI
2. AnimationAndSpriteLoader.java - Remove debug visuals
3. CharacterAnimationTester.java - PNG-based GUI

### Phase 3 (NICE-TO-HAVE)
1. Characters.java - Remove placeholder graphics
2. WeaponsEntities.java - Sprite-based effects
3. TilesEntities.java - Image-based hazard indicators
4. Testing/Utility Files - UI text replacement

---

## ✅ VALIDATION CHECKLIST

For each file converted:
- [ ] Remove all `fillRect()` calls
- [ ] Remove all `drawString()` calls
- [ ] Remove all `setColor()` calls
- [ ] Remove all `setFont()` calls
- [ ] Remove all `drawLine()`, `drawOval()`, `fillOval()`, `drawPolygon()` calls
- [ ] Verify ONLY `drawImage()` calls remain for rendering
- [ ] Test that all visual elements display correctly
- [ ] Confirm file compiles without vector graphics warnings

---

## 📊 SUMMARY

| Component | Status | Priority | Lines to Fix |
|-----------|--------|----------|-------------|
| GameMinimal.java | ✅ COMPLETE | N/A | 0 |
| Game.java | ❌ TODO | CRITICAL | 35+ |
| AnimationAndSpriteLoader.java | ❌ TODO | HIGH | 4 |
| GUIEntities.java | ❌ TODO | HIGH | 30+ |
| Characters.java | ❌ TODO | MEDIUM | 8 |
| CharacterAnimationTester.java | ❌ TODO | MEDIUM | 5+ |
| WeaponsEntities.java | ❌ TODO | LOW | 4 |
| TilesEntities.java | ❌ TODO | LOW | 4 |
| AssetsAnimationAndLoadingTester.java | ❌ TODO | LOW | 15+ |

**Total Vector Graphics Lines to Remove: 110+**

---

## 🎨 RASTER GRAPHICS STRATEGY

### Approved Methods Only:
```java
// ALL drawing uses this ONLY:
g.drawImage(BufferedImage img, int x, int y, ImageObserver observer);
g.drawImage(BufferedImage img, int x, int y, int width, int height, ImageObserver observer);

// NEVER use:
g.fillRect();           // ✗ BANNED
g.drawString();         // ✗ BANNED
g.setColor();           // ✗ BANNED
g.setFont();            // ✗ BANNED
g.drawLine();           // ✗ BANNED
g.fillOval();           // ✗ BANNED
g.drawOval();           // ✗ BANNED
g.fillPolygon();        // ✗ BANNED
```

---

**Last Updated:** April 2, 2026 at 12:00 UTC
