package ru.csv.order_management.domain.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SetAddressCommand implements Command {
    public Long userId;
    public String chatId;
    public Long orderId;
    public Long addressId;

    @Override
    public Long getId() {
        return userId;
    }
}
