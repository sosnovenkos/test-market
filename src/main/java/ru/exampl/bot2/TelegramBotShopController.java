package ru.exampl.bot2;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RestController
@AllArgsConstructor
public class TelegramBotShopController {
    @PostMapping("/1")
    public String onUpdateReceived(@RequestBody Update update){

//        log.info(update.toString());

        return update.getUpdateId().toString();
    }
}
