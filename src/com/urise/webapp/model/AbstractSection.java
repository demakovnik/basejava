package com.urise.webapp.model;


public abstract class AbstractSection {
    private final SectionType title;
    private final Object property;

    protected AbstractSection(SectionType title, Object property) {
        this.title = title;
        this.property = property;
    }

    public SectionType getTitle() {
        return title;
    }

    public Object getProperty() {
        return property;
    }
}
