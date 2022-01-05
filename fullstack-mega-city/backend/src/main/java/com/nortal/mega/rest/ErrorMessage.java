package com.nortal.mega.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorMessage {

    private HttpStatus httpStatus;
    private Integer statusCode;
    private Instant timestamp;
    private String message;
    private String description;

}
