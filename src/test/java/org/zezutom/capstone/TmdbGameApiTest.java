package org.zezutom.capstone;

import com.google.appengine.api.users.User;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zezutom.capstone.dao.GameSetRepository;
import org.zezutom.capstone.dao.ScoreRepository;
import org.zezutom.capstone.model.*;
import org.zezutom.capstone.service.GameApi;
import org.zezutom.capstone.util.GameSetBuilder;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-servlet.xml")
public class TmdbGameApiTest {

    @Autowired
    private GameApi gameApi;

    @Autowired
    private GameSetRepository gameSetRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig().setApplyAllHighRepJobPolicy());

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void randomizeByDifficulty() {

        // Prepare data
        gameSetRepository.save(createGameSet(Difficulty.EASY));
        gameSetRepository.save(createGameSet(Difficulty.EASY));
        gameSetRepository.save(createGameSet(Difficulty.EASY));
        gameSetRepository.save(createGameSet(Difficulty.TOUGH));

        // Set expectations
        final int twoEasyOnes = 2;

        // Fetch results
        final List<GameSet> gameSets = gameApi.getRandomGameSetsByDifficulty(twoEasyOnes, Difficulty.EASY);
        assertNotNull(gameSets);

        // The total number of returned records should (in this case) fit the requested count
        assertTrue(gameSets.size() == twoEasyOnes);

        // Verify individual game sets
        for (GameSet gameSet : gameSets) assertGameSet(gameSet, Difficulty.EASY);
    }

    @Test
    public void randomizeByCriteria() {

        // Prepare data
        gameSetRepository.save(createGameSet(Difficulty.EASY));
        gameSetRepository.save(createGameSet(Difficulty.AVERAGE));
        gameSetRepository.save(createGameSet(Difficulty.CHALLENGING));
        gameSetRepository.save(createGameSet(Difficulty.CHALLENGING));
        gameSetRepository.save(createGameSet(Difficulty.TOUGH));

        // Set expectations
        final int anAverageOne = 1;
        final int twoChallenges = 2;
        final int aToughOne = 1;

        final Map<Difficulty, Integer> criteria = new HashMap<>();
        criteria.put(Difficulty.AVERAGE, anAverageOne);
        criteria.put(Difficulty.CHALLENGING, twoChallenges);
        criteria.put(Difficulty.TOUGH, aToughOne);

        // Fetch results
        final List<GameSet> gameSets = gameApi.getRandomGameSetsByCriteria(criteria);
        assertNotNull(gameSets);

        // The total number of returned records should (in this case) fit the sum of all requested counts
        assertTrue(gameSets.size() == anAverageOne + twoChallenges + aToughOne);

        // The difficulty spread should be as per the criteria
        final Map<Difficulty, Integer> difficultyMap = new EnumMap<Difficulty, Integer>(Difficulty.class);

        for (GameSet gameSet : gameSets) {
            assertGameSet(gameSet);

            final Difficulty difficulty = gameSet.getDifficulty();
            final Integer count = difficultyMap.get(difficulty);
            difficultyMap.put(difficulty, (count == null) ? 1 : count + 1);
        }

        assertTrue(difficultyMap.get(Difficulty.AVERAGE) == anAverageOne);
        assertTrue(difficultyMap.get(Difficulty.CHALLENGING) == twoChallenges);
        assertTrue(difficultyMap.get(Difficulty.TOUGH) == aToughOne);
    }

    @Test
    public void rate() {

        // Retrieve a game set
        GameSet gameSet = createDefaultGameSet();
        assertNotNull(gameSet);

        // rate it
        final double value = 3d;
        final User user = createUser();
        gameApi.rate(user, gameSet.getId(), value);

        // verify the rating was saved
        gameSet = gameSetRepository.getOne(gameSet.getId());
        final List<Rating> ratings = gameSet.getRatings();
        assertThat(ratings.size(), is(1));

        // and check the value is as expected
        Rating rating = ratings.get(0);
        assertNotNull(rating);
        assertThat(rating.getValue(), is(value));

        // and the rating is associated with the correct user
        assertThat(rating.getUsername(), is(user.getUserId()));
    }

    @Test
    public void score() {
        final User user = createUser();
        final Integer value = 100;

        // Let the save her score
        gameApi.score(user, value);

        // Verify it was actually saved
        List<Score> scores = scoreRepository.findAll();
        assertNotNull(scores);
        assertThat(scores.size(), is(1));

        // and that it contains the expected value
        Score score = scores.get(0);
        assertNotNull(score);
        assertThat(score.getValue(), is(value));

        // and that it is associated with the user
        assertThat(score.getUsername(), is(user.getUserId()));
    }

    @Test
    public void addGameSet() {

        final GameSet gameSet = new GameSetBuilder()
                .setDifficulty(Difficulty.AVERAGE)
                .addMovie(createMovie("The Wolf of Wall Street", "7.5", 2013, "/f4Dup6awDfDqAHKgWqNJ2HFw1qN.jpg"))
                .addMovie(createMovie("The Hunger Games", "6.9", 2012, "/b2SUvKY4ZkkY9a1OzW9uetbW8vx.jpg"))
                .addMovie(createMovie("The Hobbit", "4.3", 2012, "/kHwBfsvYOY8url7KrCtBRbXBpiB.jpg"))
                .addMovie(createMovie("Gravity", "8.2", 2013, "/t4rHBaoIMFbN0hRbqxCfinW3VkQ.jpg"))
                .setAnswer(3)
                .setExplanation("The is no Hobbit anywhere but in this one.")
                .build();

        assertThat(gameApi.addGameSet(createUser(), gameSet), is(gameSet));
    }

    private GameSet createDefaultGameSet() {
        return gameSetRepository.save(createGameSet(Difficulty.AVERAGE));
    }

    private void assertGameSet(GameSet gameSet) {
        // A game set should comprise exactly four movies
        final List<Movie> movies = gameSet.getMovies();
        assertNotNull(movies);
        assertThat(movies.size(), is(4));

        // There should be a solution to the game set
        final Integer answer = gameSet.getAnswer();
        assertNotNull(answer);

        // The solution should refer to one of the 4 movies
        assertTrue(answer >= 0 && answer < movies.size());

        // and an explanation should be provided
        assertNotNull(gameSet.getExplanation());
    }

    private void assertGameSet(GameSet gameSet, Difficulty difficulty) {
        assertGameSet(gameSet);
        assertThat(gameSet.getDifficulty(), is(difficulty));
    }

    private GameSet createGameSet(Difficulty difficulty) {
        GameSet gameSet = new GameSet();
        gameSet.setDifficulty(difficulty);

        // Add movies
        for (Movie movie : getMovies()) gameSet.addMovie(movie);

        // Randomize an answer
        Random random = new Random();
        gameSet.setAnswer(random.nextInt(gameSet.getMovies().size() - 1));

        // Explanation
        gameSet.setExplanation("Just for the mockery of it!");

        return gameSet;
    }

    private List<Movie> getMovies() {
        final List<Movie> movies = new ArrayList<>();

        movies.add(createMovie("The Terminal", "6.8", 2004, "/f4Dup6awDfDqAHKgWqNJ2HFw1qN.jpg"));
        movies.add(createMovie("The Terminal Man", "0.0", 1974, "/b2SUvKY4ZkkY9a1OzW9uetbW8vx.jpg"));
        movies.add(createMovie("The Smashing Pumpkins: Terminal 5", "9.5", 2011, "/t4rHBaoIMFbN0hRbqxCfinW3VkQ.jpg"));
        movies.add(createMovie("The Terminal Trust", "3.0", 2012, "/kHwBfsvYOY8url7KrCtBRbXBpiB.jpg"));

        return movies;
    }

    private User createUser() {
        return new User("test", "test@test.com");
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
