/*
 * Decompiled with CFR 0.152.
 */
package core;

public static class Core.StateMachine<T extends Enum<T>> {
    private T currentState;
    private T previousState;
    private long stateTransitionTime;
    private int transitionCount;
    private StateChangeListener<T> stateChangeListener;

    public Core.StateMachine(T t) {
        this.currentState = t;
        this.previousState = null;
        this.stateTransitionTime = 0L;
        this.transitionCount = 0;
    }

    public void setStateChangeListener(StateChangeListener<T> stateChangeListener) {
        this.stateChangeListener = stateChangeListener;
    }

    public boolean setState(T t) {
        if (t == this.currentState) {
            return false;
        }
        this.previousState = this.currentState;
        this.currentState = t;
        this.stateTransitionTime = 0L;
        ++this.transitionCount;
        if (this.stateChangeListener != null) {
            this.stateChangeListener.onStateChange(this.previousState, t);
        }
        return true;
    }

    public void update(long l) {
        this.stateTransitionTime += l;
    }

    public T getState() {
        return this.currentState;
    }

    public T getPreviousState() {
        return this.previousState;
    }

    public boolean isInState(T t) {
        return this.currentState == t;
    }

    public boolean wasInState(T t) {
        return this.previousState == t;
    }

    public long getStateTime() {
        return this.stateTransitionTime;
    }

    public int getTransitionCount() {
        return this.transitionCount;
    }

    public static interface StateChangeListener<E> {
        public void onStateChange(E var1, E var2);
    }
}
