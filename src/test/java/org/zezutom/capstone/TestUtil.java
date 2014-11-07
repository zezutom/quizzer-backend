package org.zezutom.capstone;

import com.google.appengine.api.users.User;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.zezutom.capstone.domain.*;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;

public class TestUtil {

    private TestUtil() {}

    public static User createUser() {
        return new User("test@test.com", "test");
    }

    public static GameResult createGameResult() {
        GameResult gameResult = new GameResult();
        gameResult.setPowerUps(10);
        gameResult.setRound(10);
        gameResult.setRoundOneRatio(10);
        gameResult.setRoundTwoRatio(10);
        gameResult.setRoundThreeRatio(10);
        gameResult.setScore(150);
        return gameResult;
    }

    public static PlayoffResult createPlayoffResult(String opponent) {
        PlayoffResult playoffResult = new PlayoffResult();
        playoffResult.setOpponent(opponent);
        playoffResult.setRound(10);
        playoffResult.setWin(true);
        return playoffResult;
    }

    public static Quiz createQuiz() {
        Quiz quiz = new Quiz();
        quiz.setAnswer(1);
        quiz.setDifficulty(3);
        quiz.setExplanation("test");
        quiz.setMovieOne("Movie One");
        quiz.setMovieTwo("Movie Two");
        quiz.setMovieThree("Movie Three");
        quiz.setMovieFour("Movie Four");
        return quiz;
    }

    public static UserStats createUserStats(User user) {
        UserStats userStats = new UserStats();
        userStats.setUsername(user.getUserId());
        userStats.setPowerUps(10);
        userStats.setRound(10);
        userStats.setRoundOneRatio(25);
        userStats.setRoundTwoRatio(35);
        userStats.setRoundThreeRatio(40);
        return userStats;
    }

    public static<T extends AuditableEntity> void assertEntities(int expectedSize, List<T> entities) {
        assertNotNull(entities);
        assertTrue(entities.size() == expectedSize);
        for (T entity : entities) {
            assertEntity(entity);
        }

    }

    public static<T extends AuditableEntity> void assertEntity(T entity) {
        assertNotNull(entity);
        assertNotNull(entity.getId());
        assertNotNull(entity.getVersion());
        assertNotNull(entity.getCreatedAt());
        assertNotNull(entity.getUsername());
    }

    public static void assertGameResult(GameResult actual, GameResult expected) {
        assertTrue(actual.getPowerUps() == expected.getPowerUps());
        assertTrue(actual.getRound() == expected.getRound());
        assertTrue(actual.getRoundOneRatio() == expected.getRoundOneRatio());
        assertTrue(actual.getRoundTwoRatio() == expected.getRoundTwoRatio());
        assertTrue(actual.getRoundThreeRatio() == expected.getRoundThreeRatio());
        assertTrue(actual.getScore() == expected.getScore());
    }

    public static void assertPlayOffResult(PlayoffResult actual, PlayoffResult expected) {
        assertThat(actual.getOpponent(), is(expected.getOpponent()));
        assertTrue(actual.getRound() == expected.getRound());
    }

    public static LocalServiceTestHelper getDatastoreTestHelper() {
        return new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig().setApplyAllHighRepJobPolicy());
    }


}