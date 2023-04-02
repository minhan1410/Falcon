package com.test_selenium.demo.service.impl;

import com.test_selenium.demo.model.Game;
import com.test_selenium.demo.repository.GameRepository;
import com.test_selenium.demo.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private ChromeDriver driver;
    private List<Game> games;

    private void init() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        this.driver = new ChromeDriver(options);
        this.games = new ArrayList<>();
    }

    @SneakyThrows
    @Override
    public List<Game> getGame() {
        init();
        this.driver.get("https://play.google.com/store/apps/collection/cluster?clp=ChwKGgoUdG9wc2VsbGluZ19mcmVlX0dBTUUQBxgD:S:ANO1ljJH_B0&gsr=Ch4KHAoaChR0b3BzZWxsaW5nX2ZyZWVfR0FNRRAHGAM%3D:S:ANO1ljLEXvI&gl=jp");
//        driver.get("http://the-internet.herokuapp.com/login");
//        driver.findElement(By.id("username")).sendKeys("tomsmith");
//        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
//        driver.findElement(By.cssSelector("button")).click();
//        System.out.println("success message not present: " + driver.findElement(By.cssSelector(".flash.success")).isDisplayed());

        JavascriptExecutor js = (JavascriptExecutor) driver;
        long lastHeight = (long) js.executeScript("return document.body.scrollHeight");

        while (true) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(2000);
            long newHeight = (long) js.executeScript("return document.body.scrollHeight");
            if (newHeight == lastHeight) {
                break;
            }
            lastHeight = newHeight;
        }

        List<WebElement> allDivs = driver.findElements(By.className("ULeU3b"));
        System.out.println("game: " + allDivs.size());

        allDivs.forEach(game -> {
            String[] text = game.getText().split("\n");
            games.add(Game.builder().name(text[0]).star((text.length > 1 ? Double.valueOf(text[1]) : 0)).build());
        });

        return games;
    }

    @Override
    public List<Game> getTopPaid() throws InterruptedException {
        init();
        this.driver.get("https://play.google.com/store/games?gl=jp");
        Thread.sleep(5000);
        this.driver.findElement(By.id("ct|apps_topselling_paid")).click();

        List<WebElement> allDivs = this.driver.findElements(By.className("cXFu1"));
        allDivs.forEach(div -> {

            WebElement nameElement = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"yDmH0d\"]/c-wiz[2]/div/div/div[1]/c-wiz/div/c-wiz/c-wiz[2]/c-wiz/section/div/div[3]/div/div/div/div[1]/div[1]/div[1]/div/a/div[2]/div/div[1]/span")));
            String name = nameElement.getText();

            String star = div.findElement(By.className("CKzsaf")).findElement(By.className("w2kbF")).getText() + 1;
            games.add(Game.builder()
                    .name(div.findElement(By.className("DdYX5")).getText())
                    .star(Double.valueOf(div.findElement(By.className("CKzsaf")).findElement(By.className("w2kbF")).getText()))
                    .build());
        });
        System.out.println(games);
        return games;
    }

    @SneakyThrows
    @Override
    public void writeExcel(List<Game> games, String name) {
        Workbook writeGameFile = new XSSFWorkbook();
        Sheet sheet = writeGameFile.createSheet("Game");
//        sheet.setColumnWidth(0, 6000);
//        sheet.setColumnWidth(1, 4000);

        Row header = sheet.createRow(0);
        CellStyle headerStyle = createStyle(writeGameFile);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Star");
        headerCell.setCellStyle(headerStyle);

        for (int i = 0; i < games.size(); i++) {
            Game game = games.get(i);
            Row row = sheet.createRow(i + 1);

            Cell cell = row.createCell(0);
            cell.setCellValue(game.getName());

            cell = row.createCell(1);
            cell.setCellValue(game.getStar());
        }

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "src\\main\\resources\\static\\" + name + ".xlsx";

        System.out.println("Path: " + fileLocation);
        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        writeGameFile.write(outputStream);
        writeGameFile.close();
    }

    private CellStyle createStyle(Workbook writeGameFile) {
        CellStyle headerStyle = writeGameFile.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        XSSFFont font = (XSSFFont) writeGameFile.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        headerStyle.setFont(font);
        return headerStyle;
    }
}
