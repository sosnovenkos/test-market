package ru.exampl.bot2.domain.command;

import lombok.Builder;
import lombok.Data;
import lombok.Data;


@Data
//@Builder
public class AddItemCommand {
    public String chatId;
    public Long userId;
    public Long itemId;
//    public Long orderId;
}
