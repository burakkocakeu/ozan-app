package com.burakkocak.casestudies.exchangeservice.exception;

/**
 * @author Burak KOCAK
 * @date 7/11/2021
 */
public enum RestServiceErrorEnum {

    UNKNOWN_CURRENCY(2000, "One or more currencies either invalid or could not be supported, currently!"),
    NULL_OBJECT(1000, "Saving null is not permitted!");

    private final int code;
    private final String description;

    RestServiceErrorEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }

}
