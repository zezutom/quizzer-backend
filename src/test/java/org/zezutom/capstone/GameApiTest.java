package org.zezutom.capstone;

import org.apache.commons.io.IOUtils;
import org.hamcrest.core.StringStartsWith;
import org.junit.Before;
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
import org.zezutom.capstone.model.GameSet;
import org.zezutom.capstone.model.Movie;
import org.zezutom.capstone.model.Solution;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-servlet.xml")
public class GameApiTest {

    @Autowired
    private GameApi gameApi;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Before
    public void setUp() throws IOException {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer.expect(requestTo(StringStartsWith.startsWith(getQueryUrl())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(getSearchResults(), MediaType.APPLICATION_JSON)
                );

    }

    @Test
    public void getMovie() {
        Collection<Movie> movies = gameApi.getMovie("Terminal");
        assertNotNull(movies);
        assertThat(movies.size(), is(5));

        final Iterator<Movie> iterator = movies.iterator();

        assertMovie(iterator.next(), "The Terminal", "/f4Dup6awDfDqAHKgWqNJ2HFw1qN.jpg");
        assertMovie(iterator.next(), "The Terminal Man", "/b2SUvKY4ZkkY9a1OzW9uetbW8vx.jpg");
        assertMovie(iterator.next(), "The Smashing Pumpkins: Terminal 5", "/t4rHBaoIMFbN0hRbqxCfinW3VkQ.jpg");
        assertMovie(iterator.next(), "Prison Terminal: The Last Days of Private Jack Hall", "/knCEuyiUJvEgbn1POhoVe0ivI5E.jpg");
        assertMovie(iterator.next(), "The Terminal Trust", "/kHwBfsvYOY8url7KrCtBRbXBpiB.jpg");
    }

    @Test
    public void getRandomGameSet() {
        final GameSet gameSet = gameApi.getRandomGameSet();
        assertNotNull(gameSet);

        // A game set should comprise exactly four movies
        final Collection<Movie> movies = gameSet.getMovies();
        assertNotNull(movies);
        assertThat(movies.size(), is(4));

        // There should be a solution to the game set
        final Solution solution = gameSet.getSolution();
        assertNotNull(solution);

        // The solution should refer to one of the 4 movies
        // and it should provide an explanation
        assertTrue(movies.contains(solution.getMovie()));
        assertNotNull(solution.getExplanation());
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
        return GameApi.API_URL.replace("{key}", GameApi.API_KEY).replace("{query}", "");
    }

}
