package ru.exampl.bot2.domain.command;

import lombok.Data;

@Data
//@Builder`
public class PriceCommand {
    public Long userId;
    public String chatId;
}
