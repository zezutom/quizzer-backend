package org.zezutom.capstone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.zezutom.capstone.AppUtil;

import java.io.Serializable;

/**
 * Created by tom on 05/10/2014.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie implements Serializable {

    private String title;

    private String genre;

    @JsonProperty("vote_average")
    private Float rating;

    @JsonProperty("release_date")
    private Integer year;

    @JsonProperty("poster_path")
    private String imagePath;

    private String basePath;

    public Movie() {}

    public Movie(String title, String imagePath) {
        this.title = title;
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        if (imagePath == null) return null;
        else if (basePath == null) return imagePath;
        else return basePath + imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = AppUtil.parseRating(rating);
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(String date) {
        this.year = AppUtil.parseYear(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (imagePath != null ? !imagePath.equals(movie.imagePath) : movie.imagePath != null) return false;
        if (title != null ? !title.equals(movie.title) : movie.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (imagePath != null ? imagePath.hashCode() : 0);
        return result;
    }
}
