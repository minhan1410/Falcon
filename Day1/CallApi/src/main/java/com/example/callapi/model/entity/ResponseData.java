package com.example.callapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(value = "mo_data")
@JsonIgnoreProperties
public class ResponseData {
    private Integer total;
    private List<? extends Object> data;
}
