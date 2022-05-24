package ru.csv.order_management.sender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class Sender extends DefaultAbsSender {
    @Value("${bot.token}")
    private String token;

    protected Sender() {
        super(new DefaultBotOptions());
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public void send(SendMessage sendMessage) throws TelegramApiException {
        execute(sendMessage);
    }

    public void sendList(List<SendMessage> sendMessageList) throws TelegramApiException {
        for (SendMessage sendMessage : sendMessageList) {
            execute(sendMessage);
        }
    }
}