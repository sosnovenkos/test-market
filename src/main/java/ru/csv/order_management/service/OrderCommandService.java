package ru.csv.order_management.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.csv.order_management.domain.command.*;
import ru.csv.order_management.domain.context.*;
import ru.csv.order_management.store.*;
import ru.csv.order_management.store.entity.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@AllArgsConstructor
public class OrderCommandService {
    private final Sender sender;
    private final ItemRepositoryImpl itemRepository;
    private final OrderRepositoryImpl orderRepository;
    private final AddressRepositoryImpl addressRepository;
    private final ActionRepositoryImpl actionRepositoryImpl;
    private final MessageToBeDeletedRepositoryImpl messageToBeDeletedRepository;

    private static final List<String> NOT_DELETED_MESSAGES = List.of("Воспользуйтесь меню.");
    private static final List<String> TEMP_NOT_DELETED_MESSAGES = List.of("Нажимайте на позиции, чтобы ДОБАВИТЬ их в корзину");

    public void handle(Command command) {
        command.handle(this);
    }

    public void handle(StartCommand command) {
        var messagesToBeDeletedNextTime = sender.prepareAndSend(StartCommandContext.builder().command(command).build());
        var messagesToBeDeletedNow = filter(messageToBeDeletedRepository.findAllByUserId(command.userId));
        messageToBeDeletedRepository.deleteAll(messagesToBeDeletedNow);
        messageToBeDeletedRepository.save(messagesToBeDeletedNextTime);
        sender.delete(messagesToBeDeletedNow);
    }

    public void handle(PriceCommand command) {
        var items = itemRepository.findHeadGroup();
        var messagesToBeDeletedNextTime = sender.prepareAndSend(PriceCommandContext.builder().command(command).items(items).build());
        var messagesToBeDeletedNow = filter(messageToBeDeletedRepository.findAllByUserId(command.userId));
        messageToBeDeletedRepository.deleteAll(messagesToBeDeletedNow);
        messageToBeDeletedRepository.save(messagesToBeDeletedNextTime);
        sender.delete(messagesToBeDeletedNow);
    }

    public void handle(FindChildCommand command) {
        var order = orderRepository.findOrderInCartStatus(command.getUserId());
        if (isNull(order)) {
            order = new Order();
            order.setStatus("cart");
            order.setUserId(command.getUserId());
            order.setCreatedAt(OffsetDateTime.now());
            orderRepository.saveOrder(order);
        }
        var items = itemRepository.findChildGroup(command.parentId);

        var messagesToBeDeletedNextTime = sender.prepareAndSend(FindChildCommandContext.builder().command(command).order(order).items(items).build());
        var messagesToBeDeletedNow = filter(messageToBeDeletedRepository.findAllByUserId(command.userId));
        messageToBeDeletedRepository.deleteAll(messagesToBeDeletedNow);
        messageToBeDeletedRepository.save(messagesToBeDeletedNextTime);
        sender.delete(messagesToBeDeletedNow);
    }

    public void handle(AddItemCommand command) {
        var order = orderRepository.findOrderInCartStatus(command.getUserId());
        if (order != null) {
            if (order.getItems() == null) {
                order.setItems(List.of(command.getItemId()));
            } else {
                order.getItems().add(command.getItemId());
            }
            orderRepository.saveOrder(order);
        }

        var messagesToBeDeletedNextTime = sender.prepareAndSend(AddItemCommandContext.builder().command(command).order(order).build());
        var messagesToBeDeletedNow = tempFilter(filter(messageToBeDeletedRepository.findAllByUserId(command.userId)));
        messageToBeDeletedRepository.deleteAll(messagesToBeDeletedNow);
        messageToBeDeletedRepository.save(messagesToBeDeletedNextTime);
        sender.delete(messagesToBeDeletedNow);
    }

    public void handle(DelItemCommand command) {
        var order = orderRepository.findOrderInCartStatus(command.getUserId());
        order.getItems().remove(command.getItemId());
        orderRepository.saveOrder(order);
        var messagesToBeDeletedNextTime = sender.prepareAndSend(DelItemCommandContext.builder().command(command).build());
        var messagesToBeDeletedNow = filter(messageToBeDeletedRepository.findAllByUserId(command.userId));
        messageToBeDeletedRepository.deleteAll(messagesToBeDeletedNow);
        messageToBeDeletedRepository.save(messagesToBeDeletedNextTime);
        sender.delete(messagesToBeDeletedNow);

        handle(BasketCommand.builder()
                .chatId(command.getChatId())
                .userId(command.getUserId())
                .build());

    }

    public void handle(GetItemInfoCommand command) {
        var item = itemRepository.findItem(Long.valueOf(command.getItemId()));
        var messagesToBeDeletedNextTime = sender.prepareAndSend(GetItemInfoCommandContext.builder()
                .command(command)
                .item(item)
                .build());
        var messagesToBeDeletedNow = filter(messageToBeDeletedRepository.findAllByUserId(command.userId));
        messageToBeDeletedRepository.deleteAll(messagesToBeDeletedNow);
        messageToBeDeletedRepository.save(messagesToBeDeletedNextTime);
        sender.delete(messagesToBeDeletedNow);
    }

    public void handle(BasketCommand command) {
        log.info("Handle .BasketCommand");
        var order = orderRepository.findOrderInCartStatus(command.getUserId());
        List<Items> items = null;
        if (order != null && order.getItems() != null && order.getItems().size() > 0) {
            items = itemRepository.findItems(order.getItems());
            var amount = items.stream().filter(item -> nonNull(item.getPrice())).mapToLong(Items::getPrice).sum();
            var weight = items.stream().filter(item -> nonNull(item.getPrice())).mapToLong(Items::getWeight).sum();
            order.setAmount(amount);
            order.setWeight(weight);
            orderRepository.saveOrder(order);
        }

        var messagesToBeDeletedNextTime = sender.prepareAndSend(BasketCommandContext.builder()
                .command(command)
                .items(items)
                .order(order)
                .build());
        var messagesToBeDeletedNow = filter(messageToBeDeletedRepository.findAllByUserId(command.userId));
        messageToBeDeletedRepository.deleteAll(messagesToBeDeletedNow);
        messageToBeDeletedRepository.save(messagesToBeDeletedNextTime);
        sender.delete(messagesToBeDeletedNow);;
    }

    public void handle (CheckoutCommand command) {
        var order = orderRepository.findOrderInCartStatus(command.getUserId());
        var addresses = addressRepository.findByUserId(command.getUserId());
        var messagesToBeDeletedNextTime = sender.prepareAndSend(CheckoutCommandContext.builder()
                .command(command)
                .order(order)
                .addresses(addresses)
                .build());
        var messagesToBeDeletedNow = filter(messageToBeDeletedRepository.findAllByUserId(command.userId));
        messageToBeDeletedRepository.deleteAll(messagesToBeDeletedNow);
        messageToBeDeletedRepository.save(messagesToBeDeletedNextTime);
        sender.delete(messagesToBeDeletedNow);
    }

    public void handle(OrderHistoryCommand command) {
        var orders = orderRepository.findAllByUserId(command.userId);
        var messagesToBeDeletedNextTime = sender.prepareAndSend(OrderHistoryCommandContext.builder()
                .command(command)
                .orders(orders)
                .build());
        var messagesToBeDeletedNow = filter(messageToBeDeletedRepository.findAllByUserId(command.userId));
        messageToBeDeletedRepository.deleteAll(messagesToBeDeletedNow);
        messageToBeDeletedRepository.save(messagesToBeDeletedNextTime);
        sender.delete(messagesToBeDeletedNow);
    }

    public void handle(AddAddressCommand command) {
        var action = new Action();
        action.setUserId(command.userId);
        action.setWaitingForAction(true);
        action.setData("address");
        actionRepositoryImpl.save(action);
        var messagesToBeDeletedNextTime = sender.prepareAndSend(AddAddressCommandContext.builder()
                .command(command)
                .text("Введите адрес в формате ГОРОД УЛИЦА ДОМ КВАРТИРА, например \"Железнодорожный Пролетарская 2 35\"")
                .build());
        var messagesToBeDeletedNow = filter(messageToBeDeletedRepository.findAllByUserId(command.userId));
        messageToBeDeletedRepository.deleteAll(messagesToBeDeletedNow);
        messageToBeDeletedRepository.save(messagesToBeDeletedNextTime);
        sender.delete(messagesToBeDeletedNow);
    }

    public void handle(WaitingForActionCommand command) {
        var waitingForAction = actionRepositoryImpl.findWaitingForActionByUserId(command.userId);
        String text = "";
        if (Objects.equals(waitingForAction.getData(), "address")) {
            var address = new Address();
            address.setUserId(command.userId);
            address.setDescription(command.text);
            addressRepository.save(address);
            waitingForAction.setData("phone");
            actionRepositoryImpl.save(waitingForAction);
            text = "Введите номер телефона получателя:";
        } else if (Objects.equals(waitingForAction.getData(), "phone")) {
            var address = addressRepository.findByUserId(command.userId).stream()
                    .filter(it -> isNull(it.getPhone()))
                    .findFirst()
                    .orElse(new Address());
            address.setPhone(command.text);
            addressRepository.save(address);
            waitingForAction.setWaitingForAction(false);
            waitingForAction.setData(null);
            actionRepositoryImpl.save(waitingForAction);
            text = "Адрес добавлен. Нажмите Оформить заказ.";
        }

        var messagesToBeDeletedNextTime = sender.prepareAndSend(WaitingForActionCommandContext.builder()
                .command(command)
                .text(text)
                .build());
        var messagesToBeDeletedNow = filter(messageToBeDeletedRepository.findAllByUserId(command.userId));
        messageToBeDeletedRepository.deleteAll(messagesToBeDeletedNow);
        messageToBeDeletedRepository.save(messagesToBeDeletedNextTime);
        sender.delete(messagesToBeDeletedNow);
    }

    public void handle(SetAddressCommand command) {
        var order = orderRepository.findById(command.orderId);
        order.setAddressId(command.addressId);
        order.setStatus("delivery");
        order.setCreatedAt(OffsetDateTime.now());
        orderRepository.saveOrder(order);
        var items = itemRepository.findItems(order.getItems());
        var address = addressRepository.findById(command.addressId);

        var messagesToBeDeletedNextTime = sender.prepareAndSend(SetAddressCommandContext.builder()
                .command(command)
                .order(order)
                .address(address)
                .items(items)
                .build());
        var messagesToBeDeletedNow = filter(messageToBeDeletedRepository.findAllByUserId(command.userId));
        messageToBeDeletedRepository.deleteAll(messagesToBeDeletedNow);
        messageToBeDeletedRepository.save(messagesToBeDeletedNextTime);
        sender.delete(messagesToBeDeletedNow);
    }

    private List<MessageToBeDeleted> filter(List<MessageToBeDeleted> messagesToBeDeletedNow) {
        return messagesToBeDeletedNow.stream().filter(it -> it.getText() == null || !NOT_DELETED_MESSAGES.contains(it.getText())).collect(Collectors.toList());
    }

    private List<MessageToBeDeleted> tempFilter(List<MessageToBeDeleted> messagesToBeDeletedNow) {
        return messagesToBeDeletedNow.stream().filter(it -> it.getText() == null || !TEMP_NOT_DELETED_MESSAGES.contains(it.getText())).collect(Collectors.toList());
    }
}
