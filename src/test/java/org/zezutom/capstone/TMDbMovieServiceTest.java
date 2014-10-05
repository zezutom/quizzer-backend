package org.zezutom.capstone;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.zezutom.capstone.model.Movie;
import org.zezutom.capstone.service.MovieService;
import org.zezutom.capstone.service.impl.TMDbMovieService;

import javax.annotation.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Created by tom on 05/10/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-servlet.xml")
public class TMDbMovieServiceTest {

    @Resource(name = "tmdbMovieService")
    private MovieService service;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Before
    public void setUp() throws IOException {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer.expect(requestTo(getQueryUrl()))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(getSearchResults(), MediaType.APPLICATION_JSON)
                );
    }

    @Test
    public void findByTitle() {
        Collection<Movie> movies = service.findByTitle("Terminal");
        assertNotNull(movies);
        assertThat(movies.size(), is(5));

        final Iterator<Movie> iterator = movies.iterator();

        assertMovie(iterator.next(), "The Terminal", "/f4Dup6awDfDqAHKgWqNJ2HFw1qN.jpg");
        assertMovie(iterator.next(), "The Terminal Man", "/b2SUvKY4ZkkY9a1OzW9uetbW8vx.jpg");
        assertMovie(iterator.next(), "The Smashing Pumpkins: Terminal 5", "/t4rHBaoIMFbN0hRbqxCfinW3VkQ.jpg");
        assertMovie(iterator.next(), "Prison Terminal: The Last Days of Private Jack Hall", "/knCEuyiUJvEgbn1POhoVe0ivI5E.jpg");
        assertMovie(iterator.next(), "The Terminal Trust", "/kHwBfsvYOY8url7KrCtBRbXBpiB.jpg");
    }

    private void assertMovie(Movie movie, String title, String image) {
        assertNotNull(movie);
        assertThat(movie.getTitle(), is(title));
        assertThat(movie.getImage(), is(image));
    }

    private String getSearchResults() throws IOException {
        return IOUtils.toString(new ClassPathResource("tmdbSearch.json").getInputStream());
    }

    private String getQueryUrl() {
        return TMDbMovieService.API_URL.replace("{key}", TMDbMovieService.API_KEY).replace("{query}", "Terminal");
    }
}
