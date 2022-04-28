package ru.exampl.bot2.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.exampl.bot2.domain.command.StartCommand;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageFactory {

    public SendMessage createUser(StartCommand command) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage(command.chatId, command.userName + ", добрый день!");
        KeyboardButton history = new KeyboardButton("История");
        KeyboardButton menu = new KeyboardButton("Меню");
        List<KeyboardButton> keyboardButtons1 = new ArrayList<>();
        keyboardButtons1.add(history);
        keyboardButtons1.add(menu);
        KeyboardRow keyboardRow1 = new KeyboardRow(keyboardButtons1);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow1);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
       return sendMessage;
    }

    public SendMessage createAdmin (StartCommand command){
        SendMessage sendMessage = new SendMessage(command.chatId, command.userName);
        KeyboardButton history = new KeyboardButton("История");
        KeyboardButton menu = new KeyboardButton("Меню");
        KeyboardButton addItem = new KeyboardButton("Добавить товар");
        KeyboardButton deleteItem = new KeyboardButton("Удалить товар");
        KeyboardButton orders = new KeyboardButton("Посмотреть заказы");
        KeyboardButton numberOfVisit = new KeyboardButton("Кол-во посещений");
        List<KeyboardButton> keyboardButtons1 = new ArrayList<>();
        keyboardButtons1.add(history);
        keyboardButtons1.add(menu);
        List<KeyboardButton> keyboardButtons2 = new ArrayList<>();
        keyboardButtons2.add(addItem);
        keyboardButtons2.add(deleteItem);
        List<KeyboardButton> keyboardButtons3 = new ArrayList<>();
        keyboardButtons3.add(orders);
        keyboardButtons3.add(numberOfVisit);
        KeyboardRow keyboardRow1 = new KeyboardRow(keyboardButtons1);
        KeyboardRow keyboardRow2 = new KeyboardRow(keyboardButtons2);
        KeyboardRow keyboardRow3 = new KeyboardRow(keyboardButtons3);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    public SendMessage createManager (StartCommand command){
        SendMessage sendMessage = new SendMessage(command.chatId, command.userName);
        KeyboardButton history = new KeyboardButton("История");
        KeyboardButton menu = new KeyboardButton("Меню");
        KeyboardButton addItem = new KeyboardButton("Добавить товар");
        KeyboardButton deleteItem = new KeyboardButton("Удалить товар");
        List<KeyboardButton> keyboardButtons1 = new ArrayList<>();
        keyboardButtons1.add(history);
        keyboardButtons1.add(menu);
        List<KeyboardButton> keyboardButtons2 = new ArrayList<>();
        keyboardButtons2.add(addItem);
        keyboardButtons2.add(deleteItem);
        KeyboardRow keyboardRow1 = new KeyboardRow(keyboardButtons1);
        KeyboardRow keyboardRow2 = new KeyboardRow(keyboardButtons2);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
}
