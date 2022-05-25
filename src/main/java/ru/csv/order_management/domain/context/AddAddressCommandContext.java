package ru.csv.order_management.domain.context;

import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.csv.order_management.domain.command.AddAddressCommand;
import ru.csv.order_management.service.MessageFactory;

import java.util.List;

@Builder
public class AddAddressCommandContext implements Context{
    public AddAddressCommand command;
    public String text;

    @Override
    public List<SendMessage> handle(MessageFactory factory) {
        return factory.createMessages(this);
    }
}