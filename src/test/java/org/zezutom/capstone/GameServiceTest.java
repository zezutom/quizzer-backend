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
import org.zezutom.capstone.domain.GameResult;
import org.zezutom.capstone.domain.PlayoffResult;
import org.zezutom.capstone.service.GameService;
import org.zezutom.capstone.util.AppUtil;

import java.util.List;

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
        // Save a result of a single game
        final User user = TestUtil.createUser();
        final GameResult gameResult = TestUtil.createGameResult();
        gameService.saveGameResult(user, gameResult);

        // Verify the result has been correctly saved and is associated with the expected user
        final List<GameResult> gameResults = gameResultRepository.findByUserId(AppUtil.getUserId());
        TestUtil.assertEntities(1, gameResults);
        TestUtil.assertGameResult(gameResults.get(0), gameResult);
    }

    @Test
    public void savePlayoffResult() {
        // Save a result of a play-off match
        final User user = TestUtil.createUser();
        final String opponent = "Test Opponent";
        final PlayoffResult playoffResult = TestUtil.createPlayoffResult(opponent);
        gameService.savePlayoffResult(user, playoffResult);

        // Verify the result has been correctly saved and is associated with the expected user
        final List<PlayoffResult> playoffResults = playoffResultRepository.findByUserId(AppUtil.getUserId());
        TestUtil.assertEntities(1, playoffResults);
        TestUtil.assertPlayOffResult(playoffResults.get(0), playoffResult);
    }
}
