package org.zezutom.capstone.service;

import com.google.appengine.api.users.User;
import org.zezutom.capstone.model.Difficulty;
import org.zezutom.capstone.model.GameSet;

import java.util.List;
import java.util.Map;

/**
 * Created by tom on 17/10/2014.
 */
public interface GameApi {

    /**
     * Returns a random selection comprising the given number of game sets,
     * all of them being of the same level of difficulty.
     *
     * @param count
     * @param difficulty
     * @return  randomized game sets
     */
    List<GameSet> getRandomGameSetsByDifficulty(int count, Difficulty difficulty);

    /**
     * Allows for a fine grained random selection. Returns randomized game sets
     * having a various difficulties.
     *
     * @param criteria  difficulty quotas, ex.: 2 EASY, 3 AVERAGE ones and 1 TOUGH
     * @return  randomized game sets
     */
    List<GameSet> getRandomGameSetsByCriteria(Map<Difficulty, Integer> criteria);

    void rate(User user, Long gameSetId, Double rating);

    void score(User user, Integer points);

    GameSet addGameSet(User user, GameSet gameSet);

}
