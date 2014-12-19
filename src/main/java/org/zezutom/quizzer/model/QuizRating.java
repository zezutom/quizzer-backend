package org.zezutom.quizzer.model;

import javax.persistence.Entity;

/**
 *  An individual quiz rating, it adds to the overall rating stats
 */
@Entity
public class QuizRating extends GenericEntity {

    private String quizId;

    private boolean liked;

    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuizRating)) return false;

        QuizRating that = (QuizRating) o;

        if (liked != that.liked) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (quizId != null ? !quizId.equals(that.quizId) : that.quizId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = quizId != null ? quizId.hashCode() : 0;
        result = 31 * result + (liked ? 1 : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
