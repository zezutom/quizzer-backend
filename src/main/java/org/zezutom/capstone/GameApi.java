package org.zezutom.capstone;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.users.User;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.zezutom.capstone.model.GameSet;
import org.zezutom.capstone.model.Movie;
import org.zezutom.capstone.model.Solution;

import java.io.IOException;
import java.util.*;

@Service
@Api(name = "game",
        version = "v1",
        scopes = {AppUtil.EMAIL_SCOPE},
        clientIds = {AppUtil.ANDROID_CLIENT_ID},
        audiences = {AppUtil.ANDROID_AUDIENCE})
public class GameApi {

    public static final String RESULTS_KEY = "results";

    public static final String API_KEY = "b3c6fea4b402c485db3ec798c57d67b5";

    public static final String API_URL = "http://api.themoviedb.org/3/search/multi?api_key={key}&query={query}";

    @Autowired
    private RestTemplate template;

    public GameApi() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public Collection<Movie> getMovie(@Named("title") String title) {
        return getResults(title);
    }

    public GameSetBuilder getGameSetBuilder(User user) { return new GameSetBuilder(); }

    public GameSet getGameSet(@Named("id") Long id) {
        // TODO
        return null;
    }

    public GameSet getRandomGameSet() {
        // TODO
        GameSet gameSet = new GameSet();
        final Collection<Movie> movies = getResults("rainy");
        final Iterator<Movie> iterator = movies.iterator();
        final Movie theOne = iterator.next();

        gameSet.addMovie(iterator.next());
        gameSet.addMovie(iterator.next());
        gameSet.addMovie(iterator.next());
        gameSet.addMovie(theOne);
        gameSet.setSolution(new Solution(theOne, "Lucky day is the secret."));
        return gameSet;
    }

    @ApiMethod(name = "game.addGameSet", httpMethod = AppUtil.HTTP_POST)
    public void addGameSet(User user, GameSet gameSet) {
        // TODO
    }


    private Collection<Movie> getResults(String query) {
        if (TextUtils.isEmpty(query)) return Collections.EMPTY_LIST;

        Map<String, ? extends Object> response = template.getForObject(API_URL, Map.class, API_KEY, AppUtil.sanitize(query));

        if (response == null || !response.containsKey(RESULTS_KEY)) return Collections.EMPTY_LIST;

        ObjectMapper mapper = new ObjectMapper();
        List<Movie> movies = Collections.EMPTY_LIST;
        try {
            final String jsonArray = mapper.writeValueAsString(response.get(RESULTS_KEY));
            movies = mapper.readValue(jsonArray, new TypeReference<List<Movie>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }
}