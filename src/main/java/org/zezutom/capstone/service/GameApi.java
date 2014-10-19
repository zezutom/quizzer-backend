package org.zezutom.capstone.service;

import com.google.appengine.api.users.User;
import org.zezutom.capstone.model.GameSet;

/**
 * Created by tom on 17/10/2014.
 */
public interface GameApi {

    GameSet getNextGameSet();

    void rate(User user, Long gameSetId, Double rating);

    void score(User user, Integer points);

    GameSet addGameSet(User user, GameSet gameSet);

}
