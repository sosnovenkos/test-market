package ru.csv.order_management.domain.command;

import lombok.Data;
import ru.csv.order_management.service.OrderCommandService;

@Data
public class AddItemToCartCommand implements Command {
    public String chatId;
    public Long userId;
    public String orderId;
    public String itemId;

    @Override
    public Long getId() {
        return userId;
    }

    @Override
    public void handle(OrderCommandService service) {
        service.handle(this);
    }
}
