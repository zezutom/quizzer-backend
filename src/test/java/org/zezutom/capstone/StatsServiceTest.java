package org.zezutom.capstone;

import com.google.appengine.api.users.User;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zezutom.capstone.model.*;
import org.zezutom.capstone.service.GameService;
import org.zezutom.capstone.service.QuizService;
import org.zezutom.capstone.service.StatsService;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-servlet.xml")
public class StatsServiceTest {

    @Autowired
    private StatsService statsService;

    @Autowired
    private GameService gameService;

    @Autowired
    private QuizService quizService;

    private final LocalServiceTestHelper helper = TestUtil.getDatastoreTestHelper();

    @Before
    public void setUp() {
        helper.setUp();
        TestUtil.login();
    }

    @After
    public void tearDown() {
        helper.tearDown();
        TestUtil.logout();
    }

    @Test
    public void getGameResultStats() {
        final User user = TestUtil.createUser();

        GameResult coolResult = createGameResult(30, 10, 4, 8);
        GameResult okayResult = createGameResult(20, 5, 2, 5);
        GameResult poorResult = createGameResult(10, 2, 1, 3);

        gameService.saveGameResult(user, coolResult.getRound(), coolResult.getScore(), coolResult.getPowerUps(), 10, 8);
        gameService.saveGameResult(user, okayResult.getRound(), okayResult.getScore(), okayResult.getPowerUps(), 5, 5);
        gameService.saveGameResult(user, poorResult.getRound(), poorResult.getScore(), poorResult.getPowerUps(), 2, 3);

        GameResultStats stats = statsService.getGameResultStats(user);

        final int expectedAttemptOneRatio = Math.max
                        (Math.max(coolResult.getAttemptOneRatio(), okayResult.getAttemptOneRatio()),
                        poorResult.getAttemptOneRatio());
        final int expectedAttemptTwoRatio = Math.max
                        (Math.max(coolResult.getAttemptTwoRatio(), okayResult.getAttemptTwoRatio()),
                        poorResult.getAttemptTwoRatio());
        final int expectedAttemptThreeRatio = Math.max
                        (Math.max(coolResult.getAttemptThreeRatio(), okayResult.getAttemptThreeRatio()),
                        poorResult.getAttemptThreeRatio());

        assertThat(stats.getPowerUps(), is(coolResult.getPowerUps()));
        assertThat(stats.getRound(), is(coolResult.getRound()));
        assertThat(stats.getAttemptOneRatio(), is(expectedAttemptOneRatio));
        assertThat(stats.getAttemptTwoRatio(), is(expectedAttemptTwoRatio));
        assertThat(stats.getAttemptThreeRatio(), is(expectedAttemptThreeRatio));
    }

    @Test
    public void getGameResults() {
        final int count = 10;
        final User user = TestUtil.createUser();

        final int round = 10;
        final int oneTimeAttempts = 5;
        final int oneTimeConsecutiveAttempts = 2;
        final int twoTimeAttempts = 1;

        for (int i = 0; i < count; i++) {
            GameResult gameResult = createGameResult(round, oneTimeAttempts, oneTimeConsecutiveAttempts, twoTimeAttempts);
            assertThat(gameService.saveGameResult(user,
                    round, gameResult.getScore(), gameResult.getPowerUps(),
                    oneTimeAttempts, twoTimeAttempts), is(gameResult));
        }

        List<GameResult> gameResults = statsService.getGameResults(user);
        TestUtil.assertEntities(count, gameResults);
    }

    @Test
    public void getPlayoffResults() {
        final int count = 10;
        final User user = TestUtil.createUser();
        final String opponentId = "Myself";
        final int round = 10;
        final boolean win = false;

        for (int i = 0; i < count; i++) {
            PlayoffResult playoffResult = TestUtil.createPlayoffResult(opponentId, round, win);
            assertThat(gameService.savePlayoffResult(user, opponentId, round, win), is(playoffResult));
        }

        List<PlayoffResult> playOffResults = statsService.getPlayoffResults(user);
        TestUtil.assertEntities(count, playOffResults);
    }

    @Test
    public void getQuizRatings() {
        final int count = 10;
        final User user = TestUtil.createUser();

        quizService.addNew(user, TestUtil.createQuiz());
        Quiz quiz = quizService.getAll().get(0);

        for (int i = 0; i < count; i++) {
            assertNotNull(quizService.rate(user, quiz.getId(), i % 2 == 0));
        }

        List<QuizRating> quizRatings = statsService.getQuizRatings(quiz.getId());
        TestUtil.assertEntities(count, quizRatings);

        int likes = 0, dislikes = 0;
        for (QuizRating quizRating : quizRatings) {
            if (quizRating.isLiked()) likes++; else dislikes++;
        }
        assertTrue(likes == dislikes);
        assertThat(likes + dislikes, is(count));
    }

    @Test
    public void getQuizRatingStats() {
        final int count = 10;
        final User user = TestUtil.createUser();

        Quiz quiz = quizService.addNew(user, TestUtil.createQuiz());

        int likes = 0, dislikes = 0;
        for (int i = 0; i < count; i++) {
            boolean liked = i % 2 == 0;
            if (liked) likes++; else dislikes++;
            assertNotNull(quizService.rate(user, quiz.getId(), liked));
        }

        List<QuizRatingStats> statsList = statsService.getQuizRatingStats();
        assertNotNull(statsList);
        assertThat(statsList.size(), is(1));

        QuizRatingStats stats = statsList.get(0);
        assertThat(stats.getQuizId(), is(quiz.getId()));
        assertThat(stats.getTitle(), is(quiz.getTitle()));
        assertThat(stats.getRatingCount(), is(count));
        assertThat(stats.getDownVotes(), is(likes));
        assertThat(stats.getUpVotes(), is(dislikes));
    }

    private GameResult createGameResult(int round, int oneTimeAttempts, int oneTimeConsecutiveAttempts, int twoTimeAttempts) {
        return new GameResultBuilder()
                .setRound(round)
                .setOneTimeAttempts(oneTimeAttempts)
                .setOneTimeConsecutiveAttempts(oneTimeConsecutiveAttempts)
                .setTwoTimeAttempts(twoTimeAttempts)
                .build();
    }

}
