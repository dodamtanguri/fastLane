package com.project.fastLane.commons.enmuns;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    SUCCESS(OK, "Success"),
    ERROR(INTERNAL_SERVER_ERROR, "Internal Server Error"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST,"요청 파라미터를 확인해주세요.");

    private final HttpStatus status;
    private final String message;
}
