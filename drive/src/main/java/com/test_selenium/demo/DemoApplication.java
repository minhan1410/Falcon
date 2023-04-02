package com.test_selenium.demo;

import com.test_selenium.demo.contains.Role;
import com.test_selenium.demo.service.GPTService;
import com.test_selenium.demo.service.GoogleDriveService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication implements CommandLineRunner {
    private final GPTService gptService;
    private final GoogleDriveService googleDriveService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException, IOException, GeneralSecurityException {
//        System.out.println(gptService.load("https://chatgptvietnam.org/account/api-keys"));
//        gptService.checkAccount();
//        gptService.screenShot("https://selenide.org/documentation/screenshots.html",
//                "selenide");
//        gptService.downloadFile("https://www.unikey.org/download.html");


/*        open("https://www.unikey.org/download.html");
        Configuration.downloadsFolder = "src/main/resources/static";

        Configuration.savePageSource = false; // k luu html
        Configuration.reportsFolder = "src/main/resources/static";

        $(byText("Download")).click();
        File download = $(By.xpath("/html/body/div[3]/div/article/div/section/ul[1]/li[2]/a")).download();
        googleDriveService.uploadFile(download, "an");

        String screenshot = screenshot(download.getName()).replaceAll("file:/", "");
        File screen = new File(screenshot);
        googleDriveService.uploadFile(screen, "an");

        download.delete();
        screen.delete();*/

        String id = googleDriveService.uploadFile(new File("src/main/resources/static/getTop.xlsx"), null);
        System.out.println("Upload: " + id);

        googleDriveService.listEverything().forEach(file -> System.out.printf("Name: %s - ID: %s - Parents: %s\n", file.getName(), file.getId(), file.getParents()));

        System.out.println(googleDriveService.shareFile(id, "minhan14102001@gmail.com", Role.WRITER));
        System.out.println(googleDriveService.shareFile(id, "tienantn85@gmail.com", Role.READER));
        System.out.println(googleDriveService.shareFile(id, "ghtkannm7@gmail.com", Role.COMMENTER));
    }
}
