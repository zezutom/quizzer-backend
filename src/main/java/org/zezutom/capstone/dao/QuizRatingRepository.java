package org.zezutom.capstone.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.zezutom.capstone.domain.QuizRating;

public interface QuizRatingRepository extends JpaRepository<QuizRating, String> {

    QuizRating findOneByQuizId(String quizId);
}
