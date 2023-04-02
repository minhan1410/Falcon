package com.example.callapi.service.impl;

import com.example.callapi.service.AuthenticationService;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public void authentication(String userid, String authorization) throws AuthenticationException {
        if (!userid.equals("vietcd") || !authorization.equals("falcon-api")) {
            throw new AuthenticationException();
        }
    }
}
