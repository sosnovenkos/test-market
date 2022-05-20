package ru.exampl.bot2.domain.command;

import lombok.Data;

@Data
public class CheckoutCommand {
    public String chatId;
    public String userid;
    public String username;
}
