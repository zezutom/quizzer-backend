package org.zezutom.quizzer.model;

import org.datanucleus.api.jpa.annotations.Extension;
import org.zezutom.quizzer.util.AppUtil;

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

    private String userId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String username) {
        this.userId = username;
    }


    @PrePersist
    public void audit() {
        setCreatedAt(new Date());
        setUserId(AppUtil.getUserId());
    }

}
