package com.example.callapi.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Status {
    private Integer code;
    private String msg;
}
