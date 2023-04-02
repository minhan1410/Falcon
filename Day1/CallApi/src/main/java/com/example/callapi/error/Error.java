package com.example.callapi.error;

import com.example.callapi.model.response.Response;
import com.example.callapi.model.response.Status;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.sasl.AuthenticationException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class Error {
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder stringBuilder = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error ->
                stringBuilder.append(error.getDefaultMessage()).append(", "));

        return Response.builder()
                .status(Status.builder().code(-2).msg(stringBuilder.toString()).build())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleAuthenticationException() {
        return Response.builder()
                .status(Status.builder().code(-3).msg("token authorized failed").build())
                .timestamp(LocalDateTime.now())
                .build();
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Response handleException(Exception e) {
//        return Response.builder()
//                .status(Status.builder().code(-4).msg(e.getMessage()).build())
//                .timestamp(LocalDateTime.now())
//                .build();
//    }
}
