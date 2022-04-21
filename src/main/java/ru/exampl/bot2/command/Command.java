package ru.exampl.bot2.command;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum Command {
    START(List.of("start", "старт")),
    HISTORY(List.of("history", "история")),
    MENU(List.of("menu", "меню")),
    UNKNOWN(List.of());

    private final List<String> title;

    public static Command findById(String name) {
        var listOfEnums = Command.values();
        for (Command command : listOfEnums) {
            var list = command.title;
            if (list.contains(name)) {
                return command;
            }
        }
        return UNKNOWN;
//        return Arrays.stream(Command.values()).filter(value -> value.title.contains(name)).findFirst().orElse(UNKNOWN);
    }
}
