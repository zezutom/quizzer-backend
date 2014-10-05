package org.zezutom.capstone.service;

import org.zezutom.capstone.model.GameSet;
import org.zezutom.capstone.model.Solution;

/**
 * Created by tom on 05/10/2014.
 */
public interface GameService {

    GameSet next();

    boolean guess(long gameSetId);

    Solution showSolution(long gameSetId);

    void rate(long gameSetId);

    boolean isGameOver();
}
