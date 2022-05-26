package ru.csv.order_management.domain.command;

import lombok.Builder;
import lombok.Data;
import ru.csv.order_management.service.OrderCommandService;

@Data
@Builder
public class AddItemCommandForEntry implements Command{
    public String chatId;
    public Long userId;
    public Long timeslotId;
    public String date;
    public String time;

    @Override
    public Long getId() {
        return userId;
    }

    @Override
    public void handle(OrderCommandService service) {

    }
}