package org.zezutom.capstone.dao;


import org.zezutom.capstone.domain.QuizRating;

public interface QuizRatingRepository extends GenericEntityRepository<QuizRating> {

    QuizRating findOneByQuizId(String quizId);
}
