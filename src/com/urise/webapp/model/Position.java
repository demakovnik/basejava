package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Position {
    private final String title;
    private final LocalDate startTime;
    private final LocalDate endTime;
    private final String description;

    public Position(String position, LocalDate startTime, LocalDate endTime, String description) {
        Objects.requireNonNull(position, "position must not be null");
        Objects.requireNonNull(startTime, "startTime must not be null");
        Objects.requireNonNull(endTime, "endTime must not be null");
        this.title = position;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    public String getTitle() {
        return title;
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
        Position position1 = (Position) o;
        return title.equals(position1.title) &&
                startTime.equals(position1.startTime) &&
                endTime.equals(position1.endTime) &&
                Objects.equals(description, position1.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, startTime, endTime, description);
    }

    @Override
    public String toString() {
        return "Position{" +
                "position='" + title + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", description='" + description + '\'' +
                '}';
    }
}
