package ru.csv.order_management.domain.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckoutCommand implements Command {
    public String chatId;
    public Long userId;
    public String userName;

    @Override
    public Long getId() {
        return userId;
    }
}
