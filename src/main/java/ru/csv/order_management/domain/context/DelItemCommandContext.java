package ru.csv.order_management.domain.context;

import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.csv.order_management.domain.command.DelItemCommand;
import ru.csv.order_management.service.MessageFactory;

import java.util.List;

@Builder
public class DelItemCommandContext implements Context {
    public DelItemCommand command;

    @Override
    public List<SendMessage> handle(MessageFactory factory) {
        return factory.createMessages(this);
    }
}