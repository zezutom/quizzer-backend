package org.zezutom.capstone.domain;

import org.datanucleus.api.jpa.annotations.Extension;

import javax.persistence.*;
import java.util.Date;

/**
 * A record of an individual completed game.
 *
 * username         Identifies the user
 *
 * timestamp        When and what time was the game completed
 *
 * round            The furthest round the user made it to in that particular game
 *
 * score            The achieved score
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
@Entity
@EntityListeners(AuditableListener.class)
public class GameResult implements AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
    private String id;

    @Version
    private Long version;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private String username;

    private int round;

    private int score;

    private int powerUps;

    private int roundOneRatio;

    private int roundTwoRatio;

    private int roundThreeRatio;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Long getVersion() {
        return version;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

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
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + round;
        result = 31 * result + score;
        result = 31 * result + powerUps;
        result = 31 * result + roundOneRatio;
        result = 31 * result + roundTwoRatio;
        result = 31 * result + roundThreeRatio;
        return result;
    }
}
