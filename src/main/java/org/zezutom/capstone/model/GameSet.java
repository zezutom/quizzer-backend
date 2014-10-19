package org.zezutom.capstone.model;

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

    private String author;

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

    public void setId(Long id) {
        this.id = id;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameSet)) return false;

        GameSet gameSet = (GameSet) o;

        if (answer != null ? !answer.equals(gameSet.answer) : gameSet.answer != null) return false;
        if (author != null ? !author.equals(gameSet.author) : gameSet.author != null) return false;
        if (explanation != null ? !explanation.equals(gameSet.explanation) : gameSet.explanation != null) return false;
        if (id != null ? !id.equals(gameSet.id) : gameSet.id != null) return false;
        if (version != null ? !version.equals(gameSet.version) : gameSet.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (explanation != null ? explanation.hashCode() : 0);
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        return result;
    }
}
