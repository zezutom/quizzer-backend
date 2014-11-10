package org.zezutom.capstone.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zezutom.capstone.domain.GameResult;

import java.util.List;

/**
 * Created by tom on 03/11/2014.
 */
public interface GameResultRepository extends GenericEntityRepository<GameResult> {

    List<GameResult> findByUsername(String username);
}
