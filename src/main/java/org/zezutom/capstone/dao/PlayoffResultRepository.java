package org.zezutom.capstone.dao;

import org.zezutom.capstone.model.PlayoffResult;

import java.util.List;

public interface PlayoffResultRepository extends GenericEntityRepository<PlayoffResult> {

    List<PlayoffResult> findByUserId(String userId);
}
