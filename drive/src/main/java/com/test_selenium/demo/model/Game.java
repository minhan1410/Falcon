package com.test_selenium.demo.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document()
@Builder
@Data
public class Game {
    String name;
    Double star;
}
