package com.test_selenium.demo.service;

import com.test_selenium.demo.model.Game;

import java.util.List;

public interface GameService {
    List<Game> getGame();

    List<Game> getTopPaid() throws InterruptedException;

    void writeExcel(List<Game> games, String name);
}
