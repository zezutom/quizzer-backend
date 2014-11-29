package org.zezutom.capstone.service;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zezutom.capstone.dao.GameResultRepository;
import org.zezutom.capstone.dao.PlayoffResultRepository;
import org.zezutom.capstone.model.GameResult;
import org.zezutom.capstone.model.PlayoffResult;
import org.zezutom.capstone.util.AppUtil;
import org.zezutom.capstone.util.Ids;
import org.zezutom.capstone.util.Scopes;

import static com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID;

@Transactional
@Service
@Api(name = "gameService", namespace = @ApiNamespace(ownerDomain = "org.zezutom", ownerName = "org.zezutom"),
        version = AppUtil.API_VERSION,
        clientIds = {Ids.WEB, Ids.ANDROID, API_EXPLORER_CLIENT_ID},
        audiences = {Ids.WEB, Ids.ANDROID},
        scopes = {Scopes.EMAIL, Scopes.PROFILE})
public class GameServiceImpl extends GAEService implements GameService {

    @Autowired
    private GameResultRepository gameResultRepository;

    @Autowired
    private PlayoffResultRepository playoffResultRepository;

    @Override
    public GameResult saveGameResult(User user,
                                     @Named("round") int round,
                                     @Named("score") int score,
                                     @Named("powerUps") int powerUps,
                                     @Named("oneTimeAttempts") int oneTimeAttempts,
                                     @Named("twoTimeAttempts") int twoTimeAttempts) {
        GameResult gameResult = new GameResult();
        gameResult.setRound(round);
        gameResult.setScore(score);
        gameResult.setPowerUps(powerUps);
        gameResult.setAttemptOneRatio(getRatio(round, oneTimeAttempts));
        gameResult.setAttemptTwoRatio(getRatio(round, twoTimeAttempts));
        gameResult.setAttemptThreeRatio(getRatio(round, round - oneTimeAttempts - twoTimeAttempts));

        return gameResultRepository.save(gameResult);
    }

    @Override
    public PlayoffResult savePlayoffResult(User user,
                                           @Named("opponentId") String opponentId,
                                           @Named("round")int round,
                                           @Named("win") boolean win) {
        PlayoffResult playoffResult = new PlayoffResult();
        playoffResult.setOpponentId(opponentId);
        playoffResult.setRound(round);
        playoffResult.setWin(win);

        return playoffResultRepository.save(playoffResult);
    }

    private int getRatio(int round, int attempts) {
        return (int) ((attempts * 100.0f) / round);
    }
}
