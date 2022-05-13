package ru.exampl.bot2.domain.command;

import lombok.Data;

@Data
public class BasketCommand {
    public String chatId;
    public String userid;
    public String orderId;

}
