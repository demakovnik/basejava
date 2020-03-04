package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ExperienceOrEducation extends AbstractSection {

    private final List<Organization> listOfExperienceOrEducation;

    public ExperienceOrEducation(List<Organization> listOfExperienceOrEducation) {
        this.listOfExperienceOrEducation = listOfExperienceOrEducation;
    }

    public List<Organization> getListOfExperienceOrEducation() {
        return listOfExperienceOrEducation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperienceOrEducation that = (ExperienceOrEducation) o;
        return listOfExperienceOrEducation.equals(that.listOfExperienceOrEducation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listOfExperienceOrEducation);
    }

}
