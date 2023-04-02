package com.test_selenium.demo.service.impl;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.test_selenium.demo.model.AccountGPT;
import com.test_selenium.demo.service.GPTService;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

@Service
public class GPTServiceImpl implements GPTService {
    private final List<AccountGPT> accountGPTS = new ArrayList<>();

    @Override
    public List<AccountGPT> load(String url) {
        open(url);
//        $("tr").shouldBe(visible);
        ElementsCollection trs = $$("tr");
        trs.stream().forEach(tr -> {
            String[] text = tr.getText().split(" ");
            if (!text[0].equals("STT")) {
                accountGPTS.add(AccountGPT.builder()
                        .username(text[1])
                        .password(text[2])
                        .build());
            }
        });
        return accountGPTS;
    }

    @SneakyThrows
    @Override
    public void checkAccount() {
        open("https://chatgpt.com/login");
//        Thread.sleep(10_000);

        accountGPTS.forEach(account -> {
            $("#username").setValue(account.getUsername());
            $(byText("Continue")).click();
            $("#password").setValue(account.getPassword());
            $(byText("Continue")).click();
            System.out.println();

        });
    }

    @Override
    public String screenShot(String url, String name) {
        open(url);
        Configuration.savePageSource = false; // k luu html
        Configuration.reportsFolder = "src/main/resources/static";
        return screenshot(name);
    }

    @SneakyThrows
    @Override
    public void downloadFile(String url) {
        open(url);
        Configuration.downloadsFolder = "src/main/resources/static";
        $(byText("Download")).click();
        $(By.xpath("/html/body/div[3]/div/article/div/section/ul[1]/li[2]/a")).download();
    }
}
