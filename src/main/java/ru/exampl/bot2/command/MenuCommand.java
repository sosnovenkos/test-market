package ru.exampl.bot2.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuCommand {
    public String chatId;
}
