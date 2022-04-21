package ru.exampl.bot2;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BotCommandService {
    private final Sender sender;

    public void handle(StartCommand command) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage(command.chatId, "Выбирите меню");
        KeyboardButton history = new KeyboardButton("History");
        KeyboardButton menu = new KeyboardButton("Menu");
        List<KeyboardButton> keyboardButtons = new ArrayList<>();
        keyboardButtons.add(history);
        keyboardButtons.add(menu);
        KeyboardRow keyboardRow = new KeyboardRow(keyboardButtons);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sender.send(sendMessage);
    }
}
