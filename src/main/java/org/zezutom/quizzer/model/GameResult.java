package org.zezutom.quizzer.model;

import javax.persistence.Entity;

/**
 * A record of an individual completed game.
 *
 * round                The round the user made it to in that particular game
 *
 * score                The achieved score
 *
 * powerUps             The number of power-ups the user has earned during a single game
 *
 * attemptOneRatio      The ratio of successful 1st-attempt guesses
 *
 * attemptTwoRatio      The ratio of successful 2nd-attempt guesses
 *
 * attemptThreeRatio    The ratio of successful 3rd-attempt guesses
 *
 */
@Entity
public class GameResult extends GenericEntity {

    private int round;

    private int score;

    private int powerUps;

    private int attemptOneRatio;

    private int attemptTwoRatio;

    private int attemptThreeRatio;

    private String email;

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPowerUps() {
        return powerUps;
    }

    public void setPowerUps(int powerUps) {
        this.powerUps = powerUps;
    }

    public int getAttemptOneRatio() {
        return attemptOneRatio;
    }

    public void setAttemptOneRatio(int attemptOneRatio) {
        this.attemptOneRatio = attemptOneRatio;
    }

    public int getAttemptTwoRatio() {
        return attemptTwoRatio;
    }

    public void setAttemptTwoRatio(int attemptTwoRatio) {
        this.attemptTwoRatio = attemptTwoRatio;
    }

    public int getAttemptThreeRatio() {
        return attemptThreeRatio;
    }

    public void setAttemptThreeRatio(int attemptThreeRatio) {
        this.attemptThreeRatio = attemptThreeRatio;
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
        if (!(o instanceof GameResult)) return false;

        GameResult that = (GameResult) o;

        if (attemptOneRatio != that.attemptOneRatio) return false;
        if (attemptThreeRatio != that.attemptThreeRatio) return false;
        if (attemptTwoRatio != that.attemptTwoRatio) return false;
        if (powerUps != that.powerUps) return false;
        if (round != that.round) return false;
        if (score != that.score) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = round;
        result = 31 * result + score;
        result = 31 * result + powerUps;
        result = 31 * result + attemptOneRatio;
        result = 31 * result + attemptTwoRatio;
        result = 31 * result + attemptThreeRatio;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(email)
                .append(":").append("[")
                .append(round).append("|")
                .append(score).append("|")
                .append(attemptOneRatio).append("|")
                .append(attemptTwoRatio).append("|")
                .append(attemptThreeRatio).append("]")
                .toString();
    }
}
