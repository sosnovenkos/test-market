package ru.csv.order_management.domain.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StartCommand implements Command {
    public String chatId;
    public String firstName;
    public String userName;
    public Long userId;

    @Override
    public Long getId() {
        return userId;
    }
}
