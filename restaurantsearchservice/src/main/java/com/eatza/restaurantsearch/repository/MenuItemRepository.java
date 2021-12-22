package com.eatza.restaurantsearch.repository;

import com.eatza.restaurantsearch.model.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    Page<MenuItem> findByNameContaining(String name, Pageable pageable);

    Optional<MenuItem> findById(Long id);

    Page<MenuItem> findByMenu_id(Long id, Pageable pageable);

}
