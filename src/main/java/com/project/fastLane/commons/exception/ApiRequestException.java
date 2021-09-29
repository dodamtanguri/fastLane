package com.project.fastLane.commons.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.fastLane.commons.enmuns.ErrorCode;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiRequestException {
    @ApiModelProperty(value = "Http status code", example = "BAD_REQUEST")
    private HttpStatus status;

    @ApiModelProperty(value = "Error Message", example = "Invalid JSON format")
    private String errorMessage;

    @ApiModelProperty(dataType = "java.util.Date", example = "2021-07-29 10:00")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;

    private ApiRequestException() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiRequestException(HttpStatus status, String errorMessage) {
        this();
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public ApiRequestException(ErrorCode code) {
        this();
        this.status = code.getStatus();
        this.errorMessage = code.getMessage();
    }
}
