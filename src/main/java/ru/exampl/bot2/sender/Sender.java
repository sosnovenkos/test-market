package ru.exampl.bot2.sender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
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
//        SendMessage sendMessage = new SendMessage(chatId, s);
        execute(sendMessage);
//        System.out.println(message + token);
//        return message.toString();
    }

    public void sendList(List<SendMessage> sendMessageList) throws TelegramApiException {
//        SendMessage sendMessage = new SendMessage();
        for (int i = 0; i < sendMessageList.size(); i++) {
            execute(sendMessageList.get(i));
        }
//        System.out.println(message + token);
//        return message.toString();
    }
}