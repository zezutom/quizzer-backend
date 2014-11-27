package org.zezutom.capstone.dao;

import org.zezutom.capstone.domain.PlayoffResult;

import java.util.List;

public interface PlayoffResultRepository extends GenericEntityRepository<PlayoffResult> {

    List<PlayoffResult> findByUserId(String userId);
}
