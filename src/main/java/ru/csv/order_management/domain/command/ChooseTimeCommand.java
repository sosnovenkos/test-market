package ru.csv.order_management.domain.command;

import lombok.Builder;
import lombok.Data;
import ru.csv.order_management.service.OrderCommandService;

import java.time.OffsetDateTime;

@Data
@Builder
public class ChooseTimeCommand implements Command {
    public long userId;
    public String chatId;
    public OffsetDateTime date;
    public long parentId;

    @Override
    public Long getId() {
        return userId;
    }

    @Override
    public void handle(OrderCommandService service) {
        service.handle(this);
    }
}
