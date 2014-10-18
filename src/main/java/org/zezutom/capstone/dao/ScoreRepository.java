package org.zezutom.capstone.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zezutom.capstone.model.Score;

public interface ScoreRepository extends JpaRepository<Score, Long> {
}
