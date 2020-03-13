package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Link implements Serializable {
    private String title;
    private String url;

    public Link() {
    }

    public Link(String title, String url) {
        Objects.requireNonNull(title, "title must not be null");
        this.title = title;
        if (url == null) {
            this.url = "null";
        }
        this.url = url;
    }

    public Link(String title) {
        this(title,"null");
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return title.equals(link.title) &&
                Objects.equals(url, link.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url);
    }

    @Override
    public String toString() {
        return "Link{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
