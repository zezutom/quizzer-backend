package org.zezutom.capstone.service;

import org.zezutom.capstone.model.Movie;

import java.util.Collection;

/**
 * Created by tom on 05/10/2014.
 */
public interface MovieService {

    Collection<Movie> findByTitle(String title);
}
