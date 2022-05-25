package ru.csv.order_management.domain.context;

import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.csv.order_management.domain.command.PriceCommand;
import ru.csv.order_management.service.MessageFactory;
import ru.csv.order_management.store.entity.Items;

import java.util.List;

@Builder
public class PriceCommandContext implements Context {
    public PriceCommand command;
    public List<Items> items;

    @Override
    public List<SendMessage> handle(MessageFactory factory) {
        return factory.createMessages(this);
    }
}
