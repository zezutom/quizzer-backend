package org.zezutom.capstone.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.zezutom.capstone.model.Movie;
import org.zezutom.capstone.service.MovieService;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Created by tom on 05/10/2014.
 */
@Controller
@RequestMapping("api")
public class MovieController {

    private static final String API_KEY = "b3c6fea4b402c485db3ec798c57d67b5";

    private static final String API_URL = "http://api.themoviedb.org/3/search/multi?api_key={key}&query={query}";

    private RestTemplate template = new RestTemplate();

    @Resource(name = "tmdbMovieService")
    private MovieService movieService;

    @RequestMapping("movie/{title}")
    @ResponseBody
    public Collection<Movie> byTitle(@PathVariable String title) {
        return movieService.findByTitle(title);
    }

}
