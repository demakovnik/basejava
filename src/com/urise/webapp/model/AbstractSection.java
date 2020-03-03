package com.urise.webapp.model;


public abstract class AbstractSection {
    private final SectionType title;
    private final Object composition;

    protected AbstractSection(SectionType title, Object composition) {
        this.title = title;
        this.composition = composition;
    }

    public SectionType getTitle() {
        return title;
    }

    public Object getComposition() {
        return composition;
    }
}
