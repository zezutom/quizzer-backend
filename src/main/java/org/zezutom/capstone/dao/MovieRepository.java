package org.zezutom.capstone.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zezutom.capstone.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, String> {
}
