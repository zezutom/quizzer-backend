package org.zezutom.capstone.service;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zezutom.capstone.dao.GameResultRepository;
import org.zezutom.capstone.dao.PlayoffResultRepository;
import org.zezutom.capstone.domain.GameResult;
import org.zezutom.capstone.domain.PlayoffResult;
import org.zezutom.capstone.util.AppUtil;
import org.zezutom.capstone.util.Ids;
import org.zezutom.capstone.util.Scopes;

import static com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID;

@Transactional
@Service
@Api(name = "gameService", namespace = @ApiNamespace(ownerDomain = "org.zezutom", ownerName = "org.zezutom"),
        version = AppUtil.API_VERSION,
        clientIds = {Ids.WEB, Ids.ANDROID, API_EXPLORER_CLIENT_ID},
        audiences = {Ids.WEB, Ids.ANDROID},
        scopes = {Scopes.EMAIL, Scopes.PROFILE})
public class GameServiceImpl extends GAEService implements GameService {

    @Autowired
    private GameResultRepository gameResultRepository;

    @Autowired
    private PlayoffResultRepository playoffResultRepository;

    @Override
    @ApiMethod(path = "singlegame/save", httpMethod = ApiMethod.HttpMethod.POST)
    public void saveGameResult(User user, GameResult gameResult) {
        gameResultRepository.save(gameResult);
    }

    @Transactional
    @Override
    @ApiMethod(path = "playoff/save", httpMethod = ApiMethod.HttpMethod.POST)
    public void savePlayoffResult(User user, PlayoffResult playoffResult) {
        playoffResultRepository.save(playoffResult);
    }
}
