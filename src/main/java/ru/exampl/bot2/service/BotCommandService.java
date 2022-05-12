package ru.exampl.bot2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.exampl.bot2.domain.Order;
import ru.exampl.bot2.domain.command.*;
import ru.exampl.bot2.sender.Sender;
import ru.exampl.bot2.store.ActionRepositoryImpl;
import ru.exampl.bot2.store.ItemRepositoryImpl;
import ru.exampl.bot2.store.OrderRepositoryImpl;
import ru.exampl.bot2.store.entity.DbEntityItems;
import ru.exampl.bot2.store.entity.DbEntityOrders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//import static org.graalvm.compiler.phases.util.GraphOrder.createOrder;

@Slf4j
@Service
@AllArgsConstructor
public class BotCommandService {
    private final Sender sender;
    private final OrderRepositoryImpl orderRepository;
    private final ItemRepositoryImpl itemRepository;
    private final MessageFactory messageFactory;
    private final ActionRepositoryImpl actionRepositoryImpl;



    public void handleStartCommand(StartCommand command) throws TelegramApiException, JsonProcessingException {

        var message = messageFactory.createAdmin(command);
        actionRepositoryImpl.save(command);
        sender.send(message);



    }

    public void handleActionHistoryCommand(ActionHistoryCommand command) throws TelegramApiException {

    }

    public void handleOrderHistoryCommand(OrderHistoryCommand command) throws TelegramApiException {
        List<Order> orders = orderRepository.findByUserId(String.valueOf(command.userId));
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

    public void handleMenuCommand(MenuCommand command) throws TelegramApiException {
        log.info("handleMenu");
//        DbEntityOrders order = orderRepository.findOrderInCartStatus();
//        DbEntityOrders order = orderRepository.findOrder(UUID.fromString("e289f6c1-fa21-4f97-aac2-ec564c5dae49"));
//        if (order == null) order = (DbEntityOrders) createOrder();
//        if (order == null) order = DbEntityOrders.id.toString("e289f6c1-fa21-4f97-aac2-ec564c5dae49");
        var items = itemRepository.findAllItems();
//        command.setOrderId(order.getId().toString());
          var message = messageFactory.createMessageForItemsList(command, items);
            sender.sendList(message);

    }

//    private Object createOrder() {
//    }

//    public void

    public void handleOrdersItemsCommand (String chatId, String userid, int orderNumber) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
//        List<Item> items = itemRepository.findByOrderNumber(userid, orderNumber);
//        for (int i = 0; i < items.size(); i++) {
////            System.out.println(items.get(i).toString());
////            for (int j = 0; j < ; j++) {
////
////            }
//            sendMessage.setText(items.get(i).name + items.get(i).price);
//            sender.send(sendMessage);

//        }
//        sendMessage.setText(items.toString());
//        sender.send(sendMessage);
    }

    public void handleAddItem (AddItemCommand addItemCommand) throws TelegramApiException {
        DbEntityOrders order = orderRepository.findOrder(UUID.fromString("e289f6c1-fa21-4f97-aac2-ec564c5dae49"));
//        DbEntityItems item = itemRepository.findItem(UUID.fromString(addItemCommand.getItemId()));
        order.setItems(String.valueOf(addItemCommand.getItemId()));
        SendMessage sendMessage = new SendMessage(addItemCommand.getChatId(), "товар добавлен в заказ");
        sender.send(sendMessage);
    }

    public void handleGetInfoCommand (GetItemInfoCommand getItemInfoCommand) throws TelegramApiException {
        DbEntityItems item = itemRepository.findItem(UUID.fromString(getItemInfoCommand.getItemId()));
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(getItemInfoCommand.chatId);
        sendMessage.setText(item.getName() + "\n" + item.getDescription() + "\n" + item.getPrice());
        sender.send(sendMessage);
    }

}