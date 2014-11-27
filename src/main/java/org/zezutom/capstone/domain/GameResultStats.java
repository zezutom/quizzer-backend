package org.zezutom.capstone.domain;

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
 * roundOneRatio    The highest 1st-attempt success ratio the user has ever achieved during a single game
 *
 * roundTwoRatio    The highest 2st-attempt success ratio the user has ever achieved during a single game
 *
 * roundThreeRatio  The highest 3rd-attempt success ratio the user has ever achieved during a single game
 *
 */
public class GameResultStats extends GenericEntity {

    private int score;

    private int round;

    private int powerUps;

    private int roundOneRatio;

    private int roundTwoRatio;

    private int roundThreeRatio;

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

    public int getRoundOneRatio() {
        return roundOneRatio;
    }

    public void setRoundOneRatio(int roundOneRatio) {
        this.roundOneRatio = roundOneRatio;
    }

    public int getRoundTwoRatio() {
        return roundTwoRatio;
    }

    public void setRoundTwoRatio(int roundTwoRatio) {
        this.roundTwoRatio = roundTwoRatio;
    }

    public int getRoundThreeRatio() {
        return roundThreeRatio;
    }

    public void setRoundThreeRatio(int roundThreeRatio) {
        this.roundThreeRatio = roundThreeRatio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameResultStats)) return false;

        GameResultStats userStats = (GameResultStats) o;

        if (powerUps != userStats.powerUps) return false;
        if (round != userStats.round) return false;
        if (roundOneRatio != userStats.roundOneRatio) return false;
        if (roundThreeRatio != userStats.roundThreeRatio) return false;
        if (roundTwoRatio != userStats.roundTwoRatio) return false;
        if (score != userStats.score) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = score;
        result = 31 * result + round;
        result = 31 * result + powerUps;
        result = 31 * result + roundOneRatio;
        result = 31 * result + roundTwoRatio;
        result = 31 * result + roundThreeRatio;
        return result;
    }
}
