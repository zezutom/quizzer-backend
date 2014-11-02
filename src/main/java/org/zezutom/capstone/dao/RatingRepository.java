package org.zezutom.capstone.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;
import org.zezutom.capstone.model.Rating;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, String> {

    List<Rating> findByGameSetId(String gameSetId);
}
