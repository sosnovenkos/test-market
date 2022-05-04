package ru.exampl.bot2.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.exampl.bot2.domain.command.AddItemToCartCommand;
import ru.exampl.bot2.domain.command.MenuCommand;
import ru.exampl.bot2.sender.Sender;
import ru.exampl.bot2.domain.CommandType;
import ru.exampl.bot2.domain.command.ActionHistoryCommand;
import ru.exampl.bot2.domain.command.StartCommand;
import ru.exampl.bot2.service.BotCommandService;

import java.util.ArrayList;

//import static jdk.javadoc.internal.tool.Main.execute;

@Slf4j
@RestController
@AllArgsConstructor
public class TelegramBotShopController {
    private final BotCommandService service;
    private final Sender sender;

    @PostMapping("/1")
    public void onUpdateReceived(@RequestBody Update update) throws TelegramApiException, JsonProcessingException {
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
                            .userId(Math.toIntExact(update.getMessage().getChat().getId()))
                            .chatId(update.getMessage().getChatId().toString())
                            .userName(update.getMessage().getChat().getUserName())
                            .build();
                    service.handleStart(startCommand);
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

                    service.handleMenu(menuCommand);
//                    System.out.println("!!!!!!!!!!!!!!");
//                    SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(),/*command.userid*/"хватит ТРАТИТЬ!!!!]");
//                    sender.send(sendMessage);
//                    sendMessage.setText(/*command.userid*/"хватит ТРАТИТЬ!!!!]");
                    break;
                case ADDITEM:
////                    sendMessage.setChatId(update.getMessage().getChatId().toString());
//                    sendMessage.setText(/*command.userid*/"Добавьте товар");
//                    sender.send(sendMessage);


            }
        } else if (update.hasCallbackQuery()) {
//            if (update.getCallbackQuery().hashCode()){
//
//            }
            String cbd = update.getCallbackQuery().getData();
            String[] strings = cbd.split(":");
            String orderId = strings[1];
            String itemId = strings[2];
            AddItemToCartCommand addItemToCartCommand = new AddItemToCartCommand();
            String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
            String userId = update.getCallbackQuery().getId();
            addItemToCartCommand.setOrderId(orderId);
            addItemToCartCommand.setItemId(itemId);
            addItemToCartCommand.setChatId(chatId);
            addItemToCartCommand.setUserId(userId);
//            service.handleAddItemToCartCommand(addItemToCartCommand);

        }
    }
}

