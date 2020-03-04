package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class AchievementOrQualifications extends AbstractSection {

    private final List<String> listOfAchievementsOrQualifications;

    public AchievementOrQualifications(List<String> listOfAchievementsOrQualifications) {
        this.listOfAchievementsOrQualifications = listOfAchievementsOrQualifications;
    }

    public List<String> getListOfAchievementsOrQualifications() {
        return listOfAchievementsOrQualifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AchievementOrQualifications that = (AchievementOrQualifications) o;
        return listOfAchievementsOrQualifications.equals(that.listOfAchievementsOrQualifications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listOfAchievementsOrQualifications);
    }
}

