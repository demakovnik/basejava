package com.urise.webapp.model;

import java.util.Objects;

public class PersonalOrObjectiveSection extends AbstractSection {

    //private static final long serializationUID = 1L;
    private String text;

    public PersonalOrObjectiveSection() {
    }

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

    @Override
    public String toString() {
        return "PersonalOrObjectiveSection{" +
                "text='" + text + '\'' +
                '}';
    }

    public boolean isEmpty() {
        return text.trim().isEmpty();
    }
}
