package com.example.callapi.service;

import com.example.callapi.model.dto.RequestDto;
import com.example.callapi.model.response.Response;

public interface GameService {
    Response getAll(RequestDto requestDto);
}
