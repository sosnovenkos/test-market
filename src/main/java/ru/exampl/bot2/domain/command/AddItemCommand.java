package ru.exampl.bot2.domain.command;

import lombok.Builder;
import lombok.Data;
import lombok.Data;

import java.util.UUID;

@Data
//@Builder
public class AddItemCommand {
    public String chatId;
    public UUID itemId;
    public UUID orderId;
}
