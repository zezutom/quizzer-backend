package org.zezutom.capstone.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by tom on 05/10/2014.
 */
public class GameSet implements Serializable {

    private Long id;

    private Collection<Movie> movies;

    private Collection<Guess> guesses;

    private Collection<Rating> ratings;

    private Solution solution;

    public void addMovie(Movie movie) {
        if (movies == null) movies = new ArrayList<>();
        movies.add(movie);
    }

    public Collection<Movie> getMovies() {
        return movies;
    }

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }
}
