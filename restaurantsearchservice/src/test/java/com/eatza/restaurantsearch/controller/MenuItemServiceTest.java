package com.eatza.restaurantsearch.controller;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static  org.mockito.ArgumentMatchers.any;
import static  org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.eatza.restaurantsearch.dto.ItemRequestDto;
import com.eatza.restaurantsearch.exception.ItemNotFoundException;
import com.eatza.restaurantsearch.exception.MenuNotSavedException;
import com.eatza.restaurantsearch.exception.RestaurantBadRequestException;
import com.eatza.restaurantsearch.model.Menu;
import com.eatza.restaurantsearch.model.MenuItem;
import com.eatza.restaurantsearch.model.Restaurant;
import com.eatza.restaurantsearch.repository.MenuItemRepository;
import com.eatza.restaurantsearch.service.menuitemservice.MenuItemServiceImpl;
import com.eatza.restaurantsearch.service.menuservice.MenuService;
import com.eatza.restaurantsearch.service.restaurantservice.RestaurantService;

@SpringBootTest
class MenuItemServiceTest {
	
	@InjectMocks
	private MenuItemServiceImpl menuItemService;
	
	@Mock
	private MenuItemRepository menuItemRepository;
	
	@Mock
	private MenuService menuService;
	
	@Mock
	private RestaurantService restaurantService;
	
	@Test
	public void findByNameOfRestaurant() throws ItemNotFoundException {
		MenuItem menuItem = new MenuItem();
		menuItem.setDescription("Dosa");
		menuItem.setId(1L);
		Restaurant restaurant = new Restaurant("Dominos", "RR", "Italian", 400, 4.1);
		restaurant.setId(1L);
		Menu menu = new Menu("10", "22", restaurant);
		menu.setId(1L);
		Optional<Menu> optionalMenu= Optional.of(new Menu("10", "22", restaurant));
		optionalMenu.get().setId(1L);
		menuItem.setMenu(menu);
		menuItem.setName("Onion Dosa");
		menuItem.setPrice(110);
		List<MenuItem> list = new ArrayList<>();
		list.add(menuItem);
		List<MenuItem> menuItems= new ArrayList<>();
		menuItems.add(menuItem);
		Page<MenuItem> page = new PageImpl<>(menuItems);
       
		when(menuItemRepository.findByNameContaining(any(String.class), any(Pageable.class))).thenReturn(page);
		when(menuService.getMenuById(anyLong())).thenReturn(optionalMenu);
		
		List<Restaurant> restaurantsToReturn =  menuItemService.findByName("Dominos",1,10);
	
	}
	
	@Test
	void findByNameOfRestaurant_empty() throws ItemNotFoundException {
		MenuItem menuItem = new MenuItem();
		menuItem.setDescription("Dosa");
		menuItem.setId(1L);
		Restaurant restaurant = new Restaurant("Dominos", "RR", "Italian", 400, 4.1);
		restaurant.setId(1L);
		Menu menu = new Menu("10", "22", restaurant);
		menu.setId(1L);
		Optional<Menu> optionalMenu= Optional.of(new Menu("10", "22", restaurant));
		optionalMenu.get().setId(1L);
		menuItem.setMenu(menu);
		menuItem.setName("Onion Dosa");
		menuItem.setPrice(110);
		List<MenuItem> list = new ArrayList<>();
		list.add(menuItem);
		List<MenuItem> menuItems= new ArrayList<>();
		menuItems.add(menuItem);
		Page<MenuItem> page = new PageImpl<>(menuItems);
       
		when(menuItemRepository.findByNameContaining(any(String.class), any(Pageable.class))).thenReturn(page);
		when(menuService.getMenuById(anyLong())).thenReturn(Optional.empty());
		
		Assertions.assertThrows(RestaurantBadRequestException.class, ()->menuItemService.findByName("Dominos",1,10));
	
	}
	
	@Test
	public void findByNameOfRestaurant_exception() throws ItemNotFoundException {
		MenuItem menuItem = new MenuItem();
		menuItem.setDescription("Dosa");
		menuItem.setId(1L);
		Restaurant restaurant = new Restaurant("Dominos", "RR", "Italian", 400, 4.1);
		restaurant.setId(1L);
		Menu menu = new Menu("10", "22", restaurant);
		menu.setId(1L);
		Optional<Menu> optionalMenu= Optional.of(new Menu("10", "22", restaurant));
		optionalMenu.get().setId(1L);
		menuItem.setMenu(menu);
		menuItem.setName("Onion Dosa");
		menuItem.setPrice(110);
		List<MenuItem> list = new ArrayList<>();
		list.add(menuItem);
       
		when(menuItemRepository.findByNameContaining(any(String.class), any(Pageable.class))).thenReturn(Mockito.mock(Page.class));
		when(menuService.getMenuById(anyLong())).thenReturn(optionalMenu);
		
		Assertions.assertThrows(ItemNotFoundException.class, ()->menuItemService.findByName("Dominos",1,10));
		

		
		
		
	}
	
	@Test
	public void saveMenuItem() {
		MenuItem menuItem = new MenuItem();
		menuItem.setDescription("Dosa");
		menuItem.setId(1L);
		ItemRequestDto dto = new ItemRequestDto();
		dto.setDescription("Dosa");
		dto.setMenuId(1L);
		dto.setName("Onion Dosa");
		dto.setPrice(110);
		
		menuItem.setName("Onion Dosa");
		menuItem.setPrice(110);
		Restaurant restaurant = new Restaurant("Dominos", "RR", "Italian", 400, 4.1);
		restaurant.setId(1L);
		Optional<Menu> optionalMenu= Optional.of(new Menu("10", "22", restaurant));
		optionalMenu.get().setId(1L);
		when(menuService.getMenuById(anyLong())).thenReturn(optionalMenu);
		menuItem.setMenu(optionalMenu.get());
		when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);
		MenuItem savedItem = menuItemService.saveMenuItem(dto);
		assertNotNull(savedItem);
		
	}
	
	@Test
	public void saveMenuItem_error() {
		MenuItem menuItem = new MenuItem();
		menuItem.setDescription("Dosa");
		menuItem.setId(1L);
		ItemRequestDto dto = new ItemRequestDto();
		dto.setDescription("Dosa");
		dto.setMenuId(1L);
		dto.setName("Onion Dosa");
		dto.setPrice(110);
		
		menuItem.setName("Onion Dosa");
		menuItem.setPrice(110);
		Restaurant restaurant = new Restaurant("Dominos", "RR", "Italian", 400, 4.1);
		restaurant.setId(1L);
		Optional<Menu> optionalMenu= Optional.of(new Menu("10", "22", restaurant));
		optionalMenu.get().setId(1L);
		when(menuService.getMenuById(anyLong())).thenReturn(Optional.empty());
		menuItem.setMenu(optionalMenu.get());
		when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);
		Assertions.assertThrows(MenuNotSavedException.class, ()->menuItemService.saveMenuItem(dto));
		
	}
	
	@Test
	public void findByMenuId() {
		
		List<MenuItem> menuItems = Arrays.asList(
				new MenuItem("Dosa", "Dosa", 50, new Menu("Active", "Till", new Restaurant("Dominos", "RR Nagar", "Italian", 400, 4.2))));
		Page<MenuItem> page = new PageImpl<>(menuItems);
		when(menuItemService.findByMenuId(anyLong(), any())).thenReturn(page);
		Page<MenuItem> items = menuItemService.findByMenuId(1L, PageRequest.of(1, 10));
		assertTrue(items.hasContent());
	}
	
	@Test
	public void findById() {
		
		MenuItem menuItems = new MenuItem("Dosa", "Dosa", 50, new Menu("Active", "Till", new Restaurant("Dominos", "RR Nagar", "Italian", 400, 4.2)));
		Optional<MenuItem> menuItemsToReturn = Optional.of(menuItems);
		when(menuItemService.findById(anyLong())).thenReturn(menuItemsToReturn);
		Optional<MenuItem> items = menuItemService.findById(1L);
		assertTrue(menuItemsToReturn.isPresent());
	}
	
	

}
