package org.zezutom.capstone.dao;

import org.zezutom.capstone.model.Quiz;

import java.util.List;

public interface QuizRepository extends GenericEntityRepository<Quiz> {

    List<Quiz> findByUserId(String userId);
}
