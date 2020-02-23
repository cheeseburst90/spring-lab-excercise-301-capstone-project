package com.eatza.notify.communique.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eatza.notify.communique.helper.Receiver;

@RestController
public class CommiqueController {

	@Autowired
	private Receiver reciever;

	@GetMapping(value = "/commique/notify")
	public ResponseEntity<List<String>> notifyCommique() {
		// TODO:smtp to be implemented.
		return new ResponseEntity<List<String>>(reciever.getMsg(), HttpStatus.ACCEPTED);
	}

}
