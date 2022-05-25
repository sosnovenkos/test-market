package ru.csv.order_management.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.csv.order_management.domain.command.*;
import ru.csv.order_management.domain.context.*;
import ru.csv.order_management.store.ActionRepositoryImpl;
import ru.csv.order_management.store.AddressRepositoryImpl;
import ru.csv.order_management.store.ItemRepositoryImpl;
import ru.csv.order_management.store.OrderRepositoryImpl;
import ru.csv.order_management.store.entity.Address;
import ru.csv.order_management.store.entity.Action;
import ru.csv.order_management.store.entity.Items;
import ru.csv.order_management.store.entity.Order;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

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

    public void handle(Command command) {
        command.handle(this);
    }

    public void handle(StartCommand command) {
        sender.prepareAndSend(StartCommandContext.builder().command(command).build());
    }

    public void handle(PriceCommand command) {
        var items = itemRepository.findHeadGroup();
        sender.prepareAndSend(PriceCommandContext.builder().command(command).items(items).build());
    }

    public void handle(FindChildCommand command) {
        var order = orderRepository.findOrderInCartStatus(command.getUserId());
        if (isNull(order)) {
            order = new Order();
            order.setStatus("cart");
            order.setUserId(command.getUserId());
            orderRepository.saveOrder(order);
        }
        var items = itemRepository.findChildGroup(command.parentId);

        var toDeleteMessages = sender.prepareAndSend(FindChildCommandContext.builder().command(command).order(order).items(items).build());
//        toBeDeletedMessagesRepository
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
        sender.prepareAndSend(AddItemCommandContext.builder().command(command).order(order).build());
    }

    public void handle(DelItemCommand command) {
        var order = orderRepository.findOrderInCartStatus(command.getUserId());
        order.getItems().remove(command.getItemId());
        orderRepository.saveOrder(order);
        sender.prepareAndSend(DelItemCommandContext.builder().command(command).build());

        handle(BasketCommand.builder()
                .chatId(command.getChatId())
                .userId(command.getUserId())
                .build());

    }

    public void handle(GetItemInfoCommand command) {
        var item = itemRepository.findItem(Long.valueOf(command.getItemId()));
        sender.prepareAndSend(GetItemInfoCommandContext.builder()
                .command(command)
                .item(item)
                .build());
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

        sender.prepareAndSend(BasketCommandContext.builder()
                .command(command)
                .items(items)
                .order(order)
                .build());

    }

    public void handle (CheckoutCommand checkoutCommand) {
        var order = orderRepository.findOrderInCartStatus(checkoutCommand.getUserId());
        var addresses = addressRepository.findByUserId(checkoutCommand.getUserId());
        sender.prepareAndSend(CheckoutCommandContext.builder()
                .command(checkoutCommand)
                .order(order)
                .addresses(addresses)
                .build());
    }

    public void handle(OrderHistoryCommand command) {
        var orders = orderRepository.findAllByUserId(command.userId);
        sender.prepareAndSend(OrderHistoryCommandContext.builder()
                .command(command)
                .orders(orders)
                .build());
    }

    public void handle(AddAddressCommand command) {
        var action = new Action();
        action.setUserId(command.userId);
        action.setWaitingForAction(true);
        action.setData("address");
        actionRepositoryImpl.save(action);
        sender.prepareAndSend(AddAddressCommandContext.builder()
                .command(command)
                .text("Введите адрес в формате ГОРОД УЛИЦА ДОМ КВАРТИРА, например \"Железнодорожный Пролетарская 2 35\"")
                .build());
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

        sender.prepareAndSend(WaitingForActionCommandContext.builder()
                .command(command)
                .text(text)
                .build());
    }

    public void handle(SetAddressCommand command) {
        var order = orderRepository.findById(command.orderId);
        order.setAddressId(command.addressId);
        order.setStatus("delivery");
        order.setCreatedAt(OffsetDateTime.now());
        orderRepository.saveOrder(order);
        var items = itemRepository.findItems(order.getItems());
        var address = addressRepository.findById(command.addressId);

        sender.prepareAndSend(SetAddressCommandContext.builder()
                .command(command)
                .order(order)
                .address(address)
                .items(items)
                .build());
    }


}
