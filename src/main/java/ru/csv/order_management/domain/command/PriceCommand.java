package ru.csv.order_management.domain.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceCommand implements Command {
    public Long userId;
    public String chatId;

    @Override
    public Long getId() {
        return userId;
    }
}