/*
 * Decompiled with CFR 0.152.
 */
import animation.AnimationAndSpriteLoader;
import animation.PlayerCharacterAnimations;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import physics.CharacterPhysicsProfile;

private static class LiveCharacterPhysicsTester.CharacterInstance {
    public float x;
    public float y;
    public float velocityX = 0.0f;
    public float velocityY = 0.0f;
    public String currentAnimation = "Idle_Default";
    public CharacterPhysicsProfile profile;
    public CharacterPhysicsProfile.CharacterType type;
    public AnimationAndSpriteLoader spriteLoader;
    public BufferedImage currentFrame;
    public long animationStartTime;
    public int currentFrameIndex = 0;
    public static final float CHAR_WIDTH = 28.0f;
    public static final float CHAR_HEIGHT = 44.0f;
    public boolean isGrounded = false;
    public boolean canJump = true;

    public LiveCharacterPhysicsTester.CharacterInstance(CharacterPhysicsProfile.CharacterType characterType, float f, float f2, AnimationAndSpriteLoader animationAndSpriteLoader) {
        this.type = characterType;
        this.x = f;
        this.y = f2;
        this.spriteLoader = animationAndSpriteLoader;
        this.profile = CharacterPhysicsProfile.createProfile(characterType);
        this.animationStartTime = System.currentTimeMillis();
        this.updateFrame();
    }

    void setAnimationState(String string) {
        if (!this.currentAnimation.equals(string)) {
            this.currentAnimation = string;
            this.animationStartTime = System.currentTimeMillis();
            this.currentFrameIndex = 0;
        }
    }

    void updateAnimation() {
        if (this.spriteLoader == null) {
            return;
        }
        try {
            LinkedHashMap<String, PlayerCharacterAnimations.AnimationConfig> linkedHashMap = null;
            switch (this.type) {
                case BIKER: {
                    linkedHashMap = PlayerCharacterAnimations.BikerAnimations.animations;
                    break;
                }
                case CYBORG: {
                    linkedHashMap = PlayerCharacterAnimations.CyborgAnimations.animations;
                    break;
                }
                case PUNK: {
                    linkedHashMap = PlayerCharacterAnimations.PunkAnimations.animations;
                }
            }
            if (linkedHashMap == null) {
                return;
            }
            PlayerCharacterAnimations.AnimationConfig animationConfig = linkedHashMap.getOrDefault(this.currentAnimation, linkedHashMap.get("Idle_Default"));
            long l = System.currentTimeMillis() - this.animationStartTime;
            int n = animationConfig.timingMs;
            this.currentFrameIndex = (int)(l / (long)n % (long)animationConfig.frameCount);
            this.updateFrame();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    void updatePhysics() {
        this.isGrounded = false;
        this.velocityY += this.profile.gravity;
        if (this.velocityY > this.profile.maxFallSpeed) {
            this.velocityY = this.profile.maxFallSpeed;
        }
        float f = this.x + this.velocityX;
        float f2 = this.y + this.velocityY;
        for (int[] nArray : LiveCharacterPhysicsTester.TestCharacterPanel.PLATFORM_BLOCKS) {
            if (!this.checkHorizontalCollision(f, this.y, nArray)) continue;
            if (this.velocityX > 0.0f) {
                f = (float)nArray[0] - 14.0f;
            } else if (this.velocityX < 0.0f) {
                f = (float)(nArray[0] + nArray[2]) + 14.0f;
            }
            this.velocityX *= 0.2f;
        }
        for (int[] nArray : LiveCharacterPhysicsTester.TestCharacterPanel.PLATFORM_BLOCKS) {
            if (!this.checkVerticalCollision(f, f2, nArray)) continue;
            float f3 = f2 + 22.0f;
            float f4 = f2 - 22.0f;
            float f5 = nArray[1];
            float f6 = nArray[1] + nArray[3];
            if (this.velocityY > 0.0f && f3 >= f5 && f3 <= f5 + 15.0f) {
                f2 = f5 - 22.0f;
                this.velocityY = 0.0f;
                this.isGrounded = true;
                this.canJump = true;
                this.velocityX *= this.profile.friction;
                continue;
            }
            if (!(this.velocityY < 0.0f) || !(f4 <= f6) || !(f4 >= f6 - 15.0f)) continue;
            f2 = f6 + 22.0f;
            this.velocityY = 0.0f;
            this.velocityY = this.profile.gravity * 0.5f;
        }
        if (!this.isGrounded) {
            this.velocityX *= this.profile.airFriction;
        }
        this.x = f;
        this.y = f2;
        if (this.x < 14.0f) {
            this.x = 14.0f;
            this.velocityX = 0.0f;
        }
        if (this.x > 1086.0f) {
            this.x = 1086.0f;
            this.velocityX = 0.0f;
        }
        if (this.y > 700.0f) {
            this.y = 150.0f;
            this.velocityY = 0.0f;
            this.velocityX = 0.0f;
            this.isGrounded = false;
        }
    }

    private boolean checkHorizontalCollision(float f, float f2, int[] nArray) {
        return f + 14.0f > (float)nArray[0] && f - 14.0f < (float)(nArray[0] + nArray[2]) && f2 + 22.0f > (float)nArray[1] && f2 - 22.0f < (float)(nArray[1] + nArray[3]);
    }

    private boolean checkVerticalCollision(float f, float f2, int[] nArray) {
        return f + 14.0f > (float)nArray[0] && f - 14.0f < (float)(nArray[0] + nArray[2]) && f2 + 22.0f > (float)nArray[1] && f2 - 22.0f < (float)(nArray[1] + nArray[3]);
    }

    void updateFrame() {
    }

    void setPhysicsProfile(CharacterPhysicsProfile characterPhysicsProfile) {
        this.profile = characterPhysicsProfile;
    }

    void draw(Graphics2D graphics2D) {
        float f = this.x - 14.0f;
        float f2 = this.y - 22.0f;
        if (this.isGrounded) {
            graphics2D.setColor(new Color(100, 200, 100, 200));
        } else {
            graphics2D.setColor(new Color(100, 150, 255, 200));
        }
        graphics2D.fillRect((int)f, (int)f2, 28, 44);
        graphics2D.setColor(this.isGrounded ? new Color(50, 150, 50) : new Color(50, 100, 200));
        graphics2D.setStroke(new BasicStroke(2.0f));
        graphics2D.drawRect((int)f, (int)f2, 28, 44);
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(new Font("Arial", 1, 10));
        String string = this.type.displayName.substring(0, 1);
        graphics2D.drawString(string, (int)this.x - 5, (int)this.y + 5);
        if (Math.abs(this.velocityX) > 0.1f || Math.abs(this.velocityY) > 0.1f) {
            graphics2D.setColor(new Color(255, 200, 0));
            graphics2D.setStroke(new BasicStroke(2.0f));
            int n = (int)(this.velocityX * 3.0f);
            int n2 = (int)(this.velocityY * 3.0f);
            graphics2D.drawLine((int)this.x, (int)this.y, (int)this.x + n, (int)this.y + n2);
            double d = Math.atan2(n2, n);
            int n3 = 8;
            int n4 = (int)this.x + n;
            int n5 = (int)this.y + n2;
            int n6 = (int)((double)n4 - (double)n3 * Math.cos(d - 0.5235987755982988));
            int n7 = (int)((double)n5 - (double)n3 * Math.sin(d - 0.5235987755982988));
            int n8 = (int)((double)n4 - (double)n3 * Math.cos(d + 0.5235987755982988));
            int n9 = (int)((double)n5 - (double)n3 * Math.sin(d + 0.5235987755982988));
            graphics2D.drawLine(n4, n5, n6, n7);
            graphics2D.drawLine(n4, n5, n8, n9);
        }
        graphics2D.setColor(new Color(150, 200, 150));
        graphics2D.setFont(new Font("Monospaced", 0, 8));
        String string2 = this.currentAnimation.length() > 15 ? this.currentAnimation.substring(0, 12) + "..." : this.currentAnimation;
        graphics2D.drawString(string2, (int)f, (int)(f2 + 44.0f + 12.0f));
        graphics2D.setColor(new Color(200, 200, 100));
        graphics2D.setFont(new Font("Monospaced", 0, 7));
        graphics2D.drawString(String.format("vX:%.1f vY:%.1f", Float.valueOf(this.velocityX), Float.valueOf(this.velocityY)), (int)f, (int)(f2 + 44.0f + 22.0f));
        graphics2D.drawString(this.isGrounded ? "GROUNDED" : "FALLING", (int)f, (int)(f2 + 44.0f + 30.0f));
    }
}
