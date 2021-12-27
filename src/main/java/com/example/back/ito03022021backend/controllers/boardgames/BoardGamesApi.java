package com.example.back.ito03022021backend.controllers.boardgames;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "boardgames")
public class BoardGamesApi {

    private Map<Long, BoardGame> boardGames = new HashMap<>();

    //todo Welcome to the theory!
    // To start put these classes into my.project.controller.theory so you can check these using swagger or browser
    // Each team member has to do only 1 assignment and commit/push it to your repository.
    // (So 2 people - 2 assignments, 3 people - 3 assignments, 4 people - 4 assignments).
    // Make sure to commit under your user otherwise the points won't count. Each team member has to score at least 50%.
    // Don't add unnecessary code (no need for services or database).
    // We are doing mock-api design. I am grading urls and structure of the methods.
    // It should still work, i.e I can access this api from swagger or browser.
    // A good source for learning about proper API design is https://docs.microsoft.com/en-us/azure/architecture/best-practices/api-design

    //todo The Story
    // Brandon has been elected head of the board game club.
    // ---
    // Nice to meet you, I'm Brandon. I'm the head of the board game club.
    // It's an active club with thousands of players and thousands of games. (We meet once a week in Telliskivi).
    // To manage this system we have some student php application which is very bad.
    // I need a better and more modern application. Written with some cool Java and Spring Boot.
    // This could also function as api for other applications that could connect to our api.
    // I am looking to replace games part of the application and if this goes well then also users part.
    // We have a large catalog of games from which our members can select games they want to play.
    // Each game has a detailed info on a separate page.
    // Each month we buy new game and add it to our system. We are missing functionality to update a game, but would like to have it.
    // Currently we have to delete a game and add a new one.
    // For a catalog of games we can filter by genre and number of players.
    // Sort by gameplay time and year released.
    //
    //todo A first things first, please add necessary annotations to this class

    //todo B "We have a large catalog of games from which our members can select games they want to play."
    // create a method to query BoardGames (plural)
    @GetMapping(path = "")
    public List<BoardGame> getBoardGames(@RequestParam Optional<String> genre, @RequestParam Optional<String> players,
                                         @RequestParam Optional<String> sort, @RequestParam Optional<String> dir) {
        List<BoardGame> boardGamesList = new ArrayList<>(boardGames.values());

        if (genre.isPresent()) {
            String genreString = genre.get().toLowerCase(Locale.ROOT);
            boardGamesList = boardGamesList
                    .stream()
                    .filter(boardGame -> boardGame.getGenre().equals(genreString))
                    .collect(Collectors.toList());
        }
        if (players.isPresent()) {
            String playersString = players.get();
            boardGamesList = boardGamesList
                    .stream()
                    .filter(boardGame -> boardGame.getNumberOfPlayers().equals(playersString))
                    .collect(Collectors.toList());

        }
        if (sort.isPresent()) {
            String sortString = sort.get().toLowerCase(Locale.ROOT);
            if (sortString.contains("year")) {
                boardGamesList = boardGamesList
                        .stream()
                        .sorted(Comparator.comparing(boardGame -> Integer.parseInt(boardGame.getYearReleased())))
                        .collect(Collectors.toList());
            } else if (sortString.contains("time")) {
                boardGamesList = boardGamesList
                        .stream()
                        .sorted(Comparator.comparing(boardGame -> Integer.parseInt(boardGame.getGameplayTime())))
                        .collect(Collectors.toList());
            }
            if (dir.isPresent()) {
                String orderString = dir.get().toLowerCase(Locale.ROOT);
                if (orderString.equals("desc")) {
                    Collections.reverse(boardGamesList);
                }
            }
        }
        return boardGamesList;
    }

    //todo C "Each game has a detailed info on a separate page."
    // create a method to query a single BoardGame
    @GetMapping(path = "{id}")
    public BoardGame getBoardGame(@PathVariable String id) {
        Long longId = Long.parseLong(id);
        if (boardGames.containsKey(longId)) {
            return boardGames.get(longId);
        }
        return null;
    }

    /**
     * Example post request body in JSON:
     * {"id": 2,"name": "chess1234","genre": "chess","numberOfPlayers": "2","gameplayTime": "12","yearReleased":1901}
     */
    //todo D "Each month we buy new game and add it to our system"
    // create a method to save a new BoardGame
    @PostMapping(value = "")
    public ResponseEntity<Void> addBoardGame(@RequestBody BoardGame boardGame) {
        boardGames.put(boardGame.getId(), boardGame);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //todo E "We are missing functionality to update a game, but would like to have it"
    // create a method to update a BoardGame
    @PutMapping(value = "{id}")
    public ResponseEntity<Void> updateBoardGame(@PathVariable String id, @RequestBody BoardGame updatedBoardGame) {
        Long longId = Long.parseLong(id);
        if (boardGames.containsKey(longId)) {
            BoardGame boardGame = getBoardGame(id);
            boardGame.setId(updatedBoardGame.getId());
            boardGame.setName(updatedBoardGame.getName());
            boardGame.setGenre(updatedBoardGame.getGenre());
            boardGame.setNumberOfPlayers(updatedBoardGame.getNumberOfPlayers());
            boardGame.setGameplayTime(updatedBoardGame.getGameplayTime());
            boardGame.setYearReleased(updatedBoardGame.getYearReleased());
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //todo F "Currently we have to delete a game and add a new one." We can assume they need delete
    // create a method to delete a BoardGame
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteBoardGame(@PathVariable String id) {
        Long longId = Long.parseLong(id);
        if (boardGames.containsKey(longId)) {
            boardGames.remove(longId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //todo G, H "For a catalog of games we can filter by genre and number of players."
    // G modify correct method to filter by genre (strategy, cards, etc)
    // H modify correct method to filter by number of players (2, 4, 6 etc)
    // make sure existing functionality doesn't break

    //todo I-J "Sort by gameplay time and year released."
    // I modify correct method to provide sorting by gameplay time and year released
    // J modify correct method to support sorting in ascending and descending order
    // in addition write some examples for how you will sort using your api (provide urls)

    /**
     * add a new game: localhost:8080/boardgames
     * update a game: localhost:8080/boardgames/1
     * remove a game: localhost:8080/boardgames/1
     * filter by genre: localhost:8080/boardgames?genre=chess
     * filter by number of players: localhost:8080/boardgames?players=2
     * sort by gameplay time: localhost:8080/boardgames?sort=time
     * sort by year released (ascending): localhost:8080/boardgames?sort=year
     * sort by year released (descending): localhost:8080/boardgames?sort=year&dir=desc
     */
}
