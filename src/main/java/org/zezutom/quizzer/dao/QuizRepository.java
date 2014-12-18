package org.zezutom.quizzer.dao;

import org.zezutom.quizzer.model.Quiz;

import java.util.List;

public interface QuizRepository extends GenericEntityRepository<Quiz> {

    List<Quiz> findByUserId(String userId);
}
