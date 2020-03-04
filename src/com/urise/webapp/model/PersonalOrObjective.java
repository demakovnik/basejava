package com.urise.webapp.model;

import java.util.Objects;

public class PersonalOrObjective extends AbstractSection {

    private final String text;

    public PersonalOrObjective(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalOrObjective that = (PersonalOrObjective) o;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}
