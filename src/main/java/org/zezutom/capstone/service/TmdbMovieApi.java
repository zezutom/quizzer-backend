package org.zezutom.capstone.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.zezutom.capstone.model.Movie;
import org.zezutom.capstone.model.MovieImageConfig;
import org.zezutom.capstone.util.AppUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Api(name = "movie",
        version = AppUtil.API_VERSION,
        scopes = {AppUtil.EMAIL_SCOPE},
        clientIds = {AppUtil.ANDROID_CLIENT_ID},
        audiences = {AppUtil.ANDROID_AUDIENCE})
public class TmdbMovieApi implements MovieApi {

    public static final String RESULTS_KEY = "results";

    public static final String CONFIGS_KEY = "images";

    public static final String API_KEY = "b3c6fea4b402c485db3ec798c57d67b5";

    public static final String MOVIE_QUERY = "https://api.themoviedb.org/3/search/movie?api_key={key}&query={query}";

    public static final String CONFIG_QUERY = "https://api.themoviedb.org/3/configuration?api_key={key}";

    public static final String CONFIG_CACHE_KEY = "config";

    @Autowired
    private RestTemplate template;

    private Cache cache;

    public TmdbMovieApi() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        try {
            // TODO set cache expiry to 24hrs
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            cache = cacheFactory.createCache(Collections.emptyMap());
        } catch(CacheException e) {
            throw new IllegalStateException("Failed to initialize cache: " + e);
        }
    }

    @ApiMethod(httpMethod = ApiMethod.HttpMethod.GET)
    @Override
    public List<Movie> findByTitle(@Named("title") String title) {
        List<Movie> movies = retrieveData(MOVIE_QUERY, title, RESULTS_KEY, new TypeReference<List<Movie>>() {}, Collections.EMPTY_LIST);

        String basePath = (String) cache.get(CONFIG_CACHE_KEY);
        if (basePath == null) basePath = getAndCacheConfig();

        for (Movie movie : movies) {
            if (movie.getImageUrl() != null) movie.setBasePath(basePath);
        }
        return movies;
    }

    private String getAndCacheConfig() {
        MovieImageConfig config = retrieveData(CONFIG_QUERY, CONFIGS_KEY, new TypeReference<MovieImageConfig>() {});
        if (config == null) return "";

        String basePath = config.getSecureBaseUrl();

        final String[] posters = config.getPosterSizes();
        if (posters != null && posters.length > 0)  basePath += posters[0];

        cache.put(CONFIG_CACHE_KEY, basePath);

        return basePath;
    }

    private <T extends Object> T retrieveData(final String queryUrl, final String key, TypeReference typeRef) {
        return retrieveData(queryUrl, null, key, typeRef, null);
    }

    private <T extends Object> T retrieveData(final String queryUrl, final String queryString, final String key, TypeReference typeRef, T defaultValue) {

        Map<String, ? extends Object> response = (queryString == null) ?
                template.getForObject(queryUrl, Map.class, API_KEY) :
                template.getForObject(queryUrl, Map.class, API_KEY, AppUtil.sanitize(queryString));

        if (response == null || !response.containsKey(key)) return defaultValue;

        ObjectMapper mapper = new ObjectMapper();
        T data;
        try {
            final String jsonArray = mapper.writeValueAsString(response.get(key));
            data = mapper.readValue(jsonArray, typeRef);
        } catch (IOException e) {
            throw new IllegalStateException("JSON parsing failed: " + e);
        }
        return data;
    }
}
