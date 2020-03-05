package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Organization {

    private final String title;
    private final String position;
    private final LocalDate startTime;
    private final LocalDate endTime;
    private final String description;
    private final String url;

    public Organization(String title, String position, LocalDate startTime, LocalDate endTime, String description, String url) {

        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(position, "position must not be null");
        Objects.requireNonNull(startTime, "startTime must not be null");
        Objects.requireNonNull(endTime, "endTime must not be null");
        this.title = title;
        this.position = position;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getPosition() {
        return position;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return title.equals(that.title) &&
                position.equals(that.position) &&
                startTime.equals(that.startTime) &&
                endTime.equals(that.endTime) &&
                Objects.equals(description, that.description) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, position, startTime, endTime, description, url);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "title='" + title + '\'' +
                ", position='" + position + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
