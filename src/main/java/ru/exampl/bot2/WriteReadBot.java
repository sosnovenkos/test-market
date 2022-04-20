package ru.exampl.bot2;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

import javax.websocket.MessageHandler;
import java.io.IOException;

@Getter
@Setter
@FieldDefaults (level = AccessLevel.PRIVATE)
public class WriteReadBot extends SpringWebhookBot {

    MessageHandler messageHandler;

    public WriteReadBot(SetWebhook setWebhook, MessageHandler messageHandler) {
        super(setWebhook);
        this.messageHandler = messageHandler;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
//        return new TelegramBotShopController().onUpdateReceived(update);
        try {
            return handleUpdate(update);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BotApiMethod<?> handleUpdate(Update update) throws IOException {
        if (update.hasMessage()){
            Message message = update.getMessage();
            if (message != null) {
                return (BotApiMethod<?>) messageHandler;
            }
        }
            return null;
    }

    @Override
    public String getBotPath() {
        return null;
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }
}
