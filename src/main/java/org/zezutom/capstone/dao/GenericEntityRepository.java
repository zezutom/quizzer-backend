package org.zezutom.capstone.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zezutom.capstone.domain.GenericEntity;

public interface GenericEntityRepository<T extends GenericEntity> extends JpaRepository<T, String> {
}
