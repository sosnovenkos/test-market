package com.example.testmarket;



import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

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
	void df() {
		var x1 = new ArrayList<String>();
		var x2 = new LinkedList<String>();
		var x3 = new HashSet<String>();
		var z = new ArrayList<Collection>();
		z.add(x1);
		z.get(0);
	}


	@Test
	void contextLoads() throws Exception {
//		getUpdate()
		var jsonRequestBody = "{\"update_id\":473646717,\"callback_query\":{\"id\":\"3179970785721082875\",\"from\":" +
				"{\"id\":740394644,\"first_name\":\"Имя секрет\",\"is_bot\":false,\"language_code\":\"ru\"},\"message\":" +
				"{\"message_id\":2418,\"from\":{\"id\":5389563438,\"first_name\":\"K&b\",\"is_bot\":true,\"username\":" +
				"\"Kb19042022Bot\"},\"date\":1653035134,\"chat\":{\"id\":740394644,\"type\":\"private\",\"first_name\":" +
				"\"Имя секрет\"},\"text\":\"Нажимайте на кнопки и добавляйте позиции в корзину\",\"reply_markup\":" +
				"{\"inline_keyboard\":[[{\"text\":\"Миндаль 500 гр. 500 руб.\",\"callback_data\":\"ADD_TO_CART:9:21\"}]," +
				"[{\"text\":\"Грецкий орех 500 гр. 400 руб.\",\"callback_data\":\"ADD_TO_CART:9:22\"}],[{\"text\":" +
				"\"Кедровый орех 200 гр. 500 руб.\",\"callback_data\":\"ADD_TO_CART:9:23\"}],[{\"text\":\"Бразильский " +
				"орех 500 гр. 800 руб.\",\"callback_data\":\"ADD_TO_CART:9:24\"}],[{\"text\":\"Изюм белый 700 гр. 250 " +
				"руб.\",\"callback_data\":\"ADD_TO_CART:9:25\"}],[{\"text\":\"Изюм чёрный 700 гр. 250 руб.\"," +
				"\"callback_data\":\"ADD_TO_CART:9:26\"}],[{\"text\":\"Изюм корич 700 гр. 200 руб.\",\"callback_data\":" +
				"\"ADD_TO_CART:9:27\"}],[{\"text\":\"Финики 500 гр. 250 руб.\",\"callback_data\":\"ADD_TO_CART:9:28\"}]," +
				"[{\"text\":\"Курага 500 гр. 300 руб.\",\"callback_data\":\"ADD_TO_CART:9:29\"}],[{\"text\":\"Чернослив " +
				"500 гр. 150 руб.\",\"callback_data\":\"ADD_TO_CART:9:30\"}]]}},\"data\":\"ADD_TO_CART:9:21\",\"" +
				"chat_instance\":\"-2749085820830196998\"}}\n";

		Update update = objectMapper.readValue(jsonRequestBody, Update.class);

		doNothing().when(sender).send(any());


		mockMvc.perform(
				post("/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(update)));


	}

	@Test
	@DisplayName("Oформить заказ")
	void contextLoads2() throws Exception {
//		getUpdate()

		var jsonRequestBody = "{\"update_id\":473646728,\"message\":{\"message_id\":2437,\"from\":{\"id\":387340096,\"first_name\":\"Сергей\",\"is_bot\":false,\"username\":\"sosnovenko\",\"language_code\":\"ru\"},\"date\":1653035304,\"chat\":{\"id\":387340096,\"type\":\"private\",\"first_name\":\"Сергей\",\"username\":\"sosnovenko\"},\"text\":\"Оформить заказ\"}}";
		Update update = objectMapper.readValue(jsonRequestBody, Update.class);

//		doNothing().when(sender).send(any());


		mockMvc.perform(
				post("/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(update)));


	}


	@Test
	@DisplayName("Запрос добавления заказа позиции ")
	void contextLoads3() throws Exception {
//		getUpdate()

		var jsonRequestBody = "{\"update_id\":473646726,\"callback_query\":{\"id\":\"1663613046820920186\",\"from\":{\"id\":387340096,\"first_name\":\"Сергей\",\"is_bot\":false,\"username\":\"sosnovenko\",\"language_code\":\"ru\"},\"message\":{\"message_id\":2431,\"from\":{\"id\":5389563438,\"first_name\":\"K&b\",\"is_bot\":true,\"username\":\"Kb19042022Bot\"},\"date\":1653035293,\"chat\":{\"id\":387340096,\"type\":\"private\",\"first_name\":\"Сергей\",\"username\":\"sosnovenko\"},\"text\":\"Нажи��айте на кнопки и добавляйте позиции в корзину\",\"reply_markup\":{\"inline_keyboard\":[[{\"text\":\"Миндаль 500 гр. 500 руб.\",\"callback_data\":\"ADD_TO_CART:10:21\"}],[{\"text\":\"Грецкий орех 500 гр. 400 руб.\",\"callback_data\":\"ADD_TO_CART:10:22\"}],[{\"text\":\"Кедровый орех 200 гр. 500 руб.\",\"callback_data\":\"ADD_TO_CART:10:23\"}],[{\"text\":\"Бразильский орех 500 гр. 800 руб.\",\"callback_data\":\"ADD_TO_CART:10:24\"}],[{\"text\":\"Изюм белый 700 гр. 250 руб.\",\"callback_data\":\"ADD_TO_CART:10:25\"}],[{\"text\":\"Изюм чёрный 700 гр. 250 руб.\",\"callback_data\":\"ADD_TO_CART:10:26\"}],[{\"text\":\"Изюм корич 700 гр. 200 руб.\",\"callback_data\":\"ADD_TO_CART:10:27\"}],[{\"text\":\"Финики 500 гр. 250 руб.\",\"callback_data\":\"ADD_TO_CART:10:28\"}],[{\"text\":\"Курага 500 гр. 300 руб.\",\"callback_data\":\"ADD_TO_CART:10:29\"}],[{\"text\":\"Чернослив 500 гр. 150 руб.\",\"callback_data\":\"ADD_TO_CART:10:30\"}]]}},\"data\":\"ADD_TO_CART:10:26\",\"chat_instance\":\"-5559790698702389123\"}}\n";
		Update update = objectMapper.readValue(jsonRequestBody, Update.class);

//		doNothing().when(sender).send(any());


		mockMvc.perform(
				post("/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(update)));


	}

	@Test
	@DisplayName("Создает нулевой заказ")
	void contextLoads4() throws Exception {
//		getUpdate()

		var emptyOrderBody = "{\"update_id\":473646723,\"message\":{\"message_id\":2430,\"from\":{\"id\":387340096,\"first_name\":\"Сергей\",\"is_bot\":false,\"username\":\"sosnovenko\",\"language_code\":\"ru\"},\"date\":1653035293,\"chat\":{\"id\":387340096,\"type\":\"private\",\"first_name\":\"Сергей\",\"username\":\"sosnovenko\"},\"text\":\"Прайс\"}}";
		Update emptyOrderUpdate = objectMapper.readValue(emptyOrderBody, Update.class);

//		doNothing().when(sender).send(any());

		mockMvc.perform(
				post("/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(emptyOrderUpdate)));

		var itemToOrderBody = "{\"update_id\":473646726,\"callback_query\":{\"id\":\"1663613046820920186\",\"from\":{\"id\":387340096,\"first_name\":\"Сергей\",\"is_bot\":false,\"username\":\"sosnovenko\",\"language_code\":\"ru\"},\"message\":{\"message_id\":2431,\"from\":{\"id\":5389563438,\"first_name\":\"K&b\",\"is_bot\":true,\"username\":\"Kb19042022Bot\"},\"date\":1653035293,\"chat\":{\"id\":387340096,\"type\":\"private\",\"first_name\":\"Сергей\",\"username\":\"sosnovenko\"},\"text\":\"Нажи��айте на кнопки и добавляйте позиции в корзину\",\"reply_markup\":{\"inline_keyboard\":[[{\"text\":\"Миндаль 500 гр. 500 руб.\",\"callback_data\":\"ADD_TO_CART:10:21\"}],[{\"text\":\"Грецкий орех 500 гр. 400 руб.\",\"callback_data\":\"ADD_TO_CART:10:22\"}],[{\"text\":\"Кедровый орех 200 гр. 500 руб.\",\"callback_data\":\"ADD_TO_CART:10:23\"}],[{\"text\":\"Бразильский орех 500 гр. 800 руб.\",\"callback_data\":\"ADD_TO_CART:10:24\"}],[{\"text\":\"Изюм белый 700 гр. 250 руб.\",\"callback_data\":\"ADD_TO_CART:10:25\"}],[{\"text\":\"Изюм чёрный 700 гр. 250 руб.\",\"callback_data\":\"ADD_TO_CART:10:26\"}],[{\"text\":\"Изюм корич 700 гр. 200 руб.\",\"callback_data\":\"ADD_TO_CART:10:27\"}],[{\"text\":\"Финики 500 гр. 250 руб.\",\"callback_data\":\"ADD_TO_CART:10:28\"}],[{\"text\":\"Курага 500 гр. 300 руб.\",\"callback_data\":\"ADD_TO_CART:10:29\"}],[{\"text\":\"Чернослив 500 гр. 150 руб.\",\"callback_data\":\"ADD_TO_CART:10:30\"}]]}},\"data\":\"ADD_TO_CART:10:26\",\"chat_instance\":\"-5559790698702389123\"}}\n";
		Update update = objectMapper.readValue(itemToOrderBody, Update.class);

//		doNothing().when(sender).send(any());

		mockMvc.perform(
				post("/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(update)));


		var checkoutBody = "{\"update_id\":473646728,\"message\":{\"message_id\":2437,\"from\":{\"id\":387340096,\"first_name\":\"Сергей\",\"is_bot\":false,\"username\":\"sosnovenko\",\"language_code\":\"ru\"},\"date\":1653035304,\"chat\":{\"id\":387340096,\"type\":\"private\",\"first_name\":\"Сергей\",\"username\":\"sosnovenko\"},\"text\":\"Оформить заказ\"}}";
		Update checkoutUpdate = objectMapper.readValue(checkoutBody, Update.class);

//		doNothing().when(sender).send(any());


		mockMvc.perform(
				post("/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(checkoutUpdate)));


	}

}
