package ru.csv.order_management.domain.context;

import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.csv.order_management.domain.command.FindChildCommand;
import ru.csv.order_management.service.MessageFactory;
import ru.csv.order_management.store.entity.Items;
import ru.csv.order_management.store.entity.Order;

import java.util.List;

@Builder
public class FindChildCommandContext implements Context{
    public FindChildCommand command;
    public Order order;
    public List<Items> items;

    @Override
    public List<SendMessage> handle(MessageFactory factory) {
        return factory.createMessages(this);
    }
}
