package org.zezutom.capstone;

import com.google.appengine.api.users.User;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.zezutom.capstone.model.*;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

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

    public static PlayoffResult createPlayoffResult(String opponentId) {
        PlayoffResult playoffResult = new PlayoffResult();
        playoffResult.setOpponentId(opponentId);
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

    public static GameResultStats createUserStats(User user) {
        GameResultStats userStats = new GameResultStats();
        userStats.setUserId(user.getUserId());
        userStats.setPowerUps(10);
        userStats.setRound(10);
        userStats.setRoundOneRatio(25);
        userStats.setRoundTwoRatio(35);
        userStats.setRoundThreeRatio(40);
        return userStats;
    }

    public static<T extends GenericEntity> void assertEntities(int expectedSize, List<T> entities) {
        assertNotNull(entities);
        assertTrue(entities.size() == expectedSize);
        for (T entity : entities) {
            assertEntity(entity);
        }

    }

    public static<T extends GenericEntity> void assertEntity(T entity) {
        assertNotNull(entity);
        assertNotNull(entity.getId());
        assertNotNull(entity.getVersion());
        assertNotNull(entity.getCreatedAt());
        assertNotNull(entity.getUserId());
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
        assertThat(actual.getOpponentId(), is(expected.getOpponentId()));
        assertTrue(actual.getRound() == expected.getRound());
    }

    public static LocalServiceTestHelper getDatastoreTestHelper() {
        return new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig().setApplyAllHighRepJobPolicy());
    }

    public static void login() {
        User user = createUser();
        TestingAuthenticationToken token = new TestingAuthenticationToken(user, null);
        OAuth2Request req = new OAuth2Request(null, user.getEmail(), null, true, null, null, null, null, null);
        OAuth2Authentication auth = new OAuth2Authentication(req, token);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public static void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
