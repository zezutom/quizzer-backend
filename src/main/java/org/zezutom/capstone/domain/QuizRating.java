package org.zezutom.capstone.domain;

import javax.persistence.Entity;

/**
 * Rating statistics of an individual quiz.
 *
 * quizId       Identifies the quiz
 *
 * username     Identifies the author of the quiz
 *
 * title        Human-friendly quiz identifier, it should capture the essence of the puzzle
 *
 * ratingCount  The total number of votes
 *
 * upVotes      The total number of up-votes aka thumb-ups
 *
 * downVotes    The total number of down-votes aka thumb-downs
 *
 */
@Entity
public class QuizRating extends GenericEntity {

    private String quizId;

    private String title;

    private int ratingCount;

    private int upVotes;

    private int downVotes;

    public QuizRating() {
    }

    public QuizRating(String title) {
        this.title = title;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getTitle() {
        return title;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuizRating)) return false;

        QuizRating that = (QuizRating) o;

        if (downVotes != that.downVotes) return false;
        if (ratingCount != that.ratingCount) return false;
        if (upVotes != that.upVotes) return false;
        if (quizId != null ? !quizId.equals(that.quizId) : that.quizId != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = quizId != null ? quizId.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + ratingCount;
        result = 31 * result + upVotes;
        result = 31 * result + downVotes;
        return result;
    }
}
