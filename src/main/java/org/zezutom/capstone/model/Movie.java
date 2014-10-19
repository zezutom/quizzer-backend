package org.zezutom.capstone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.datanucleus.api.jpa.annotations.Extension;
import org.zezutom.capstone.util.AppUtil;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
    private String id;

    @Version
    private Long version;

    private String title;

    private String genre;

    @JsonProperty("vote_average")
    private Double rating;

    private Integer year;

    @JsonProperty("poster_path")
    private String imageUrl;

    private String basePath;

    @ManyToMany
    private List<GameSet> gameSets;

    public Movie() {}

    public Movie(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        if (imageUrl == null) return null;
        else if (basePath == null) return imageUrl;
        else return basePath + imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public Double getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = AppUtil.parseRating(rating);
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @JsonProperty("release_date")
    public void setReleaseYear(String date) {
        this.year = AppUtil.parseYear(date);
    }

    public void addGameSet(GameSet gameSet) {
        if (gameSets == null) gameSets = new ArrayList<>();
        gameSets.add(gameSet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (imageUrl != null ? !imageUrl.equals(movie.imageUrl) : movie.imageUrl != null) return false;
        if (title != null ? !title.equals(movie.title) : movie.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        return result;
    }
}
