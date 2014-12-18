package org.zezutom.quizzer.dao;


import org.zezutom.quizzer.model.QuizRating;

import java.util.List;

public interface QuizRatingRepository extends GenericEntityRepository<QuizRating> {

    List<QuizRating> findByQuizId(String quizId);
}
