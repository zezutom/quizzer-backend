package org.zezutom.capstone.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zezutom.capstone.model.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, String> {

}
