/*
 * Decompiled with CFR 0.152.
 */
package animation;
import game2D.*;

import java.util.ArrayList;
import java.util.List;
public class LevelWeaponPlacementSystem {
    private int[][] tileMap;
    private int mapWidth;
    private int mapHeight;
    private List<WeaponPlacement> placements;

    public LevelWeaponPlacementSystem(int[][] nArray, int n, int n2) {
        this.tileMap = nArray;
        this.mapWidth = n;
        this.mapHeight = n2;
        this.placements = new ArrayList<WeaponPlacement>();
    }

    public boolean placeWeapon(String string, int n, int n2, int n3, int n4) {
        WeaponPlacement weaponPlacement = new WeaponPlacement(string, n, n2, n3, n4);
        if (n3 < 0 || n3 >= this.mapWidth || n4 < 0 || n4 >= this.mapHeight) {
            weaponPlacement.validationReason = "Out of bounds";
            this.placements.add(weaponPlacement);
            return false;
        }
        int n5 = this.tileMap[n4][n3];
        weaponPlacement.placementTile = String.valueOf(n5);
        if (!this.isTileValidForWeapon(n5, string)) {
            weaponPlacement.validationReason = "Tile " + n5 + " not in valid set for " + string;
            this.placements.add(weaponPlacement);
            return false;
        }
        if (!this.hasAdjacentSupportTile(n3, n4)) {
            weaponPlacement.validationReason = "No support tile below";
            this.placements.add(weaponPlacement);
            return false;
        }
        weaponPlacement.isValid = true;
        weaponPlacement.validationReason = "Placed successfully on tile " + n5;
        this.placements.add(weaponPlacement);
        return true;
    }

    private boolean isTileValidForWeapon(int n, String string) {
        if (n == 52 || n == 58) {
            return false;
        }
        if (n == 55 || n >= 77 && n <= 80) {
            return false;
        }
        if (n < 3) {
            return false;
        }
        return n >= 3 && n <= 74;
    }

    private boolean hasAdjacentSupportTile(int n, int n2) {
        int n3;
        if (n2 + 1 < this.mapHeight && this.isWalkableTile(n3 = this.tileMap[n2 + 1][n])) {
            return true;
        }
        return n2 == this.mapHeight - 1;
    }

    private boolean isWalkableTile(int n) {
        return n >= 3 && n <= 74 && n != 52 && n != 58 && n != 55 && (n < 77 || n > 80);
    }

    public List<int[]> findValidSpawnPoints(String string, float f) {
        ArrayList<int[]> arrayList = new ArrayList<int[]>();
        for (int i = 0; i < this.mapHeight; ++i) {
            for (int j = 0; j < this.mapWidth; ++j) {
                int n = this.tileMap[i][j];
                if (!this.isTileValidForWeapon(n, string) || !this.hasAdjacentSupportTile(j, i) || !(Math.random() < (double)f)) continue;
                arrayList.add(new int[]{j, i});
            }
        }
        return arrayList;
    }

    public List<WeaponPlacement> getAllPlacements() {
        return new ArrayList<WeaponPlacement>(this.placements);
    }

    public String getPlacementSummary() {
        int n = this.placements.size();
        int n2 = 0;
        for (WeaponPlacement weaponPlacement : this.placements) {
            if (!weaponPlacement.isValid) continue;
            ++n2;
        }
        return String.format("Weapon Placements: %d total, %d valid (%.1f%% success)", n, n2, Float.valueOf(100.0f * (float)n2 / (float)Math.max(1, n)));
    }
public class WeaponPlacement {
        public String gunFile;
        public int screenX;
        public int screenY;
        public int tileX;
        public int tileY;
        public String placementTile;
        public String validationReason;
        public boolean isValid;

        public WeaponPlacement(String string, int n, int n2, int n3, int n4) {
            this.gunFile = string;
            this.screenX = n;
            this.screenY = n2;
            this.tileX = n3;
            this.tileY = n4;
            this.isValid = false;
        }
    }
}
