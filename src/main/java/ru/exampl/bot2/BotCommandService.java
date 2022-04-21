package ru.exampl.bot2;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@AllArgsConstructor
public class BotCommandService {
    private final Sender sender;
    public void handle(StartCommand command) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sender.send(sendMessage);
    }
}
