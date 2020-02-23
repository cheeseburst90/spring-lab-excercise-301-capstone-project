package com.eatza.order.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.eatza.order.dto.OrderRequestDto;
import com.eatza.order.dto.OrderUpdateDto;
import com.eatza.order.dto.OrderUpdateResponseDto;
import com.eatza.order.exception.OrderException;
import com.eatza.order.model.Order;
import com.eatza.order.service.orderservice.OrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class OrderController {

	@Autowired
	OrderService orderService;

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@GetMapping("/eatza/order/test/1")
	public String test() {
		return "hello";
	}

	@PostMapping("/eatza/order")
	public ResponseEntity<Order> placeOrder(@RequestBody OrderRequestDto orderRequestDto) throws OrderException{
		logger.info("In place order method, calling the service");
		Order order = orderService.placeOrder(orderRequestDto);
		logger.info("Order Placed Successfully");
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(order);

	}
	@PutMapping("/eatza/order/cancel/{orderId}")
	public ResponseEntity<String> cancel(@PathVariable Long orderId) throws OrderException{
		logger.info("In cancel order method");
		boolean result =orderService.cancelOrder(orderId);
		if(result) {
			logger.info("Order Cancelled Successfully");
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("Order Cancelled Successfully");
		} else {
			logger.info("No records found for respective id");
			throw new OrderException("No records found for respective id");
		}	
	}

	@PutMapping("/eatza/order")
	public ResponseEntity<OrderUpdateResponseDto> updateOrder(@RequestBody OrderUpdateDto orderUpdateDto) throws OrderException{

		logger.info(" In updateOrder method, calling service");
		OrderUpdateResponseDto updatedResponse = orderService.updateOrder(orderUpdateDto);
		logger.info("Returning back the object");

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(updatedResponse);


	}

	@GetMapping("/eatza/order/{orderId}")
	public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) throws OrderException{
		logger.info("In get order by id method, calling service to get Order by ID");
		Optional<Order> order = orderService.getOrderById(orderId);
		if(order.isPresent()) {
			logger.info("Got order from service");
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(order.get());
		}
		else {
			logger.info("No orders were found");
			throw new OrderException("No result found for specified inputs");
		}
	}

	@GetMapping("/eatza/order/value/{orderId}")
	public ResponseEntity<Double> getOrderAmountByOrderId(@PathVariable Long orderId) throws OrderException{
		logger.info("In get order value by id method, calling service to get Order value");
		double price = orderService.getOrderAmountByOrderId(orderId);

		if(price!=0) {
			logger.info("returning price: "+price);
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(price);
		}
		else {
			logger.info("No result found for specified inputs");
			throw new OrderException("No result found for specified inputs");
		}	
	}



}
