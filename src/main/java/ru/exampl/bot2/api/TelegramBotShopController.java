package ru.exampl.bot2.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.exampl.bot2.domain.command.MenuCommand;
import ru.exampl.bot2.sender.Sender;
import ru.exampl.bot2.domain.CommandType;
import ru.exampl.bot2.domain.command.ActionHistoryCommand;
import ru.exampl.bot2.domain.command.StartCommand;
import ru.exampl.bot2.service.BotCommandService;

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
                    MenuCommand menuCommand = MenuCommand.builder()
                            .chatId(update.getMessage().getChatId().toString())
                            .build();
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
        } else if (update.hasCallbackQuery()){
            SendMessage sendMessage = new SendMessage();
            String orderNumber = update.getCallbackQuery().getData();
            String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
            String userId = update.getCallbackQuery().getId();
            sendMessage.setText(orderNumber);
            sendMessage.setChatId(chatId);
            System.out.println(sendMessage);
            sender.send(sendMessage);
            service.handleOrdersItems(chatId, userId, Integer.parseInt(orderNumber));


//            try {
//                execute(new SendMessage().setText(
//                                update.getCallbackQuery().getData())
//                        .setChatId(update.getCallbackQuery().getMessage().getChatId().toString()));
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
        }
//            StartCommand command = new StartCommand();
//            command.setChatId(update.getMessage().getChatId().toString());
    }
}

