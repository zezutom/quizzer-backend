package org.zezutom.capstone.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zezutom.capstone.domain.Quiz;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, String> {

    List<Quiz> findByUsername(String username);
}
