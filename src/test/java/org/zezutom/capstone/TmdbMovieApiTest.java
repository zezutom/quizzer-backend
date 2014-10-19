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
import org.zezutom.capstone.model.Movie;
import org.zezutom.capstone.service.TmdbMovieApi;
import org.zezutom.capstone.util.AppUtil;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-servlet.xml")
public class TmdbMovieApiTest {

    @Autowired
    private TmdbMovieApi movieApi;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalMemcacheServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        mockQuery(TmdbMovieApi.MOVIE_QUERY, "tmdbSearch");
        mockQuery(TmdbMovieApi.CONFIG_QUERY, "tmdbImageConfig");
    }

    @After
    public void tearDown() { helper.tearDown(); }


    @Test
    public void findByTitle() {
        List<Movie> movies = movieApi.findByTitle("Terminal");
        assertNotNull(movies);
        assertThat(movies.size(), is(5));

        final Iterator<Movie> iterator = movies.iterator();

        assertMovie(iterator.next(), "The Terminal", "/f4Dup6awDfDqAHKgWqNJ2HFw1qN.jpg", 2004, AppUtil.parseRating("6.8"));
        assertMovie(iterator.next(), "The Terminal Man", "/b2SUvKY4ZkkY9a1OzW9uetbW8vx.jpg", 1974, AppUtil.parseRating("0.0"));
        assertMovie(iterator.next(), "The Smashing Pumpkins: Terminal 5", "/t4rHBaoIMFbN0hRbqxCfinW3VkQ.jpg", 2011, AppUtil.parseRating("9.5"));
        assertMovie(iterator.next(), "Prison Terminal: The Last Days of Private Jack Hall", "/knCEuyiUJvEgbn1POhoVe0ivI5E.jpg", 2013, AppUtil.parseRating("0.0"));
        assertMovie(iterator.next(), "The Terminal Trust", "/kHwBfsvYOY8url7KrCtBRbXBpiB.jpg", 2012, AppUtil.parseRating("3.0"));

    }

    private void assertMovie(Movie movie, String title, String imagePath, Integer year, Double rating) {
        assertNotNull(movie);
        assertThat(movie.getTitle(), is(title));
        assertThat(movie.getImageUrl(), is("https://image.tmdb.org/t/p/w92" + imagePath));
        assertThat(movie.getYear(), is(year));
        assertThat(movie.getRating(), is(rating));
    }

    private String readJsonFile(String fileName) throws IOException {
        return IOUtils.toString(new ClassPathResource(fileName + ".json").getInputStream());
    }

    private String getQueryUrl(String baseUrl) {
        return baseUrl.replace("{key}", TmdbMovieApi.API_KEY).replace("{query}", "");
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
}