/*
 * Decompiled with CFR 0.152.
 */
package animation;

public static class AnimationAndSpriteLoader.BulletSpawner {
    public static final String SYSTEM_TYPE = "bullet_spawner";

    public static String selectBulletForGun(String string) {
        if (string.contains("Pistol")) {
            return "01_Weapon_Bullet_TypeA_Single_StaticSprite.png";
        }
        if (string.contains("Compact")) {
            return "02_Weapon_Bullet_TypeB_Single_StaticSprite.png";
        }
        if (string.contains("Detail")) {
            return "04_Weapon_Bullet_TypeD_VariantA_StaticSprite.png";
        }
        if (string.contains("Rifle")) {
            return "06_Weapon_Bullet_TypeE_VariantA_StaticSprite.png";
        }
        if (string.contains("Special")) {
            return "09_Weapon_Bullet_TypeG_VariantA_StaticSprite.png";
        }
        return "01_Weapon_Bullet_TypeA_Single_StaticSprite.png";
    }

    public static BulletInstance fireWeapon(String string, float f, float f2, int n) {
        String string2 = AnimationAndSpriteLoader.BulletSpawner.selectBulletForGun(string);
        float f3 = (float)n * 36.0f;
        return new BulletInstance(string2, f, f2, f3);
    }

    public static class BulletInstance {
        public String bulletType;
        public float x;
        public float y;
        public float velocityX;
        public float velocityY;
        public float directionAngle;
        public long spawnTime;

        public BulletInstance(String string, float f, float f2, float f3) {
            this.bulletType = string;
            this.x = f;
            this.y = f2;
            this.directionAngle = f3;
            this.spawnTime = System.currentTimeMillis();
            float f4 = 5.0f;
            this.velocityX = f4 * (float)Math.cos(Math.toRadians(f3));
            this.velocityY = f4 * (float)Math.sin(Math.toRadians(f3));
        }
    }
}
