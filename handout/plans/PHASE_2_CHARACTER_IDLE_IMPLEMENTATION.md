# PHASE 2: CHARACTER IDLE ANIMATIONS - IMPLEMENTATION GUIDE

**Status**: READY FOR IMPLEMENTATION  
**Blocking Issue**: None - All assets verified  
**Estimated Time**: 1-2 hours  
**Priority**: CRITICAL - UI/UX feature

---

## OBJECTIVE
Implement animated character idle animations in CharacterSelectScreen showing Biker, Punk, and Cyborg breathing/standing animations with proper frame timing.

---

## STEP 1: Verify Asset Files (PRE-IMPLEMENTATION CHECK)

### Expected File Paths (VERIFY THESE EXIST):

```powershell
# Run in terminal to verify:
Test-Path "handout\Resources\industrial-zone\characters\player\biker\01_Player_Biker_Idle_5Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png"
Test-Path "handout\Resources\industrial-zone\characters\player\punk\01_Player_Punk_Idle_5Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png"
Test-Path "handout\Resources\industrial-zone\characters\player\cyborg\01_Player_Cyborg_Idle_4Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png"
```

### Expected Results:
All three should return `True`

If ANY return `False`, **STOP** and identify the correct filename in the directory.

---

## STEP 2: Create AnimationController Base Class

**File**: `src/gui/AnimationController.java`

```java
package gui;

import java.awt.image.BufferedImage;

/**
 * Base class for managing sprite animations with frame timing.
 * Handles frame advancement, looping, and state management.
 */
public class AnimationController {
    
    protected BufferedImage[] frames;
    protected int currentFrameIndex = 0;
    protected float elapsedTime = 0;
    protected float frameDuration;
    protected boolean isLooping = true;
    protected boolean isFinished = false;
    
    /**
     * Initialize controller with frames and timing.
     * @param frames Array of BufferedImage frames
     * @param frameDurationMs Duration per frame in milliseconds
     */
    public AnimationController(BufferedImage[] frames, float frameDurationMs) {
        this.frames = frames;
        this.frameDuration = frameDurationMs / 1000f;  // Convert to seconds
    }
    
    /**
     * Update animation state based on delta time.
     * Must be called once per game frame.
     */
    public void update(float deltaTime) {
        if (isFinished && !isLooping) {
            return;
        }
        
        elapsedTime += deltaTime;
        
        if (elapsedTime >= frameDuration) {
            currentFrameIndex++;
            elapsedTime = 0;
            
            if (currentFrameIndex >= frames.length) {
                if (isLooping) {
                    currentFrameIndex = 0;
                } else {
                    currentFrameIndex = frames.length - 1;
                    isFinished = true;
                }
            }
        }
    }
    
    /**
     * Get the current frame image.
     */
    public BufferedImage getCurrentFrame() {
        if (frames == null || frames.length == 0) {
            return null;
        }
        return frames[currentFrameIndex];
    }
    
    /**
     * Get current frame index (0-based).
     */
    public int getCurrentFrameIndex() {
        return currentFrameIndex;
    }
    
    /**
     * Get total number of frames.
     */
    public int getFrameCount() {
        return frames != null ? frames.length : 0;
    }
    
    /**
     * Reset animation to frame 0.
     */
    public void reset() {
        currentFrameIndex = 0;
        elapsedTime = 0;
        isFinished = false;
    }
    
    /**
     * Set whether animation should loop.
     */
    public void setLooping(boolean looping) {
        this.isLooping = looping;
    }
    
    /**
     * Check if animation has finished (only relevant for non-looping).
     */
    public boolean isFinished() {
        return isFinished;
    }
    
    /**
     * Get animation progress as float 0.0 - 1.0
     */
    public float getProgress() {
        float totalTime = frameDuration * frames.length;
        float elapsed = (currentFrameIndex * frameDuration) + elapsedTime;
        return Math.min(elapsed / totalTime, 1.0f);
    }
}
```

---

## STEP 3: Create Character Animation Loader

**File**: `src/gui/CharacterIdleAnimationLoader.java`

```java
package gui;

import animation.AnimationAndSpriteLoader;
import java.awt.image.BufferedImage;

/**
 * Loads and manages character idle animations.
 * Handles all three character types with their respective frame counts.
 */
public class CharacterIdleAnimationLoader {
    
    public enum Character {
        BIKER("biker", 5),
        PUNK("punk", 5),
        CYBORG("cyborg", 4);
        
        public final String dirName;
        public final int frameCount;
        
        Character(String dirName, int frameCount) {
            this.dirName = dirName;
            this.frameCount = frameCount;
        }
    }
    
    private static final String BASE_PATH = "Resources/industrial-zone/characters/player";
    private static final float FRAME_DURATION_MS = 150f;  // 150ms per frame
    
    /**
     * Load idle animation for a character.
     * @param character Which character to load (BIKER, PUNK, CYBORG)
     * @return AnimationController ready to animate
     */
    public static AnimationController loadCharacterIdle(Character character) {
        String path = buildPath(character);
        System.out.println("[CharacterIdleAnimationLoader] Loading: " + path);
        
        // Load spritesheet
        AnimationAndSpriteLoader.HorizontalSpritesheetLoader loader = 
            new AnimationAndSpriteLoader.HorizontalSpritesheetLoader();
        
        try {
            // This call loads the entire spritesheet and prepares to split it
            // into individual frames horizontally
            loader.load(path, character.frameCount);
            
            // Extract individual frames
            BufferedImage[] frames = new BufferedImage[character.frameCount];
            for (int i = 0; i < character.frameCount; i++) {
                frames[i] = loader.getFrame(i);
                if (frames[i] == null) {
                    System.err.println("[CharacterIdleAnimationLoader] ERROR: Frame " + i 
                                     + " is null for " + character.name());
                }
            }
            
            // Create and return controller
            AnimationController controller = new AnimationController(frames, FRAME_DURATION_MS);
            controller.setLooping(true);
            System.out.println("[CharacterIdleAnimationLoader] Successfully loaded " 
                             + character.frameCount + " frames for " + character.name());
            
            return controller;
            
        } catch (Exception e) {
            System.err.println("[CharacterIdleAnimationLoader] ERROR loading " + character.name());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Build the full file path for a character idle animation.
     */
    private static String buildPath(Character character) {
        return BASE_PATH + "/" + character.dirName 
             + "/01_Player_" + capitalize(character.dirName) 
             + "_Idle_" + character.frameCount + "Frames1Row_StandingBreathLoop_DefaultIdle_Loop_150ms.png";
    }
    
    private static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
```

---

## STEP 4: Update CharacterSelectScreen

**Modify**: `src/gui/screens/CharacterSelectScreen.java`

```java
package gui.screens;

import gui.AnimationController;
import gui.CharacterIdleAnimationLoader;
import gui.FrameTiler;
import gui.GUIAssetManager;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class CharacterSelectScreen {
    
    private class CharacterCard {
        String name;
        CharacterIdleAnimationLoader.Character character;
        AnimationController animation;
        int x, y;
        int width = 180;
        int height = 250;
        boolean selected = false;
    }
    
    private FrameTiler frameTiler;
    private GUIAssetManager assetManager;
    private CharacterCard[] characters;
    private int selectedIndex = 0;
    
    public CharacterSelectScreen() {
        frameTiler = new FrameTiler();
        assetManager = GUIAssetManager.getInstance();
        initializeCharacters();
    }
    
    private void initializeCharacters() {
        characters = new CharacterCard[3];
        
        // BIKER
        characters[0] = new CharacterCard();
        characters[0].name = "BIKER";
        characters[0].character = CharacterIdleAnimationLoader.Character.BIKER;
        characters[0].animation = CharacterIdleAnimationLoader.loadCharacterIdle(
            CharacterIdleAnimationLoader.Character.BIKER
        );
        characters[0].x = 80;
        characters[0].y = 150;
        
        // PUNK
        characters[1] = new CharacterCard();
        characters[1].name = "PUNK";
        characters[1].character = CharacterIdleAnimationLoader.Character.PUNK;
        characters[1].animation = CharacterIdleAnimationLoader.loadCharacterIdle(
            CharacterIdleAnimationLoader.Character.PUNK
        );
        characters[1].x = 310;
        characters[1].y = 150;
        
        // CYBORG
        characters[2] = new CharacterCard();
        characters[2].name = "CYBORG";
        characters[2].character = CharacterIdleAnimationLoader.Character.CYBORG;
        characters[2].animation = CharacterIdleAnimationLoader.loadCharacterIdle(
            CharacterIdleAnimationLoader.Character.CYBORG
        );
        characters[2].x = 540;
        characters[2].y = 150;
        
        // Mark first as selected
        characters[0].selected = true;
    }
    
    /**
     * Update animation state. Call once per frame with deltaTime.
     */
    public void update(float deltaTime) {
        for (CharacterCard card : characters) {
            if (card.animation != null) {
                card.animation.update(deltaTime);
            }
        }
    }
    
    /**
     * Render the entire character selection screen.
     */
    public void render(Graphics2D g) {
        // Main background frame
        BufferedImage mainFrame = frameTiler.buildFrame(800, 600);
        g.drawImage(mainFrame, 0, 0, null);
        
        // Title
        BufferedImage titleFrame = frameTiler.buildFrame(600, 100);
        g.drawImage(titleFrame, 100, 20, null);
        g.setColor(Color.WHITE);
        g.drawString("SELECT YOUR CHARACTER", 250, 70);
        
        // Render character cards
        for (int i = 0; i < characters.length; i++) {
            renderCharacterCard(g, characters[i], i == selectedIndex);
        }
        
        // Instructions
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Use Arrow Keys to Select | Press ENTER to Confirm", 140, 550);
    }
    
    private void renderCharacterCard(Graphics2D g, CharacterCard card, boolean selected) {
        // Card frame background (with selection highlight)
        BufferedImage cardFrame = frameTiler.buildCardFrame(
            card.width, 
            card.height, 
            selected
        );
        g.drawImage(cardFrame, card.x, card.y, null);
        
        // Character idle animation
        if (card.animation != null) {
            BufferedImage idleFrame = card.animation.getCurrentFrame();
            if (idleFrame != null) {
                // Center animation frame within card
                int centerX = card.x + (card.width - idleFrame.getWidth()) / 2;
                int centerY = card.y + 20;  // Top margin
                g.drawImage(idleFrame, centerX, centerY, null);
            }
        }
        
        // Character name below animation
        g.setColor(Color.WHITE);
        int textX = card.x + (card.width - (card.name.length() * 6)) / 2;  // Approximate centering
        g.drawString(card.name, textX, card.y + card.height - 20);
        
        // Selection indicator (if selected)
        if (selected) {
            g.setColor(new Color(255, 200, 0));  // Gold highlight
            g.drawRect(card.x - 2, card.y - 2, card.width + 4, card.height + 4);
            g.drawRect(card.x - 4, card.y - 4, card.width + 8, card.height + 8);
        }
    }
    
    /**
     * Handle navigation input.
     */
    public void handleInput(int keyCode) {
        // LEFT ARROW - Previous character
        if (keyCode == java.awt.event.KeyEvent.VK_LEFT) {
            characters[selectedIndex].selected = false;
            selectedIndex = (selectedIndex - 1 + characters.length) % characters.length;
            characters[selectedIndex].selected = true;
        }
        // RIGHT ARROW - Next character
        else if (keyCode == java.awt.event.KeyEvent.VK_RIGHT) {
            characters[selectedIndex].selected = false;
            selectedIndex = (selectedIndex + 1) % characters.length;
            characters[selectedIndex].selected = true;
        }
    }
    
    /**
     * Get currently selected character.
     */
    public CharacterIdleAnimationLoader.Character getSelectedCharacter() {
        return characters[selectedIndex].character;
    }
    
    /**
     * Get selected character name for passing to gameplay.
     */
    public String getSelectedCharacterName() {
        return characters[selectedIndex].name.toLowerCase();
    }
}
```

---

## STEP 5: Integrate into Game Main Loop

**Modify**: `src/Game.java` (or appropriate main game loop class)

```java
// In game loop or screen manager:
private CharacterSelectScreen charSelectScreen;
private float deltaTime;

public void gameLoop() {
    while (running) {
        // Calculate delta time
        long currentTime = System.nanoTime();
        deltaTime = (currentTime - lastFrameTime) / 1_000_000_000f;
        lastFrameTime = currentTime;
        
        // Update current screen
        if (currentScreen == Screen.CHARACTER_SELECT) {
            charSelectScreen.update(deltaTime);
        }
        
        // Render
        Graphics2D g = (Graphics2D) getGraphics();
        if (currentScreen == Screen.CHARACTER_SELECT) {
            charSelectScreen.render(g);
        }
        
        // Handle input
        // ... input handling code
    }
}
```

---

## STEP 6: Compilation & Testing

### Compile Step:
```powershell
cd "handout"
javac -cp "src" src/gui/AnimationController.java src/gui/CharacterIdleAnimationLoader.java
javac -cp "src" src/gui/screens/CharacterSelectScreen.java
```

### Expected Compilation Output:
```
[No errors]
[Ready to run]
```

### Runtime Testing Checklist:
- [ ] Application starts without errors
- [ ] CharacterSelectScreen initializes without crash
- [ ] Biker character card shows idle animation (breathing, 5 frames)
- [ ] Punk character card shows idle animation (breathing, 5 frames)
- [ ] Cyborg character card shows idle animation (breathing, 4 frames)
- [ ] All animations loop smoothly
- [ ] Arrow keys navigate between characters
- [ ] Selection highlight appears on active character
- [ ] **NO console error messages about missing files**

### Visual Verification:
```
Expected Output When Rendering:
┌─────────────────────────────────────────────────┐
│                                                 │
│   ╔════════════════════════════╗                │
│   ║  SELECT YOUR CHARACTER     ║                │
│   ╚════════════════════════════╝                │
│                                                 │
│  Biker         Punk          Cyborg             │
│  ┌────────┐   ┌────────┐   ┌────────┐          │
│  │ [ANIM] │   │ [ANIM] │   │ [ANIM] │          │
│  │ (idle) │   │ (idle) │   │ (idle) │  ← Active
│  │ loop   │   │        │   │        │          │
│  │ 5fps   │   │        │   │        │          │
│  └────────┘   └────────┘   └────────┘          │
│                                                 │
│  Use Arrow Keys to Select | Press ENTER...    │
└─────────────────────────────────────────────────┘

[ANIMATION FRAMES CHANGING EVERY ~150ms]
```

---

## TROUBLESHOOTING

### Issue: "File not found" error
**Solution**: Verify exact filename in directory. Character name might differ.
```powershell
Get-ChildItem "handout\Resources\industrial-zone\characters\player\biker" -Filter "*Idle*"
```

### Issue: Animation not advancing/frozen
**Cause**: `update(deltaTime)` not being called in main loop  
**Solution**: Ensure CharacterSelectScreen.update() is called every frame with non-zero deltaTime

### Issue: Frames are blank/null
**Cause**: HorizontalSpritesheetLoader.getFrame() returning null  
**Solution**: 
- Verify spritesheet loads successfully
- Check frame count matches actual frames in image
- Add debug logging: `System.out.println("Frame " + i + ": " + (frames[i] != null ? "OK" : "NULL"))`

### Issue: Incorrect frame count
**If Biker/Punk shows wrong animation**:
- Expected: 5 frames (look for `5Frames` in filename)  
- Verify file: `*Biker*Idle*5Frames1Row*`

**If Cyborg shows wrong animation**:
- Expected: 4 frames (look for `4Frames` in filename)
- Verify file: `*Cyborg*Idle*4Frames1Row*`

---

## SUCCESS CRITERIA

✓ **Phase 2 Complete When**:
1. All 3 characters load animations without errors
2. Idle animations visible and looping in character cards
3. Frame advancement occurs at ~150ms intervals
4. No console errors about missing assets
5. Arrow key navigation working
6. ENTER key confirms selection
7. Ready to proceed to Phase 3 (HUD bars)

---

**Next Phase**: HUD Status Bars Integration  
**Estimated After**: Phase 2 completion + testing  
**Dependencies**: AnimationController, HorizontalSpritesheetLoader working correctly

