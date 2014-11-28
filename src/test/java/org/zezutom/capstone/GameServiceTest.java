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
        // Save a result of a single game
        final User user = TestUtil.createUser();
        final GameResult gameResult = TestUtil.createGameResult();
        TestUtil.assertGameResult(gameService.saveGameResult(user, gameResult), gameResult);
    }

    @Test
    public void savePlayoffResult() {
        // Save a result of a play-off match
        final User user = TestUtil.createUser();
        final String opponent = "Test Opponent";
        final PlayoffResult playoffResult = TestUtil.createPlayoffResult(opponent);
        TestUtil.assertPlayOffResult(gameService.savePlayoffResult(user, playoffResult), playoffResult);
    }
}
