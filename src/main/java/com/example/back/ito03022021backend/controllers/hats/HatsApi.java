package com.example.back.ito03022021backend.controllers.hats;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping(path = "/hats")
public class HatsApi {

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
    // Mad Hatter. Another Telliskivi butique. Hat know-how link (:  https://www.youtube.com/watch?v=6lYuL_kz9Ak
    // ---
    // Hey, I am Max Hatter. I'm from the states (US). I played basketball in my youth and I fell in love with hats.
    // I came to Estonia few years ago and started a business selling hats. I have a busy shop in Telliskivi region.
    // However during winter-time our sales are slow, so I am thinking of expanding our online presence.
    // I think we need to do something on the web. Like a shop or gallery or both. Connect it to tik-tok, instagram, facebook.
    // Do the online thing. Can you help?
    // I guess I need like a landing page where you can see many hats.
    // And each hat has some info, so once you click on it, it displays it.
    // And then there are buttons for saving and updating when I have new hats or some info was wrong.
    // Oh, and some way to remove hats.
    // For landing page it is important that the hats can be filtered by style and colour.
    // Also I'd like to order them by size and price.

    //todo A first things first, please add necessary annotations to this class
    private final HashMap<Long, Hat> hats;

    public HatsApi() {
        this.hats = new LinkedHashMap<>();
    }

    //todo B "I guess I need like a landing page where you can see many hats"
    // create a method to query hats (plural)
    @GetMapping(path = "")
    public List<Hat> getHats(@RequestParam Optional<String> style, @RequestParam Optional<String> colour,
                             @RequestParam Optional<String> sort, @RequestParam Optional<String> order) {
        List<Hat> hats = new ArrayList<>(this.hats.values());
        if (style.isPresent()) {
            String styleFilter = style.get().toLowerCase(Locale.ROOT);
            hats = hats.stream().filter(a -> a.getStyle().equals(styleFilter)).collect(Collectors.toList());
        } if (colour.isPresent()) {
            String colourFilter = colour.get().toLowerCase(Locale.ROOT);
            hats = hats.stream().filter(a -> a.getColour().equals(colourFilter)).collect(Collectors.toList());
        }
        if (sort.isPresent()) {
            Comparator<Hat> comparator = Comparator.comparing(Hat::getSize);
            if (sort.get().toLowerCase(Locale.ROOT).equals("price")) {
                comparator = Comparator.comparing(i -> Long.parseLong(i.getPrice()));
            }
            if (order.isPresent()) {
                if (order.get().toLowerCase(Locale.ROOT).equals("desc")) {
                    comparator = comparator.reversed();
                }
            }
            return hats.stream().sorted(comparator).collect(Collectors.toList());
        }
        return hats;
    }

    //todo C "And each hat has some info, so once you click on it, it displays it"
    // create a method to query a single hat
    @GetMapping(path = "/{id}")
    public Hat getHat(@PathVariable String id) {
        Long ID = Long.parseLong(id);
        if (hats.containsKey(ID)) {
            return hats.get(ID);
        } return null;
    }

    /** Example body:
     * {
     *     "id": 1,
     *     "size": "54",
     *     "colour": "red",
     *     "price": "20",
     *     "style": "cap"
     * }
     */
    //todo D "And then there are buttons for saving [..] when I have new hats [..]"
    // create a method to save a new hat
    @PostMapping(value = "")
    public ResponseEntity<Void> saveHat(@RequestBody Hat hat) {
        hat.setColour(hat.getColour().toLowerCase(Locale.ROOT));
        hat.setStyle(hat.getStyle().toLowerCase(Locale.ROOT));
        this.hats.put(hat.getId(), hat);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //todo E "And then there are buttons for [..] updating when [..] some info was wrong"
    // create a method to update a hat
    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateHat(@PathVariable String id, @RequestBody Hat updatedInfo) {
        Long ID = Long.parseLong(id);
        if (hats.containsKey(ID)) {
            Hat hat = hats.get(ID);
            hat.setSize(updatedInfo.getSize());
            hat.setColour(updatedInfo.getColour().toLowerCase(Locale.ROOT));
            hat.setPrice(updatedInfo.getPrice());
            hat.setStyle(updatedInfo.getStyle().toLowerCase(Locale.ROOT));
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //todo F "Oh, and some way to remove hats."
    // create a method to delete a hat
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteHat(@PathVariable String id) {
        this.hats.remove(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    //todo G, H "For landing page it is important that the hats can be filtered by style and colour."
    // G modify correct method to filter by hat style (59fifty, 9twenty, cap, etc)
    // H modify correct method to filter by hat colour (red, blue, etc)
    // make sure existing functionality doesn't break

    //todo I-J "Also I'd like to order them [the hats] by size and price."
    // I modify correct method to provide sorting by size and price
    // J modify correct method to support sorting in ascending and descending order
    // in addition write some examples for how you will sort using your api (provide urls)

    /**
     * Urls:
     * Filter by style: http://localhost:8080/hats?style=cap
     * Filter by colour: http://localhost:8080/hats?colour=red
     * Filter by both: http://localhost:8080/hats?style=cap&colour=red
     *
     * Sort by size in ascending order: http://localhost:8080/hats?sort=size
     * Sort by price in ascending order: http://localhost:8080/hats?sort=price
     * Sort by size in descending order: http://localhost:8080/hats?sort=size&order=desc
     * Sort by price in descending order: http://localhost:8080/hats?sort=price&order=desc
     */

}
