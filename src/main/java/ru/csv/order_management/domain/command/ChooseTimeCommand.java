package ru.csv.order_management.domain.command;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class ChooseTimeCommand implements Command{
    public Long userId;
    public OffsetDateTime date;
    public String time;
    public String chatId;
    public Long parentId;

    @Override
    public Long getId() {
        return userId;
    }
}
