package org.zezutom.capstone.service;

import com.google.appengine.api.users.User;
import org.zezutom.capstone.model.Quiz;
import org.zezutom.capstone.model.QuizRating;

import java.util.List;

/**
 * Provides a list of quizzes, allows the user to add a new one, rate an existing quiz etc.
 */
public interface QuizService {

    /**
     * Provides a list of all available quizzes.
     *
     * @return  all of the active quizzes or an empty list if there is no data
     */
    List<Quiz> getAll();

    /**
     * Allows to add a new quiz. Requires authentication.
     *
     * @param user      User authentication
     * @param quiz      The new quiz
     */
    Quiz addNew(User user, Quiz quiz);

    /**
     * Allows to rate a quiz. Requires authentication.
     *
     * @param user      User authentication
     * @param quizId    Identifies the rated quiz
     * @param liked     true if the user liked the quiz, false otherwise
     *
     * @return the creŒated rating
     */
    QuizRating rate(User user, String quizId, boolean liked);
}
