package org.zezutom.capstone.domain;

import org.datanucleus.api.jpa.annotations.Extension;
import org.zezutom.capstone.util.AppUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class GenericEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
    private String id;

    @Version
    private Long version;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private String username;

    public String getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @PrePersist
    public void audit() {
        setCreatedAt(new Date());
        setUsername(AppUtil.getUsername());
    }

}
