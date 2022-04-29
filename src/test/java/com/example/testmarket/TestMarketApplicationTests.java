package com.example.testmarket;



import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.exampl.bot2.sender.Sender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 8081)
@SpringBootTest(classes = Config.class)
class TestMarketApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@SpyBean
	private Sender sender;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void contextLoads() throws Exception {
//		getUpdate()
		var update = new Update();//
		User user = new User(2046100242L,
				"Anton",
				false, null,
				"stoa2891",
				"ru",
				null,
				null,
				null);
		Chat chat = new Chat(2046100242L,
				"private",
				null,
				"Anton",
				null,
				"stoa2891",
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null);
		Message message = new Message(
				1168,
				user,
				1651154281,
				chat,
				null,
				null,
				null,
				"menu",
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null
		);
		update.setMessage(message);

		doNothing().when(sender).send(any());


		mockMvc.perform(
				post("/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(update)));


	}

}
