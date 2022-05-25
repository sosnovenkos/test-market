package ru.csv.order_management.domain.command;

import lombok.Builder;
import lombok.Data;
import ru.csv.order_management.service.OrderCommandService;

@Data
@Builder
public class GetItemInfoCommand implements Command {
    public Long userId;
    public String chatId;
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
