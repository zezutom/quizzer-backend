package org.zezutom.capstone;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.zezutom.capstone.model.Movie;
import org.zezutom.capstone.service.MovieService;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by tom on 07/10/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-servlet-test.xml")
@WebAppConfiguration
public class MovieControllerTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        when(movieService.findByTitle(anyString())).thenReturn(getMovies());
    }

    @Test
    public void byTitle() throws Exception {
        mockMvc.perform(get("/api/movie/Lucky"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(AppUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$..[0].title", is("Lucky")))
                .andExpect(jsonPath("$..[0].poster_path", is("/jLbid3S4PJFdN1uOhl4PkH9xKpl.jpg")))
                .andExpect(jsonPath("$..[1].title", is("Luckytown")))
                .andExpect(jsonPath("$..[1].poster_path", is("/tF8VrRD5TXRYAB2WwzdGHUZM4Dd.jpg")))
                .andExpect(jsonPath("$..[2].title", is("Lucky Number")))
                .andExpect(jsonPath("$..[2].poster_path", is("/tt6dvfDRgI1810tWxZc8HLqXjlT.jpg")));
    }

    private Collection<Movie> getMovies() {
        Collection<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Lucky", "/jLbid3S4PJFdN1uOhl4PkH9xKpl.jpg"));
        movies.add(new Movie("Luckytown", "/tF8VrRD5TXRYAB2WwzdGHUZM4Dd.jpg"));
        movies.add(new Movie("Lucky Number", "/tt6dvfDRgI1810tWxZc8HLqXjlT.jpg"));
        return movies;
    }
}
