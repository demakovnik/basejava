package com.urise.webapp.model;

import java.util.Objects;

public class PersonalOrObjectiveSection extends AbstractSection {

    private final String text;

    public PersonalOrObjectiveSection(String text) {
        Objects.requireNonNull(text, "text must not be null");
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalOrObjectiveSection that = (PersonalOrObjectiveSection) o;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}
