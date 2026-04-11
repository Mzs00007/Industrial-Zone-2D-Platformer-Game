# 🔄 LOADER CONSOLIDATION & UPGRADE STRATEGY

## 🎯 Executive Summary

**Current State**: 7 core loaders with potential redundancy
- SingleSpriteLoader
- HorizontalSpritesheetLoader
- VerticalSpritesheetLoader  
- GridSpritesheetLoader
- GridFrameAnimationLoader
- SequenceFrameAnimationLoader
- StateVariantLoader

**Goal**: Optimize by consolidating overlapping functionality while preserving specialized behaviors

**Strategy**: 
1. Keep specialized loaders for their specific use cases
2. Enhance HorizontalSpritesheetLoader as primary workhorse
3. Create unified interface for all loaders
4. Plan deprecation path for GridSpritesheetLoader

---

## 📊 LOADER CAPABILITY MATRIX

| Feature | Single | Horizontal | Vertical | Grid | GridFrame | Sequence | StateVariant |
|---------|--------|-----------|----------|------|-----------|----------|--------------|
| Load single image | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 1D horizontal strip | ❌ | ✅ | ❌ | ❌ | ❌ | ❌ | ❌ |
| 1D vertical strip | ❌ | ❌ | ✅ | ❌ | ❌ | ❌ | ❌ |
| 2D grid layout | ❌ | ❌ | ❌ | ✅ | ✅ | ❌ | ❌ |
| Frame-by-frame timing | ❌ | ❌ | ❌ | ❌ | ✅ | ✅ | With sub |
| Multiple files | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ | ❌ |
| State container | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ |
| Auto-detect frames | ⚠️ | ✅ | ✅ | ⚠️ | ⚠️ | ❌ | N/A |
| Frame indexing | Simple | Linear | Linear | Grid-based | Grid-based | Sequential | State-based |

---

## 🎯 CONSOLIDATION PLAN

### TIER 1: KEEP AS-IS (Core Loaders)

These provide non-overlapping, specialized functionality:

#### 1. SingleSpriteLoader (KEEP)
**Purpose**: Load single non-animated image
**Why Keep**: 
- Clear, simple use case
- No modification needed
- Fast, minimal overhead

**Never change**: This loader is fine as-is

#### 2. HorizontalSpritesheetLoader (ENHANCE)
**Current**: Loads horizontal strips
**Enhancement Opportunities**:
- [ ] Add auto-frame-count detection (filename parsing)
- [ ] Add offset parameter support for padding
- [ ] Add validation for aspect ratio
- [ ] Add detailed error messages with file paths
- [ ] Add frame caching for memory efficiency

```java
// ENHANCED VERSION
public class HorizontalSpritesheetLoader extends AssetType {
    
    // NEW: Support custom frame count detection
    public boolean loadWithAutoDetect()
      // Auto-detect from filename, image ratio, or metadata
    
    // NEW: Support offset/padding
    public boolean loadWithOffset(int offsetX, int offsetY, int borderPixels)
      // Handles spritesheets with padding
    
    // NEW: Metadata integration
    public void setMetadata(SpriteMetadata metadata)
      // Use pre-computed metadata for frame count
    
    // NEW: Validation
    public boolean validateFraming()
      // Check that image dimensions divide evenly by frame count
      
    // EXISTING: stays as-is
    public boolean load()
    public BufferedImage getFrame(int frameIndex)
    public int getFrameCount()
}
```

#### 3. VerticalSpritesheetLoader (ENHANCE)
**Current**: Loads vertical stacks
**Enhancement Opportunities**:
- [ ] Add auto-detection for vertical strips
- [ ] Improve frame detection from filename
- [ ] Add rotation parameter (for converting H→V)

```java
// ENHANCEMENT EXAMPLE
public class VerticalSpritesheetLoader extends AssetType {
    
    // NEW: Auto-detect vertical from metadata
    public boolean loadWithMetadata(SpriteMetadata metadata)
      // Uses pre-analyzed metadata
    
    // NEW: Validate vertical division
    public boolean validateVerticalDimensions()
      // Ensure height % frameCount == 0
    
    // EXISTING
    public boolean load()
    public BufferedImage getFrame(int frameIndex)
}
```

#### 4. StateVariantLoader (KEEP)
**Purpose**: Container for multiple animation states
**Why Keep**: 
- Different paradigm (composition vs decomposition)
- Essential for multi-state entities
- No redundancy with other loaders

```java
// StateVariantLoader is perfect, no changes
public class StateVariantLoader extends AssetType {
    // Add new states at runtime
    // Switch between animation loaders
    // Container paradigm, not specialized
}
```

#### 5. SequenceFrameAnimationLoader (KEEP)
**Purpose**: Load animation from separate files
**Use Case**: Cinematics, complex sequences
**Why Keep**: Unique capability (multi-file support)

```java
// Good as-is, no changes
public class SequenceFrameAnimationLoader extends AssetType {
    public boolean loadSequence(String[] filePaths)
    public BufferedImage getNextFrame(float deltaTime)
    public int getFrameCount()
}
```

---

### TIER 2: CONSOLIDATE/DEPRECATE

#### GridSpritesheetLoader and GridFrameAnimationLoader

**Current Situation**:
- GridSpritesheetLoader: Basic grid loading
- GridFrameAnimationLoader: Grid + per-frame timing
- Redundancy: Both handle 2D grids

**Consolidation Strategy**:

**Option A** (RECOMMENDED): Deprecate GridSpritesheetLoader
- Migrate grid users to enhanced HorizontalSpritesheetLoader + helper
- GridSpritesheetLoader becomes wrapper around HorizontalSpritesheetLoader
- Single source of truth

**Option B**: Merge GridFrameAnimationLoader into single GridLoader
- Single class handling both static and animated grids
- More complex but unified

**Recommendation: Go with Option A**

```java
// PROPOSED: Enhanced HorizontalSpritesheetLoader with grid support
public class HorizontalSpritesheetLoader extends AssetType {
    
    // Existing single-row support
    public boolean load() // ← existing, works for H strips
    
    // NEW: Grid support (replaces GridSpritesheetLoader)
    public class GridMode {
        int rows, cols;
        
        public BufferedImage getFrameAt(int row, int col)
        public BufferedImage getFrame(int linearIndex)
    }
    
    private GridMode gridMode = null;
    
    // NEW: Initialize grid mode
    public void enableGridMode(int rows, int cols)
      // Converts from linear to 2D grid indexing
    
    // NEW: Get frame with grid logic
    @Override
    public BufferedImage getFrame(int index) {
        if (gridMode != null) {
            return gridMode.getFrame(index);  // Grid-aware
        } else {
            return super.getFrame(index);      // Linear
        }
    }
}

// DEPRECATION: GridSpritesheetLoader becomes this
@Deprecated(since="2026-04-02", forRemoval=true)
public class GridSpritesheetLoader extends AssetType {
    // Internal wrapper for backwards compatibility
    private HorizontalSpritesheetLoader internalLoader;
    
    @Override
    public boolean load() {
        internalLoader = new HorizontalSpritesheetLoader(...);
        internalLoader.enableGridMode(rows, cols);
        return internalLoader.load();
    }
    
    @Override
    public BufferedImage getFrame(int index) {
        return internalLoader.getFrame(index);
    }
}
```

---

## 📋 ENHANCEMENT ROADMAP

### Phase 1: Metadata Integration (IMMEDIATE)
- [ ] Connect all loaders to MetadataExtractor
- [ ] Loaders use pre-computed metadata when available
- [ ] Add verbose logging for frame count detection

```java
// BEFORE (current):
HorizontalSpritesheetLoader loader = new HorizontalSpritesheetLoader(
    id, 
    path, 
    0, 0, 0  // offsets, no metadata
);

// AFTER (enhanced):
SpriteMetadata meta = MetadataExtractor.analyzeImage(path);
HorizontalSpritesheetLoader loader = new HorizontalSpritesheetLoader(
    id,
    path,
    meta.estimatedFrameCount  // auto-detected
);
loader.useMetadata(meta);  // Pass analysis results
```

### Phase 2: Error Handling & Validation (Week 2)
- [ ] All loaders validate dimensions match expected frames
- [ ] Detailed error messages with exact file paths
- [ ] Return NULL on failure, never fallback colors

```java
// Example: HorizontalSpritesheetLoader validation
public boolean load() {
    BufferedImage image = loadImageOrNull(filePath);
    if (image == null) {
        System.err.println("❌ FAILED TO LOAD: " + filePath);
        System.err.println("   Full: " + new File(filePath).getAbsolutePath());
        return false;
    }
    
    // Validate
    if (image.getWidth() % frameCount != 0) {
        System.err.println("❌ VALIDATION FAILED: " + filePath);
        System.err.println("   Width " + image.getWidth() + " not divisible by frames " + frameCount);
        System.err.println("   Expected width: " + (image.getHeight() * frameCount));
        return false;
    }
    
    // Success
    System.out.println("✓ Loaded: " + filePath);
    System.out.println("  Frames: " + frameCount + ", Size: " + image.getWidth() + "×" + image.getHeight());
    return true;
}
```

### Phase 3: Frame Caching Optimization (Week 3)
- [ ] Loaders can opt into automatic frame caching
- [ ] RecycledImageArary for memory-efficient buffer reuse
- [ ] LRU eviction for large texture pools

```java
// Example: Memory-efficient frame storage
public abstract class AssetType {
    
    // NEW: Built-in frame cache
    protected BufferedImage[] frameCache = null;
    
    // NEW: Enable/disable auto-caching
    public void enableFrameCache(boolean enabled) {
        if (enabled && frameCache == null) {
            frameCache = new BufferedImage[getFrameCount()];
        }
    }
    
    // NEW: Cache-aware frame retrieval
    public BufferedImage getFrame(int index) {
        if (frameCache != null && frameCache[index] != null) {
            return frameCache[index];  // From cache
        }
        
        BufferedImage frame = computeFrame(index);  // From spritesheet
        
        if (frameCache != null) {
            frameCache[index] = frame;  // Cache for next time
        }
        
        return frame;
    }
}
```

### Phase 4: Grid Mode Consolidation (Week 4)
- [ ] Add grid mode to HorizontalSpritesheetLoader
- [ ] Create GridSpritesheetLoader wrapper
- [ ] Migrate existing grid users
- [ ] Mark for deprecation

### Phase 5: Animation Timing Enhancements (Week 5)
- [ ] Add per-frame timing to all loaders
- [ ] Support timing array in HorizontalSpritesheetLoader
- [ ] Create TimingMetadata class

```java
// Example: Variable frame timing
public class HorizontalSpritesheetLoader extends AssetType {
    
    private int[] frameTiming = null;  // null = uniform timing
    
    // NEW: Set per-frame timing
    public void setFrameTiming(int[] timing) {
        // timing[i] = milliseconds for frame i
        this.frameTiming = timing;
    }
    
    // NEW: Get timing for specific frame
    public int getFrameDelay(int frameIndex) {
        if (frameTiming != null && frameIndex < frameTiming.length) {
            return frameTiming[frameIndex];
        }
        return defaultFrameDelay;  // Fall back to uniform timing
    }
}
```

---

## 🛠️ IMPLEMENTATION GUIDE

### For Each Loader Enhancement:

**Step 1: Document Current Usage**
```
Example: Find all uses of GridSpritesheetLoader
  grep -r "GridSpritesheetLoader" src/
  Results: 0 uses found (safe to modify/deprecate)
```

**Step 2: Create Test Coverage**
```java
public class HorizontalSpritesheetLoaderTest {
    
    @Test
    public void testAutoDetectFrameCount() {
        // Load file named "walk_8frames.png"
        HorizontalSpritesheetLoader loader = ...;
        assertEquals(8, loader.getFrameCount());
    }
    
    @Test
    public void testGridMode() {
        HorizontalSpritesheetLoader loader = ...;
        loader.enableGridMode(2, 4);
        
        // Test grid access
        assertEquals(loader.getFrameAt(0, 0), loader.getFrame(0));
        assertEquals(loader.getFrameAt(1, 3), loader.getFrame(7));
    }
    
    @Test
    public void testMetadataIntegration() {
        SpriteMetadata meta = MetadataExtractor.analyzeImage(path);
        HorizontalSpritesheetLoader loader = new HorizontalSpritesheetLoader(id, path, 0, 0, 0);
        loader.useMetadata(meta);
        
        assertTrue(loader.load());
        assertEquals(meta.estimatedFrameCount, loader.getFrameCount());
    }
}
```

**Step 3: Update Documentation**
```
Update index document with new signatures
Add migration guide for deprecated methods
Show before/after examples
```

**Step 4: Migrate Existing Code**
```
For each loader usage:
  - Test with new signature
  - Verify output unchanged
  - Update to use metadata if available
  - Remove deprecated features
```

---

## 🔗 UNIFIED LOADER INTERFACE

All loaders should follow this contract:

```java
public abstract static class AssetType {
    
    // ════════════════════════════════════════════════════════════════════
    // CORE INTERFACE (should not change)
    // ════════════════════════════════════════════════════════════════════
    
    /** Load asset from disk */
    public abstract boolean load();
    
    /** Get frame by index */
    public abstract BufferedImage getFrame(int index);
    
    /** Total frame count */
    public abstract int getFrameCount();
    
    // ════════════════════════════════════════════════════════════════════
    // OPTIONAL ENHANCEMENTS (can be overridden)
    // ════════════════════════════════════════════════════════════════════
    
    /** Metadata support */
    public void useMetadata(SpriteMetadata metadata) {
        // Default: do nothing, subclasses can override
    }
    
    /** Frame caching */
    public void enableFrameCache(boolean enabled) {
        // Default: do nothing, subclasses can implement
    }
    
    /** Animation timing */
    public int getFrameDelay(int frameIndex) {
        return 100;  // Default 100ms per frame
    }
    
    // ════════════════════════════════════════════════════════════════════
    // PROTECTED UTILITIES (for subclass use)
    // ════════════════════════════════════════════════════════════════════
    
    protected boolean fileExists(String path) {
        return new File(path).exists() && new File(path).isFile();
    }
    
    protected BufferedImage loadImageOrNull(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            return null;
        }
    }
    
    protected void logError(String message) {
        System.err.println("❌ " + message);
    }
    
    protected void logSuccess(String message) {
        System.out.println("✓ " + message);
    }
}
```

---

## 📋 DEPRECATION PATH

**For GridSpritesheetLoader**:

```
Timeline:
  v2026-04-02: Mark as @Deprecated, announce migration path
  v2026-05-02: Provide GridSpritesheetLoader wrapper (backwards compatible)
  v2026-06-02: Require migration to HorizontalSpritesheetLoader.enableGridMode()
  v2026-07-02: Remove GridSpritesheetLoader entirely
```

**Deprecation Announcement**:
```java
@Deprecated(since="2026-04-02", forRemoval=true,
  message="Use HorizontalSpritesheetLoader.enableGridMode(rows, cols) instead")
public static class GridSpritesheetLoader extends AssetType {
    // Implementation wraps HorizontalSpritesheetLoader
}
```

**Migration Guide**:
```
OLD:
  GridSpritesheetLoader loader = new GridSpritesheetLoader(...);
  loader.load(path, rows, cols);
  BufferedImage frame = loader.getFrame(index);

NEW:
  HorizontalSpritesheetLoader loader = new HorizontalSpritesheetLoader(...);
  loader.enableGridMode(rows, cols);
  loader.load();
  BufferedImage frame = loader.getFrame(index);
```

---

End of Consolidation Strategy Document
