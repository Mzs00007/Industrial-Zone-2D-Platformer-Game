/*
 * Decompiled with CFR 0.152.
 */
package physics;

public class CharacterPhysicsProfile {
    public float walkSpeed = 3.0f;
    public float runSpeed = 6.0f;
    public float dashSpeed = 12.0f;
    public float jumpPower = 15.0f;
    public float gravity = 0.5f;
    public float maxFallSpeed = 20.0f;
    public float maxVelocity = 10.0f;
    public float acceleration = 0.3f;
    public float friction = 0.85f;
    public float airFriction = 0.95f;
    public float width = 28.0f;
    public float height = 44.0f;
    public float collisionWidth = 28.0f;
    public float collisionHeight = 44.0f;
    public float mass = 1.0f;
    public int maxHealth = 100;
    public int armor = 0;
    public int maxAmmo = 30;
    public float attackRange = 100.0f;
    public float attackDamage = 10.0f;
    public float climbSpeed = 2.0f;
    public float swimSpeed = 2.5f;
    public float wallSlideSpeed = 1.0f;
    public long attackCooldown = 500L;
    public long dashCooldown = 300L;

    public float getJumpPower() {
        return this.jumpPower;
    }

    public float getAcceleration() {
        return this.acceleration;
    }

    public float getMaxVelocity() {
        return this.runSpeed;
    }

    public float getFriction() {
        return this.friction;
    }

    public float getGravityScale() {
        return this.gravity;
    }

    public static CharacterPhysicsProfile createProfile(CharacterType characterType) {
        CharacterPhysicsProfile characterPhysicsProfile = new CharacterPhysicsProfile();
        switch (characterType.ordinal()) {
            case 0: {
                characterPhysicsProfile.walkSpeed = 2.5f;
                characterPhysicsProfile.runSpeed = 5.0f;
                characterPhysicsProfile.jumpPower = 12.0f;
                characterPhysicsProfile.gravity = 0.5f;
                characterPhysicsProfile.friction = 0.9f;
                characterPhysicsProfile.mass = 1.1f;
                break;
            }
            case 2: {
                characterPhysicsProfile.walkSpeed = 3.5f;
                characterPhysicsProfile.runSpeed = 7.0f;
                characterPhysicsProfile.jumpPower = 18.0f;
                characterPhysicsProfile.gravity = 0.48f;
                characterPhysicsProfile.friction = 0.8f;
                characterPhysicsProfile.mass = 0.9f;
                break;
            }
            case 1: {
                characterPhysicsProfile.walkSpeed = 3.0f;
                characterPhysicsProfile.runSpeed = 6.0f;
                characterPhysicsProfile.jumpPower = 15.0f;
                characterPhysicsProfile.gravity = 0.52f;
                characterPhysicsProfile.friction = 0.85f;
                characterPhysicsProfile.mass = 1.2f;
            }
        }
        return characterPhysicsProfile;
    }

    public static CharacterPhysicsProfile createCyborgProfile() {
        return CharacterPhysicsProfile.createProfile(CharacterType.CYBORG);
    }

    public static CharacterPhysicsProfile createPunkProfile() {
        return CharacterPhysicsProfile.createProfile(CharacterType.PUNK);
    }

    public static CharacterPhysicsProfile createBikerProfile() {
        return CharacterPhysicsProfile.createProfile(CharacterType.BIKER);
    }

    public static enum CharacterType {
        BIKER("Biker"),
        CYBORG("Cyborg"),
        PUNK("Punk");

        public final String displayName;

        private CharacterType(String string2) {
            this.displayName = string2;
        }
    }
}
