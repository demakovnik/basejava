package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization {

    private final Link link;
    private final List<Position> positionList;

    public Organization(String title, String url, List<Position> positionList) {
        Objects.requireNonNull(positionList, "positionList must not be null");
        link = new Link(title, url);
        this.positionList = positionList;
    }

    public Link getLink() {
        return link;
    }

    public List<Position> getPositionList() {
        return positionList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(link, that.link) &&
                positionList.equals(that.positionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, positionList);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "link=" + link +
                ", positionList=" + positionList +
                '}';
    }
}
