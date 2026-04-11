# GUI IMPLEMENTATION DIAGRAMS & VISUAL SPECIFICATIONS

## Diagram 1: Game State Machine (Complete)

```mermaid
stateDiagram-v2
    [*] --> MAIN_MENU
    
    MAIN_MENU --> CHARACTER_SELECT: "New Game"
    MAIN_MENU --> LEVEL_SELECT: "Continue"
    MAIN_MENU --> SETTINGS: "Settings (overlay)"
    MAIN_MENU --> [*]: "Quit"
    
    CHARACTER_SELECT --> LEVEL_SELECT: "Character Selected"
    CHARACTER_SELECT --> MAIN_MENU: "Back"
    
    LEVEL_SELECT --> GAME_ACTIVE: "Level Selected"
    LEVEL_SELECT --> MAIN_MENU: "Back"
    
    GAME_ACTIVE --> GAME_PAUSED: "Pause (P key)"
    GAME_ACTIVE --> GAME_OVER: "Player Dies"
    GAME_ACTIVE --> LEVEL_COMPLETE: "Level Complete"
    
    GAME_PAUSED --> GAME_ACTIVE: "Resume"
    GAME_PAUSED --> MAIN_MENU: "Quit to Menu"
    GAME_PAUSED --> SETTINGS: "Settings (overlay)"
    
    GAME_OVER --> LEVEL_SELECT: "Retry"
    GAME_OVER --> MAIN_MENU: "Back to Menu"
    
    LEVEL_COMPLETE --> LEVEL_SELECT: "Next Level"
    LEVEL_COMPLETE --> MAIN_MENU: "Back to Menu"
    
    SETTINGS --> MAIN_MENU: "Apply & Close"
    
    note right of MAIN_MENU
        - Display logo
        - Show menu buttons
        - Background music
    end note
    
    note right of CHARACTER_SELECT
        - Display character cards
        - Show stats
        - Preview animation
    end note
    
    note right of GAME_ACTIVE
        - TopBar (health, ammo)
        - LeftSidebar (inventory)
        - ButtonPanel (actions)
        - HUDBar (zone, time)
    end note
```

## Diagram 2: GUI Component Hierarchy

```mermaid
classDiagram
    class AnimationAndSpriteLoader{
        +loadImage(String): BufferedImage
        +cacheAsset(String, BufferedImage): void
        +getAsset(String): BufferedImage
    }
    
    class TopBarPanel{
        -healthIcon: BufferedImage
        -ammoIcon: BufferedImage
        -healthBar: BufferedImage
        -digits: BufferedImage[]
        +render(PlayerState): BufferedImage
        -drawNumber(Graphics2D, int): void
    }
    
    class LeftSidebar{
        -cardAssets: Map~String, BufferedImage~
        -selectedSlot: int
        +render(Inventory): BufferedImage
        +animateCardFlip(): void
    }
    
    class ButtonPanel{
        -buttons: List~InteractiveButton~
        -panelBackground: BufferedImage
        +handleMouseMove(int, int): void
        +handleMousePress(int, int): void
        +render(): BufferedImage
    }
    
    class HUDBar{
        -zoneNameAsset: BufferedImage
        -timeDisplay: BufferedImage
        -scoreDisplay: BufferedImage
        +render(GameState): BufferedImage
    }
    
    class InteractiveButton{
        -normalImage: BufferedImage
        -hoverImage: BufferedImage
        -pressedImage: BufferedImage
        -state: ButtonState
        -action: Runnable
        +handleMouseMove(int, int): void
        +handleMousePress(int, int): void
        +render(): BufferedImage
    }
    
    class HUDPanel{
        -topBar: TopBarPanel
        -sidebar: LeftSidebar
        -buttons: ButtonPanel
        -hudBar: HUDBar
        +paintComponent(Graphics): void
    }
    
    AnimationAndSpriteLoader <|-- TopBarPanel
    AnimationAndSpriteLoader <|-- LeftSidebar
    AnimationAndSpriteLoader <|-- ButtonPanel
    AnimationAndSpriteLoader <|-- HUDBar
    AnimationAndSpriteLoader <|-- InteractiveButton
    
    HUDPanel --> TopBarPanel
    HUDPanel --> LeftSidebar
    HUDPanel --> ButtonPanel
    HUDPanel --> HUDBar
    ButtonPanel --> InteractiveButton
```

## Diagram 3: Button State Machine (Detailed)

```mermaid
stateDiagram-v2
    [*] --> NORMAL
    
    NORMAL --> HOVER: MouseEnters
    NORMAL --> PRESSED: MouseDown(onButton)
    
    HOVER --> NORMAL: MouseLeaves
    HOVER --> PRESSED: MouseDown
    
    PRESSED --> NORMAL: MouseUp(notOnButton)
    PRESSED --> HOVER: MouseUp(onButton)
    note right of PRESSED
        Action Triggered
        Only if released on button
    end note
    
    DISABLED --> DISABLED: Any event ignored
    NORMAL --> DISABLED: setDisabled(true)
    DISABLED --> NORMAL: setDisabled(false)
```

## Diagram 4: Rendering Pipeline (Frame-by-Frame)

```mermaid
flowchart TD
    A["🎮 Game Loop<br/>(60 FPS)"] --> B["📥 Input Polling<br/>MouseInputHandler"]
    
    B --> C["🔄 Game State Update<br/>Physics, Entities"]
    
    C --> D["📸 Snapshot Current State<br/>Player Health, Ammo, Items"]
    
    D --> E["🎨 Render Background<br/>Level, Parallax, Entities"]
    
    E --> F["🏗️ Render GUI Overlay"]
    
    F --> F1["TopBar.render()"]
    F1 --> F1a["Draw background frame"]
    F1a --> F1b["Draw health/ammo icons"]
    F1b --> F1c["Draw health bar scaled"]
    F1c --> F1d["Draw numeric display"]
    F1d --> F1e["Return BufferedImage"]
    
    F --> F2["LeftSidebar.render()"]
    F2 --> F2a["Draw inventory frame"]
    F2a --> F2b["Draw card slots"]
    F2b --> F2c["Draw collected items"]
    F2c --> F2d["Return BufferedImage"]
    
    F --> F3["ButtonPanel.render()"]
    F3 --> F3a["Draw panel background"]
    F3a --> F3b["Render each button<br/>with current state"]
    F3b --> F3c["Return BufferedImage"]
    
    F --> F4["HUDBar.render()"]
    F4 --> F4a["Draw status info"]
    F4a --> F4b["Return BufferedImage"]
    
    F1e --> G["🖼️ Composite to Main"]
    F2d --> G
    F3c --> G
    F4b --> G
    
    G --> H["📺 Display to Screen<br/>JFrame.paint()"]
    
    H --> I["⏱️ Frame Complete<br/>~16.67ms"]
    
    I --> A
```

## Diagram 5: Asset Loading Architecture

```mermaid
graph TB
    A["GUIAssetManager"] --> B["Startup Phase<br/>Essential Assets"]
    A --> C["Runtime Phase<br/>Cached Access"]
    A --> D["Cleanup Phase<br/>Memory Release"]
    
    B --> B1["Load TopBar Assets<br/>50ms"]
    B --> B2["Load Button Assets<br/>100ms"]
    B --> B3["Load Sidebar Assets<br/>50ms"]
    B --> B4["Async: Level Assets<br/>Background thread"]
    
    C --> C1["Request Asset"]
    C1 --> C2{In Cache?}
    C2 -->|Yes| C3["Return Cached"]
    C2 -->|No| C4["Load from File"]
    C4 --> C5["Cache Result"]
    C5 --> C3
    
    D --> D1["Level Change"]
    D1 --> D2["Clear Cache"]
    D2 --> D3["Flush Images"]
    D3 --> D4["Free Memory"]
```

## Diagram 6: Button Construction & Lifecycle

```mermaid
sequenceDiagram
    participant Game
    participant ButtonPanel
    participant Button as InteractiveButton
    participant AssetManager
    participant Resources
    
    Game->>ButtonPanel: new ButtonPanel()
    ButtonPanel->>ButtonPanel: initializeButtons()
    
    ButtonPanel->>Button: new InteractiveButton("pause", "pause", x, y, action)
    Button->>Button: loadButtonAssets("pause")
    Button->>AssetManager: loadImage(GUI_BUTTONS + "btn_pause.png")
    AssetManager->>Resources: Read file
    Resources-->>AssetManager: BufferedImage
    AssetManager-->>Button: normalImage
    
    Button->>AssetManager: loadImage(GUI_BUTTONS + "btn_pause_hover.png")
    AssetManager->>Resources: Read file
    Resources-->>AssetManager: BufferedImage
    AssetManager-->>Button: hoverImage
    
    Button->>AssetManager: loadImage(GUI_BUTTONS + "btn_pause_pressed.png")
    AssetManager->>Resources: Read file
    Resources-->>AssetManager: BufferedImage
    AssetManager-->>Button: pressedImage
    
    Button-->>ButtonPanel: ✓ Initialized
    
    Note over Button: Runtime: Mouse Interaction
    
    Game->>Button: handleMouseMove(500, 100)
    Button->>Button: isMouseOver()?
    alt Mouse over button
        Button->>Button: state = HOVER
    else Mouse leaves
        Button->>Button: state = NORMAL
    end
    
    Game->>Button: handleMousePress(500, 100)
    alt Is over button
        Button->>Button: state = PRESSED
    end
    
    Game->>Button: handleMouseRelease(500, 100)
    alt Was pressed & still over
        Button->>Button: state = NORMAL
        Button->>Button: action.run()
        Button->>Game: [Button Action]
    end
    
    Game->>Button: render()
    alt state == NORMAL
        Button-->>Game: normalImage
    else state == HOVER
        Button-->>Game: hoverImage
    else state == PRESSED
        Button-->>Game: pressedImage
    end
```

## Diagram 7: Input Event Routing

```mermaid
graph TD
    A["JFrame (GameCore)"] --> B["MouseListener<br/>MouseMotionListener"]
    
    B --> C["MouseInputHandler"]
    
    C --> D["Parse Events"]
    D --> D1["Mouse Position"]
    D --> D2["Mouse Buttons"]
    D --> D3["Button State"]
    
    D1 --> E["Distribute to<br/>GUI Components"]
    D2 --> E
    D3 --> E
    
    E --> E1["ButtonPanel.handleMouse*()"]
    E --> E2["PlayerController<br/>for game input"]
    
    E1 --> E1a["Update button states"]
    E1a --> E1b["Detect button clicks"]
    E1b --> E1c["Trigger actions"]
    
    E1c --> F["Game Action<br/>Dispatch"]
    
    F --> F1["Pause Game"]
    F --> F2["Select Weapon"]
    F --> F3["Open Settings"]
    F --> F4["Show Help"]
```

## Diagram 8: Asset Organization in Resources

```mermaid
graph TB
    GUI["GUI/ (Root)"] --> F["1 Frames/<br/>82 tiles × 7 themes"]
    GUI --> B["2 Bars/<br/>Health, Ammo, Mana"]
    GUI --> I["3 Icons/<br/>Status indicators"]
    GUI --> P["4 Palette/<br/>Color reference"]
    GUI --> L["5 Logo/<br/>Game branding"]
    GUI --> BTN["6 Buttons/<br/>⭐ PRIMARY"]
    GUI --> N["7 Numbers/<br/>Digit rendering"]
    GUI --> C["8 Cursors/<br/>Mouse pointers"]
    GUI --> O["9 Other/<br/>Decorative"]
    GUI --> FT["10 Font/<br/>Text assets"]
    GUI --> CA["card-animations/<br/>Sprite sequences"]
    
    BTN --> BTN1["btn_pause.png"]
    BTN --> BTN2["btn_pause_hover.png"]
    BTN --> BTN3["btn_pause_pressed.png"]
    BTN --> BTN4["btn_play.png"]
    BTN --> BTN5["btn_wpn_1.png"]
    BTN --> BTN6["... variations"]
    
    I --> IB["Buttons2/<br/>Button-specific"]
    I --> II["Icons/<br/>General icons"]
    
    O --> OD["1 Decor/<br/>Frames"]
    O --> OS["2 Skill icons/<br/>Abilities"]
```

## Diagram 9: TopBar Panel Rendering Sequence

```mermaid
sequenceDiagram
    participant Game
    participant TopBar as TopBarPanel
    participant Cache as AssetCache
    
    Game->>TopBar: render(playerState)
    TopBar->>TopBar: new BufferedImage(screenWidth, 48, ARGB)
    TopBar->>TopBar: Graphics2D g2d = canvas.createGraphics()
    
    TopBar->>Cache: getAsset("bgFrame")
    Cache-->>TopBar: BufferedImage
    TopBar->>TopBar: g2d.drawImage(bgFrame, 0, 0)
    
    TopBar->>Cache: getAsset("healthIcon")
    Cache-->>TopBar: BufferedImage
    TopBar->>TopBar: g2d.drawImage(healthIcon, 10, 8)
    
    TopBar->>Cache: getAsset("ammoIcon")
    Cache-->>TopBar: BufferedImage
    TopBar->>TopBar: g2d.drawImage(ammoIcon, 150, 8)
    
    TopBar->>TopBar: barWidth = (playerHealth / 100) * 100
    TopBar->>Cache: getAsset("healthBar")
    Cache-->>TopBar: BufferedImage
    TopBar->>TopBar: g2d.drawImage(healthBar, 50, 15, barWidth, 18)
    
    loop for each digit in health value
        TopBar->>Cache: getAsset("digit_" + digit)
        Cache-->>TopBar: BufferedImage
        TopBar->>TopBar: g2d.drawImage(digitImage, x, y)
    end
    
    TopBar->>TopBar: g2d.dispose()
    TopBar-->>Game: canvas (BufferedImage)
    Game->>Game: composite to main render
```

## Diagram 10: Complete Game State to GUI Mapping

```mermaid
graph TB
    MM["MAIN_MENU<br/>(MainMenuScreen)"] --> MM1["Layout: Centered"]
    MM --> MM2["Assets: Logo, Buttons"]
    MM --> MM3["Input: Menu Navigation"]
    
    CS["CHARACTER_SELECT<br/>(CharacterSelectScreen)"] --> CS1["Layout: Card Grid"]
    CS --> CS2["Assets: Character portraits"]
    CS --> CS3["Dynamic: Stat display"]
    
    LS["LEVEL_SELECT<br/>(LevelSelectScreen)"] --> LS1["Layout: Level Grid"]
    LS --> LS2["Assets: Level thumbnails"]
    LS --> LS3["Dynamic: Progress display"]
    
    GA["GAME_ACTIVE<br/>(InGameGUI)"] --> GA1["TopBar"]
    GA --> GA2["LeftSidebar"]
    GA --> GA3["ButtonPanel"]
    GA --> GA4["HUDBar"]
    
    GA1 --> GA1a["Health, Ammo display"]
    GA2 --> GA2a["Inventory grid"]
    GA3 --> GA3a["Weapon buttons"]
    GA4 --> GA4a["Zone, Time display"]
    
    GP["GAME_PAUSED<br/>(PauseMenuScreen)"] --> GP1["Layout: Centered overlay"]
    GP --> GP2["Background: Dimmed"]
    GP --> GP3["Options: Resume, Quit"]
    
    GO["GAME_OVER<br/>(GameOverScreen)"] --> GO1["Layout: Centered"]
    GO --> GO2["Dynamic: Score display"]
    GO --> GO3["Options: Retry, Quit"]
    
    LC["LEVEL_COMPLETE<br/>(CompleteScreen)"] --> LC1["Layout: Score display"]
    LC --> LC2["Dynamic: Bonus calc"]
    LC --> LC3["Options: Next, Menu"]
```

## Diagram 11: Memory Management Lifecycle

```mermaid
graph TD
    A["Game Initialization"] --> B["Phase 1: Load<br/>Essential Assets"]
    B --> B1["TopBar: 50ms"]
    B --> B2["Buttons: 100ms"]
    B --> B3["Sidebar: 50ms"]
    B1 --> C["Memory Usage: ~20MB"]
    B2 --> C
    B3 --> C
    
    C --> D["Phase 2: Runtime<br/>Cached Access"]
    D --> D1["Asset requests"]
    D --> D2{Cached?}
    D2 -->|Yes| D3["Return from cache"]
    D2 -->|No| D4["Load & cache"]
    D3 --> E["Active Game"]
    D4 --> E
    
    E --> F["Player Action:<br/>Level Change"]
    F --> G["Phase 3: Cleanup"]
    G --> G1["Identify assets<br/>to remove"]
    G --> G2["Flush images"]
    G --> G3["Clear caches"]
    G --> H["Memory freed: ~20MB"]
    H --> I["Load new level<br/>assets"]
    I --> J["Back to Phase 2"]
```

---

## Implementation Checklist

### Phase 1: Foundation
- [ ] Create `GUIAssetManager.java` (centralized loader)
- [ ] Create `AnimationAndSpriteLoaderExtension.java` (base for GUI components)
- [ ] Verify all asset paths from Resources folder
- [ ] Set up asset caching system
- [ ] Test image loading pipeline (verify no vector code)

### Phase 2: Components
- [ ] Implement `TopBarPanel.java`
- [ ] Implement `LeftSidebar.java`
- [ ] Implement `InteractiveButton.java`
- [ ] Implement `ButtonPanel.java`
- [ ] Implement `HUDBar.java`
- [ ] Test each component independently

### Phase 3: Screens
- [ ] Implement `MainMenuScreen.java`
- [ ] Implement `CharacterSelectScreen.java`
- [ ] Implement `LevelSelectScreen.java`
- [ ] Implement `PauseMenuScreen.java`
- [ ] Implement `GameOverScreen.java`
- [ ] Implement `LevelCompleteScreen.java`

### Phase 4: Integration
- [ ] Wire input handlers
- [ ] Connect state machine
- [ ] Test all transitions
- [ ] Verify button actions

### Phase 5: Polish
- [ ] Add animations
- [ ] Add visual effects
- [ ] Performance optimization
- [ ] Memory profiling

---

## Key Principles (Never Violate)

✅ **Use ONLY image assets** - No Graphics2D drawing  
✅ **Extend AnimationAndSpriteLoader** - Reuse base functionality  
✅ **Real asset paths** - Use GUI_BASE constants  
✅ **State-driven rendering** - GUI changes with game state  
✅ **Efficient caching** - Load once, use many times  
✅ **Clean input handling** - Mouse → ButtonPanel → Game  

❌ **NO vector graphics** - fillRect, drawString, etc.  
❌ **NO hardcoded dimensions** - Use asset actual sizes  
❌ **NO placeholder colors** - No Color-based fallbacks  
❌ **NO mixing paradigms** - Pure image-based only  
