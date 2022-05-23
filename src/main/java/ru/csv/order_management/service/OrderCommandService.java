package ru.csv.order_management.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.csv.order_management.domain.command.*;
import ru.csv.order_management.sender.Sender;
import ru.csv.order_management.store.ActionRepositoryImpl;
import ru.csv.order_management.store.ItemRepositoryImpl;
import ru.csv.order_management.store.OrderRepositoryImpl;
import ru.csv.order_management.store.AddressRepositoryImpl;
import ru.csv.order_management.store.entity.DbEntityItems;
import ru.csv.order_management.store.entity.DbEntityOrder;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

//import static org.graalvm.compiler.phases.util.GraphOrder.createOrder;

@Slf4j
@Service
@AllArgsConstructor
public class OrderCommandService {
    private final Sender sender;
    private final OrderRepositoryImpl orderRepository;
    private final ItemRepositoryImpl itemRepository;
    private final AddressRepositoryImpl addressRepository;
    private final MessageFactory messageFactory;
    private final ActionRepositoryImpl actionRepositoryImpl;

    @Value("${bot.admin.chat-ids}")
    private List<String> adminChatIds;

    public void handle(Command command) throws TelegramApiException {
        if (command instanceof StartCommand) handleStartCommand((StartCommand)command);
        if (command instanceof PriceCommand) handlePriceCommand((PriceCommand)command);
        if (command instanceof FindChildCommand) handleFindChildCommand((FindChildCommand)command);
        if (command instanceof AddItemCommand) handleAddItemCommand((AddItemCommand)command);
        if (command instanceof DelItemCommand) handleDelItemCommand((DelItemCommand)command);
        if (command instanceof GetItemInfoCommand) handleGetInfoCommand((GetItemInfoCommand)command);
        if (command instanceof BasketCommand) handleBasketCommand((BasketCommand)command);
        if (command instanceof CheckoutCommand) handleCheckoutCommand((CheckoutCommand)command);
        if (command instanceof ActionHistoryCommand) handleActionHistoryCommand((ActionHistoryCommand)command);
        if (command instanceof OrderHistoryCommand) handleOrderHistoryCommand((OrderHistoryCommand)command);
    }

    public void handleStartCommand(StartCommand command) throws TelegramApiException {
//        actionRepositoryImpl.save(command);
        var messages = new ArrayList<>(List.of(messageFactory.createReplyToStartCommand(command)));
        adminChatIds.forEach(id -> messages.add(new SendMessage(id, "Дана команда .start от @" + command.userName)));
        sender.sendList(messages);
    }

    public void handlePriceCommand(PriceCommand command) throws TelegramApiException {
        var items = itemRepository.findHeadGroup();
        var message = messageFactory.createMessageForGroupList(command, items);
        sender.sendList(message);
    }

    public void handleFindChildCommand(FindChildCommand command) throws TelegramApiException {
        var order = orderRepository.findOrderInCartStatus(command.getUserId());
        if (isNull(order)) {
            order = new DbEntityOrder();
            order.setStatus("cart");
            order.setUserId(command.getUserId());
            orderRepository.saveOrder(order);
        }
        var items = itemRepository.findChildGroup(command.parentId);
        var message = messageFactory.createMessageForItemsList(command, order, items);
        sender.sendList(message);
    }

    public void handleAddItemCommand (AddItemCommand addItemCommand) throws TelegramApiException {
        var order = orderRepository.findOrderInCartStatus(addItemCommand.getUserId());
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
        sender.send(sendMessage);
    }

    public void handleDelItemCommand (DelItemCommand delItemCommand) throws TelegramApiException {
        var order = orderRepository.findOrderInCartStatus(Long.valueOf(delItemCommand.getUserId()));
        order.getItems().remove(delItemCommand.getItemId());
        orderRepository.saveOrder(order);
        SendMessage sendMessage = new SendMessage(delItemCommand.getChatId(), "Товар удалён из корзины");
        sender.send(sendMessage);
        var nextCommand = BasketCommand.builder()
                .chatId(delItemCommand.getChatId())
                .userId(delItemCommand.getUserId())
                .build();
        handleBasketCommand(nextCommand);

    }

    public void handleGetInfoCommand (GetItemInfoCommand getItemInfoCommand) throws TelegramApiException {
        var item = itemRepository.findItem(Long.valueOf(getItemInfoCommand.getItemId()));
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(getItemInfoCommand.chatId);
        sendMessage.setText(item.getName() + " " + item.getDescription() + " " + item.getPrice() + " руб.");
        sender.send(sendMessage);
    }

    public void handleBasketCommand (BasketCommand basketCommand) throws TelegramApiException {
        var order = orderRepository.findOrderInCartStatus(Long.valueOf(basketCommand.getUserId()));
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
        var order = orderRepository.findOrderInCartStatus(checkoutCommand.getUserId());
        var addresses = addressRepository.findByUserId(checkoutCommand.getUserId());
        var messages = messageFactory.createMessageForOrdering(checkoutCommand, addresses, order);
        adminChatIds.forEach(id -> messages.add(new SendMessage(id, "Оформил заказ @" + checkoutCommand.userName)));
        sender.sendList(messages);
    }

    public void handleActionHistoryCommand(ActionHistoryCommand command) throws TelegramApiException {
        var sendMessage = new SendMessage(command.getChatId(), "У вас пока нет истории заказов");
        sender.send(sendMessage);
    }

    public void handleOrderHistoryCommand(OrderHistoryCommand command) throws TelegramApiException {
        var orders = orderRepository.findByUserId(command.userId);
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
        sender.send(sendMessage);
    }


}