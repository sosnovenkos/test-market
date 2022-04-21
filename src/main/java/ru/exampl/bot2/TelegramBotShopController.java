package ru.exampl.bot2;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@RestController
@AllArgsConstructor
public class TelegramBotShopController{
    private final BotCommandService service;
    @PostMapping("/1")
    public void onUpdateReceived(@RequestBody Update update) throws TelegramApiException {
//        service.handle(update.getMessage().getChatId().toString(), update.getMessage().getText());
//        log.info(context.toString());
        log.info("Received update with message"+update.getMessage().getText());
        if (update.getMessage().getText().equalsIgnoreCase("start")) {
            StartCommand command = StartCommand.builder()
                    .chatId(update.getMessage().getChatId().toString())
                    .userName(update.getMessage().getChat().getUserName())
                    .build();
//            StartCommand command = new StartCommand();
//            command.setChatId(update.getMessage().getChatId().toString());
            service.handle(command);
        }
    }
}
