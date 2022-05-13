package ru.exampl.bot2.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.exampl.bot2.domain.command.BasketCommand;
import ru.exampl.bot2.domain.command.MenuCommand;
import ru.exampl.bot2.domain.command.StartCommand;
import ru.exampl.bot2.store.entity.DbEntityItems;
import ru.exampl.bot2.store.entity.DbEntityOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class MessageFactory {

    public List<SendMessage> createMessageForItemsList(MenuCommand command, List<DbEntityItems> items){
        log.info("createMessageForItemsList");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Нажимайте на кнопки для добавления в корзину");
        sendMessage.setChatId(command.chatId);
        List<SendMessage> sendMessageList = new ArrayList<>();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(
                    items.get(i).getName() + "\n" + items.get(i).getDescription() + "\n" + items.get(i).getPrice());
            var cbd = "ADD_TO_CART:" + command.getOrderId() + ":" + items.get(i).getId().toString();
            var getInfo = "GET_INFO:" + items.get(i).getId().toString();
            inlineKeyboardButton.setCallbackData(getInfo);
            inlineKeyboardButton.getSwitchInlineQuery();
            InlineKeyboardButton inlineKeyboardButtonPlus = new InlineKeyboardButton("+");
            inlineKeyboardButtonPlus.setCallbackData(cbd);
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            keyboardButtonsRow.add(inlineKeyboardButton);
            keyboardButtonsRow.add(inlineKeyboardButtonPlus);
            rowList.add(keyboardButtonsRow);
        }
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessageList.add(sendMessage);
        log.info("sendMessageList size" + sendMessageList.size());
        return sendMessageList;
    }

    public List<SendMessage> createMessageForBasket (BasketCommand basketCommand, List<DbEntityItems> items){
        log.info("createMessageForBasket");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Проверьте заказ");
        sendMessage.setChatId(basketCommand.getChatId());
        List<SendMessage> sendMessageList = new ArrayList<>();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(
                    items.get(i).getName() /*+ "\n" + items.get(i).getDescription() + "\n" + items.get(i).getPrice()*/);
            var cbd = "DELETE:"/* + basketCommand.getOrderId() + ":" + items.get(i).getId().toString()*/;
            var getInfo = "GET_INFO:" + items.get(i).getId().toString();
            inlineKeyboardButton.setCallbackData(getInfo);
            inlineKeyboardButton.getSwitchInlineQuery();
            InlineKeyboardButton inlineKeyboardButtonMinus = new InlineKeyboardButton("del");
            inlineKeyboardButtonMinus.setCallbackData(cbd);
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            keyboardButtonsRow.add(inlineKeyboardButton);
            keyboardButtonsRow.add(inlineKeyboardButtonMinus);
            rowList.add(keyboardButtonsRow);
        }
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessageList.add(sendMessage);
        log.info("sendMessageList size" + sendMessageList.size());
        return sendMessageList;
    }

    public SendMessage createUser(StartCommand command) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage(command.chatId, command.firstName + ", добрый день!");
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
        SendMessage sendMessage = new SendMessage(command.chatId, command.firstName);
        KeyboardButton history = new KeyboardButton("История");
        KeyboardButton menu = new KeyboardButton("Меню");
        KeyboardButton basket = new KeyboardButton("Корзина");
        KeyboardButton addItem = new KeyboardButton("Добавить товар");
        KeyboardButton deleteItem = new KeyboardButton("Удалить товар");
        KeyboardButton orders = new KeyboardButton("Посмотреть заказы");
        KeyboardButton numberOfVisit = new KeyboardButton("Кол-во посещений");
        List<KeyboardButton> keyboardButtons1 = new ArrayList<>();
        keyboardButtons1.add(history);
        keyboardButtons1.add(menu);
        List<KeyboardButton> keyboardButtons2 = new ArrayList<>();
        keyboardButtons2.add(basket);
        List<KeyboardButton> keyboardButtons3 = new ArrayList<>();
        keyboardButtons3.add(addItem);
        keyboardButtons3.add(deleteItem);
        List<KeyboardButton> keyboardButtons4 = new ArrayList<>();
        keyboardButtons4.add(orders);
        keyboardButtons4.add(numberOfVisit);
        KeyboardRow keyboardRow1 = new KeyboardRow(keyboardButtons1);
        KeyboardRow keyboardRow2 = new KeyboardRow(keyboardButtons2);
        KeyboardRow keyboardRow3 = new KeyboardRow(keyboardButtons3);
        KeyboardRow keyboardRow4 = new KeyboardRow(keyboardButtons4);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);
        keyboardRows.add(keyboardRow4);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    public SendMessage createManager (StartCommand command){
        SendMessage sendMessage = new SendMessage(command.chatId, command.firstName);
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
