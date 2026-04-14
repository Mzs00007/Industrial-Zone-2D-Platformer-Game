package enums;

/**
 * AssetEnumIndex - Master index for all asset enums
 * Auto-generated: 2026-04-13
 * Total assets: 1174
 */
public class AssetEnumIndex {

    public static void printAssetInventory() {
        System.out.println("==== ASSET INVENTORY ====");
        System.out.println("Total Assets: 1174");
        System.out.println("Categories: 8");
        System.out.println();

        System.out.println("  weapons: " + WeaponAssets.values().length + " files");
        System.out.println("  1 Tiles: " + TileAssets.values().length + " files");
        System.out.println("  characters: " + CharacterAssets.values().length + " files");
        System.out.println("  KeyBoard_Keys: " + KeyboardKeyAssets.values().length + " files");
        System.out.println("  gui: " + GUIAssets.values().length + " files");
        System.out.println("  vfx: " + VFXAssets.values().length + " files");
        System.out.println("  Mouse_keys: " + MouseKeyAssets.values().length + " files");
        System.out.println("  audio: " + AudioAssets.values().length + " files");

        System.out.println("========================");
    }

    public static void main(String[] args) {
        printAssetInventory();
    }
}
