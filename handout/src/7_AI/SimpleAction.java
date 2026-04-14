/*
 * Decompiled with CFR 0.152.
 */
package ai;

import ai.AI;

public static abstract class AI.AIBehavior.SimpleAction
implements AI.AIBehavior.AIAction {
    protected String name;
    protected boolean complete = false;

    public AI.AIBehavior.SimpleAction(String string) {
        this.name = string;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isComplete() {
        return this.complete;
    }
}
