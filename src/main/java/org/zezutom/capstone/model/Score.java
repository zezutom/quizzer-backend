package org.zezutom.capstone.model;

import org.datanucleus.api.jpa.annotations.Extension;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
    private String id;

    @Version
    private Long version;

    private int value;

    private String username;

    private Date timestamp = new Date();

    public Score() {}

    public Score(int value, String username) {
        this.value = value;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int score) {
        this.value = score;
    }

    public String getUsername() {
        return username;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
