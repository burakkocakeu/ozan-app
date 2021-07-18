package com.burakkocak.casestudies.exchangeservice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Burak KOCAK
 * @date 7/10/2021
 * The custom annotation for tracking any method for logging
 * execution time in aspect oriented programming(AOP) way.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TrackExecutionTime {
}
