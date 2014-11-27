package org.zezutom.capstone.dao;


import org.zezutom.capstone.domain.QuizRating;

import java.util.List;

public interface QuizRatingRepository extends GenericEntityRepository<QuizRating> {

    List<QuizRating> findByQuizId(String quizId);
}
