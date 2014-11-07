package org.zezutom.capstone.domain;

import org.datanucleus.api.jpa.annotations.Extension;

import javax.persistence.*;
import java.util.Date;

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
@EntityListeners(AuditableListener.class)
public class QuizRating implements AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
    private String id;

    @Version
    private Long version;

    private String quizId;

    private String username;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private String title;

    private int ratingCount;

    private int upVotes;

    private int downVotes;

    public QuizRating() {
    }

    public QuizRating(String title) {
        this.title = title;
    }

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
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + ratingCount;
        result = 31 * result + upVotes;
        result = 31 * result + downVotes;
        return result;
    }
}
