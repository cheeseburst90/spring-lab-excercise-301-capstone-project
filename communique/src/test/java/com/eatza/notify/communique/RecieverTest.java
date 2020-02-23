package com.eatza.notify.communique;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.eatza.notify.communique.helper.Receiver;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecieverTest {
	
	@MockBean
	private Sender sender;
	
	@Autowired
	private Receiver receiver;
	
	

	@Before
	private void sendMessgae() {
		sender.send("this is a test message");
	}
	
	@Test
	private void testReceiver() {
		assertEquals(0,receiver.getMsg().size());
		receiver.receive("message");
	}

}
