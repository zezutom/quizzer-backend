package org.zezutom.capstone.model;

import org.zezutom.capstone.util.AppUtil;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GameSet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    private String explanation;

    private Integer answer;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Movie> movies;

    // TODO replace with lazy loading (you'd have to encounter for detached objects)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "gameSet")
    private List<Rating> ratings;

    public Long getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void addMovie(Movie movie) {
        if (movies == null) movies = new ArrayList<>();
        movies.add(movie);
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void addRating(Rating rating) {
        if (ratings == null) ratings = new ArrayList<>();
        ratings.add(rating);
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }
}
