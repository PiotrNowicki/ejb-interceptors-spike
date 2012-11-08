package com.piotrnowicki.interceptors.control;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * Convenience class for commonly used resources that should be CDI-injectable.
 * 
 * @author Piotr Nowicki
 * 
 */
public class Resources {

    @Produces
    private Logger produceLogger(InjectionPoint ip) {
        return Logger.getLogger(ip.getMember().getDeclaringClass().getName());
    }
}
