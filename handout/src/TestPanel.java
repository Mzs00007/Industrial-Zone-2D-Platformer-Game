/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

class TestPanel
extends JPanel
implements KeyListener {
    private float walkSpeed = 4.0f;
    private float runSpeed = 8.0f;
    private float walkAcceleration = 0.3f;
    private float runAcceleration = 0.25f;
    private float gravity = 0.5f;
    private float maxFallSpeed = 12.0f;
    private float maxJumpVelocity = 12.0f;
    private float doubleJumpVelocity = 10.0f;
    private float coyoteTime = 6.0f;
    private float jumpGracePeriod = 4.0f;
    private float groundFriction = 0.15f;
    private float airFriction = 0.05f;
    private float wallSlideFriction = 0.3f;
    private float airControl = 0.6f;
    private float groundDetectionRange = 300.0f;
    private float groundChaseSpeed = 2.5f;
    private int groundAttackCooldown = 60;
    private float groundAttackRange = 60.0f;
    private float groundPatrolDistance = 200.0f;
    private float groundAcceleration = 0.4f;
    private float airDetectionRange = 400.0f;
    private float airChaseSpeed = 3.5f;
    private int airAttackCooldown = 45;
    private float airAttackRange = 80.0f;
    private float airPatrolAltitude = 150.0f;
    private float airVerticalSpeed = 2.0f;
    private float bossDetectionRange = 500.0f;
    private float bossChaseSpeed = 3.0f;
    private int bossAttackCooldown = 40;
    private float bossAttackRange = 100.0f;
    private float bossPatrolDistance = 250.0f;
    private float bossAcceleration = 0.5f;
    private String selectedCategory = "Physics";
    private int selectedParameter = 0;
    private BufferedImage buffer;

    public TestPanel() {
        this.setFocusable(true);
        this.addKeyListener(this);
        this.buffer = new BufferedImage(1400, 900, 1);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D)this.buffer.getGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(new Color(20, 20, 30));
        graphics2D.fillRect(0, 0, 1400, 900);
        this.drawPhysicsVisualization(graphics2D);
        this.drawEnemyAI(graphics2D);
        this.drawControls(graphics2D);
        graphics2D.dispose();
        graphics.drawImage(this.buffer, 0, 0, null);
    }

    private void drawPhysicsVisualization(Graphics2D graphics2D) {
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(new Font("Monospaced", 1, 16));
        int n = 20;
        int n2 = 30;
        graphics2D.drawString("=== PHYSICS CONSTANTS (16 total) ===", n, n2);
        graphics2D.setFont(new Font("Monospaced", 0, 14));
        graphics2D.setColor(new Color(100, 150, 200));
        graphics2D.drawString("Movement Control:", n, n2 += 30);
        this.drawParameter(graphics2D, n + 20, n2 += 25, "Walk Speed", this.walkSpeed, 0, this.selectedParameter == 0);
        this.drawParameter(graphics2D, n + 20, n2 += 22, "Run Speed", this.runSpeed, 1, this.selectedParameter == 1);
        this.drawParameter(graphics2D, n + 20, n2 += 22, "Walk Acceleration", this.walkAcceleration, 2, this.selectedParameter == 2);
        this.drawParameter(graphics2D, n + 20, n2 += 22, "Run Acceleration", this.runAcceleration, 3, this.selectedParameter == 3);
        graphics2D.setColor(new Color(200, 150, 100));
        graphics2D.drawString("Gravity System:", n, n2 += 30);
        this.drawParameter(graphics2D, n + 20, n2 += 25, "Gravity", this.gravity, 4, this.selectedParameter == 4);
        this.drawParameter(graphics2D, n + 20, n2 += 22, "Max Fall Speed", this.maxFallSpeed, 5, this.selectedParameter == 5);
        graphics2D.setColor(new Color(150, 200, 150));
        graphics2D.drawString("Jump Mechanics:", n, n2 += 30);
        this.drawParameter(graphics2D, n + 20, n2 += 25, "Max Jump Velocity", this.maxJumpVelocity, 6, this.selectedParameter == 6);
        this.drawParameter(graphics2D, n + 20, n2 += 22, "Double Jump Velocity", this.doubleJumpVelocity, 7, this.selectedParameter == 7);
        this.drawParameter(graphics2D, n + 20, n2 += 22, "Coyote Time (frames)", this.coyoteTime, 8, this.selectedParameter == 8);
        this.drawParameter(graphics2D, n + 20, n2 += 22, "Jump Grace Period (frames)", this.jumpGracePeriod, 9, this.selectedParameter == 9);
        graphics2D.setColor(new Color(200, 100, 150));
        graphics2D.drawString("Friction & Control:", n, n2 += 30);
        this.drawParameter(graphics2D, n + 20, n2 += 25, "Ground Friction", this.groundFriction, 10, this.selectedParameter == 10);
        this.drawParameter(graphics2D, n + 20, n2 += 22, "Air Friction", this.airFriction, 11, this.selectedParameter == 11);
        this.drawParameter(graphics2D, n + 20, n2 += 22, "Wall Slide Friction", this.wallSlideFriction, 12, this.selectedParameter == 12);
        this.drawParameter(graphics2D, n + 20, n2 += 22, "Air Control", this.airControl, 13, this.selectedParameter == 13);
    }

    private void drawEnemyAI(Graphics2D graphics2D) {
        int n = 750;
        int n2 = 30;
        graphics2D.setColor(new Color(150, 100, 100));
        graphics2D.setFont(new Font("Monospaced", 1, 16));
        graphics2D.drawString("=== GROUND ENEMY AI ===", n, n2);
        graphics2D.setFont(new Font("Monospaced", 0, 14));
        this.drawParameter(graphics2D, n, n2 += 30, "Detection Range", this.groundDetectionRange, 14, this.selectedParameter == 14);
        this.drawParameter(graphics2D, n, n2 += 22, "Chase Speed", this.groundChaseSpeed, 15, this.selectedParameter == 15);
        this.drawParameter(graphics2D, n, n2 += 22, "Attack Cooldown", this.groundAttackCooldown, 16, this.selectedParameter == 16);
        this.drawParameter(graphics2D, n, n2 += 22, "Attack Range", this.groundAttackRange, 17, this.selectedParameter == 17);
        this.drawParameter(graphics2D, n, n2 += 22, "Patrol Distance", this.groundPatrolDistance, 18, this.selectedParameter == 18);
        this.drawParameter(graphics2D, n, n2 += 22, "Acceleration", this.groundAcceleration, 19, this.selectedParameter == 19);
        graphics2D.setColor(new Color(100, 150, 150));
        graphics2D.drawString("=== AIR ENEMY AI ===", n, n2 += 50);
        this.drawParameter(graphics2D, n, n2 += 30, "Detection Range", this.airDetectionRange, 20, this.selectedParameter == 20);
        this.drawParameter(graphics2D, n, n2 += 22, "Chase Speed", this.airChaseSpeed, 21, this.selectedParameter == 21);
        this.drawParameter(graphics2D, n, n2 += 22, "Attack Cooldown", this.airAttackCooldown, 22, this.selectedParameter == 22);
        this.drawParameter(graphics2D, n, n2 += 22, "Attack Range", this.airAttackRange, 23, this.selectedParameter == 23);
        this.drawParameter(graphics2D, n, n2 += 22, "Patrol Altitude", this.airPatrolAltitude, 24, this.selectedParameter == 24);
        this.drawParameter(graphics2D, n, n2 += 22, "Vertical Speed", this.airVerticalSpeed, 25, this.selectedParameter == 25);
        graphics2D.setColor(new Color(150, 100, 200));
        graphics2D.drawString("=== BOSS AI ===", n, n2 += 50);
        this.drawParameter(graphics2D, n, n2 += 30, "Detection Range", this.bossDetectionRange, 26, this.selectedParameter == 26);
        this.drawParameter(graphics2D, n, n2 += 22, "Chase Speed", this.bossChaseSpeed, 27, this.selectedParameter == 27);
        this.drawParameter(graphics2D, n, n2 += 22, "Attack Cooldown", this.bossAttackCooldown, 28, this.selectedParameter == 28);
        this.drawParameter(graphics2D, n, n2 += 22, "Attack Range", this.bossAttackRange, 29, this.selectedParameter == 29);
        this.drawParameter(graphics2D, n, n2 += 22, "Patrol Distance", this.bossPatrolDistance, 30, this.selectedParameter == 30);
        this.drawParameter(graphics2D, n, n2 += 22, "Acceleration", this.bossAcceleration, 31, this.selectedParameter == 31);
    }

    private void drawParameter(Graphics2D graphics2D, int n, int n2, String string, float f, int n3, boolean bl) {
        if (bl) {
            graphics2D.setColor(new Color(255, 255, 0));
            graphics2D.fillRect(n - 10, n2 - 15, 400, 20);
            graphics2D.setColor(Color.BLACK);
        } else {
            graphics2D.setColor(Color.WHITE);
        }
        String string2 = String.format("%-25s: % 8.2f", string, Float.valueOf(f));
        graphics2D.drawString(string2, n, n2);
    }

    private void drawParameter(Graphics2D graphics2D, int n, int n2, String string, int n3, int n4, boolean bl) {
        if (bl) {
            graphics2D.setColor(new Color(255, 255, 0));
            graphics2D.fillRect(n - 10, n2 - 15, 400, 20);
            graphics2D.setColor(Color.BLACK);
        } else {
            graphics2D.setColor(Color.WHITE);
        }
        String string2 = String.format("%-25s: % 8d", string, n3);
        graphics2D.drawString(string2, n, n2);
    }

    private void drawControls(Graphics2D graphics2D) {
        int n = 20;
        int n2 = 750;
        graphics2D.setColor(new Color(150, 150, 150));
        graphics2D.setFont(new Font("Monospaced", 0, 12));
        graphics2D.drawString("SHIFT + UP/DOWN Arrow    : Navigate Physics (16 params)", n, n2);
        graphics2D.drawString("SHIFT + LEFT/RIGHT Arrow : Adjust Physics value", n, n2 + 18);
        graphics2D.drawString("CTRL + UP/DOWN Arrow     : Navigate Ground Enemy (6 params)", n, n2 + 40);
        graphics2D.drawString("CTRL + LEFT/RIGHT Arrow  : Adjust Ground Enemy value", n, n2 + 58);
        graphics2D.drawString("CTRL+ALT + UP/DOWN Arrow : Navigate Air Enemy (6 params)", n, n2 + 80);
        graphics2D.drawString("CTRL+ALT + LEFT/RIGHT    : Adjust Air Enemy value", n, n2 + 98);
        graphics2D.drawString("ALT + UP/DOWN Arrow      : Navigate Boss AI (6 params)", n, n2 + 120);
        graphics2D.drawString("ALT + LEFT/RIGHT Arrow   : Adjust Boss value", n, n2 + 138);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        boolean bl = keyEvent.isShiftDown();
        boolean bl2 = keyEvent.isControlDown();
        boolean bl3 = keyEvent.isAltDown();
        if (bl && !bl2 && !bl3) {
            this.handlePhysicsInput(keyEvent.getKeyCode());
        } else if (bl2 && !bl3) {
            this.handleGroundEnemyInput(keyEvent.getKeyCode());
        } else if (bl2 && bl3) {
            this.handleAirEnemyInput(keyEvent.getKeyCode());
        } else if (bl3 && !bl2) {
            this.handleBossInput(keyEvent.getKeyCode());
        }
        this.repaint();
    }

    private void handlePhysicsInput(int n) {
        float f = 0.1f;
        if (n == 38) {
            this.selectedParameter = Math.max(0, this.selectedParameter - 1);
        } else if (n == 40) {
            this.selectedParameter = Math.min(13, this.selectedParameter + 1);
        } else if (n == 37) {
            f = -f;
        } else if (n != 39) {
            return;
        }
        if (n == 37 || n == 39) {
            this.updatePhysicsParameter(this.selectedParameter, f);
        }
    }

    private void updatePhysicsParameter(int n, float f) {
        switch (n) {
            case 0: {
                this.walkSpeed = Math.max(0.1f, this.walkSpeed + f);
                break;
            }
            case 1: {
                this.runSpeed = Math.max(0.1f, this.runSpeed + f);
                break;
            }
            case 2: {
                this.walkAcceleration = Math.max(0.01f, this.walkAcceleration + f);
                break;
            }
            case 3: {
                this.runAcceleration = Math.max(0.01f, this.runAcceleration + f);
                break;
            }
            case 4: {
                this.gravity = Math.max(0.01f, this.gravity + f);
                break;
            }
            case 5: {
                this.maxFallSpeed = Math.max(0.1f, this.maxFallSpeed + f);
                break;
            }
            case 6: {
                this.maxJumpVelocity = Math.max(0.1f, this.maxJumpVelocity + f);
                break;
            }
            case 7: {
                this.doubleJumpVelocity = Math.max(0.1f, this.doubleJumpVelocity + f);
                break;
            }
            case 8: {
                this.coyoteTime = Math.max(0.0f, this.coyoteTime + f);
                break;
            }
            case 9: {
                this.jumpGracePeriod = Math.max(0.0f, this.jumpGracePeriod + f);
                break;
            }
            case 10: {
                this.groundFriction = Math.max(0.0f, Math.min(1.0f, this.groundFriction + f));
                break;
            }
            case 11: {
                this.airFriction = Math.max(0.0f, Math.min(1.0f, this.airFriction + f));
                break;
            }
            case 12: {
                this.wallSlideFriction = Math.max(0.0f, Math.min(1.0f, this.wallSlideFriction + f));
                break;
            }
            case 13: {
                this.airControl = Math.max(0.0f, Math.min(1.0f, this.airControl + f));
            }
        }
    }

    private void handleGroundEnemyInput(int n) {
        float f = 5.0f;
        if (n == 38) {
            this.selectedParameter = Math.max(14, this.selectedParameter - 1);
        } else if (n == 40) {
            this.selectedParameter = Math.min(19, this.selectedParameter + 1);
        } else if (n == 37) {
            f = -f;
        } else if (n != 39) {
            return;
        }
        if (n == 37 || n == 39) {
            this.updateGroundEnemyParameter(this.selectedParameter - 14, f);
        }
    }

    private void updateGroundEnemyParameter(int n, float f) {
        switch (n) {
            case 0: {
                this.groundDetectionRange = Math.max(10.0f, this.groundDetectionRange + f);
                break;
            }
            case 1: {
                this.groundChaseSpeed = Math.max(0.1f, this.groundChaseSpeed + f / 10.0f);
                break;
            }
            case 2: {
                this.groundAttackCooldown = Math.max(1, (int)((float)this.groundAttackCooldown + f / 5.0f));
                break;
            }
            case 3: {
                this.groundAttackRange = Math.max(10.0f, this.groundAttackRange + f);
                break;
            }
            case 4: {
                this.groundPatrolDistance = Math.max(10.0f, this.groundPatrolDistance + f);
                break;
            }
            case 5: {
                this.groundAcceleration = Math.max(0.1f, this.groundAcceleration + f / 10.0f);
            }
        }
    }

    private void handleAirEnemyInput(int n) {
        float f = 5.0f;
        if (n == 38) {
            this.selectedParameter = Math.max(20, this.selectedParameter - 1);
        } else if (n == 40) {
            this.selectedParameter = Math.min(25, this.selectedParameter + 1);
        } else if (n == 37) {
            f = -f;
        } else if (n != 39) {
            return;
        }
        if (n == 37 || n == 39) {
            this.updateAirEnemyParameter(this.selectedParameter - 20, f);
        }
    }

    private void updateAirEnemyParameter(int n, float f) {
        switch (n) {
            case 0: {
                this.airDetectionRange = Math.max(10.0f, this.airDetectionRange + f);
                break;
            }
            case 1: {
                this.airChaseSpeed = Math.max(0.1f, this.airChaseSpeed + f / 10.0f);
                break;
            }
            case 2: {
                this.airAttackCooldown = Math.max(1, (int)((float)this.airAttackCooldown + f / 5.0f));
                break;
            }
            case 3: {
                this.airAttackRange = Math.max(10.0f, this.airAttackRange + f);
                break;
            }
            case 4: {
                this.airPatrolAltitude = Math.max(10.0f, this.airPatrolAltitude + f);
                break;
            }
            case 5: {
                this.airVerticalSpeed = Math.max(0.1f, this.airVerticalSpeed + f / 10.0f);
            }
        }
    }

    private void handleBossInput(int n) {
        float f = 5.0f;
        if (n == 38) {
            this.selectedParameter = Math.max(26, this.selectedParameter - 1);
        } else if (n == 40) {
            this.selectedParameter = Math.min(31, this.selectedParameter + 1);
        } else if (n == 37) {
            f = -f;
        } else if (n != 39) {
            return;
        }
        if (n == 37 || n == 39) {
            this.updateBossParameter(this.selectedParameter - 26, f);
        }
    }

    private void updateBossParameter(int n, float f) {
        switch (n) {
            case 0: {
                this.bossDetectionRange = Math.max(10.0f, this.bossDetectionRange + f);
                break;
            }
            case 1: {
                this.bossChaseSpeed = Math.max(0.1f, this.bossChaseSpeed + f / 10.0f);
                break;
            }
            case 2: {
                this.bossAttackCooldown = Math.max(1, (int)((float)this.bossAttackCooldown + f / 5.0f));
                break;
            }
            case 3: {
                this.bossAttackRange = Math.max(10.0f, this.bossAttackRange + f);
                break;
            }
            case 4: {
                this.bossPatrolDistance = Math.max(10.0f, this.bossPatrolDistance + f);
                break;
            }
            case 5: {
                this.bossAcceleration = Math.max(0.1f, this.bossAcceleration + f / 10.0f);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }
}
