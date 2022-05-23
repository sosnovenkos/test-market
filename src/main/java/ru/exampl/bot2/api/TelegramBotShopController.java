package ru.exampl.bot2.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.exampl.bot2.domain.command.*;
import ru.exampl.bot2.sender.Sender;
import ru.exampl.bot2.domain.CommandType;
import ru.exampl.bot2.service.BotCommandService;


//import static jdk.javadoc.internal.tool.Main.execute;

@Slf4j
@RestController
@AllArgsConstructor
public class TelegramBotShopController {
    private final BotCommandService service;
    private final ObjectMapper objectMapper;
    private final Sender sender;
    @PostMapping("/1")
    public void onUpdateReceived(@RequestBody String jsonRequestBody) throws TelegramApiException, JsonProcessingException {
        try {
            Update update = objectMapper.readValue(jsonRequestBody, Update.class);
            log.info("Received request:\n" + objectMapper.writeValueAsString(update));

            if (update.hasMessage() && update.getMessage().hasText()) {

                switch (CommandType.findById(update.getMessage().getText().toLowerCase())) {
                    case START:
                        StartCommand startCommand = StartCommand.builder()
                                .userId(update.getMessage().getChat().getId())
                                .chatId(update.getMessage().getChatId().toString())
                                .firstName(update.getMessage().getChat().getFirstName())
                                .username(update.getMessage().getFrom().getUserName())
                                .build();
                        service.handleStartCommand(startCommand);
                        break;
                    case HISTORY:
                        ActionHistoryCommand actionHistoryCommand = ActionHistoryCommand.builder()
                                .chatId(update.getMessage().getChatId().toString())
                                .userId(Math.toIntExact(update.getMessage().getChat().getId()))
                                .build();
                        service.handleActionHistoryCommand(actionHistoryCommand);

                        break;
                    case PRISE:
                        PriceCommand priceCommand = new PriceCommand();
                        priceCommand.setChatId(update.getMessage().getChatId().toString());
                        priceCommand.setUserId(update.getMessage().getFrom().getId());
                        try {
                            service.handlePriceCommand(priceCommand);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case TIMESLOT:

                        break;
                    case BASKET:
                        BasketCommand basketCommand = new BasketCommand();
                        basketCommand.setChatId(update.getMessage().getChatId().toString());
                        basketCommand.setUserid(update.getMessage().getChat().getId());
                        service.handleBasketCommand(basketCommand);
                        break;
                    case CHECKOUT:
                        CheckoutCommand checkoutCommand = new CheckoutCommand();
                        checkoutCommand.setChatId(update.getMessage().getChatId().toString());
                        checkoutCommand.setUserid(update.getMessage().getChat().getId().toString());
                        checkoutCommand.setUsername(update.getMessage().getFrom().getUserName());
                        service.handleCheckoutCommand(checkoutCommand);
                        break;
                    case ADDITEM:
////
                }
            } else if (update.hasCallbackQuery()) {
                String [] strings = update.getCallbackQuery().getData().split(":");
                if (strings[0].equalsIgnoreCase("GET_INFO")){
                    GetItemInfoCommand getItemInfoCommand = new GetItemInfoCommand();
                    getItemInfoCommand.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
                    getItemInfoCommand.setItemId(strings[1]);
                    service.handleGetInfoCommand(getItemInfoCommand);

                } else if (strings[0].equalsIgnoreCase("ADD_TO_CART")) {
                    AddItemCommand addItemCommand = new AddItemCommand();
                    addItemCommand.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
                    if (update.getCallbackQuery().getFrom().getId() != null) {
                        addItemCommand.setUserId(Long.valueOf(update.getCallbackQuery().getFrom().getId()));
                    } else {
                        addItemCommand.setUserId(Long.valueOf(update.getCallbackQuery().getMessage().getChat().getId()));
                    }
//                addItemCommand.setOrderId();
                    addItemCommand.setItemId(Long.valueOf(strings[2]));
                    service.handleAddItem(addItemCommand);
                } else if (strings[0].equalsIgnoreCase("DELETE")) {
                    DelItemCommand delItemCommand = new DelItemCommand();
                    delItemCommand.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
                    if (update.getCallbackQuery().getFrom().getId() != null) {
                        delItemCommand.setUserId(Long.valueOf(update.getCallbackQuery().getFrom().getId()));
                    } else {
                        delItemCommand.setUserId(Long.valueOf(update.getCallbackQuery().getMessage().getChat().getId()));
                    }
                    delItemCommand.setItemId(Long.valueOf(strings[1]));
                    service.handleDelItem(delItemCommand);
                }
//            String cbd = update.getCallbackQuery().getData();
//            String[] strings = cbd.split(":");
//            String orderId = strings[1];
//            String itemId = strings[2];
//            AddItemToCartCommand addItemToCartCommand = new AddItemToCartCommand();
//            String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
//            String userId = update.getCallbackQuery().getId();
//            addItemToCartCommand.setOrderId(orderId);
//            addItemToCartCommand.setItemId(itemId);
//            addItemToCartCommand.setChatId(chatId);
//            addItemToCartCommand.setUserId(userId);
//            service.handleAddItemToCartCommand(addItemToCartCommand);

            }
        } catch (Exception e) {


        }


    }
}

