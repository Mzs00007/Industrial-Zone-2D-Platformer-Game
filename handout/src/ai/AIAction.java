/*
 * Decompiled with CFR 0.152.
 */
package ai;

import ai.AI;

public static interface AI.AIBehavior.AIAction {
    public void execute(AI.AIAgent var1);

    public boolean isComplete();

    public String getName();
}
