package com.burakkocak.casestudies.exchangeservice.exception;

/**
 * @author Burak KOCAK
 * @date 7/11/2021
 */
public class RestServiceException extends Exception {

    public RestServiceException(RestServiceErrorEnum error) {
        super(error.getCode() + ": '" + error.getDescription() + "'");
    }

}
