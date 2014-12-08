package org.zezutom.capstone.service;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zezutom.capstone.dao.GameResultRepository;
import org.zezutom.capstone.dao.PlayoffResultRepository;
import org.zezutom.capstone.dao.QuizRatingRepository;
import org.zezutom.capstone.dao.QuizRepository;
import org.zezutom.capstone.model.*;
import org.zezutom.capstone.util.AppUtil;
import org.zezutom.capstone.util.Ids;
import org.zezutom.capstone.util.Scopes;

import java.util.*;

import static com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID;

@Service
@Api(name = "statsService", namespace = @ApiNamespace(ownerDomain = "org.zezutom", ownerName = "org.zezutom"),
        version = AppUtil.API_VERSION,
        clientIds = {Ids.WEB, Ids.ANDROID, API_EXPLORER_CLIENT_ID},
        audiences = {Ids.WEB, Ids.ANDROID},
        scopes = {Scopes.EMAIL, Scopes.PROFILE})
public class StatsServiceImpl extends GAEService implements StatsService {


    @Autowired
    private GameResultRepository gameResultRepository;

    @Autowired
    private PlayoffResultRepository playoffResultRepository;

    @Autowired
    private QuizRatingRepository quizRatingRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Override
    @ApiMethod(path = "game/result/stats/get", httpMethod = ApiMethod.HttpMethod.GET)
    public GameResultStats getGameResultStats(User user) {
        int score, round, powerUps, roundOneRatio, roundTwoRatio, roundThreeRatio;
        score = round = powerUps = roundOneRatio = roundTwoRatio = roundThreeRatio = 0;

        for (GameResult gameResult : getGameResults(user)) {
            if (gameResult.getScore() > score) score = gameResult.getScore();
            if (gameResult.getRound() > round) round = gameResult.getRound();
            if (gameResult.getPowerUps() > powerUps) powerUps = gameResult.getPowerUps();
            if (gameResult.getAttemptOneRatio() > roundOneRatio) roundOneRatio = gameResult.getAttemptOneRatio();
            if (gameResult.getAttemptTwoRatio() > roundTwoRatio) roundTwoRatio = gameResult.getAttemptTwoRatio();
            if (gameResult.getAttemptThreeRatio() > roundThreeRatio) roundThreeRatio = gameResult.getAttemptThreeRatio();
        }

        GameResultStats stats = new GameResultStats();
        stats.setScore(score);                      // the best score ever
        stats.setRound(round);                      // the furthest round
        stats.setPowerUps(powerUps);                // the highest number of accumulated power-ups
        stats.setAttemptOneRatio(roundOneRatio);      // the highest percentage of 1st-time attempts
        stats.setAttemptTwoRatio(roundTwoRatio);      // the highest percentage of 2nd-time attempts
        stats.setAttemptThreeRatio(roundThreeRatio);  // the highest percentage of 3rd-time attempts

        return stats;
    }

    @Override
    @ApiMethod(path = "game/result/list", httpMethod = ApiMethod.HttpMethod.GET)
    public List<GameResult> getGameResults(User user) {
        return gameResultRepository.findByUserId(AppUtil.getUserId());
    }

    @Override
    @ApiMethod(path = "playoff/list", httpMethod = ApiMethod.HttpMethod.GET)
    public List<PlayoffResult> getPlayoffResults(User user) {
        return playoffResultRepository.findByUserId(AppUtil.getUserId());
    }

    @Override
    @ApiMethod(path = "quiz/rating/list", httpMethod = ApiMethod.HttpMethod.GET)
    public List<QuizRating> getQuizRatings(@Named("quizId") String quizId) {
        return quizRatingRepository.findByQuizId(quizId);
    }

    @Override
    public List<QuizRatingStats> getQuizRatingStats() {
        Map<String, QuizRatingStats> statsMap = new HashMap<>();

        for (QuizRating quizRating : quizRatingRepository.findAll()) {
            String quizId = quizRating.getQuizId();
            QuizRatingStats stats = statsMap.get(quizId);

            if (stats == null) {
                Quiz quiz = quizRepository.findOne(quizId);
                stats = new QuizRatingStats();
                stats.setQuizId(quizId);
                stats.setTitle(quiz.getQuestion());
                stats.setRatingCount(1);
                stats.setDownVotes(quizRating.isLiked() ? 0 : 1);
                stats.setUpVotes(quizRating.isLiked() ? 1 : 0);
                statsMap.put(quizId, stats);
            } else {
                stats.setRatingCount(stats.getRatingCount() + 1);
                if (quizRating.isLiked()) {
                    stats.setUpVotes(stats.getUpVotes() + 1);
                } else {
                    stats.setDownVotes(stats.getDownVotes() + 1);
                }
            }
        }

        ArrayList<QuizRatingStats> statsList = new ArrayList<>(statsMap.values());
        Collections.sort(statsList, new QuizRatingStatsComparator());

        return statsList;
    }
}
