package ru.csv.order_management.domain.context;

import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.csv.order_management.domain.command.WaitingForActionCommand;
import ru.csv.order_management.service.MessageFactory;

import java.util.List;

@Builder
public class WaitingForActionCommandContext implements Context{
    public WaitingForActionCommand command;
    public String text;

    @Override
    public List<SendMessage> handle(MessageFactory factory) {
        return factory.createMessages(this);
    }
}
