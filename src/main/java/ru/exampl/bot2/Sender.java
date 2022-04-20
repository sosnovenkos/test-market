package ru.exampl.bot2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Sender extends DefaultAbsSender {
    @Value("${bot.token}")
    private String token;

    protected Sender() {
        super(new DefaultBotOptions());
    }

    @Override
    public String getBotToken() {
        return null;
    }

    public void send() throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        execute(sendMessage);
    }
}