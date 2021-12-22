package com.eatza.notify.communique;

import com.eatza.notify.communique.controller.CommiqueController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CommiqueController.class)
@SpringBootTest
public class CommuniqueAppControllerTest {

    @Autowired
    private CommiqueController commiqueController;

    @Autowired
    private Sender sender;

    @Before
    private void sendMessgae() {
        sender.send("this is a test message");
    }

    @Test
    private void testController() {
        assertThat(commiqueController.notifyCommique().getBody().size()).isEqualTo(0);
    }

}
