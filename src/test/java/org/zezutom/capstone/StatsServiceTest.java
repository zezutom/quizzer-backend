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
import org.zezutom.capstone.dao.QuizRatingRepository;
import org.zezutom.capstone.domain.*;
import org.zezutom.capstone.service.GameService;
import org.zezutom.capstone.service.QuizService;
import org.zezutom.capstone.service.StatsService;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-servlet.xml")
public class StatsServiceTest {

    @Autowired
    private StatsService statsService;

    @Autowired
    private GameService gameService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizRatingRepository quizRatingRepository;

    private final LocalServiceTestHelper helper = TestUtil.getDatastoreTestHelper();

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void getUserStats_firstTime() {

        // Saving a new game should result into a new stats record
        final User user = TestUtil.createUser();
        final GameResult gameResult = TestUtil.createGameResult();
        gameService.saveSingleGame(user, gameResult);

        // Verify that saved stats can be correctly retrieved
        final UserStats userStats = statsService.getUserStats(user);
        TestUtil.assertEntity(userStats);
        assertUserStats(userStats, gameResult);
    }

    @Test
    public void getUserStats_newRecord() {

        // Save a new game
        final User user = TestUtil.createUser();
        final GameResult gameResult = TestUtil.createGameResult();
        gameService.saveSingleGame(user, gameResult);

        // Retrieve the stats
        final UserStats userStats = statsService.getUserStats(user);

        // Play another game and achieve a better score
        final GameResult gameResultWithABetterScore = TestUtil.createGameResult();
        gameResultWithABetterScore.setScore(gameResult.getScore() + 1);
        gameService.saveSingleGame(user, gameResultWithABetterScore);

        // Verify the stats were updated with the new score
        final UserStats userStatsWithAnIncreasedScore = statsService.getUserStats(user);
        assertTrue(userStatsWithAnIncreasedScore.getScore() == gameResultWithABetterScore.getScore());
    }

    @Test
    public void getUserStats_noUpdate() {

        // Save a new game
        final User user = TestUtil.createUser();
        final GameResult gameResult = TestUtil.createGameResult();
        gameService.saveSingleGame(user, gameResult);

        // Retrieve the stats
        final UserStats userStats = statsService.getUserStats(user);

        // Play another game and achieve a worse score than before
        final GameResult gameResultWithAWorseScore = TestUtil.createGameResult();
        gameResultWithAWorseScore.setScore(gameResult.getScore() - 1);
        gameService.saveSingleGame(user, gameResultWithAWorseScore);

        // Verify the stats retain the original values
        final UserStats userStatsWithAnUnchangedScore = statsService.getUserStats(user);
        assertTrue(userStatsWithAnUnchangedScore.getScore() == gameResult.getScore());
    }


    @Test
    public void getSingleGameHistory() {
        // Let's play to build up a bit of a gaming history
        final User user = TestUtil.createUser();

        final int count = 10;

        for (int i = 0; i < count; i++)
            gameService.saveSingleGame(user, TestUtil.createGameResult());

        // Verify that all of the saved results can be correctly retrieved
        final List<GameResult> gameResults = statsService.getSingleGameHistory(user);
        TestUtil.assertEntities(count, gameResults);
        final GameResult expected = TestUtil.createGameResult();
        for (GameResult gameResult : gameResults)
            TestUtil.assertGameResult(gameResult, expected);
    }

    @Test
    public void getPlayoffHistory() {

        // Let's challenge a friend a few times to come up with a playoff result list
        final User user = TestUtil.createUser();

        final int count = 10;

        final String opponent = "test";

        for (int i = 0; i < count; i++)
            gameService.savePlayoff(user, TestUtil.createPlayoffResult(opponent));

        // Verify that all of the saved results can be correctly retrieved
        final List<PlayoffResult> playoffResults = statsService.getPlayoffHistory(user);
        TestUtil.assertEntities(count, playoffResults);
        final PlayoffResult expected = TestUtil.createPlayoffResult(opponent);
        for (PlayoffResult playoffResult : playoffResults)
            TestUtil.assertPlayOffResult(playoffResult, expected);

    }

    @Test
    public void getQuizRatings() {

        // Create a new quiz
        final User user = TestUtil.createUser();
        final Quiz quiz = TestUtil.createQuiz();
        quizService.addNew(user, quiz);

        // Capture initial counts
        final int upVotes = quiz.getUpVotes();
        final int downVotes = quiz.getDownVotes();

        // Like the quiz
        quizService.rate(user, quiz.getId(), true);
        assertQuizRating(quizRatingRepository.findOneByQuizId(quiz.getId()), upVotes + 1, downVotes);

        // Dislike the quiz
        quizService.rate(user, quiz.getId(), false);
        assertQuizRating(quizRatingRepository.findOneByQuizId(quiz.getId()), upVotes + 1, downVotes + 1);
    }

    private void assertQuizRating(QuizRating quizRating, int expectedUpVotes, int expectedDownVotes) {
        TestUtil.assertEntity(quizRating);
        assertTrue(quizRating.getRatingCount() == expectedUpVotes + expectedDownVotes);
        assertTrue(quizRating.getUpVotes() == expectedUpVotes);
        assertTrue(quizRating.getDownVotes() == expectedDownVotes);
    }

    private void assertUserStats(UserStats actual, GameResult expected) {
        assertTrue(actual.getPowerUps() == expected.getPowerUps());
        assertTrue(actual.getRound() == expected.getRound());
        assertTrue(actual.getRoundOneRatio() == expected.getRoundOneRatio());
        assertTrue(actual.getRoundTwoRatio() == expected.getRoundTwoRatio());
        assertTrue(actual.getRoundThreeRatio() == expected.getRoundThreeRatio());
        assertTrue(actual.getScore() == expected.getScore());
    }
}
