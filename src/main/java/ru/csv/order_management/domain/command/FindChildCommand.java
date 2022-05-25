package ru.csv.order_management.domain.command;

import lombok.Builder;
import lombok.Data;
import ru.csv.order_management.service.OrderCommandService;

@Data
@Builder
public class FindChildCommand implements Command {
    public Long userId;
    public Long parentId;
    public String chatId;

    @Override
    public Long getId() {
        return userId;
    }

    @Override
    public void handle(OrderCommandService service) {
        service.handle(this);
    }
}
