package org.zezutom.capstone.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zezutom.capstone.model.Difficulty;
import org.zezutom.capstone.model.GameSet;

import java.util.List;

public interface GameSetRepository extends JpaRepository<GameSet, Long> {

    List<GameSet> findByDifficulty(Difficulty difficulty);
}
