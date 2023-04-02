package com.example.callapi.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(value = "mo_data1")
public class Game {
    @Id
    private String gameId;
    private Date date;
    private String appBundleId;
    private String country;
    private float revenue;
    private Integer impression;
    private String platform;
    private String source;
    private float eCPM;
}
