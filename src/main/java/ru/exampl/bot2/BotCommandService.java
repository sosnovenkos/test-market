package ru.exampl.bot2;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.exampl.bot2.command.HistoryCommand;
import ru.exampl.bot2.command.MenuCommand;
import ru.exampl.bot2.command.StartCommand;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BotCommandService {
    private final Sender sender;
    private final OrderRepository repository;

    public void handleStart(StartCommand command) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage(command.chatId, command.userName + ", добрый день!");
        KeyboardButton history = new KeyboardButton("History");
        KeyboardButton menu = new KeyboardButton("Menu");
        List<KeyboardButton> keyboardButtons = new ArrayList<>();
        keyboardButtons.add(history);
        keyboardButtons.add(menu);
        KeyboardRow keyboardRow = new KeyboardRow(keyboardButtons);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sender.send(sendMessage);
    }

    public void handleHistory(HistoryCommand command) throws TelegramApiException {
        List<Order> orders = repository.findByUserId(command.userid);
        SendMessage sendMessage = new SendMessage(command.chatId, command.userid);

        sender.send(sendMessage);
    }

    public void handleMenu(MenuCommand command) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage(command.chatId, command.getChatId());
        sender.send(sendMessage);
    }
}
