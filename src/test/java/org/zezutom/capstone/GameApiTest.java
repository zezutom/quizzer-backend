package org.zezutom.capstone;

import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.apache.commons.io.IOUtils;
import org.hamcrest.core.StringStartsWith;
import org.junit.After;
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

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalMemcacheServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        mockQuery(GameApi.MOVIE_QUERY, "tmdbSearch");
        mockQuery(GameApi.CONFIG_QUERY, "tmdbImageConfig");
    }

    @After
    public void tearDown() { helper.tearDown(); }

    @Test
    public void getMoviesByTitle() throws IOException {
        Collection<Movie> movies = gameApi.getMoviesByTitle("Terminal");
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
    public void getRandomGameSet() throws IOException {
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

    private void mockQuery(String queryUrl, String fileName) {
        try {
            mockServer.expect(requestTo(StringStartsWith.startsWith(getQueryUrl(queryUrl))))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(
                            withSuccess(readJsonFile(fileName), MediaType.APPLICATION_JSON)
                    );
        }
        catch (IOException e) {
            fail("Couldn't create a mocked response for: " + queryUrl);
        }
    }

    private void assertMovie(Movie movie, String title, String imagePath) {
        assertNotNull(movie);
        assertThat(movie.getTitle(), is(title));
        assertThat(movie.getImagePath(), is("https://image.tmdb.org/t/p/w92" + imagePath));
    }

    private String readJsonFile(String fileName) throws IOException {
        return IOUtils.toString(new ClassPathResource(fileName + ".json").getInputStream());
    }

    private String getQueryUrl(String baseUrl) {
        return baseUrl.replace("{key}", GameApi.API_KEY).replace("{query}", "");
    }

}
