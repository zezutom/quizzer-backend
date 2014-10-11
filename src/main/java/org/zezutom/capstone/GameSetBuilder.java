package org.zezutom.capstone;

import org.zezutom.capstone.model.GameSet;
import org.zezutom.capstone.model.Movie;
import org.zezutom.capstone.model.Solution;

/**
 * Created by tom on 10/10/2014.
 */
public class GameSetBuilder {

    private GameSet gameSet = new GameSet();

    public GameSetBuilder addMovie(Movie movie) {
        gameSet.addMovie(movie);
        return this;
    }

    public GameSetBuilder addSolution(Movie movie, String explanation) {
        gameSet.setSolution(new Solution(movie, explanation));
        return this;
    }

    public GameSet build() { return gameSet; }
}
