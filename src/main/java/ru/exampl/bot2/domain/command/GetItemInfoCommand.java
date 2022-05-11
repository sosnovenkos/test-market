package ru.exampl.bot2.domain.command;

import lombok.Data;

@Data
public class GetItemInfoCommand {
    public String chatId;
    public String itemId;
}
