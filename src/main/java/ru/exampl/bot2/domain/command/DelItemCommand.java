package ru.exampl.bot2.domain.command;

import lombok.Data;

import java.util.UUID;

@Data
public class DelItemCommand {
    public UUID itemId;
    public String chatId;

}
