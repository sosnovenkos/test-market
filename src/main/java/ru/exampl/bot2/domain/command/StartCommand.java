package ru.exampl.bot2.domain.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
//@NoArgsConstructor
public class StartCommand {
    public String chatId;
    public String userName;

    public Long userId;
}
