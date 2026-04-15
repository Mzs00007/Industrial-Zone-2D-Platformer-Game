package tests;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.*;
import animation.HorizontalSpritesheetLoader;
import animation.SequenceFrameAnimationLoader;
import javax.sound.midi.*;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║   INTERACTIVE VISUAL GAME TESTER  —  Industrial Zone 2D Platformer  ║
 * ║   YOU are the tester. Nothing is auto-graded.                        ║
 * ╠══════════════════════════════════════════════════════════════════════╣
 * ║  Tab 1 🌅  Parallax     — drag mouse or auto-scroll 5 BG layers     ║
 * ║  Tab 2 🎮  Player       — WASD / Space / Z X C  +  1-2-3 chars      ║
 * ║  Tab 3 🔫  Weapons      — equip gun, aim with mouse, click to fire   ║
 * ║  Tab 4 👾  Enemies      — all drone animations, A/D to cycle         ║
 * ║  Tab 5 🧱  Tiles        — full 81-tile palette, hover for name       ║
 * ║  Tab 6 💥  VFX          — click to spawn smoke / sparks              ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 *
 *  Run from handout/ :
 *   javac -d bin src/tests/MasterGameTestSuite.java
 *   java  -cp bin  tests.MasterGameTestSuite
 */
public class InteractiveGameTester extends JFrame implements KeyListener {

    // ── Paths (relative to handout/ working dir) ─────────────────────────────
    private static final String RES      = "Resources/industrial-zone/";
    private static final String BG_L1    = RES + "1 Tiles/Industrial_zone_level_1/2 Background_level_1/";
    private static final String BIKER    = RES + "characters/player/biker/";
    private static final String PUNK     = RES + "characters/player/punk/";
    private static final String CYBORG   = RES + "characters/player/cyborg/";
    private static final String DRONE    = RES + "characters/enemies/drones/1/";
    private static final String TILES1   = RES + "1 Tiles/Industrial_zone_level_1/1 Tiles/";
    private static final String SMOKE    = RES + "vfx/1 Smoke/";
    private static final String SPARKS   = RES + "vfx/3 Sparks/";
    private static final String GUNS     = RES + "weapons/1/2 Guns/";
    private static final String BULLETS  = RES + "weapons/1/5 Bullets/";
    private static final String FX       = RES + "weapons/1/4 Shoot_effects/";
    private static final String MIDI_DIR = RES + "audio/music_midi/";

    // ── Colours ───────────────────────────────────────────────────────────────
    static final Color C_BG      = new Color(18, 18, 32);
    static final Color C_PANEL   = new Color(28, 28, 50);
    static final Color C_ACCENT  = new Color(0,  220, 170);
    static final Color C_WARN    = new Color(255, 90,  90);
    static final Color C_TEXT    = new Color(200, 215, 240);
    static final Color C_TABSEL  = new Color(0,  200, 160);
    static final Color C_TABIDLE = new Color(42,  42, 72);

    // ── State shared with canvas ───────────────────────────────────────────────
    final Set<Integer> keysDown = new HashSet<>();
    int activeTab = 0;

    // ── UI ────────────────────────────────────────────────────────────────────
    GameCanvas canvas;
    JLabel statusBar;
    JButton[] tabBtns = new JButton[7];

    // ═════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}
            new InteractiveGameTester().setVisible(true);
        });
    }

    public InteractiveGameTester() {
        super("🎮  Industrial Zone — Interactive Visual Tester");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(C_BG);

        add(buildSidebar(), BorderLayout.WEST);

        canvas = new GameCanvas();
        canvas.setPreferredSize(new Dimension(1000, 540));
        add(canvas, BorderLayout.CENTER);

        statusBar = new JLabel("  Loading assets…");
        statusBar.setForeground(C_ACCENT);
        statusBar.setBackground(new Color(10, 10, 22));
        statusBar.setOpaque(true);
        statusBar.setFont(new Font("Consolas", Font.PLAIN, 12));
        statusBar.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        add(statusBar, BorderLayout.SOUTH);

        addKeyListener(this);
        setFocusable(true);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);

        new Thread(() -> {
            canvas.loadAll();
            SwingUtilities.invokeLater(() -> {
                statusBar.setText("  Assets loaded. Click viewport → use keyboard + mouse.");
                switchTab(0);
            });
        }).start();

        new javax.swing.Timer(16, e -> { canvas.tick(); canvas.repaint(); }).start();
    }

    // ── Sidebar ───────────────────────────────────────────────────────────────
    private JPanel buildSidebar() {
        JPanel p = new JPanel();
        p.setBackground(C_PANEL);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(12, 6, 12, 6));
        p.setPreferredSize(new Dimension(168, 540));

        JLabel title = new JLabel("<html><center><b>VISUAL<br>TESTER</b></center></html>");
        title.setForeground(C_ACCENT);
        title.setFont(new Font("Consolas", Font.BOLD, 17));
        title.setAlignmentX(0.5f);
        p.add(title);
        p.add(Box.createVerticalStrut(14));

        String[] labels = {
            "🌅  Parallax",
            "🎮  Player",
            "🔫  Weapons",
            "👾  Enemies",
            "🧱  Tiles",
            "💥  VFX",
            "🔊  Sound"
        };
        for (int i = 0; i < labels.length; i++) {
            final int idx = i;
            JButton b = new JButton(labels[i]);
            b.setMaximumSize(new Dimension(158, 44));
            b.setAlignmentX(0.5f);
            b.setBackground(C_TABIDLE);
            b.setForeground(C_TEXT);
            b.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));
            b.setFocusPainted(false);
            b.setBorderPainted(false);
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            b.addActionListener(e -> switchTab(idx));
            tabBtns[i] = b;
            p.add(b);
            p.add(Box.createVerticalStrut(7));
        }
        return p;
    }

    void switchTab(int t) {
        activeTab = t;
        for (int i = 0; i < tabBtns.length; i++) {
            boolean sel = i == t;
            tabBtns[i].setBackground(sel ? C_TABSEL : C_TABIDLE);
            tabBtns[i].setForeground(sel ? Color.BLACK : C_TEXT);
        }
        canvas.onTabSwitch(t);
        requestFocusInWindow();
    }

    @Override public void keyPressed(KeyEvent e)  { keysDown.add(e.getKeyCode()); canvas.onKey(e, true);  }
    @Override public void keyReleased(KeyEvent e) { keysDown.remove(e.getKeyCode()); canvas.onKey(e, false); }
    @Override public void keyTyped(KeyEvent e)    {}

    // ═════════════════════════════════════════════════════════════════════════
    //  GAME CANVAS
    // ═════════════════════════════════════════════════════════════════════════
    class GameCanvas extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

        // ── Sprite animation container ────────────────────────────────────────
        static class Anim {
            final String  name;
            final BufferedImage sheet;
            final int frames, fw, fh, msPerFrame;
            final boolean loop;

            Anim(String name, BufferedImage img, int frames, int ms, boolean loop) {
                this.name = name; this.sheet = img;
                this.frames = frames; this.msPerFrame = ms; this.loop = loop;
                fw = (img != null) ? img.getWidth()  / Math.max(frames, 1) : 0;
                fh = (img != null) ? img.getHeight() : 0;
            }
            boolean ok() { return sheet != null; }
            /** Build from a HorizontalSpritesheetLoader (uses its full sheet). */
            static Anim fromHSL(HorizontalSpritesheetLoader h) {
                return new Anim(
                    h != null ? h.getName()       : "?",
                    h != null ? h.getSheet()      : null,
                    h != null ? h.getFrameCount() : 1,
                    h != null ? h.getMsPerFrame() : 100,
                    h == null || h.isLoop());
            }
            BufferedImage frame(int f) {
                if (!ok()) return null;
                f = loop ? (f % frames) : Math.min(f, frames - 1);
                return sheet.getSubimage(f * fw, 0, fw, fh);
            }
        }

        // ── VFX particle ─────────────────────────────────────────────────────
        static class Particle {
            int x, y, animIdx, frame, timer;
            int type; // 0=smoke 1=sparks
            Particle(int x, int y, int type, int animIdx) {
                this.x=x; this.y=y; this.type=type; this.animIdx=animIdx;
            }
        }

        // ── Asset buckets ─────────────────────────────────────────────────────
        BufferedImage[] bgLayers  = new BufferedImage[5];
        float[]         bgFactors = {0f, 0.15f, 0.25f, 0.40f, 0.60f};
        String[]        bgNames   = {"Sky (static)","Trees ×0.15","Far factory ×0.25","Mid factory ×0.40","Near factory ×0.60"};

        Anim[][] chars   = new Anim[3][24];  // [char][anim]
        String[] charTag = {"Biker","Punk","Cyborg"};

        BufferedImage[] gunImgs, bulletImgs, fxImgs;
        Anim[]          droneAnims;
        BufferedImage[] tileImgs;
        String[]        tileNames;
        Anim            smokeAnim;
        Anim[]          sparkAnims;

        // One-shot anim indices (0-indexed matching filename order 0-23)
        static final boolean[] ONE_SHOT = {
            false,false,false,false,true,true,true,false,false,false,
            true,true,true,true,true,true,true,true,true,true,true,false,false,false
        };

        // ── Shared loop state ─────────────────────────────────────────────────
        long lastMs = System.currentTimeMillis();
        int fps, fpsCount; long fpsMs;

        // ── Tab 0: Parallax ───────────────────────────────────────────────────
        float camX = 0;
        boolean autoScroll = true;
        float scrollSpeed  = 2f;
        int   dragStartX   = -1;
        float dragCamStart = 0;

        // ── Tab 1: Player ────────────────────────────────────────────────────
        int    curChar  = 0, curAnim = 0, curFrame = 0;
        long   animMs   = 0;
        float  pX = 400, pY = 340;
        float  pVY = 0;
        boolean pGround = true, pFacing = true;
        boolean pOneShot = false;
        int     pPending = -1;    // requested one-shot from key

        // ── Tab 2: Weapons ───────────────────────────────────────────────────
        int curGun = 0, curBullet = 0;
        int mx = 500, my = 270;
        List<float[]> bullets = new ArrayList<>(); // [x,y,vx,vy,life_ms]

        // ── Tab 3: Enemies ───────────────────────────────────────────────────
        int curDroneAnim = 0, droneFrame = 0;
        long droneMs = 0;

        // ── Tab 4: Tiles ─────────────────────────────────────────────────────
        int tileScrollY = 0, hovTile = -1;

        // ── Tab 6: Sound / MIDI ────────────────────────────────────────────────
        File[] midiFiles;
        int    curMidi    = -1;
        boolean midiPlaying = false;
        Sequencer midiSeq;

        // ── Tab 5: VFX ───────────────────────────────────────────────────────
        List<Particle> particles = new ArrayList<>();
        int vfxType = 0; // 0=smoke 1=sparks

        // ─────────────────────────────────────────────────────────────────────
        GameCanvas() {
            setBackground(C_BG);
            addMouseListener(this);
            addMouseMotionListener(this);
            addMouseWheelListener(this);
        }

        // ── Asset loading ─────────────────────────────────────────────────────
        void loadAll() {
            loadBg();
            loadChars();
            loadWeapons();
            loadDrones();
            loadTiles();
            loadVfx();
            loadMidi();
        }

        private BufferedImage img(String path) {
            try { return ImageIO.read(new File(path)); }
            catch (Exception e) { return null; }
        }

        private File[] sorted(String dir) {
            File d = new File(dir);
            if (!d.exists()) return new File[0];
            File[] f = d.listFiles(x -> x.getName().toLowerCase().endsWith(".png"));
            if (f == null) return new File[0];
            Arrays.sort(f);
            return f;
        }

        private int pf(String fn) { // parse frame count from filename
            Matcher m = Pattern.compile("(\\d+)Frames").matcher(fn);
            return m.find() ? Integer.parseInt(m.group(1)) : 1;
        }
        private int pm(String fn) { // parse ms per frame
            Matcher m = Pattern.compile("_(\\d+)ms\\.").matcher(fn);
            return m.find() ? Integer.parseInt(m.group(1)) : 100;
        }
        private boolean isLoop(String fn) { return fn.contains("Loop"); }

        private String shortName(String fname) {
            String[] p = fname.split("_");
            return p.length >= 4 ? p[3] : fname.replaceAll("\\.png$","");
        }

        private void loadBg() {
            String[] files = {
                BG_L1+"BG_Layer1_SkyBase_SolidLavenderGrey_StaticFill_DrawFirstNoScroll.png",
                BG_L1+"BG_Layer2_FractalTreeSilhouette_MintSkyBlackCracks_ParallaxFactor015.png",
                BG_L1+"BG_Layer3_FarFactorySilhouette_LightBlueIndustrial_ParallaxFactor025.png",
                BG_L1+"BG_Layer4_MidFactorySilhouette_MediumBluePipeDetail_ParallaxFactor040.png",
                BG_L1+"BG_Layer5_NearFactorySilhouette_DarkNavyLargeTank_ParallaxFactor060.png"
            };
            for (int i = 0; i < files.length; i++) bgLayers[i] = img(files[i]);
        }

        private void loadChars() {
            String[] paths = {BIKER, PUNK, CYBORG};
            for (int c = 0; c < 3; c++) {
                File[] files = sorted(paths[c]);
                for (int a = 0; a < Math.min(files.length, 24); a++) {
                    chars[c][a] = Anim.fromHSL(
                        HorizontalSpritesheetLoader.fromFilename(files[a].getAbsolutePath()));
                }
            }
        }

        private void loadWeapons() {
            File[] gf = sorted(GUNS);    gunImgs    = toImgs(gf);
            File[] bf = sorted(BULLETS); bulletImgs = toImgs(bf);
            File[] ff = sorted(FX);      fxImgs     = toImgs(ff);
        }

        private BufferedImage[] toImgs(File[] files) {
            BufferedImage[] out = new BufferedImage[files.length];
            for (int i = 0; i < files.length; i++) out[i] = img(files[i].getAbsolutePath());
            return out;
        }

        private void loadDrones() {
            File[] files = sorted(DRONE);
            droneAnims = new Anim[files.length];
            for (int i = 0; i < files.length; i++) {
                droneAnims[i] = Anim.fromHSL(
                    HorizontalSpritesheetLoader.fromFilename(files[i].getAbsolutePath()));
            }
        }

        private void loadTiles() {
            File[] files = sorted(TILES1);
            tileImgs  = new BufferedImage[files.length];
            tileNames = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                tileImgs[i]  = img(files[i].getAbsolutePath());
                tileNames[i] = files[i].getName();
            }
        }

        private void loadVfx() {
            // Smoke — stitch 18 separate PNGs via SequenceFrameAnimationLoader
            smokeAnim = Anim.fromHSL(
                SequenceFrameAnimationLoader.fromDirectory(SMOKE, "Smoke", 80, false));

            // Sparks — each is a 4-frame strip
            File[] spf = sorted(SPARKS);
            sparkAnims = new Anim[spf.length];
            for (int i = 0; i < spf.length; i++)
                sparkAnims[i] = Anim.fromHSL(
                    HorizontalSpritesheetLoader.fromFile(spf[i].getAbsolutePath(),
                                                         "Sparks"+i, 4, 80, false));
        }

        // ── Game loop tick ────────────────────────────────────────────────────
        void tick() {
            long now = System.currentTimeMillis();
            long dt  = now - lastMs; lastMs = now;
            fpsCount++; fpsMs += dt;
            if (fpsMs >= 1000) { fps = fpsCount; fpsCount = 0; fpsMs = 0; }

            switch (activeTab) {
                case 0: tickParallax(dt); break;
                case 1: tickPlayer(dt);   break;
                case 2: tickWeapons(dt);  break;
                case 3: tickDrones(dt);   break;
                case 5: tickVfx(dt);      break;
                case 6: tickSound();      break;
            }
        }

        private void tickParallax(long dt) {
            if (autoScroll) { camX += scrollSpeed; if (camX > 4000) camX = 0; }
        }

        private void tickPlayer(long dt) {
            boolean left  = keysDown.contains(KeyEvent.VK_A) || keysDown.contains(KeyEvent.VK_LEFT);
            boolean right = keysDown.contains(KeyEvent.VK_D) || keysDown.contains(KeyEvent.VK_RIGHT);
            boolean run   = keysDown.contains(KeyEvent.VK_SHIFT);

            // Movement & camera
            if (right) { pX += run ? 5 : 3; pFacing = true; }
            if (left)  { pX -= run ? 5 : 3; pFacing = false; }
            pX = Math.max(0, Math.min(getWidth() - 120, pX));
            camX = Math.max(0, pX - getWidth() * 0.4f);

            // Gravity
            if (!pGround) {
                pVY += 0.7f; pY += pVY;
                if (pY >= 340) { pY = 340; pVY = 0; pGround = true; }
            }

            // Choose animation
            if (pPending >= 0) {
                curAnim = pPending; curFrame = 0; animMs = 0;
                pOneShot = curAnim < ONE_SHOT.length && ONE_SHOT[curAnim];
                pPending = -1;
            } else if (!pOneShot) {
                int want = 0;
                if (!pGround && pVY < 0)  want = 5;  // jump
                else if (!pGround)         want = 7;  // fall
                else if (left || right)    want = run ? 3 : 2; // run/walk
                else                       want = 0;  // idle
                if (want != curAnim) { curAnim = want; curFrame = 0; animMs = 0; }
            }

            // Advance frame
            Anim a = curAnim < 24 ? chars[curChar][curAnim] : null;
            if (a != null && a.ok()) {
                animMs += dt;
                if (animMs >= a.msPerFrame) {
                    animMs = 0; curFrame++;
                    if (curFrame >= a.frames) {
                        curFrame = a.loop ? 0 : a.frames - 1;
                        if (pOneShot) { pOneShot = false; curAnim = 0; curFrame = 0; }
                    }
                }
            }
        }

        private void tickWeapons(long dt) {
            List<float[]> dead = new ArrayList<>();
            for (float[] b : bullets) {
                b[0] += b[2]; b[1] += b[3]; b[4] -= dt;
                if (b[4] <= 0 || b[0]<0||b[0]>getWidth()||b[1]<0||b[1]>getHeight()) dead.add(b);
            }
            bullets.removeAll(dead);
            // Idle anim advance for player silhouette
            Anim a = chars[0][0];
            if (a != null && a.ok()) {
                animMs += dt;
                if (animMs >= a.msPerFrame) { animMs = 0; curFrame = (curFrame+1) % a.frames; }
            }
        }

        private void tickDrones(long dt) {
            if (droneAnims == null || droneAnims.length == 0) return;
            Anim a = droneAnims[curDroneAnim];
            if (a == null || !a.ok()) return;
            droneMs += dt;
            if (droneMs >= a.msPerFrame) { droneMs = 0; droneFrame = (droneFrame+1) % a.frames; }
        }

        private void tickVfx(long dt) {
            List<Particle> dead = new ArrayList<>();
            for (Particle p : particles) {
                Anim a = getParticleAnim(p);
                if (a == null || !a.ok()) { dead.add(p); continue; }
                p.timer += dt;
                if (p.timer >= a.msPerFrame) { p.timer = 0; p.frame++; }
                if (p.frame >= a.frames) dead.add(p);
            }
            particles.removeAll(dead);
        }

        private Anim getParticleAnim(Particle p) {
            if (p.type == 0) return smokeAnim;
            if (sparkAnims != null && p.animIdx < sparkAnims.length) return sparkAnims[p.animIdx];
            return null;
        }

        void onTabSwitch(int t) {
            activeTab = t;
            bullets.clear(); particles.clear();
            if (t != 6) stopMidi();
            if (t == 1) { pX = 400; pY = 340; curAnim = 0; curFrame = 0; pOneShot = false; pPending = -1; camX = 0; }
            if (t == 3) { curDroneAnim = 0; droneFrame = 0; droneMs = 0; }
        }

        void onKey(KeyEvent e, boolean pressed) {
            if (!pressed) return;
            int k = e.getKeyCode();
            if (activeTab == 0) {
                if (k==KeyEvent.VK_A)         autoScroll = !autoScroll;
                if (k==KeyEvent.VK_UP)        scrollSpeed = Math.min(12, scrollSpeed + 0.5f);
                if (k==KeyEvent.VK_DOWN)      scrollSpeed = Math.max(0,  scrollSpeed - 0.5f);
            }
            if (activeTab == 1) {
                if (k==KeyEvent.VK_SPACE) { if(pGround){pGround=false;pVY=-16;pPending=5;}else{pPending=6;} }
                if (k==KeyEvent.VK_Z)  pPending = 12;
                if (k==KeyEvent.VK_X)  pPending = 13;
                if (k==KeyEvent.VK_C)  pPending = 14;
                if (k==KeyEvent.VK_F)  pPending = 4;  // dash
                if (k==KeyEvent.VK_H)  pPending = 17; // hurt
                if (k==KeyEvent.VK_K)  pPending = 18; // death
                if (k==KeyEvent.VK_E)  pPending = 19; // use
                if (k==KeyEvent.VK_T)  pPending = 23; // talk
                if (k==KeyEvent.VK_1)  { curChar=0; curAnim=0; curFrame=0; pOneShot=false; }
                if (k==KeyEvent.VK_2)  { curChar=1; curAnim=0; curFrame=0; pOneShot=false; }
                if (k==KeyEvent.VK_3)  { curChar=2; curAnim=0; curFrame=0; pOneShot=false; }
            }
            if (activeTab == 2) {
                if (k==KeyEvent.VK_Q) curGun    = (curGun-1+Math.max(1,gunImgs.length))    % Math.max(1,gunImgs.length);
                if (k==KeyEvent.VK_E) curGun    = (curGun+1) % Math.max(1,gunImgs.length);
                if (k==KeyEvent.VK_1) curBullet = 0;
                if (k==KeyEvent.VK_2) curBullet = Math.min(1, bulletImgs.length-1);
                if (k==KeyEvent.VK_3) curBullet = Math.min(2, bulletImgs.length-1);
            }
            if (activeTab == 3) {
                if (k==KeyEvent.VK_D||k==KeyEvent.VK_RIGHT) { curDroneAnim=(curDroneAnim+1)%Math.max(1,droneAnims.length); droneFrame=0; droneMs=0; }
                if (k==KeyEvent.VK_A||k==KeyEvent.VK_LEFT)  { curDroneAnim=Math.max(0,curDroneAnim-1); droneFrame=0; droneMs=0; }
            }
            if (activeTab == 5) {
                if (k==KeyEvent.VK_1) vfxType=0;
                if (k==KeyEvent.VK_2) vfxType=1;
            }
        }

        // ── Rendering ─────────────────────────────────────────────────────────
        @Override
        protected void paintComponent(Graphics g0) {
            super.paintComponent(g0);
            Graphics2D g = (Graphics2D) g0.create();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(C_BG); g.fillRect(0, 0, getWidth(), getHeight());
            switch (activeTab) {
                case 0: drawParallax(g); break;
                case 1: drawPlayer(g);   break;
                case 2: drawWeapons(g);  break;
                case 3: drawDrones(g);   break;
                case 4: drawTiles(g);    break;
                case 5: drawVfx(g);      break;
                case 6: drawSound(g);    break;
            }
            // FPS
            g.setFont(new Font("Consolas",Font.PLAIN,11));
            g.setColor(new Color(80,255,150,180));
            g.drawString("FPS "+fps, getWidth()-55, 16);
            g.dispose();
        }

        // ── TAB 0: Parallax ───────────────────────────────────────────────────
        private void drawParallax(Graphics2D g) {
            int W=getWidth(), H=getHeight();
            // Draw all 5 layers
            for (int i = 0; i < bgLayers.length; i++) {
                if (bgLayers[i] == null) {
                    g.setColor(new Color(40+i*15, 20, 60)); g.fillRect(0,0,W,H);
                    g.setColor(C_WARN);
                    g.setFont(new Font("Consolas",Font.BOLD,13));
                    g.drawString("Layer "+(i+1)+" not loaded — check path: "+BG_L1, 20, 60);
                    continue;
                }
                float scroll = camX * bgFactors[i];
                int iW = bgLayers[i].getWidth(), iH = bgLayers[i].getHeight();
                double sh = (double)H / iH;
                int dW = (int)(iW*sh);
                int x0 = -(int)(scroll % dW);
                for (int x = x0 - dW; x < W; x += dW)
                    g.drawImage(bgLayers[i], x, 0, dW, H, null);
            }
            // Info panel
            int pw = 330, ph = 175;
            int px = 12, py = H - ph - 12;
            g.setColor(new Color(0,0,0,170)); g.fillRoundRect(px,py,pw,ph,10,10);
            g.setFont(new Font("Consolas",Font.BOLD,13)); g.setColor(C_ACCENT);
            g.drawString("PARALLAX SCROLL TEST", px+12, py+22);
            g.setFont(new Font("Consolas",Font.PLAIN,11)); g.setColor(C_TEXT);
            g.drawString("Camera X : "+(int)camX, px+12, py+40);
            g.drawString("Speed    : "+String.format("%.1f",scrollSpeed)+" px/frame", px+12, py+56);
            g.drawString("Auto-scroll : "+(autoScroll?"ON ":"OFF")+"  [A = toggle]", px+12, py+72);
            g.drawString("↑ / ↓   — faster / slower", px+12, py+88);
            g.drawString("Drag mouse left-right to pan", px+12, py+104);
            g.setFont(new Font("Consolas",Font.PLAIN,10));
            for (int i = 0; i < bgNames.length; i++) {
                boolean loaded = bgLayers[i] != null;
                g.setColor(loaded ? new Color(80,255,130) : C_WARN);
                g.drawString((loaded?"✓ ":"✗ ") + "Layer "+(i+1)+": "+bgNames[i], px+12, py+122+i*11);
            }
        }

        // ── TAB 1: Player ─────────────────────────────────────────────────────
        private void drawPlayer(Graphics2D g) {
            int W=getWidth(), H=getHeight();
            drawBgScroll(g, W, H);  // scrolling background behind player

            // Ground
            int groundY = 420;
            g.setColor(new Color(50,35,70,220)); g.fillRect(0, groundY, W, H-groundY);
            g.setColor(new Color(90,65,120));     g.fillRect(0, groundY, W, 3);

            // Player sprite
            Anim anim = (curAnim<24 && chars[curChar]!=null) ? chars[curChar][curAnim] : null;
            if (anim != null && anim.ok()) {
                BufferedImage fr = anim.frame(curFrame);
                if (fr != null) {
                    int sc = 3;
                    int dw = fr.getWidth()*sc, dh = fr.getHeight()*sc;
                    int sx = (int)pX, sy = groundY - dh;
                    AffineTransform at = AffineTransform.getTranslateInstance(sx, sy);
                    if (!pFacing) { at.translate(dw,0); at.scale(-1,1); }
                    at.scale(sc, sc);
                    g.drawImage(fr, at, null);
                    g.setColor(new Color(0,0,0,70));
                    g.fillOval(sx+dw/4, groundY-5, dw/2, 9);
                }
            }

            // HUD — character selector
            hud(g, 10, 10, 195, 82, () -> {
                g.setFont(new Font("Consolas",Font.BOLD,12)); g.setColor(C_ACCENT);
                g.drawString("CHARACTER  [1/2/3]", 18, 28);
                String[] cn = {"1.Biker","2.Punk","3.Cyborg"};
                for (int i=0;i<3;i++) {
                    g.setColor(i==curChar ? C_ACCENT : C_TEXT);
                    g.drawString((i==curChar?"► ":"  ")+cn[i], 18, 46+i*16);
                }
            });

            // HUD — animation + controls
            hud(g, 10, H-200, 335, 195, () -> {
                Anim a2 = (curAnim<24 && chars[curChar]!=null) ? chars[curChar][curAnim] : null;
                g.setFont(new Font("Consolas",Font.BOLD,12)); g.setColor(C_ACCENT);
                g.drawString("ANIM: "+(a2!=null?a2.name:"?")+"  frame "+curFrame+"/"+(a2!=null?a2.frames:0), 18, H-184);
                g.setFont(new Font("Consolas",Font.PLAIN,10)); g.setColor(C_TEXT);
                String[][] ctrl = {{"A/D","Walk left/right"},{"SHIFT+A/D","Run"},
                    {"SPACE","Jump  (again = double)"},{"Z","Attack 1"},{"X","Attack 2"},
                    {"C","Attack 3"},{"F","Dash"},{"H","Hurt"},{"K","Death"},
                    {"E","Use / Interact"},{"T","Talk"},{"1/2/3","Switch character"}};
                for (int i=0;i<ctrl.length;i++) {
                    g.setColor(new Color(255,210,80));
                    g.drawString(String.format("%-12s",ctrl[i][0]), 18, H-166+i*13);
                    g.setColor(C_TEXT);
                    g.drawString(ctrl[i][1], 116, H-166+i*13);
                }
            });
        }

        // ── TAB 2: Weapons ────────────────────────────────────────────────────
        private void drawWeapons(Graphics2D g) {
            int W=getWidth(), H=getHeight();
            drawBgScroll(g, W, H);

            int groundY = 400;
            g.setColor(new Color(50,35,70,220)); g.fillRect(0, groundY, W, H-groundY);
            g.setColor(new Color(90,65,120));     g.fillRect(0, groundY, W, 3);

            // Gun palette panel (left)
            int GP = 210;
            g.setColor(new Color(0,0,0,180)); g.fillRect(0, 0, GP, H);
            g.setFont(new Font("Consolas",Font.BOLD,11)); g.setColor(C_ACCENT);
            g.drawString("GUNS  Q/E cycle", 8, 16);
            int T = 54;
            for (int i=0; gunImgs!=null && i<gunImgs.length; i++) {
                int gc = i%3, gr = i/3;
                int gx = 6+gc*(T+5), gy = 24+gr*(T+5);
                boolean sel = i==curGun;
                g.setColor(sel ? C_TABSEL : new Color(38,38,62));
                g.fillRoundRect(gx,gy,T,T,5,5);
                if (sel) { g.setColor(C_ACCENT); g.drawRoundRect(gx,gy,T,T,5,5); }
                if (gunImgs[i]!=null) drawCentered(g, gunImgs[i], gx,gy,T,T);
                else { g.setColor(C_WARN); g.drawString("?",(int)(gx+T*0.45),(int)(gy+T*0.6)); }
                g.setFont(new Font("Consolas",Font.PLAIN,8)); g.setColor(new Color(120,130,160));
                g.drawString(String.valueOf(i+1), gx+2, gy+T-2);
            }

            // Player silhouette in centre with gun
            int px = W/2, py = groundY;
            Anim idle = chars[0][0];
            if (idle != null && idle.ok()) {
                BufferedImage fr = idle.frame(curFrame % idle.frames);
                if (fr != null) {
                    int sc=3, dw=fr.getWidth()*sc, dh=fr.getHeight()*sc;
                    g.drawImage(fr, px-dw/2, py-dh, dw, dh, null);
                }
            }
            // Gun arm aimed at mouse
            int gunOriginX = px+10, gunOriginY = py-90;
            double angle = Math.atan2(my - gunOriginY, mx - gunOriginX);
            if (gunImgs!=null && curGun<gunImgs.length && gunImgs[curGun]!=null) {
                AffineTransform old = g.getTransform();
                g.translate(gunOriginX, gunOriginY);
                g.rotate(angle);
                BufferedImage gun = gunImgs[curGun];
                g.drawImage(gun, 0, -gun.getHeight(), gun.getWidth()*2, gun.getHeight()*2, null);
                g.setTransform(old);
            }

            // Aim line
            g.setColor(new Color(255,80,80,90));
            g.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawLine(gunOriginX, gunOriginY, mx, my);
            g.setStroke(new BasicStroke(1));

            // Bullets
            for (float[] b : bullets) {
                g.setColor(new Color(255,240,80));
                g.fillOval((int)b[0]-4,(int)b[1]-4,8,8);
                g.setColor(new Color(255,180,40,120));
                g.fillOval((int)(b[0]-b[2])-3,(int)(b[1]-b[3])-3,6,6);
            }

            // HUD
            hud(g, W-255, H-95, 250, 88, () -> {
                g.setFont(new Font("Consolas",Font.BOLD,12)); g.setColor(C_ACCENT);
                g.drawString("WEAPONS TESTER", W-245, H-77);
                g.setFont(new Font("Consolas",Font.PLAIN,11)); g.setColor(C_TEXT);
                g.drawString("Q/E    — cycle guns ("+(curGun+1)+"/"+(gunImgs!=null?gunImgs.length:0)+")", W-245, H-59);
                g.drawString("Mouse  — aim direction", W-245, H-44);
                g.drawString("Click  — fire bullet", W-245, H-29);
            });
        }

        // ── TAB 3: Enemies ────────────────────────────────────────────────────
        private void drawDrones(Graphics2D g) {
            int W=getWidth(), H=getHeight();
            drawBgScroll(g, W, H);

            int groundY = 400;
            g.setColor(new Color(50,35,70,220)); g.fillRect(0, groundY, W, H-groundY);

            if (droneAnims==null||droneAnims.length==0) {
                g.setColor(C_WARN); g.setFont(new Font("Consolas",Font.BOLD,18));
                g.drawString("Drone assets not found at: "+DRONE, 30, H/2);
                return;
            }
            Anim a = droneAnims[curDroneAnim];
            if (a!=null && a.ok()) {
                BufferedImage fr = a.frame(droneFrame);
                if (fr!=null) {
                    int sc=4, dw=fr.getWidth()*sc, dh=fr.getHeight()*sc;
                    int floatY = (int)(Math.sin(System.currentTimeMillis()/700.0)*10);
                    g.drawImage(fr, W/2-dw/2, groundY-dh-100+floatY, dw, dh, null);
                }
            }

            // Anim strip at bottom
            int stripH = 110;
            g.setColor(new Color(0,0,0,180)); g.fillRect(0, H-stripH, W, stripH);
            g.setFont(new Font("Consolas",Font.BOLD,12)); g.setColor(C_ACCENT);
            g.drawString("DRONE ANIMATIONS   A / D  to cycle", 12, H-stripH+18);
            int bw=170, bh=84;
            for (int i=0; i<droneAnims.length; i++) {
                int bx = 12+i*(bw+6), by = H-stripH+22;
                boolean sel = i==curDroneAnim;
                g.setColor(sel ? new Color(0,180,140,210) : new Color(42,42,72,210));
                g.fillRoundRect(bx,by,bw,bh,8,8);
                if (sel) { g.setColor(C_ACCENT); g.drawRoundRect(bx,by,bw,bh,8,8); }
                if (droneAnims[i]!=null && droneAnims[i].ok()) {
                    BufferedImage th = droneAnims[i].frame(0);
                    if (th!=null) drawCentered(g, th, bx+4, by+4, 58, 58);
                }
                g.setFont(new Font("Consolas",Font.BOLD,10));
                g.setColor(sel ? Color.BLACK : new Color(180,200,240));
                g.drawString(droneAnims[i]!=null?droneAnims[i].name:"?", bx+66, by+26);
                g.setFont(new Font("Consolas",Font.PLAIN,9));
                g.drawString(droneAnims[i]!=null?droneAnims[i].frames+" frames":"", bx+66, by+40);
            }
        }

        // ── TAB 4: Tiles ──────────────────────────────────────────────────────
        private void drawTiles(Graphics2D g) {
            int W=getWidth(), H=getHeight();
            g.setColor(new Color(22,20,36)); g.fillRect(0,0,W,H);
            if (tileImgs==null||tileImgs.length==0) {
                g.setColor(C_WARN); g.setFont(new Font("Consolas",Font.BOLD,18));
                g.drawString("Tile assets not found at: "+TILES1, 30, H/2); return;
            }
            int CELL=80, COLS=(W-10)/CELL;
            for (int i=0; i<tileImgs.length; i++) {
                int col=i%COLS, row=i/COLS;
                int x=8+col*CELL, y=8+row*CELL-tileScrollY;
                if (y+CELL<0||y>H) continue;
                boolean hov = i==hovTile;
                g.setColor(hov ? new Color(0,180,140,130) : new Color(36,34,56));
                g.fillRoundRect(x+1,y+1,CELL-2,CELL-2,5,5);
                if (hov) { g.setColor(C_ACCENT); g.drawRoundRect(x+1,y+1,CELL-2,CELL-2,5,5); }
                if (tileImgs[i]!=null) drawCentered(g, tileImgs[i], x+2,y+2,CELL-4,CELL-4);
                else { g.setColor(C_WARN); g.setFont(new Font("Consolas",Font.PLAIN,8)); g.drawString("ERR",x+CELL/2-8,y+CELL/2+3); }
                g.setFont(new Font("Consolas",Font.PLAIN,8)); g.setColor(new Color(100,100,140));
                g.drawString(String.valueOf(i+1), x+3, y+CELL-3);
            }
            // Hovered tile info
            if (hovTile>=0 && tileNames!=null && hovTile<tileNames.length) {
                String nm = tileNames[hovTile];
                String[] bits = nm.split("_");
                String display = bits.length>4 ? bits[2]+"_"+bits[3]+"_"+bits[4] : nm;
                g.setColor(new Color(0,0,0,200)); g.fillRoundRect(W-390,H-52,385,46,8,8);
                g.setFont(new Font("Consolas",Font.BOLD,11)); g.setColor(C_ACCENT);
                g.drawString("Tile "+(hovTile+1)+" : ", W-382, H-33);
                g.setFont(new Font("Consolas",Font.PLAIN,10)); g.setColor(C_TEXT);
                g.drawString(display, W-382, H-16);
            }
            g.setFont(new Font("Consolas",Font.PLAIN,10)); g.setColor(new Color(110,110,155));
            g.drawString("Scroll mouse wheel  |  "+tileImgs.length+" tiles total  |  Hover = name", 10, H-6);
        }

        // ── TAB 5: VFX ────────────────────────────────────────────────────────
        private void drawVfx(Graphics2D g) {
            int W=getWidth(), H=getHeight();
            g.setColor(new Color(12,12,22)); g.fillRect(0,0,W,H);

            // Draw spawned particles
            for (Particle p : particles) {
                Anim a = getParticleAnim(p);
                if (a==null||!a.ok()) continue;
                BufferedImage fr = a.frame(p.frame);
                if (fr==null) continue;
                int sc = p.type==0 ? 3 : 2;
                int dw=fr.getWidth()*sc, dh=fr.getHeight()*sc;
                g.drawImage(fr, p.x-dw/2, p.y-dh/2, dw, dh, null);
            }

            // Bottom reference strip
            int sy = H-115;
            g.setColor(new Color(0,0,0,160)); g.fillRect(0,sy,W,115);
            g.setFont(new Font("Consolas",Font.BOLD,13)); g.setColor(C_ACCENT);
            g.drawString("VFX REFERENCE STRIP", 12, sy+20);

            // Smoke frames
            if (smokeAnim!=null && smokeAnim.ok()) {
                g.setFont(new Font("Consolas",Font.PLAIN,10)); g.setColor(new Color(160,180,255));
                g.drawString("SMOKE ("+smokeAnim.frames+"f)", 12, sy+38);
                for (int i=0;i<Math.min(smokeAnim.frames,20);i++) {
                    BufferedImage fr = smokeAnim.frame(i);
                    if (fr!=null) drawCentered(g, fr, 12+i*44, sy+42, 42,42);
                }
            }
            // Sparks
            if (sparkAnims!=null&&sparkAnims.length>0) {
                g.setFont(new Font("Consolas",Font.PLAIN,10)); g.setColor(new Color(255,220,80));
                g.drawString("SPARKS ("+sparkAnims.length+" variants × 4f)", 12, sy+92);
                for (int i=0;i<Math.min(sparkAnims.length,8);i++) {
                    if (sparkAnims[i]!=null&&sparkAnims[i].ok()) {
                        BufferedImage fr = sparkAnims[i].frame(0);
                        if (fr!=null) drawCentered(g, fr, 12+i*44, sy+96, 42,42);
                    }
                }
            }
            // Controls hint
            g.setFont(new Font("Consolas",Font.BOLD,14)); g.setColor(C_ACCENT);
            g.drawString("Click anywhere above to spawn    [1] Smoke   [2] Sparks", 12, sy-14);
            g.setColor(vfxType==0 ? Color.WHITE : new Color(100,100,140));
            g.drawString("► SMOKE active", W-200, sy-32);
            g.setColor(vfxType==1 ? new Color(255,220,80) : new Color(100,100,140));
            g.drawString("► SPARKS active", W-200, sy-16);
            // Active count
            g.setColor(C_TEXT); g.setFont(new Font("Consolas",Font.PLAIN,11));
            g.drawString("Active particles: "+particles.size(), W-200, sy-2);
        }

        // ── Helpers ───────────────────────────────────────────────────────────
        private void drawBgScroll(Graphics2D g, int W, int H) {
            for (int i=0; i<bgLayers.length; i++) {
                if (bgLayers[i]==null) continue;
                float scroll = camX * bgFactors[i];
                int iW=bgLayers[i].getWidth(), iH=bgLayers[i].getHeight();
                double sh=(double)H/iH;
                int dW=(int)(iW*sh);
                int x0=-(int)(scroll % dW);
                for (int x=x0-dW; x<W; x+=dW)
                    g.drawImage(bgLayers[i], x, 0, dW, H, null);
            }
        }

        private void drawCentered(Graphics2D g, BufferedImage img, int rx, int ry, int rw, int rh) {
            if (img==null) return;
            double sc = Math.min((double)rw/img.getWidth(), (double)rh/img.getHeight());
            int dw=(int)(img.getWidth()*sc), dh=(int)(img.getHeight()*sc);
            g.drawImage(img, rx+(rw-dw)/2, ry+(rh-dh)/2, dw, dh, null);
        }

        /** Draw a semi-transparent rounded panel, then execute inner drawing code */
        private void hud(Graphics2D g, int x, int y, int w, int h, Runnable draw) {
            g.setColor(new Color(0,0,0,165)); g.fillRoundRect(x,y,w,h,10,10);
            draw.run();
        }

        // ── Mouse ─────────────────────────────────────────────────────────────
        @Override public void mouseClicked(MouseEvent e) {
            if (activeTab==2) { // fire bullet
                int ox=getWidth()/2+10, oy=400-90;
                double ang = Math.atan2(e.getY()-oy, e.getX()-ox);
                float spd=14;
                bullets.add(new float[]{ox,oy,(float)Math.cos(ang)*spd,(float)Math.sin(ang)*spd,1500});
            }
            if (activeTab==5 && e.getY() < getHeight()-115) {
                int idx = vfxType==1 && sparkAnims!=null && sparkAnims.length>0
                        ? (int)(Math.random()*sparkAnims.length) : 0;
                particles.add(new Particle(e.getX(), e.getY(), vfxType, idx));
            }
            if (activeTab==6 && midiFiles!=null) { // click a MIDI track to toggle
                for (int i=0; i<midiFiles.length; i++) {
                    int ty = 110 + i*46;
                    if (e.getX()>=24 && e.getX()<=504 && e.getY()>=ty && e.getY()<=ty+38) {
                        if (curMidi==i && midiPlaying) stopMidi();
                        else { playMidi(i); }
                        break;
                    }
                }
            }
        }
        @Override public void mousePressed(MouseEvent e) {
            if (activeTab==0) { dragStartX=e.getX(); dragCamStart=camX; autoScroll=false; }
        }
        @Override public void mouseReleased(MouseEvent e) { dragStartX=-1; }
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseExited(MouseEvent e)  {}
        @Override public void mouseDragged(MouseEvent e) {
            mx=e.getX(); my=e.getY();
            if (activeTab==0 && dragStartX>=0) {
                camX = Math.max(0, dragCamStart - (e.getX()-dragStartX)*4);
            }
        }
        @Override public void mouseMoved(MouseEvent e) {
            mx=e.getX(); my=e.getY();
            if (activeTab==4) {
                int CELL=80, COLS=(getWidth()-10)/CELL;
                int col=(e.getX()-8)/CELL, row=(e.getY()-8+tileScrollY)/CELL;
                int idx=row*COLS+col;
                hovTile = (col>=0&&col<COLS&&idx>=0&&tileImgs!=null&&idx<tileImgs.length)?idx:-1;
            }
        }
        @Override public void mouseWheelMoved(MouseWheelEvent e) {
            if (activeTab==4) tileScrollY = Math.max(0, tileScrollY + e.getWheelRotation()*20);
            if (activeTab==0) scrollSpeed = Math.max(0, Math.min(15, scrollSpeed - e.getWheelRotation()));
        }

        // ── MIDI / Sound ──────────────────────────────────────────────────────
        private void loadMidi() {
            File dir = new File(MIDI_DIR);
            if (!dir.isDirectory()) { midiFiles = new File[0]; return; }
            midiFiles = dir.listFiles(f ->
                f.isFile()
                && f.getName().toLowerCase().endsWith(".mid")
                && !f.getName().startsWith("._"));
            if (midiFiles == null) midiFiles = new File[0];
            Arrays.sort(midiFiles);
            System.out.println("[MIDI] Found " + midiFiles.length + " tracks in " + MIDI_DIR);
        }

        private void playMidi(int idx) {
            stopMidi();
            if (midiFiles==null || idx<0 || idx>=midiFiles.length) return;
            try {
                midiSeq = MidiSystem.getSequencer();
                midiSeq.open();
                midiSeq.setSequence(MidiSystem.getSequence(midiFiles[idx]));
                midiSeq.start();
                curMidi    = idx;
                midiPlaying = true;
                System.out.println("[MIDI] Playing: " + midiFiles[idx].getName());
            } catch (Exception e) {
                System.err.println("[MIDI] Error playing " + midiFiles[idx].getName() + ": " + e.getMessage());
                midiPlaying = false;
            }
        }

        private void stopMidi() {
            if (midiSeq != null) {
                try { if (midiSeq.isOpen()) { midiSeq.stop(); midiSeq.close(); } }
                catch (Exception ignored) {}
                midiSeq = null;
            }
            midiPlaying = false;
        }

        private void tickSound() {
            // Poll sequencer to detect when track ends naturally
            if (midiPlaying && (midiSeq==null || !midiSeq.isRunning())) {
                midiPlaying = false;
            }
        }

        // ── TAB 6: Sound ─────────────────────────────────────────────────────
        private void drawSound(Graphics2D g) {
            int W=getWidth(), H=getHeight();
            g.setColor(new Color(12,12,30)); g.fillRect(0,0,W,H);

            // Title
            g.setFont(new Font("Consolas",Font.BOLD,22)); g.setColor(C_ACCENT);
            g.drawString("\ud83d\udd0a  SOUND & MUSIC", 24, 50);

            // MIDI track list
            g.setFont(new Font("Consolas",Font.BOLD,14)); g.setColor(C_TEXT);
            g.drawString("MIDI Tracks  (javax.sound.midi — plays on Windows)", 24, 85);

            if (midiFiles==null || midiFiles.length==0) {
                g.setColor(C_WARN);
                g.drawString("No MIDI files found at: " + MIDI_DIR, 24, 115);
            } else {
                for (int i=0; i<midiFiles.length; i++) {
                    int ty = 110 + i*46;
                    boolean sel = i==curMidi;
                    boolean playing = sel && midiPlaying;
                    g.setColor(playing ? new Color(0,180,140,200) : (sel ? new Color(0,120,100,160) : new Color(36,34,60)));
                    g.fillRoundRect(24, ty, 480, 38, 8, 8);
                    if (sel) { g.setColor(C_ACCENT); g.drawRoundRect(24,ty,480,38,8,8); }
                    g.setFont(new Font("Consolas",Font.BOLD,14));
                    g.setColor(playing ? Color.WHITE : (sel ? C_ACCENT : C_TEXT));
                    g.drawString((playing ? "\u25b6  " : "   ") + midiFiles[i].getName(), 36, ty+25);
                }
                g.setFont(new Font("Consolas",Font.PLAIN,11)); g.setColor(new Color(130,140,170));
                g.drawString("Click a track to play. Click again to stop.", 24, 110+midiFiles.length*46+20);
            }

            // Warning about WAV files
            int wy = H-70;
            g.setColor(new Color(0,0,0,150)); g.fillRoundRect(20,wy-18,W-40,65,10,10);
            g.setFont(new Font("Consolas",Font.BOLD,12)); g.setColor(C_WARN);
            g.drawString("\u26a0  WAV / SFX files in these assets are macOS resource forks (._* prefix)", 30, wy);
            g.setFont(new Font("Consolas",Font.PLAIN,11)); g.setColor(new Color(200,180,130));
            g.drawString("   They start with '._' and are NOT valid audio on Windows.", 30, wy+18);
            g.drawString("   MIDI is the only working audio format in this asset pack.", 30, wy+34);
        }
    } // end GameCanvas
} // end MasterGameTestSuite