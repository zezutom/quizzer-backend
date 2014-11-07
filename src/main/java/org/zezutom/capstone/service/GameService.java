package org.zezutom.capstone.service;

import com.google.appengine.api.users.User;
import org.zezutom.capstone.domain.GameResult;
import org.zezutom.capstone.domain.PlayoffResult;

/**
 * Allows for key game operations, such as saveSingleGame a finished game or challenge a friend etc.
 */
public interface GameService {

    /**
     * Saves the result of a single-user game. Requires authentication.
     *
     * @param user          User authentication
     * @param gameResult    Game result (score, last round etc.)
     */
    void saveSingleGame(User user, GameResult gameResult);

    /**
     * Saves the result of a challenge. Requires authentication.
     *
     * @param user          User authentication
     * @param playoffResult Game result (if the user won or lost, who was the opponent etc.)
     */
    void savePlayoff(User user, PlayoffResult playoffResult);
}
