package ru.csv.order_management.domain.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindChildCommand implements Command {
    public Long userId;
    public Long parentId;
    public String chatId;

    @Override
    public Long getId() {
        return userId;
    }
}
