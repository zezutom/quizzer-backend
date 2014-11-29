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
import org.zezutom.capstone.dao.GameResultRepository;
import org.zezutom.capstone.dao.PlayoffResultRepository;
import org.zezutom.capstone.model.GameResult;
import org.zezutom.capstone.model.PlayoffResult;
import org.zezutom.capstone.service.GameService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-servlet.xml")
public class GameServiceTest {

    @Autowired
    private GameService gameService;

    @Autowired
    private GameResultRepository gameResultRepository;

    @Autowired
    private PlayoffResultRepository playoffResultRepository;

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
    public void saveGameResult() {
        final User user = TestUtil.createUser();

        final int oneTimeAttempts = 4;
        final int twoTimeAttempts = 2;

        GameResult expected = new GameResultBuilder()
                                .setRound(10)
                                .setOneTimeAttempts(oneTimeAttempts)
                                .setOneTimeConsecutiveAttempts(oneTimeAttempts / 2)
                                .setTwoTimeAttempts(twoTimeAttempts)
                                .build();
        GameResult actual = gameService.saveGameResult(user,
                expected.getRound(), expected.getScore(), expected.getPowerUps(), oneTimeAttempts, twoTimeAttempts);

        TestUtil.assertGameResult(actual, expected);
    }

    @Test
    public void savePlayoffResult() {
        // Save a result of a play-off match
        final User user = TestUtil.createUser();

        // Playoff
        final String opponentId = "Test Opponent";
        final int round = 10;
        final boolean win = true;

        final PlayoffResult playoffResult = TestUtil.createPlayoffResult(opponentId, round, win);
        TestUtil.assertPlayOffResult(gameService.savePlayoffResult(user, opponentId, round, win), playoffResult);
    }
}
