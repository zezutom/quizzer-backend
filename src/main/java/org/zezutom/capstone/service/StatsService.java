package org.zezutom.capstone.service;

import com.google.appengine.api.users.User;
import org.zezutom.capstone.domain.GameResult;
import org.zezutom.capstone.domain.PlayoffResult;
import org.zezutom.capstone.domain.QuizRating;
import org.zezutom.capstone.domain.UserStats;

import java.util.List;

/**
 * Lists all kinds of stats (user's best scores, play-off table etc.)
 */
public interface StatsService {

    UserStats getUserStats(User user);

    List<GameResult> getSingleGameHistory(User user);

    List<PlayoffResult> getPlayoffHistory(User user);

    List<QuizRating> getQuizRatings();
}
