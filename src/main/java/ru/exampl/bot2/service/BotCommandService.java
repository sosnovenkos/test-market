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
import ru.exampl.bot2.store.entity.DbEntityOrder;

import java.util.ArrayList;
import java.util.List;


import static java.util.Objects.isNull;

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

        var message = messageFactory.createUser(command);
        actionRepositoryImpl.save(command);
        sender.send(message);
    }

    public void handlePriceCommand(PriceCommand command) throws TelegramApiException {
        log.info("handlePrice");
        DbEntityOrder order = orderRepository.findOrderInCartStatus(command.getUserId());
        if (isNull(order)) {
            order = new DbEntityOrder();
            order.setStatus("cart");
            order.setUserId(command.getUserId());
//            order.setId(Long.valueOf(command.getChatId()));
            try {
                orderRepository.saveOrder(order);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
//        DbEntityOrder order = orderRepository.findOrder(Long.fromString("e289f6c1-fa21-4f97-aac2-ec564c5dae49"));
//        if (order == null) order = (DbEntityOrder) createOrder();
//        if (order == null) order = DbEntityOrder.id.toString("e289f6c1-fa21-4f97-aac2-ec564c5dae49");
        var items = itemRepository.findAllItems();
//        command.setOrderId(order.getId().toString());
        var message = messageFactory.createMessageForItemsList(command, order, items);
        sender.sendList(message);
    }

    public void handleAddItem (AddItemCommand addItemCommand) throws TelegramApiException {

//        DbEntityOrder order = orderRepository.findOrder(addItemCommand.getOrderId());
        DbEntityOrder order = orderRepository.findOrderInCartStatus(addItemCommand.getUserId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(addItemCommand.getChatId());
        if (order != null) {
            if (order.getItems() == null) {
                order.setItems(List.of(addItemCommand.getItemId()));
            } else {
                order.getItems().add(addItemCommand.getItemId());
            }
            orderRepository.saveOrder(order);
            sendMessage.setText("Товар добавлен в заказ");
        } else {
            sendMessage.setText("Заказ не создан");
        }
//        DbEntityItems item = itemRepository.findItem(Long.fromString(addItemCommand.getItemId()));
        sender.send(sendMessage);
    }

    public void handleDelItem (DelItemCommand delItemCommand) throws TelegramApiException {
        DbEntityOrder order = orderRepository.findOrderInCartStatus(Long.valueOf(delItemCommand.getUserId()));
        order.getItems().remove(delItemCommand.getItemId());
        orderRepository.saveOrder(order);
        SendMessage sendMessage = new SendMessage(delItemCommand.getChatId(), "товар удалён из корзины");
        sender.send(sendMessage);
        BasketCommand basketCommand = new BasketCommand();
        basketCommand.setChatId(delItemCommand.getChatId());
        basketCommand.setUserid(delItemCommand.getUserId());
        handleBasketCommand(basketCommand);

    }

    public void handleGetInfoCommand (GetItemInfoCommand getItemInfoCommand) throws TelegramApiException {
        DbEntityItems item = itemRepository.findItem(Long.valueOf(getItemInfoCommand.getItemId()));
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(getItemInfoCommand.chatId);
        sendMessage.setText(item.getName() + " " + item.getDescription() + " " + item.getPrice() + " руб.");
        sender.send(sendMessage);
    }

    public void handleBasketCommand (BasketCommand basketCommand) throws TelegramApiException {
        log.info("handleBasket");
        DbEntityOrder order = orderRepository.findOrderInCartStatus(Long.valueOf(basketCommand.getUserid()));
//        log.info("order is null? - " + (order == null) + "\n"  + order.getItems());
        if (order != null && order.getItems() != null && order.getItems().size() > 0){
            List<DbEntityItems> items = new ArrayList<>();
            for (Long i: order.getItems()) {
                items.add(itemRepository.findItem(i));
            }
            var message = messageFactory.createMessageForBasket(basketCommand, items);
            log.info("List<DbEntityItems> items " + items.size());
            sender.sendList(message);
        } else {
            SendMessage message = new SendMessage(basketCommand.getChatId(), " Ваша корзина пуста");
            sender.send(message);
        }
    }

    public void handleCheckoutCommand (CheckoutCommand checkoutCommand) throws TelegramApiException {
        log.info("handleCheckout");
        DbEntityOrder order = orderRepository.findOrderInCartStatus(Long.valueOf(checkoutCommand.getUserid()));
        if (order != null && order.getItems() != null){
            order.setStatus("delivery");
            orderRepository.saveOrder(order);
            SendMessage sendMessage = new SendMessage(checkoutCommand.getChatId(), "Заказ оформлен");
            SendMessage sendMessage2 = new SendMessage("387340096", "Заказ оформлен");
            sender.sendList(List.of(sendMessage, sendMessage2));
        }
    }

    public void handleActionHistoryCommand(ActionHistoryCommand command) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage(command.getChatId(), "У вас пока нет истории заказов");
        sender.send(sendMessage);
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

}
