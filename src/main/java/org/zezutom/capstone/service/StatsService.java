package org.zezutom.capstone.service;

import com.google.appengine.api.users.User;
import org.zezutom.capstone.domain.*;

import java.util.List;

/**
 * Lists all kinds of stats (user's best scores, play-off table etc.)
 */
public interface StatsService {

    GameResultStats getGameResultStats(User user);

    List<GameResult> getGameResults(User user);

    List<PlayoffResult> getPlayoffResults(User user);

    /**
     * List of individual quiz ratings
     *
     * @param quizId
     * @return user ratings or an empty list if no one has rated the quiz so far
     */
    List<QuizRating> getQuizRatings(String quizId);

    /**
     * Rating summaries. Each and every quiz has a single summary.
     * So this encompasses all quizzes in the system.
     *
     * @return
     */
    List<QuizRatingStats> getQuizRatingStats();
}
