package com.test_selenium.demo.contains;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    READER("reader"), COMMENTER("commenter"), WRITER("writer");
    private String value;

}
