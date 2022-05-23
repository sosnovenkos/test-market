package ru.exampl.bot2.domain.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeslotCommand {
    public String chatId;
    public String userId;
    public String orderId;
    public String itemId;
}
