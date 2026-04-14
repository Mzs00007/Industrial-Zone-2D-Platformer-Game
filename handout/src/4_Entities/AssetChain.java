/*
 * Decompiled with CFR 0.152.
 */
package core_game_entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public static class AssetChainCoordinator.AssetChain {
    public String chainId;
    public String sourcEntity;
    public List<String> assetSequence = new ArrayList<String>();
    public Map<String, String> assetToEntityMap = new HashMap<String, String>();
    public List<String> triggerConditions = new ArrayList<String>();
    public List<String> chainsIntoNext = new ArrayList<String>();

    public AssetChainCoordinator.AssetChain(String string, String string2) {
        this.chainId = string;
        this.sourcEntity = string2;
    }

    public void addAsset(String string, String string2) {
        this.assetSequence.add(string);
        this.assetToEntityMap.put(string, string2);
    }

    public void addTrigger(String string) {
        this.triggerConditions.add(string);
    }

    public void chainInto(String string) {
        this.chainsIntoNext.add(string);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Chain: ").append(this.chainId).append("\n");
        stringBuilder.append("  Source: ").append(this.sourcEntity).append("\n");
        stringBuilder.append("  Assets: ");
        for (String string : this.assetSequence) {
            stringBuilder.append("\n    - ").append(string);
            stringBuilder.append(" (").append(this.assetToEntityMap.get(string)).append(")");
        }
        stringBuilder.append("\n  Triggers: ").append(this.triggerConditions);
        stringBuilder.append("\n  Chains Into: ").append(this.chainsIntoNext);
        return stringBuilder.toString();
    }
}
