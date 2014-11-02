package org.zezutom.capstone.service;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.zezutom.capstone.dao.GameSetRepository;
import org.zezutom.capstone.dao.ScoreRepository;
import org.zezutom.capstone.model.Difficulty;
import org.zezutom.capstone.model.GameSet;
import org.zezutom.capstone.model.Rating;
import org.zezutom.capstone.model.Score;
import org.zezutom.capstone.util.AppUtil;
import org.zezutom.capstone.util.Ids;
import org.zezutom.capstone.util.Scopes;

import java.util.*;

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

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<GameSet> getNextFive() {
        final List<GameSet> gameSets = new ArrayList<>();
        final int max_loops = 20;
        int i = 0;

        while(gameSets.size() < 5 && i < max_loops) {
            int index = AppUtil.randomInt(Difficulty.values().length);
            gameSets.addAll(getByDifficulty(1, Difficulty.values()[index]));
            i++;
        }

        return gameSets;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<GameSet> getByDifficulty(@Named("count") int count, @Named("difficulty") Difficulty difficulty) {
        return randomize(count, gameSetRepository.findByDifficulty(difficulty));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<GameSet> getByCriteria(Map<Difficulty, Integer> criteria) {
        if (criteria == null || criteria.isEmpty()) return Collections.EMPTY_LIST;
        final List<GameSet> gameSets = new ArrayList<>();

        for (Difficulty key : criteria.keySet()) {
            gameSets.addAll(getByDifficulty(criteria.get(key), key));
        }

        return gameSets;
    }

    @Transactional
    @Override
    public GameSet addGameSet(User user, GameSet gameSet) {
        gameSet.setAuthor(AppUtil.getUsername(user));
        return gameSetRepository.saveAndFlush(gameSet);
    }

    @Transactional
    @Override
    public void rate(User user, @Named("gameSetId") String gameSetId, @Named("rating") Double rating) {
        GameSet gameSet = gameSetRepository.findOne(gameSetId);
        gameSet.addRating(new Rating(rating, AppUtil.getUsername(user)));
        gameSetRepository.saveAndFlush(gameSet);
    }

    @Transactional
    @Override
    public void score(User user, @Named("points") Integer points) {
        Score score = new Score(points, user.getUserId());
        scoreRepository.save(score);
    }

    private List<GameSet> randomize(int count, List<GameSet> gameSets) {
        if (gameSets == null || gameSets.isEmpty()) return Collections.EMPTY_LIST;
        if (gameSets.size() <= count) return gameSets;

        final List<GameSet> randomGameSets = new ArrayList<>();
        final Set<Integer> indices = new HashSet<>();

        while (randomGameSets.size() < count) {
            int index = AppUtil.randomInt(gameSets.size());

            // Ensure no two identical items are selected
            while (indices.contains(index))
                index = AppUtil.randomInt(gameSets.size());

            final GameSet randomGameSet = gameSets.get(index);
            randomGameSets.add(randomGameSet);
            indices.add(index);
        }


        return randomGameSets;
    }
}
