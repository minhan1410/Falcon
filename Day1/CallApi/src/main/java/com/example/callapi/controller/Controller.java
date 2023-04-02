package com.example.callapi.controller;

import com.example.callapi.model.dto.RequestDto;
import com.example.callapi.model.response.Response;
import com.example.callapi.service.AuthenticationService;
import com.example.callapi.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.security.sasl.AuthenticationException;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final GameService gameService;
    private final AuthenticationService authenticationService;

    @PostMapping("/test")
    Response getAll(
            @RequestHeader(value = "x-userid") String userid,
            @RequestHeader(value = "x-authorization") String authorization,
            @Valid @RequestBody RequestDto requestDto) throws AuthenticationException {
        authenticationService.authentication(userid, authorization);
        return gameService.getAll(requestDto);
    }
}
