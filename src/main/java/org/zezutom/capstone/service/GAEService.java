package org.zezutom.capstone.service;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Google App Engine - generic service, a single place for common functionality: such as Spring's DI.
 */
public abstract class GAEService {

    public GAEService() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
}
