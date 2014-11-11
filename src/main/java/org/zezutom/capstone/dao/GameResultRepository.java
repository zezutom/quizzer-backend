package org.zezutom.capstone.dao;

import org.zezutom.capstone.domain.GameResult;

import java.util.List;

public interface GameResultRepository extends GenericEntityRepository<GameResult> {

    List<GameResult> findByUsername(String username);
}
