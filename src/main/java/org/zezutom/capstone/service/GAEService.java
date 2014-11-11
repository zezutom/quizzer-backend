package org.zezutom.capstone.service;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Created by tom on 11/11/2014.
 */
public abstract class GAEService {

    public GAEService() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
}
