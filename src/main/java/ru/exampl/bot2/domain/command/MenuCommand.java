package ru.exampl.bot2.domain.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuCommand {
    public String chatId;
}
