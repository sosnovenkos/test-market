package ru.csv.order_management.domain.context;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.csv.order_management.sender.MessageFactory;

import java.util.List;

public interface Context {
    List<SendMessage> handle(MessageFactory factory);

    Long getUserId();

    Long getChatId();

    Integer getMessageId();
}
