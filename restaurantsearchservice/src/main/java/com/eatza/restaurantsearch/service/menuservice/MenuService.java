package com.eatza.restaurantsearch.service.menuservice;

import com.eatza.restaurantsearch.model.Menu;

import java.util.Optional;

public interface MenuService {

    Menu saveMenu(Menu menu);

    Optional<Menu> getMenuById(Long id);

    Menu getMenuByRestaurantId(Long id);

}
