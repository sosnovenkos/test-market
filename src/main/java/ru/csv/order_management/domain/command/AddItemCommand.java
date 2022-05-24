package ru.csv.order_management.domain.command;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AddItemCommand implements Command {
    public String chatId;
    public Long userId;
    public Long itemId;
//    public Long orderId;

    @Override
    public Long getId() {
        return userId;
    }
}
