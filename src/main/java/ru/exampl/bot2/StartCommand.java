package ru.exampl.bot2;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
//@NoArgsConstructor
public class StartCommand {
    public String chatId;
    public String userName;
}
