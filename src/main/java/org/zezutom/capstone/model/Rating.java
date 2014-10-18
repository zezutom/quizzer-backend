package org.zezutom.capstone.model;

import org.datanucleus.api.jpa.annotations.Extension;

import javax.persistence.*;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
    private String id;

    @Version
    private Long version;

    private String username;

    private Double value;

    @ManyToOne
    private GameSet gameSet;

    public Rating() {}

    public Rating(Double value, String username) {
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

    public String getUsername() {
        return username;
    }

    public Double getValue() {
        return value;
    }

    public GameSet getGameSet() {
        return gameSet;
    }

    public void setGameSet(GameSet gameSet) {
        this.gameSet = gameSet;
    }
}
