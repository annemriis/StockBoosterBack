package com.example.back.ito03022021backend.contorllers.skateboard;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/skateboards")
public class SkateboardsApi {

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
    // Fred has a Skateboard shop in Telliskivi.
    // ---
    // Hi. I'm Fred the hipster. I studied law and music, but now I'm selling and making skateboards. Wild life!
    // Our business has grown and I need some help automating it.
    // Currently our inventory is managed by pen and paper. You need to make it better.
    // This is what I need:
    // - an overview of the skateboards we sell
    // - I want to know which ones are in stock and which ones are new (vs used)
    // - I want to order by the price or by the name alphabetically
    // and a
    // - page for each skateboard where I can see it's info
    // - button to add a new skateboard
    // - button to update existing skateboard
    // - button to delete skateboard

    private final HashMap<Long, Skateboard> skateboards;

    public SkateboardsApi() {
        this.skateboards = new LinkedHashMap<>();
    }

    //todo A first things first, please add necessary annotations to this class

    //todo B "an overview of the skateboards we sell"
    // create a method to query skateboards (plural)
    @GetMapping(path = "")
    public List<Skateboard> getAllSkateboards() {
        return new ArrayList<>(this.skateboards.values());
    }

    //todo C "page for each skateboard where I can see it's info"
    // create a method to query a single skateboard
    @GetMapping(path = "/{id}")
    public Skateboard getSkateboard(@PathVariable String id) {
        System.out.println(id);
        Long ID = Long.parseLong(id);
        if (skateboards.containsKey(ID)) {
            return skateboards.get(ID);
        } return null;
    }

    //todo D "button to add a new skateboard"
    // create a method to save a new skateboard

    // Postman body sample
    // {
    //    "id": 1,
    //    "name": "Peeter",
    //    "inStock": "True",
    //    "condition": "Good",
    //    "price": 15.0,
    //    "designer": "Peeter"
    //}
    @PostMapping(value = "/add")
    public ResponseEntity<Void> saveSkateboard(@RequestBody Skateboard skateboard) {
        skateboard.setCondition(skateboard.getCondition().toUpperCase(Locale.ROOT));
        skateboard.setCondition(skateboard.getInStock().toUpperCase(Locale.ROOT));
        this.skateboards.put(skateboard.getId(), skateboard);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //todo E "button to update existing skateboard"
    // create a method to update a skateboard
    @PutMapping(value = "/{id}/update")
    public ResponseEntity<Void> updateSkateboard(@PathVariable String id,
                                                       @RequestBody Skateboard skateboardDetails) {
        Long ID = Long.parseLong(id);
        if (skateboards.containsKey(ID)) {
            Skateboard skateboard = skateboards.get(ID);
            skateboard.setName(skateboardDetails.getName());
            skateboard.setPrice(skateboardDetails.getPrice());
            skateboard.setCondition(skateboardDetails.getCondition().toUpperCase(Locale.ROOT));
            skateboard.setDesigner(skateboardDetails.getDesigner());
            skateboard.setInStock(skateboardDetails.getInStock().toUpperCase(Locale.ROOT));
            System.out.println(skateboards);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //todo F "button to delete skateboard"
    // create a method to delete a skateboard
    @DeleteMapping(value = "/{id}/remove")
    public ResponseEntity<Void> deleteSkateboard(@PathVariable String id) {
        Long ID = Long.parseLong(id);
        this.skateboards.remove(ID);
        System.out.println(skateboards);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }



    //todo G, H "I want to know which ones are in stock and which ones are new (vs used)"
    // G modify correct method to filter whether the skateboard is in stock or out of stock
    // H modify correct method to filter by condition (new, used, broken)
    // make sure existing functionality doesn't break

    // Specify media types
    @GetMapping(path = "/available")
    public List<Skateboard> getAvailableSkateboards() {
        return getAllSkateboards().stream().filter(a -> a.getInStock().equals("TRUE")).collect(Collectors.toList());
    }

    @GetMapping(path = "?condition={condition}")
    public List<Skateboard> getAvailableSkateboards(@PathVariable String condition) {
        condition = condition.toUpperCase(Locale.ROOT);
        String finalCondition = condition;
        return getAllSkateboards().stream().filter(a -> a.getCondition().equals(finalCondition)).collect(Collectors.toList());
    }



    //todo I-J "I want to order by the price or by the name alphabetically"
    // I modify correct method to provide sorting by price and name
    // J modify correct method to support sorting in ascending and descending order
    // in addition write some examples for how you will sort using your api (provide urls)

    /**
     * @param by what category do you want the thing to be sorted by, like name or price
     * @param order asc or desc order has to be specified
     * @return
     */
    @GetMapping(path = "?sort={by},{order}")
    public List<Skateboard> getAvailableSkateboardsSorted(@PathVariable String by, @PathVariable String order) {
        Comparator<Skateboard> comparator;
        by = by.toUpperCase(Locale.ROOT);
        if (by.equals("NAME")) {
            comparator = Comparator.comparing(Skateboard::getName);
        } else {
            comparator = Comparator.comparing(Skateboard::getPrice);
        }
        order = order.toUpperCase(Locale.ROOT);
        if (order.equals("DESC")) {
            comparator = comparator.reversed();
        }
        return getAllSkateboards().stream().sorted(comparator).collect(Collectors.toList());
    }





}
