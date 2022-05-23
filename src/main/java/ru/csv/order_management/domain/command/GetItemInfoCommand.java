package ru.csv.order_management.domain.command;

import lombok.Builder;
import lombok.Data;

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
}
