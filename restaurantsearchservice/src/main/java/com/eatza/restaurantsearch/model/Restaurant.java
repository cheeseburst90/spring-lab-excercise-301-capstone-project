package com.eatza.restaurantsearch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private String cuisine;
    private int budget;
    private double rating;

    public Restaurant(String name, String location, String cuisine, int budget, double rating) {
        super();
        this.name = name;
        this.location = location;
        this.cuisine = cuisine;
        this.budget = budget;
        this.rating = rating;
    }

}
