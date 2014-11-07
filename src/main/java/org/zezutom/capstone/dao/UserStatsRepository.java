package org.zezutom.capstone.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zezutom.capstone.domain.UserStats;

public interface UserStatsRepository extends JpaRepository<UserStats, String> {

    UserStats findByUsername(String username);
}
