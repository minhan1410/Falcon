package com.example.callapi.model.dto;

import com.example.callapi.validate.CheckDimensions;
import com.example.callapi.validate.CheckMetrics;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RequestDto {
    @CheckMetrics
    @NotEmpty(message = "metrics is not empty")
    List<String> metrics;
    @CheckDimensions
    @NotEmpty(message = "dimensions is not empty")
    List<String> dimensions;
    @Max(value = 100_000, message = "limit less than or equal 100_000")
    @Min(value = 0, message = "limit min 0")
    private Integer limit;
    private Date start;
    private Date end;
}
