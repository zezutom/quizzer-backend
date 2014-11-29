package org.zezutom.capstone.service;

import com.google.appengine.api.users.User;
import org.zezutom.capstone.model.GameResult;
import org.zezutom.capstone.model.PlayoffResult;

/**
 * Allows for key game operations, such as saveGameResult a finished game or challenge a friend etc.
 */
public interface GameService {

    /**
     * Saves the result of a single-user game. Requires authentication.
     *
     * @param user                  User authentication
     * @param round                 Achieved round
     * @param score                 Total score
     * @param powerUps              Earned power-ups
     * @param oneTimeAttempts       The number of first time attempts
     * @param twoTimeAttempts       The number of second time attempts
     *
     * @return                      Game result (score, last round etc.)
     */
    GameResult saveGameResult(User user, int round, int score, int powerUps, int oneTimeAttempts, int twoTimeAttempts);

    /**
     * Saves the result of a challenge. Requires authentication.
     *
     * @param user          User authentication
     * @param playoffResult Game result (if the user won or lost, who was the opponent etc.)
     */


    /**
     * Saves the result of a challenge. Requires authentication.
     *
     * @param user          User authentication
     * @param opponentId    Opponent's user id
     * @param round         Achieved round
     * @param win           The result - true (win), false (loss)
     *
     * @return              Playoff result
     */
    PlayoffResult savePlayoffResult(User user, String opponentId, int round, boolean win);
}
