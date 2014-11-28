package org.zezutom.capstone.model;

import javax.persistence.Entity;

/**
 * A record of an individual completed game.
 *
 * username         Identifies the user
 *
 * timestamp        When and what time was the game completed
 *
 * round            The round the user made it to in that particular game
 *
 * score            The achieved score
 *
 * powerUps         The number of power-ups the user has earned during a single game
 *
 * roundOneRatio    The 1st-attempt success ratio the user has achieved during a single game
 *
 * roundTwoRatio    The 2st-attempt success ratio the user has achieved during a single game
 *
 * roundThreeRatio  The 3rd-attempt success ratio the user has achieved during a single game
 *
 */
@Entity
public class GameResult extends GenericEntity {

    private int round;

    private int score;

    private int powerUps;

    private int roundOneRatio;

    private int roundTwoRatio;

    private int roundThreeRatio;

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
        if (!(o instanceof GameResult)) return false;

        GameResult that = (GameResult) o;

        if (powerUps != that.powerUps) return false;
        if (round != that.round) return false;
        if (roundOneRatio != that.roundOneRatio) return false;
        if (roundThreeRatio != that.roundThreeRatio) return false;
        if (roundTwoRatio != that.roundTwoRatio) return false;
        if (score != that.score) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = round;
        result = 31 * result + score;
        result = 31 * result + powerUps;
        result = 31 * result + roundOneRatio;
        result = 31 * result + roundTwoRatio;
        result = 31 * result + roundThreeRatio;
        return result;
    }
}
