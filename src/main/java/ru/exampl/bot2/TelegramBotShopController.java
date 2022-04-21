package ru.exampl.bot2;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.exampl.bot2.command.Command;
import ru.exampl.bot2.command.HistoryCommand;
import ru.exampl.bot2.command.StartCommand;

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
        Message message = update.getMessage();
        if (message != null && message.hasText()
//                update.getMessage().getText().equalsIgnoreCase("start")
    ) {
            switch (Command.findById(message.getText().toLowerCase())){
                case START:
                    StartCommand startCommand = StartCommand.builder()
                            .chatId(update.getMessage().getChatId().toString())
                            .userName(update.getMessage().getChat().getUserName())
                            .build();
                    service.handleStart(startCommand);
                    break;
                case HISTORY:
                    HistoryCommand historyCommand = HistoryCommand.builder()
                            .chatId(update.getMessage().getChatId().toString())
                            .userid(update.getMessage().getChat().getId().toString())
                            .build();
                    service.handleHistory(historyCommand);
                    break;
                case MENU:
                    System.out.println("!!!!!!!!!!!!!!");
                    break;

            }
//            StartCommand command = new StartCommand();
//            command.setChatId(update.getMessage().getChatId().toString());
        }
    }
}
