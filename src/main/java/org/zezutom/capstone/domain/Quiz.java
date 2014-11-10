package org.zezutom.capstone.domain;

import org.datanucleus.api.jpa.annotations.Extension;

import javax.persistence.*;
import java.util.Date;

/**
 * A quiz comprises four different movie titles, one of which being the odd one.
 *
 * username     Identifies the author of the quiz
 *
 * title        Helps to identify the quiz, it should capture the essence of the puzzle. Should only
 *              be shown on the quiz stats screen, since it could give a user a hint.
 *
 * movieOne     First movie title
 *
 * movieTwo     Second movie title
 *
 * movieThree   Third movie title
 *
 * movieFour    Fourth movie title
 *
 * answer       Identifies the odd title (1 to 4)
 *
 * explanation  Explains the reasoning for the answer
 *
 * difficulty   How difficult the game set is considered to be, scale 1 (easy) to 5 (tough)
 *
 * ratingCount  How many users have rated this game set. Every single up- and down-vote increments this count by one.
 *
 * upVotes      The total number of up-votes aka thumb-ups
 *
 * downVotes    The total number of down-votes aka thumb-downs
 *
 */
@Entity
public class Quiz extends GenericEntity {

    private String title;

    private String movieOne;

    private String movieTwo;

    private String movieThree;

    private String movieFour;

    private int answer;

    private String explanation;

    private int difficulty;

    private int ratingCount;

    private int upVotes;

    private int downVotes;

    public Quiz() {}

    public Quiz(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getMovieOne() {
        return movieOne;
    }

    public void setMovieOne(String movieOne) {
        this.movieOne = movieOne;
    }

    public String getMovieTwo() {
        return movieTwo;
    }

    public void setMovieTwo(String movieTwo) {
        this.movieTwo = movieTwo;
    }

    public String getMovieThree() {
        return movieThree;
    }

    public void setMovieThree(String movieThree) {
        this.movieThree = movieThree;
    }

    public String getMovieFour() {
        return movieFour;
    }

    public void setMovieFour(String movieFour) {
        this.movieFour = movieFour;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
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

    public int getDownVotes() {
        return downVotes;
    }

    public void upVote() { upVotes++; incrementRatingCount(); }

    public void downVote() { downVotes++; incrementRatingCount(); }

    private void incrementRatingCount() {
        this.ratingCount++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quiz)) return false;

        Quiz quiz = (Quiz) o;

        if (answer != quiz.answer) return false;
        if (difficulty != quiz.difficulty) return false;
        if (downVotes != quiz.downVotes) return false;
        if (ratingCount != quiz.ratingCount) return false;
        if (upVotes != quiz.upVotes) return false;
        if (explanation != null ? !explanation.equals(quiz.explanation) : quiz.explanation != null) return false;
        if (movieFour != null ? !movieFour.equals(quiz.movieFour) : quiz.movieFour != null) return false;
        if (movieOne != null ? !movieOne.equals(quiz.movieOne) : quiz.movieOne != null) return false;
        if (movieThree != null ? !movieThree.equals(quiz.movieThree) : quiz.movieThree != null) return false;
        if (movieTwo != null ? !movieTwo.equals(quiz.movieTwo) : quiz.movieTwo != null) return false;
        if (title != null ? !title.equals(quiz.title) : quiz.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (movieOne != null ? movieOne.hashCode() : 0);
        result = 31 * result + (movieTwo != null ? movieTwo.hashCode() : 0);
        result = 31 * result + (movieThree != null ? movieThree.hashCode() : 0);
        result = 31 * result + (movieFour != null ? movieFour.hashCode() : 0);
        result = 31 * result + answer;
        result = 31 * result + (explanation != null ? explanation.hashCode() : 0);
        result = 31 * result + difficulty;
        result = 31 * result + ratingCount;
        result = 31 * result + upVotes;
        result = 31 * result + downVotes;
        return result;
    }
}
