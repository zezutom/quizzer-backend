package org.zezutom.capstone.service.impl;

import org.springframework.stereotype.Service;
import org.zezutom.capstone.model.GameSet;
import org.zezutom.capstone.model.Solution;
import org.zezutom.capstone.service.GameService;

/**
 * Created by tom on 05/10/2014.
 */
@Service
public class DefaultGameService implements GameService {

    @Override
    public GameSet next() {
        return null;
    }

    @Override
    public boolean guess(long gameSetId) {
        return false;
    }

    @Override
    public Solution showSolution(long gameSetId) {
        return null;
    }

    @Override
    public void rate(long gameSetId) {

    }

    @Override
    public boolean isGameOver() {
        return false;
    }
}
