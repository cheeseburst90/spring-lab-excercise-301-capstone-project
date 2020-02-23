package com.practice.kafka.messaging.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.kafka.messaging.service.MessagingService;

@RestController
public class MessagingController {

	@Autowired
	private MessagingService messagingService;

	@GetMapping(value = "/producer")
	public String producer() {
		messagingService.publish();
		return "Message sent to the Kafka Topic TutorialTopic Successfully";
	}

	@GetMapping(value = "/consumer", produces= MediaType.IMAGE_JPEG_VALUE)
	public byte[] consumer() {
		return messagingService.subscribeMessage();
		/* return "message obtained in console"; */
	}

}
