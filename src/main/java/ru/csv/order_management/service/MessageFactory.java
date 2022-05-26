package ru.csv.order_management.service;

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
import ru.csv.order_management.App;
import ru.csv.order_management.domain.command.*;
import ru.csv.order_management.store.entity.DbEntityOrder;
import ru.csv.order_management.store.entity.DbEntityItems;
import ru.csv.order_management.store.entity.DbEntityTimeslot;
import wiremock.com.github.jknack.handlebars.internal.antlr.misc.Interval;

import java.time.OffsetDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@AllArgsConstructor
public class MessageFactory {

    public List<SendMessage> createMessageForGroupList(PriceCommand command, List<DbEntityItems> items) {
        log.info("Create Messages For Groups: ." + command.getClass().getSimpleName() + " - " + command.getId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Выберите раздел");
        sendMessage.setChatId(command.chatId);
        List<SendMessage> sendMessageList = new ArrayList<>();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        items.forEach(item -> {
            var inlineKeyboardButton = new InlineKeyboardButton(item.getName() + " " + item.getDescription() + " " + item.getPrice() + " руб.");
            var callbackData = "FIND_CHILD:" + ":" + item.getId();
            inlineKeyboardButton.setCallbackData(callbackData);
            inlineKeyboardButton.getSwitchInlineQuery();
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            keyboardButtonsRow.add(inlineKeyboardButton);
            rowList.add(keyboardButtonsRow);
                }
        );
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessageList.add(sendMessage);
        log.info("Created Messages: .FIND_CHILD command: " + sendMessageList.size());
        return sendMessageList;
    }

    public List<SendMessage> createMessageForItemsList(FindChildCommand command, DbEntityOrder order, List<DbEntityItems> items){
        log.info("Create Messages For Items");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Нажимайте на кнопки и добавляйте позиции в корзину");
        sendMessage.setChatId(command.chatId);
        List<SendMessage> sendMessageList = new ArrayList<>();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
       items.forEach(item -> {
                   var inlineKeyboardButton = new InlineKeyboardButton(item.getName() + " " + item.getDescription() + " " + item.getPrice() + " руб.");
                   var cbd = "ADD_TO_CART:" + order.getId() + ":" + item.getId();
                   inlineKeyboardButton.setCallbackData(cbd);
                   inlineKeyboardButton.getSwitchInlineQuery();
                   List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
                   keyboardButtonsRow1.add(inlineKeyboardButton);
                   rowList.add(keyboardButtonsRow1);
               }
        );
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessageList.add(sendMessage);
        log.info("Created Messages: .ADD_TO_CART command: " + sendMessageList.size());
        return sendMessageList;
    }

    public List<SendMessage> createMessageForChooseDate(ChooseDateCommand command, OffsetDateTime date){
        log.info("createMessageForChooseDate");
        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(command.chatId);
        sendMessage.setText("Выберите желаемую дату");
        List<SendMessage> sendMessageList = new ArrayList<>();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(
                    date.plusDays(i+1).getDayOfWeek().getDisplayName(TextStyle.FULL,
                            Locale.getDefault())
                            + " " + date.plusDays(i+1).getDayOfMonth()
                            + " " + date.plusDays(i+1).getMonth().getDisplayName(TextStyle.FULL,
                            Locale.getDefault())
                            + " " + date.plusDays(i+1).getYear()
            );
            var cbd = "CHOOSE_TIME:" + date.plusDays(i+1).toInstant().toEpochMilli() + ":" + date.plusDays(i+1).getDayOfWeek().getValue();
            log.info("CHOOSE_TIME:" + date.plusDays(i+1).toInstant().toEpochMilli()+ ":" + date.plusDays(i+1).getDayOfWeek().getValue());
            inlineKeyboardButton.setCallbackData(cbd);
            inlineKeyboardButton.getSwitchInlineQuery();
            List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
            keyboardButtonsRow1.add(inlineKeyboardButton);
            rowList.add(keyboardButtonsRow1);
        }
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessageList.add(sendMessage);
        log.info("sendMessageList size" + sendMessageList.size());
        return sendMessageList;
    }

    public List<SendMessage> createMessageForChooseTime(ChooseTimeCommand command, List<DbEntityTimeslot> timeslots){
        log.info("createMessageForChooseTime");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(command.chatId);
        sendMessage.setText(command.getDate().getDayOfWeek().getDisplayName(TextStyle.FULL,
                Locale.getDefault()) + " " + command.getDate().getDayOfMonth() + " " + command.getDate().getMonth().getDisplayName(TextStyle.FULL,
                Locale.getDefault()) + "\n" + "Выберите время");
        List<SendMessage> sendMessageList = new ArrayList<>();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (int i = 0; i < timeslots.size(); i++) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(
                    timeslots.get(i).getTimeslot()
            );
            var cbd = "ADD_TO_CART_FOR_ENTRY:" + command.getDate().toString().split("T")[0] + ":" + timeslots.get(i).getTimeslot() + ":" + timeslots.get(i).getId();
            log.info("ADD_TO_CART_FOR_ENTRY:" + command.getDate().toString().split("T")[0] + ":" + timeslots.get(i).getTimeslot() + ":" + timeslots.get(i).getId());

            inlineKeyboardButton.setCallbackData(cbd);
            inlineKeyboardButton.getSwitchInlineQuery();
            List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
            keyboardButtonsRow1.add(inlineKeyboardButton);
            rowList.add(keyboardButtonsRow1);
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
        sendMessage.setText("Нажимайте на кнопки и удляйте позиции из корзины");
        sendMessage.setChatId(basketCommand.getChatId());
        List<SendMessage> sendMessageList = new ArrayList<>();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(
                    items.get(i).getName() + " " + items.get(i).getDescription() + " " + items.get(i).getPrice() + " руб.");
            var cbd = "DEL_ITEM:" + items.get(i).getId().toString();
            inlineKeyboardButton.setCallbackData(cbd);
            inlineKeyboardButton.getSwitchInlineQuery();
            List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
            List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
            keyboardButtonsRow1.add(inlineKeyboardButton);
            rowList.add(keyboardButtonsRow1);
            rowList.add(keyboardButtonsRow2);
        }

        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessageList.add(sendMessage);
        log.info("Created Messages: .DEL_ITEM command: " + sendMessageList.size());
        return sendMessageList;
    }

    public SendMessage createReplyToStartCommand(StartCommand command) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage(command.chatId, command.firstName + ", добрый день!");
        KeyboardButton history = new KeyboardButton("История");
        KeyboardButton price = new KeyboardButton("Прайс");
        KeyboardButton basket = new KeyboardButton("Корзина");
        KeyboardButton checkout = new KeyboardButton("Оформить заказ");
        KeyboardButton choseTime = new KeyboardButton("Выбрать время");
        List<KeyboardButton> keyboardButtons1 = new ArrayList<>();
        keyboardButtons1.add(price);
        keyboardButtons1.add(choseTime);
        keyboardButtons1.add(basket);
        List<KeyboardButton> keyboardButtons2 = new ArrayList<>();
        keyboardButtons2.add(checkout);
        keyboardButtons2.add(history);
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
