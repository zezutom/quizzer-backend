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
import org.zezutom.capstone.domain.*;
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
        User user = TestUtil.createUser();
        GameResult coolResult = boostResult(3);
        GameResult okayResult = boostResult(2);
        GameResult poorResult = boostResult(1);

        gameService.saveGameResult(user, coolResult);
        gameService.saveGameResult(user, okayResult);
        gameService.saveGameResult(user, poorResult);

        GameResultStats stats = statsService.getGameResultStats(user);
        assertThat(stats.getPowerUps(), is(coolResult.getPowerUps()));
        assertThat(stats.getRound(), is(coolResult.getRound()));
        assertThat(stats.getRoundOneRatio(), is(coolResult.getRoundOneRatio()));
        assertThat(stats.getRoundTwoRatio(), is(coolResult.getRoundTwoRatio()));
        assertThat(stats.getRoundThreeRatio(), is(coolResult.getRoundThreeRatio()));
    }

    @Test
    public void getGameResults() {
        final int count = 10;
        final User user = TestUtil.createUser();

        for (int i = 0; i < count; i++) {
            gameService.saveGameResult(user, TestUtil.createGameResult());
        }

        List<GameResult> gameResults = statsService.getGameResults(user);
        TestUtil.assertEntities(count, gameResults);

        for (GameResult gameResult : gameResults) {
            assertEquals(TestUtil.createGameResult(), gameResult);
        }
    }

    @Test
    public void getPlayoffResults() {
        final int count = 10;
        final User user = TestUtil.createUser();
        final String opponentId = "Myself";

        for (int i = 0; i < count; i++) {
            gameService.savePlayoffResult(user, TestUtil.createPlayoffResult(opponentId));
        }

        List<PlayoffResult> playOffResults = statsService.getPlayoffResults(user);
        TestUtil.assertEntities(count, playOffResults);

        for (PlayoffResult playOffResult : playOffResults) {
            assertEquals(TestUtil.createPlayoffResult(opponentId), playOffResult);
        }
    }

    @Test
    public void getQuizRatings() {
        final int count = 10;
        final User user = TestUtil.createUser();

        quizService.addNew(user, TestUtil.createQuiz());
        Quiz quiz = quizService.getAll().get(0);

        for (int i = 0; i < count; i++) {
            quizService.rate(user, quiz.getId(), i % 2 == 0);
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

        quizService.addNew(user, TestUtil.createQuiz());
        Quiz quiz = quizService.getAll().get(0);

        int likes = 0, dislikes = 0;
        for (int i = 0; i < count; i++) {
            boolean liked = i % 2 == 0;
            if (liked) likes++; else dislikes++;
            quizService.rate(user, quiz.getId(), liked);
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

    private GameResult boostResult(int multiplicator) {
        GameResult result = TestUtil.createGameResult();
        result.setPowerUps(result.getPowerUps() * multiplicator);
        result.setRound(result.getRound() * multiplicator);
        result.setRoundOneRatio(result.getRoundOneRatio() * multiplicator);
        result.setRoundTwoRatio(result.getRoundTwoRatio() * multiplicator);
        result.setRoundThreeRatio(result.getRoundThreeRatio() * multiplicator);
        result.setScore(result.getScore() * multiplicator);

        return result;
    }

}
