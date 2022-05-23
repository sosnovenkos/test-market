package ru.csv.order_management.domain.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActionHistoryCommand implements Command {
    public String chatId;
    public Long userId;

    @Override
    public Long getId() {
        return userId;
    }
}
