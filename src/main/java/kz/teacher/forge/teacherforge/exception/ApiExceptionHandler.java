package kz.teacher.forge.teacherforge.exception;

import kz.teacher.forge.teacherforge.models.exception.ApiError;
import kz.teacher.forge.teacherforge.models.exception.ApiException;
import kz.teacher.forge.teacherforge.models.exception.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@RestControllerAdvice
public class ApiExceptionHandler {

    private final Logger LOG = LoggerFactory.getLogger(ExceptionHandler.class);

    private String applicationName;

    public ApiExceptionHandler() {
    }

    public ApiExceptionHandler(String applicationName) {
        this.applicationName = applicationName;
    }

    @ExceptionHandler({IOException.class})
    public ResponseEntity<String> handleNetworkErrors(IOException exception) {
        LOG.error(exception.getLocalizedMessage(), exception);

        ApiError error = ApiError.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(
                error.name(),
                exception.getClass().getSimpleName() + ": " + exception.getMessage());

        if (errorResponse.getOrigin() == null || errorResponse.getOrigin().isEmpty()) {
            errorResponse.setOrigin(applicationName);
        }

        return new ResponseEntity(errorResponse, HttpStatus.valueOf(error.getStatus()));
    }

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ErrorResponse> handleApiExceptions(ApiException exception) {
        LOG.error(exception.getLocalizedMessage(), exception);

        ApiError apiError = exception.getApiError();
        ErrorResponse errorResponse = exception.getErrorResponse();

        if (errorResponse.getOrigin() == null || errorResponse.getOrigin().isEmpty()) {
            errorResponse.setOrigin(applicationName);
        }

        return new ResponseEntity(errorResponse, HttpStatus.valueOf(apiError.getStatus()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleInterServerError(Exception exception) {
        LOG.error(exception.getLocalizedMessage(), exception);
        ApiError error = ApiError.INTERNAL_SERVER_ERROR;

        ErrorResponse errorResponse = new ErrorResponse(
                error.name(), exception.getClass().getSimpleName() + ": " + exception.getMessage());

        if (errorResponse.getOrigin() == null || errorResponse.getOrigin().isEmpty()) {
            errorResponse.setOrigin(applicationName);
        }

        return new ResponseEntity(errorResponse, HttpStatus.valueOf(error.getStatus()));
    }
}