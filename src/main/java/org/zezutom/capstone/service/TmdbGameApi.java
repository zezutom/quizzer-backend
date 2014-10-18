package org.zezutom.capstone.service;

import com.google.api.server.spi.config.Api;
import com.google.appengine.api.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zezutom.capstone.dao.GameSetRepository;
import org.zezutom.capstone.dao.ScoreRepository;
import org.zezutom.capstone.model.GameSet;
import org.zezutom.capstone.model.Rating;
import org.zezutom.capstone.model.Score;
import org.zezutom.capstone.util.AppUtil;

@Service
@Api(name = "game",
        version = AppUtil.API_VERSION,
        scopes = {AppUtil.EMAIL_SCOPE},
        clientIds = {AppUtil.ANDROID_CLIENT_ID},
        audiences = {AppUtil.ANDROID_AUDIENCE})
public class TmdbGameApi implements GameApi {

    @Autowired
    private GameSetRepository gameSetRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Override
    public GameSet play() {
        return gameSetRepository.findOne(AppUtil.randomLong(gameSetRepository.count()));
    }

    @Transactional
    @Override
    public void rate(User user, Long gameSetId, Double rating) {
        GameSet gameSet = gameSetRepository.findOne(gameSetId);
        gameSet.addRating(new Rating(rating, (user == null) ? null : user.getUserId()));
        gameSetRepository.save(gameSet);
    }

    @Transactional
    @Override
    public void score(User user, Integer points) {
        Score score = new Score(points, user.getUserId());
        scoreRepository.save(score);
    }
}
