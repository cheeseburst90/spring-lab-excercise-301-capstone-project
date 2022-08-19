package com.eatza.restaurantsearch.repository;

import com.eatza.restaurantsearch.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    Menu findByRestaurant_id(Long restaurantId);
}
