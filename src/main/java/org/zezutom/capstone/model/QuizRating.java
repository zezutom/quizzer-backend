package org.zezutom.capstone.model;

import javax.persistence.Entity;

/**
 *  An individual quiz rating, it adds to the overall rating stats
 */
@Entity
public class QuizRating extends GenericEntity {

    private String quizId;

    private boolean liked;

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuizRating)) return false;

        QuizRating that = (QuizRating) o;

        if (liked != that.liked) return false;
        if (!quizId.equals(that.quizId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = quizId.hashCode();
        result = 31 * result + (liked ? 1 : 0);
        return result;
    }
}
