package com.nortal.mega.exceptionHandler;

import com.nortal.mega.exception.BuildingAlreadyExistsException;
import com.nortal.mega.exception.NoBuildingFoundException;
import com.nortal.mega.rest.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class BuildingControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoBuildingFoundException.class)
    public ResponseEntity<ErrorMessage> handleNoBuildingFoundException(RuntimeException ex, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(Instant.now())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();

        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BuildingAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleBuildingAlreadyExistsException(RuntimeException ex, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .httpStatus(HttpStatus.CONFLICT)
                .statusCode(HttpStatus.CONFLICT.value())
                .timestamp(Instant.now())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();

        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(Instant.now())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();

        return new ResponseEntity<Object>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(Instant.now())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();

        return new ResponseEntity<Object>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
