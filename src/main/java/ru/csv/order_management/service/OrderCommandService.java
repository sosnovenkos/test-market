package ru.csv.order_management.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.csv.order_management.domain.command.*;
import ru.csv.order_management.sender.Sender;
import ru.csv.order_management.store.ActionRepositoryImpl;
import ru.csv.order_management.store.AddressRepositoryImpl;
import ru.csv.order_management.store.ItemRepositoryImpl;
import ru.csv.order_management.store.OrderRepositoryImpl;
import ru.csv.order_management.store.entity.DbEntityAction;
import ru.csv.order_management.store.entity.DbEntityAddress;
import ru.csv.order_management.store.entity.DbEntityItems;
import ru.csv.order_management.store.entity.DbEntityOrder;

import java.time.DateTimeException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

//import static org.graalvm.compiler.phases.util.GraphOrder.createOrder;

@Slf4j
@Service
@AllArgsConstructor
public class OrderCommandService {
    private final ObjectMapper mapper;
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
//        if (command instanceof ActionHistoryCommand) handleActionHistoryCommand((ActionHistoryCommand)command);
        if (command instanceof OrderHistoryCommand) handleOrderHistoryCommand((OrderHistoryCommand)command);
        if (command instanceof AddAddressCommand) handleAddAddressCommand((AddAddressCommand)command);
        if (command instanceof WaitingForActionCommand) handleWaitingForActionCommand((WaitingForActionCommand)command);
        if (command instanceof SetAddressCommand) handleSetAddressCommand((SetAddressCommand)command);
    }

    public void handleStartCommand(StartCommand command) throws TelegramApiException {
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
        var order = orderRepository.findOrderInCartStatus(delItemCommand.getUserId());
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
        sendMessage.setText(item.getName() + " " + item.getWeight() + " " + item.getPrice() + " руб.");
        sender.send(sendMessage);
    }

    public void handleBasketCommand (BasketCommand basketCommand) throws TelegramApiException {
        log.info("Handle .BasketCommand");
        var order = orderRepository.findOrderInCartStatus(basketCommand.getUserId());
        if (order != null && order.getItems() != null && order.getItems().size() > 0){
            List<DbEntityItems> items = itemRepository.findItems(order.getItems());
            var amount = items.stream().filter(item -> nonNull(item.getPrice())).mapToLong(DbEntityItems::getPrice).sum();
            var weight = items.stream().filter(item -> nonNull(item.getPrice())).mapToLong(DbEntityItems::getWeight).sum();
            order.setAmount(amount);
            order.setWeight(weight);
            orderRepository.saveOrder(order);
            var message = messageFactory.createMessageForBasket(basketCommand, items, order);
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
        adminChatIds.forEach(id -> messages.add(new SendMessage(id, "Пытался оформить заказ @" + checkoutCommand.userName)));
        sender.sendList(messages);
    }

    public void handleOrderHistoryCommand(OrderHistoryCommand command) throws TelegramApiException {
        var orders = orderRepository.findAllByUserId(command.userId);
        var messages = new ArrayList<SendMessage>();
        if (orders == null || orders.isEmpty()) {
            messages.add(new SendMessage(command.getChatId(), "У вас пока нет истории заказов"));
        } else {
            var dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            messages.add(new SendMessage(command.getChatId(), "Ваши заказы:"));
            orders.forEach( order -> messages.add(new SendMessage(command.getChatId(), ". Заказ " + order.getId() +
                    " от " + order.getCreatedAt().format(dateTimeFormatter) + " на " + order.getAmount() +
                    " руб, " + order.getWeight()/1000.0 + " кг"))
            );
        }
        sender.sendList(messages);
    }

    public void handleAddAddressCommand(AddAddressCommand command) throws TelegramApiException {
        var action = new DbEntityAction();
        action.setUserId(command.userId);
        action.setWaitingForAction(true);
        action.setData("address");
        actionRepositoryImpl.save(action);
        sender.send(new SendMessage(command.getChatId(), "Введите адрес в формате ГОРОД УЛИЦА ДОМ КВАРТИРА, например \"Железнодорожный Пролетарская 2 35\""));
    }

    public void handleWaitingForActionCommand(WaitingForActionCommand command) throws TelegramApiException {
        var waitingForAction = actionRepositoryImpl.findWaitingForActionByUserId(command.userId);
        if (Objects.equals(waitingForAction.getData(), "address")) {
            var address = new DbEntityAddress();
            address.setUserId(command.userId);
            address.setDescription(command.text);
            addressRepository.save(address);
            waitingForAction.setData("phone");
            actionRepositoryImpl.save(waitingForAction);
            sender.send(new SendMessage(command.getChatId(), "Введите номер телефона получателя:"));
        } else if (Objects.equals(waitingForAction.getData(), "phone")) {
            var address = addressRepository.findByUserId(command.userId).stream()
                    .filter(it -> isNull(it.getPhone()))
                    .findFirst()
                    .orElse(new DbEntityAddress());
            address.setPhone(command.text);
            addressRepository.save(address);
            waitingForAction.setWaitingForAction(false);
            waitingForAction.setData(null);
            actionRepositoryImpl.save(waitingForAction);
            sender.send(new SendMessage(command.getChatId(), "Адрес добавлен. Нажмите Оформить заказ."));
        }
    }

    public void handleSetAddressCommand(SetAddressCommand command) throws TelegramApiException {
        var order = orderRepository.findById(command.orderId);
        order.setAddressId(command.addressId);
        order.setStatus("delivery");
        order.setCreatedAt(OffsetDateTime.now());
        orderRepository.saveOrder(order);
        var items = itemRepository.findItems(order.getItems());
        var address = addressRepository.findById(command.addressId);
        var messages = new ArrayList<>(List.of(new SendMessage(command.getChatId(), "Спасибо за заказ. " +
                "В ближайшее время мы свяжемся с вами для уточнения деталей доставки.")));
        adminChatIds.forEach(id -> {
            messages.add(new SendMessage(id, "Поступил заказ (" + order.getWeight() / 1000.0 +
                    " кг, " + order.getAmount() + " руб.):"));

            items.forEach(item -> messages.add(new SendMessage(id, ". " + item.getName() + " " + item.getWeight() + " гр. " + item.getPrice() + " руб.")));

            messages.add(new SendMessage(id, "Доставка по адресу: " + address.getDescription() +
                    ", звонить для согласования времени доставки: " + address.getPhone()));
                }
        );

        sender.sendList(messages);
    }
}


