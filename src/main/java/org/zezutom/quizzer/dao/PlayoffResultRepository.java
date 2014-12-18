package org.zezutom.quizzer.dao;

import org.zezutom.quizzer.model.PlayoffResult;

import java.util.List;

public interface PlayoffResultRepository extends GenericEntityRepository<PlayoffResult> {

    List<PlayoffResult> findByUserId(String userId);
}
