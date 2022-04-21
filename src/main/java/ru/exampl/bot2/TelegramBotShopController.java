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
    private final ApplicationContext context;
    @PostMapping("/1")
    public String onUpdateReceived(@RequestBody Update update) throws TelegramApiException {
        log.info(context.toString());
//        log.info(update.toString());
        if (update.getMessage().getText().equals("start")) {
            StartCommand command = StartCommand.builder().build();
            service.handle(command);
        }
        return "";
    }
}
