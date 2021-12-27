package com.example.back.ito03022021backend.controllers.boardgames;

public class BoardGame {

    private Long id;
    private String name;
    private String genre;
    private String numberOfPlayers;
    private String gameplayTime;
    private String yearReleased;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(String numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public String getGameplayTime() {
        return gameplayTime;
    }

    public void setGameplayTime(String gameplayTime) {
        this.gameplayTime = gameplayTime;
    }

    public String getYearReleased() {
        return yearReleased;
    }

    public void setYearReleased(String yearReleased) {
        this.yearReleased = yearReleased;
    }
}
