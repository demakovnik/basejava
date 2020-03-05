package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class AchievementOrQualificationsSection extends AbstractSection {

    private final List<String> listOfAchievementsOrQualifications;

    public AchievementOrQualificationsSection(List<String> listOfAchievementsOrQualifications) {
        Objects.requireNonNull(listOfAchievementsOrQualifications, "listOfAchievementsOrQualifications must not be null");
        this.listOfAchievementsOrQualifications = listOfAchievementsOrQualifications;
    }

    public List<String> getListOfAchievementsOrQualifications() {
        return listOfAchievementsOrQualifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AchievementOrQualificationsSection that = (AchievementOrQualificationsSection) o;
        return listOfAchievementsOrQualifications.equals(that.listOfAchievementsOrQualifications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listOfAchievementsOrQualifications);
    }
}

