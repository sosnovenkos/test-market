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

import java.util.UUID;

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

        Update update = objectMapper.readValue(jsonRequestBody, Update.class);
        log.info("Received request:\n" + objectMapper.writeValueAsString(update));
//        service.handle(update.getMessage().getChatId().toString(), update.getMessage().getText());
//        log.info(context.toString());
//        log.info("Received update with message" + update.getMessage().getText());

        if (update.hasMessage() && update.getMessage().hasText()) {
//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setChatId(update.getMessage().getChatId().toString());
//            System.out.println(update.getMessage());
            switch (CommandType.findById(update.getMessage().getText().toLowerCase())) {
                case START:
                    StartCommand startCommand = StartCommand.builder()
                            .userId(update.getMessage().getChat().getId())
                            .chatId(update.getMessage().getChatId().toString())
                            .firstName(update.getMessage().getChat().getFirstName())
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
                case MENU:
                    MenuCommand menuCommand = new MenuCommand();
                    menuCommand.setChatId(update.getMessage().getChatId().toString());
//                    menuCommand.setUserId(update.getMessage().getFrom().getId());
                    service.handleMenuCommand(menuCommand);
//                    System.out.println("!!!!!!!!!!!!!!");
//                    SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(),/*command.userid*/"хватит ТРАТИТЬ!!!!]");
//                    sender.send(sendMessage);
//                    sendMessage.setText(/*command.userid*/"хватит ТРАТИТЬ!!!!]");
                    break;
                case BASKET:
                    BasketCommand basketCommand = new BasketCommand();
                    basketCommand.setChatId(update.getMessage().getChatId().toString());
                    basketCommand.setUserid(update.getMessage().getChat().getId().toString());
                    basketCommand.setOrderId("3228f2aa-f2da-48ee-965c-efb1a5972f44");
                    service.handleBasketCommand(basketCommand);
                    break;
                case ADDITEM:
////                    sendMessage.setChatId(update.getMessage().getChatId().toString());
//                    sendMessage.setText(/*command.userid*/"Добавьте товар");
//                    sender.send(sendMessage);



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
//                addItemCommand.setUserId(update.getCallbackQuery());
//                addItemCommand.setOrderId();
                addItemCommand.setItemId(UUID.fromString(strings[2]));
                service.handleAddItem(addItemCommand);
            } else if (strings[0].equalsIgnoreCase("DELETE")) {
                DelItemCommand delItemCommand = new DelItemCommand();
                delItemCommand.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
                delItemCommand.setItemId(UUID.fromString(strings[1]));
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
    }
}

