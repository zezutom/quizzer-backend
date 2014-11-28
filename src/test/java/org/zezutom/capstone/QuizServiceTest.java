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
import org.zezutom.capstone.dao.QuizRatingRepository;
import org.zezutom.capstone.dao.QuizRepository;
import org.zezutom.capstone.model.Quiz;
import org.zezutom.capstone.model.QuizRating;
import org.zezutom.capstone.service.QuizService;

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

    @Autowired
    private QuizRatingRepository quizRatingRepository;

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

        for (int i = 0; i < count; i++) {
            Quiz quiz = TestUtil.createQuiz();
            assertThat(quizService.addNew(user, quiz), is(quiz));
        }

        // Verify that all of the saved quizzes can be correctly retrieved
        TestUtil.assertEntities(count, quizService.getAll());
    }

    @Test
    public void addNew() {
        User user = TestUtil.createUser();
        Quiz quiz = TestUtil.createQuiz();
        assertQuiz(quizService.addNew(user, quiz), quiz);
    }

    @Test
    public void rate_like() {
        // Save a new quiz
        Quiz quiz = saveAndGetQuiz();

        // Let a user like it
        QuizRating quizRating = quizService.rate(TestUtil.createUser(), quiz.getId(), true);

        // Verify it's got up-voted
        assertRating(quizRating, true);
    }

    @Test
    public void rate_dislike() {
        // Save a new quiz
        Quiz quiz = saveAndGetQuiz();

        // Let a user dislike it
        QuizRating quizRating = quizService.rate(TestUtil.createUser(), quiz.getId(), false);

        // Verify it's got down-voted
        assertRating(quizRating, false);
    }

    private Quiz saveAndGetQuiz() {
        quizService.addNew(TestUtil.createUser(), TestUtil.createQuiz());
        return quizRepository.findAll().get(0);
    }

    private void assertRating(QuizRating quizRating, boolean liked) {
        TestUtil.assertEntity(quizRating);
        assertThat(quizRating.isLiked(), is(liked));
    }

    private void assertQuiz(Quiz actual, Quiz expected) {
        assertTrue(actual.getAnswer() == expected.getAnswer());
        assertThat(actual.getExplanation(), is(expected.getExplanation()));
        assertThat(actual.getMovieOne(), is(expected.getMovieOne()));
        assertThat(actual.getMovieTwo(), is(expected.getMovieTwo()));
        assertThat(actual.getMovieThree(), is(expected.getMovieThree()));
        assertThat(actual.getMovieFour(), is(expected.getMovieFour()));
        assertThat(actual.getTitle(), is(expected.getTitle()));
    }

}
