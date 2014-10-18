package org.zezutom.capstone.service;

import org.zezutom.capstone.model.Movie;

import java.util.List;

public interface MovieApi {

    List<Movie> findByTitle(String title);
}
