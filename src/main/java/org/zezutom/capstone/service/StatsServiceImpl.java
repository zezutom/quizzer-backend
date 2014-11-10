package org.zezutom.capstone.service;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zezutom.capstone.dao.GameResultRepository;
import org.zezutom.capstone.dao.PlayoffResultRepository;
import org.zezutom.capstone.dao.QuizRatingRepository;
import org.zezutom.capstone.dao.UserStatsRepository;
import org.zezutom.capstone.domain.GameResult;
import org.zezutom.capstone.domain.PlayoffResult;
import org.zezutom.capstone.domain.QuizRating;
import org.zezutom.capstone.domain.UserStats;
import org.zezutom.capstone.util.AppUtil;
import org.zezutom.capstone.util.Ids;
import org.zezutom.capstone.util.Scopes;

import java.util.List;

import static com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID;

@Service
@Api(name = "statsService", namespace = @ApiNamespace(ownerDomain = "org.zezutom", ownerName = "org.zezutom"),
        version = AppUtil.API_VERSION,
        clientIds = {Ids.WEB, Ids.ANDROID, API_EXPLORER_CLIENT_ID},
        audiences = {Ids.WEB, Ids.ANDROID},
        scopes = {Scopes.EMAIL, Scopes.PROFILE})
public class StatsServiceImpl implements StatsService {

    @Autowired
    private UserStatsRepository userStatsRepository;

    @Autowired
    private GameResultRepository gameResultRepository;

    @Autowired
    private PlayoffResultRepository playoffResultRepository;

    @Autowired
    private QuizRatingRepository quizRatingRepository;

    @Override
    @ApiMethod(path = "userstats/get", httpMethod = ApiMethod.HttpMethod.GET)
    public UserStats getUserStats(User user) {
        return userStatsRepository.findByUsername(AppUtil.getUsername());
    }

    @Override
    @ApiMethod(path = "singlegame/history", httpMethod = ApiMethod.HttpMethod.GET)
    public List<GameResult> getSingleGameHistory(User user) {
        return gameResultRepository.findByUsername(AppUtil.getUsername());
    }

    @Override
    @ApiMethod(path = "playoff/history", httpMethod = ApiMethod.HttpMethod.GET)
    public List<PlayoffResult> getPlayoffHistory(User user) {
        return playoffResultRepository.findByUsername(AppUtil.getUsername());
    }

    @Override
    @ApiMethod(path = "quizrating/list", httpMethod = ApiMethod.HttpMethod.GET)
    public List<QuizRating> getQuizRatings() {
        return quizRatingRepository.findAll();
    }
}
