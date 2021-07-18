package com.burakkocak.casestudies.exchangeservice.exception;

import com.burakkocak.casestudies.exchangeservice.resource.exception.RestErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Burak KOCAK
 * @date 7/11/2021
 */
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(RestServiceException.class)
    protected ResponseEntity handleRestServiceExceptions(Exception e) {
        List<String> errors = new ArrayList<>();

        StackTraceElement error = e.getStackTrace()[0];
        errors.add(error.getClassName() + "." + error.getMethodName() + "()" + "[L:" + error.getLineNumber() + "]");

        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), errors);

        return ResponseEntity.status(errorMessage.getStatus())
                .body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity handleUnhandledExceptions(Exception e) {
        List<String> errors = new ArrayList<>();

        StackTraceElement error = e.getStackTrace()[0];
        errors.add(error.getClassName() + "." + error.getMethodName() + "()" + "[L:" + error.getLineNumber() + "]");

        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), errors, e.getClass().getSimpleName());

        return ResponseEntity.status(errorMessage.getStatus())
                .body(errorMessage);
    }

}
