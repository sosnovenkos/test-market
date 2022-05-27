package ru.csv.order_management.domain.command;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.csv.order_management.service.OrderCommandService;

public interface Command {
    Long getId();

    void handle(OrderCommandService service) throws TelegramApiException;
}
