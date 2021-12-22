package com.eatza.restaurantsearch.controller;

import com.eatza.restaurantsearch.dto.ItemRequestDto;
import com.eatza.restaurantsearch.exception.ItemNotFoundException;
import com.eatza.restaurantsearch.model.MenuItem;
import com.eatza.restaurantsearch.model.Restaurant;
import com.eatza.restaurantsearch.service.menuitemservice.MenuItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MenuItemController {


    private static final Logger logger = LoggerFactory.getLogger(MenuItemController.class);
    @Autowired
    MenuItemService menuItemService;

    @PostMapping("/rest/item")
    public ResponseEntity<String> addItemsToRestaurantMenu(@RequestBody ItemRequestDto itemRequestDto) {

        logger.debug("In addItemsToRestaurantMenu method");
        menuItemService.saveMenuItem(itemRequestDto);
        logger.debug("Item added successfully");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Item Added successfully");

    }

    @GetMapping("/rest/restaurant/item/name/{name}")
    public ResponseEntity<List<Restaurant>> getRestaurantsContainingItem(@PathVariable String name, @RequestParam(defaultValue = "1") int pagenumber, @RequestParam(defaultValue = "10") int pagesize) throws ItemNotFoundException {
        logger.debug("In getRestaurantsContainingItem method, calling service");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(menuItemService.findByName(name, pagenumber, pagesize));

    }

    @GetMapping("/rest/item/id/{id}")
    public ResponseEntity<MenuItem> getItemById(@PathVariable Long id) throws ItemNotFoundException {
        logger.debug("In getItemById method, calling service");
        Optional<MenuItem> item = menuItemService.findById(id);
        if (item.isPresent()) {
            logger.debug("got the item");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(item.get());
        } else {
            logger.debug("Item not found");
            //return new ResponseEntity<>(new MenuItem(),HttpStatus.ACCEPTED);
            throw new ItemNotFoundException("No Item found for specified inputs");
        }


    }


}
