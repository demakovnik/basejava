package com.urise.webapp.model;

import com.urise.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Position implements Serializable {
    private String title;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate startTime;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)

    private LocalDate endTime;
    private String description;

    public Position() {
    }

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