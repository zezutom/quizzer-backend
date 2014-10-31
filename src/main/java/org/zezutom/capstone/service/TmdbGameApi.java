package org.zezutom.capstone.service;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;
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
import org.zezutom.capstone.util.Ids;
import org.zezutom.capstone.util.Scopes;

import java.util.List;

import static com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID;

@Service
@Api(name = "game", namespace = @ApiNamespace(ownerDomain = "org.zezutom", ownerName = "org.zezutom"),
        version = AppUtil.API_VERSION,
        clientIds = {Ids.WEB, Ids.ANDROID, API_EXPLORER_CLIENT_ID},
        audiences = {Ids.WEB, Ids.ANDROID},
        scopes = {Scopes.EMAIL, Scopes.PROFILE})
public class TmdbGameApi implements GameApi {

    @Autowired
    private GameSetRepository gameSetRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    public TmdbGameApi() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    public GameSet getNextGameSet() {
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

    // TODO deleteme
    public Score getFakeScore(User user) {
        if (user == null)
            return new Score(0, "anonymous");
        else
            return new Score(100, user.getEmail());
    }
}
