package org.zezutom.quizzer;

import com.google.appengine.api.users.User;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zezutom.quizzer.dao.QuizRepository;
import org.zezutom.quizzer.model.*;
import org.zezutom.quizzer.service.QuizzerService;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-servlet.xml")
public class QuizzerServiceTest {

    @Autowired
    private QuizzerService service;

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
    public void saveGameResult() {
        final User user = TestUtil.createUser();

        final int oneTimeAttempts = 4;
        final int twoTimeAttempts = 2;

        GameResult expected = new GameResultBuilder()
                .setRound(10)
                .setOneTimeAttempts(oneTimeAttempts)
                .setOneTimeConsecutiveAttempts(oneTimeAttempts / 2)
                .setTwoTimeAttempts(twoTimeAttempts)
                .build();
        GameResult actual = service.saveGameResult(user,
                expected.getRound(), expected.getScore(), expected.getPowerUps(), oneTimeAttempts, twoTimeAttempts, user.getEmail());

        TestUtil.assertGameResult(actual, expected);
    }

    @Test
    public void getQuizzes() {

        // Create a bunch of new quizzes
        final User user = TestUtil.createUser();
        final int count = 10;

        for (int i = 0; i < count; i++) {
            Quiz quiz = TestUtil.createQuiz();
            assertThat(service.addNewQuiz(user, quiz), is(quiz));
        }

        // Verify that all of the saved quizzes can be correctly retrieved
        TestUtil.assertEntities(count, service.getQuizzes());
    }

    @Test
    public void getQuizzesByCriteria() {
        Quiz anEasyJavaQuiz = TestUtil.createQuiz("quiz A", QuizCategory.JAVA, QuizDifficulty.EASY);
        Quiz anEasyAndroidQuiz = TestUtil.createQuiz("quiz B", QuizCategory.ANDROID, QuizDifficulty.EASY);
        Quiz aToughJavascriptQuiz = TestUtil.createQuiz("quiz C", QuizCategory.JAVASCRIPT, QuizDifficulty.TOUGH);
        Quiz aMediumJavaQuiz = TestUtil.createQuiz("quiz D", QuizCategory.JAVA, QuizDifficulty.MEDIUM);
        Quiz aMediumHTML5Quiz = TestUtil.createQuiz("quiz E", QuizCategory.HTML5, QuizDifficulty.MEDIUM);

        User user = TestUtil.createUser();

        service.addNewQuiz(user, anEasyJavaQuiz);
        service.addNewQuiz(user, anEasyAndroidQuiz);
        service.addNewQuiz(user, aToughJavascriptQuiz);
        service.addNewQuiz(user, aMediumJavaQuiz);
        service.addNewQuiz(user, aMediumHTML5Quiz);

        // All easy ones, category doesn't matter
        QuizSelectionCriteria criteria = new QuizSelectionCriteria();
        criteria.addDifficultyLevels(QuizDifficulty.EASY);

        List<Quiz> quizzes = service.getQuizzesByCriteria(criteria);
        TestUtil.assertEntities(2, quizzes);
        assertThat(quizzes.get(0).getQuestion(), is(anEasyJavaQuiz.getQuestion()));
        assertThat(quizzes.get(1).getQuestion(), is(anEasyAndroidQuiz.getQuestion()));

        // Java only, difficulty level doesn't matter
        criteria = new QuizSelectionCriteria();
        criteria.addCategories(QuizCategory.JAVA);

        quizzes = service.getQuizzesByCriteria(criteria);
        TestUtil.assertEntities(2, quizzes);
        assertThat(quizzes.get(0).getQuestion(), is(anEasyJavaQuiz.getQuestion()));
        assertThat(quizzes.get(1).getQuestion(), is(aMediumJavaQuiz.getQuestion()));

        // Java, medium difficulty only
        criteria = new QuizSelectionCriteria();
        criteria.addCategories(QuizCategory.JAVA);
        criteria.addDifficultyLevels(QuizDifficulty.MEDIUM);
        quizzes = service.getQuizzesByCriteria(criteria);
        TestUtil.assertEntities(1, quizzes);
        assertThat(quizzes.get(0).getQuestion(), is(aMediumJavaQuiz.getQuestion()));
    }

    @Test
    public void addNewQuiz() {
        User user = TestUtil.createUser();
        Quiz quiz = TestUtil.createQuiz();
        assertQuiz(service.addNewQuiz(user, quiz), quiz);
    }

    @Test
    public void rateQuiz_like() {
        // Save a new quiz
        Quiz quiz = saveAndGetQuiz();

        // Let a user like it
        User user = TestUtil.createUser();
        QuizRating quizRating = service.rateQuiz(user, quiz.getId(), true, user.getEmail());

        // Verify it's got up-voted
        assertRating(quizRating, true);
    }

    @Test
    public void rateQuiz_dislike() {
        // Save a new quiz
        Quiz quiz = saveAndGetQuiz();

        // Let a user dislike it
        User user = TestUtil.createUser();
        QuizRating quizRating = service.rateQuiz(user, quiz.getId(), false, user.getEmail());

        // Verify it's got down-voted
        assertRating(quizRating, false);
    }

    @Test
    public void getGameResultStats() {
        final User user = TestUtil.createUser();
        final String email = user.getEmail();

        GameResult coolResult = createGameResult(30, 10, 4, 8);
        GameResult okayResult = createGameResult(20, 5, 2, 5);
        GameResult poorResult = createGameResult(10, 2, 1, 3);

        service.saveGameResult(user, coolResult.getRound(), coolResult.getScore(), coolResult.getPowerUps(), 10, 8, email);
        service.saveGameResult(user, okayResult.getRound(), okayResult.getScore(), okayResult.getPowerUps(), 5, 5, email);
        service.saveGameResult(user, poorResult.getRound(), poorResult.getScore(), poorResult.getPowerUps(), 2, 3, email);

        GameResultStats stats = service.getGameResultStats(user, email);

        final int expectedAttemptOneRatio = Math.max
                (Math.max(coolResult.getAttemptOneRatio(), okayResult.getAttemptOneRatio()),
                        poorResult.getAttemptOneRatio());
        final int expectedAttemptTwoRatio = Math.max
                (Math.max(coolResult.getAttemptTwoRatio(), okayResult.getAttemptTwoRatio()),
                        poorResult.getAttemptTwoRatio());
        final int expectedAttemptThreeRatio = Math.max
                (Math.max(coolResult.getAttemptThreeRatio(), okayResult.getAttemptThreeRatio()),
                        poorResult.getAttemptThreeRatio());

        assertThat(stats.getPowerUps(), is(coolResult.getPowerUps()));
        assertThat(stats.getRound(), is(coolResult.getRound()));
        assertThat(stats.getAttemptOneRatio(), is(expectedAttemptOneRatio));
        assertThat(stats.getAttemptTwoRatio(), is(expectedAttemptTwoRatio));
        assertThat(stats.getAttemptThreeRatio(), is(expectedAttemptThreeRatio));
    }

    @Test
    public void getGameResults() {
        final int count = 10;
        final User user = TestUtil.createUser();

        final int round = 10;
        final int oneTimeAttempts = 5;
        final int oneTimeConsecutiveAttempts = 2;
        final int twoTimeAttempts = 1;

        for (int i = 0; i < count; i++) {
            GameResult gameResult = createGameResult(round, oneTimeAttempts, oneTimeConsecutiveAttempts, twoTimeAttempts);
            GameResult actual = service.saveGameResult(user,
                    round, gameResult.getScore(), gameResult.getPowerUps(),
                    oneTimeAttempts, twoTimeAttempts, user.getEmail());
            assertThat(actual, is(gameResult));
        }

        List<GameResult> gameResults = service.getGameResults(user, user.getEmail());
        TestUtil.assertEntities(count, gameResults, user);
    }

    @Test
    public void getQuizRatings() {
        final int count = 10;
        final User user = TestUtil.createUser();

        service.addNewQuiz(user, TestUtil.createQuiz());
        Quiz quiz = service.getQuizzes().get(0);

        for (int i = 0; i < count; i++) {
            assertNotNull(service.rateQuiz(user, quiz.getId(), i % 2 == 0, user.getEmail()));
        }

        List<QuizRating> quizRatings = service.getQuizRatings(quiz.getId());
        TestUtil.assertEntities(count, quizRatings);

        int likes = 0, dislikes = 0;
        for (QuizRating quizRating : quizRatings) {
            if (quizRating.isLiked()) likes++; else dislikes++;
        }
        assertTrue(likes == dislikes);
        assertThat(likes + dislikes, is(count));
    }

    @Test
    public void getQuizRatingStats() {
        final int count = 10;
        final User user = TestUtil.createUser();

        Quiz quiz = service.addNewQuiz(user, TestUtil.createQuiz());

        int likes = 0, dislikes = 0;
        for (int i = 0; i < count; i++) {
            boolean liked = i % 2 == 0;
            if (liked) likes++; else dislikes++;
            assertNotNull(service.rateQuiz(user, quiz.getId(), liked, user.getEmail()));
        }

        List<QuizRatingStats> statsList = service.getQuizRatingStats();
        assertNotNull(statsList);
        assertThat(statsList.size(), is(1));

        QuizRatingStats stats = statsList.get(0);
        assertThat(stats.getQuizId(), is(quiz.getId()));
        assertThat(stats.getTitle(), is(quiz.getQuestion()));
        assertThat(stats.getRatingCount(), is(count));
        assertThat(stats.getDownVotes(), is(likes));
        assertThat(stats.getUpVotes(), is(dislikes));
    }

    private Quiz saveAndGetQuiz() {
        service.addNewQuiz(TestUtil.createUser(), TestUtil.createQuiz());
        return quizRepository.findAll().get(0);
    }

    private void assertQuiz(Quiz actual, Quiz expected) {
        assertTrue(actual.getAnswer() == expected.getAnswer());
        assertThat(actual.getExplanation(), is(expected.getExplanation()));
        assertThat(actual.getOptionOne(), is(expected.getOptionOne()));
        assertThat(actual.getOptionTwo(), is(expected.getOptionTwo()));
        assertThat(actual.getOptionThree(), is(expected.getOptionThree()));
        assertThat(actual.getOptionFour(), is(expected.getOptionFour()));
        assertThat(actual.getQuestion(), is(expected.getQuestion()));
    }

    private void assertRating(QuizRating quizRating, boolean liked) {
        TestUtil.assertEntity(quizRating);
        assertThat(quizRating.isLiked(), is(liked));
        assertThat(quizRating.getEmail(), is(TestUtil.createUser().getEmail()));
    }

    private GameResult createGameResult(int round, int oneTimeAttempts, int oneTimeConsecutiveAttempts, int twoTimeAttempts) {
        return new GameResultBuilder()
                .setRound(round)
                .setOneTimeAttempts(oneTimeAttempts)
                .setOneTimeConsecutiveAttempts(oneTimeConsecutiveAttempts)
                .setTwoTimeAttempts(twoTimeAttempts)
                .setEmail(TestUtil.createUser().getEmail())
                .build();
    }

}
