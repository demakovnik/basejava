package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ExperienceOrEducationSection extends AbstractSection {

    //private static final long serializationUID = 1L;

    private final List<Organization> listOfExperienceOrEducation;

    public ExperienceOrEducationSection(List<Organization> listOfExperienceOrEducation) {
        Objects.requireNonNull(listOfExperienceOrEducation, "listOfExperienceOrEducation must not be null");
        this.listOfExperienceOrEducation = listOfExperienceOrEducation;
    }

    public List<Organization> getListOfExperienceOrEducation() {
        return listOfExperienceOrEducation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperienceOrEducationSection that = (ExperienceOrEducationSection) o;
        return listOfExperienceOrEducation.equals(that.listOfExperienceOrEducation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listOfExperienceOrEducation);
    }
}
