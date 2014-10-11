package org.zezutom.capstone.model;

/**
 * Created by tom on 05/10/2014.
 */
public class Solution {

    private Movie movie;

    private String explanation;

    public Solution() {}

    public Solution(Movie movie, String explanation) {
        this.movie = movie;
        this.explanation = explanation;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
