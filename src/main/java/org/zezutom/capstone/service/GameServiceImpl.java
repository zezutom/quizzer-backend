package org.zezutom.capstone.service;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zezutom.capstone.dao.GameResultRepository;
import org.zezutom.capstone.dao.PlayoffResultRepository;
import org.zezutom.capstone.dao.UserStatsRepository;
import org.zezutom.capstone.domain.GameResult;
import org.zezutom.capstone.domain.PlayoffResult;
import org.zezutom.capstone.domain.UserStats;
import org.zezutom.capstone.util.AppUtil;
import org.zezutom.capstone.util.Ids;
import org.zezutom.capstone.util.Scopes;

import static com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID;

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

    @Autowired
    private UserStatsRepository userStatsRepository;

    @Transactional
    @Override
    @ApiMethod(path = "singlegame/save", httpMethod = ApiMethod.HttpMethod.POST)
    public void saveSingleGame(User user, GameResult gameResult) {
        gameResultRepository.save(gameResult);

        UserStats userStats = userStatsRepository.findByUsername(AppUtil.getUsername());

        if (userStats == null)
            userStats = createUserStats(user, gameResult);
        else
            userStats = updateUserStats(userStats, gameResult);

        userStatsRepository.save(userStats);
    }

    @Transactional
    @Override
    @ApiMethod(path = "playoff/save", httpMethod = ApiMethod.HttpMethod.POST)
    public void savePlayoff(User user, PlayoffResult playoffResult) {
        playoffResultRepository.save(playoffResult);
    }

    private UserStats createUserStats(User user, GameResult gameResult) {
        UserStats userStats = new UserStats();
        userStats.setPowerUps(gameResult.getPowerUps());
        userStats.setRound(gameResult.getRound());
        userStats.setRoundOneRatio(gameResult.getRoundOneRatio());
        userStats.setRoundTwoRatio(gameResult.getRoundTwoRatio());
        userStats.setRoundThreeRatio(gameResult.getRoundThreeRatio());
        userStats.setScore(gameResult.getScore());

        return userStats;
    }

    private UserStats updateUserStats(UserStats userStats, GameResult gameResult) {

        final int powerUps =        gameResult.getPowerUps();
        final int round =           gameResult.getRound();
        final int roundOneRatio =   gameResult.getRoundOneRatio();
        final int roundTwoRatio =   gameResult.getRoundTwoRatio();
        final int roundThreeRatio = gameResult.getRoundThreeRatio();
        final int score =           gameResult.getScore();

        if (powerUps > userStats.getPowerUps()) userStats.setPowerUps(powerUps);
        if (round > userStats.getRound()) userStats.setRound(round);
        if (roundOneRatio > userStats.getRoundOneRatio()) userStats.setRoundOneRatio(roundOneRatio);
        if (roundTwoRatio > userStats.getRoundTwoRatio()) userStats.setRoundTwoRatio(roundTwoRatio);
        if (roundThreeRatio > userStats.getRoundThreeRatio()) userStats.setRoundThreeRatio(roundThreeRatio);
        if (score > userStats.getScore()) userStats.setScore(score);

        return userStats;
    }
}
