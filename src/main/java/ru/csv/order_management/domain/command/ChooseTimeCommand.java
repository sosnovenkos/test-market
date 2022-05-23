package ru.csv.order_management.domain.command;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class ChooseTimeCommand implements Command{
    public Long userId;
    public OffsetDateTime date;
    public String chatId;

    @Override
    public Long getId() {
        return null;
    }
}
