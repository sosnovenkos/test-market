package ru.csv.order_management.domain.command;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class AddItemCommandForEntry implements Command{
    public String chatId;
    public Long userId;
    public String date;
    public String time;

    @Override
    public Long getId() {
        return userId;
    }
}
