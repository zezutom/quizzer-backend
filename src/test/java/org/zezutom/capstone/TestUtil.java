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

    public static PlayoffResult createPlayoffResult(String opponentId, int round, boolean win) {
        PlayoffResult playoffResult = new PlayoffResult();
        playoffResult.setOpponentId(opponentId);
        playoffResult.setRound(round);
        playoffResult.setWin(win);
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
        userStats.setAttemptOneRatio(25);
        userStats.setAttemptTwoRatio(35);
        userStats.setAttemptThreeRatio(40);
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
        assertTrue(actual.getAttemptOneRatio() == expected.getAttemptOneRatio());
        assertTrue(actual.getAttemptTwoRatio() == expected.getAttemptTwoRatio());
        assertTrue(actual.getAttemptThreeRatio() == expected.getAttemptThreeRatio());
        assertTrue(actual.getScore() == expected.getScore());

        // sanity checks
        assertThat(actual.getAttemptOneRatio() + actual.getAttemptTwoRatio() + actual.getAttemptThreeRatio(), is(100));
        assertTrue(actual.getScore() >= actual.getRound());
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
