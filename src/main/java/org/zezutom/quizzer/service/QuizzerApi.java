package org.zezutom.quizzer.service;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zezutom.quizzer.dao.GameResultRepository;
import org.zezutom.quizzer.dao.QuizRatingRepository;
import org.zezutom.quizzer.dao.QuizRepository;
import org.zezutom.quizzer.model.*;
import org.zezutom.quizzer.util.AppUtil;
import org.zezutom.quizzer.util.Ids;
import org.zezutom.quizzer.util.Scopes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

import static com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID;

@Transactional
@Service
@Api(name = "quizzer", namespace = @ApiNamespace(ownerDomain = "org.zezutom", ownerName = "org.zezutom"),
        version = AppUtil.API_VERSION,
        clientIds = {Ids.WEB, Ids.ANDROID, API_EXPLORER_CLIENT_ID},
        audiences = {Ids.WEB, Ids.ANDROID},
        scopes = {Scopes.EMAIL, Scopes.PROFILE})
public class QuizzerApi extends GAEService implements QuizzerService {

    @Autowired
    private GameResultRepository gameResultRepository;

    @PersistenceContext(unitName = "jpa.unit")
    private EntityManager em;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizRatingRepository quizRatingRepository;

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
    @ApiMethod(path = "quiz/get/all", httpMethod = ApiMethod.HttpMethod.GET)
    public List<Quiz> getQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    @ApiMethod(path = "quiz/get/criteria", httpMethod = ApiMethod.HttpMethod.GET)
    public List<Quiz> getQuizzesByCriteria(QuizSelectionCriteria criteria) {

        final String queryString = "select q from Quiz q where q.difficulty in " +
                inEnum(criteria.getDifficultyLevels()) +
                " and q.category in " + inEnum(criteria.getCategories()) + " order by q.question";
        Query query = em.createQuery(queryString);

        return query.getResultList();
    }

    @Override
    @ApiMethod(path = "quiz/add", httpMethod = ApiMethod.HttpMethod.POST)
    public Quiz addNewQuiz(User user, Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    @ApiMethod(path = "quiz/rate", httpMethod = ApiMethod.HttpMethod.POST)
    public QuizRating rateQuiz(User user, @Named("quizId") String quizId, @Named("liked") boolean liked) {
        QuizRating rating = new QuizRating();
        rating.setQuizId(quizId);
        rating.setLiked(liked);
        return quizRatingRepository.save(rating);
    }

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
        stats.setScore(score);                          // the best score ever
        stats.setRound(round);                          // the furthest round
        stats.setPowerUps(powerUps);                    // the highest number of accumulated power-ups
        stats.setAttemptOneRatio(roundOneRatio);        // the highest percentage of 1st-time attempts
        stats.setAttemptTwoRatio(roundTwoRatio);        // the highest percentage of 2nd-time attempts
        stats.setAttemptThreeRatio(roundThreeRatio);    // the highest percentage of 3rd-time attempts

        return stats;
    }

    @Override
    @ApiMethod(path = "game/result/list", httpMethod = ApiMethod.HttpMethod.GET)
    public List<GameResult> getGameResults(User user) {
        return gameResultRepository.findByUserId(AppUtil.getUserId());
    }

    @Override
    @ApiMethod(path = "quiz/rating/list", httpMethod = ApiMethod.HttpMethod.GET)
    public List<QuizRating> getQuizRatings(@Named("quizId") String quizId) {
        return quizRatingRepository.findByQuizId(quizId);
    }

    @Override
    @ApiMethod(path = "quz/rating/stats/list", httpMethod = ApiMethod.HttpMethod.GET)
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

    private int getRatio(int round, int attempts) {
        return (int) ((attempts * 100.0f) / round);
    }

    private<T extends Enum> String inEnum(List<T> enumList) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (T item : enumList) {
            sb.append("'").append(item.name()).append("',");
        }
        sb.setLength(sb.length() - 1);
        sb.append(")");

        return sb.toString();
    }
}
