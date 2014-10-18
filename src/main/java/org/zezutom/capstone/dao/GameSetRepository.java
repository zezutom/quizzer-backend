package org.zezutom.capstone.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zezutom.capstone.model.GameSet;

public interface GameSetRepository extends JpaRepository<GameSet, Long> {
}
