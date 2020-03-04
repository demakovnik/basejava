package com.urise.webapp.model;

public abstract class AbstractSection<T> {
    private final T composition;

    protected AbstractSection(T composition) {
        this.composition = composition;
    }

    public T getComposition() {
        return composition;
    }

    protected abstract String getCompositionString();

    @Override
    public String toString() {
        return getCompositionString();
    }
}
