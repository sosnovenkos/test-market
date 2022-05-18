package ru.exampl.bot2.domain.command;

import lombok.Data;


@Data
public class DelItemCommand {
    public Long userId;
    public Long itemId;
    public String chatId;

}
