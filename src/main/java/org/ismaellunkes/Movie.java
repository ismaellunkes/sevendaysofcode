package org.ismaellunkes;

import java.io.Serializable;
import java.util.Comparator;

public class Movie implements Serializable {
    public String id;
    public Integer rank;
    public String title;
    public String fullTitle;
    public Integer year;
    public String image;
    public String crew;
    public Double imDbRating;
    public Integer imDbRatingCount;

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", rank=" + rank +
                ", title='" + title + '\'' +
                ", fullTitle='" + fullTitle + '\'' +
                ", year=" + year +
                ", image='" + image + '\'' +
                ", crew='" + crew + '\'' +
                ", imDbRating=" + imDbRating +
                ", imDbRatingCount=" + imDbRatingCount +
                '}';
    }
}
