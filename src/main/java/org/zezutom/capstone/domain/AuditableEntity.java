package org.zezutom.capstone.domain;

import java.util.Date;

public interface AuditableEntity extends Persistable {

    String getUsername();

    void setUsername(String username);

    Date getCreatedAt();

    void setCreatedAt(Date date);
}
