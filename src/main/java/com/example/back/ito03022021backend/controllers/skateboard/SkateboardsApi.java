package com.example.back.ito03022021backend.controllers.skateboard;

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

    /**
     * Adding bodys
     *      {
     *         "id": 1,
     *         "name": "A",
     *         "inStock": "TRUE",
     *         "condition": "OLD",
     *         "price": "25",
     *         "designer": "Peeter"
     *     }
     *     {
     *         "id": 3,
     *         "name": "Peeter3",
     *         "inStock": "TRUE",
     *         "condition": "OLD",
     *         "price": "35",
     *         "designer": "Peeter"
     *     }
     *     {
     *         "id": 2,
     *         "name": "Peeter2",
     *         "inStock": "TRUE",
     *         "condition": "BROKEN",
     *         "price": "15",
     *         "designer": "Peeter"
     *     }
     *      {
     *         "id": 4,
     *         "name": "Peeter5",
     *         "inStock": "FALSE",
     *         "condition": "NEW",
     *         "price": "5",
     *         "designer": "Peeter"
     *     }
     */
    @PostMapping(value = "/add")
    public ResponseEntity<Void> saveSkateboard(@RequestBody Skateboard skateboard) {
        skateboard.setCondition(skateboard.getCondition().toUpperCase(Locale.ROOT));
        skateboard.setInStock(skateboard.getInStock().toUpperCase(Locale.ROOT));
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
    //todo I-J "I want to order by the price or by the name alphabetically"
    // I modify correct method to provide sorting by price and name
    // J modify correct method to support sorting in ascending and descending order
    // in addition write some examples for how you will sort using your api (provide urls)
    /**
     *    http://localhost:8080/skateboards?instock=true
     *    http://localhost:8080/skateboards?instock=false
     *    http://localhost:8080/skateboards?condition=used
     *    http://localhost:8080/skateboards?condition=new
     *    http://localhost:8080/skateboards?condition=broken
     *
     *    http://localhost:8080/skateboards?sort=name
     *    http://localhost:8080/skateboards?sort=price
     *    http://localhost:8080/skateboards?sort=name&order=desc
     *    http://localhost:8080/skateboards?sort=price&order=desc
     * **/
    @GetMapping(path = "")
    public List<Skateboard> getSkateboards(@RequestParam Optional<String> instock, @RequestParam Optional<String> condition,
                                           @RequestParam Optional<String> sort, @RequestParam Optional<String> order) {
        List<Skateboard> skateboards = new ArrayList<>(this.skateboards.values());
        if (instock.isPresent()) {
            String availableStatus = instock.get().toUpperCase(Locale.ROOT);
            skateboards = skateboards.stream().filter(a -> a.getInStock().equals(availableStatus)).collect(Collectors.toList());
        } if (condition.isPresent()) {
            String isNew = condition.get().toUpperCase(Locale.ROOT);
            System.out.println(isNew);
            skateboards = skateboards.stream().filter(a -> a.getCondition().equals(isNew)).collect(Collectors.toList());
        }
        if (sort.isPresent()) {
            Comparator<Skateboard> comparator = Comparator.comparing(Skateboard::getName);
            if (sort.get().toLowerCase(Locale.ROOT).equals("price")) {
                comparator = Comparator.comparing(i -> Long.parseLong(i.getPrice()));
            } else if (sort.get().toLowerCase(Locale.ROOT).equals("name")) {
                comparator = Comparator.comparing(Skateboard::getName);
            }
            if (order.isPresent()) {
                if (order.get().toLowerCase(Locale.ROOT).equals("desc")) {
                    comparator = comparator.reversed();
                }
            }
            return skateboards.stream().sorted(comparator).collect(Collectors.toList());
        }
        return skateboards;
    }




}
