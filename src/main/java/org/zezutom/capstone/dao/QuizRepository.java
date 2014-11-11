package org.zezutom.capstone.dao;

import org.zezutom.capstone.domain.Quiz;

import java.util.List;

public interface QuizRepository extends GenericEntityRepository<Quiz> {

    List<Quiz> findByUsername(String username);
}
