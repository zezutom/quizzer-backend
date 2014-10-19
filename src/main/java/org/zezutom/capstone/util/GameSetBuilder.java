package org.zezutom.capstone.util;

import org.zezutom.capstone.model.GameSet;
import org.zezutom.capstone.model.Movie;

public class GameSetBuilder {

    private GameSet gameSet = new GameSet();

    public GameSetBuilder addMovie(Movie movie) {
        gameSet.addMovie(movie);
        return this;
    }

    public GameSetBuilder setAnswer(int index) {
        gameSet.setAnswer(index);
        return this;
    }

    public GameSetBuilder setExplanation(String explanation) {
        gameSet.setExplanation(explanation);
        return this;
    }

    public GameSet build() {
        return gameSet;
    }
}
