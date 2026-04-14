/*
 * Decompiled with CFR 0.152.
 */
package ai;

import ai.AI;

public static interface AI.AIBehavior {
    public void initialize();

    public AIAction execute(AI.AIAgent var1, float var2);

    public boolean isComplete();

    public void reset();

    public void stop();

    public String getName();

    public static abstract class SimpleAction
    implements AIAction {
        protected String name;
        protected boolean complete = false;

        public SimpleAction(String string) {
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

    public static abstract class SimpleBehavior
    implements AI.AIBehavior {
        protected String name;
        protected boolean complete = false;
        protected float duration = 0.0f;
        protected float elapsedTime = 0.0f;

        public SimpleBehavior(String string) {
            this.name = string;
        }

        public SimpleBehavior(String string, float f) {
            this.name = string;
            this.duration = f;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public boolean isComplete() {
            if (this.duration > 0.0f) {
                return this.elapsedTime >= this.duration;
            }
            return this.complete;
        }

        @Override
        public void reset() {
            this.complete = false;
            this.elapsedTime = 0.0f;
        }

        @Override
        public void stop() {
            this.complete = true;
        }

        @Override
        public void initialize() {
        }
    }

    public static interface AIAction {
        public void execute(AI.AIAgent var1);

        public boolean isComplete();

        public String getName();
    }
}
