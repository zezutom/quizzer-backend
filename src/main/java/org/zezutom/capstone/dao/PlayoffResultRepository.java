package org.zezutom.capstone.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zezutom.capstone.domain.PlayoffResult;

import java.util.List;

public interface PlayoffResultRepository extends JpaRepository<PlayoffResult, String> {

    List<PlayoffResult> findByUsername(String username);
}
