package ru.csv.order_management.domain.context;

import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.csv.order_management.domain.command.GetItemInfoCommand;
import ru.csv.order_management.sender.MessageFactory;
import ru.csv.order_management.store.entity.Items;

import java.util.List;

@Builder
public class GetItemInfoCommandContext implements Context{
    public GetItemInfoCommand command;
    public Items item;

    @Override
    public List<SendMessage> handle(MessageFactory factory) {
        return factory.createMessages(this);
    }

    @Override
    public Long getUserId() {
        return command.userId;
    }

    @Override
    public Long getChatId() {
        return Long.parseLong(command.chatId);
    }

    @Override
    public Integer getMessageId() {
        return command.messageId;
    }
}