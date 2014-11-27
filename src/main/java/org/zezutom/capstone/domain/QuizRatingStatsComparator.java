package org.zezutom.capstone.domain;

import java.util.Comparator;

public class QuizRatingStatsComparator implements Comparator<QuizRatingStats> {
    @Override
    public int compare(QuizRatingStats o1, QuizRatingStats o2) {
        return o1.getTitle().compareTo(o2.getTitle());
    }
}
