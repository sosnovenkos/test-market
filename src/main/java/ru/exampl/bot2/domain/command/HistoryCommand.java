package ru.exampl.bot2.domain.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HistoryCommand {
    public String chatId;
    public Integer userId;
}
