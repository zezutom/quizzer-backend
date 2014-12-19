package org.zezutom.quizzer.service;

import com.google.appengine.api.users.User;
import org.zezutom.quizzer.model.*;

import java.util.List;

public interface QuizzerService {

    /**
     * Saves the result of a single-user game. Requires authentication.
     *
     * @param user                  User authentication
     * @param round                 Achieved round
     * @param score                 Total score
     * @param powerUps              Earned power-ups
     * @param oneTimeAttempts       The number of first time attempts
     * @param twoTimeAttempts       The number of second time attempts
     * @param email                 User's email as a temporary user id, since I couldn't get OAUTH2 working
     *
     * @return                      Game result (score, last round etc.)
     */
    GameResult saveGameResult(User user, int round, int score, int powerUps, int oneTimeAttempts, int twoTimeAttempts, String email);

    /**
     * Provides a list of all available quizzes.
     *
     * @return  all of the active quizzes or an empty list if there is no data
     */
    List<Quiz> getQuizzes();

    /**
     *
     * @param criteria
     * @return
     */
    List<Quiz> getQuizzesByCriteria(QuizSelectionCriteria criteria);

    /**
     * Allows to add a new quiz. Requires authentication.
     *
     * @param user      User authentication
     * @param quiz      The new quiz
     */
    Quiz addNewQuiz(User user, Quiz quiz);

    /**
     * Allows to rate a quiz. Requires authentication.
     *
     * @param user      User authentication
     * @param quizId    Identifies the rated quiz
     * @param liked     true if the user liked the quiz, false otherwise
     * @param email     User's email as a temporary user id, since I couldn't get OAUTH2 working
     *
     * @return the created rating
     */
    QuizRating rateQuiz(User user, String quizId, boolean liked, String email);

    /**
     * Fetches user's highest rankings.
     *
     * @param user      User authentication
     * @param email     User's email as a temporary user id, since I couldn't get OAUTH2 working
     * @return  rankings summmary
     */
    GameResultStats getGameResultStats(User user, String email);

    /**
     * Fetches user's gaming history
     * @param user      User authentication
     * @param email     User's email as a temporary user id, since I couldn't get OAUTH2 working
     * @return
     */
    List<GameResult> getGameResults(User user, String email);

    /**
     * List of individual quiz ratings
     *
     * @param quizId
     * @return user ratings or an empty list if no one has rated the quiz so far
     */
    List<QuizRating> getQuizRatings(String quizId);

    /**
     * Rating summaries. Each and every quiz has a single summary.
     * So this encompasses all quizzes in the system.
     *
     * @return
     */
    List<QuizRatingStats> getQuizRatingStats();



}
