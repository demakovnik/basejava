package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Organization {

    private final Link link;
    private final String position;
    private final LocalDate startTime;
    private final LocalDate endTime;
    private final String description;


    public Organization(String title, String position, LocalDate startTime, LocalDate endTime, String description, String url) {
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(position, "position must not be null");
        Objects.requireNonNull(startTime, "startTime must not be null");
        Objects.requireNonNull(endTime, "endTime must not be null");
        link = new Link(title, url);
        this.position = position;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    public Link getLink() {
        return link;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(link, that.link) &&
                position.equals(that.position) &&
                startTime.equals(that.startTime) &&
                endTime.equals(that.endTime) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, position, startTime, endTime, description);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "link=" + link +
                ", position='" + position + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", description='" + description + '\'' +
                '}';
    }
}
