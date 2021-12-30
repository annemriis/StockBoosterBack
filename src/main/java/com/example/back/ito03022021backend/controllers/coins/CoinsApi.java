package com.example.back.ito03022021backend.controllers.coins;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/coins")
public class CoinsApi {

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
    // Chris is a coin collector. (Numismatic is the official term)
    // ---
    // I have 1 000 different coins from the Ancient Greek to the Modern Estonian euros.
    // I used to travel a lot with my coin collection. Do you know how many times have I had an exhibition in Telliskivi?
    // I used to travel, not anymore. Have you heard of corona?
    // Anyways I want to develop a web page for my coins so myself and my friends can view my collection wherever they are.
    // I need to have like a list view with many coins. If I click on a single coin, I get to a detail page.
    // I want to add new coins, update existing ones and occasionally delete some.
    // There should be some filtering, by period and region.
    // And sorting, by value and dateAdded. By default it can sort with latest coins first.
    //
    //todo A first things first, please add necessary annotations to this class

    private final HashMap<Long, Coin> coins;

    public CoinsApi() {
        this.coins = new LinkedHashMap<>();
    }

    //todo B "I need to have like a list view with many coins"
    // create a method to query coins (plural)

    //todo C "If I click on a single coin, I get to a detail page."
    // create a method to query a single coin
    @GetMapping(path = "/{id}")
    public Coin getCoin(@PathVariable String id) {
        System.out.println(id);
        Long ID = Long.parseLong(id);
        if (coins.containsKey(ID)) {
            return coins.get(ID);
        } return null;
    }

    //todo D "I want to add new coins"
    // create a method to save a new coin
    /**
     * Example bodies
     * {
     *         "id": 1,
     *         "name": "1eur",
     *         "period": "ancient times",
     *         "region": "americas",
     *         "age": "100",
     *         "condition": "old",
     *         "value": "25",
     *         "dateAdded": "2009"
     *     }
     *     {
     *         "id": 3,
     *         "name": "3eur",
     *         "period": "18th century",
     *         "region": "europe",
     *         "age": "3",
     *         "condition": "new",
     *         "value": "3",
     *         "dateAdded": "1999"
     *     }
     */
    @PostMapping(value = "/add")
    public ResponseEntity<Void> saveCoin(@RequestBody Coin coin) {
        coin.setCondition(coin.getCondition().toUpperCase(Locale.ROOT));
        coin.setDateAdded(coin.getDateAdded().toUpperCase(Locale.ROOT));
        this.coins.put(coin.getId(), coin);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //todo E "update existing ones"
    // create a method to update a coin
    @PutMapping(value = "/{id}/update")
    public ResponseEntity<Void> updateCoin(@PathVariable String id, @RequestBody Coin coinDetails) {
        Long ID = Long.parseLong(id);
        if (coins.containsKey(ID)) {
            Coin coin = coins.get(ID);
            coin.setName(coinDetails.getName());
            coin.setPeriod(coinDetails.getPeriod());
            coin.setRegion(coinDetails.getRegion().toUpperCase(Locale.ROOT));
            coin.setAge(coinDetails.getAge());
            coin.setCondition(coinDetails.getCondition().toUpperCase(Locale.ROOT));
            coin.setValue(coinDetails.getValue());
            coin.setDateAdded(coinDetails.getDateAdded());
            System.out.println(coins);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //todo F "occasionally delete some"
    // create a method to delete a blog
    @DeleteMapping(value = "/{id}/remove")
    public ResponseEntity<Void> deleteCoin(@PathVariable String id) {
        Long ID = Long.parseLong(id);
        this.coins.remove(ID);
        System.out.println(coins);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    //todo G, H "There should be some filtering, by period and region"
    // G modify correct method to filter by period (ancient times, 18th century, 19th century)
    // H modify correct method to filter by region (americas, europe)
    // make sure existing functionality doesn't break
    //todo I-J "And sorting, by value and date added. By default it can sort with latest coins first."
    // I modify correct method to provide sorting by value and date added
    // J modify correct method to support sorting in ascending and descending order
    // in addition write some examples for how you will sort using your api (provide urls)

    /**
     *    http://localhost:8080/coins?period=ancient times
     *    http://localhost:8080/coins?period=18th century
     *    http://localhost:8080/coins?period=19th century
     *    http://localhost:8080/coins?region=americas
     *    http://localhost:8080/coins?region=europe
     *
     *    http://localhost:8080/coins?sort=value
     *    http://localhost:8080/coins?sort=dateadded
     *    http://localhost:8080/coins?sort=value&order=desc
     *    http://localhost:8080/coins?sort=dateadded&order=desc
     * **/
    @GetMapping(path = "")
    public List<Coin> getCoins(@RequestParam Optional<String> period, @RequestParam Optional<String> region,
                                           @RequestParam Optional<String> sort, @RequestParam Optional<String> order) {
        List<Coin> coins = new ArrayList<>(this.coins.values());
        if (period.isPresent()) {
            String periodString = period.get().toUpperCase(Locale.ROOT);
            coins = coins.stream().filter(a -> a.getPeriod().equals(periodString)).collect(Collectors.toList());
        } if (region.isPresent()) {
            String regionString = region.get().toUpperCase(Locale.ROOT);
            System.out.println(regionString);
            coins = coins.stream().filter(a -> a.getRegion().equals(regionString)).collect(Collectors.toList());
        }
        if (sort.isPresent()) {
            Comparator<Coin> comparator = Comparator.comparing(Coin::getDateAdded);
            if (sort.get().toLowerCase(Locale.ROOT).equals("value")) {
                comparator = Comparator.comparing(i -> Long.parseLong(i.getValue()));
            } else if (sort.get().toLowerCase(Locale.ROOT).equals("dateadded")) {
                comparator = Comparator.comparing(Coin::getDateAdded);
            }
            if (order.isPresent()) {
                if (order.get().toLowerCase(Locale.ROOT).equals("desc")) {
                    comparator = comparator.reversed();
                }
            }
            return coins.stream().sorted(comparator).collect(Collectors.toList());
        }
        return coins;
    }

}