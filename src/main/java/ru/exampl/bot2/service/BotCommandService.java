package ru.exampl.bot2.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.exampl.bot2.domain.Order;
import ru.exampl.bot2.sender.Sender;
import ru.exampl.bot2.domain.command.HistoryCommand;
import ru.exampl.bot2.domain.Item;
import ru.exampl.bot2.domain.command.MenuCommand;
import ru.exampl.bot2.domain.command.StartCommand;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BotCommandService {
    private final Sender sender;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public void handleStart(StartCommand command) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage(command.chatId, command.userName + ", добрый день!");
        sender.send(sendMessage);
        KeyboardButton history = new KeyboardButton("History");
        KeyboardButton menu = new KeyboardButton("Menu");
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
        sender.send(sendMessage);
    }

    public void handleHistory(HistoryCommand command) throws TelegramApiException {
        List<Order> orders = orderRepository.findByUserId(command.userid);
        SendMessage sendMessage = new SendMessage(command.chatId, /*command.userid*/"вы потратили очень много");
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        int j = 1;
        for (int i = 0; i < orders.size(); i++) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton("Order - " + j);
            inlineKeyboardButton.setCallbackData(String.valueOf(j));
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            keyboardButtonsRow.add(inlineKeyboardButton);
            rowList.add(keyboardButtonsRow);
            System.out.println(j);
            j++;
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(rowList);
//        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
//        sendMessage.setText(orders.toString());
        sender.send(sendMessage);
    }

    public void handleMenu(MenuCommand command) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage(command.chatId, /*command.getChatId()*/"хватит тратить");
        sender.send(sendMessage);
    }

    public void handleOrdersItems (String chatId, String userid, int orderNumber) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        List<Item> items = itemRepository.findByOrderNumber(userid, orderNumber);
        for (int i = 0; i < items.size(); i++) {
//            System.out.println(items.get(i).toString());
//            for (int j = 0; j < ; j++) {
//
//            }
            sendMessage.setText(items.get(i).name + items.get(i).price);
            sender.send(sendMessage);

        }
//        sendMessage.setText(items.toString());
//        sender.send(sendMessage);
    }

}
