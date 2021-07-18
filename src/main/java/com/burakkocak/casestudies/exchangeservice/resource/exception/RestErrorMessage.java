package com.burakkocak.casestudies.exchangeservice.resource.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * @author Burak KOCAK
 * @date 7/11/2021
 */
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestErrorMessage {

    private HttpStatus status;

    private String message;

    private List<String> errors;

    @Nullable
    @JsonProperty("exception")
    private String exceptionName;

    public RestErrorMessage(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        this.errors = Arrays.asList(error);
    }

    public RestErrorMessage(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}
