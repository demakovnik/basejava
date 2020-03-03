package com.urise.webapp.model;

import java.util.Objects;

public class Organization {

    private final String title;
    private final String startTime;
    private final String endTime;
    private final String description;

    public Organization(String title, String startTime, String endTime, String description) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return title.equals(that.title) &&
                startTime.equals(that.startTime) &&
                endTime.equals(that.endTime) &&
                description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, startTime, endTime, description);
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDescription() {
        return description;
    }
}
