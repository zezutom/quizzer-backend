package org.zezutom.quizzer.dao;

import org.zezutom.quizzer.model.GameResult;

import java.util.List;

public interface GameResultRepository extends GenericEntityRepository<GameResult> {

    // List<GameResult> findByUserId(String userId);

    List<GameResult> findByEmailOrderByCreatedAtDesc(String email);
}
