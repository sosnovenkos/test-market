package ru.csv.order_management.domain.context;

import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.csv.order_management.domain.command.SetAddressCommand;
import ru.csv.order_management.service.MessageFactory;
import ru.csv.order_management.store.entity.Address;
import ru.csv.order_management.store.entity.Items;
import ru.csv.order_management.store.entity.Order;

import java.util.List;

@Builder
public class SetAddressCommandContext implements Context{
    public SetAddressCommand command;
    public Order order;
    public Address address;
    public List<Items> items;

    @Override
    public List<SendMessage> handle(MessageFactory factory) {
        return factory.createMessages(this);
    }
}