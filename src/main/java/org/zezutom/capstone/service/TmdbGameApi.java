package org.zezutom.capstone.service;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.zezutom.capstone.dao.GameSetRepository;
import org.zezutom.capstone.dao.ScoreRepository;
import org.zezutom.capstone.model.GameSet;
import org.zezutom.capstone.model.Rating;
import org.zezutom.capstone.model.Score;
import org.zezutom.capstone.util.AppUtil;

import java.util.List;

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

    public TmdbGameApi() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    public GameSet play() {
        final List<GameSet> gameSets = gameSetRepository.findAll();
        if (gameSets == null || gameSets.isEmpty()) return null;
        return gameSets.get(AppUtil.randomInt(gameSets.size()));
    }

    @Transactional
    @Override
    public GameSet addGameSet(User user, GameSet gameSet) {
        gameSet.setAuthor(AppUtil.getUsername(user));
        return gameSetRepository.saveAndFlush(gameSet);
    }

    @Transactional
    @Override
    public void rate(User user, @Named("gameSetId") Long gameSetId, @Named("rating") Double rating) {
        GameSet gameSet = gameSetRepository.findOne(gameSetId);
        gameSet.addRating(new Rating(rating, AppUtil.getUsername(user)));
        gameSetRepository.save(gameSet);
    }

    @Transactional
    @Override
    public void score(User user, @Named("points") Integer points) {
        Score score = new Score(points, user.getUserId());
        scoreRepository.save(score);
    }
}
