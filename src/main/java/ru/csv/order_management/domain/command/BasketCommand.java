package ru.csv.order_management.domain.command;

import lombok.Builder;
import lombok.Data;
import ru.csv.order_management.service.OrderCommandService;

@Data
@Builder
public class BasketCommand implements Command {
    public String chatId;
    public Long userId;
    public Integer messageId;

    @Override
    public Long getId() {
    return userId;
}

    @Override
    public void handle(OrderCommandService service) {
        service.handle(this);
    }
}
