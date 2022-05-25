package ru.csv.order_management.domain.context;

import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.csv.order_management.domain.command.BasketCommand;
import ru.csv.order_management.service.MessageFactory;
import ru.csv.order_management.store.entity.Items;
import ru.csv.order_management.store.entity.Order;

import java.util.List;

@Builder
public class BasketCommandContext implements Context{
    public BasketCommand command;
    public Order order;
    public List<Items> items;
    public String text;

    @Override
    public List<SendMessage> handle(MessageFactory factory) {
        return factory.createMessages(this);
    }
}