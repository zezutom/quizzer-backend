package org.zezutom.capstone.dao;

import org.zezutom.capstone.domain.UserStats;

public interface UserStatsRepository extends GenericEntityRepository<UserStats> {

    UserStats findByUsername(String username);
}
