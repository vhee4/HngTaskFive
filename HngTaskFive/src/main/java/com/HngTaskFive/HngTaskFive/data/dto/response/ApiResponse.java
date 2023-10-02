package com.HngTaskFive.HngTaskFive.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor @Builder @AllArgsConstructor
public class ApiResponse<T> {
    private String message;
    private Integer statusCode;
    private T data;

    public ApiResponse(HttpStatus httpStatus) {
    }
}
