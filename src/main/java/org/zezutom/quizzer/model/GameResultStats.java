package org.zezutom.quizzer.model;

/**
 * User statistics - keeps a track of the very best achievements of a particular user.
 *
 * Please note, this is not an entity.
 *
 * username         Identifies the user
 *
 * score            The highest achieved score
 *
 * round            The furthest round the user ever made it to
 *
 * powerUps         The highest number of power-ups the user has ever earned during a single game
 *
 * attemptOneRatio    The highest 1st-attempt success ratio the user has ever achieved during a single game
 *
 * attemptTwoRatio    The highest 2st-attempt success ratio the user has ever achieved during a single game
 *
 * attemptThreeRatio  The highest 3rd-attempt success ratio the user has ever achieved during a single game
 *
 */
public class GameResultStats extends GenericEntity {

    private int score;

    private int round;

    private int powerUps;

    private int attemptOneRatio;

    private int attemptTwoRatio;

    private int attemptThreeRatio;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameResultStats)) return false;

        GameResultStats userStats = (GameResultStats) o;

        if (powerUps != userStats.powerUps) return false;
        if (round != userStats.round) return false;
        if (attemptOneRatio != userStats.attemptOneRatio) return false;
        if (attemptThreeRatio != userStats.attemptThreeRatio) return false;
        if (attemptTwoRatio != userStats.attemptTwoRatio) return false;
        if (score != userStats.score) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = score;
        result = 31 * result + round;
        result = 31 * result + powerUps;
        result = 31 * result + attemptOneRatio;
        result = 31 * result + attemptTwoRatio;
        result = 31 * result + attemptThreeRatio;
        return result;
    }
}
