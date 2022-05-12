package com.qikserve.challenge.dtos;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * The type Exception response.
 */
@Data
@Builder
public class ExceptionResponse {

    private Integer code;
    private String details;
    private String message;
    private HttpStatus httpStatus;
}
