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
import ru.csv.order_management.domain.command.*;
import ru.csv.order_management.store.entity.DbEntityAddress;
import ru.csv.order_management.store.entity.DbEntityItems;
import ru.csv.order_management.store.entity.DbEntityOrder;
import ru.csv.order_management.store.entity.DbEntityUser;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@AllArgsConstructor
public class MessageFactory {
    private final List<String> PRODUCT_CODE_AS_BACK = List.of("14", "15", "16");

    public List<SendMessage> createMessageForGroupList(PriceCommand command, List<DbEntityItems> items) {
        log.info("Create Messages For Groups: ." + command.getClass().getSimpleName() + " - " + command.getId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Выберите раздел");
        sendMessage.setChatId(command.chatId);
        List<SendMessage> sendMessageList = new ArrayList<>();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        items.forEach(item -> {
            var inlineKeyboardButton = new InlineKeyboardButton(item.getName());
            var callbackData = "FIND_CHILD:" + item.getProductCode();
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
            InlineKeyboardButton inlineKeyboardButton;
            String callbackData;
            if (PRODUCT_CODE_AS_BACK.contains(item.getProductCode())) {
                inlineKeyboardButton = new InlineKeyboardButton(item.getName());
                callbackData = "price:";
            } else {
                inlineKeyboardButton = new InlineKeyboardButton(item.getName() + " " + item.getDescription() + " гр. " + item.getPrice() + " руб.");
                callbackData = "ADD_TO_CART:" + order.getId() + ":" + item.getId();
            }
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
        log.info("Created Messages: .ADD_TO_CART command: " + sendMessageList.size());
        return sendMessageList;
    }

    public List<SendMessage> createMessageForBasket (BasketCommand command, List<DbEntityItems> items){
        log.info("Create message for Basket: " + command.getUserId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Нажимайте на кнопки и удляйте позиции из корзины");
        sendMessage.setChatId(command.getChatId());
        List<SendMessage> sendMessageList = new ArrayList<>();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(
                    items.get(i).getName() + " " + items.get(i).getDescription() + " " + items.get(i).getPrice() + " руб.");
            var cbd = "DEL_ITEM:" + items.get(i).getId().toString();
            inlineKeyboardButton.setCallbackData(cbd);
            inlineKeyboardButton.getSwitchInlineQuery();
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            keyboardButtonsRow.add(inlineKeyboardButton);
            rowList.add(keyboardButtonsRow);
        }

        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessageList.add(sendMessage);
        log.info("Created Messages: .DEL_ITEM command: " + sendMessageList.size());

        SendMessage sendInfoMessage = new SendMessage();
        var sum = items.stream().filter(item -> nonNull(item.getPrice())).mapToLong(DbEntityItems::getPrice).sum();
        var weight = items.stream().filter(item -> nonNull(item.getPrice())).mapToLong(item -> Long.parseLong(item.getDescription())).sum();
        sendInfoMessage.setText("Сумма заказа: " + sum + " руб. Вес заказа: " + weight / 1000.0 + " кг.");
        sendInfoMessage.setChatId(command.chatId);
        sendMessageList.add(sendInfoMessage);

        return sendMessageList;
    }

    public SendMessage createReplyToStartCommand(StartCommand command) {
        SendMessage sendMessage = new SendMessage(command.chatId, command.firstName + ", добрый день!");
        KeyboardButton history = new KeyboardButton("История");
        KeyboardButton price = new KeyboardButton("Прайс");
        KeyboardButton basket = new KeyboardButton("Корзина");
        KeyboardButton checkout = new KeyboardButton("Оформить заказ");
        List<KeyboardButton> keyboardButtons1 = new ArrayList<>();
        keyboardButtons1.add(price);
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

    public List<SendMessage> createMessageForOrdering(CheckoutCommand command, List<DbEntityAddress> addresses, DbEntityOrder order) {
        log.info("Create message for Ordering: " + command.getUserId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Выберите ваши адреса доставки или создайте новый: ");
        sendMessage.setChatId(command.getChatId());

        List<SendMessage> sendMessageList = new ArrayList<>();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        addresses.forEach( address -> {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(address.getPhone() + " : " + address.getDescription());
            var cbd = "SET_ADDR:" + order.getId() + ":" + address.getId();
            inlineKeyboardButton.setCallbackData(cbd);
            inlineKeyboardButton.getSwitchInlineQuery();
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            keyboardButtonsRow.add(inlineKeyboardButton);
            rowList.add(keyboardButtonsRow);
        }
        );

        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(".Новый адрес");
        var cbd = "ADD_ADDR:";
        inlineKeyboardButton.setCallbackData(cbd);
        inlineKeyboardButton.getSwitchInlineQuery();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(inlineKeyboardButton);
        rowList.add(keyboardButtonsRow);

        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessageList.add(sendMessage);
        log.info("Created Messages: .SET_ADDR command: " + sendMessageList.size());
        return sendMessageList;
    }
}
