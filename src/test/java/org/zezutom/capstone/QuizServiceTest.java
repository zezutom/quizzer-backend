package org.zezutom.capstone;

import com.google.appengine.api.users.User;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zezutom.capstone.dao.QuizRepository;
import org.zezutom.capstone.domain.Quiz;
import org.zezutom.capstone.service.QuizService;
import org.zezutom.capstone.util.AppUtil;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-servlet.xml")
public class QuizServiceTest {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizRepository quizRepository;

    private final LocalServiceTestHelper helper = TestUtil.getDatastoreTestHelper();

    @Before
    public void setUp() {
        helper.setUp();
        TestUtil.login();
    }

    @After
    public void tearDown() {
        helper.tearDown();
        TestUtil.logout();
    }

    @Test
    public void getAll() {

        // Create a bunch of new quizzes
        final User user = TestUtil.createUser();
        final int count = 10;

        for (int i = 0; i < count; i++)
            quizService.addNew(user, TestUtil.createQuiz());

        // Verify that all of the saved quizzes can be correctly retrieved
        TestUtil.assertEntities(count, quizService.getAll());
    }

    @Test
    public void addNew() {

        final User user = TestUtil.createUser();
        final Quiz quiz = TestUtil.createQuiz();

        quizService.addNew(user, quiz);

        final List<Quiz> gameResults = quizRepository.findByUsername(AppUtil.getUsername());
        TestUtil.assertEntities(1, gameResults);
        assertQuiz(gameResults.get(0), quiz);
    }

    @Test
    public void rate_like() {
        // Save a new quiz
        final Quiz quiz = saveAndGetQuiz();

        // Take the existing ratings
        final int upVotes = quiz.getUpVotes();
        final int downVotes = quiz.getDownVotes();

        // Let a user like it
        quizService.rate(TestUtil.createUser(), quiz.getId(), true);

        // Verify it's got up-voted
        assertRating(quiz, upVotes + 1, downVotes);
    }

    @Test
    public void rate_dislike() {
        // Save a new quiz
        final Quiz quiz = saveAndGetQuiz();

        // Take the existing ratings
        final int upVotes = quiz.getUpVotes();
        final int downVotes = quiz.getDownVotes();

        // Let a user dislike it
        quizService.rate(TestUtil.createUser(), quiz.getId(), false);

        // Verify it's got down-voted
        assertRating(quiz, upVotes, downVotes + 1);
    }

    private Quiz saveAndGetQuiz() {
        quizService.addNew(TestUtil.createUser(), TestUtil.createQuiz());
        return quizRepository.findAll().get(0);
    }

    private void assertRating(Quiz quiz, int expectedUpVotes, int expectedDownVotes) {
        quiz = quizRepository.findOne(quiz.getId());
        TestUtil.assertEntity(quiz);
        assertThat(quiz.getUpVotes(), is(expectedUpVotes));
        assertThat(quiz.getDownVotes(), is(expectedDownVotes));
    }

    private void assertQuiz(Quiz actual, Quiz expected) {
        assertTrue(actual.getAnswer() == expected.getAnswer());
        assertThat(actual.getExplanation(), is(expected.getExplanation()));
        assertThat(actual.getMovieOne(), is(expected.getMovieOne()));
        assertThat(actual.getMovieTwo(), is(expected.getMovieTwo()));
        assertThat(actual.getMovieThree(), is(expected.getMovieThree()));
        assertThat(actual.getMovieFour(), is(expected.getMovieFour()));
        assertThat(actual.getTitle(), is(expected.getTitle()));
        assertThat(actual.getRatingCount(), is(expected.getRatingCount()));
        assertThat(actual.getUpVotes(), is(expected.getUpVotes()));
        assertThat(actual.getDownVotes(), is(expected.getDownVotes()));
    }

}
