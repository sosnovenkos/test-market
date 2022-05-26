package ru.csv.order_management.domain.command;

import lombok.Builder;
import lombok.Data;
import ru.csv.order_management.service.OrderCommandService;


@Data
@Builder
public class AddItemCommand implements Command {
    public String chatId;
    public Long userId;
    public Long itemId;
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
