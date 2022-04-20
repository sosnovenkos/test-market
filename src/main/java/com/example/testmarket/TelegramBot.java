package com.example.testmarket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Random;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botUserName;

    @Value("${bot.token}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            Random rd = new Random();
            String message = String.valueOf(rd.nextInt(100));
            SendMessage x = new SendMessage();
                    x.setChatId(update.getMessage().getChatId().toString());
            x.setText(message);
            execute(x);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
}
