package com.test_selenium.demo.service;

import com.test_selenium.demo.model.AccountGPT;

import java.util.List;

public interface GPTService {
    List<AccountGPT> load(String url);

    void checkAccount();

    String screenShot(String url, String name);

    void downloadFile(String url);
}
