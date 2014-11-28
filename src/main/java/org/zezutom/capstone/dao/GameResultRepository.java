package org.zezutom.capstone.dao;

import org.zezutom.capstone.model.GameResult;

import java.util.List;

public interface GameResultRepository extends GenericEntityRepository<GameResult> {

    List<GameResult> findByUserId(String userId);
}
