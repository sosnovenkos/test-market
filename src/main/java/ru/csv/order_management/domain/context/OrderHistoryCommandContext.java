package ru.csv.order_management.domain.context;

import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.csv.order_management.domain.command.OrderHistoryCommand;
import ru.csv.order_management.service.MessageFactory;
import ru.csv.order_management.store.entity.Order;

import java.util.List;

@Builder
public class OrderHistoryCommandContext implements Context {
    public OrderHistoryCommand command;
    public List<Order> orders;

    @Override
    public List<SendMessage> handle(MessageFactory factory) {
        return factory.createMessages(this);
    }
}
