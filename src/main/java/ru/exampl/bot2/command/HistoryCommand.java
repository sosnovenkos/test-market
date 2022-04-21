package ru.exampl.bot2.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HistoryCommand {
    public String chatId;
    public String userid;
}
