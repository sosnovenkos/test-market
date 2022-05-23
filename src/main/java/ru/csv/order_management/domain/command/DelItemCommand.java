package ru.csv.order_management.domain.command;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class DelItemCommand implements Command {
    public Long userId;
    public Long itemId;
    public String chatId;

    @Override
    public Long getId() {
        return userId;
    }
}
