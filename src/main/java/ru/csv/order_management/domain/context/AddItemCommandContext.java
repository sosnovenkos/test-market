package ru.csv.order_management.domain.context;

import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.csv.order_management.domain.command.AddItemCommand;
import ru.csv.order_management.service.MessageFactory;
import ru.csv.order_management.store.entity.Order;

import java.util.List;

@Builder
public class AddItemCommandContext implements Context{
    public AddItemCommand command;
    public Order order;

    @Override
    public List<SendMessage> handle(MessageFactory factory) {
        return factory.createMessages(this);
    }
}
