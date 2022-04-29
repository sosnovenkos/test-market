package ru.exampl.bot2.domain.command;

import lombok.Builder;
import lombok.Data;

@Data
public class AddItemToCartCommand {
    public String chatId;
    public String userId;
    public String orderId;
    public String itemId;

}
