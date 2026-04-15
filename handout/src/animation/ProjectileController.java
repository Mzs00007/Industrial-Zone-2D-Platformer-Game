/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
public class ProjectileController
extends AnimationAndSpriteLoader.EntityAnimationController {
    private float damage;
    private float lifetime;
    private long spawnTime;

    public ProjectileController(AnimationAndSpriteLoader.PhysicsUnitSystem.PhysicsBody physicsBody, float f) {
        super(physicsBody);
        this.damage = f;
        this.lifetime = 5.0f;
        this.spawnTime = System.currentTimeMillis();
    }

    @Override
    protected void initializeAssets() {
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.SPARKLE_BURST, "Resources/industrial-zone/weapons/1/5 Bullets/01_Weapon_Bullet_TypeA_Single_StaticSprite.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.IMPACT_HIT, "Resources/industrial-zone/weapons/1/5 Bullets/02_Weapon_Bullet_TypeB_Single_StaticSprite.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.ENERGY_BEAM, "Resources/industrial-zone/weapons/1/5 Bullets/03_Weapon_Bullet_TypeC_Single_StaticSprite.png");
        this.stateToAssetPath.put(AnimationAndSpriteLoader.AnimationState.EXPLOSION, "Resources/industrial-zone/weapons/1/5 Bullets/06_Weapon_Bullet_TypeE_VariantA_StaticSprite.png");
    }

    @Override
    protected void initializeTransitions() {
    }

    @Override
    protected void updatePhysicsForState(AnimationAndSpriteLoader.AnimationState animationState, float f) {
        this.physics.isAffectedByGravity = true;
        this.physics.update(f);
    }

    public boolean isAlive() {
        long l = System.currentTimeMillis() - this.spawnTime;
        return (float)l < this.lifetime * 1000.0f;
    }

    public float getDamage() {
        return this.damage;
    }
}
