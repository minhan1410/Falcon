package com.example.callapi.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Response<T> {
    private Status status;
    private LocalDateTime timestamp;
    private T data;
}
