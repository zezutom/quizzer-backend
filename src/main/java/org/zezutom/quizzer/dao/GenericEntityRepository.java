package org.zezutom.quizzer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zezutom.quizzer.model.GenericEntity;

public interface GenericEntityRepository<T extends GenericEntity> extends JpaRepository<T, String> {
}
