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
public class PlayoffResult extends GenericEntity {

    private String opponent;

    private int round;

    private boolean win;

    public PlayoffResult() {}

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
        if (opponent != null ? !opponent.equals(that.opponent) : that.opponent != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = opponent != null ? opponent.hashCode() : 0;
        result = 31 * result + round;
        result = 31 * result + (win ? 1 : 0);
        return result;
    }
}
