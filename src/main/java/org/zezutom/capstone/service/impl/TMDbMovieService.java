package org.zezutom.capstone.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.zezutom.capstone.AppUtil;
import org.zezutom.capstone.model.Movie;
import org.zezutom.capstone.service.MovieService;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by tom on 05/10/2014.
 */
@Service("tmdbMovieService")
public class TMDbMovieService implements MovieService {

    public static final String RESULTS_KEY = "results";

    public static final String API_KEY = "b3c6fea4b402c485db3ec798c57d67b5";

    public static final String API_URL = "http://api.themoviedb.org/3/search/multi?api_key={key}&query={query}";

    @Autowired
    private RestTemplate template;

    @Override
    public Collection<Movie> findByTitle(String title) {
        return getResults(title);
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
