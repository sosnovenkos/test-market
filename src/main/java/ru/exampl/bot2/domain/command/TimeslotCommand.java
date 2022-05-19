package ru.exampl.bot2.domain.command;

import lombok.Builder;
import lombok.Data;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;

@Data
@Builder
public class TimeslotCommand {
    public String chatId;
    public Long userId;

}
