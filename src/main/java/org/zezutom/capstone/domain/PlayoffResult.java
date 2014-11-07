package org.zezutom.capstone.domain;

import org.datanucleus.api.jpa.annotations.Extension;

import javax.persistence.*;
import java.util.Date;

/**
 * A record of an individual play-off game, i.e. two players challenge each other in the "sudden-death" game mode.
 *
 * username     Identifies the user
 *
 * opponent     Identifies the user's opponent
 *
 * round        The round the match ended
 *
 * win          True, if the user identified by username won the match, false otherwise
 *
 */
@Entity
@EntityListeners({AuditableListener.class})
public class PlayoffResult implements AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
    private String id;

    @Version
    private Long version;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private String username;

    private String opponent;

    private int round;

    private boolean win;

    public PlayoffResult() {}

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

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayoffResult)) return false;

        PlayoffResult that = (PlayoffResult) o;

        if (round != that.round) return false;
        if (win != that.win) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (opponent != null ? !opponent.equals(that.opponent) : that.opponent != null) return false;
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
        result = 31 * result + (opponent != null ? opponent.hashCode() : 0);
        result = 31 * result + round;
        result = 31 * result + (win ? 1 : 0);
        return result;
    }
}
