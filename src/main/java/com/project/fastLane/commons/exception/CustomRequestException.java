package com.project.fastLane.commons.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.h2.api.ErrorCode;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class CustomRequestException {
    @ApiModelProperty(value = "Http status code", example = "BAD_REQUEST")
    private HttpStatus status;

    @ApiModelProperty(value = "Error Message", example = "Invalid JSON format")
    private String errorMessage;

    private ErrorCode errorCode;


    @ApiModelProperty(dataType = "java.util.Date", example = "2021-07-29 10:00")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private final LocalDateTime timeStamp;

    private CustomRequestException() {
        this.timeStamp = LocalDateTime.now();
    }



}
