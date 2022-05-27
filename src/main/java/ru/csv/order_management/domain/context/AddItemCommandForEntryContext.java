package ru.csv.order_management.domain.context;

import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.csv.order_management.domain.command.AddItemCommandForEntry;
import ru.csv.order_management.sender.MessageFactory;
import ru.csv.order_management.store.entity.Order;

import java.util.List;

@Builder
public class AddItemCommandForEntryContext implements Context {
    public AddItemCommandForEntry command;
    public Order order;

    @Override
    public List<SendMessage> handle(MessageFactory factory) {
        return factory.createMessages(this);
    }

    @Override
    public Long getUserId() {
        return null;
    }

    @Override
    public Long getChatId() {
        return null;
    }

    @Override
    public Integer getMessageId() {
        return null;
    }
}
