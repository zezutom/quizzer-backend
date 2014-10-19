package org.zezutom.capstone;

import org.junit.Test;
import org.zezutom.capstone.model.GameSet;
import org.zezutom.capstone.model.Movie;
import org.zezutom.capstone.util.GameSetBuilder;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class GameSetBuilderTest {

    @Test
    public void build() {
        final Movie movie1 = createMovie("The Wolf of Wall Street", "7.5", 2013, "/f4Dup6awDfDqAHKgWqNJ2HFw1qN.jpg");
        final Movie movie2 = createMovie("The Hunger Games", "6.9", 2012, "/b2SUvKY4ZkkY9a1OzW9uetbW8vx.jpg");
        final Movie movie3 = createMovie("The Hobbit", "4.3", 2012, "/kHwBfsvYOY8url7KrCtBRbXBpiB.jpg");
        final Movie movie4 = createMovie("Gravity", "8.2", 2013, "/t4rHBaoIMFbN0hRbqxCfinW3VkQ.jpg");
        final int answer = 3;
        final String explanation = "The is no Hobbit anywhere but in this one.";

        final GameSet gameSet = new GameSetBuilder()
                .addMovie(movie1)
                .addMovie(movie2)
                .addMovie(movie3)
                .addMovie(movie4)
                .setAnswer(answer)
                .setExplanation(explanation)
                .build();

        assertNotNull(gameSet);

        final List<Movie> movies = gameSet.getMovies();

        assertThat(movies.size(), is(4));
        assertTrue(movies.contains(movie1));
        assertTrue(movies.contains(movie2));
        assertTrue(movies.contains(movie3));
        assertTrue(movies.contains(movie4));
        assertThat(gameSet.getAnswer(), is(answer));
        assertThat(gameSet.getExplanation(), is(explanation));
    }

    private Movie createMovie(String title, String rating, Integer year, String imageUrl) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setRating(rating);
        movie.setYear(year);
        movie.setImageUrl(imageUrl);

        return movie;
    }
}
