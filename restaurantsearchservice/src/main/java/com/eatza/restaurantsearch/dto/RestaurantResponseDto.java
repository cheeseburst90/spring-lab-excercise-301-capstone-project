package com.eatza.restaurantsearch.dto;

import com.eatza.restaurantsearch.model.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RestaurantResponseDto {

    List<Restaurant> restaurants;
    int totalPages;
    long totalElements;


    public RestaurantResponseDto(List<Restaurant> restaurants, int totalPages, long totalElements) {
        super();
        this.restaurants = restaurants;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }


}
