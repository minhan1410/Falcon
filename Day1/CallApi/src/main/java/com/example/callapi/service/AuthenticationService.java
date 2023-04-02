package com.example.callapi.service;

import javax.security.sasl.AuthenticationException;

public interface AuthenticationService {
    void authentication(String userid, String authorization) throws AuthenticationException;
}
