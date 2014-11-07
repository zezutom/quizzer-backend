package org.zezutom.capstone.domain;

import org.zezutom.capstone.util.AppUtil;

import javax.persistence.PrePersist;
import java.util.Date;

public class AuditableListener {

    @PrePersist
    public void prePersist(Object object) {
        AuditableEntity entity = (AuditableEntity) object;
        entity.setCreatedAt(new Date());
    }

}
