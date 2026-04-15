/*
 * Decompiled with CFR 0.152.
 */
package animation;

import animation.AnimationAndSpriteLoader;
import java.util.List;
public class PhysicsCollisionSystem {
    public void updateBulletPosition(AnimationAndSpriteLoader.BulletSpawner.BulletInstance bulletInstance, long l) {
        float f = (float)l / 1000.0f;
        bulletInstance.x += bulletInstance.velocityX * f;
        bulletInstance.y += bulletInstance.velocityY * f;
        bulletInstance.velocityY += 200.0f * f;
    }

    public CollisionResult checkCollisions(AnimationAndSpriteLoader.BulletSpawner.BulletInstance bulletInstance, List<Integer> list, List<Object> list2, List<Object> list3) {
        CollisionResult collisionResult = new CollisionResult();
        if (this.checkTileCollision(bulletInstance, list, collisionResult)) {
            collisionResult.hasCollided = true;
            collisionResult.targetType = "tile";
            return collisionResult;
        }
        if (this.checkEnemyCollision(bulletInstance, list2, collisionResult)) {
            collisionResult.hasCollided = true;
            collisionResult.targetType = "enemy";
            return collisionResult;
        }
        if (this.checkObjectCollision(bulletInstance, list3, collisionResult)) {
            collisionResult.hasCollided = true;
            collisionResult.targetType = "object";
            return collisionResult;
        }
        return collisionResult;
    }

    private boolean checkTileCollision(AnimationAndSpriteLoader.BulletSpawner.BulletInstance bulletInstance, List<Integer> list, CollisionResult collisionResult) {
        int n = (int)(bulletInstance.x - 4.0f);
        int n2 = (int)(bulletInstance.x + 4.0f);
        int n3 = (int)(bulletInstance.y - 4.0f);
        int n4 = (int)(bulletInstance.y + 4.0f);
        for (int i = n / 32; i <= n2 / 32; ++i) {
            for (int j = n3 / 32; j <= n4 / 32; ++j) {
                int n5 = j * 20 + i;
                if (!list.contains(n5)) continue;
                collisionResult.impactX = bulletInstance.x;
                collisionResult.impactY = bulletInstance.y;
                collisionResult.damage = this.calculateDamage(bulletInstance);
                return true;
            }
        }
        return false;
    }

    private boolean checkEnemyCollision(AnimationAndSpriteLoader.BulletSpawner.BulletInstance bulletInstance, List<Object> list, CollisionResult collisionResult) {
        for (Object object : list) {
            try {
                float f;
                float f2;
                float f3;
                float f4 = ((Float)object.getClass().getMethod("getX", new Class[0]).invoke(object, new Object[0])).floatValue();
                float f5 = bulletInstance.x - f4;
                float f6 = f5 * f5 + (f3 = bulletInstance.y - (f2 = ((Float)object.getClass().getMethod("getY", new Class[0]).invoke(object, new Object[0])).floatValue())) * f3;
                if (!(f6 <= (f = ((Float)object.getClass().getMethod("getRadius", new Class[0]).invoke(object, new Object[0])).floatValue()) * f)) continue;
                collisionResult.target = object;
                collisionResult.impactX = bulletInstance.x;
                collisionResult.impactY = bulletInstance.y;
                collisionResult.damage = this.calculateDamage(bulletInstance);
                return true;
            }
            catch (Exception exception) {
            }
        }
        return false;
    }

    private boolean checkObjectCollision(AnimationAndSpriteLoader.BulletSpawner.BulletInstance bulletInstance, List<Object> list, CollisionResult collisionResult) {
        for (Object object : list) {
            try {
                float f = ((Float)object.getClass().getMethod("getX", new Class[0]).invoke(object, new Object[0])).floatValue();
                float f2 = ((Float)object.getClass().getMethod("getY", new Class[0]).invoke(object, new Object[0])).floatValue();
                float f3 = ((Float)object.getClass().getMethod("getWidth", new Class[0]).invoke(object, new Object[0])).floatValue();
                float f4 = ((Float)object.getClass().getMethod("getHeight", new Class[0]).invoke(object, new Object[0])).floatValue();
                if (!(bulletInstance.x >= f) || !(bulletInstance.x <= f + f3) || !(bulletInstance.y >= f2) || !(bulletInstance.y <= f2 + f4)) continue;
                collisionResult.target = object;
                collisionResult.impactX = bulletInstance.x;
                collisionResult.impactY = bulletInstance.y;
                collisionResult.damage = this.calculateDamage(bulletInstance);
                return true;
            }
            catch (Exception exception) {
            }
        }
        return false;
    }

    private int calculateDamage(AnimationAndSpriteLoader.BulletSpawner.BulletInstance bulletInstance) {
        int n = 10;
        if (bulletInstance.bulletType.contains("E")) {
            n = 15;
        } else if (bulletInstance.bulletType.contains("D")) {
            n = 18;
        } else if (bulletInstance.bulletType.contains("A")) {
            n = 10;
        }
        float f = (float)Math.sqrt(bulletInstance.velocityX * bulletInstance.velocityX + bulletInstance.velocityY * bulletInstance.velocityY) * (float)(System.currentTimeMillis() - bulletInstance.spawnTime) / 1000.0f;
        if (f > 500.0f) {
            float f2 = Math.max(0.1f, 1.0f - (f - 500.0f) / 1000.0f);
            n = (int)((float)n * f2);
        }
        return Math.max(1, n);
    }

    public boolean isBulletOutOfBounds(AnimationAndSpriteLoader.BulletSpawner.BulletInstance bulletInstance, int n, int n2) {
        return bulletInstance.x < -50.0f || bulletInstance.x > (float)(n + 50) || bulletInstance.y < -50.0f || bulletInstance.y > (float)(n2 + 50);
    }
public class CollisionResult {
        public boolean hasCollided = false;
        public String targetType = "none";
        public Object target = null;
        public int damage = 0;
        public float impactX;
        public float impactY;
    }
}
